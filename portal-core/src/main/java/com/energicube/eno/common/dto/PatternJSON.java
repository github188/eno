package com.energicube.eno.common.dto;

import com.energicube.eno.calendar.model.UcCalendar;
import com.energicube.eno.monitor.model.Event;
import com.energicube.eno.pattern.model.UcPatternRecord;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-8-31
 * Time: 下午4:50
 * 用于封装日历的数据
 */
public class PatternJSON implements Serializable {
    String date; //格式 yyyyMMdd
    List<Event> eventList = new ArrayList<Event>();
    UcCalendar ucCalendar = new UcCalendar();
    List<UcPatternRecord> ucPatternRecords = new ArrayList<UcPatternRecord>();

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    public UcCalendar getUcCalendar() {
        return ucCalendar;
    }

    public void setUcCalendar(UcCalendar ucCalendars) {
        this.ucCalendar = ucCalendars;
    }

    public List<UcPatternRecord> getUcPatternRecords() {
        return ucPatternRecords;
    }

    public void setUcPatternRecords(List<UcPatternRecord> ucPatternRecords) {
        this.ucPatternRecords = ucPatternRecords;
    }
}
