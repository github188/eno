package com.energicube.eno.pattern.repository;


import com.energicube.eno.pattern.model.UcStrategyItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-8-8
 * Time: 上午11:24
 * To change this template use File | Settings | File Templates.
 */
public interface UcStrategyItemRepository extends JpaRepository<UcStrategyItem, String> {

    /**
     * 通过父ID查询策略的项目
     *
     * @param parentId 父ID
     * @return
     */
    public List<UcStrategyItem> findByParentId(String parentId);
}
