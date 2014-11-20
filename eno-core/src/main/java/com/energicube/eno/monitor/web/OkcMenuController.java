package com.energicube.eno.monitor.web;

import com.energicube.eno.common.PatternConst;
import com.energicube.eno.common.model.Message;
import com.energicube.eno.monitor.model.OkcMenu;
import com.energicube.eno.monitor.service.MenuService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统菜单管理
 */
@Controller
@RequestMapping("/okcsys/okcmenu")
public class OkcMenuController {

    private static final Log logger = LogFactory.getLog(OkcMenuController.class);

    @Autowired
    private MenuService menuService;

    @ModelAttribute("menuTreeView")
    public String GenMenuTreeView(HttpServletRequest request) {
        return menuService.getMenuTreeString(request);
    }

    @RequestMapping(value = "/{ownerelement}")
    @ResponseBody
    public String showOkcMenuList(@PathVariable("ownerelement") String ownerelement) {
        String strMenu = menuService.getZTreeNodeCollection(ownerelement);
        //logger.info(strMenu);
        return strMenu;
    }

    /**
     * 初始化菜单数据
     */
    @RequestMapping(method = RequestMethod.GET)
    public String initCreationMenuForm(Model model) {
        OkcMenu okcMenu = new OkcMenu();
        model.addAttribute("okcMenu", okcMenu);

        return "okcmenu/okcmenu";
    }


    /**
     * 保存新增菜单数据
     *
     * @param okcMenu 菜单对象
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public String processCreationMenuForm(@Valid OkcMenu okcMenu,
                                          BindingResult result, SessionStatus status, RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            return "okcmenu/okcmenu";
        } else {
            menuService.saveOkcMenu(okcMenu);
            status.setComplete();
            redirectAttrs.addFlashAttribute("message", "菜单信息新增成功");
            return "redirect:/okcsys/okcmenu/edit?okcmenuid=" + okcMenu.getOkcmenuid();
        }
    }

    /**
     * 更新指定的菜单数据
     *
     * @param okcmenuid 菜单ID
     */
    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String initUpdateMenuFrom(@RequestParam("okcmenuid") Long okcmenuid, Model model, RedirectAttributes redirectAttrs) {
        OkcMenu okcMenu = new OkcMenu();
        if (okcmenuid == null || okcmenuid <= 0) {
            redirectAttrs.addFlashAttribute("message", "菜单ID异常");
            return "okcmenu/okcmenu";
        } else {
            okcMenu = menuService.findOne(okcmenuid);
            model.addAttribute("okcMenu", okcMenu);
            return "okcmenu/okcmenu";
        }
    }

    /**
     * 持久化更新指定的菜单数据
     *
     * @param okcmenuid 菜单ID
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String processUpdateMenuForm(@RequestParam("okcmenuid") Long okcmenuid, @Valid OkcMenu okcMenu,
                                        BindingResult result, SessionStatus status, RedirectAttributes redirectAttrs) {
        if (result.hasErrors()) {
            return "okcmenu/okcmenu";
        } else {
            menuService.saveOkcMenu(okcMenu);
            status.setComplete();
            redirectAttrs.addFlashAttribute("message", "菜单信息更新成功");
            return "redirect:/okcsys/okcmenu/edit?okcmenuid=" + okcMenu.getOkcmenuid();
        }
    }


    /**
     * 持久化更新指定的菜单数据
     *
     * @param okcMenu 菜单ID
     */
    @RequestMapping(value = "/copy", method = RequestMethod.POST)
    @ResponseBody
    public Message processCopyMenuForm(@Valid @ModelAttribute("okcMenu") OkcMenu okcMenu,
                                       BindingResult result, SessionStatus status, RedirectAttributes redirectAttrs) {
//		logger.info(okcMenu);
        Message message = new Message();
        message.setSuccess(false);
        if (result.hasErrors()) {
            message.setMsg(result.toString());
        } else {
            try {
                OkcMenu copyOkcMenu = new OkcMenu();
                copyOkcMenu = menuService.saveCopyOkcMenu(okcMenu);
                status.setComplete();
                message.setMsg(copyOkcMenu.getOkcmenuid().toString());
                message.setSuccess(true);
            } catch (Exception e) {
                message.setMsg(e.toString());
            }
        }
        return message;
    }


    /**
     * 删除指定ID的菜单数据
     *
     * @param okcmenuid 菜单ID
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Message processDelete(@RequestParam("okcmenuid") Long okcmenuid, RedirectAttributes redirectAttrs) {
        Message message = new Message();
        message.setSuccess(false);
        if (okcmenuid == null || okcmenuid <= 0) {
            message.setMsg("菜单ID不存在");
        } else {
            try {
                menuService.deleteMenu(okcmenuid);
                message.setSuccess(true);
            } catch (Exception e) {
                message.setMsg(e.toString());
                logger.error("processDelete",e);
            }
        }
        return message;
    }

    // 返回指定菜单编码的子菜单的Json数组 [ ChengKang 2014-09-03 ]
    @RequestMapping(value = "/children", method = RequestMethod.POST)
    @ResponseBody
    public Object processSelectMenuChldren(@RequestParam("menuCode") String menuCode, RedirectAttributes redirectAttrs, HttpServletResponse response) throws IOException {
        List menuJson = new ArrayList();
        List<OkcMenu> MenuObj = menuService.findByOwnerelement(menuCode);
        for (OkcMenu okcmenu : MenuObj) {
            if (!okcmenu.getMenutype().equals(PatternConst.MENU_TYPE_PATTERN)) {//模式的菜单独立处理
                menuJson.add(menuService.getMenuJsonObj(okcmenu));
            }
        }
        return menuJson;
    }

    // 返回指定菜单编码的菜单名 [ ChengKang 2014-09-03 ]
    @RequestMapping(value = "/menuname", method = RequestMethod.POST)
    @ResponseBody
    public String getMenuName(@RequestParam("menuCode") String menuCode, RedirectAttributes redirectAttrs, HttpServletResponse response) throws IOException {

        String MenuName = "";
        List<OkcMenu> MenuObj = menuService.findByElementvalue(menuCode);
        if (MenuObj.size() > 0) {
            MenuName = MenuObj.get(0).getHeaderdescription();
        }
        return MenuName;
    }

    // 响应前台获取菜单数据，函数以JSONArray格式返回整个菜单的结构 [ ChengKang 2014-08-28 ]
    @RequestMapping(value = "/init", method = RequestMethod.POST)
    @ResponseBody
    public String getMainMenu(RedirectAttributes redirectAttrs, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        long startTime = System.currentTimeMillis();
        String menuJsonStr = menuService.findMainMenuNoPatter();
        long endTime = System.currentTimeMillis();
        logger.info("getMainMenu运行时间:" + (endTime - startTime));
        out.print(menuJsonStr);
        return null;
        /*
		byte [] fullByte = MenuJson.toString().trim().getBytes("GBK");
		String MenuJsonStr = new String(fullByte, "UTF-8");
		
		//String MenuJsonStr = new String(MenuJson.toString().getBytes("ISO-8859-1"), "UTF-8");
		//byte[] mun = MenuJsonStr.getBytes();
		return MenuJsonStr;
		*/
    }

}
