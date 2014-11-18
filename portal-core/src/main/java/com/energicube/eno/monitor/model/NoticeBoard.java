package com.energicube.eno.monitor.model;

import java.util.Date;


public class NoticeBoard {

    private static final long serialVersionUID = -5492250996531388470L;

    private String msType;

    private String msContent;

    private String responsiblePerson;

    private String moment;

    private Date momentTime;

    public String getMsType() {
        return msType;
    }

    public void setMsType(String msType) {
        this.msType = msType;
    }

    public String getMsContent() {
        return msContent;
    }

    public Date getMomentTime() {
        return momentTime;
    }

    public void setMomentTime(Date momentTime) {
        this.momentTime = momentTime;
    }

    public void setMsContent(String msContent) {
        this.msContent = msContent;
    }

    public String getResponsiblePerson() {
        return responsiblePerson;
    }

    public void setResponsiblePerson(String responsiblePerson) {
        this.responsiblePerson = responsiblePerson;
    }

    public String getMoment() {
        return moment;
    }

    public void setMoment(String moment) {
        this.moment = moment;
    }
}
