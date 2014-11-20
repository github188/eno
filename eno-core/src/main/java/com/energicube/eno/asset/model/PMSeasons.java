package com.energicube.eno.asset.model;


import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * 在定义的季节性日期内PM有效
 */
@Entity
@Table(name = "PMSEASONS", schema = "dbo")
public class PMSeasons implements java.io.Serializable {

    private static final long serialVersionUID = -4271392360281676184L;
    private long pmseasonsid;
    /**
     * 标识PM的季节结束日，位于结束月中的哪天
     */
    private Integer endday;
    /**
     * 确定PM的季节结束月份，可选值从一月到十二月
     */
    private String endmonth;
    /**
     * 组织标识
     */
    private String orgid;
    /**
     * PM编号
     */
    private String pmnum;
    /**
     * 地点
     */
    private String siteid;
    /**
     * 标识PM的季节开始日，位于开始月中的哪天
     */
    private Integer startday;
    /**
     * 标识PM的季节开始月份，可选值从一月到十二月
     */
    private String startmonth;

    public PMSeasons() {
    }

    public PMSeasons(long pmseasonsid) {
        this.pmseasonsid = pmseasonsid;
    }

    public PMSeasons(long pmseasonsid, Integer endday, String endmonth,
                     String orgid, String pmnum, String siteid, Integer startday,
                     String startmonth) {
        this.pmseasonsid = pmseasonsid;
        this.endday = endday;
        this.endmonth = endmonth;
        this.orgid = orgid;
        this.pmnum = pmnum;
        this.siteid = siteid;
        this.startday = startday;
        this.startmonth = startmonth;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PMSEASONSID", unique = true, nullable = false)
    public long getPmseasonsid() {
        return this.pmseasonsid;
    }

    public void setPmseasonsid(long pmseasonsid) {
        this.pmseasonsid = pmseasonsid;
    }

    @NotNull
    @Column(name = "ENDDAY")
    public Integer getEndday() {
        return this.endday;
    }

    public void setEndday(Integer endday) {
        this.endday = endday;
    }

    @NotNull
    @Column(name = "ENDMONTH", length = 16)
    public String getEndmonth() {
        return this.endmonth;
    }

    public void setEndmonth(String endmonth) {
        this.endmonth = endmonth;
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

    @NotNull
    @Column(name = "STARTDAY")
    public Integer getStartday() {
        return this.startday;
    }

    public void setStartday(Integer startday) {
        this.startday = startday;
    }

    @NotNull
    @Column(name = "STARTMONTH", length = 16)
    public String getStartmonth() {
        return this.startmonth;
    }

    public void setStartmonth(String startmonth) {
        this.startmonth = startmonth;
    }

}
