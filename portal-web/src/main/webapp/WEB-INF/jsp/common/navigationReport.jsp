<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<c:set var="requestURL" value="${requestScope['javax.servlet.forward.request_uri']}" scope="request" />

<div style="margin: 20px 10px;">

	<a class=" Btn-big <c:if test="${fn:contains(requestURL,'deviceTrend') || fn:contains(requestURL,'operating')}">cur</c:if>"
		id="deviceTrend" href="${pageContext.request.contextPath}/report/deviceTrend.html"><i
		class="icon_btn icon_report"></i>
		<p style="margin-left: -5px;">趋势图</p>
	</a> 
	
	<a class="Btn-big <c:if test="${fn:contains(requestURL,'runningReport')}">cur</c:if>"
		id="REPORT" href="${pageContext.request.contextPath}/report/deviceTrend.html"><i
		class="icon_btn icon_book"></i>
		<p>报表</p>
	</a>
</div>