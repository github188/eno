<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>测试系统</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">

	<spring:url value="/webjars/jquery/1.9.0/jquery.js" var="jQuery"/>
    <script src="${jQuery}"></script>

	<spring:url value="/resources/plugins/datepicker/bootstrap-datetimepicker.zh-CN.js" var="datepicker"/>
    <link href="${datepicker}" rel="stylesheet"/>
    
    <spring:url value="/resources/plugins/datepicker/datepicker.css" var="datepicker"/>
    <link href="${datepicker}" rel="stylesheet"/>
    
    <script src="<spring:url value="/resources/plugins/datepicker/bootstrap-datepicker.js" />"></script>

	<spring:url value="/resources/plugins/jeasyui/themes/bootstrap/easyui.css" var="easyui"/>
    <link href="${easyui}" rel="stylesheet"/>
    
    <script src="<spring:url value="/resources/plugins/jeasyui/jquery.easyui.min.js" />"></script>
    


    <spring:url value="/webjars/jquery-ui/1.9.2/css/smoothness/jquery-ui-1.9.2.custom.css" var="jQueryUiCss"/>
    <link href="${jQueryUiCss}" rel="stylesheet"></link>
    
    <spring:url value="/webjars/bootstrap/2.3.0/js/bootstrap.min.js" var="bootstrapJs"/>
    <script src="${bootstrapJs}"></script>
    
    <spring:url value="/resources/plugins/nicescroll/jquery.nicescroll.min.js" var="nicescrollJS"/>
    <script src="${nicescrollJS}"></script>
    
     <spring:url value="/resources/scripts/lte-ie7.js" var="ie7"/>
    <!--[if lte IE 7]><script src="${ie7}"></script><![endif]-->
    
    <script src="<spring:url value="/resources/scripts/application.js" />"></script>
	<script src="<spring:url value="/resources/scripts/jquery.ztree.all-3.5.js" />"></script>
    
    <spring:url value="/resources/css/zTreeStyle/zTreeStyle.css" var="zTreeStyle"/>
    <link href="${zTreeStyle}" rel="stylesheet"/>

    
    <script src="<spring:url value="/resources/plugins/highcharts/highcharts.js" />"></script>
</head>
