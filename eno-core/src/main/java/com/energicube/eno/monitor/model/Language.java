package com.energicube.eno.monitor.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

/**
 * 各国语言字母码，使用
 */
@Entity
@Table(name = "LANGUAGE", schema = "dbo")
public class Language implements java.io.Serializable {
    private static final long serialVersionUID = -426869020129961912L;
    private long languageid;
    private String langcode;
    private String languagename;
    private String oralangabrv;
    private Boolean enabled;
    private Boolean userdefined;

    public Language() {
    }

    public Language(long languageid, String langcode) {
        this.languageid = languageid;
        this.langcode = langcode;
    }

    public Language(long languageid, String langcode, String languagename,
                    String oralangabrv, Boolean enabled, Boolean userdefined) {
        this.languageid = languageid;
        this.langcode = langcode;
        this.languagename = languagename;
        this.oralangabrv = oralangabrv;
        this.enabled = enabled;
        this.userdefined = userdefined;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LANGUAGEID", unique = true, nullable = false)
    public long getLanguageid() {
        return this.languageid;
    }

    public void setLanguageid(long languageid) {
        this.languageid = languageid;
    }

    @NotEmpty
    @Column(name = "LANGCODE", nullable = false, length = 4)
    public String getLangcode() {
        return this.langcode;
    }

    public void setLangcode(String langcode) {
        this.langcode = langcode;
    }

    @NotEmpty
    @Column(name = "LANGUAGENAME", length = 256)
    public String getLanguagename() {
        return this.languagename;
    }

    public void setLanguagename(String languagename) {
        this.languagename = languagename;
    }

    @Column(name = "ORALANGABRV", length = 4)
    public String getOralangabrv() {
        return this.oralangabrv;
    }

    public void setOralangabrv(String oralangabrv) {
        this.oralangabrv = oralangabrv;
    }

    @Column(name = "ENABLED")
    public Boolean getEnabled() {
        return this.enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Column(name = "USERDEFINED")
    public Boolean getUserdefined() {
        return this.userdefined;
    }

    public void setUserdefined(Boolean userdefined) {
        this.userdefined = userdefined;
    }

}
