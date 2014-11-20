package com.energicube.eno.message.redis;

import java.util.ArrayList;
import java.util.List;

public class TaginfoCollection {

    private static List<TagInfo> list = new ArrayList<TagInfo>();
    private static List<AlarmsInfo> list2 = new ArrayList<AlarmsInfo>();

    public static List<TagInfo> getList() {
        return list;
    }

    public static void setList(List<TagInfo> list) {
        TaginfoCollection.list = list;
    }

    public static List<AlarmsInfo> getList2() {
        return list2;
    }

    public static void setList2(List<AlarmsInfo> list2) {
        TaginfoCollection.list2 = list2;
    }


}
