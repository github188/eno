package com.energicube.eno.asset.model;


import javax.persistence.*;
import java.util.Date;

/**
 * 资产移动记录
 */
@Entity
@Table(name = "ASSETTRANS")
public class AssetTrans implements java.io.Serializable {

    private static final long serialVersionUID = 7504027654586279708L;
    private long assettransid;
    private Integer assetid;
    private String assetnum;
    private String conditioncode;
    private Date datemoved;
    private String enterby;
    private String externalrefid;
    private String fromconditioncode;
    private String fromdept;
    private String fromloc;
    private String fromparent;
    private String memo;
    private String orgid;
    private String ownersysid;
    private String siteid;
    private String sourcesysid;
    private String todept;
    private String toloc;
    private String toorgid;
    private String toparent;
    private String tositeid;
    private Date transdate;
    private String transtype;
    private String wonum;

    public AssetTrans() {
    }

    public AssetTrans(long assettransid, String siteid) {
        this.assettransid = assettransid;
        this.siteid = siteid;
    }

    public AssetTrans(long assettransid, Integer assetid, String assetnum,
                      String conditioncode, Date datemoved, String enterby,
                      String externalrefid, String fromconditioncode, String fromdept,
                      String fromloc, String fromparent, String memo, String orgid,
                      String ownersysid, String siteid, String sourcesysid,
                      String todept, String toloc, String toorgid, String toparent,
                      String tositeid, Date transdate, String transtype, String wonum) {
        this.assettransid = assettransid;
        this.assetid = assetid;
        this.assetnum = assetnum;
        this.conditioncode = conditioncode;
        this.datemoved = datemoved;
        this.enterby = enterby;
        this.externalrefid = externalrefid;
        this.fromconditioncode = fromconditioncode;
        this.fromdept = fromdept;
        this.fromloc = fromloc;
        this.fromparent = fromparent;
        this.memo = memo;
        this.orgid = orgid;
        this.ownersysid = ownersysid;
        this.siteid = siteid;
        this.sourcesysid = sourcesysid;
        this.todept = todept;
        this.toloc = toloc;
        this.toorgid = toorgid;
        this.toparent = toparent;
        this.tositeid = tositeid;
        this.transdate = transdate;
        this.transtype = transtype;
        this.wonum = wonum;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ASSETTRANSID", unique = true, nullable = false)
    public long getAssettransid() {
        return this.assettransid;
    }

    public void setAssettransid(long assettransid) {
        this.assettransid = assettransid;
    }

    @Column(name = "ASSETID")
    public Integer getAssetid() {
        return this.assetid;
    }

    public void setAssetid(Integer assetid) {
        this.assetid = assetid;
    }

    @Column(name = "ASSETNUM", length = 12)
    public String getAssetnum() {
        return this.assetnum;
    }

    public void setAssetnum(String assetnum) {
        this.assetnum = assetnum;
    }

    @Column(name = "CONDITIONCODE", length = 30)
    public String getConditioncode() {
        return this.conditioncode;
    }

    public void setConditioncode(String conditioncode) {
        this.conditioncode = conditioncode;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATEMOVED", length = 23)
    public Date getDatemoved() {
        return this.datemoved;
    }

    public void setDatemoved(Date datemoved) {
        this.datemoved = datemoved;
    }

    @Column(name = "ENTERBY", length = 30)
    public String getEnterby() {
        return this.enterby;
    }

    public void setEnterby(String enterby) {
        this.enterby = enterby;
    }

    @Column(name = "EXTERNALREFID", length = 10)
    public String getExternalrefid() {
        return this.externalrefid;
    }

    public void setExternalrefid(String externalrefid) {
        this.externalrefid = externalrefid;
    }

    @Column(name = "FROMCONDITIONCODE", length = 30)
    public String getFromconditioncode() {
        return this.fromconditioncode;
    }

    public void setFromconditioncode(String fromconditioncode) {
        this.fromconditioncode = fromconditioncode;
    }

    @Column(name = "FROMDEPT", length = 30)
    public String getFromdept() {
        return this.fromdept;
    }

    public void setFromdept(String fromdept) {
        this.fromdept = fromdept;
    }

    @Column(name = "FROMLOC", length = 30)
    public String getFromloc() {
        return this.fromloc;
    }

    public void setFromloc(String fromloc) {
        this.fromloc = fromloc;
    }

    @Column(name = "FROMPARENT", length = 12)
    public String getFromparent() {
        return this.fromparent;
    }

    public void setFromparent(String fromparent) {
        this.fromparent = fromparent;
    }

    @Column(name = "MEMO", length = 254)
    public String getMemo() {
        return this.memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    @Column(name = "ORGID", length = 8)
    public String getOrgid() {
        return this.orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    @Column(name = "OWNERSYSID", length = 10)
    public String getOwnersysid() {
        return this.ownersysid;
    }

    public void setOwnersysid(String ownersysid) {
        this.ownersysid = ownersysid;
    }

    @Column(name = "SITEID", nullable = false, length = 8)
    public String getSiteid() {
        return this.siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

    @Column(name = "SOURCESYSID", length = 10)
    public String getSourcesysid() {
        return this.sourcesysid;
    }

    public void setSourcesysid(String sourcesysid) {
        this.sourcesysid = sourcesysid;
    }

    @Column(name = "TODEPT", length = 30)
    public String getTodept() {
        return this.todept;
    }

    public void setTodept(String todept) {
        this.todept = todept;
    }

    @Column(name = "TOLOC", length = 30)
    public String getToloc() {
        return this.toloc;
    }

    public void setToloc(String toloc) {
        this.toloc = toloc;
    }

    @Column(name = "TOORGID", length = 8)
    public String getToorgid() {
        return this.toorgid;
    }

    public void setToorgid(String toorgid) {
        this.toorgid = toorgid;
    }

    @Column(name = "TOPARENT", length = 12)
    public String getToparent() {
        return this.toparent;
    }

    public void setToparent(String toparent) {
        this.toparent = toparent;
    }

    @Column(name = "TOSITEID", length = 8)
    public String getTositeid() {
        return this.tositeid;
    }

    public void setTositeid(String tositeid) {
        this.tositeid = tositeid;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "TRANSDATE", length = 23)
    public Date getTransdate() {
        return this.transdate;
    }

    public void setTransdate(Date transdate) {
        this.transdate = transdate;
    }

    @Column(name = "TRANSTYPE", length = 16)
    public String getTranstype() {
        return this.transtype;
    }

    public void setTranstype(String transtype) {
        this.transtype = transtype;
    }

    @Column(name = "WONUM", length = 10)
    public String getWonum() {
        return this.wonum;
    }

    public void setWonum(String wonum) {
        this.wonum = wonum;
    }

}
