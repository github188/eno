package com.energicube.eno.monitor.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 导出导入配置
 * <p>
 * 数据库导出导出配置，保存要导出的表或导入的表。
 * </p>
 */
@Entity
@Table(name = "DATA_IE_CONFIG")
public class Dataieconfig implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private int datacolumnconfiguid;
    private String datacolumnconfigid;
    private String configname;
    private String description;
    private String datasourceconfigid;
    private String datatable;
    private String configtype;

    public Dataieconfig() {
    }

    public Dataieconfig(int datacolumnconfiguid, String datacolumnconfigid,
                        String configname, String datasourceconfigid, String datatable,
                        String configtype) {
        this.datacolumnconfiguid = datacolumnconfiguid;
        this.datacolumnconfigid = datacolumnconfigid;
        this.configname = configname;
        this.datasourceconfigid = datasourceconfigid;
        this.datatable = datatable;
        this.configtype = configtype;
    }

    public Dataieconfig(int datacolumnconfiguid, String datacolumnconfigid,
                        String configname, String description, String datasourceconfigid,
                        String datatable, String configtype) {
        this.datacolumnconfiguid = datacolumnconfiguid;
        this.datacolumnconfigid = datacolumnconfigid;
        this.configname = configname;
        this.description = description;
        this.datasourceconfigid = datasourceconfigid;
        this.datatable = datatable;
        this.configtype = configtype;
    }

    @Id
    @Column(name = "DATACOLUMNCONFIGUID", unique = true, nullable = false)
    public int getDatacolumnconfiguid() {
        return this.datacolumnconfiguid;
    }

    public void setDatacolumnconfiguid(int datacolumnconfiguid) {
        this.datacolumnconfiguid = datacolumnconfiguid;
    }

    @Column(name = "DATACOLUMNCONFIGID", nullable = false, length = 12)
    public String getDatacolumnconfigid() {
        return this.datacolumnconfigid;
    }

    public void setDatacolumnconfigid(String datacolumnconfigid) {
        this.datacolumnconfigid = datacolumnconfigid;
    }

    @Column(name = "CONFIGNAME", nullable = false, length = 30)
    public String getConfigname() {
        return this.configname;
    }

    public void setConfigname(String configname) {
        this.configname = configname;
    }

    @Column(name = "DESCRIPTION", length = 100)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "DATASOURCECONFIGID", nullable = false, length = 12)
    public String getDatasourceconfigid() {
        return this.datasourceconfigid;
    }

    public void setDatasourceconfigid(String datasourceconfigid) {
        this.datasourceconfigid = datasourceconfigid;
    }

    @Column(name = "DATATABLE", nullable = false, length = 50)
    public String getDatatable() {
        return this.datatable;
    }

    public void setDatatable(String datatable) {
        this.datatable = datatable;
    }

    @Column(name = "CONFIGTYPE", nullable = false, length = 20)
    public String getConfigtype() {
        return this.configtype;
    }

    public void setConfigtype(String configtype) {
        this.configtype = configtype;
    }

}
