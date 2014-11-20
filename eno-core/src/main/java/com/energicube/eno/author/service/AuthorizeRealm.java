package com.energicube.eno.author.service;

import com.energicube.eno.common.Constants;
import com.energicube.eno.monitor.model.AppAuth;
import com.energicube.eno.monitor.model.GroupMember;
import com.energicube.eno.monitor.model.UserInfo;
import com.energicube.eno.monitor.model.Users;
import com.energicube.eno.monitor.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * All rights Reserved, Designed By ZCLF
 * Copyright:    Copyright(C) 2013-2014
 * Company   ZCLF Energy Technologies Inc.
 *
 * @author 刘广路
 * @version 1.0
 * @date 2014/10/26 18:59
 * @Description 用户授权
 */
public class AuthorizeRealm extends AuthorizingRealm {

    private static final Log logger = LogFactory.getLog(AuthorizeRealm.class);

    @Resource
    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     *
     * @param principalCollection 认证的信息
     * @return 权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        if (principalCollection == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
        UserInfo userInfo=(UserInfo)principalCollection.getPrimaryPrincipal();

        logger.info("授权：" + principalCollection.getRealmNames() + "========username==" + userInfo.getLoginid());

        Users users = userService.findUsersByLoginid(userInfo.getLoginid());
        //角色
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        List<AppAuth> appAuths = new ArrayList<AppAuth>();
        List<GroupMember> GroupMembers = userService.findGroupMemberByUserid(users.getUserid());
        for (int j = 0; j < GroupMembers.size(); j++) {
            //基于用户名的角色信息
            info.addRole(GroupMembers.get(j).getGroupname());
            List<AppAuth> appAuthtemp = userService.findAppauthByGroupname(GroupMembers.get(j).getGroupname());
            appAuths.addAll(appAuthtemp);
        }

        //菜单
        Set<String> appauth = new HashSet<>();
        for (int i = 0; i < appAuths.size(); i++) {
            appauth.add(appAuths.get(i).getAppcode());
        }
        //基于角色的权限信息
        info.setStringPermissions(appauth);

        return info;
    }

    /**
     * 认证用户
     *
     * @param authenticationToken 登录的用户信息
     * @return 认证信息
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) authenticationToken;
        String username = upToken.getUsername();

        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        }
        //处理密码
        String password = String.valueOf(upToken.getPassword());
        String md = Constants.getMdPassword(password);
        logger.info(md + "-------------username:" + username + "[---password---]" + password);
        //验证用户
        Users users = userService.checkUserLogin(username, md);

        if (users==null || users.getUseruid()<=0) {
            throw new AccountException("account error...");
        }

        logger.info("-----is check true--------" + getName());
        //重新包装用户信息
        UserInfo userInfo = new UserInfo();
        userInfo.setLoginid(username);
        userInfo.setPwd(md);
        userInfo.setStatus(users.getStatus());
        userInfo.setUserId(users.getUserid());
        AuthenticationInfo info = new SimpleAuthenticationInfo(userInfo,  md, getName());
        return info;

    }

}
