package com.energicube.eno.monitor.model;


import javax.persistence.*;
import java.util.Date;

/**
 *
 */
@Entity
@Table(name = "OKCUSERSTATUS", schema = "dbo")
public class OkcUserStatus implements java.io.Serializable {

    private static final long serialVersionUID = 5010470912393783534L;
    private long userstatusid;
    private String changeby;
    private Date changedate;
    private String memo;
    private String status;
    private String userid;

    public OkcUserStatus() {
    }

    public OkcUserStatus(long userstatusid) {
        this.userstatusid = userstatusid;
    }

    public OkcUserStatus(long userstatusid, String changeby, Date changedate,
                         String memo, String status, String userid) {
        this.userstatusid = userstatusid;
        this.changeby = changeby;
        this.changedate = changedate;
        this.memo = memo;
        this.status = status;
        this.userid = userid;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USERSTATUSID", unique = true, nullable = false)
    public long getUserstatusid() {
        return this.userstatusid;
    }

    public void setUserstatusid(long userstatusid) {
        this.userstatusid = userstatusid;
    }

    @Column(name = "CHANGEBY", length = 50)
    public String getChangeby() {
        return this.changeby;
    }

    public void setChangeby(String changeby) {
        this.changeby = changeby;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CHANGEDATE", length = 23)
    public Date getChangedate() {
        return this.changedate;
    }

    public void setChangedate(Date changedate) {
        this.changedate = changedate;
    }

    @Column(name = "MEMO", length = 256)
    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Column(name = "STATUS", length = 12)
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "USERID", length = 50)
    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

}
