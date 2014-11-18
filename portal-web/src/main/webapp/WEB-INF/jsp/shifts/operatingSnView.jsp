<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
	<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/main.css">
	<link rel="stylesheet" type="text/css"
	href="${pageContext.request.contextPath}/resources/css/runManage.css">
<script type="text/javascript"
    src="${pageContext.request.contextPath}/resources/scripts/jquery-migrate-1.2.1.min.js"></script>	
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/scripts/main.js"></script>	
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/scripts/runManage.js"></script>

<!-- 	<div class="top_btn_group"> -->
<!-- 		<div> -->
<!-- 			<div class="Btn-big"><a href="shiftsView"><i class="icon_btn icon_bag"></i><p>班次管理</p></a></div> -->
<!-- 			<div class="Btn-big"><a href="shiftworkView"><i class="icon_btn icon_key"></i><p>交接班</p></a></div> -->
<!-- 			<div class="Btn-big  cur nomargin"><a href="operatingView"><i class="icon_btn icon_book"></i><p>运行记录</p></a></div> -->
<!-- 		</div> -->
<!-- 	</div> -->
	<!-- 
	<div class="btn_group1" style="margin-left: 40px;">
		<div class="btnStyle1  nomargin  btn2"><p><a href="${pageContext.request.contextPath}/shifts/operatingView">冷机运行</a></p></div>
		<div class="btnStyle1 btn2 cur"><p><a href="${pageContext.request.contextPath}/shifts/operatingSnView">室内温度</a></p></div>
		<div class="btnStyle1 btn2"><p><a href="${pageContext.request.contextPath}/shifts/operatingBpdView">变配电运行</a></p></div>
		<div class="btnStyle1 btn2"><p><a href="${pageContext.request.contextPath}/shifts/operatingSlView">生活水泵运行</a></p></div>
		<div class="btnStyle1 btn2"><p><a href="${pageContext.request.contextPath}/shifts/operatingZslView">中水泵运行</a></p></div>
		<div class="btnStyle1 btn2"><p><a href="${pageContext.request.contextPath}/shifts/operatingDtView">电梯运行</a></p></div>
		<div class="select_bar1">
			<p>选择时间：</p>
			<input type="text" />
			<div class="btn-small">查询</div>
			<div class="btn-small">导出</div>
		</div>
	</div>
	 -->
	<div class="span12 main row-fluid" >
	<table class="table user_table" style="margin-left: 40px;">
		<tbody>
			<tr class="table_head">
				<th style="text-align: center;">位置</th>
				<th>商户名称</th>
				<th>11:00</th>
				<th>13:00</th>
				<th>17:00</th>
			</tr>
			<tr>
				<td class="td-middle" >一层</td>
				<td>优衣库</td>
				<td>26.3</td>
				<td>26.1</td>
				<td>25.8</td>
			</tr>
			<tr>
				<td class="td-middle" >二层</td>
				<td>国美</td>
				<td>26.2</td>
				<td>25.7</td>
				<td>25.7</td>
			</tr>
			<tr>
				<td class="td-middle" >三层</td>
				<td>红黄蓝</td>
				<td>26.3</td>
				<td>25.9</td>
				<td>25.3</td>
			</tr>
			<tr>
				<td class="td-middle" >四层</td>
				<td>海底捞</td>
				<td>26.4</td>
				<td>26.1</td>
				<td>26.4</td>
			</tr>
			<tr>
				<td class="td-middle" >一层</td>
				<td>大玩家</td>
				<td>26.1</td>
				<td>25.8</td>
				<td>25.4</td>
			</tr>
			<tr>
				<td class="td-middle" >二层</td>
				<td>大歌星大厅</td>
				<td>26.4</td>
				<td>26.2</td>
				<td>25.6</td>
			</tr>
			<tr>
				<td class="td-middle" >三层</td>
				<td>万达影院大厅</td>
				<td>26.4</td>
				<td>26.1</td>
				<td>25.4</td>
			</tr>
			<tr>
				<td class="td-middle" >三层</td>
				<td>东侧1号</td>
				<td>26.6</td>
				<td>26.4</td>
				<td>26.0</td>
			</tr>
			<tr>
				<td class="td-middle" >三层</td>
				<td>东侧2号</td>
				<td>26.4</td>
				<td>26.3</td>
				<td>25.3</td>
			</tr>
			<tr>
				<td class="td-middle" >三层</td>
				<td>东侧3号</td>
				<td>26.2</td>
				<td>26.2</td>
				<td>25.1</td>
			</tr>
			<tr>
				<td class="td-middle" >三层</td>
				<td>圆弧4号</td>
				<td>26.1</td>
				<td>26.1</td>
				<td>25.2</td>
			</tr>
			<tr>
				<td class="td-middle" >三层</td>
				<td>圆弧5号</td>
				<td>25.8</td>
				<td>25.8</td>
				<td>25.1</td>
			</tr>
			<tr>
				<td class="td-middle" >三层</td>
				<td>圆弧6号</td>
				<td>25.7</td>
				<td>26.9</td>
				<td>25.2</td>
			</tr>
			<tr>
				<td class="td-middle" >三层</td>
				<td>圆弧7号</td>
				<td>25.6</td>
				<td>25.8</td>
				<td>25.4</td>
			</tr>
			<tr>
				<td class="td-middle" >三层</td>
				<td>圆弧8号</td>
				<td>25.8</td>
				<td>25.4</td>
				<td>25.6</td>
			</tr>
				<tr>
				<td class="td-middle" >步行街</td>
				<td>风慕出风东/西门温度</td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
		</tbody>
	</table>
</div>
</html>