package com.energicube.eno.monitor.service;

import com.energicube.eno.common.jsonquery.jpa.request.DataTablesRequestParams;
import com.energicube.eno.common.jsonquery.jpa.response.DataTablesResponse;
import com.energicube.eno.monitor.model.UclcCell;

import java.util.List;

public interface UclcCellService {
    /**
     * 查找全部预案信息
     *
     * @return
     */
    public List<UclcCell> findAll();

    /**
     * 查找指定预案信息
     *
     * @return
     */
    public UclcCell findOne(int condid);

    /**
     * 保存预案信息
     *
     * @param obj 预案对象
     * @return
     */
    public void save(UclcCell obj);

    /**
     * 更新并保存预案信息
     *
     * @param obj 预案对象
     * @return
     */
    public void edit(UclcCell obj);

    /**
     * 删除指定预案信息
     *
     * @param obj
     * @return
     */
    public void del(int id);

    /**
     * 查找联动预案列表
     *
     * @param params
     * @return
     */
    public DataTablesResponse<UclcCell> findAllCellToDataTables(DataTablesRequestParams params);
}
