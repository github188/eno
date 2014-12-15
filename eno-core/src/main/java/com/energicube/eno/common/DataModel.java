package com.energicube.eno.common;

/**
 * All rights Reserved, Designed By ZCLF Copyright: Copyright(C) 2013-2014
 * Company ZCLF Energy Technologies Inc.
 *
 * @author 邹智祥
 * @version 1.0
 * @date 2014/12/04 15:06
 * @Description ENO数据接口类
 */
public class DataModel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String pointname; // 点位名称
	private String timescales; // HOUR,DAY,WEEK,MONTH,YEAR
	private String aggregatefunction; // MAX,MIN,SUM,AVG,CHANGE,PERCENT
	// 如果AggregateFunction为CHANGE,该项为SUM或AVG，以区分该变化度是以平均值或求和值为单位；
	// 如果AggregateFunction为PERCENT,该项为被除数的名称，如总用电
	private String additioncontion;
	private String timestart; // 起始时间
	private String timeend; // 终止时间
	private String format; // 值格式化要求
	private String timeformat; // 时间格式化
	private String range; // MAX,MIN,SUM,AVG，仅当AggregateFunction为PERCENT时生效

	public String getRange() {
		return range;
	}

	public void setRange(String range) {
		this.range = range;
	}

	public String getTimeformat() {
		return timeformat;
	}

	public void setTimeformat(String timeformat) {
		this.timeformat = timeformat;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getPointname() {
		return pointname;
	}

	public void setPointname(String pointname) {
		this.pointname = pointname;
	}

	public String getTimescales() {
		return timescales;
	}

	public void setTimescales(String timescales) {
		this.timescales = timescales;
	}

	public String getAggregatefunction() {
		return aggregatefunction;
	}

	public void setAggregatefunction(String aggregatefunction) {
		this.aggregatefunction = aggregatefunction;
	}

	public String getAdditioncontion() {
		return additioncontion;
	}

	public void setAdditioncontion(String additioncontion) {
		this.additioncontion = additioncontion;
	}

	public String getTimestart() {
		return timestart;
	}

	public void setTimestart(String timestart) {
		this.timestart = timestart;
	}

	public String getTimeend() {
		return timeend;
	}

	public void setTimeend(String timeend) {
		this.timeend = timeend;
	}

}
