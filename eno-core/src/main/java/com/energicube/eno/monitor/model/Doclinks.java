package com.energicube.eno.monitor.model;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

/**
 * 与文档链接的库存项目注册
 */
@Entity
@Table(name = "DOCLINKS", schema = "zclfsys")
public class Doclinks implements java.io.Serializable {

    private static final long serialVersionUID = 9083087483405162923L;
    private long doclinksid;
    private long docinfoid;
    private String document;
    private String ownertable;
    private Long ownerid;
    private String reference;
    private String doctype;
    private String docversion;
    private Integer getlatestversion;
    private String createby;
    private DateTime createdate;
    private String changeby;
    private DateTime changedate;
    private Boolean printthrulink;
    private Boolean copylinktowo;

    public Doclinks() {
    }

    public Doclinks(long doclinksid, long docinfoid, String document) {
        this.doclinksid = doclinksid;
        this.docinfoid = docinfoid;
        this.document = document;
    }

    public Doclinks(long doclinksid, long docinfoid, String document,
                    String ownertable, Long ownerid, String reference, String doctype,
                    String docversion, Integer getlatestversion, String createby,
                    DateTime createdate, String changeby, DateTime changedate,
                    Boolean printthrulink, Boolean copylinktowo) {
        this.doclinksid = doclinksid;
        this.docinfoid = docinfoid;
        this.document = document;
        this.ownertable = ownertable;
        this.ownerid = ownerid;
        this.reference = reference;
        this.doctype = doctype;
        this.docversion = docversion;
        this.getlatestversion = getlatestversion;
        this.createby = createby;
        this.createdate = createdate;
        this.changeby = changeby;
        this.changedate = changedate;
        this.printthrulink = printthrulink;
        this.copylinktowo = copylinktowo;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DOCLINKSID", unique = true, nullable = false)
    public long getDoclinksid() {
        return this.doclinksid;
    }

    public void setDoclinksid(long doclinksid) {
        this.doclinksid = doclinksid;
    }

    @Column(name = "DOCINFOID", nullable = false)
    public long getDocinfoid() {
        return this.docinfoid;
    }

    public void setDocinfoid(long docinfoid) {
        this.docinfoid = docinfoid;
    }

    @Column(name = "DOCUMENT", nullable = false, length = 20)
    public String getDocument() {
        return this.document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    @Column(name = "OWNERTABLE", length = 30)
    public String getOwnertable() {
        return this.ownertable;
    }

    public void setOwnertable(String ownertable) {
        this.ownertable = ownertable;
    }

    @Column(name = "OWNERID")
    public Long getOwnerid() {
        return this.ownerid;
    }

    public void setOwnerid(Long ownerid) {
        this.ownerid = ownerid;
    }

    @Column(name = "REFERENCE", length = 8)
    public String getReference() {
        return this.reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @Column(name = "DOCTYPE", length = 16)
    public String getDoctype() {
        return this.doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    @Column(name = "DOCVERSION", length = 20)
    public String getDocversion() {
        return this.docversion;
    }

    public void setDocversion(String docversion) {
        this.docversion = docversion;
    }

    @Column(name = "GETLATESTVERSION")
    public Integer getGetlatestversion() {
        return this.getlatestversion;
    }

    public void setGetlatestversion(Integer getlatestversion) {
        this.getlatestversion = getlatestversion;
    }

    @Column(name = "CREATEBY", length = 30)
    public String getCreateby() {
        return this.createby;
    }

    public void setCreateby(String createby) {
        this.createby = createby;
    }

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "CREATEDATE", length = 23)
    public DateTime getCreatedate() {
        return this.createdate;
    }

    public void setCreatedate(DateTime createdate) {
        this.createdate = createdate;
    }

    @Column(name = "CHANGEBY", length = 30)
    public String getChangeby() {
        return this.changeby;
    }

    public void setChangeby(String changeby) {
        this.changeby = changeby;
    }

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "CHANGEDATE", length = 23)
    public DateTime getChangedate() {
        return this.changedate;
    }

    public void setChangedate(DateTime changedate) {
        this.changedate = changedate;
    }

    @Column(name = "PRINTTHRULINK")
    public Boolean getPrintthrulink() {
        return this.printthrulink;
    }

    public void setPrintthrulink(Boolean printthrulink) {
        this.printthrulink = printthrulink;
    }

    @Column(name = "COPYLINKTOWO")
    public Boolean getCopylinktowo() {
        return this.copylinktowo;
    }

    public void setCopylinktowo(Boolean copylinktowo) {
        this.copylinktowo = copylinktowo;
    }

}
