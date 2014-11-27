package com.energicube.eno.alarm.model;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "UC_alarmlog", schema = "zclfsys")
public class UcAlarmlog implements java.io.Serializable {

    private static final long serialVersionUID = -5807305794686534141L;
    private int id;
    private int almlogid;
    private int loginst;
    private Date almt;
    private int tagid;
    private String tagname;
    private int almpriority;
    private int typecode;
    private String typename;
    private int groupid;
    private String groupname;
    private String grouppath;
    private String assetid;
    private String propertyid;
    private int deviceid;
    private String devicename;
    private float tagvalue;
    private float limitvalue;
    private String tagcomment;
    private String almcomment;
    private String almoperatorname;
    private String reviewer;
    private Date reviewt;
    private String reviewcontent;
    private DateTime ackt;
    private Float ackvalue;
    private String ackoperatorname;
    private DateTime rett;
    private Float retvalue;
    private String retoperatorname;
    private String ecode;
    private String almdirection;

    public UcAlarmlog() {
    }

    public UcAlarmlog(int id, int almlogid, int loginst, Date almt, int tagid,
                      String tagname, int almpriority, int typecode, String typename,
                      int groupid, int deviceid, float tagvalue, float limitvalue) {
        this.id = id;
        this.almlogid = almlogid;
        this.loginst = loginst;
        this.almt = almt;
        this.tagid = tagid;
        this.tagname = tagname;
        this.almpriority = almpriority;
        this.typecode = typecode;
        this.typename = typename;
        this.groupid = groupid;
        this.deviceid = deviceid;
        this.tagvalue = tagvalue;
        this.limitvalue = limitvalue;
    }

    public UcAlarmlog(int id, int almlogid, int loginst, Date almt, int tagid,
                      String tagname, int almpriority, int typecode, String typename,
                      int groupid, String groupname, String grouppath, String assetid,
                      String propertyid, int deviceid, String devicename, float tagvalue,
                      float limitvalue, String tagcomment, String almcomment,
                      String almoperatorname, String reviewer, Date reviewt,
                      String reviewcontent, DateTime ackt, Float ackvalue,
                      String ackoperatorname, DateTime rett, Float retvalue,
                      String retoperatorname, String ecode, String almdirection) {
        this.id = id;
        this.almlogid = almlogid;
        this.loginst = loginst;
        this.almt = almt;
        this.tagid = tagid;
        this.tagname = tagname;
        this.almpriority = almpriority;
        this.typecode = typecode;
        this.typename = typename;
        this.groupid = groupid;
        this.groupname = groupname;
        this.grouppath = grouppath;
        this.assetid = assetid;
        this.propertyid = propertyid;
        this.deviceid = deviceid;
        this.devicename = devicename;
        this.tagvalue = tagvalue;
        this.limitvalue = limitvalue;
        this.tagcomment = tagcomment;
        this.almcomment = almcomment;
        this.almoperatorname = almoperatorname;
        this.reviewer = reviewer;
        this.reviewt = reviewt;
        this.reviewcontent = reviewcontent;
        this.ackt = ackt;
        this.ackvalue = ackvalue;
        this.ackoperatorname = ackoperatorname;
        this.rett = rett;
        this.retvalue = retvalue;
        this.retoperatorname = retoperatorname;
        this.ecode = ecode;
        this.almdirection = almdirection;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "almlogid", nullable = false)
    public int getAlmlogid() {
        return this.almlogid;
    }

    public void setAlmlogid(int almlogid) {
        this.almlogid = almlogid;
    }

    @Column(name = "loginst", nullable = false)
    public int getLoginst() {
        return this.loginst;
    }

    public void setLoginst(int loginst) {
        this.loginst = loginst;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "almt", length = 23)
    public Date getAlmt() {
        return this.almt;
    }

    public void setAlmt(Date almt) {
        this.almt = almt;
    }

    @Column(name = "tagid", nullable = false)
    public int getTagid() {
        return this.tagid;
    }

    public void setTagid(int tagid) {
        this.tagid = tagid;
    }

    @Column(name = "tagname", nullable = false, length = 64)
    public String getTagname() {
        return this.tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    @Column(name = "almpriority", nullable = false)
    public int getAlmpriority() {
        return this.almpriority;
    }

    public void setAlmpriority(int almpriority) {
        this.almpriority = almpriority;
    }

    @Column(name = "typecode", nullable = false)
    public int getTypecode() {
        return this.typecode;
    }

    public void setTypecode(int typecode) {
        this.typecode = typecode;
    }

    @Column(name = "typename", nullable = false, length = 20)
    public String getTypename() {
        return this.typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    @Column(name = "groupid", nullable = false)
    public int getGroupid() {
        return this.groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    @Column(name = "groupname", length = 64)
    public String getGroupname() {
        return this.groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    @Column(name = "assetid", length = 32)
    public String getAssetid() {
        return this.assetid;
    }

    public void setAssetid(String assetid) {
        this.assetid = assetid;
    }

    @Column(name = "propertyid", length = 32)
    public String getPropertyid() {
        return this.propertyid;
    }

    public void setPropertyid(String propertyid) {
        this.propertyid = propertyid;
    }

    @Column(name = "deviceid", nullable = false)
    public int getDeviceid() {
        return this.deviceid;
    }

    public void setDeviceid(int deviceid) {
        this.deviceid = deviceid;
    }

    @Column(name = "devicename", length = 64)
    public String getDevicename() {
        return this.devicename;
    }

    public void setDevicename(String devicename) {
        this.devicename = devicename;
    }

    @Column(name = "tagvalue", nullable = false, precision = 24, scale = 0)
    public float getTagvalue() {
        return this.tagvalue;
    }

    public void setTagvalue(float tagvalue) {
        this.tagvalue = tagvalue;
    }

    @Column(name = "limitvalue", nullable = false, precision = 24, scale = 0)
    public float getLimitvalue() {
        return this.limitvalue;
    }

    public void setLimitvalue(float limitvalue) {
        this.limitvalue = limitvalue;
    }

    @Column(name = "tagcomment", length = 64)
    public String getTagcomment() {
        return this.tagcomment;
    }

    public void setTagcomment(String tagcomment) {
        this.tagcomment = tagcomment;
    }

    @Column(name = "almcomment", length = 64)
    public String getAlmcomment() {
        return this.almcomment;
    }

    public void setAlmcomment(String almcomment) {
        this.almcomment = almcomment;
    }

    @Column(name = "almoperatorname", length = 64)
    public String getAlmoperatorname() {
        return this.almoperatorname;
    }

    public void setAlmoperatorname(String almoperatorname) {
        this.almoperatorname = almoperatorname;
    }

    @Column(name = "reviewer", length = 64)
    public String getReviewer() {
        return this.reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "reviewt", length = 23)
    public Date getReviewt() {
        return this.reviewt;
    }

    public void setReviewt(Date reviewt) {
        this.reviewt = reviewt;
    }

    @Column(name = "reviewcontent")
    public String getReviewcontent() {
        return this.reviewcontent;
    }

    public void setReviewcontent(String reviewcontent) {
        this.reviewcontent = reviewcontent;
    }

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "ackt", length = 23)
    public DateTime getAckt() {
        return this.ackt;
    }

    public void setAckt(DateTime ackt) {
        this.ackt = ackt;
    }

    @Column(name = "ackvalue", precision = 24, scale = 0)
    public Float getAckvalue() {
        return this.ackvalue;
    }

    public void setAckvalue(Float ackvalue) {
        this.ackvalue = ackvalue;
    }

    @Column(name = "ackoperatorname")
    public String getAckoperatorname() {
        return this.ackoperatorname;
    }

    public void setAckoperatorname(String ackoperatorname) {
        this.ackoperatorname = ackoperatorname;
    }

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "rett", length = 23)
    public DateTime getRett() {
        return this.rett;
    }

    public void setRett(DateTime rett) {
        this.rett = rett;
    }

    @Column(name = "retvalue", precision = 24, scale = 0)
    public Float getRetvalue() {
        return this.retvalue;
    }

    public void setRetvalue(Float retvalue) {
        this.retvalue = retvalue;
    }

    @Column(name = "retoperatorname")
    public String getRetoperatorname() {
        return this.retoperatorname;
    }

    public void setRetoperatorname(String retoperatorname) {
        this.retoperatorname = retoperatorname;
    }

    @Column(name = "ecode", length = 32)
    public String getEcode() {
        return this.ecode;
    }

    public void setEcode(String ecode) {
        this.ecode = ecode;
    }

    @Column(name = "almdirection", length = 32)
    public String getAlmdirection() {
        return this.almdirection;
    }

    public void setAlmdirection(String almdirection) {
        this.almdirection = almdirection;
    }

    @Column(name = "grouppath", length = 32)
    public String getGrouppath() {
        return grouppath;
    }

    public void setGrouppath(String grouppath) {
        this.grouppath = grouppath;
    }

}
