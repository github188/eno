package com.energicube.eno.monitor.web;

import com.energicube.eno.asset.model.ClassSpec;
import com.energicube.eno.asset.model.ClassStructure;
import com.energicube.eno.asset.repository.ClassSpecRepository;
import com.energicube.eno.asset.repository.ClassStructureRepository;
import com.energicube.eno.common.dto.DeviceConfigDTO;
import com.energicube.eno.common.model.Message;
import com.energicube.eno.common.model.MessageResult;
import com.energicube.eno.monitor.model.DeviceConfig;
import com.energicube.eno.monitor.model.Pagelayout;
import com.energicube.eno.monitor.repository.DeviceConfigRepository;
import com.energicube.eno.monitor.repository.jpa.AssetMeasurementRepository;
import com.energicube.eno.monitor.service.PagelayoutService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 设备列表配置控制页面
 *
 * @author zouzhixiang
 * @date 2014-08-24
 */
@Controller
@RequestMapping("/okcsys/devicelist")
public class DeviceConfigController {
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
    private AssetMeasurementRepository assetMeasurementRepository;

    /**
     * 进入导入配置页面
     *
     * @return String (页面路径)
     */
    @RequestMapping(value = "/config", method = RequestMethod.GET)
    public String showSysconfig(Model model, HttpServletRequest request) {
        return "devicelist/config";
    }

    /**
     * 保存配置信息
     *
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonGenerationException
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public MessageResult<DeviceConfig> processDeviceConfigFormPost(
            @Valid DeviceConfig deviceconfig, BindingResult result,
            SessionStatus status, RedirectAttributes redirectAttrs,
            Model model, HttpServletRequest request)
            throws JsonGenerationException, JsonMappingException, IOException {

        if (result.hasErrors()) {
            return new MessageResult<DeviceConfig>(new Message(false, result), deviceconfig);
        } else {
            String mo = request.getParameter("modul");
            if (mo.equals("")) {
                return new MessageResult<DeviceConfig>(new Message(true, "描述不能为空"), deviceconfig);
            }
            String id = request.getParameter("id");
            String system = request.getParameter("system");
            String classid = request.getParameter("classid");
            if (id.equals("-1")) {
                DeviceConfig dc = new DeviceConfig();
                dc.setSystem(system);
                dc.setClassid(classid);
                dc.setAttribute(request.getParameter("attr"));
                dc.setDescription(new String(request.getParameter("description").getBytes("ISO-8859-1"), "utf-8"));
                dc.setModul(new String(request.getParameter("modul").getBytes("ISO-8859-1"), "utf-8"));
                deviceconfig = deviceConfigRepository.save(dc);
                return new MessageResult<DeviceConfig>(new Message(true, "新增成功"), deviceconfig);
            } else {
                DeviceConfig deviceConfig = deviceConfigRepository.findOne(Integer.parseInt(id));
                if (deviceConfig == null) {
                    DeviceConfig dc = new DeviceConfig();
                    dc.setSystem(system);
                    dc.setClassid(classid);
                    dc.setAttribute(request.getParameter("attr"));
                    dc.setDescription(new String(request.getParameter("description").getBytes("ISO-8859-1"), "utf-8"));
                    dc.setModul(new String(request.getParameter("modul").getBytes("ISO-8859-1"), "utf-8"));
                    deviceconfig = deviceConfigRepository.save(dc);
                    return new MessageResult<DeviceConfig>(new Message(true, "新增成功"), deviceconfig);

                } else {

                    deviceConfig.setSystem(system);
                    deviceConfig.setClassid(classid);
                    deviceConfig.setAttribute(request.getParameter("attr"));
                    deviceConfig.setDescription(new String(request.getParameter("description").getBytes("ISO-8859-1"), "utf-8"));
                    deviceConfig.setModul(new String(request.getParameter("modul").getBytes("ISO-8859-1"), "utf-8"));
                    deviceconfig = deviceConfigRepository.save(deviceConfig);
                    return new MessageResult<DeviceConfig>(new Message(true, "修改成功"), deviceconfig);
                }
            }
        }
    }

    /**
     * 设备配置信息列表
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public List<DeviceConfig> findAllDeviceConfig() {
        return deviceConfigRepository.findAll();
    }

    /**
     * 设备配置信息列表
     */
    @RequestMapping(value = "/listAll", method = RequestMethod.POST)
    @ResponseBody
    public List<DeviceConfigDTO> findAllDeviceConfigs() {
        List<DeviceConfig> dcList = deviceConfigRepository.findAll();
        List<DeviceConfigDTO> dcDTOList = new ArrayList<DeviceConfigDTO>();
        if (dcList != null && dcList.size() > 0) {
            for (DeviceConfig deviceConfig : dcList) {
                DeviceConfigDTO deviceConfigDTO = new DeviceConfigDTO();
                deviceConfigDTO.setId(deviceConfig.getId());
//			 ClassStructure classStructure1=classStructureRepository.findByClassstructureid(deviceConfig.getSystem()).get(0);
//			 deviceConfigDTO.setSystem(deviceConfig.getSystem());
//			 deviceConfigDTO.setSystemdesc(classStructure1.getDescription());
                ClassStructure classStructure2 = classStructureRepository.findByClassstructureid(deviceConfig.getClassid()).get(0);
                deviceConfigDTO.setClassid(deviceConfig.getClassid());
                deviceConfigDTO.setClassiddesc(classStructure2.getDescription());
                deviceConfigDTO.setDescription(deviceConfig.getDescription());
                deviceConfigDTO.setModul(deviceConfig.getModul());
                deviceConfigDTO.setAttribute(deviceConfig.getAttribute());
                dcDTOList.add(deviceConfigDTO);
            }

            return dcDTOList;
        }
        return dcDTOList;
    }

    /**
     * 删除配置信息
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    @ResponseBody
    public void deleteDeviceConfigById(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        List<Pagelayout> pagelayout = new ArrayList<Pagelayout>();
        pagelayout = pagelayoutService.findByDeviceconfigid(Integer.parseInt(id));
        if (!pagelayout.isEmpty()) {
            response.setContentType("text/html;charset=utf-8");
            try {
                response.getWriter().write("页面【" + pagelayout.get(0).getLayoutname() + "】绑定了该配置项，不允许删除绑定的项！");
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            deviceConfigRepository.delete(Integer.parseInt(id));
            response.setContentType("text/html;charset=utf-8");
            try {
                response.getWriter().write("删除成功");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequestMapping(value = "/getModulList", method = RequestMethod.GET)
    @ResponseBody
    public List<Object> getModulList() {
        return assetMeasurementRepository.findAttributes();
    }

    @RequestMapping(value = "/getAttributeByClassstructureid", method = RequestMethod.GET)
    @ResponseBody
    public List<ClassSpec> getAttributeByClassstructureid(HttpServletRequest request, HttpServletResponse response) {
        return classSpecRepository.findByClassstructureid(request.getParameter("classstructureid"));
    }
}
