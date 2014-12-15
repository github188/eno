package com.energicube.eno.common.enums;

/**
 * All rights Reserved, Designed By ZCLF Copyright: Copyright(C) 2013-2014
 * Company ZCLF Energy Technologies Inc.
 *
 * @author 邹智祥
 * @version 1.0
 * @date 2014/12/04 15:41
 * @Description 数据接口枚举类
 */
public enum AggregateFunction {
	max, min, sum, avg, change, percent;

	public static AggregateFunction getAggregateFunction(String aggregatefunction) {
		return valueOf(aggregatefunction.toLowerCase());
	}
}
