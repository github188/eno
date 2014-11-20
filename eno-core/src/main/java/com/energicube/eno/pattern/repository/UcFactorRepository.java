package com.energicube.eno.pattern.repository;

import com.energicube.eno.pattern.model.UcFactor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-7-11
 * Time: 上午11:28
 * To change this template use File | Settings | File Templates.
 */
public interface UcFactorRepository extends JpaRepository<UcFactor, String> {


    /**
     * 查询某主因素下的所有因素
     *
     * @param parentId 父因素的ID
     * @return
     */
    public List<UcFactor> findByParentId(String parentId);
}
