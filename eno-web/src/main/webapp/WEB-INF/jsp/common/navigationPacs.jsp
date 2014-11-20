<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<c:set var="requestURL" value="${requestScope['javax.servlet.forward.request_uri']}" scope="request" />
<!--  <div style="margin:20px 10px;"> -->
<%-- <c:forEach items="${okcMenus}" var="okcMenu"> --%>
<%-- <c:if test="${okcMenu.headerdescription eq '公告板'}"> --%>
<%--    <a class=" Btn-big <c:if test="${fn:contains(requestURL,'noticeBoard')}">cur</c:if>" id="BBOARD" href="${pageContext.request.contextPath}/noticeBoard/operatingModelView.html"><i class="icon_btn icon_book"></i><p>公告板</p></a> --%>
<%-- </c:if>	   --%>
<%-- <c:if test="${okcMenu.headerdescription eq '报表'}">  --%>
<%-- 	<a class="Btn-big <c:if test="${fn:contains(requestURL,'report')}">cur</c:if>" id="REPORT" href="${pageContext.request.contextPath}/pacs/report.html"><i class="icon_btn icon_report"></i><p>报表</p></a> --%>
<%-- </c:if>	 --%>
<%-- </c:forEach> --%>
<!-- </div> -->

<div style="margin:20px 10px;">
   <a class=" Btn-big <c:if test="${fn:contains(requestURL,'noticeBoard')}">cur</c:if>" id="BBOARD" href="${pageContext.request.contextPath}/noticeBoard/noticeBoardView.html"><i class="icon_btn icon_book"></i><p>公告板</p></a>
	<a class="Btn-big <c:if test="${fn:contains(requestURL,'report')}">cur</c:if>" id="REPORT" href="${pageContext.request.contextPath}/pacs/report.html"><i class="icon_btn icon_report"></i><p>报表</p></a>
<%-- 	<%-- <a class="Btn-big <c:if test="${fn:contains(requestURL,'attachment')}">cur</c:if>" id="ATTACHMENT" href="${pageContext.request.contextPath}/pacs/attachment.html"><i class="icon_btn icon_attachment"></i><p>附件</p></a> --%> 
</div>




