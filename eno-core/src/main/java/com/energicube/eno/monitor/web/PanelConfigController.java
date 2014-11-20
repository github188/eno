package com.energicube.eno.monitor.web;

import com.energicube.eno.asset.model.ClassSpec;
import com.energicube.eno.monitor.service.PanelConfigService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 平面图下方 面板配置
 *
 * @author shangpeibao
 */
@Controller
@RequestMapping("/okcsys/panel")
public class PanelConfigController extends BaseController {
    private static final Log logger = LogFactory
            .getLog(PageManageController.class);

    @Autowired
    private PanelConfigService panelConfigService;

    /**
     * 进入面板配置页面
     *
     * @return String (页面路径)
     */
    @RequestMapping(value = "/panelConfig", method = RequestMethod.GET)
    public String showSysconfig(Model model, HttpServletRequest request) {
        return "panel/panelConfig";
    }

    /**
     * 获取所有属性
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getClassSpec", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<ClassSpec> getConfig(HttpServletRequest request) {
        String type = request.getParameter("type");
        String classstructureid = request.getParameter("classid");
        List<ClassSpec> list = panelConfigService.getSetAttribute(classstructureid, type);
        return list;
    }

    /**
     * 保存面板设置
     *
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value = "/saveConfig", method = RequestMethod.POST)
    public
    @ResponseBody
    String saveConfig(Model model, HttpServletRequest request) {
        String classstructureid = request.getParameter("classstructureid");
        String description = request.getParameter("description");
        String classStr_not = request.getParameter("panelConfig_not");
        String classStr_yes = request.getParameter("panelConfig_yes");
        try {
            panelConfigService.savePanelConfig(classstructureid, description, classStr_not, classStr_yes);
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
        }
        return "false";
    }

}
