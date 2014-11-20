package com.energicube.eno.asset.model;

public class Readingtype implements java.io.Serializable {

    private static final long serialVersionUID = 9127922859591392387L;

    public String readingtype;

    public String description;

    public Readingtype() {
    }

    public Readingtype(String readingtype, String description) {
        super();
        this.readingtype = readingtype;
        this.description = description;
    }

    public String getReadingtype() {
        return readingtype;
    }

    public void setReadingtype(String readingtype) {
        this.readingtype = readingtype;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
