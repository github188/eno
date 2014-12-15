package com.energicube.eno.asset.model;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

/**
 * 资产当前使用的技术规范参数
 */
@Entity
@Table(name = "ASSETSPEC")
public class AssetSpec implements java.io.Serializable {

    private static final long serialVersionUID = 5631357740871480613L;
    private int assetspecid;
    private String alnvalue;
    private String assetattrid;
    private String assetnum;
    private String basemeasureunitid;
    private String changeby;
    private DateTime changedate;
    private String classstructureid;
    private Integer displaysequence;
    private Boolean mandatory;
    private String measureunitid;
    private Long numvalue;
    private String orgid = "";
    private String section = "";
    private String siteid = "";
    private String tablevalue;

    public AssetSpec() {
    }

    public AssetSpec(int assetspecid) {
        this.assetspecid = assetspecid;
    }

    public AssetSpec(int assetspecid, String alnvalue, String assetattrid,
                     String assetnum, String basemeasureunitid, String changeby,
                     DateTime changedate, String classstructureid,
                     Integer displaysequence, Boolean mandatory, String measureunitid,
                     Long numvalue, String orgid, String section, String siteid,
                     String tablevalue) {
        this.assetspecid = assetspecid;
        this.alnvalue = alnvalue;
        this.assetattrid = assetattrid;
        this.assetnum = assetnum;
        this.basemeasureunitid = basemeasureunitid;
        this.changeby = changeby;
        this.changedate = changedate;
        this.classstructureid = classstructureid;
        this.displaysequence = displaysequence;
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
    @Column(name = "ASSETSPECID", unique = true, nullable = false)
    public int getAssetspecid() {
        return this.assetspecid;
    }

    public void setAssetspecid(int assetspecid) {
        this.assetspecid = assetspecid;
    }

    @Column(name = "ALNVALUE", length = 254)
    public String getAlnvalue() {
        return this.alnvalue;
    }

    public void setAlnvalue(String alnvalue) {
        this.alnvalue = alnvalue;
    }

    @NotEmpty
    @Column(name = "ASSETATTRID", length = 20)
    public String getAssetattrid() {
        return this.assetattrid;
    }

    public void setAssetattrid(String assetattrid) {
        this.assetattrid = assetattrid;
    }

    @NotEmpty
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

    @Column(name = "NUMVALUE", precision = 18, scale = 0)
    public Long getNumvalue() {
        return this.numvalue;
    }

    public void setNumvalue(Long numvalue) {
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
