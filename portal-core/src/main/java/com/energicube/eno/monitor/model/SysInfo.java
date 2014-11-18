package com.energicube.eno.monitor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 系统信息
 */
@Entity
@Table(name = "SYSINFO")
public class SysInfo implements java.io.Serializable {

    private static final long serialVersionUID = -2061644237313023080L;
    private String syscode; // 系统编码
    private String sysname; // 系统名称
    private String title; // 显示标题
    private String description; // 系统描述
    private String shortcut; // 显示图标
    private String homeurl; // 主页URL
    private String logourl; // LOGO地址
    private String bodybg; // 页面背景
    private String loginbg; // 登陆页面背景图
    private String copyright; // 版权信息
    private String mqHost; // 消息服务器地址
    private String ftpHost; // FTP服务器地址
    private String status; // 系统状态 ACTIVE or INACTIVE
    private String manager; // 管理员姓名
    private String phone; // 管理员电话
    private String managertel; // 移动电话
    private String businesstype; // 业务类型
    private String portalmap;//首页总览地图


    public SysInfo() {
    }

    public SysInfo(String syscode, String sysname, String title) {
        this.syscode = syscode;
        this.sysname = sysname;
        this.title = title;
    }

    public SysInfo(String syscode, String sysname, String title,
                   String description, String shortcut, String homeurl,
                   String logourl, String bodybg, String loginbg, String copyright,
                   String mqHost, String ftpHost, String status, String manager,
                   String phone, String managertel, String businesstype, String portalmap) {
        this.syscode = syscode;
        this.sysname = sysname;
        this.title = title;
        this.description = description;
        this.shortcut = shortcut;
        this.homeurl = homeurl;
        this.logourl = logourl;
        this.bodybg = bodybg;
        this.loginbg = loginbg;
        this.copyright = copyright;
        this.mqHost = mqHost;
        this.ftpHost = ftpHost;
        this.status = status;
        this.manager = manager;
        this.phone = phone;
        this.managertel = managertel;
        this.businesstype = businesstype;
        this.portalmap = portalmap;
    }

    @Id
    @Column(name = "SYSCODE", unique = true, nullable = false, length = 20)
    public String getSyscode() {
        return this.syscode;
    }

    public void setSyscode(String syscode) {
        this.syscode = syscode;
    }

    @Column(name = "SYSNAME", nullable = false, length = 100)
    public String getSysname() {
        return this.sysname;
    }

    public void setSysname(String sysname) {
        this.sysname = sysname;
    }

    @Column(name = "TITLE", nullable = false, length = 100)
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "SHORTCUT", length = 100)
    public String getShortcut() {
        return this.shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    @Column(name = "HOMEURL", length = 100)
    public String getHomeurl() {
        return this.homeurl;
    }

    public void setHomeurl(String homeurl) {
        this.homeurl = homeurl;
    }

    @Column(name = "LOGOURL")
    public String getLogourl() {
        return this.logourl;
    }

    public void setLogourl(String logourl) {
        this.logourl = logourl;
    }

    @Column(name = "BODYBG")
    public String getBodybg() {
        return this.bodybg;
    }

    public void setBodybg(String bodybg) {
        this.bodybg = bodybg;
    }

    @Column(name = "LOGINBG")
    public String getLoginbg() {
        return this.loginbg;
    }

    public void setLoginbg(String loginbg) {
        this.loginbg = loginbg;
    }

    @Column(name = "COPYRIGHT")
    public String getCopyright() {
        return this.copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    @Column(name = "MQHOST", length = 100)
    public String getMqHost() {
        return mqHost;
    }

    public void setMqHost(String mqHost) {
        this.mqHost = mqHost;
    }

    @Column(name = "FTPHOST", length = 100)
    public String getFtpHost() {
        return ftpHost;
    }

    public void setFtpHost(String ftpHost) {
        this.ftpHost = ftpHost;
    }

    @Column(name = "STATUS", length = 20)
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "MANAGER", length = 20)
    public String getManager() {
        return this.manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    @Column(name = "PHONE", length = 20)
    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "MANAGERTEL", length = 20)
    public String getManagertel() {
        return this.managertel;
    }

    public void setManagertel(String managertel) {
        this.managertel = managertel;
    }

    @Column(name = "BUSINESSTYPE", length = 20)
    public String getBusinesstype() {
        return this.businesstype;
    }

    public void setBusinesstype(String businesstype) {
        this.businesstype = businesstype;
    }

    @Column(name = "PORTALMAP", length = 100)
    public String getPortalmap() {
        return portalmap;
    }

    public void setPortalmap(String portalmap) {
        this.portalmap = portalmap;
    }

}
