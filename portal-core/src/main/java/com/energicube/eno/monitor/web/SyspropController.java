package com.energicube.eno.monitor.web;

import com.energicube.eno.common.Config;
import com.energicube.eno.monitor.model.Sysprop;
import com.energicube.eno.monitor.service.SyspropService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;


/**
 * 系统属性页面操作控制类
 * <p/>
 * <p>
 * <p/>
 * <code>/okcsys/sysprops </code>  系统属性列表<br />
 * <code>/okcsys/sysprops/{propid} </code>  系统属性列表    <br />
 * </p>
 *
 * @version 1.0
 */
@Controller
@RequestMapping("/okcsys/sysprops")
public class SyspropController extends BaseController {
    private final Log logger = LogFactory.getLog(this.getClass());
    private Config config = new Config();
    @Autowired
    private SyspropService syspropService;

    @ModelAttribute("sysprop")
    public Sysprop getSyspropObject() {
        return new Sysprop();
    }

    /**
     * 查询所有的系统属性
     *
     * @return 返回所有的系统属性
     */
    /*@ModelAttribute("sysprops")
	public List<Sysprop> findAllProps(){
		
		return syspropService.findAllSysprops();
	}*/
    @RequestMapping(value = "/getAllSysprop", method = RequestMethod.POST)
    @ResponseBody
    public List<Sysprop> getAllSysprop(Model model) {

        return syspropService.findAllSysprops();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showSysprops(Model model) {
        //model.addAttribute("sysprop", new Sysprop());
        model.addAttribute("sysprops", syspropService.findAllSysprops());
        return "sysprop/sysprops";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String showSyspropsPost(Model model) {
        //model.addAttribute("sysprop", new Sysprop());
        model.addAttribute("sysprops", syspropService.findAllSysprops());
        return "sysprop/sysprops";
    }

    @RequestMapping(value = "/newSysprop", method = RequestMethod.GET)
    public String toSyspropForm(Model model) {
        //model.addAttribute("sysprop", new Sysprop());

        return "sysprop/editSysprops";

    }

    /**
     * @param propname 属性名
     * @param sysprop  系统属性
     * @return 新增后跳转的页面
     */
    @RequestMapping(value = "/newSysprop", method = RequestMethod.POST)
    public String processSyspropForm(@RequestParam("propname") String propname, @Valid Sysprop sysprop) {
        //新增之前先判断该属性名是否已经存在，如果已经存在则不插入，如果不存在则新增

        boolean existSysprop = syspropService.existSysprop(propname);
        if (!existSysprop) {
            syspropService.saveSysprop(sysprop);
        }
        return "redirect:/okcsys/sysprops";

    }

    /**
     * @param propid 系统属性主键
     * @return 返回根据主键查询到的对象
     */
    @RequestMapping(value = "/updateSysprop", method = RequestMethod.GET)
    public String findSyspropByPropid(@RequestParam("propid") int propid, Model model) {
        Sysprop sysprop = new Sysprop();
        sysprop = syspropService.findSyspropById(propid);
        model.addAttribute("sysprop", sysprop);
        return "sysprop/editSysprops";

    }

    /**
     * 更新系统属性
     *
     * @param propid
     * @param sysprop
     * @param result
     * @param status
     * @param redirectAttrs
     * @return
     */
    @RequestMapping(value = "/updateSysprop", method = RequestMethod.POST)

    public String processSyspropForm(
            @RequestParam("propid") int propid, @Valid Sysprop sysprop, BindingResult result, SessionStatus status,
            RedirectAttributes redirectAttrs) {

        if (result.hasErrors()) {
            return "sysporp/editSysprops";
        } else {
            Date date = new Date();
            sysprop.setChangedate(date);
            sysprop = syspropService.saveSysprop(sysprop);
            status.setComplete();
            return "redirect:/okcsys/sysprops";

        }

    }

    /**
     * 删除指定的系统属性
     *
     * @param propid
     * @return
     */
    @RequestMapping(value = "/removeSysprop", method = RequestMethod.GET)
    public String processRemovePagelayout(@RequestParam("propid") int propid) {
        syspropService.deleteSysprop(propid);
        return "redirect:/okcsys/sysprops";
    }
}

