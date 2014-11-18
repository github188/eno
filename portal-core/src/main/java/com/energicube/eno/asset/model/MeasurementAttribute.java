package com.energicube.eno.asset.model;

/**
 * 计量属性信息
 *
 * @author CHENPING 2014-02-24
 */
public class MeasurementAttribute {

    private String assetnum;
    private String metername;
    private String valuetag;
    private String assetattrid;
    private String descrption;
    private String measureunitid;
    private String classstructureid; // 为适应WEB版万达设备列表和面板数据字典对应而加[2014-10-16,zzx]

    public String getAssetnum() {
        return assetnum;
    }

    public void setAssetnum(String assetnum) {
        this.assetnum = assetnum;
    }

    public String getMetername() {
        return metername;
    }

    public void setMetername(String metername) {
        this.metername = metername;
    }

    public String getValuetag() {
        return valuetag;
    }

    public void setValuetag(String valuetag) {
        this.valuetag = valuetag;
    }

    public String getAssetattrid() {
        return assetattrid;
    }

    public void setAssetattrid(String assetattrid) {
        this.assetattrid = assetattrid;
    }

    public String getDescrption() {
        return descrption;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }

    public String getMeasureunitid() {
        return measureunitid;
    }

    public void setMeasureunitid(String measureunitid) {
        this.measureunitid = measureunitid;
    }

    public String getClassstructureid() {
        return classstructureid;
    }

    public void setClassstructureid(String classstructureid) {
        this.classstructureid = classstructureid;
    }

    @Override
    public String toString() {
        return "MeasurementAttribute [assetnum=" + assetnum + ", metername="
                + metername + ", valuetag=" + valuetag + ", assetattrid="
                + assetattrid + ", descrption=" + descrption
                + ", measureunitid=" + measureunitid
                + ", classstructureid=" + classstructureid + "]";
    }

}
