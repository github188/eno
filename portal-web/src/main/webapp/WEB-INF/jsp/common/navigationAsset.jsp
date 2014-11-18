<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<c:set var="requestURL"
	value="${requestScope['javax.servlet.forward.request_uri']}"
	scope="request" />
<c:choose>
	<c:when test="${empty assetuid or assetuid eq ''}">
		<c:set var="assetuid" value="0"></c:set>
		<spring:url value="/asset/new.html" var="editUrl" htmlEscape="true"></spring:url>
	</c:when>
	<c:otherwise>
		<spring:url value="/asset/edit/${assetuid}.html" var="editUrl"
			htmlEscape="true"></spring:url>
	</c:otherwise>
</c:choose>
<c:if test="${fn:contains(requestURL, '/asset/list')}">
	<c:set var="clsList" value="active" />
	<c:set var="optStatus" value="disabled" />
</c:if>
<c:if
	test="${fn:contains(requestURL, '/asset/edit') or fn:contains(requestURL, '/asset/new')}">
	<c:set var="clsDetail" value="active" />
</c:if>
<div style="margin: 0 20px">
	<div class="row-fluid" style="margin: 10px 0;">
		<div class="btn-big <c:if test="${fn:contains(requestURL, '/asset/list')}">cur</c:if>">
			<a href="<spring:url value="/asset/list.html" />"><i
				class="icon_btn icon_wrench"></i>
				<p>设备列表</p></a>
		</div>
		<%--  <div class="btn-big <c:if test="${fn:contains(requestURL, '/measure/')}">cur</c:if>"><a href="<spring:url value="/measure/list.html"></spring:url>"><i class="icon_btn icon_tel"></i><p>点检管理</p></a></div> --%>
		<!-- 
		<div
			class="btn-big <c:if test="${fn:contains(requestURL, '/asset/failurecode/')}">cur</c:if>">
			<a href="<spring:url value="/failurecode/list.html"></spring:url>"><i
				class="icon_btn icon_tel"></i>
				<p>故障代码</p></a>
		</div>
		<div
			class="btn-big <c:if test="${fn:contains(requestURL, '/asset/pm/')}">cur</c:if>">
			<a href="<spring:url value="/pm/list.html"></spring:url>"><i
				class="icon_btn icon_tel"></i>
				<p>预防性维护</p></a>
		</div>
		<div
			class="btn-big <c:if test="${fn:contains(requestURL, '/asset/jobplan/')}">cur</c:if>">
			<a href="<spring:url value="/jobplan/list.html"></spring:url>"><i
				class="icon_btn icon_tel"></i>
				<p>作业计划</p></a>
		</div>
		<div class="btn-group" style="float: right;">
			<button class="btn btn-large dropdown-toggle" data-toggle="dropdown">
				基础设置 <span class="caret"></span>
			</button>
			<ul class="dropdown-menu">
				<li><a href="<spring:url value="/locations/list.html" />">位置</a></li>
				<li><a href="<spring:url value="/meter/list.html" />">计量器</a></li>
				<li><a
					href="<spring:url value="/metergroup/list.html"></spring:url>">计量器组</a></li>
				<li><a
					href="<spring:url value="/class/list.html"></spring:url>">资产分类</a></li>
				<li class="divider"></li>
				<li><a
					href="<spring:url value="/companies/list.html"></spring:url>">公司</a></li>
			</ul>
		</div>
		 -->
		<!-- /btn-group -->
	</div>
	<div class="btn-toolbar">
		<!-- <select class="input-medium selectpicker span1">
		<option></option>
		<option>所有条目</option>
		<option>书签条目</option>
	</select> -->
		<!-- 
		<div class="btn-group">
			<button class="btn dropdown-toggle input-medium"
				data-toggle="dropdown">
				更多操作 <span class="caret"></span>
			</button>
			<ul class="dropdown-menu ">
				<c:if test="${!empty clsDetail}">
					<c:choose>
						<c:when test="${assetuid>0}">
							<li><a
								href="<spring:url value="/asset/delete/${assetuid}.html" htmlEscape="true"></spring:url>">删除资产</a></li>
						</c:when>
						<c:otherwise>
							<li><a href="#">删除资产</a></li>
						</c:otherwise>
					</c:choose>
				</c:if>
			</ul>
		</div>
		 -->
		<!-- /btn-group -->
		<!-- 
		<a href="<spring:url value="/asset/new.html" htmlEscape="true" />"
			class="btn btn-primary">新建</a>
		<button id="submit" class="btn " type="button" ${optStatus}>保存</button>
		<button id="reset" class="btn" type="button" ${optStatus}>清除更改</button>
		<%-- <button id="previous" class="btn btn-success" type="button"
		${optStatus}>
		<i class="icon-arrow-left"></i> 上一条
		</button>
		<button id="next" class="btn btn-success" type="button" ${optStatus}>
			下一条 <i class="icon-arrow-right"></i>
		</button> --%>
		 -->
	</div>

	<ul class="nav nav-tabs" style="margin-bottoM: 0px;">
		<li
			<c:if test="${fn:contains(requestURL, '/asset/list')}">class="active"</c:if>><a
			href="<spring:url value="/asset/list.html" htmlEscape="true" />">列表</a></li>
		<!-- 
		<li
			<c:if test="${fn:contains(requestURL, '/asset/edit') or fn:contains(requestURL, '/asset/new')}">class="active"</c:if>><a
			href="${editUrl}">资产</a></li>
		<li
			<c:if test="${fn:contains(requestURL, '/meters')}">class="active"</c:if>><a
			href="<spring:url value="/asset/${assetuid}/meters.html" htmlEscape="true" />">计量器</a></li>
		<li
			<c:if test="${fn:contains(requestURL, '/specs')}">class="active"</c:if>><a
			href="<spring:url value="/asset/${assetuid}/specs.html" htmlEscape="true" />">规范</a></li>
		 -->
	</ul>

</div>
