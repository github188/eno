package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.OkcModule;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 系统模块定义数据操作类
 *
 * @author CHENPING
 */
public interface OkcModulesRepository extends JpaRepository<OkcModule, Long> {

}
