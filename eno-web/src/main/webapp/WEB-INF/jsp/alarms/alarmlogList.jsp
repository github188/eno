<%@page import="com.energicube.eno.monitor.model.KeyValue"%>
<%@page import="java.util.List"%>
<%@page import="com.energicube.eno.common.Const"%>
<%@page import="org.joda.time.DateTime"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/bootstrap-select.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ui/jquery.ui.all.css">	
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/main.css">
<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/alertManage.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/scripts/bootstrap-select.min.js"></script>	
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/scripts/main.js"></script>		
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/scripts/alertManage.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/highcharts.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/scripts/My97DatePicker/WdatePicker.js"></script>
<%
	List<KeyValue> warningLevel = Const.WarningLevel;
pageContext.setAttribute("warningLevel", warningLevel);
%>
<c:set var="cxt" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
$(function() {
	$("#dialog-modal").dialog({
		width : "900px",
		autoOpen : false,
		show : "blind",
		hide : "explode",
		modal : true
	});
	
	$(".cancel").click(function(){
		$("#dialog-modal").dialog("close");
	});
});

$(document).ready(function() {
	var endIndexInt = ${endIndex};
	if(endIndexInt>0){
	$(".bc").text(1);
	}
	else{
	$(".bc").text(0);	
	}
	qz.value = 1;
	var fDateValueText = ${fDateValue};
    var tDateValueText = ${tDateValue};
    alarmpriority.value = ${alarmpriorityValue};
    if(fDateValueText=="0"){
		fDate.value = " ";	
	}
	else{
		fDate.value = fDateValueText;
	}
 	if(tDateValueText=="0"){
 		tDate.value = " ";
 	}
 	else{
 	    tDate.value = tDateValueText;
 	}
 	
 	changeChart('week');
});
 var headerdescription = "";
 var alarmBz = 0;
 function alarmRefresh(){
 }
 function alarmLogJlChange(){
	 var alarmLogJlValue = alarmLogJl.value;
	 if(alarmLogJlValue == '0'){
		 alarmBz = 1;
		 changeChart('week'); 
	 }
     if(alarmLogJlValue == '1'){
    	 alarmBz = 0;
    	 changeChart('month');
	 }
 }
 var paraId = "";
 function answer(id){
	 $("#dialog-modal").dialog("open");
	 
	 var url = "ucAlarmlogfindById?id=" + id;
	 $.ajax({
		 type:"GET",
		 url:url ,
		 cache:false,
		 success:function(data){
			 var almpriorityJb = "";
			 if(data.almpriority == 1){
				 almpriorityJb = "极高";
			 }
			 if(data.almpriority == 2){
				 almpriorityJb = "高";
			 }
			 if(data.almpriority == 3){
				 almpriorityJb = "中";
			 }
			 if(data.almpriority == 4){
				 almpriorityJb = "低";
			 }
			 var reviewtValue = "";
			 if(data.reviewer == '1'){
				 var D = new Date(data.reviewt);
				 var y = D.getFullYear();
				 var m = D.getMonth() + 1;
				 if(m<10){
					m = '0'+m;
				 }
				 var d = D.getDate();
				 if(d<10){
					d = '0'+d;
				 }
				 var h = D.getHours();
				 if(h<10){
					h = '0'+h;
				 }
				 var i = D.getMinutes();
				 if(i<10){
					i = '0'+i;
				 }
				 var s = D.getSeconds();
				 if(s<10){
					s = '0'+s;
				 }
				 reviewtValue = y+'年'+m+'月'+d+'日'+h+'点'+i+'分'+s+'秒';
			 }
			 else{
				 reviewtValue = "";
			 }
			 textareaId.value = data.reviewcontent;
			 $(".bjLevel").text(almpriorityJb);
			 $(".bjLevel1").text(reviewtValue);
			 $(".bjLevel3").text(data.devicename);
		 	 $(".bjLevel4").text(data.tagcomment);
		 	var url1 = 'findByDictidAndDescriptionBy?dictid=SUBSYS'+"&description="+encodeURIComponent(data.groupname);
		 	 $.ajax({
				 type:"GET",
				 url:url1 ,
				 cache:false,
				 success:function(data){
					 textareaIdreason.value = data.CAUSE;
					 textareaIdflow.value = data.REMEDY;
				 }
			});  
		 }
	});
	 $(".ui-widget-content").height(400);
	 paraId = id;
 }
 
 var reviewcontent = "";
 function ansYd(){
	 reviewcontent = textareaId.value;
	 selectFy(qz.value,paraId);
 }
 
 function showDateTime(s,d)
 {
	var Nowdate = new Date(Date.parse(s.replace(/-/g, '/')));
	var WeekFirstDay=new Date(Nowdate-((Nowdate.getDay() == 0 ? 7 : Nowdate.getDay())-d)*86400000);
	var day = (WeekFirstDay.getDate() + "");
	day = day.length == 1 ? ("0" + day) : day;
	var month = ((WeekFirstDay.getMonth()+1) + "");
	month = month.length == 1 ? ("0" + month) : month;
	t = [WeekFirstDay.getFullYear(), month, day ];
	return t.join('-');
}

 
 var dataListLog1 = ${dataList1};
 var dataListLog2 = ${dataList2};
 var dataListLog3 = ${dataList3};
 var dataWeekListLog1 = ${dataWeekList1};
 var dataWeekListLog2 = ${dataWeekList2};
 var dataWeekListLog3 = ${dataWeekList3};
 var jsByweek1 = 0, jsByweek2 = 0, jsByweek3 = 0;
// 加载图表 
function changeChart(type){
		var now_date = new Date();
		var nowtime = [now_date.getFullYear(), now_date.getMonth()+1, now_date.getDate()].join('-'); // 当前时间
		var weekday = now_date.getDay();
		
		var loop = 7,str='',step=1;
		if(type=="week"){
			loop = (weekday == 0 ? 7 : now_date.getDay());
		} else {
			str = now_date.getFullYear() + "-" + (now_date.getMonth()+1) + "-";
			loop = now_date.getDate();
		}
		
		// x轴的间隔
		if(loop > 10 && loop <= 15){
			step = 3;
		} else if(loop > 15 && loop < 25){
			step = 4;
		}
		var dataList1 = [],dataList2 = [],dataList3 = [], cataList = []; // 数据列表
		for(var i=0;i<loop;i++){
			if(type=="week"){
				cataList.push(showDateTime(nowtime,(i+1)));
			} else {
				cataList.push( str + (i+1));
			}
		}
		if(type=="week"){
			dataList1 = dataWeekListLog1.slice(dataWeekListLog1.length-loop);
			dataList2 = dataWeekListLog2.slice(dataWeekListLog1.length-loop);
			dataList3 = dataWeekListLog3.slice(dataWeekListLog1.length-loop);
			if(alarmBz != 1 ){
			jsByweek1 = 0;
			jsByweek2 = 0;
			jsByweek3 = 0;
			for(var k=dataWeekListLog1.length-loop;k<dataWeekListLog1.length;k++){
				jsByweek1 += dataWeekListLog1[k];
			}
            for(var m=dataWeekListLog2.length-loop;m<dataWeekListLog2.length;m++){
            	jsByweek2 += dataWeekListLog2[m];
			}
            for(var l=dataWeekListLog3.length-loop;l<dataWeekListLog3.length;l++){
            	jsByweek3 += dataWeekListLog3[l];
            }
           $('#num_one').text(jsByweek1);
		   $('#num_two').text(jsByweek2);
		   $('#num_three').text(jsByweek3);
		  }
		}
		else{
			dataList1 = dataListLog1
			dataList2 = dataListLog2
			dataList3 = dataListLog3
		}
	
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
				labels : {
					step : step
				},
				categories: cataList
			},
			yAxis: {
				title: {
					text: ''
				}
			},
			tooltip: {
				crosshairs : true,
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
			series: [{
				name: '普通',
				color : '#26C3BC',
				fillColor: {
					linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1},
					stops: [
						[0, '#DEECEC'],
						[1, Highcharts.Color('#DEECEC').setOpacity(0).get('rgba')]
					]
				},
				marker : {
					lineWidth : 2,
					lineColor : '#26C3BC',
					fillColor : 'white',
					symbol: 'circle'
				},
				data: dataList3
			}, {
				name: '严重',
				color : '#5CC8E4',
				fillColor: {
					linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1},
					stops: [
						[0, '#D0EAEB'],
						[1, Highcharts.Color('#D0EAEB').setOpacity(0).get('rgba')]
					]
				},
				marker : {
					lineWidth : 2,
					lineColor : '#5CC8E4',
					fillColor : 'white',
					symbol: 'circle'
				},
				data: dataList2
			}, {
				name: '紧急',
				color : '#E66E4C',
				fillColor: {
					linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1},
					stops: [
						[0, '#F0D7D0'],
						[1, Highcharts.Color('#F0D7D0').setOpacity(0).get('rgba')]
					]
				},
				marker : {
					lineWidth : 2,
					lineColor : '#E66E4C',
					fillColor : 'white',
					symbol: 'circle'
				},
				data: dataList1
			}]
		});
    }

	function showUcAlarmlog() {
		headerdescription = '${groupname}';	
		var fDateValue = fDate.value;
		var tDateValue = tDate.value;
		var alarmpriorityValue = alarmpriority.value;
		var url = "alarmfindByfDateValue?fDateValue=" + fDateValue
				+ "&tDateValue=" + tDateValue + "&alarmpriorityValue="
				+ alarmpriorityValue+"&groupname="+encodeURIComponent(headerdescription);
		window.location.href = url;
	}
	
	function show() {
		headerdescription = '${groupname}';	
		var fDateValue = fDate.value;
		var tDateValue = tDate.value;
		var alarmpriorityValue = alarmpriority.value;
		var url = "alarmfindByfDateValue?fDateValue=" + fDateValue
				+ "&tDateValue=" + tDateValue + "&alarmpriorityValue="
				+ alarmpriorityValue+"&groupname="+encodeURIComponent(headerdescription);
		window.location.href = url;
	}
	
	var  numOneWk = "" , numTwoWk = "", numTreeWk = "";
	var  numOneMt = "" , numTwoMt = "", numTreeMt = "";
	function alarmLogChange() {
 		 var alarmLogViewValue = alarmLogView.value;
		 if(alarmLogViewValue == "0"){
			 alarmBz = 0;
			 $('#num_one').text(jsByweek1);
			 $('#num_two').text(jsByweek2);
			 $('#num_three').text(jsByweek3);
		 }
		 else{
			 alarmBz = 0;
			 numOneMt =  ${zjs1};
		     numTwoMt =  ${zjs2}
		     numTreeMt = ${zjs3}
			 $('#num_one').text(numOneMt);
			 $('#num_two').text(numTwoMt);
			 $('#num_three').text(numTreeMt);
		 }
	}
	 function showAlaramLog(para){
		 headerdescription = para;
		 if(headerdescription!="")
		 window.location.href = 'alarmfindByAlaramLog?groupname='+encodeURIComponent(headerdescription);
	}
	var aft = ${endIndex};
	headerdescription = '${groupname}';	
	function selectFy(para,id){
		var url = "";
		var pagef = "";
		var fDateValue ="";
		var tDateValue ="";
		var fDateValueText = ${fDateValue};
	    var tDateValueText = ${tDateValue};
	    if(fDateValueText=="0"){
	    	fDateValue = " ";	
		}
		else{
			fDateValue = fDateValueText;
		}
	 	if(tDateValueText=="0"){
	 		tDateValue = " ";
	 	}
	 	else{
	 		tDateValue = tDateValueText;
	 	}
		var alarmpriorityValue = alarmpriority.value;
		if(id==-1){
			if(para == "Before"){
				if(qz.value>1){
					qz.value = qz.value-1;
					}
				pagef = qz.value;
			}
			if(para == "After"){
				if(qz.value<aft){
					qz.value = qz.value+1;
			}
				pagef = qz.value;
			}
			if(para == "End"){
				qz.value = ${endIndex};
				pagef = ${endIndex};
			}
			if(para == "First"){
				qz.value = 1;
				pagef = 1;
			}
			if(aft=="0"){
				pagef = 0;
		}	
		$(".bc").text(pagef);
		url = "ucAlarmlogfindByfDateValuePage?fDateValue=" + fDateValue
		+ "&tDateValue=" + tDateValue + "&alarmpriorityValue="
		+ alarmpriorityValue + "&pagef="+pagef +"&groupname="+encodeURIComponent(headerdescription);
		}
		else{
		pagef = qz.value;	
		$(".bc").text(pagef);
		url = "upDateucAlarmlog?fDateValue=" + fDateValue
		+ "&tDateValue=" + tDateValue + "&alarmpriorityValue="
		+ alarmpriorityValue + "&pagef="+pagef +"&id="+id+"&reviewcontent="+encodeURIComponent(reviewcontent)+"&groupname="+encodeURIComponent(headerdescription);
		$("#dialog-modal").dialog("close");
		}
		$.ajax({
			 type:"GET",
			 url:url ,
			 cache:false,
			 success:function(data){
	    var dataList = data.content;
	    var html =" <tr><th>报警日期</th><th>报警时间</th><th>报警描述</th><th>子系统</th><th>设备</th><th>位置</th><th>报警级别</th><th>报警处理</th></tr>";
	    $("#tbodyQz").html("");
	    for(var k=0;k<dataList.length;k++){
	    	var ucAlarmlogData = dataList[k];
			 var D = new Date(ucAlarmlogData.almt);
			 var y = D.getFullYear();
			 var m = D.getMonth() + 1;
			 if(m<10){
				m = '0'+m;
			 }
			 var d = D.getDate();
			 if(d<10){
				d = '0'+d;
			 }
			 var h = D.getHours();
			 if(h<10){
				h = '0'+h;
			 }
			 var i = D.getMinutes();
			 if(i<10){
				i = '0'+i;
			 }
			 var s = D.getSeconds();
			 if(s<10){
				s = '0'+s;
			 }
			 var dateBj = y+'年'+m+'月'+d+'日';
			 var timeBj = h+'点'+i+'分'+s+'秒';
			 var htmlk = "";
			 if(k%2==0){
              htmlk = '<tr onclick="answer('+ucAlarmlogData.id+');"  style="cursor: pointer;"  class="changeColor"><td>'+dateBj+'</td>'
 			            +'<td>'+timeBj+'</td>'
 			            +'<td>'+ucAlarmlogData.almcomment+'</td>'
 			            +'<td>'+ucAlarmlogData.groupname+'</td>'
 			            +'<td>'+ucAlarmlogData.devicename+'</td>'
 			            +'<td>'+ucAlarmlogData.tagcomment+'</td>';
			 }
			 else{
			  htmlk = '<tr onclick="answer('+ucAlarmlogData.id+');"  style="cursor: pointer;"><td>'+dateBj+'</td>'
		           +'<td>'+timeBj+'</td>'
			       +'<td>'+ucAlarmlogData.almcomment+'</td>'
	               +'<td>'+ucAlarmlogData.groupname+'</td>'
	               +'<td>'+ucAlarmlogData.devicename+'</td>'
	               +'<td>'+ucAlarmlogData.tagcomment+'</td>';
			 }
		           
		 var htmlkc = "";           
		 if(ucAlarmlogData.almpriority==1){
		     htmlkc =   '<td style="color: red;">极高</td>';
		  }
		  if(ucAlarmlogData.almpriority==2){
			 htmlkc =   '<td style="color: #F79646;">高</td>';
		 }  
		  if(ucAlarmlogData.almpriority==3){
			 htmlkc =   '<td style="color: #996633;">中</td>';
			  }  
		  if(ucAlarmlogData.almpriority==4){
			 htmlkc =   '<td style="color: #33CC33;">低</td>';
		   }
		  var htmlkcc = "";
		  if(ucAlarmlogData.reviewer != '1'){
			  htmlkcc =  '<td>未应答</td>';
			  }
		  if(ucAlarmlogData.reviewer == '1'){
			  htmlkcc =  '<td>已应答</td>';
		   } 
          html = html+htmlk+htmlkc+htmlkcc+'</tr>';
	 }
	 $("#tbodyQz").append(html);
			 }
		});
	}
	// 导出报警列表
	function exportAlarm(){
	    var fDateValue = $("#fDate").val();
	    var tDateValue = $("#tDate").val();
	    headerdescription = '${groupname}';
	    var alarmpriorityValue = $("#alarmpriority").val();
	    var fileUrl = CONTEXT_PATH+"/pwarn/alarm/download?fDateValue=" + fDateValue
	            + "&tDateValue=" + tDateValue + "&alarmpriorityValue="
	            + alarmpriorityValue+"&groupname="+encodeURIComponent(headerdescription);
	    window.open(fileUrl);
	}
</script>
<div class="span10">
    <div id="dialog-modal" class="popover1" title="报警详细页">
		<div class="popover_nav">
			<ul>
				<li class="cur" style="cursor:pointer" ><i class="icon_file_pop"></i>
					<p>报警内容</p></li>
				<li style="cursor:pointer" ><i class="icon_file_question"></i>
					<p>可能原因</p></li>
				<li style="cursor:pointer" ><i class="icon_file_list"></i>
					<p>处理流程</p></li>
				<li style="cursor:pointer" ><i class="icon_file_list1"></i>
					<p>批注</p></li>	
			</ul>
		</div>
		<div class="alert_tab">
			<div class="alert_table">
				<table>
					<tbody>
						<tr>
							<td class="rank">报警级别</td>
							<td class="bjLevel"></td>
						</tr>
						<tr>
							<td class="respond_time">响应时间</td>
							<td class="bjLevel1"></td>
						</tr>
						<tr>
							<td class="this_device">设备</td>
							<td class="bjLevel3"></td>
						</tr>
						<tr>
							<td class="device_pos">位置</td>
							<td class="bjLevel4"></td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="alert_ol probably_reason">
				<textarea id="textareaIdreason" readonly="readonly" style="width: 80%; margin-left: 50px;" rows="10" cols=""></textarea>
			</div>
			<div class="alert_ol handle_flow">
                 <textarea id="textareaIdflow" readonly="readonly" style="width: 80%; margin-left: 50px;" rows="10" cols=""></textarea>
			</div>
			<div class="alert_ol handle_flowd" align="center">
                   <textarea id="textareaId" style="width: 80%" rows="10" cols=""></textarea>
			</div>
		</div>

		<div class="modal_btn">
			<div class="hand_ok" onclick="ansYd();">应答</div>
			<div class="cancel">关闭</div>
		</div>
	</div>
	
	<div class="span12 no_right_margin" style="height: 375px;">
			<div class="span6 title alertSituation">
				<div class="alert_title">
					<div>
						<span class="sub_title">报警概况&nbsp;&nbsp;</span>
						 <select id="alarmLogView" name="alarmLogView" style="width: 80px" onchange="alarmLogChange();"> 
                           <option value="0">本周</option>
                           <option value="1">本月</option>
                         </select>
						<div class="division_line"></div>
					</div>
				</div>
			 <div class="chart_style"  style="background-image:url(${pageContext.request.contextPath}/resources/images/alertBj.png);">&nbsp;</div>
			<div id="num_one" style="width : 100px; height : 100px ; line-height: 100px; text-align : center;position: absolute;left:335px;top:315px;font-size:44px;color:#FFF"></div>
			<div id="num_two" style="width : 100px; height : 100px ; line-height: 100px; text-align : center;position: absolute;left:600px;top:315px;font-size:44px;color:#FFF"></div>
			<div id="num_three" style="width : 100px; height : 100px ; line-height: 100px; text-align : center;position: absolute;left:850px;top:315px;font-size:44px;color:#FFF"></div>
			</div>
			<div class="span6 title alertRecord">
				<div class="alert_title">
					<div>
						<span class="sub_title">报警记录&nbsp;&nbsp;</span>
						<select id="alarmLogJl" name="alarmLogJl" style="width: 80px" onchange="alarmLogJlChange();"> 
                           <option value="0">本周</option>
                           <option value="1">本月</option>
                         </select>
						<div class="division_line"></div>
					</div>
				</div>
<%-- 			<div class="chart_style" style="background-image:url(${pageContext.request.contextPath}/resources/images/bj2.png);"></div> --%>
                <div class="chart_style" id="container"></div>
			</div>
		</div>

	<div class="span12 no_right_margin" style="height: 80%">
			<!-- <div>
				<h3>实时报警</h3>
			</div> -->
			<div class="alert_title title">
				<div>
					<span class="sub_title float_l">报警列表</span>
					<span class="little_ico btn3 float_l">▼</span>
					<div class="division_line float_l div_style2"></div>
				</div>
			</div>
			<div class="span12 query_bar">
				<div>
					<span>起始时间：</span>
					<span><input id="fDate" type="text" onClick="WdatePicker()" style="height: 20px"/></span>
					<span>结束时间：</span>
					<span><input id="tDate" type="text" onClick="WdatePicker()" style="height: 20px"/></span>
				</div>
				<div class="butt confirm" onclick="show();">确定</div>
				<div class="butt exportAlarm " onclick="exportAlarm();">导出报警</div>
				<div>
					<span>报警级别：</span>
					<select id="alarmpriority" name="alarmpriority"  onchange="showUcAlarmlog();">
				   <option value="0">请选择</option>
				   <c:forEach items="${alarmLevels}" var="alarmLevel">
						<option value="${alarmLevel.dmvalue }">${alarmLevel.description}</option>
					</c:forEach>
			        </select>
				</div>

			</div>
			<div class="span12 alert_detail">
				<table class="alert_list">
					<tbody id="tbodyQz">
						<tr>
							<th>报警日期</th>
							<th>报警时间</th>
							<th>报警描述</th>
							<th>子系统</th>
							<th>设备</th>
							<th>位置</th>
							<th>报警级别</th>
							<th>报警处理</th>
						</tr>
						<c:forEach items="${ucAlarmlogs.content}" var="ucAlarmlog">
				              <tr  onclick="answer(${ucAlarmlog.id});" style="cursor: pointer;">
					           <td><fmt:formatDate value="${ucAlarmlog.almt}" pattern="yyyy年MM月dd日" /></td> 
					           <td><fmt:formatDate value="${ucAlarmlog.almt}" pattern="HH点mm分ss秒" /></td>
					           <td>${ucAlarmlog.almcomment}</td>
				               <td>${ucAlarmlog.groupname }</td>
				               <td>${ucAlarmlog.devicename }</td>
				               <td>${ucAlarmlog.tagcomment }</td>
				               <c:if test="${ucAlarmlog.almpriority==1}">
				               <td style="color: red;">极高</td>
				               </c:if>
				               <c:if test="${ucAlarmlog.almpriority==2}">
				               <td style="color:#F79646;">高</td>
				               </c:if>
				               <c:if test="${ucAlarmlog.almpriority==3}">
				               <td style="color: #996633;">中</td>
				               </c:if>
				               <c:if test="${ucAlarmlog.almpriority==4}">
				               <td style="color: #33CC33;">低</td>
				               </c:if>
				               <c:if test="${ucAlarmlog.reviewer == '1'}">
				               <td>已应答</td>
				               </c:if>
				               <c:if test="${ucAlarmlog.reviewer != '1'}">
				               <td>未应答</td>
				               </c:if>
				             </tr>
			            </c:forEach>
					</tbody>
				</table>
			 <div class="paging">
				<img src="${pageContext.request.contextPath}/resources/images/left_first.png" onclick="selectFy('First',-1);"/> 
				<img src="${pageContext.request.contextPath}/resources/images/left.png" onclick="selectFy('Before',-1);"/>
				<span class="page_des1">Page</span><span class="bc"></span> <span class="page_des2">of ${endIndex}</span>
				<img src="${pageContext.request.contextPath}/resources/images/right.png" onclick="selectFy('After',-1);"/> 	
				<img src="${pageContext.request.contextPath}/resources/images/right_end.png" onclick="selectFy('End',-1);"/>
			</div>
			</div>
		</div>
</div>
<div id="qz" style="display: none;"></div>
