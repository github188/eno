package com.energicube.eno.monitor.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "EXPRESSION")
public class Expression implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 6927483228222037173L;

    private long expindex;
    private String expcode;
    private String expname;
    private String expcontent;

    public Expression() {
        super();
    }

    public Expression(long expindex) {
        super();
        this.expindex = expindex;
    }

    public Expression(long expindex, String expcode, String expname, String expcontent) {
        super();
        this.expindex = expindex;
        this.expcode = expcode;
        this.expname = expname;
        this.expcontent = expcontent;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//自增
    @Column(name = "EXPINDEX", unique = true, nullable = false)//惟一，不可为空
    public long getExpindex() {
        return expindex;
    }

    public void setExpindex(long expindex) {
        this.expindex = expindex;
    }

    @Column(name = "EXPCODE", unique = true)
    public String getExpcode() {
        return expcode;
    }

    public void setExpcode(String expcode) {
        this.expcode = expcode;
    }

    @Column(name = "EXPNAME", unique = true)
    public String getExpname() {
        return expname;
    }

    public void setExpname(String expname) {
        this.expname = expname;
    }

    @Column(name = "EXPCONTENT", length = 2048)
    public String getExpcontent() {
        return expcontent;
    }

    public void setExpcontent(String expcontent) {
        this.expcontent = expcontent;
    }

}
