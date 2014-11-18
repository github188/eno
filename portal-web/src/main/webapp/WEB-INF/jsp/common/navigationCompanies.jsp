<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<c:set var="requestURL"
	value="${requestScope['javax.servlet.forward.request_uri']}"
	scope="request" />
<c:choose>
	<c:when test="${fn:contains(requestURL, '/companies/list')}">
		<c:set var="clsList" value="active" />
		<c:set var="optStatus" value="disabled" />
	</c:when>
	<c:otherwise>
		<c:set var="clsList" value="" />
		<c:set var="optStatus" value="enabled" />
	</c:otherwise>
</c:choose>

<div class="btn-toolbar">
	<!-- <select class="selectpicker span1">
		<option></option>
		<option>所有条目</option>
		<option>书签条目</option>
	</select> -->
	<!-- <label for="keywords" class="control-label">查找：</label>
	<div class="controls">
		<input type="text" class="input-small" id="keywords" />
	</div> -->
	<div class="btn-group">
		<button class="btn dropdown-toggle input-medium"
			data-toggle="dropdown">
		更多操作 <span class="caret"></span>
		</button>
		<ul class="dropdown-menu ">
			<c:if test="${empty clsList}">
				<li class="divider"></li>
				<li><a href="#" id="copyCompany">复制公司</a></li>
				<li><a href="${pageContext.request.contextPath}/companies/delete/${companiesid}" id="delCompany">删除公司</a></li>
				<!-- <li><a href="#">添加至书签</a></li> -->
				<li class="divider"></li>
				
			</c:if>
			<!-- <li><a href="#">运行报表</a></li> -->
		</ul>

	</div>
	<!-- /btn-group -->
	<a href="<spring:url value="/companies/new.html" htmlEscape="true" />"
		class="btn btn-primary">新建</a>
	<button id="submit" class="btn btn-primary" type="button" ${optStatus}>保存</button>
	<button id="reset" class="btn btn-primary" type="button" ${optStatus}>清除更改</button>
	<%-- <button id="previous" class="btn btn-success" type="button"
		${optStatus}>
		<i class="icon-arrow-left"></i> 上一条
	</button>
	<button id="next" class="btn btn-success" type="button" ${optStatus}>
		下一条 <i class="icon-arrow-right"></i>
	</button> --%>
</div>
<c:choose>
	<c:when test="${companiesid==null or companiesid eq ''}">
		<spring:url value="/companies/new.html" var="editUrl" htmlEscape="true"></spring:url>
		<c:set var="companiesid" value="0"></c:set>
	</c:when>
	<c:otherwise>
		<spring:url value="/companies/edit/${companiesid}.html"
			var="editUrl" htmlEscape="true"></spring:url>
	</c:otherwise>
</c:choose>
<ul class="nav nav-tabs" style="margin-bottoM: 0px;">
	<li
		<c:if test="${fn:contains(requestURL, '/companies/list')}">class="active"</c:if>><a
		href="<spring:url value="/companies/list.html" htmlEscape="true" />">列表</a></li>
	<li
		<c:if test="${fn:contains(requestURL, '/companies/new') or fn:contains(requestURL, '/companies/edit/') }">class="active"</c:if>><a
		href="${editUrl}">公司</a></li>
	<li
		<c:if test="${fn:contains(requestURL, '/address')}">class="active"</c:if>><a
		href="<spring:url value="/companies/${companiesid}/address.html" htmlEscape="true" />">地址</a></li>
	<li
		<c:if test="${fn:contains(requestURL, '/branch')}">class="active"</c:if>><a
		href="<spring:url value="/companies/${companiesid}/branch.html" htmlEscape="true" />">分支机构</a></li>
</ul>


