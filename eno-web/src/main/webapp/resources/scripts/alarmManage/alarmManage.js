$(function() {

			// 报警趋势_选择子系统_下拉列表切换
			$("#choosechildsystem").change(function(a, b) {
						alarmManage.buildAlarmTrendChart(); // 生成报警趋势图
					});

			// 生成报警趋势图
			alarmManage.buildAlarmTrendChart(); 

			
		});

var t_all = 0;
var timer_all = setInterval("alarmManage.setAlarmsAllTimes()", 20); // 动态设置报警效果
var t_red = 0;
var timer_red = setInterval("alarmManage.setAlarmsRedTimes()", 20); // 动态设置报警效果
var t_blue = 0;
var timer_blue = setInterval("alarmManage.setAlarmsBlueTimes()", 20); // 动态设置报警效果

var alarmManage = {

	// 动态设置报警总数
	setAlarmsAllTimes: function() {
		var num = t_all + 1;
		if (num > 100) {
			clearInterval(timer_all);
		} else {
			$(".alarams_all").css("width", num + "%");
		}
		t_all = num;
	},
	// 动态设置已处理报警数
	setAlarmsRedTimes: function() {
		var num = t_red + 1;
		if (num > 30) {
			clearInterval(timer_red);
		} else {
			$(".alarams_red").css("width", num + "%");
		}
		t_red = num;
	},
	// 动态设置未处理报警数
	setAlarmsBlueTimes: function() {
		var num = t_blue + 1;
		if (num > 70) {
			clearInterval(timer_blue);
		} else {
			$(".alarams_blue").css("width", num + "%");
		}
		t_blue = num;
	},
	// 生成报警趋势图
	buildAlarmTrendChart : function() {
		var dataList = [], catalist = []; // 数据列表、X周列表
		for (var i = 0; i < 24; i++) {
			dataList.push(Math.floor(Math.random() * 10));
			catalist.push(i + ":00");
		}

		var color = "#F8D733"; // 颜色
		var radius = 3;
		var symbol = 'circle'; // 曲线点类型

		$('#alarmTrendChart').highcharts({
			chart : {
				type : 'line',
				marginRight : '0'
			},
			xAxis : {
				labels : {
					style : {
						color : '#A8A8A9', // 坐标颜色
						fontWeight : 'bold',
						fontSize : '14px',
						fontFamily : 'RobotoRegular'
					},
					step : 4 // x轴显示的间隔
				},
				categories : catalist
			},
			yAxis : {
				labels : {
					style : {
						color : '#A8A8A9', // 坐标颜色
						fontWeight : 'normal',
						fontSize : '14px',
						fontFamily : 'RobotoRegular'
					}
				}
			},
			tooltip : {
				crosshairs: true,
                shared: true,
				backgroundColor : '#D19A06', // 提示框背景颜色
				headerFormat: '<table>',
                pointFormat: '<tr><td style="color: #FFF; font-size:14px">{point.x}:00</td>' +
                    '<td style="color: #FFF; font-size: 36px; padding-left: 50px; "><b>{point.y}</b></td></tr>',
                footerFormat: '</table>',
                useHTML: true
			},
			legend : {
				enabled: false
			},
			series : [{
						name : '报警数',
						color : color,
						marker : {
							radius : radius,
							lineColor : color,
							symbol : symbol,
							lineWidth : 0.5
						},
						data : dataList
					}]
		});
	}

};
