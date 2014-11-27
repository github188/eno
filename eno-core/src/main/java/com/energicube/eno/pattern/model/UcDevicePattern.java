package com.energicube.eno.pattern.model;
// Generated 2013-8-21 16:23:12 by Hibernate Tools 3.4.0.CR1


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * UcDevicePattern generated by hbm2java
 */
@Entity
@Table(name = "UC_devicePattern"
        , schema = "zclfsys"
)
public class UcDevicePattern implements java.io.Serializable {


    private String id;
    @org.codehaus.jackson.annotate.JsonBackReference
    @com.fasterxml.jackson.annotation.JsonBackReference
    private UcPatternAction ucPatternAction;
    private String name;
    private String selectType;
    private String device;
    private String strategyId;
    private Set<UcRunParam> ucRunParams = new HashSet<UcRunParam>(0);
    private Integer startTime;
    private Integer endTime;

    public UcDevicePattern() {
    }


    public UcDevicePattern(String id) {
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
    @JoinColumn(name = "patternActionId")
    public UcPatternAction getUcPatternAction() {
        return this.ucPatternAction;
    }

    public void setUcPatternAction(UcPatternAction ucPatternAction) {
        this.ucPatternAction = ucPatternAction;
    }


    @Column(name = "name", length = 60)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Column(name = "selectType", length = 4)
    public String getSelectType() {
        return this.selectType;
    }

    public void setSelectType(String selectType) {
        this.selectType = selectType;
    }


    @Column(name = "device", length = 36)
    public String getDevice() {
        return this.device;
    }

    public void setDevice(String device) {
        this.device = device;
    }


    @Column(name = "strategyId", length = 36)
    public String getStrategyId() {
        return this.strategyId;
    }

    public void setStrategyId(String strategyId) {
        this.strategyId = strategyId;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ucDevicePattern",  cascade ={CascadeType.REMOVE,CascadeType.DETACH})
    public Set<UcRunParam> getUcRunParams() {
        return this.ucRunParams;
    }

    public void setUcRunParams(Set<UcRunParam> ucRunParams) {
        this.ucRunParams = ucRunParams;
    }

    @Column(name = "startTime")
    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    @Column(name = "endTime")
    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }
}


