package com.energicube.eno.monitor.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NetvideoRotationConfig implements Serializable {
    private long rotationId;
    private String rotationName;
    private int rotationInterval;
    private String checkedCamera;

    public NetvideoRotationConfig(long rotationId, String rotationName,
                                  int rotationInterval, String checkedCamera) {
        super();
        this.rotationId = rotationId;
        this.rotationName = rotationName;
        this.rotationInterval = rotationInterval;
        this.checkedCamera = checkedCamera;
    }

    public NetvideoRotationConfig() {
        super();
    }

    public long getRotationId() {
        return rotationId;
    }

    public void setRotationId(long rotationId) {
        this.rotationId = rotationId;
    }

    public String getRotationName() {
        return rotationName;
    }

    public void setRotationName(String rotationName) {
        this.rotationName = rotationName;
    }

    public int getRotationInterval() {
        return rotationInterval;
    }

    public void setRotationInterval(int rotationInterval) {
        this.rotationInterval = rotationInterval;
    }

    public String getCheckedCamera() {
        return checkedCamera;
    }

    public void setCheckedCamera(String checkedCamera) {
        this.checkedCamera = checkedCamera;
    }

}
