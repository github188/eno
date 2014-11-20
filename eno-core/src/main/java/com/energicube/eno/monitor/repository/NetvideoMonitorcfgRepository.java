package com.energicube.eno.monitor.repository;


import com.energicube.eno.monitor.model.NetvideoMonitorcfg;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 监视器配置数据库操作接口
 */
public interface NetvideoMonitorcfgRepository extends
        JpaRepository<NetvideoMonitorcfg, Integer> {

}
