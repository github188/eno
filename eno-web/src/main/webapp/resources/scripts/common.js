$(function(){

    //用户管理下拉
    $(".login_user").on("click", function() {
        if($(this).hasClass("showDown")) {
            $(this).removeClass("showDown").addClass("hideUp");
        } else {
            $(this).removeClass("hideUp").addClass("showDown");
        }
        $(".login_user_box").slideToggle("slow");
    });

	//二级菜单切换
	$(".js_subnav ul li").click(function(){
		$(this).siblings().removeClass("js_subnav_current");
		$(this).addClass("js_subnav_current");
		$(".js_tab_content").hide().eq($(".js_subnav ul li").index(this)).show();
	});

    //能源管理一宫格、六宫格、九宫格切换
    $(".js_Echart_nav li").click(function(){
        $(this).siblings().removeClass("js_Echart_nav_current");
        $(this).addClass("js_Echart_nav_current");
        $(this).parent().siblings(".js_Echart_content").hide();
        $(this).parent().parent().find(".js_Echart_content:eq("+ $(this).index() +")").show();
    });
	
	$(".Echart_sidebar_subcon>ul>li").click(function(){
		$(this).siblings().removeClass("selecton");
        $(this).addClass("selecton");
	});
    //设备管理预防性维护 左侧菜单切换
    $(".device_sidebar_subcon ul li").click(function(){
        $(this).siblings().removeClass("selecton");
        $(this).addClass("selecton");
        $(".device_main").hide().eq($(".device_sidebar_subcon ul li").index(this)).show();
    });
    //下拉列表
    $('.selectpicker').selectpicker({
        "width" : "130px"
    });
    $('.selectpicker_w150').selectpicker({
        "width" : "150px"
    });
    $('.selectpicker_w197').selectpicker({
        "width" : "197px"
    });
    $('.selectpicker_w218').selectpicker({
        "width" : "218px"
    });
    //运行监测二级页面左侧菜单
    $(".main_menu>ul>li").on("click", function(){
        $(this).siblings().removeClass("main_menu_current");
        $(this).addClass("main_menu_current");
    });

    $(".second_menu li").click(function(){
        var subCon=$(this);
        if(subCon.hasClass('on')){
            subCon.find('.subcon').slideUp('fast',function(){
                subCon.removeClass('on');
            });
        }else{
            subCon.find('.subcon').slideDown('fast',function(){
                subCon.addClass('on');
            });
            subCon.siblings().removeClass('on').find('.subcon').slideUp('fast');
        }
    });
    $(".second_menu>ul>li").on("click", function(){
        $(this).siblings().removeClass("on");
        $(this).addClass("on");
    });

    // li标签下增加点击事件
    $(".second_menu a").click(function(){
        var subCon=$(this).addClass("focus");
        $(this).siblings().removeClass('focus');
        return false;
    });
    //暖通空调结构图点击弹框事件
    $(".tooltipBox:has('.tooltip_showBox')").click(function(){
        $(this).children(".tooltip_showBox").stop(true,true).show();
    });
    $(".tooltip_closeBtn").click(function(){
        $(".tooltip_showBox").hide();
        return false;
    });
    //日周月年视图的点击事件
    $(".viewList>li:not(.compare)").click(function(){
        $(this).siblings().removeClass("changeColor");
        $(this).addClass("changeColor");

        var dtype = $(this).attr("dtype"); // 获取li标签的dtype属性
        var format = "yyyy-MM-dd";
        if (dtype == "day") {
            format = "yyyy-MM-dd";  //日视图日历格式为yyyy-MM-dd显示
        } else if (dtype == "week") {
            format = "yyyy-MM-dd";  //周视图日历格式为yyyy-MM-ww显示
        } else if (dtype == "month") {
            format = "yyyy-MM";     //年视图日历格式为yyyy显示
        } else if (dtype == "year") {
            format = "yyyy";
        } else if (dtype == "all") {
            format = "yyyy-MM-dd";
        }
//        console.log(format);
        chooseTime(format, dtype, $(this).parent().attr("func"));
    });
    $(".viewList_language li").click(function(){
        $(this).siblings().removeClass("changeColor");
        $(this).addClass("changeColor");
    });
//    $(".yminput").attr("readOnly",true);

    //设备管理点击编辑/删除前边出现编辑/删除按钮
    $(".btn_edit").on("click", function() {
        if($(this).hasClass("btn_green")) {
            $(this).removeClass("btn_green").addClass("btn_grey");
            $(".tableList .edit").hide();
            $(".tableList .delete").hide();
        } else {
            $(this).removeClass("btn_grey").addClass("btn_green");
            $(this).siblings(".btn_delete").removeClass("btn_green").addClass("btn_grey");
            $(".tableList .edit").show();
            $(".tableList .delete").hide();
        }
    });
    $(".btn_delete").on("click", function() {
        if($(this).hasClass("btn_green")) {
            $(this).removeClass("btn_green").addClass("btn_grey");
            $(".tableList .delete").hide();
            $(".tableList .edit").hide();
        } else {
            $(this).removeClass("btn_grey").addClass("btn_green");
            $(this).siblings(".btn_edit").removeClass("btn_green").addClass("btn_grey");
            $(".tableList .delete").show();
            $(".tableList .edit").hide();
        }
    });

    //运行监测二级页面三种视图切换
    $(".js_system_viewBar>ul>li").click(function(){
        $(this).siblings().removeClass("js_system_viewBar_on");
        $(this).addClass("js_system_viewBar_on");
        $(".js_system_content").hide().eq($(".js_system_viewBar>ul>li").index(this)).show();
    });

	//报表内容展开收起

    $(".triangle_show").on("click", function() {
        if($(this).hasClass("triangle_hide")) {
            $(this).removeClass("triangle_hide").addClass("triangle_show");
            $(".tableBox table").css("height","600px");
            $(".tableBox .reporF_noCon").css("height","440px");
        } else {
            $(this).removeClass("triangle_show").addClass("triangle_hide");
            $(".tableBox table").css("height","726px");
            $(".tableBox .reporF_noCon").css("height","566px");
        }
        $(".reportForm_category").slideToggle("slow");
    });

    //定额管理的配置定额 历史数据展开收起
    $(".export_showBtn").on("click", function() {
        if($(this).hasClass("export_hideBtn")) {
            $(this).removeClass("export_hideBtn").addClass("export_showBtn");
             //console.log("has");
        } else {
             //console.log("nohas");
            $(this).removeClass("export_showBtn").addClass("export_hideBtn");
        }
        $(".history_showhide").slideToggle("slow");
        //console.log(event);
    });
    
    setInterval("GetTime()", 1000); // 动态设置时间和星期
    
});

/**
 * 动态设置日期时间和星期
 */
function GetTime() {
    var mon, day, now, hour, min, sec;
    mon = new Array("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
    day = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");
    now = new Date();
    hour = now.getHours();
    min = now.getMinutes();
    sec = now.getSeconds();
    if (hour < 10) {
        hour = "0" + hour;
    }
    if (min < 10) {
        min = "0" + min;
    }
    if (sec < 10) {
        sec = "0" + sec;
    }

    var time = hour + ":" + min;
    var date = now.getFullYear() + "-" + mon[now.getMonth()] + "-" + now.getDate();
    var week = day[now.getDay()];

    if ($("#global_time").length) {
        $("#global_time").html(time);
    }
    if ($("#global_date").length) {
        $("#global_date").html(date);
    }
    if ($("#global_week").length) {
        $("#global_week").html(week);
    }
}

// 获取当前时间，格式为：2013-10-09
function getCurrentTime() {
	var Nowdate = new Date();
	var day = (Nowdate.getDate() + "");
	day = day.length == 1 ? ("0" + day) : day;
	var month = ((Nowdate.getMonth() + 1) + "");
	month = month.length == 1 ? ("0" + month) : month;
	t = [Nowdate.getFullYear(), month, day];
	return t.join('-');
}

// 根据给出的时间s往后推d天，例如：s=2013-10-13，d=2，则返回2013-10-15
function getTimeByDays(s, d) {
	var Nowdate = new Date(Date.parse(s.replace(/-/g, '/')));
	// var WeekFirstDay=new Date(Nowdate-((Nowdate.getDay() == 0 ? 7 :
	// Nowdate.getDay())-d)*86400000);
	var WeekFirstDay = new Date(Nowdate.getTime() + (d * 86400000));
	var day = (WeekFirstDay.getDate() + "");
	day = day.length == 1 ? ("0" + day) : day;
	var month = ((WeekFirstDay.getMonth() + 1) + "");
	month = month.length == 1 ? ("0" + month) : month;
	t = [WeekFirstDay.getFullYear(), month, day];
	return t.join('-');
}

// 显示当前日期对应的周的第一天
function showWeekFirstDay(s) {
	var Nowdate = new Date(Date.parse(s.replace(/-/g, '/')));
	var WeekFirstDay = new Date(Nowdate
			- ((Nowdate.getDay() == 0 ? 7 : Nowdate.getDay()) - 1) * 86400000);
	var day = (WeekFirstDay.getDate() + "");
	day = day.length == 1 ? ("0" + day) : day;
	var month = ((WeekFirstDay.getMonth() + 1) + "");
	month = month.length == 1 ? ("0" + month) : month;
	t = [WeekFirstDay.getFullYear(), month, day];
	return t.join('-');
}

// 显示传递日期对应的下个月的第一天
function showNextMonthFirstDay(s) {
	var Nowdate = new Date(Date.parse(s.replace(/-/g, '/')));
	Nowdate.setMonth(Nowdate.getMonth() + 1); // 当前月份往后推一个月
	var month = ((Nowdate.getMonth() + 1) + "");
	month = month.length == 1 ? ("0" + month) : month;
	t = [Nowdate.getFullYear(), month, "01"];
	return t.join('-');
}

// 显示当前日期对应的周的最后一天
function showWeekLastDay(s) {
	var Nowdate = new Date(Date.parse(s.replace(/-/g, '/')));
	var WeekFirstDay = new Date(Nowdate
			- ((Nowdate.getDay() == 0 ? 7 : Nowdate.getDay()) - 1) * 86400000);
	var WeekLastDay = new Date((WeekFirstDay / 1000 + 6 * 86400) * 1000);
	var day = (WeekLastDay.getDate() + "");
	day = day.length == 1 ? ("0" + day) : day;
	var month = ((WeekLastDay.getMonth() + 1) + "");
	month = month.length == 1 ? ("0" + month) : month;
	t = [WeekLastDay.getFullYear(), month, day];
	return t.join('-');
}


// 选择时间
function chooseTime(format, dtype, func) {
    WdatePicker({
        el : 'hidden_date',	//只操作这个日历控件
        dateFmt : format, //定义时间的格式
        onpicked : function(dp) {
            // 具体的对比时间，格式为（yyyy-MM-dd），此时间会传递到后台
            var paretime= dp.cal.getDateStr(format);
            
            var start = '', end = '', querytype = dtype, step = '4', timeformat = 'HH'; // 开始、结束时间、查询类型、图表间隔、时间格式
            var catalist = [];
            if (dtype == "day") {
	            start = paretime, end = getTimeByDays(start, 1), querytype = 'hour', step = '4', timeformat = 'HH';
	            title = start;
	        } else if (dtype == "week") {
	            start = showWeekFirstDay(paretime), end = showWeekLastDay(paretime), querytype = 'day', step = '1', timeformat = 'yyyy-MM-dd';
	            title = start + " ~ " + end;
	            catalist = ['周一', '周二', '周三', '周四', '周五', '周六', '周日'];
	        } else if (dtype == "month") {
	            start = dp.cal.getDateStr("yyyy-MM-dd"), end = showNextMonthFirstDay(paretime), querytype = 'day', step = '4', timeformat = 'dd';
	            title = paretime;
	        } else if (dtype == "year") {
	            start = dp.cal.getDateStr("yyyy-MM-dd"), end = (parseInt(paretime) + 1) + "-01-01", querytype = 'month', step = '4', timeformat = 'yyyy-MM';
	            title = paretime;
	        } else if (dtype == "all") {
	        	
	        }
	        
//            console.log(func + "--start--" + start + "----end----" + end + "----dtype----" + dtype + "----querytype----" + querytype + "----step----" + step);
            if (func == "SexEnergyTotal") { // 六宫格-1.建筑总能耗图表
            	renderMoreCharts(buildChart.buildEnergySexEnergyTotal, sexEnergyTotalName, title, start, end, querytype, step, catalist, timeformat);
            } else if (func == "SexEnergySubentry") { // 六宫格-2.建筑总能耗分项
            	renderPieCharts(buildChart.buildEnergySexEnergySubentry, sexEnergySubentry, sexEnergySubentrySum, start, end, querytype);
            } else if (func == "SexElectricityTotal") { // 六宫格-3.建筑总能耗图表
            	renderMoreCharts(buildChart.buildEnergySexElectricityTotal, sexElectricityTotalName, title, start, end, querytype, step, catalist, timeformat);
            } else if (func == "SexHvacSubentry") { // 六宫格-4.空调系统用电分项
            	renderPieCharts(buildChart.buildEnergySexHvacSubentry, sexHvacSubentry, sexHvacSubentrySum, start, end, querytype);
            } else if (func == "SexDataCenter") {// 生成六宫格-5.数据中心用电图表
            	renderMoreCharts(buildChart.buildEnergySexDataCenter, sexDataCenterName, title, start, end, querytype, step, catalist, timeformat);
            } else if (func == "SexDeviceEnergy") { // 生成六宫格-6.建筑设备能耗图表
            	renderMoreCharts(buildChart.buildEnergySexDeviceEnergy, sexDeviceEnergyName, title, start, end, querytype, step, catalist, timeformat);
            } else if (func == "NineEnergy") { // 生成九宫格图表
            	buildEnergyNineChart(title, start, end, querytype, step, catalist, timeformat);
            }
            
        }
    });
}
