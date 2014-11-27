package com.energicube.eno.asset.model;


import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;


/**
 * 资产信息
 */
@Entity
@Table(name = "ASSET", schema = "zclfsys")
public class Asset implements java.io.Serializable {

    private static final long serialVersionUID = 7647679150251168973L;
    private long assetuid;
    private String ancestor;
    private long assetid;
    private String assetnum;
    private String assettag;
    private String assettype;
    private String specclass;
    private String calnum;
    private String changeby;
    private DateTime changedate;
    private String classstructureid;
    private String description;
    private Boolean disabled;
    private String externalrefid;
    private String failurecode;
    private String groupname;
    private Boolean haschildren = false;
    private Boolean hasmoredesc = false;
    private DateTime installdate;
    private Boolean isrunning = true;
    private String langcode;
    private String location;
    private Boolean mainthierchy;
    private String manufacturer;
    private Boolean moved = false;
    private String orgid;
    private String ownersysid;
    private String parent;
    private Integer priority;
    private String sendersysid;
    private String serialnum;
    private String shiftnum;
    private String siteid;
    private String sourcesysid;
    private String status;
    private DateTime statusdate;
    private String usage;
    private String vendor;
    private DateTime warrantyexpdate;
    private String ec1;
    private String ec2;
    private String ec3;
    private String ec4;
    private BigDecimal ec5;
    private DateTime ec6;
    private BigDecimal ec7;
    private String ec8;
    private String ec9;
    private String ec10;
    private String ec11;
    private BigDecimal ec12;
    private DateTime ec13;
    private String ec14;
    private BigDecimal ec15;

    public Asset() {
    }

    public Asset(long assetuid, long assetid, String assetnum) {
        this.assetuid = assetuid;
        this.assetid = assetid;
        this.assetnum = assetnum;
    }

    public Asset(long assetuid, String ancestor, long assetid, String assetnum,
                 String assettag, String assettype, String specclass, String calnum,
                 String changeby, DateTime changedate, String classstructureid,
                 String description, Boolean disabled, String externalrefid,
                 String failurecode, String groupname, Boolean haschildren,
                 Boolean hasmoredesc, DateTime installdate, Boolean isrunning,
                 String langcode, String location, Boolean mainthierchy,
                 String manufacturer, Boolean moved, String orgid,
                 String ownersysid, String parent, Integer priority,
                 String sendersysid, String serialnum, String shiftnum,
                 String siteid, String sourcesysid, String status, DateTime statusdate,
                 String usage, String vendor, DateTime warrantyexpdate, String ec1,
                 String ec2, String ec3, String ec4, BigDecimal ec5, DateTime ec6,
                 BigDecimal ec7, String ec8, String ec9, String ec10, String ec11,
                 BigDecimal ec12, DateTime ec13, String ec14, BigDecimal ec15) {
        this.assetuid = assetuid;
        this.ancestor = ancestor;
        this.assetid = assetid;
        this.assetnum = assetnum;
        this.assettag = assettag;
        this.assettype = assettype;
        this.specclass = specclass;
        this.calnum = calnum;
        this.changeby = changeby;
        this.changedate = changedate;
        this.classstructureid = classstructureid;
        this.description = description;
        this.disabled = disabled;
        this.externalrefid = externalrefid;
        this.failurecode = failurecode;
        this.groupname = groupname;
        this.haschildren = haschildren;
        this.hasmoredesc = hasmoredesc;
        this.installdate = installdate;
        this.isrunning = isrunning;
        this.langcode = langcode;
        this.location = location;
        this.mainthierchy = mainthierchy;
        this.manufacturer = manufacturer;
        this.moved = moved;
        this.orgid = orgid;
        this.ownersysid = ownersysid;
        this.parent = parent;
        this.priority = priority;
        this.sendersysid = sendersysid;
        this.serialnum = serialnum;
        this.shiftnum = shiftnum;
        this.siteid = siteid;
        this.sourcesysid = sourcesysid;
        this.status = status;
        this.statusdate = statusdate;
        this.usage = usage;
        this.vendor = vendor;
        this.warrantyexpdate = warrantyexpdate;
        this.ec1 = ec1;
        this.ec2 = ec2;
        this.ec3 = ec3;
        this.ec4 = ec4;
        this.ec5 = ec5;
        this.ec6 = ec6;
        this.ec7 = ec7;
        this.ec8 = ec8;
        this.ec9 = ec9;
        this.ec10 = ec10;
        this.ec11 = ec11;
        this.ec12 = ec12;
        this.ec13 = ec13;
        this.ec14 = ec14;
        this.ec15 = ec15;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ASSETUID", unique = true, nullable = false)
    public long getAssetuid() {
        return this.assetuid;
    }

    public void setAssetuid(long assetuid) {
        this.assetuid = assetuid;
    }

    @Column(name = "ANCESTOR", length = 12)
    public String getAncestor() {
        return this.ancestor;
    }

    public void setAncestor(String ancestor) {
        this.ancestor = ancestor;
    }

    @Column(name = "ASSETID", nullable = false)
    public long getAssetid() {
        return this.assetid;
    }

    public void setAssetid(long assetid) {
        this.assetid = assetid;
    }

    @NotEmpty
    @Column(name = "ASSETNUM", nullable = false, length = 12)
    public String getAssetnum() {
        return this.assetnum;
    }

    public void setAssetnum(String assetnum) {
        this.assetnum = assetnum;
    }

    @Column(name = "ASSETTAG", length = 64)
    public String getAssettag() {
        return this.assettag;
    }

    public void setAssettag(String assettag) {
        this.assettag = assettag;
    }

    @Column(name = "ASSETTYPE", length = 15)
    public String getAssettype() {
        return this.assettype;
    }

    public void setAssettype(String assettype) {
        this.assettype = assettype;
    }

    @Column(name = "SPECCLASS", length = 15)
    public String getSpecclass() {
        return this.specclass;
    }

    public void setSpecclass(String specclass) {
        this.specclass = specclass;
    }

    @Column(name = "CALNUM", length = 8)
    public String getCalnum() {
        return this.calnum;
    }

    public void setCalnum(String calnum) {
        this.calnum = calnum;
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

    @Column(name = "FAILURECODE", length = 8)
    public String getFailurecode() {
        return this.failurecode;
    }

    public void setFailurecode(String failurecode) {
        this.failurecode = failurecode;
    }

    @Column(name = "GROUPNAME", length = 10)
    public String getGroupname() {
        return this.groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    @Column(name = "HASCHILDREN")
    public Boolean getHaschildren() {
        return this.haschildren;
    }

    public void setHaschildren(Boolean haschildren) {
        this.haschildren = haschildren;
    }

    @Column(name = "HASMOREDESC")
    public Boolean getHasmoredesc() {
        return this.hasmoredesc;
    }

    public void setHasmoredesc(Boolean hasmoredesc) {
        this.hasmoredesc = hasmoredesc;
    }

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "INSTALLDATE", length = 23)
    public DateTime getInstalldate() {
        return this.installdate;
    }

    public void setInstalldate(DateTime installdate) {
        this.installdate = installdate;
    }

    @Column(name = "ISRUNNING")
    public Boolean getIsrunning() {
        return this.isrunning;
    }

    public void setIsrunning(Boolean isrunning) {
        this.isrunning = isrunning;
    }

    @Column(name = "LANGCODE", length = 4)
    public String getLangcode() {
        return this.langcode;
    }

    public void setLangcode(String langcode) {
        this.langcode = langcode;
    }

    @Column(name = "LOCATION", length = 30)
    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Column(name = "MAINTHIERCHY")
    public Boolean getMainthierchy() {
        return this.mainthierchy;
    }

    public void setMainthierchy(Boolean mainthierchy) {
        this.mainthierchy = mainthierchy;
    }

    @Column(name = "MANUFACTURER", length = 12)
    public String getManufacturer() {
        return this.manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    @Column(name = "MOVED")
    public Boolean getMoved() {
        return this.moved;
    }

    public void setMoved(Boolean moved) {
        this.moved = moved;
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

    @Column(name = "PARENT", length = 12)
    public String getParent() {
        return this.parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    @Column(name = "PRIORITY")
    public Integer getPriority() {
        return this.priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Column(name = "SENDERSYSID", length = 50)
    public String getSendersysid() {
        return this.sendersysid;
    }

    public void setSendersysid(String sendersysid) {
        this.sendersysid = sendersysid;
    }

    @Column(name = "SERIALNUM", length = 64)
    public String getSerialnum() {
        return this.serialnum;
    }

    public void setSerialnum(String serialnum) {
        this.serialnum = serialnum;
    }

    @Column(name = "SHIFTNUM", length = 8)
    public String getShiftnum() {
        return this.shiftnum;
    }

    public void setShiftnum(String shiftnum) {
        this.shiftnum = shiftnum;
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
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "STATUSDATE", length = 23)
    public DateTime getStatusdate() {
        return this.statusdate;
    }

    public void setStatusdate(DateTime statusdate) {
        this.statusdate = statusdate;
    }

    @Column(name = "USAGE", length = 15)
    public String getUsage() {
        return this.usage;
    }

    public void setUsage(String usage) {
        this.usage = usage;
    }

    @Column(name = "VENDOR", length = 12)
    public String getVendor() {
        return this.vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "WARRANTYEXPDATE", length = 23)
    public DateTime getWarrantyexpdate() {
        return this.warrantyexpdate;
    }

    public void setWarrantyexpdate(DateTime warrantyexpdate) {
        this.warrantyexpdate = warrantyexpdate;
    }

    @Column(name = "EC1", length = 10)
    public String getEc1() {
        return this.ec1;
    }

    public void setEc1(String ec1) {
        this.ec1 = ec1;
    }

    @Column(name = "EC2", length = 10)
    public String getEc2() {
        return this.ec2;
    }

    public void setEc2(String ec2) {
        this.ec2 = ec2;
    }

    @Column(name = "EC3", length = 10)
    public String getEc3() {
        return this.ec3;
    }

    public void setEc3(String ec3) {
        this.ec3 = ec3;
    }

    @Column(name = "EC4", length = 10)
    public String getEc4() {
        return this.ec4;
    }

    public void setEc4(String ec4) {
        this.ec4 = ec4;
    }

    @Column(name = "EC5", precision = 10)
    public BigDecimal getEc5() {
        return this.ec5;
    }

    public void setEc5(BigDecimal ec5) {
        this.ec5 = ec5;
    }

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "EC6", length = 23)
    public DateTime getEc6() {
        return this.ec6;
    }

    public void setEc6(DateTime ec6) {
        this.ec6 = ec6;
    }

    @Column(name = "EC7", precision = 10)
    public BigDecimal getEc7() {
        return this.ec7;
    }

    public void setEc7(BigDecimal ec7) {
        this.ec7 = ec7;
    }

    @Column(name = "EC8", length = 10)
    public String getEc8() {
        return this.ec8;
    }

    public void setEc8(String ec8) {
        this.ec8 = ec8;
    }

    @Column(name = "EC9", length = 10)
    public String getEc9() {
        return this.ec9;
    }

    public void setEc9(String ec9) {
        this.ec9 = ec9;
    }

    @Column(name = "EC10", length = 10)
    public String getEc10() {
        return this.ec10;
    }

    public void setEc10(String ec10) {
        this.ec10 = ec10;
    }

    @Column(name = "EC11", length = 10)
    public String getEc11() {
        return this.ec11;
    }

    public void setEc11(String ec11) {
        this.ec11 = ec11;
    }

    @Column(name = "EC12", precision = 10)
    public BigDecimal getEc12() {
        return this.ec12;
    }

    public void setEc12(BigDecimal ec12) {
        this.ec12 = ec12;
    }

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "EC13", length = 23)
    public DateTime getEc13() {
        return this.ec13;
    }

    public void setEc13(DateTime ec13) {
        this.ec13 = ec13;
    }

    @Column(name = "EC14", length = 10)
    public String getEc14() {
        return this.ec14;
    }

    public void setEc14(String ec14) {
        this.ec14 = ec14;
    }

    @Column(name = "EC15", precision = 10)
    public BigDecimal getEc15() {
        return this.ec15;
    }

    public void setEc15(BigDecimal ec15) {
        this.ec15 = ec15;
    }

    @Override
    public String toString() {
        return "Asset [assetuid=" + assetuid + ", ancestor=" + ancestor
                + ", assetid=" + assetid + ", assetnum=" + assetnum
                + ", assettag=" + assettag + ", assettype=" + assettype
                + ", specclass=" + specclass + ", calnum=" + calnum
                + ", changeby=" + changeby + ", changedate=" + changedate
                + ", classstructureid=" + classstructureid + ", description="
                + description + ", disabled=" + disabled + ", externalrefid="
                + externalrefid + ", failurecode=" + failurecode
                + ", groupname=" + groupname + ", haschildren=" + haschildren
                + ", hasmoredesc=" + hasmoredesc + ", installdate="
                + installdate + ", isrunning=" + isrunning + ", langcode="
                + langcode + ", location=" + location + ", mainthierchy="
                + mainthierchy + ", manufacturer=" + manufacturer + ", moved="
                + moved + ", orgid=" + orgid + ", ownersysid=" + ownersysid
                + ", parent=" + parent + ", priority=" + priority
                + ", sendersysid=" + sendersysid + ", serialnum=" + serialnum
                + ", shiftnum=" + shiftnum + ", siteid=" + siteid
                + ", sourcesysid=" + sourcesysid + ", status=" + status
                + ", statusdate=" + statusdate + ", usage=" + usage
                + ", vendor=" + vendor + ", warrantyexpdate=" + warrantyexpdate
                + ", ec1=" + ec1 + ", ec2=" + ec2 + ", ec3=" + ec3 + ", ec4="
                + ec4 + ", ec5=" + ec5 + ", ec6=" + ec6 + ", ec7=" + ec7
                + ", ec8=" + ec8 + ", ec9=" + ec9 + ", ec10=" + ec10
                + ", ec11=" + ec11 + ", ec12=" + ec12 + ", ec13=" + ec13
                + ", ec14=" + ec14 + ", ec15=" + ec15 + "]";
    }


}
