package com.energicube.eno.asset.web;

import com.energicube.eno.asset.model.Meter;
import com.energicube.eno.asset.model.Metertype;
import com.energicube.eno.asset.model.Readingtype;
import com.energicube.eno.asset.service.MeterService;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * 计量器具
 */
@Controller
@RequestMapping("/meter")
public class MeterController extends BaseController {

    @Autowired
    private MeterService meterService;


    /**
     * 显示计量器具列表页
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String showMeterList(Model model) {
        return "meter/meterList";
    }

    /**
     * 选择计量器对话框
     */
    @RequestMapping(value = "/list/dialog", method = RequestMethod.GET)
    public String showMeasureUnitSelectList(Model model) {
        return "dialog/dlgMeterList";
    }


    /**
     * 选择对话框数据源
     *
     * @param params 查询参数
     * @return DataTables JSON数据对象
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public DatatablesResponse<Meter> findMetersWithDatatablesCriterias(HttpServletRequest request) {

        DatatablesCriterias criterias = DatatablesCriterias.getFromRequest(request);

        DataSet<Meter> meters = meterService.findMetersWithDatatablesCriterias(criterias);
        return DatatablesResponse.build(meters, criterias);
    }


    /**
     * 新增计量器具信息
     */
    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String initCreationMeterForm(Model model) {
        Meter meter = new Meter();
        Metertype metertype = new Metertype();
        Readingtype readingtype = new Readingtype();
        model.addAttribute("meter", meter);
        model.addAttribute("metertype", metertype);
        model.addAttribute("readingtype", readingtype);
        return "meter/editMeter";
    }

    /**
     * 保存新增计量器具信息
     */
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String processCreationMeterForm(@Valid Meter meter,
                                           BindingResult result, SessionStatus status,
                                           RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            return "meter/editMeter";
        } else {
            if (meterService.existMetername(meter.getMetername())) {
                result.addError(new ObjectError("metername", "对象名称已经存在"));
                return "meter/editMeter";
            } else {

                meter = meterService.saveMeter(meter);
                status.setComplete();
                redirectAttrs.addFlashAttribute("message", "新增成功");
                return "redirect:/meter/edit/" + meter.getMeterid();
            }
        }
    }

    /**
     * 编辑计量器具信息
     */
    @RequestMapping(value = "/edit/{meterid}", method = RequestMethod.GET)
    public String initUpdateMeterForm(@PathVariable("meterid") Long meterid,
                                      Model model) {
        Meter meter = new Meter();
        Metertype metertype = new Metertype();
        Readingtype readingtype = new Readingtype();
        if (meterid != null) {
            meter = meterService.findOne(meterid);
            metertype = meterService.findMetertypeById(meter.getMetertype());
            if (StringUtils.hasLength(meter.getReadingtype())) {
                readingtype = meterService.findReadingtypeById(meter.getReadingtype());
            }
        }
        model.addAttribute("meter", meter);
        model.addAttribute("metertype", metertype);
        model.addAttribute("readingtype", readingtype);
        return "meter/editMeter";
    }

    /**
     * 保存计量器具编辑信息
     *
     * @param meter         计量仪信息
     * @param result        校验结果
     * @param status        会话状态
     * @param redirectAttrs 跳转属性
     * @return 转向页
     */
    @RequestMapping(value = "/edit/{meterid}", method = RequestMethod.PUT)
    public String processUpdateMeterForm(@Valid Meter meter,
                                         BindingResult result, SessionStatus status,
                                         RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            return "meter/editMeter";
        } else {
            meter = meterService.saveMeter(meter);
            status.setComplete();
            redirectAttrs.addFlashAttribute("message", "编辑成功");
            return "redirect:/meter/edit/" + meter.getMeterid();
        }
    }

    @RequestMapping(value = "/metertypes", method = RequestMethod.POST)
    @ResponseBody
    public List<Metertype> showMeterTypes() {
        return meterService.findMetertypes();
    }

    @RequestMapping(value = "/readingtypes", method = RequestMethod.POST)
    @ResponseBody
    public List<Readingtype> showReadingTypes() {
        return meterService.findReadingtypes();
    }


}
