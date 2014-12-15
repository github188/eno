package com.energicube.eno.asset.model;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 附加到位置的计量器
 */
@Entity
@Table(name = "LOCATIONMETER")
public class LocationMeter implements java.io.Serializable {

    private static final long serialVersionUID = -3234611765138268877L;
    private long locationmeterid;
    private Boolean active;
    private BigDecimal average = new BigDecimal(0.00);
    private String avgcalcmethod;
    private String changeby;
    private DateTime changedate;
    private Boolean hasld;
    private String langcode;
    private String lastreading;
    private DateTime lastreadingdate;
    private String lastreadinginspctr;
    private BigDecimal lifetodate = new BigDecimal(0.00);
    private String location;
    private String measureunitid;
    private String metername;
    private String orgid;
    private String pointnum;
    private String readingtype;
    private String remarks;
    private BigDecimal rollover;
    private Integer sequence;
    private BigDecimal sinceinstall = new BigDecimal(0.00);
    private BigDecimal sincelastinspect = new BigDecimal(0.00);
    private BigDecimal sincelastoverhaul = new BigDecimal(0.00);
    private BigDecimal sincelastrepair = new BigDecimal(0.00);
    private String siteid = "";
    private Integer slidingwindowsize = 0;

    public LocationMeter() {
    }

    public LocationMeter(long locationmeterid, BigDecimal sinceinstall,
                         String siteid) {
        this.locationmeterid = locationmeterid;
        this.sinceinstall = sinceinstall;
        this.siteid = siteid;
    }

    public LocationMeter(long locationmeterid, Boolean active,
                         BigDecimal average, String avgcalcmethod, String changeby,
                         DateTime changedate, Boolean hasld, String langcode,
                         String lastreading, DateTime lastreadingdate,
                         String lastreadinginspctr, BigDecimal lifetodate, String location,
                         String measureunitid, String metername, String orgid,
                         String pointnum, String readingtype, String remarks,
                         BigDecimal rollover, Integer sequence, BigDecimal sinceinstall,
                         BigDecimal sincelastinspect, BigDecimal sincelastoverhaul,
                         BigDecimal sincelastrepair, String siteid, Integer slidingwindowsize) {
        this.locationmeterid = locationmeterid;
        this.active = active;
        this.average = average;
        this.avgcalcmethod = avgcalcmethod;
        this.changeby = changeby;
        this.changedate = changedate;
        this.hasld = hasld;
        this.langcode = langcode;
        this.lastreading = lastreading;
        this.lastreadingdate = lastreadingdate;
        this.lastreadinginspctr = lastreadinginspctr;
        this.lifetodate = lifetodate;
        this.location = location;
        this.measureunitid = measureunitid;
        this.metername = metername;
        this.orgid = orgid;
        this.pointnum = pointnum;
        this.readingtype = readingtype;
        this.remarks = remarks;
        this.rollover = rollover;
        this.sequence = sequence;
        this.sinceinstall = sinceinstall;
        this.sincelastinspect = sincelastinspect;
        this.sincelastoverhaul = sincelastoverhaul;
        this.sincelastrepair = sincelastrepair;
        this.siteid = siteid;
        this.slidingwindowsize = slidingwindowsize;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOCATIONMETERID", unique = true, nullable = false)
    public long getLocationmeterid() {
        return this.locationmeterid;
    }

    public void setLocationmeterid(long locationmeterid) {
        this.locationmeterid = locationmeterid;
    }

    @Column(name = "ACTIVE")
    public Boolean getActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Column(name = "AVERAGE", precision = 11, scale = 4)
    public BigDecimal getAverage() {
        return this.average;
    }

    public void setAverage(BigDecimal average) {
        this.average = average;
    }

    @Column(name = "AVGCALCMETHOD", length = 25)
    public String getAvgcalcmethod() {
        return this.avgcalcmethod;
    }

    public void setAvgcalcmethod(String avgcalcmethod) {
        this.avgcalcmethod = avgcalcmethod;
    }

    @Column(name = "CHANGEBY", length = 30)
    public String getChangeby() {
        return this.changeby;
    }

    public void setChangeby(String changeby) {
        this.changeby = changeby;
    }

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "CHANGEDATE", length = 23)
    public DateTime getChangedate() {
        return this.changedate;
    }

    public void setChangedate(DateTime changedate) {
        this.changedate = changedate;
    }

    @Column(name = "HASLD")
    public Boolean getHasld() {
        return this.hasld;
    }

    public void setHasld(Boolean hasld) {
        this.hasld = hasld;
    }

    @Column(name = "LANGCODE", length = 4)
    public String getLangcode() {
        return this.langcode;
    }

    public void setLangcode(String langcode) {
        this.langcode = langcode;
    }

    @Column(name = "LASTREADING", length = 18)
    public String getLastreading() {
        return this.lastreading;
    }

    public void setLastreading(String lastreading) {
        this.lastreading = lastreading;
    }

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "LASTREADINGDATE", length = 23)
    public DateTime getLastreadingdate() {
        return this.lastreadingdate;
    }

    public void setLastreadingdate(DateTime lastreadingdate) {
        this.lastreadingdate = lastreadingdate;
    }

    @Column(name = "LASTREADINGINSPCTR", length = 30)
    public String getLastreadinginspctr() {
        return this.lastreadinginspctr;
    }

    public void setLastreadinginspctr(String lastreadinginspctr) {
        this.lastreadinginspctr = lastreadinginspctr;
    }

    @Column(name = "LIFETODATE", precision = 11, scale = 4)
    public BigDecimal getLifetodate() {
        return this.lifetodate;
    }

    public void setLifetodate(BigDecimal lifetodate) {
        this.lifetodate = lifetodate;
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

    @Column(name = "ORGID", length = 8)
    public String getOrgid() {
        return this.orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    @Column(name = "POINTNUM", length = 8)
    public String getPointnum() {
        return this.pointnum;
    }

    public void setPointnum(String pointnum) {
        this.pointnum = pointnum;
    }

    @Column(name = "READINGTYPE", length = 10)
    public String getReadingtype() {
        return this.readingtype;
    }

    public void setReadingtype(String readingtype) {
        this.readingtype = readingtype;
    }

    @Column(name = "REMARKS", length = 50)
    public String getRemarks() {
        return this.remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Column(name = "ROLLOVER", precision = 11, scale = 4)
    public BigDecimal getRollover() {
        return this.rollover;
    }

    public void setRollover(BigDecimal rollover) {
        this.rollover = rollover;
    }

    @Column(name = "SEQUENCE")
    public Integer getSequence() {
        return this.sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    @Column(name = "SINCEINSTALL", nullable = false, precision = 11, scale = 4)
    public BigDecimal getSinceinstall() {
        return this.sinceinstall;
    }

    public void setSinceinstall(BigDecimal sinceinstall) {
        this.sinceinstall = sinceinstall;
    }

    @Column(name = "SINCELASTINSPECT", precision = 11, scale = 4)
    public BigDecimal getSincelastinspect() {
        return this.sincelastinspect;
    }

    public void setSincelastinspect(BigDecimal sincelastinspect) {
        this.sincelastinspect = sincelastinspect;
    }

    @Column(name = "SINCELASTOVERHAUL", precision = 11, scale = 4)
    public BigDecimal getSincelastoverhaul() {
        return this.sincelastoverhaul;
    }

    public void setSincelastoverhaul(BigDecimal sincelastoverhaul) {
        this.sincelastoverhaul = sincelastoverhaul;
    }

    @Column(name = "SINCELASTREPAIR", precision = 11, scale = 4)
    public BigDecimal getSincelastrepair() {
        return this.sincelastrepair;
    }

    public void setSincelastrepair(BigDecimal sincelastrepair) {
        this.sincelastrepair = sincelastrepair;
    }

    @Column(name = "SITEID", nullable = false, length = 8)
    public String getSiteid() {
        return this.siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

    @Column(name = "SLIDINGWINDOWSIZE")
    public Integer getSlidingwindowsize() {
        return this.slidingwindowsize;
    }

    public void setSlidingwindowsize(Integer slidingwindowsize) {
        this.slidingwindowsize = slidingwindowsize;
    }

}
