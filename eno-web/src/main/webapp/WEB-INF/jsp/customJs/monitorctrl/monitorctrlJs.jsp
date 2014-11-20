<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp"%>
<%
org.joda.time.DateTimeZone.setDefault(org.joda.time.DateTimeZone.forID("Asia/Shanghai"));
pageContext.setAttribute("now", new org.joda.time.LocalDateTime(org.joda.time.DateTimeZone.getDefault()));
%>
<c:set var="requestURL" value="${requestScope['javax.servlet.forward.request_uri']}" scope="request" />
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/elecpatrol.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/alertManage.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/main.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/monitorControl.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/mctrl/custom.css">
<%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/modeswitch.css">--%>
<%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/dtree.css">--%>

<script src="${pageContext.request.contextPath}/resources/plugins/bootstrap-select/bootstrap-select.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/scripts/main.js"></script>
<script src="${pageContext.request.contextPath}/resources/scripts/mctrl/controlModel.js"></script>
<c:if test="${!fn:contains(requestURL,'monitorsum')}">
<script src="${pageContext.request.contextPath}/resources/scripts/mctrl/systemList.js"></script>
</c:if>
<%--<script src="${pageContext.request.contextPath}/resources/scripts/dtree.js"></script>--%>
<script src="${pageContext.request.contextPath}/resources/scripts/pattern/showMenu.js"></script>
<script src="${pageContext.request.contextPath}/resources/scripts/modeswitch.js"></script>
<script src="${pageContext.request.contextPath}/resources/scripts/mctrl/monitorControl.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/scripts/My97DatePicker/WdatePicker.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/mctrl/light_func.js"></script>
