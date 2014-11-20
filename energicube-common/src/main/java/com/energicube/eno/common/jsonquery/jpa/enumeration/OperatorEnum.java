package com.energicube.eno.common.jsonquery.jpa.enumeration;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * 操作类型
 * */
public enum OperatorEnum {
	
	EQUAL(0, "eq"), 
	NOT_EQUAL(1, "ne"), 
	LESS_THAN(2, "lt"), 
	GREATER_THAN(3,"gt"), 
	GREATER_EQUAL(4, "ge"), 
	LESSER_EQUAL(5, "le"), 
	ENDS_WITH(6,"ew"), 
	BEGINS_WITH(7, "bw"), 
	CONTAINS(8, "cn");

	private Integer value;
	private String description;
	private static final Map<Integer, OperatorEnum> lookupByInteger = new HashMap<Integer, OperatorEnum>();
	private static final Map<String, OperatorEnum> lookupByString = new HashMap<String, OperatorEnum>();

	static {
		for (OperatorEnum r : EnumSet.allOf(OperatorEnum.class))
			lookupByInteger.put(r.getValue(), r);

		for (OperatorEnum r : EnumSet.allOf(OperatorEnum.class))
			lookupByString.put(r.getDescription(), r);
	}

	private OperatorEnum(Integer value, String description) {
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

	public static OperatorEnum getEnum(int code) {
		return lookupByInteger.get(code);
	}

	public static OperatorEnum getEnum(String code) {
		return getEnum(code, false);
	}

	public static OperatorEnum getEnum(String code, Boolean caseSensitive) {
		if (caseSensitive == true) {
			return lookupByString.get(code);
		} else {
			return lookupByString.get(code.toLowerCase());
		}
	}
}
