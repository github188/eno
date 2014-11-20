package com.energicube.eno.common.dto;

import java.io.Serializable;

public class DeviceConfigDTO implements Serializable {
    public int id;
    public String system;
    public String systemdesc;
    public String classid;
    public String classiddesc;
    public String description;
    public String modul;
    public String attribute;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModul() {
        return modul;
    }

    public void setModul(String modul) {
        this.modul = modul;
    }

    public String getSystemdesc() {
        return systemdesc;
    }

    public void setSystemdesc(String systemdesc) {
        this.systemdesc = systemdesc;
    }

    public String getClassiddesc() {
        return classiddesc;
    }

    public void setClassiddesc(String classiddesc) {
        this.classiddesc = classiddesc;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

}
