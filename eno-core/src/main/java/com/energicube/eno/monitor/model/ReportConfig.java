package com.energicube.eno.monitor.model;

import javax.persistence.*;

/**
 * Created by energicube on 2014/8/21.
 * user:zengyin
 */
@Entity
@Table(name = "reportconfig")
public class ReportConfig implements java.io.Serializable {
    private static final long serialVersionUID = 8989193834006410770L;
    private int uid;
    private String system;
    private String devicetype;
    private String device;
    private String params;
    private String unit;
    private String name;
    private String id;
    private String ispd;
    private String comments;

    public ReportConfig() {
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid", unique = true, nullable = false)
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Column(name = "system", nullable = false)
    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    @Column(name = "devicetype", nullable = false)
    public String getDevicetype() {
        return devicetype;
    }

    @Column(name = "device", nullable = false)
    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    @Column(name = "params", nullable = false)
    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    @Column(name = "unit", nullable = true)
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setDevicetype(String devicetype) {
        this.devicetype = devicetype;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "id", nullable = false)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "ispd", nullable = true)
    public String getIspd() {
        return ispd;
    }

    public void setIspd(String ispd) {
        this.ispd = ispd;
    }

    @Column(name = "comments", nullable = true)
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

}
