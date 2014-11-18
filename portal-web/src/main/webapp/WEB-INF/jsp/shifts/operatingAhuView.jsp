<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/main.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/runManage.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/jquery-migrate-1.2.1.min.js"></script>	
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/highcharts.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/main.js"></script>	
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/runManage.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/mctrl/customVar.js"></script>
<script type="text/javascript">var model = '空调机组';</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/shifts/operatingAhu.js"></script>

<!-- 空调机组 -->
<div class="span12 main row-fluid" >
	<div class="fault_content">
		<div class="running_sel">
			<span class="timeStyle">当前显示时间：</span>
			<span class="timeStyle nowTimeText">${querytime}</span>
		</div>
		<div class="running_sel">
			<span class="timeStyle">设备列表：</span>
			<select class="format_select" id="runningSel" onchange="buildCharts()"></select>
		</div>
		
		<div class="span5 chart_width">
			<!-- 回风温度 -->
			<div>
				<div class="fault_title">
					<h3>回风温度</h3>
				</div>
				<div class="chart_content">
					<div class="chart" style="height: 340px;" class="chart_style" id="t_ra_chart"></div>
				</div>
			</div>
		</div>
		
		<div class="span5 chart_width">
			<!-- 回风湿度 -->
			<div>
				<div class="fault_title">
					<h3>回风湿度</h3>
				</div>
				<div class="chart_content">
					<div class="chart" style="height: 340px;" class="chart_style" id="rh_ra_chart"></div>
				</div>
			</div>
		</div>
		
		<div class="span5 chart_width">
			<!-- 送风温度 -->
			<div>
				<div class="fault_title">
					<h3>送风温度</h3>
				</div>
				<div class="chart_content">
					<div class="chart" style="height: 340px;" class="chart_style" id="t_sa_chart"></div>
				</div>
			</div>
		</div>
		
		<div class="span5 chart_width">
			<!-- 二氧化碳浓度 -->
			<div>
				<div class="fault_title">
					<h3>二氧化碳浓度</h3>
				</div>
				<div class="chart_content">
					<div class="chart" style="height: 340px;" class="chart_style" id="co2_ra_chart"></div>
				</div>
			</div>
		</div>
	</div>
</div>
</html>