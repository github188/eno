package com.energicube.eno.monitor.model;


import javax.persistence.*;

/**
 * 文档类型
 */
@Entity
@Table(name = "DOCTYPES")
public class Doctypes implements java.io.Serializable {

    private static final long serialVersionUID = -2120036229181180083L;
    private int doctypesid;
    private String doctype;
    private String description;
    private String defaultfilepath;

    public Doctypes() {
    }

    public Doctypes(int doctypesid, String doctype) {
        this.doctypesid = doctypesid;
        this.doctype = doctype;
    }

    public Doctypes(int doctypesid, String doctype, String description,
                    String defaultfilepath) {
        this.doctypesid = doctypesid;
        this.doctype = doctype;
        this.description = description;
        this.defaultfilepath = defaultfilepath;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DOCTYPESID", unique = true, nullable = false)
    public int getDoctypesid() {
        return this.doctypesid;
    }

    public void setDoctypesid(int doctypesid) {
        this.doctypesid = doctypesid;
    }

    @Column(name = "DOCTYPE", nullable = false, length = 16)
    public String getDoctype() {
        return this.doctype;
    }

    public void setDoctype(String doctype) {
        this.doctype = doctype;
    }

    @Column(name = "DESCRIPTION", length = 100)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "DEFAULTFILEPATH")
    public String getDefaultfilepath() {
        return this.defaultfilepath;
    }

    public void setDefaultfilepath(String defaultfilepath) {
        this.defaultfilepath = defaultfilepath;
    }

}
