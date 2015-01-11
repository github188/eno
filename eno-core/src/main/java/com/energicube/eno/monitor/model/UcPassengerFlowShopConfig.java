package com.energicube.eno.monitor.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 客流店铺的配置
 * Created by LiuGuanglu
 * 2014/5/22.
 */
@Entity
@Table(name = "UC_PassengerFlowShopConfig")
public class UcPassengerFlowShopConfig implements Serializable {
    private String id;
    private String shopName;
    private String shopCode;
    private String targetShopName;
    private String targetShopCode;
    private String createDate;
    private String action;
    private String shopType;


    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(name = "id", unique = true, nullable = false, length = 36)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "shopName", length = 100)
    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    @Column(name = "shopCode", length = 50)
    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    @Column(name = "targetShopName", length = 100)
    public String getTargetShopName() {
        return targetShopName;
    }

    public void setTargetShopName(String targetShopName) {
        this.targetShopName = targetShopName;
    }

    @Column(name = "targetShopCode", length = 50)
    public String getTargetShopCode() {
        return targetShopCode;
    }

    public void setTargetShopCode(String targetShopCode) {
        this.targetShopCode = targetShopCode;
    }

    @Column(name = "createDate", length = 20)
    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    @Column(name = "action", length = 2)
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Column(name = "shopType", length = 10)
    public String getShopType() {
        return shopType;
    }

    public void setShopType(String shopType) {
        this.shopType = shopType;
    }

    @Override
    public String toString() {
        return "UcPassengerFlowShopConfig{" +
                "id='" + id + '\'' +
                ", shopName='" + shopName + '\'' +
                ", shopCode='" + shopCode + '\'' +
                ", targetShopName='" + targetShopName + '\'' +
                ", targetShopCode='" + targetShopCode + '\'' +
                ", createDate='" + createDate + '\'' +
                ", action='" + action + '\'' +
                '}';
    }
}