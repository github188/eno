package com.energicube.eno.monitor.model;


import javax.persistence.*;

/**
 *
 */
@Entity
@Table(name = "OKCMSG", schema = "dbo")
public class OkcMsg implements java.io.Serializable {

    private static final long serialVersionUID = -4449701099700592539L;
    private long okcmsgid;
    private String title;
    private String msgcontent;
    private String displaymethod;
    private String msggroup;
    private String msgid;
    private String description;
    private String msgkeyword;
    private String operatorresp;

    public OkcMsg() {
    }

    public OkcMsg(long okcmsgid, String title, String msgcontent) {
        this.okcmsgid = okcmsgid;
        this.title = title;
        this.msgcontent = msgcontent;
    }

    public OkcMsg(long okcmsgid, String title, String msgcontent,
                  String displaymethod, String msggroup, String msgid,
                  String description, String msgkeyword, String operatorresp) {
        this.okcmsgid = okcmsgid;
        this.title = title;
        this.msgcontent = msgcontent;
        this.displaymethod = displaymethod;
        this.msggroup = msggroup;
        this.msgid = msgid;
        this.description = description;
        this.msgkeyword = msgkeyword;
        this.operatorresp = operatorresp;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OKCMSGID", unique = true, nullable = false)
    public long getOkcmsgid() {
        return this.okcmsgid;
    }

    public void setOkcmsgid(long okcmsgid) {
        this.okcmsgid = okcmsgid;
    }

    @Column(name = "TITLE", nullable = false, length = 100)
    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "MSGCONTENT", nullable = false, length = 2000)
    public String getMsgcontent() {
        return this.msgcontent;
    }

    public void setMsgcontent(String msgcontent) {
        this.msgcontent = msgcontent;
    }

    @Column(name = "DISPLAYMETHOD", length = 10)
    public String getDisplaymethod() {
        return this.displaymethod;
    }

    public void setDisplaymethod(String displaymethod) {
        this.displaymethod = displaymethod;
    }

    @Column(name = "MSGGROUP", length = 25)
    public String getMsggroup() {
        return this.msggroup;
    }

    public void setMsggroup(String msggroup) {
        this.msggroup = msggroup;
    }

    @Column(name = "MSGID", length = 20)
    public String getMsgid() {
        return this.msgid;
    }

    public void setMsgid(String msgid) {
        this.msgid = msgid;
    }

    @Column(name = "DESCRIPTION", length = 2000)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "MSGKEYWORD", length = 40)
    public String getMsgkeyword() {
        return this.msgkeyword;
    }

    public void setMsgkeyword(String msgkeyword) {
        this.msgkeyword = msgkeyword;
    }

    @Column(name = "OPERATORRESP", length = 2000)
    public String getOperatorresp() {
        return this.operatorresp;
    }

    public void setOperatorresp(String operatorresp) {
        this.operatorresp = operatorresp;
    }

}
