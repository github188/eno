// 根据给出的时间s往后推d天，例如：s=2013-10-13，d=2，则返回2013-10-15
function getTimeByDays(s, d) {
	var Nowdate = new Date(Date.parse(s.replace(/-/g, '/')));
	var WeekFirstDay = new Date(Nowdate.getTime() + (d * 86400000));
	var day = (WeekFirstDay.getDate() + "");
	day = day.length == 1 ? ("0" + day) : day;
	var month = ((WeekFirstDay.getMonth() + 1) + "");
	month = month.length == 1 ? ("0" + month) : month;
	t = [ WeekFirstDay.getFullYear(), month, day ];
	return t.join('-');
}

// 生成图表
function buildSingleCharts(modelname, charttype, color, yTitle, seriesname) {
	var type = 'day', tfrom = _now_tfrom, tto = getTimeByDays(_now_tfrom, 1), dayFormat = '&beforeFormat=yyyy-MM-dd HH:mm:ss&afterFormat=HH:mm';
	// 万达现场环境
	var name = modelname, id = $("#runningSel").val(), ispd = '';
	// 公司测试环境
//	if (model != '新风机组') { // 空调机组
//		name = 'LJYK_DDC_2_CHWP_CCHWR_T';
//	} else {
//		name = 'LJYK_DDC_2_CWP_CWR_T';
//	}
//	ipaddress = "192.168.2.35", port = "8087", tfrom = '2013-06-05', tto = '2013-06-06', id = 'chiller', ispd = '';
	var url = CONTEXT_PATH + '/Chart/GetChartData?name=' + name + '&id=' + id
			+ '&type=' + type + '&tfrom=' + tfrom + '&tto=' + tto + '&ispd='
			+ ispd + '&attribute=' + '&ipaddress=' + ipaddress + '&port='
			+ port + dayFormat;
	console.log("url---" + url);
	$.ajax({
		type : "POST",
		url : url,
		// async : false, //同步
		success : function(result) {
			try {
				var datalist = result.datalist; // 此处为数据列表，数组形式，如：datalist[0]即可取到第一组数据

				$('#' + modelname + '_chart').highcharts({
					chart : {
						type : charttype,
						marginTop : 25,
						marginBottom : 25
					},
					title : {
						text : '',
						x : -20
					// center
					},
					subtitle : {
						text : '',
						x : -20
					},
					xAxis : {
						tickColor : '#FFFFFF', // 刻度线的颜色
						labels : {
							step : 12 * 4
						},
						categories : result.catalist
					},
					yAxis : {
						title : {
							text : yTitle
						},
						plotLines : [ {
							value : 0,
							width : 1,
							color : '#808080'
						} ]
					},
					legend : {
						enabled : false
					},
					series : [ {
						name : seriesname,
						color : color,
						marker : {
							lineWidth : parseInt(2),
							lineColor : color,
							// fillColor : fillColorList[k],
							radius : 1, // 曲线点半径，默认是4
							symbol : 'circle' // 曲线点类型",
						},
						tooltip : {
							valueSuffix : ''
						},
						data : datalist[0]
					} ]
				});

			} catch (e) {
				console.log('buildSingleCharts---error--' + e);
			}
		},
		error : function(result) {
			console.log('error---');
		}
	});
}

// 生成图表
function buildCharts() {
	if (model != '新风机组') { // 空调机组
		buildSingleCharts("t_ra", "line", "#5494DE", "", "回风温度"); // 回风温度
		buildSingleCharts("rh_ra", "line", "#910000", "", "回风湿度"); // 回风湿度
		buildSingleCharts("t_sa", "line", "#3BC379", "", "送风温度 ");// 送风温度
		buildSingleCharts("co2_ra", "line", "#8BD8FF", "", "二氧化碳浓度"); // 二氧化碳浓度
	} else {
		buildSingleCharts("status_sf", "line", "#5494DE", "", "新风机机组风机启停状态"); // 新风机机组风机启停状态
		buildSingleCharts("frequency_sf", "line", "#910000", "", "转速"); // 转速
		buildSingleCharts("t_sa", "line", "#3BC379", "", "送风温度 ");// 送风温度
		buildSingleCharts("t_sa_sp", "line", "#8BD8FF", "", "送风温度设定值"); // 送风温度设定值
	}
}

$(function() {
	// 将设备信息追加到下拉列表中
	var devicesList = [ 'X_B2_1_WD', 'X_3_01_YL', 'X_3_02_YL', 'X_3_03_YL',
			'X_3_04_YL', 'X_3_01_BJ', 'X_3_04_BJ', 'X_3_06_BJ', 'X_3_07_BJ',
			'X_3_08_BJ', 'X_3_11_BJ', 'X_3_13_BJ', 'X_3_14_BJ', 'X_4_01_YL',
			'X_4_02_BJ', 'X_4_03_BJ', 'X_4_05_BJ', 'X_4_09_BJ', 'X_4_10_BJ',
			'X_4_12_BJ', 'X_5_01_YL', 'X_5_02_YL', 'X_6_01_WD', 'X_6_02_WD' ];

	if (model != '新风机组') { // 空调机组
		devicesList = [ 'K_B1_01_GM', 'K_B1_02_GM', 'K_B1_03_GM', 'K_B1_04_GM',
				'K_1_01_YL', 'K_1_02_YL', 'K_1_03_YL', 'K_1_04_YL',
				'K_2_01_YL', 'K_2_02_YL', 'K_2_03_YL', 'K_2_04_YL',
				'K_4_01_YL_1#', 'K_4_01_YL_2#', 'K_5_01_YL_1#', 'K_5_01_YL_2#',
				'K_5_02_YL', 'K_5_03_YL', 'K_5_04_YL ', 'K_5_05_YL',
				'K_5_06_YL', 'K_5_07_YL', 'K_5_08_YL', 'K_5_09_YL',
				'K_5_10_YL', 'K_5_11_YL', 'K_6_01_YL' ];
	}
	for ( var i = 0; i < devicesList.length; i++) {
		$("#runningSel").append(
				"<option value='" + devicesList[i] + "' "
						+ ((i == 1) ? "selected='selected'" : "") + ">"
						+ devicesList[i] + "</option>");
	}

	buildCharts(); // building charts
});