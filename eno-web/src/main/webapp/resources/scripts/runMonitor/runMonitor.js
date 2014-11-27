$(function() {

//	runMonitor.buildETDChart(); // 生成变配电高压图
	
});

var runMonitor = {
	
	buildETDChart: function() { // 生成变配电高压图
	
		var dataList1 = [],dataList2 = [],dataList3 = []; // 数据列表
			for(var i=0;i<24;i++){
				dataList1.push( Math.floor(Math.random()*100));
				dataList2.push( Math.floor(Math.random()*100));
				dataList3.push( Math.floor(Math.random()*100));
			}
		
			$('#high_pressure').highcharts({
					chart : {
						type : 'spline'
					},
					title : {
						text : ''
					},
					credits : {
						enabled : false
					},
					xAxis : {
						tickColor : '#FFFFFF',
						labels : {
							step : 300 // x轴显示的间隔
						},
						categories : [ '', '', '', '', '', '', '', '']
					},
					yAxis : {
						gridLineDashStyle : 'LongDash',
						title: {
						   text : ''
						}
					},
					tooltip : {
						backgroundColor : '#5CC8E4', // 提示框背景颜色
						formatter : function() {
							return '<b>' + this.y + ' kW' +  '</b>'
						},
						style: {
							color: '#FFF', // 提示内容颜色
							fontSize: '12px', 
							padding: '8px'
						},
						borderWidth : 0
					},
					plotOptions : {
						spline : {
							marker : {
								radius : 0,
								lineColor : '#666666',
								lineWidth : 1
							}
						}
					},
					legend: {
						//backgroundColor: '#FCFFC5',
						shadow : true,
						layout: 'vertical',
						align: 'right',
						verticalAlign: 'top',
						x: -10,
						y: 50,
						itemMarginTop : 10,
						borderWidth: 0
					},
					series : [
							{
								name : '视在功率',
								color : '#469FE3',
								marker : {
									lineWidth : 0,
									lineColor : '#469FE3',
									fillColor : 'white',
									symbol : 'circle' //曲线点类型："circle", "square", "diamond", "triangle","triangle-down"，默认是"circle"
								},
								data : dataList1
							},
							{
								name : '有用功率',
								color : '#5EC8E2',
								marker : {
									lineWidth : 2,
									lineColor : '#5EC8E2',
									fillColor : 'white',
									symbol : 'diamond' //曲线点类型："circle", "square", "diamond", "triangle","triangle-down"，默认是"circle"
								},
								data : dataList2
							},{
								name : '无用功率',
								color : '#FBAB3A',
								marker : {
									lineWidth : 2,
									lineColor : '#FBAB3A',
									fillColor : 'white',
									symbol : 'circle' //曲线点类型："circle", "square", "diamond", "triangle","triangle-down"，默认是"circle"
								},
								data : dataList3
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
