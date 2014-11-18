package com.energicube.eno.asset.model;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 预防性维护表，PM相当于工单模板，可从此生成PM工单（系统由于无主PM，就没有关联预防性维护，只有类似季节性预防性维护概念）
 */
@Entity
@Table(name = "PM", schema = "dbo")
public class PM implements java.io.Serializable {

    private static final long serialVersionUID = -6303582265196561484L;

    /**
     * 唯一标识
     */
    private long pmid;
    /**
     * 基于时间的频率中设定，生成后是否调整下一个截止日期？
     */
    private Boolean adjnextdue;
    /**
     * 基于时间的频率中设定的预警期，在PM截止日期之前可接受期限（以天为单位），在此期间可执行PM。
     */
    private Integer alertlead = 0;
    /**
     * 执行预防性维护的资产
     */
    private String assetnum;
    /**
     * 用于确定工作班次的日历
     */
    private String calendar;
    /**
     * PM 的输入人/修改人
     */
    private String changeby;
    /**
     * PM 上次变更的日期
     */
    private LocalDateTime changedate = LocalDateTime.now();
    /**
     * PM生成工单分配的工作班组。 系统从PM上的单个作业计划或作业计划序列中的第一个作业计划里复制该字段值。
     * 如果与PM关联的作业计划有变更，则须手工更新此字段。
     */
    private String crewid;
    /**
     * PM描述
     */
    private String description;
    /**
     * 基于时间的频率里设定的延长日期，如果有值会覆盖下一个工单生成截止日期NEXTDATE。
     */
    private DateTime extdate;
    /**
     * 生成第一个工单的开始日期
     */
    private DateTime firstdate;
    /**
     * 基于时间的频率，单位由frequnit字段指定
     */
    private Integer frequency = 0;
    /**
     * 基于时间的频率里设定的频率单位（DAYS、WEEKS、MONTHS、YEARS）
     */
    private String frequnit = "DAYS";

    /**
     * 指定 PM 是否包含任何子PM。如选中，则该 PM 包含相关子PM。如未选中，则没有子PM。
     */
    private Boolean haschildren;
    /**
     * 是否有长描述。
     */
    private Boolean hasld;
    /**
     * 指定在资源计划过程中是否允许停止或重新开始执行由该 PM 生成的工单。 如果此复选框已选中，那么可停止或重新开始执行工单。
     * 如果清除该复选框，那么在资源计划过程中不能中断工单的执行。
     */
    private Boolean interruptible;
    /**
     * 标识与该 PM 的相关作业计划。
     */
    private String jpnum;
    /**
     * PM是否使用作业计划序列？ 选中该复选框，PM每次将根据作业计划序列生成工单。 如果未选中该复选框，PM每次将根据JPNUM字段值生成相同的工单。
     */
    private Boolean jpseqinuse;
    /**
     * 语言列
     */
    private String langcode;
    /**
     * 由 PM 生成的上一工单的完成日期。完成或关闭工单后，系统将自动更新该字段。
     */
    private LocalDateTime lastcompdate;
    /**
     * 开始日期，该日期是在 PM 中生成的上一工单的预定开始日期。
     */
    private DateTime laststartdate;
    /**
     * 从该 PM 生成的工单的负责人。
     */
    private String lead;
    /**
     * 系统从该PM生成工单的日期比“下一截止日期”提前的天数。
     * 工单的目标开始日期仍然为“下一截止日期”。进入或编辑该字段数据前，必须选中“提前时间有效”。
     */
    private LocalDateTime leadtime;
    /**
     * 在该 PM 中生成工单时，完成工作的位置。
     */
    private String location;
    /**
     * 工单计数器，从“第一个开始日期”以来，在PM中生成的工单数目。 <br />
     * 新的PM记录生成计数器值默认为“0”； <br />
     * 每次PM中生成顶级工单时计数器读数均会增加。 <br />
     * 如果要重新勾选“使用作业计划序列”项，请在计数器增量完成后重新勾选。 <br />
     * 可由“设置PM计数器”复位。
     */
    private Integer ltdpmcounter;

    /**
     * 基于时间的频率里设定，估算生成下一工单的日期
     */
    private DateTime nextdate;
    /**
     * 组织标识
     */
    private String orgid;
    /**
     * 当前PM的父级PM编号
     */
    private String parent;
    /**
     * 指定当父级状态变更时，子工单是否变更状态。<br />
     * 如果选中复选框，将变更子工单的状态。<br />
     * 如果清除复选框，将不变更子工单的状态。
     */
    private Boolean parentchgsstatus;
    /**
     * 此标志指示到达计量器频率时自动从 PM 生成工单。
     */
    private Boolean pmassetwogen;
    /**
     * 从“第一个开始日期”以来，在PM中生成的工单数。<br />
     * 当您插入新的PM记录时，计数器设置为<code>0</code>；<br />
     * 每次您在PM中生成顶级工单时，计数器读数均会增加。<br />
     * 如果要使用作业计划序列，那么在计数器增量之后选定该作业计划。
     */
    private Integer pmcounter;
    /**
     * PM 扩展字段
     */
    private String pmec1;
    /**
     * PM 扩展字段
     */
    private LocalDateTime pmec2;
    /**
     * PM 扩展字段
     */
    private BigDecimal pmec3;
    /**
     * PM编号，在不同地点上此值唯一。每条PM记录仅能针对一个资产或一个位置
     */
    private String pmnum;
    /**
     * 由该 PM 生成的工单的优先级。
     */
    private Integer priority;

    /**
     * 频率冲突时，提前调度，此复选框确定如何解决PM频率与一周中指定日期之间的冲突。 <br />
     * 为PM工单指定“目标开始日期”字段值时，“目标开始日期”必须在一周中选定的有效日期内；<br />
     * 如果复选框选中，则该日期应该早于或等于基于频率的正常调度日期；<br />
     * 如果复选框未选中，该日期只能等于或晚于基于频率的正常调度日期。
     */
    private Boolean schedearly;
    /**
     * 标识地点
     */
    private String siteid;
    /**
     * PM 的状态  DRAFT\ACTIVE\INACTIVE
     */
    private String status;
    /**
     * 周一标志位，指示当日对于 PM 是否为活动的
     */
    private Boolean monday;
    /**
     * 周二标志位，指示当日对于 PM 是否为活动的
     */
    private Boolean tuesday;
    /**
     * 周三标志位，指示当日对于 PM 是否为活动的
     */
    private Boolean wednesday;
    /**
     * 周四标志位，指示当日对于 PM 是否为活动的
     */
    private Boolean thursday;
    /**
     * 周五标志位，指示当日对于 PM 是否为活动的
     */
    private Boolean friday;
    /**
     * 周六标志位，指示当日对于 PM 是否为活动的
     */
    private Boolean saturday;
    /**
     * 周日标志位，指示当日对于 PM 是否为活动的
     */
    private Boolean sunday;
    /**
     * 负责执行该工单的主管人。<br />
     * 系统从PM上的单个作业计划或作业计划序列中的第一个作业计划里复制该字段值。<br />
     * 如果与PM关联的作业计划有变更，则须手工更新此字段。
     */
    private String supervisor;

    /**
     * 用于启动PM工单的目标时间。如果未设定值，则目标时间按频率与有效日期冲突时目标时间为最小时间差来处理。
     */
    private LocalTime targstarttime;

    /**
     * 是否使用该PM来触发PM层次结构，告诉“生成工单”功能在确定是否生成层次结构中所有 PM 时，应该检查低级 PM 的频率。
     */
    private Boolean usefrequency;
    /**
     * 使用上次的工单目标开始日期计算下一到期频率
     */
    private Boolean usetargetdate;
    /**
     * 在此PM中生成的工单分类或类型，一般是PM。
     */
    private String worktype;
    /**
     * 工单序号。复制到WorkOrder.WOSequence。
     */
    private Integer wosequence;
    /**
     * 标识所生成工单的原始工单状态。
     */
    private String wostatus;

    public PM() {
    }

    public PM(long pmid) {
        this.pmid = pmid;
    }

    public PM(long pmid, Boolean adjnextdue, Integer alertlead,
              String assetnum, String calendar, String changeby,
              LocalDateTime changedate, String crewid, String description,
              DateTime extdate, DateTime firstdate, Integer frequency,
              String frequnit, Boolean friday, Boolean haschildren,
              Boolean hasld, Boolean interruptible, String jpnum,
              Boolean jpseqinuse, String langcode, LocalDateTime lastcompdate,
              DateTime laststartdate, String lead, LocalDateTime leadtime,
              String location, Integer ltdpmcounter, Boolean monday,
              DateTime nextdate, String orgid, String parent,
              Boolean parentchgsstatus, Boolean pmassetwogen, Integer pmcounter,
              String pmec1, LocalDateTime pmec2, BigDecimal pmec3, String pmnum,
              Integer priority, Boolean saturday, Boolean schedearly,
              String siteid, String status, Boolean sunday, String supervisor,
              LocalTime targstarttime, Boolean thursday, Boolean tuesday,
              Boolean usefrequency, Boolean usetargetdate, Boolean wednesday,
              String worktype, Integer wosequence, String wostatus) {
        this.pmid = pmid;
        this.adjnextdue = adjnextdue;
        this.alertlead = alertlead;
        this.assetnum = assetnum;
        this.calendar = calendar;
        this.changeby = changeby;
        this.changedate = changedate;
        this.crewid = crewid;
        this.description = description;
        this.extdate = extdate;
        this.firstdate = firstdate;
        this.frequency = frequency;
        this.frequnit = frequnit;
        this.friday = friday;
        this.haschildren = haschildren;
        this.hasld = hasld;
        this.interruptible = interruptible;
        this.jpnum = jpnum;
        this.jpseqinuse = jpseqinuse;
        this.langcode = langcode;
        this.lastcompdate = lastcompdate;
        this.laststartdate = laststartdate;
        this.lead = lead;
        this.leadtime = leadtime;
        this.location = location;
        this.ltdpmcounter = ltdpmcounter;
        this.monday = monday;
        this.nextdate = nextdate;
        this.orgid = orgid;
        this.parent = parent;
        this.parentchgsstatus = parentchgsstatus;
        this.pmassetwogen = pmassetwogen;
        this.pmcounter = pmcounter;
        this.pmec1 = pmec1;
        this.pmec2 = pmec2;
        this.pmec3 = pmec3;
        this.pmnum = pmnum;
        this.priority = priority;
        this.saturday = saturday;
        this.schedearly = schedearly;
        this.siteid = siteid;
        this.status = status;
        this.sunday = sunday;
        this.supervisor = supervisor;
        this.targstarttime = targstarttime;
        this.thursday = thursday;
        this.tuesday = tuesday;
        this.usefrequency = usefrequency;
        this.usetargetdate = usetargetdate;
        this.wednesday = wednesday;
        this.worktype = worktype;
        this.wosequence = wosequence;
        this.wostatus = wostatus;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PMID", unique = true, nullable = false)
    public long getPmid() {
        return this.pmid;
    }

    public void setPmid(long pmid) {
        this.pmid = pmid;
    }

    @Column(name = "ADJNEXTDUE")
    public Boolean getAdjnextdue() {
        return this.adjnextdue;
    }

    public void setAdjnextdue(Boolean adjnextdue) {
        this.adjnextdue = adjnextdue;
    }

    @Column(name = "ALERTLEAD")
    public Integer getAlertlead() {
        return this.alertlead;
    }

    public void setAlertlead(Integer alertlead) {
        this.alertlead = alertlead;
    }

    @Column(name = "ASSETNUM", length = 12)
    public String getAssetnum() {
        return this.assetnum;
    }

    public void setAssetnum(String assetnum) {
        this.assetnum = assetnum;
    }

    @Column(name = "CALENDAR", length = 8)
    public String getCalendar() {
        return this.calendar;
    }

    public void setCalendar(String calendar) {
        this.calendar = calendar;
    }

    @Column(name = "CHANGEBY", length = 30)
    public String getChangeby() {
        return this.changeby;
    }

    public void setChangeby(String changeby) {
        this.changeby = changeby;
    }

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "CHANGEDATE", length = 23)
    public LocalDateTime getChangedate() {
        return this.changedate;
    }

    public void setChangedate(LocalDateTime changedate) {
        this.changedate = changedate;
    }

    @Column(name = "CREWID", length = 12)
    public String getCrewid() {
        return this.crewid;
    }

    public void setCrewid(String crewid) {
        this.crewid = crewid;
    }

    @Column(name = "DESCRIPTION", length = 100)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "EXTDATE", length = 23)
    public DateTime getExtdate() {
        return this.extdate;
    }

    public void setExtdate(DateTime extdate) {
        this.extdate = extdate;
    }

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "FIRSTDATE", length = 23)
    public DateTime getFirstdate() {
        return this.firstdate;
    }

    public void setFirstdate(DateTime firstdate) {
        this.firstdate = firstdate;
    }

    @Column(name = "FREQUENCY")
    public Integer getFrequency() {
        return this.frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    @Column(name = "FREQUNIT", length = 8)
    public String getFrequnit() {
        return this.frequnit;
    }

    public void setFrequnit(String frequnit) {
        this.frequnit = frequnit;
    }

    @Column(name = "FRIDAY")
    public Boolean getFriday() {
        return this.friday;
    }

    public void setFriday(Boolean friday) {
        this.friday = friday;
    }

    @Column(name = "HASCHILDREN")
    public Boolean getHaschildren() {
        return this.haschildren;
    }

    public void setHaschildren(Boolean haschildren) {
        this.haschildren = haschildren;
    }

    @Column(name = "HASLD")
    public Boolean getHasld() {
        return this.hasld;
    }

    public void setHasld(Boolean hasld) {
        this.hasld = hasld;
    }

    @Column(name = "INTERRUPTIBLE")
    public Boolean getInterruptible() {
        return this.interruptible;
    }

    public void setInterruptible(Boolean interruptible) {
        this.interruptible = interruptible;
    }

    @Column(name = "JPNUM", length = 10)
    public String getJpnum() {
        return this.jpnum;
    }

    public void setJpnum(String jpnum) {
        this.jpnum = jpnum;
    }

    @Column(name = "JPSEQINUSE")
    public Boolean getJpseqinuse() {
        return this.jpseqinuse;
    }

    public void setJpseqinuse(Boolean jpseqinuse) {
        this.jpseqinuse = jpseqinuse;
    }

    @Column(name = "LANGCODE", length = 4)
    public String getLangcode() {
        return this.langcode;
    }

    public void setLangcode(String langcode) {
        this.langcode = langcode;
    }

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "LASTCOMPDATE", length = 23)
    public LocalDateTime getLastcompdate() {
        return this.lastcompdate;
    }

    public void setLastcompdate(LocalDateTime lastcompdate) {
        this.lastcompdate = lastcompdate;
    }

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "LASTSTARTDATE", length = 23)
    public DateTime getLaststartdate() {
        return this.laststartdate;
    }

    public void setLaststartdate(DateTime laststartdate) {
        this.laststartdate = laststartdate;
    }

    @Column(name = "LEAD", length = 30)
    public String getLead() {
        return this.lead;
    }

    public void setLead(String lead) {
        this.lead = lead;
    }

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "LEADTIME", length = 23)
    public LocalDateTime getLeadtime() {
        return this.leadtime;
    }

    public void setLeadtime(LocalDateTime leadtime) {
        this.leadtime = leadtime;
    }

    @Column(name = "LOCATION", length = 30)
    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Column(name = "LTDPMCOUNTER")
    public Integer getLtdpmcounter() {
        return this.ltdpmcounter;
    }

    public void setLtdpmcounter(Integer ltdpmcounter) {
        this.ltdpmcounter = ltdpmcounter;
    }

    @Column(name = "MONDAY")
    public Boolean getMonday() {
        return this.monday;
    }

    public void setMonday(Boolean monday) {
        this.monday = monday;
    }

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "NEXTDATE", length = 23)
    public DateTime getNextdate() {
        return this.nextdate;
    }

    public void setNextdate(DateTime nextdate) {
        this.nextdate = nextdate;
    }

    @Column(name = "ORGID", length = 8)
    public String getOrgid() {
        return this.orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    @Column(name = "PARENT", length = 8)
    public String getParent() {
        return this.parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    @Column(name = "PARENTCHGSSTATUS")
    public Boolean getParentchgsstatus() {
        return this.parentchgsstatus;
    }

    public void setParentchgsstatus(Boolean parentchgsstatus) {
        this.parentchgsstatus = parentchgsstatus;
    }

    @Column(name = "PMASSETWOGEN")
    public Boolean getPmassetwogen() {
        return this.pmassetwogen;
    }

    public void setPmassetwogen(Boolean pmassetwogen) {
        this.pmassetwogen = pmassetwogen;
    }

    @Column(name = "PMCOUNTER")
    public Integer getPmcounter() {
        return this.pmcounter;
    }

    public void setPmcounter(Integer pmcounter) {
        this.pmcounter = pmcounter;
    }

    @Column(name = "PMEC1", length = 10)
    public String getPmec1() {
        return this.pmec1;
    }

    public void setPmec1(String pmec1) {
        this.pmec1 = pmec1;
    }

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "PMEC2", length = 23)
    public LocalDateTime getPmec2() {
        return this.pmec2;
    }

    public void setPmec2(LocalDateTime pmec2) {
        this.pmec2 = pmec2;
    }

    @Column(name = "PMEC3", precision = 11, scale = 4)
    public BigDecimal getPmec3() {
        return this.pmec3;
    }

    public void setPmec3(BigDecimal pmec3) {
        this.pmec3 = pmec3;
    }

    @NotEmpty
    @Column(name = "PMNUM", length = 8)
    public String getPmnum() {
        return this.pmnum;
    }

    public void setPmnum(String pmnum) {
        this.pmnum = pmnum;
    }

    @Column(name = "PRIORITY")
    public Integer getPriority() {
        return this.priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Column(name = "SATURDAY")
    public Boolean getSaturday() {
        return this.saturday;
    }

    public void setSaturday(Boolean saturday) {
        this.saturday = saturday;
    }

    @Column(name = "SCHEDEARLY")
    public Boolean getSchedearly() {
        return this.schedearly;
    }

    public void setSchedearly(Boolean schedearly) {
        this.schedearly = schedearly;
    }

    @Column(name = "SITEID", length = 8)
    public String getSiteid() {
        return this.siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

    @Column(name = "STATUS", length = 20)
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "SUNDAY")
    public Boolean getSunday() {
        return this.sunday;
    }

    public void setSunday(Boolean sunday) {
        this.sunday = sunday;
    }

    @Column(name = "SUPERVISOR", length = 30)
    public String getSupervisor() {
        return this.supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalTime")
    @DateTimeFormat(pattern = "HH:mm:ss")
    @Column(name = "TARGSTARTTIME", length = 23)
    public LocalTime getTargstarttime() {
        return this.targstarttime;
    }

    public void setTargstarttime(LocalTime targstarttime) {
        this.targstarttime = targstarttime;
    }

    @Column(name = "THURSDAY")
    public Boolean getThursday() {
        return this.thursday;
    }

    public void setThursday(Boolean thursday) {
        this.thursday = thursday;
    }

    @Column(name = "TUESDAY")
    public Boolean getTuesday() {
        return this.tuesday;
    }

    public void setTuesday(Boolean tuesday) {
        this.tuesday = tuesday;
    }

    @Column(name = "USEFREQUENCY")
    public Boolean getUsefrequency() {
        return this.usefrequency;
    }

    public void setUsefrequency(Boolean usefrequency) {
        this.usefrequency = usefrequency;
    }

    @Column(name = "USETARGETDATE")
    public Boolean getUsetargetdate() {
        return this.usetargetdate;
    }

    public void setUsetargetdate(Boolean usetargetdate) {
        this.usetargetdate = usetargetdate;
    }

    @Column(name = "WEDNESDAY")
    public Boolean getWednesday() {
        return this.wednesday;
    }

    public void setWednesday(Boolean wednesday) {
        this.wednesday = wednesday;
    }

    @Column(name = "WORKTYPE", length = 5)
    public String getWorktype() {
        return this.worktype;
    }

    public void setWorktype(String worktype) {
        this.worktype = worktype;
    }

    @Column(name = "WOSEQUENCE")
    public Integer getWosequence() {
        return this.wosequence;
    }

    public void setWosequence(Integer wosequence) {
        this.wosequence = wosequence;
    }

    @Column(name = "WOSTATUS", length = 16)
    public String getWostatus() {
        return this.wostatus;
    }

    public void setWostatus(String wostatus) {
        this.wostatus = wostatus;
    }

}
