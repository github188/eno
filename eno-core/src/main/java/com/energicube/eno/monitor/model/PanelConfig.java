package com.energicube.eno.monitor.model;

import javax.persistence.*;

/**
 * 平面图下方面板定义
 * <p/>
 * <p>
 * 主要用于定义面板显示的属性
 * </p>
 *
 * @author shangpeibao
 * @version 2.0
 */
@Entity
@Table(name = "PANELCONFIG")
public class PanelConfig implements java.io.Serializable {

    private static final long serialVersionUID = -3839486697734064064L;

    private int id;  // 自增主键
    private String description; // 自定义描述
    private String classstructureid;
    private String assetattrid;
    /**
     * 是否显示到页面
     * <p/>
     * 1--可设置属性
     * 0--不可设置属性
     */
    private int iseditor;

    public PanelConfig() {
    }

    public PanelConfig(int id) {
        this.id = id;
    }

    public PanelConfig(int id, String description, String classstructureid,
                       String assetattrid, int iseditor) {
        super();
        this.id = id;
        this.description = description;
        this.classstructureid = classstructureid;
        this.assetattrid = assetattrid;
        this.iseditor = iseditor;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "description", nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "classstructureid", nullable = false)
    public String getClassstructureid() {
        return classstructureid;
    }

    public void setClassstructureid(String classstructureid) {
        this.classstructureid = classstructureid;
    }

    @Column(name = "assetattrid", nullable = false)
    public String getAssetattrid() {
        return assetattrid;
    }

    public void setAssetattrid(String assetattrid) {
        this.assetattrid = assetattrid;
    }

    @Column(name = "iseditor", nullable = false)
    public int getIseditor() {
        return iseditor;
    }

    public void setIseditor(int iseditor) {
        this.iseditor = iseditor;
    }

}
