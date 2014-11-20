package com.energicube.eno.asset.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * 资产属性模板
 */
@Entity
@Table(name = "CLASSSPEC", schema = "dbo")
public class ClassSpec implements java.io.Serializable {

    private static final long serialVersionUID = 4445157212064167093L;
    private long classspecid;
    private Boolean applydownhier = false;
    private Long assetattributeid;
    private String assetattrid;
    private String attrdescprefix;
    private String classstructureid;
    private String domainid;
    private String inheritedfrom;
    private Integer inheritedfromid;
    private String lookupname;
    private String measureunitid;
    private String orgid;
    private String section;
    private String siteid;
    private String tableattribute;
    private String description;

    public ClassSpec() {
    }

    public ClassSpec(long classspecid) {
        this.classspecid = classspecid;
    }

    public ClassSpec(long classspecid, Boolean applydownhier,
                     Long assetattributeid, String assetattrid, String attrdescprefix,
                     String classstructureid, String domainid, String inheritedfrom,
                     Integer inheritedfromid, String lookupname, String measureunitid,
                     String orgid, String section, String siteid, String tableattribute,
                     String description) {
        this.classspecid = classspecid;
        this.applydownhier = applydownhier;
        this.assetattributeid = assetattributeid;
        this.assetattrid = assetattrid;
        this.attrdescprefix = attrdescprefix;
        this.classstructureid = classstructureid;
        this.domainid = domainid;
        this.inheritedfrom = inheritedfrom;
        this.inheritedfromid = inheritedfromid;
        this.lookupname = lookupname;
        this.measureunitid = measureunitid;
        this.orgid = orgid;
        this.section = section;
        this.siteid = siteid;
        this.tableattribute = tableattribute;
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLASSSPECID", unique = true, nullable = false)
    public long getClassspecid() {
        return this.classspecid;
    }

    public void setClassspecid(long classspecid) {
        this.classspecid = classspecid;
    }

    @Column(name = "APPLYDOWNHIER")
    public Boolean getApplydownhier() {
        return this.applydownhier;
    }

    public void setApplydownhier(Boolean applydownhier) {
        this.applydownhier = applydownhier;
    }

    @Column(name = "ASSETATTRIBUTEID")
    public Long getAssetattributeid() {
        return this.assetattributeid;
    }

    public void setAssetattributeid(Long assetattributeid) {
        this.assetattributeid = assetattributeid;
    }

    @NotEmpty
    @Column(name = "ASSETATTRID", length = 8)
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

    @NotEmpty
    @Column(name = "CLASSSTRUCTUREID", length = 20)
    public String getClassstructureid() {
        return this.classstructureid;
    }

    public void setClassstructureid(String classstructureid) {
        this.classstructureid = classstructureid;
    }

    @Column(name = "DOMAINID", length = 18)
    public String getDomainid() {
        return this.domainid;
    }

    public void setDomainid(String domainid) {
        this.domainid = domainid;
    }

    @Column(name = "INHERITEDFROM", length = 254)
    public String getInheritedfrom() {
        return this.inheritedfrom;
    }

    public void setInheritedfrom(String inheritedfrom) {
        this.inheritedfrom = inheritedfrom;
    }

    @Column(name = "INHERITEDFROMID")
    public Integer getInheritedfromid() {
        return this.inheritedfromid;
    }

    public void setInheritedfromid(Integer inheritedfromid) {
        this.inheritedfromid = inheritedfromid;
    }

    @Column(name = "LOOKUPNAME", length = 30)
    public String getLookupname() {
        return this.lookupname;
    }

    public void setLookupname(String lookupname) {
        this.lookupname = lookupname;
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

    @Column(name = "TABLEATTRIBUTE", length = 50)
    public String getTableattribute() {
        return this.tableattribute;
    }

    public void setTableattribute(String tableattribute) {
        this.tableattribute = tableattribute;
    }

    @Column(name = "description", length = 100)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
