package com.energicube.eno.monitor.repository;


import com.energicube.eno.monitor.model.NetvideoDvrcfg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * DVR配置数据库操作接口
 */
public interface NetvideoDvrcfgRepository extends
        JpaRepository<NetvideoDvrcfg, Integer> {
    /**
     * 返回DVR配置信息中最大显示顺序的值
     *
     * @return 显示顺序最大值
     */
    @Query("select max(dvrsequence) from NetvideoDvrcfg")
    public Integer findMaxIndex();
}
