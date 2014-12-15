package com.energicube.eno.monitor.model;

import javax.persistence.*;
import java.util.Date;

/**
 * 站内短消息
 */
@Entity
@Table(name = "message")
public class Message implements java.io.Serializable {

    private static final long serialVersionUID = -3243893623750804123L;

    private long messageId;  //消息ID
    private String title;    //消息主题
    private String content;  //消息内容
    private String sender;  //发送人
    private Long senderId;  //发送人ID
    private String incept;  //接收人
    private Long inceptId;    //接收人ID
    private String folder;
    private Date sendTime;  //发送时间
    private Boolean isSend;        //是否发送，未发送则为草稿
    private Boolean isDelInbox;    //是否删除(收件箱)
    private Boolean isDelSendbox;    //是否删除 (发件箱)
    private Boolean isRead;    //是否阅读

    public Message() {
    }

    public Message(long messageId) {
        this.messageId = messageId;
    }

    public Message(long messageId, String title, String content, String sender,
                   Long senderId, String incept, Long inceptId, String folder,
                   Date sendTime, Boolean isSend, Boolean isDelInbox,
                   Boolean isDelSendbox, Boolean isRead) {
        this.messageId = messageId;
        this.title = title;
        this.content = content;
        this.sender = sender;
        this.senderId = senderId;
        this.incept = incept;
        this.inceptId = inceptId;
        this.folder = folder;
        this.sendTime = sendTime;
        this.isSend = isSend;
        this.isDelInbox = isDelInbox;
        this.isDelSendbox = isDelSendbox;
        this.isRead = isRead;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "messageId", unique = true, nullable = false)
    public long getMessageId() {
        return this.messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    @Column(name = "title", length = 100)
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "content")
    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "sender", length = 50)
    public String getSender() {
        return this.sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    @Column(name = "senderId")
    public Long getSenderId() {
        return this.senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    @Column(name = "incept", length = 50)
    public String getIncept() {
        return this.incept;
    }

    public void setIncept(String incept) {
        this.incept = incept;
    }

    @Column(name = "inceptId")
    public Long getInceptId() {
        return this.inceptId;
    }

    public void setInceptId(Long inceptId) {
        this.inceptId = inceptId;
    }

    @Column(name = "folder", length = 20)
    public String getFolder() {
        return this.folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "sendTime", length = 23)
    public Date getSendTime() {
        return this.sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    @Column(name = "isSend")
    public Boolean getIsSend() {
        return this.isSend;
    }

    public void setIsSend(Boolean isSend) {
        this.isSend = isSend;
    }

    @Column(name = "isDelInbox")
    public Boolean getIsDelInbox() {
        return this.isDelInbox;
    }

    public void setIsDelInbox(Boolean isDelInbox) {
        this.isDelInbox = isDelInbox;
    }

    @Column(name = "isDelSendbox")
    public Boolean getIsDelSendbox() {
        return this.isDelSendbox;
    }

    public void setIsDelSendbox(Boolean isDelSendbox) {
        this.isDelSendbox = isDelSendbox;
    }

    @Column(name = "isRead")
    public Boolean getIsRead() {
        return this.isRead;
    }

    public void setIsRead(Boolean isRead) {
        this.isRead = isRead;
    }

}
