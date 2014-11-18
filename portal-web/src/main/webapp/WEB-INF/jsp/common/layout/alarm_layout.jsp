<%@page import="com.energicube.eno.common.Config"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<%
	pageContext.setAttribute("now", new org.joda.time.LocalDateTime());
%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../taglib.jsp"%>
<%@ page session="false"%>
<c:if test="${!ajaxRequest}">
	<!DOCTYPE html>
	<html lang="en">
<head>
<meta charset="utf-8">
<title><tiles:insertAttribute name="title" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
<% 
com.energicube.eno.common.Config mt = new com.energicube.eno.common.Config();
String activeurl = "'"+mt.getProps().getProperty("getAlarmDefUrl")+"'";
%> 
<script type="text/javascript">
	var CONTEXT_PATH = "${pageContext.request.contextPath}";
	var ALARM_IP = <%=activeurl%>;
	var IPADDRESS = "${pageContext.request.localAddr}";
	var wsUrl = "<spring:url value="/ws"></spring:url>";
</script>
<tiles:insertAttribute name="assets" />
</head>
<body>
</c:if>
<div class="navbar navbar-inverse ">
	<div class="navbar-inner">
		<a class="brand" href="#"><img
			src="<spring:url value="/resources/images/wdd.png" />"></a>
		<div class="nav-collapse collapse">
			<ul class="nav pull-left" id="navigation">
				<li class="" id="PWARN"><a href="${pageContext.request.contextPath}/pwarn/alarmcurr.html">报警管理</a></li>
                <li class="" value="1"><a href="#" id="windowSwitch" leftUrl="<spring:url value="/pwarn/alarmcurr.html"></spring:url>" rightUrl="<spring:url value="/mctrl.html"></spring:url>">窗口切换</a></li>
			</ul>
			<ul class="nav pull-right">
                <li class="weather">
                    <div class="weatherImg pull-left" id="WEATHER_ICON">
                        <%-- <img src="<spring:url value="/resources/images/weather.png" />" /> --%>
                    </div>
                    

                </li>
				<li class="clock">
					<div class="time pull-left" id="global_time"><joda:format value="${now}" pattern="HH:mm" /></div>
					<div class="date pull-right">
						<div class="describe" id="global_week">星期三</div>
						<div class="describe_value" id="global_date"><joda:format value="${now}" pattern="yyyy-MM-dd" /></div>
					</div>

				</li>
			</ul>
		</div>
	</div>
</div>
<tiles:importAttribute name="sidebar" />
<c:set var="hasSidebar" value="${sidebar}" />
<div class="span12 main row-fluid" style="margin: 0px;">
	<tiles:insertAttribute name="sidebar" />
	<c:choose>
		<c:when test="${empty hasSidebar}">
			<tiles:insertAttribute name="body" />
		</c:when>
		<c:otherwise>
			<tiles:insertAttribute name="navigation" />
			<tiles:insertAttribute name="body" />
		</c:otherwise>
	</c:choose>
</div>

<c:if test="${!ajaxRequest}">
	<tiles:insertAttribute name="footer" />
	</body>
	</html>
</c:if>

<tiles:insertAttribute name="scripts" />