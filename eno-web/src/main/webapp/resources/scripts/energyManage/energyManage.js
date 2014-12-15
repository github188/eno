$(function() {

			energyManage.buildEnergySexChart(); // 生成六宫格图表
			
			energyManage.buildEnergyNineEnergyTotal(); // 生成九宫格-1.建筑总能耗图表
			energyManage.buildEnergyNineEnergySubentry(); // 生成九宫格-2.建筑总能耗分项
			energyManage.buildEnergyNineElectricityTotal(); // 生成九宫格-3.建筑总用电图表
			energyManage.buildEnergyNineWaterTotal(); // 生成九宫格-4.建筑总用水图表
			energyManage.buildEnergyNineGasTotal(); // 生成九宫格-5.建筑总用气图表
			energyManage.buildEnergyNineBuildSubentry(); // 生成九宫格-6.建筑用电分项
			energyManage.buildEnergyNineHvacSubentry(); // 生成九宫格-7.空调系统用电分项
			energyManage.buildEnergyNineDataCenter(); // 生成九宫格-8.数据中心用电图表
			energyManage.buildEnergyNineDeviceEnergy(); // 生成九宫格-9.建筑设备能耗图表

		});

var energyManage = {
	
	// 生成六宫格图表
	buildEnergySexChart : function() {
		energyManage.buildEnergySexEnergyTotal(); // 生成六宫格-1.建筑总能耗图表
		energyManage.buildEnergySexEnergySubentry(); // 生成六宫格-2.建筑总能耗分项
		energyManage.buildEnergySexElectricityTotal(); // 生成六宫格-3.建筑总能耗图表
		energyManage.buildEnergySexHvacSubentry(); // 生成六宫格-4.空调系统用电分项
		energyManage.buildEnergySexDataCenter(); // 生成六宫格-5.数据中心用电图表
		energyManage.buildEnergySexDeviceEnergy(); // 生成六宫格-6.建筑设备能耗图表
	},
	
	// 生成六宫格-1.建筑总能耗图表
	buildEnergySexEnergyTotal : function() {

		// 实际能耗
		var columnData = [28, 72, 66, 111, 55, 68, 75];
		var columnColor = "#3DBA90";
		// 室外温度
		var lineData = [114.5, 170, 173.54, 183.54, 193.54, 183.54, 173.54];
		var lineColor = "#FFB400";
		var catalist = ['周一', '周二', '周三', '周四', '周五', '周六', '周日'];
		var radius = 2; // 数据点的大小
		
		$('#energy_sex_energytotal').highcharts({
			chart: {
				backgroundColor: '#343434',
				marginBottom: 35,
				marginLeft: 50
			},
			title: {
				align: 'left',
				style: {
					color: '#FFFFFF',
					fontFamily: '微软雅黑'
				},
				x: 10,
				text: '2014 - 8 - 10 ~ 2014 - 08 - 16'
			},
			legend : {
				verticalAlign: 'top',
				x: 150,
				y: -7,
				itemStyle: {
					color: '#C7C7C7',
					fontWeight: 'normal',
					fontSize: '16px',
					fontFamily: '微软雅黑'
				},
				borderWidth: 0
			},
			xAxis: {
				tickColor:"#343434",
				categories: catalist,
				labels: {
					style: {
						color: '#C7C7C7',
						fontSize: '16px',
						fontFamily: '微软雅黑'
					}
				}
			},
		    yAxis: [{ // Primary yAxis
				labels: {
					style: {
						color: '#C7C7C7',
						fontWeight: 'normal',
						fontSize: '14px'
					}
				}
			}, { // Secondary yAxis
				labels: {
					style: {
						color: '#C7C7C7',
						fontWeight: 'normal',
						fontSize: '14px'
					}
				},
				opposite: true
			}],
			series: [{
				type: 'column',
				color: columnColor, 
				name: '实际能耗(kWh)',
				data: columnData
			}, {
				type: 'line',
				name: '室外温度(℃)',
				yAxis: 1,
				data: lineData,
				color: lineColor, 
				marker: {
					lineWidth: 2,
					radius: radius,
					lineColor: lineColor,
					fillColor: lineColor
				}
			}]
		});
	},
	
	// 生成六宫格-2.建筑总能耗分项
	buildEnergySexEnergySubentry : function() {
		$('#energy_sex_energysubentry').highcharts({
			chart: {
				backgroundColor: '#343434',
				marginBottom: 35,
				marginLeft: -10,
				plotBackgroundColor: null,
				plotBorderWidth: null,
				plotShadow: false
			},
			title: {
				align: 'right',
				style: {
					color: '#FFFFFF',
					fontFamily: '微软雅黑'
				},
				x: -45,
				y: 50,
				text: '2014 - 8 - 10 ~ 2014 - 8 -16'
			},
			legend : {
				verticalAlign: 'middle',
				align: 'right',
				itemMarginTop: 6,
				width: 300,
				itemWidth: 150,
				itemStyle: {
					color: '#FFF',
					fontWeight: 'normal',
					fontSize: '14px',
					fontFamily: '微软雅黑'
				},
				borderWidth: 0
			},
			plotOptions: {
				pie: {
					allowPointSelect: true,
					cursor: 'pointer',
					dataLabels: {
						enabled: false
					},
					showInLegend: true
				}
			},
			colors: [
				'#3DBA90', 
				'#A7C91D', 
				'#FFD600', 
				'#FF9326', 
				'#FB605E', 
				'#FFB59C', 
				'#74DCFF', 
				'#00B7D9'
			],
			tooltip: {
				pointFormat: ''
			},
			series: [{
				type: 'pie',
				innerSize: '100%',
				size : '150%',
				// 空调,照明,  数据中心, 电梯, 给排水,消防,其他
				data: [
					['空调25%',   25.0],
					['照明25%',    20],
					['数据中心25%',    15],
					['电梯25%',     11],
					['给排水25%',     9],
					['消防25%',     18],
					['其他25%',     12]
				]
			}]
		});
	},
	
	// 生成六宫格-3.建筑总能耗图表
	buildEnergySexElectricityTotal : function() {
		// 实际用电
		var columnData = [];
		var columnColor = "#3DBA90";
		// 室外温度
		var lineData = [];
		var lineColor = "#FFB400";
		var catalist = [];
		for(var i=0;i<24;i++){
			catalist.push(i + ":00");
			columnData.push(Math.floor(Math.random()*200));
			lineData.push(Math.floor(Math.random()*20));
		}
		var radius = 2; // 数据点的大小
		
		$('#energy_sex_electricitytotal').highcharts({
			chart: {
				backgroundColor: '#343434',
				marginBottom: 35,
				marginLeft: 50
			},
			title: {
				align: 'left',
				style: {
					color: '#FFFFFF',
					fontFamily: '微软雅黑'
				},
				x: 10,
				text: '2014 - 8 - 10'
			},
			legend : {
				verticalAlign: 'top',
				x: 150,
				y: -7,
				itemStyle: {
					color: '#C7C7C7',
					fontWeight: 'normal',
					fontSize: '16px',
					fontFamily: '微软雅黑'
				},
				borderWidth: 0
			},
			xAxis: {
				tickColor:"#343434",
				categories: catalist,
				labels: {
					step: 4,
					style: {
						color: '#C7C7C7',
						fontSize: '16px',
						fontFamily: '微软雅黑'
					}
				}
			},
			yAxis: [{ // Primary yAxis
				labels: {
					style: {
						color: '#C7C7C7',
						fontWeight: 'normal',
						fontSize: '14px'
					}
				}
			}, { // Secondary yAxis
				labels: {
					style: {
						color: '#C7C7C7',
						fontWeight: 'normal',
						fontSize: '14px'
					}
				},
				opposite: true
			}],
			series: [{
				type: 'line',
				color: columnColor, 
				name: '实际用电(kWh)',
				data: columnData
			}, {
				type: 'line',
				name: '室外温度(℃)',
				yAxis: 1,
				data: lineData,
				color: lineColor, 
				marker: {
					lineWidth: 2,
					radius: radius,
					lineColor: lineColor,
					fillColor: lineColor
				}
			}]
		});
	},
	
	// 生成六宫格-4.空调系统用电分项
	buildEnergySexHvacSubentry : function() {
		$('#energy_sex_hvacsubentry').highcharts({
			chart: {
				backgroundColor: '#343434',
				marginBottom: 35,
				marginLeft: -10,
				plotBackgroundColor: null,
				plotBorderWidth: null,
				plotShadow: false
			},
			title: {
				align: 'right',
				style: {
					color: '#FFFFFF',
					fontFamily: '微软雅黑'
				},
				x: -45,
				y: 50,
				text: '2014 - 8 - 10 ~ 2014 - 8 -16'
			},
			legend : {
				verticalAlign: 'middle',
				align: 'right',
				itemMarginTop: 6,
				width: 300,
				itemWidth: 150,
				itemStyle: {
					color: '#FFF',
					fontWeight: 'normal',
					fontSize: '14px',
					fontFamily: '微软雅黑'
				},
				borderWidth: 0
			},
			plotOptions: {
				pie: {
					allowPointSelect: true,
					cursor: 'pointer',
					dataLabels: {
						enabled: false
					},
					showInLegend: true
				}
			},
			colors: [
				'#3DBA90', 
				'#A7C91D', 
				'#FFD600', 
				'#FF9326', 
				'#FB605E', 
				'#FFB59C', 
				'#74DCFF', 
				'#00B7D9'
			],
			tooltip: {
				pointFormat: ''
			},
			series: [{
				type: 'pie',
				innerSize: '100%',
				size : '150%',
				// 空调,照明,  数据中心, 电梯, 给排水,消防,其他
				data : [['冷机25%', 25.0], ['空调水泵25%', 20], ['冷却塔25%', 15],
						['空调箱25%', 11], ['ACU用电25%', 9], ['其他30%', 30]]
			}]
		});
	},
	
	// 生成六宫格-5.数据中心用电图表
	buildEnergySexDataCenter : function() {
		// 实际能耗
		var columnData = [];
		var columnColor = "#3DBA90";
		// 室外温度
		var lineData = [];
		var lineColor = "#FFB400";
		var catalist = ['一月' , '二月' , '三月' , '四月' , '五月' , '六月' , '七月' , '八月' , '九月' , '十月' , '十一月' , '十二月'];
		for(var i=0;i<12;i++){
			columnData.push(Math.floor(Math.random()*200));
			lineData.push(Math.floor(Math.random()*20));
		}
		var radius = 2; // 数据点的大小
		
		$('#energy_sex_datacenter').highcharts({
			chart: {
				backgroundColor: '#343434',
				marginBottom: 35,
				marginLeft: 50
			},
			title: {
				align: 'left',
				style: {
					color: '#FFFFFF',
					fontFamily: '微软雅黑'
				},
				x: 10,
				text: '2014'
			},
			legend : {
				verticalAlign: 'top',
				x: 150,
				y: -7,
				itemStyle: {
					color: '#C7C7C7',
					fontWeight: 'normal',
					fontSize: '16px',
					fontFamily: '微软雅黑'
				},
				borderWidth: 0
			},
			xAxis: {
				tickColor:"#343434",
				categories: catalist,
				labels: {
					staggerLines: 1,
					step: 2,
					style: {
						color: '#C7C7C7',
						fontSize: '16px',
						fontFamily: '微软雅黑'
					}
				}
			},
			yAxis: [{ // Primary yAxis
				labels: {
					style: {
						color: '#C7C7C7',
						fontWeight: 'normal',
						fontSize: '14px'
					}
				}
			}, { // Secondary yAxis
				labels: {
					style: {
						color: '#C7C7C7',
						fontWeight: 'normal',
						fontSize: '14px'
					}
				},
				opposite: true
			}],
			series: [{
				type: 'area',
				color: columnColor, 
				name: '实际能耗(kWh)',
				data: columnData, 
				marker: {
					lineWidth: 1,
					radius: radius,
					lineColor: columnColor
				}
			}, {
				type: 'line',
				name: '室外温度(℃)',
				yAxis: 1,
				data: lineData,
				color: lineColor, 
				marker: {
					lineWidth: 2,
					radius: radius,
					lineColor: lineColor,
					fillColor: lineColor
				}
			}]
		});
	},
	
	// 生成六宫格-6.建筑设备能耗图表
	buildEnergySexDeviceEnergy : function() {
		// 实际能耗
		var columnData = [];
		var columnColor = "#3DBA90";
		// 室外温度
		var lineData = [];
		var lineColor = "#FFB400";
		var catalist = [];
		for (var i = 0; i < 31; i++) {
			catalist.push(i);
			columnData.push(Math.floor(Math.random() * 200));
			lineData.push(Math.floor(Math.random() * 20));
		}
		var radius = 2; // 数据点的大小
		
		$('#energy_sex_deviceenergy').highcharts({
			chart: {
				backgroundColor: '#343434',
				marginBottom: 35,
				marginLeft: 50
			},
			title: {
				align: 'left',
				style: {
					color: '#FFFFFF',
					fontFamily: '微软雅黑'
				},
				x: 10,
				text: '2014'
			},
			legend : {
				verticalAlign: 'top',
				x: 150,
				y: -7,
				itemStyle: {
					color: '#C7C7C7',
					fontWeight: 'normal',
					fontSize: '16px',
					fontFamily: '微软雅黑'
				},
				borderWidth: 0
			},
			xAxis: {
				tickColor:"#343434",
				categories: catalist,
				labels: {
					staggerLines: 1,
					step: 2,
					style: {
						color: '#C7C7C7',
						fontSize: '16px',
						fontFamily: '微软雅黑'
					}
				}
			},
			yAxis: [{ // Primary yAxis
				labels: {
					style: {
						color: '#C7C7C7',
						fontWeight: 'normal',
						fontSize: '14px'
					}
				}
			}, { // Secondary yAxis
				labels: {
					style: {
						color: '#C7C7C7',
						fontWeight: 'normal',
						fontSize: '14px'
					}
				},
				opposite: true
			}],
			series: [{
				type: 'column',
				color: columnColor,
				name: '实际能耗(kWh)',
				data: columnData
			}, {
				type: 'line',
				name: '室外温度(℃)',
				yAxis: 1,
				data: lineData,
				color: lineColor, 
				marker: {
					lineWidth: 2,
					radius: radius,
					lineColor: lineColor,
					fillColor: lineColor
				}
			}]
		});
	},
	
	// 生成九宫格图表
	buildEnergyNineChart : function() {
		energyManage.buildEnergyNineEnergyTotal(); // 生成九宫格-1.建筑总能耗图表
		energyManage.buildEnergyNineEnergySubentry(); // 生成九宫格-2.建筑总能耗分项
		energyManage.buildEnergyNineElectricityTotal(); // 生成九宫格-3.建筑总能耗图表
		energyManage.buildEnergyNineWaterTotal(); // 生成九宫格-4.建筑总用水图表
		energyManage.buildEnergyNineGasTotal(); // 生成九宫格-5.建筑总用气图表
		energyManage.buildEnergyNineBuildSubentry(); // 生成九宫格-6.建筑用电分项
		energyManage.buildEnergyNineHvacSubentry(); // 生成九宫格-7.空调系统用电分项
		energyManage.buildEnergyNineDataCenter(); // 生成九宫格-8.数据中心用电图表
		energyManage.buildEnergyNineDeviceEnergy(); // 生成九宫格-9.建筑设备能耗图表
	},
	
	// 生成九宫格-1.建筑总能耗图表
	buildEnergyNineEnergyTotal : function() {
		// 实际能耗
		var columnData = [28, 72, 66, 111, 55, 68, 75];
		var columnColor = "#00C28E";
		
		var catalist = ['周一', '周二', '周三', '周四', '周五', '周六', '周日'];
		var radius = 2; // 数据点的大小
		
		$('#energy_nine_energytotal').highcharts({
			chart: {
				backgroundColor: '#343434',
				marginBottom: 35,
				marginLeft: 50
			},
			title: {
				align: 'left',
				style: {
					color: '#9fa0a2',
					fontSize: '10px'
					//, fontFamily: '微软雅黑'
				},
				x: 0,
				text: '(kWh)'
			},
			legend : {
				enabled: false
			},
			xAxis: {
				tickColor:"#343434",
				categories: catalist,
				labels: {
					style: {
						color: '#C7C7C7',
						fontSize: '16px',
						fontFamily: '微软雅黑'
					}
				}
			},
			yAxis: {
				labels: {
					style: {
						color: '#C7C7C7',
						fontWeight: 'normal',
						fontSize: '14px'
					}
				}
			},
			series: [{
				type: 'column',
				color: columnColor, 
				name: '实际能耗(kWh)',
				data: columnData
			}]
		});
	},
	
	// 生成九宫格-2.建筑总能耗分项
	buildEnergyNineEnergySubentry : function() {
		
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
				size : '160%', // 空调,照明,  数据中心, 电梯, 给排水,消防,其他
				data : [['空调25%', 25.0], ['照明25%', 20], ['数据中心25%', 15],
						['电梯25%', 11], ['给排水25%', 9], ['消防25%', 18],
						['其他25%', 12]]
			}]
		});
	},

	// 生成九宫格-3.建筑总用电图表
	buildEnergyNineElectricityTotal : function() {

		var columnData = [28, 72, 66, 111, 55, 68, 75];
		var columnColor = "#00C28E";
		var catalist = ['周一', '周二', '周三', '周四', '周五', '周六', '周日'];
		var radius = 2; // 数据点的大小
		
		$('#energy_nine_electricitytotal').highcharts({
			chart: {
				backgroundColor: '#343434',
				marginBottom: 35,
				marginLeft: 50
			},
			title: {
				align: 'left',
				style: {
					color: '#9fa0a2',
					fontSize: '10px'
					, fontFamily: '微软雅黑'
				},
				x: 0,
				text: '(kWh)'
			},
			legend : {
				enabled: false
			},
			xAxis: {
				tickColor:"#343434",
				categories: catalist,
				labels: {
					style: {
						color: '#C7C7C7',
						fontSize: '16px',
						fontFamily: '微软雅黑'
					}
				}
			},
			yAxis: {
				labels: {
					style: {
						color: '#C7C7C7',
						fontWeight: 'normal',
						fontSize: '14px'
					}
				}
			},
			series: [{
				type: 'line',
				color: columnColor, 
				name: '实际用电(kWh)',
				data: columnData
			}]
		});
	},

	// 生成九宫格-4.建筑总用水图表
	buildEnergyNineWaterTotal : function() {
		
		var columnData = [28, 72, 66, 111, 55, 68, 75];
		var columnColor = "#5CC8E4";
		var catalist = ['周一', '周二', '周三', '周四', '周五', '周六', '周日'];
		var radius = 2; // 数据点的大小
		
		$('#energy_nine_watertotal').highcharts({
			chart: {
				backgroundColor: '#343434',
				marginBottom: 35,
				marginLeft: 50
			},
			title: {
				align: 'left',
				style: {
					color: '#9fa0a2',
					fontSize: '10px'
					//, fontFamily: '微软雅黑'
				},
				x: 0,
				text: '(kWh)'
			},
			legend : {
				enabled: false
			},
			xAxis: {
				tickColor:"#343434",
				categories: catalist,
				labels: {
					style: {
						color: '#C7C7C7',
						fontSize: '16px',
						fontFamily: '微软雅黑'
					}
				}
			},
			yAxis: {
				labels: {
					style: {
						color: '#C7C7C7',
						fontWeight: 'normal',
						fontSize: '14px'
					}
				}
			},
			series: [{
				type: 'column',
				color: columnColor, 
				name: '实际能耗(kWh)',
				data: columnData
			}]
		});
	},
	
	// 生成九宫格-5.建筑总用气图表
	buildEnergyNineGasTotal : function() {
		
		var columnData = [28, 72, 66, 111, 55, 68, 75];
		var columnColor = "#5CC9DE";
		var catalist = ['周一', '周二', '周三', '周四', '周五', '周六', '周日'];
		var radius = 2; // 数据点的大小
		
		$('#energy_nine_gastotal').highcharts({
			chart: {
				backgroundColor: '#343434',
				marginBottom: 35,
				marginLeft: 50
			},
			title: {
				align: 'left',
				style: {
					color: '#9fa0a2',
					fontSize: '10px'
					//, fontFamily: '微软雅黑'
				},
				x: 0,
				text: '(kWh)'
			},
			legend : {
				enabled: false
			},
			xAxis: {
				tickColor:"#343434",
				categories: catalist,
				labels: {
					style: {
						color: '#C7C7C7',
						fontSize: '16px',
						fontFamily: '微软雅黑'
					}
				}
			},
			yAxis: {
				labels: {
					style: {
						color: '#C7C7C7',
						fontWeight: 'normal',
						fontSize: '14px'
					}
				}
			},
			series: [{
				type: 'area',
				color: columnColor, 
				name: '总用气(kWh)',
				data: columnData
			}]
		});
	},
	
	// 生成九宫格-6.建筑用电分项
	buildEnergyNineBuildSubentry : function() {
		
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
				size : '160%', // 空调,照明,  数据中心, 电梯, 给排水,消防,其他
				data : [['空调25%', 25.0], ['照明25%', 20], ['数据中心25%', 15],
						['电梯25%', 11], ['给排水25%', 9], ['消防25%', 18],
						['其他25%', 12]]
			}]
		});
	},
	
	// 生成九宫格-7.空调系统用电分项
	buildEnergyNineHvacSubentry : function() {
		
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
				size : '160%', // 空调,照明,  数据中心, 电梯, 给排水,消防,其他
				data : [['空调25%', 25.0], ['照明25%', 20], ['数据中心25%', 15],
						['电梯25%', 11], ['给排水25%', 9], ['消防25%', 18],
						['其他25%', 12]]
			}]
		});
	},

	// 生成九宫格-8.数据中心用电图表
	buildEnergyNineDataCenter : function() {
		
		var columnData = [28, 72, 66, 111, 55, 68, 75];
		var columnColor = "#EFCB1D";
		var catalist = ['周一', '周二', '周三', '周四', '周五', '周六', '周日'];
		var radius = 2; // 数据点的大小
		
		$('#energy_nine_datacenter').highcharts({
			chart: {
				backgroundColor: '#343434',
				marginBottom: 35,
				marginLeft: 50
			},
			title: {
				align: 'left',
				style: {
					color: '#9fa0a2',
					fontSize: '10px'
					//, fontFamily: '微软雅黑'
				},
				x: 0,
				text: '(kWh)'
			},
			legend : {
				enabled: false
			},
			xAxis: {
				tickColor:"#343434",
				categories: catalist,
				labels: {
					style: {
						color: '#C7C7C7',
						fontSize: '16px',
						fontFamily: '微软雅黑'
					}
				}
			},
			yAxis: {
				labels: {
					style: {
						color: '#C7C7C7',
						fontWeight: 'normal',
						fontSize: '14px'
					}
				}
			},
			series: [{
				type: 'area',
				color: columnColor, 
				name: '总用气(kWh)',
				data: columnData
			}]
		});
	}, 
	
	// 生成九宫格-9.建筑设备能耗图表
	buildEnergyNineDeviceEnergy : function() {

		var columnData = [28, 72, 66, 111, 55, 68, 75];
		var columnColor = "#EFD318";
		var catalist = ['周一', '周二', '周三', '周四', '周五', '周六', '周日'];
		var radius = 2; // 数据点的大小
		
		$('#energy_nine_deviceenergy').highcharts({
			chart: {
				backgroundColor: '#343434',
				marginBottom: 35,
				marginLeft: 50
			},
			title: {
				align: 'left',
				style: {
					color: '#9fa0a2',
					fontSize: '10px'
					, fontFamily: '微软雅黑'
				},
				x: 0,
				text: '(kWh)'
			},
			legend : {
				enabled: false
			},
			xAxis: {
				tickColor:"#343434",
				categories: catalist,
				labels: {
					style: {
						color: '#C7C7C7',
						fontSize: '16px',
						fontFamily: '微软雅黑'
					}
				}
			},
			yAxis: {
				labels: {
					style: {
						color: '#C7C7C7',
						fontWeight: 'normal',
						fontSize: '14px'
					}
				}
			},
			series: [{
				type: 'line',
				color: columnColor, 
				name: '实际用电(kWh)',
				data: columnData
			}]
		});
	}
	
};
