package com.energicube.eno.monitor.model;

/**
 * 用户基本信息表 <br />
 * 用户登陆界面之用户列表
 */
public class UserInfo implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 登陆名
     */
    private String loginid;
    /**
     * 登陆密码
     */
    private String pwd;
    /**
     * 用户状态
     */
    private String status;
    /**
     * 用户全名
     */
    private String fullname;
    /**
     * 用户类别
     */
    private String category;
    /**
     * 用户头像
     */
    private String userface;

    /**
     * 用户表里的userID,在控制模式的权限的时候用到
     */
    private String userId;

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getUserface() {
        return userface;
    }

    public void setUserface(String userface) {
        this.userface = userface;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return loginid;
    }
}
