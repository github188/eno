package com.energicube.eno.monitor.model;

public class ModelArray implements java.io.Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private UclcAction[] uclcActionArray;// 预案动作对象集合
    private UclcLinkcell[] uclcLinkcellArray;// 联动规划关联预案对象集合
    private NetvideoDvrcfg[] dvrcfgArray; // DVR配置对象集合
    private NetvideoMonitorcfg[] monitorcfgArray;// 存放修改或添加的监视器配置
    private NetvideoCameracfg[] cameracfgArray;
    private NetvideoRotationConfig[] rotationConfigs;
    private Datacolsrelation[] datacolsrelations;// 导入导出列配置信息集合

    public ModelArray() {
        super();
        // TODO Auto-generated constructor stub
    }

    public ModelArray(NetvideoRotationConfig[] rotationConfigs) {
        super();
        this.rotationConfigs = rotationConfigs;
    }

    public ModelArray(UclcAction[] uclcActionArray) {
        super();
        this.uclcActionArray = uclcActionArray;
    }

    public ModelArray(UclcLinkcell[] uclcLinkcellArray) {
        super();
        this.uclcLinkcellArray = uclcLinkcellArray;
    }

    public ModelArray(NetvideoDvrcfg[] dvrcfgArray) {
        super();
        this.dvrcfgArray = dvrcfgArray;
    }

    public ModelArray(NetvideoMonitorcfg[] monitorcfgArray) {
        super();
        this.monitorcfgArray = monitorcfgArray;
    }

    public ModelArray(NetvideoCameracfg[] cameracfgArray) {
        super();
        this.cameracfgArray = cameracfgArray;
    }

    public ModelArray(Datacolsrelation[] datacolsrelations) {
        super();
        this.datacolsrelations = datacolsrelations;
    }

    public UclcAction[] getUclcActionArray() {
        return uclcActionArray;
    }

    public void setUclcActionArray(UclcAction[] uclcActionArray) {
        this.uclcActionArray = uclcActionArray;
    }

    public UclcLinkcell[] getUclcLinkcellArray() {
        return uclcLinkcellArray;
    }

    public void setUclcLinkcellArray(UclcLinkcell[] uclcLinkcellArray) {
        this.uclcLinkcellArray = uclcLinkcellArray;
    }

    public NetvideoDvrcfg[] getDvrcfgArray() {
        return dvrcfgArray;
    }

    public void setDvrcfgArray(NetvideoDvrcfg[] dvrcfgArray) {
        this.dvrcfgArray = dvrcfgArray;
    }

    public NetvideoMonitorcfg[] getMonitorcfgArray() {
        return monitorcfgArray;
    }

    public void setMonitorcfgArray(NetvideoMonitorcfg[] monitorcfgArray) {
        this.monitorcfgArray = monitorcfgArray;
    }

    public NetvideoCameracfg[] getCameracfgArray() {
        return cameracfgArray;
    }

    public void setCameracfgArray(NetvideoCameracfg[] cameracfgArray) {
        this.cameracfgArray = cameracfgArray;
    }

    public Datacolsrelation[] getDatacolsrelations() {
        return datacolsrelations;
    }

    public NetvideoRotationConfig[] getRotationConfigs() {
        return rotationConfigs;
    }

    public void setDatacolsrelations(Datacolsrelation[] datacolsrelations) {
        this.datacolsrelations = datacolsrelations;
    }

    public void setRotationConfigs(NetvideoRotationConfig[] rotationConfigs) {
        this.rotationConfigs = rotationConfigs;
    }


}
