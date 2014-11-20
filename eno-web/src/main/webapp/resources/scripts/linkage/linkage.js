/**
 * 联动控制台
 */
$(function() {

	// 点击创建事件触发的方法
	$('#createEvent').click(function (){
		window.location.href = CONTEXT_PATH + '/linkage/forward/add/condition/-1';
	});

	// 点击创建预案触发的方法
	$('#createCell').click(function (){
		window.location.href = CONTEXT_PATH + '/linkage/forward/add/cell/-1';
	});

	// 点击创建规划触发的方法
	$('#createLinkAll').click(function (){
		window.location.href = CONTEXT_PATH + '/linkage/forward/add/linkall/-1';
	});

	showEventTable(); // 显示联动事件列表
	showCellTable(); // 显示预案列表
	showLinkAllTable(); // 显示规划列表

});

// 时间格式化
Date.prototype.format = function(format) {
	var o = {
		"M+" : this.getMonth() + 1, // month
		"d+" : this.getDate(), // day
		"H+" : this.getHours(), // hour
		"m+" : this.getMinutes(), // minute
		"s+" : this.getSeconds(), // second
		"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
		"S" : this.getMilliseconds()
		// millisecond
	}
	if (/(y+)/.test(format))
		format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4
						- RegExp.$1.length));
	for (var k in o)
		if (new RegExp("(" + k + ")").test(format))
			format = format.replace(RegExp.$1, RegExp.$1.length == 1
							? o[k]
							: ("00" + o[k]).substr(("" + o[k]).length));
	return format;
}

/**
 * 显示联动事件列表
 */
var oEvent; // 联动事件列表
function showEventTable() {

	oEvent = $('#eventtable').dataTable({
		"bFilter" : false,
		"oLanguage" : {
			"sUrl" : CONTEXT_PATH + "/resources/plugins/datatables/jquery.dataTables.zh_cn.txt"
		},
		"sDom" : 't<"widget-header custom-table eventtable-header"frtipr>',
		"bStateSave" : false,
		"aaSorting" : [[0, "asc"]],
		"bProcessing" : true,
		"bServerSide" : true,
		"sAjaxSource" : CONTEXT_PATH + "/linkage/eventlist",
		"aoColumns" : [{
					"mData" : "condid"
				}, {
					"mData" : "condname"
				}, {
					"mData" : "condcomment"
				}, {
					"mData" : "condexp"
				}, {
					"mData" : "updatet"
             	}],
		"aoColumnDefs" : [{
					"sName" : "condid",
					"sClass" : "hide",
					"aTargets" : [0]
				}, {
					"sName" : "condname",
					"aTargets" : [1],
					"fnRender" : function(oObj) {
						return '<a href=\"javascript:editCondition(\'' + oObj.aData.condid + '\')" class="a_control_css">' + oObj.aData.condname + '</a>';
					}
				}, {
					"sName" : "condcomment",
					"aTargets" : [2]
				}, {
					"sName" : "condexp",
					"aTargets" : [3]
				}, {
					"sName" : "updatet",
					"aTargets" : [4],
					"fnRender" : function(oObj) {
						return new Date(oObj.aData.updatet).format('yyyy-MM-dd HH:mm:ss')
					}
				}]
	});
}

/**
 * 显示预案列表
 */
var oCell; // 列表
function showCellTable() {

	oCell = $('#celltable').dataTable({
		"bFilter" : false,
		"oLanguage" : {
			"sUrl" : CONTEXT_PATH + "/resources/plugins/datatables/jquery.dataTables.zh_cn.txt"
		},
		"sDom" : 't<"widget-header custom-table celltable-header"frtipr>',
		"bStateSave" : false,
		"aaSorting" : [[0, "asc"]],
		"bProcessing" : true,
		"bServerSide" : true,
		"sAjaxSource" : CONTEXT_PATH + "/linkage/celllist",
		"aoColumns" : [{
					"mData" : "cellid"
				}, {
					"mData" : "cellname"
				}, {
					"mData" : "cellcomment"
				}, {
					"mData" : "updatet"
             	}],
		"aoColumnDefs" : [{
					"sName" : "cellid",
					"sClass" : "hide",
					"aTargets" : [0]
				}, {
					"sName" : "cellname",
					"aTargets" : [1],
					"fnRender" : function(oObj) {
						return '<a href=\"javascript:editCell(\'' + oObj.aData.cellid + '\')" class="a_control_css">' + oObj.aData.cellname + '</a>';
					}
				}, {
					"sName" : "cellcomment",
					"aTargets" : [2]
				}, {
					"sName" : "updatet",
					"aTargets" : [3],
					"fnRender" : function(oObj) {
						return new Date(oObj.aData.updatet).format('yyyy-MM-dd HH:mm:ss')
					}
				}]
	});
}
/**
 * 显示规划列表
 */
var oLinkAll; // 列表
function showLinkAllTable() {

	oLinkAll = $('#linkalltable').dataTable({
		"bFilter" : false,
		"oLanguage" : {
			"sUrl" : CONTEXT_PATH + "/resources/plugins/datatables/jquery.dataTables.zh_cn.txt"
		},
		"sDom" : 't<"widget-header custom-table linkalltable-header"frtipr>',
		"bStateSave" : false,
		"aaSorting" : [[0, "asc"]],
		"bProcessing" : true,
		"bServerSide" : true,
		"sAjaxSource" : CONTEXT_PATH + "/linkage/linkalllist",
		"aoColumns" : [{
					"mData" : "linkageid"
				}, {
					"mData" : "linkagename"
				}, {
					"mData" : "linkagecomment"
				}, {
					"mData" : "authorname"
				}, {
					"mData" : "conditionname"
				}, {
					"mData" : "paused"
				}, {
					"mData" : "checkactive"
				}, {
					"mData" : "updatet"
             	}],
		"aoColumnDefs" : [{
					"sName" : "linkageid",
					"sClass" : "hide",
					"aTargets" : [0]
				}, {
					"sName" : "linkagename",
					"aTargets" : [1],
					"fnRender" : function(oObj) {
						return '<a href=\"javascript:editLinkAll(\'' + oObj.aData.linkageid + '\')" class="a_control_css">' + oObj.aData.linkagename + '</a>';
					}
				}, {
					"sName" : "linkagecomment",
					"aTargets" : [2]
				}, {
					"sName" : "authorname",
					"aTargets" : [3]
				}, {
					"sName" : "conditionname",
					"aTargets" : [4]
				}, {
					"sName" : "paused",
					"aTargets" : [5]
				}, {
					"sName" : "checkactive",
					"aTargets" : [6]
				}, {
					"sName" : "updatet",
					"aTargets" : [7],
					"fnRender" : function(oObj) {
						return new Date(oObj.aData.updatet).format('yyyy-MM-dd HH:mm:ss')
					}
				}]
	});
}

/**
 * 查看具体的事件
 * @param {} condid
 */
function editCondition(condid) {
	window.location.href = CONTEXT_PATH + '/linkage/forward/edit/condition/' + condid;
}

/**
 * 查看具体的预案
 * @param {} cellid
 */
function editCell(cellid) {
	window.location.href = CONTEXT_PATH + '/linkage/forward/edit/cell/' + cellid;
}

/**
 * 查看具体的规划
 * @param {} linkageid
 */
function editLinkAll(linkageid) {
	window.location.href = CONTEXT_PATH + '/linkage/forward/edit/linkall/' + linkageid;
}

/**
 * 导出事件库数据到excel中
 */
function exportCondition() {
	window.open(CONTEXT_PATH + '/linkage/exportCondition?source=condition');
}

/**
 * 导出事件库数据到excel中
 */
function exportCell() {
	window.open(CONTEXT_PATH + '/linkage/exportCondition?source=cell');
}

/**
 * 导入excel
 */
function importExcel(source) {
	openwindow('importExcel?source=' + source, '', 350, 150);
}
   
function openwindow(url, name, iWidth, iHeight) {
	var name; // 网页名称，可为空;
	var iWidth; // 弹出窗口的宽度;
	var iHeight; // 弹出窗口的高度;
	var iTop = (window.screen.availHeight - 30 - iHeight) / 2; // 获得窗口的垂直位置;
	var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; // 获得窗口的水平位置;
	window
			.open(
					url,
					name,
					'height='
							+ iHeight
							+ ',,innerHeight='
							+ iHeight
							+ ',width='
							+ iWidth
							+ ',innerWidth='
							+ iWidth
							+ ',top='
							+ iTop
							+ ',left='
							+ iLeft
							+ ',toolbar=no,menubar=no,scrollbars=auto,resizeable=no,location=no,status=no');
}