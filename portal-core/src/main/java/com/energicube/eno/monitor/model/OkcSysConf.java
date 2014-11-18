package com.energicube.eno.monitor.model;


import javax.persistence.*;
import java.util.Date;

/**
 *
 */
@Entity
@Table(name = "OKCSYSCONF", schema = "dbo")
public class OkcSysConf implements java.io.Serializable {

    private static final long serialVersionUID = -5773378946832120042L;
    private long okcsysconfid;
    private String propname;
    private String confvalue;
    private String encryptedvalue;
    private String serverhost;
    private String servername;
    private String changeby;
    private Date changedate;

    public OkcSysConf() {
    }

    public OkcSysConf(long okcsysconfid, String propname) {
        this.okcsysconfid = okcsysconfid;
        this.propname = propname;
    }

    public OkcSysConf(long okcsysconfid, String propname, String confvalue,
                      String encryptedvalue, String serverhost, String servername,
                      String changeby, Date changedate) {
        this.okcsysconfid = okcsysconfid;
        this.propname = propname;
        this.confvalue = confvalue;
        this.encryptedvalue = encryptedvalue;
        this.serverhost = serverhost;
        this.servername = servername;
        this.changeby = changeby;
        this.changedate = changedate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OKCSYSCONFID", unique = true, nullable = false)
    public long getOkcsysconfid() {
        return this.okcsysconfid;
    }

    public void setOkcsysconfid(long okcsysconfid) {
        this.okcsysconfid = okcsysconfid;
    }

    @Column(name = "PROPNAME", nullable = false, length = 50)
    public String getPropname() {
        return this.propname;
    }

    public void setPropname(String propname) {
        this.propname = propname;
    }

    @Column(name = "CONFVALUE", length = 512)
    public String getConfvalue() {
        return this.confvalue;
    }

    public void setConfvalue(String confvalue) {
        this.confvalue = confvalue;
    }

    @Column(name = "ENCRYPTEDVALUE", length = 2000)
    public String getEncryptedvalue() {
        return this.encryptedvalue;
    }

    public void setEncryptedvalue(String encryptedvalue) {
        this.encryptedvalue = encryptedvalue;
    }

    @Column(name = "SERVERHOST", length = 100)
    public String getServerhost() {
        return this.serverhost;
    }

    public void setServerhost(String serverhost) {
        this.serverhost = serverhost;
    }

    @Column(name = "SERVERNAME", length = 100)
    public String getServername() {
        return this.servername;
    }

    public void setServername(String servername) {
        this.servername = servername;
    }

    @Column(name = "CHANGEBY", length = 50)
    public String getChangeby() {
        return this.changeby;
    }

    public void setChangeby(String changeby) {
        this.changeby = changeby;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CHANGEDATE", length = 23)
    public Date getChangedate() {
        return this.changedate;
    }

    public void setChangedate(Date changedate) {
        this.changedate = changedate;
    }

}
