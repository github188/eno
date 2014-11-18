package com.energicube.eno.common.dto;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-10-9
 * Time: 下午3:38
 * To change this template use File | Settings | File Templates.
 */
public class SystemMessage implements Serializable {
    private boolean success;
    private String msg;
    //模式用到的参数
    private String patternId;
    private String actionType;
    private String patternName;
    private String author;
    //设备或子系统运行状态
    private String systemName;
    private String systemId;
    private String systemCode;
    private String deviceId;
    private String runPattern;

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getPatternId() {
        return patternId;
    }

    public void setPatternId(String patternId) {
        this.patternId = patternId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPatternName() {
        return patternName;
    }

    public void setPatternName(String patternName) {
        this.patternName = patternName;
    }

    public String getRunPattern() {
        return runPattern;
    }

    public void setRunPattern(String runPattern) {
        this.runPattern = runPattern;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getSystemCode() {
        return systemCode;
    }

    public void setSystemCode(String systemCode) {
        this.systemCode = systemCode;
    }
}
