package com.energicube.eno.monitor.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 停车管理
 *
 * @author lihuihui
 */
@Entity
@Table(name = "SUB_parking")
public class SubParking implements Serializable {
    private String id;
    private String carNum;//车牌号
    private String inDate;
    private String outDate;
    private String cardType;
    private String cardNum;//卡号
    private String cardName;
    private String userName;
    private String comeName;
    private String goName;
    private String payMoney;

    @Id
    @Column(name = "id", unique = true, nullable = false)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "carNum", length = 10)
    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    @Column(name = "inDate", length = 20)
    public String getInDate() {
        return inDate;
    }

    public void setInDate(String inDate) {
        this.inDate = inDate;
    }

    @Column(name = "outDate", length = 20)
    public String getOutDate() {
        return outDate;
    }

    public void setOutDate(String outDate) {
        this.outDate = outDate;
    }

    @Column(name = "cardType", length = 10)
    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    @Column(name = "cardNum", length = 36)
    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    @Column(name = "cardName", length = 50)
    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    @Column(name = "userName", length = 50)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Column(name = "comeName", length = 50)
    public String getComeName() {
        return comeName;
    }

    public void setComeName(String comeName) {
        this.comeName = comeName;
    }

    @Column(name = "goName", length = 50)
    public String getGoName() {
        return goName;
    }

    public void setGoName(String goName) {
        this.goName = goName;
    }

    @Column(name = "payMoney", length = 20)
    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

}
