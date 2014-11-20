<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<c:set var="requestURL"
	value="${requestScope['javax.servlet.forward.request_uri']}"
	scope="request" />
<c:choose>
	<c:when test="${fn:contains(requestURL, '/pm/list')}">
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
	<div class="btn-group">
		<button class="btn dropdown-toggle input-medium"
			data-toggle="dropdown">
		更多操作 <span class="caret"></span>
		</button>
		<ul class="dropdown-menu ">
			<c:if test="${empty clsList}">
				<c:choose>
					<c:when test="${pmid>0}">
					<li><a href="${pageContext.request.contextPath}/pm/changestatus/${pmid}" id="changestatus">变更状态</a></li>
					<li class="divider"></li>
					<li><a href="${pageContext.request.contextPath}/pm/copy/${pmid}" id="copy">复制PM</a></li>
					<li><a href="${pageContext.request.contextPath}/pm/delete/${pmid}" id="delete">删除PM</a></li>
					</c:when>
					<c:otherwise>
					<li><a href="#" id="changestatus">变更状态</a></li>
					<li class="divider"></li>
					<li><a href="#" id="copy">复制PM</a></li>
					<li><a href="#" id="delete">删除PM</a></li>
					</c:otherwise>
				</c:choose>
				
				<li><a href="#">添加至书签</a></li>
				<li class="divider"></li>
				<li><a href="#">运行报表</a></li>
			</c:if>
		</ul>

	</div>
	<!-- /btn-group -->
	<a href="<spring:url value="/pm/new.html" htmlEscape="true" />"
		class="btn btn-primary">新建</a>
	<button id="submit" class="btn " type="button" ${optStatus}>保存</button>
	<button id="reset" class="btn " type="button" ${optStatus}>清除更改</button>
	<%-- <button id="previous" class="btn btn-success" type="button"
		${optStatus}>
		<i class="icon-arrow-left"></i> 上一条
	</button>
	<button id="next" class="btn btn-success" type="button" ${optStatus}>
		下一条 <i class="icon-arrow-right"></i>
	</button> --%>
</div>
<c:choose>
	<c:when test="${pmid==null or pmid eq ''}">
		<spring:url value="/pm/new.html" var="editUrl" htmlEscape="true"></spring:url>
		<c:set var="pmid" value="0"></c:set>
	</c:when>
	<c:otherwise>
		<spring:url value="/pm/edit/${pmid}.html"
			var="editUrl" htmlEscape="true"></spring:url>
	</c:otherwise>
</c:choose>
<ul class="nav nav-tabs" style="margin-bottoM: 0px;">
	<li
		<c:if test="${fn:contains(requestURL, '/pm/list')}">class="active"</c:if>><a
		href="<spring:url value="/pm/list.html" htmlEscape="true" />">列表</a></li>
	<li
		<c:if test="${fn:contains(requestURL, '/pm/new') or fn:contains(requestURL, '/pm/edit/') }">class="active"</c:if>><a
		href="${editUrl}">PM</a></li>
	<li
		<c:if test="${fn:contains(requestURL, '/frequency')}">class="active"</c:if>><a
		href="<spring:url value="/pm/${pmid}/frequency.html" htmlEscape="true" />">频率</a></li>	
	<li
		<c:if test="${fn:contains(requestURL, '/seasons')}">class="active"</c:if>><a
		href="<spring:url value="/pm/${pmid}/seasons.html" htmlEscape="true" />">季节性日期</a></li>
	<li
		<c:if test="${fn:contains(requestURL, '/sequence')}">class="active"</c:if>><a
		href="<spring:url value="/pm/${pmid}/sequence.html" htmlEscape="true" />">作业计划序列</a></li>
	<li
		<c:if test="${fn:contains(requestURL, '/ancestor')}">class="active"</c:if>><a
		href="<spring:url value="/pm/${pmid}/ancestor.html" htmlEscape="true" />">PM层次结构</a></li>
</ul>


