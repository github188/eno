package com.energicube.eno.monitor.model;

import javax.persistence.*;
import java.util.Date;

/**
 * UCTAG，用于获取变量列表
 *
 * @author ZOUZHIXIANG
 * @version 1.0
 */
@Entity
@Table(name = "UC_TAG")
public class UcTag implements java.io.Serializable {

    private static final long serialVersionUID = 6389886947579778267L;
    private int used;
    private long tagid; // 数据库主键，自增型
    private int tagtype;
    private String tagname;
    private int devicedriver;
    private int alarmtype;
    private int groupid;
    private int logmode;
    private String tagprop;
    private Date updatet;

    public UcTag() {
    }

    public UcTag(int used, long tagid, int tagtype, String tagname,
                 int devicedriver, int alarmtype, int groupid, int logmode,
                 String tagprop, Date updatet) {
        super();
        this.used = used;
        this.tagid = tagid;
        this.tagtype = tagtype;
        this.tagname = tagname;
        this.devicedriver = devicedriver;
        this.alarmtype = alarmtype;
        this.groupid = groupid;
        this.logmode = logmode;
        this.tagprop = tagprop;
        this.updatet = updatet;
    }

    @Column(name = "USED")
    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TAGID", unique = true, nullable = false)
    public long getTagid() {
        return tagid;
    }

    public void setTagid(long tagid) {
        this.tagid = tagid;
    }

    @Column(name = "TAGTYPE")
    public int getTagtype() {
        return tagtype;
    }

    public void setTagtype(int tagtype) {
        this.tagtype = tagtype;
    }

    @Column(name = "TAGNAME", length = 64)
    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    @Column(name = "DEVICEDRIVER")
    public int getDevicedriver() {
        return devicedriver;
    }

    public void setDevicedriver(int devicedriver) {
        this.devicedriver = devicedriver;
    }

    @Column(name = "ALARMTYPE")
    public int getAlarmtype() {
        return alarmtype;
    }

    public void setAlarmtype(int alarmtype) {
        this.alarmtype = alarmtype;
    }

    @Column(name = "GROUPID")
    public int getGroupid() {
        return groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    @Column(name = "LOGMODE")
    public int getLogmode() {
        return logmode;
    }

    public void setLogmode(int logmode) {
        this.logmode = logmode;
    }

    @Column(name = "TAGPROP", length = 8000)
    public String getTagprop() {
        return tagprop;
    }

    public void setTagprop(String tagprop) {
        this.tagprop = tagprop;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATET", length = 23)
    public Date getUpdatet() {
        return updatet;
    }

    public void setUpdatet(Date updatet) {
        this.updatet = updatet;
    }
}
