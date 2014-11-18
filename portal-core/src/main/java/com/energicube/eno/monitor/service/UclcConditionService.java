package com.energicube.eno.monitor.service;

import com.energicube.eno.common.jsonquery.jpa.request.DataTablesRequestParams;
import com.energicube.eno.common.jsonquery.jpa.response.DataTablesResponse;
import com.energicube.eno.monitor.model.UcTag;
import com.energicube.eno.monitor.model.UclcCondition;

import java.util.List;

public interface UclcConditionService {
    /**
     * 查找事件库中的全部事件信息
     *
     * @return
     */
    public List<UclcCondition> findAll();

    /**
     * 查找单个联动触发事件或者条件信息
     *
     * @return
     */
    public UclcCondition findOne(int condid);

    /**
     * 添加触发事件或者条件信息
     *
     * @param obj
     * @return
     */
    public void save(UclcCondition obj);

    /**
     * 编辑触发事件或者条件信息
     *
     * @param obj
     * @return
     */
    public void edit(UclcCondition obj);

    /**
     * 根据唯一标识删除指定事件
     *
     * @param id 事件唯一标识
     * @return
     */
    public void del(int id);

    /**
     * 查找事件库列表
     *
     * @param params
     * @return
     */
    public DataTablesResponse<UclcCondition> findAllCondiotionToDataTables(DataTablesRequestParams params);

    /**
     * 查找变量
     *
     * @return
     */
    public List<UcTag> findAllUcTagNames();
}
