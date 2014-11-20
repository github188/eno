package com.energicube.eno.monitor.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;

/**
 * 联动规划关联预案
 */
@Entity
@Table(name = "UCLC_LINKCELL")
public class UclcLinkcell implements java.io.Serializable {

    private static final long serialVersionUID = -6490057644158430597L;
    private Integer lcid;
    private Long linkageid;
    private String cellname;
    private Integer offsett;
    private String shiftname;
    private Date updatet;

    public UclcLinkcell() {
    }

    public UclcLinkcell(Integer lcid, Long linkageid, String cellname) {
        this.lcid = lcid;
        this.linkageid = linkageid;
        this.cellname = cellname;
    }

    public UclcLinkcell(Integer lcid, Long linkageid, String cellname,
                        Integer offsett, String shiftname, Date updatet) {
        this.lcid = lcid;
        this.linkageid = linkageid;
        this.cellname = cellname;
        this.offsett = offsett;
        this.shiftname = shiftname;
        this.updatet = updatet;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LCID", unique = true, nullable = false)
    public Integer getLcid() {
        return this.lcid;
    }

    public void setLcid(Integer lcid) {
        this.lcid = lcid;
    }

    @Column(name = "LINKAGEID", nullable = false)
    public Long getLinkageid() {
        return this.linkageid;
    }

    public void setLinkageid(Long linkageid) {
        this.linkageid = linkageid;
    }

    @NotEmpty
    @Column(name = "CELLNAME", nullable = false, length = 100)
    public String getCellname() {
        return this.cellname;
    }

    public void setCellname(String cellname) {
        this.cellname = cellname;
    }

    @Column(name = "OFFSETT")
    public Integer getOffsett() {
        return this.offsett;
    }

    public void setOffsett(Integer offsett) {
        this.offsett = offsett;
    }

    @Column(name = "SHIFTNAME", length = 100)
    public String getShiftname() {
        return this.shiftname;
    }

    public void setShiftname(String shiftname) {
        this.shiftname = shiftname;
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
