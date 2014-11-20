package com.energicube.eno.pattern.repository;

import com.energicube.eno.pattern.model.UcDeviceGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-8-21
 * Time: 下午4:47
 * To change this template use File | Settings | File Templates.
 */
public interface UcDeviceGroupRepository extends JpaRepository<UcDeviceGroup, String> {

    /**
     * 查询子系统的设备组
     *
     * @param systemId 子系统
     * @return 设备组
     */
    public List<UcDeviceGroup> findByUcDeviceSystem_Id(String systemId);
}
