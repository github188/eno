package com.energicube.eno.pattern.repository;


import com.energicube.eno.pattern.model.UcPatternFactor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-7-11
 * Time: 上午11:29
 * To change this template use File | Settings | File Templates.
 */
public interface UcPatternFactorRepository extends JpaRepository<UcPatternFactor, String> {


    /**
     * 查询模式的影响因素
     *
     * @param id 模式ID
     * @return
     */
    public List<UcPatternFactor> findByUcPattern_Id(String id);
}
