<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ include file="../taglib.jsp"%>
<%@ page session="false"%>
<c:if test="${!ajaxRequest}">
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title><tiles:insertAttribute name="title" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<META HTTP-EQUIV="pragma" CONTENT="no-cache" />
    <link href="<spring:url value="/webjars/bootstrap/2.3.2/css/bootstrap.min.css" />" rel="stylesheet"/>
     <link href="<spring:url value="/resources/plugins/datatables/jquery.dataTables.css"/>" rel="stylesheet"/>
 	<link href="<spring:url value="/resources/plugins/jeasyui/themes/bootstrap/easyui.css"/>" rel="stylesheet"/>
    <link href="<spring:url value="/resources/plugins/jeasyui/themes/icon.css"/>" rel="stylesheet"/>
    <link href="<spring:url value="/resources/css/style.css" />" rel="stylesheet"/>
	<script type="text/javascript">
	var CONTEXT_PATH = "${pageContext.request.contextPath}";
	</script>
    <script src="<spring:url value="/webjars/jquery/1.9.1/jquery.js" />"></script>
   <%--  <script src="<spring:url value="/resources/plugins/jquery/jquery-1.8.0.js" />"></script> --%>
    <script src=" <spring:url value="/webjars/bootstrap/2.3.2/js/bootstrap.min.js" />"></script>
    <script src="<spring:url value="/resources/plugins/jeasyui/jquery.easyui.min.js" />"></script>
    <script src="<spring:url value="/resources/plugins/jeasyui/datagrid-detailview.js" />"></script>
    <script src="<spring:url value="/resources/plugins/jeasyui/easyui-lang-zh_CN.js" />"></script>
    
    <script src="<spring:url value="/resources/plugins/jquery-validation/jquery.validate.min.js" />"></script>
    <script src="<spring:url value="/resources/plugins/jquery-validation/messages_zh.js" />"></script>

    <spring:url value="/resources/scripts/lte-ie7.js" var="ie7"/>
    <!--[if lte IE 7]><script src="${ie7}"></script><![endif]-->
    <script src="<spring:url value="/resources/scripts/main.js" />"></script>
          
	<script src="<spring:url value="/resources/plugins/datatables/jquery.dataTables.js"/>"></script>    
	<script src="<spring:url value="/resources/plugins/datatables/dataTablesPagination.js"/>"></script>    
    <tiles:insertAttribute name="scripts" />
</head>
<body style="margin:0px;padding:0px;">
</c:if>
<div id="container-fluid">
	<tiles:insertAttribute name="body" />
</div>
<c:if test="${!ajaxRequest}">
	</body>
	</html>
</c:if>