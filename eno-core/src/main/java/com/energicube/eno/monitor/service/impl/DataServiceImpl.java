package com.energicube.eno.monitor.service.impl;

import com.energicube.eno.common.ChartModel;
import com.energicube.eno.common.Const;
import com.energicube.eno.common.DataModel;
import com.energicube.eno.common.enums.AggregateFunction;
import com.energicube.eno.common.enums.TimeScales;
import com.energicube.eno.common.jdbc.ChartModelMapper;
import com.energicube.eno.monitor.service.DataService;

import org.apache.commons.collections15.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * All rights Reserved, Designed By ZCLF Copyright: Copyright(C) 2013-2014
 * Company ZCLF Energy Technologies Inc.
 *
 * @author 邹智祥
 * @version 1.0
 * @date 2014/12/04 15:09
 * @Description ENO数据接口类
 */
@Service
public class DataServiceImpl implements DataService {

	private final static Log logger = LogFactory.getLog(DataServiceImpl.class);

	@Autowired
	private JdbcTemplate dataSourceTemplate;

	@Override
	public Map<String, Object> findRequestData(DataModel dm) {
		Map<String, Object> map = new HashedMap<String, Object>();
		List<Object> datalist = new ArrayList<Object>();// 存储数据
		List<Object> catalist = new ArrayList<Object>();// 存储时间数据
		DecimalFormat df = new DecimalFormat(StringUtils.isNotEmpty(dm.getFormat()) ? dm.getFormat() : "0.0"); // 保留小数位数，0.0表示保留一位小数
		List list = new ArrayList();
		String sql = "";

		String timescales = dm.getTimescales();
		Map<String, String> numberAndFormatMap = findSubStringLengthAndFormat(timescales);
		String number = numberAndFormatMap.get("number"); // 根据TimeScales决定截串位数
		SimpleDateFormat bFormat = new SimpleDateFormat(numberAndFormatMap.get("bFormat")); // 格式化字符串,根据TimeScales决定格式化位数
		SimpleDateFormat aFormat = new SimpleDateFormat(StringUtils.isNotEmpty(dm.getTimeformat()) ? dm.getTimeformat() : numberAndFormatMap.get("aFormat"));
		String aggregatefunction = dm.getAggregatefunction(); // MAX,MIN,SUM,AVG,CHANGE,PERCENT
		String pointname = dm.getPointname();
		String timestart = dm.getTimestart();
		String timeend = dm.getTimeend();
		String range = dm.getRange();
		
		// 当AggregateFunction为MAX,MIN,SUM,AVG,PERCENT时，执行SQL如下：
		if (Const.MAX.equalsIgnoreCase(aggregatefunction)
				|| Const.MIN.equalsIgnoreCase(aggregatefunction)
				|| Const.SUM.equalsIgnoreCase(aggregatefunction)
				|| Const.AVG.equalsIgnoreCase(aggregatefunction)) {
			sql = findSqlByTimeScales(timescales, aggregatefunction, pointname, number, timestart, timeend);
		}
		
		 // PERCENT需要特殊处理，PERCENT需要取两个值，并且进行运算
		if (Const.PERCENT.equalsIgnoreCase(aggregatefunction)) {
			map = findPrecentValue(timescales, aggregatefunction, pointname, number, timestart, timeend, range, dm.getAdditioncontion(), map);
		}
		
		logger.debug("sql--" + sql);
		if (StringUtils.isNotEmpty(sql)) {
			list = dataSourceTemplate.query(sql, new ChartModelMapper());
			for (int i = 0; i < list.size(); i++) {
				ChartModel cm = (ChartModel) list.get(i);
				datalist.add(Double.parseDouble(df.format(Double.parseDouble(cm.getValue()))));
				try {
					catalist.add(aFormat.format(bFormat.parse(cm.getTime())));
				} catch (ParseException e) {
					logger.error("--时间转换出错了--" + cm.getTime());
				}
			}
			map.put("sql", sql);
		}
		
		map.put("catalist", catalist);
		map.put("datalist", datalist);
		return map;
	}
	
	/**
	 * 根据相应的条件获取percent的值
	 * 
	 * @param timescales
	 * @param aggregatefunction
	 * @param pointname
	 * @param number
	 * @param timestart
	 * @param timeend
	 * @param range
	 * @param additioncontion
	 * @param map
	 * @description 需要计算出两个值，然后对这两个值进行相除
	 * 
	 * @return
	 */
	private Map<String, Object> findPrecentValue(String timescales, String aggregatefunction, String pointname, String number, String timestart, String timeend, String range, String additioncontion, Map<String, Object> map) {
		ChartModelMapper chartModelMapper = new ChartModelMapper();
		// 计算第一个值
		String sql = findSqlByTimeScales(timescales, range, pointname, number, timestart, timeend);
		List list = dataSourceTemplate.query(sql, chartModelMapper);
		String number1 = !list.isEmpty() ? ((ChartModel)list.get(0)).getValue() : "0";
		logger.debug("number1---" + number1);

		// 计算第二个值
		sql = findSqlByTimeScales(timescales, range, additioncontion, number, timestart, timeend);
		list = dataSourceTemplate.query(sql, chartModelMapper);
		String number2 = !list.isEmpty() ? ((ChartModel)list.get(0)).getValue() : "0";
		logger.debug("number2---" + number2);

		String percent = String.format("{0:F1}", Double.parseDouble(number1) / Double.parseDouble(number2) * 100) + "%";
		map.put("percent", percent);
		return map;
	}
	
	/**
	 * 根据AggregateFunction和TimeScales，得到相应的执行sql
	 * @param timescales
	 * @param aggregatefunction
	 * @param pointname
	 * @param number
	 * @param startdate
	 * @param enddate
	 * @return
	 */
	private String findSqlByTimeScales(String timescales, String aggregatefunction, String pointname, String number, String startdate, String enddate) {
		String sql = ""; // return value
		switch (TimeScales.getTimeScales(timescales)) {
		case hour:
			sql = "SELECT '' as name, C.t AS time," + aggregatefunction
					+ "(C.VALUE)  as value FROM  (SELECT SUBSTR(time,0,"
					+ number + ") || ':00:00' as t,value FROM " + pointname
					+ " where time  between '" + startdate + "' and '" + enddate
					+ "') C  GROUP BY C.t ORDER BY C.t";
			break;
		case day:
			sql = "SELECT '' as name, C.t AS time," + aggregatefunction
					+ "(C.VALUE)  as value FROM  (SELECT SUBSTR(time,0,"
					+ number + ") as t,value FROM " + pointname
					+ " where time  between '" + startdate + "' and '" + enddate
					+ "') C  GROUP BY C.t ORDER BY C.t";
			break;
		case week:
			sql = "SELECT '' as name, C.t  AS time,"
					+ aggregatefunction
					+ "(c.VALUE)  as value FROM  (SELECT to_char(TO_DATE(SUBSTR(time,0,10), 'YYYY-MM-DD') - (to_number(to_char(TO_DATE(SUBSTR(time,0,10), 'YYYY-MM-DD') - 1,'d')) -1),'yyyy-ww') as t,value FROM " + pointname
					+ " where time  between '" + startdate + "' and '"
					+ enddate + "') C  GROUP BY C.t ORDER BY C.t";
			break;
		case month:
			sql = "SELECT '' as name, C.t  AS time," + aggregatefunction
			+ "(C.VALUE)  as value FROM  (SELECT SUBSTR(time,0,"
			+ number + ") as t,value FROM " + pointname
			+ " where time  between '" + startdate + "' and '" + enddate
			+ "') C  GROUP BY C.t ORDER BY C.t";
			break;
		case year:
			sql = "SELECT '' as name, C.t  AS time," + aggregatefunction
			+ "(C.VALUE)  as value FROM  (SELECT SUBSTR(time,0,"
			+ number + ") as t,value FROM " + pointname
			+ " where time  between '" + startdate + "' and '" + enddate
			+ "') C  GROUP BY C.t ORDER BY C.t";
			break;
		default:
			sql = "";
			break;
		}
		return sql;
	}
	
	/**
	 * 根据TimeScales决定sql截取的位数
	 * @param timescales
	 * @return
	 */
	private Map<String, String> findSubStringLengthAndFormat(String timescales) {
		Map<String, String> map = new HashedMap<String, String>();
		String number = "", bFormat = Const.YEARMONTHDAYHOUR, aFormat = Const.YEARMONTHDAYHOUR; // return number
		switch (TimeScales.getTimeScales(timescales)) {
		case hour:
			number = "13";
			bFormat = Const.YEARMONTHDAYHOUR;
			aFormat = Const.HOUR;
			break;
		case day:
			number = "10";
			bFormat = Const.YEARMONTHDAY;
			aFormat = Const.YEARMONTHDAY;
			break;
		case week:
			number = "4";
			bFormat = Const.YEARWEEK;
			aFormat = Const.YEARWEEK;
			break;
		case month:
			number = "7";
			bFormat = Const.YEARMONTH;
			aFormat = Const.YEARMONTH;
			break;
		case year:
			number = "4";
			bFormat = Const.YEAR;
			aFormat = Const.YEAR;
			break;
		default:
			number = "13";
			bFormat = Const.YEARMONTHDAYHOUR;
			aFormat = Const.HOUR;
			break;
		}
		map.put("number", number);
		map.put("aFormat", aFormat);
		map.put("bFormat", bFormat);
		return map;
	}
}
