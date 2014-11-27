package com.energicube.eno.monitor.model;


import javax.persistence.*;
import java.math.BigDecimal;

/**
 *
 */
@Entity
@Table(name = "OKCDMNUMERIC", schema = "zclfsys")
public class OkcDMNumeric implements java.io.Serializable {

    private static final long serialVersionUID = -5544305085262075631L;
    private long okcdmnumericid;
    private String dictid;
    private String description;
    private String orgid;
    private String siteid;
    private BigDecimal dmvalue;

    public OkcDMNumeric() {
    }

    public OkcDMNumeric(long okcdmnumericid, String dictid, BigDecimal dmvalue) {
        this.okcdmnumericid = okcdmnumericid;
        this.dictid = dictid;
        this.dmvalue = dmvalue;
    }

    public OkcDMNumeric(long okcdmnumericid, String dictid, String description,
                        String orgid, String siteid, BigDecimal dmvalue) {
        this.okcdmnumericid = okcdmnumericid;
        this.dictid = dictid;
        this.description = description;
        this.orgid = orgid;
        this.siteid = siteid;
        this.dmvalue = dmvalue;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OKCDMNUMERICID", unique = true, nullable = false)
    public long getOkcdmnumericid() {
        return this.okcdmnumericid;
    }

    public void setOkcdmnumericid(long okcdmnumericid) {
        this.okcdmnumericid = okcdmnumericid;
    }

    @Column(name = "DICTID", nullable = false, length = 18)
    public String getDictid() {
        return this.dictid;
    }

    public void setDictid(String dictid) {
        this.dictid = dictid;
    }

    @Column(name = "DESCRIPTION", length = 100)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "ORGID", length = 8)
    public String getOrgid() {
        return this.orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    @Column(name = "SITEID", length = 8)
    public String getSiteid() {
        return this.siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

    @Column(name = "DMVALUE", nullable = false, precision = 26, scale = 4)
    public BigDecimal getDmvalue() {
        return this.dmvalue;
    }

    public void setDmvalue(BigDecimal dmvalue) {
        this.dmvalue = dmvalue;
    }

}
