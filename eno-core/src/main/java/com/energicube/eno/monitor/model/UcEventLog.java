package com.energicube.eno.monitor.model;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

/**
 * All rights Reserved, Designed By ZCLF
 * Copyright:    Copyright(C) 2013-2014
 * Company   ZCLF Energy Technologies Inc.
 *
 * @author 刘广路
 * @version 1.0
 * @date 2014/10/26 18:59
 * @Description CS设备运行日志
 */
@Entity
@Table(name = "UC_eventlog", schema = "dbo")
public class UcEventLog {

    private long id;
    private long loginst;
    private long logid;
    private Date logt;
    private Date reviewt;
    private BigInteger eventcode;
    private BigInteger eventtype;
    private BigInteger eventlevel;
    private BigInteger tagid;
    private BigInteger deviceid;
    private String eventname;
    private String eventstatus;
    private String stationname;
    private String tagname;
    private String fieldname;
    private String tagcomment;
    private String assetid;
    private String propertyid;
    private String devicename;
    private String tagvalue;
    private String oldvalue;
    private String operatorname;
    private String reviewcontent;
    private String reviewer;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "loginst")
    public long getLoginst() {
        return loginst;
    }

    public void setLoginst(long loginst) {
        this.loginst = loginst;
    }

    @Column(name = "logid")
    public long getLogid() {
        return logid;
    }

    public void setLogid(long logid) {
        this.logid = logid;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "logt", length = 23)
    public Date getLogt() {
        return logt;
    }

    public void setLogt(Date logt) {
        this.logt = logt;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "reviewt", length = 23)
    public Date getReviewt() {
        return reviewt;
    }

    public void setReviewt(Date reviewt) {
        this.reviewt = reviewt;
    }

    @Column(name = "eventcode")
    public BigInteger getEventcode() {
        return eventcode;
    }

    public void setEventcode(BigInteger eventcode) {
        this.eventcode = eventcode;
    }

    @Column(name = "eventtype")
    public BigInteger getEventtype() {
        return eventtype;
    }

    public void setEventtype(BigInteger eventtype) {
        this.eventtype = eventtype;
    }

    @Column(name = "eventlevel")
    public BigInteger getEventlevel() {
        return eventlevel;
    }

    public void setEventlevel(BigInteger eventlevel) {
        this.eventlevel = eventlevel;
    }

    @Column(name = "tagid")
    public BigInteger getTagid() {
        return tagid;
    }

    public void setTagid(BigInteger tagid) {
        this.tagid = tagid;
    }

    @Column(name = "deviceid")
    public BigInteger getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(BigInteger deviceid) {
        this.deviceid = deviceid;
    }

    @Column(name = "eventname", length = 255)
    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    @Column(name = "eventstatus", length = 64)
    public String getEventstatus() {
        return eventstatus;
    }

    public void setEventstatus(String eventstatus) {
        this.eventstatus = eventstatus;
    }

    @Column(name = "stationname", length = 64)
    public String getStationname() {
        return stationname;
    }

    public void setStationname(String stationname) {
        this.stationname = stationname;
    }

    @Column(name = "tagname", length = 64)
    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    @Column(name = "fieldname", length = 64)
    public String getFieldname() {
        return fieldname;
    }

    public void setFieldname(String fieldname) {
        this.fieldname = fieldname;
    }

    @Column(name = "tagcomment", length = 64)
    public String getTagcomment() {
        return tagcomment;
    }

    public void setTagcomment(String tagcomment) {
        this.tagcomment = tagcomment;
    }

    @Column(name = "assetid", length = 32)
    public String getAssetid() {
        return assetid;
    }

    public void setAssetid(String assetid) {
        this.assetid = assetid;
    }

    @Column(name = "propertyid", length = 32)
    public String getPropertyid() {
        return propertyid;
    }

    public void setPropertyid(String propertyid) {
        this.propertyid = propertyid;
    }

    @Column(name = "devicename", length = 64)
    public String getDevicename() {
        return devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    @Column(name = "tagvalue", length = 255)
    public String getTagvalue() {
        return tagvalue;
    }

    public void setTagvalue(String tagvalue) {
        this.tagvalue = tagvalue;
    }

    @Column(name = "oldvalue", length = 255)
    public String getOldvalue() {
        return oldvalue;
    }

    public void setOldvalue(String oldvalue) {
        this.oldvalue = oldvalue;
    }

    @Column(name = "operatorname", length = 64)
    public String getOperatorname() {
        return operatorname;
    }

    public void setOperatorname(String operatorname) {
        this.operatorname = operatorname;
    }

    @Column(name = "reviewcontent", length = 255)
    public String getReviewcontent() {
        return reviewcontent;
    }

    public void setReviewcontent(String reviewcontent) {
        this.reviewcontent = reviewcontent;
    }

    @Column(name = "reviewer", length = 64)
    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }
}
