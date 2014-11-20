package com.energicube.eno.monitor.service;

import com.energicube.eno.monitor.model.Rules;

import java.util.List;

/**
 * 权限操作接口
 */
public interface RuleService {

    public List<Integer> selectFuncIdByUserGroupId(Integer userGroupId);

    public List<Rules> selectRulesByUserGroupId(Integer userGroupId);
}
