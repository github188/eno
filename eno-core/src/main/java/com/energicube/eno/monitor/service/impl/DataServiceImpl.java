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
			String per = findPrecentValue(timescales, aggregatefunction, pointname, number, timestart, timeend, range, dm.getAdditioncontion());
			map.put("percent", per);
		}
		
		logger.debug("sql--" + sql);
		if (StringUtils.isNotEmpty(sql)) {
			List list = dataSourceTemplate.query(sql, new ChartModelMapper());
			for (int i = 0; i < list.size(); i++) {
				ChartModel cm = (ChartModel) list.get(i);
				try {
					datalist.add(Double.parseDouble(df.format(Double.parseDouble(cm.getValue()))));
					catalist.add(aFormat.format(bFormat.parse(cm.getTime())));
				} catch (ParseException e) {
					logger.error(cm.getValue() + "--时间转换出错了--" + cm.getTime());
				}
			}
		}
		
		map.put("sql", sql);
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
	private String findPrecentValue(String timescales, String aggregatefunction, String pointname, String number, String timestart, String timeend, String range, String additioncontion) {
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

		DecimalFormat df = new DecimalFormat("0.0");
		return df.format(Double.parseDouble(number1) / Double.parseDouble(number2) * 100);
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
	
	
	
	
	
	
	
	
	
	@Override
	public Map<String, Object> getDataAndCataList(DataModel dm) {
		Map<String, Object> map = new HashedMap<String, Object>();
		DecimalFormat df = new DecimalFormat(StringUtils.isNotEmpty(dm.getFormat()) ? dm.getFormat() : "0.0"); // 保留小数位数，0.0表示保留一位小数
		String sql = "";
		
		List<Object> returnDataList = new ArrayList<Object>(); // 数据存储
		List<Object> returnCataList = new ArrayList<Object>(); // 数据存储
		
		String format = dm.getTimeformat();
		String[] points = dm.getPointname().split(",");
		String[] timescaleses = dm.getTimescales().split(",");
		String[] aggregatefunctions = dm.getAggregatefunction().split(",");
		String[] timestarts = dm.getTimestart().split(",");
		String[] timeends = dm.getTimeend().split(",");
		
		for (int i = 0; i < points.length; i++) {
			List<Object> tempDataList = new ArrayList<Object>(); // 数据存储
			List<Object> tempCataList = new ArrayList<Object>(); // 存储

			String timescales = timescaleses[i];
			Map<String, String> numberAndFormatMap = findSubStringLengthAndFormat(timescales);
			String number = numberAndFormatMap.get("number"); // 根据TimeScales决定截串位数
			SimpleDateFormat bFormat = new SimpleDateFormat(numberAndFormatMap.get("bFormat")); // 格式化字符串,根据TimeScales决定格式化位数
			SimpleDateFormat aFormat = new SimpleDateFormat(StringUtils.isNotEmpty(format) ? format : numberAndFormatMap.get("aFormat"));
			String aggregatefunction = aggregatefunctions[i]; // MAX,MIN,SUM,AVG,CHANGE,PERCENT
			String pointname = points[i];
			String timestart = timestarts[i];
			String timeend = timeends[i];
			
			// 当AggregateFunction为MAX,MIN,SUM,AVG,PERCENT时，执行SQL如下：
			if (Const.MAX.equalsIgnoreCase(aggregatefunction)
					|| Const.MIN.equalsIgnoreCase(aggregatefunction)
					|| Const.SUM.equalsIgnoreCase(aggregatefunction)
					|| Const.AVG.equalsIgnoreCase(aggregatefunction)) {
				sql = findSqlByTimeScales(timescales, aggregatefunction, pointname, number, timestart, timeend);
			}
			
			 // PERCENT需要特殊处理，PERCENT需要取两个值，并且进行运算
			if (Const.PERCENT.equalsIgnoreCase(aggregatefunction)) {
				String[] additioncontions = dm.getAdditioncontion().split(",");
				String[] ranges = dm.getRange().split(",");
				String per = findPrecentValue(timescales, aggregatefunction, pointname, number, timestart, timeend, ranges[i], additioncontions[i]);
				map.put("percent", per);
			}
			
			logger.debug("sql--" + sql);
			if (StringUtils.isNotEmpty(sql)) {
				List list = dataSourceTemplate.query(sql, new ChartModelMapper());
				for (int k = 0; k < list.size(); k++) {
					List<Object> datalist = new ArrayList<Object>();// 存储数据
					List<Object> catalist = new ArrayList<Object>();// 存储时间数据
					ChartModel cm = (ChartModel) list.get(k);
					try {
						datalist.add(Double.parseDouble(df.format(Double.parseDouble(cm.getValue()))));
						catalist.add(aFormat.format(bFormat.parse(cm.getTime())));
					} catch (ParseException e) {
						logger.error(cm.getValue() + "--时间转换出错了--" + cm.getTime());
					}
					tempDataList.add(datalist);
					tempCataList.add(catalist);
				}
			}
			
			returnDataList.add(tempDataList);
			returnCataList.add(tempCataList);
		}
		
		map.put("sql", sql);
		map.put("data", returnDataList);
		map.put("cata", returnCataList);
		
		return map;
	}
	
	
	
	@Override
	public Map<String, Object> getPieDataList(DataModel dm) {
		Map<String, Object> map = new HashedMap<String, Object>();
		List<Object> returnDataList = new ArrayList<Object>(); // 数据存储
		
		Map<String, String> numberAndFormatMap = findSubStringLengthAndFormat(dm.getTimescales());
		String number = numberAndFormatMap.get("number"); // 根据TimeScales决定截串位数
		
		String[] points = dm.getPointname().split(",");
		for (int i = 0; i < points.length; i++) {
			try {
				String per = findPrecentValue(dm.getTimescales(), dm.getAggregatefunction(), points[i], number, dm.getTimestart(), dm.getTimeend(), dm.getRange(), dm.getAdditioncontion());
				returnDataList.add(per);
			} catch (Exception e) {
				returnDataList.add("0");
				logger.error("-----计算percent值的时候出错了-----", e);
			}
		}
		map.put("data", returnDataList);
		return map;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
