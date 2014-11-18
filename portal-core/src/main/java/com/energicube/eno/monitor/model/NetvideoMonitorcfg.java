package com.energicube.eno.monitor.model;


import javax.persistence.*;

/**
 * 监视器设置
 */
@Entity
@Table(name = "NETVIDEO_MONITORCFG")
public class NetvideoMonitorcfg implements java.io.Serializable {

    private static final long serialVersionUID = -390725746286283319L;
    private Integer displaysequence;
    private int monitorid;
    private String monitorname;
    private String resourceid;

    public NetvideoMonitorcfg() {
    }

    public NetvideoMonitorcfg(int monitorid) {
        this.monitorid = monitorid;
    }

    public NetvideoMonitorcfg(Integer displaysequence, int monitorid, String monitorname,
                              String resourceid) {
        this.displaysequence = displaysequence;
        this.monitorid = monitorid;
        this.monitorname = monitorname;
        this.resourceid = resourceid;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)//编号自增
    @Column(name = "DISPLAYSEQUENCE", unique = true, nullable = false)
    public Integer getDisplaysequence() {
        return displaysequence;
    }

    public void setDisplaysequence(Integer displaysequence) {
        this.displaysequence = displaysequence;
    }

    @Id
    @Column(name = "MONITORID", unique = true, nullable = false)
    public int getMonitorid() {
        return this.monitorid;
    }

    public void setMonitorid(int monitorid) {
        this.monitorid = monitorid;
    }

    @Column(name = "MONITORNAME", length = 50)
    public String getMonitorname() {
        return this.monitorname;
    }

    public void setMonitorname(String monitorname) {
        this.monitorname = monitorname;
    }

    @Column(name = "RESOURCEID")
    public String getResourceid() {
        return this.resourceid;
    }

    public void setResourceid(String resourceid) {
        this.resourceid = resourceid;
    }

}
