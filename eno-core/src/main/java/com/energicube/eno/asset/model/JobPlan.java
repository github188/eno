package com.energicube.eno.asset.model;


import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * 作业计划
 */
@Entity
@Table(name = "JOBPLAN", uniqueConstraints = @UniqueConstraint(columnNames = {
        "JPNUM", "ORGID", "SITEID"}))
public class JobPlan implements java.io.Serializable {

    private static final long serialVersionUID = -4933494334408612766L;

    private long jobplanid;
    /**
     * 用于确定工作班次的日历
     */
    private String calendar;
    /**
     * 工种中特定班组的标识
     */
    private String crewid;
    private String description;
    private Boolean hasld;
    private String jpduration;
    private String jpnum;
    /**
     * 定义负责人，该人员将被复制到本作业计划所创建工单中。
     */
    private String laborcode;
    private String langcode;
    private String orgid;
    /**
     * 所有者<br />
     * 对该作业计划所创建工单负责的人员
     */
    private String owner;
    private String ownergroup;
    /**
     * 工作组
     */
    private String personGroup;
    private Integer priority;
    private String siteid;
    /**
     * 此标准作业计划的状态：<br />
     * <code>DRAFT</code> - 草稿，新建JP初始状态都为草稿；<br />
     * <code>ACTIVE</code>  - 活动，JP处于活动状态才能被（PM、WO）引用， 一旦被引用，JP的活动状态只能设置为不活动；<br />
     * <code>INACTIVE</code>  - 闲置，JP状态为不活动。"
     */
    private String status;
    /**
     * 作业主管人
     */
    private String supervisor;

    public JobPlan() {
    }

    public JobPlan(int jobplanid) {
        this.jobplanid = jobplanid;
    }

    public JobPlan(long jobplanid, Boolean hasld, String jpduration,
                   String jpnum, String laborcode, String langcode, String orgid,
                   String owner, String ownergroup, Integer priority, String siteid,
                   String status, String supervisor) {
        this.jobplanid = jobplanid;
        this.hasld = hasld;
        this.jpduration = jpduration;
        this.jpnum = jpnum;
        this.laborcode = laborcode;
        this.langcode = langcode;
        this.orgid = orgid;
        this.owner = owner;
        this.ownergroup = ownergroup;
        this.priority = priority;
        this.siteid = siteid;
        this.status = status;
        this.supervisor = supervisor;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "JOBPLANID", unique = true, nullable = false)
    public long getJobplanid() {
        return this.jobplanid;
    }

    public void setJobplanid(long jobplanid) {
        this.jobplanid = jobplanid;
    }

    @Column(name = "HASLD")
    public Boolean getHasld() {
        return this.hasld;
    }

    public void setHasld(Boolean hasld) {
        this.hasld = hasld;
    }

    @Column(name = "JPDURATION", length = 10)
    public String getJpduration() {
        return this.jpduration;
    }

    public void setJpduration(String jpduration) {
        this.jpduration = jpduration;
    }

    @NotEmpty
    @Column(name = "JPNUM", length = 10)
    public String getJpnum() {
        return this.jpnum;
    }

    public void setJpnum(String jpnum) {
        this.jpnum = jpnum;
    }

    @Column(name = "LABORCODE", length = 30)
    public String getLaborcode() {
        return this.laborcode;
    }

    public void setLaborcode(String laborcode) {
        this.laborcode = laborcode;
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

    @Column(name = "PRIORITY")
    public Integer getPriority() {
        return this.priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Column(name = "SITEID", length = 8)
    public String getSiteid() {
        return this.siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

    @Column(name = "STATUS", length = 16)
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "SUPERVISOR", length = 30)
    public String getSupervisor() {
        return this.supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    @Column(name = "CALENDAR", length = 8)
    public String getCalendar() {
        return calendar;
    }

    public void setCalendar(String calendar) {
        this.calendar = calendar;
    }

    @Column(name = "CREWID", length = 12)
    public String getCrewid() {
        return crewid;
    }

    public void setCrewid(String crewid) {
        this.crewid = crewid;
    }

    @NotEmpty
    @Column(name = "DESCRIPTION", length = 100)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "PERSONGROUP", length = 8)
    public String getPersonGroup() {
        return personGroup;
    }

    public void setPersonGroup(String personGroup) {
        this.personGroup = personGroup;
    }

    @Override
    public String toString() {
        return "JobPlan [jobplanid=" + jobplanid + ", calendar=" + calendar
                + ", crewid=" + crewid + ", description=" + description
                + ", hasld=" + hasld + ", jpduration=" + jpduration
                + ", jpnum=" + jpnum + ", laborcode=" + laborcode
                + ", langcode=" + langcode + ", orgid=" + orgid + ", owner="
                + owner + ", ownergroup=" + ownergroup + ", personGroup="
                + personGroup + ", priority=" + priority + ", siteid=" + siteid
                + ", status=" + status + ", supervisor=" + supervisor + "]";
    }


}
