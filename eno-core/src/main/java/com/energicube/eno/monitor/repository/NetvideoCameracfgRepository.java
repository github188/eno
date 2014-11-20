package com.energicube.eno.monitor.repository;


import com.energicube.eno.monitor.model.NetvideoCameracfg;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 摄像机数据库操作接口
 */
public interface NetvideoCameracfgRepository extends
        JpaRepository<NetvideoCameracfg, Integer> {

}
