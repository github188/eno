package com.energicube.eno.monitor.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class NetvideoMatrixConfig implements Serializable {

    private String type_0;//型号
    private String type_1;
    private String type_2;
    private String type_3;
    private String com_0;//连接COM口
    private String com_1;
    private String com_2;
    private String com_3;
    private String baudRate_0;//波特率
    private String baudRate_1;
    private String baudRate_2;
    private String baudRate_3;
    private String validetype_0;//校验方式
    private String validetype_1;
    private String validetype_2;
    private String validetype_3;
    private int dataBit_0;//数据位
    private int dataBit_1;
    private int dataBit_2;
    private int dataBit_3;
    private int soptBit_0;//停止位
    private int soptBit_1;
    private int soptBit_2;
    private int soptBit_3;
    private String username_0;//用户名
    private String username_1;
    private String username_2;
    private String username_3;
    private String password_0;//密码
    private String password_1;
    private String password_2;
    private String password_3;
    private int freeChannel_0;//空闲输出通道
    private int freeChannel_1;
    private int freeChannel_2;
    private int freeChannel_3;

    public NetvideoMatrixConfig(String type_0, String type_1, String type_2,
                                String type_3, String com_0, String com_1, String com_2,
                                String com_3, String baudRate_0, String baudRate_1,
                                String baudRate_2, String baudRate_3, String validetype_0,
                                String validetype_1, String validetype_2, String validetype_3,
                                int dataBit_0, int dataBit_1, int dataBit_2, int dataBit_3,
                                int soptBit_0, int soptBit_1, int soptBit_2, int soptBit_3,
                                String username_0, String username_1, String username_2,
                                String username_3, String password_0, String password_1,
                                String password_2, String password_3, int freeChannel_0,
                                int freeChannel_1, int freeChannel_2, int freeChannel_3) {
        super();
        this.type_0 = type_0;
        this.type_1 = type_1;
        this.type_2 = type_2;
        this.type_3 = type_3;
        this.com_0 = com_0;
        this.com_1 = com_1;
        this.com_2 = com_2;
        this.com_3 = com_3;
        this.baudRate_0 = baudRate_0;
        this.baudRate_1 = baudRate_1;
        this.baudRate_2 = baudRate_2;
        this.baudRate_3 = baudRate_3;
        this.validetype_0 = validetype_0;
        this.validetype_1 = validetype_1;
        this.validetype_2 = validetype_2;
        this.validetype_3 = validetype_3;
        this.dataBit_0 = dataBit_0;
        this.dataBit_1 = dataBit_1;
        this.dataBit_2 = dataBit_2;
        this.dataBit_3 = dataBit_3;
        this.soptBit_0 = soptBit_0;
        this.soptBit_1 = soptBit_1;
        this.soptBit_2 = soptBit_2;
        this.soptBit_3 = soptBit_3;
        this.username_0 = username_0;
        this.username_1 = username_1;
        this.username_2 = username_2;
        this.username_3 = username_3;
        this.password_0 = password_0;
        this.password_1 = password_1;
        this.password_2 = password_2;
        this.password_3 = password_3;
        this.freeChannel_0 = freeChannel_0;
        this.freeChannel_1 = freeChannel_1;
        this.freeChannel_2 = freeChannel_2;
        this.freeChannel_3 = freeChannel_3;
    }

    public NetvideoMatrixConfig() {
        super();
    }

    public String getType_0() {
        return type_0;
    }

    public void setType_0(String type_0) {
        this.type_0 = type_0;
    }

    public String getType_1() {
        return type_1;
    }

    public void setType_1(String type_1) {
        this.type_1 = type_1;
    }

    public String getType_2() {
        return type_2;
    }

    public void setType_2(String type_2) {
        this.type_2 = type_2;
    }

    public String getType_3() {
        return type_3;
    }

    public void setType_3(String type_3) {
        this.type_3 = type_3;
    }

    public String getCom_0() {
        return com_0;
    }

    public void setCom_0(String com_0) {
        this.com_0 = com_0;
    }

    public String getCom_1() {
        return com_1;
    }

    public void setCom_1(String com_1) {
        this.com_1 = com_1;
    }

    public String getCom_2() {
        return com_2;
    }

    public void setCom_2(String com_2) {
        this.com_2 = com_2;
    }

    public String getCom_3() {
        return com_3;
    }

    public void setCom_3(String com_3) {
        this.com_3 = com_3;
    }

    public String getBaudRate_0() {
        return baudRate_0;
    }

    public void setBaudRate_0(String baudRate_0) {
        this.baudRate_0 = baudRate_0;
    }

    public String getBaudRate_1() {
        return baudRate_1;
    }

    public void setBaudRate_1(String baudRate_1) {
        this.baudRate_1 = baudRate_1;
    }

    public String getBaudRate_2() {
        return baudRate_2;
    }

    public void setBaudRate_2(String baudRate_2) {
        this.baudRate_2 = baudRate_2;
    }

    public String getBaudRate_3() {
        return baudRate_3;
    }

    public void setBaudRate_3(String baudRate_3) {
        this.baudRate_3 = baudRate_3;
    }

    public String getValidetype_0() {
        return validetype_0;
    }

    public void setValidetype_0(String validetype_0) {
        this.validetype_0 = validetype_0;
    }

    public String getValidetype_1() {
        return validetype_1;
    }

    public void setValidetype_1(String validetype_1) {
        this.validetype_1 = validetype_1;
    }

    public String getValidetype_2() {
        return validetype_2;
    }

    public void setValidetype_2(String validetype_2) {
        this.validetype_2 = validetype_2;
    }

    public String getValidetype_3() {
        return validetype_3;
    }

    public void setValidetype_3(String validetype_3) {
        this.validetype_3 = validetype_3;
    }

    public int getDataBit_0() {
        return dataBit_0;
    }

    public void setDataBit_0(int dataBit_0) {
        this.dataBit_0 = dataBit_0;
    }

    public int getDataBit_1() {
        return dataBit_1;
    }

    public void setDataBit_1(int dataBit_1) {
        this.dataBit_1 = dataBit_1;
    }

    public int getDataBit_2() {
        return dataBit_2;
    }

    public void setDataBit_2(int dataBit_2) {
        this.dataBit_2 = dataBit_2;
    }

    public int getDataBit_3() {
        return dataBit_3;
    }

    public void setDataBit_3(int dataBit_3) {
        this.dataBit_3 = dataBit_3;
    }

    public int getSoptBit_0() {
        return soptBit_0;
    }

    public void setSoptBit_0(int soptBit_0) {
        this.soptBit_0 = soptBit_0;
    }

    public int getSoptBit_1() {
        return soptBit_1;
    }

    public void setSoptBit_1(int soptBit_1) {
        this.soptBit_1 = soptBit_1;
    }

    public int getSoptBit_2() {
        return soptBit_2;
    }

    public void setSoptBit_2(int soptBit_2) {
        this.soptBit_2 = soptBit_2;
    }

    public int getSoptBit_3() {
        return soptBit_3;
    }

    public void setSoptBit_3(int soptBit_3) {
        this.soptBit_3 = soptBit_3;
    }

    public String getUsername_0() {
        return username_0;
    }

    public void setUsername_0(String username_0) {
        this.username_0 = username_0;
    }

    public String getUsername_1() {
        return username_1;
    }

    public void setUsername_1(String username_1) {
        this.username_1 = username_1;
    }

    public String getUsername_2() {
        return username_2;
    }

    public void setUsername_2(String username_2) {
        this.username_2 = username_2;
    }

    public String getUsername_3() {
        return username_3;
    }

    public void setUsername_3(String username_3) {
        this.username_3 = username_3;
    }

    public String getPassword_0() {
        return password_0;
    }

    public void setPassword_0(String password_0) {
        this.password_0 = password_0;
    }

    public String getPassword_1() {
        return password_1;
    }

    public void setPassword_1(String password_1) {
        this.password_1 = password_1;
    }

    public String getPassword_2() {
        return password_2;
    }

    public void setPassword_2(String password_2) {
        this.password_2 = password_2;
    }

    public String getPassword_3() {
        return password_3;
    }

    public void setPassword_3(String password_3) {
        this.password_3 = password_3;
    }

    public int getFreeChannel_0() {
        return freeChannel_0;
    }

    public void setFreeChannel_0(int freeChannel_0) {
        this.freeChannel_0 = freeChannel_0;
    }

    public int getFreeChannel_1() {
        return freeChannel_1;
    }

    public void setFreeChannel_1(int freeChannel_1) {
        this.freeChannel_1 = freeChannel_1;
    }

    public int getFreeChannel_2() {
        return freeChannel_2;
    }

    public void setFreeChannel_2(int freeChannel_2) {
        this.freeChannel_2 = freeChannel_2;
    }

    public int getFreeChannel_3() {
        return freeChannel_3;
    }

    public void setFreeChannel_3(int freeChannel_3) {
        this.freeChannel_3 = freeChannel_3;
    }

}
