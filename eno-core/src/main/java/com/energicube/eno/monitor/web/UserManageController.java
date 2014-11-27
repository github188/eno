package com.energicube.eno.monitor.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.energicube.eno.monitor.model.User;
import com.energicube.eno.monitor.service.UserManageService;


/**
 * All rights Reserved, Designed By ZCLF
 * Copyright:  Copyright(C) 2013-2014
 * Company  ZCLF Energy Technologies Inc.
 *
 * @author 王雪亮
 * @version 1.0
 * @date 2014年11月26日
 * @Description 用户管理相关方法
 */
@Controller
@RequestMapping("/user")
public class UserManageController extends BaseController {

    private Log logger = LogFactory.getLog(UserManageController.class);

    private UserManageService userManageService;

	/**
     * 用户管理用户查询
     * @return 
     */
    @RequestMapping(value = "/findUserList", method = RequestMethod.GET)
    @ResponseBody
    public List<User> findUserList() {
    	List<User> userList = new ArrayList<User>();
    	userList = userManageService.findUserList();
        	
    	return userList;
    }
    
    /**
     * 用户管理用户新建
     *
     * @param user
     * @return 
     */
    @RequestMapping(value = "/saveUser", method = RequestMethod.GET)
    @ResponseBody
    public String saveUser( HttpServletRequest request) {
    	//request.getParameter("firstname");
    	User user=new User();
    	userManageService.saveUser(user);
    	return "";
    }

    /**
     * 修改时单个用户查询
     *
     * @return 
     */
    @RequestMapping(value = "/findUserId", method = RequestMethod.GET)
    @ResponseBody
    public User findUserId( HttpServletRequest request) {
    	User user=new User();
    	user = userManageService.findUserid("userid");
    	return user;
    }
    /**
     * 用户管理用户修改
     *
     * @param user
     * @return 
     */
    @RequestMapping(value = "/updateUser", method = RequestMethod.GET)
    @ResponseBody
    public String updateUser( HttpServletRequest request) {
    	User user=new User();
    	userManageService.updateUser(user);
        return "";
    }
    
    /**
     * 用户管理用户删除
     *
     * @param user
     * @return 
     */
    @RequestMapping(value = "/delUser", method = RequestMethod.GET)
    @ResponseBody
    public List<User> delUser( HttpServletRequest request) {
    	userManageService.delUser(request.getParameter("userid"));
    	List<User> userList = new ArrayList<User>();
    	userList=this.findUserList();
        return userList;
    }
}
