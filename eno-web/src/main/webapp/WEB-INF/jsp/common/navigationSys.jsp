<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<c:set var="requestURL" value="${requestScope['javax.servlet.forward.request_uri']}" scope="request" />
<!-- <div class="span12" style="margin: 20px 10px"> -->
<%-- <c:forEach items="${okcMenus}" var="okcMenu"> --%>
<%-- <c:if test="${okcMenu.headerdescription eq '用户管理'}"> --%>
<%-- 	<div class="Btn-big btn1 <c:if test="${fn:contains(requestURL,'user.')}">cur</c:if>"> --%>
<%-- 		<a href="<spring:url value="/okcsys/user.html"></spring:url>"><i class="icon_btn icon_user"></i> --%>
<!-- 		<p>用户管理</p></a> -->
<!-- 	</div> -->
<%-- </c:if>	 --%>
<%-- <c:if test="${okcMenu.headerdescription eq '角色与权限'}">	 --%>
<%-- 	<div class="Btn-big btn3 <c:if test="${fn:contains(requestURL,'usergroup')}">cur</c:if>"> --%>
<%-- 		<a href="<spring:url value="/okcsys/usergroup.html"></spring:url>"><i class="icon_btn icon_diamond"></i> --%>
<!-- 		<p>角色与权限</p></a> -->
<!-- 	</div> -->
<%-- </c:if>	 --%>
<%-- <c:if test="${okcMenu.headerdescription eq '系统日志'}">		 --%>
<%-- 	<div class="Btn-big btn3 <c:if test="${fn:contains(requestURL,'logs')}">cur</c:if>"> --%>
<%-- 		<a href="<spring:url value="/okcsys/logs.html"></spring:url>"><i class="icon_btn icon_link"></i> --%>
<!-- 		<p>系统日志</p></a> -->
<!-- 	</div> -->
<%-- </c:if>	 --%>
<%-- <c:if test="${okcMenu.headerdescription eq '系统管理'}">		 --%>
<%-- 	<div class="Btn-big btn3 <c:if test="${fn:contains(requestURL,'okcorghier')}">cur</c:if>"> --%>
<%-- 		<a href="<spring:url value="/okcsys/okcorghier.html"></spring:url>"><i class="icon_btn icon_config"></i> --%>
<!-- 		<p>系统管理</p></a> -->
<!-- 	</div> -->
<%-- </c:if>	 --%>
<%-- </c:forEach> --%>
<!-- </div> -->
<div class="span12" style="margin: 20px 10px">
	<div class="Btn-big btn1 <c:if test="${fn:contains(requestURL,'user.')}">cur</c:if>">
		<a href="<spring:url value="/okcsys/user.html"></spring:url>"><i class="icon_btn icon_user"></i>
		<p>用户管理</p></a>
	</div>
	<div class="Btn-big btn3 <c:if test="${fn:contains(requestURL,'usergroup')}">cur</c:if>">
		<a href="<spring:url value="/okcsys/usergroup.html"></spring:url>"><i class="icon_btn icon_diamond"></i>
		<p>角色与权限</p></a>
	</div>
	<!--  
	<div class="Btn-big btn3 <c:if test="${fn:contains(requestURL,'logs')}">cur</c:if>">
		<a href="<spring:url value="/okcsys/logs.html"></spring:url>"><i class="icon_btn icon_link"></i>
		<p>系统日志</p></a>
	</div>
	<div class="Btn-big btn3 <c:if test="${fn:contains(requestURL,'okcorghier')}">cur</c:if>">
		<a href="<spring:url value="/okcsys/okcorghier.html"></spring:url>"><i class="icon_btn icon_config"></i>
		<p>系统管理</p></a>
	</div>
	-->
</div>