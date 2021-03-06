package com.energicube.eno.pattern.model;
// Generated 2013-8-21 16:23:12 by Hibernate Tools 3.4.0.CR1


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * UcStrategyParam generated by hbm2java
 */
@Entity
@Table(name = "UC_strategyParam"
        
)
public class UcStrategyParam implements java.io.Serializable {


    private String id;
    private UcDeviceStrategy ucDeviceStrategy;
    private String paramName;
    private String paramValue;
    private String valueType;

    public UcStrategyParam() {
    }


    public UcStrategyParam(String id) {
        this.id = id;
    }

    public UcStrategyParam(String id, UcDeviceStrategy ucDeviceStrategy, String paramName, String paramValue) {
        this.id = id;
        this.ucDeviceStrategy = ucDeviceStrategy;
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
    @JoinColumn(name = "strategyId")
    public UcDeviceStrategy getUcDeviceStrategy() {
        return this.ucDeviceStrategy;
    }

    public void setUcDeviceStrategy(UcDeviceStrategy ucDeviceStrategy) {
        this.ucDeviceStrategy = ucDeviceStrategy;
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

    @Column(name = "valueType", length = 10)
    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }
}


