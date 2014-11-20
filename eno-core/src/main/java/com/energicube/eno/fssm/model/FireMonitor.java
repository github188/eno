package com.energicube.eno.fssm.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.UUID;

/**
 * 消防消息
 *
 * @author CHENPING
 */
@Entity
@Table(name = "FSSM_FIREMONITOR")
public class FireMonitor implements java.io.Serializable {
    private static final long serialVersionUID = -3555323263029542423L;

    private String msgid;    //消息ID
    private String msgtype = "XF";    //消息类型,默认为XF
    private String timestamp;// 消息时间戳
    private String syscode;    //系统代码
    private String signaltype;    //消防信号类型(D)  紧急火警:JJHJ  确认火警:QRHJ  自动火警:ZDHJ  误报火警:WBHJ  测试火警:CSHJ  故障:GZ  动作:DZ  反馈:FK  监管:JG 屏蔽:PB
    private String signaltime; //消防信号发生时间
    private String devicetype;  //设备类型(D) 慧云系统: HY  烟感:YG  温感:WG  手动报警器:SD  漏电:LD 感温电缆:GWDL  消防水泡:XFSP  气体灭火:QTMH  燃气:RQ
    private String devicecode;    //设备编码
    private String displaycode;  //设备显示编码
    private String devicelocation;    //设备位置
    private String businesstype;    //业态类型(D)
    private String relatefile;    //相关图片
    private String coordinate;  //消息点坐标,如：  X:1012-Y:354，表示X轴1012像素、Y轴354像素的点位
    private String description;    //消防信号描述
    private String userid;    //慧云系统值班人员
    private String usertel;    //值班人员联系

    @JsonIgnore
    private int almlogid;   //不生成为JSON Field


    public FireMonitor() {
    }

    public FireMonitor(String msgid) {
        this.msgid = msgid;
    }

    public FireMonitor(String msgid, String msgtype, String syscode,
                       String signaltype, String signaltime, String devicetype,
                       String devicecode, String displaycode, String devicelocation,
                       String businesstype, String relatefile, String description,
                       String userid, String usertel) {
        this.msgid = msgid;
        this.msgtype = msgtype;
        this.syscode = syscode;
        this.signaltype = signaltype;
        this.signaltime = signaltime;
        this.devicetype = devicetype;
        this.devicecode = devicecode;
        this.displaycode = displaycode;
        this.devicelocation = devicelocation;
        this.businesstype = businesstype;
        this.relatefile = relatefile;
        this.description = description;
        this.userid = userid;
        this.usertel = usertel;
    }

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(name = "MSGID", unique = true, nullable = false, length = 64)
    public String getMsgid() {
        return this.msgid;
    }

    public void setMsgid(String msgid) {
        if (msgid == null || "".equals(msgid)) {
            msgid = UUID.randomUUID().toString().replace("-", "");
        }
        this.msgid = msgid;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "TIMESTAMP", length = 23)
    public String getTimestamp() {
        return this.timestamp;
    }


    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Column(name = "MSGTYPE", length = 12)
    public String getMsgtype() {
        return this.msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    @Column(name = "SYSCODE", length = 30)
    public String getSyscode() {
        return this.syscode;
    }

    public void setSyscode(String syscode) {
        this.syscode = syscode;
    }

    @Column(name = "SIGNALTYPE", length = 30)
    public String getSignaltype() {
        return this.signaltype;
    }

    public void setSignaltype(String signaltype) {
        this.signaltype = signaltype;
    }

    @Column(name = "SIGNALTIME", length = 23)
    public String getSignaltime() {
        return this.signaltime;
    }

    public void setSignaltime(String signaltime) {

        this.signaltime = signaltime;
    }

    @Column(name = "DEVICETYPE", length = 12)
    public String getDevicetype() {
        return this.devicetype;
    }

    public void setDevicetype(String devicetype) {
        this.devicetype = devicetype;
    }

    @Column(name = "DEVICECODE", length = 30)
    public String getDevicecode() {
        return this.devicecode;
    }

    public void setDevicecode(String devicecode) {
        this.devicecode = devicecode;
    }

    @Column(name = "DISPLAYCODE", length = 50)
    public String getDisplaycode() {
        return this.displaycode;
    }

    public void setDisplaycode(String displaycode) {
        this.displaycode = displaycode;
    }

    @Column(name = "DEVICELOCATION", length = 100)
    public String getDevicelocation() {
        return this.devicelocation;
    }

    public void setDevicelocation(String devicelocation) {
        this.devicelocation = devicelocation;
    }

    @Column(name = "BUSINESSTYPE", length = 12)
    public String getBusinesstype() {
        return this.businesstype;
    }

    public void setBusinesstype(String businesstype) {
        this.businesstype = businesstype;
    }

    @Column(name = "RELATEFILE", length = 128)
    public String getRelatefile() {
        return this.relatefile;
    }

    public void setRelatefile(String relatefile) {
        this.relatefile = relatefile;
    }


    @Column(name = "COORDINATE", length = 128)
    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    @Column(name = "DESCRIPTION", length = 200)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "USERID", length = 32)
    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Column(name = "USERTEL", length = 32)
    public String getUsertel() {
        return this.usertel;
    }

    public void setUsertel(String usertel) {
        this.usertel = usertel;
    }

    @Column(name = "ALMLOGID")
    public int getAlmlogid() {
        return almlogid;
    }

    public void setAlmlogid(int almlogid) {
        this.almlogid = almlogid;
    }

    @Override
    public String toString() {
        return "FireMonitor [msgid=" + msgid + ", msgtype=" + msgtype
                + ", timestamp=" + timestamp + ", syscode=" + syscode
                + ", signaltype=" + signaltype + ", signaltime=" + signaltime
                + ", devicetype=" + devicetype + ", devicecode=" + devicecode
                + ", displaycode=" + displaycode + ", devicelocation="
                + devicelocation + ", businesstype=" + businesstype
                + ", relatefile=" + relatefile + ", coordinate=" + coordinate
                + ", description=" + description + ", userid=" + userid
                + ", usertel=" + usertel + ", almlogid=" + almlogid + "]";
    }


}
