$(function() {
	
	// dashboard事件
	$("#env_dashboard").on("click", function() {
		$("#mask").hide(); // 隐藏
		$("#dashboard_mask,.dashboard").show();
		$("#dashboard_mask,.dashboard").removeClass("ui-draggable");
		$("#dashboard_mask").css("background-color", "#20243d"); // 修改背景颜色
		buildBoard(); // 渲染dashboard
	});

	// 关闭dashboard事件
	$(".close_board").on("click", function() {
		$("#mask,.dashboard").hide();
		$("#mask").css("background-color", "rgba(170,170,170,0.95)"); // 修改背景颜色
	});
	
	// 能效分析切换
	$(".energy_content_title>ul>li").click(function() {
		$(this).siblings().removeClass("focus_title");
		$(this).addClass("focus_title");
		$(".enery_box").hide().eq($(".energy_content_title>ul>li").index(this)).show();
	});
	
	/*配置定额弹出框*/
	$(".set_quotaBtn").on("click",function(){
		$(".mask").hide();
		$(".mask,.set_quotaBox").show();
		$("#dashboard_mask").hide();
	});
	$(".dialog-close, .close_btn, .btn_cancel").on("click", function(){
		$(".mask,.set_quotaBox").hide();
	})
	/*下拉列表*/
	$(".select").each(function(){
		var s=$(this);
		var z=parseInt(s.css("z-index"));
		var dt=$(this).children("dt");
		var dd=$(this).children("dd");
		var _show=function(){dd.slideDown(200);dt.addClass("focus");s.css("z-index",z+1);};   //展开效果
		var _hide=function(){dd.slideUp(200);dt.removeClass("focus");s.css("z-index",z);};    //关闭效果
		dt.click(function(){dd.is(":hidden")?_show():_hide();});
		dd.find("a").click(function(){dt.html($(this).html());_hide();});     //选择效果（如需要传值，可自定义参数，在此处返回对应的“value”值 ）
		$("body").click(function(i){ !$(i.target).parents(".select").first().is(s) ? _hide():"";});
	})
	
	/*历史数据展开收起*/
	$(".export_openBtn").toggle(function(){
		$(".enery_showCon").slideUp("slow");
		$(this).removeClass("export_openBtn").addClass("export_closeBtn");
	},function(){
		$(".enery_showCon").slideDown("slow");
		$(this).removeClass("export_closeBtn").addClass("export_openBtn");
	});
	
	var ala_index = 0;
	// 菜单事件，点击左侧第三级菜单（楼层菜单）的处理事件
	$(".cataloge_class_two li").click(function() {
		var id = $(this).attr("id");
		if(id == 'ENVMANAGE') { // 能耗定额
			$("#energyStandard").show();
			$("#energyChart").hide();
			$("#subMeasure").hide();
			$(".menu_layout").hide();
			
			buildYearEleChart(); // 生成年度用电定额图表
			// 右侧排名进度条
			var processList = [90, 80, 70, 60, 57, 55, 52, 49, 48, 40, 39, 38, 37];
			$(".energy_progress_bar").each(function(index) { // 遍历所有的.energy_progress_bar
				$(this).css("width", 320 * processList[index] / 100 + 19); // 获取.energy_progress_bar 的宽度 19是箭头的宽度
				$(this).find("div:eq(0)").animate({
				  width: (320 * processList[index] / 100 * 0.9) // 获取.energy_progress_bar 的第一个子元素的宽度，即进度条的宽度
				},1500);
			});
			
			// 初始化日历
			$("#calendar").fullCalendar({
					selectable: true,
					selectHelper: true,
					select: function(start, end) {
					},
					editable: true,
					eventLimit: true // allow "more" link when too many events
				});
			GetCalendar(); // 设置日历样式
			
			monthElectricity(); // 生成逐月用电定额情况
		}
		if(id == 'EMS') { // 用能趋势
			$("#energyStandard").hide();
			$("#subMeasure").hide();
			$("#energyChart").show();
			$(".menu_layout").show();
		}
		if(id == 'ENVSYS') { // 能效分析
			$("#energyStandard").hide();
			$("#energyChart").hide();
			$(".menu_layout").hide();
			$("#subMeasure").show();
			
			if (ala_index == 0) { // 第一次进入才显示图表
				// 获取当前图表
				var _currenttime = getCurrentTime();
				getBuildJZElectricityChart('day', _currenttime); // 1.获取单位面积建筑总用电趋势图表
				getBuildRJElectricityChart('day', _currenttime); // 2.获取人均建筑总用电量趋势图表
				getBuildKTElectricityChart('day', _currenttime); // 3.单位面积空调用电量
				getBuildRJKTElectricityChart('day', _currenttime); // 4.人均空调用电量
				getBuildZMElectricityChart('day', _currenttime); // 5.单位面积照明用电量
				getBuildDTElectricityChart('day', _currenttime); // 6.单位面积电梯用电量
				getBuildZLMJElectricityChart('day', _currenttime); // 7.单位面积制冷量
				getBuildZLXTElectricityChart('day', _currenttime); // 8.制冷系统能效比
				getBuildLQSElectricityChart('day', _currenttime); // 9.冷却水输运系数
				getBuildLDSElectricityChart('day', _currenttime); // 10.冷冻水输运系数
				getBuildCOPElectricityChart('day', _currenttime); // 11.冷机COP
				getBuildLQTElectricityChart('day', _currenttime); // 12.冷却塔能效比
			}
			ala_index++;
		}
	});
	
	var cur_year = new Date().getFullYear();
	$("#year_date").val(cur_year); // 默认年度用电定额情况
	$("#curYear_date").val(cur_year); // 默认逐月用电定额情况
	var y_m = new Date().Format("yyyy-MM"); 
	$("#month_date").val(y_m); // 默认定额日历时间
	$("#chooseMonth").text(y_m);
			
});

// 生成年度用电定额图表
function buildYearEleChart() {
	var myChart = echarts.init(document.getElementById('energy_picYear'));
	var option = {
		tooltip : {
			formatter : "{a} <br/>{b} : {c}%"
		},
		toolbox : {
			show : false
		},
		series : [{
			startAngle : 180,
			endAngle : 0,
			center : ['50%', '90%'], // 默认全局居中
			radius : [0, '165%'],
			min : 0, // 最小值
			max : 1000, // 最大值
			name : '业务指标',
			type : 'gauge',
			axisLine : { // 坐标轴线
				show : true, // 默认显示，属性show控制显示与否
				lineStyle : { // 属性lineStyle控制线条样式
					color : [[0.25, '#7CD000'], [0.75, '#FFBB00'],
							[1, '#FE5F71']],
					width : 50
				}
			},
			axisTick : { // 坐标轴小标记
				show : true, // 属性show控制显示与否，默认不显示
				splitNumber : 5, // 每份split细分多少段
				length : -6, // 属性length控制线长
				inside : false,
				lineStyle : { // 属性lineStyle控制线条样式
					color : '#878787',
					width : 1,
					type : 'solid'
				}
			},
			splitLine : { // 分隔线
				show : true, // 默认显示，属性show控制显示与否
				length : 45, // 属性length控制线长
				lineStyle : { // 属性lineStyle（详见lineStyle）控制线条样式
					color : 'rgba(0,0,0,0)',
					width : 1,
					type : 'solid'
				}
			},
			axisLabel : {
				textStyle : { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
					color : '#525252',
					fontFamily : '微软雅黑'
				}
			},

			title : {
				show : true,
				offsetCenter : [0, '-60%'], // x, y，单位px
				textStyle : { // 其余属性默认使用全局文本样式，详见TEXTSTYLE
					color : 'rgba(0,0,0,0)'
				}
			},
			data : [{
						value : 750,
						name : '年度用电定额'
					}]
		}]
	};

	// 为echarts对象加载数据
	myChart.setOption(option); 
}

// 选择定额日历
function chooseTime() {
	var format = "yyyy-MM";
	WdatePicker({
		el : 'month_date',	// 只操作这个日历控件
		dateFmt : format, // 定义时间的格式
		maxDate : new Date().Format(format), // 最大能选择的时间
		onpicked : function(dp) {
			// 具体的对比时间，格式为（yyyy-MM-dd），此时间会传递到后台
			var paretime= dp.cal.getDateStr(format);
			$("#calendar").fullCalendar('gotoDate', paretime);// 跳到相应的时间
			$("#chooseMonth").text(paretime);
			
			var today = new Date().Format(format);
			var compare = dp.cal.getDateStr(format);
			GetCalendar(today === compare);
		}
	});
}

// 年度用电定额情况选择日历
function yearElectrict() {
	var format = "yyyy";
	WdatePicker({
		el : 'year_date',	// 只操作这个日历控件
		dateFmt : format, // 定义时间的格式
		maxDate : new Date().Format(format), // 最大能选择的时间
		onpicked : function(dp) {
			// 具体的对比时间，格式为（yyyy-MM-dd），此时间会传递到后台
			var paretime= dp.cal.getDateStr('yyyy');
			buildYearEleChart(); // 生成年度用电定额图表
		}
	});
}

// 逐月用电定额情况选择日历
function curYearElectrict() {
	var format = "yyyy";
	WdatePicker({
		el : 'curYear_date',	// 只操作这个日历控件
		dateFmt : format, // 定义时间的格式
		maxDate : new Date().Format(format), // 最大能选择的时间
		onpicked : function(dp) {
			// 具体的对比时间，格式为（yyyy-MM-dd），此时间会传递到后台
			var paretime= dp.cal.getDateStr('yyyy');
			monthElectricity(); // 生成逐月用电定额情况
		}
	});
}

// 设置定额日历样式
function GetCalendar(bool){		
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
}

// 生成逐月用电定额情况
function monthElectricity() {
	var catalist = [];
	for (var i = 1; i <= 12; i++) {
		catalist.push(i);
	}
	$('#monthElectricity').highcharts({
		chart : {
			type : 'column',
			marginBottom : 60
		},
		title : {
			text : '' // <span style="font-family: 微软雅黑;">今年实际用电/定额</span>
		},
		xAxis : {
			categories : catalist
		},
		yAxis : [{ // Primary yAxis
			title : {
				text : ''
			}
		}, {	// Secondary yAxis
					title : {
						text : ''
					},
					opposite : true
				}],
		legend : {
			layout : 'horizontal',
			align : 'right',
			verticalAlign : 'bottom',
			x : 0,
			y : 10,
			floating : true,
			itemStyle : {
				cursor : 'pointer',
				color : '#3E576F',
				fontFamily : '微软雅黑',
				fontSize : '18px'
			},
			symbolPadding : 10,
			borderWidth : 0
		},
		tooltip : {
			crosshairs : true,
			shared : true,
			backgroundColor : '#DFBFFF',
			borderColor : '#DFBFFF',
			style : {
				color : '#FFF',
				fontSize : '14px',
				fontFamily : '微软雅黑'
			},
			headerFormat : '<span style="font-size:18px">{point.key}月</span><table>',
			pointFormat : '<tr><td><br />{series.name}: </td></tr>'
					+ '<tr><td><b><span style="font-size: 20px;">{point.y:.1f}</span> kWh</b></td></tr>',
			footerFormat : '</table>'
		},
		plotOptions : {
			spline : {
				marker : {
					radius : 4,
					lineColor : '#666666',
					lineWidth : 1
				}
			}
		},
		colors : ['#82EBEF', '#DFBFFF'],
		series : [{
			name : '实际用电(kWh)',
			data : [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3,
					13.9, 9.6]
		}, {
			name : '定额(kWh)',
			yAxis : 1,
			data : [3.9, 4.2, 5.7, 8.5, 11.9, 15.2, 17.0, 16.6, 14.2, 10.3,
					6.6, 4.8]
		}]
	});
}