<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<c:set var="requestURL"
	value="${requestScope['javax.servlet.forward.request_uri']}"
	scope="request" />
<c:if test="${fn:contains(requestURL, '/class/list')}">
	<c:set var="clsList" value="active" />
	<c:set var="optStatus" value="disabled" />
</c:if>
<c:if
	test="${fn:contains(requestURL, '/class/edit') or fn:contains(requestURL, '/class/new')}">
	<c:set var="clsDetail" value="active" />
</c:if>
<div class="btn-toolbar">
	<!-- <select class="selectpicker">
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
			<li class="dropdown-submenu"><a tabindex="-1" href="#">添加/修改属性</a>
				<ul class="dropdown-menu">
					<li><a tabindex="-1" href="#" id="showClassificationWindow" class="showClassificationWindow" link="<spring:url value="/class/classification"></spring:url>">分类</a></li>
					<li><a tabindex="-1" href="#" id="showAssetattributeWindow" class="showAssetattributeWindow" link="<spring:url value="/class/assetattribute"></spring:url>">属性</a></li>
					<li><a tabindex="-1" href="#" id="showMeasureunitWindow" class="showMeasureunitWindow" link="<spring:url value="/measureunit/edit"></spring:url>">记量单位</a></li>
				</ul></li>
			<c:if test="${!empty clsDetail}">
				<li class="divider"></li>
				<li><a href="#" id="dlgLocSystem2">复制分类</a></li>
				<li><a href="#" id="linkLocSystem">删除分类</a></li>
				<li><a href="#">添加至书签</a></li>
				<li class="divider"></li>
				
			</c:if>
			<!-- <li><a href="#">运行报表</a></li> -->
		</ul>

	</div>
	<!-- /btn-group -->
	<a href="<spring:url value="/class/new.html" htmlEscape="true" />"
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
	<c:when test="${classstructureuid==null or classstructureuid eq ''}">
		<spring:url value="/class/new.html" var="editUrl" htmlEscape="true"></spring:url>
	</c:when>
	<c:otherwise>
		<spring:url value="/class/edit/${classstructureuid}.html"
			var="editUrl" htmlEscape="true"></spring:url>
	</c:otherwise>
</c:choose>
<ul class="nav nav-tabs" style="margin-bottoM: 0px;">
	<li
		<c:if test="${fn:contains(requestURL, '/class/list')}">class="active"</c:if>><a
		href="<spring:url value="/class/list.html" htmlEscape="true" />">列表</a></li>
	<li
		<c:if test="${fn:contains(requestURL, '/class/new') or fn:contains(requestURL, '/class/edit') }">class="active"</c:if>><a
		href="${editUrl}">分类</a></li>
</ul>

<%@ include file="dialog/dlgClassification.jsp" %>
<%@ include file="dialog/dlgAssetAttribute.jsp" %>
<%@ include file="dialog/dlgMeasureUnit.jsp" %>

