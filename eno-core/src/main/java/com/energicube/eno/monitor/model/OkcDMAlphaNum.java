package com.energicube.eno.monitor.model;


import javax.persistence.*;

/**
 *
 */
@Entity
@Table(name = "OKCDMALPHANUM", schema = "zclfsys")
public class OkcDMAlphaNum implements java.io.Serializable {

    private static final long serialVersionUID = 8766169278084507271L;
    private long okcdmalphanumid;
    private String dictid;
    private String description;
    private String orgid;
    private String siteid;
    private String dmvalue;

    public OkcDMAlphaNum() {
    }

    public OkcDMAlphaNum(long okcdmalphanumid, String dictid, String dmvalue) {
        this.okcdmalphanumid = okcdmalphanumid;
        this.dictid = dictid;
        this.dmvalue = dmvalue;
    }

    public OkcDMAlphaNum(long okcdmalphanumid, String dictid,
                         String description, String orgid, String siteid, String dmvalue) {
        this.okcdmalphanumid = okcdmalphanumid;
        this.dictid = dictid;
        this.description = description;
        this.orgid = orgid;
        this.siteid = siteid;
        this.dmvalue = dmvalue;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OKCDMALPHANUMID", unique = true, nullable = false)
    public long getOkcdmalphanumid() {
        return this.okcdmalphanumid;
    }

    public void setOkcdmalphanumid(long okcdmalphanumid) {
        this.okcdmalphanumid = okcdmalphanumid;
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

    @Column(name = "DMVALUE", nullable = false, length = 254)
    public String getDmvalue() {
        return this.dmvalue;
    }

    public void setDmvalue(String dmvalue) {
        this.dmvalue = dmvalue;
    }

}
