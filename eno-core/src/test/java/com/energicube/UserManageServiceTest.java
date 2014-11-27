package com.energicube;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.energicube.eno.monitor.model.User;
import com.energicube.eno.monitor.service.UserManageService;
import com.energicube.eno.monitor.web.UserManageController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * All rights Reserved, Designed By ZCLF
 * Copyright:  Copyright(C) 2013-2014
 * Company  ZCLF Energy Technologies Inc.
 *
 * @author 王雪亮
 * @version 1.0
 * @date 2014年11月27日
 * @Description 用户管理相关测试方法
 */
@ContextConfiguration(locations = {"classpath:spring/business-config.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserManageServiceTest {

	 private Log logger = LogFactory.getLog(UserManageController.class);

    @Autowired
    private UserManageService userManageService;


    /**
     * 用户管理用户新建
     *
     * @param user
     * @return 
     */
    @Test
    @Rollback(value = true)
    public void saveUser(){
    	User user= new User();
    	user.setUserid("1");
    	user.setFirstname("张三");
    	user.setDepartment("adsfasdf");
    	user.setSex("男");
    	user.setBirthday(new Date());
    	user.setWorkdate(new Date());
    	user.setPhone("12123123");
        userManageService.saveUser(user);
    }

    /**
     * 用户管理用户查询
     */
    @Test
    @Rollback(value = true)
    public void findUserList(){
    	List<User> userList = new ArrayList<User>();
    	userList = userManageService.findUserList();
        if (userList!=null ){
            for (User user:userList){
                logger.info(user.getUserid());
            }
        }
    }

    /**
     * 用户管理单个用户查询
     *//*
    @Test
    @Rollback(value = true)
    public void findUserid(){
    	User user=new User();
    	user = userManageService.findUserid("userid");
        if (user!=null ){
            logger.info(user.getUserid());
        }
    }*/

    /**
     * 用户管理单个用户修改
     *
     * @param user
     * @return 
     */
    @Test
    @Rollback(value = true)
    public void updateUser(){
    	User user= new User();
    	user.setUserid("1");
    	user.setFirstname("张三");
    	user.setDepartment("adsfasdf");
    	user.setSex("男");
    	user.setBirthday(new Date());
    	user.setWorkdate(new Date());
    	user.setPhone("12123123");
        userManageService.updateUser(user);
    }
    
    /**
     * 删除事件
     */
    @Test
    @Rollback(value = true)
    public void  delUser(){
    	userManageService.delUser("1");
    }

}
