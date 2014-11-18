package com.energicube.eno.asset.web;

import com.energicube.eno.asset.model.MeasureUnit;
import com.energicube.eno.asset.service.MeasureUnitService;
import com.energicube.eno.common.jsonquery.jpa.request.DataTablesRequestParams;
import com.energicube.eno.common.jsonquery.jpa.response.DataTablesResponse;
import com.energicube.eno.common.model.Message;
import com.energicube.eno.monitor.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import javax.validation.Valid;
import java.util.List;

/**
 * 计量单位
 */
@Controller
@RequestMapping("/measureunit")
public class MeasureUnitController extends BaseController {

    private MeasureUnitService measureUnitService;

    @Autowired
    public MeasureUnitController(MeasureUnitService measureUnitService) {
        this.measureUnitService = measureUnitService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<MeasureUnit> getMeasureUnits() {
        return measureUnitService.getMeasureUnits();
    }

    /**
     * 选择计量单位
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String showMeasureUnitSelectWindow(Model model) {
        return "dialog/dlgMeasureUnit";
    }

    /**
     * 计量单位列表
     *
     * @param params 请求参数
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public DataTablesResponse<MeasureUnit> list(DataTablesRequestParams params) {
        return measureUnitService.findMeasureUnitToDataTables(params);
    }


    /**
     * 选择计量单位
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String showMeasureUnitEditWindow(Model model) {
        MeasureUnit measureUnit = new MeasureUnit();
        model.addAttribute("measureUnit", measureUnit);
        return "dialog/editMeasureUnit";
    }

    /**
     * 验证资产类别ID否存在
     *
     * @param classificationid 类别ID
     */
    @RequestMapping(value = "/check", method = RequestMethod.GET)
    @ResponseBody
    public String checkMeasureunitid(@RequestParam String measureunitid) {
        //如果已经存在，则未能通过验证，返回 false,否则为true
        return measureUnitService.existMeasureUnit(measureunitid) ? "false" : "true";
    }

    /**
     * 保存计量单位
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Message processMeasureUnitEditForm(
            @Valid MeasureUnit measureUnit, BindingResult result,
            SessionStatus status, Model model) {
        Message message = new Message();
        message.setSuccess(false);
        if (result.hasErrors()) {
            String msg = result.getFieldError().getField() + "" + result.getFieldError().getDefaultMessage();
            message.setMsg(msg);
        } else {
            boolean isContinue = true;
            if (measureUnit.getMeasureunituid() == 0) {
                boolean ischeck = measureUnitService.existMeasureUnit(measureUnit.getMeasureunitid());
                if (ischeck) {
                    message.setMsg("名称为" + measureUnit.getMeasureunitid() + "的计量单位已经存在");
                    isContinue = false;
                }
            }
            if (isContinue) {
                measureUnit = measureUnitService.saveMeasureUnit(measureUnit);
                message.setSuccess(true);
                message.setMsg(Long.toString(measureUnit.getMeasureunituid()));
                status.setComplete();
            }
        }

        return message;

    }


}
