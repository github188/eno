package com.energicube.eno.monitor.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.energicube.eno.monitor.model.User;
import com.energicube.eno.monitor.repository.UserManageRepository;
import com.energicube.eno.monitor.service.UserManageService;

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
@Service
public class UserManageServiceImpl implements UserManageService {

    private final static Log logger = LogFactory.getLog(UserManageServiceImpl.class);

    private UserManageRepository userManageRepository;
    
    /**
     * 获取所有用户信息列表
     */
    public List<User> findUserList() {
        List<User> userList = new ArrayList<User>();
        userList = userManageRepository.findAll();
        return userList;
    }
    
    /**
     * 用户管理用户新建
     */
    public String saveUser(User user){
    	userManageRepository.save(user);
    	return "";
    }
    
    /**
     * 用户管理用户修改
     */
    public String updateUser(User user){
    	userManageRepository.saveAndFlush(user);
    	return "";
    }
    
    /**
     * 用户管理用户删除
     */
    public String delUser(String userId){
    	userManageRepository.delUser(userId);
    	return "";
    }
   
}
