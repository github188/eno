<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../taglib.jsp"%>

<%
	org.joda.time.DateTimeZone.setDefault(org.joda.time.DateTimeZone.forID("Asia/Shanghai"));
	pageContext.setAttribute("now", new org.joda.time.LocalDateTime());
%>

<c:set var="requestURL" value="${requestScope['javax.servlet.forward.request_uri']}" scope="request" />
<%@ page session="false"%>
<c:if test="${!ajaxRequest}">
	<!DOCTYPE html>
	<html lang="en" ng-app>
<head>
<meta charset="utf-8">
<title><tiles:insertAttribute name="title" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<meta HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
<meta HTTP-EQUIV="expires" CONTENT="0">
<link rel="shortcut icon" href="<spring:url value="/resources/img/favicon.ico" />">
<script type="text/javascript">
	var CONTEXT_PATH = "${pageContext.request.contextPath}";
	var LOCALADDR = "${pageContext.request.serverName}";
    var PORT="${pageContext.request.serverPort}";
</script>
<tiles:insertAttribute name="commoninclude" />
<tiles:insertAttribute name="scripts" />
</head>

<c:if test="${not fn:contains(okcMenu.views,'LIST')}">
	<c:set var="current" value="current"></c:set>
</c:if>
<body style="width:1920px;height:1080px;" scroll="no">
</c:if>
<!-- script移动到张天乐【08031440】 -->
<div id="loading" style="display:none;" class="loading"></div>

<div class="navbar">
    <h1 class="logo">ENO</h1>
    <div class="topnav">
        <ul>
            <li <c:if test="${fn:contains(requestURL, 'dashboard')}"> class="current"</c:if>><a href="${pageContext.request.contextPath}/dashboard.html" class="icon_big dashboard" title="DashBoard"></a><c:if test="${fn:contains(requestURL, 'dashboard')}"><span>DashBoard</span></c:if></li>
            <li <c:if test="${fn:contains(requestURL, 'runMonitor')}"> class="current"</c:if>><a href="${pageContext.request.contextPath}/runMonitor/runMonitor.html" class="icon_big runMonitor" title="运行监测"></a><c:if test="${fn:contains(requestURL, 'runMonitor')}"><span>运行监测</span></c:if></li>
            <li <c:if test="${fn:contains(requestURL, 'alarmManage')}"> class="current"</c:if>><a href="${pageContext.request.contextPath}/alarmManage/alarmManage.html" class="icon_big alarmManage" title="报警管理"></a><c:if test="${fn:contains(requestURL, 'alarmManage')}"><span>报警管理</span></c:if></li>
            <li <c:if test="${fn:contains(requestURL, 'energyManage')}"> class="current"</c:if>><a href="${pageContext.request.contextPath}/energyManage/energyManage.html" class="icon_big energyManage" title="能源管理"></a><c:if test="${fn:contains(requestURL, 'energyManage')}"><span>能源管理</span></c:if></li>
            <li <c:if test="${fn:contains(requestURL, 'quotaManage')}"> class="current"</c:if>><a href="${pageContext.request.contextPath}/quotaManage/quotaManage.html" class="icon_big quotaManage" title="定额管理"></a><c:if test="${fn:contains(requestURL, 'quotaManage')}"><span>定额管理</span></c:if></li>
            <li <c:if test="${fn:contains(requestURL, 'deviceManage')}"> class="current"</c:if>><a href="${pageContext.request.contextPath}/deviceManage/deviceManage.html" class="icon_big deviceManage" title="设备管理"></a><c:if test="${fn:contains(requestURL, 'deviceManage')}"><span>设备管理</span></c:if></li>
            <li <c:if test="${fn:contains(requestURL, 'lowCarbon')}"> class="current"</c:if>><a href="${pageContext.request.contextPath}/lowCarbon/lowCarbon.html" class="icon_big lowCarbon" title="低碳管理"></a><c:if test="${fn:contains(requestURL, 'lowCarbon')}"><span>低碳管理</span></c:if></li>
            <li <c:if test="${fn:contains(requestURL, 'reportForm')}"> class="current"</c:if>><a href="${pageContext.request.contextPath}/reportForm/reportForm.html" class="icon_big reportForm" title="报表管理"></a><c:if test="${fn:contains(requestURL, 'reportForm')}"><span>报表管理</span></c:if></li>
        </ul>
    </div>
    <div class="datebox">
        <span class="weather_pic icon_big"></span>
        <div class="weather_detail">
            <p class="weather_info ft18">阴转小雨</p>
            <p class="ft_robotoB ft15">12℃-20℃</p>
        </div>
        <div class="date">
            <span class="time ft50" id="global_time"><joda:format value="${now}" pattern="HH:mm" /></span>
            <div class="date_info">
                <p class="ft18" id="global_week">星期三</p>
                <p class="ft_robotoB ft15" id="global_date"><joda:format value="${now}" pattern="yyyy-MM-dd" /></p>
            </div>
        </div>
    </div>
    <div class="login_user"><a href="#" class="ft20">admin</a><i class="icon_small hideUp"></i>
        <ul class="login_user_box">
            <li><a href="#userManager" data-toggle="modal">用户管理</a></li>
            <li><a href="#changePwd " data-toggle="modal" class="on">修改密码</a></li>
            <li><a href="<c:url value="/logout" />" class="last">退出管理</a></li>
        </ul>
    </div>
</div>


<tiles:importAttribute name="sidebar" />
<tiles:importAttribute name="navigation" />


<c:set var="hasSidebar" value="${sidebar}" />
<c:set var="hasNavigation" value="${navigation}" />
<div class="span12 main row-fluid" style="margin: 0px;">
	<tiles:insertAttribute name="sidebar" />
	<c:choose>
		<c:when test="${empty hasSidebar}">
			<c:if test="${!empty hasNavigation}">
			<tiles:insertAttribute name="navigation" />
			</c:if>
			<tiles:insertAttribute name="body" />
		</c:when>
		<c:otherwise>
			<tiles:insertAttribute name="navigation" />
			<tiles:insertAttribute name="body" />
		</c:otherwise>
	</c:choose>
</div>

<%@ include file="../../user/userManager.jsp"%>
<c:if test="${!ajaxRequest}">
	<tiles:insertAttribute name="footer" />
	</body>
	</html>
</c:if>