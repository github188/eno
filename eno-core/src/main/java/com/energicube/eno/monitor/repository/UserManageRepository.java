package com.energicube.eno.monitor.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

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
public interface UserManageRepository extends JpaRepository<User, Long> {

    
    @Query(" delete from User u where u.userid=?1")
    public List<User> delUser(String userid) throws DataAccessException;
    
    @Query(" select u from User u where u.userid=?1")
    public User findUserid(String userid) throws DataAccessException;
}