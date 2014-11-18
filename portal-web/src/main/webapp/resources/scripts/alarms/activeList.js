/*
 * 实时报警页面js
 * 
 * @author zouzhixiang @comments 重新梳理报警相关代码
 * 
 * @version 1.0 build 20140905
 */
//实时报警点击左侧菜单重新加载页面内容
function showAlaramLog(para, id) {
	$(".left_menu tr td").attr("style","");
	$(id).parent().attr("style","background-color:#39a6ae;");

	headerdescription = para;
	pageDataRefresh(para);
}

/**
 * 更新页面数据
 * @param groupName 报警组名
 */
function pageDataRefresh(groupName){
	var url = 'alarmcurrfindByAlaram?groupname=' + encodeURIComponent(headerdescription);
	$.ajax({
		type: "GET",
		url: url,
		cache: false,
		success: function (data) {
//                    aft = data.endIndex;
			dataList1 = data.dataList1;
			dataList2 = data.dataList2;
			dataList3 = data.dataList3;
			cataList = data.cataList;
			zjs1=data.zjs1;
			zjs2=data.zjs2;
			zjs3=data.zjs3;
//                    endIndexInt=data.endIndexInt;
			$("#alarmpriority").val(data.alarmpriorityValue);
			var page = data.ucAlarmactives;//报警列表
			if(page!=null){
				var totalPages = page.totalPages;
				if(totalPages>0){
					$(".bc").text(1);
				}else{
					$(".bc").text(0);
				}
				$(".page_des2").text(totalPages);
				//加载列表数据
				$("#tbodyQz").html(getAlarmListHtml(page.content));
			}

			try {
				$('#num_one').text(zjs1);
				$('#num_two').text(zjs2);
				$('#num_three').text(zjs3);
//                        if (endIndexInt > 0) {
//                            $(".bc").text(1);
//                        } else {
//                            $(".bc").text(0);
//                        }
//                        qz.value = 1;

				$("#dialog-modal").dialog({
					width: "900px",
					autoOpen: false,
					show: "blind",
					hide: "explode",
					modal: true
				});

				$(".handle").live("click", function () {
					$("#dialog-modal").dialog("open");
					return false;
				});

				$(".cancel").click(function () {
					$("#dialog-modal").dialog("close");
				});
				$('#container').highcharts({
					chart: {
						type: 'areaspline'
					},
					title: {
						text: ''
					},
					legend: {
						enabled: false
					},
					xAxis: {
						categories: cataList
					},
					yAxis: {
						title: {
							text: ''
						}
					},
					tooltip: {
						crosshairs: true,
						shared: true,
						valueSuffix: ' '
					},
					credits: {
						enabled: false
					},
					plotOptions: {
						areaspline: {
							fillOpacity: 0.5
						}
					},
					series: [
						{
							name: '普通',
							color: '#26C3BC',
							fillColor: {
								linearGradient: {
									x1: 0,
									y1: 0,
									x2: 0,
									y2: 1
								},
								stops: [
									[0, '#DEECEC'],
									[
										1,
										Highcharts.Color('#DEECEC').setOpacity(0)
											.get('rgba')]
								]
							},
							marker: {
								lineWidth: 2,
								lineColor: '#26C3BC',
								fillColor: 'white',
								symbol: 'circle'
							},
							data: dataList3
						},
						{
							name: '严重',
							color: '#5CC8E4',
							fillColor: {
								linearGradient: {
									x1: 0,
									y1: 0,
									x2: 0,
									y2: 1
								},
								stops: [
									[0, '#D0EAEB'],
									[
										1,
										Highcharts.Color('#D0EAEB').setOpacity(0)
											.get('rgba')]
								]
							},
							marker: {
								lineWidth: 2,
								lineColor: '#5CC8E4',
								fillColor: 'white',
								symbol: 'circle'
							},
							data: dataList2
						},
						{
							name: '紧急',
							color: '#E66E4C',
							fillColor: {
								linearGradient: {
									x1: 0,
									y1: 0,
									x2: 0,
									y2: 1
								},
								stops: [
									[0, '#F0D7D0'],
									[
										1,
										Highcharts.Color('#F0D7D0').setOpacity(0)
											.get('rgba')]
								]
							},
							marker: {
								lineWidth: 2,
								lineColor: '#E66E4C',
								fillColor: 'white',
								symbol: 'circle'
							},
							data: dataList1
						}
					]
				});
			}catch (e){
				console.log("--showAlaramLog--"+e);
			}
		}
	});
}

//查询实时报警列表
function getAlarmcurrList(){
	var url = "alarmfindByalarmpriorityPage?alarmpriority=" + alarmpriority.value + "&pagef=1&groupname="
		+ encodeURIComponent(headerdescription);
	$.ajax({
		type: "GET",
		url: url,
		cache: false,
		success: function (data) {
			try {
				if(data!=null){
					var page = data.alarmPage;
					if (page != null) {
						var totalPages = page.totalPages;
						if(totalPages>0){
							$(".bc").text(1);
						}else{
							$(".bc").text(0);
						}
						$(".page_des2").text(totalPages);
						//加载列表数据
						$("#tbodyQz").html(getAlarmListHtml(page.content));
					}
					var tel = data.telnet;
					if (tel == 'Y') {
						$("#deviceShowStatus").attr("class",
							"alarms_right_text alarms_right_no");
						$("#deviceShowStatus").text("设备通讯故障");
					} else {
						$("#deviceShowStatus").attr("class",
							"alarms_right_text alarms_right_ok");
						$("#deviceShowStatus").text("设备通讯正常");
					}

					var alarmData = data.alarmAlert;
					$("#dialog-modal").dialog("close");
					if (alarmData!=null&&alarmData.groupname != null&&alarmData.groupname!="") {
						answer(alarmData.almlogid, alarmData.almt);
					}
				}

			} catch (e) {
				console.error("--alarmfindByalarmpriorityPage--"+e);
			}
		}
	});
}

$(function () {
	$('#num_one').text(zjs1);
	$('#num_two').text(zjs2);
	$('#num_three').text(zjs3);
	if (endIndexInt > 0) {
		$(".bc").text(1);
	} else {
		$(".bc").text(0);
	}
	$(".page_des2").text(endIndexInt);
//    qz.value = 1;

	$("#dialog-modal").dialog({
		width: "1200px",
		autoOpen: false,
		show: "blind",
		hide: "explode",
		modal: true
	});

	$(".handle").live("click", function () {
		$("#dialog-modal").dialog("open");
		return false;
	});

	$(".cancel").click(function () {
		$("#dialog-modal").dialog("close");
	});
	$('#container').highcharts({
		chart: {
			type: 'areaspline'
		},
		title: {
			text: ''
		},
		legend: {
			enabled: false
		},
		xAxis: {
			categories: cataList
		},
		yAxis: {
			title: {
				text: ''
			}
		},
		tooltip: {
			crosshairs: true,
			shared: true,
			valueSuffix: ' '
		},
		credits: {
			enabled: false
		},
		plotOptions: {
			areaspline: {
				fillOpacity: 0.5
			}
		},
		series: [
			{
				name: '普通',
				color: '#26C3BC',
				fillColor: {
					linearGradient: {
						x1: 0,
						y1: 0,
						x2: 0,
						y2: 1
					},
					stops: [
						[0, '#DEECEC'],
						[
							1,
							Highcharts.Color('#DEECEC').setOpacity(0)
								.get('rgba')]
					]
				},
				marker: {
					lineWidth: 2,
					lineColor: '#26C3BC',
					fillColor: 'white',
					symbol: 'circle'
				},
				data: dataList3
			},
			{
				name: '严重',
				color: '#5CC8E4',
				fillColor: {
					linearGradient: {
						x1: 0,
						y1: 0,
						x2: 0,
						y2: 1
					},
					stops: [
						[0, '#D0EAEB'],
						[
							1,
							Highcharts.Color('#D0EAEB').setOpacity(0)
								.get('rgba')]
					]
				},
				marker: {
					lineWidth: 2,
					lineColor: '#5CC8E4',
					fillColor: 'white',
					symbol: 'circle'
				},
				data: dataList2
			},
			{
				name: '紧急',
				color: '#E66E4C',
				fillColor: {
					linearGradient: {
						x1: 0,
						y1: 0,
						x2: 0,
						y2: 1
					},
					stops: [
						[0, '#F0D7D0'],
						[
							1,
							Highcharts.Color('#F0D7D0').setOpacity(0)
								.get('rgba')]
					]
				},
				marker: {
					lineWidth: 2,
					lineColor: '#E66E4C',
					fillColor: 'white',
					symbol: 'circle'
				},
				data: dataList1
			}
		]
	});
	// window.setInterval('alarmRefresh();',5000);
	// setTimeout("alarmRefresh();",5000);
//    alarmRefresh();
	//报警消防系统弹框
	if (telnet == 'Y') {
		$("#deviceShowStatus").attr("class",
			"alarms_right_text alarms_right_no");
		$("#deviceShowStatus").text("设备通讯故障");
		$("#dialog-modal").dialog("close");
	} else {
		$("#deviceShowStatus").attr("class",
			"alarms_right_text alarms_right_ok");
		$("#deviceShowStatus").text("设备通讯正常");
	}
//    if (alertGroupname!=null&&alertGroupname!="") {
//        answer(alertLogid, alertAlmt);  2014-10-31 只弹出安监的，不弹出自已的应答
//    }
});


var paraId = "";
var date_time = "";

function answer(id, date_almt,tagid,tagname) {
	$('#alarmLogId').val(id);
	$('#alarmTagId').val(tagid);
	$('#alarmTagName').val(tagname);
	var url = "ucAlarmfindById?id=" + id + "&date_almt=" + date_almt;
	$.ajax({
		type: "GET",
		url: url,
		cache: false,
		success: function (data) {
			var almpriorityJb = getAlarmLevel(data.almpriority);
			var reviewtValue = "";
			if (data.reviewer == '1') {
				var D = new Date(data.reviewt);
				var y = D.getFullYear();
				var m = D.getMonth() + 1;
				if (m < 10) {
					m = '0' + m;
				}
				var d = D.getDate();
				if (d < 10) {
					d = '0' + d;
				}
				var h = D.getHours();
				if (h < 10) {
					h = '0' + h;
				}
				var i = D.getMinutes();
				if (i < 10) {
					i = '0' + i;
				}
				var s = D.getSeconds();
				if (s < 10) {
					s = '0' + s;
				}
				reviewtValue = y + '年' + m + '月' + d + '日' + h + '点'
				+ i + '分' + s + '秒';
			} else {
				reviewtValue = "";
			}

			$('#textareaId').val(data.reviewcontent);
			$('#alarmGrade').val(data.almpriority);
			$(".bjLevel").text(almpriorityJb);
			$(".bjLevel1").text(reviewtValue);
			$(".bjLevel3").text(data.devicename);
			$(".bjLevel4").text(data.tagcomment);
			var url1 = 'findByLjDictidAndDescriptionBy?dictid=SUBSYS'
				+ "&description="
				+ encodeURIComponent(data.groupname);
			$.ajax({
				type: "GET",
				url: url1,
				cache: false,
				success: function (data) {
					$('#textareaIdreason').val(data.CAUSE);
					$('#textareaIdflow').val(data.REMEDY);
				}
			});
			$("#dialog-modal").dialog("open");
		}
	});
	// $(".ui-widget-content").height(400);
	paraId = id;
	date_time = date_almt;
}

var reviewcontent = "";
function ansYd() {
	var reviewcontent = $("#textareaId").val();
//    var alarmpriorityValue = $("#alarmpriority").val();
	var	url = 'upDatealarm?id="' + paraId + "&reviewcontent="
		+ encodeURIComponent(reviewcontent) + "&date_almt="
		+ date_time;
	$("#dialog-modal").dialog("close");
	$.ajax({
		type:"GET",
		url:url ,
		cache:false,
		success:function(data){
			if(data!=null){
				if(data.status==0){
					console.log('--报警应答成功--');
					//应答成功，刷新当前页面
					selectFy("Refresh");
				}else{
					console.log('--报警应答失败--');
				}
			}
		},
		error:function(e){
			console.error("--报警应答请求异常--"+e);
		}
	});
}

// 点击报警级别触发
function show() {
	getAlarmcurrList();
}
//分页查询
function selectFy(para, id) {
	var currentPage = parseInt($(".bc").text());
	var totalPages = parseInt($(".page_des2").text());
	var pagef = currentPage;//进行查询的页码
	var alarmpriorityValue = $("#alarmpriority").val();

	if(para == "First"){
		pagef = 1;
	}else if(para == "Before"&&currentPage>1){//前一页
		pagef = currentPage-1;
	}else if(para == "After"&&currentPage<totalPages){
		pagef = currentPage+1;
	}else if(para == "End"){
		pagef = totalPages;
	}
	console.log("-pagef-"+pagef);

	var url = 'alarmfindByalarmpriorityPage?alarmpriority='
		+ alarmpriorityValue + "&pagef=" + pagef + "&groupname="
		+ encodeURIComponent(headerdescription);


	$.ajax({
		type: "GET",
		url: url,
		cache: false,
		success: function (data) {
			if(data!=null){
				var page = data.alarmPage;
				if (page != null) {
					var totalPages = page.totalPages;
					if(totalPages>0){
						$(".bc").text(1);
					}else{
						$(".bc").text(0);
					}
					$(".page_des2").text(totalPages);
					//加载列表数据
					$("#tbodyQz").html(getAlarmListHtml(page.content));
				}
			}
		}
	});
}

function filterDeviceEr() {

	var va = $("#deviceShowStatus").val();
	if (va == "1") {
		$("#alarmpriority").val(4);
		show();
	} else {
		$("#alarmpriority").val(0);
		show();
	}
}

/**
 * 报警压制
 */
function alarmCancel(result) {

	var alarmid=$('#alarmLogId').val();
	var tagid=$('#alarmTagId').val();
	var tagname=$('#alarmTagName').val();
	var reason=$('#reason').val();

	var params={tagname:tagname,tagid:tagid,alarmId:alarmid,disable:result,reason:reason};
	$.ajax({
		type: "POST",
		url: CONTEXT_PATH+'/pwarn/restrainAlarm',
		dataType:'json',
		async:true,
		data:params,
		cache: false,
		success: function (data) {
			selectFy(qz.value, paraId);
			$("#dialog-modal").dialog("close");
		}
	});
}

/**
 * 报警级别修改
 */
function editAlarmGrade(obj) {
	var tagid=$('#alarmTagId').val();
	var tagname=$('#alarmTagName').val();
	var grade=obj.value;
	var params={tagname:tagname,tagid:tagid,grade:grade};
	$.ajax({
		type: "POST",
		url:CONTEXT_PATH+'/pwarn/editAlarmGrade',
		dataType:'json',
		async:true,
		data:params,
		cache: false,
		success: function (data) {
			selectFy(qz.value, paraId);
			$("#dialog-modal").dialog("close");
		}
	});
}