package com.energicube.eno.pattern.repository;

import com.energicube.eno.pattern.model.UcDeviceSystem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-8-21
 * Time: 下午4:43
 * To change this template use File | Settings | File Templates.
 */
public interface UcDeviceSystemRepository extends JpaRepository<UcDeviceSystem, String> {


    /**
     * 查询哪些子系统需要运行模式
     *
     * @param isRunPattern 是否需要运行 Y--是  N--不是
     * @return
     */
    public List<UcDeviceSystem> findByIsRunPattern(String isRunPattern);

    /**
     * 通过父ID查询子系统
     *
     * @param parentId 父ID
     * @return
     */
    public List<UcDeviceSystem> findByParentId(String parentId);

    /**
     * 通过编码体系查询子系统
     *
     * @param systemClass 设备分类的编码
     * @return
     */
    public List<UcDeviceSystem> findBySystemClass(String systemClass);

    /**
     * 通过编码体系查询子系统
     *
     * @param systemCode 子系统的编码
     * @return
     */
    public UcDeviceSystem findBySystemCode(String systemCode);

}
