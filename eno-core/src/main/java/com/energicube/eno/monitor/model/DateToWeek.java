package com.energicube.eno.monitor.model;


public class DateToWeek {
    private static final long serialVersionUID = -5606895454250797400L;
    /**
     * ID
     */
    private String dat;
    /**
     * Text
     */
    private String week;

    private String shifttype;

    private String shifttypeName;

    private String name;

    private String season;

    private String shiftsid;

    private String wzDate;

    public String getWzDate() {
        return wzDate;
    }

    public void setWzDate(String wzDate) {
        this.wzDate = wzDate;
    }

    public String getShiftsid() {
        return shiftsid;
    }

    public void setShiftsid(String shiftsid) {
        this.shiftsid = shiftsid;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShifttype() {
        return shifttype;
    }

    public void setShifttype(String shifttype) {
        this.shifttype = shifttype;
    }

    public String getShifttypeName() {
        return shifttypeName;
    }

    public void setShifttypeName(String shifttypeName) {
        this.shifttypeName = shifttypeName;
    }

    public String getDat() {
        return dat;
    }

    public void setDat(String dat) {
        this.dat = dat;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}
