package com.energicube.eno.asset.model;


import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 计量单位换算
 */
@Entity
@Table(name = "CONVERSION", schema = "zclfsys")
public class Conversion implements java.io.Serializable {

    private static final long serialVersionUID = -4706962658376602894L;
    private long conversionid;
    private String changeby;
    private Date changedate;
    private BigDecimal conversion;
    private String frommeasureunit;
    private String itemnum;
    private String itemsetid;
    private String tomeasureunit;

    public Conversion() {
    }

    public Conversion(long conversionid) {
        this.conversionid = conversionid;
    }

    public Conversion(long conversionid, String changeby, Date changedate,
                      BigDecimal conversion, String frommeasureunit, String itemnum,
                      String itemsetid, String tomeasureunit) {
        this.conversionid = conversionid;
        this.changeby = changeby;
        this.changedate = changedate;
        this.conversion = conversion;
        this.frommeasureunit = frommeasureunit;
        this.itemnum = itemnum;
        this.itemsetid = itemsetid;
        this.tomeasureunit = tomeasureunit;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONVERSIONID", unique = true, nullable = false)
    public long getConversionid() {
        return this.conversionid;
    }

    public void setConversionid(long conversionid) {
        this.conversionid = conversionid;
    }

    @Column(name = "CHANGEBY", length = 30)
    public String getChangeby() {
        return this.changeby;
    }

    public void setChangeby(String changeby) {
        this.changeby = changeby;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CHANGEDATE", length = 23)
    public Date getChangedate() {
        return this.changedate;
    }

    public void setChangedate(Date changedate) {
        this.changedate = changedate;
    }

    @Column(name = "CONVERSION", precision = 11, scale = 4)
    public BigDecimal getConversion() {
        return this.conversion;
    }

    public void setConversion(BigDecimal conversion) {
        this.conversion = conversion;
    }

    @Column(name = "FROMMEASUREUNIT", length = 16)
    public String getFrommeasureunit() {
        return this.frommeasureunit;
    }

    public void setFrommeasureunit(String frommeasureunit) {
        this.frommeasureunit = frommeasureunit;
    }

    @Column(name = "ITEMNUM", length = 30)
    public String getItemnum() {
        return this.itemnum;
    }

    public void setItemnum(String itemnum) {
        this.itemnum = itemnum;
    }

    @Column(name = "ITEMSETID", length = 8)
    public String getItemsetid() {
        return this.itemsetid;
    }

    public void setItemsetid(String itemsetid) {
        this.itemsetid = itemsetid;
    }

    @Column(name = "TOMEASUREUNIT", length = 16)
    public String getTomeasureunit() {
        return this.tomeasureunit;
    }

    public void setTomeasureunit(String tomeasureunit) {
        this.tomeasureunit = tomeasureunit;
    }

}
