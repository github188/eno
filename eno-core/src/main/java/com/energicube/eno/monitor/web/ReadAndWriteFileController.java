package com.energicube.eno.monitor.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 自定义文件的读写
 *
 * @author LiHuiHui
 */
@Controller
@RequestMapping("/self/style")
public class ReadAndWriteFileController {

    private static final Log logger = LogFactory.getLog(ReadAndWriteFileController.class);
    @Autowired
    private SyscontrolController SyscontrolController;

    @RequestMapping(method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String fileOperate(HttpServletRequest request, String definedStyle, HttpServletResponse response) {

        try {
            ReadAndWriteFileController st = new ReadAndWriteFileController();
            String fileName = "meter_icons.css";
            String ret = null;
            ret = request.getSession().getServletContext().getRealPath("/")
                    + "/" + "resources" + "/" + "icons" + "/" + fileName;
            logger.info(ret);
            st.OutputTest(ret, definedStyle);
            st.InputTest(ret, request, response);
        } catch (Exception e) {
            logger.error(e);
        }
        return "redirect:/okcsys/controls";

    }

    //字节-读
    @RequestMapping(value = "/read", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
    @ResponseBody
    public String InputTest(String fileName, HttpServletRequest request, HttpServletResponse response) {
        String fileName1 = "meter_icons.css";
        String ret = null;
        ret = request.getSession().getServletContext().getRealPath("/")
                + "/" + "resources" + "/" + "icons" + "/" + fileName1;
        int len = 0;
        StringBuffer str = new StringBuffer("");
        File file = new File(ret);
        try {
            FileInputStream is = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(is, "utf-8");
            BufferedReader in = new BufferedReader(isr);
            String line = null;
            while ((line = in.readLine()) != null) {
                if (len != 0)  // 处理换行符的问题
                {
                    str.append("\r\n" + line);
                } else {
                    str.append(line);
                }
                len++;
            }
            in.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return str.toString();
    }

    //字节-写
    private void OutputTest(String fileName, String text) {
        try (
                FileOutputStream fop = new FileOutputStream(fileName)) {

            byte[] contentInBytes = text.getBytes();

            fop.write(contentInBytes);
            fop.flush();
            fop.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

