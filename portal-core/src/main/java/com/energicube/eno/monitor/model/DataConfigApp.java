package com.energicube.eno.monitor.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 */
@Entity
@Table(name = "DATA_CONFIG_APP")
public class DataConfigApp implements java.io.Serializable {

    private static final long serialVersionUID = -6743751496690547289L;
    private int dataconfigappid;
    private String configid;
    private int configtype = 1;  //配置类型  1导入 2导出  3 SQL语句
    private String app;

    public DataConfigApp() {
    }

    public DataConfigApp(int dataconfigappid, String configid, int configtype,
                         String app) {
        this.dataconfigappid = dataconfigappid;
        this.configid = configid;
        this.configtype = configtype;
        this.app = app;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DATACONFIGAPPID", unique = true, nullable = false)
    public int getDataconfigappid() {
        return this.dataconfigappid;
    }

    public void setDataconfigappid(int dataconfigappid) {
        this.dataconfigappid = dataconfigappid;
    }

    @NotNull
    @Column(name = "CONFIGID", nullable = false, length = 12)
    public String getConfigid() {
        return this.configid;
    }

    public void setConfigid(String configid) {
        this.configid = configid;
    }

    @Column(name = "CONFIGTYPE", nullable = false)
    public int getConfigtype() {
        return this.configtype;
    }

    public void setConfigtype(int configtype) {
        this.configtype = configtype;
    }

    @NotNull
    @Column(name = "APP", nullable = false, length = 20)
    public String getApp() {
        return this.app;
    }

    public void setApp(String app) {
        this.app = app;
    }

}
