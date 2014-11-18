package com.energicube.eno.monitor.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @author 刘广路
 * @since 2014-08-11
 */
@Entity
@Table(name = "SUB_patrol")
public class SubPatrol implements java.io.Serializable {

    private String id;
    private String lineNum;
    private String lineName;
    private String userId;
    private String userName;
    private String placeName;
    private Date startTime;
    private Date endTime;
    private Date checkTime;
    private String checkResult;
    private String shifts;
    private Integer missedNum;
    private Integer onTimeNum;
    private Integer earlyNum;
    private Integer lateNum;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(name = "id", unique = true, nullable = false, length = 36)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "lineNum", length = 36)
    public String getLineNum() {
        return lineNum;
    }

    public void setLineNum(String lineNum) {
        this.lineNum = lineNum;
    }

    @Column(name = "lineName", length = 50)
    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    @Column(name = "userId", length = 36)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Column(name = "userName", length = 60)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "placeName", length = 40)
    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    @Temporal(TemporalType.TIME)
    @Column(name = "startTime", length = 23)
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Temporal(TemporalType.TIME)
    @Column(name = "endTime", length = 23)
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Temporal(TemporalType.TIME)
    @Column(name = "checkTime", length = 23)
    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    @Column(name = "checkResult", length = 60)
    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    @Column(name = "shifts", length = 100)
    public String getShifts() {
        return shifts;
    }

    public void setShifts(String shifts) {
        this.shifts = shifts;
    }

    @Column(name = "missedNum")
    public Integer getMissedNum() {
        return missedNum;
    }

    public void setMissedNum(Integer missedNum) {
        this.missedNum = missedNum;
    }

    @Column(name = "onTimeNum")
    public Integer getOnTimeNum() {
        return onTimeNum;
    }

    public void setOnTimeNum(Integer onTimeNum) {
        this.onTimeNum = onTimeNum;
    }

    @Column(name = "earlyNum")
    public Integer getEarlyNum() {
        return earlyNum;
    }

    public void setEarlyNum(Integer earlyNum) {
        this.earlyNum = earlyNum;
    }

    @Column(name = "lateNum")
    public Integer getLateNum() {
        return lateNum;
    }

    public void setLateNum(Integer lateNum) {
        this.lateNum = lateNum;
    }
}
