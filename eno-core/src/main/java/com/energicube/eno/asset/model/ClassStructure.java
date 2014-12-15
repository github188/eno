package com.energicube.eno.asset.model;


import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * 类别层次结构定义
 */
@Entity
@Table(name = "CLASSSTRUCTURE")
public class ClassStructure implements java.io.Serializable {

    private static final long serialVersionUID = -4428168197446992139L;
    private long classstructureuid;
    private String classificationid;
    private String classstructureid;
    private String description;
    private Boolean genassetdesc = true;
    private Boolean haschildren;
    private Boolean hasld;
    private String langcode;
    private String orgid;
    private String parent;
    private String siteid;
    private String type;
    private Boolean useclassindesc;

    public ClassStructure() {
    }

    public ClassStructure(long classstructureuid) {
        this.classstructureuid = classstructureuid;
    }

    public ClassStructure(long classstructureuid, String classificationid,
                          String classstructureid, String description, Boolean genassetdesc,
                          Boolean haschildren, Boolean hasld, String langcode, String orgid,
                          String parent, String siteid, String type, Boolean useclassindesc) {
        this.classstructureuid = classstructureuid;
        this.classificationid = classificationid;
        this.classstructureid = classstructureid;
        this.description = description;
        this.genassetdesc = genassetdesc;
        this.haschildren = haschildren;
        this.hasld = hasld;
        this.langcode = langcode;
        this.orgid = orgid;
        this.parent = parent;
        this.siteid = siteid;
        this.type = type;
        this.useclassindesc = useclassindesc;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLASSSTRUCTUREUID", unique = true, nullable = false)
    public long getClassstructureuid() {
        return this.classstructureuid;
    }

    public void setClassstructureuid(long classstructureuid) {
        this.classstructureuid = classstructureuid;
    }

    @NotEmpty
    @Column(name = "CLASSIFICATIONID", length = 200)
    public String getClassificationid() {
        return this.classificationid;
    }

    public void setClassificationid(String classificationid) {
        this.classificationid = classificationid;
    }


    @Column(name = "CLASSSTRUCTUREID", length = 20)
    public String getClassstructureid() {
        return this.classstructureid;
    }

    public void setClassstructureid(String classstructureid) {
        this.classstructureid = classstructureid;
    }

    @Column(name = "DESCRIPTION", length = 100)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "GENASSETDESC")
    public Boolean getGenassetdesc() {
        return this.genassetdesc;
    }

    public void setGenassetdesc(Boolean genassetdesc) {
        this.genassetdesc = genassetdesc;
    }

    @Column(name = "HASCHILDREN")
    public Boolean getHaschildren() {
        return this.haschildren;
    }

    public void setHaschildren(Boolean haschildren) {
        this.haschildren = haschildren;
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

    @Column(name = "ORGID", length = 8)
    public String getOrgid() {
        return this.orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    @Column(name = "PARENT", length = 20)
    public String getParent() {
        return this.parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    @Column(name = "SITEID", length = 10)
    public String getSiteid() {
        return this.siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

    @Column(name = "TYPE", length = 8)
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Column(name = "USECLASSINDESC")
    public Boolean getUseclassindesc() {
        return this.useclassindesc;
    }

    public void setUseclassindesc(Boolean useclassindesc) {
        this.useclassindesc = useclassindesc;
    }

}
