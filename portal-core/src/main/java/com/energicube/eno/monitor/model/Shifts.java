package com.energicube.eno.monitor.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 交接班班次定义
 */
@Entity
@Table(name = "SHIFTS", schema = "dbo")
public class Shifts implements java.io.Serializable {

    private static final long serialVersionUID = 3676036009975577742L;
    private long shiftsid;
    private String deptname;
    private String name;
    private Date shiftdate;
    private String shifttype;
    private String mobile;
    private String description;
    private List<DateToWeek> dateToWeeklist;

    @Transient
    public List<DateToWeek> getDateToWeeklist() {
        return dateToWeeklist;
    }

    public void setDateToWeeklist(List<DateToWeek> dateToWeeklist) {
        this.dateToWeeklist = dateToWeeklist;
    }

    public Shifts() {
    }

    public Shifts(long shiftsid) {
        this.shiftsid = shiftsid;
    }

    public Shifts(long shiftsid, String deptname, String name, Date shiftdate,
                  String shifttype, String mobile, String description) {
        this.shiftsid = shiftsid;
        this.deptname = deptname;
        this.name = name;
        this.shiftdate = shiftdate;
        this.shifttype = shifttype;
        this.mobile = mobile;
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SHIFTSID", unique = true, nullable = false)
    public long getShiftsid() {
        return this.shiftsid;
    }

    public void setShiftsid(long shiftsid) {
        this.shiftsid = shiftsid;
    }

    @Column(name = "DEPTNAME", length = 30)
    public String getDeptname() {
        return this.deptname;
    }

    public void setDeptname(String deptname) {
        this.deptname = deptname;
    }

    @Column(name = "NAME", length = 30)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "SHIFTDATE", length = 23)
    public Date getShiftdate() {
        return this.shiftdate;
    }

    public void setShiftdate(Date shiftdate) {
        this.shiftdate = shiftdate;
    }

    @Column(name = "SHIFTTYPE", length = 16)
    public String getShifttype() {
        return this.shifttype;
    }

    public void setShifttype(String shifttype) {
        this.shifttype = shifttype;
    }

    @Column(name = "MOBILE", length = 20)
    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(name = "DESCRIPTION", length = 254)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
