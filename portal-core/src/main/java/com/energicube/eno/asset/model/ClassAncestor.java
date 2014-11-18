package com.energicube.eno.asset.model;

import javax.persistence.*;

/**
 * 分类结构祖代和层级
 */
@Entity
@Table(name = "CLASSANCESTOR", schema = "dbo")
public class ClassAncestor implements java.io.Serializable {

    private static final long serialVersionUID = -1666284038784960034L;
    private long classancestorid;
    private String ancestor;
    private String ancestorclassid;
    private String classificationid;
    private String classstructureid;
    private Integer hierarchylevels;
    private String orgid;
    private String siteid;

    public ClassAncestor() {
    }

    public ClassAncestor(long classancestorid) {
        this.classancestorid = classancestorid;
    }

    public ClassAncestor(long classancestorid, String ancestor,
                         String ancestorclassid, String classificationid,
                         String classstructureid, Integer hierarchylevels, String orgid,
                         String siteid) {
        this.classancestorid = classancestorid;
        this.ancestor = ancestor;
        this.ancestorclassid = ancestorclassid;
        this.classificationid = classificationid;
        this.classstructureid = classstructureid;
        this.hierarchylevels = hierarchylevels;
        this.orgid = orgid;
        this.siteid = siteid;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLASSANCESTORID", unique = true, nullable = false)
    public long getClassancestorid() {
        return this.classancestorid;
    }

    public void setClassancestorid(long classancestorid) {
        this.classancestorid = classancestorid;
    }

    @Column(name = "ANCESTOR", length = 20)
    public String getAncestor() {
        return this.ancestor;
    }

    public void setAncestor(String ancestor) {
        this.ancestor = ancestor;
    }

    @Column(name = "ANCESTORCLASSID", length = 200)
    public String getAncestorclassid() {
        return this.ancestorclassid;
    }

    public void setAncestorclassid(String ancestorclassid) {
        this.ancestorclassid = ancestorclassid;
    }

    @Column(name = "CLASSIFICATIONID", length = 200)
    public String getClassificationid() {
        return this.classificationid;
    }

    public void setClassificationid(String classificationid) {
        this.classificationid = classificationid;
    }

    @Column(name = "CLASSSTRUCTUREID", length = 20)
    public String getClassstructureid() {
        return this.classstructureid;
    }

    public void setClassstructureid(String classstructureid) {
        this.classstructureid = classstructureid;
    }

    @Column(name = "HIERARCHYLEVELS")
    public Integer getHierarchylevels() {
        return this.hierarchylevels;
    }

    public void setHierarchylevels(Integer hierarchylevels) {
        this.hierarchylevels = hierarchylevels;
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

}
