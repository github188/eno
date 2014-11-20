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
		<div class="btnStyle1 nomargin btn2"><p><a href="${pageContext.request.contextPath}/shifts/operatingView">冷机运行</a></p></div>
		<div class="btnStyle1 btn2"><p><a href="${pageContext.request.contextPath}/shifts/operatingSnView">室内温度</a></p></div>
		<div class="btnStyle1 btn2"><p><a href="${pageContext.request.contextPath}/shifts/operatingBpdView">变配电运行</a></p></div>
		<div class="btnStyle1 btn2 cur"><p><a href="${pageContext.request.contextPath}/shifts/operatingSlView">生活水泵运行</a></p></div>
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
	<div class="span12 main row-fluid">
	<table class="table user_table" style="margin-left: 40px;">
		<tbody>
			<tr class="table_head">
				<th></th>
				<th></th>
				<th></th>
				<th>9:00</th>
				<th>11:00</th>
				<th>13:00</th>
				<th>15:00</th>
				<th>17:00</th>
				<th>19:00</th>
				<th>21:00</th>
				<th>23:00</th>
				<th>1:00</th>
				<th>3:00</th>
				<th>5:00</th>
				<th>7:00</th>
			</tr>
			<tr>
				<td class="td-middle" rowspan="4" >1#泵</td>
				<td class="td-middle" rowspan="2">压力(Mpa)</td>
				<td>入口</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>泵出</td>
				<td></td>
				<td>0.56</td>
				<td>0.56</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td>0.56</td>
				<td>0.56</td>
				<td></td>
			</tr>
			<tr>
				<td class="td-middle" colspan="2">电压/频率</td>
				<td></td>
				<td>420/41</td>
				<td>420/40</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td>420/41</td>
				<td>420/41</td>
				<td></td>
			</tr>
			<tr>
				<td class="td-middle" colspan="2">电流(A)</td>
				<td></td>
				<td>10</td>
				<td>10</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td>10</td>
				<td>10</td>
				<td></td>
			</tr>
			<tr>
				<td class="td-middle" rowspan="4" >2#泵</td>
				<td class="td-middle" rowspan="2">压力(Mpa)</td>
				<td>入口</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>泵出</td>
				<td></td>
				<td></td>
				<td></td>
				<td>0.56</td>
				<td>0.56</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td>0.56</td>
			</tr>
			<tr>
				<td class="td-middle" colspan="2">电压/频率</td>
				<td></td>
				<td></td>
				<td></td>
				<td>420/41</td>
				<td>420/40</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td>420/41</td>
			</tr>
			<tr>
				<td class="td-middle" colspan="2">电流(A)</td>
				<td></td>
				<td></td>
				<td></td>
				<td>10</td>
				<td>10</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td>10</td>
			</tr>
			<tr>
				<td class="td-middle" rowspan="4" >3#泵</td>
				<td class="td-middle" rowspan="2">压力(Mpa)</td>
				<td>入口</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>泵出</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td>0.56</td>
				<td>0.56</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="td-middle" colspan="2">电压/频率</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td>420/40</td>
				<td>420/41</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="td-middle" colspan="2">电流(A)</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td>10</td>
				<td>10</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="td-middle" rowspan="4" >4#泵</td>
				<td class="td-middle" rowspan="2">压力(Mpa)</td>
				<td>入口</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>泵出</td>
				<td>0.56</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td>0.56</td>
				<td>0.56</td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="td-middle" colspan="2">电压/频率</td>
				<td>420/41</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td>420/41</td>
				<td>420/41</td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="td-middle" colspan="2">电流(A)</td>
				<td>10</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td>10</td>
				<td>10</td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="td-middle" rowspan="4" >稳压泵</td>
				<td class="td-middle" rowspan="2">压力(Mpa)</td>
				<td>入口</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td>泵出</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="td-middle" colspan="2">电压/频率</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="td-middle" colspan="2">电流(A)</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td  class="td-middle" colspan="3">水箱水位</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
			</tr>
		</tbody>
	</table>
</div>
</html>