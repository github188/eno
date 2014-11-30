$(function() {

	dashboard.buildQuotaChart(); // 生成用能定额图表
	dashboard.buildCartonChart(); // 生成碳足迹曲线
	
});

var dashboard = {
	
	buildCartonChart: function() { // 生成碳足迹曲线
	
		var catalist = [];
		for (var i = 0; i < 15; i++) {
			catalist.push(i + ":00");
		}
		$('#cartonchart').highcharts({
			chart : {
				type : 'areaspline',
				backgroundColor : '#000000'
			},
			legend : {
				enabled : false
			},
			xAxis : {
				tickColor : 'rgba(0, 0, 0, 0.5)',
				categories : catalist,
				labels : {
					step : 8,
					style : {
						color : '#A2A2A2',
						fontWeight : 'bold',
						fontFamily : 'RobotoRegular'
					}
				}
			},
			yAxis : {
				gridLineColor : '#5F5F5F',
				labels : {
					style : {
						fontSize : '14px'
					}
				}
			},
			tooltip : {
				shared : true,
				valueSuffix : ''
			},
			plotOptions : {
				areaspline : {
					fillOpacity : 0.5
				}
			},
			series : [{
				name : '碳排放',
				lineWidth : 1.5,
				marker : {
					radius : 3
				},
				color : '#FFBA00',
				data : [11, 68, 77, 85, 190, 198, 111, 123, 117, 111, 100, 92, 87, 76, 61]
			}]
		});
		
	},
	buildQuotaChart : function() { // 生成用能定额图表
	
		var myChart = echarts.init(document.getElementById('energyquota'));
        var option = {
			tooltip : { // 提示信息
				formatter: "{a} <br/>{b} : {c}%"
			},
			toolbox: {
				show : false
			},
			series : [{
					startAngle: 180,
					endAngle : 0,
					center : ['50%', '90%'],    // 默认全局居中
					radius : [0, '165%'],
					min: 0,                     // 最小值
					max: 100,                   // 最大值
					name:'业务指标',
					splitNumber: 4, // 间隔段，用MAX除以这个数字得出
					type:'gauge',
					axisLine: {            // 坐标轴线
						show: true,        // 默认显示，属性show控制显示与否
						lineStyle: {       // 属性lineStyle控制线条样式
							color: [[0.25, '#7CD000'],[0.75, '#FFBB00'],[1, '#FE5F71']],
							width: 40
						}
					},
					axisTick: {            // 坐标轴小标记
						show: true,        // 属性show控制显示与否，默认不显示
						splitNumber: 5,    // 每份split细分多少段
						length : -10,         // 属性length控制线长
						inside : false,
						lineStyle: {       // 属性lineStyle控制线条样式
							color: '#FFF',
							width: 1,
							type: 'solid'
						}
					},
					splitLine: {           // 分隔线
						show: true,        // 默认显示，属性show控制显示与否
						length : 35,         // 属性length控制线长
						lineStyle: {       // 属性lineStyle（详见lineStyle）控制线条样式
							color: 'rgba(0,0,0,0)',
							width: 1,
							type: 'solid'
						}
					},
					axisLabel: {
						textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
							color: '#FFF',
							fontSize: '16',
							fontFamily: '微软雅黑'
						},
						formatter: function(v){
							switch (v + ''){
								case '0': return '0%';
								case '25': return '25%';
								case '50': return '50%';
								case '75': return '75%';
								case '100': return '100%';
								default: return '';
							}
						}
					},
					title : {
						show : true,
						offsetCenter: [0, '-60%'],       // x, y，单位px
						textStyle: {       // 其余属性默认使用全局文本样式，详见TEXTSTYLE
							color: 'rgba(0,0,0,0)'
						}
					},
					data:[{
						value: 75, 
						name: '今日用能定额'
					}]
				}
			]
		};
        myChart.setOption(option); // 为echarts对象加载数据 
        
	}
	
};
