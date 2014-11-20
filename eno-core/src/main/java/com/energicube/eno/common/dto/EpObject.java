package com.energicube.eno.common.dto;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: EnergyUser
 * Date: 13-11-26
 * Time: 上午11:50
 * 巡更的前台显示对象.
 */
public class EpObject implements Serializable {

    String id;  //编号
    String road;//线路
    String shiftWork; //班次
    String startDate; //开始时间
    String endDate; //结束时间
    //0为准时，1为早，2为漏巡，3为未计划，4为顺序错误，5为晚巡，6为未巡，
    String result; //核查结果
    String person; //巡逻人员
    String firstTime; //第一个刷卡时间
    String endTime;  //最后一次刷卡时间
    //    =0全无序，1为首点有序，2为首尾无序  =3为有序，=4为平均间隔，等误差，=5单独间隔、误差
    String roadType; //线路类型
    String remark;//备注
    //1为计划排班，2为自动生成排班，3为未计划排班
    String shiftType;//排班类型
    String missedNum;  //漏个数
    //准的个数
    String onTimeNum;
    String earlyNum;//早巡个数
    String lateNum;  //晚巡个数

    public String getMissedNum() {
        return missedNum;
    }

    public void setMissedNum(String missedNum) {
        this.missedNum = missedNum;
    }

    public String getOnTimeNum() {
        return onTimeNum;
    }

    public void setOnTimeNum(String onTimeNum) {
        this.onTimeNum = onTimeNum;
    }

    public String getEarlyNum() {
        return earlyNum;
    }

    public void setEarlyNum(String earlyNum) {
        this.earlyNum = earlyNum;
    }

    public String getLateNum() {
        return lateNum;
    }

    public void setLateNum(String lateNum) {
        this.lateNum = lateNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoad() {
        return road;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public String getShiftWork() {
        return shiftWork;
    }

    public void setShiftWork(String shiftWork) {
        this.shiftWork = shiftWork;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getFirstTime() {
        return firstTime;
    }

    public void setFirstTime(String firstTime) {
        this.firstTime = firstTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRoadType() {
        return roadType;
    }

    public void setRoadType(String roadType) {
        this.roadType = roadType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getShiftType() {
        return shiftType;
    }

    public void setShiftType(String shiftType) {
        this.shiftType = shiftType;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }
}
