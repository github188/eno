package com.energicube.eno.message.redis;

/**
 * 客流发送命令
 *
 * @author ZOUZHIXIANG
 * @create 2014-07-31
 */
public class PassengerCmdInfo implements java.io.Serializable {

    private static final long serialVersionUID = 7549528961025703102L;
    private String partype; // 区分是切换楼层(switchfloor)还是具体的店铺(shopdata)
    private String paramter;
    private String name;
    private String childpoint;

    public PassengerCmdInfo() {

    }

    public String getParamter() {
        return paramter;
    }

    public void setParamter(String paramter) {
        this.paramter = paramter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPartype() {
        return partype;
    }

    public void setPartype(String partype) {
        this.partype = partype;
    }

    public String getChildpoint() {
        return childpoint;
    }

    public void setChildpoint(String childpoint) {
        this.childpoint = childpoint;
    }

    /**
     * 发送字符串
     */
    public String toSendString() {
        return "{\"paramter\":\"" + paramter + "\",\"name\":\"" + name + "\",\"partype\":\"" + partype + "\",\"childpoint\":" + childpoint + "}";
    }

}