package com.energicube.eno.asset.model;


import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * PM计量器表，对应计量器必须是连续型计量器，并且在PM上添加基于计量器的频率之前必须附加到PM使用的资产或位置
 */
@Entity
@Table(name = "PMMETER", uniqueConstraints = @UniqueConstraint(columnNames = {
        "METERNAME", "PMNUM", "SITEID"}))
public class PMMeter implements java.io.Serializable {

    private static final long serialVersionUID = 8558465981406790836L;
    /**
     * 唯一标识
     */
    private long pmmeterid;
    /**
     * 预警期（单位）
     */
    private BigDecimal alertlead;
    /**
     * 资产编号
     */
    private String assetnum;
    /**
     * 设定的计量器频率
     */
    private BigDecimal frequency = new BigDecimal(0.00);
    /**
     * 上次生成工单时的计量器读数
     */
    private BigDecimal lastpmwogenread = new BigDecimal(0.00);
    /**
     * 上次生成工单时的计量器读数日期
     */
    private LocalDateTime lastpmwogenreaddt;
    /**
     * 位置标识
     */
    private String location;
    /**
     * 累计计量器读数
     */
    private BigDecimal ltdlastpmworead;
    /**
     * 累计下一次计量器读数
     */
    private BigDecimal ltdreadatnextwo;
    /**
     * 计量器名称
     */
    private String metername;
    /**
     * 组织标识
     */
    private String orgid;
    /**
     * 预防性维护标识
     */
    private String pmnum;

    /**
     * 下一次生成工单时的读数
     */
    private BigDecimal readingatnextwo;
    /**
     * 地点标识
     */
    private String siteid;
    /**
     * 容差
     */
    private BigDecimal tolerance;

    /**
     * 更新计量器
     */
    private Boolean updmeter;

    public PMMeter() {
    }

    public PMMeter(long pmmeterid) {
        this.pmmeterid = pmmeterid;
    }

    public PMMeter(long pmmeterid, BigDecimal alertlead, String assetnum,
                   BigDecimal frequency, BigDecimal lastpmwogenread,
                   LocalDateTime lastpmwogenreaddt, String location,
                   BigDecimal ltdlastpmworead, BigDecimal ltdreadatnextwo,
                   String metername, String orgid, String pmnum,
                   BigDecimal readingatnextwo, String siteid, BigDecimal tolerance) {
        this.pmmeterid = pmmeterid;
        this.alertlead = alertlead;
        this.assetnum = assetnum;
        this.frequency = frequency;
        this.lastpmwogenread = lastpmwogenread;
        this.lastpmwogenreaddt = lastpmwogenreaddt;
        this.location = location;
        this.ltdlastpmworead = ltdlastpmworead;
        this.ltdreadatnextwo = ltdreadatnextwo;
        this.metername = metername;
        this.orgid = orgid;
        this.pmnum = pmnum;
        this.readingatnextwo = readingatnextwo;
        this.siteid = siteid;
        this.tolerance = tolerance;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PMMETERID", unique = true, nullable = false)
    public long getPmmeterid() {
        return this.pmmeterid;
    }

    public void setPmmeterid(long pmmeterid) {
        this.pmmeterid = pmmeterid;
    }

    @Column(name = "ALERTLEAD", precision = 11, scale = 4)
    public BigDecimal getAlertlead() {
        return this.alertlead;
    }

    public void setAlertlead(BigDecimal alertlead) {
        this.alertlead = alertlead;
    }

    @Column(name = "ASSETNUM", length = 12)
    public String getAssetnum() {
        return this.assetnum;
    }

    public void setAssetnum(String assetnum) {
        this.assetnum = assetnum;
    }

    @Column(name = "FREQUENCY", precision = 11, scale = 4)
    public BigDecimal getFrequency() {
        return this.frequency;
    }

    public void setFrequency(BigDecimal frequency) {
        this.frequency = frequency;
    }

    @Column(name = "LASTPMWOGENREAD", precision = 11, scale = 4)
    public BigDecimal getLastpmwogenread() {
        return this.lastpmwogenread;
    }

    public void setLastpmwogenread(BigDecimal lastpmwogenread) {
        this.lastpmwogenread = lastpmwogenread;
    }

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "LASTPMWOGENREADDT", length = 23)
    public LocalDateTime getLastpmwogenreaddt() {
        return this.lastpmwogenreaddt;
    }

    public void setLastpmwogenreaddt(LocalDateTime lastpmwogenreaddt) {
        this.lastpmwogenreaddt = lastpmwogenreaddt;
    }

    @Column(name = "LOCATION", length = 30)
    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Column(name = "LTDLASTPMWOREAD", precision = 11, scale = 4)
    public BigDecimal getLtdlastpmworead() {
        return this.ltdlastpmworead;
    }

    public void setLtdlastpmworead(BigDecimal ltdlastpmworead) {
        this.ltdlastpmworead = ltdlastpmworead;
    }

    @Column(name = "LTDREADATNEXTWO", precision = 11, scale = 4)
    public BigDecimal getLtdreadatnextwo() {
        return this.ltdreadatnextwo;
    }

    public void setLtdreadatnextwo(BigDecimal ltdreadatnextwo) {
        this.ltdreadatnextwo = ltdreadatnextwo;
    }

    @NotEmpty
    @Column(name = "METERNAME", length = 30)
    public String getMetername() {
        return this.metername;
    }

    public void setMetername(String metername) {
        this.metername = metername;
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

    @Column(name = "READINGATNEXTWO", precision = 11, scale = 4)
    public BigDecimal getReadingatnextwo() {
        return this.readingatnextwo;
    }

    public void setReadingatnextwo(BigDecimal readingatnextwo) {
        this.readingatnextwo = readingatnextwo;
    }

    @Column(name = "SITEID", length = 8)
    public String getSiteid() {
        return this.siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

    @Column(name = "TOLERANCE", precision = 11, scale = 4)
    public BigDecimal getTolerance() {
        return this.tolerance;
    }

    public void setTolerance(BigDecimal tolerance) {
        this.tolerance = tolerance;
    }

    @Column(name = "UPDMETER")
    public Boolean getUpdmeter() {
        return updmeter;
    }

    public void setUpdmeter(Boolean updmeter) {
        this.updmeter = updmeter;
    }


}
