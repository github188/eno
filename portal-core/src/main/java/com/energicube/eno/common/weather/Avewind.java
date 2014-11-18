package com.energicube.eno.common.weather;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-9-10
 * Time: 下午3:39
 * To change this template use File | Settings | File Templates.
 */
public class Avewind implements Serializable {
    private String degrees;
    private String dir;//             "西北"
    private String kph;

    private String mph;

    public String getDegrees() {
        return degrees;
    }

    public void setDegrees(String degrees) {
        this.degrees = degrees;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getKph() {
        return kph;
    }

    public void setKph(String kph) {
        this.kph = kph;
    }

    public String getMph() {
        return mph;
    }

    public void setMph(String mph) {
        this.mph = mph;
    }

    @Override
    public String toString() {
        return "Avewind{" +
                "degrees='" + degrees + '\'' +
                ", dir='" + dir + '\'' +
                ", kph='" + kph + '\'' +
                ", mph='" + mph + '\'' +
                '}';
    }
}
