<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<c:set var="requestURL"
	value="${requestScope['javax.servlet.forward.request_uri']}"
	scope="request" />
<c:choose>
	<c:when test="${fn:contains(requestURL, '/jobplan/list')}">
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
				<!-- <li><a href="#" id="copyCompany">复制作业计划</a></li> -->
				<c:choose>
					<c:when test="${jobplanid>0}">
					<li><a href="${pageContext.request.contextPath}/jobplan/delete/${jobplanid}" id="delCompany">删除作业计划</a></li>
					</c:when>
					<c:otherwise>
					<li><a href="#">删除作业计划</a>
					</c:otherwise>
				</c:choose>
				
				<li><a href="#">添加至书签</a></li>
				<li class="divider"></li>
				<li><a href="#">运行报表</a></li>
			</c:if>
		</ul>

	</div>
	<!-- /btn-group -->
	<a href="<spring:url value="/jobplan/new.html" htmlEscape="true" />"
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
	<c:when test="${jobplanid==null or jobplanid eq ''}">
		<spring:url value="/jobplan/new.html" var="editUrl" htmlEscape="true"></spring:url>
		<c:set var="jobplanid" value="0"></c:set>
	</c:when>
	<c:otherwise>
		<spring:url value="/jobplan/edit/${jobplanid}.html"
			var="editUrl" htmlEscape="true"></spring:url>
	</c:otherwise>
</c:choose>
<ul class="nav nav-tabs" style="margin-bottoM: 0px;">
	<li
		<c:if test="${fn:contains(requestURL, '/jobplan/list')}">class="active"</c:if>><a
		href="<spring:url value="/jobplan/list.html" htmlEscape="true" />">列表</a></li>
	<li
		<c:if test="${fn:contains(requestURL, '/jobplan/new') or fn:contains(requestURL, '/jobplan/edit/') }">class="active"</c:if>><a
		href="${editUrl}">作业计划</a></li>
</ul>


