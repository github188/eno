package com.energicube.eno.common.dto.lspub;

/**
 * Created by EnergyUser on 14-1-16.
 */
public class AssetGroupDTO {

    private String groupName;  //组名
    private String status;    //状态开 关
    private String specclass; //系统编码
    private String classstructureid;  //分类编码
    private String location;   //几层
    private String coordinate;//坐标
    private String enName;

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSpecclass() {
        return specclass;
    }

    public void setSpecclass(String specclass) {
        this.specclass = specclass;
    }

    public String getClassstructureid() {
        return classstructureid;
    }

    public void setClassstructureid(String classstructureid) {
        this.classstructureid = classstructureid;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }
}
