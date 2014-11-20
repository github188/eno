package com.energicube.eno.common.jsonquery.jpa.util;

import com.energicube.eno.common.jsonquery.jpa.enumeration.OperatorEnum;
import com.energicube.eno.common.jsonquery.jpa.filter.JsonFilter;
import com.energicube.eno.common.jsonquery.jpa.mapper.JsonObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * Retrieves the correct {@link org.joda.time.DateTime} for the current timezone
 * 
 * */
public class DateTimeUtil {
	
	public static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd");
	public static TimeZone TIMEZONE = new DateTime().getZone().toTimeZone();

	public static DateTime getDateTimeWithOffset(DateTime dt) {
		DateTime dateTime = dt.withZone(DateTimeZone.forTimeZone(TIMEZONE));
		return normalize(dateTime);
	}

	public static DateTime normalize(DateTime dateTime) {
		if (dateTime.toString().matches(".+(\\+)[0-9][0-9]:[0-9][0-9]")) {
			dateTime = dateTime.minusHours(dateTime.getHourOfDay());

		} else if (dateTime.toString().matches(".+(\\-)[0-9][0-9]:[0-9][0-9]")) {
			dateTime = dateTime.plusHours(24 - dateTime.getHourOfDay());
		}
		return dateTime;
	}

	public static Map<String, DateTime> getYesterdaySpan() {
		DateTime now = new DateTime();

		DateTime from = now.minusDays(1).minusHours(now.getHourOfDay())
				.minusMinutes(now.getMinuteOfHour())
				.minusSeconds(now.getSecondOfMinute())
				.minusMillis(now.getMillisOfSecond());

		DateTime to = from.plusHours(23).plusMinutes(59).plusSeconds(59);

		Map<String, DateTime> map = new HashMap<String, DateTime>();
		map.put("from", from);
		map.put("to", to);

		return map;
	}

	public static String getDateTimeAsString(String filter, String datefield) {
		JsonFilter jsonFilter = JsonObjectMapper.map(filter);

		for (JsonFilter.Rule rule : jsonFilter.getRules()) {
			if (rule.getField().equals(datefield)) {
				return rule.getData();
			}
		}

		return null;
	}

	public static DateTime getDateTime(String filter, String datefield) {
		JsonFilter jsonFilter = JsonObjectMapper.map(filter);

		for (JsonFilter.Rule rule : jsonFilter.getRules()) {
			if (rule.getField().equals(datefield)) {
				return new DateTime(rule.getData());
			}
		}

		return null;
	}

	public static String toDateRangeQuery(String filter, String datefield) {
		DateTime from = DateTimeUtil.getDateTime(filter, datefield);
		DateTime to = from.plusHours(23).plusMinutes(59).plusSeconds(59);

		// Replace operator from "eq" to "ge"
		filter = filter.replace(
				"\"field\":\"" + datefield + "\",\"op\":\"eq\"", "\"field\":\""
						+ datefield + "\",\"op\":\"ge\"");
		filter = QueryUtil.addAnd(filter, datefield, OperatorEnum.LESSER_EQUAL,
				to.toString());

		return filter;
	}

	public static String format(Date date, String parsePatterns) {
		if (StringUtils.isEmpty(parsePatterns) || date == null) {
			return null;
		}
		return new SimpleDateFormat(parsePatterns).format(date);
	}
}
