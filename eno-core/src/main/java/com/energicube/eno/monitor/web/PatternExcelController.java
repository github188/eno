package com.energicube.eno.monitor.web;

import com.energicube.eno.common.Config;
import com.energicube.eno.common.PatternConst;
import com.energicube.eno.common.PatternExcel;
import com.energicube.eno.common.dto.SystemMessage;
import com.energicube.eno.pattern.model.UcPattern;
import com.energicube.eno.pattern.model.UcPatternAction;
import com.energicube.eno.pattern.service.PatternService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-10-18
 * Time: 下午3:17
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class PatternExcelController {

    private static Log logger = LogFactory.getLog(PatternExcelController.class);

    private Config config = new Config();

    @Autowired
    PatternService patternService;

    /**
     * 上传文件
     *
     * @param request
     * @throws Exception
     */
    @RequestMapping(value = "/pattern/upload")
//    @ResponseBody
    public Object upload(HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception {
        SystemMessage message = new SystemMessage();
        try {
            PatternExcel.setPatternService(patternService);
            String patternExcelPath = config.getProps().getProperty("patternExcelPath");
            Collection<UcPatternAction> result = new ArrayList<UcPatternAction>();

            MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
            String patternId = mRequest.getParameter("patternId");
            logger.debug("-------patternId------" + patternId);
            Map<String, MultipartFile> fileMap = mRequest.getFileMap();

            String uploadDir = request.getSession().getServletContext().getRealPath("/") + patternExcelPath;
            logger.debug("-------uploadDir------" + uploadDir);
            File file = new File(uploadDir);

            if (!file.exists()) {
                file.mkdir();
            }

            String fileName = null;
            int i = 0;
            for (Iterator<Map.Entry<String, MultipartFile>> it = fileMap.entrySet().iterator(); it.hasNext(); i++) {

                Map.Entry<String, MultipartFile> entry = it.next();
                MultipartFile mFile = entry.getValue();

                fileName = mFile.getOriginalFilename();
                logger.debug("-------fileName------" + fileName);
                OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(uploadDir + fileName));

                FileCopyUtils.copy(mFile.getInputStream(), outputStream);
                Collection<UcPatternAction> objects = PatternExcel.getUcPatternAction(uploadDir + fileName);
                result.addAll(objects);
            }
            patternService.saveExcelOfPattern(patternId, result);

            message.setSuccess(true);
            message.setMsg(PatternConst.JSON_SUCCESS);

        } catch (Exception e) {
            logger.error("-----------", e);
            message.setSuccess(false);
            message.setMsg(PatternConst.JSON_FAILURE);
        }

        return message;
    }

    /**
     * 下载
     */
    @RequestMapping(value = "/pattern/download")
    public ModelAndView download(HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        PatternExcel.setPatternService(patternService);
        String storeName = System.currentTimeMillis() + ".xls";

        String contentType = "application/octet-stream";
        String patternExcelPath = config.getProps().getProperty("patternExcelPath");
        String patternId = request.getParameter("patternId");
        String ctxPath = request.getSession().getServletContext().getRealPath("/") + patternExcelPath;

        File file = new File(ctxPath);
        if (!file.exists()) {
            file.mkdir();
        }
        String downLoadPath = ctxPath + storeName;
        UcPattern ucPattern = patternService.findUcPatternById(patternId);

        PatternExcel.getUcPatternToFile(ucPattern, downLoadPath);

        request.setCharacterEncoding("UTF-8");
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        long fileLength = new File(downLoadPath).length();

        response.setContentType(contentType);
        response.setHeader("Content-disposition", "attachment; filename=" + new String(storeName.getBytes("utf-8"), "ISO8859-1"));
        response.setHeader("Content-Length", String.valueOf(fileLength));

        bis = new BufferedInputStream(new FileInputStream(downLoadPath));
        bos = new BufferedOutputStream(response.getOutputStream());
        byte[] buff = new byte[2048];
        int bytesRead;
        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
            bos.write(buff, 0, bytesRead);
        }
        bis.close();
        bos.close();

        return null;
    }
}
