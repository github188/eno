package com.energicube.eno.monitor.service;

import com.energicube.eno.monitor.model.Rules;
import com.energicube.eno.monitor.repository.RuleRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RuleServiceImpl implements RuleService {

    private final static Log logger = LogFactory.getLog(RuleServiceImpl.class);

    @Autowired
    private RuleRepository ruleRepository;

    public List<Integer> selectFuncIdByUserGroupId(Integer userGroupId) {
        return ruleRepository.selectFuncIdByUserGroupId(userGroupId);
    }

    public List<Rules> selectRulesByUserGroupId(Integer userGroupId) {
        return ruleRepository.selectRulesByUserGroupId(userGroupId);
    }

}
