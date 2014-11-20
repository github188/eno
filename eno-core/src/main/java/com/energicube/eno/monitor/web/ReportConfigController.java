package com.energicube.eno.monitor.web;

import com.energicube.eno.monitor.model.ReportConfig;
import com.energicube.eno.monitor.service.ReportConfigService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by energicube on 2014/8/21.
 * User: zengyin
 */
@Controller
@RequestMapping("/reportConfig")
public class ReportConfigController extends BaseController {
    private static final Log logger = LogFactory.getLog(MonitorCtrlController.class);

    @Autowired
    ReportConfigService reportConfigService;

    /**
     * 获取子系统列表
     */
    @RequestMapping(value = "/getSystemList", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getSystemList() {
        return reportConfigService.findSystemList();
    }

    /**
     * 获取设备类型列表，取reportconfig表中的devicetype字段数据，需控制唯一,分两种情况：
     * 1.参数为空，查询全部
     * 2.参数不为空，根据system查询对应的devicetype
     *
     * @param system
     */
    @RequestMapping(value = "/getDeviceType", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getDeviceType(@RequestParam(value = "system", required = false) String system, HttpServletRequest request) {
        String systemStr = "";
        try {
            systemStr = new String(request.getParameter("system")
                    .getBytes("ISO-8859-1"), "utf-8");
            logger.info("--------------system-------------" + systemStr);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return reportConfigService.findDeviceType(systemStr);
    }

    /**
     * 获取设备列表，取reportconfig表中的device字段数据，需控制唯一,分两种情况：
     * 1.参数为空，查询全部
     * 2.参数不为空，根据system和devicetype查询关联的device
     *
     * @param system
     * @param request
     */
    @RequestMapping(value = "/getDeviceList", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getDeviceList(@RequestParam(value = "system", required = false) String system, @RequestParam(value = "devicetype", required = false) String devicetype, HttpServletRequest request) {
        String systemStr = "", devicetypeStr = "";
        try {
            systemStr = new String(request.getParameter("system")
                    .getBytes("ISO-8859-1"), "utf-8");
            devicetypeStr = new String(request.getParameter("devicetype")
                    .getBytes("ISO-8859-1"), "utf-8");
            logger.info("--------------system-------------" + systemStr + "------devicetype----" + devicetypeStr);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return reportConfigService.findDeviceList(systemStr, devicetypeStr);
    }

    /**
     * 获取设备列表，取reportconfig表中的id字段数据，需控制唯一,分两种情况：
     * 1.参数为空，查询全部
     * 2.参数不为空，根据system和devicetype查询关联的id
     *
     * @param system
     * @param request
     */
    @RequestMapping(value = "/getDeviceIdList", method = RequestMethod.GET)
    @ResponseBody
    public List<String> getDeviceIdList(@RequestParam(value = "system", required = false) String system, @RequestParam(value = "devicetype", required = false) String devicetype, HttpServletRequest request) {
        String systemStr = "", devicetypeStr = "";
        try {
            systemStr = new String(request.getParameter("system")
                    .getBytes("ISO-8859-1"), "utf-8");
            devicetypeStr = new String(request.getParameter("devicetype")
                    .getBytes("ISO-8859-1"), "utf-8");
            logger.info("--------------system-------------" + systemStr + "------devicetype----" + devicetypeStr);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return reportConfigService.findDeviceIdList(systemStr, devicetypeStr);
    }

    /**
     * 获取设备列表，取reportconfig表中的id字段数据，需控制唯一,分两种情况：
     * 1.参数为空，查询全部
     * 2.参数不为空，根据system和devicetype查询关联的id
     *
     * @param system
     * @param devicetype
     */
    @RequestMapping(value = "/getParamsList", method = RequestMethod.GET)
    @ResponseBody
    public List<ReportConfig> getParamsList(@RequestParam(value = "system", required = false) String system, @RequestParam(value = "devicetype", required = false) String devicetype, @RequestParam(value = "id", required = false) String id, HttpServletRequest request) {
        String systemStr = "", devicetypeStr = "", idStr = "";
        try {
            systemStr = new String(request.getParameter("system")
                    .getBytes("ISO-8859-1"), "utf-8");
            devicetypeStr = new String(request.getParameter("devicetype")
                    .getBytes("ISO-8859-1"), "utf-8");
            idStr = new String(request.getParameter("id")
                    .getBytes("ISO-8859-1"), "utf-8");
            logger.info("-------------system--" + systemStr + "---------devicetype--" + devicetypeStr + "--device--" + idStr);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return reportConfigService.findParamsList(systemStr, devicetypeStr, idStr);
    }

    /**
     * 获取实体类，取reportconfig数据，需控制唯一,分两种情况：
     * 1.参数为空，查询全部
     * 2.参数不为空，根据system、devicetype和 device查询关联的 reportconfig
     *
     * @param system
     * @param devicetype
     * @param device
     */
    @RequestMapping(value = "/getReportconfigsList", method = RequestMethod.GET)
    @ResponseBody
    public List<ReportConfig> getReportconfigsList(@RequestParam(value = "system", required = false) String system, @RequestParam(value = "devicetype", required = false) String devicetype, @RequestParam(value = "device", required = false) String device, HttpServletRequest request) {
        String systemStr = "", devicetypeStr = "", deviceStr = "";
        try {
            systemStr = new String(request.getParameter("system")
                    .getBytes("ISO-8859-1"), "utf-8");
            devicetypeStr = new String(request.getParameter("devicetype")
                    .getBytes("ISO-8859-1"), "utf-8");
            deviceStr = new String(request.getParameter("device")
                    .getBytes("ISO-8859-1"), "utf-8");
            logger.info("-------------system--" + systemStr + "---------devicetype--" + devicetypeStr + "--device--" + deviceStr);
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return reportConfigService.findReportconfigsList(systemStr, devicetypeStr, deviceStr);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(MultipartFile configFile, HttpServletRequest request, Model model) throws IOException {
        logger.info("-----------上传配置---------");
//        try {
        HSSFWorkbook workbook = new HSSFWorkbook(configFile.getInputStream());
        Sheet sheet = workbook.getSheet("Config");
        List<ReportConfig> reportConfigs = null;
        if (sheet != null) {
            logger.info("-------sheet != null-------------");
            reportConfigs = new ArrayList<ReportConfig>(sheet.getLastRowNum());
            // 循环行Row
            for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (row == null) {
                    continue;
                }

                // 循环列Cell
                ReportConfig reportConfig = new ReportConfig();
                for (int cellNum = 0; cellNum <= row.getLastCellNum(); cellNum++) {
                    if (cellNum > 7) {
                        break;
                    }
                    Cell cell = row.getCell(cellNum);
                    if (cell == null) {
                        continue;
                    } else {
                        String cellValue = cell.getStringCellValue();
                        switch (cellNum) {
                            case 0:
                                reportConfig.setSystem(cellValue);
                                break;
                            case 1:
                                reportConfig.setDevicetype(cellValue);
                                break;
                            case 2:
                                reportConfig.setDevice(cellValue);
                                break;
                            case 3:
                                reportConfig.setParams(cellValue);
                                break;
                            case 4:
                                reportConfig.setUnit(cellValue);
                                break;
                            case 5:
                                reportConfig.setName(cellValue);
                                break;
                            case 6:
                                reportConfig.setId(cellValue);
                                break;
                            case 7:
                                reportConfig.setIspd(cellValue);
                                break;
                        }
                    }
                }
                reportConfigs.add(reportConfig);
            }
            if (!reportConfigs.isEmpty()) {
                reportConfigs = reportConfigService.save(reportConfigs);
            }
        }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        model.addAttribute("reportConfigs", reportConfigs);

        return "/import/config";
    }
}
