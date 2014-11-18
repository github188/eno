package com.energicube.eno.pattern.model;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * UcStrategyItem generated by hbm2java
 */
@Entity
@Table(name = "UC_strategyItem"
        , schema = "dbo"
)
public class UcStrategyItem implements java.io.Serializable {


    private String id;
    private String itemName;
    private String itemValue;
    private String systemId;
    private String parentId;

    public UcStrategyItem() {
    }


    public UcStrategyItem(String id) {
        this.id = id;
    }

    public UcStrategyItem(String id, String itemName, String itemValue, String systemId, String parentId) {
        this.id = id;
        this.itemName = itemName;
        this.itemValue = itemValue;
        this.systemId = systemId;
        this.parentId = parentId;
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


    @Column(name = "itemName", length = 100)
    public String getItemName() {
        return this.itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }


    @Column(name = "itemValue", length = 36)
    public String getItemValue() {
        return this.itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }


    @Column(name = "systemId", length = 36)
    public String getSystemId() {
        return this.systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }


    @Column(name = "parentId", length = 36)
    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }


}


