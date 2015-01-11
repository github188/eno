var currentFloor = 'B1'; // 默认显示B1层数据
var trendMap = new HashMap(); // 右下角客流趋势
var posMap = new HashMap(), floorMap = new HashMap(), hundredMap = new HashMap();
var hundredList = [ '百货一层', '百货二层', '百货三层', '百货四层', '百货五层', '百货六层'];
var background1 = "#68C8CA", background2 = "#B9B9B9"; // 声明两个图标的背景颜色
for (var i = 1; i <= 6; i++) {
    floorMap.put("F" + i, "F" + i);
    hundredMap.put("F" + i, hundredList[i - 1]);
}
$(function(){
	var currentFloor = $(".cataloge_class_three .current a").text();
	// 设置左上角的楼层信息
	$(".f_n").text(currentFloor);
	// 修改截止时间
	$(".cur_time").text(new Date().Format("hh:mm"));
	
	getWorkDayAndHoliDayAveragePassengerOfDay(); // 获取工作日和节假日日均客流
	getTotalPassengerFlowHour(); // 获取每小时的总客流
});

//获取工作日和节假日日均客流
function getWorkDayAndHoliDayAveragePassengerOfDay() {
	var url = baseurl + '/pfe/findWorkDayAndHoliDayAveragePassengerOfDay';
	$.ajax({
				type : "POST",
				url : url,
				success : function(result) {
					$("#avgHolidayCount").text(result.avgHolidayCount);
					$("#avgWorkDayCount").text(result.avgWorkDayCount);
				},
				error : function(result) {
					console.log('error');
				}
			});
}

//获取每小时的总客流
function getTotalPassengerFlowHour() {
	// 获取柱状图需要的数据
	var url = baseurl + '/pfe/getTotalPassengerFlowHour';
	$.ajax({
				type : "POST",
				url : url,
				success : function(result) {
					var totalInCount = 0, totalOutCount = 0, totalInSide = 0;
					try {
						var inDatalist = [], catalist = [], inSideDatalist = [], outDatalist = [];
						for (var i = 0; i < result.length; i++) {
							totalInCount = totalInCount + result[i].inCount;
							totalOutCount = totalOutCount + result[i].outCount;
//							totalInSide = totalInSide + (result[i].insidePerson);
							inDatalist.push(result[i].inCount);
							inSideDatalist.push(result[i].insidePerson);
							outDatalist.push(result[i].outCount);
							catalist.push(result[i].countTime);
						}
						
						// 以下是本地缓存
						trendMap.put("totalInCountChart", inDatalist);
						trendMap.put("totalOutCountChart", outDatalist);
						trendMap.put("totalInSideChart", inSideDatalist);
						trendMap.put("catalist", catalist);
						
						$("#totalInCount").text(totalInCount);
						$("#totalOutCount").text(totalOutCount);
						var insidecount = totalInCount - totalOutCount < 0 ? 0 : totalInCount - totalOutCount; // 场内人数如果小于0的话，则置为0
						$("#totalInSide").text(insidecount);
						staticPfeChart("todayChart", background1, false, inSideDatalist, catalist); // 渲染今日图表
						
					} catch (e) {
						console.log('error' + e);
					}
				},
				error : function(result) {
					console.log('error');
				}
			});
}
//生成图表
function staticPfeChart(id, color, isStatic, datalist, catalist) {
	// 客流量
//	var list = [0,0,0,0,0,0,0,73,100,120,140,200,180,130,300,400,460,460,370,350,180,100,73,0];
	if(isStatic) {
		catalist = [], datalist = [];
		var list = [50, 20, 30, 40, 50, 60, 100, 173, 300, 220, 140, 200, 180, 130,300,400,460,460,370,350,180,100,73,0];
		for(var i = 0; i < new Date().getHours(); i++) {
			catalist.push(i + ":00"); 
			datalist.push(list[i]);
		}
	}
	renderPassengerCharts(id, datalist, catalist, color); // 生成图表
}