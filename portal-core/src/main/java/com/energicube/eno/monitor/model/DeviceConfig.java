package com.energicube.eno.monitor.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * 设备配置
 */
@Entity
@Table(name = "DEVICECONFIG")
public class DeviceConfig implements java.io.Serializable {
    private static final long serialVersionUID = -7848707994553419536L;
    private int id;
    private String system;
    private String classid;
    private String attribute;
    private String description;
    private String modul;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "SYSTEM", length = 30)
    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    @NotEmpty
    @Column(name = "CLASSID", nullable = false, length = 30)
    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    @NotEmpty
    @Column(name = "ATTRIBUTE", nullable = false, length = 30)
    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    @Column(name = "DESCRIPTION", nullable = false, length = 255)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "MODUL")
    public String getModul() {
        return modul;
    }

    public void setModul(String modul) {
        this.modul = modul;
    }

}
