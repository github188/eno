package com.energicube.eno.monitor.service;

import com.energicube.eno.monitor.model.UclcLinkcell;

import java.util.List;

public interface UclcLinkcellService {
    /**
     * 查找全部联动触发事件或者条件信息
     *
     * @return
     */
    public List<UclcLinkcell> findAll();

    /**
     * 查找单个联动触发事件或者条件信息
     *
     * @return
     */
    public UclcLinkcell findOne(int condid);

    /**
     * 添加触发事件或者条件信息
     *
     * @param obj
     * @return
     */
    public void save(UclcLinkcell obj);

    /**
     * 编辑触发事件或者条件信息
     *
     * @param obj
     * @return
     */
    public void edit(UclcLinkcell obj);

    /**
     * 删除单个规划绑定信息
     *
     * @param id 绑定信息的id
     * @return
     */
    public void del(int id);

    /**
     * 批量删除指定规划的绑定信息
     *
     * @param cellLinks
     */
    public void del(List<UclcLinkcell> cellLinks);

    public List<UclcLinkcell> findByLinkageid(long Linkageid);
}
