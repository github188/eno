package com.energicube.eno.monitor.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 导出导入列配置
 */
@Entity
@Table(name = "DATA_COLS_RELATION")
public class Datacolsrelation implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private long datacolsrelationuid;
    private String datacolumnconfigid;
    private String incolumnname;
    private String outcolumnname;
    private String caption;

    public Datacolsrelation() {
    }

    public Datacolsrelation(long datacolsrelationuid,
                            String datacolumnconfigid, String incolumnname) {
        this.datacolsrelationuid = datacolsrelationuid;
        this.datacolumnconfigid = datacolumnconfigid;
        this.incolumnname = incolumnname;
    }

    public Datacolsrelation(long datacolsrelationuid,
                            String datacolumnconfigid, String incolumnname,
                            String outcolumnname, String caption) {
        this.datacolsrelationuid = datacolsrelationuid;
        this.datacolumnconfigid = datacolumnconfigid;
        this.incolumnname = incolumnname;
        this.outcolumnname = outcolumnname;
        this.caption = caption;
    }

    @Id
    @Column(name = "DATACOLSRELATIONUID", unique = true, nullable = false)
    public long getDatacolsrelationuid() {
        return this.datacolsrelationuid;
    }

    public void setDatacolsrelationuid(long datacolsrelationuid) {
        this.datacolsrelationuid = datacolsrelationuid;
    }

    @Column(name = "DATACOLUMNCONFIGID", nullable = false, length = 12)
    public String getDatacolumnconfigid() {
        return this.datacolumnconfigid;
    }

    public void setDatacolumnconfigid(String datacolumnconfigid) {
        this.datacolumnconfigid = datacolumnconfigid;
    }

    @Column(name = "INCOLUMNNAME", nullable = false, length = 50)
    public String getIncolumnname() {
        return this.incolumnname;
    }

    public void setIncolumnname(String incolumnname) {
        this.incolumnname = incolumnname;
    }

    @Column(name = "OUTCOLUMNNAME", length = 50)
    public String getOutcolumnname() {
        return this.outcolumnname;
    }

    public void setOutcolumnname(String outcolumnname) {
        this.outcolumnname = outcolumnname;
    }

    @Column(name = "CAPTION", length = 30)
    public String getCaption() {
        return this.caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

}
