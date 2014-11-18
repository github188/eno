package com.energicube.eno.asset.model;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum AssetStatusEnum {

    NOTREADY("未就绪"), OPERATING("操作中"), DECOMMISSION("停止使用");

    private static final Map<String, AssetStatusEnum> lookupByString = new HashMap<String, AssetStatusEnum>();

    static {
        for (AssetStatusEnum r : EnumSet.allOf(AssetStatusEnum.class)) {
            lookupByString.put(r.getDescription(), r);
        }
    }

    private AssetStatusEnum(String description) {
        this.description = description;
    }

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static AssetStatusEnum getEnum(String code) {
        return lookupByString.get(code);
    }

}
