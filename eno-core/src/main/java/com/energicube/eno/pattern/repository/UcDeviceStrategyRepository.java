package com.energicube.eno.pattern.repository;

import com.energicube.eno.pattern.model.UcDeviceStrategy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-8-8
 * Time: 下午12:36
 * To change this template use File | Settings | File Templates.
 */
public interface UcDeviceStrategyRepository extends JpaRepository<UcDeviceStrategy, String> {

    /**
     * 查询设备
     *
     * @param systemId
     * @return
     */
    public List<UcDeviceStrategy> findBySystemId(String systemId);
}
