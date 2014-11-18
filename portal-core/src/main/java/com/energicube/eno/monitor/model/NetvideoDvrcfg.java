package com.energicube.eno.monitor.model;


import javax.persistence.*;

/**
 * DVR配置
 */
@Entity
@Table(name = "NETVIDEO_DVRCFG")
public class NetvideoDvrcfg implements java.io.Serializable {

    private static final long serialVersionUID = 4522030135657776095L;
    private int dvrcfgid;
    private String dvrname;
    private String dvrtype;
    private String ipaddress;
    private Integer port;
    private String username;
    private String password;
    private String resourceid;
    private int dvrsequence;

    public NetvideoDvrcfg() {
    }

    public NetvideoDvrcfg(int dvrcfgid, String dvrname, String dvrtype,
                          String ipaddress, int dvrsequence) {
        this.dvrcfgid = dvrcfgid;
        this.dvrname = dvrname;
        this.dvrtype = dvrtype;
        this.ipaddress = ipaddress;
        this.dvrsequence = dvrsequence;
    }

    public NetvideoDvrcfg(int dvrcfgid, String dvrname, String dvrtype,
                          String ipaddress, Integer port, String username, String password,
                          String resourceid, int dvrsequence) {
        this.dvrcfgid = dvrcfgid;
        this.dvrname = dvrname;
        this.dvrtype = dvrtype;
        this.ipaddress = ipaddress;
        this.port = port;
        this.username = username;
        this.password = password;
        this.resourceid = resourceid;
        this.dvrsequence = dvrsequence;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DVRCFGID", unique = true, nullable = false)
    public int getDvrcfgid() {
        return this.dvrcfgid;
    }

    public void setDvrcfgid(int dvrcfgid) {
        this.dvrcfgid = dvrcfgid;
    }

    @Column(name = "DVRNAME", nullable = false, length = 30)
    public String getDvrname() {
        return this.dvrname;
    }

    public void setDvrname(String dvrname) {
        this.dvrname = dvrname;
    }

    @Column(name = "DVRTYPE", nullable = false, length = 50)
    public String getDvrtype() {
        return this.dvrtype;
    }

    public void setDvrtype(String dvrtype) {
        this.dvrtype = dvrtype;
    }

    @Column(name = "IPADDRESS", nullable = false, length = 30)
    public String getIpaddress() {
        return this.ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    @Column(name = "PORT")
    public Integer getPort() {
        return this.port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Column(name = "USERNAME", length = 30)
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "PASSWORD", length = 30)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "RESOURCEID")
    public String getResourceid() {
        return this.resourceid;
    }

    public void setResourceid(String resourceid) {
        this.resourceid = resourceid;
    }

    @Column(name = "DISPLAYSEQUENCE", nullable = false)
    public int getDvrsequence() {
        return dvrsequence;
    }

    public void setDvrsequence(int dvrsequence) {
        this.dvrsequence = dvrsequence;
    }
}
