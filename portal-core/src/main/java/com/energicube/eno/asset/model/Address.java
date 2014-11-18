package com.energicube.eno.asset.model;


import javax.persistence.*;

/**
 * 地址定义
 *
 * @author CHENPING
 */
@Entity
@Table(name = "ADDRESS", schema = "dbo")
public class Address implements java.io.Serializable {

    private static final long serialVersionUID = -4404140875328833441L;
    private long addressid;
    private String address1;
    private String address2;
    private String address3;
    private String address4;
    private String address5;
    private String addresscode;
    private String changeby;
    private String description;
    private Boolean hasld;
    private String langcode;
    private String orgid;

    public Address() {
    }

    public Address(long addressid) {
        this.addressid = addressid;
    }

    public Address(long addressid, String address1, String address2,
                   String address3, String address4, String address5,
                   String addresscode, String changeby, String description,
                   Boolean hasld, String langcode, String orgid) {
        this.addressid = addressid;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.address4 = address4;
        this.address5 = address5;
        this.addresscode = addresscode;
        this.changeby = changeby;
        this.description = description;
        this.hasld = hasld;
        this.langcode = langcode;
        this.orgid = orgid;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADDRESSID", unique = true, nullable = false)
    public long getAddressid() {
        return this.addressid;
    }

    public void setAddressid(long addressid) {
        this.addressid = addressid;
    }

    @Column(name = "ADDRESS1", length = 50)
    public String getAddress1() {
        return this.address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    @Column(name = "ADDRESS2", length = 50)
    public String getAddress2() {
        return this.address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    @Column(name = "ADDRESS3", length = 50)
    public String getAddress3() {
        return this.address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    @Column(name = "ADDRESS4", length = 50)
    public String getAddress4() {
        return this.address4;
    }

    public void setAddress4(String address4) {
        this.address4 = address4;
    }

    @Column(name = "ADDRESS5", length = 50)
    public String getAddress5() {
        return this.address5;
    }

    public void setAddress5(String address5) {
        this.address5 = address5;
    }

    @Column(name = "ADDRESSCODE", length = 30)
    public String getAddresscode() {
        return this.addresscode;
    }

    public void setAddresscode(String addresscode) {
        this.addresscode = addresscode;
    }

    @Column(name = "CHANGEBY", length = 50)
    public String getChangeby() {
        return this.changeby;
    }

    public void setChangeby(String changeby) {
        this.changeby = changeby;
    }

    @Column(name = "DESCRIPTION", length = 100)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Column(name = "ORGID", length = 8)
    public String getOrgid() {
        return this.orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

}
