package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.Sysprop;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 系统属性设置数据库操作接口类
 *
 * @version 1.0
 */
public interface SyspropRepository extends JpaRepository<Sysprop, Integer> {

    /**
     * 返回指定属性名称的条目列表
     *
     * @param propname 属性名称
     * @return 属性列表
     */
    List<Sysprop> findByPropname(String propname) throws DataAccessException;


}
