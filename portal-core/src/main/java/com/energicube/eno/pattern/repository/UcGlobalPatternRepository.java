package com.energicube.eno.pattern.repository;

import com.energicube.eno.pattern.model.UcGlobalPattern;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-8-8
 * Time: 下午12:34
 * To change this template use File | Settings | File Templates.
 */
public interface UcGlobalPatternRepository extends JpaRepository<UcGlobalPattern, String> {


    /**
     * 查询按某
     *
     * @param orderType 时序类型
     * @return
     */
    public List<UcGlobalPattern> findByOrderType(String orderType);
}
