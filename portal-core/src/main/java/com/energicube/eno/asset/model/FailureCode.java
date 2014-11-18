package com.energicube.eno.asset.model;


import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * 故障代码
 */
@Entity
@Table(name = "FAILURECODE", schema = "dbo")
public class FailureCode implements java.io.Serializable {

    private static final long serialVersionUID = 2740772609992251229L;
    private long failurecodeid;
    private String failurecode;
    private String description;
    private Boolean hasld;
    private String langcode;
    private String orgid;

    public FailureCode() {
    }

    public FailureCode(long failurecodeid) {
        this.failurecodeid = failurecodeid;
    }

    public FailureCode(long failurecodeid, String failurecode,
                       String description, Boolean hasld, String langcode, String orgid) {
        this.failurecodeid = failurecodeid;
        this.failurecode = failurecode;
        this.description = description;
        this.hasld = hasld;
        this.langcode = langcode;
        this.orgid = orgid;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FAILURECODEID", unique = true, nullable = false)
    public long getFailurecodeid() {
        return this.failurecodeid;
    }

    public void setFailurecodeid(long failurecodeid) {
        this.failurecodeid = failurecodeid;
    }

    @NotEmpty
    @Column(name = "FAILURECODE", length = 8)
    public String getFailurecode() {
        return this.failurecode;
    }

    public void setFailurecode(String failurecode) {
        this.failurecode = failurecode;
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

    @Override
    public String toString() {
        return "FailureCode [failurecodeid=" + failurecodeid + ", failurecode="
                + failurecode + ", description=" + description + ", hasld="
                + hasld + ", langcode=" + langcode + ", orgid=" + orgid + "]";
    }

}
