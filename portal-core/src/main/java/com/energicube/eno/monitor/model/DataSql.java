package com.energicube.eno.monitor.model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 *
 */
@Entity
@Table(name = "DATA_SQL")
public class DataSql implements java.io.Serializable {

    private static final long serialVersionUID = 7110634621291744444L;
    private int datasqlid;
    private String datasourceconfigid;
    private String script;
    private String comments;

    public DataSql() {
    }

    public DataSql(int datasqlid, String datasourceconfigid, String script) {
        this.datasqlid = datasqlid;
        this.datasourceconfigid = datasourceconfigid;
        this.script = script;
    }

    public DataSql(int datasqlid, String datasourceconfigid, String script,
                   String comments) {
        this.datasqlid = datasqlid;
        this.datasourceconfigid = datasourceconfigid;
        this.script = script;
        this.comments = comments;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DATASQLID", unique = true, nullable = false)
    public int getDatasqlid() {
        return this.datasqlid;
    }

    public void setDatasqlid(int datasqlid) {
        this.datasqlid = datasqlid;
    }

    @NotNull
    @Column(name = "DATASOURCECONFIGID", nullable = false, length = 12)
    public String getDatasourceconfigid() {
        return this.datasourceconfigid;
    }

    public void setDatasourceconfigid(String datasourceconfigid) {
        this.datasourceconfigid = datasourceconfigid;
    }

    @NotNull
    @Column(name = "SCRIPT", nullable = false, length = 1000)
    public String getScript() {
        return this.script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    @Column(name = "COMMENTS", length = 100)
    public String getComments() {
        return this.comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

}
