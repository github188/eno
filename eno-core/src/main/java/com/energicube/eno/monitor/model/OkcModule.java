package com.energicube.eno.monitor.model;


import javax.persistence.*;

/**
 * 系统模块定义
 */
@Entity
@Table(name = "OKCMODULE")
public class OkcModule implements java.io.Serializable {

    private static final long serialVersionUID = -6017115650688137948L;
    private long okcmoduleid;
    /**
     * 模块代码
     */
    private String modulecode;
    /**
     * 模块描述
     */
    private String description;

    public OkcModule() {
    }

    public OkcModule(long okcmoduleid, String modulecode) {
        this.okcmoduleid = okcmoduleid;
        this.modulecode = modulecode;
    }

    public OkcModule(long okcmoduleid, String modulecode, String description) {
        this.okcmoduleid = okcmoduleid;
        this.modulecode = modulecode;
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OKCMODULEID", unique = true, nullable = false)
    public long getOkcmoduleid() {
        return this.okcmoduleid;
    }

    public void setOkcmoduleid(long okcmoduleid) {
        this.okcmoduleid = okcmoduleid;
    }

    @Column(name = "MODULECODE", nullable = false, length = 10)
    public String getModulecode() {
        return this.modulecode;
    }

    public void setModulecode(String modulecode) {
        this.modulecode = modulecode;
    }

    @Column(name = "DESCRIPTION", length = 100)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
