package com.energicube.eno.asset.web;

import com.energicube.eno.asset.model.JobPlan;
import com.energicube.eno.asset.model.JobTask;
import com.energicube.eno.asset.service.JobPlanService;
import com.energicube.eno.common.jsonquery.jpa.request.DataTablesRequestParams;
import com.energicube.eno.common.jsonquery.jpa.response.DataTablesResponse;
import com.energicube.eno.common.model.Message;
import com.energicube.eno.monitor.web.BaseController;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import com.github.dandelion.datatables.core.ajax.DatatablesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 工作计划
 */
@Controller
@RequestMapping("/jobplan")
public class JobPlanController extends BaseController {

    private JobPlanService jobPlanService;

    @Autowired
    public JobPlanController(JobPlanService jobPlanService) {
        this.jobPlanService = jobPlanService;
    }


    /**
     * 显示作业计划列表页
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String showJobPlan(Model model) {
        return "jobplan/listJobPlan";
    }


    /**
     * 显示作业计划列表数据源
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public DatatablesResponse<JobPlan> findJobPlansWithDatatablesCriterias(HttpServletRequest request) {
        DatatablesCriterias criterias = DatatablesCriterias.getFromRequest(request);
        DataSet<JobPlan> jobPlans = jobPlanService.findJobPlansWithDatatablesCriterias(criterias);
        return DatatablesResponse.build(jobPlans, criterias);
    }


    /**
     * 显示作业计划列表对话框
     */
    @RequestMapping(value = "/list/dialog", method = RequestMethod.GET)
    public String showJobPlanListDialog(
            @RequestParam(value = "type", required = false, defaultValue = "") String type,
            Model model) {
        model.addAttribute("type", type);
        return "dialog/dlgJobPlanList";
    }


    /**
     * 新增作业计划
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String initCreationJobPlanForm(Model model) {
        JobPlan jobPlan = new JobPlan();
        JobTask jobTask = new JobTask();
        jobPlan.setStatus("DRAFT");
        jobPlan.setJpduration("0:0");
        jobTask.setTaskduration("0:0");
        model.addAttribute("jobPlan", jobPlan);
        model.addAttribute("jobTask", jobTask);
        return "jobplan/editJobPlanForm";
    }

    /**
     * 校验作业计划代码是否已经存在
     *
     * @param company 作业计划代码
     * @return
     */
    @RequestMapping(value = "/new/check", method = RequestMethod.GET)
    @ResponseBody
    public String processCheckNewCompany(@RequestParam("jpnum") String jpnum) {
        if (StringUtils.hasLength(jpnum)) {
            return jobPlanService.existJpnum(jpnum.toUpperCase(), "", "") ? "true" : "false";
        } else {
            return "false";
        }

    }

    /**
     * 保存新增作业计划
     *
     * @param jobPlan {@link JobPlan} object
     * @param result  {@link BindingResult} object
     * @param status  {@link SessionStatus} object
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String processCreationCompanyForm(@Valid JobPlan jobPlan,
                                             BindingResult result, SessionStatus status, RedirectAttributes redirectAttrs, Model model) {
        if (result.hasErrors()) {
            JobTask jobTask = new JobTask();
            jobPlan.setStatus("DRAFT");
            jobPlan.setJpduration("0:0");
            jobTask.setTaskduration("0:0");
            model.addAttribute("jobPlan", jobPlan);
            model.addAttribute("jobTask", jobTask);
            return "jobplan/editJobPlanForm";
        } else {
            if (jobPlanService.existJpnum(jobPlan.getJpnum().toUpperCase(), "", "")) {
                result.addError(new ObjectError("company", "作业计划编号已经存在!"));
                return "jobplan/editJobPlanForm";
            } else {
                jobPlan = jobPlanService.saveJobPlan(jobPlan);
                status.setComplete();
                redirectAttrs.addFlashAttribute("message", "作业计划新增成功");
                return "redirect:/jobplan/edit/" + jobPlan.getJobplanid();
            }
        }
    }

    /**
     * 编辑作业计划
     */
    @RequestMapping(value = "/edit/{jobplanid}", method = RequestMethod.GET)
    public String initEditJobPlanForm(@PathVariable long jobplanid, Model model) {
        JobPlan jobPlan = new JobPlan();
        JobTask jobTask = new JobTask();
        if (jobplanid > 0) {
            jobPlan = jobPlanService.findOne(jobplanid);
            jobTask.setJobplanid(jobplanid);
            jobTask.setJpnum(jobPlan.getJpnum());
            jobTask.setOrgid(jobPlan.getOrgid());
            jobTask.setSiteid(jobPlan.getSiteid());
            jobTask.setTaskduration("0:0");
        }
        model.addAttribute("jobPlan", jobPlan);
        model.addAttribute("jobTask", jobTask);
        return "jobplan/editJobPlanForm";
    }

    /**
     * 保存编辑作业计划
     */
    @RequestMapping(value = "/edit/{jobplanid}", method = RequestMethod.PUT)
    public String processEditJobPlanForm(@Valid JobPlan jobPlan,
                                         BindingResult result, SessionStatus status, RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            return "jobplan/editJobPlanForm";
        } else {
            jobPlan = jobPlanService.saveJobPlan(jobPlan);
            status.setComplete();
            redirectAttrs.addFlashAttribute("message", "作业计划更新成功");
            return "redirect:/jobplan/edit/" + jobPlan.getJobplanid();
        }
    }


    /**
     * 显示作业计划作务列表数据源
     */
    @RequestMapping(value = "/{jobplanid}/jobtask", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public DataTablesResponse<JobTask> showJobTaskToDataTables(
            @PathVariable long jobplanid, DataTablesRequestParams params) {
        JobPlan jobPlan = new JobPlan();
        if (jobplanid > 0) {
            jobPlan = jobPlanService.findOne(jobplanid);
            return jobPlanService.findAllJobTasks(jobPlan.getJpnum(), jobPlan.getOrgid(), jobPlan.getSiteid(), params);
        } else {
            return new DataTablesResponse<JobTask>();
        }
    }

    /**
     * 保存作业计划作务
     */
    @RequestMapping(value = "/{jobplanid}/jobtask/save", method = RequestMethod.POST)
    @ResponseBody
    public Message processEditJobTaskForm(@Valid JobTask jobTask, BindingResult result, SessionStatus status) {
        Message message = new Message();
        message.setSuccess(false);
        if (result.hasErrors()) {
            String msg = result.getFieldError().getField() + "" + result.getFieldError().getDefaultMessage();
            message.setMsg(msg);
        } else {
            boolean isExist = jobPlanService.existJobTask(jobTask.getJpnum(), jobTask.getJptask(), jobTask.getOrgid(), jobTask.getSiteid());
            if (isExist) {
                message.setMsg("在同一作业计划中存在相同的任务ID");
            } else {
                jobTask = jobPlanService.saveJobTask(jobTask);
                status.setComplete();
                message.setMsg("计划任务更新成功");
                message.setSuccess(true);
            }
        }
        return message;
    }


    @RequestMapping(value = "/{jobplanid}/jobtask/delete", method = RequestMethod.POST)
    @ResponseBody
    public Message processDeleteJobTask(@RequestParam("id") long jobtaskid) {
        Message message = new Message();
        message.setSuccess(false);

        try {
            jobPlanService.deleteJobTaskById(jobtaskid);
            message.setSuccess(true);
        } catch (Exception e) {
            message.setMsg(e.toString());
            e.printStackTrace();
        }

        return message;
    }

}
