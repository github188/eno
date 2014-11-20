package com.energicube.eno.asset.web;


import com.energicube.eno.asset.model.FailureCode;
import com.energicube.eno.asset.model.FailureList;
import com.energicube.eno.asset.model.FailureListSet;
import com.energicube.eno.asset.service.FailureCodeService;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
@RequestMapping("/failurecode")
public class FailureListController extends BaseController {

    @Autowired
    private FailureCodeService failureCodeService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String showFailureList(Model model) {
        return "failurecode/failureList";
    }


    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public DatatablesResponse<FailureCode> showFailureDataSource(HttpServletRequest request) {
        DatatablesCriterias criterias = DatatablesCriterias.getFromRequest(request);
        DataSet<FailureCode> failureCodes = failureCodeService.findFailureCodesWithDatatablesCriterias(criterias);
        return DatatablesResponse.build(failureCodes, criterias);
    }


    @RequestMapping(value = "/list/dialog", method = RequestMethod.GET)
    public String showFailureListDialog(Model model) {
        return "dialog/dlgFailureList";
    }


    /**
     * 新增故障类
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String initCreationFailureListForm(Model model) {
        model.addAttribute("failureList", new FailureList());
        model.addAttribute("failureCode", new FailureCode());
        model.addAttribute("failureListSet", new FailureListSet());
        return "failurecode/editFailurelist";
    }

    /**
     * 保存故障列表
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String processCereationFailureListForm(
            @Valid FailureCode failureCode, BindingResult result,
            SessionStatus status, RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            return "failurecode/editFailurelist";
        } else {
            if (failureCodeService.existFailureCode(failureCode.getFailurecode())) {
                result.addError(new ObjectError("failurecode", "故障代码已经存在"));
                return "failurecode/editFailurelist";
            } else {
                FailureList failureList = failureCodeService.saveFailureList(failureCode);
                status.setComplete();
                redirectAttrs.addFlashAttribute("message", "新增成功");
                return "redirect:/failurecode/edit/" + failureList.getFailurecode();
            }
        }
    }

    /**
     * 编辑故障类别
     *
     * @param failurecodeid 故障类别ID
     */
    @RequestMapping(value = "/edit/{failurecodeid}", method = RequestMethod.GET)
    public String initUpdateFailureListForm(
            @PathVariable("failurecodeid") Long failurecodeid, Model model) {

        FailureCode failureCode = failureCodeService.findFailureCode(failurecodeid);
        FailureList failureList = null;
        if (failureCode != null) {
            failureList = failureCodeService.findFailureListByFailurecode(failureCode.getFailurecode());
        }
        model.addAttribute("failureList", failureList);
        model.addAttribute("failureCode", failureCode);
        model.addAttribute("failureListSet", new FailureListSet());
        return "failurecode/editFailurelist";
    }

    /**
     * 保存故障代码列表信息
     */
    @RequestMapping(value = "/edit/{failurecodeid}", method = RequestMethod.PUT)
    public String processUpdateFailureListForm(@Valid FailureCode failureCode, BindingResult result,
                                               SessionStatus status, RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            return "failurecode/editFailurelist";
        } else {
            FailureList failureList = failureCodeService.saveFailureList(failureCode);
            status.setComplete();
            redirectAttrs.addFlashAttribute("message", "更新成功");
            return "redirect:/failurecode/edit/" + failureList.getFailurecode();
        }
    }

    /**
     * 显示故障问题数据
     *
     * @param failurelist 故障类ID
     * @return
     */
    @RequestMapping(value = "/{failurelist}/problems", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public DataTablesResponse<FailureListSet> showProblemList(@PathVariable("failurelist") long failurelist, DataTablesRequestParams params) {
        return failureCodeService.findProblemList(failurelist, params);
    }


    @RequestMapping(value = "/{failurelist}/problems/save", method = RequestMethod.POST)
    @ResponseBody
    public Message processProblemForm(@ModelAttribute("failureCode") FailureCode failureCode,
                                      @ModelAttribute("failureList") FailureList failureList) {
        Message message = new Message();


        return message;
    }


    @RequestMapping(value = "/{failurelist}/causes", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public DataTablesResponse<FailureListSet> showCausesList(@PathVariable("failurelist") long failurelist, DataTablesRequestParams params) {
        return failureCodeService.findCauseList(failurelist, params);

    }

    @RequestMapping(value = "/{failurelist}/remedys", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public DataTablesResponse<FailureListSet> showRemedysList(@PathVariable("failurelist") long failurelist, DataTablesRequestParams params) {
        return failureCodeService.findRemedyList(failurelist, params);
    }

}
