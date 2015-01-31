$(function() {
			var start = getCurrentTime(), end = getTimeByDays(start, 1);

			buildEnergyNineChart(start, start, end, "hour", "4"); // 生成九宫格图表
			buildEnergySexChart(start, start, end, "hour", "4"); // 生成六宫格图表
			
		});
		
var sexEnergyTotalName = 'CO10_RA#KT_3F_2,CO18_RA#KT_5_5'; // 六宫格-1.建筑总能耗图表
// 生成六宫格-2.建筑总能耗分项
var sexEnergySubentry =  'CO18_RA#KT_5_5,CO18_RA#KT_5_5,CO18_RA#KT_5_5,CO18_RA#KT_5_5,CO18_RA#KT_5_5,CO18_RA#KT_5_5,CO18_RA#KT_5_5' ;
var sexEnergySubentrySum =  'CO10_RA#KT_3F_2' ;
var sexElectricityTotalName = 'CO10_RA#KT_3F_2,CO18_RA#KT_5_5'; // 六宫格-3.建筑总能耗图表
// 生成六宫格-4.空调系统用电分项
var sexHvacSubentry =  'CO18_RA#KT_5_5,CO18_RA#KT_5_5,CO18_RA#KT_5_5,CO18_RA#KT_5_5,CO18_RA#KT_5_5,CO18_RA#KT_5_5' ;
var sexHvacSubentrySum =  'CO10_RA#KT_3F_2' ;
var sexDataCenterName = 'CO10_RA#KT_3F_2,CO18_RA#KT_5_5'; // 六宫格-5.数据中心用电图表
var sexDeviceEnergyName = 'CO10_RA#KT_3F_2,CO18_RA#KT_5_5'; // 六宫格-6.建筑设备能耗图表

var nineEnergyTotalName = 'CO10_RA#KT_3F_2'; // 九宫格-1.建筑总能耗图表
var nineElectricityTotalName = 'CO10_RA#KT_3F_2'; // 九宫格-3.建筑总能耗图表
var nineWaterTotalName = 'CO10_RA#KT_3F_2'; // 九宫格-4.建筑总用水图表
var nineGasTotalName = 'CO10_RA#KT_3F_2'; // 九宫格-5.建筑总用气图表
var nineDataCenterName = 'CO10_RA#KT_3F_2'; // 九宫格-8.数据中心用电图表
var nineDeviceEnergyName = 'CO10_RA#KT_3F_2'; // 九宫格-9.建筑设备能耗图表

// 生成九宫格-2.建筑总能耗分项
var nineEnergyTotal =  'CO18_RA#KT_5_5,CO18_RA#KT_5_5,CO18_RA#KT_5_5,CO18_RA#KT_5_5,CO18_RA#KT_5_5,CO18_RA#KT_5_5,CO18_RA#KT_5_5' ;
var nineEnergyTotalSum =  'CO10_RA#KT_3F_2' ;
// 生成九宫格-6.建筑用电分项
var nineBuildSubentry =  'CO18_RA#KT_5_5,CO18_RA#KT_5_5,CO18_RA#KT_5_5,CO18_RA#KT_5_5,CO18_RA#KT_5_5,CO18_RA#KT_5_5,CO18_RA#KT_5_5' ;
var nineBuildSubentrySum =  'CO10_RA#KT_3F_2' ;
// 生成九宫格-7.空调系统用电分项
var nineHvacSubentry =  'CO18_RA#KT_5_5,CO18_RA#KT_5_5,CO18_RA#KT_5_5,CO18_RA#KT_5_5,CO18_RA#KT_5_5,CO18_RA#KT_5_5,CO18_RA#KT_5_5' ;
var nineHvacSubentrySum =  'CO10_RA#KT_3F_2' ;

// 生成六宫格图表
function buildEnergySexChart(title, start, end, timescales, step) {
	renderMoreCharts(buildChart.buildEnergySexEnergyTotal, sexEnergyTotalName, title, start, end, timescales, step); // 生成六宫格-1.建筑总能耗图表
	renderPieCharts(buildChart.buildEnergySexEnergySubentry, sexEnergySubentry, sexEnergySubentrySum, start, end, timescales); // 生成六宫格-2.建筑总能耗分项
	renderMoreCharts(buildChart.buildEnergySexElectricityTotal, sexElectricityTotalName, title, start, end, timescales, step); // 生成六宫格-3.建筑总能耗图表
	renderPieCharts(buildChart.buildEnergySexHvacSubentry, sexHvacSubentry, sexHvacSubentrySum, start, end, timescales); // 生成六宫格-4.空调系统用电分项
	renderMoreCharts(buildChart.buildEnergySexDataCenter, sexDataCenterName, title, start, end, timescales, step); // 生成六宫格-5.数据中心用电图表
	renderMoreCharts(buildChart.buildEnergySexDeviceEnergy, sexDeviceEnergyName, title, start, end, timescales, step); // 生成六宫格-6.建筑设备能耗图表
}

// 生成九宫格图表
function buildEnergyNineChart(title, start, end, timescales, step, catalist, timeformat) {

	renderMoreCharts(buildChart.buildEnergyNineEnergyTotal, nineEnergyTotalName, title, start, end, timescales, step, catalist, timeformat); // 生成九宫格-1.建筑总能耗图表
	renderPieCharts(buildChart.buildEnergyNineEnergySubentry, nineEnergyTotal, nineEnergyTotalSum, start, end, timescales); // 生成九宫格-2.建筑总能耗分项
	renderMoreCharts(buildChart.buildEnergyNineElectricityTotal, nineElectricityTotalName, title, start, end, timescales, step, catalist, timeformat); // 生成九宫格-3.建筑总能耗图表
	renderMoreCharts(buildChart.buildEnergyNineWaterTotal, nineWaterTotalName, title, start, end, timescales, step, catalist, timeformat); // 生成九宫格-4.建筑总用水图表
	renderMoreCharts(buildChart.buildEnergyNineGasTotal, nineGasTotalName, title, start, end, timescales, step, catalist, timeformat); // 生成九宫格-5.建筑总用气图表
	renderPieCharts(buildChart.buildEnergyNineBuildSubentry, nineBuildSubentry, nineBuildSubentrySum, start, end, timescales); // 生成九宫格-6.建筑用电分项
	renderPieCharts(buildChart.buildEnergyNineHvacSubentry, nineHvacSubentry, nineHvacSubentrySum, start, end, timescales); // 生成九宫格-7.空调系统用电分项
	renderMoreCharts(buildChart.buildEnergyNineDataCenter, nineDataCenterName, title, start, end, timescales, step, catalist, timeformat); // 生成九宫格-8.数据中心用电图表
	renderMoreCharts(buildChart.buildEnergyNineDeviceEnergy, nineDeviceEnergyName, title, start, end, timescales, step, catalist, timeformat); // 生成九宫格-9.建筑设备能耗图表
}

/**
 * 生成一个或多个曲线
 * 
 * @param {} callback 回调函数
 * @param {} pointname
 * @param {} title
 * @param {} start
 * @param {} end
 * @param {} timescales
 * @param {} step
 * @param {} clist
 * @param {} timeformat
 */
function renderMoreCharts(callback, pointname, title, start, end, timescales, step, clist, timeformat) {
	var aggregatefunction = 'sum,sum';
	var starts = [], ends = [], timescaleses = [];
	for (var i = 0; i < pointname.split(",").length; i++) {
		starts.push(start);
		ends.push(end);
		timescaleses.push(timescales);
	}
	var action = CONTEXT_PATH + "/energyManage/getDataAndCataList";
	var params = {
		pointname : pointname,
		aggregatefunction : aggregatefunction,
		timeend : ends.join(),
		timescales : timescaleses.join(),
		timestart : starts.join(),
		timeformat : timeformat ? timeformat : "HH"
	};
	$.post(action, params, function(data) {
				var datalist = data.data, ca = [];
				try {
					var ca = data.cata[0];
					if (clist && clist.length > 0) {
						ca = clist;
					}
				} catch (e) {
					console.log(e);
				}
				callback(title, ca, datalist, step);
			});
}

/**
 * 生成饼图
 * 
 * @param {} callback 回调函数
 * @param {} pointname
 * @param {} additioncontion
 * @param {} start
 * @param {} end
 * @param {} timescales
 */
function renderPieCharts(callback, pointname, additioncontion, start, end, timescales) {
	var action = CONTEXT_PATH + "/energyManage/getPieDataList";
	var params = {
		pointname : pointname,
		aggregatefunction : 'percent',
		range : 'sum',
		additioncontion : additioncontion,
		timeend : end,
		timescales : timescales,
		timestart : start
	};
	$.post(action, params, function(data) {
				callback(data.data);
			});
}