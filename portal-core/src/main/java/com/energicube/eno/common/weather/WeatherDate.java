package com.energicube.eno.common.weather;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-9-11
 * Time: 上午10:26
 * To change this template use File | Settings | File Templates.
 */
public class WeatherDate implements Serializable {
    private String mday;
    private String mon;
    private String year;
    private String day;
    private String month;

    public String getMday() {
        return mday;
    }

    public void setMday(String mday) {
        this.mday = mday;
    }

    public String getMon() {
        return mon;
    }

    public void setMon(String mon) {
        this.mon = mon;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }
}
