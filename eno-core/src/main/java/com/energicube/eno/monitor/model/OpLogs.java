package com.energicube.eno.monitor.model;

import javax.persistence.*;


@Entity
@Table(name = "OPLOGS")
public class OpLogs implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private Long logid;
    private String userid;
    private String dated;
    private String logger;
    private String level;
    private String subsys;
    private String message;
    private String category;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OPLOGID", unique = true, nullable = false)
    public Long getLogid() {
        return logid;
    }

    public void setLogid(Long logid) {
        this.logid = logid;
    }

    @Column(name = "USERID", length = 30)
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Column(name = "DATED", length = 30)
    public String getDated() {
        return dated;
    }

    public void setDated(String dated) {
        this.dated = dated;
    }

    @Column(name = "LOGGER", length = 200)
    public String getLogger() {
        return logger;
    }

    public void setLogger(String logger) {
        this.logger = logger;
    }

    @Column(name = "[LEVEL]", length = 10)
    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }


    @Column(name = "SUBSYS")
    public String getSubsys() {
        return subsys;
    }

    public void setSubsys(String subsys) {
        this.subsys = subsys;
    }


    @Column(name = "MESSAGE")
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Column(name = "CATEGORY")
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


}
