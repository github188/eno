package com.energicube.eno.asset.model;


import javax.persistence.*;

/**
 * 同义数值域的定义
 */
@Entity
@Table(name = "SYNONYMDOMAIN", schema = "zclfsys")
public class SynonymDomain implements java.io.Serializable {

    private static final long serialVersionUID = -1541599142095847443L;
    /**
     * 唯一标识
     */
    private long synonymdomainid;
    /**
     * 是否为缺省值？
     */
    private Boolean defaults;
    /**
     * 值的描述
     */
    private String description;
    /**
     * 域的标识
     */
    private String domainid = "";
    /**
     * 指定了域值的组织标识
     */
    private String orgid;
    /**
     * 指定了域值的地点标识
     */
    private String siteid;
    /**
     * 同义值
     */
    private String value = "";
    /**
     * 域值唯一标识(值为domainid+"|"+value)
     */
    private String valueid;

    public SynonymDomain() {
    }

    public SynonymDomain(long synonymdomainid) {
        this.synonymdomainid = synonymdomainid;
    }

    public SynonymDomain(long synonymdomainid, Boolean defaults,
                         String description, String domainid, String orgid, String siteid,
                         String value, String valueid) {
        this.synonymdomainid = synonymdomainid;
        this.defaults = defaults;
        this.description = description;
        this.domainid = domainid;
        this.orgid = orgid;
        this.siteid = siteid;
        this.value = value;
        this.valueid = valueid;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SYNONYMDOMAINID", unique = true, nullable = false)
    public long getSynonymdomainid() {
        return this.synonymdomainid;
    }

    public void setSynonymdomainid(long synonymdomainid) {
        this.synonymdomainid = synonymdomainid;
    }

    @Column(name = "DEFAULTS")
    public Boolean getDefaults() {
        return this.defaults;
    }

    public void setDefaults(Boolean defaults) {
        this.defaults = defaults;
    }

    @Column(name = "DESCRIPTION", length = 256)
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

    @Column(name = "ORGID", length = 8)
    public String getOrgid() {
        return this.orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    @Column(name = "SITEID", length = 8)
    public String getSiteid() {
        return this.siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

    @Column(name = "VALUE", length = 50)
    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Column(name = "VALUEID", length = 256)
    public String getValueid() {
        return this.valueid;
    }

    public void setValueid(String valueid) {
        if (valueid == null || "".equals(valueid)) {
            valueid = this.domainid.toUpperCase().trim() + "|" + this.value.toUpperCase().replace(" ", "").trim();
        }
        this.valueid = valueid;
    }

}
