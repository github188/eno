package com.energicube.eno.pattern.model;
// Generated 2013-8-21 16:23:12 by Hibernate Tools 3.4.0.CR1


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * UcGroupDevice generated by hbm2java
 */
@Entity
@Table(name = "UC_groupDevice"
        , schema = "zclfsys"
)
public class UcGroupDevice implements java.io.Serializable {


    private String id;
    private UcLogicGroup ucLogicGroup;
    private String deviceId;

    public UcGroupDevice() {
    }


    public UcGroupDevice(String id) {
        this.id = id;
    }

    public UcGroupDevice(String id, UcLogicGroup ucLogicGroup, String deviceId) {
        this.id = id;
        this.ucLogicGroup = ucLogicGroup;
        this.deviceId = deviceId;
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
    @JoinColumn(name = "logicGroupId")
    public UcLogicGroup getUcLogicGroup() {
        return this.ucLogicGroup;
    }

    public void setUcLogicGroup(UcLogicGroup ucLogicGroup) {
        this.ucLogicGroup = ucLogicGroup;
    }


    @Column(name = "deviceId", length = 36)
    public String getDeviceId() {
        return this.deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }


}


