package com.energicube.eno.monitor.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 客流每月的统计数据实体
 * Created by LiuGuanglu
 * 2014/5/22.
 */
@Entity
@Table(name="UC_PassengerFlowMonth" ,schema="dbo")
public class UcPassengerFlowMonth implements Serializable {
    private String id;
    private String shopName;
    private String shopCode;
    private int inCount ;
    private int outCount;
    private int dateYear;
    private int dateMonth;
    private int minValue;
    private int maxValue;
    private int avgValue;

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(name="id", unique=true, nullable=false, length=36)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name="shopName", length=100)
    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    @Column(name="shopCode", length=50)
    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    @Column(name="inCount")
    public int getInCount() {
        return inCount;
    }

    public void setInCount(int inCount) {
        this.inCount = inCount;
    }

    @Column(name="outCount")
    public int getOutCount() {
        return outCount;
    }

    public void setOutCount(int outCount) {
        this.outCount = outCount;
    }

    @Column(name="dateYear")
    public int getDateYear() {
        return dateYear;
    }

    public void setDateYear(int dateYear) {
        this.dateYear = dateYear;
    }

    @Column(name="dateMonth")
    public int getDateMonth() {
        return dateMonth;
    }

    public void setDateMonth(int dateMonth) {
        this.dateMonth = dateMonth;
    }

    @Column(name="minValue")
    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    @Column(name="maxValue")
    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    @Column(name="avgValue")
    public int getAvgValue() {
        return avgValue;
    }

    public void setAvgValue(int avgValue) {
        this.avgValue = avgValue;
    }

    @Override
    public String toString() {
        return "UcPassengerFlowMonth{" +
                "id='" + id + '\'' +
                ", shopName='" + shopName + '\'' +
                ", shopCode='" + shopCode + '\'' +
                ", inCount=" + inCount +
                ", outCount=" + outCount +
                ", dateYear=" + dateYear +
                ", dateMonth=" + dateMonth +
                ", minValue=" + minValue +
                ", maxValue=" + maxValue +
                ", avgValue=" + avgValue +
                '}';
    }
}
