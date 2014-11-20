package com.energicube.eno.asset.model;


import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * 计量单位定义
 */
@Entity
@Table(name = "MEASUREUNIT", schema = "dbo")
public class MeasureUnit implements java.io.Serializable {

    private static final long serialVersionUID = 4423527293423888936L;
    private long measureunituid;
    private String abbreviation;
    private String contentuid;
    private String description;
    private String measureunitid;
    private String orgid;
    private String siteid;

    public MeasureUnit() {
    }

    public MeasureUnit(long measureunituid) {
        this.measureunituid = measureunituid;
    }

    public MeasureUnit(long measureunituid, String abbreviation,
                       String contentuid, String description, String measureunitid,
                       String orgid, String siteid) {
        this.measureunituid = measureunituid;
        this.abbreviation = abbreviation;
        this.contentuid = contentuid;
        this.description = description;
        this.measureunitid = measureunitid;
        this.orgid = orgid;
        this.siteid = siteid;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEASUREUNITUID", unique = true, nullable = false)
    public long getMeasureunituid() {
        return this.measureunituid;
    }

    public void setMeasureunituid(long measureunituid) {
        this.measureunituid = measureunituid;
    }

    @Column(name = "ABBREVIATION", length = 8)
    public String getAbbreviation() {
        return this.abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    @Column(name = "CONTENTUID", length = 50)
    public String getContentuid() {
        return this.contentuid;
    }

    public void setContentuid(String contentuid) {
        this.contentuid = contentuid;
    }

    @Column(name = "DESCRIPTION", length = 100)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotEmpty
    @Column(name = "MEASUREUNITID", length = 16)
    public String getMeasureunitid() {
        return this.measureunitid;
    }

    public void setMeasureunitid(String measureunitid) {
        this.measureunitid = measureunitid;
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
