package com.energicube.eno.fssm.model;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

/**
 * 链路消息Model
 *
 * @author CHENPING
 */
@Entity
@Table(name = "FSSM_LINKMONITOR")
public class LinkMonitor implements java.io.Serializable {

    private static final long serialVersionUID = 5364201519970938749L;
    private String msgguid;    //消息ID
    private String timestamp;// 消息时间戳
    private String msgtype = "LL";    //消息类型： LL
    private String syscode;     //慧云系统编码
    private String linktype = "ZC";    //链路检测类型,ZC:正常 GZ:故障汇报(当慧云系统故障时，需通过此消息通知道安监系统 ) YJ:预警提示(当慧云系统本身超过 6个小时 未收到任何消防设备所上传的消息时，通过此状态知安监系统)
    private String ipaddress;  //慧云系统IPv4地址
    private String description;    //消息描述
    private String userid;     //慧云系统值班人员ID
    private String usertel;     //值班人员联系电话

    public LinkMonitor() {
    }

    public LinkMonitor(String msgguid) {
        this.msgguid = msgguid;
    }

    public LinkMonitor(String msgguid, String msgtype, String syscode,
                       String linktype, String ipaddress, String description,
                       String userid, String usertel) {
        this.msgguid = msgguid;
        this.msgtype = msgtype;
        this.syscode = syscode;
        this.linktype = linktype;
        this.ipaddress = ipaddress;
        this.description = description;
        this.userid = userid;
        this.usertel = usertel;
    }

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid.hex")
    @Column(name = "MSGGUID", unique = true, nullable = false, length = 64)
    public String getMsgguid() {
        return this.msgguid;
    }

    public void setMsgguid(String msgguid) {
        this.msgguid = msgguid;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
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

    @Column(name = "LINKTYPE", length = 10)
    public String getLinktype() {
        return this.linktype;
    }

    public void setLinktype(String linktype) {
        this.linktype = linktype;
    }

    @Column(name = "IPADDRESS", length = 30)
    public String getIpaddress() {
        return this.ipaddress;
    }

    public void setIpaddress(String ipaddress) {
        this.ipaddress = ipaddress;
    }

    @Column(name = "DESCRIPTION", length = 128)
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

}
