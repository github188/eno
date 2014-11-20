package com.energicube.eno.monitor.web;

import com.energicube.eno.common.model.Message;
import com.energicube.eno.common.model.MessageResult;
import com.energicube.eno.monitor.model.Syscontrol;
import com.energicube.eno.monitor.service.SyscontrolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 * 系统控件操作控制器
 *
 * @author CHENPING
 */
@Controller
@RequestMapping("/okcsys/controls")
public class SyscontrolController extends BaseController {

    @Autowired
    private SyscontrolService syscontrolService;

    /**
     * 获取系统组件数据列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public List<Syscontrol> getSyscontrolList() {
        return syscontrolService.findAll();
    }

    /**
     * 显示组件新增表单
     */
    @RequestMapping(method = RequestMethod.GET)
    public String showControlCreationForm(Model model) {
        model.addAttribute("syscontrol", new Syscontrol());
        model.addAttribute("isnew", true);
        return "controls/editSyscontrolForm";
    }

    /**
     * 保存组件信息
     */
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public MessageResult<Syscontrol> processControlCreationForm(@Valid Syscontrol syscontrol,
                                                                BindingResult result, SessionStatus status,
                                                                RedirectAttributes redirectAttrs, Model model) {
        if (result.hasErrors()) {
            return new MessageResult<Syscontrol>(new Message(false, result), syscontrol);
        } else {
            syscontrol = syscontrolService.saveSyscontrol(syscontrol);
            status.setComplete();
            return new MessageResult<Syscontrol>(new Message(true, "组件更新成功"), syscontrol);
        }
    }

    /**
     * 显示组件新增表单
     */
    @RequestMapping("/{controluid}")
    public String showControlEditForm(@PathVariable Integer controluid,
                                      Model model) {
        Syscontrol syscontrol = syscontrolService.findOne(controluid);
        model.addAttribute("syscontrol", syscontrol);
        model.addAttribute("isnew", false);
        return "controls/editSyscontrolForm";
    }

    /**
     * 更新组件信息
     */
    @RequestMapping(value = "/{controluid}", method = RequestMethod.PUT)
    public String processControlEditForm(@Valid Syscontrol syscontrol,
                                         BindingResult result, SessionStatus status,
                                         RedirectAttributes redirectAttrs, Model model) {
        if (result.hasErrors()) {
            return "controls/editSyscontrolForm";
        } else {
            syscontrol = syscontrolService.saveSyscontrol(syscontrol);
            status.setComplete();
            redirectAttrs.addFlashAttribute("message", "组件更新成功");
            return "redirect:/okcsys/controls/" + syscontrol.getControluid();
        }
    }

    /**
     * 删除组件信息
     */
    @RequestMapping(value = "/{controluid}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public Message deleteSysControl(@PathVariable("controluid") Integer controluid) {
        Message message = new Message();
        if (controluid > 0) {
            boolean ret = syscontrolService.deleteSyscontrol(controluid);
            if (ret) {
                message.setSuccess(true);
            } else {
                message.setSuccess(false);
                message.setMsg("删除失败，控件不存在或者控件正在使用");
            }
        }
        return message;
    }

    /**
     * 显示组件新增表单
     */
    @RequestMapping("/one/{controluid}")
    @ResponseBody
    public Syscontrol findControl(@PathVariable Integer controluid, Model model) {
        Syscontrol syscontrol = syscontrolService.findOne(controluid);
        return syscontrol;
    }

}
