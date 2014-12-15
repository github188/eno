package com.energicube.eno.asset.model;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;
import org.joda.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 测点使用的技术规范参数
 */
@Entity
@Table(name = "MEASURESPEC")
public class MeasureSpec implements java.io.Serializable {

    private static final long serialVersionUID = 8324113027145170682L;
    private long measurespecid;

    /**
     * 字符型的值，此时MEASUREUNITID字段不可修改且值为空。
     */
    private String alnvalue;
    /**
     * 测点使用的技术规范属性
     */
    private String assetattrid;
    /**
     * 关联的资产标识
     */
    private Long assetid;
    /**
     * 关联的资产号
     */
    private String assetnum;
    /**
     * 计算方法（能源模块使用）
     */
    private String calcmethod;
    /**
     * 最后变更技术规范记录的用户
     */
    private String changeby;
    /**
     * 记录修改的日期
     */
    private LocalDateTime changedate;
    /**
     * 类别结构标识
     */
    private String classstructureid;
    /**
     * 显示顺序
     */
    private int displaysequence = 0;
    /**
     * 测量数据更新频率单位
     */
    private String frequnit;
    /**
     * 是强制填写字段吗？
     */
    private Boolean mandatory;
    /**
     * 测量数据更新频率
     */
    private Integer measurefreq;
    /**
     * 属性值的计量单位，通常适用于数字属性。
     */
    private String measureunitid;
    /**
     * 与该“测点”的技术规范属性关联的计量器名称（能源模块使用）
     */
    private String metername;
    /**
     * 以输入的单位表示的数字值
     */
    private BigDecimal numvalue;
    /**
     * 组织机构标识
     */
    private String orgid;
    /**
     * 此参考值对应到测点的技术规范参数，作为参数相关测量值的参考值（能源模块使用）
     */
    private BigDecimal refvalue;
    /**
     * 属性组部分
     */
    private String section;
    /**
     * 地点标识
     */
    private String siteid;

    public MeasureSpec() {
    }

    public MeasureSpec(long measurespecid, String assetattrid, String assetnum,
                       String calcmethod, String changeby, LocalDateTime changedate,
                       String classstructureid, int displaysequence, String metername) {
        this.measurespecid = measurespecid;
        this.assetattrid = assetattrid;
        this.assetnum = assetnum;
        this.calcmethod = calcmethod;
        this.changeby = changeby;
        this.changedate = changedate;
        this.classstructureid = classstructureid;
        this.displaysequence = displaysequence;
        this.metername = metername;
    }

    public MeasureSpec(long measurespecid, String alnvalue, String assetattrid,
                       Long assetid, String assetnum, String calcmethod, String changeby,
                       LocalDateTime changedate, String classstructureid, int displaysequence,
                       String frequnit, Boolean mandatory, Integer measurefreq,
                       String measureunitid, String metername, BigDecimal numvalue,
                       String orgid, BigDecimal refvalue, String section, String siteid) {
        this.measurespecid = measurespecid;
        this.alnvalue = alnvalue;
        this.assetattrid = assetattrid;
        this.assetid = assetid;
        this.assetnum = assetnum;
        this.calcmethod = calcmethod;
        this.changeby = changeby;
        this.changedate = changedate;
        this.classstructureid = classstructureid;
        this.displaysequence = displaysequence;
        this.frequnit = frequnit;
        this.mandatory = mandatory;
        this.measurefreq = measurefreq;
        this.measureunitid = measureunitid;
        this.metername = metername;
        this.numvalue = numvalue;
        this.orgid = orgid;
        this.refvalue = refvalue;
        this.section = section;
        this.siteid = siteid;
    }

    @Id
    //@TableGenerator(name = "GEN_MEASURESPECID", table = "ID_GEN", pkColumnName = "GEN_NAME", valueColumnName = "GEN_VAL",pkColumnValue = "com.energicube.eno.asset.model.MeasureSpec#measurespecid", initialValue = 0, allocationSize = 1)
    //@GeneratedValue(strategy=GenerationType.TABLE,generator = "GEN_MEASURESPECID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEASURESPECID", unique = true, nullable = false)
    public long getMeasurespecid() {
        return this.measurespecid;
    }

    public void setMeasurespecid(long measurespecid) {
        this.measurespecid = measurespecid;
    }

    @Column(name = "ALNVALUE", length = 254)
    public String getAlnvalue() {
        return this.alnvalue;
    }

    public void setAlnvalue(String alnvalue) {
        this.alnvalue = alnvalue;
    }

    @NotEmpty
    @Column(name = "ASSETATTRID", nullable = false, length = 20)
    public String getAssetattrid() {
        return this.assetattrid;
    }

    public void setAssetattrid(String assetattrid) {
        this.assetattrid = assetattrid;
    }

    @Column(name = "ASSETID")
    public Long getAssetid() {
        return this.assetid;
    }

    public void setAssetid(Long assetid) {
        this.assetid = assetid;
    }

    @Column(name = "ASSETNUM", nullable = false, length = 20)
    public String getAssetnum() {
        return this.assetnum;
    }

    public void setAssetnum(String assetnum) {
        this.assetnum = assetnum;
    }

    @Column(name = "CALCMETHOD", nullable = false, length = 25)
    public String getCalcmethod() {
        return this.calcmethod;
    }

    public void setCalcmethod(String calcmethod) {
        this.calcmethod = calcmethod;
    }

    @Column(name = "CHANGEBY", length = 30)
    public String getChangeby() {
        return this.changeby;
    }

    public void setChangeby(String changeby) {
        this.changeby = changeby;
    }

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "CHANGEDATE", length = 23)
    public LocalDateTime getChangedate() {
        return this.changedate;
    }

    public void setChangedate(LocalDateTime changedate) {
        this.changedate = changedate;
    }

    @Column(name = "CLASSSTRUCTUREID", nullable = false, length = 20)
    public String getClassstructureid() {
        return this.classstructureid;
    }

    public void setClassstructureid(String classstructureid) {
        this.classstructureid = classstructureid;
    }

    @Column(name = "DISPLAYSEQUENCE", nullable = false)
    public int getDisplaysequence() {
        return this.displaysequence;
    }

    public void setDisplaysequence(int displaysequence) {
        this.displaysequence = displaysequence;
    }

    @Column(name = "FREQUNIT", length = 8)
    public String getFrequnit() {
        return this.frequnit;
    }

    public void setFrequnit(String frequnit) {
        this.frequnit = frequnit;
    }

    @Column(name = "MANDATORY")
    public Boolean getMandatory() {
        return this.mandatory;
    }

    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    @Column(name = "MEASUREFREQ")
    public Integer getMeasurefreq() {
        return this.measurefreq;
    }

    public void setMeasurefreq(Integer measurefreq) {
        this.measurefreq = measurefreq;
    }

    @Column(name = "MEASUREUNITID", length = 16)
    public String getMeasureunitid() {
        return this.measureunitid;
    }

    public void setMeasureunitid(String measureunitid) {
        this.measureunitid = measureunitid;
    }

    @NotEmpty
    @Column(name = "METERNAME", nullable = false, length = 30)
    public String getMetername() {
        return this.metername;
    }

    public void setMetername(String metername) {
        this.metername = metername;
    }

    @Column(name = "NUMVALUE", precision = 18, scale = 4)
    public BigDecimal getNumvalue() {
        return this.numvalue;
    }

    public void setNumvalue(BigDecimal numvalue) {
        this.numvalue = numvalue;
    }

    @Column(name = "ORGID", length = 8)
    public String getOrgid() {
        return this.orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
    }

    @Column(name = "REFVALUE", precision = 11, scale = 4)
    public BigDecimal getRefvalue() {
        return this.refvalue;
    }

    public void setRefvalue(BigDecimal refvalue) {
        this.refvalue = refvalue;
    }

    @Column(name = "SECTION", length = 10)
    public String getSection() {
        return this.section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    @Column(name = "SITEID", length = 8)
    public String getSiteid() {
        return this.siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

    @Override
    public String toString() {
        return "MeasureSpec [measurespecid=" + measurespecid + ", alnvalue="
                + alnvalue + ", assetattrid=" + assetattrid + ", assetid="
                + assetid + ", assetnum=" + assetnum + ", calcmethod="
                + calcmethod + ", changeby=" + changeby + ", changedate="
                + changedate + ", classstructureid=" + classstructureid
                + ", displaysequence=" + displaysequence + ", frequnit="
                + frequnit + ", mandatory=" + mandatory + ", measurefreq="
                + measurefreq + ", measureunitid=" + measureunitid
                + ", metername=" + metername + ", numvalue=" + numvalue
                + ", orgid=" + orgid + ", refvalue=" + refvalue + ", section="
                + section + ", siteid=" + siteid + "]";
    }

}
