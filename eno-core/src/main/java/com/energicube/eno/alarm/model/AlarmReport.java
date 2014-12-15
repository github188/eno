package com.energicube.eno.alarm.model;

import javax.persistence.*;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-10-18
 * Time: 下午9:05
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "AlarmReport")
public class AlarmReport implements java.io.Serializable {
    private long id;
    private Date reportTime;
    private String reporter;
    private Integer alarmMode;
    private String reportMobile;
    private String reportMessage;
    private Integer execFlag;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ReportTime", length = 23)
    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }


    @Column(name = "Reporter", length = 50)
    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    @Column(name = "AlarmMode")
    public Integer getAlarmMode() {
        return alarmMode;
    }

    public void setAlarmMode(Integer alarmMode) {
        this.alarmMode = alarmMode;
    }

    @Column(name = "ReportMobile", length = 50)
    public String getReportMobile() {
        return reportMobile;
    }

    public void setReportMobile(String reportMobile) {
        this.reportMobile = reportMobile;
    }

    @Column(name = "ReportMessage", length = 300)
    public String getReportMessage() {
        return reportMessage;
    }

    public void setReportMessage(String reportMessage) {
        this.reportMessage = reportMessage;
    }

    @Column(name = "ExecFlag")
    public Integer getExecFlag() {
        return execFlag;
    }

    public void setExecFlag(Integer execFlag) {
        this.execFlag = execFlag;
    }
}
