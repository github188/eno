package com.energicube.eno.fssm.model;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 查岗消息类
 *
 * @author CHENPING
 */
@Entity
@Table(name = "FSSM_CHECKMONITOR")
public class CheckMonitor implements java.io.Serializable {

    private static final long serialVersionUID = -3356893994045499850L;

    private String msgid;// 消息id
    private String msgtype = "CG";// 消息类型
    private String syscode;// 系统代码
    private LocalDateTime timestamp;// 消息时间戳
    private String checktype;// 查岗类型 CG:查岗 ZG:在岗 TG:脱岗
    private Integer responsetime;// 最大响应时间(秒)
    private String relatemsg;// 相关消息，取查岗请求的msgid值
    private String description; // 描述信息
    private String userid;// 值班人员ID
    private String usertel;// 电话号码
    private int status = 0; //消息状态

    @Id
    @Column(name = "MSGID", unique = true, nullable = false, length = 64)
    public String getMsgid() {
        return this.msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="CET")
    @Column(name = "TIMESTAMP", length = 23)
    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    @NotEmpty
    @Column(name = "MSGTYPE", length = 12)
    public String getMsgtype() {
        return this.msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    @NotEmpty
    @Column(name = "SYSCODE", length = 30)
    public String getSyscode() {
        return this.syscode;
    }

    public void setSyscode(String syscode) {
        this.syscode = syscode;
    }

    @NotEmpty
    @Column(name = "CHECKTYPE")
    public String getChecktype() {
        return this.checktype;
    }

    public void setChecktype(String checktype) {
        this.checktype = checktype;
    }

    @Column(name = "RESPONSETIME")
    public Integer getResponsetime() {
        return this.responsetime;
    }

    public void setResponsetime(Integer responsetime) {
        this.responsetime = responsetime;
    }

    @Column(name = "RELATEMSG", length = 64)
    public String getRelatemsg() {
        return this.relatemsg;
    }

    public void setRelatemsg(String relatemsg) {
        this.relatemsg = relatemsg;
    }

    @Column(name = "DESCRIPTION", length = 200)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "USERID", length = 32)
    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Column(name = "USERTEL", length = 32)
    public String getUsertel() {
        return this.usertel;
    }

    public void setUsertel(String usertel) {
        this.usertel = usertel;
    }

    @Column(name = "STATUS")
    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


}
