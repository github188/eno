package com.energicube.eno.monitor.service;

import com.energicube.eno.monitor.model.ReportConfig;

import java.util.List;

/**
 * 
 * All rights Reserved, Designed By ZCLF
 * Copyright:  Copyright(C) 2013-2014
 * Company  ZCLF Energy Technologies Inc.
 *
 * @author 尚培宝
 * @version 1.0
 * @date 2014年11月14日
 * @Description 报表台账分类查询
 */
public interface ReportConfigService {
	/**
	 * 获取子系统列表
	 * 
	 * @return List<String>
	 */
    public List<String> findSystemList();

    /**
     * 获取设备类型列表
     * 参数为空时查询全部
     * 参数不为空，根据system查询对应的devicetype
     *
     * @param system
     * @return List<String>
     */
    public List<String> findDeviceType(String system);

    /**
     * 获取设备列表
     * 参数为空时查询全部
     * 参数不为空，根据system，devicetypeStr查询对应的device
     *
     * @param system
     * @param devicetype
     * @return List<String>
     */
    public List<String> findDeviceList(String system, String devicetype);

	/**
	 * 获取设备列表中的ID 参数为空时查询全部 参数不为空，根据system，devicetypeStr查询对应的device
	 *
	 * @param system
	 * @param devicetype
	 * @return List<String>
	 */
    public List<String> findDeviceIdList(String system, String devicetype);

    /**
     * 获取设备列表，取reportconfig表中的id字段数据，需控制唯一,分两种情况：
     * 参数为空查询查询全部
     * 参数不为空，根据system和devicetype查询关联的id
     *
     * @param system
     * @param devicetype
     * @param device
     * @return List<ReportConfig>
     */
    public List<ReportConfig> findParamsList(String system, String devicetype, String device);

    /**
     * 获取reportconfig数据，分两种情况：
     * 参数为空查询查询全部
     * 参数不为空，根据system和devicetype查询关联的id
     *
     * @param system
     * @param devicetype
     * @param device
     * @return List<ReportConfig>
     */
    public List<ReportConfig> findReportconfigsList(String systemStr, String devicetypeStr, String deviceStr);

    /**
     * 批量保存
     *
     * @param reportConfigs
     * @return List<ReportConfig>
     */
    public List<ReportConfig> save(Iterable<ReportConfig> reportConfigs);

}
