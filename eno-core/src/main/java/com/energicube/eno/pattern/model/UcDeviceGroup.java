package com.energicube.eno.pattern.model;

// Generated 2013-8-21 16:23:12 by Hibernate Tools 3.4.0.CR1

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * UcDeviceGroup generated by hbm2java
 */
@Entity
@Table(name = "UC_deviceGroup", schema = "zclfsys")
public class UcDeviceGroup implements java.io.Serializable {

    private String id;
    @org.codehaus.jackson.annotate.JsonBackReference
    @com.fasterxml.jackson.annotation.JsonBackReference
    private UcDeviceSystem ucDeviceSystem;
    private String deviceTypeId;

    public UcDeviceGroup() {
    }

    public UcDeviceGroup(String id) {
        this.id = id;
    }

    public UcDeviceGroup(String id, UcDeviceSystem ucDeviceSystem,
                         String deviceTypeId) {
        this.id = id;
        this.ucDeviceSystem = ucDeviceSystem;
        this.deviceTypeId = deviceTypeId;
    }

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(name = "id", unique = true, nullable = false, length = 36)
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY,cascade = CascadeType.DETACH)
    @JoinColumn(name = "systemId")
    public UcDeviceSystem getUcDeviceSystem() {
        return this.ucDeviceSystem;
    }

    public void setUcDeviceSystem(UcDeviceSystem ucDeviceSystem) {
        this.ucDeviceSystem = ucDeviceSystem;
    }

    @Column(name = "deviceTypeId", length = 36)
    public String getDeviceTypeId() {
        return this.deviceTypeId;
    }

    public void setDeviceTypeId(String deviceTypeId) {
        this.deviceTypeId = deviceTypeId;
    }

}
