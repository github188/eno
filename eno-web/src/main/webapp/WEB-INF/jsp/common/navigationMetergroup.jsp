<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<c:set var="requestURL"
	value="${requestScope['javax.servlet.forward.request_uri']}"
	scope="request" />
<c:if test="${fn:contains(requestURL, '/metergroup/list')}">
	<c:set var="clsList" value="active" />
	<c:set var="optStatus" value="disabled" />
</c:if>
<c:if test="${fn:contains(requestURL, '/metergroup/edit') or fn:contains(requestURL, '/metergroup/new')}">
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
				<li><a href="#" id="linkDuplicationGroup">复制组</a></li>
				<li><a href="#" id="linkDelGroup">删除组</a></li>
				<li><a href="#" id="linkAddToBookmarks">添加至书签</a></li>
				<li class="divider"></li>
				<li><a href="#" id="linkRunReports">运行报表</a></li>
			</ul>
		</c:if>
	</div>
	<!-- /btn-group -->
	<a href="<spring:url value="/metergroup/new.html" htmlEscape="true" />" class="btn btn-primary">新建</a>
	<button id="submit" class="btn" type="button" ${optStatus}>保存</button>
	<button id="reset" class="btn" type="button" ${optStatus}>清除更改</button>
<%-- 	<button id="previous" class="btn btn-success" type="button" ${optStatus}>
		<i class="icon-arrow-left"></i> 上一条
	</button>
	<button id="next" class="btn btn-success" type="button" ${optStatus}>
		下一条 <i class="icon-arrow-right"></i>
	</button> --%>
</div>

<ul class="nav nav-tabs" style="margin-bottoM: 0px;">
	<li class="${clsList}"><a
		href="<spring:url value="/metergroup/list.html" htmlEscape="true" />">列表</a></li>
	<li class="${clsDetail}"><a
		href="<spring:url value="/metergroup/new.html" htmlEscape="true" />">计量器组</a></li>
</ul>