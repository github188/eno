package com.energicube.eno.asset.model;


import javax.persistence.*;

/**
 * 记录每个位置的根位置
 */
@Entity
@Table(name = "LOCANCESTOR", schema = "zclfsys")
public class LocAncestor implements java.io.Serializable {

    private static final long serialVersionUID = 8773765658281600263L;
    private long locancestorid;
    private String ancestor;
    private String location;
    private String orgid;
    private String siteid;
    private String systemid;

    public LocAncestor() {
    }

    public LocAncestor(long locancestorid) {
        this.locancestorid = locancestorid;
    }

    public LocAncestor(long locancestorid, String ancestor, String location,
                       String orgid, String siteid, String systemid) {
        this.locancestorid = locancestorid;
        this.ancestor = ancestor;
        this.location = location;
        this.orgid = orgid;
        this.siteid = siteid;
        this.systemid = systemid;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOCANCESTORID", unique = true, nullable = false)
    public long getLocancestorid() {
        return this.locancestorid;
    }

    public void setLocancestorid(long locancestorid) {
        this.locancestorid = locancestorid;
    }

    @Column(name = "ANCESTOR", length = 12)
    public String getAncestor() {
        return this.ancestor;
    }

    public void setAncestor(String ancestor) {
        this.ancestor = ancestor;
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
