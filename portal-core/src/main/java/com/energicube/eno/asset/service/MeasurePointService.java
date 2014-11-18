package com.energicube.eno.asset.service;

import com.energicube.eno.asset.model.MeasurePoint;
import com.energicube.eno.common.jsonquery.jpa.request.DataTablesRequestParams;
import com.energicube.eno.common.jsonquery.jpa.response.DataTablesResponse;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;

import java.util.List;

/**
 * 状态监控测点业务逻辑接口
 *
 * @author CHENPING
 */
public interface MeasurePointService {
    /**
     * 获取所有测点数据
     *
     * @param siteid 地点
     * @param orgid  组织机构
     * @param params 请求参数
     * @return JQUERY DataTables {@link DataTablesResponse} 数据源
     */
    public DataTablesResponse<MeasurePoint> findAllMeasurePoints(String siteid, String orgid, DataTablesRequestParams params);


    /**
     * <p/>
     * Query used to populate the DataTables that display the list of MeasurePoints.
     *
     * @param criterias The DataTables criterias used to filter the MeasurePoints.
     *                  (maxResult, filtering, paging, ...)
     * @return a bean that wraps all the needed information by DataTables to
     * redraw the table with the data.
     */
    public DataSet<MeasurePoint> findMeasurePointsWithDatatablesCriterias(DatatablesCriterias criterias);

    /**
     * 获取指定的资产编号和计量器的测点列表
     *
     * @param assetnum  资产编号
     * @param metername 计量器名称
     * @param isspec    是否技术规范参数测量读数（true=是,false=否 ,null=所有读数)
     * @return {@link MeasurePoint} object list
     */
    public List<MeasurePoint> findMeasurePointsByAssetnumAndMetername(String assetnum, String metername, Boolean isspec);

    /**
     * 生成测点编号
     */
    public String generatePointNum();

    /**
     * 判断指定的测点是否存在
     *
     * @param pointnum 测点编号
     * @param siteid   地点
     * @param orgid    组织机构
     * @return true=存在 false=不存在
     */
    public boolean existPointNum(String pointnum, String siteid, String orgid);

    /**
     * 保存测点数据
     *
     * @param measurePoint {@link MeasurePoint} object
     * @return {@link MeasurePoint} object
     */
    public MeasurePoint saveMeasurePoint(MeasurePoint measurePoint) throws Exception;

    /**
     * 获取指定的测点数据
     *
     * @param measurepointid 测点ID
     * @return {@link MeasurePoint} 测点对象
     */
    public MeasurePoint findMeasurePointById(long measurepointid);

    /**
     * 删除指定的测点数据
     *
     * @param measurepointid 测点ID
     * @return true=成功 false=失败
     */
    public void deleteMeasurePoint(long measurepointid) throws Exception;


}
