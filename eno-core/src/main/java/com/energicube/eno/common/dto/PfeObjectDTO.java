package com.energicube.eno.common.dto;

import java.io.Serializable;

/**
 * Created by EnergyUser on 14-1-20.
 */
public class PfeObjectDTO implements Serializable {

    String id;
    long inCount;
    long outCount;
    String blockName;
    String status;
    String countTime;
    String todayTotal;  //今天的统计值
    String yesterdayTotal; //昨天的统计值
    long insidePerson; // 场内人数

    public String getTodayTotal() {
        return todayTotal;
    }

    public void setTodayTotal(String todayTotal) {
        this.todayTotal = todayTotal;
    }

    public String getYesterdayTotal() {
        return yesterdayTotal;
    }

    public void setYesterdayTotal(String yesterdayTotal) {
        this.yesterdayTotal = yesterdayTotal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getInCount() {
        return inCount;
    }

    public void setInCount(long inCount) {
        this.inCount = inCount;
    }

    public long getOutCount() {
        return outCount;
    }

    public void setOutCount(long outCount) {
        this.outCount = outCount;
    }

    public String getBlockName() {
        return blockName;
    }

    public void setBlockName(String blockName) {
        this.blockName = blockName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCountTime() {
        return countTime;
    }

    public void setCountTime(String countTime) {
        this.countTime = countTime;
    }

    public long getInsidePerson() {
        return insidePerson;
    }

    public void setInsidePerson(long insidePerson) {
        this.insidePerson = insidePerson;
    }
}
