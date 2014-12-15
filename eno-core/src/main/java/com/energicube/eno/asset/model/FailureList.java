package com.energicube.eno.asset.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * 故障层次结构
 */
@Entity
@Table(name = "FAILURELIST")
public class FailureList implements java.io.Serializable {

    private static final long serialVersionUID = -7840201211234058510L;
    private long failurelist;
    private String failurecode;
    private String orgid;
    private Long parent;
    private String type;

    public FailureList() {
    }

    public FailureList(long failurelist) {
        this.failurelist = failurelist;
    }

    public FailureList(long failurelist, String failurecode, String orgid,
                       Long parent, String type) {
        this.failurelist = failurelist;
        this.failurecode = failurecode;
        this.orgid = orgid;
        this.parent = parent;
        this.type = type;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FAILURELIST", unique = true, nullable = false)
    public long getFailurelist() {
        return this.failurelist;
    }

    public void setFailurelist(long failurelist) {
        this.failurelist = failurelist;
    }

    @NotEmpty
    @Column(name = "FAILURECODE", length = 8)
    public String getFailurecode() {
        return this.failurecode;
    }

    public void setFailurecode(String failurecode) {
        this.failurecode = failurecode;
    }

    @Column(name = "ORGID", length = 8)
    public String getOrgid() {
        return this.orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    @Column(name = "PARENT")
    public Long getParent() {
        return this.parent;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }

    @Column(name = "TYPE", length = 12)
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "FailureList [failurelist=" + failurelist + ", failurecode="
                + failurecode + ", orgid=" + orgid + ", parent=" + parent
                + ", type=" + type + "]";
    }

}
