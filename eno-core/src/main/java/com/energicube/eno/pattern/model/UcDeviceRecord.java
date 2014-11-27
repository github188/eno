package com.energicube.eno.pattern.model;
// Generated 2013-8-21 16:23:12 by Hibernate Tools 3.4.0.CR1


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * UcDeviceRecord generated by hbm2java
 */
@Entity
@Table(name = "UC_deviceRecord"
        , schema = "zclfsys"
)
public class UcDeviceRecord implements java.io.Serializable {


    private String id;
    @org.codehaus.jackson.annotate.JsonBackReference
    @com.fasterxml.jackson.annotation.JsonBackReference
    private UcPatternRecord ucPatternRecord;
    private Integer baseTime;
    private Integer offsetTime;
    private Integer relativeTime;
    private String description;
    private String deviceId;
    private String strategyId;
    private String selectType;
    private String actionType;
    private Date startTime;
    private Date endTime;
    private Set<UcParamRecord> ucParamRecords = new HashSet<UcParamRecord>(0);

    public UcDeviceRecord() {
    }


    public UcDeviceRecord(String id) {
        this.id = id;
    }


    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(name = "id", unique = true, nullable = false, length = 36)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.DETACH)
    @JoinColumn(name = "patternRecordId")
    public UcPatternRecord getUcPatternRecord() {
        return this.ucPatternRecord;
    }

    public void setUcPatternRecord(UcPatternRecord ucPatternRecord) {
        this.ucPatternRecord = ucPatternRecord;
    }


    @Column(name = "baseTime")
    public Integer getBaseTime() {
        return this.baseTime;
    }

    public void setBaseTime(Integer baseTime) {
        this.baseTime = baseTime;
    }

    @Column(name = "offsetTime")
    public Integer getOffsetTime() {
        return this.offsetTime;
    }

    public void setOffsetTime(Integer offsetTime) {
        this.offsetTime = offsetTime;
    }


    @Column(name = "relativeTime")
    public Integer getRelativeTime() {
        return this.relativeTime;
    }

    public void setRelativeTime(Integer relativeTime) {
        this.relativeTime = relativeTime;
    }


    @Column(name = "description")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Column(name = "deviceId", length = 36)
    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }


    @Column(name = "strategyId", length = 36)
    public String getStrategyId() {
        return this.strategyId;
    }

    public void setStrategyId(String strategyId) {
        this.strategyId = strategyId;
    }


    @Column(name = "selectType", length = 4)
    public String getSelectType() {
        return this.selectType;
    }

    public void setSelectType(String selectType) {
        this.selectType = selectType;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "startTime", length = 23)
    public Date getStartTime() {
        return this.startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "endTime", length = 23)
    public Date getEndTime() {
        return this.endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ucDeviceRecord",  cascade ={CascadeType.REMOVE,CascadeType.DETACH})
    public Set<UcParamRecord> getUcParamRecords() {
        return this.ucParamRecords;
    }

    public void setUcParamRecords(Set<UcParamRecord> ucParamRecords) {
        this.ucParamRecords = ucParamRecords;
    }

    @Column(name = "actionType", length = 4)
    public String getActionType() {
        return this.actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }


}


