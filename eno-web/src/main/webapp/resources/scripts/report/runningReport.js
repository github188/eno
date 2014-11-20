var pageSize = 10;//每页显示的记录条数
var curPage=0;
var len;
var page;
$(function(){
	var date = new Date().Format("yyyy-MM-dd");
	$("#from_time").val(date);
	$("#to_time").val(date);

	// 点击输出按钮触发的事件
	$('.output').click(function(){
		expDataToExcel($('#from_time').val(), $('#to_time').val(), $('#select_time_type option:selected').val());
	});
	
	$("body").on("click", '#reportdiv>table>tbody>tr', function(){
		$(this).siblings().removeClass("highlight");
		$(this).addClass("highlight");
	});
	getSystemNames();
	
	
	/**--------- 分页操作-------*/
    curPage=1;
    $("#btn_first").click(function(){
     curPage=1;
     displayPage(curPage);
	 });
    $("#btn_prev").click(function(){
    	curPage -=1;
	     displayPage(curPage);
	 });
    $("#btn_next").click(function(){
    	curPage +=1;
	     displayPage(curPage);
	 });
    $("#btn_last").click(function(){
	     curPage=page;
	     displayPage(curPage);
	 });
	/**--------- 分页操作  end-------*/
});
// 分页
function displayPage(num){
	if(num<1){
		curPage = 1;
		alert("已经是第一页了");
		return;
	}
	if(num > page) {
		curPage = page;
		if(page > 0){
			alert("已经是最后一页了");
		}
		return;
	}
	curPage = num;
	$("#cur_page").val(curPage);
	var begin = (curPage-1)*pageSize;//起始记录号
	var end = begin + pageSize;
    $("#reportdiv table tbody tr").hide();
    $("#reportdiv table tbody tr").each(function(i){
        if(i>=begin && i<end)//显示第page页的记录
            $(this).show();
    });
	
}

//获取子系统列表
function getSystemNames(){
	var url = CONTEXT_PATH + '/reportConfig/getSystemList';
	$.ajax({
		type : "GET",
		url : url,
		async : false, //同步
		success : function(result) {
			try{
				var optionHtml = "";
				for(var i=0;i<result.length;i++){
					optionHtml += "<option>" + result[i] +"</option>";
				}
				$("#system_type").append(optionHtml);
				var system_type = $("#system_type option:selected").val();
				getDeviceTypeNames(system_type);
			} catch (e) {
				console.log('getSystemNames---error--' + e);
			}
		},
		error : function(result) {
			console.log('error---');
		}
	});
}
// 获取设备类型
function getDeviceTypeNames(systemType){
	var url = CONTEXT_PATH + '/reportConfig/getDeviceType';
	$.ajax({
		type : "GET",
		data : {
			system : systemType
		},
		url : url,
		async : false, //同步
		success : function(result) {
//			console.log(result);
			try{
				var optionHtml = "";
				for(var i=0;i<result.length;i++){
					optionHtml += "<option>" + result[i] +"</option>";
				}
				$("#device_types").empty().append(optionHtml);
				var deviceType = $("#device_types option:selected").val();
				getDevices(systemType, deviceType);
			} catch (e) {
				console.log('getDeviceType---error--' + e);
			}
		},
		error : function(result) {
			console.log('error---');
		}
	});
}
//获取设备
function getDevices(systemType, deviceType){
	var url = CONTEXT_PATH + '/reportConfig/getDeviceList';
	$.ajax({
		type : "GET",
		data : {
			system : systemType,
			devicetype : deviceType
		},
		url : url,
//		async : false, //同步
		success : function(result) {
//			console.log(result);
			try{
				var optionHtml = "";
				for(var i=0;i<result.length;i++){
					optionHtml += "<option>" + result[i] +"</option>";
				}
				$("#devices").empty().append(optionHtml);
				queryReport();
			} catch (e) {
				console.log('getDevices---error--' + e);
			}
		},
		error : function(result) {
			console.log('error---');
		}
	});
}

// select 改变事件
function changeSelect(type) {
	if(type == 'system') {
		getDeviceTypeNames($("#system_type option:selected").val());
	}else if(type == 'deviceType') {
		var system = $("#system_type option:selected").val();
		var deviceType = $("#device_types option:selected").val();
		getDevices(system, deviceType);
	}
}
//点击查询按钮触发的事件
function queryReport() {
	commonColumnList = [];
	var sTime = $('#from_time').val(); // 获取开始时间
	if (sTime == "") {
		alert("请选择要查询的时间段！");
		return false;
	}
	if(temp_tfrom != '') {
		rsTime = temp_tfrom;
	}else{
		rsTime = sTime;
	}
	var system = $("#system_type option:selected").val();
	var deviceType = $("#device_types option:selected").val();
	var device = $("#devices option:selected").val();
	var select_time_type = $('#select_time_type option:selected').val(); // 获取当前选择的查询类型（日、周、月）
	rSystemType = system;
	rDeviceType = deviceType;
	rDevice = device;
	rDateType = select_time_type;
	$('#current_search_date').html('').html(sTime);
	
	var url = CONTEXT_PATH + '/reportConfig/getReportconfigsList';
	modelCode = device;
	var name = new Array(), id = new Array(), ispd = new Array();
	$.ajax({
		type : "GET",
		data : {
			system : rSystemType,
			devicetype : rDeviceType,
			device : rDevice
		},
		url : url,
//		async : false, //同步
		success : function(result) {
//			console.log(result);
			try{
				commonColumnList = [];
				for(var i=0;i<result.length;i++){
					commonColumnList.push(result[i].params+"("+result[i].unit+")");
					name.push(result[i].name);
					id.push(result[i].id);
					ispd.push(result[i].ispd);
				}
				loadData(name.join(","),id.join(","),ispd.join(","), rsTime, select_time_type);
			} catch (e) {
				console.log('getSystemNames---error--' + e);
			}
		},
		error : function(result) {
			console.log('error---');
		}
	});
	
	
//	ipaddress = "127.0.0.1", port = "8087";
};
//加载对应的数据
function loadData(name, id, ispd, rsTime, type){
	$('#reportdiv').html("暂无数据！");
	var url = CONTEXT_PATH + '/Chart/getReportDataList';
    $.ajax({
        type : "POST",
        url : url,
		data : {
			name : name,
			id : id,
			ispd : ispd,
			tfrom : rsTime,
			tto : (type == 'day' ? getTimeByDays(rsTime, 1) : (type == 'year' ? (parseInt(rsTime.substring(0,4)) + 1) + "-01-01" : (type == 'week' ? getTimeByDays(rsTime, 6) : (type == 'month' ? returnNextMonthfirstDay(rsTime):'')))),
			type : type,
			decimals : '0.0',
			ipaddress : ipaddress,
			port : port,
			isNotStatic : rIsNotStatic
		},
        success : function(data) {
			var showCataList = data.showCataList;
			var showDataList = data.showDataList;

			// 最后赋值的html变量
			var html = '<table class="table_style1 sub_table_style1">';
			var firstRow = '<thead><tr><th>日期</th>';
			var content = ''; // 中间的内容数据
			
			// 报表的时间类型（day/week/month/year）
			if(type == "day"){// 日报的处理方式
				firstRow += '<th>时间</th>';
			}
			// 处理报表列头信息
			for(var i = 0; i < commonColumnList.length; i++){
				firstRow += '<th>'+commonColumnList[i]+'</th>';
			}
			firstRow += '</tr></thead><tbody>';
			
			var dataSize = showCataList[0].length;
			// 循环添加内容数据
			for(var i=0;i<dataSize;i++){
				content += "<tr class='' align='center'>" + (type == "day" ? ("<td>" + rsTime + "</td>") :"") + "<td>" + (showCataList[0][i] == null || showCataList[0][i] == 'null' ? rsTime : showCataList[0][i]) + "</td>";
				for(var k = 0; k < commonColumnList.length; k++){
					content += '<td>'+ (showDataList[k][i] != undefined ? showDataList[k][i] : '') +'</td>';
				}
				content += "</tr>";
			}
			
			html = html + firstRow + content;
			
			html += '</tbody></table>';
		    $('#reportdiv').html(html);
			len =$("#reportdiv table tbody tr").length;
		    page=len % pageSize==0 ? len/pageSize : Math.floor(len/pageSize)+1;//根据记录条数，计算页数
		    $('#page_num_1').html('of '+page);
		    $('#page_num_2').html(page);
		    displayPage(1);//显示第一页
        },
        error : function(result) {
            console.log('error');
        }
    });
}

// 输出对应的数据到excel中
function expDataToExcel(){
	var commonColumnList = [];
	var url = CONTEXT_PATH + '/reportConfig/getReportconfigsList';
	var name = new Array(), id = new Array(), ispd = new Array();
	$.ajax({
		type : "GET",
		data : {
			system : rSystemType,
			devicetype : rDeviceType,
			device : rDevice
		},
		url : url,
//		 async : false, //同步
		success : function(result) {
			console.log(result);
			try{
				for(var i=0;i<result.length;i++){
					commonColumnList.push(result[i].params+"("+result[i].unit+")");
					name.push(result[i].name);
					id.push(result[i].id);
					ispd.push(result[i].ispd);
				}
				var sheetname = rDevice; 
				var hSrc = CONTEXT_PATH + "/Chart/outputToExcel?name=" + name + "&id=" + id + "&ispd=" + ispd + "&tfrom=" + rsTime + "&tto=" + (rDateType == 'day' ? getTimeByDays(rsTime, 1) : (rDateType == 'year' ? (parseInt(rsTime.substring(0,4)) + 1) + "-01-01" : '')) + 
				"&type=" + rDateType + "&decimals=" + '0.0&ipaddress=' + ipaddress + "&port=" + port + "&sheetname=" + encodeURIComponent(sheetname) + "&commonColumn=" + encodeURIComponent(commonColumnList.join()) + "&isNotStatic=" + rIsNotStatic;
				window.open(hSrc);
			} catch (e) {
				console.log('getSystemNames---error--' + e);
			}
		},
		error : function(result) {
			console.log('error---');
		}
	});
}
