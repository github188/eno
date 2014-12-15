package com.energicube.eno.asset.model;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 测量型计量器的读数，或技术规范参数的测量值
 *
 * @author CHENPING
 */
@Entity
@Table(name = "MEASUREMENT")
public class Measurement implements java.io.Serializable {

    private static final long serialVersionUID = -7717995786878703922L;
    private long measurementid;
    /**
     * 资产ID
     */
    private Long assetid;
    /**
     * 资产编号
     */
    private String assetnum;
    /**
     * 通常，这与资产的计量单位相同。如果不同，那么系统将以计量单位显示计量值，
     * 但将同时以计量单位和基本计量单位存储计量值。如果基本计量单位与计量单位不同，
     * 那么必须具备计量单位和基本计量单位之间的转换（通过资产中的“计量单位和转换”操作输入）。
     */
    private String basemeasureunitid;
    /**
     * 测量人员
     */
    private String inspector;
    /**
     * 资产位置
     */
    private String location;
    /**
     * 测量日期
     */
    private LocalDate measuredate;
    /**
     * 实际测量值
     */
    private BigDecimal measurementvalue;
    /**
     * 计量器
     */
    private String metername;

    /**
     * 计量规范ID
     */
    private Long measurespecid;

    /**
     * CHARACTERISTIC 特征型计量器的读数。来自域标识与计量器域标识相同的 ALNDOMAINVALUE 表的有效值。
     */
    private String observation;
    private String orgid;
    /**
     * 测点
     */
    private String pointnum;
    /**
     * 地点ID
     */
    private String siteid;
    /**
     * 计算方法（能源模块使用）
     */
    private String calcmethod;
    /**
     * 测点的数据来源描述
     */
    private String measuresourcesys;
    /**
     * 测量时间
     */
    private LocalDateTime measuretime;
    /**
     * 所有者系统标识，标识记录的更新由本系统发出还是外部系统维护，如果记录由外部系统维护，也即标识了测点数据的来源接口。
     */
    private String ownersysid;
    /**
     * 集成接口使用列，标识记录用于验证由本系统从接口发送到外部系统成功，或者标识本系统从接口获得数据成功时发送数据的外部系统。
     */
    private String sendersysid;
    /**
     * tagID
     */
    private String valuetag;

    public Measurement() {
    }

    public Measurement(long measurementid) {
        this.measurementid = measurementid;
    }

    public Measurement(long measurementid, Long assetid, String assetnum,
                       String basemeasureunitid, String inspector, String location,
                       LocalDate measuredate, BigDecimal measurementvalue, String metername,
                       String observation, String orgid, String pointnum, String siteid,
                       String calcmethod, String measuresourcesys, LocalDateTime measuretime,
                       String ownersysid, String sendersysid, String valuetag) {
        this.measurementid = measurementid;
        this.assetid = assetid;
        this.assetnum = assetnum;
        this.basemeasureunitid = basemeasureunitid;
        this.inspector = inspector;
        this.location = location;
        this.measuredate = measuredate;
        this.measurementvalue = measurementvalue;
        this.metername = metername;
        this.observation = observation;
        this.orgid = orgid;
        this.pointnum = pointnum;
        this.siteid = siteid;
        this.calcmethod = calcmethod;
        this.measuresourcesys = measuresourcesys;
        this.measuretime = measuretime;
        this.ownersysid = ownersysid;
        this.sendersysid = sendersysid;
        this.valuetag = valuetag;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEASUREMENTID", unique = true, nullable = false)
    public long getMeasurementid() {
        return this.measurementid;
    }

    public void setMeasurementid(long measurementid) {
        this.measurementid = measurementid;
    }

    @Column(name = "ASSETID")
    public Long getAssetid() {
        return this.assetid;
    }

    public void setAssetid(Long assetid) {
        this.assetid = assetid;
    }

    @Column(name = "ASSETNUM", length = 12)
    public String getAssetnum() {
        return this.assetnum;
    }

    public void setAssetnum(String assetnum) {
        this.assetnum = assetnum;
    }

    @Column(name = "BASEMEASUREUNITID", length = 16)
    public String getBasemeasureunitid() {
        return this.basemeasureunitid;
    }

    public void setBasemeasureunitid(String basemeasureunitid) {
        this.basemeasureunitid = basemeasureunitid;
    }

    @Column(name = "INSPECTOR", length = 30)
    public String getInspector() {
        return this.inspector;
    }

    public void setInspector(String inspector) {
        this.inspector = inspector;
    }

    @Column(name = "LOCATION", length = 30)
    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "MEASUREDATE", length = 23)
    public LocalDate getMeasuredate() {
        return this.measuredate;
    }

    public void setMeasuredate(LocalDate measuredate) {
        this.measuredate = measuredate;
    }

    @Column(name = "MEASUREMENTVALUE", precision = 11, scale = 4)
    public BigDecimal getMeasurementvalue() {
        return this.measurementvalue;
    }

    public void setMeasurementvalue(BigDecimal measurementvalue) {
        this.measurementvalue = measurementvalue;
    }

    @Column(name = "METERNAME", length = 30)
    public String getMetername() {
        return this.metername;
    }

    public void setMetername(String metername) {
        this.metername = metername;
    }

    public Long getMeasurespecid() {
        return measurespecid;
    }

    public void setMeasurespecid(Long measurespecid) {
        this.measurespecid = measurespecid;
    }

    @Column(name = "OBSERVATION", length = 254)
    public String getObservation() {
        return this.observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    @Column(name = "ORGID", length = 8)
    public String getOrgid() {
        return this.orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    @Column(name = "POINTNUM", length = 20)
    public String getPointnum() {
        return this.pointnum;
    }

    public void setPointnum(String pointnum) {
        this.pointnum = pointnum;
    }

    @Column(name = "SITEID", length = 8)
    public String getSiteid() {
        return this.siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

    @Column(name = "CALCMETHOD", length = 25)
    public String getCalcmethod() {
        return this.calcmethod;
    }

    public void setCalcmethod(String calcmethod) {
        this.calcmethod = calcmethod;
    }

    @Column(name = "MEASURESOURCESYS", length = 25)
    public String getMeasuresourcesys() {
        return this.measuresourcesys;
    }

    public void setMeasuresourcesys(String measuresourcesys) {
        this.measuresourcesys = measuresourcesys;
    }

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "MEASURETIME", length = 23)
    public LocalDateTime getMeasuretime() {
        return this.measuretime;
    }

    public void setMeasuretime(LocalDateTime measuretime) {
        this.measuretime = measuretime;
    }

    @Column(name = "OWNERSYSID", length = 10)
    public String getOwnersysid() {
        return this.ownersysid;
    }

    public void setOwnersysid(String ownersysid) {
        this.ownersysid = ownersysid;
    }

    @Column(name = "SENDERSYSID", length = 50)
    public String getSendersysid() {
        return this.sendersysid;
    }

    public void setSendersysid(String sendersysid) {
        this.sendersysid = sendersysid;
    }

    @Column(name = "VALUETAG", length = 64)
    public String getValuetag() {
        return this.valuetag;
    }

    public void setValuetag(String valuetag) {
        this.valuetag = valuetag;
    }

    @Override
    public String toString() {
        return "Measurement [measurementid=" + measurementid + ", assetid="
                + assetid + ", assetnum=" + assetnum + ", basemeasureunitid="
                + basemeasureunitid + ", inspector=" + inspector
                + ", location=" + location + ", measuredate=" + measuredate
                + ", measurementvalue=" + measurementvalue + ", metername="
                + metername + ", observation=" + observation + ", orgid="
                + orgid + ", pointnum=" + pointnum + ", siteid=" + siteid
                + ", calcmethod=" + calcmethod + ", measuresourcesys="
                + measuresourcesys + ", measuretime=" + measuretime
                + ", ownersysid=" + ownersysid + ", sendersysid=" + sendersysid
                + ", valuetag=" + valuetag + "]";
    }

}
