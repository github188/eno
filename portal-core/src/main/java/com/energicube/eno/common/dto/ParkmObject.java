package com.energicube.eno.common.dto;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: EnergyUser
 * Date: 13-11-26
 * Time: 下午12:09
 * 停车信息.
 */
public class ParkmObject implements Serializable {

    String carNum;
    String inDate;
    String outDate;
    String payMoney;
    String comeName;
    String goName;
    String cardType;
    String cardNo;
    String cardName;

    public String getComeName() {
        return comeName;
    }

    public void setComeName(String comeName) {
        this.comeName = comeName;
    }

    public String getGoName() {
        return goName;
    }

    public void setGoName(String goName) {
        this.goName = goName;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getInDate() {
        return inDate;
    }

    public void setInDate(String inDate) {
        this.inDate = inDate;
    }

    public String getOutDate() {
        return outDate;
    }

    public void setOutDate(String outDate) {
        this.outDate = outDate;
    }

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }
}
