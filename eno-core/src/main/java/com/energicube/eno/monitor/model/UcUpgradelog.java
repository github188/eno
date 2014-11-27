package com.energicube.eno.monitor.model;


import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "UC_upgradelog", schema = "zclfsys")
public class UcUpgradelog implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private String tablename;
    private int ver;
    private Date upgradet;
    private String author;
    private String ucomment;
    private String appdata;

    public UcUpgradelog() {
    }

    public UcUpgradelog(String tablename, int ver, Date upgradet,
                        String author, String ucomment, String appdata) {
        this.tablename = tablename;
        this.ver = ver;
        this.upgradet = upgradet;
        this.author = author;
        this.ucomment = ucomment;
        this.appdata = appdata;
    }

    @Id
    @Column(name = "tablename", unique = true, nullable = false)
    public String getTablename() {
        return this.tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    @Column(name = "ver", nullable = false)
    public int getVer() {
        return this.ver;
    }

    public void setVer(int ver) {
        this.ver = ver;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "upgradet", nullable = false, length = 23)
    public Date getUpgradet() {
        return this.upgradet;
    }

    public void setUpgradet(Date upgradet) {
        this.upgradet = upgradet;
    }

    @Column(name = "author", nullable = false)
    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Column(name = "ucomment", nullable = false)
    public String getUcomment() {
        return this.ucomment;
    }

    public void setUcomment(String ucomment) {
        this.ucomment = ucomment;
    }

    @Column(name = "appdata", nullable = false, length = 4000)
    public String getAppdata() {
        return this.appdata;
    }

    public void setAppdata(String appdata) {
        this.appdata = appdata;
    }

}
