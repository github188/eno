package com.energicube.eno.alarm.model;

import java.util.List;

public class UcAlarmsList implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    //统计图的值
    private List dataList1;
    private List dataList2;
    private List dataList3;
    private String zjs1;
    private String zjs2;
    private String zjs3;
    private List cataList;

    public List getDataList1() {
        return dataList1;
    }

    public void setDataList1(List dataList1) {
        this.dataList1 = dataList1;
    }

    public List getDataList2() {
        return dataList2;
    }

    public void setDataList2(List dataList2) {
        this.dataList2 = dataList2;
    }

    public List getDataList3() {
        return dataList3;
    }

    public void setDataList3(List dataList3) {
        this.dataList3 = dataList3;
    }

    public String getZjs1() {
        return zjs1;
    }

    public void setZjs1(String zjs1) {
        this.zjs1 = zjs1;
    }

    public String getZjs2() {
        return zjs2;
    }

    public void setZjs2(String zjs2) {
        this.zjs2 = zjs2;
    }

    public String getZjs3() {
        return zjs3;
    }

    public void setZjs3(String zjs3) {
        this.zjs3 = zjs3;
    }

    public List getCataList() {
        return cataList;
    }

    public void setCataList(List cataList) {
        this.cataList = cataList;
    }
}
