package com.energicube.eno.asset.model;


import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

/**
 * 计划任务
 */
@Entity
@Table(name = "JOBTASK", schema = "zclfsys")
public class JobTask implements java.io.Serializable {

    private static final long serialVersionUID = -9195136117894654126L;
    private long jobtaskid;
    private String description;
    private Boolean hasld;
    private String jt1;
    private String jt2;
    private String jt3;
    private BigDecimal jt4;
    private String jt5;
    private String jt6;
    private String jt7;
    private String jt8;
    private Long jobplanid;
    private String jpnum;
    private Integer jptask;
    private String langcode;
    private String orgid;
    private String owner;
    private String ownergroup;
    private String siteid;
    private String taskduration;
    private Integer tasksequence;

    public JobTask() {
    }

    public JobTask(int jobtaskid) {
        this.jobtaskid = jobtaskid;
    }

    public JobTask(long jobtaskid, String description, Boolean hasld,
                   String jt1, String jt2, String jt3, BigDecimal jt4, String jt5,
                   String jt6, String jt7, String jt8, Long jobplanid,
                   String jpnum, Integer jptask, String langcode, String orgid,
                   String owner, String ownergroup, String siteid, String taskduration,
                   Integer tasksequence) {
        this.jobtaskid = jobtaskid;
        this.description = description;
        this.hasld = hasld;
        this.jt1 = jt1;
        this.jt2 = jt2;
        this.jt3 = jt3;
        this.jt4 = jt4;
        this.jt5 = jt5;
        this.jt6 = jt6;
        this.jt7 = jt7;
        this.jt8 = jt8;
        this.jobplanid = jobplanid;
        this.jpnum = jpnum;
        this.jptask = jptask;
        this.langcode = langcode;
        this.orgid = orgid;
        this.owner = owner;
        this.ownergroup = ownergroup;
        this.siteid = siteid;
        this.taskduration = taskduration;
        this.tasksequence = tasksequence;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "JOBTASKID", unique = true, nullable = false)
    public long getJobtaskid() {
        return this.jobtaskid;
    }

    public void setJobtaskid(long jobtaskid) {
        this.jobtaskid = jobtaskid;
    }

    @NotEmpty
    @Column(name = "DESCRIPTION", length = 255)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "HASLD")
    public Boolean getHasld() {
        return this.hasld;
    }

    public void setHasld(Boolean hasld) {
        this.hasld = hasld;
    }

    @Column(name = "JT1", length = 10)
    public String getJt1() {
        return this.jt1;
    }

    public void setJt1(String jt1) {
        this.jt1 = jt1;
    }

    @Column(name = "JT2", length = 10)
    public String getJt2() {
        return this.jt2;
    }

    public void setJt2(String jt2) {
        this.jt2 = jt2;
    }

    @Column(name = "JT3", length = 10)
    public String getJt3() {
        return this.jt3;
    }

    public void setJt3(String jt3) {
        this.jt3 = jt3;
    }

    @Column(name = "JT4", precision = 11, scale = 4)
    public BigDecimal getJt4() {
        return this.jt4;
    }

    public void setJt4(BigDecimal jt4) {
        this.jt4 = jt4;
    }

    @Column(name = "JT5", length = 10)
    public String getJt5() {
        return this.jt5;
    }

    public void setJt5(String jt5) {
        this.jt5 = jt5;
    }

    @Column(name = "JT6", length = 10)
    public String getJt6() {
        return this.jt6;
    }

    public void setJt6(String jt6) {
        this.jt6 = jt6;
    }

    @Column(name = "JT7", length = 10)
    public String getJt7() {
        return this.jt7;
    }

    public void setJt7(String jt7) {
        this.jt7 = jt7;
    }

    @Column(name = "JT8", length = 10)
    public String getJt8() {
        return this.jt8;
    }

    public void setJt8(String jt8) {
        this.jt8 = jt8;
    }

    @Column(name = "JOBPLANID")
    public Long getJobplanid() {
        return this.jobplanid;
    }

    public void setJobplanid(Long jobplanid) {
        this.jobplanid = jobplanid;
    }

    @NotEmpty
    @Column(name = "JPNUM", length = 10)
    public String getJpnum() {
        return this.jpnum;
    }

    public void setJpnum(String jpnum) {
        this.jpnum = jpnum;
    }

    //@DecimalMin(value = "1")
    //@Pattern(regexp = "[\\s]*[0-9]*[1-9]+",message="必须为数字")
    @Column(name = "JPTASK")
    public Integer getJptask() {
        return this.jptask;
    }

    public void setJptask(Integer jptask) {
        this.jptask = jptask;
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

    @Column(name = "OWNER", length = 30)
    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    @Column(name = "OWNERGROUP", length = 8)
    public String getOwnergroup() {
        return this.ownergroup;
    }

    public void setOwnergroup(String ownergroup) {
        this.ownergroup = ownergroup;
    }

    @Column(name = "SITEID", length = 8)
    public String getSiteid() {
        return this.siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

    @Column(name = "TASKDURATION", length = 8)
    public String getTaskduration() {
        return this.taskduration;
    }

    public void setTaskduration(String taskduration) {
        this.taskduration = taskduration;
    }

    @Pattern(regexp = "[\\s]*[0-9]*[1-9]+", message = "必须为数字")
    @Column(name = "TASKSEQUENCE")
    public Integer getTasksequence() {
        return this.tasksequence;
    }

    public void setTasksequence(Integer tasksequence) {
        this.tasksequence = tasksequence;
    }

}
