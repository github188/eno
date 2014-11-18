package com.energicube.eno.asset.model;


import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;

/**
 * 公司定义（用于资产的供应商与生产商）
 */
@Entity
@Table(name = "COMPANIES", schema = "dbo")
public class Companies implements java.io.Serializable {

    private static final long serialVersionUID = -2466114221353344873L;
    private long companiesid;
    private String address0;
    private String address1;
    private String address2;
    private String address3;
    private String address4;
    private String address5;
    private String cellphone;
    private String changeby;
    private Date changedate;
    private String company;
    private String currencycode;
    private String externalrefid;
    private String fax;
    private Boolean hasld;
    private String homepage;
    private String langcode;
    private String name;
    private String orgid;
    private String ownersysid;
    private String parentcompany;
    private String phone;
    private String sendersysid;
    private String sourcesysid;
    private String type;

    public Companies() {
    }

    public Companies(long companiesid) {
        this.companiesid = companiesid;
    }

    public Companies(long companiesid, String address1, String address2,
                     String address3, String address4, String address5,
                     String cellphone, String changeby, Date changedate, String company,
                     String currencycode, String externalrefid, String fax,
                     Boolean hasld, String homepage, String langcode, String name,
                     String orgid, String ownersysid, String parentcompany,
                     String phone, String sendersysid, String sourcesysid, String type) {
        this.companiesid = companiesid;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.address4 = address4;
        this.address5 = address5;
        this.cellphone = cellphone;
        this.changeby = changeby;
        this.changedate = changedate;
        this.company = company;
        this.currencycode = currencycode;
        this.externalrefid = externalrefid;
        this.fax = fax;
        this.hasld = hasld;
        this.homepage = homepage;
        this.langcode = langcode;
        this.name = name;
        this.orgid = orgid;
        this.ownersysid = ownersysid;
        this.parentcompany = parentcompany;
        this.phone = phone;
        this.sendersysid = sendersysid;
        this.sourcesysid = sourcesysid;
        this.type = type;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMPANIESID", unique = true, nullable = false)
    public long getCompaniesid() {
        return this.companiesid;
    }

    public void setCompaniesid(long companiesid) {
        this.companiesid = companiesid;
    }

    @Column(name = "ADDRESS0", length = 50)
    public String getAddress0() {
        return address0;
    }

    public void setAddress0(String address0) {
        this.address0 = address0;
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

    @Column(name = "CELLPHONE", length = 20)
    public String getCellphone() {
        return this.cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    @Column(name = "CHANGEBY", length = 30)
    public String getChangeby() {
        return this.changeby;
    }

    public void setChangeby(String changeby) {
        this.changeby = changeby;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CHANGEDATE", length = 23)
    public Date getChangedate() {
        return this.changedate;
    }

    public void setChangedate(Date changedate) {
        this.changedate = changedate;
    }

    @NotEmpty
    @Column(name = "COMPANY", length = 12)
    public String getCompany() {
        return this.company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    @Column(name = "CURRENCYCODE", length = 8)
    public String getCurrencycode() {
        return this.currencycode;
    }

    public void setCurrencycode(String currencycode) {
        this.currencycode = currencycode;
    }

    @Column(name = "EXTERNALREFID", length = 10)
    public String getExternalrefid() {
        return this.externalrefid;
    }

    public void setExternalrefid(String externalrefid) {
        this.externalrefid = externalrefid;
    }

    @Column(name = "FAX", length = 20)
    public String getFax() {
        return this.fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Column(name = "HASLD")
    public Boolean getHasld() {
        return this.hasld;
    }

    public void setHasld(Boolean hasld) {
        this.hasld = hasld;
    }

    @Column(name = "HOMEPAGE", length = 250)
    public String getHomepage() {
        return this.homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    @Column(name = "LANGCODE", length = 4)
    public String getLangcode() {
        return this.langcode;
    }

    public void setLangcode(String langcode) {
        this.langcode = langcode;
    }

    @NotEmpty
    @Column(name = "NAME", length = 50)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "ORGID", length = 8)
    public String getOrgid() {
        return this.orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    @Column(name = "OWNERSYSID", length = 10)
    public String getOwnersysid() {
        return this.ownersysid;
    }

    public void setOwnersysid(String ownersysid) {
        this.ownersysid = ownersysid;
    }

    @Column(name = "PARENTCOMPANY", length = 12)
    public String getParentcompany() {
        return this.parentcompany;
    }

    public void setParentcompany(String parentcompany) {
        this.parentcompany = parentcompany;
    }

    @Column(name = "PHONE", length = 20)
    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "SENDERSYSID", length = 50)
    public String getSendersysid() {
        return this.sendersysid;
    }

    public void setSendersysid(String sendersysid) {
        this.sendersysid = sendersysid;
    }

    @Column(name = "SOURCESYSID", length = 10)
    public String getSourcesysid() {
        return this.sourcesysid;
    }

    public void setSourcesysid(String sourcesysid) {
        this.sourcesysid = sourcesysid;
    }

    @Column(name = "TYPE", length = 1)
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
