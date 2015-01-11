package com.energicube.eno.monitor.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.energicube.eno.monitor.model.UcPassengerFlowShopConfig;

import java.util.List;

/**
 * 客流的存储接口
 * Created by LiuGuanglu
 * 2014/5/23.
 */
public interface UcPassengerFlowShopConfigRepository extends JpaRepository<UcPassengerFlowShopConfig, String> {

    /**
     * 根据是否激活查询所有店铺
     * @param action 激活 Y--是 N--否
     * @return 店铺集合
     */
    public List<UcPassengerFlowShopConfig> findByAction(String action);
}
