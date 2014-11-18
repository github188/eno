package com.energicube.eno.monitor.model;

import javax.persistence.*;
import java.util.Date;

/**
 * 交接班信息
 */
@Entity
@Table(name = "SHIFTWORK", schema = "dbo")
public class Shiftwork implements java.io.Serializable {

    private static final long serialVersionUID = 7205571354986767070L;
    private long shiftworkid;
    private Long shiftendingbyid;
    private String shiftendingby;
    private String workcontent;
    private String focuscontent;
    private String leadassign;
    private String shiftitems;
    private Date shiftstartingtime;
    private Date shiftendingtime;
    private Long shiftstartingbyid;
    private String shiftstartingby;
    private String comments;
    private Date createdate;

    public Shiftwork() {
    }

    public Shiftwork(long shiftworkid) {
        this.shiftworkid = shiftworkid;
    }

    public Shiftwork(long shiftworkid, Long shiftendingbyid,
                     String shiftendingby, String workcontent, String focuscontent,
                     String leadassign, String shiftitems, Date shiftstartingtime,
                     Date shiftendingtime, Long shiftstartingbyid,
                     String shiftstartingby, String comments, Date createdate) {
        this.shiftworkid = shiftworkid;
        this.shiftendingbyid = shiftendingbyid;
        this.shiftendingby = shiftendingby;
        this.workcontent = workcontent;
        this.focuscontent = focuscontent;
        this.leadassign = leadassign;
        this.shiftitems = shiftitems;
        this.shiftstartingtime = shiftstartingtime;
        this.shiftendingtime = shiftendingtime;
        this.shiftstartingbyid = shiftstartingbyid;
        this.shiftstartingby = shiftstartingby;
        this.comments = comments;
        this.createdate = createdate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SHIFTWORKID", unique = true, nullable = false)
    public long getShiftworkid() {
        return this.shiftworkid;
    }

    public void setShiftworkid(long shiftworkid) {
        this.shiftworkid = shiftworkid;
    }

    @Column(name = "SHIFTENDINGBYID")
    public Long getShiftendingbyid() {
        return this.shiftendingbyid;
    }

    public void setShiftendingbyid(Long shiftendingbyid) {
        this.shiftendingbyid = shiftendingbyid;
    }

    @Column(name = "SHIFTENDINGBY", length = 30)
    public String getShiftendingby() {
        return this.shiftendingby;
    }

    public void setShiftendingby(String shiftendingby) {
        this.shiftendingby = shiftendingby;
    }

    @Column(name = "WORKCONTENT")
    public String getWorkcontent() {
        return this.workcontent;
    }

    public void setWorkcontent(String workcontent) {
        this.workcontent = workcontent;
    }

    @Column(name = "FOCUSCONTENT")
    public String getFocuscontent() {
        return this.focuscontent;
    }

    public void setFocuscontent(String focuscontent) {
        this.focuscontent = focuscontent;
    }

    @Column(name = "LEADASSIGN")
    public String getLeadassign() {
        return this.leadassign;
    }

    public void setLeadassign(String leadassign) {
        this.leadassign = leadassign;
    }

    @Column(name = "SHIFTITEMS")
    public String getShiftitems() {
        return this.shiftitems;
    }

    public void setShiftitems(String shiftitems) {
        this.shiftitems = shiftitems;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SHIFTSTARTINGTIME", length = 23)
    public Date getShiftstartingtime() {
        return this.shiftstartingtime;
    }

    public void setShiftstartingtime(Date shiftstartingtime) {
        this.shiftstartingtime = shiftstartingtime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SHIFTENDINGTIME", length = 23)
    public Date getShiftendingtime() {
        return this.shiftendingtime;
    }

    public void setShiftendingtime(Date shiftendingtime) {
        this.shiftendingtime = shiftendingtime;
    }

    @Column(name = "SHIFTSTARTINGBYID")
    public Long getShiftstartingbyid() {
        return this.shiftstartingbyid;
    }

    public void setShiftstartingbyid(Long shiftstartingbyid) {
        this.shiftstartingbyid = shiftstartingbyid;
    }

    @Column(name = "SHIFTSTARTINGBY", length = 30)
    public String getShiftstartingby() {
        return this.shiftstartingby;
    }

    public void setShiftstartingby(String shiftstartingby) {
        this.shiftstartingby = shiftstartingby;
    }

    @Column(name = "COMMENTS")
    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATEDATE", length = 23)
    public Date getCreatedate() {
        return this.createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

}
