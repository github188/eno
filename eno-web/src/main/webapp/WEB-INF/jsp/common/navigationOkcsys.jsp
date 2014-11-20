<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<c:set var="requestURL"
	value="${requestScope['javax.servlet.forward.request_uri']}"
	scope="request" />

<div class="navbar " id="sysnav" style="margin-bottom:10px;">
	<div class="navbar-inner" style="padding:0 20px;">
		<div class="container">
			<a class="btn btn-navbar" data-toggle="collapse"
				data-target=".navbar-responsive-collapse"> <span
				class="icon-bar"></span> <span class="icon-bar"></span> <span
				class="icon-bar"></span>
			</a> <a class="brand" href="#" style="padding: 10px 20px 10px;">系统设置</a>
			<div class="nav-collapse collapse navbar-responsive-collapse">
				<ul class="nav">
					<li class="<c:if test="${fn:contains(requestURL,'okcmenu')}">active</c:if>"><a href="<spring:url value="/okcsys/okcmenu.html"></spring:url>">菜单设置</a></li>
					<li class="<c:if test="${fn:contains(requestURL,'page')}">active</c:if>"><a href="<spring:url value="/okcsys/page.html"></spring:url>">页面管理</a></li>
					<li class="<c:if test="${fn:contains(requestURL,'controls')}">active</c:if>"><a href="<spring:url value="/okcsys/controls.html"></spring:url>">系统组件</a></li>  
					<li class="<c:if test="${fn:contains(requestURL,'sysprops')}">active</c:if>"><a href="<spring:url value="/okcsys/sysprops.html"></spring:url>">系统参数</a></li>
					<li class="<c:if test="${fn:contains(requestURL,'datasources')}">active</c:if>"><a href="<spring:url value="/okcsys/datasources.html"></spring:url>">数据源</a></li>
					<li class="<c:if test="${fn:contains(requestURL,'datasql')}">active</c:if>"><a href="<spring:url value="/okcsys/datasql.html"></spring:url>">SQL配置</a></li>
					<li class="<c:if test="${fn:contains(requestURL,'dataconfigapp')}">active</c:if>"><a href="<spring:url value="/okcsys/dataapp.html"></spring:url>">APP配置</a></li>
					<li class="<c:if test="${fn:contains(requestURL,'linkage')}">active</c:if>"><a href="<spring:url value="/linkage/view_condition.html"></spring:url>">联动控制</a></li>
					<!-- 小牛配置添加到okcsys[140803-13:49]ztl -->
					<li class="<c:if test="${fn:contains(requestURL,'video')}">active</c:if>"><a href="<spring:url value="/okcsys/video/sysconfig.html"></spring:url>">小牛配置</a></li>
					<li class="<c:if test="${fn:contains(requestURL,'import')}">active</c:if>"><a href="<spring:url value="/okcsys/import/config.html"></spring:url>">导入配置</a></li>
					<li class="<c:if test="${fn:contains(requestURL,'devicelist')}">active</c:if>"><a href="<spring:url value="/okcsys/devicelist/config.html"></spring:url>">设备列表配置</a></li>
					<li class="<c:if test="${fn:contains(requestURL,'panelConfig')}">active</c:if>"><a href="<spring:url value="/okcsys/panel/panelConfig.html"></spring:url>">面板配置</a></li>
					<li class="<c:if test="${fn:contains(requestURL,'expression')}">active</c:if>"><a href="<spring:url value="/okcsys/expression/index.html"></spring:url>">表达式配置</a></li>
					<!-- 首页系统参数配置 [140926-09:57]mopeng -->
					<li class="<c:if test="${fn:contains(requestURL,'homepage')}">active</c:if>"><a href="<spring:url value="/okcsys/homepage/parameterconfig.html"></spring:url>">首页系统参数配置</a></li>
					<!-- 登陆页配置 LiHuiHui-->
					<li class="<c:if test="${fn:contains(requestURL,'sysinfo')}">active</c:if>"><a href="<spring:url value="/okcsys/sysinfo/editSysinfo.html"></spring:url>">登陆页参数配置</a></li>
				</ul>
			</div>
			<!-- /.nav-collapse -->
		</div>
	</div>
	<!-- /navbar-inner -->
</div>
<!-- /navbar -->


