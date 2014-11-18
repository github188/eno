package com.energicube.eno.asset.model;


import javax.persistence.*;

/**
 * 特殊型计量器的读数
 */
@Entity
@Table(name = "CHARPOINTACTION", schema = "dbo")
public class CharPointAction implements java.io.Serializable {

    private static final long serialVersionUID = 3556601089690959057L;
    private long charpointactionid;
    private String orgid;
    private String pmnum;
    private String pointnum;
    private Integer priority;
    private String siteid;
    private String value;

    public CharPointAction() {
    }

    public CharPointAction(long charpointactionid) {
        this.charpointactionid = charpointactionid;
    }

    public CharPointAction(long charpointactionid, String orgid, String pmnum,
                           String pointnum, Integer priority, String siteid, String value) {
        this.charpointactionid = charpointactionid;
        this.orgid = orgid;
        this.pmnum = pmnum;
        this.pointnum = pointnum;
        this.priority = priority;
        this.siteid = siteid;
        this.value = value;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CHARPOINTACTIONID", unique = true, nullable = false)
    public long getCharpointactionid() {
        return this.charpointactionid;
    }

    public void setCharpointactionid(long charpointactionid) {
        this.charpointactionid = charpointactionid;
    }

    @Column(name = "ORGID", length = 8)
    public String getOrgid() {
        return this.orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    @Column(name = "PMNUM", length = 8)
    public String getPmnum() {
        return this.pmnum;
    }

    public void setPmnum(String pmnum) {
        this.pmnum = pmnum;
    }

    @Column(name = "POINTNUM", length = 8)
    public String getPointnum() {
        return this.pointnum;
    }

    public void setPointnum(String pointnum) {
        this.pointnum = pointnum;
    }

    @Column(name = "PRIORITY")
    public Integer getPriority() {
        return this.priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Column(name = "SITEID", length = 8)
    public String getSiteid() {
        return this.siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

    @Column(name = "VALUE", length = 254)
    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
