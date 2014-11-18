package com.energicube.eno.asset.service;

import com.energicube.eno.asset.model.MeasureSpec;
import com.energicube.eno.asset.model.MeasureSpecSet;
import com.energicube.eno.common.jsonquery.jpa.request.DataTablesRequestParams;
import com.energicube.eno.common.jsonquery.jpa.response.DataTablesResponse;

import java.util.List;

/**
 * 状态监控测点技术规范业务逻辑接口
 * <p>
 * 业务逻辑接口主要描述了状态监控的业务操作方法，包括测点列表、保存测点、判断测点是否存在、保存测点技术规范
 * </p>
 *
 * @author CHENPING
 */
public interface MeasureSpecService {


    /**
     * 获取JQUERY DATATABLES 结构形式的技术规范数据
     *
     * @param metername 计量器编号
     * @param siteid    地点
     * @param params    {@link DataTablesRequestParams} request parameters
     * @return MeasureSpec of {@link DataTablesResponse} object
     */
    public DataTablesResponse<MeasureSpec> findMeasureSpecsToDataTables(String metername, String siteid, DataTablesRequestParams params);

    /**
     * 获取JQUERY DATATABLES 结构形式的技术规范数据
     *
     * @param assetnum  资产编号
     * @param metername 计量器编号
     * @param siteid    地点
     * @param params    {@link DataTablesRequestParams} request parameters
     * @return MeasureSpec of {@link DataTablesResponse} object
     */
    public DataTablesResponse<MeasureSpec> findAssetMeasureSpecsToDataTables(String assetnum, String metername, String siteid, DataTablesRequestParams params);


    /**
     * 获取测点状态监控技术规范
     *
     * @param assetnum  资产编号
     * @param metername 计量器编号
     * @return 测点状态监控技术规范列表
     */
    public List<MeasureSpec> findMeasureSpecsByAssetnumAndMetername(String assetnum, String metername, String siteid);

    /**
     * 保存测点技术规范
     *
     * @param measureSpec {@link MeasureSpec} object
     * @return {@link MeasureSpec} object
     */
    public MeasureSpec saveMeasureSpec(MeasureSpec measureSpec, long measurepointid) throws Exception;


    public MeasureSpec updateMeasureSpec(MeasureSpec measureSpec, long measurepointid) throws Exception;

    /**
     * 获取测点技术规范
     *
     * @param measurespecid 技术规范ID
     * @return
     */
    public MeasureSpecSet findMeasureSpecSetById(long measurespecid);

    /**
     * 判断测点中是否存在相同的技术规范
     *
     * @param assetnum    资产编号
     * @param metername   计量器编号
     * @param siteid      地点
     * @param assetattrid 资产属性ID
     * @return true=存在 false=不存在
     */
    public boolean existMeasureSpec(String assetnum, String metername, String siteid, String assetattrid);

    /**
     * 删除测点技术规范
     *
     * @param measurespecid {@link MeasureSpec}'s primary key id
     * @return true=成功,false=失败
     */
    public boolean deleteMeasureSpec(long measurespecid) throws Exception;

    /**
     * 获取计量器技术规范
     *
     * @param measurespecid {@link MeasureSpec}'s primary key id
     */
    public MeasureSpec findMeasureSpecById(long measurespecid);

}
