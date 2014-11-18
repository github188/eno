package com.energicube.eno.monitor.model;


import javax.persistence.*;
import java.util.Date;

/**
 *
 */
@Entity
@Table(name = "OKCSYSPROP", schema = "dbo")
public class OkcSysProp implements java.io.Serializable {

    private static final long serialVersionUID = -8793334854801618399L;
    private long okcsyspropid;
    private String propname;
    private String description;
    private String dictid;
    private Boolean encrypted;
    private Boolean globalonly;
    private Boolean instanceonly;
    private Boolean liverefresh;
    private Boolean masked;
    private String defaultvalue;
    private String datatype;
    private Boolean nullsallowed;
    private Boolean onlinechanges;
    private String securelevel;
    private Boolean userdefined;
    private String changeby;
    private Date changedate;

    public OkcSysProp() {
    }

    public OkcSysProp(long okcsyspropid, String propname) {
        this.okcsyspropid = okcsyspropid;
        this.propname = propname;
    }

    public OkcSysProp(long okcsyspropid, String propname, String description,
                      String dictid, Boolean encrypted, Boolean globalonly,
                      Boolean instanceonly, Boolean liverefresh, Boolean masked,
                      String defaultvalue, String datatype, Boolean nullsallowed,
                      Boolean onlinechanges, String securelevel, Boolean userdefined,
                      String changeby, Date changedate) {
        this.okcsyspropid = okcsyspropid;
        this.propname = propname;
        this.description = description;
        this.dictid = dictid;
        this.encrypted = encrypted;
        this.globalonly = globalonly;
        this.instanceonly = instanceonly;
        this.liverefresh = liverefresh;
        this.masked = masked;
        this.defaultvalue = defaultvalue;
        this.datatype = datatype;
        this.nullsallowed = nullsallowed;
        this.onlinechanges = onlinechanges;
        this.securelevel = securelevel;
        this.userdefined = userdefined;
        this.changeby = changeby;
        this.changedate = changedate;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OKCSYSPROPID", unique = true, nullable = false)
    public long getOkcsyspropid() {
        return this.okcsyspropid;
    }

    public void setOkcsyspropid(long okcsyspropid) {
        this.okcsyspropid = okcsyspropid;
    }

    @Column(name = "PROPNAME", nullable = false, length = 50)
    public String getPropname() {
        return this.propname;
    }

    public void setPropname(String propname) {
        this.propname = propname;
    }

    @Column(name = "DESCRIPTION", length = 100)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "DICTID", length = 18)
    public String getDictid() {
        return this.dictid;
    }

    public void setDictid(String dictid) {
        this.dictid = dictid;
    }

    @Column(name = "ENCRYPTED")
    public Boolean getEncrypted() {
        return this.encrypted;
    }

    public void setEncrypted(Boolean encrypted) {
        this.encrypted = encrypted;
    }

    @Column(name = "GLOBALONLY")
    public Boolean getGlobalonly() {
        return this.globalonly;
    }

    public void setGlobalonly(Boolean globalonly) {
        this.globalonly = globalonly;
    }

    @Column(name = "INSTANCEONLY")
    public Boolean getInstanceonly() {
        return this.instanceonly;
    }

    public void setInstanceonly(Boolean instanceonly) {
        this.instanceonly = instanceonly;
    }

    @Column(name = "LIVEREFRESH")
    public Boolean getLiverefresh() {
        return this.liverefresh;
    }

    public void setLiverefresh(Boolean liverefresh) {
        this.liverefresh = liverefresh;
    }

    @Column(name = "MASKED")
    public Boolean getMasked() {
        return this.masked;
    }

    public void setMasked(Boolean masked) {
        this.masked = masked;
    }

    @Column(name = "DEFAULTVALUE", length = 512)
    public String getDefaultvalue() {
        return this.defaultvalue;
    }

    public void setDefaultvalue(String defaultvalue) {
        this.defaultvalue = defaultvalue;
    }

    @Column(name = "DATATYPE", length = 8)
    public String getDatatype() {
        return this.datatype;
    }

    public void setDatatype(String datatype) {
        this.datatype = datatype;
    }

    @Column(name = "NULLSALLOWED")
    public Boolean getNullsallowed() {
        return this.nullsallowed;
    }

    public void setNullsallowed(Boolean nullsallowed) {
        this.nullsallowed = nullsallowed;
    }

    @Column(name = "ONLINECHANGES")
    public Boolean getOnlinechanges() {
        return this.onlinechanges;
    }

    public void setOnlinechanges(Boolean onlinechanges) {
        this.onlinechanges = onlinechanges;
    }

    @Column(name = "SECURELEVEL", length = 20)
    public String getSecurelevel() {
        return this.securelevel;
    }

    public void setSecurelevel(String securelevel) {
        this.securelevel = securelevel;
    }

    @Column(name = "USERDEFINED")
    public Boolean getUserdefined() {
        return this.userdefined;
    }

    public void setUserdefined(Boolean userdefined) {
        this.userdefined = userdefined;
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
