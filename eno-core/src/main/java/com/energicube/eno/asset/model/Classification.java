package com.energicube.eno.asset.model;


import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * 资产类别定义
 */
@Entity
@Table(name = "CLASSIFICATION", schema = "dbo")
public class Classification implements java.io.Serializable {

    private static final long serialVersionUID = 3733577649389449972L;
    private long classificationuid;
    private String classificationid;
    private String description;
    private Boolean hasld;
    private String orgid;
    private String siteid;

    public Classification() {
    }

    public Classification(long classificationuid) {
        this.classificationuid = classificationuid;
    }

    public Classification(long classificationuid, String classificationid,
                          String description, Boolean hasld, String orgid, String siteid) {
        this.classificationuid = classificationuid;
        this.classificationid = classificationid;
        this.description = description;
        this.hasld = hasld;
        this.orgid = orgid;
        this.siteid = siteid;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLASSIFICATIONUID", unique = true, nullable = false)
    public long getClassificationuid() {
        return this.classificationuid;
    }

    public void setClassificationuid(long classificationuid) {
        this.classificationuid = classificationuid;
    }

    @NotEmpty
    @Column(name = "CLASSIFICATIONID", length = 20)
    public String getClassificationid() {
        return this.classificationid;
    }

    public void setClassificationid(String classificationid) {
        this.classificationid = classificationid;
    }

    @Column(name = "DESCRIPTION", length = 100)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "HASLD")
    public Boolean getHasld() {
        return this.hasld;
    }

    public void setHasld(Boolean hasld) {
        this.hasld = hasld;
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
