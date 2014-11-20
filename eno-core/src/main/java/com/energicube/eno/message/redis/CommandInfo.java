package com.energicube.eno.message.redis;


/**
 * 计量点信息
 */
public class CommandInfo implements java.io.Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 7549528961025703102L;
    private String p1;//惟一id
    private String value;
    private String cmd;//

    public CommandInfo() {

    }

    public String getP1() {
        return p1;
    }

    public void setP1(String p1) {
        this.p1 = p1;
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 发送字符串
     */
    public String toSendString() {
        return String.format("{\"cmd\":\"%s\",\"p1\":\"%s\"}", cmd, p1);
    }

}