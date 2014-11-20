package com.energicube.eno.monitor.model;


import javax.persistence.*;

/**
 * 部门科室
 */
@Entity
@Table(name = "CUDEPT", schema = "dbo")
public class Department implements java.io.Serializable {

    private static final long serialVersionUID = 5336032031219967416L;
    private int cudeptid;
    private Boolean active;
    private String deptnum;
    private Integer deptorder;
    private String description;
    private String gm;
    private Boolean hasld;
    private String orgid;
    private String siteid;
    private String parent;
    private String type;

    public Department() {
    }

    public Department(int cudeptid) {
        this.cudeptid = cudeptid;
    }

    public Department(int cudeptid, Boolean active, String deptnum,
                      Integer deptorder, String description, String gm, Boolean hasld,
                      String orgid, String siteid, String parent, String type) {
        this.cudeptid = cudeptid;
        this.active = active;
        this.deptnum = deptnum;
        this.deptorder = deptorder;
        this.description = description;
        this.gm = gm;
        this.hasld = hasld;
        this.orgid = orgid;
        this.siteid = siteid;
        this.parent = parent;
        this.type = type;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CUDEPTID", unique = true, nullable = false)
    public int getCudeptid() {
        return this.cudeptid;
    }

    public void setCudeptid(int cudeptid) {
        this.cudeptid = cudeptid;
    }

    @Column(name = "ACTIVE")
    public Boolean getActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Column(name = "DEPTNUM", length = 10)
    public String getDeptnum() {
        return this.deptnum;
    }

    public void setDeptnum(String deptnum) {
        this.deptnum = deptnum;
    }

    @Column(name = "DEPTORDER")
    public Integer getDeptorder() {
        return this.deptorder;
    }

    public void setDeptorder(Integer deptorder) {
        this.deptorder = deptorder;
    }

    @Column(name = "DESCRIPTION", length = 30)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "GM", length = 30)
    public String getGm() {
        return this.gm;
    }

    public void setGm(String gm) {
        this.gm = gm;
    }

    @Column(name = "HASLD")
    public Boolean getHasld() {
        return this.hasld;
    }

    public void setHasld(Boolean hasld) {
        this.hasld = hasld;
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

    @Column(name = "PARENT", length = 10)
    public String getParent() {
        return this.parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    @Column(name = "TYPE", length = 25)
    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
