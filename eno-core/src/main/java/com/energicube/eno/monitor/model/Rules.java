package com.energicube.eno.monitor.model;


import javax.persistence.*;


@Entity
@Table(name = "rules", schema = "zclfsys")
public class Rules implements java.io.Serializable {

    private static final long serialVersionUID = 4690533365864849928L;
    private int ruleid;
    private int groupid;
    private int funcid;

    public Rules() {
    }

    public Rules(int ruleid, int groupid, int funcid) {
        this.ruleid = ruleid;
        this.groupid = groupid;
        this.funcid = funcid;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ruleid", unique = true, nullable = false)
    public int getRuleid() {
        return this.ruleid;
    }

    public void setRuleid(int ruleid) {
        this.ruleid = ruleid;
    }

    @Column(name = "groupid", nullable = false)
    public int getGroupid() {
        return this.groupid;
    }

    public void setGroupid(int groupid) {
        this.groupid = groupid;
    }

    @Column(name = "funcid", nullable = false)
    public int getFuncid() {
        return this.funcid;
    }

    public void setFuncid(int funcid) {
        this.funcid = funcid;
    }

}
