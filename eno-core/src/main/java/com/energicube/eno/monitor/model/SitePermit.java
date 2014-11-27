package com.energicube.eno.monitor.model;


import javax.persistence.*;

/**
 * 权限组的地点授权，据此限制用户在业务对象地点字段上可访问的数据级别
 */
@Entity
@Table(name = "SITEPERMIT", schema = "zclfsys")
public class SitePermit implements java.io.Serializable {

    private static final long serialVersionUID = -3705808632145499547L;
    private long sitepermitid;
    private String siteid;
    private String orgid;
    private String groupname;

    public SitePermit() {
    }

    public SitePermit(long sitepermitid) {
        this.sitepermitid = sitepermitid;
    }

    public SitePermit(long sitepermitid, String siteid, String orgid, String groupname) {
        this.sitepermitid = sitepermitid;
        this.siteid = siteid;
        this.orgid = orgid;
        this.groupname = groupname;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SITEPERMITID", unique = true, nullable = false)
    public long getSiteauth() {
        return this.sitepermitid;
    }

    public void setSiteauth(long sitepermitid) {
        this.sitepermitid = sitepermitid;
    }

    @Column(name = "SITEID", length = 8)
    public String getSiteid() {
        return this.siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

    @Column(name = "ORGID", length = 8)
    public String getOrgid() {
        return this.orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    @Column(name = "GROUPNAME", length = 30)
    public String getGroupname() {
        return this.groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

}
