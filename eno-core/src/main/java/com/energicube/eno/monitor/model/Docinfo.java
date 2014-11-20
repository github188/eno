package com.energicube.eno.monitor.model;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

/**
 * 单个文档信息
 */
@Entity
@Table(name = "DOCINFO", schema = "dbo")
public class Docinfo implements java.io.Serializable {

    private static final long serialVersionUID = 1612928399807603895L;
    private long docinfoid;
    private String document;
    private String description;
    private String doctype;
    private String urltype;
    private String urlname;
    private String application;
    private String status;
    private DateTime statusdate;
    private String createby;
    private DateTime createdate;
    private Integer revision;
    private Boolean show;
    private String changeby;
    private DateTime changedate;
    private String doclocation;
    private String dmsname;
    private String urlparam1;
    private String urlparam4;
    private String urlparam2;
    private String urlparam3;
    private String urlparam5;
    private Boolean printthrulinkdflt;
    private Boolean usedefaultfilepath;
    private String langcode;
    private Boolean hasld;
    private String contentuid;

    public Docinfo() {
    }

    public Docinfo(long docinfoid, String document) {
        this.docinfoid = docinfoid;
        this.document = document;
    }

    public Docinfo(long docinfoid, String document, String description,
                   String doctype, String urltype, String urlname, String application,
                   String status, DateTime statusdate, String createby, DateTime createdate,
                   Integer revision, Boolean show, String changeby, DateTime changedate,
                   String doclocation, String dmsname, String urlparam1,
                   String urlparam4, String urlparam2, String urlparam3,
                   String urlparam5, Boolean printthrulinkdflt,
                   Boolean usedefaultfilepath, String langcode, Boolean hasld,
                   String contentuid) {
        this.docinfoid = docinfoid;
        this.document = document;
        this.description = description;
        this.doctype = doctype;
        this.urltype = urltype;
        this.urlname = urlname;
        this.application = application;
        this.status = status;
        this.statusdate = statusdate;
        this.createby = createby;
        this.createdate = createdate;
        this.revision = revision;
        this.show = show;
        this.changeby = changeby;
        this.changedate = changedate;
        this.doclocation = doclocation;
        this.dmsname = dmsname;
        this.urlparam1 = urlparam1;
        this.urlparam4 = urlparam4;
        this.urlparam2 = urlparam2;
        this.urlparam3 = urlparam3;
        this.urlparam5 = urlparam5;
        this.printthrulinkdflt = printthrulinkdflt;
        this.usedefaultfilepath = usedefaultfilepath;
        this.langcode = langcode;
        this.hasld = hasld;
        this.contentuid = contentuid;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DOCINFOID", unique = true, nullable = false)
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

    @Column(name = "DESCRIPTION", length = 254)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "DOCTYPE", length = 16)
    public String getDoctype() {
        return this.doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    @Column(name = "URLTYPE", length = 8)
    public String getUrltype() {
        return this.urltype;
    }

    public void setUrltype(String urltype) {
        this.urltype = urltype;
    }

    @Column(name = "URLNAME", length = 254)
    public String getUrlname() {
        return this.urlname;
    }

    public void setUrlname(String urlname) {
        this.urlname = urlname;
    }

    @Column(name = "APPLICATION", length = 20)
    public String getApplication() {
        return this.application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    @Column(name = "STATUS", length = 8)
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @Column(name = "STATUSDATE", length = 23)
    public DateTime getStatusdate() {
        return this.statusdate;
    }

    public void setStatusdate(DateTime statusdate) {
        this.statusdate = statusdate;
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

    @Column(name = "REVISION")
    public Integer getRevision() {
        return this.revision;
    }

    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    @Column(name = "SHOW")
    public Boolean getShow() {
        return this.show;
    }

    public void setShow(Boolean show) {
        this.show = show;
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

    @Column(name = "DOCLOCATION", length = 10)
    public String getDoclocation() {
        return this.doclocation;
    }

    public void setDoclocation(String doclocation) {
        this.doclocation = doclocation;
    }

    @Column(name = "DMSNAME", length = 30)
    public String getDmsname() {
        return this.dmsname;
    }

    public void setDmsname(String dmsname) {
        this.dmsname = dmsname;
    }

    @Column(name = "URLPARAM1", length = 32)
    public String getUrlparam1() {
        return this.urlparam1;
    }

    public void setUrlparam1(String urlparam1) {
        this.urlparam1 = urlparam1;
    }

    @Column(name = "URLPARAM4", length = 32)
    public String getUrlparam4() {
        return this.urlparam4;
    }

    public void setUrlparam4(String urlparam4) {
        this.urlparam4 = urlparam4;
    }

    @Column(name = "URLPARAM2", length = 32)
    public String getUrlparam2() {
        return this.urlparam2;
    }

    public void setUrlparam2(String urlparam2) {
        this.urlparam2 = urlparam2;
    }

    @Column(name = "URLPARAM3", length = 32)
    public String getUrlparam3() {
        return this.urlparam3;
    }

    public void setUrlparam3(String urlparam3) {
        this.urlparam3 = urlparam3;
    }

    @Column(name = "URLPARAM5", length = 32)
    public String getUrlparam5() {
        return this.urlparam5;
    }

    public void setUrlparam5(String urlparam5) {
        this.urlparam5 = urlparam5;
    }

    @Column(name = "PRINTTHRULINKDFLT")
    public Boolean getPrintthrulinkdflt() {
        return this.printthrulinkdflt;
    }

    public void setPrintthrulinkdflt(Boolean printthrulinkdflt) {
        this.printthrulinkdflt = printthrulinkdflt;
    }

    @Column(name = "USEDEFAULTFILEPATH")
    public Boolean getUsedefaultfilepath() {
        return this.usedefaultfilepath;
    }

    public void setUsedefaultfilepath(Boolean usedefaultfilepath) {
        this.usedefaultfilepath = usedefaultfilepath;
    }

    @Column(name = "LANGCODE", length = 4)
    public String getLangcode() {
        return this.langcode;
    }

    public void setLangcode(String langcode) {
        this.langcode = langcode;
    }

    @Column(name = "HASLD")
    public Boolean getHasld() {
        return this.hasld;
    }

    public void setHasld(Boolean hasld) {
        this.hasld = hasld;
    }

    @Column(name = "CONTENTUID", length = 50)
    public String getContentuid() {
        return this.contentuid;
    }

    public void setContentuid(String contentuid) {
        this.contentuid = contentuid;
    }

}
