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
		<div class="btnStyle1 btn2"><p><a href="${pageContext.request.contextPath}/shifts/operatingSlView">生活水泵运行</a></p></div>
		<div class="btnStyle1 btn2"><p><a href="${pageContext.request.contextPath}/shifts/operatingZslView">中水泵运行</a></p></div>
		<div class="btnStyle1 btn2 cur"><p><a href="${pageContext.request.contextPath}/shifts/operatingDtView">电梯运行</a></p></div>
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
				<th  style="text-align: center;">位置</th>
				<th  >商户名称</th>
				<th>09:00</th>
				<th>11:00</th>
				<th>13:00</th>
				<th>17:00</th>
			</tr>
			<tr>
				<td class="td-middle" >步行街17#扶梯</td>
				<td>优衣库</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
			</tr>
			<tr>
				<td class="td-middle" >步行街18#扶梯</td>
				<td>国美</td>
				<td>√</td>
				<td>√</td>
				<td>X</td>
				<td>√</td>
			</tr>
			<tr>
				<td class="td-middle" >步行街19#扶梯</td>
				<td>红黄蓝</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
			</tr>
			<tr>
				<td class="td-middle" >步行街20#扶梯</td>
				<td>海底捞</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
			</tr>
			<tr>
				<td class="td-middle" >步行街21#扶梯</td>
				<td>大玩家</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
			</tr>
			<tr>
				<td class="td-middle" >步行街22#扶梯</td>
				<td>大歌星大厅</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
			</tr>
			<tr>
				<td class="td-middle" >步行街23#扶梯</td>
				<td>万达影院大厅</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
				<td>X</td>
			</tr>
			<tr>
				<td class="td-middle" >步行街24#扶梯</td>
				<td>东侧1号</td>
				<td>√</td>
				<td>X</td>
				<td>√</td>
				<td>√</td>
			</tr>
			<tr>
				<td class="td-middle" >步行街25#扶梯</td>
				<td>东侧2号</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
			</tr>
			<tr>
				<td class="td-middle" >步行街26#扶梯</td>
				<td>东侧3号</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
			</tr>
			<tr>
				<td class="td-middle" >步行街27#扶梯</td>
				<td>圆弧2号</td>
				<td>√</td>
				<td>√</td>
				<td>X</td>
				<td>√</td>
			</tr>
			<tr>
				<td class="td-middle" >步行街28#扶梯</td>
				<td>圆弧4号</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
			</tr>
			<tr>
				<td class="td-middle" >步行街29#扶梯</td>
				<td>圆弧5号</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
			</tr>
			<tr>
				<td class="td-middle" >步行街30#扶梯</td>
				<td>圆弧6号</td>
				<td>√</td>
				<td>√</td>
				<td>X</td>
				<td>√</td>
			</tr>
			<tr>
				<td class="td-middle" >步行街31#扶梯</td>
				<td>圆弧7号</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
			</tr>
			<tr>
				<td class="td-middle" >步行街32#扶梯</td>
				<td>万达</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
			</tr>
			<tr>
				<td class="td-middle" >步行街33#扶梯</td>
				<td>苏宁</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
			</tr>
			<tr>
				<td class="td-middle" >步行街34#扶梯</td>
				<td>苹果</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
			</tr>
			<tr>
				<td class="td-middle" >步行街35#扶梯</td>
				<td>三星</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
			</tr>
			<tr>
				<td class="td-middle" >步行街36#扶梯</td>
				<td></td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
			</tr>
			<tr>
				<td class="td-middle" >步行街7#直梯</td>
				<td></td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
				<td>X</td>
			</tr>
			<tr>
				<td class="td-middle" >步行街8#直梯</td>
				<td></td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
			</tr>
			<tr>
				<td class="td-middle" >步行街9#直梯</td>
				<td></td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
			</tr>
			<tr>
				<td class="td-middle" >步行街10#直梯</td>
				<td></td>
				<td>√</td>
				<td>X</td>
				<td>√</td>
				<td>√</td>
			</tr>
			<tr>
				<td class="td-middle" >步行街11#直梯</td>
				<td></td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
			</tr><tr>
				<td class="td-middle" >步行街12#直梯</td>
				<td></td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
			</tr><tr>
				<td class="td-middle" >步行街13#直梯</td>
				<td></td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
			</tr><tr>
				<td class="td-middle" >步行街14#直梯</td>
				<td></td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
			</tr><tr>
				<td class="td-middle" >步行街15#直梯</td>
				<td></td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
			</tr><tr>
				<td class="td-middle" >步行街16#直梯</td>
				<td></td>
				<td>√</td>
				<td>√</td>
				<td>X</td>
				<td>√</td>
			</tr><tr>
				<td class="td-middle" >步行街17#直梯</td>
				<td></td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
			</tr><tr>
				<td class="td-middle" >步行街18#直梯</td>
				<td></td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
			</tr><tr>
				<td class="td-middle" >步行街19#直梯</td>
				<td></td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
			</tr><tr>
				<td class="td-middle" >步行街20#直梯</td>
				<td></td>
				<td>√</td>
				<td>√</td>
				<td>√</td>
				<td>X</td>
			</tr>
		</tbody>
	</table>
</div>
</html>