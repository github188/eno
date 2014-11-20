package com.energicube.eno.pattern.repository;


import com.energicube.eno.pattern.model.UcPatternAction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-7-11
 * Time: 上午11:29
 * To change this template use File | Settings | File Templates.
 */
public interface UcPatternActionRepository extends JpaRepository<UcPatternAction, String> {

    /**
     * 查询时间列表
     *
     * @param id 模式ID
     * @return
     */
    public List<UcPatternAction> findByUcPattern_Id(String id);
}
