package com.energicube.eno.monitor.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;

/**
 *
 */
@Entity
@Table(name = "USERS", schema = "zclfsys")
public class Users_bak implements java.io.Serializable {

    private static final long serialVersionUID = 6864670836615506994L;
    private long useruid;
    private String loginid;
    private String pwd;
    private String memo;
    private String defsite;
    private Boolean querywithdefsite;
    private String status;
    private String type;
    private Boolean issysuser = false;
    private String userid;
    private String clientip;
    private Date lastactivity;
    /**
     * 用户分类，取“通用数据字典 USERCATEGORY”,如一般用户、技术用户、管理员
     */
    private String category;

    public Users_bak() {
    }

    public Users_bak(long useruid) {
        this.useruid = useruid;
    }

    public Users_bak(long useruid, String loginid, String pwd, String memo,
                 String defsite, Boolean querywithdefsite, String status,
                 String type, Boolean issysuser, String userid, String clientip,
                 Date lastactivity) {
        this.useruid = useruid;
        this.loginid = loginid;
        this.pwd = pwd;
        this.memo = memo;
        this.defsite = defsite;
        this.querywithdefsite = querywithdefsite;
        this.status = status;
        this.type = type;
        this.issysuser = issysuser;
        this.userid = userid;
        this.clientip = clientip;
        this.lastactivity = lastactivity;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USERUID", unique = true, nullable = false)
    public long getUseruid() {
        return this.useruid;
    }

    public void setUseruid(long useruid) {
        this.useruid = useruid;
    }

    @NotEmpty
    @Column(name = "LOGINID", length = 50)
    public String getLoginid() {
        return this.loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    @Column(name = "PWD", length = 128)
    public String getPwd() {
        return this.pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    @Column(name = "MEMO", length = 256)
    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Column(name = "DEFSITE", length = 8)
    public String getDefsite() {
        return this.defsite;
    }

    public void setDefsite(String defsite) {
        this.defsite = defsite;
    }

    @Column(name = "QUERYWITHDEFSITE")
    public Boolean getQuerywithdefsite() {
        return this.querywithdefsite;
    }

    public void setQuerywithdefsite(Boolean querywithdefsite) {
        this.querywithdefsite = querywithdefsite;
    }

    @Column(name = "STATUS", length = 12)
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "TYPE", length = 30)
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "ISSYSUSER")
    public Boolean getIssysuser() {
        return this.issysuser;
    }

    public void setIssysuser(Boolean issysuser) {
        this.issysuser = issysuser;
    }

    @NotEmpty
    @Column(name = "USERID", length = 50)
    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Column(name = "CLIENTIP", length = 100)
    public String getClientip() {
        return this.clientip;
    }

    public void setClientip(String clientip) {
        this.clientip = clientip;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LASTACTIVITY", length = 23)
    public Date getLastactivity() {
        return this.lastactivity;
    }

    public void setLastactivity(Date lastactivity) {
        this.lastactivity = lastactivity;
    }

    @Column(name = "CATEGORY", length = 50)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


}
