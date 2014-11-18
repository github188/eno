package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.PanelConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


/**
 * 面板相关处理接口
 *
 * @author shangpeibao
 */
public interface PanelConfigRepository extends JpaRepository<PanelConfig, Long> {

    @Transactional
    @Modifying
    @Query("delete from PanelConfig where classstructureid = ?1")
    void deleteAllPanelConfigs(String classstructureid);
}
