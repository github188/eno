<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/bootstrap-select.min.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/elecpatrol.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/alertManage.css">
<script src="${pageContext.request.contextPath}/webjars/jquery/1.9.1/jquery.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/scripts/bootstrap-select.min.js"></script>	
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/scripts/main.js"></script>		
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/scripts/alertManage.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/highcharts.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/scripts/My97DatePicker/WdatePicker.js"></script>
<div class="span10" >
     <div class="span12 no_right_margin" style="margin-top: 0px;">	
			<div class="span12 query_bar">
				<div>
<!-- 					<span>开始时间：</span> -->
<!-- 					<span><input id="fDate" type="text" onClick="WdatePicker()" style="height: 20px;width: 100px;"/></span> -->
<!-- 					<span>结束时间：</span> -->
<!-- 					<span><input id="tDate" type="text" onClick="WdatePicker()" style="height: 20px;width: 100px;"/></span> -->
                    <span style="margin-left: 30px;"></span>
                      <span>路线：</span>  
                      <select id="lx" name="lx" class="span1"
					   style="width: 223px; height: 25px">
					   <option value="0">请选择</option>
					   <option value="1">一楼路线</option>
					   <option value="2">二楼路线</option>
					   <option value="3">三楼路线</option>
				      </select>
					<span>班次：</span>
					<select id="bc" name="bc" class="span1"
					   style="width: 223px; height: 25px">
					   <option value="0">请选择</option>
					   <option value="1">A班</option>
					   <option value="2">B班</option>
					   <option value="3">C班</option>
				     </select>
<!-- 					<span style="margin-left: 30px">漏巡</span><input type="radio"  name="radio1"  value="1" style="width:20px"> -->
<!-- 					<span>未巡</span><input type="radio"  name="radio1"   value="2"  style="width:20px"> -->
<!-- 					<span>早巡</span><input type="radio"  name="radio1"   value="3"  style="width:20px"> -->
<!-- 					<span>晚巡</span><input type="radio"  name="radio1"    value="3" style="width:20px"> -->
                    <span>巡更时间：</span>
                    <span><input id="startDate" name="startDate" type="text" onClick="WdatePicker()" style="height: 20px;width: 100px;"/></span>
					<span style="margin-left: 30px;cursor: pointer;" onclick="selectShow();">查询</span>
				</div>
			</div>
			<div class="span12 alert_detail">
				<table class="table_style1 sub_table_style1">
					<tbody id="tbodyContent">
						<tr>
							<th>ID</th>
							<th>线路</th>
							<th>班次</th>
							<th>开始时间</th>
							<th>结束时间</th>
							<th>核查时间</th>
							<th>核查结果</th>
							<th>巡逻员</th>
					    	<th>漏巡个数</th>
							<th>准时个数</th>
							<th>早巡个数</th>
							<th>晚巡个数</th>
							<th>排班类型</th>
						</tr>

					</tbody>
				</table>
                <div class="paging">
                    <img src="${pageContext.request.contextPath}/resources/images/left_first.png" onclick="selectPatrolFy('First');"/> <img
                        src="${pageContext.request.contextPath}/resources/images/left.png" onclick="selectPatrolFy('Before');"/>
                    <span class="page_des1">Page</span>
                    <span class="bc">1</span><span class="page_des2">of</span>
                      <span class="pageCount"></span> <img src="${pageContext.request.contextPath}/resources/images/right.png" onclick="selectPatrolFy('After');"/> <img
                        src="${pageContext.request.contextPath}/resources/images/right_end.png" onclick="selectPatrolFy('End');"/>
                </div>
                <!-- <div class="search_result">共<span class="pade_num">1000</span>条搜索结果</div> -->
			</div>
		</div>
</div>
<script type="text/javascript">
	var base_url = "${pageContext.request.contextPath}";
	var sexs = document.getElementsByName("radio1");
	var size = sexs.length;
	for (var i = 0; i < size; i++) {
		if (sexs[i].value == '0') {
			sexs[i].checked = true;
		} else {
			sexs[i].checked = false;
		}
	}
	var pageNum = 1;
	var pageCount = 1;
	var pageSize = 15;
	function selectPatrolFy(para) {
		var pagef = "";
		if (para == "Before") {
			if (pageNum > 1) {
				pageNum = pageNum - 1;
			}
			pagef = pageNum;
		}
		if (para == "After") {
			if (pageNum < pageCount) {
				pageNum = pageNum + 1;
			}
			pagef = pageNum;
		}
		if (para == "End") {
			pageNum = pageCount;
			pagef = pageCount;
		}
		if (para == "First") {
			pageNum = 1;
			pagef = 1;
		}
		if (pageCount == "0") {
			pagef = 0;
		}
		$(".bc").text(pagef);
		var checkDate = $("#startDate").val();
		getSubPatrols(pageNum, pageSize, checkDate);

	}
	
	// 点击查询事件
	function selectShow() {
		var checkDate = $("#startDate").val();
		getSubPatrols(pageNum, pageSize, checkDate);
	}
	
	// 与后台交互，发送查询，处理返回数据
	function getSubPatrols(pageNum, pageSize, checkDate) {
		var url = base_url + '/elecpatrol/getPatrolList';
		$.ajax({
			url : url,
			type : 'GET',
			dataType : 'json',
			data : {
				pageNum : pageNum,
				pageSize : pageSize,
				checkDate : checkDate
			},
			success : function(data) {
				console.log(data);
				var dataHtml = '';
				var sub;
				for (var i = 0; i < data.pageItems.length; i++) {
					sub = data.pageItems[i];
					dataHtml += '<tr><td>'+sub.id+'</td><td>'+sub.lineNum+'</td><td>'+sub.lineName+'</td><td>'+sub.startTime+'</td><td>'+sub.endTime+'</td><td>'+sub.checkTime+'</td><td>'+sub.checkResult+'</td>'
						+'<td>'+sub.userName+'</td><td>'+sub.missedNum+'</td><td>'+sub.onTimeNum+'</td><td>'+sub.earlyNum+'</td><td>'+sub.lateNum+'</td><td>'+sub.shifts+'</td></tr>';
				}
				$("#tbodyContent tr:gt(0)").remove();
				$("#tbodyContent").append(dataHtml);
				pageCount = data.pagesAvailable;
				$(' .paging .pageCount').html(pageCount);
			},
			error : function(e) {
				console.log("get EpList error---:" + e);
			}
		});
	}
	$(":radio").click(function() {
		var r = $(this).attr("name");
		$(":radio[name=" + r + "]:not(:checked)").attr("tag", 0);
		if ($(this).attr("tag") == 1) {
			$(this).attr("checked", false);
			$(this).attr("tag", 0);
		} else {
			$(this).attr("tag", 1);
		}
	});

	function showShift() {
		$("#bc").html("");
		$("#bc").append('<option value="">请选择</option>');
		var url = base_url + '/other/ep/getShiftList';
		$.ajax({
			url : url,
			type : 'GET',
			dataType : 'json',
			async : true,
			data : '',
			success : function(data) {

				if (data != null) {
					var htmlJc = '';
					for (var i = 0; i < data.length; i++) {
						var obj = data[i];
						htmlJc = htmlJc + '<option value="'+obj[0]+'">'
								+ obj[1] + '</option>';
					}
					$("#bc").append(htmlJc);
				}
			}
		});
	}

	function showRoad() {
		$("#lx").html("");
		$("#lx").append('<option value="">请选择</option>');
		var url = base_url + '/other/ep/getRoadList';
		$.ajax({
			url : url,
			type : 'GET',
			dataType : 'json',
			async : true,
			data : '',
			success : function(data) {

				if (data != null) {
					var htmlJc = '';
					for (var i = 0; i < data.length; i++) {
						var obj = data[i];
						htmlJc = htmlJc + '<option value="'+obj[0]+'">'
								+ obj[1] + '</option>';
					}
				}
			}
		});
	}
	$(function(){
		var Nowdate = new Date();
		Nowdate.setDate(Nowdate.getDate()-1);
		var year = Nowdate.getFullYear();
		var month = (Nowdate.getMonth()+1)+"";
		month = (month.length == 1 ? ("0" + month) : month);
		var day = Nowdate.getDate()+"";
		day = day.length == 1 ? ("0" + day) : day;
		$("#startDate").val(year+"-"+month+"-"+day);
		
		// 初始化查询
		selectShow();
	})
</script>
</html>
