package com.energicube.eno.asset.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * 位置系统定义
 */
@Entity
@Table(name = "LOCSYSTEM", schema = "dbo")
public class LocSystem implements java.io.Serializable {

    private static final long serialVersionUID = 3610930712851908736L;
    private long locsystemid;
    private String doctype;
    private String description;
    private String externalrefid;
    private boolean hasld;
    private String langcode;
    private boolean network;
    private String orgid;
    private String ownersysid;
    private boolean primarysystem;
    private String siteid;
    private String sourcesysid;

    private String systemid;

    public LocSystem() {
    }

    public LocSystem(long locsystemid, boolean hasld, String langcode,
                     boolean network, String orgid, boolean primarysystem,
                     String siteid, String systemid) {
        this.locsystemid = locsystemid;
        this.hasld = hasld;
        this.langcode = langcode;
        this.network = network;
        this.orgid = orgid;
        this.primarysystem = primarysystem;
        this.siteid = siteid;
        this.systemid = systemid;
    }

    public LocSystem(long locsystemid, String doctype, String description,
                     String externalrefid, boolean hasld, String langcode,
                     boolean network, String orgid, String ownersysid,
                     boolean primarysystem, String siteid, String sourcesysid,
                     String systemid) {
        this.locsystemid = locsystemid;
        this.doctype = doctype;
        this.description = description;
        this.externalrefid = externalrefid;
        this.hasld = hasld;
        this.langcode = langcode;
        this.network = network;
        this.orgid = orgid;
        this.ownersysid = ownersysid;
        this.primarysystem = primarysystem;
        this.siteid = siteid;
        this.sourcesysid = sourcesysid;
        this.systemid = systemid;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOCSYSTEMID", unique = true, nullable = false)
    public long getLocsystemid() {
        return this.locsystemid;
    }

    public void setLocsystemid(long locsystemid) {
        this.locsystemid = locsystemid;
    }

    @Column(name = "DOCTYPE", length = 16)
    public String getDoctype() {
        return this.doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    @Column(name = "DESCRIPTION", length = 100)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "EXTERNALREFID", length = 10)
    public String getExternalrefid() {
        return this.externalrefid;
    }

    public void setExternalrefid(String externalrefid) {
        this.externalrefid = externalrefid;
    }

    @Column(name = "HASLD", nullable = false)
    public boolean isHasld() {
        return this.hasld;
    }

    public void setHasld(boolean hasld) {
        this.hasld = hasld;
    }

    @Column(name = "LANGCODE", nullable = false, length = 8)
    public String getLangcode() {
        return this.langcode;
    }

    public void setLangcode(String langcode) {
        this.langcode = langcode;
    }

    @Column(name = "NETWORK", nullable = false)
    public boolean isNetwork() {
        return this.network;
    }

    public void setNetwork(boolean network) {
        this.network = network;
    }

    @Column(name = "ORGID", nullable = false, length = 8)
    public String getOrgid() {
        return this.orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    @Column(name = "OWNERSYSID", length = 10)
    public String getOwnersysid() {
        return this.ownersysid;
    }

    public void setOwnersysid(String ownersysid) {
        this.ownersysid = ownersysid;
    }

    @Column(name = "PRIMARYSYSTEM", nullable = false)
    public boolean isPrimarysystem() {
        return this.primarysystem;
    }

    public void setPrimarysystem(boolean primarysystem) {
        this.primarysystem = primarysystem;
    }

    @Column(name = "SITEID", nullable = false, length = 8)
    public String getSiteid() {
        return this.siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

    @Column(name = "SOURCESYSID", length = 10)
    public String getSourcesysid() {
        return this.sourcesysid;
    }

    public void setSourcesysid(String sourcesysid) {
        this.sourcesysid = sourcesysid;
    }

    @NotEmpty
    @Column(name = "SYSTEMID", nullable = false, length = 8)
    public String getSystemid() {
        return this.systemid;
    }

    public void setSystemid(String systemid) {
        this.systemid = systemid;
    }

}
