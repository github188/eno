package com.energicube.eno.pattern.model;
// Generated 2013-8-21 16:23:12 by Hibernate Tools 3.4.0.CR1


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * UcSystemParam generated by hbm2java
 */
@Entity
@Table(name = "UC_systemParam"
        
)
public class UcSystemParam implements java.io.Serializable {


    private String id;
    @org.codehaus.jackson.annotate.JsonBackReference
    @com.fasterxml.jackson.annotation.JsonBackReference
    private UcDeviceSystem ucDeviceSystem;
    private String paramName;
    private String paramEnName;

    public UcSystemParam() {
    }


    public UcSystemParam(String id) {
        this.id = id;
    }

    public UcSystemParam(String id, UcDeviceSystem ucDeviceSystem, String paramName) {
        this.id = id;
        this.ucDeviceSystem = ucDeviceSystem;
        this.paramName = paramName;
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


    @Column(name = "paramName", length = 100)
    public String getParamName() {
        return this.paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    @Column(name = "paramEnName", length = 100)
    public String getParamEnName() {
        return paramEnName;
    }

    public void setParamEnName(String paramEnName) {
        this.paramEnName = paramEnName;
    }
}


