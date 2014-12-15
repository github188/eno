package com.energicube.eno.asset.model;


import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * 作业计划序列表
 */
@Entity
@Table(name = "PMSEQUENCE")
public class PMSequence implements java.io.Serializable {

    private static final long serialVersionUID = -888012172984701029L;
    private long pmsequenceid;
    private Integer interval = 0;
    private String jpnum;
    private String orgid;
    private String pmnum;
    private String siteid;

    public PMSequence() {
    }

    public PMSequence(long pmsequenceid) {
        this.pmsequenceid = pmsequenceid;
    }

    public PMSequence(long pmsequenceid, Integer interval, String jpnum,
                      String orgid, String pmnum, String siteid) {
        this.pmsequenceid = pmsequenceid;
        this.interval = interval;
        this.jpnum = jpnum;
        this.orgid = orgid;
        this.pmnum = pmnum;
        this.siteid = siteid;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PMSEQUENCEID", unique = true, nullable = false)
    public long getPmsequenceid() {
        return this.pmsequenceid;
    }

    public void setPmsequenceid(long pmsequenceid) {
        this.pmsequenceid = pmsequenceid;
    }

    @NotNull
    @Column(name = "INTERVAL")
    public Integer getInterval() {
        return this.interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    @NotEmpty
    @Column(name = "JPNUM", length = 10)
    public String getJpnum() {
        return this.jpnum;
    }

    public void setJpnum(String jpnum) {
        this.jpnum = jpnum;
    }

    @Column(name = "ORGID", length = 8)
    public String getOrgid() {
        return this.orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    @NotEmpty
    @Column(name = "PMNUM", length = 8)
    public String getPmnum() {
        return this.pmnum;
    }

    public void setPmnum(String pmnum) {
        this.pmnum = pmnum;
    }

    @Column(name = "SITEID", length = 8)
    public String getSiteid() {
        return this.siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

}
