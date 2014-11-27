package com.energicube.eno.asset.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 位置上的计量器读数和观测值记录
 */
@Entity
@Table(name = "LOCMETERREADING", schema = "zclfsys")
public class LocMeterReading implements java.io.Serializable {

    private static final long serialVersionUID = -8587308440627547009L;
    private long meterreadingid;
    private BigDecimal delta;
    private Boolean didrollover;
    private String enterby;
    private Date enterdate;
    private String inspector;
    private String location;
    private String measureunitid;
    private String metername;
    private Boolean modified;
    private String orgid;
    private BigDecimal reading;
    private Date readingdate;
    private String readingtype;
    private String reason;
    private BigDecimal rollover;
    private String siteid;

    public LocMeterReading() {
    }

    public LocMeterReading(long meterreadingid) {
        this.meterreadingid = meterreadingid;
    }

    public LocMeterReading(long meterreadingid, BigDecimal delta,
                           Boolean didrollover, String enterby, Date enterdate,
                           String inspector, String location, String measureunitid,
                           String metername, Boolean modified, String orgid,
                           BigDecimal reading, Date readingdate, String readingtype,
                           String reason, BigDecimal rollover, String siteid) {
        this.meterreadingid = meterreadingid;
        this.delta = delta;
        this.didrollover = didrollover;
        this.enterby = enterby;
        this.enterdate = enterdate;
        this.inspector = inspector;
        this.location = location;
        this.measureunitid = measureunitid;
        this.metername = metername;
        this.modified = modified;
        this.orgid = orgid;
        this.reading = reading;
        this.readingdate = readingdate;
        this.readingtype = readingtype;
        this.reason = reason;
        this.rollover = rollover;
        this.siteid = siteid;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "METERREADINGID", unique = true, nullable = false)
    public long getMeterreadingid() {
        return this.meterreadingid;
    }

    public void setMeterreadingid(long meterreadingid) {
        this.meterreadingid = meterreadingid;
    }

    @Column(name = "DELTA", precision = 11, scale = 4)
    public BigDecimal getDelta() {
        return this.delta;
    }

    public void setDelta(BigDecimal delta) {
        this.delta = delta;
    }

    @Column(name = "DIDROLLOVER")
    public Boolean getDidrollover() {
        return this.didrollover;
    }

    public void setDidrollover(Boolean didrollover) {
        this.didrollover = didrollover;
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

    @Column(name = "MEASUREUNITID", length = 16)
    public String getMeasureunitid() {
        return this.measureunitid;
    }

    public void setMeasureunitid(String measureunitid) {
        this.measureunitid = measureunitid;
    }

    @Column(name = "METERNAME", length = 30)
    public String getMetername() {
        return this.metername;
    }

    public void setMetername(String metername) {
        this.metername = metername;
    }

    @Column(name = "MODIFIED")
    public Boolean getModified() {
        return this.modified;
    }

    public void setModified(Boolean modified) {
        this.modified = modified;
    }

    @Column(name = "ORGID", length = 8)
    public String getOrgid() {
        return this.orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    @Column(name = "READING", precision = 11, scale = 4)
    public BigDecimal getReading() {
        return this.reading;
    }

    public void setReading(BigDecimal reading) {
        this.reading = reading;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "READINGDATE", length = 23)
    public Date getReadingdate() {
        return this.readingdate;
    }

    public void setReadingdate(Date readingdate) {
        this.readingdate = readingdate;
    }

    @Column(name = "READINGTYPE", length = 10)
    public String getReadingtype() {
        return this.readingtype;
    }

    public void setReadingtype(String readingtype) {
        this.readingtype = readingtype;
    }

    @Column(name = "REASON", length = 254)
    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Column(name = "ROLLOVER", precision = 11, scale = 4)
    public BigDecimal getRollover() {
        return this.rollover;
    }

    public void setRollover(BigDecimal rollover) {
        this.rollover = rollover;
    }

    @Column(name = "SITEID", length = 8)
    public String getSiteid() {
        return this.siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

}
