package com.energicube.eno.monitor.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;

/**
 * 联动规划
 */
@Entity
@Table(name = "UCLC_LINKAGE")
public class UclcLinkage implements java.io.Serializable {

    private static final long serialVersionUID = -123869910021451001L;
    private Long linkageid;
    private String linkagename;
    private String linkagecomment;
    private String authorname;
    private String conditionname;
    private Integer paused;
    private Integer checkactive;
    private Date updatet;

    public UclcLinkage() {
    }

    public UclcLinkage(Long linkageid, String linkagename, Integer paused) {
        this.linkageid = linkageid;
        this.linkagename = linkagename;
        this.paused = paused;
    }

    public UclcLinkage(Long linkageid, String linkagename,
                       String linkagecomment, String authorname, String conditionname,
                       Integer paused, Integer checkactive, Date updatet) {
        this.linkageid = linkageid;
        this.linkagename = linkagename;
        this.linkagecomment = linkagecomment;
        this.authorname = authorname;
        this.conditionname = conditionname;
        this.paused = paused;
        this.checkactive = checkactive;
        this.updatet = updatet;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LINKAGEID", unique = true, nullable = false)
    public Long getLinkageid() {
        return this.linkageid;
    }

    public void setLinkageid(Long linkageid) {
        this.linkageid = linkageid;
    }

    @NotEmpty
    @Column(name = "LINKAGENAME", nullable = false, length = 100)
    public String getLinkagename() {
        return this.linkagename;
    }

    public void setLinkagename(String linkagename) {
        this.linkagename = linkagename;
    }

    @Column(name = "LINKAGECOMMENT")
    public String getLinkagecomment() {
        return this.linkagecomment;
    }

    public void setLinkagecomment(String linkagecomment) {
        this.linkagecomment = linkagecomment;
    }

    @Column(name = "AUTHORNAME", length = 12)
    public String getAuthorname() {
        return this.authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    @NotEmpty
    @Column(name = "CONDITIONNAME", length = 100)
    public String getConditionname() {
        return this.conditionname;
    }

    public void setConditionname(String conditionname) {
        this.conditionname = conditionname;
    }

    @Column(name = "PAUSED", nullable = false)
    public Integer getPaused() {
        return this.paused;
    }

    public void setPaused(Integer paused) {
        this.paused = paused;
    }

    @Column(name = "CHECKACTIVE")
    public Integer getCheckactive() {
        return this.checkactive;
    }

    public void setCheckactive(Integer checkactive) {
        this.checkactive = checkactive;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UPDATET", length = 23)
    public Date getUpdatet() {
        return this.updatet;
    }

    public void setUpdatet(Date updatet) {
        this.updatet = updatet;
    }

}
