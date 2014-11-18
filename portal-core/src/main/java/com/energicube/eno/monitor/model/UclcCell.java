package com.energicube.eno.monitor.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;

/**
 * 联动预案
 */
@Entity
@Table(name = "UCLC_CELL")
public class UclcCell implements java.io.Serializable {

    private static final long serialVersionUID = 1674234276494861818L;
    private Integer cellid;
    private String cellname;
    private String cellcomment;
    private Date updatet;

    public UclcCell() {
    }

    public UclcCell(Integer cellid, String cellname) {
        this.cellid = cellid;
        this.cellname = cellname;
    }

    public UclcCell(Integer cellid, String cellname, String cellcomment,
                    Date updatet) {
        this.cellid = cellid;
        this.cellname = cellname;
        this.cellcomment = cellcomment;
        this.updatet = updatet;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CELLID", unique = true, nullable = false)
    public Integer getCellid() {
        return this.cellid;
    }

    public void setCellid(Integer cellid) {
        this.cellid = cellid;
    }

    @NotEmpty
    @Column(name = "CELLNAME", nullable = false, length = 200)
    public String getCellname() {
        return this.cellname;
    }

    public void setCellname(String cellname) {
        this.cellname = cellname;
    }

    @Column(name = "CELLCOMMENT", length = 200)
    public String getCellcomment() {
        return this.cellcomment;
    }

    public void setCellcomment(String cellcomment) {
        this.cellcomment = cellcomment;
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
