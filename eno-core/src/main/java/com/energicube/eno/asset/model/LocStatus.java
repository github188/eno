package com.energicube.eno.asset.model;

import javax.persistence.*;
import java.util.Date;

/**
 * 位置状态变更记录
 */
@Entity
@Table(name = "LOCSTATUS", schema = "zclfsys")
public class LocStatus implements java.io.Serializable {

    private static final long serialVersionUID = 6486104414731357511L;
    private long locstatusid;
    private String changeby;
    private Date changedate;
    private String location;
    private String memo;
    private String orgid;
    private String siteid;
    private String status;

    public LocStatus() {
    }

    public LocStatus(long locstatusid, String changeby, Date changedate,
                     String location, String orgid, String siteid, String status) {
        this.locstatusid = locstatusid;
        this.changeby = changeby;
        this.changedate = changedate;
        this.location = location;
        this.orgid = orgid;
        this.siteid = siteid;
        this.status = status;
    }

    public LocStatus(long locstatusid, String changeby, Date changedate,
                     String location, String memo, String orgid, String siteid,
                     String status) {
        this.locstatusid = locstatusid;
        this.changeby = changeby;
        this.changedate = changedate;
        this.location = location;
        this.memo = memo;
        this.orgid = orgid;
        this.siteid = siteid;
        this.status = status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOCSTATUSID", unique = true, nullable = false)
    public long getLocstatusid() {
        return this.locstatusid;
    }

    public void setLocstatusid(long locstatusid) {
        this.locstatusid = locstatusid;
    }

    @Column(name = "CHANGEBY", nullable = false, length = 30)
    public String getChangeby() {
        return this.changeby;
    }

    public void setChangeby(String changeby) {
        this.changeby = changeby;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CHANGEDATE", nullable = false, length = 23)
    public Date getChangedate() {
        return this.changedate;
    }

    public void setChangedate(Date changedate) {
        this.changedate = changedate;
    }

    @Column(name = "LOCATION", nullable = false, length = 30)
    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Column(name = "MEMO", length = 25)
    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Column(name = "ORGID", nullable = false, length = 8)
    public String getOrgid() {
        return this.orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    @Column(name = "SITEID", nullable = false, length = 8)
    public String getSiteid() {
        return this.siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

    @Column(name = "STATUS", nullable = false, length = 20)
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
