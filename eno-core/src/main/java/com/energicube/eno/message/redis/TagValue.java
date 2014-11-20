package com.energicube.eno.message.redis;

public class TagValue {

    private static TagInfo taginfo;

    public static TagInfo getTaginfo() {
        return taginfo;
    }

    public static void setTaginfo(TagInfo taginfo) {
        TagValue.taginfo = taginfo;
    }


}
