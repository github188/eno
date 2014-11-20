package com.energicube.eno.common.jsonquery.jpa.enumeration;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * 连接类型
 * 
 * @author CHENPING
 * */
public enum JunctionEnum {
	
	AND(0, "and"), OR(1, "or");

	private Integer value;
	private String description;
	private static final Map<Integer, JunctionEnum> lookupByInteger = new HashMap<Integer, JunctionEnum>();
	private static final Map<String, JunctionEnum> lookupByString = new HashMap<String, JunctionEnum>();

	static {
		for (JunctionEnum r : EnumSet.allOf(JunctionEnum.class))
			lookupByInteger.put(r.getValue(), r);

		for (JunctionEnum r : EnumSet.allOf(JunctionEnum.class))
			lookupByString.put(r.getDescription(), r);
	}

	private JunctionEnum(Integer value, String description) {
		this.value = value;
		this.description = description;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public static JunctionEnum getEnum(int code) {
		return lookupByInteger.get(code);
	}

	public static JunctionEnum getEnum(String code) {
		return getEnum(code, false);
	}

	public static JunctionEnum getEnum(String code, Boolean caseSensitive) {
		if (caseSensitive == true) {
			return lookupByString.get(code);
		} else {
			return lookupByString.get(code.toLowerCase());
		}
	}
}
