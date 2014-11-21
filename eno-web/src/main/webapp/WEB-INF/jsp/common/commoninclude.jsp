<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>	
<c:set var="requestURL" value="${requestScope['javax.servlet.forward.request_uri']}" scope="request" />
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<link href="<spring:url value="/resources/plugins/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet" />
<link href="<spring:url value="/resources/plugins/bootstrap-select/bootstrap-select.min.css"/>" rel="stylesheet" />
<link href="<spring:url value="/resources/css/base.css"/>" rel="stylesheet" />
<link href="<spring:url value="/resources/css/index.css"/>" rel="stylesheet" />

<script src="<spring:url value="/resources/scripts/jquery-1.9.1.min.js" />"></script>
<script src="<spring:url value="/resources/scripts/jquery-migrate-1.2.1.min.js" />"></script>
<script src="<spring:url value="/resources/plugins/bootstrap-select/bootstrap-select.min.js" />"></script>
<script src="<spring:url value="/resources/scripts/common.js" />"></script>

