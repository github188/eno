package com.energicube.eno.monitor.web;

import com.energicube.eno.common.util.Native2AsciiUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.*;

/**
 * 首页参数配置Controller
 *
 * @author MoPeng
 */
@Controller
@RequestMapping("/okcsys/homepage")
public class HomePageConfigController {

    /**
     * 进入配置页面
     *
     * @return String (页面路径)
     */
    @RequestMapping(value = "/parameterconfig", method = RequestMethod.GET)
    public String showSysconfig(Model model, HttpServletRequest request) {
        // 读取配置文件
        String filePath = request.getRealPath("/WEB-INF/classes/properties/monitorsum.properties");
        StringBuffer propertiesFileStr = getFileString(filePath);
        model.addAttribute("propertiesFile", Native2AsciiUtils.ascii2Native(propertiesFileStr.toString()));
        return "homepage/parameterconfig";
    }

    /**
     * 保存配置信息
     *
     * @return String (页面路径)
     */
    @RequestMapping(value = "/savaparameterconfig", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String saveParameterConfig(
            @RequestParam(value = "propertiesFile") String propertiesFile, Model model, HttpServletRequest request) {
        try {
            //保存配置文件
            String filePath = request.getRealPath("/WEB-INF/classes/properties/monitorsum.properties");
            FileOutputStream fop = new FileOutputStream(filePath);
            OutputStreamWriter osw = new OutputStreamWriter(fop, "UTF-8");
            osw.write(propertiesFile);
            osw.flush();
            osw.close();
            fop.close();

            //读取最新内容
            model.addAttribute("propertiesFile", getFileString(filePath));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "homepage/parameterconfig";
    }

    /**
     * 读取文件内容
     *
     * @param filePath 文件路径
     * @return String
     */
    private StringBuffer getFileString(String filePath) {
        int len = 0;
        StringBuffer propertiesFileStr = new StringBuffer("");
        File file = new File(filePath);
        try {
            FileInputStream is = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader in = new BufferedReader(isr);
            String line = null;
            while ((line = in.readLine()) != null) {
                if (len != 0) {// 处理换行符的问题
                    propertiesFileStr.append("\r\n" + line);
                } else {
                    propertiesFileStr.append(line);
                }
                len++;
            }
            in.close();
            is.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return propertiesFileStr;
    }
}
