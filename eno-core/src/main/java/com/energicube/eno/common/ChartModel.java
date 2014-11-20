package com.energicube.eno.common;

public class ChartModel implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -1735566680532906636L;

    /**
     * 名称
     */
    private String name;
    /**
     * 时间
     */
    private String time;
    /**
     * value值
     */
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
