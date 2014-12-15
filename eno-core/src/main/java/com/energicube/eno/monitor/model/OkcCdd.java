package com.energicube.eno.monitor.model;


import javax.persistence.*;

/**
 *
 */
@Entity
@Table(name = "OKCCDD")
public class OkcCdd implements java.io.Serializable {

    private static final long serialVersionUID = -7833412572827854559L;
    private long okccddid;
    private String dictid;
    private String dicttype;
    private Integer internal;
    private Integer length;
    private String datatype;
    private Integer scale;

    public OkcCdd() {
    }

    public OkcCdd(long okccddid) {
        this.okccddid = okccddid;
    }

    public OkcCdd(long okccddid, String dictid, String dicttype,
                  Integer internal, Integer length, String datatype, Integer scale) {
        this.okccddid = okccddid;
        this.dictid = dictid;
        this.dicttype = dicttype;
        this.internal = internal;
        this.length = length;
        this.datatype = datatype;
        this.scale = scale;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OKCCDDID", unique = true, nullable = false)
    public long getOkccddid() {
        return this.okccddid;
    }

    public void setOkccddid(long okccddid) {
        this.okccddid = okccddid;
    }

    @Column(name = "DICTID", length = 18)
    public String getDictid() {
        return this.dictid;
    }

    public void setDictid(String dictid) {
        this.dictid = dictid;
    }

    @Column(name = "DICTTYPE", length = 20)
    public String getDicttype() {
        return this.dicttype;
    }

    public void setDicttype(String dicttype) {
        this.dicttype = dicttype;
    }

    @Column(name = "INTERNAL")
    public Integer getInternal() {
        return this.internal;
    }

    public void setInternal(Integer internal) {
        this.internal = internal;
    }

    @Column(name = "LENGTH")
    public Integer getLength() {
        return this.length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    @Column(name = "DATATYPE", length = 8)
    public String getDatatype() {
        return this.datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    @Column(name = "SCALE")
    public Integer getScale() {
        return this.scale;
    }

    public void setScale(Integer scale) {
        this.scale = scale;
    }

}
