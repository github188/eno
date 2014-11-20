package com.energicube.eno.common.jsonquery.jpa.enumeration;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * 排序类型
 * 
 * @author CHENPING
 * */
public enum OrderEnum {

	ASC(0, "asc"), 
	DESC(1, "desc");

	private Integer value;
	private String description;
	private static final Map<Integer, OrderEnum> lookupByInteger = new HashMap<Integer, OrderEnum>();
	private static final Map<String, OrderEnum> lookupByString = new HashMap<String, OrderEnum>();

	static {
		for (OrderEnum r : EnumSet.allOf(OrderEnum.class))
			lookupByInteger.put(r.getValue(), r);

		for (OrderEnum r : EnumSet.allOf(OrderEnum.class))
			lookupByString.put(r.getDescription(), r);
	}

	private OrderEnum(Integer value, String description) {
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

	public static OrderEnum getEnum(int code) {
		return lookupByInteger.get(code);
	}

	public static OrderEnum getEnum(String code) {
		return getEnum(code, false);
	}

	public static OrderEnum getEnum(String code, Boolean caseSensitive) {
		if (caseSensitive == true) {
			return lookupByString.get(code);
		} else {
			return lookupByString.get(code.toLowerCase());
		}
	}

}
