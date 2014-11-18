package com.energicube.eno.monitor.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 作者:刘广路
 * 创建于 2014/8/28
 * 版本:1.0
 * 项目:ibms-portal
 */
@Entity
@Table(name = "DATA_INDEXCONFIG")
public class DataIndexconfig implements java.io.Serializable {

    private String indexId;
    private String name;
    private String id;
    private String description;
    private Integer frequency;
    private String categoryId;
    private String regionId;
    private String value;
    private String tagname;
    private Integer tagid;
    private Integer iscreate;
    private Integer calGrade;
    private Integer ispd;
    private Integer ismax;
    private Integer ismin;
    private Integer issum;
    private Integer isavg;
    private Integer ispercent;
    private Integer ischange;
    private String buildId;


    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(name = "INDEXID", unique = true, nullable = false, length = 36)
    public String getIndexId() {
        return indexId;
    }

    public void setIndexId(String indexId) {
        this.indexId = indexId;
    }

    @Column(name = "NAME", length = 80)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "ID", length = 80)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "DESCRIPTION", length = 200)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Column(name = "CATEGORYID", length = 36)
    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Column(name = "REGIONID", length = 36)
    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    @Column(name = "VALUE", length = 200)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Column(name = "TAGNAME")
    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }

    @Column(name = "BUILDID", length = 36)
    public String getBuildId() {
        return buildId;
    }

    public void setBuildId(String buildId) {
        this.buildId = buildId;
    }

    @Column(name = "FREQUENCY")
    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    @Column(name = "TAGID")
    public Integer getTagid() {
        return tagid;
    }

    public void setTagid(Integer tagid) {
        this.tagid = tagid;
    }

    @Column(name = "ISCREATE")
    public Integer getIscreate() {
        return iscreate;
    }

    public void setIscreate(Integer iscreate) {
        this.iscreate = iscreate;
    }

    @Column(name = "CALGRADE")
    public Integer getCalGrade() {
        return calGrade;
    }

    public void setCalGrade(Integer calGrade) {
        this.calGrade = calGrade;
    }

    @Column(name = "ISPD")
    public Integer getIspd() {
        return ispd;
    }

    public void setIspd(Integer ispd) {
        this.ispd = ispd;
    }

    @Column(name = "ISMAX")
    public Integer getIsmax() {
        return ismax;
    }

    public void setIsmax(Integer ismax) {
        this.ismax = ismax;
    }

    @Column(name = "ISMIN")
    public Integer getIsmin() {
        return ismin;
    }

    public void setIsmin(Integer ismin) {
        this.ismin = ismin;
    }

    @Column(name = "ISSUM")
    public Integer getIssum() {
        return issum;
    }

    public void setIssum(Integer issum) {
        this.issum = issum;
    }

    @Column(name = "ISAVG")
    public Integer getIsavg() {
        return isavg;
    }

    public void setIsavg(Integer isavg) {
        this.isavg = isavg;
    }

    @Column(name = "ISPERCENT")
    public Integer getIspercent() {
        return ispercent;
    }

    public void setIspercent(Integer ispercent) {
        this.ispercent = ispercent;
    }

    @Column(name = "ISCHANGE")
    public Integer getIschange() {
        return ischange;
    }

    public void setIschange(Integer ischange) {
        this.ischange = ischange;
    }
}
