package com.energicube.eno.asset.service;

import com.energicube.eno.asset.model.MeasureUnit;
import com.energicube.eno.common.jsonquery.jpa.request.DataTablesRequestParams;
import com.energicube.eno.common.jsonquery.jpa.response.DataTablesResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MeasureUnitService {


    /**
     * 获取所有计量单位
     *
     * @param pageable 分页对象
     * @return 计量单位列表
     */
    public List<MeasureUnit> getMeasureUnits();

    /**
     * 获取所有计量单位
     *
     * @param pageable 分页对象
     * @return 计量单位列表
     */
    public Page<MeasureUnit> findAllMeasureUnit(Pageable pageable);

    /**
     * 获取所有计量单位
     *
     * @param params 请求参数,参考 {@link DataTablesRequestParams}
     * @return 计量单位{@link AssetAttribute}数据定义{@link DataTablesResponse}列表
     */
    public DataTablesResponse<MeasureUnit> findMeasureUnitToDataTables(DataTablesRequestParams params);

    /**
     * 判断计量单位ID是否已经存在
     *
     * @param measureunitid 计量单位ID
     * @return 如果存在则为true，否则为false
     */
    public boolean existMeasureUnit(String measureunitid);

    /**
     * 保存计量单位信息
     *
     * @param measureUnit {@link MeasureUnit} objects
     * @return {@link MeasureUnit} object
     */
    public MeasureUnit saveMeasureUnit(MeasureUnit measureUnit);

    /**
     * 删除计量单位
     *
     * @param measureunituid 计量单位ID
     */
    public void deleteMeasureUnit(long measureunituid);


}
