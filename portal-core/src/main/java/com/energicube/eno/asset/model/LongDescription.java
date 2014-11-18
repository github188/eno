package com.energicube.eno.asset.model;

import javax.persistence.*;

/**
 * 长描述
 */
@Entity
@Table(name = "LONGDESCRIPTION", schema = "dbo")
public class LongDescription implements java.io.Serializable {

    private static final long serialVersionUID = 5739264475792366593L;
    private long longdescriptionid;
    private long ldkey;
    private String ldownertable;
    private String ldownercole;
    private String ldtext;
    private String langcode;
    private String contentuid;

    public LongDescription() {
    }

    public LongDescription(long longdescriptionid, long ldkey,
                           String ldownertable, String ldownercole, String langcode) {
        this.longdescriptionid = longdescriptionid;
        this.ldkey = ldkey;
        this.ldownertable = ldownertable;
        this.ldownercole = ldownercole;
        this.langcode = langcode;
    }

    public LongDescription(long longdescriptionid, long ldkey,
                           String ldownertable, String ldownercole, String ldtext,
                           String langcode, String contentuid) {
        this.longdescriptionid = longdescriptionid;
        this.ldkey = ldkey;
        this.ldownertable = ldownertable;
        this.ldownercole = ldownercole;
        this.ldtext = ldtext;
        this.langcode = langcode;
        this.contentuid = contentuid;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LONGDESCRIPTIONID", unique = true, nullable = false)
    public long getLongdescriptionid() {
        return this.longdescriptionid;
    }

    public void setLongdescriptionid(long longdescriptionid) {
        this.longdescriptionid = longdescriptionid;
    }

    @Column(name = "LDKEY", nullable = false)
    public long getLdkey() {
        return this.ldkey;
    }

    public void setLdkey(long ldkey) {
        this.ldkey = ldkey;
    }

    @Column(name = "LDOWNERTABLE", nullable = false, length = 30)
    public String getLdownertable() {
        return this.ldownertable;
    }

    public void setLdownertable(String ldownertable) {
        this.ldownertable = ldownertable;
    }

    @Column(name = "LDOWNERCOLE", nullable = false, length = 30)
    public String getLdownercole() {
        return this.ldownercole;
    }

    public void setLdownercole(String ldownercole) {
        this.ldownercole = ldownercole;
    }

    @Column(name = "LDTEXT", length = 4000)
    public String getLdtext() {
        return this.ldtext;
    }

    public void setLdtext(String ldtext) {
        this.ldtext = ldtext;
    }

    @Column(name = "LANGCODE", nullable = false, length = 4)
    public String getLangcode() {
        return this.langcode;
    }

    public void setLangcode(String langcode) {
        this.langcode = langcode;
    }

    @Column(name = "CONTENTUID", length = 10)
    public String getContentuid() {
        return this.contentuid;
    }

    public void setContentuid(String contentuid) {
        this.contentuid = contentuid;
    }

}
