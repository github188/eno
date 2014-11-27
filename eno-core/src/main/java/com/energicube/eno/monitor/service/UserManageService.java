package com.energicube.eno.monitor.service;

import java.util.List;

import com.energicube.eno.monitor.model.User;

/**
 * All rights Reserved, Designed By ZCLF
 * Copyright:  Copyright(C) 2013-2014
 * Company  ZCLF Energy Technologies Inc.
 *
 * @author 王雪亮
 * @version 1.0
 * @date 2014年11月26日
 * @Description 用户管理
 */
public interface UserManageService {

    /**
     * 获取所有用户信息列表
     */
    public List<User> findUserList();
    
    /**
     * 获取单个用户信息列表
     */
    public User findUserid(String userid);
    
    /**
     * 用户管理用户新建
     */
    public String saveUser(User user);
    
    /**
     * 用户管理用户修改
     */
    public String updateUser(User user);
    
    /**
     * 用户管理用户删除
     */
    public String delUser(String userId);
   
}
