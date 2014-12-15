package com.energicube.eno.monitor.model;

import javax.persistence.*;

/**
 * 设备扩展信息，主要用于报警数据传输
 *
 * @author CHENPING
 */
@Entity
@Table(name = "UC_TAGINFOEXT")
public class UcTaginfoExt implements java.io.Serializable {

    private static final long serialVersionUID = 2959563503144523757L;

    private long taginfoextuid;
    private int tagid;        //TAGID
    private String coordinate; //设备坐标	for example：X:300-Y:400
    private String devicetype;    //设备类型编码     慧云系统: HY  烟感:YG  温感:WG  手动报警器:SD  漏电:LD 感温电缆:GWDL  消防水泡:XFSP  气体灭火:QTMH  燃气:RQ
    private String devicecode;    //设备编码
    private String displaycode;    //显示代码
    private String devicelocation;    //设备位置
    private String floornum;    //所在楼层

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TAGINFOEXTUID", unique = true, nullable = false)
    public long getTaginfoextuid() {
        return taginfoextuid;
    }

    public void setTaginfoextuid(long taginfoextuid) {
        this.taginfoextuid = taginfoextuid;
    }

    @Column(name = "TAGID", nullable = false)
    public int getTagid() {
        return tagid;
    }

    public void setTagid(int tagid) {
        this.tagid = tagid;
    }

    @Column(name = "COORDINATE", nullable = false, length = 40)
    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    @Column(name = "DEVICETYPE", nullable = false, length = 12)
    public String getDevicetype() {
        return devicetype;
    }

    public void setDevicetype(String devicetype) {
        this.devicetype = devicetype;
    }

    @Column(name = "DEVICECODE", length = 30)
    public String getDevicecode() {
        return devicecode;
    }

    public void setDevicecode(String devicecode) {
        this.devicecode = devicecode;
    }

    @Column(name = "DISPLAYCODE", length = 50)
    public String getDisplaycode() {
        return displaycode;
    }

    public void setDisplaycode(String displaycode) {
        this.displaycode = displaycode;
    }

    public String getDevicelocation() {
        return devicelocation;
    }

    @Column(name = "DEVICELOCATION", length = 100)
    public void setDevicelocation(String devicelocation) {
        this.devicelocation = devicelocation;
    }

    @Column(name = "FLOORNUM", length = 12)
    public String getFloornum() {
        return floornum;
    }

    public void setFloornum(String floornum) {
        this.floornum = floornum;
    }

    @Override
    public String toString() {
        return "UcTaginfoExt [taginfoextuid=" + taginfoextuid + ", tagid="
                + tagid + ", coordinate=" + coordinate + ", devicetype="
                + devicetype + ", devicecode=" + devicecode + ", displaycode="
                + displaycode + ", devicelocation=" + devicelocation
                + ", floornum=" + floornum + "]";
    }


}
