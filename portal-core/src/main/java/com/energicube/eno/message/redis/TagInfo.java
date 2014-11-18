package com.energicube.eno.message.redis;

import java.util.Calendar;

/**
 * 计量点信息
 */
public class TagInfo implements java.io.Serializable {

    private static final long serialVersionUID = -8623752016327368808L;
    // TagID
    private Integer id;
    private Integer f;
    private Integer p;//1 Boolean 2double 3long 4String
    private Double t;//shijian
    private String v;

    public TagInfo() {

    }

    public TagInfo(Integer id, Integer f, Integer p, Double t, String v) {
        this.id = id;
        this.f = f;
        this.p = p;
        this.t = t;
        this.v = v;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getF() {
        return f;
    }

    public void setF(Integer f) {
        this.f = f;
    }

    public Integer getP() {
        return p;
    }

    public void setP(Integer p) {
        this.p = p;
    }

    public Double getT() {
        return t;
    }

    public void setT(Double t) {
        this.t = t;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }

    /**
     * 发送字符串
     */
    public String toSendString() {
        String value = "" + v + "";
        //通过P类型判断值类型,1:boolean 2:integer 3:  4:string
        if (p == 4) {
            value = "\"" + v + "\"";
        }
        return "{\"cmd\":\"setval\",\"id\":" + id + ",\"t\":" + "\"" + Calendar.getInstance().getTimeInMillis() + "\"" + ",\"p\":" + p + ",\"v\":" + value + "}";
    }

    @Override
    public String toString() {
        return "TagInfo [id=" + id + ", f=" + f + ", p=" + p + ", t=" + t
                + ", v=" + v + "]";
    }

}