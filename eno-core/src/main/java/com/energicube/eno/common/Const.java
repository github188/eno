package com.energicube.eno.common;

import com.energicube.eno.monitor.model.KeyValue;

import java.util.ArrayList;
import java.util.List;

public class Const {

    /**
     * 报警类型
     */
    public static final List<KeyValue> WarningType = warningType();

    /**
     * 报警级别
     */
    public static List<KeyValue> WarningLevel = warningLevel();

    private static List<KeyValue> warningLevel() {
        List<KeyValue> list = new ArrayList<KeyValue>();
        list.add(new KeyValue("801-999", "极高"));
        list.add(new KeyValue("401-800", "高"));
        list.add(new KeyValue("201-400", "中"));
        list.add(new KeyValue("0-200", "低"));
        return list;
    }

    private static List<KeyValue> warningType() {
        List<KeyValue> list = new ArrayList<KeyValue>();
        list.add(new KeyValue("1", "低低"));
        list.add(new KeyValue("2", "低"));
        list.add(new KeyValue("4", "高"));
        list.add(new KeyValue("8", "高高"));
        list.add(new KeyValue("256", "开"));
        list.add(new KeyValue("512", "关"));
        return list;
    }
    //报警级别 UcAlarmlog中的almpriority
    public static final Integer AlarmPriority_1 = 1;//紧急
    public static final Integer AlarmPriority_2 = 2;//严重2
    public static final Integer AlarmPriority_3 = 3;//严重3
    public static final Integer AlarmPriority_4 = 4;//普通

    // 格式化前的字符串
    public static final String YEARMONTHDAYHOURMINUTESECOND = "yyyy-MM-dd HH:mm:ss";
    public static final String YEARMONTHDAYHOURMINUTE = "yyyy-MM-dd HH:mm";
    public static final String YEARMONTHDAYHOUR = "yyyy-MM-dd HH";
    public static final String YEARMONTHDAY = "yyyy-MM-dd";
    public static final String YEARMONTH = "yyyy-MM";
    public static final String YEAR = "yyyy";
    public static final String YEARWEEK = "yyyy-ww";
    public static final String MONTH = "MM";
    public static final String DAY = "dd";
    public static final String HOUR = "HH";
    public static final String HOURMINUTESECOND = "HH:mm:ss";
    public static final String HOURMINUTE = "HH:mm";
    
    // 定义取值时需要用到的变量
    public static final String MAX = "max";
    public static final String MIN = "min";
    public static final String SUM = "sum";
    public static final String AVG = "avg";
    public static final String CHANGE = "change";
    public static final String PERCENT = "percent";
}
