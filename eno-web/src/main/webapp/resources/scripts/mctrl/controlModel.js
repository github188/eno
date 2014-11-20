/*******************************************************************************
 * 监测与控制
 * 
 */
var host = "http://192.168.1.50";
//暖通空调
var AirHeat = {
	getVal : function() {
		document.getElementById("para_box_lj").innerHTML = "";
		var url = 'http://192.168.1.50:8080/tag/history/monthreport/list?tagid=45&tfrom=2013-01-01&callback=?';
		console.log(url);
		$.getJSON(url, function(data) {
			var para_box_lj = '<p class="current_run_monitor" >'+data[0].tagid+'</p><p class="total_moitors">共'+data[0].tagid+'台</p>';
			$("#para_box_lj").append(para_box_lj);
		});
	}
};

var PublicLight = {
	getVal : function() {
		document.getElementById("para_box_green_qy").innerHTML = "";
		var url = 'http://192.168.1.50:8080/tag/history/monthreport/list?tagid=45&tfrom=2013-01-01&callback=?';
		console.log(url);
		$.getJSON(url, function(data) {
			var para_box_green_qy = '<p class="current_run_monitor" >'+data[0].tagid+'</p><p class="total_moitors">共'+data[0].tagid+'台</p>';
			$("#para_box_green_qy").append(para_box_green_qy);
		});
	}
};
//变配电
var ElectricTran = {
	getVal : function(tagid) {
		document.getElementById("outdoor_temp_para_dl").innerHTML = "";
		var url = 'http://192.168.1.50:8080/tag/history/monthreport/list?tagid=45&tfrom=2013-01-01&callback=?';
		console.log(url);
		$.getJSON(url, function(data) {
			var outdoor_temp_para_dl = '<span class="para_value form1">'+data[0].tagid+'</span><span class="para_unit">&nbsp;A</span>';
			$("#outdoor_temp_para_dl").append(outdoor_temp_para_dl);
		});
	}
};
//给水排水
var GandWater = {
	getVal : function(tagid) {
		document.getElementById("para_box_green_qy").innerHTML = "";
		var url = 'http://192.168.1.50:8080/tag/history/monthreport/list?tagid=45&tfrom=2013-01-01&callback=?';
		console.log(url);
		$.getJSON(url, function(data) {
			var para_box_green_qy = '<p class="current_run_monitor" >'+data[0].tagid+'</p><p class="total_moitors">共'+data[0].tagid+'台</p>';
			$("#para_box_green_qy").append(para_box_green_qy);
		});
	}
};
//消防监控
var FireProtec = {
	getVal : function(tagid) {
		document.getElementById("para_box_green_qy").innerHTML = "";
		var url = 'http://192.168.1.50:8080/tag/history/monthreport/list?tagid=45&tfrom=2013-01-01&callback=?';
		console.log(url);
		$.getJSON(url, function(data) {
			var para_box_green_qy = '<p class="current_run_monitor" >'+data[0].tagid+'</p><p class="total_moitors">共'+data[0].tagid+'台</p>';
			$("#para_box_green_qy").append(para_box_green_qy);
		});
	}
};
//电梯运行
var LiftRun = {
	getVal : function(tagid) {
		document.getElementById("para_box_green_qy").innerHTML = "";
		var url = 'http://192.168.1.50:8080/tag/history/monthreport/list?tagid=45&tfrom=2013-01-01&callback=?';
		console.log(url);
		$.getJSON(url, function(data) {
			var para_box_green_qy = '<p class="current_run_monitor" >'+data[0].tagid+'</p><p class="total_moitors">共'+data[0].tagid+'台</p>';
			$("#para_box_green_qy").append(para_box_green_qy);
		});
	}
};
//停车管理
var ParkingManage = {
	getVal : function(tagid) {
		document.getElementById("para_box_green_qy").innerHTML = "";
		var url = 'http://192.168.1.50:8080/tag/history/monthreport/list?tagid=45&tfrom=2013-01-01&callback=?';
		console.log(url);
		$.getJSON(url, function(data) {
			var para_box_green_qy = '<p class="current_run_monitor" >'+data[0].tagid+'</p><p class="total_moitors">共'+data[0].tagid+'台</p>';
			$("#para_box_green_qy").append(para_box_green_qy);
		});
	}
};
//信息发布
var InformationPub = {
	getVal : function(tagid) {
		document.getElementById("para_box_green_qy").innerHTML = "";
		var url = 'http://192.168.1.50:8080/tag/history/monthreport/list?tagid=45&tfrom=2013-01-01&callback=?';
		console.log(url);
		$.getJSON(url, function(data) {
			var para_box_green_qy = '<p class="current_run_monitor" >'+data[0].tagid+'</p><p class="total_moitors">共'+data[0].tagid+'台</p>';
			$("#para_box_green_qy").append(para_box_green_qy);
		});
	}
};
//客流统计
var PassengerFlow = {
	getVal : function(tagid) {
		var url = host + "/rtdbac?q=getval&tagid=" + tagid;
		console.log(url);
		var result = null;
		$.getJSON(url, function(data) {
			result = data;
		});
		return result;
	}
};
//背景音乐
var BackgroudMusic = {
		getVal : function(tagid) {
			document.getElementById("para_box_green_qy").innerHTML = "";
			var url = 'http://192.168.1.50:8080/tag/history/monthreport/list?tagid=45&tfrom=2013-01-01&callback=?';
			console.log(url);
			$.getJSON(url, function(data) {
				var para_box_green_qy = '<p class="current_run_monitor" >'+data[0].tagid+'</p><p class="total_moitors">共'+data[0].tagid+'台</p>';
				$("#para_box_green_qy").append(para_box_green_qy);
			});
		}
};