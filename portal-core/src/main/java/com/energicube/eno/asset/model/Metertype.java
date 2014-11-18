package com.energicube.eno.asset.model;

public class Metertype implements java.io.Serializable {

    private static final long serialVersionUID = -7638163970826953862L;

    private String metertype;

    public String description;

    public Metertype() {

    }

    public Metertype(String metertype, String description) {
        super();
        this.metertype = metertype;
        this.description = description;
    }

    public String getMetertype() {
        return metertype;
    }

    public void setMetertype(String metertype) {
        this.metertype = metertype;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
