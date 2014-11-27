package com.energicube.eno.monitor.model;

import javax.persistence.*;
import java.util.Date;

/**
 * 组织机构信息
 */
@Entity
@Table(name = "ORGANIZATION", schema = "zclfsys")
public class Organization implements java.io.Serializable {

    private static final long serialVersionUID = -2271859789905610629L;
    private long organizationid;
    private Boolean active;
    private String basecurrency1;
    private String basecurrency2;
    private String clearingacct;
    private String description;
    private String enterby;
    private Date enterdate;
    private String langcode;
    private String orgid;

    public Organization() {
    }

    public Organization(long organizationid) {
        this.organizationid = organizationid;
    }

    public Organization(long organizationid, Boolean active,
                        String basecurrency1, String basecurrency2, String clearingacct,
                        String description, String enterby, Date enterdate,
                        String langcode, String orgid) {
        this.organizationid = organizationid;
        this.active = active;
        this.basecurrency1 = basecurrency1;
        this.basecurrency2 = basecurrency2;
        this.clearingacct = clearingacct;
        this.description = description;
        this.enterby = enterby;
        this.enterdate = enterdate;
        this.langcode = langcode;
        this.orgid = orgid;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORGANIZATIONID", unique = true, nullable = false)
    public long getOrganizationid() {
        return this.organizationid;
    }

    public void setOrganizationid(long organizationid) {
        this.organizationid = organizationid;
    }

    @Column(name = "ACTIVE")
    public Boolean getActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Column(name = "BASECURRENCY1", length = 9)
    public String getBasecurrency1() {
        return this.basecurrency1;
    }

    public void setBasecurrency1(String basecurrency1) {
        this.basecurrency1 = basecurrency1;
    }

    @Column(name = "BASECURRENCY2", length = 9)
    public String getBasecurrency2() {
        return this.basecurrency2;
    }

    public void setBasecurrency2(String basecurrency2) {
        this.basecurrency2 = basecurrency2;
    }

    @Column(name = "CLEARINGACCT", length = 8)
    public String getClearingacct() {
        return this.clearingacct;
    }

    public void setClearingacct(String clearingacct) {
        this.clearingacct = clearingacct;
    }

    @Column(name = "DESCRIPTION", length = 100)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "ENTERBY", length = 30)
    public String getEnterby() {
        return this.enterby;
    }

    public void setEnterby(String enterby) {
        this.enterby = enterby;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ENTERDATE", length = 23)
    public Date getEnterdate() {
        return this.enterdate;
    }

    public void setEnterdate(Date enterdate) {
        this.enterdate = enterdate;
    }

    @Column(name = "LANGCODE", length = 4)
    public String getLangcode() {
        return this.langcode;
    }

    public void setLangcode(String langcode) {
        this.langcode = langcode;
    }

    @Column(name = "ORGID", length = 8)
    public String getOrgid() {
        return this.orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

}
