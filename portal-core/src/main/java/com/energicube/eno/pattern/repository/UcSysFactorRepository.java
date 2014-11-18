package com.energicube.eno.pattern.repository;


import com.energicube.eno.pattern.model.UcSysFactor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-7-11
 * Time: 上午11:30
 * To change this template use File | Settings | File Templates.
 */
public interface UcSysFactorRepository extends JpaRepository<UcSysFactor, String> {


    /**
     * 查询某子系统的影响因素
     *
     * @param systemId 子系统ID
     * @return
     */
    public List<UcSysFactor> findBySystemId(String systemId);
}
