var rIsNotStatic = true; // 主要定义报表数据 是否使用pdcheck数据 还是使用自定义数据源数据    1、false  使用pdcheck； 2 true 使用自定义数据源
var ipaddress = "127.0.0.1", port = "8087";
var commonColumnList = []; // 列头
var rSystemType = ''; // 全局 子系统
var rDeviceType = ''; // 全局 设备类型
var rDevice = ''; // 全局设备
var rDateType = ''; // 全局日期类型
var rsTime = ''; // 全局开始时间
var reTime = ''; // 全局结束时间
var temp_tfrom = '';// 全局日期开始（后台有效日期)
// 随机一个val范围内的数
function randomValue(max){
	return Math.floor(Math.random()*parseInt(200) + parseInt((max == '' ? 0 : max)));
}

//选择时间触发的事件
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
			dateFmt : dformt,
			maxDate : new Date,
			onpicked : function(dp) {
				var showtime = dp.cal.getDateStr(dformt);
				temp_tfrom = dp.cal.getDateStr('yyyy-MM-dd');

				// 周报的开始时间和结束时间需要做特殊处理
				if (choose_type == "week") {
					// 设置当前显示界面的开始和结束时间
					$('#' + id1).val(showWeekFirstDay(showtime));
					$('#' + id2).val(showWeekLastDay(showtime));
					// 设置后序页面的开始和结束时间
					$('#from_time').val(showWeekFirstDay(showtime));
					$('#to_time').val(showWeekLastDay(showtime));
					temp_tfrom = showWeekFirstDay(showtime);
				} else if(choose_type == "month"){ // 月
					// 设置当前显示界面的开始和结束时间
					$('#' + id1).val(showtime);
					$('#' + id2).val(showtime);
					temp_tfrom = dp.cal.getDateStr("yyyy-MM") + "-01";
				} else if(choose_type == "year"){ // 年
					// 设置当前显示界面的开始和结束时间
					$('#' + id1).val(showtime);
					$('#' + id2).val(showtime);
					// 设置后序页面的开始和结束时间
					$('#from_time').val(showtime);
					$('#to_time').val(showtime);
					temp_tfrom = dp.cal.getDateStr("yyyy") + "-01-01";
				} else { // 日
					// 设置当前显示界面的开始和结束时间
					$('#' + id1).val(temp_tfrom);
					$('#' + id2).val(temp_tfrom);
					// 设置后序页面的开始和结束时间
					$('#from_time').val(temp_tfrom);
					$('#to_time').val(temp_tfrom);
				}
				rsTime = temp_tfrom;
			}
		});
	}catch(e) {
		console.log(e);
	}
	
}
// 根据给出的时间s往后推d天，例如：s=2013-10-13，d=2，则返回2013-10-15
function getTimeByDays(s,d)
{
	var Nowdate = new Date(Date.parse(s.replace(/-/g, '/')));
	var WeekFirstDay = new Date(Nowdate.getTime() + (d * 86400000));
	var day = (WeekFirstDay.getDate() + "");
	day = day.length == 1 ? ("0" + day) : day;
	var month = ((WeekFirstDay.getMonth() + 1) + "");
	month = month.length == 1 ? ("0" + month) : month;
	t = [ WeekFirstDay.getFullYear(), month, day ];
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
	var nextMonthFirstDay = new Date(d.getFullYear(), d.getMonth() + 1, 1);
	var result = new Date(nextMonthFirstDay - 86400000);
	var month = ((result.getMonth() + 1) + "");
	month = month.length == 1 ? ("0" + month) : month;
	t = [ result.getFullYear(), month, result.getDate() ];
	return t.join('-');
}

// 当前选择的时间当月的最后一天（例如：当前s为2013-08-22(22为8月的随意一天都可)返回2013-08-31）
function returnMonthLastDay(s)
{
	var d = new Date(Date.parse(s.replace(/-/g, '/')));
	var nextMonthFirstDay = new Date(d.getFullYear(), d.getMonth() + 1, 1);
	var result = new Date(nextMonthFirstDay - 86400000);
	return result.getDate();
}
function returnNextMonthfirstDay(s)
{
	var d = new Date(Date.parse(s.replace(/-/g, '/')));
	var nextMonthFirstDay = new Date(d.getFullYear(), d.getMonth() + 1, 2);
	var result = new Date(nextMonthFirstDay - 86400000);
	var day = result.getDate()+'';
	day = day.length == 1 ? ("0" + day) : day;
	var month = ((result.getMonth() + 1) + "");
	month = month.length == 1 ? ("0" + month) : month;
	t = [ result.getFullYear(), month, day ];
	return t.join('-');
}
