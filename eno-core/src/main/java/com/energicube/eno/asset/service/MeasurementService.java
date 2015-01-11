package com.energicube.eno.asset.service;

import com.energicube.eno.asset.model.Measurement;
import com.energicube.eno.asset.model.MeasurementAttribute;
import com.energicube.eno.asset.model.MeasurementSet;
import com.energicube.eno.common.jsonquery.jpa.request.DataTablesRequestParams;
import com.energicube.eno.common.jsonquery.jpa.response.DataTablesResponse;

import java.util.List;


/**
 * 计量器读数或技术规范的测量值
 *
 * @author CHENPING
 */
public interface MeasurementService {

    /**
     * 获取技术规范测量值列表
     *
     * @param pointnum  测点ID
     * @param metername 计量器ID
     * @param siteid    地点ID
     * @param params    {@link DataTablesRequestParams}
     * @return JQUERY DataTables {@link DataTablesResponse} 数据源
     */
    public DataTablesResponse<MeasurementSet> findMeasurementsToDataTables(String pointnum, String metername, String siteid, String orgid, DataTablesRequestParams params);


    /**
     * 保存技术规范的测量值
     *
     * @param measurement {@link Measurement} object
     * @return {@link Measurement} object
     */
    public Measurement saveMeasurement(Measurement measurement);

    /**
     * 删除技术规范的测量值
     *
     * @param measurementid {@link Measurement} 0bject primary key id
     * @return true=成功 false=失败
     */
    public boolean deleteMeasurement(long measurementid) throws Exception;

    /**
     * 获取指定资产的计量信息
     *
     * @param assetnum  资产编号
     * @param metername 计量器
     * @param siteid    地点
     * @param orgid     组织机构
     * @return 资产计量信息
     */
    public List<MeasurementSet> findMeasurementsByAssetnumAndMetername(String assetnum,
                                                                       String metername, String siteid, String orgid);

    /**
     * 获取指定资产的采集变量信息
     *
     * @param assetnum 资产编号
     * @param siteid   地点
     * @param orgid    组织机构
     * @return 资产计量信息
     */
    public List<MeasurementAttribute> findMeasurementsByAssetnum(String assetnum, String siteid, String orgid);


    /**
     * 获取计量器读数信息
     *
     * @param measurementid 读数ID
     * @return {@link MeasurementSet} object
     */
    public MeasurementSet findByMeasurementid(long measurementid);

    /**
     * 获取计量器读数信息
     *
     * @param measurementid 读数ID
     * @return {@link MeasurementSet} object
     */
	public Measurement findMeasurementByAssetnum(String tagid);
}
