package com.energicube.eno.monitor.model;


import javax.persistence.*;
import java.util.List;

/**
 *
 */
@Entity
@Table(name = "APPAUTH", schema = "dbo")
public class AppAuth implements java.io.Serializable {

    private static final long serialVersionUID = -768423732547346486L;
    private long appauthid;
    private String appcode;
    private String groupname;
    private String signame;
    private List<OkcMenu> okcMenuList;

    @Transient
    public List<OkcMenu> getOkcMenuList() {
        return okcMenuList;
    }

    public void setOkcMenuList(List<OkcMenu> okcMenuList) {
        this.okcMenuList = okcMenuList;
    }

    public AppAuth() {
    }

    public AppAuth(long appauthid) {
        this.appauthid = appauthid;
    }

    public AppAuth(long appauthid, String appcode, String groupname,
                   String signame) {
        this.appauthid = appauthid;
        this.appcode = appcode;
        this.groupname = groupname;
        this.signame = signame;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "APPAUTHID", unique = true, nullable = false)
    public long getAppauthid() {
        return this.appauthid;
    }

    public void setAppauthid(long appauthid) {
        this.appauthid = appauthid;
    }

    @Column(name = "APPCODE", length = 10)
    public String getAppcode() {
        return this.appcode;
    }

    public void setAppcode(String appcode) {
        this.appcode = appcode;
    }

    @Column(name = "GROUPNAME", length = 30)
    public String getGroupname() {
        return this.groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    @Column(name = "SIGNAME", length = 25)
    public String getSigname() {
        return this.signame;
    }

    public void setSigname(String signame) {
        this.signame = signame;
    }

}
