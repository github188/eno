package com.energicube.eno.common.jsonquery.jpa.util;

import java.util.Map;
import java.util.Map.Entry;

import com.energicube.eno.common.jsonquery.jpa.enumeration.OperatorEnum;

public class FieldReplacementUtil {
	public static final String QUOTE = "\"";

	public static String forQuery(String filter, Map<String, String> map) {

		for (Entry<String, String> entry : map.entrySet()) {
			filter = filter.replace(QUOTE + entry.getKey() + QUOTE, QUOTE
					+ entry.getValue() + QUOTE);
		}

		return filter;
	}

	public static String forOrder(String filter, Map<String, String> map) {

		for (Entry<String, String> entry : map.entrySet()) {
			filter = filter.replace(entry.getKey(), entry.getValue());
		}

		return filter;
	}

	public static String forDateRange(String filter, String field) {
		filter = DateTimeUtil.toDateRangeQuery(filter, field);
		return filter;
	}

	public static String forDateRange(String filter, String field,
			String datefrom, String dateto) {
		filter = QueryUtil.addAnd(filter, field, OperatorEnum.GREATER_EQUAL,
				datefrom);
		filter = QueryUtil.addAnd(filter, field, OperatorEnum.LESSER_EQUAL,
				dateto);
		return filter;
	}

	public static Boolean doesFieldAndOpExists(String filter, String field,
			String op) {
		if (filter
				.contains("\"field\":\"" + field + "\",\"op\":\"" + op + "\"")) {
			return true;
		}
		return false;
	}
}
