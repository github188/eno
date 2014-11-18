package com.energicube.eno.asset.model;


import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 计量器读数记录
 */
@Entity
@Table(name = "METERREADING", schema = "dbo")
public class MeterReading implements java.io.Serializable {

    private static final long serialVersionUID = -3608995847275474970L;
    private long meterReadingId;
    private Long assetid;
    private String assetnum;
    private String basemeasureunitid;
    private BigDecimal delta;
    private Boolean didrollover;

    public MeterReading() {
    }

    public MeterReading(long meterReadingId) {
        this.meterReadingId = meterReadingId;
    }

    public MeterReading(long meterReadingId, Long assetid, String assetnum,
                        String basemeasureunitid, BigDecimal delta, Boolean didrollover) {
        this.meterReadingId = meterReadingId;
        this.assetid = assetid;
        this.assetnum = assetnum;
        this.basemeasureunitid = basemeasureunitid;
        this.delta = delta;
        this.didrollover = didrollover;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MeterReadingId", unique = true, nullable = false)
    public long getMeterReadingId() {
        return this.meterReadingId;
    }

    public void setMeterReadingId(long meterReadingId) {
        this.meterReadingId = meterReadingId;
    }

    @Column(name = "ASSETID")
    public Long getAssetid() {
        return this.assetid;
    }

    public void setAssetid(Long assetid) {
        this.assetid = assetid;
    }

    @Column(name = "ASSETNUM", length = 12)
    public String getAssetnum() {
        return this.assetnum;
    }

    public void setAssetnum(String assetnum) {
        this.assetnum = assetnum;
    }

    @Column(name = "BASEMEASUREUNITID", length = 16)
    public String getBasemeasureunitid() {
        return this.basemeasureunitid;
    }

    public void setBasemeasureunitid(String basemeasureunitid) {
        this.basemeasureunitid = basemeasureunitid;
    }

    @Column(name = "DELTA", precision = 11, scale = 4)
    public BigDecimal getDelta() {
        return this.delta;
    }

    public void setDelta(BigDecimal delta) {
        this.delta = delta;
    }

    @Column(name = "DIDROLLOVER")
    public Boolean getDidrollover() {
        return this.didrollover;
    }

    public void setDidrollover(Boolean didrollover) {
        this.didrollover = didrollover;
    }

}
