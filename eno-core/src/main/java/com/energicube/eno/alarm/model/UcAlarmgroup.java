package com.energicube.eno.alarm.model;

import javax.persistence.*;

/**
 * 报警组
 */
@Entity
@Table(name = "UC_alarmgroup")
public class UcAlarmgroup implements java.io.Serializable {

    private static final long serialVersionUID = -3505066661176760493L;
    private int groupid;
    private int parentid;
    private String groupname;
    private String groupcomment;
    private int mainTemp = 0; //页面判断是否是系统

    public UcAlarmgroup() {
    }

    public UcAlarmgroup(int groupid, int parentid, String groupname,
                        String groupcomment) {
        this.groupid = groupid;
        this.parentid = parentid;
        this.groupname = groupname;
        this.groupcomment = groupcomment;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "groupid", unique = true, nullable = false)
    public int getGroupid() {
        return this.groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    @Column(name = "parentid", nullable = false)
    public int getParentid() {
        return this.parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    @Column(name = "groupname", nullable = false)
    public String getGroupname() {
        return this.groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    @Column(name = "groupcomment", nullable = false)
    public String getGroupcomment() {
        return this.groupcomment;
    }

    public void setGroupcomment(String groupcomment) {
        this.groupcomment = groupcomment;
    }

    @Transient
    public int getMainTemp() {
        return mainTemp;
    }

    public void setMainTemp(int mainTemp) {
        this.mainTemp = mainTemp;
    }


}
