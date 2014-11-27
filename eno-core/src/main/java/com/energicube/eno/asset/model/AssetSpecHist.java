package com.energicube.eno.asset.model;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 资产规范变更历史
 */
@Entity
@Table(name = "ASSETSPECHIST", schema = "zclfsys")
public class AssetSpecHist implements java.io.Serializable {

    private static final long serialVersionUID = -2558921454068984785L;
    private long assetspechistid;
    private String alnvalue;
    private String assetattrid;
    private String assetnum;
    private Long assetspecid;
    private String basemeasureunitid;
    private String changeby;
    private Date changedate;
    private Long classspecid;
    private String classstructureid;
    private Date createddate;
    private Integer displaysequence;
    private String linkedtoattribute;
    private String linkedtosection;
    private boolean mandatory;
    private String measureunitid;
    private BigDecimal numvalue;
    private String orgid;
    private Date removeddate;
    private String section;
    private String siteid;

    public AssetSpecHist() {
    }

    public AssetSpecHist(long assetspechistid, boolean mandatory, String orgid) {
        this.assetspechistid = assetspechistid;
        this.mandatory = mandatory;
        this.orgid = orgid;
    }

    public AssetSpecHist(long assetspechistid, String alnvalue,
                         String assetattrid, String assetnum, Long assetspecid,
                         String basemeasureunitid, String changeby, Date changedate,
                         Long classspecid, String classstructureid, Date createddate,
                         Integer displaysequence, String linkedtoattribute,
                         String linkedtosection, boolean mandatory, String measureunitid,
                         BigDecimal numvalue, String orgid, Date removeddate,
                         String section, String siteid) {
        this.assetspechistid = assetspechistid;
        this.alnvalue = alnvalue;
        this.assetattrid = assetattrid;
        this.assetnum = assetnum;
        this.assetspecid = assetspecid;
        this.basemeasureunitid = basemeasureunitid;
        this.changeby = changeby;
        this.changedate = changedate;
        this.classspecid = classspecid;
        this.classstructureid = classstructureid;
        this.createddate = createddate;
        this.displaysequence = displaysequence;
        this.linkedtoattribute = linkedtoattribute;
        this.linkedtosection = linkedtosection;
        this.mandatory = mandatory;
        this.measureunitid = measureunitid;
        this.numvalue = numvalue;
        this.orgid = orgid;
        this.removeddate = removeddate;
        this.section = section;
        this.siteid = siteid;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ASSETSPECHISTID", unique = true, nullable = false)
    public long getAssetspechistid() {
        return this.assetspechistid;
    }

    public void setAssetspechistid(long assetspechistid) {
        this.assetspechistid = assetspechistid;
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

    @Column(name = "ASSETNUM", length = 12)
    public String getAssetnum() {
        return this.assetnum;
    }

    public void setAssetnum(String assetnum) {
        this.assetnum = assetnum;
    }

    @Column(name = "ASSETSPECID")
    public Long getAssetspecid() {
        return this.assetspecid;
    }

    public void setAssetspecid(Long assetspecid) {
        this.assetspecid = assetspecid;
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

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CHANGEDATE", length = 23)
    public Date getChangedate() {
        return this.changedate;
    }

    public void setChangedate(Date changedate) {
        this.changedate = changedate;
    }

    @Column(name = "CLASSSPECID")
    public Long getClassspecid() {
        return this.classspecid;
    }

    public void setClassspecid(Long classspecid) {
        this.classspecid = classspecid;
    }

    @Column(name = "CLASSSTRUCTUREID", length = 20)
    public String getClassstructureid() {
        return this.classstructureid;
    }

    public void setClassstructureid(String classstructureid) {
        this.classstructureid = classstructureid;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATEDDATE", length = 23)
    public Date getCreateddate() {
        return this.createddate;
    }

    public void setCreateddate(Date createddate) {
        this.createddate = createddate;
    }

    @Column(name = "DISPLAYSEQUENCE")
    public Integer getDisplaysequence() {
        return this.displaysequence;
    }

    public void setDisplaysequence(Integer displaysequence) {
        this.displaysequence = displaysequence;
    }

    @Column(name = "LINKEDTOATTRIBUTE", length = 8)
    public String getLinkedtoattribute() {
        return this.linkedtoattribute;
    }

    public void setLinkedtoattribute(String linkedtoattribute) {
        this.linkedtoattribute = linkedtoattribute;
    }

    @Column(name = "LINKEDTOSECTION", length = 10)
    public String getLinkedtosection() {
        return this.linkedtosection;
    }

    public void setLinkedtosection(String linkedtosection) {
        this.linkedtosection = linkedtosection;
    }

    @Column(name = "MANDATORY", nullable = false)
    public boolean isMandatory() {
        return this.mandatory;
    }

    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }

    @Column(name = "MEASUREUNITID", length = 16)
    public String getMeasureunitid() {
        return this.measureunitid;
    }

    public void setMeasureunitid(String measureunitid) {
        this.measureunitid = measureunitid;
    }

    @Column(name = "NUMVALUE", precision = 26, scale = 4)
    public BigDecimal getNumvalue() {
        return this.numvalue;
    }

    public void setNumvalue(BigDecimal numvalue) {
        this.numvalue = numvalue;
    }

    @Column(name = "ORGID", nullable = false, length = 8)
    public String getOrgid() {
        return this.orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REMOVEDDATE", length = 23)
    public Date getRemoveddate() {
        return this.removeddate;
    }

    public void setRemoveddate(Date removeddate) {
        this.removeddate = removeddate;
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

}
