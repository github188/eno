package com.energicube.eno.common.dto;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: EnergyUser
 * Date: 13-11-27
 * Time: 下午2:12
 * 门禁信息
 */
public class SasacObject implements Serializable {

    String id;
    String contentMsg;
    String eventTime;
    String cardId;
    String userName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContentMsg() {
        return contentMsg;
    }

    public void setContentMsg(String contentMsg) {
        this.contentMsg = contentMsg;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
