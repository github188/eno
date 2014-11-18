<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="datatables" uri="http://github.com/dandelion/datatables" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%
	org.joda.time.DateTimeZone.setDefault(org.joda.time.DateTimeZone.forID("Asia/Shanghai"));
	pageContext.setAttribute("now", new org.joda.time.LocalDateTime());
%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../taglib.jsp"%>
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
<tiles:insertAttribute name="assets" />

</head>

<style type="text/css">

</style>
<body style="width:1920px;height:1080px;" scroll="no">
</c:if>
<!-- script移动到张天乐【08031440】 -->
<div id="loading" style="display:none;" class="loading"></div>
<div class="navbar navbar-inverse ">
	<div class="navbar-inner">
		<a class="brand logo" href="#"><img
			src="<spring:url value="/resources/images/wdd.png" />"></a>
		<div class="nav-collapse collapse">
			<ul class="nav pull-left" id="navigation">
				<shiro:hasPermission name="MCTRL" >
					<li class="" id="MCTRL"><a href="${pageContext.request.contextPath}/mctrl.html">系统首页</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="SSRUN" >
					<li class="" id="SSRUN"><a href="${pageContext.request.contextPath}/noticeBoard/noticeBoardView.html">运行日志</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="ASSET" >
					<li class="" id="ASSET"><a href="${pageContext.request.contextPath}/asset/list.html">维修信息</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="SAASC" >
					<li class="" id="SAASC"><a href="${pageContext.request.contextPath}/pattern/menu/HVAC.html">模式管理</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="CALTL" >
					<li class="" id="CALTL"><a href="${pageContext.request.contextPath}/calendar/showMonthCalendar.html">系统日历</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="OKCSYS" >
					<li class="" id="OKCSYS"><a href="${pageContext.request.contextPath}/okcsys/user.html">用户管理</a></li>
				</shiro:hasPermission>
				<shiro:hasPermission name="PACS" >
					<li class="" id="REPORT"><a href="${pageContext.request.contextPath}/report/deviceTrend.html">报表查询</a></li>
				</shiro:hasPermission>
				<%--<shiro:hasPermission name="PACS" >--%>
					<%--<li class="" id="PACS"><a href="${pageContext.request.contextPath}/noticeBoard/noticeBoardView.html">门户管理</a></li>--%>
				<%--</shiro:hasPermission>--%>

                <li class="" value="1"><a href="#" id="windowSwitch" leftUrl="<spring:url value="/pwarn/alarmcurr.html"></spring:url>" rightUrl="<spring:url value="/mctrl.html"></spring:url>">窗口切换</a></li>
			</ul>
			<ul class="nav pull-right">
				<li class="dropdown user_menu"><a href="#"
					class="dropdown-toggle" data-toggle="dropdown" id="userinfo"><shiro:principal property="loginid" />
						<b class="caret"></b>
				</a>
					<ul class="dropdown-menu">
						<li><a href="#">用户首选项</a></li>
						<li><a href="<spring:url value="/message/messageList.html"></spring:url>">站内消息</a></li>
						<li><a href="#">更改密码</a></li>
						<li class="divider"></li>
						<li><a href="<c:url value="/logout" />">退出</a></li>
					</ul></li>
				<li class="weather">
					<div class="weatherImg pull-left" id="WEATHER_ICON">
						<%-- <img src="<spring:url value="/resources/images/weather.png" />" /> --%>
					</div>
					<div class="weather_detail pull-right">
                        <div class="describe" id="WEATHER_CN"></div>
                        <div class="describe_value" id="WEATHER_TEMP"></div>
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
    <div class="mb_nav"></div>
</div>
<!-- 基本属性-->
<div id="global_mask">
    <div class="dialog-popover normal_info">
        <div class="dialog-header">
            <p class="dialog-title">基本属性</p>
            <div class="dialog-close close_pop">×</div>
        </div>
        <div class="normal_info_content">
            <div>
                <div class="build_pic"></div>
                <div class="build_para">
                    <table class="client_para">
                        <tr class="big_word">
                            <td><span>万达广场</span><input type="text"/></td>
                            <td><span>丹东</span><input type="text"/></td>
                            <td><span>100,000</span><sup>m<sup>2</sup></sup><input type="text"/></td>
                            <td><span>2013</span><input type="text"/></td>
                        </tr>
                        <tr>
                            <td>建筑名称</td>
                            <td>地理位置</td>
                            <td>建筑面积</td>
                            <td>建成年代</td>
                        </tr>
                        <tr class="big_word">
                            <td><span>商业综合体</span><input type="text"/></td>
                            <td><span>集中式全空气系统</span><input type="text"/></td>
                            <td><span>6</span><sup>层</sup><input type="text"/></td>
                            <td><span>混凝土</span><input type="text"/></td>
                        </tr>
                        <tr>
                            <td>建筑类型</td>
                            <td>空调类型</td>
                            <td>建筑层数</td>
                            <td>建筑结构</td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
        <div class="self-inspection-popover-footer">
            <div class="qd_btn dialog-btn">确定</div>
            <div class="preview_btn dialog-btn">预览</div>
            <div class="edit_info_btn dialog-btn">编辑</div>
            <div class="close_btn dialog-btn">取消</div>
        </div>
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


<%-- <c:out value="${userDetails}"></c:out> <br />
<c:out value="${userDetails.authorities}"></c:out>

<c:forEach items="${userDetails.authorities}" var="auth">
	<c:out value="${auth}"></c:out> <br />
</c:forEach> --%>


<c:if test="${!ajaxRequest}">
	<tiles:insertAttribute name="footer" />
	</body>
	</html>
</c:if>

<tiles:insertAttribute name="scripts" />
