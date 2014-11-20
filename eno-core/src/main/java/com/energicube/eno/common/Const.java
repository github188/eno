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
}
