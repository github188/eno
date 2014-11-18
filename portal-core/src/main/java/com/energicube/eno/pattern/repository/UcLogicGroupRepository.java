package com.energicube.eno.pattern.repository;


import com.energicube.eno.pattern.model.UcLogicGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-8-8
 * Time: 上午11:29
 * To change this template use File | Settings | File Templates.
 */
public interface UcLogicGroupRepository extends JpaRepository<UcLogicGroup, String> {

    /**
     * 通过父ID查询子逻辑组
     *
     * @param parentID 父ID
     * @return
     */
    public java.util.List<UcLogicGroup> findByParentId(String parentID);


    /**
     * 查询某子系统的逻辑组
     *
     * @param systemId 子系统ID
     * @return 逻辑组集合
     */
    public List<UcLogicGroup> findBySystemId(String systemId);
}
