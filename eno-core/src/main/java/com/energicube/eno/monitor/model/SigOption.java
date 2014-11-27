package com.energicube.eno.monitor.model;


import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * 登记的签名项
 */
@Entity
@Table(name = "SIGOPTION", schema = "zclfsys")
public class SigOption implements java.io.Serializable {

    private static final long serialVersionUID = 1754374252096339566L;
    private long sigoptionid;
    private String signame;
    private String description;
    private Boolean hasld;
    private String appcode;
    private String langcode;
    private Boolean visible;
    private Boolean alsogrants;
    private Boolean alsorevokes;

    public SigOption() {
    }

    public SigOption(long sigoptionid) {
        this.sigoptionid = sigoptionid;
    }

    public SigOption(long sigoptionid, String signame, String description,
                     Boolean hasld, String appcode, String langcode, Boolean visible,
                     Boolean alsogrants, Boolean alsorevokes) {
        this.sigoptionid = sigoptionid;
        this.signame = signame;
        this.description = description;
        this.hasld = hasld;
        this.appcode = appcode;
        this.langcode = langcode;
        this.visible = visible;
        this.alsogrants = alsogrants;
        this.alsorevokes = alsorevokes;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SIGOPTIONID", unique = true, nullable = false)
    public long getSigoptionid() {
        return this.sigoptionid;
    }

    public void setSigoptionid(long sigoptionid) {
        this.sigoptionid = sigoptionid;
    }

    @NotEmpty
    @Column(name = "SIGNAME", length = 25)
    public String getSigname() {
        return this.signame;
    }

    public void setSigname(String signame) {
        this.signame = signame;
    }

    @Column(name = "DESCRIPTION", length = 100)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "HASLD")
    public Boolean getHasld() {
        return this.hasld;
    }

    public void setHasld(Boolean hasld) {
        this.hasld = hasld;
    }

    @Column(name = "APPCODE", length = 10)
    public String getAppcode() {
        return this.appcode;
    }

    public void setAppcode(String appcode) {
        this.appcode = appcode;
    }

    @Column(name = "LANGCODE", length = 6)
    public String getLangcode() {
        return this.langcode;
    }

    public void setLangcode(String langcode) {
        this.langcode = langcode;
    }

    @Column(name = "VISIBLE")
    public Boolean getVisible() {
        return this.visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    @Column(name = "ALSOGRANTS")
    public Boolean getAlsogrants() {
        return this.alsogrants;
    }

    public void setAlsogrants(Boolean alsogrants) {
        this.alsogrants = alsogrants;
    }

    @Column(name = "ALSOREVOKES")
    public Boolean getAlsorevokes() {
        return this.alsorevokes;
    }

    public void setAlsorevokes(Boolean alsorevokes) {
        this.alsorevokes = alsorevokes;
    }

}
