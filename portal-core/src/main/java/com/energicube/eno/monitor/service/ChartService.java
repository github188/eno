package com.energicube.eno.monitor.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 首页及门户中报表使用的接口
 *
 * @author zouzhixiang
 * @create by date 2014-03-25 15:26:05
 */
public interface ChartService {

    /**
     * 根据以下参数，返回图表需要的数据
     *
     * @param name
     * @param id
     * @param ispd
     * @param type
     * @param tfrom
     * @param tto
     * @param attribute
     * @param beforeFormat
     * @param afterFormat
     * @param ipaddress
     * @param port
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Map<String, Object> getChartData(String name, String id, String ispd, String type, String tfrom, String tto, String attribute, String beforeFormat, String afterFormat, String ipaddress, String port, String isNotStatic);

    /**
     * 根据以下参数，返回单个url获取的值
     *
     * @param name
     * @param id
     * @param ispd
     * @param type
     * @param tfrom
     * @param tto
     * @param ipaddress
     * @param port
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Map<String, Object> getSingleValueData(String name, String id, String ispd, String type, String tfrom, String tto, String ipaddress, String port);

    /**
     * 查询门户管理-报表需要的数据
     *
     * @param name
     * @param id
     * @param ispd
     * @param type
     * @param tfrom
     * @param tto
     * @param decimals
     * @param ipaddress
     * @param port
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Map<String, Object> getReportDataList(String name, String id, String type, String tfrom, String tto, String decimals, String ipaddress, String port, String isNotStatic);

    /**
     * 门户管理-导出报表需要的数据到excel中
     *
     * @param name
     * @param id
     * @param ispd
     * @param type
     * @param tfrom
     * @param tto
     * @param decimals
     * @param ipaddress
     * @param port
     * @param sheetname
     * @param commonColumn
     * @param request
     * @param response
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public void outputToExcel(String name, String id, String ispd, String type, String tfrom, String tto, String decimals, String ipaddress, String port, String sheetname, String commonColumn, String isNotStatic,
                              HttpServletRequest request, HttpServletResponse response);

    /**
     * 批量导出-导出报表需要的数据到excel中
     *
     * @param tfrom
     * @param tto
     * @param type
     * @param system
     * @param deviceType
     * @param devices
     * @param decimals
     * @param ipaddress
     * @param port
     * @param isNotStatic
     * @param request
     * @param response
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public void exportToExcel(String tfrom, String tto, String type, String system, String deviceType, String devices, String decimals,
                              String ipaddress, String port, String isNotStatic, HttpServletRequest request, HttpServletResponse response);

}
