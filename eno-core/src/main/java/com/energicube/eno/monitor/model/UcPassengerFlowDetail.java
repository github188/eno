package com.energicube.eno.monitor.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 客流实体
 * Created by LiuGuanglu
 * 2014/5/22.
 */
@Entity
@Table(name="UC_PassengerFlowDetail" ,schema="dbo"
)
public class UcPassengerFlowDetail implements Serializable {
    private String id;
    private String shopName;
    private String shopCode;
    private int inCount ;
    private int outCount;
    private int dateYear;
    private int dateMonth;
    private int dateDay;
    private int dateHour;
    private int dateMinute;
    private int dateSecond;
    private Date createDate;


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

    @Column(name="dateDay")
    public int getDateDay() {
        return dateDay;
    }

    public void setDateDay(int dateDay) {
        this.dateDay = dateDay;
    }

    @Column(name="dateHour")
    public int getDateHour() {
        return dateHour;
    }

    public void setDateHour(int dateHour) {
        this.dateHour = dateHour;
    }

    @Column(name="dateMinute")
    public int getDateMinute() {
        return dateMinute;
    }

    public void setDateMinute(int dateMinute) {
        this.dateMinute = dateMinute;
    }

    @Column(name="dateSecond")
    public int getDateSecond() {
        return dateSecond;
    }

    public void setDateSecond(int dateSecond) {
        this.dateSecond = dateSecond;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createDate", length = 23)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }



    @Override
    public String toString() {
        return "UcPassengerFlow{" +
                "id='" + id + '\'' +
                ", shopName='" + shopName + '\'' +
                ", shopCode='" + shopCode + '\'' +
                ", inCount=" + inCount +
                ", outCount=" + outCount +
                ", dateYear=" + dateYear +
                ", dateMonth=" + dateMonth +
                ", dateDay=" + dateDay +
                ", dateHour=" + dateHour +
                ", dateMinute=" + dateMinute +
                ", dateSecond=" + dateSecond +
                '}';
    }
}
