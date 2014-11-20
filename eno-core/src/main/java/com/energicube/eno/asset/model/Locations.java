package com.energicube.eno.asset.model;


import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

/**
 * 位置定义
 */
@Entity
@Table(name = "LOCATIONS", schema = "dbo")
public class Locations implements java.io.Serializable {

    private static final long serialVersionUID = -5932721158460779941L;
    private long locationsid;
    private String changeby;
    private DateTime changedate;
    private String classstructureid;
    private String description;
    private Boolean disabled;
    private String externalrefid;
    private Boolean hasld;
    private Boolean isrepairfacility;
    private String langcode;
    private String location;
    private String orgid;
    private String ownersysid;
    private String sendersysid;
    private String serviceaddresscode;
    private String siteid;
    private String sourcesysid;
    private String status;
    private DateTime statusdate;
    private String type;

    public Locations() {
    }

    public Locations(long locationsid) {
        this.locationsid = locationsid;
    }

    public Locations(long locationsid, String changeby, DateTime changedate,
                     String classstructureid, String description, Boolean disabled,
                     String externalrefid, Boolean hasld, Boolean isrepairfacility,
                     String langcode, String location, String orgid, String ownersysid,
                     String sendersysid, String serviceaddresscode, String siteid,
                     String sourcesysid, String status, DateTime statusdate, String type) {
        this.locationsid = locationsid;
        this.changeby = changeby;
        this.changedate = changedate;
        this.classstructureid = classstructureid;
        this.description = description;
        this.disabled = disabled;
        this.externalrefid = externalrefid;
        this.hasld = hasld;
        this.isrepairfacility = isrepairfacility;
        this.langcode = langcode;
        this.location = location;
        this.orgid = orgid;
        this.ownersysid = ownersysid;
        this.sendersysid = sendersysid;
        this.serviceaddresscode = serviceaddresscode;
        this.siteid = siteid;
        this.sourcesysid = sourcesysid;
        this.status = status;
        this.statusdate = statusdate;
        this.type = type;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOCATIONSID", unique = true, nullable = false)
    public long getLocationsid() {
        return this.locationsid;
    }

    public void setLocationsid(long locationsid) {
        this.locationsid = locationsid;
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

    @Column(name = "DESCRIPTION", length = 100)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "DISABLED")
    public Boolean getDisabled() {
        return this.disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }

    @Column(name = "EXTERNALREFID", length = 10)
    public String getExternalrefid() {
        return this.externalrefid;
    }

    public void setExternalrefid(String externalrefid) {
        this.externalrefid = externalrefid;
    }

    @Column(name = "HASLD")
    public Boolean getHasld() {
        return this.hasld;
    }

    public void setHasld(Boolean hasld) {
        this.hasld = hasld;
    }

    @Column(name = "ISREPAIRFACILITY")
    public Boolean getIsrepairfacility() {
        return this.isrepairfacility;
    }

    public void setIsrepairfacility(Boolean isrepairfacility) {
        this.isrepairfacility = isrepairfacility;
    }

    @Column(name = "LANGCODE", length = 4)
    public String getLangcode() {
        return this.langcode;
    }

    public void setLangcode(String langcode) {
        this.langcode = langcode;
    }

    @NotEmpty
    @Column(name = "LOCATION", length = 30)
    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Column(name = "ORGID", length = 8)
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

    @Column(name = "SENDERSYSID", length = 50)
    public String getSendersysid() {
        return this.sendersysid;
    }

    public void setSendersysid(String sendersysid) {
        this.sendersysid = sendersysid;
    }

    @Column(name = "SERVICEADDRESSCODE", length = 30)
    public String getServiceaddresscode() {
        return this.serviceaddresscode;
    }

    public void setServiceaddresscode(String serviceaddresscode) {
        this.serviceaddresscode = serviceaddresscode;
    }

    @Column(name = "SITEID", length = 8)
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

    @Column(name = "STATUS", length = 20)
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "STATUSDATE", length = 23)
    public DateTime getStatusdate() {
        return this.statusdate;
    }

    public void setStatusdate(DateTime statusdate) {
        this.statusdate = statusdate;
    }

    @Column(name = "TYPE", length = 16)
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
