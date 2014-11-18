package com.energicube.eno.asset.web;

import com.energicube.eno.asset.model.MeterGroup;
import com.energicube.eno.asset.model.MeterInGroup;
import com.energicube.eno.asset.service.MeterGroupService;
import com.energicube.eno.common.jsonquery.jpa.specifier.Order;
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
import java.util.ArrayList;
import java.util.List;

@Controller
public class MeterGroupController extends BaseController {

    @Autowired
    private MeterGroupService meterGroupService;

    private Order order = new Order(MeterGroup.class);

    /**
     * 显示计量器组列表
     */
    @RequestMapping(value = "/metergroup/list", method = RequestMethod.GET)
    public String showMetergroupList(Model model) {
        return "metergroup/metergroupList";
    }

    /**
     * 显示计量器组列表
     */
    @RequestMapping(value = "/metergroup/list/dialog", method = RequestMethod.GET)
    public String showMetergroupWindow(Model model) {
        return "dialog/dlgMetergroupList";
    }


    /**
     * 资台台账数据源
     */
    @RequestMapping(value = "/metergroup/list", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public DatatablesResponse<MeterGroup> findAllForDataTables(HttpServletRequest request) {
        DatatablesCriterias criterias = DatatablesCriterias.getFromRequest(request);

        DataSet<MeterGroup> assets = meterGroupService.findMeterGroupsWithDatatablesCriterias(criterias);
        return DatatablesResponse.build(assets, criterias);
    }


    /**
     * 新增计量器具组信息
     */
    @RequestMapping(value = "/metergroup/new", method = RequestMethod.GET)
    public String initCreationMeterGroupForm(Model model) {
        MeterGroup meterGroup = new MeterGroup();
        MeterInGroup meterInGroup = new MeterInGroup();
        model.addAttribute("meterGroup", meterGroup);
        model.addAttribute("meterInGroup", meterInGroup);
        return "metergroup/editMetergroup";
    }

    /**
     * 保存新增计量器具组信息
     */
    @RequestMapping(value = "/metergroup/new", method = RequestMethod.POST)
    public String processCreationMeterGroupForm(@Valid MeterGroup meterGroup,
                                                BindingResult result, SessionStatus status,
                                                RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            return "metergroup/editMetergroup";
        } else {
            if (meterGroupService.existMeterGroup(meterGroup.getGroupname())) {
                result.addError(new ObjectError("groupname", "组名称已经存在"));
                return "metergroup/editMetergroup";
            } else {
                meterGroup = meterGroupService.saveMeterGroup(meterGroup);
                status.setComplete();
                redirectAttrs.addFlashAttribute("message", "新增成功");
                return "redirect:/metergroup/edit/"
                        + meterGroup.getMetergroupid();
            }
        }
    }

    /**
     * 编辑计量器具组信息
     */
    @RequestMapping(value = "/metergroup/edit/{metergroupid}", method = RequestMethod.GET)
    public String initUpdateMeterGroupForm(
            @PathVariable("metergroupid") Long metergroupid, Model model) {
        MeterGroup meterGroup = null;
        MeterInGroup meterInGroup = new MeterInGroup();
        List<MeterInGroup> meterInGroups = null;
        if (metergroupid != null) {
            meterGroup = meterGroupService.findOne(metergroupid);
            if (StringUtils.hasLength(meterGroup.getGroupname())) {
                meterInGroups = meterGroupService.findMeterInGroup(meterGroup
                        .getGroupname());
            }
        } else {
            meterGroup = new MeterGroup();
            meterInGroups = new ArrayList<MeterInGroup>();
        }
        model.addAttribute("meterGroup", meterGroup);
        model.addAttribute("meterInGroup", meterInGroup);
        model.addAttribute("meterInGroups", meterInGroups);
        return "metergroup/editMetergroup";
    }

    /**
     * 保存编辑计量器具组信息
     */
    @RequestMapping(value = "/metergroup/edit/{metergroupid}", method = RequestMethod.PUT)
    public String processUpdateMeterGroupForm(@Valid MeterGroup meterGroup,
                                              BindingResult result, SessionStatus status,
                                              RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            return "metergroup/editMetergroup";
        } else {
            meterGroup = meterGroupService.saveMeterGroup(meterGroup);
            status.setComplete();
            redirectAttrs.addFlashAttribute("message", "更新成功");
            return "redirect:/metergroup/edit/" + meterGroup.getMetergroupid();
        }
    }

    /**
     * 删除指定的计量器具组信息
     *
     * @param metergroupid 用户组ID
     */
    @RequestMapping(value = "/metergroup/delete/{metergroupid}", method = RequestMethod.GET)
    public String processDeleteMeterGroup(
            @PathVariable("metergroupid") Long metergroupid, Model model) {
        if (metergroupid != null) {
            meterGroupService.deleteMeterGroup(metergroupid);
            return "redirect:/metergroup/list";
        } else {
            return "metergroup/metergroupList";
        }
    }

    @RequestMapping(value = "/meteringroup/save", method = RequestMethod.POST)
    @ResponseBody
    public Message processMeterInGroupForm(
            @Valid MeterInGroup meterInGroup,
            BindingResult result, SessionStatus status) {
        Message message = new Message();
        if (result.hasErrors()) {
            message.setSuccess(false);
            StringBuilder sb = new StringBuilder();
            for (ObjectError err : result.getAllErrors()) {
                if (sb.length() > 0)
                    sb.append(",");
                sb.append(err.getDefaultMessage());
            }
            message.setMsg(sb.toString());
        } else {
            meterInGroup = meterGroupService.saveMeterInGroup(meterInGroup);
            message.setSuccess(true);
            message.setMsg("保存成功");
        }
        return message;
    }

    @RequestMapping(value = "/meteringroup/validate", method = RequestMethod.POST)
    @ResponseBody
    public Message processMeternameValidtion(@RequestParam(value = "metername", required = false) String metername,
                                             @RequestParam(value = "groupname", required = false) String groupname) {
        Message message = new Message();
        boolean isExist = false;
        if (StringUtils.hasLength(metername)) {
            isExist = meterGroupService.existMeternameInGroup(groupname, metername);
        }
        message.setSuccess(isExist);
        return message;
    }

    @RequestMapping(value = "/meteringroup/delete/{meteringroupid}", method = RequestMethod.GET)
    @ResponseBody
    public Message processDeleteMeterInGroup(@PathVariable("meteringroupid") long meteringroupid) {
        Message message = new Message();
        try {
            meterGroupService.deleteMeterInGroup(meteringroupid);
            message.setSuccess(true);
        } catch (Exception e) {
            message.setSuccess(false);
            message.setMsg(e.getMessage());
        }
        return message;
    }

}
