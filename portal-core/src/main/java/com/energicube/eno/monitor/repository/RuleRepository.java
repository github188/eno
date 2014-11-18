package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.Rules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RuleRepository extends JpaRepository<Rules, Long> {

    /**
     * 查询功能模块id
     *
     * @param userGroupId 用户组
     * @return
     */

    @Query("select funcid FROM Rules where groupid = :userGroupId")
    public List<Integer> selectFuncIdByUserGroupId(@Param("userGroupId") Integer userGroupId);

    /**
     * @param userGroupId 用户组
     * @return
     */

    @Query("FROM Rules where groupid = :userGroupId")
    public List<Rules> selectRulesByUserGroupId(@Param("userGroupId") Integer userGroupId);

}
