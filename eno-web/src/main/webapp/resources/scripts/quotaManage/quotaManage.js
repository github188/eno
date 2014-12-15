$(function() {

	/**
	 * 建筑用电定额年用电
	 */
	quotaManage.buildQuotaChart();
	quotaManage.monthElectricity();
	// 初始化日历
	$("#calendar").fullCalendar({
			selectable: true,
			selectHelper: true,
			select: function(start, end) {
			},
			editable: true,
			eventLimit: true // allow "more" link when too many events
		});
	quotaManage.GetCalendar(); // 设置日历样式
	
});

var quotaManage = {
	chooseTimeY: function(format) { // 生成碳足迹曲线
		WdatePicker({
            el : 'year_date',	//只操作这个日历控件
            dateFmt : "yyyy", //定义时间的格式
            onpicked : function(dp) {
                // 具体的对比时间，格式为（yyyy），此时间会传递到后台
                var paretime= dp.cal.getDateStr("yyyy");
            }
		
        });
	},
	chooseTimeM : function(format) { // 生成用能定额图表
		WdatePicker({
            el : 'month_date',	//只操作这个日历控件
            dateFmt : "yyyy-MM", //定义时间的格式
            onpicked : function(dp) {
                // 具体的对比时间，格式为（yyyy-MM），此时间会传递到后台
                var paretime= dp.cal.getDateStr("yyyy-MM");
            }
        });
	},
	// 选择定额日历
	chooseTimeD:function (format) {
		console.log("a");
		WdatePicker({
			el : 'day_date',	// 只操作这个日历控件
			dateFmt : format, // 定义时间的格式
			maxDate : new Date().Format(format), // 最大能选择的时间
			onpicked : function(dp) {
				// 具体的对比时间，格式为（yyyy-MM-dd），此时间会传递到后台
				var paretime= dp.cal.getDateStr(format);
				$("#calendar").fullCalendar('gotoDate', paretime);// 跳到相应的时间
				
				var today = new Date().Format(format);
				var compare = dp.cal.getDateStr(format);
				GetCalendar(today === compare);
			}
		});
	},
	
	// 设置定额日历样式
	GetCalendar : function(bool) { 
		$(".fc-other-month").text(""); // 将非当月的日期文本置为空
		// 循环样式为fc-day-number的td
		var index = 1;
		$(".fc-day").each(function() {
			if(!$(this).hasClass("fc-other-month")) {
				$(this).attr("id", "cal_" + index);//为本月的日期加上id
				index++;
			}
		});
		   
		var green_array = ['1', '3', '15', '16', '17', '24', '29', '30', '31'];
		var red_array = ['2', '4', '5', '6', '9', '10', '12', '13', '28', '27'];
		var orange_array = ['7', '8', '11', '14', '18', '19', '20', '23', '22', '25', '26', '21'];
		// 分别设置不同的背景色
		for (var i = 0; i < green_array.length; i++) {
			$("#cal_" + green_array[i]).css("background-color", "#7BD000");
		}
		for (var i = 0; i < red_array.length; i++) {
			$("#cal_" + red_array[i]).css("background-color", "#FF6070");
		} 
		for (var i = 0; i < orange_array.length; i++) {
			$("#cal_" + orange_array[i]).css("background-color", "#FFBB00");
		}
		
		if (bool) { // 只有选择的日期是当月的时候才执行
			var day = new Date().Format("dd");
			$("#cal_" + day).css("background-color", "#73B9FF"); // 设置今日背景颜色
			// 设置从今天完后的日期背景为灰色
			for (var i = parseInt(day); i < 31; i++) {
				$("#cal_" + (i + 1)).css("background-color", "#999");
			}
		}
	},
	monthElectricity : function() { // 生成逐月用电定额情况
			var catalist = [], data1 = [], data2 = [];
			for (var i = 0; i < 24; i++) {
				catalist.push(i); // + ":00"
				data1.push(parseFloat((Math.random() * 50).toFixed(1)));
				data2.push(parseFloat((Math.random() * 15).toFixed(1)));
			}
			var columnColor = '#00C98A', lineColor = '#FDB700';
			$('#monthElectricity').highcharts({
						chart : {
							backgroundColor : '#333333',
							marginBottom : 50
						},
						legend : {
							x: 320,
							y: 10
						},
						xAxis : {
							tickColor : '#333333',
							categories : catalist,
							labels : {
								step : 4,
								style : {
									color : '#F3F3F3',
									fontSize : '14px',
									fontWeight : 'bold',
									fontFamily : 'RobotoRegular'
								}
							}
						},
						yAxis : [{ // Primary yAxis
								}, { // Secondary yAxis
									opposite : true
								}],
						tooltip : {
							crosshairs : true,
							shared : true
						},
						series : [{
									type : 'column',
									borderColor : columnColor,
									name : '实际用电（kWh）',
									color : columnColor,
									data : data1
								}, {
									type : 'column',
									name : '定额（mWh）',
									borderColor : lineColor,
									color : lineColor,
									data : data2,
									yAxis : 1,
									lineWidth : 2,
									marker : {
										radius : 3,
										lineColor : lineColor
									}
								}]
					});
	},
	buildQuotaChart : function() { // 生成用能定额图表
		var myChart = echarts.init(document.getElementById('energyquotaManage'));
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
								case '0': return '0';
								case '25': return '250';
								case '50': return '500';
								case '75': return '750';
								case '100': return '1000';
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



