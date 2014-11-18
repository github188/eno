package com.energicube.eno.monitor.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NetvideoVersion implements Serializable {

    private long versionNo;
    private String configName;
    private String driverName;

    public NetvideoVersion(long versionNo, String configName, String driverName) {
        super();
        this.versionNo = versionNo;
        this.configName = configName;
        this.setDriverName(driverName);
    }

    public NetvideoVersion() {
        super();
    }

    public long getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(long versionNo) {
        this.versionNo = versionNo;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
}
