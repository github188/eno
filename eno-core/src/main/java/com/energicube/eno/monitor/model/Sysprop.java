package com.energicube.eno.monitor.model;


import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;

/**
 * 系统属性
 * <p/>
 * 主要用于配置管理整个系统的属性设置。每条记录表示一个属性
 */
@Entity
@Table(name = "SYSPROP")
public class Sysprop implements java.io.Serializable {

    private static final long serialVersionUID = -1044011168570784741L;
    private int propid;
    private String propname;    //属性名称   具有唯一值
    private String description;    //属性描述
    private String sysdefault;    //系统默认值
    private String propvalue;    //当前值
    private int nullsallowed;    //允许空值
    private int encrypted;        //是否加密
    private int masked;            //被屏蔽?
    private String securelevel;    //权限级别
    private String changeby;    //修改人
    private Date changedate;    //修改日期


    public Sysprop() {
        super();
    }

    public Sysprop(int propid, String propname, String description,
                   String sysdefault, String propvalue, int nullsallowed,
                   int encrypted, int masked) {
        this.propid = propid;
        this.propname = propname;
        this.description = description;
        this.sysdefault = sysdefault;
        this.propvalue = propvalue;
        this.nullsallowed = nullsallowed;
        this.encrypted = encrypted;
        this.masked = masked;
    }

    public Sysprop(int propid, String propname, String description,
                   String sysdefault, String propvalue, int nullsallowed,
                   int encrypted, int masked, String securelevel, String changeby,
                   Date changedate) {
        this.propid = propid;
        this.propname = propname;
        this.description = description;
        this.sysdefault = sysdefault;
        this.propvalue = propvalue;
        this.nullsallowed = nullsallowed;
        this.encrypted = encrypted;
        this.masked = masked;
        this.securelevel = securelevel;
        this.changeby = changeby;
        this.changedate = changedate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PROPID", unique = true, nullable = false)
    public int getPropid() {
        return this.propid;
    }

    public void setPropid(int propid) {
        this.propid = propid;
    }

    @NotEmpty
    @Column(name = "PROPNAME", nullable = false, length = 50)
    public String getPropname() {
        return this.propname;
    }

    public void setPropname(String propname) {
        this.propname = propname;
    }

    @NotEmpty
    @Column(name = "DESCRIPTION", nullable = false, length = 100)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotEmpty
    @Column(name = "SYSDEFAULT", nullable = false, length = 512)
    public String getSysdefault() {
        return this.sysdefault;
    }

    public void setSysdefault(String sysdefault) {
        this.sysdefault = sysdefault;
    }

    @Column(name = "PROPVALUE", nullable = false, length = 512)
    public String getPropvalue() {
        return this.propvalue;
    }

    public void setPropvalue(String propvalue) {
        this.propvalue = propvalue;
    }

    @Column(name = "NULLSALLOWED", nullable = false)
    public int getNullsallowed() {
        return this.nullsallowed;
    }

    public void setNullsallowed(int nullsallowed) {
        this.nullsallowed = nullsallowed;
    }

    @Column(name = "ENCRYPTED", nullable = false)
    public int getEncrypted() {
        return this.encrypted;
    }

    public void setEncrypted(int encrypted) {
        this.encrypted = encrypted;
    }

    @Column(name = "MASKED", nullable = false)
    public int getMasked() {
        return this.masked;
    }

    public void setMasked(int masked) {
        this.masked = masked;
    }

    @Column(name = "SECURELEVEL", length = 20)
    public String getSecurelevel() {
        return this.securelevel;
    }

    public void setSecurelevel(String securelevel) {
        this.securelevel = securelevel;
    }

    @Column(name = "CHANGEBY", length = 30)
    public String getChangeby() {
        return this.changeby;
    }

    public void setChangeby(String changeby) {
        this.changeby = changeby;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CHANGEDATE", length = 23)
    public Date getChangedate() {
        return this.changedate;
    }

    public void setChangedate(Date changedate) {
        this.changedate = changedate;
    }

}
