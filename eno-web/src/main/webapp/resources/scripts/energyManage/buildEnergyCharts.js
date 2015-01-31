var buildChart = {

	// 生成六宫格-1.建筑总能耗图表
	buildEnergySexEnergyTotal : function(title, catalist, datalist, step) {

		// 实际能耗
		var columnColor = "#3DBA90";
		// 室外温度
		var lineColor = "#FFB400";
		var radius = 2; // 数据点的大小

		$('#energy_sex_energytotal').highcharts({
					chart : {
						backgroundColor : '#343434',
						marginBottom : 35,
						marginLeft : 50
					},
					title : {
						align : 'left',
						style : {
							color : '#FFFFFF',
							fontFamily : '微软雅黑'
						},
						x : 10,
						text : title
					},
					legend : {
						verticalAlign : 'top',
						x : 150,
						y : -7,
						itemStyle : {
							color : '#C7C7C7',
							fontWeight : 'normal',
							fontSize : '16px',
							fontFamily : '微软雅黑'
						},
						borderWidth : 0
					},
					xAxis : {
						tickColor : "#343434",
						categories : catalist,
						labels : {
							step : step,
							style : {
								color : '#C7C7C7',
								fontSize : '16px',
								fontFamily : '微软雅黑'
							}
						}
					},
					yAxis : [{ // Primary yAxis
						labels : {
							style : {
								color : '#C7C7C7',
								fontWeight : 'normal',
								fontSize : '14px'
							}
						}
					}, {	// Secondary yAxis
								labels : {
									style : {
										color : '#C7C7C7',
										fontWeight : 'normal',
										fontSize : '14px'
									}
								},
								opposite : true
							}],
					series : [{
								type : 'column',
								color : columnColor,
								name : '实际能耗(kWh)',
								data : datalist[0]
							}, {
								type : 'line',
								name : '室外温度(℃)',
								yAxis : 1,
								data : datalist[1],
								color : lineColor,
								marker : {
									lineWidth : 2,
									radius : radius,
									lineColor : lineColor,
									fillColor : lineColor
								}
							}]
				});
	},

	// 生成六宫格-2.建筑总能耗分项
	buildEnergySexEnergySubentry : function(datalist) {
		var catalist = [ '空调', '照明', '数据中心', '电梯', '给排水', '消防', '其他' ];
		var last = [];
		for (var i = 0; i < catalist.length; i++) {
			var temp = [], data = parseFloat(datalist[i]);
			temp.push(catalist[i] + data + "%");
			temp.push(data);
			last.push(temp);
		}
		$('#energy_sex_energysubentry').highcharts({
			chart : {
				backgroundColor : '#343434',
				marginBottom : 35,
				marginLeft : -10,
				plotBackgroundColor : null,
				plotBorderWidth : null,
				plotShadow : false
			},
			title : {
				align : 'right',
				style : {
					color : '#FFFFFF',
					fontFamily : '微软雅黑'
				},
				x : -45,
				y : 50,
				text : '2014 - 8 - 10 ~ 2014 - 8 -16'
			},
			legend : {
				verticalAlign : 'middle',
				align : 'right',
				itemMarginTop : 6,
				width : 300,
				itemWidth : 150,
				itemStyle : {
					color : '#FFF',
					fontWeight : 'normal',
					fontSize : '14px',
					fontFamily : '微软雅黑'
				},
				borderWidth : 0
			},
			plotOptions : {
				pie : {
					allowPointSelect : true,
					cursor : 'pointer',
					dataLabels : {
						enabled : false
					},
					showInLegend : true
				}
			},
			colors : ['#3DBA90', '#A7C91D', '#FFD600', '#FF9326', '#FB605E',
					'#FFB59C', '#74DCFF', '#00B7D9'],
			tooltip : {
				pointFormat : ''
			},
			series : [{
				type : 'pie',
				innerSize : '100%',
				size : '150%',
				// 空调,照明, 数据中心, 电梯, 给排水,消防,其他
				data : last
			}]
		});
	},

	// 生成六宫格-3.建筑总能耗图表
	buildEnergySexElectricityTotal : function(title, catalist, datalist, step) {
		// 实际用电
		var columnColor = "#3DBA90";
		// 室外温度
		var lineColor = "#FFB400";
		var radius = 2; // 数据点的大小

		$('#energy_sex_electricitytotal').highcharts({
					chart : {
						backgroundColor : '#343434',
						marginBottom : 35,
						marginLeft : 50
					},
					title : {
						align : 'left',
						style : {
							color : '#FFFFFF',
							fontFamily : '微软雅黑'
						},
						x : 10,
						text : title
					},
					legend : {
						verticalAlign : 'top',
						x : 150,
						y : -7,
						itemStyle : {
							color : '#C7C7C7',
							fontWeight : 'normal',
							fontSize : '16px',
							fontFamily : '微软雅黑'
						},
						borderWidth : 0
					},
					xAxis : {
						tickColor : "#343434",
						categories : catalist,
						labels : {
							step : step,
							style : {
								color : '#C7C7C7',
								fontSize : '16px',
								fontFamily : '微软雅黑'
							}
						}
					},
					yAxis : [{ // Primary yAxis
						labels : {
							style : {
								color : '#C7C7C7',
								fontWeight : 'normal',
								fontSize : '14px'
							}
						}
					}, {	// Secondary yAxis
								labels : {
									style : {
										color : '#C7C7C7',
										fontWeight : 'normal',
										fontSize : '14px'
									}
								},
								opposite : true
							}],
					series : [{
								type : 'line',
								color : columnColor,
								name : '实际用电(kWh)',
								data : datalist[0]
							}, {
								type : 'line',
								name : '室外温度(℃)',
								yAxis : 1,
								data : datalist[1],
								color : lineColor,
								marker : {
									lineWidth : 2,
									radius : radius,
									lineColor : lineColor,
									fillColor : lineColor
								}
							}]
				});
	},

	// 生成六宫格-4.空调系统用电分项
	buildEnergySexHvacSubentry : function(datalist) {
		var catalist = [ '冷机', '空调水泵', '冷却塔', '空调箱', 'ACU用电', '其他' ];
		var last = [];
		for (var i = 0; i < catalist.length; i++) {
			var temp = [], data = parseFloat(datalist[i]);
			temp.push(catalist[i] + data + "%");
			temp.push(data);
			last.push(temp);
		}
		$('#energy_sex_hvacsubentry').highcharts({
			chart : {
				backgroundColor : '#343434',
				marginBottom : 35,
				marginLeft : -10,
				plotBackgroundColor : null,
				plotBorderWidth : null,
				plotShadow : false
			},
			title : {
				align : 'right',
				style : {
					color : '#FFFFFF',
					fontFamily : '微软雅黑'
				},
				x : -45,
				y : 50,
				text : '2014 - 8 - 10 ~ 2014 - 8 -16'
			},
			legend : {
				verticalAlign : 'middle',
				align : 'right',
				itemMarginTop : 6,
				width : 300,
				itemWidth : 150,
				itemStyle : {
					color : '#FFF',
					fontWeight : 'normal',
					fontSize : '14px',
					fontFamily : '微软雅黑'
				},
				borderWidth : 0
			},
			plotOptions : {
				pie : {
					allowPointSelect : true,
					cursor : 'pointer',
					dataLabels : {
						enabled : false
					},
					showInLegend : true
				}
			},
			colors : ['#3DBA90', '#A7C91D', '#FFD600', '#FF9326', '#FB605E',
					'#FFB59C', '#74DCFF', '#00B7D9'],
			tooltip : {
				pointFormat : ''
			},
			series : [{
				type : 'pie',
				innerSize : '100%',
				size : '150%',
				data : last
			}]
		});
	},

	// 生成六宫格-5.数据中心用电图表
	buildEnergySexDataCenter : function(title, catalist, datalist, step) {
		// 实际能耗
		var columnColor = "#3DBA90";
		// 室外温度
		var lineColor = "#FFB400";
		var radius = 2; // 数据点的大小

		$('#energy_sex_datacenter').highcharts({
					chart : {
						backgroundColor : '#343434',
						marginBottom : 35,
						marginLeft : 50
					},
					title : {
						align : 'left',
						style : {
							color : '#FFFFFF',
							fontFamily : '微软雅黑'
						},
						x : 10,
						text : title
					},
					legend : {
						verticalAlign : 'top',
						x : 150,
						y : -7,
						itemStyle : {
							color : '#C7C7C7',
							fontWeight : 'normal',
							fontSize : '16px',
							fontFamily : '微软雅黑'
						},
						borderWidth : 0
					},
					xAxis : {
						tickColor : "#343434",
						categories : catalist,
						labels : {
							staggerLines : 1,
							step : step,
							style : {
								color : '#C7C7C7',
								fontSize : '16px',
								fontFamily : '微软雅黑'
							}
						}
					},
					yAxis : [{ // Primary yAxis
						labels : {
							style : {
								color : '#C7C7C7',
								fontWeight : 'normal',
								fontSize : '14px'
							}
						}
					}, {	// Secondary yAxis
								labels : {
									style : {
										color : '#C7C7C7',
										fontWeight : 'normal',
										fontSize : '14px'
									}
								},
								opposite : true
							}],
					series : [{
								type : 'area',
								color : columnColor,
								name : '实际能耗(kWh)',
								data : datalist[0],
								marker : {
									lineWidth : 1,
									radius : radius,
									lineColor : columnColor
								}
							}, {
								type : 'line',
								name : '室外温度(℃)',
								yAxis : 1,
								data : datalist[1],
								color : lineColor,
								marker : {
									lineWidth : 2,
									radius : radius,
									lineColor : lineColor,
									fillColor : lineColor
								}
							}]
				});
	},

	// 生成六宫格-6.建筑设备能耗图表
	buildEnergySexDeviceEnergy : function(title, catalist, datalist, step) {
		// 实际能耗
		var columnColor = "#3DBA90";
		// 室外温度
		var lineColor = "#FFB400";
		var radius = 2; // 数据点的大小

		$('#energy_sex_deviceenergy').highcharts({
					chart : {
						backgroundColor : '#343434',
						marginBottom : 35,
						marginLeft : 50
					},
					title : {
						align : 'left',
						style : {
							color : '#FFFFFF',
							fontFamily : '微软雅黑'
						},
						x : 10,
						text : title
					},
					legend : {
						verticalAlign : 'top',
						x : 150,
						y : -7,
						itemStyle : {
							color : '#C7C7C7',
							fontWeight : 'normal',
							fontSize : '16px',
							fontFamily : '微软雅黑'
						},
						borderWidth : 0
					},
					xAxis : {
						tickColor : "#343434",
						categories : catalist,
						labels : {
							staggerLines : 1,
							step : step,
							style : {
								color : '#C7C7C7',
								fontSize : '16px',
								fontFamily : '微软雅黑'
							}
						}
					},
					yAxis : [{ // Primary yAxis
						labels : {
							style : {
								color : '#C7C7C7',
								fontWeight : 'normal',
								fontSize : '14px'
							}
						}
					}, {	// Secondary yAxis
								labels : {
									style : {
										color : '#C7C7C7',
										fontWeight : 'normal',
										fontSize : '14px'
									}
								},
								opposite : true
							}],
					series : [{
								type : 'column',
								color : columnColor,
								name : '实际能耗(kWh)',
								data : datalist[0]
							}, {
								type : 'line',
								name : '室外温度(℃)',
								yAxis : 1,
								data : datalist[1],
								color : lineColor,
								marker : {
									lineWidth : 2,
									radius : radius,
									lineColor : lineColor,
									fillColor : lineColor
								}
							}]
				});
	},

	// 生成九宫格-1.建筑总能耗图表
	buildEnergyNineEnergyTotal : function(title, catalist, datalist, step) {
		var columnColor = "#00C28E";
		var radius = 2; // 数据点的大小

		$('#energy_nine_energytotal').highcharts({
					chart : {
						backgroundColor : '#343434',
						marginBottom : 35,
						marginLeft : 50
					},
					title : {
						align : 'left',
						style : {
							color : '#9fa0a2',
							fontSize : '10px'
							// , fontFamily: '微软雅黑'
						},
						x : 0,
						text : '(kWh)'
					},
					legend : {
						enabled : false
					},
					xAxis : {
						tickColor : "#343434",
						categories : catalist,
						labels : {
							step : step,
							style : {
								color : '#C7C7C7',
								fontSize : '16px',
								fontFamily : '微软雅黑'
							}
						}
					},
					yAxis : {
						labels : {
							style : {
								color : '#C7C7C7',
								fontWeight : 'normal',
								fontSize : '14px'
							}
						}
					},
					series : [{
								type : 'column',
								color : columnColor,
								name : '实际能耗(kWh)',
								data : datalist[0]
							}]
				});
	},

	// 生成九宫格-2.建筑总能耗分项
	buildEnergyNineEnergySubentry : function(datalist) {

		var catalist = [ '空调', '照明', '数据中心', '电梯', '给排水', '消防', '其他' ];
		var last = [];
		for (var i = 0; i < catalist.length; i++) {
			var temp = [], data = parseFloat(datalist[i]);
			temp.push(catalist[i] + data + "%");
			temp.push(data);
			last.push(temp);
		}
		
		$('#energy_nine_energysubentry').highcharts({
			chart : {
				backgroundColor : '#343434',
				marginBottom : 25,
				marginTop : 20,
				plotBackgroundColor : null,
				plotBorderWidth : null,
				plotShadow : false
			},
			legend : {
				verticalAlign : 'middle',
				align : 'right',
				itemMarginTop : 1,
				width : 300,
				itemWidth : 150,
				itemStyle : {
					color : '#FFF',
					fontWeight : 'normal',
					fontFamily : '微软雅黑'
				},
				itemHeight : 240,
				borderWidth : 0
			},
			plotOptions : {
				pie : {
					allowPointSelect : true,
					cursor : 'pointer',
					dataLabels : {
						enabled : false
					},
					showInLegend : true
				}
			},
			colors : ['#3DBA90', '#A7C91D', '#FFD600', '#FF9326', '#FB605E',
					'#FFB59C', '#74DCFF', '#00B7D9'],
			tooltip : {
				pointFormat : ''
			},
			series : [{
				type : 'pie',
				innerSize : '100%',
				size : '160%', // 空调,照明, 数据中心, 电梯, 给排水,消防,其他
				data : last
			}]
		});
	},

	// 生成九宫格-3.建筑总用电图表
	buildEnergyNineElectricityTotal : function(title, catalist, datalist, step) {

		var columnColor = "#00C28E";
		var radius = 2; // 数据点的大小

		$('#energy_nine_electricitytotal').highcharts({
					chart : {
						backgroundColor : '#343434',
						marginBottom : 35,
						marginLeft : 50
					},
					title : {
						align : 'left',
						style : {
							color : '#9fa0a2',
							fontSize : '10px',
							fontFamily : '微软雅黑'
						},
						x : 0,
						text : '(kWh)'
					},
					legend : {
						enabled : false
					},
					xAxis : {
						tickColor : "#343434",
						categories : catalist,
						labels : {
							step : step,
							style : {
								color : '#C7C7C7',
								fontSize : '16px',
								fontFamily : '微软雅黑'
							}
						}
					},
					yAxis : {
						labels : {
							style : {
								color : '#C7C7C7',
								fontWeight : 'normal',
								fontSize : '14px'
							}
						}
					},
					series : [{
								type : 'line',
								color : columnColor,
								name : '实际用电(kWh)',
								data : datalist[0]
							}]
				});
	},

	// 生成九宫格-4.建筑总用水图表
	buildEnergyNineWaterTotal : function(title, catalist, datalist, step) {

		var columnColor = "#5CC8E4";
		var radius = 2; // 数据点的大小

		$('#energy_nine_watertotal').highcharts({
					chart : {
						backgroundColor : '#343434',
						marginBottom : 35,
						marginLeft : 50
					},
					title : {
						align : 'left',
						style : {
							color : '#9fa0a2',
							fontSize : '10px'
							// , fontFamily: '微软雅黑'
						},
						x : 0,
						text : '(kWh)'
					},
					legend : {
						enabled : false
					},
					xAxis : {
						tickColor : "#343434",
						categories : catalist,
						labels : {
							step : step,
							style : {
								color : '#C7C7C7',
								fontSize : '16px',
								fontFamily : '微软雅黑'
							}
						}
					},
					yAxis : {
						labels : {
							style : {
								color : '#C7C7C7',
								fontWeight : 'normal',
								fontSize : '14px'
							}
						}
					},
					series : [{
								type : 'column',
								color : columnColor,
								name : '实际能耗(kWh)',
								data : datalist[0]
							}]
				});
	},

	// 生成九宫格-5.建筑总用气图表
	buildEnergyNineGasTotal : function(title, catalist, datalist, step) {

		var columnColor = "#5CC9DE";
		var radius = 2; // 数据点的大小

		$('#energy_nine_gastotal').highcharts({
					chart : {
						backgroundColor : '#343434',
						marginBottom : 35,
						marginLeft : 50
					},
					title : {
						align : 'left',
						style : {
							color : '#9fa0a2',
							fontSize : '10px'
							// , fontFamily: '微软雅黑'
						},
						x : 0,
						text : '(kWh)'
					},
					legend : {
						enabled : false
					},
					xAxis : {
						tickColor : "#343434",
						categories : catalist,
						labels : {
							step : step,
							style : {
								color : '#C7C7C7',
								fontSize : '16px',
								fontFamily : '微软雅黑'
							}
						}
					},
					yAxis : {
						labels : {
							style : {
								color : '#C7C7C7',
								fontWeight : 'normal',
								fontSize : '14px'
							}
						}
					},
					series : [{
								type : 'area',
								color : columnColor,
								name : '总用气(kWh)',
								data : datalist[0]
							}]
				});
	},

	// 生成九宫格-6.建筑用电分项
	buildEnergyNineBuildSubentry : function(datalist) {

		var catalist = [ '空调', '照明', '数据中心', '电梯', '给排水', '消防', '其他' ];
		var last = [];
		for (var i = 0; i < catalist.length; i++) {
			var temp = [], data = parseFloat(datalist[i]);
			temp.push(catalist[i] + data + "%");
			temp.push(data);
			last.push(temp);
		}
		
		$('#energy_nine_buildsubentry').highcharts({
			chart : {
				backgroundColor : '#343434',
				marginBottom : 25,
				marginTop : 20,
				plotBackgroundColor : null,
				plotBorderWidth : null,
				plotShadow : false
			},
			legend : {
				verticalAlign : 'middle',
				align : 'right',
				itemMarginTop : 1,
				width : 300,
				itemWidth : 150,
				itemStyle : {
					color : '#FFF',
					fontWeight : 'normal',
					fontFamily : '微软雅黑'
				},
				itemHeight : 240,
				borderWidth : 0
			},
			plotOptions : {
				pie : {
					allowPointSelect : true,
					cursor : 'pointer',
					dataLabels : {
						enabled : false
					},
					showInLegend : true
				}
			},
			colors : ['#3DBA90', '#A7C91D', '#FFD600', '#FF9326', '#FB605E',
					'#FFB59C', '#74DCFF', '#00B7D9'],
			tooltip : {
				pointFormat : ''
			},
			series : [{
				type : 'pie',
				innerSize : '100%',
				size : '160%', // 空调,照明, 数据中心, 电梯, 给排水,消防,其他
				data : last
			}]
		});
	},

	// 生成九宫格-7.空调系统用电分项
	buildEnergyNineHvacSubentry : function(datalist) {

		var catalist = [ '空调', '照明', '数据中心', '电梯', '给排水', '消防', '其他' ];
		var last = [];
		for (var i = 0; i < catalist.length; i++) {
			var temp = [], data = parseFloat(datalist[i]);
			temp.push(catalist[i] + data + "%");
			temp.push(data);
			last.push(temp);
		}
		
		$('#energy_nine_hvacsubentry').highcharts({
			chart : {
				backgroundColor : '#343434',
				marginBottom : 25,
				marginTop : 20,
				plotBackgroundColor : null,
				plotBorderWidth : null,
				plotShadow : false
			},
			legend : {
				verticalAlign : 'middle',
				align : 'right',
				itemMarginTop : 1,
				width : 300,
				itemWidth : 150,
				itemStyle : {
					color : '#FFF',
					fontWeight : 'normal',
					fontFamily : '微软雅黑'
				},
				itemHeight : 240,
				borderWidth : 0
			},
			plotOptions : {
				pie : {
					allowPointSelect : true,
					cursor : 'pointer',
					dataLabels : {
						enabled : false
					},
					showInLegend : true
				}
			},
			colors : ['#3DBA90', '#A7C91D', '#FFD600', '#FF9326', '#FB605E',
					'#FFB59C', '#74DCFF', '#00B7D9'],
			tooltip : {
				pointFormat : ''
			},
			series : [{
				type : 'pie',
				innerSize : '100%',
				size : '160%', // 空调,照明, 数据中心, 电梯, 给排水,消防,其他
				data : last
//				[['空调25%', 25.0], ['照明25%', 20], ['数据中心25%', 15],
//						['电梯25%', 11], ['给排水25%', 9], ['消防25%', 18],
//						['其他25%', 12]]
			}]
		});
	},

	// 生成九宫格-8.数据中心用电图表
	buildEnergyNineDataCenter : function(title, catalist, datalist, step) {

		var columnColor = "#EFCB1D";
		var radius = 2; // 数据点的大小

		$('#energy_nine_datacenter').highcharts({
					chart : {
						backgroundColor : '#343434',
						marginBottom : 35,
						marginLeft : 50
					},
					title : {
						align : 'left',
						style : {
							color : '#9fa0a2',
							fontSize : '10px'
							// , fontFamily: '微软雅黑'
						},
						x : 0,
						text : '(kWh)'
					},
					legend : {
						enabled : false
					},
					xAxis : {
						tickColor : "#343434",
						categories : catalist,
						labels : {
							step : step,
							style : {
								color : '#C7C7C7',
								fontSize : '16px',
								fontFamily : '微软雅黑'
							}
						}
					},
					yAxis : {
						labels : {
							style : {
								color : '#C7C7C7',
								fontWeight : 'normal',
								fontSize : '14px'
							}
						}
					},
					series : [{
								type : 'area',
								color : columnColor,
								name : '总用气(kWh)',
								data : datalist[0]
							}]
				});
	},

	// 生成九宫格-9.建筑设备能耗图表
	buildEnergyNineDeviceEnergy : function(title, catalist, datalist, step) {

		var columnColor = "#EFD318";
		var radius = 2; // 数据点的大小

		$('#energy_nine_deviceenergy').highcharts({
					chart : {
						backgroundColor : '#343434',
						marginBottom : 35,
						marginLeft : 50
					},
					title : {
						align : 'left',
						style : {
							color : '#9fa0a2',
							fontSize : '10px',
							fontFamily : '微软雅黑'
						},
						x : 0,
						text : '(kWh)'
					},
					legend : {
						enabled : false
					},
					xAxis : {
						tickColor : "#343434",
						categories : catalist,
						labels : {
							step : step,
							style : {
								color : '#C7C7C7',
								fontSize : '16px',
								fontFamily : '微软雅黑'
							}
						}
					},
					yAxis : {
						labels : {
							style : {
								color : '#C7C7C7',
								fontWeight : 'normal',
								fontSize : '14px'
							}
						}
					},
					series : [{
								type : 'line',
								color : columnColor,
								name : '实际用电(kWh)',
								data : datalist[0]
							}]
				});
	}

};

