package com.energicube.eno.monitor.model;


import javax.persistence.*;

/**
 *
 */
@Entity
@Table(name = "OKCGROUP", schema = "zclfsys")
public class OkcGroup implements java.io.Serializable {

    private long ugroupid;
    private String groupname;
    private String description;
    private Boolean hasld;
    private Boolean authallsites;
    private Boolean authallpolicy;
    private Boolean independent;
    private String langcode;

    public OkcGroup() {
    }

    public OkcGroup(long ugroupid) {
        this.ugroupid = ugroupid;
    }

    public OkcGroup(long ugroupid, String groupname, String description,
                    Boolean hasld, Boolean authallsites, Boolean authallpolicy,
                    Boolean independent, String langcode) {
        this.ugroupid = ugroupid;
        this.groupname = groupname;
        this.description = description;
        this.hasld = hasld;
        this.authallsites = authallsites;
        this.authallpolicy = authallpolicy;
        this.independent = independent;
        this.langcode = langcode;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UGROUPID", unique = true, nullable = false)
    public long getUgroupid() {
        return this.ugroupid;
    }

    public void setUgroupid(long ugroupid) {
        this.ugroupid = ugroupid;
    }

    @Column(name = "GROUPNAME", length = 30)
    public String getGroupname() {
        return this.groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
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

    @Column(name = "AUTHALLSITES")
    public Boolean getAuthallsites() {
        return this.authallsites;
    }

    public void setAuthallsites(Boolean authallsites) {
        this.authallsites = authallsites;
    }

    @Column(name = "AUTHALLPOLICY")
    public Boolean getAuthallpolicy() {
        return this.authallpolicy;
    }

    public void setAuthallpolicy(Boolean authallpolicy) {
        this.authallpolicy = authallpolicy;
    }

    @Column(name = "INDEPENDENT")
    public Boolean getIndependent() {
        return this.independent;
    }

    public void setIndependent(Boolean independent) {
        this.independent = independent;
    }

    @Column(name = "LANGCODE", length = 6)
    public String getLangcode() {
        return this.langcode;
    }

    public void setLangcode(String langcode) {
        this.langcode = langcode;
    }

}
