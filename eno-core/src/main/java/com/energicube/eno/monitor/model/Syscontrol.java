package com.energicube.eno.monitor.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "SYSCONTROL")
public class Syscontrol implements java.io.Serializable {

    private static final long serialVersionUID = 1467346214932390103L;
    private int controluid;
    private String controlid;
    private String ctrlname;
    private String description;
    private String settting;
    private Integer ctrltype;
    private List<Preference> preferences;

    public Syscontrol() {
    }

    public Syscontrol(int controluid, String controlid) {
        this.controluid = controluid;
        this.controlid = controlid;
    }

    public Syscontrol(int controluid, String controlid, String ctrlname,
                      String description, String settting, Integer ctrltype) {
        this.controluid = controluid;
        this.controlid = controlid;
        this.ctrlname = ctrlname;
        this.description = description;
        this.settting = settting;
        this.ctrltype = ctrltype;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CONTROLUID", unique = true, nullable = false)
    public int getControluid() {
        return this.controluid;
    }

    public void setControluid(int controluid) {
        this.controluid = controluid;
    }

    @NotEmpty
    @Column(name = "CONTROLID", nullable = false, length = 20)
    public String getControlid() {
        return this.controlid;
    }

    public void setControlid(String controlid) {
        this.controlid = controlid;
    }

    @Column(name = "CTRLNAME", length = 50)
    public String getCtrlname() {
        return this.ctrlname;
    }

    public void setCtrlname(String ctrlname) {
        this.ctrlname = ctrlname;
    }

    @Column(name = "DESCRIPTION", length = 100)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "SETTTING", length = 3000)
    public String getSettting() {
        return settting;
    }

    public void setSettting(String settting) {
        this.settting = settting;
    }

    @Column(name = "CTRLTYPE")
    public Integer getCtrltype() {
        return this.ctrltype;
    }

    public void setCtrltype(Integer ctrltype) {
        this.ctrltype = ctrltype;
    }

    @Override
    public String toString() {
        return "Syscontrol [controluid=" + controluid + ", controlid="
                + controlid + ", ctrlname=" + ctrlname + ", description="
                + description + ", settting=" + settting + ", ctrltype="
                + ctrltype + "]";
    }

    @Transient
    public List<Preference> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<Preference> preferences) {
        this.preferences = preferences;
    }

    public class Preference implements java.io.Serializable {

        private static final long serialVersionUID = 6154839892648023641L;
        private String name;
        private String value;
        private String description;
        private String group;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getGroup() {
            return group;
        }

        public void setGroup(String group) {
            this.group = group;
        }

    }
}
