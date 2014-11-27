package com.energicube.eno.monitor.model;


import javax.persistence.*;

/**
 *
 */
@Entity
@Table(name = "OKCDMNUMRANGE", schema = "zclfsys")
public class OkcDMNumRange implements java.io.Serializable {

    private long okcdmnumrangeid;
    private String dictid;
    private String description;
    private Double rangeinterval;
    private Double rangemaximum;
    private Double rangeminimum;
    private Integer rangesegment;
    private String orgid;
    private String siteid;

    public OkcDMNumRange() {
    }

    public OkcDMNumRange(long okcdmnumrangeid, String dictid) {
        this.okcdmnumrangeid = okcdmnumrangeid;
        this.dictid = dictid;
    }

    public OkcDMNumRange(long okcdmnumrangeid, String dictid,
                         String description, Double rangeinterval, Double rangemaximum,
                         Double rangeminimum, Integer rangesegment, String orgid,
                         String siteid) {
        this.okcdmnumrangeid = okcdmnumrangeid;
        this.dictid = dictid;
        this.description = description;
        this.rangeinterval = rangeinterval;
        this.rangemaximum = rangemaximum;
        this.rangeminimum = rangeminimum;
        this.rangesegment = rangesegment;
        this.orgid = orgid;
        this.siteid = siteid;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OKCDMNUMRANGEID", unique = true, nullable = false)
    public long getOkcdmnumrangeid() {
        return this.okcdmnumrangeid;
    }

    public void setOkcdmnumrangeid(long okcdmnumrangeid) {
        this.okcdmnumrangeid = okcdmnumrangeid;
    }

    @Column(name = "DICTID", nullable = false, length = 18)
    public String getDictid() {
        return this.dictid;
    }

    public void setDictid(String dictid) {
        this.dictid = dictid;
    }

    @Column(name = "DESCRIPTION", length = 100)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "RANGEINTERVAL", precision = 53, scale = 0)
    public Double getRangeinterval() {
        return this.rangeinterval;
    }

    public void setRangeinterval(Double rangeinterval) {
        this.rangeinterval = rangeinterval;
    }

    @Column(name = "RANGEMAXIMUM", precision = 53, scale = 0)
    public Double getRangemaximum() {
        return this.rangemaximum;
    }

    public void setRangemaximum(Double rangemaximum) {
        this.rangemaximum = rangemaximum;
    }

    @Column(name = "RANGEMINIMUM", precision = 53, scale = 0)
    public Double getRangeminimum() {
        return this.rangeminimum;
    }

    public void setRangeminimum(Double rangeminimum) {
        this.rangeminimum = rangeminimum;
    }

    @Column(name = "RANGESEGMENT")
    public Integer getRangesegment() {
        return this.rangesegment;
    }

    public void setRangesegment(Integer rangesegment) {
        this.rangesegment = rangesegment;
    }

    @Column(name = "ORGID", length = 8)
    public String getOrgid() {
        return this.orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    @Column(name = "SITEID", length = 8)
    public String getSiteid() {
        return this.siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

}
