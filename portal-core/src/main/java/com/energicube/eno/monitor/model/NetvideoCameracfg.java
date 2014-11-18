package com.energicube.eno.monitor.model;


import javax.persistence.*;

/**
 * 摄像机配置
 */
@Entity
@Table(name = "NETVIDEO_CAMERACFG")
public class NetvideoCameracfg implements java.io.Serializable {

    private static final long serialVersionUID = 6559538813228032560L;
    private int cameracfgid;
    private String cameraid;  //摄像机编码
    private String cameraname;  //摄像机名称
    private String dvrname;  //DVR名称
    private int dvrchannel;  //DVR输入通道
    private String matrix;  //连接矩阵
    private int matrixchannel;  //矩阵输入通道
    private int matrixindex;
    private String ptzcontrol;  //云镜控制
    private int ptzindex;
    private String resourceid;  //资源信息
    private Integer displaysequence;
    private Integer ptzparam1;
    private Integer ptzparam2;
    private Integer ptzparam3;
    private Integer groupid;//分组信息id

    public NetvideoCameracfg() {
    }

    public NetvideoCameracfg(int cameracfgid, String cameraid, String dvrname) {
        this.cameracfgid = cameracfgid;
        this.cameraid = cameraid;
        this.dvrname = dvrname;
    }

    public NetvideoCameracfg(int cameracfgid, String cameraid,
                             String cameraname, String dvrname, int dvrchannel,
                             String matrix, int matrixchannel, int matrixindex, String ptzcontrol, int ptzindex,
                             String resourceid, Integer displaysequence, Integer ptzparam1, Integer ptzparam2, Integer ptzparam3,
                             Integer groupid) {
        this.cameracfgid = cameracfgid;
        this.cameraid = cameraid;
        this.cameraname = cameraname;
        this.dvrname = dvrname;
        this.dvrchannel = dvrchannel;
        this.matrix = matrix;
        this.matrixchannel = matrixchannel;
        this.matrixindex = matrixindex;
        this.ptzcontrol = ptzcontrol;
        this.ptzindex = ptzindex;
        this.resourceid = resourceid;
        this.displaysequence = displaysequence;
        this.ptzparam1 = ptzparam1;
        this.ptzparam2 = ptzparam2;
        this.ptzparam3 = ptzparam3;
        this.groupid = groupid;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CAMERACFGID", unique = true, nullable = false)
    public int getCameracfgid() {
        return this.cameracfgid;
    }

    public void setCameracfgid(int cameracfgid) {
        this.cameracfgid = cameracfgid;
    }

    @Column(name = "CAMERAID", nullable = false, length = 50)
    public String getCameraid() {
        return this.cameraid;
    }

    public void setCameraid(String cameraid) {
        this.cameraid = cameraid;
    }

    @Column(name = "CAMERANAME", length = 50)
    public String getCameraname() {
        return this.cameraname;
    }

    public void setCameraname(String cameraname) {
        this.cameraname = cameraname;
    }

    @Column(name = "DVRNAME", nullable = false, length = 50)
    public String getDvrname() {
        return this.dvrname;
    }

    public void setDvrname(String dvrname) {
        this.dvrname = dvrname;
    }

    @Column(name = "DVRCHANNEL", length = 50)
    public int getDvrchannel() {
        return this.dvrchannel;
    }

    public void setDvrchannel(int dvrchannel) {
        this.dvrchannel = dvrchannel;
    }

    @Column(name = "MATRIX", length = 50)
    public String getMatrix() {
        return this.matrix;
    }

    public void setMatrix(String matrix) {
        this.matrix = matrix;
    }

    @Column(name = "MATRIXCHANNEL", length = 50)
    public int getMatrixchannel() {
        return this.matrixchannel;
    }

    public void setMatrixchannel(int matrixchannel) {
        this.matrixchannel = matrixchannel;
    }

    @Column(name = "PTZCONTROL", length = 50)
    public String getPtzcontrol() {
        return this.ptzcontrol;
    }

    public void setPtzcontrol(String ptzcontrol) {
        this.ptzcontrol = ptzcontrol;
    }

    @Column(name = "RESOURCEID")
    public String getResourceid() {
        return this.resourceid;
    }

    public void setResourceid(String resourceid) {
        this.resourceid = resourceid;
    }

    @Column(name = "DISPLAYSEQUENCE", unique = true, nullable = false)
    public Integer getDisplaysequence() {
        return displaysequence;
    }

    public void setDisplaysequence(Integer displaysequence) {
        this.displaysequence = displaysequence;
    }

    @Column(name = "PTZPARAM1", nullable = false)
    public Integer getPtzparam1() {
        return ptzparam1;
    }

    public void setPtzparam1(Integer ptzparam1) {
        this.ptzparam1 = ptzparam1;
    }

    @Column(name = "PTZPARAM2", nullable = false)
    public Integer getPtzparam2() {
        return ptzparam2;
    }

    public void setPtzparam2(Integer ptzparam2) {
        this.ptzparam2 = ptzparam2;
    }

    @Column(name = "PTZPARAM3", nullable = false)
    public Integer getPtzparam3() {
        return ptzparam3;
    }

    public void setPtzparam3(Integer ptzparam3) {
        this.ptzparam3 = ptzparam3;
    }

    @Column(name = "MATRIXINDEX", nullable = false)
    public int getMatrixindex() {
        return matrixindex;
    }

    public void setMatrixindex(int matrixindex) {
        this.matrixindex = matrixindex;
    }

    @Column(name = "PTZCONTROLINDEX", nullable = false)
    public int getPtzindex() {
        return ptzindex;
    }

    public void setPtzindex(int ptzindex) {
        this.ptzindex = ptzindex;
    }

    @Column(name = "PNODES", length = 10)
    public Integer getGroupid() {
        return groupid;
    }

    public void setGroupid(Integer groupid) {
        this.groupid = groupid;
    }

}
