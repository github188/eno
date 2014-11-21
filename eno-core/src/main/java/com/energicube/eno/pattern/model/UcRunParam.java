package com.energicube.eno.pattern.model;
// Generated 2013-8-21 16:23:12 by Hibernate Tools 3.4.0.CR1


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * UcRunParam generated by hbm2java
 */
@Entity
@Table(name = "UC_runParam"
        , schema = "dbo"
)
public class UcRunParam implements java.io.Serializable {


    private String id;
    @org.codehaus.jackson.annotate.JsonBackReference
    @com.fasterxml.jackson.annotation.JsonBackReference
    private UcDevicePattern ucDevicePattern;
    private String paramName;
    private String paramValue;

    public UcRunParam() {
    }


    public UcRunParam(String id) {
        this.id = id;
    }

    public UcRunParam(String id, UcDevicePattern ucDevicePattern, String paramName, String paramValue) {
        this.id = id;
        this.ucDevicePattern = ucDevicePattern;
        this.paramName = paramName;
        this.paramValue = paramValue;
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
    @JoinColumn(name = "deviceId")
    public UcDevicePattern getUcDevicePattern() {
        return this.ucDevicePattern;
    }

    public void setUcDevicePattern(UcDevicePattern ucDevicePattern) {
        this.ucDevicePattern = ucDevicePattern;
    }


    @Column(name = "paramName", length = 200)
    public String getParamName() {
        return this.paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }


    @Column(name = "paramValue", length = 100)
    public String getParamValue() {
        return this.paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue;
    }


}

