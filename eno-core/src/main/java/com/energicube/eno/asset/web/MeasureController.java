package com.energicube.eno.asset.web;

import com.energicube.eno.asset.model.*;
import com.energicube.eno.asset.service.AssetService;
import com.energicube.eno.asset.service.MeasurePointService;
import com.energicube.eno.asset.service.MeasureSpecService;
import com.energicube.eno.asset.service.MeasurementService;
import com.energicube.eno.common.jsonquery.jpa.request.DataTablesRequestParams;
import com.energicube.eno.common.jsonquery.jpa.response.DataTablesResponse;
import com.energicube.eno.common.model.Message;
import com.energicube.eno.monitor.web.BaseController;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import com.github.dandelion.datatables.core.ajax.DatatablesResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

@Controller
@RequestMapping("/measure")
public class MeasureController extends BaseController {

    private static final Log logger = LogFactory.getLog(MeasureController.class);
    private MeasurePointService measurePointService;
    private MeasureSpecService measureSpecService;
    private MeasurementService measurementService;
    private AssetService assetService;

    @Autowired
    public MeasureController(MeasurePointService measurePointService,
                             AssetService assetService, MeasureSpecService measureSpecService,
                             MeasurementService measurementService) {
        this.measurePointService = measurePointService;
        this.assetService = assetService;
        this.measureSpecService = measureSpecService;
        this.measurementService = measurementService;
    }

    /**
     * 显示状态监控测点列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String showMeasureList(Model model) {
        return "measure/listMeasure";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public DatatablesResponse<MeasurePoint> showMeasureListDataTables(
            HttpServletRequest request) {
        DatatablesCriterias criterias = DatatablesCriterias.getFromRequest(request);
        DataSet<MeasurePoint> measurePoints = measurePointService.findMeasurePointsWithDatatablesCriterias(criterias);
        return DatatablesResponse.build(measurePoints, criterias);

    }

    /**
     * 新增状态监控
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String initCreationMeasureForm(Model model) {
        MeasurePoint measurePoint = new MeasurePoint();
        String pointnum = measurePointService.generatePointNum();
        measurePoint.setPointnum(pointnum);
        model.addAttribute("measurePoint", measurePoint);
        model.addAttribute("measurement", new Measurement());
        return "measure/editMeasureForm";
    }

    /**
     * 保存新增状态监控
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String processCreationMeasureForm(@Valid @ModelAttribute("measurePoint") MeasurePoint measurePoint,
                                             BindingResult result, SessionStatus status,
                                             RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            return "measure/editMeasureForm";
        } else {
            if (measurePointService.existPointNum(measurePoint.getPointnum(),
                    measurePoint.getSiteid(), measurePoint.getOrgid())) {
                result.addError(new ObjectError("pointnum", "状态监测已经存在!"));
                return "measure/editMeasureForm";
            } else {
                try {
                    measurePoint = measurePointService
                            .saveMeasurePoint(measurePoint);
                    status.setComplete();
                    redirectAttrs.addFlashAttribute("message", "状态监测新增成功");
                    return "redirect:/measure/edit/"
                            + measurePoint.getMeasurepointid();
                } catch (Exception e) {
                    e.printStackTrace();
                    result.addError(new ObjectError("pointnum", e.toString()));
                    return "measure/editMeasureForm";
                }
            }
        }
    }

    /**
     * 编辑状态监控
     */
    @RequestMapping(value = "/edit/{measurepointid}", method = RequestMethod.GET)
    public String initEditMeasureForm(
            @PathVariable("measurepointid") long measurepointid, Model model) {
        MeasurePoint measurePoint = new MeasurePoint();

        if (measurepointid > 0) {
            measurePoint = measurePointService.findMeasurePointById(measurepointid);
        }
        model.addAttribute("measurePoint", measurePoint);
        model.addAttribute("measurement", new Measurement());
        return "measure/editMeasureForm";
    }

    /**
     * 保存编辑状态监控
     */
    @RequestMapping(value = "/edit/{measurepointid}", method = RequestMethod.PUT)
    public String processEditMeasureForm(@Valid MeasurePoint measurePoint,
                                         BindingResult result, SessionStatus status,
                                         RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            return "measure/editMeasureForm";
        } else {
            try {
                measurePoint = measurePointService.saveMeasurePoint(measurePoint);
                status.setComplete();
                redirectAttrs.addFlashAttribute("message", "状态监测更新成功");
                return "redirect:/measure/edit/" + measurePoint.getMeasurepointid();
            } catch (Exception e) {
                e.printStackTrace();
                result.addError(new ObjectError("pointnum", e.toString()));
                return "measure/editMeasureForm";
            }

        }
    }

    /**
     * 状态监控技术规范列表
     */
    @RequestMapping(value = "/{measurepointid}/specs", method = RequestMethod.GET)
    public String showMeasureSpecs(
            @PathVariable("measurepointid") long measurepointid, Model model) {
        MeasurePoint measurePoint = new MeasurePoint();
        MeasureSpec measureSpec = new MeasureSpec();
        if (measurepointid > 0) {
            measurePoint = measurePointService.findMeasurePointById(measurepointid);
            String assetnum = measurePoint.getAssetnum();
            measureSpec.setDisplaysequence(0);
            measureSpec.setAssetnum(assetnum);
            measureSpec.setOrgid(measurePoint.getOrgid());
            measureSpec.setSiteid(measurePoint.getSiteid());
            measureSpec.setMetername(measurePoint.getMetername());
            if (StringUtils.hasLength(assetnum)) {
                try {
                    Asset asset = assetService.findAssetByAssetnum(assetnum, measurePoint.getSiteid(), measurePoint.getOrgid());
                    if (asset != null) {
                        measureSpec.setAssetid(asset.getAssetid());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        model.addAttribute("measurePoint", measurePoint);
        model.addAttribute("measureSpec", measureSpec);
        return "measure/listMeasureSpec";
    }

    /**
     * 状态监控技术规范列表数据源
     *
     * @param
     */
    @RequestMapping(value = "/{measurepointid}/specs", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public DataTablesResponse<MeasureSpec> showMeasureSpecsToDataTables(@PathVariable("measurepointid") long measurepointid, DataTablesRequestParams params) {
        DataTablesResponse<MeasureSpec> result = new DataTablesResponse<MeasureSpec>();

        MeasurePoint measurePoint = new MeasurePoint();
        String metername = null, assetnum = null, siteid = null;
        if (measurepointid > 0) {
            measurePoint = measurePointService.findMeasurePointById(measurepointid);
            metername = measurePoint.getMetername();
            assetnum = measurePoint.getAssetnum();
            siteid = measurePoint.getSiteid();
        }
        if (StringUtils.hasLength(assetnum)) {
            result = measureSpecService.findAssetMeasureSpecsToDataTables(assetnum, metername, siteid, params);
        } else {
            result = measureSpecService.findMeasureSpecsToDataTables(metername, siteid, params);
        }
        return result;
    }


    /**
     * 获取状态测点读数
     *
     * @param id 读数ID
     */
    @RequestMapping(value = "/{measurepointid}/specs/{measurespecid}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public MeasureSpecSet initEditMeasureSpecForm(@PathVariable("measurespecid") long measurespecid) {
        MeasureSpecSet measureSpecSet = new MeasureSpecSet();
        if (measurespecid > 0) {
            measureSpecSet = measureSpecService.findMeasureSpecSetById(measurespecid);
        }
        return measureSpecSet;
    }


    /**
     * 保存状态监控技术规范
     */
    @RequestMapping(value = "/{measurepointid}/specs/save", method = RequestMethod.POST)
    @ResponseBody
    public Message processEditMeasureSpecForm(@PathVariable("measurepointid") long measurepointid,
                                              @Valid MeasureSpec measureSpec, BindingResult result, SessionStatus status) {
        Message message = new Message();
        message.setSuccess(false);
        if (result.hasErrors()) {
            message.setMsg(result.toString());
        } else {
            if (measureSpecService.existMeasureSpec(measureSpec.getAssetnum(),
                    measureSpec.getMetername(), measureSpec.getSiteid(),
                    measureSpec.getAssetattrid())) {
                message.setMsg("规范已经存在");
            } else {
                try {
                    measureSpec = measureSpecService.saveMeasureSpec(measureSpec, measurepointid);
                    status.setComplete();
                    message.setSuccess(true);
                } catch (Exception e) {
                    message.setMsg(e.toString());
                    e.printStackTrace();
                }
            }
        }
        return message;
    }

    @RequestMapping(value = "/{measurepointid}/specs/delete", method = RequestMethod.POST)
    @ResponseBody
    public Message processDeleteMeasureSpec(@RequestParam("id") Long id) {
        Message message = new Message();
        message.setSuccess(false);
        try {
            measureSpecService.deleteMeasureSpec(id);
            message.setSuccess(true);
        } catch (Exception e) {
            message.setMsg(e.toString());
            e.printStackTrace();
        }

        return message;
    }


    /**
     * 获取状态测点读数
     *
     * @param id 读数ID
     */
    @RequestMapping(value = "/{measurepointid}/measurements/{measurementid}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public MeasurementSet initEditMeasurementForm(@PathVariable("measurementid") long measurementid) {
        MeasurementSet measurementSet = new MeasurementSet();
        if (measurementid > 0) {
            measurementSet = measurementService.findByMeasurementid(measurementid);
        }
        return measurementSet;
    }


    /**
     * 状态测点读数
     *
     * @param
     */
    @RequestMapping(value = "/{measurepointid}/measurements", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public DataTablesResponse<MeasurementSet> showMeasurementsToDataTables(
            @PathVariable("measurepointid") long measurepointid, DataTablesRequestParams params) {
        DataTablesResponse<MeasurementSet> result = new DataTablesResponse<MeasurementSet>();
        MeasurePoint measurePoint = new MeasurePoint();
        String metername = null, pointnum = null, siteid = null, orgid = null;
        if (measurepointid > 0) {
            measurePoint = measurePointService.findMeasurePointById(measurepointid);
            metername = measurePoint.getMetername();
            pointnum = measurePoint.getPointnum();
            siteid = measurePoint.getSiteid();
            orgid = measurePoint.getOrgid();
        }
        result = measurementService.findMeasurementsToDataTables(pointnum, metername, siteid, orgid, params);
        return result;
    }

    /**
     * 保存测点读数信息
     */
    @RequestMapping(value = "/{measurepointid}/measurements/save", method = RequestMethod.POST)
    @ResponseBody
    public Message processEditMeasurementForm(@Valid Measurement measurement,
                                              BindingResult result, SessionStatus status) {
        Message message = new Message();
        message.setSuccess(false);
        logger.info("processEditMeasurementForm start");
        if (result.hasErrors()) {
            message.setMsg(result.toString());
            logger.info("processEditMeasurementForm Error:" + result.toString());
        } else {
            try {
                logger.info("processEditMeasurementForm Begin");
                measurement = measurementService.saveMeasurement(measurement);
                status.setComplete();
                message.setSuccess(true);
            } catch (Exception e) {
                message.setMsg(e.toString());

                logger.info("processEditMeasurementForm Error:" + e.toString());
            }
        }
        return message;
    }


}
