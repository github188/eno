package com.energicube.eno.monitor.service;

import com.energicube.eno.monitor.model.UclcAction;

import java.util.List;

public interface UclcActionService {
    /**
     * 查找全部动作信息
     *
     * @return
     */
    public List<UclcAction> findAll();

    /**
     * 根据动作类别查找动作信息
     *
     * @param actiontype
     * @return
     */
    public List<UclcAction> findByActiontype(int actiontype);

    /**
     * 根据id，查找单个动作信息
     *
     * @return
     */
    public UclcAction findOne(int condid);

    /**
     * 保存动作信息
     *
     * @param obj
     * @return
     */
    public void save(UclcAction obj);

    /**
     * 编辑触发事件或者条件信息
     *
     * @param obj
     * @return
     */
    public void edit(UclcAction obj);

    /**
     * 删除指定动作
     *
     * @param obj
     * @return
     */
    public void del(int id);

    /**
     * 批量删除动作
     *
     * @param obj
     */
    public void del(Iterable<UclcAction> obj);

    /**
     * 根据预案id，查找该预案下的所有动作信息
     *
     * @param cellid
     * @return
     */
    public List<UclcAction> findByCellid(int cellid);
}
