package com.energicube.eno.asset.model;


import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 测试状态监控
 *
 * @author CHENPING
 */
@Entity
@Table(name = "MEASUREPOINT", schema = "zclfsys")
public class MeasurePoint implements java.io.Serializable {

    private static final long serialVersionUID = 6574125162469200224L;
    private long measurepointid;
    private String assetnum;
    private String description;
    private Boolean hasld;
    private String langcode;
    private String llpmnum;
    private Integer llpriority;
    private String location;
    private BigDecimal loweraction;
    private BigDecimal lowerwarning;
    private String metername;
    private String orgid;
    private String pointnum;
    private String siteid;
    private String ulpmnum;
    private String ulpriority;
    private BigDecimal upperaction;
    private BigDecimal upperwarning;
    private Boolean isspec;
    private String classstructureid;

    public MeasurePoint() {
    }

    public MeasurePoint(long measurepointid) {
        this.measurepointid = measurepointid;
    }

    public MeasurePoint(long measurepointid, String assetnum,
                        String description, Boolean hasld, String langcode, String llpmnum,
                        Integer llpriority, String location, BigDecimal loweraction,
                        BigDecimal lowerwarning, String metername, String orgid,
                        String pointnum, String siteid, String ulpmnum, String ulpriority,
                        BigDecimal upperaction, BigDecimal upperwarning, Boolean isspec,
                        String classstructureid) {
        this.measurepointid = measurepointid;
        this.assetnum = assetnum;
        this.description = description;
        this.hasld = hasld;
        this.langcode = langcode;
        this.llpmnum = llpmnum;
        this.llpriority = llpriority;
        this.location = location;
        this.loweraction = loweraction;
        this.lowerwarning = lowerwarning;
        this.metername = metername;
        this.orgid = orgid;
        this.pointnum = pointnum;
        this.siteid = siteid;
        this.ulpmnum = ulpmnum;
        this.ulpriority = ulpriority;
        this.upperaction = upperaction;
        this.upperwarning = upperwarning;
        this.isspec = isspec;
        this.classstructureid = classstructureid;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEASUREPOINTID", unique = true, nullable = false)
    public long getMeasurepointid() {
        return this.measurepointid;
    }

    public void setMeasurepointid(long measurepointid) {
        this.measurepointid = measurepointid;
    }

    @Column(name = "ASSETNUM", length = 12)
    public String getAssetnum() {
        return this.assetnum;
    }

    public void setAssetnum(String assetnum) {
        this.assetnum = assetnum;
    }

    @Column(name = "DESCRIPTION", length = 100)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "HASLD")
    public Boolean getHasld() {
        return this.hasld;
    }

    public void setHasld(Boolean hasld) {
        this.hasld = hasld;
    }

    @Column(name = "LANGCODE", length = 4)
    public String getLangcode() {
        return this.langcode;
    }

    public void setLangcode(String langcode) {
        this.langcode = langcode;
    }

    @Column(name = "LLPMNUM", length = 8)
    public String getLlpmnum() {
        return this.llpmnum;
    }

    public void setLlpmnum(String llpmnum) {
        this.llpmnum = llpmnum;
    }

    @Column(name = "LLPRIORITY")
    public Integer getLlpriority() {
        return this.llpriority;
    }

    public void setLlpriority(Integer llpriority) {
        this.llpriority = llpriority;
    }

    @Column(name = "LOCATION", length = 30)
    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Column(name = "LOWERACTION", precision = 11, scale = 4)
    public BigDecimal getLoweraction() {
        return this.loweraction;
    }

    public void setLoweraction(BigDecimal loweraction) {
        this.loweraction = loweraction;
    }

    @Column(name = "LOWERWARNING", precision = 11, scale = 4)
    public BigDecimal getLowerwarning() {
        return this.lowerwarning;
    }

    public void setLowerwarning(BigDecimal lowerwarning) {
        this.lowerwarning = lowerwarning;
    }

    @NotEmpty
    @Column(name = "POINTNUM", length = 20)
    public String getPointnum() {
        return this.pointnum;
    }

    public void setPointnum(String pointnum) {
        this.pointnum = pointnum;
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


    @Column(name = "SITEID", length = 8)
    public String getSiteid() {
        return this.siteid;
    }

    public void setSiteid(String siteid) {
        this.siteid = siteid;
    }

    @Column(name = "ULPMNUM", length = 8)
    public String getUlpmnum() {
        return this.ulpmnum;
    }

    public void setUlpmnum(String ulpmnum) {
        this.ulpmnum = ulpmnum;
    }

    @Column(name = "ULPRIORITY", length = 12)
    public String getUlpriority() {
        return this.ulpriority;
    }

    public void setUlpriority(String ulpriority) {
        this.ulpriority = ulpriority;
    }

    @Column(name = "UPPERACTION", precision = 11, scale = 4)
    public BigDecimal getUpperaction() {
        return this.upperaction;
    }

    public void setUpperaction(BigDecimal upperaction) {
        this.upperaction = upperaction;
    }

    @Column(name = "UPPERWARNING", precision = 11, scale = 4)
    public BigDecimal getUpperwarning() {
        return this.upperwarning;
    }

    public void setUpperwarning(BigDecimal upperwarning) {
        this.upperwarning = upperwarning;
    }

    @NotNull
    @Column(name = "ISSPEC")
    public Boolean getIsspec() {
        return this.isspec;
    }

    public void setIsspec(Boolean isspec) {
        this.isspec = isspec;
    }

    @Column(name = "CLASSSTRUCTUREID", length = 20)
    public String getClassstructureid() {
        return this.classstructureid;
    }

    public void setClassstructureid(String classstructureid) {
        this.classstructureid = classstructureid;
    }

    @Override
    public String toString() {
        return "Measurepoint [measurepointid=" + measurepointid + ", assetnum="
                + assetnum + ", description=" + description + ", hasld="
                + hasld + ", langcode=" + langcode + ", llpmnum=" + llpmnum
                + ", llpriority=" + llpriority + ", location=" + location
                + ", loweraction=" + loweraction + ", lowerwarning="
                + lowerwarning + ", metername=" + metername + ", orgid="
                + orgid + ", pointnum=" + pointnum + ", siteid=" + siteid
                + ", ulpmnum=" + ulpmnum + ", ulpriority=" + ulpriority
                + ", upperaction=" + upperaction + ", upperwarning="
                + upperwarning + ", isspec=" + isspec + ", classstructureid="
                + classstructureid + "]";
    }

    @Transient
    public boolean isNew() {
        return this.measurepointid == 0;
    }

}
