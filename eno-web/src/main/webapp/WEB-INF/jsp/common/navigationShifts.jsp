<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<c:set var="requestURL" value="${requestScope['javax.servlet.forward.request_uri']}" scope="request" />
<!-- <div class="span12" style="margin: 20px 10px"> -->
<%-- <c:forEach items="${okcMenus}" var="okcMenu"> --%>
<%-- 		<c:if test="${okcMenu.headerdescription eq '班次管理'}"> --%>
<%-- 			<div class="Btn-big btn1 <c:if test="${fn:contains(requestURL,'shiftsView')}">cur</c:if>"> --%>
<%-- 		   <a href="<spring:url value="shiftsView.html"></spring:url>"><i class="icon_btn icon_user"></i> --%>
<!-- 		   <p>班次管理</p></a> -->
<!-- 	       </div>		 -->
<%-- 		</c:if>	 --%>
<%-- 		<c:if test="${okcMenu.headerdescription eq '交接班'}"> --%>
<%-- 			<div class="Btn-big btn3 <c:if test="${fn:contains(requestURL,'shiftworkView')}">cur</c:if>"> --%>
<%-- 		    <a href="<spring:url value="shiftworkView.html"></spring:url>"><i class="icon_btn icon_diamond"></i> --%>
<!-- 		   <p>交接班</p></a> -->
<!-- 	       </div>		 -->
<%-- 		</c:if>	 --%>
<%-- 		<c:if test="${okcMenu.headerdescription eq '运行日志'}"> --%>
<%-- 			<div class="Btn-big btn3 <c:if test="${fn:contains(requestURL,'operatingView')}">cur</c:if>"> --%>
<%-- 		     <a href="<spring:url value="operatingView.html"></spring:url>"><i class="icon_btn icon_link"></i> --%>
<!-- 		    <p>运行记录</p></a> -->
<!-- 	       </div>		 -->
<%-- 		</c:if>	 --%>
<%-- </c:forEach> --%>
<!-- </div> -->

<div class="span12" style="margin: 20px 10px">
	<div class="Btn-big btn1 <c:if test="${fn:contains(requestURL,'noticeBoard')}">cur</c:if>" style="width: 180px;">
		<a href="<spring:url value="/noticeBoard/noticeBoardView.html"></spring:url>">
			<i class="icon_btn icon_book"></i><p style="margin-left: 0">系统操作记录</p>
		</a>
	</div>
	<div class="Btn-big btn2 <c:if test="${fn:contains(requestURL,'operatingListView')}">cur</c:if>" style="width: 180px;">
		<a href="<spring:url value="/shifts/operatingListView.html"></spring:url>">
			<i class="icon_btn icon_link"></i><p style="margin-left: 0">设备运行记录</p>
		</a>
	</div>
	<div class="Btn-big btn3 <c:if test="${fn:contains(requestURL,'shiftsView')||fn:contains(requestURL,'shifttypesList')}">cur</c:if>">
		<a href="<spring:url value="/shifts/shiftsView.html"></spring:url>">
			<i class="icon_btn icon_user"></i><p>班次管理</p>
		</a>
	</div>
	<div class="Btn-big btn4 <c:if test="${fn:contains(requestURL,'shiftworkView')}">cur</c:if>">
		<a href="<spring:url value="/shifts/shiftworkView.html"></spring:url>">
			<i class="icon_btn icon_diamond"></i><p>交接班</p>
		</a>
	</div>
</div>
