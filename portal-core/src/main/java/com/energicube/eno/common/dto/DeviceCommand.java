package com.energicube.eno.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-8-12
 * Time: 下午2:16
 * To change this template use File | Settings | File Templates.
 */
public class DeviceCommand implements Serializable {

    //===============命令中主要标识性参数===========================
    private String deviceId;   //设备的Id
    private String commandType; //T 特例  M  手动  G  全局  P  预设  A 系统自选
    private String status;       //设备控制的开关状态  Y-开 N-关
    private Date sendTime;      //控制发出的时间  2013-05-06 12:12:00
    private Integer spaceTime;   //间隔时间
    private String systemId;
    private String executeStatus;//执行状态 Y-已经执行 N-未执行 C--被手动覆盖

    //================下面用于策略的命令的标识==============================
    private String isStrategy;//是否是策略     Y--是  N--不是
    private String strategyId;//是否是策略     Y--是  N--不是
    private String startDate;    //策略开始的时间
    private String endDate;      //策略结束的时间
    private long executeTime;   //上一次执行时的时间

    //================下面用于具体参数的设置==============================
    private String tagId;   //设备的参数操作的tagId
    private String paramName;
    private String paramValue;

    private List<DeviceCommand> params = new ArrayList<DeviceCommand>();

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getExecuteStatus() {
        return executeStatus;
    }

    public void setExecuteStatus(String executeStatus) {
        this.executeStatus = executeStatus;
    }

    public String getStrategy() {
        return isStrategy;
    }

    public void setStrategy(String strategy) {
        isStrategy = strategy;
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

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public List<DeviceCommand> getParams() {
        return params;
    }

    public void setParams(List<DeviceCommand> params) {
        this.params = params;
    }

    public Integer getSpaceTime() {
        return spaceTime;
    }

    public void setSpaceTime(Integer spaceTime) {
        this.spaceTime = spaceTime;
    }

    public String getCommandType() {
        return commandType;
    }

    public void setCommandType(String commandType) {
        this.commandType = commandType;
    }

    public String getStrategyId() {
        return strategyId;
    }

    public void setStrategyId(String strategyId) {
        this.strategyId = strategyId;
    }

    public long getExecuteTime() {
        return executeTime;
    }

    public void setExecuteTime(long executeTime) {
        this.executeTime = executeTime;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    @Override
    public String toString() {
        return "DeviceCommand{" +
                "deviceId='" + deviceId + '\'' +
                ", commandType='" + commandType + '\'' +
                ", status='" + status + '\'' +
                ", sendTime=" + sendTime +
                ", spaceTime=" + spaceTime +
                ", executeStatus='" + executeStatus + '\'' +
                ", isStrategy='" + isStrategy + '\'' +
                ", strategyId='" + strategyId + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", executeTime=" + executeTime +
                ", tagId='" + tagId + '\'' +
                ", paramName='" + paramName + '\'' +
                ", paramValue='" + paramValue + '\'' +
                ", params=" + params +
                '}';
    }
}
