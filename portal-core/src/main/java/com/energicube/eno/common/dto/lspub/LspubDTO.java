package com.energicube.eno.common.dto.lspub;

import java.io.Serializable;

/**
 * Created by EnergyUser on 14-1-6.
 */
public class LspubDTO implements Serializable {
    private String assetNum;
    private String tagId;
    private String assetName;
    private String status;
    private String specclass;
    private String classstructureid;
    private String location;
    private String coordinate;//坐标


    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public String getAssetNum() {
        return assetNum;
    }

    public void setAssetNum(String assetNum) {
        this.assetNum = assetNum;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
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
}
