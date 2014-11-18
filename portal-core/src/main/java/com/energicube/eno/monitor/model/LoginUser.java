package com.energicube.eno.monitor.model;

public class LoginUser implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    private String img;
    private String value;
    private String name;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}