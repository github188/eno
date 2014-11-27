package com.energicube.eno.monitor.model;

import javax.persistence.*;


@Entity
@Table(name = "funcblock", schema = "zclfsys")
public class Funcblock implements java.io.Serializable {

    private static final long serialVersionUID = -6461543583706041579L;
    private int funcid;
    private int parentid;
    private String funcname;
    private String funccomment;

    public Funcblock() {
    }

    public Funcblock(int funcid, int parentid, String funcname,
                     String funccomment) {
        this.funcid = funcid;
        this.parentid = parentid;
        this.funcname = funcname;
        this.funccomment = funccomment;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "funcid", unique = true, nullable = false)
    public int getFuncid() {
        return this.funcid;
    }

    public void setFuncid(int funcid) {
        this.funcid = funcid;
    }

    @Column(name = "parentid", nullable = false)
    public int getParentid() {
        return this.parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    @Column(name = "funcname", nullable = false)
    public String getFuncname() {
        return this.funcname;
    }

    public void setFuncname(String funcname) {
        this.funcname = funcname;
    }

    @Column(name = "funccomment", nullable = false)
    public String getFunccomment() {
        return this.funccomment;
    }

    public void setFunccomment(String funccomment) {
        this.funccomment = funccomment;
    }

}
