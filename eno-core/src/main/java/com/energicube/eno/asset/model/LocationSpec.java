package com.energicube.eno.asset.model;


import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 位置技术参数模板
 */
@Entity
@Table(name = "LOCATIONSPEC", schema = "dbo")
public class LocationSpec implements java.io.Serializable {

    private static final long serialVersionUID = -7372575356556239223L;
    private long locationspecid;
    private String alnvalue;
    private String assetattrid;
    private String changeby;
    private String changedate;
    private String classstructureid;
    private Integer displaysequence;
    private String location;
    private Boolean mandatory;
    private String measureunitid;
    private BigDecimal numvalue;
    private String orgid;
    private String section;
    private String siteid;
    private String tablevalue;

    public LocationSpec() {
    }

    public LocationSpec(long locationspecid) {
        this.locationspecid = locationspecid;
    }

    public LocationSpec(long locationspecid, String alnvalue,
                        String assetattrid, String changeby, String changedate,
                        String classstructureid, Integer displaysequence, String location,
                        Boolean mandatory, String measureunitid, BigDecimal numvalue,
                        String orgid, String section, String siteid, String tablevalue) {
        this.locationspecid = locationspecid;
        this.alnvalue = alnvalue;
        this.assetattrid = assetattrid;
        this.changeby = changeby;
        this.changedate = changedate;
        this.classstructureid = classstructureid;
        this.displaysequence = displaysequence;
        this.location = location;
        this.mandatory = mandatory;
        this.measureunitid = measureunitid;
        this.numvalue = numvalue;
        this.orgid = orgid;
        this.section = section;
        this.siteid = siteid;
        this.tablevalue = tablevalue;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOCATIONSPECID", unique = true, nullable = false)
    public long getLocationspecid() {
        return this.locationspecid;
    }

    public void setLocationspecid(long locationspecid) {
        this.locationspecid = locationspecid;
    }

    @Column(name = "ALNVALUE", length = 254)
    public String getAlnvalue() {
        return this.alnvalue;
    }

    public void setAlnvalue(String alnvalue) {
        this.alnvalue = alnvalue;
    }

    @Column(name = "ASSETATTRID", length = 8)
    public String getAssetattrid() {
        return this.assetattrid;
    }

    public void setAssetattrid(String assetattrid) {
        this.assetattrid = assetattrid;
    }

    @Column(name = "CHANGEBY", length = 30)
    public String getChangeby() {
        return this.changeby;
    }

    public void setChangeby(String changeby) {
        this.changeby = changeby;
    }

    @Column(name = "CHANGEDATE", length = 10)
    public String getChangedate() {
        return this.changedate;
    }

    public void setChangedate(String changedate) {
        this.changedate = changedate;
    }

    @Column(name = "CLASSSTRUCTUREID", length = 20)
    public String getClassstructureid() {
        return this.classstructureid;
    }

    public void setClassstructureid(String classstructureid) {
        this.classstructureid = classstructureid;
    }

    @Column(name = "DISPLAYSEQUENCE")
    public Integer getDisplaysequence() {
        return this.displaysequence;
    }

    public void setDisplaysequence(Integer displaysequence) {
        this.displaysequence = displaysequence;
    }

    @Column(name = "LOCATION", length = 30)
    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Column(name = "MANDATORY")
    public Boolean getMandatory() {
        return this.mandatory;
    }

    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    @Column(name = "MEASUREUNITID", length = 16)
    public String getMeasureunitid() {
        return this.measureunitid;
    }

    public void setMeasureunitid(String measureunitid) {
        this.measureunitid = measureunitid;
    }

    @Column(name = "NUMVALUE", precision = 11, scale = 4)
    public BigDecimal getNumvalue() {
        return this.numvalue;
    }

    public void setNumvalue(BigDecimal numvalue) {
        this.numvalue = numvalue;
    }

    @Column(name = "ORGID", length = 8)
    public String getOrgid() {
        return this.orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    @Column(name = "SECTION", length = 10)
    public String getSection() {
        return this.section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    @Column(name = "SITEID", length = 8)
    public String getSiteid() {
        return this.siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

    @Column(name = "TABLEVALUE", length = 254)
    public String getTablevalue() {
        return this.tablevalue;
    }

    public void setTablevalue(String tablevalue) {
        this.tablevalue = tablevalue;
    }

}
