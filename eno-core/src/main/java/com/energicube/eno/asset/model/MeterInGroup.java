package com.energicube.eno.asset.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 计量器组包含的计量器
 */
@Entity
@Table(name = "METERINGROUP")
public class MeterInGroup implements java.io.Serializable {

    private static final long serialVersionUID = -1024831436964128634L;
    private long meteringroupid;
    private String avgcalcmethod;

    private String groupname;

    private String metername;
    private BigDecimal rollover;
    private Integer sequence;
    private Integer slidingwindowsize;
    private BigDecimal staticaverage;

    public MeterInGroup() {
    }

    public MeterInGroup(long meteringroupid) {
        this.meteringroupid = meteringroupid;
    }

    public MeterInGroup(long meteringroupid, String avgcalcmethod,
                        String groupname, String metername, BigDecimal rollover,
                        Integer sequence, Integer slidingwindowsize,
                        BigDecimal staticaverage) {
        this.meteringroupid = meteringroupid;
        this.avgcalcmethod = avgcalcmethod;
        this.groupname = groupname;
        this.metername = metername;
        this.rollover = rollover;
        this.sequence = sequence;
        this.slidingwindowsize = slidingwindowsize;
        this.staticaverage = staticaverage;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "METERINGROUPID", unique = true, nullable = false)
    public long getMeteringroupid() {
        return this.meteringroupid;
    }

    public void setMeteringroupid(long meteringroupid) {
        this.meteringroupid = meteringroupid;
    }

    @Column(name = "AVGCALCMETHOD", length = 25)
    public String getAvgcalcmethod() {
        return this.avgcalcmethod;
    }

    public void setAvgcalcmethod(String avgcalcmethod) {
        this.avgcalcmethod = avgcalcmethod;
    }

    @NotEmpty
    @Column(name = "GROUPNAME", length = 10)
    public String getGroupname() {
        return this.groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    @NotEmpty
    @Column(name = "METERNAME", length = 30)
    public String getMetername() {
        return this.metername;
    }

    public void setMetername(String metername) {
        this.metername = metername;
    }

    @Column(name = "ROLLOVER", precision = 11, scale = 4)
    public BigDecimal getRollover() {
        return this.rollover;
    }

    public void setRollover(BigDecimal rollover) {
        this.rollover = rollover;
    }

    @Column(name = "SEQUENCE")
    public Integer getSequence() {
        return this.sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    @Column(name = "SLIDINGWINDOWSIZE")
    public Integer getSlidingwindowsize() {
        return this.slidingwindowsize;
    }

    public void setSlidingwindowsize(Integer slidingwindowsize) {
        this.slidingwindowsize = slidingwindowsize;
    }

    @Column(name = "STATICAVERAGE", precision = 11, scale = 4)
    public BigDecimal getStaticaverage() {
        return this.staticaverage;
    }

    public void setStaticaverage(BigDecimal staticaverage) {
        this.staticaverage = staticaverage;
    }

}
