package com.energicube.eno.monitor.model;

import javax.persistence.*;

/**
 *
 */
@Entity
@Table(name = "MENUOPTION")
public class MenuOption implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private long id;
    private String okcmenuid;
    private String groupname;
    private String optionname;

    public MenuOption() {
    }

    public MenuOption(long id) {
        this.id = id;
    }

    public MenuOption(long id, String okcmenuid, String groupname,
                      String optionname) {
        this.id = id;
        this.okcmenuid = okcmenuid;
        this.groupname = groupname;
        this.optionname = optionname;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "OKCMENUID", length = 10)
    public String getOkcmenuid() {
        return this.okcmenuid;
    }

    public void setOkcmenuid(String okcmenuid) {
        this.okcmenuid = okcmenuid;
    }

    @Column(name = "GROUPNAME", length = 30)
    public String getGroupname() {
        return this.groupname;
    }

    public void setGroupname(String groupname) {
        this.groupname = groupname;
    }

    @Column(name = "OPTIONNAME", length = 25)
    public String getOptionname() {
        return this.optionname;
    }

    public void setOptionname(String optionname) {
        this.optionname = optionname;
    }

}
