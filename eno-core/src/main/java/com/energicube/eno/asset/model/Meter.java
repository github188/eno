package com.energicube.eno.asset.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * 计量器定义
 */
@Entity
@Table(name = "METER", schema = "zclfsys")
public class Meter implements java.io.Serializable {

    private static final long serialVersionUID = -3753915973234174467L;
    private long meterid;
    private String description;
    private String domainid;
    private Boolean hasld;
    private String langcode;
    private String measureunitid;
    private String metername;
    private String metertype;
    private String readingtype;

    public Meter() {
    }

    public Meter(long meterid) {
        this.meterid = meterid;
    }

    public Meter(long meterid, String description, String domainid,
                 Boolean hasld, String langcode, String measureunitid,
                 String metername, String metertype, String readingtype) {
        this.meterid = meterid;
        this.description = description;
        this.domainid = domainid;
        this.hasld = hasld;
        this.langcode = langcode;
        this.measureunitid = measureunitid;
        this.metername = metername;
        this.metertype = metertype;
        this.readingtype = readingtype;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "METERID", unique = true, nullable = false)
    public long getMeterid() {
        return this.meterid;
    }

    public void setMeterid(long meterid) {
        this.meterid = meterid;
    }

    @Column(name = "DESCRIPTION", length = 100)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "DOMAINID", length = 18)
    public String getDomainid() {
        return this.domainid;
    }

    public void setDomainid(String domainid) {
        this.domainid = domainid;
    }

    @Column(name = "HASLD")
    public Boolean getHasld() {
        return this.hasld;
    }

    public void setHasld(Boolean hasld) {
        this.hasld = hasld;
    }

    @Column(name = "LANGCODE", length = 4)
    public String getLangcode() {
        return this.langcode;
    }

    public void setLangcode(String langcode) {
        this.langcode = langcode;
    }

    @Column(name = "MEASUREUNITID", length = 16)
    public String getMeasureunitid() {
        return this.measureunitid;
    }

    public void setMeasureunitid(String measureunitid) {
        this.measureunitid = measureunitid;
    }

    @NotEmpty
    @Column(name = "METERNAME", length = 30)
    public String getMetername() {
        return this.metername;
    }

    public void setMetername(String metername) {
        this.metername = metername;
    }

    @NotEmpty
    @Column(name = "METERTYPE", length = 25)
    public String getMetertype() {
        return this.metertype;
    }

    public void setMetertype(String metertype) {
        this.metertype = metertype;
    }

    @Column(name = "READINGTYPE", length = 10)
    public String getReadingtype() {
        return this.readingtype;
    }

    public void setReadingtype(String readingtype) {
        this.readingtype = readingtype;
    }

    @Override
    public String toString() {
        return "Meter [meterid=" + meterid + ", description=" + description
                + ", domainid=" + domainid + ", hasld=" + hasld + ", langcode="
                + langcode + ", measureunitid=" + measureunitid
                + ", metername=" + metername + ", metertype=" + metertype
                + ", readingtype=" + readingtype + "]";
    }

}
