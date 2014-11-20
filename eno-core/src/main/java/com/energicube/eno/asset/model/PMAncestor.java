package com.energicube.eno.asset.model;


import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * 每条PM记录的顶级PM关联，PM层次结构可生成工单层次结构。
 */
@Entity
@Table(name = "PMANCESTOR", schema = "dbo")
public class PMAncestor implements java.io.Serializable {

    private static final long serialVersionUID = 337886183691612227L;

    /**
     * 唯一标识
     */
    private long pmancestorid;
    /**
     * 当前PM记录的顶级PM，如果当前PM本身就是顶级PM，则此字段值就是当前PM
     */
    private String ancestor;
    /**
     * 当前PM所处层次结构级别数，顶级为0，一级子级PM为1。
     */
    private Integer hierarchylevels = 0;
    /**
     * 组织机构
     */
    private String orgid;
    /**
     * PM编号
     */
    private String pmnum;
    /**
     * 地点标识
     */
    private String siteid;

    public PMAncestor() {
    }

    public PMAncestor(long pmancestorid) {
        this.pmancestorid = pmancestorid;
    }

    public PMAncestor(long pmancestorid, String ancestor,
                      Integer hierarchylevels, String orgid, String pmnum, String siteid) {
        this.pmancestorid = pmancestorid;
        this.ancestor = ancestor;
        this.hierarchylevels = hierarchylevels;
        this.orgid = orgid;
        this.pmnum = pmnum;
        this.siteid = siteid;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PMANCESTORID", unique = true, nullable = false)
    public long getPmancestorid() {
        return this.pmancestorid;
    }

    public void setPmancestorid(long pmancestorid) {
        this.pmancestorid = pmancestorid;
    }

    @NotEmpty
    @Column(name = "ANCESTOR", length = 8)
    public String getAncestor() {
        return this.ancestor;
    }

    public void setAncestor(String ancestor) {
        this.ancestor = ancestor;
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

    @NotEmpty
    @Column(name = "PMNUM", length = 8)
    public String getPmnum() {
        return this.pmnum;
    }

    public void setPmnum(String pmnum) {
        this.pmnum = pmnum;
    }

    @Column(name = "SITEID", length = 8)
    public String getSiteid() {
        return this.siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

}
