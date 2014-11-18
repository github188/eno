package com.energicube.eno.asset.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum FailureCodeType {
    PROBLEM("PROBLEM"),
    CAUSE("CAUSE"),
    REMEDY("REMEDY");

    private String description;

    private static final Map<String, FailureCodeType> lookupByString = new HashMap<String, FailureCodeType>();


    static {
        for (FailureCodeType r : EnumSet.allOf(FailureCodeType.class))
            lookupByString.put(r.getDescription(), r);
    }

    private FailureCodeType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public static FailureCodeType getEnum(String code) {
        return getEnum(code, false);
    }

    public static FailureCodeType getEnum(String code, Boolean caseSensitive) {
        if (caseSensitive == true) {
            return lookupByString.get(code);
        } else {
            return lookupByString.get(code.toLowerCase());
        }
    }


}
