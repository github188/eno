var temp_tfrom = ''; // 全局的开始时间
var temp_type = ''; // 全局的查询类型
var temp_query_num = '1'; // 全局的查询个数

$(function(){
	// 监听下拉列表改变事件
	$('#select_type').change(function(){
		temp_type = this.value; // 全局的查询类型
	});
	
	// 监听下拉列表改变事件
	$('#select_time_type5').change(function(){
		temp_type = this.value; // 全局的查询类型
	});

	// 点击返回按钮触发的事件
	$('#backpage').click(function(){
		$("#report_choose").show(); // 隐藏报表导出选择页面
		$(".form_style").hide(); // 显示报表详情页面
	});
	
	// 点击后序页面查询按钮触发的事件
	$('#querydata').click(function(){
		loadData($('#select_time_type5').val());
	});
	
	
	/*$(".date_pick").focus(function(){
	
		selectdate('starttime','endtime','select_type');
		
	});*/
	
});

//点击查询按钮触发的事件
function queryReport(id1, id2, type, num){
	var tfrom = $('#' + id1).val(); // 获取当前选择的查询类型（日、周、月）
	if(tfrom==""){
		alert("请选择要查询的时间段！");
		return false;
	} 

	$('#from_time5').val($('#' + id1).val()); // 设置开始时间
	$('#to_time5').val($('#' + id2).val()); // 设置结束时间
	$('#select_time_type5').val($('#' + type).val()); // 设置选择类型的值
	$("#report_choose").hide(); // 隐藏报表导出选择页面
	$(".form_style").show(); // 显示报表详情页面
	
	temp_query_num = num;
	loadData($('#select_time_type5').val());
};

// 选择时间触发的事件
function selectdate(id1,id2,sel_id){
	try {	
		// 设置时间字体的大小
		$('#' + id1).css('fontSize','12px');
		$('#' + id2).css('fontSize','12px');
					
		var choose_type = $('#' + sel_id).val(); // 获取当前选择的查询类型（日、周、月）
		var dformt = 'yyyy-MM-dd';
		if (choose_type == "day") {
			dformt = 'yyyy-MM-dd';
		} else if (choose_type == "week") {
			dformt = 'yyyy-MM-dd';
		} else if (choose_type == "month") {
			dformt = 'MM';
		} else if (choose_type == "year") {
			dformt = 'yyyy';
		}


		new WdatePicker({
			//el : 'showDateTime',
			dateFmt : dformt,
			maxDate : new Date,
			onpicked : function(dp) {
				var showtime = dp.cal.getDateStr(dformt);
				var hidetime = dp.cal.getDateStr('yyyy-MM-dd');

				temp_tfrom = hidetime;
				// 周报的开始时间和结束时间需要做特殊处理
				if (choose_type == "week") {
					// 设置当前显示界面的开始和结束时间
					$('#' + id1).val(showWeekFirstDay(showtime));
					$('#' + id2).val(showWeekLastDay(showtime));
					// 设置后序页面的开始和结束时间
					$('#from_time5').val(showWeekFirstDay(showtime));
					$('#to_time5').val(showWeekLastDay(showtime));
					temp_tfrom = showWeekLastDay(showtime);
				} else if(choose_type == "month"){ // 月
					// 设置当前显示界面的开始和结束时间
					$('#' + id1).val(showtime);
					$('#' + id2).val(showtime);
					temp_tfrom = dp.cal.getDateStr("yyyy-MM") + "-01";
				} else {
					// 设置当前显示界面的开始和结束时间
					$('#' + id1).val(showtime);
					$('#' + id2).val(showtime);
					// 设置后序页面的开始和结束时间
					$('#from_time5').val(showtime);
					$('#to_time5').val(showtime);
				}
				
			}
		});
		
	}catch(e) {
		console.log(e);
	}
	
}

// 加载对应的数据
function loadData(type){
	var html = '<table align="center" class="form_detail table  table-hover  table-bordered">',content = ''; // 内容
	var loop = 24; // 循环次数
	if(type == 'day'){
		loop = 24;
	} else if(type == 'week'){
		loop = 7;
	} else if(type == 'month'){
		loop = returnMonthLastDay($('#from_time5').val());
	} else if(type == 'year'){
		loop = 12;
	}
	// 第一行数据开始
	var firstRow = '<tr><th style="width: 70px;">日期</th>';
	if(type == 'day'){ // 如果类型是日，那么增加一列时间
		firstRow += '<th style="width: 70px;">时间</th>';
	}
	
	var columnList = [];
	if(temp_query_num == '2'){ // 建筑总用电报表
		$(".list_title").text("建筑总用电报表");
		columnList = ['空调用电量(kWh)','照明用电量(kWh)','消防用电量(kWh)','给排水用电量(kWh)','电梯用电量(kWh)','数据中心用电量(kWh)','其他用电量(kWh)'];
	} else if(temp_query_num == '3'){ // 空调总用电报表
		$(".list_title").text("空调总用电报表");
		columnList = ['冷机用电量(kWh)','水泵用电量(kWh)','冷却塔用电量(kWh)','空调末端用电量(kWh)','空调系统总用电量(kWh)'];
	} else if(temp_query_num == '4'){ // 冷机总用电报表
		$(".list_title").text("冷机总用电报表");
		columnList = ['冷机制冷量(kW)','冷机COP','冷冻水输运系数','冷却水输运系数','空调末端能效比'];
	} else {
		$(".list_title").text("建筑总能耗报表");
		columnList = ['建筑总用电(kWh)','建筑总用气（m³）','建筑总用水（kg)','建筑总用能（kgce)'];
	} 
	for(var i=0;i<columnList.length;i++){ // 第一行标题
		firstRow += '<th style="width: 70px;">' + columnList[i] + '</th>';
	}

	console.log("type--" + type);
	// 先循环行数，再循环列数
	for(var k=0;k<loop;k++){
		content += "<tr align='center'>";
		
		if(type == 'day'){ // 日
			content += '<td>'+ $('#from_time5').val() +'</td><td>'+ (k + ':00') +'</td>';
		} else if(type == 'week'){// 周
			content += '<td>'+ getTimeByDays($('#from_time5').val(),k) +'</td>';
		} else if(type == 'month'){// 月
//			console.log((k+1)+"--"+temp_tfrom);
			content += '<td>'+ getTimeByDays(temp_tfrom,k) +'</td>';
		} else if(type == 'year'){// 年
			content += '<td>'+ $('#from_time5').val() + "-" + (((k+1)+"").length == 1 ? ("0" + (k+1)) : (k+1)) +'</td>';
		}
		
		for(var i=0;i<columnList.length;i++){
			content += '<td>'+ randomValue(200) +'</td>';
		}
		content += "</tr>";
	}
	
	firstRow += '</tr>'; // 第一行标题 结尾
	html = html + firstRow + content;;
	html += '</table><br/><br/><br/>';
	//console.log("html--"+html);
	$('#reportdiv').html('');
	$('#reportdiv').html(html);
}

// 随机一个val范围内的数
function randomValue(max){
	return Math.floor(Math.random()*parseInt(200) + parseInt((max == '' ? 0 : max)));
}

// 根据给出的时间s往后推d天，例如：s=2013-10-13，d=2，则返回2013-10-15
function getTimeByDays(s,d)
{
	var Nowdate = new Date(Date.parse(s.replace(/-/g, '/')));
	var WeekFirstDay=new Date(Nowdate.getTime() +(d*86400000));
	var day = (WeekFirstDay.getDate() + "");
	day = day.length == 1 ? ("0" + day) : day;
	var month = ((WeekFirstDay.getMonth()+1) + "");
	month = month.length == 1 ? ("0" + month) : month;
	t = [WeekFirstDay.getFullYear(), month, day ];
	//console.log(d+"--"+Nowdate.getDay()+"--"+t.join('-'));
	return t.join('-');
}

// 显示当前日期对应的周的第一天
function showWeekFirstDay(s)
{
	var Nowdate = new Date(Date.parse(s.replace(/-/g, '/')));
	var WeekFirstDay=new Date(Nowdate-((Nowdate.getDay() == 0 ? 7 : Nowdate.getDay())-1)*86400000);
	var day = (WeekFirstDay.getDate() + "");
	day = day.length == 1 ? ("0" + day) : day;
	var month = ((WeekFirstDay.getMonth()+1) + "");
	month = month.length == 1 ? ("0" + month) : month;
	t = [WeekFirstDay.getFullYear(), month, day ];
	return t.join('-');
}

// 显示当前日期对应的周的最后一天
function showWeekLastDay(s)
{
	var Nowdate = new Date(Date.parse(s.replace(/-/g, '/')));
	var WeekFirstDay=new Date(Nowdate-((Nowdate.getDay() == 0 ? 7 : Nowdate.getDay())-1)*86400000);
	var WeekLastDay=new Date((WeekFirstDay/1000+6*86400)*1000);	
	var day = (WeekLastDay.getDate() + "");
	day = day.length == 1 ? ("0" + day) : day;
	var month = ((WeekLastDay.getMonth()+1) + "");
	month = month.length == 1 ? ("0" + month) : month;
	t = [WeekLastDay.getFullYear(), month, day ];
	return t.join('-');
}

// 当前选择的时间当月的最后一天（例如：当前s为2013-08-22(22为8月的随意一天都可)返回2013-08-31）
function showMonthLastDay(s)
{
	var d = new Date(Date.parse(s.replace(/-/g, '/')));
	var nextMonthFirstDay=new Date(d.getFullYear(),d.getMonth()+1,1);
	var result=new Date(nextMonthFirstDay-86400000);
	var month = ((result.getMonth()+1) + "");
	month = month.length == 1 ? ("0" + month) : month;
	t = [result.getFullYear(), month, result.getDate() ];
	return t.join('-');
}

// 当前选择的时间当月的最后一天（例如：当前s为2013-08-22(22为8月的随意一天都可)返回2013-08-31）
function returnMonthLastDay(s)
{
	var d = new Date(Date.parse(s.replace(/-/g, '/')));
	var nextMonthFirstDay=new Date(d.getFullYear(),d.getMonth()+1,1);
	var result=new Date(nextMonthFirstDay-86400000);
	return result.getDate();
}