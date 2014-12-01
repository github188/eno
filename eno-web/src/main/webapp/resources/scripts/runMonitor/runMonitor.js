$(function() {

	// 变压器_下拉列表切换
	$("#etdSelect").change(function(a, b) {
		runMonitor.buildETDTemperatureChart(); // 生成变配电_温度图
	});
	
	runMonitor.buildWaterTemperatureChart(); // 生成暖通空调_冷冻水/冷却水温度图
	runMonitor.buildWaterFlowChart(); // 生成暖通空调_冷冻水/冷却水流量图
	runMonitor.buildETDChart(); // 生成变配电高压图
	runMonitor.buildETDTemperatureChart(); // 生成变配电_温度图
	runMonitor.buildWSDFrequencyChart(); // 生成1号水泵房频率图
	runMonitor.buildWSDGiveWaterChart(); // 生成水泵房给水图
	
});

var runMonitor = {
	
	// 生成暖通空调_冷冻水/冷却水温度图
	buildWaterTemperatureChart: function() {
		// 冷冻水供水温度
		var cold_give_water_data = [6.56,10.35,8.23,7.17,6.82,9.03,18.55];
		var cold_give_water_color = "#42BE99";
		// 冷冻水回水温度
		var cold_back_water_data = [20.5,20.5,20.06,20.06,18.47,17.76,14.41];
		var cold_back_water_color = "#F6D632";
		// 冷却水供水温度
		var cool_give_water_data = [23.54,23.54,23.54,23.54,23.54,23.54,23.54];
		var cool_give_water_color = "#5BC5E3";
		// 冷却水回水温度
		var cool_back_water_data = [25.99,25.99,25.99,25.99,25.99,25.99,25.99];
		var cool_back_water_color = "#F2A733";
	
		var radius = 2; // 数据点的大小
		var symbol = 'circle'; //曲线点类型："circle", "square", "diamond", "triangle","triangle-down"，默认是"circle"
		
		$('#waterTemperature').highcharts({
					chart : {
						type : 'line'
					},
					xAxis : {
						labels : {
							step : 8,
							style : {
								color : '#A2A2A2',
								fontWeight : 'bold',
								fontFamily : 'RobotoRegular'
							}
						},
						categories : ['', '', '', '', '', '', '', '']
					},
					yAxis : [{
								title : {
									text : ''
								}
							}, {
								opposite : true,
								title : {
									text : ''
								}
							}],
					 tooltip : {
						backgroundColor : '#5CC8E4', // 提示框背景颜色
						formatter : function(a) {
							return '<b>' + this.series.name + "：" + this.y + this.series.tooltipOptions.valueSuffix + '</b>';
						},
						style : {
							color : '#FFF' // 提示内容颜色
						},
						borderWidth : 0
					},
					legend : {
						shadow : true,
						layout : 'vertical',
						itemMarginTop : 5,
						verticalAlign : 'top',
						align : 'right'
					},
					series : [{
								name : '冷冻供水',
								color : cold_give_water_color,
								marker : {
									radius : radius,
									lineColor : cold_give_water_color,
									symbol : symbol
								},
								data : cold_give_water_data,
								tooltip : {
									valueSuffix : '℃'
								},
								yAxis : 0
							}, {
								name : '冷冻回水',
								color : cold_back_water_color,
								marker : {
									radius : radius,
									lineColor : cold_back_water_color,
									symbol : symbol
								},
								data : cold_back_water_data,
								tooltip : {
									valueSuffix : '%'
								},
								yAxis : 1
							}, {
								name : '冷却供水',
								color : cool_give_water_color,
								marker : {
									radius : radius,
									lineColor : cool_give_water_color,
									symbol : symbol
								},
								data : cool_give_water_data,
								tooltip : {
									valueSuffix : '℃'
								},
								yAxis : 0
							}, {
								name : '冷却回水',
								color : cool_back_water_color,
								marker : {
									radius : radius,
									lineColor : cool_back_water_color,
									symbol : symbol
								},
								data : cool_back_water_data,
								tooltip : {
									valueSuffix : '%'
								},
								yAxis : 1
							}]
				});
	},
	// 生成暖通空调_冷冻水/冷却水流量图
	buildWaterFlowChart: function() {
		// 冷冻水流量
		var cold_give_water_data = [36.56,50.35,18.23,7.17,6.82,39.03,88.55];
		var cold_give_water_color = "#5BC8E9";
		// 冷却水流量
		var cool_give_water_data = [43.54,23.54,43.54,53.54,63.54,23.54,73.54];
		var cool_give_water_color = "#FAAB34";
		var radius = 2; // 数据点的大小
		var symbol = 'circle'; //曲线点类型："circle", "square", "diamond", "triangle","triangle-down"，默认是"circle"
		
		$('#waterFlow').highcharts({
			chart : {
				type : 'line'
			},
			xAxis : {
				labels : {
					step : 8,
					style : {
						color : '#A2A2A2',
						fontWeight : 'bold',
						fontFamily : 'RobotoRegular'
					}
				},
				categories : ['', '', '', '', '', '', '', '']
			},
			yAxis : {
			},
			tooltip : {
				backgroundColor : '#5CC8E4', // 提示框背景颜色
				formatter : function() {
					return '<b>' + this.series.name + "：" + this.y + '</b>'
				},
				style : {
					color : '#FFF' // 提示内容颜色
				},
				borderWidth : 0
			},
			legend : {
				shadow : true,
				layout : 'vertical',
				itemMarginTop : 2,
				verticalAlign : 'top',
				align : 'right'
			},
			series : [{
						name : '冷冻水',
						color : cold_give_water_color,
						marker : {
							radius : radius,
							lineColor : cold_give_water_color,
							symbol : symbol
						},
						data : cold_give_water_data
					}, {
						name : '冷却水',
						color : cool_give_water_color,
						marker : {
							radius : radius,
							lineColor : cool_give_water_color,
							symbol : symbol
						},
						data : cool_give_water_data
					}]
		});
	},
	// 生成变配电高压图
	buildETDChart: function() {
	
		var dataList1 = [],dataList2 = [],dataList3 = []; // 数据列表
		for (var i = 0; i < 24; i++) {
			dataList1.push(Math.floor(Math.random() * 100));
			dataList2.push(Math.floor(Math.random() * 100));
			dataList3.push(Math.floor(Math.random() * 50));
		}
	
		var szglColor = "#F3D827"; // 视在功率颜色
		var yyglColor = "#60C5D9"; // 有用功率颜色
		var wyglColor = "#FAAB34"; // 无用功率颜色
		var radius = 2;
		var symbol = 'circle'; //曲线点类型："circle", "square", "diamond", "triangle","triangle-down"，默认是"circle"
		
		$('#highPressure').highcharts({
				chart : {
					type : 'line',
					marginBottom : 15
				},
				xAxis : {
					labels : {
						step : 300 // x轴显示的间隔
					},
					categories : [ '', '', '', '', '', '', '', '']
				},
				yAxis : {
				},
				tooltip : {
					backgroundColor : '#5CC8E4', // 提示框背景颜色
					formatter : function() {
						return '<b>' + this.series.name + "：" + this.y + '</b>'
					},
					style : {
						color : '#FFF' // 提示内容颜色
					},
					borderWidth : 0
				},
				legend: {
					shadow : true,
					layout : 'vertical',
					itemMarginTop : 5,
					verticalAlign : 'top',
					align : 'right'
				},
				series : [
						{
							name : '视在功率',
							color : szglColor,
							marker : {
								radius : radius,
								lineColor : szglColor,
								symbol : symbol
							},
							data : dataList1
						},
						{
							name : '有用功率',
							color : yyglColor,
							marker : {
								radius : radius,
								lineColor : yyglColor,
								symbol : symbol
							},
							data : dataList2
						},{
							name : '无用功率',
							color : wyglColor,
							marker : {
								radius : radius,
								lineColor : wyglColor,
								symbol : symbol
							},
							data : dataList3
						}]
			});
	},
	// 生成变配电_温度图
	buildETDTemperatureChart: function() {
		
		var dataList1 = []; // 数据列表
		for (var i = 0; i < 24; i++) {
			dataList1.push(Math.floor(Math.random() * 10));
		}

		var color = "#FAAB34"; // 颜色
		var radius = 2;
		var symbol = 'circle'; // 曲线点类型："circle", "square", "diamond", "triangle","triangle-down"，默认是"circle"

		$('#etdTemperature').highcharts({
					chart : {
						type : 'line',
						marginBottom : 15
					},
					xAxis : {
						labels : {
							step : 300
							// x轴显示的间隔
						},
						categories : ['', '', '', '', '', '', '', '']
					},
					yAxis : {},
					tooltip : {
						backgroundColor : '#5CC8E4', // 提示框背景颜色
						formatter : function() {
							return '<b>' + this.series.name + "：" + this.y + '℃' + '</b>'
						},
						style : {
							color : '#FFF' // 提示内容颜色
						},
						borderWidth : 0
					},
					legend : {
						shadow : true,
						layout : 'vertical',
//						itemMarginTop : 7,
						verticalAlign : 'top',
						align : 'right'
					},
					series : [{
								name : '温度',
								color : color,
								marker : {
									radius : radius,
									lineColor : color,
									symbol : symbol
								},
								data : dataList1
							}]
				});
	},
	// 生成1号水泵房频率图
	buildWSDFrequencyChart: function() {
		var dataList1 = []; // 数据列表
		for (var i = 0; i < 24; i++) {
			dataList1.push(Math.floor(Math.random() * 10));
		}

		var color = "#63C6E6"; // 颜色
		var radius = 2;
		var symbol = 'circle'; // 曲线点类型："circle", "square", "diamond", "triangle","triangle-down"，默认是"circle"

		$('#wsdFrequency').highcharts({
					chart : {
						type : 'line',
						marginBottom : 15
					},
					xAxis : {
						labels : {
							step : 300
							// x轴显示的间隔
						},
						categories : ['', '', '', '', '', '', '', '']
					},
					yAxis : {},
					tooltip : {
						backgroundColor : '#5CC8E4', // 提示框背景颜色
						formatter : function() {
							return '<b>' + this.series.name + "：" + this.y + '℃' + '</b>'
						},
						style : {
							color : '#FFF' // 提示内容颜色
						},
						borderWidth : 0
					},
					legend : {
						shadow : true,
						layout : 'vertical',
						itemMarginTop : 7,
						verticalAlign : 'top',
						align : 'right'
					},
					series : [{
								name : '频率',
								color : color,
								marker : {
									radius : radius,
									lineColor : color,
									symbol : symbol
								},
								data : dataList1
							}]
				});
	},
	// 生成水泵房给水图
	buildWSDGiveWaterChart: function() {
		var dataList1 = []; // 数据列表
		for (var i = 0; i < 24; i++) {
			dataList1.push(Math.floor(Math.random() * 10));
		}

		var color = "#FAAB34"; // 颜色
		var radius = 2;
		var symbol = 'circle'; // 曲线点类型："circle", "square", "diamond", "triangle","triangle-down"，默认是"circle"

		$('#giveWater').highcharts({
					chart : {
						type : 'line',
						marginBottom : 15
					},
					xAxis : {
						labels : {
							step : 300
							// x轴显示的间隔
						},
						categories : ['', '', '', '', '', '', '', '']
					},
					yAxis : {},
					tooltip : {
						backgroundColor : '#5CC8E4', // 提示框背景颜色
						formatter : function() {
							return '<b>' + this.series.name + "：" + this.y + '℃' + '</b>'
						},
						style : {
							color : '#FFF' // 提示内容颜色
						},
						borderWidth : 0
					},
					legend : {
						shadow : true,
						layout : 'vertical',
						itemMarginTop : 7,
						verticalAlign : 'top',
						align : 'right'
					},
					series : [{
								name : '压力',
								color : color,
								marker : {
									radius : radius,
									lineColor : color,
									symbol : symbol
								},
								data : dataList1
							}]
				});
	}

};
