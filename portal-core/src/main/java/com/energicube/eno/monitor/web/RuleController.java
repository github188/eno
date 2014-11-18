package com.energicube.eno.monitor.web;

import com.energicube.eno.monitor.model.Rules;
import com.energicube.eno.monitor.repository.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * 功能模块管理
 *
 * @author ZHANGTAO
 */
@Controller
public class RuleController extends BaseController {

	/*@Autowired
    private RuleService ruleService;*/

    @Autowired
    private RuleRepository ruleRepository;

//	@Autowired
//	public RuleController(UserService userService) {
//		this.userService = userService;
//	}

    /**
     * @param model
     * @return 查询角色信息
     */

    @RequestMapping(value = "/rule/save", method = RequestMethod.GET)
    public String SelectFuncblock(Integer userGroupId, Integer[] funId, Model model) {
        List<Rules> list = ruleRepository.selectRulesByUserGroupId(userGroupId);
        if (list != null && list.size() > 0) {
            ruleRepository.delete(list);
        }
        if (userGroupId != null && funId != null && funId.length > 0) {
            List<Rules> listRules = new ArrayList<Rules>();
            for (Integer fun : funId) {
                Rules rule = new Rules();
                rule.setGroupid(userGroupId);
                rule.setFuncid(fun);
                listRules.add(rule);
            }
            ruleRepository.save(listRules);
        }
        return "redirect:/funcblock/load/index?userGroupId=" + userGroupId + "&t=" + Math.floor(Math.random() * (1000000));
    }

}
