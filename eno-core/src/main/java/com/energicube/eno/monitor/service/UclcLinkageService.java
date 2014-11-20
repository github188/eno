package com.energicube.eno.monitor.service;

import com.energicube.eno.common.jsonquery.jpa.request.DataTablesRequestParams;
import com.energicube.eno.common.jsonquery.jpa.response.DataTablesResponse;
import com.energicube.eno.monitor.model.UclcLinkage;

import java.util.List;

public interface UclcLinkageService {
    /**
     * 查找全部联动触发事件或者条件信息
     *
     * @return
     */
    public List<UclcLinkage> findAll();

    /**
     * 查找单个联动触发事件或者条件信息
     *
     * @return
     */
    public UclcLinkage findOne(long id);

    /**
     * 保存规划信息
     *
     * @param obj
     * @return
     */
    public void save(UclcLinkage obj);

    /**
     * 保存并更新规划信息
     *
     * @param obj
     */
    public void update(UclcLinkage obj);

    /**
     * 删除指定规划信息
     *
     * @param obj
     * @return
     */
    public void del(long id);

    /**
     * 查找规划列表
     *
     * @param params
     * @return
     */
    public DataTablesResponse<UclcLinkage> findAllLinkAllToDataTables(DataTablesRequestParams params);
}
