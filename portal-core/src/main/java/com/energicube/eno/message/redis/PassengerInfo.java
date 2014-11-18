package com.energicube.eno.message.redis;

/**
 * 客流需要信息
 *
 * @author ZOUZHIXIANG
 * @create 2014-07-29
 */
public class PassengerInfo implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private String tagid;
    private String avg_month; // 本月日均客流量
    private String avg_year; // 本年日均客流量
    private String change; // 同比昨日
    private String sum_day; // 今日总客流
    private String paramter; // 店铺名称
    private String name; // 店铺名称
    private String sum; // 店铺名称
    private String sort_value; // 店铺排名

    public PassengerInfo(String tagid, String avg_month, String avg_year,
                         String change, String sum_day, String paramter, String name,
                         String sum, String sort_value) {
        super();
        this.tagid = tagid;
        this.avg_month = avg_month;
        this.avg_year = avg_year;
        this.change = change;
        this.sum_day = sum_day;
        this.paramter = paramter;
        this.name = name;
        this.sum = sum;
        this.sort_value = sort_value;
    }

    public String getTagid() {
        return tagid;
    }

    public void setTagid(String tagid) {
        this.tagid = tagid;
    }

    public String getAvg_month() {
        return avg_month;
    }

    public void setAvg_month(String avg_month) {
        this.avg_month = avg_month;
    }

    public String getAvg_year() {
        return avg_year;
    }

    public void setAvg_year(String avg_year) {
        this.avg_year = avg_year;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getSum_day() {
        return sum_day;
    }

    public void setSum_day(String sum_day) {
        this.sum_day = sum_day;
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

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getSort_value() {
        return sort_value;
    }

    public void setSort_value(String sort_value) {
        this.sort_value = sort_value;
    }

}