<%@page import="com.energicube.eno.monitor.model.KeyValue"%>
<%@page import="java.util.List"%>
<%@page import="com.energicube.eno.common.Const"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<link rel="stylesheet" type="text/css"
	  href="${pageContext.request.contextPath}/resources/css/bootstrap-select.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugin/jquery-ui-1.11.2/jquery-ui.min.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/main.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/alertManage.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/bootstrap-select.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/main.js"></script>

<div class="span10">
	<div class="row-fluid">
		<div class="span6 title alertSituation">
			<div class="alert_title">
				<div>
					<span class="sub_title">报警概况</span><span class="this_week">当日报警&nbsp;&nbsp;</span>
					<!-- 						<span class="little_ico">▼</span> -->
					<div class="division_line"></div>
				</div>
			</div>
			<%-- 			<div class="chart_style" style="background-image:url(${pageContext.request.contextPath}/resources/images/bj1.png);"> --%>
			<!-- 			</div> -->
			<div class="chart_style"  style="background-image:url(${pageContext.request.contextPath}/resources/images/alertBj.png);">&nbsp;</div>
			<div id="num_one" style="width : 100px; height : 100px ; line-height: 100px; text-align : center;position: absolute;left:335px;top:350px;font-size:44px;color:#FFF">0</div>
			<div id="num_two" style="width : 100px; height : 100px ; line-height: 100px; text-align : center;position: absolute;left:600px;top:350px;font-size:44px;color:#FFF">0</div>
			<div id="num_three" style="width : 100px; height : 100px ; line-height: 100px; text-align : center;position: relative;left:564px;top:-184px;font-size:44px;color:#FFF" >0</div>

			<%--<div id="c1" style="line-height: 100px;width: 280px;position: relative;left: -41px;top: -570px;"></div>--%>
			<%--<div id="c2" style="line-height: 100px;width: 280px;position: relative;left: 217px;top: -850px;"></div>--%>
			<%--<div id="c3" style="line-height: 100px;width: 280px;position: relative;left: 473px;top: -1130px;"></div>--%>
		</div>
		<div class="span6 title alertRecord">
			<div class="alert_title">
				<div>
					<span class="sub_title">报警记录</span><span class="this_week">当日报警&nbsp;&nbsp;</span>
					<!-- 					 <span class="little_ico">▼</span> -->
					<div class="division_line"></div>
				</div>
			</div>
			<%-- 			<div class="chart_style" style="background-image:url(${pageContext.request.contextPath}/resources/images/bj2.png);"></div> --%>
			<div class="chart_style" id="container"></div>
		</div>
	</div>

	<div class="row-fluid">
		<div class="alert_title title" style="float: left;width: 90%;">
			<div>
				<span class="sub_title float_l">实时报警</span> <span
					class="little_ico btn3 float_l">▼</span>
				<div class="division_line float_l div_style2" style="width: 50%;"></div>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <span
					style="font-family: 微软雅黑; font-size: 14px; font-weight: normal; font-style: normal; text-decoration: none; color: #37969D;">报警级别：</span>
				<select id="alarmpriority" name="alarmpriority" class="span1"
						style="width: 223px; height: 35px" onchange="show();">
					<option value="0">请选择</option>
					<c:forEach items="${alarmLevels}" var="alarmLevel">
						<option value="${alarmLevel.dmvalue }">${alarmLevel.description}</option>
					</c:forEach>
				</select>
			</div>
		</div>

		<div id="deviceShowStatus"  value="1" class="alarms_right_text <c:if test="${TELNET=='Y'}"> alarms_right_no</c:if><c:if test="${TELNET=='N'}"> alarms_right_ok</c:if>"><span><c:if test="${TELNET=='Y'}">设备通讯故障</c:if><c:if test="${TELNET=='N'}">设备通讯正常</c:if></span></div>


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
					<th>报警状态</th>
				</tr>
				<c:forEach items="${ucAlarmactives}" var="ucAlarmactive">
					<tr onclick="answer(${ucAlarmactive.almlogid},'${ucAlarmactive.almt}','${ucAlarmactive.tagid}','${ucAlarmactive.tagname}');" style="cursor: pointer;">
						<td><fmt:formatDate value="${ucAlarmactive.almt}"
											pattern="yyyy年MM月dd日" /></td>
						<td><fmt:formatDate value="${ucAlarmactive.almt}"
											pattern="HH点mm分ss秒" /></td>
						<td>${ucAlarmactive.almcomment }</td>
						<td>${ucAlarmactive.groupname }</td>
						<td>${ucAlarmactive.devicename }</td>
						<td>${ucAlarmactive.tagcomment }</td>
						<c:if test="${ucAlarmactive.almpriority==1}">
							<td style="color: red;">极高</td>
						</c:if>
						<c:if test="${ucAlarmactive.almpriority==2}">
							<td style="color:#F79646;">高</td>
						</c:if>
						<c:if test="${ucAlarmactive.almpriority==3}">
							<td style="color: #996633;">中</td>
						</c:if>
						<c:if test="${ucAlarmactive.almpriority==4}">
							<td style="color: #33CC33;">低</td>
						</c:if>
						<c:if test="${ucAlarmactive.almt>'2000'}">
							<td>已应答</td>
						</c:if>
						<c:if test="${ucAlarmactive.almt<'2000'}">
							<td>未应答</td>
						</c:if>
					</tr>
				</c:forEach>
				</tbody>
			</table>
			<div class="paging">
				<img src="${pageContext.request.contextPath}/resources/images/left_first.png" onclick="selectFy('First',-1);"/> <img
					src="${pageContext.request.contextPath}/resources/images/left.png" onclick="selectFy('Before',-1);"/>
				<span class="page_des1">Page</span>
				<span class="bc"></span><span class="sc">of</span><span class="page_des2">
				${endIndex}</span> <img src="${pageContext.request.contextPath}/resources/images/right.png" onclick="selectFy('After',-1);"/> <img
					src="${pageContext.request.contextPath}/resources/images/right_end.png" onclick="selectFy('End',-1);"/>
			</div>
		</div>
	</div>
	<div id="dialog-modal" class="popover1" title="报警详细页" style="display:none;">
		<div class="popover_nav">
			<ul>
				<li class="cur" style="cursor:pointer"><i class="icon_file_pop"></i>
					<p>报警内容</p></li>
				<li style="cursor:pointer"><i class="icon_file_question"></i>
					<p>可能原因</p></li>
				<li style="cursor:pointer"><i class="icon_file_list"></i>
					<p>处理流程</p></li>
				<li style="cursor:pointer"><i class="icon_file_list1"></i>
					<p>批注</p></li>
				<li style="cursor:pointer"><i class="icon_file_list1"></i>
					<p>抑制原因</p></li>
				<li style="cursor:pointer"><i class="icon_file_list2"></i>
					<p>编辑级别</p></li>
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
				<textarea id="textareaIdflow" readonly="readonly" style="width: 80%; margin-left: 50px;"  rows="10" cols=""></textarea>
			</div>
			<div class="alert_ol handle_flowd" align="center">
				<textarea id="textareaId" style="width: 80%" rows="10" cols=""></textarea>
				<input id="alarmTagId" type="hidden"/>
				<input id="alarmTagName" type="hidden" />
				<input id="alarmLogId" type="hidden" />
			</div>
			<div class="alert_ol handle_flowd" align="center">
				<textarea id="reason" style="width: 80%" rows="10" cols=""></textarea>
			</div>
			<div class="alert_ol handle_flowd" align="center">
				<select id="alarmGrade" onchange="editAlarmGrade(this)">
					<option value="1">极高</option>
					<option value="2">高</option>
					<option value="3">中</option>
					<option value="4">低</option>
				</select>
			</div>
		</div>

		<div class="modal_btn">
			<div class="hand_ok" onclick="alarmCancel('Y')">抑制</div>
			<div class="hand_ok" onclick="ansYd()">应答</div>
			<div class="cancel">关闭</div>
		</div>
	</div>

	<!-- CHECK MONITOR MODAL -->
	<div id="checkMonitorModal" class="modal hide fade checkMonitorModal" tabindex="-1"
		 role="dialog" aria-labelledby="checkMonitorModalLabel"
		 aria-hidden="true" data-backdrop='false'>
		<div class="modal-header">
			<h3 id="checkMonitorModalLabel">查岗应答</h3>
		</div>
		<div class="modal-body">
			<div class="cont-title">
				<i class="icon-time"></i>&nbsp;<span data-bind="text:chekmonitorTitle"></span><br />
			</div>
			<div class="cont-body text-center">
				<div id="cont-countdown">
					<span class="cont-body-minute" data-bind="text:minute"> 00</span> <span
						class="cont-body-lable">分</span> <span class="cont-body-second"
															   data-bind="text:second"> 00</span><span class="cont-body-lable">秒
					</span>
				</div>
			</div>
			<div class="cont-footer text-center">查岗时间已到，请应答</div>
		</div>
		<div class="modal-footer">
			<button class="btn-reponse" data-bind="click:responseCheckMonitor">应答</button>
		</div>
	</div>


	<!-- Manual Alarm MODAL -->
	<div id="manualAlarmModal" class="alermmodal modal hide fade" tabindex="-1"
		 role="dialog" aria-labelledby="manualAlarmModalLabel"
		 aria-hidden="true" data-backdrop='false'>
		<div class="modal-header">
			<h3 id="manualAlarmModalLabel">手动报警上传</h3>
		</div>
		<div class="modal-body">
			<div class="cont-body text-center">

				请确认是否上传紧急火警信息?


			</div>
		</div>
		<div class="modal-footer">
			<button class="btn-cannel" data-dismiss="modal" aria-hidden="true">取消</button>
			<button class="btn-reponse" data-bind="click:sendManualAlarm">确定</button>
		</div>
	</div>


	<!-- Confirm Fire Alarm MODAL -->
	<div id="fireMonitorModal" class="popover1" title="自动报警上传"  >

		<div class="modal-body">
			<div class="cont-title">
				<i class="icon-time"></i>&nbsp;<span data-bind="text:fireMonitorTitle" style="font-size: 19px;"></span><br />
			</div>
			<div class="cont-body text-center">
				<div id="cont-countdown" style="font-size: 19px;margin-top: 8px;margin-bottom: 5px;">
					<span class="cont-body-minute" data-bind="text:confirmFireMinute"> 00</span> <span
						class="cont-body-lable">分</span> <span class="cont-body-second"
															   data-bind="text:confirmFireSecond"> 00</span><span class="cont-body-lable">秒
					</span>
				</div>
			</div>
			<div class="cont-title" style="margin-left:15px;font-size: 19px;margin-top: 20px;margin-bottom: 5px;">
				&nbsp;请确认报警类型： <span class="text-error">*</span><br />
			</div>
			<div class="cont-footer text-center" >
				<form id="frmFiremonitor" class="form-inline">
					<label>
						<input type="radio" name="signaltype" value="QRHJ" style="font-size: 19px;"> 确认火警 </label>
					<label>
						<input type="radio" name="signaltype" value="WBHJ" style="font-size: 19px;"> 误报火警  </label>
					<label>
						<input type="radio" name="signaltype" value="CSHJ" style="font-size: 19px;"> 测试火警</label>
				</form>
				<div class="alert hide fade in" style="font-size: 19px;"> 请选择警情类型</div>
			</div>
		</div>
		<div class="modal_btn">
			<div  data-bind="click:confirmAlarm" >确定</div>
		</div>
	</div>

</div>
<div id="qz" style="display: none;"></div>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/alertManage.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/highcharts.js"></script>
<script type="text/javascript">

	function changeMusic(state){
		var url = "http://192.168.1.254:8089/tag/rtdb/set?tagname=qiting&value="+state+"&jsoncallback=?";
		$.getJSON(url, function () {
		});
	}

	var aft = ${endIndex};
	var headerdescription = '${groupname}';
	var dataList1 = ${dataList1};
	var dataList2 = ${dataList2};
	var dataList3 = ${dataList3};
	var cataList = ${cataList};
	var zjs1 = ${zjs1};
	var zjs2 = ${zjs2};
	var zjs3 = ${zjs3};
	var endIndexInt = ${endIndex};
	$("#alarmpriority").val(${alarmpriorityValue});
	var telnet = "${TELNET}";
	var alertGroupname = "${alarmAlert.groupname}";
	var alertLogid = "${alarmAlert.almlogid}";
	var alertAlmt = "${alarmAlert.almt}";
</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/alarms/activeList.js"></script>