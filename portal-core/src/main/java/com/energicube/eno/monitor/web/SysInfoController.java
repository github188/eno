package com.energicube.eno.monitor.web;

import com.energicube.eno.common.Config;
import com.energicube.eno.common.PatternConst;
import com.energicube.eno.common.dto.SystemMessage;
import com.energicube.eno.monitor.model.SysInfo;
import com.energicube.eno.monitor.service.SyspropService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 系统属性页面操作控制类 LiHuiHui
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
@RequestMapping("/okcsys/sysinfo")
public class SysInfoController extends BaseController {

    @Autowired
    private SyspropService syspropService;
    private final Log logger = LogFactory.getLog(this.getClass());
    private Config config = new Config();

    /**
     * 显示系统属性 LiHuiHui 2014-10-24
     *
     * @param sysInfo系统对象
     * @return
     */
    @RequestMapping(value = "/editSysinfo", method = RequestMethod.GET)
    public String showSysInfo(Model model, HttpServletRequest request) {
        SysInfo sysInfo = syspropService.findSysInfo();
        if (sysInfo != null) {
            model.addAttribute(sysInfo);
        }
        return "sysinfo/editSysinfo";
    }

    /**
     * 修改系统属性 LiHuiHui 2014-10-24
     *
     * @param
     * @return
     */

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String updateSysInfo(HttpServletRequest request, HttpServletResponse response, HttpServletResponse httpServletResponse,
                                SysInfo sysInfo) throws Exception {
        SystemMessage message = new SystemMessage();
        try {
            //处理上传文件
            MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> fileMap = mRequest.getFileMap();

            String uploadDir = request.getSession().getServletContext().getRealPath("/") + "\\" + config.getProps().getProperty("index.background.path");
            logger.debug("----picUpload---uploadDir------" + uploadDir);
            File file = new File(uploadDir);
            if (!file.exists()) {
                file.mkdir();
            }

            String fileName = config.getProps().getProperty("index.background.name");
            int i = 0;
            for (Iterator<Map.Entry<String, MultipartFile>> it = fileMap.entrySet().iterator(); it.hasNext(); i++) {

                Map.Entry<String, MultipartFile> entry = it.next();
                MultipartFile mFile = entry.getValue();

                String tempFileName = mFile.getOriginalFilename();
//	                if(tempFileName.substring(tempFileName.indexOf("."),tempFileName.length()).equals("jpg")){
                logger.debug("-------fileName------" + fileName);
                OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(uploadDir + fileName));
                FileCopyUtils.copy(mFile.getInputStream(), outputStream);
                message.setSuccess(true);
                message.setMsg(PatternConst.JSON_SUCCESS);
            }

        } catch (Exception e) {
            logger.error("---picUpload upload--------", e);
            message.setSuccess(false);
            message.setMsg(PatternConst.JSON_FAILURE);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String valueAsString = objectMapper.writeValueAsString(message);
//        JSONObject jsonObject = JSONObject.fromObject(message);
        httpServletResponse.getOutputStream().print(valueAsString);
        syspropService.updateSysInfo(sysInfo);
        return "redirect:/okcsys/sysinfo/editSysinfo.html";
    }
}	
