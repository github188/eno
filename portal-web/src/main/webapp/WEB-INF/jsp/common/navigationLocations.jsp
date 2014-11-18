<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp" %>
<c:set var="requestURL"
	value="${requestScope['javax.servlet.forward.request_uri']}"
	scope="request" />
<c:if test="${fn:contains(requestURL, '/locations/list')}">
	<c:set var="clsList" value="active" />
	<c:set var="optStatus" value="disabled" />
</c:if>
<c:if test="${fn:contains(requestURL, '/locations/edit') or fn:contains(requestURL, '/locations/new')}">
	<c:set var="clsDetail" value="active" />
</c:if>
<div class="btn-toolbar">
	<!-- <select class="input-medium">
		<option></option>
		<option>所有条目</option>
		<option>书签条目</option>
	</select> -->
	<div class="btn-group">
		<button class="btn dropdown-toggle input-medium"
			data-toggle="dropdown">
			更多操作 <span class="caret"></span>
		</button>
		<c:if test="${!empty clsDetail}">
			<ul class="dropdown-menu ">
				<li><a href="#">复制故障层级</a></li>
				<li><a href="#">复制故障代码</a></li>
				<li><a href="#">删除故障代码</a></li>
				<li class="divider"></li>
				<li><a href="#" id="dlgLocSystem2">将系统与位置相关联</a></li>
				<li><a href="#" id="linkLocSystem" link="<spring:url value="/locations/locsystems"></spring:url>">管理系统</a></li>
				<li><a href="#">添加至书签</a></li>
				<li class="divider"></li>
				<li><a href="#">运行报告</a></li>
			</ul>
		</c:if>
	</div>
	<!-- /btn-group -->
	<a href="<spring:url value="/locations/new.html" htmlEscape="true" />" class="btn btn-primary">新建</a>
	<button id="submit" class="btn" type="button" ${optStatus}>保存</button>
	<button id="reset" class="btn " type="button" ${optStatus}>清除更改</button>
	<%-- <button id="previous" class="btn btn-success" type="button" ${optStatus}>
		<i class="icon-arrow-left"></i> 上一条
	</button>
	<button id="next" class="btn btn-success" type="button" ${optStatus}>
		下一条 <i class="icon-arrow-right"></i>
	</button> --%>
</div>
<c:choose>
	<c:when test="${empty locationsid or locationsid eq ''}">
		<c:set var="locationsid" value="0"></c:set>
		<spring:url value="/locations/new.html" var="editUrl" htmlEscape="true"></spring:url>
	</c:when>
	<c:otherwise>
		<spring:url value="/locations/edit/${locationsid}.html" var="editUrl" htmlEscape="true"></spring:url>
	</c:otherwise>
</c:choose>
<ul class="nav nav-tabs" style="margin-bottoM: 0px;">
	<li <c:if test="${fn:contains(requestURL, '/locations/list')}">class="active"</c:if>><a
		href="<spring:url value="/locations/list.html" htmlEscape="true" />">列表</a></li>
	<li <c:if test="${fn:contains(requestURL, '/locations/new') or fn:contains(requestURL, '/locations/edit') }">class="active"</c:if>><a
		href="${editUrl}">位置</a></li>
	<li <c:if test="${fn:contains(requestURL, '/assets')}">class="active"</c:if>><a
		href="<spring:url value="/locations/${locationsid}/assets.html" htmlEscape="true" />">资产</a></li>
	<li <c:if test="${fn:contains(requestURL, '/histories')}">class="active"</c:if>><a
		href="<spring:url value="/locations/${locationsid}/histories.html" htmlEscape="true" />">历史记录</a></li>
	<li <c:if test="${fn:contains(requestURL, '/meters')}">class="active"</c:if>><a
		href="<spring:url value="/locations/${locationsid}/meters.html" htmlEscape="true" />">记量器</a></li>
</ul>
