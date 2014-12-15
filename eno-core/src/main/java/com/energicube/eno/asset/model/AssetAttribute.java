package com.energicube.eno.asset.model;


import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * 资产属性
 */
@Entity
@Table(name = "ASSETATTRIBUTE")
public class AssetAttribute implements java.io.Serializable {

    private static final long serialVersionUID = -587726797455763284L;
    private long assetattributeid;
    private String assetattrid;
    private String attrdescprefix;
    private String datatype;
    private String description;
    private String domainid;
    private String measureunitid;
    private String orgid;
    private String siteid;

    public AssetAttribute() {
    }

    public AssetAttribute(long assetattributeid) {
        this.assetattributeid = assetattributeid;
    }


    public AssetAttribute(long assetattributeid, String assetattrid,
                          String attrdescprefix, String datatype, String description,
                          String domainid, String measureunitid, String orgid, String siteid) {
        this.assetattributeid = assetattributeid;
        this.assetattrid = assetattrid;
        this.attrdescprefix = attrdescprefix;
        this.datatype = datatype;
        this.description = description;
        this.domainid = domainid;
        this.measureunitid = measureunitid;
        this.orgid = orgid;
        this.siteid = siteid;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ASSETATTRIBUTEID", unique = true, nullable = false)
    public long getAssetattributeid() {
        return this.assetattributeid;
    }

    public void setAssetattributeid(long assetattributeid) {
        this.assetattributeid = assetattributeid;
    }

    @NotEmpty
    @Column(name = "ASSETATTRID", length = 20)
    public String getAssetattrid() {
        return this.assetattrid;
    }

    public void setAssetattrid(String assetattrid) {
        this.assetattrid = assetattrid;
    }

    @Column(name = "ATTRDESCPREFIX", length = 8)
    public String getAttrdescprefix() {
        return this.attrdescprefix;
    }

    public void setAttrdescprefix(String attrdescprefix) {
        this.attrdescprefix = attrdescprefix;
    }

    @Column(name = "DATATYPE", length = 8)
    public String getDatatype() {
        return this.datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    @Column(name = "DESCRIPTION", length = 100)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "DOMAINID", length = 18)
    public String getDomainid() {
        return this.domainid;
    }

    public void setDomainid(String domainid) {
        this.domainid = domainid;
    }

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


    @Override
    public String toString() {
        return "AssetAttribute [assetattributeid=" + assetattributeid
                + ", assetattrid=" + assetattrid + ", attrdescprefix="
                + attrdescprefix + ", datatype=" + datatype + ", description="
                + description + ", domainid=" + domainid + ", measureunitid="
                + measureunitid + ", orgid=" + orgid + ", siteid=" + siteid
                + "]";
    }
}
