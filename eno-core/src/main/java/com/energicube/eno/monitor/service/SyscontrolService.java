package com.energicube.eno.monitor.service;

import com.energicube.eno.monitor.model.Syscontrol;

import java.util.List;

/**
 * 系统组件业务操作接口
 */
public interface SyscontrolService {

    /**
     * 获取指定主键ID的组件对象
     */
    Syscontrol findOne(int controluid);


    /**
     * 删除指定的组件
     *
     * @param controluid 组件ID
     */
    boolean deleteSyscontrol(int controluid);

    /**
     * 持久化组件对象
     *
     * @param syscontrol 组件对象
     */
    Syscontrol saveSyscontrol(Syscontrol syscontrol);

    /**
     * 获取所有组件对象
     *
     * @return 组件对象列表
     */
    List<Syscontrol> findAll();


}
