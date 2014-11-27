package com.energicube.eno.asset.model;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 资产使用的计量器
 */
@Entity
@Table(name = "ASSETMETER", schema = "zclfsys")
public class AssetMeter implements java.io.Serializable {

    private static final long serialVersionUID = 4176483029129021687L;
    private int assetmeterid;
    private Boolean active = true;
    private String assetnum;
    private BigDecimal average = new BigDecimal(0);
    private String avgcalcmethod = "";
    private String basemeasureunitid = "";
    private String changeby;
    private DateTime changedate;
    private Boolean hasld = false;
    private String langcode = "";
    private String lastreading = "";
    private DateTime lastreadingdate = DateTime.now();
    private String lastreadinginspctr = "";
    private BigDecimal lifetodate = new BigDecimal(0);
    private String measureunitid = "";
    private String metername;
    private String orgid = "";
    private String pointnum = "";
    private String readingtype = "";
    private String remarks = "";
    private String rolldownsource = "";
    private BigDecimal rollover = new BigDecimal(0);
    private Integer sequence = 0;
    private BigDecimal sinceinstall = new BigDecimal(0);
    private BigDecimal sincelastinspect = new BigDecimal(0);
    private BigDecimal sincelastoverhaul = new BigDecimal(0);
    private BigDecimal sincelastrepair = new BigDecimal(0);
    private String siteid = "";
    private Integer slidingwindowsize = 0;

    public AssetMeter() {
    }

    public AssetMeter(int assetmeterid, String changeby, String langcode,
                      String orgid) {
        this.assetmeterid = assetmeterid;
        this.changeby = changeby;
        this.langcode = langcode;
        this.orgid = orgid;
    }

    public AssetMeter(int assetmeterid, Boolean active, String assetnum,
                      BigDecimal average, String avgcalcmethod, String basemeasureunitid,
                      String changeby, DateTime changedate, Boolean hasld,
                      String langcode, String lastreading, DateTime lastreadingdate,
                      String lastreadinginspctr, BigDecimal lifetodate,
                      String measureunitid, String metername, String orgid,
                      String pointnum, String readingtype, String remarks,
                      String rolldownsource, BigDecimal rollover, Integer sequence,
                      BigDecimal sinceinstall, BigDecimal sincelastinspect,
                      BigDecimal sincelastoverhaul, BigDecimal sincelastrepair,
                      String siteid, Integer slidingwindowsize) {
        this.assetmeterid = assetmeterid;
        this.active = active;
        this.assetnum = assetnum;
        this.average = average;
        this.avgcalcmethod = avgcalcmethod;
        this.basemeasureunitid = basemeasureunitid;
        this.changeby = changeby;
        this.changedate = changedate;
        this.hasld = hasld;
        this.langcode = langcode;
        this.lastreading = lastreading;
        this.lastreadingdate = lastreadingdate;
        this.lastreadinginspctr = lastreadinginspctr;
        this.lifetodate = lifetodate;
        this.measureunitid = measureunitid;
        this.metername = metername;
        this.orgid = orgid;
        this.pointnum = pointnum;
        this.readingtype = readingtype;
        this.remarks = remarks;
        this.rolldownsource = rolldownsource;
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
    @Column(name = "ASSETMETERID", unique = true, nullable = false)
    public int getAssetmeterid() {
        return this.assetmeterid;
    }

    public void setAssetmeterid(int assetmeterid) {
        this.assetmeterid = assetmeterid;
    }

    @Column(name = "ACTIVE")
    public Boolean getActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @NotEmpty
    @Column(name = "ASSETNUM", length = 12)
    public String getAssetnum() {
        return this.assetnum;
    }

    public void setAssetnum(String assetnum) {
        this.assetnum = assetnum;
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

    @Column(name = "BASEMEASUREUNITID", length = 16)
    public String getBasemeasureunitid() {
        return this.basemeasureunitid;
    }

    public void setBasemeasureunitid(String basemeasureunitid) {
        this.basemeasureunitid = basemeasureunitid;
    }

    @Column(name = "CHANGEBY", nullable = false, length = 30)
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

    @Column(name = "LANGCODE", nullable = false, length = 4)
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

    @Column(name = "MEASUREUNITID", length = 16)
    public String getMeasureunitid() {
        return this.measureunitid;
    }

    public void setMeasureunitid(String measureunitid) {
        this.measureunitid = measureunitid;
    }

    @NotEmpty
    @Column(name = "METERNAME", length = 30)
    public String getMetername() {
        return this.metername;
    }

    public void setMetername(String metername) {
        this.metername = metername;
    }

    @Column(name = "ORGID", nullable = false, length = 8)
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

    @Column(name = "ROLLDOWNSOURCE", length = 14)
    public String getRolldownsource() {
        return this.rolldownsource;
    }

    public void setRolldownsource(String rolldownsource) {
        this.rolldownsource = rolldownsource;
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

    @Column(name = "SINCEINSTALL", precision = 11, scale = 4)
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

    @Column(name = "SITEID", length = 8)
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

    @Override
    public String toString() {
        return "AssetMeter [assetmeterid=" + assetmeterid + ", active="
                + active + ", assetnum=" + assetnum + ", average=" + average
                + ", avgcalcmethod=" + avgcalcmethod + ", basemeasureunitid="
                + basemeasureunitid + ", changeby=" + changeby
                + ", changedate=" + changedate + ", hasld=" + hasld
                + ", langcode=" + langcode + ", lastreading=" + lastreading
                + ", lastreadingdate=" + lastreadingdate
                + ", lastreadinginspctr=" + lastreadinginspctr
                + ", lifetodate=" + lifetodate + ", measureunitid="
                + measureunitid + ", metername=" + metername + ", orgid="
                + orgid + ", pointnum=" + pointnum + ", readingtype="
                + readingtype + ", remarks=" + remarks + ", rolldownsource="
                + rolldownsource + ", rollover=" + rollover + ", sequence="
                + sequence + ", sinceinstall=" + sinceinstall
                + ", sincelastinspect=" + sincelastinspect
                + ", sincelastoverhaul=" + sincelastoverhaul
                + ", sincelastrepair=" + sincelastrepair + ", siteid=" + siteid
                + ", slidingwindowsize=" + slidingwindowsize + "]";
    }

}
