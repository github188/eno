package com.energicube.eno.monitor.service;

import com.energicube.eno.monitor.model.SysInfo;
import com.energicube.eno.monitor.model.Sysprop;

import java.util.List;

/**
 * 系统属性业务操作接口类
 *
 * @version 1.0
 */
public interface SyspropService {

    /**
     * 返回系统属性列表
     */
    public List<Sysprop> findAllSysprops();

    /**
     * 判断系统属性名称是否存在
     *
     * @param propname 属性名称
     * @return true || false
     */
    public boolean existSysprop(String propname);

    /**
     * 返回指定ID的系统属性
     *
     * @param propid 属性ID
     * @return {@link Sysprop}  系统属性
     */
    public Sysprop findSyspropById(int propid);

    /**
     * 返回指定名称的系统属性
     *
     * @param propname 属性名称
     * @return {@link Sysprop}  系统属性
     */
    public Sysprop findSyspropByName(String propname);

    /**
     * 持久化系统属性
     *
     * @param sysprop 系统属性
     * @return {@link Sysprop}  系统属性
     */
    public Sysprop saveSysprop(Sysprop sysprop);

    /**
     * 批量保存系统属性
     *
     * @param 系统属性列表
     * @return 系统属性列表
     */
    public List<Sysprop> saveSysprops(List<Sysprop> sysprops);

    /**
     * 删除指定的系统属性
     *
     * @param sysprop 系统属性
     */
    public void deleteSysprop(Sysprop sysprop);

    /**
     * 删除指定ID的系统属性
     *
     * @param propid 系统属性ID
     */
    public void deleteSysprop(int propid);

    /**
     * 显示系统属性
     */
    public SysInfo findSysInfo();

    /**
     * 修改系统属性
     */
    public void updateSysInfo(SysInfo sysInfo);
}
