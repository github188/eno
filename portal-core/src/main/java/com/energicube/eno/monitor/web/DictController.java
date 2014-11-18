package com.energicube.eno.monitor.web;

import com.energicube.eno.asset.model.ClassSpec;
import com.energicube.eno.asset.repository.ClassSpecRepository;
import com.energicube.eno.asset.repository.ClassStructureRepository;
import com.energicube.eno.monitor.model.Dict;
import com.energicube.eno.monitor.repository.DeviceConfigRepository;
import com.energicube.eno.monitor.repository.DictRepository;
import com.energicube.eno.monitor.service.PagelayoutService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 设备列表配置控制页面
 *
 * @author zouzhixiang
 * @date 2014-10-16
 */
@Controller
@RequestMapping("/dict")
public class DictController {
    private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private DeviceConfigRepository deviceConfigRepository;
    @Autowired
    private ClassStructureRepository classStructureRepository;
    @Autowired
    private ClassSpecRepository classSpecRepository;
    @Autowired
    private PagelayoutService pagelayoutService;
    @Autowired
    private DictRepository dictRepository;

    /**
     * 进入导入配置页面
     *
     * @return String (页面路径)
     */
    @RequestMapping(value = "/config", method = RequestMethod.GET)
    public String showSysconfig(Model model, HttpServletRequest request) {
        return "devicelist/config";
    }

    @RequestMapping(value = "/findDistinctValue", method = RequestMethod.GET)
    @ResponseBody
    public List<Dict> findDistinctValue() {
        return dictRepository.findDistinctValue();
    }

    @RequestMapping(value = "/getAttributeByClassstructureid", method = RequestMethod.GET)
    @ResponseBody
    public List<ClassSpec> getAttributeByClassstructureid(HttpServletRequest request, HttpServletResponse response) {
        return classSpecRepository.findByClassstructureid(request.getParameter("classstructureid"));
    }
}
