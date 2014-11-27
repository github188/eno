package com.energicube.eno.asset.model;


import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * 计量器组
 */
@Entity
@Table(name = "METERGROUP", schema = "zclfsys")
public class MeterGroup implements java.io.Serializable {

    private static final long serialVersionUID = 8016481764632559700L;
    private long metergroupid;
    private String description;
    private String groupname;
    private Boolean hasld;
    private String langcode;

    public MeterGroup() {
    }

    public MeterGroup(long metergroupid) {
        this.metergroupid = metergroupid;
    }

    public MeterGroup(long metergroupid, String description, String groupname,
                      Boolean hasld, String langcode) {
        this.metergroupid = metergroupid;
        this.description = description;
        this.groupname = groupname;
        this.hasld = hasld;
        this.langcode = langcode;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "METERGROUPID", unique = true, nullable = false)
    public long getMetergroupid() {
        return this.metergroupid;
    }

    public void setMetergroupid(long metergroupid) {
        this.metergroupid = metergroupid;
    }

    @Column(name = "DESCRIPTION", length = 100)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @NotEmpty
    @Column(name = "GROUPNAME", length = 10)
    public String getGroupname() {
        return this.groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
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

}
