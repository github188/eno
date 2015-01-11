<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<%
	org.joda.time.DateTimeZone.setDefault(org.joda.time.DateTimeZone.forID("Asia/Shanghai"));
	org.joda.time.LocalDateTime now = new org.joda.time.LocalDateTime();
	pageContext.setAttribute("now", now);
	pageContext.setAttribute("week",now.dayOfWeek().getAsText());
%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../taglib.jsp"%>
<c:set var="requestURL" value="${requestScope['javax.servlet.forward.request_uri']}" scope="request" />
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
<script type="text/javascript">
	var CONTEXT_PATH = "${pageContext.request.contextPath}";
</script>
<tiles:insertAttribute name="assets" />
<tiles:insertAttribute name="scripts" />
</head>
<body>
</c:if>
<div class="navbar navbar-inverse ">
	<div class="navbar-inner">
		<a class="brand" href="#"><img
			src="<spring:url value="/resources/images/wdd.png" />" style="margin: 18px 0 0 10px;"
			></a>
		<div class="nav-collapse collapse">
			<%-- <ul class="nav pull-left" id="navigation">
				<c:forEach items="${mainMenus}" var="menu">
					<li class="" id="${menu.elementvalue}"><a
						href="${pageContext.request.contextPath}${menu.url}">${menu.headerdescription}</a></li>
				</c:forEach>
			</ul> --%>
			
			<c:set var="hostpath" value="http://${pageContext.request.serverName}:${pageContext.request.serverPort}"></c:set>
			
			<ul class="nav pull-left" id="navigation">
			
				<li class="" id="MCTRL"><a href="${hostpath}/portal/mctrl/monitorsum.html">监测与控制</a>
				</li>
				<li class="" id="SSRUN"><a href="${hostpath}/portal/shifts/shiftsView.html">运行管理</a>
				</li>
			
				<li class="" id="ASSET"><a href="${hostpath}/asset/asset/list">设备管理</a>
				</li>
			
				<li class="" id="SAASC"><a href="${hostpath}/portal/pattern/menu/HVAC.html">模式管理</a>
				</li>
			
				<li class="" id="CALTL"><a href="${hostpath}/portal/calendar/showMonthCalendar.html">系统日历</a>
				</li>
			
				<li class="" id="OKCSYS"><a href="${hostpath}/portal/okcsys/user.html">系统管理</a>
				</li>
			
				<li class="" id="PACS"><a href="${hostpath}/portal/noticeBoard/noticeBoardView.html">门户管理</a>
				</li>
			 		
			</ul>
			<ul class="nav pull-right">
				<!-- <li class="dropdown user_menu"><a href="#"
					class="dropdown-toggle" data-toggle="dropdown" id="userinfo">admin
						<b class="caret"></b>
				</a>
					<ul class="dropdown-menu">
						<li><a href="#">用户首选项</a></li>
						<li><a href="javascrip:void(0)">更改密码</a></li>
						<li class="divider"></li>
						<li><a href="#">退出</a></li>
					</ul></li>
				<li class="weather">
					<div class="weatherImg pull-left">
						<img src="<spring:url value="/resources/images/weather.png" />" />
					</div>
					<div class="weather_detail pull-right">
						<div class="describe">阴转小雨</div>
						<div class="describe_value">12&#176;C-20&#176;C</div>
					</div>

				</li> -->
				<li class="clock">
					<div class="time pull-left"><joda:format value="${now}" pattern="HH:mm" /></div>
					<div class="date pull-right">
						<div class="describe"><c:out value="${week}" /></div>
						<div class="describe_value"><joda:format value="${now}" pattern="yyyy-MM-dd" /></div>
					</div>

				</li>
			</ul>
		</div>
	</div>
</div>

<div class="span12 main row-fluid" style="margin: 0px;">
<!-- 	<div class="span2 mainnav"> -->
<!--   <ul id="accordion"> -->
<!--   <!-- 添加全部链接,返回模块主页 --> -->
<!-- 	<!-- <li><div><a  href="#">全部</a></div></li> --> -->
<%-- 	<c:forEach items="${filterMenus}" var="m"> --%>
<%-- 	<c:choose> --%>
<%-- 		<c:when test="${fn:toUpperCase(m.elementtype) eq 'THEADER'}"> --%>
<%-- 		<li><div><a href="#">${m.headerdescription}</a><img src="${pageContext.request.contextPath}/resources/images/upicon.png"> <img class="disnone" src="${pageContext.request.contextPath}/resources/images/downicon.png"></div> --%>
<!-- 			<ul class="sub"> -->
<%-- 			<c:forEach items="${filterMenus}" var="c"> --%>
<%-- 				<c:if test="${(c.position eq m.position) and (fn:toUpperCase(c.elementtype) eq 'TYPE') }"> --%>
<%-- 				<c:set value="?specclass=${c.elementvalue}" var="url" /> --%>
<%-- 					<li class="<c:if test="${fn:contains(requestURL,c.elementvalue)}">cur</c:if>" id="${c.elementvalue}"><i class="icon_${fn:toLowerCase(c.elementvalue)}"></i><a href="${url}">${c.headerdescription}</a></li> --%>
<%-- 				</c:if> --%>
<%-- 			</c:forEach> --%>
<!-- 			</ul> -->
<!-- 		</li> -->
<%-- 		</c:when> --%>
<%-- 		<c:otherwise> --%>
<%-- 			<c:if test="${m.subposition eq 0}"> --%>
<!-- 			<li><div> -->
<%-- 				<c:if test="${fn:toUpperCase(m.elementtype) eq 'TYPE'}"><a href="#">${m.headerdescription}</a></c:if></div></li></c:if> --%>
<%-- 		</c:otherwise> --%>
<%-- 		</c:choose> --%>
<%-- 	</c:forEach> --%>
<!--   </ul> -->
 
<!--   </div> -->
      <div class="left_menu">
        <table>
            <tr>
                <td class="xfgl bt" rowspan="1">消防管理</td>
                <td><i class="icon_fas"></i><a href="<spring:url value="/asset/list.html?specclass=FAS"></spring:url>">消防系统</a></td>
            </tr>
            
            <tr>
                <td class="afgl bt" rowspan="4">安<br>防<br>管<br>理</td>
                <td><i class="icon_msvdo"></i><a href="<spring:url value="/asset/list.html?specclass=MSVDO"></spring:url>">视频监控</a></td>
            </tr>
            <tr>
                <td><i class="icon_sassa"></i><a href="<spring:url value="/asset/list.html?specclass=SASSA"></spring:url>">防盗报警</a></td>
            </tr>
            <tr>
                <td><i class="icon_sasac"></i><a href="<spring:url value="/asset/list.html?specclass=SASAC"></spring:url>">门禁管理</a></td>
            </tr>
            <tr>
                <td><i class="icon_ep"></i><a href="<spring:url value="/asset/list.html?specclass=EP"></spring:url>">电子巡更</a></td>
            </tr>

            <tr>
                <td width="60" class="sbgl bt" rowspan="6">设<br>备<br>管<br>理</td>
                <td class=""><i class="icon_hvac"></i><a href="<spring:url value="/asset/list.html?specclass=HVAC"></spring:url>">暖通空调</a></td>
            </tr>
            <tr>
                <td><i class="icon_wsd"></i><a href="<spring:url value="/asset/list.html?specclass=WSD"></spring:url>">给水排水</a></td>
            </tr>
            <tr>
                <td><i class="icon_etd"></i><a href="<spring:url value="/asset/list.html?specclass=ETD"></spring:url>">变配电</a></td>
            </tr>
            <tr>
                <td><i class="icon_lspub"></i><a href="<spring:url value="/asset/list.html?specclass=LSPUB"></spring:url>">公共照明</a></td>
            </tr>
            <tr>
                <td><i class="icon_lsn"></i><a href="<spring:url value="/asset/list.html?specclass=LSN"></spring:url>">夜景照明</a></td>
            </tr>
            <tr>
                <td><i class="icon_msem"></i><a href="<spring:url value="/asset/list.html?specclass=MSEM"></spring:url>">电梯运行</a></td>
            </tr>

            <tr>
                <td class="yygl bt" rowspan="4">运<br>营<br>管<br>理</td>
                <td><i class="icon_pfe"></i><a href="<spring:url value="/asset/list.html?specclass=PFE"></spring:url>">客流统计</a></td>
            </tr>
            <tr>
                <td><i class="icon_parkm"></i><a href="<spring:url value="/asset/list.html?specclass=PARKM"></spring:url>">停车管理</a></td>
            </tr>
            <tr>
                <td><i class="icon_infp"></i><a href="<spring:url value="/asset/list.html?specclass=INFP"></spring:url>">信息发布</a></td>
            </tr>
            <tr>
                <td><i class="icon_bgmb"></i><a href="<spring:url value="/asset/list.html?specclass=BGMB.html"></spring:url>">背景音乐</a></td>
            </tr>

            <tr>
                <td class="jngl bt" rowspan="1">节能管理</td>
                <td><i class="icon_envms"></i><a href="<spring:url value="/asset/list.html?specclass=ENVMS"></spring:url>">能耗计量</a></td>
            </tr>
        </table>
    </div>

    
	<div class="span10">
		<div class="row-fluid" style="margin:10px 0;">
			<div class="btn-big <c:if test="${fn:contains(requestURL, '/asset/asset/')}">cur</c:if>"><a href="<spring:url value="/asset/list.html" />"><i class="icon_btn icon_wrench"></i><p>设备列表</p></a></div>
           <%--  <div class="btn-big <c:if test="${fn:contains(requestURL, '/measure/')}">cur</c:if>"><a href="<spring:url value="/measure/list.html"></spring:url>"><i class="icon_btn icon_tel"></i><p>点检管理</p></a></div> --%>
           <div class="btn-big <c:if test="${fn:contains(requestURL, '/asset/failurecode/')}">cur</c:if>"><a href="<spring:url value="/failurecode/list.html"></spring:url>"><i class="icon_btn icon_tel"></i><p>故障代码</p></a></div>
            <div class="btn-big <c:if test="${fn:contains(requestURL, '/asset/pm/')}">cur</c:if>"><a href="<spring:url value="/pm/list.html"></spring:url>"><i class="icon_btn icon_tel"></i><p>预防性维护</p></a></div>
            <div class="btn-big <c:if test="${fn:contains(requestURL, '/asset/jobplan/')}">cur</c:if>"><a href="<spring:url value="/jobplan/list.html"></spring:url>"><i class="icon_btn icon_tel"></i><p>作业计划</p></a></div>
			<div class="btn-group" style="float:right;">
					<button class="btn btn-large dropdown-toggle"
						data-toggle="dropdown">
						基础设置 <span class="caret"></span>
					</button>
					<ul class="dropdown-menu">
						<li><a href="<spring:url value="/locations/list.html" />">位置</a></li>
						<li><a href="<spring:url value="/meter/list.html" />">计量器</a></li>
						<li><a href="<spring:url value="/metergroup/list.html"></spring:url>">计量器组</a></li>
						<li><a href="<spring:url value="/class/list.html"></spring:url>">资产分类</a></li>
						<li class="divider"></li>
						<li><a href="<spring:url value="/companies/list.html"></spring:url>">公司</a></li>
					</ul>
				</div>
					<!-- /btn-group -->
		</div>

		<div class="row-fluid">
			<tiles:insertAttribute name="navigation" />
		</div>
		<tiles:insertAttribute name="body" />
	</div>
</div>
<c:if test="${!ajaxRequest}">
	<tiles:insertAttribute name="footer" />
	</body>
	</html>
</c:if>