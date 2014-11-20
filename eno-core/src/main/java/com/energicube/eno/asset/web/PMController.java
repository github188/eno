package com.energicube.eno.asset.web;

import com.energicube.eno.asset.model.*;
import com.energicube.eno.asset.service.PMService;
import com.energicube.eno.common.jsonquery.jpa.request.DataTablesRequestParams;
import com.energicube.eno.common.jsonquery.jpa.response.DataTablesResponse;
import com.energicube.eno.common.model.Message;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import com.github.dandelion.datatables.core.ajax.DatatablesResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

/**
 * 预防性维护计划页面控制类
 *
 * @author CHENPING
 * @version 1.0
 */
@Controller
@RequestMapping("/pm")
public class PMController extends BaseAssetController {

    private final static Log logger = LogFactory.getLog(PMController.class);


    private PMService pmService;

    @Autowired
    public PMController(PMService pmService) {
        this.pmService = pmService;
    }

    /**
     * 预防性维护计划列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String showPMlist(Model model) {
        return "pm/listPM";
    }

    /**
     * 资台台账数据源
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public DatatablesResponse<PM> findPMsWithDatatablesCriterias(HttpServletRequest request) {
        DatatablesCriterias criterias = DatatablesCriterias.getFromRequest(request);
        DataSet<PM> pms = pmService.findPMsWithDatatablesCriterias(criterias);
        return DatatablesResponse.build(pms, criterias);
    }


    /**
     * 新增预防性维护计划
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String initCreationPMForm(Model model) {
        PM pm = pmService.initPM();
        model.addAttribute("pm", pm);
        return "pm/editPMForm";
    }

    /**
     * 保存预防性维护计划信息
     *
     * @param pm            预防性维护计划信息
     * @param result        验证结果信息
     * @param status        状态对象
     * @param redirectAttrs 跳转属性
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String processCreationPMForm(@Valid @ModelAttribute("pm") PM pm, BindingResult result,
                                        SessionStatus status, RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            return "pm/editPMForm";
        } else {
            try {
                if (pmService.existPM(pm.getPmnum(), pm.getSiteid())) {
                    result.addError(new ObjectError("pmnum", "PM编号已经存在!"));
                    return "pm/editPMForm";
                } else {
                    pm = pmService.savePM(pm);
                    status.setComplete();
                    redirectAttrs.addFlashAttribute("message", "PM创建成功");
                    return "redirect:/pm/edit/" + pm.getPmid();
                }
            } catch (Exception e) {
                logger.error(e);
                e.printStackTrace();
            }
            return "pm/editPMForm";
        }
    }

    /**
     * 设置PM
     */
    private PM setPMModel(long pmid, Model model) {
        PM pm = new PM();
        if (pmid > 0) {
            try {
                pm = pmService.findPMById(pmid);
            } catch (Exception e) {
                pm = new PM();
                logger.error(e);
                e.printStackTrace();
            }
        }
        model.addAttribute("pm", pm);
        return pm;
    }


    /**
     * 新增预防性维护计划
     */
    @RequestMapping(value = "/edit/{pmid}", method = RequestMethod.GET)
    public String initEditingPMForm(@PathVariable long pmid, Model model) {
        setPMModel(pmid, model);
        return "pm/editPMForm";
    }

    /**
     * 保存预防性维护计划信息
     *
     * @param pm            预防性维护计划信息
     * @param result        验证结果信息
     * @param status        状态对象
     * @param redirectAttrs 跳转属性
     */
    @RequestMapping(value = "/edit/{pmid}", method = RequestMethod.PUT)
    public String processEditPMForm(@PathVariable long pmid, @Valid @ModelAttribute("pm") PM pm, BindingResult result,
                                    SessionStatus status, RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            return "pm/editPMForm";
        } else {
            try {
                pm = pmService.savePM(pm);
                status.setComplete();
                redirectAttrs.addFlashAttribute("message", "PM更新成功");
                return "redirect:/pm/edit/" + pm.getPmid();
            } catch (Exception e) {
                logger.error(e);
                e.printStackTrace();
            }
            return "pm/editPMForm";
        }
    }


    /**
     * 频率设置列表
     */
    @RequestMapping(value = "/{pmid}/frequency", method = RequestMethod.GET)
    public String showFrequencylist(@PathVariable long pmid, Model model) {
        PM pm = new PM();
        PMMeterSet pmMeterSet = null;
        pm = setPMModel(pmid, model);
        try {
            pmMeterSet = pmService.initPMMeter(pm);
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
        model.addAttribute("pmMeterSet", pmMeterSet);
        return "pm/listFrequency";
    }

    /**
     * 基于计量器的频率列表
     *
     * @param pmid   维护ID
     * @param params 请求参数
     * @return 频率列表
     */
    @RequestMapping(value = "/{pmid}/frequency", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public DataTablesResponse<PMMeterSet> showFrequencylistToDataTable(@PathVariable long pmid,
                                                                       DataTablesRequestParams params, Model model) {
        PM pm = new PM();
        pm = setPMModel(pmid, model);
        return pmService.findPMMeters(pm.getPmnum(), pm.getSiteid(), params);
    }


    /**
     * 频率设置列表
     */
    @RequestMapping(value = "/{pmid}/frequency", method = RequestMethod.PUT)
    public String processUpdateFrequency(@PathVariable long pmid, @ModelAttribute("pm") PM pm, BindingResult result,
                                         SessionStatus status, RedirectAttributes redirectAttrs, Model model) {
        if (result.hasErrors()) {
            return "pm/listFrequency";
        } else {
            try {
                pm = pmService.savePMByFrequency(pm);
                status.setComplete();
                redirectAttrs.addFlashAttribute("message", "更新成功");
                return "redirect:/pm/" + pm.getPmid() + "/frequency";
            } catch (Exception e) {
                logger.error(e);
                e.printStackTrace();
            }
        }
        return "pm/listFrequency";
    }


    /**
     * 频率设置列表
     */
    @RequestMapping(value = "/{pmid}/frequency/save", method = RequestMethod.POST)
    @ResponseBody
    public Message processUpdateFrequencyDetail(@PathVariable long pmid, @ModelAttribute("pmMeterSet") PMMeterSet pmMeterSet,
                                                BindingResult result, SessionStatus status) {
        Message message = new Message();
        message.setSuccess(false);
        if (result.hasErrors()) {
            message.setMsg(result.toString());
        } else {
            try {

                PMMeter pmMeter = pmMeterSet.getPmMeter();
                pmService.savePMMeter(pmMeter);
                status.setComplete();
                message.setSuccess(true);

            } catch (Exception e) {
                logger.error(e);
                e.printStackTrace();
                message.setMsg(e.getMessage());
            }
        }
        return message;
    }


    /**
     * 季节性日期列表
     */
    @RequestMapping(value = "/{pmid}/seasons", method = RequestMethod.GET)
    public String showPMSeasonslist(@PathVariable long pmid, Model model) {
        PM pm = new PM();
        pm = setPMModel(pmid, model);
        PMSeasons pmSeasons = null;
        try {
            pmSeasons = pmService.initPMSeasons(pm);
        } catch (Exception e) {
            pmSeasons = new PMSeasons();
            logger.error(e);
            e.printStackTrace();
        }
        model.addAttribute("pmSeasons", pmSeasons);
        return "pm/listSeasons";
    }


    /**
     * 季节性日期列表
     *
     * @param pmid   维护ID
     * @param params 请求参数
     * @return 季节性日期列表
     */
    @RequestMapping(value = "/{pmid}/seasons", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public DataTablesResponse<PMSeasons> showPMSeasonslistToDataTable(@PathVariable long pmid, DataTablesRequestParams params, Model model) {
        PM pm = new PM();
        pm = setPMModel(pmid, model);
        return pmService.findPMSeasonsList(pm.getPmnum(), pm.getSiteid(), params);
    }

    /**
     * 更新季节性日期设置
     */
    @RequestMapping(value = "/{pmid}/seasons", method = RequestMethod.PUT)
    public String processUpdateSeasons(@PathVariable long pmid, @ModelAttribute("pm") PM pm, BindingResult result,
                                       SessionStatus status, RedirectAttributes redirectAttrs, Model model) {
        if (result.hasErrors()) {
            return "pm/listSeasons";
        } else {
            try {
                pm = pmService.savePMBySeasons(pm);
                status.setComplete();
                redirectAttrs.addFlashAttribute("message", "更新成功");
                return "redirect:/pm/" + pm.getPmid() + "/seasons";
            } catch (Exception e) {
                logger.error(e);
                e.printStackTrace();
            }
        }
        return "pm/listSeasons";
    }

    /**
     * 保存季节性日期设置
     */
    @RequestMapping(value = "/{pmid}/seasons/save", method = RequestMethod.POST)
    @ResponseBody
    public Message processPMSeasonsForm(@PathVariable long pmid, @ModelAttribute("pmSeasons") PMSeasons pmSeasons,
                                        BindingResult result, SessionStatus status) {
        Message message = new Message();
        message.setSuccess(false);
        if (result.hasErrors()) {
            message.setMsg(result.toString());
        } else {
            try {
                pmService.savePMSeasons(pmSeasons);
                status.setComplete();
                message.setSuccess(true);

            } catch (Exception e) {
                logger.error(e);
                e.printStackTrace();
                message.setMsg(e.getMessage());
            }
        }
        return message;
    }


    /**
     * 作业计划序列列表
     */
    @RequestMapping(value = "/{pmid}/sequence", method = RequestMethod.GET)
    public String showPMSequencelist(@PathVariable long pmid, Model model) {
        PM pm = new PM();
        pm = setPMModel(pmid, model);
        PMSequenceSet pmSequenceSet = null;
        try {
            pmSequenceSet = pmService.initPMSequenceSet(pm);
        } catch (Exception e) {
            pmSequenceSet = new PMSequenceSet();
            logger.error(e);
            e.printStackTrace();
        }
        model.addAttribute("pmSequenceSet", pmSequenceSet);
        return "pm/listSequence";
    }

    /**
     * 季节性日期列表
     *
     * @param pmid   维护ID
     * @param params 请求参数
     * @return 季节性日期列表
     */
    @RequestMapping(value = "/{pmid}/sequence", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public DataTablesResponse<PMSequenceSet> showPMSequencelistToDataTable(@PathVariable long pmid, DataTablesRequestParams params, Model model) {
        PM pm = new PM();
        pm = setPMModel(pmid, model);
        return pmService.findPMSequenceList(pm.getPmnum(), pm.getSiteid(), pm.getOrgid(), params);
    }


    /**
     * 更新作业计划序列设置
     */
    @RequestMapping(value = "/{pmid}/sequence", method = RequestMethod.PUT)
    public String processUpdateSequence(@PathVariable long pmid, @ModelAttribute("pm") PM pm, BindingResult result,
                                        SessionStatus status, RedirectAttributes redirectAttrs, Model model) {
        if (result.hasErrors()) {
            return "pm/listSequence";
        } else {
            try {
                pm = pmService.savePMBySequence(pm);
                status.setComplete();
                redirectAttrs.addFlashAttribute("message", "更新成功");
                return "redirect:/pm/" + pm.getPmid() + "/sequence";
            } catch (Exception e) {
                logger.error(e);
                e.printStackTrace();
            }
        }
        return "pm/listSequence";
    }

    /**
     * 频率设置列表
     */
    @RequestMapping(value = "/{pmid}/sequence/save", method = RequestMethod.POST)
    @ResponseBody
    public Message processPMSequenceForm(@PathVariable long pmid, @ModelAttribute("pmSequenceSet") PMSequenceSet pmSequenceSet,
                                         BindingResult result, SessionStatus status) {
        Message message = new Message();
        message.setSuccess(false);
        if (result.hasErrors()) {
            message.setMsg(result.toString());
        } else {
            try {

                PMSequence pmSequence = pmSequenceSet.getPmSequence();
                pmService.savePMSequence(pmSequence);
                status.setComplete();
                message.setSuccess(true);

            } catch (Exception e) {
                logger.error(e);
                e.printStackTrace();
                message.setMsg(e.getMessage());
            }
        }
        return message;
    }


    /**
     * PM层次结构列表
     */
    @RequestMapping(value = "/{pmid}/ancestor", method = RequestMethod.GET)
    public String showPMAncestorlist(@PathVariable long pmid, Model model) {
        PM pm = new PM();
        pm = setPMModel(pmid, model);
        PMAncestorSet pmAncestorSet = pmService.initPMAncestorSet(pm);
        model.addAttribute("pmAncestorSet", pmAncestorSet);
        return "pm/listAncestor";
    }

    /**
     * PM层次结构列表
     *
     * @param pmid   维护ID
     * @param params 请求参数
     * @return PM层次结构列表
     */
    @RequestMapping(value = "/{pmid}/ancestor", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public DataTablesResponse<PMAncestorSet> showPMAncestorsToDataTable(@PathVariable long pmid, DataTablesRequestParams params, Model model) {
        PM pm = new PM();
        pm = setPMModel(pmid, model);
        return pmService.findPMAncestorList(pm.getPmnum(), pm.getSiteid(), params);
    }

    /**
     * 更新PM层次结构
     */
    @RequestMapping(value = "/{pmid}/ancestor", method = RequestMethod.PUT)
    public String processUpdatePMAncestor(@PathVariable long pmid, @ModelAttribute("pm") PM pm, BindingResult result,
                                          SessionStatus status, RedirectAttributes redirectAttrs, Model model) {
        if (result.hasErrors()) {
            return "pm/listAncestor";
        } else {
            try {
                pm = pmService.savePMByAncestor(pm);
                status.setComplete();
                redirectAttrs.addFlashAttribute("message", "更新成功");
                return "redirect:/pm/" + pm.getPmid() + "/ancestor";
            } catch (Exception e) {
                logger.error(e);
                e.printStackTrace();
            }
        }
        return "pm/listAncestor";
    }

    /**
     * 频率设置列表
     */
    @RequestMapping(value = "/{pmid}/ancestor/save", method = RequestMethod.POST)
    @ResponseBody
    public Message processPMSequenceForm(@PathVariable long pmid, @ModelAttribute("pmAncestorSet") PMAncestorSet pmAncestorSet,
                                         BindingResult result, SessionStatus status) {
        Message message = new Message();
        message.setSuccess(false);
        if (result.hasErrors()) {
            message.setMsg(result.toString());
        } else {
            try {

                PMAncestor pmAncestor = pmAncestorSet.getPmAncestor();
                pmService.savePMAncestor(pmid, pmAncestor);
                status.setComplete();
                message.setSuccess(true);
            } catch (Exception e) {
                logger.error(e);
                e.printStackTrace();
                message.setMsg(e.getMessage());
            }
        }
        return message;
    }
}
