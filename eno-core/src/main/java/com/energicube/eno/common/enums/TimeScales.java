package com.energicube.eno.common.enums;

/**
 * All rights Reserved, Designed By ZCLF
 * Copyright:    Copyright(C) 2013-2014
 * Company   ZCLF Energy Technologies Inc.
 *
 * @author 邹智祥
 * @version 1.0
 * @date 2014/12/04 15:36
 * @Description 数据接口枚举类
 */
public enum TimeScales {
	hour, day, week, month, year;

	public static TimeScales getTimeScales(String timescales) {
		return valueOf(timescales.toLowerCase());
	}
}
