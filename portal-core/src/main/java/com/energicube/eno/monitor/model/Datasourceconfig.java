package com.energicube.eno.monitor.model;


import javax.persistence.*;

/**
 * 数据源配置
 * <p>数据源配置，保存各类数据库的连接配置</p>
 */
@Entity
@Table(name = "DATA_SOURCE_CONFIG")
public class Datasourceconfig implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int datasourceconfiguid;
    private String datasourceconfigid;
    private String sourcename;
    private String description;
    private String dbtype;
    private String dbname;
    private String ipaddress;
    private int port;
    private String userid;
    private String password;

    public Datasourceconfig() {
    }

    public Datasourceconfig(int datasourceconfiguid, String datasourceconfigid,
                            String sourcename, String dbtype, String ipaddress, int port) {
        this.datasourceconfiguid = datasourceconfiguid;
        this.datasourceconfigid = datasourceconfigid;
        this.sourcename = sourcename;
        this.dbtype = dbtype;
        this.ipaddress = ipaddress;
        this.port = port;
    }

    public Datasourceconfig(int datasourceconfiguid, String datasourceconfigid,
                            String sourcename, String description, String dbtype,
                            String dbname, String ipaddress, int port, String userid,
                            String password) {
        this.datasourceconfiguid = datasourceconfiguid;
        this.datasourceconfigid = datasourceconfigid;
        this.sourcename = sourcename;
        this.description = description;
        this.dbtype = dbtype;
        this.dbname = dbname;
        this.ipaddress = ipaddress;
        this.port = port;
        this.userid = userid;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DATASOURCECONFIGUID", unique = true, nullable = false)
    public int getDatasourceconfiguid() {
        return this.datasourceconfiguid;
    }

    public void setDatasourceconfiguid(int datasourceconfiguid) {
        this.datasourceconfiguid = datasourceconfiguid;
    }

    @Column(name = "DATASOURCECONFIGID", nullable = false, length = 12)
    public String getDatasourceconfigid() {
        return this.datasourceconfigid;
    }

    public void setDatasourceconfigid(String datasourceconfigid) {
        this.datasourceconfigid = datasourceconfigid;
    }

    @Column(name = "SOURCENAME", nullable = false, length = 30)
    public String getSourcename() {
        return this.sourcename;
    }

    public void setSourcename(String sourcename) {
        this.sourcename = sourcename;
    }

    @Column(name = "DESCRIPTION", length = 100)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "DBTYPE", nullable = false, length = 30)
    public String getDbtype() {
        return this.dbtype;
    }

    public void setDbtype(String dbtype) {
        this.dbtype = dbtype;
    }

    @Column(name = "DBNAME", length = 50)
    public String getDbname() {
        return this.dbname;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    @Column(name = "IPADDRESS", nullable = false, length = 30)
    public String getIpaddress() {
        return this.ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    @Column(name = "PORT")
    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @Column(name = "USERID", length = 30)
    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Column(name = "PASSWORD", length = 128)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
