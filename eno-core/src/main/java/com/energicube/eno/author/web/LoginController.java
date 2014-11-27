package com.energicube.eno.author.web;

import com.energicube.eno.monitor.model.*;
import com.energicube.eno.monitor.repository.GroupMemberRepository;
import com.energicube.eno.monitor.repository.OkcGroupRepository;
import com.energicube.eno.monitor.service.DictionaryService;
import com.energicube.eno.monitor.service.SyspropService;
import com.energicube.eno.monitor.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By ZCLF
 * Copyright:    Copyright(C) 2013-2014
 * Company   ZCLF Energy Technologies Inc.
 *
 * @author 刘广路
 * @version 1.0
 * @date 2014/10/27 22:12
 * @Description 用户登录控制
 */
@Controller
public class LoginController {

    private static final Log logger = LogFactory.getLog(LoginController.class);


    @Autowired
    private UserService userService;

    @Autowired
    private SyspropService sysPropService;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private OkcGroupRepository okcGroupRepository;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    /**
     * 用户类别
     */
    @ModelAttribute("userclasses")
    public List<OkcDMAlphaNum> GetUserClass(HttpServletRequest request) {
        // 用户类型,取字典表
        return dictionaryService.findALNDictionaryByDictid("USERCATEGORY");
    }

    @RequestMapping(value = "/login/findUserListByDmValue", method = RequestMethod.GET)
    public
    @ResponseBody
    List<LoginUser> findUserListByDmValue(HttpServletRequest request,
                                          HttpServletResponse response, Model model) {
        List<OkcGroup> okcGroups = new ArrayList<OkcGroup>();
        List<GroupMember> groupMembersAll = new ArrayList<GroupMember>();
        List<LoginUser> loginUsers = new ArrayList<LoginUser>();
        try {
            String dmValue = request.getParameter("DmValue");
            okcGroups = okcGroupRepository.findByLangcode(dmValue);
            for (int i = 0; i < okcGroups.size(); i++) {
                OkcGroup okcGroup = okcGroups.get(i);
                List<GroupMember> groupMembers = new ArrayList<GroupMember>();
                groupMembers = groupMemberRepository.findByGroupname(okcGroup.getGroupname());
                for (int j = 0; j < groupMembers.size(); j++) {
                    groupMembersAll.add(groupMembers.get(j));
                }
            }
            for (int k = 0; k < groupMembersAll.size(); k++) {
                List<Users> UsersList = new ArrayList<Users>();
                UsersList = userService.findByUserid(groupMembersAll.get(k).getUserid());
                LoginUser loginUser = new LoginUser();
                if (UsersList.get(0).getStatus().equals("0")) { //  && UsersList.get(0).getType().equals("1")
                    loginUser.setName(UsersList.get(0).getLoginid());
                    loginUser.setValue(UsersList.get(0).getLoginid());
                    if (userService.findPersonsByUserid(UsersList.get(0).getUserid()).getSex().equals("0")) {
                        loginUser.setImg("user0.png");
                    } else {
                        loginUser.setImg("user1.png");
                    }
                    loginUsers.add(loginUser);
                }
            }
        } catch (Exception e) {
            logger.info("-----", e);
        }
        return loginUsers;

    }

    /**
     * 判断用户是否登录
     *
     * @param loginUserid 用户登录名
     * @param loginPwd    密码
     * @return
     */
    @RequestMapping(value = "/login/check", method = RequestMethod.POST)
    public Object checkLogin(@RequestParam("username") String loginUserid,
                             @RequestParam("password") String loginPwd) {
        Subject user = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(loginUserid, loginPwd);
        token.setRememberMe(true);
        try {
            user.login(token);
            return "redirect:/mctrl";
        } catch (Exception e) {
            logger.error("登录失败错误信息:", e);
            token.clear();
            return "redirect:/login";
        }
    }

    /**
     * 用户登陆页
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String initUserTypePage(Model model) {
        return "login/loginForm";
    }

    /**
     * 用户退出
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(Model model) {
        Subject user = SecurityUtils.getSubject();
        user.logout();
        return "login/loginForm";
    }
}
