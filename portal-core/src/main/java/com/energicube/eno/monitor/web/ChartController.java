package com.energicube.eno.monitor.web;

import com.energicube.eno.monitor.service.ChartService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求图表数据
 * 调用方法，如：/Chart/GetChartData?name=&id=&type=&tfrom=&tto=&ispd=&ipaddress=&port=
 *
 * @author zouzhixiang
 */
@Controller
@RequestMapping("/Chart")
public class ChartController {
    private final Log logger = LogFactory.getLog(getClass());

    @Autowired
    ChartService chartService;

    /**
     * 根据以下参数，返回图表需要的数据
     */
    @RequestMapping(value = "/GetChartData", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map<String, Object> GetChartData(
            @RequestParam(value = "name", required = true, defaultValue = "") String name,
            @RequestParam(value = "id", required = true, defaultValue = "") String id,
            @RequestParam(value = "ispd", required = true, defaultValue = "") String ispd,
            @RequestParam(value = "type", required = true, defaultValue = "day") String type,
            @RequestParam(value = "tfrom", required = true, defaultValue = "") String tfrom,
            @RequestParam(value = "tto", required = true, defaultValue = "") String tto,
            @RequestParam(value = "attribute", required = true, defaultValue = "") String attribute,
            @RequestParam(value = "beforeFormat", required = true, defaultValue = "yyyy-MM-dd") String beforeFormat,
            @RequestParam(value = "afterFormat", required = true, defaultValue = "") String afterFormat,
            @RequestParam(value = "ipaddress", required = true, defaultValue = "") String ipaddress,
            @RequestParam(value = "port", required = true, defaultValue = "") String port,
            @RequestParam(value = "isNotStatic", required = true, defaultValue = "") String isNotStatic) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            map = chartService.getChartData(name, id, ispd, type, tfrom, tto,
                    attribute, beforeFormat, afterFormat, ipaddress, port, isNotStatic);
        } catch (Exception e) {
            logger.error(e);
        }
        return map;
    }

    /**
     * 根据以下参数，返回单个url获取的值
     */
    @RequestMapping(value = "/GetSingleValueData", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map<String, Object> GetSingleValueData(
            @RequestParam(value = "name", required = true, defaultValue = "") String name,
            @RequestParam(value = "id", required = true, defaultValue = "") String id,
            @RequestParam(value = "ispd", required = true, defaultValue = "") String ispd,
            @RequestParam(value = "type", required = true, defaultValue = "day") String type,
            @RequestParam(value = "tfrom", required = true, defaultValue = "") String tfrom,
            @RequestParam(value = "tto", required = true, defaultValue = "") String tto,
            @RequestParam(value = "ipaddress", required = true, defaultValue = "") String ipaddress,
            @RequestParam(value = "port", required = true, defaultValue = "") String port) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            chartService.getSingleValueData(name, id, ispd, type, tfrom, tto,
                    ipaddress, port);
        } catch (Exception e) {
            logger.error(e);
        }
        return map;
    }

    /**
     * 查询门户管理-报表需要的数据
     */
    @RequestMapping(value = "/getReportDataList", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public Map<String, Object> getReportDataList(
            @RequestParam(value = "name", required = true, defaultValue = "") String name,
            @RequestParam(value = "id", required = true, defaultValue = "") String id,
            @RequestParam(value = "ispd", required = true, defaultValue = "") String ispd,
            @RequestParam(value = "type", required = true, defaultValue = "day") String type,
            @RequestParam(value = "tfrom", required = true, defaultValue = "") String tfrom,
            @RequestParam(value = "tto", required = true, defaultValue = "") String tto,
            @RequestParam(value = "decimals", required = true, defaultValue = "") String decimals,
            @RequestParam(value = "ipaddress", required = true, defaultValue = "") String ipaddress,
            @RequestParam(value = "port", required = true, defaultValue = "") String port,
            @RequestParam(value = "isNotStatic", required = true, defaultValue = "") String isNotStatic) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            resultMap = chartService.getReportDataList(name, id, type,
                    tfrom, tto, decimals, ipaddress, port, isNotStatic);
        } catch (Exception e) {
            logger.error(e);
        }
        return resultMap;
    }

    /**
     * 查询门户管理-报表需要的数据
     */
    @RequestMapping(value = "/outputToExcel", method = RequestMethod.GET, produces = "application/octet-stream")
    @ResponseBody
    public void outputToExcel(
            @RequestParam(value = "name", required = true, defaultValue = "") String name,
            @RequestParam(value = "id", required = true, defaultValue = "") String id,
            @RequestParam(value = "ispd", required = true, defaultValue = "") String ispd,
            @RequestParam(value = "type", required = true, defaultValue = "day") String type,
            @RequestParam(value = "tfrom", required = true, defaultValue = "") String tfrom,
            @RequestParam(value = "tto", required = true, defaultValue = "") String tto,
            @RequestParam(value = "decimals", required = true, defaultValue = "") String decimals,
            @RequestParam(value = "sheetname", required = true, defaultValue = "sheetname") String sheetname,
            @RequestParam(value = "commonColumn", required = true, defaultValue = "") String commonColumn,
            @RequestParam(value = "ipaddress", required = true, defaultValue = "") String ipaddress,
            @RequestParam(value = "port", required = true, defaultValue = "") String port,
            @RequestParam(value = "isNotStatic", required = true, defaultValue = "true") String isNotStatic,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            chartService.outputToExcel(name, id, ispd, type, tfrom, tto, decimals, ipaddress, port, sheetname, commonColumn, isNotStatic, request, response);
        } catch (Exception e) {
            logger.error(e);
        }
    }

    @RequestMapping(value = "/exportToExcel", method = RequestMethod.GET, produces = "application/octet-stream")
    @ResponseBody
    public void exportToExcel(
            @RequestParam(value = "tfrom", required = true, defaultValue = "") String tfrom,
            @RequestParam(value = "tto", required = true, defaultValue = "") String tto,
            @RequestParam(value = "type", required = true, defaultValue = "day") String type,
            @RequestParam(value = "system", required = true, defaultValue = "") String system,
            @RequestParam(value = "deviceType", required = true, defaultValue = "") String deviceType,
            @RequestParam(value = "devices", required = true, defaultValue = "") String devices,
            @RequestParam(value = "decimals", required = true, defaultValue = "") String decimals,
            @RequestParam(value = "ipaddress", required = true, defaultValue = "127.0.0.1") String ipaddress,
            @RequestParam(value = "port", required = true, defaultValue = "8087") String port,
            @RequestParam(value = "isNotStatic", required = true, defaultValue = "true") String isNotStatic,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            chartService.exportToExcel(tfrom, tto, type, system, deviceType, devices, decimals, ipaddress, port, isNotStatic, request, response);
        } catch (Exception e) {
            logger.equals(e);
        }

    }
}