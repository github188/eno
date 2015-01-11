package com.energicube.eno.common.dto;

import java.io.Serializable;

/**
 * Created by energicube on 2014/9/16.
 */
public class PfeHuiNaCameraDTO implements Serializable {
    private String hostName;
    private String eventTime;
    private String cameraName;
    private String stateCode;
    private String stateName;

    public PfeHuiNaCameraDTO() {
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getCameraName() {
        return cameraName;
    }

    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String stateCode) {
        this.stateCode = stateCode;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}
