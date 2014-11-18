package com.energicube.eno.monitor.model;

import javax.persistence.*;
import java.util.Date;

/**
 * 班次类型
 */
@Entity
@Table(name = "SHIFTTYPES", schema = "dbo")
public class Shifttypes implements java.io.Serializable {

    private static final long serialVersionUID = -6112172564043377506L;
    private int shifttypesid;
    private String shifttype;
    private String season;
    private Date starttime;
    private Date endtime;
    private String description;

    public Shifttypes() {
    }

    public Shifttypes(int shifttypesid, String shifttype, String season,
                      Date starttime, Date endtime) {
        this.shifttypesid = shifttypesid;
        this.shifttype = shifttype;
        this.season = season;
        this.starttime = starttime;
        this.endtime = endtime;
    }

    public Shifttypes(int shifttypesid, String shifttype, String season,
                      Date starttime, Date endtime, String description) {
        this.shifttypesid = shifttypesid;
        this.shifttype = shifttype;
        this.season = season;
        this.starttime = starttime;
        this.endtime = endtime;
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SHIFTTYPESID", unique = true, nullable = false)
    public int getShifttypesid() {
        return this.shifttypesid;
    }

    public void setShifttypesid(int shifttypesid) {
        this.shifttypesid = shifttypesid;
    }

    @Column(name = "SHIFTTYPE", nullable = false, length = 16)
    public String getShifttype() {
        return this.shifttype;
    }

    public void setShifttype(String shifttype) {
        this.shifttype = shifttype;
    }

    @Column(name = "SEASON", nullable = false, length = 10)
    public String getSeason() {
        return this.season;
    }

    public void setSeason(String season) {
        this.season = season;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "STARTTIME", length = 23)
    public Date getStarttime() {
        return this.starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ENDTIME", length = 23)
    public Date getEndtime() {
        return this.endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    @Column(name = "DESCRIPTION", length = 254)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
