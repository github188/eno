package com.energicube.eno.monitor.model;

import javax.persistence.*;

/**
 * 数据字典表，主要用于设备列表和面板的显示对应
 *
 * @author zouzhixiang 2014-10-09
 */
@Entity
@Table(name = "DICT")
public class Dict implements java.io.Serializable {
    private static final long serialVersionUID = -7848707994553419536L;
    private int id;
    private String name;
    private String classstructureid;
    private String assetattrid;
    private String description;
    private int control;
    private int translate;
    private int icon;
    private String value;
    private String display;
    private String icon_number;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "NAME")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "CLASSSTRUCTUREID")
    public String getClassstructureid() {
        return classstructureid;
    }

    public void setClassstructureid(String classstructureid) {
        this.classstructureid = classstructureid;
    }

    @Column(name = "ASSETATTRID")
    public String getAssetattrid() {
        return assetattrid;
    }

    public void setAssetattrid(String assetattrid) {
        this.assetattrid = assetattrid;
    }

    @Column(name = "DESCRIPTION")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "CONTROL")
    public int getControl() {
        return control;
    }

    public void setControl(int control) {
        this.control = control;
    }

    @Column(name = "TRANSLATE")
    public int getTranslate() {
        return translate;
    }

    public void setTranslate(int translate) {
        this.translate = translate;
    }

    @Column(name = "ICON")
    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    @Column(name = "VALUE")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Column(name = "DISPLAY")
    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    @Column(name = "ICON_NUMBER")
    public String getIcon_number() {
        return icon_number;
    }

    public void setIcon_number(String icon_number) {
        this.icon_number = icon_number;
    }

}
