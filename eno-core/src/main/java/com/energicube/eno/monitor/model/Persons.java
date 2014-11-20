package com.energicube.eno.monitor.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;

/**
 * 人员相关的资源信息定义
 */
@Entity
@Table(name = "PERSON")
public class Persons implements java.io.Serializable {

    private static final long serialVersionUID = 8587108903001113811L;
    private long personid;
    private String userid;
    private Boolean hasld;
    private String firstname;
    private String lastname;
    private String displayname;
    private String department;
    private String sex;
    private String jobdelegate;
    private Date birthdate;
    private Date hiredate;
    private String employeetype;
    private String jobcode;
    private String supervisor;
    private String imId;
    private String caltype;
    private String city;
    private String country;
    private String county;
    private String langcode;
    private String language;
    private String locale;
    private String postalcode;
    private String regiondistrict;
    private String stateprovince;
    private String addressline1;
    private String addressline2;
    private String addressline3;
    private String timezone;
    private String workemail;
    private String email;
    private String workphone;
    private String phone;
    private String userface;
    private Integer facewidth = 0;
    private Integer faceheight = 0;

    public Persons() {
    }

    public Persons(long personid) {
        this.personid = personid;
    }

    public Persons(long personid, String userid, Boolean hasld,
                   String firstname, String lastname, String displayname,
                   String department, String sex, String jobdelegate, Date birthdate,
                   Date hiredate, String employeetype, String jobcode,
                   String supervisor, String imId, String caltype, String city,
                   String country, String county, String langcode, String language,
                   String locale, String postalcode, String regiondistrict,
                   String stateprovince, String addressline1, String addressline2,
                   String addressline3, String timezone, String workemail,
                   String email, String workphone, String phone) {
        this.personid = personid;
        this.userid = userid;
        this.hasld = hasld;
        this.firstname = firstname;
        this.lastname = lastname;
        this.displayname = displayname;
        this.department = department;
        this.sex = sex;
        this.jobdelegate = jobdelegate;
        this.birthdate = birthdate;
        this.hiredate = hiredate;
        this.employeetype = employeetype;
        this.jobcode = jobcode;
        this.supervisor = supervisor;
        this.imId = imId;
        this.caltype = caltype;
        this.city = city;
        this.country = country;
        this.county = county;
        this.langcode = langcode;
        this.language = language;
        this.locale = locale;
        this.postalcode = postalcode;
        this.regiondistrict = regiondistrict;
        this.stateprovince = stateprovince;
        this.addressline1 = addressline1;
        this.addressline2 = addressline2;
        this.addressline3 = addressline3;
        this.timezone = timezone;
        this.workemail = workemail;
        this.email = email;
        this.workphone = workphone;
        this.phone = phone;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PersonID", unique = true, nullable = false)
    public long getPersonid() {
        return personid;
    }

    public void setPersonid(long personid) {
        this.personid = personid;
    }


    @NotEmpty
    @Column(name = "USERID", length = 50)
    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Column(name = "HASLD")
    public Boolean getHasld() {
        return this.hasld;
    }

    public void setHasld(Boolean hasld) {
        this.hasld = hasld;
    }

    @Column(name = "FIRSTNAME", length = 30)
    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Column(name = "LASTNAME", length = 30)
    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Column(name = "DISPLAYNAME", length = 62)
    public String getDisplayname() {
        return this.displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    @Column(name = "DEPARTMENT", length = 30)
    public String getDepartment() {
        return this.department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Column(name = "SEX", length = 10)
    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Column(name = "JOBDELEGATE", length = 50)
    public String getJobdelegate() {
        return this.jobdelegate;
    }

    public void setJobdelegate(String jobdelegate) {
        this.jobdelegate = jobdelegate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "BIRTHDATE", length = 23)
    public Date getBirthdate() {
        return this.birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "HIREDATE", length = 23)
    public Date getHiredate() {
        return this.hiredate;
    }

    public void setHiredate(Date hiredate) {
        this.hiredate = hiredate;
    }

    @Column(name = "EMPLOYEETYPE", length = 10)
    public String getEmployeetype() {
        return this.employeetype;
    }

    public void setEmployeetype(String employeetype) {
        this.employeetype = employeetype;
    }

    @Column(name = "JOBCODE", length = 10)
    public String getJobcode() {
        return this.jobcode;
    }

    public void setJobcode(String jobcode) {
        this.jobcode = jobcode;
    }

    @Column(name = "SUPERVISOR", length = 50)
    public String getSupervisor() {
        return this.supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    @Column(name = "IM_ID", length = 50)
    public String getImId() {
        return this.imId;
    }

    public void setImId(String imId) {
        this.imId = imId;
    }

    @Column(name = "CALTYPE", length = 10)
    public String getCaltype() {
        return this.caltype;
    }

    public void setCaltype(String caltype) {
        this.caltype = caltype;
    }

    @Column(name = "CITY", length = 36)
    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "COUNTRY", length = 36)
    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name = "COUNTY", length = 36)
    public String getCounty() {
        return this.county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    @Column(name = "LANGCODE", length = 6)
    public String getLangcode() {
        return this.langcode;
    }

    public void setLangcode(String langcode) {
        this.langcode = langcode;
    }

    @Column(name = "LANGUAGE", length = 6)
    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Column(name = "LOCALE", length = 10)
    public String getLocale() {
        return this.locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    @Column(name = "POSTALCODE", length = 12)
    public String getPostalcode() {
        return this.postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    @Column(name = "REGIONDISTRICT", length = 36)
    public String getRegiondistrict() {
        return this.regiondistrict;
    }

    public void setRegiondistrict(String regiondistrict) {
        this.regiondistrict = regiondistrict;
    }

    @Column(name = "STATEPROVINCE", length = 36)
    public String getStateprovince() {
        return this.stateprovince;
    }

    public void setStateprovince(String stateprovince) {
        this.stateprovince = stateprovince;
    }

    @Column(name = "ADDRESSLINE1", length = 60)
    public String getAddressline1() {
        return this.addressline1;
    }

    public void setAddressline1(String addressline1) {
        this.addressline1 = addressline1;
    }

    @Column(name = "ADDRESSLINE2", length = 60)
    public String getAddressline2() {
        return this.addressline2;
    }

    public void setAddressline2(String addressline2) {
        this.addressline2 = addressline2;
    }

    @Column(name = "ADDRESSLINE3", length = 60)
    public String getAddressline3() {
        return this.addressline3;
    }

    public void setAddressline3(String addressline3) {
        this.addressline3 = addressline3;
    }

    @Column(name = "TIMEZONE", length = 28)
    public String getTimezone() {
        return this.timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    @Column(name = "WORKEMAIL", length = 50)
    public String getWorkemail() {
        return this.workemail;
    }

    public void setWorkemail(String workemail) {
        this.workemail = workemail;
    }

    @Column(name = "EMAIL", length = 50)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "WORKPHONE", length = 30)
    public String getWorkphone() {
        return this.workphone;
    }

    public void setWorkphone(String workphone) {
        this.workphone = workphone;
    }

    @Column(name = "PHONE", length = 30)
    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "USERFACE", length = 255)
    public String getUserface() {
        return userface;
    }

    public void setUserface(String userface) {
        this.userface = userface;
    }

    @Column(name = "FACEWIDTH")
    public Integer getFacewidth() {
        return facewidth;
    }

    public void setFacewidth(Integer facewidth) {
        this.facewidth = facewidth;
    }

    @Column(name = "FACEHEIGHT")
    public Integer getFaceheight() {
        return faceheight;
    }

    public void setFaceheight(Integer faceheight) {
        this.faceheight = faceheight;
    }


}
