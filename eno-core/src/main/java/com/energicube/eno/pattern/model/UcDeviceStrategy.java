package com.energicube.eno.pattern.model;
// Generated 2013-8-21 16:23:12 by Hibernate Tools 3.4.0.CR1


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * UcDeviceStrategy generated by hbm2java
 */
@Entity
@Table(name = "UC_deviceStrategy"
        
)
public class UcDeviceStrategy implements java.io.Serializable {


    private String id;
    private String strategyName;
    private String systemId;
    private String itemParam;
    private String itemMain;
    private String compareSymbol;
    private String valueType;
    private String compareValue;
    private String compareMain;
    private String compareParam;
    private String strategyType;
    private String siteId;
    private String orgId;
    private Set<UcStrategyParam> ucStrategyParams = new HashSet<UcStrategyParam>(0);

    public UcDeviceStrategy() {
    }


    public UcDeviceStrategy(String id) {
        this.id = id;
    }

    public UcDeviceStrategy(String id, String strategyName, String systemId, String itemParam, String compareSymbol, String valueType, String compareValue, String strategyType, Set<UcStrategyParam> ucStrategyParams) {
        this.id = id;
        this.strategyName = strategyName;
        this.systemId = systemId;
        this.itemParam = itemParam;
        this.compareSymbol = compareSymbol;
        this.valueType = valueType;
        this.compareValue = compareValue;
        this.strategyType = strategyType;
        this.ucStrategyParams = ucStrategyParams;
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


    @Column(name = "strategyName", length = 200)
    public String getStrategyName() {
        return this.strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }


    @Column(name = "systemId", length = 36)
    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }


    @Column(name = "itemParam", length = 100)
    public String getItemParam() {
        return this.itemParam;
    }

    public void setItemParam(String itemParam) {
        this.itemParam = itemParam;
    }


    @Column(name = "compareSymbol", length = 30)
    public String getCompareSymbol() {
        return this.compareSymbol;
    }

    public void setCompareSymbol(String compareSymbol) {
        this.compareSymbol = compareSymbol;
    }


    @Column(name = "valueType", length = 36)
    public String getValueType() {
        return this.valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }


    @Column(name = "compareValue", length = 30)
    public String getCompareValue() {
        return this.compareValue;
    }

    public void setCompareValue(String compareValue) {
        this.compareValue = compareValue;
    }


    @Column(name = "strategyType", length = 4)
    public String getStrategyType() {
        return this.strategyType;
    }

    public void setStrategyType(String strategyType) {
        this.strategyType = strategyType;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "ucDeviceStrategy", cascade = CascadeType.REMOVE)
    public Set<UcStrategyParam> getUcStrategyParams() {
        return this.ucStrategyParams;
    }

    public void setUcStrategyParams(Set<UcStrategyParam> ucStrategyParams) {
        this.ucStrategyParams = ucStrategyParams;
    }

    @Column(name = "siteId", length = 36)
    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    @Column(name = "orgId", length = 36)
    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @Column(name = "itemMain", length = 100)
    public String getItemMain() {
        return itemMain;
    }

    public void setItemMain(String itemMain) {
        this.itemMain = itemMain;
    }

    @Column(name = "compareMain", length = 100)
    public String getCompareMain() {
        return compareMain;
    }

    public void setCompareMain(String compareMain) {
        this.compareMain = compareMain;
    }

    @Column(name = "compareParam", length = 100)
    public String getCompareParam() {
        return compareParam;
    }

    public void setCompareParam(String compareParam) {
        this.compareParam = compareParam;
    }
}


