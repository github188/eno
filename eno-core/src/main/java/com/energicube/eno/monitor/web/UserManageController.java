package com.energicube.eno.monitor.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
    private String firstname;
    
    public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	//新建用户信息
    private  User user=new User();
    
    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
     * 用户管理用户查询
     *
     * @param pageSize
     * @param model
     * @param request
     * @param response
     * @return 
     */
    @RequestMapping(value = "/findUserList", method = RequestMethod.GET)
    @ResponseBody
    public List<User> findUserList() {
    	List<User> userList = new ArrayList<User>();
    	//userList = userManageService.findUserList();
        	User user= new User();
        	user.setUserid("1");
        	user.setFirstname("张三");
        	user.setDepartment("adsfasdf");
        	user.setSex("男");
        	user.setBirthday(new Date());
        	user.setWorkdate(new Date());
        	user.setPhone("12123123");
        	userList.add(user);
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
    	request.getParameter("firstname");
    	//userManageService.saveUser(user);
    	return "";
    }

    /**
     * 修改时单个用户查询
     *
     * @return 
     */
    @RequestMapping(value = "/findUserId", method = RequestMethod.GET)
    @ResponseBody
    public User findUserId() {
    	User user= new User();
    	user.setUserid("1");
    	user.setFirstname("张三");
    	user.setDepartment("adsfasdf");
    	user.setSex("男");
    	user.setBirthday(new Date());
    	user.setWorkdate(new Date());
    	user.setPhone("12123123");
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
    public String updateUser(@Valid User user) {
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
    public List<User> delUser(String userId) {
    	userManageService.delUser(userId);
    	List<User> userList = new ArrayList<User>();
    	userList=this.findUserList();
        return userList;
    }
}
