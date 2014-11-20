package com.energicube.eno.monitor.web;

import com.energicube.eno.common.Config;
import com.energicube.eno.common.FileUtil;
import com.energicube.eno.common.ImportExpression;
import com.energicube.eno.common.PatternConst;
import com.energicube.eno.common.dto.SystemMessage;
import com.energicube.eno.common.excel.ExcelService;
import com.energicube.eno.monitor.model.Expression;
import com.energicube.eno.monitor.model.NetvideoCameracfg;
import com.energicube.eno.monitor.model.NetvideoDvrcfg;
import com.energicube.eno.monitor.service.ExpressionService;
import com.energicube.eno.monitor.service.NetvideoCameracfgService;
import com.energicube.eno.monitor.service.NetvideoDvrcfgService;
import com.energicube.eno.monitor.service.OtherSystemService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * 导入配置控制页面
 *
 * @author zouzhixiang
 * @date 2014-08-20
 */
@Controller
@RequestMapping("/okcsys/import")
public class ImportController {
    private final Log logger = LogFactory.getLog(this.getClass());

    private Config config = new Config();

    @Autowired
    private OtherSystemService otherSystemService;
    @Autowired
    private ExpressionService expressionService;
    @Autowired
    private NetvideoDvrcfgService dvrcfgService;
    @Autowired
    private NetvideoCameracfgService cameracfgService;

    /**
     * 进入导入配置页面
     *
     * @return String (页面路径)
     */
    @RequestMapping(value = "/config", method = RequestMethod.GET)
    public String showSysconfig(Model model, HttpServletRequest request) {
        return "import/config";
    }

    /**
     * 导入设备台帐
     * liuguanglu
     *
     * @param request
     * @throws Exception
     * @since 2014-08-28
     */
    @RequestMapping(value = "/asset/upload", method = RequestMethod.POST)
    @ResponseBody
    public Object upload(HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception {
        SystemMessage message = new SystemMessage();
        try {
            //处理上传文件
            String patternExcelPath = config.getProps().getProperty("patternExcelPath");
            Collection<Sheet> result = new ArrayList<Sheet>();
            MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> fileMap = mRequest.getFileMap();

            String uploadDir = request.getSession().getServletContext().getRealPath("/") +"\\"+ patternExcelPath;
            logger.debug("----asset---uploadDir------" + uploadDir);
            File file = new File(uploadDir);
            if (!file.exists()) {
                file.mkdir();
            }

            //多文件上传
            String fileName = null;
            int i = 0;
            for (Iterator<Map.Entry<String, MultipartFile>> it = fileMap.entrySet().iterator(); it.hasNext(); i++) {

                Map.Entry<String, MultipartFile> entry = it.next();
                MultipartFile mFile = entry.getValue();

                fileName = mFile.getOriginalFilename();
                logger.debug("-------fileName------" + fileName);
                OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(uploadDir + fileName));
                FileCopyUtils.copy(mFile.getInputStream(), outputStream);

                Collection<Sheet> sheetCollection = ExcelService.getExcelSheetFilePath(uploadDir + fileName);
                result.addAll(sheetCollection);
            }
            //导入数据
            otherSystemService.saveAssetToDatabase(result);

            message.setSuccess(true);
            message.setMsg(PatternConst.JSON_SUCCESS);

        } catch (Exception e) {
            logger.error("---asset  upload--------", e);
            message.setSuccess(false);
            message.setMsg(PatternConst.JSON_FAILURE);
        }

        return message;
    }

    /**
     * 上传首页背景图
     * liuguanglu
     *
     * @param request
     * @throws Exception
     * @since 2014-08-28
     */
    @RequestMapping(value = "/pic/upload", method = RequestMethod.POST)
    @ResponseBody
    public Object picUpload(HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception {
        SystemMessage message = new SystemMessage();
        try {
            //处理上传文件
            MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> fileMap = mRequest.getFileMap();

            String uploadDir = request.getSession().getServletContext().getRealPath("/") +"\\"+ config.getProps().getProperty("index.background.path");
            logger.debug("----picUpload---uploadDir------" + uploadDir);
            File file = new File(uploadDir);
            if (!file.exists()) {
                file.mkdir();
            }

            //多文件上传
            String fileName = config.getProps().getProperty("index.background.name");
            int i = 0;
            for (Iterator<Map.Entry<String, MultipartFile>> it = fileMap.entrySet().iterator(); it.hasNext(); i++) {

                Map.Entry<String, MultipartFile> entry = it.next();
                MultipartFile mFile = entry.getValue();

                String tempFileName = mFile.getOriginalFilename();
//                if(tempFileName.substring(tempFileName.indexOf("."),tempFileName.length()).equals("jpg")){
                logger.debug("-------fileName------" + fileName);
                OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(uploadDir + fileName));
                FileCopyUtils.copy(mFile.getInputStream(), outputStream);

                message.setSuccess(true);
                message.setMsg(PatternConst.JSON_SUCCESS);
//                }else{
//                    message.setSuccess(false);
//                    message.setMsg(PatternConst.JSON_FAILURE);
//                }
            }

        } catch (Exception e) {
            logger.error("---picUpload upload--------", e);
            message.setSuccess(false);
            message.setMsg(PatternConst.JSON_FAILURE);
        }
        return message;
    }

    /**
     * 上传首页背景图
     * liuguanglu
     *
     * @param request
     * @throws Exception
     * @since 2014-08-28
     */
    @RequestMapping(value = "/pic/night/upload", method = RequestMethod.POST)
    @ResponseBody
    public Object picNightUpload(HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception {
        SystemMessage message = new SystemMessage();
        try {
            //处理上传文件
            MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> fileMap = mRequest.getFileMap();

            String uploadDir = request.getSession().getServletContext().getRealPath("/") +"\\"+ config.getProps().getProperty("night.background.path");
            logger.debug("---picNightUpload----uploadDir------" + uploadDir);
            File file = new File(uploadDir);
            if (!file.exists()) {
                file.mkdir();
            }

            //多文件上传
            String fileName = "";
            int i = 0;
            for (Iterator<Map.Entry<String, MultipartFile>> it = fileMap.entrySet().iterator(); it.hasNext(); i++) {

                Map.Entry<String, MultipartFile> entry = it.next();
                MultipartFile mFile = entry.getValue();

                String tempFileName = mFile.getOriginalFilename();
//                if(tempFileName.substring(tempFileName.indexOf("."),tempFileName.length()).equals("png")){
                logger.debug("-------fileName------" + fileName);
                OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(uploadDir + tempFileName));
                FileCopyUtils.copy(mFile.getInputStream(), outputStream);

                message.setSuccess(true);
                message.setMsg(PatternConst.JSON_SUCCESS);
//                }else{
//                    message.setSuccess(false);
//                    message.setMsg(PatternConst.JSON_FAILURE);
//                }
            }

        } catch (Exception e) {
            logger.error("---picNightUpload  upload--------", e);
            message.setSuccess(false);
            message.setMsg(PatternConst.JSON_FAILURE);
        }

        return message;
    }

    /**
     * 上传首页的配置表
     * liuguanglu
     *
     * @param request
     * @throws Exception
     * @since 2014-08-28
     */
    @RequestMapping(value = "/dataconfig/index/upload", method = RequestMethod.POST)
    @ResponseBody
    public Object dataConfigUpload(HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception {
        SystemMessage message = new SystemMessage();
        try {
            //处理上传文件
            MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;
            Map<String, MultipartFile> fileMap = mRequest.getFileMap();
            Collection<Sheet> result = new ArrayList<Sheet>();
            String excelPath = config.getProps().getProperty("patternExcelPath");
            String uploadDir = request.getSession().getServletContext().getRealPath("/") +"\\"+ excelPath;
            logger.debug("---dataConfigUpload----uploadDir------" + uploadDir);
            File file = new File(uploadDir);
            if (!file.exists()) {
                file.mkdir();
            }

            //多文件上传
            int i = 0;
            for (Iterator<Map.Entry<String, MultipartFile>> it = fileMap.entrySet().iterator(); it.hasNext(); i++) {

                Map.Entry<String, MultipartFile> entry = it.next();
                MultipartFile mFile = entry.getValue();

                String tempFileName = mFile.getOriginalFilename();
                logger.debug("-------fileName------" + tempFileName);
                OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(uploadDir + tempFileName));
                FileCopyUtils.copy(mFile.getInputStream(), outputStream);


                Collection<Sheet> sheetCollection = ExcelService.getExcelSheetFilePath(uploadDir + tempFileName);
                result.addAll(sheetCollection);
            }
            //导入数据
            otherSystemService.saveIndexConfigToDatabase(result);

            message.setSuccess(true);
            message.setMsg(PatternConst.JSON_SUCCESS);
        } catch (Exception e) {
            logger.error("---dataConfigUpload  upload--------", e);
            message.setSuccess(false);
            message.setMsg(PatternConst.JSON_FAILURE);
        }

        return message;
    }

    /**
     * 导入表达式
     *
     * @param type
     * @param path
     */
    @RequestMapping(value = "/dataconfig/expression/{type}", method = RequestMethod.POST)
    @ResponseBody
    public int processimportexpress(@PathVariable int type, HttpServletRequest request) {
        String dirName = "resources/structures/Netvideo/Driver";
        String fileName = "";
        File file = null;
        List<Expression> expressions = null;
        try {
            fileName = FileUtil.uploadFile(request, "newExcel", dirName);
            expressions = ImportExpression.readExcel(request, fileName);
        } catch (Exception e1) {
            e1.printStackTrace();
            return 1;//表示文件中内容有误
        }
        if (expressions != null && expressions.size() > 0) {
            if (type == 0) {
                expressionService.deleteAll();
            }
            try {
                expressionService.saveExpressions(expressions);
            } catch (Exception e) {
                return 1;//表示文件中内容有误
            }
            return 0;//表示导入成功
        } else {
            return 1;//表示文件中内容有误
        }
    }

    /**
     * 导入dvr配置
     *
     * @param type
     * @param path
     */
    @RequestMapping(value = "/dvrconfig/{type}", method = RequestMethod.POST)
    @ResponseBody
    public int processimportdvr(@PathVariable int type, HttpServletRequest request) {
        String dirName = "resources/structures/Netvideo/Driver";
        String fileName = "";
        File file = null;
        List<NetvideoDvrcfg> dvrcfgs = null;
        try {
            fileName = FileUtil.uploadFile(request, "newExcel", dirName);
            file = new File(fileName);
            dvrcfgs = ImportExpression.readDvrExcel(file);
        } catch (Exception e1) {
            e1.printStackTrace();
            return 1;//表示文件中内容有误
        }
        file.deleteOnExit();//删除文件
        if (dvrcfgs != null && dvrcfgs.size() > 0) {
            if (type == 1) {
                dvrcfgService.deleteAll();
            }
            try {
                dvrcfgService.saveNetvideoDvrcfg(dvrcfgs);
            } catch (Exception e) {
                return 1;//表示文件中内容有误
            }
            return 0;//表示导入成功
        } else {
            return 1;//表示文件中内容有误
        }
    }

    /**
     * 导入camera配置
     *
     * @param type
     * @param path
     */
    @RequestMapping(value = "/camconfig/{type}", method = RequestMethod.POST)
    @ResponseBody
    public int processimportcamera(@PathVariable int type, HttpServletRequest request) {
        String dirName = "resources/structures/Netvideo/Driver";
        String fileName = "";
        File file = null;
        List<NetvideoCameracfg> cameracfgs = null;
        try {
            fileName = FileUtil.uploadFile(request, "newExcel", dirName);
            file = new File(fileName);
            cameracfgs = ImportExpression.readCamExcel(file);
        } catch (Exception e1) {
            e1.printStackTrace();
            return 1;//表示文件中内容有误
        }
        file.deleteOnExit();//删除文件
        if (cameracfgs != null && cameracfgs.size() > 0) {
            if (type == 1) {
                cameracfgService.deleteAll();
            }
            try {
                cameracfgService.saveConfigs(cameracfgs);
            } catch (Exception e) {
                return 1;//表示文件中内容有误
            }
            return 0;//表示导入成功
        } else {
            return 1;//表示文件中内容有误
        }
    }
}
