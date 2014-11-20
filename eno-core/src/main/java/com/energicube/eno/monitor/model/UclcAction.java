package com.energicube.eno.monitor.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 预案动作
 */
@Entity
@Table(name = "UCLC_ACTION")
public class UclcAction implements java.io.Serializable {

    private static final long serialVersionUID = 7287655042871498456L;
    private Integer actionid;
    private Integer cellid;
    private String actioncomment;
    private String authorname;
    private Integer actiontype;
    private String actionc;
    private Integer offsett;
    private Integer enabled;
    private Date updatet;

    public UclcAction() {
    }

    public UclcAction(Integer actionid, Integer cellid, String actioncomment) {
        this.actionid = actionid;
        this.cellid = cellid;
        this.actioncomment = actioncomment;
    }

    public UclcAction(Integer actionid, Integer cellid, String actioncomment,
                      String authorname, Integer actiontype, String actionc,
                      Integer offsett, Integer enabled, Date updatet) {
        this.actionid = actionid;
        this.cellid = cellid;
        this.actioncomment = actioncomment;
        this.authorname = authorname;
        this.actiontype = actiontype;
        this.actionc = actionc;
        this.offsett = offsett;
        this.enabled = enabled;
        this.updatet = updatet;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACTIONID", unique = true, nullable = false)
    public Integer getActionid() {
        return this.actionid;
    }

    public void setActionid(Integer actionid) {
        this.actionid = actionid;
    }

    @Column(name = "CELLID", nullable = false)
    public Integer getCellid() {
        return this.cellid;
    }

    public void setCellid(Integer cellid) {
        this.cellid = cellid;
    }

    @Column(name = "ACTIONCOMMENT", nullable = false, length = 200)
    public String getActioncomment() {
        return this.actioncomment;
    }

    public void setActioncomment(String actioncomment) {
        this.actioncomment = actioncomment;
    }

    @Column(name = "AUTHORNAME", length = 200)
    public String getAuthorname() {
        return this.authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    @NotNull
    @Column(name = "ACTIONTYPE")
    public Integer getActiontype() {
        return this.actiontype;
    }

    public void setActiontype(Integer actiontype) {
        this.actiontype = actiontype;
    }

    @Column(name = "ACTIONC", length = 2000)
    public String getActionc() {
        return this.actionc;
    }

    public void setActionc(String actionc) {
        this.actionc = actionc;
    }

    @Column(name = "OFFSETT")
    public Integer getOffsett() {
        return this.offsett;
    }

    public void setOffsett(Integer offsett) {
        this.offsett = offsett;
    }

    @Column(name = "ENABLED")
    public Integer getEnabled() {
        return this.enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
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
