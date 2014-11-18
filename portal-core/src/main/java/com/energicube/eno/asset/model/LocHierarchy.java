package com.energicube.eno.asset.model;


import javax.persistence.*;

/**
 * 位置层次结构
 */
@Entity
@Table(name = "LOCHIERARCHY", schema = "dbo")
public class LocHierarchy implements java.io.Serializable {


    private static final long serialVersionUID = -4562801198300933280L;
    private long lochierarchyid;
    private Boolean children;
    private String location;
    private String orgid;
    private String parent;
    private String siteid;
    private String systemid;

    public LocHierarchy() {
    }

    public LocHierarchy(long lochierarchyid) {
        this.lochierarchyid = lochierarchyid;
    }

    public LocHierarchy(long lochierarchyid, Boolean children, String location,
                        String orgid, String parent, String siteid, String systemid) {
        this.lochierarchyid = lochierarchyid;
        this.children = children;
        this.location = location;
        this.orgid = orgid;
        this.parent = parent;
        this.siteid = siteid;
        this.systemid = systemid;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOCHIERARCHYID", unique = true, nullable = false)
    public long getLochierarchyid() {
        return this.lochierarchyid;
    }

    public void setLochierarchyid(long lochierarchyid) {
        this.lochierarchyid = lochierarchyid;
    }

    @Column(name = "CHILDREN")
    public Boolean getChildren() {
        return this.children;
    }

    public void setChildren(Boolean children) {
        this.children = children;
    }

    @Column(name = "LOCATION", length = 30)
    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Column(name = "ORGID", length = 8)
    public String getOrgid() {
        return this.orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    @Column(name = "PARENT", length = 12)
    public String getParent() {
        return this.parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    @Column(name = "SITEID", length = 8)
    public String getSiteid() {
        return this.siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

    @Column(name = "SYSTEMID", length = 8)
    public String getSystemid() {
        return this.systemid;
    }

    public void setSystemid(String systemid) {
        this.systemid = systemid;
    }

}
