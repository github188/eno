<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
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
		<div class="btnStyle1 btn2 cur"><p><a href="${pageContext.request.contextPath}/shifts/operatingBpdView">变配电运行</a></p></div>
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
				<th  style="text-align: center;">项目</th>
				<th  style="text-align: center;">标准</th>
				<th>时间</th>
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
				<td class="td-middle" rowspan="3" >高压1＃进线</td>
				<td class="td-middle" >电压（KV）</td>
				<td>10±0.5</td>
				<td>10.2</td>
				<td>10</td>
				<td>10</td>
				<td>10.2</td>
				<td>10.2</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="td-middle" >电流（A）</td>
				<td>＜IN</td>
				<td>59</td>
				<td>88</td>
				<td>99</td>
				<td>88</td>
				<td>91</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="td-middle" >有功功率（KWH)</td>
				<td>KWH</td>
				<td>5017.59</td>
				<td>5018.04</td>
				<td>5018.56</td>
				<td>5019.05</td>
				<td>5019.52</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="td-middle" rowspan="3" >高压2＃进线</td>
				<td class="td-middle" >电压（KV）</td>
				<td>10±0.5</td>
				<td>10.2</td>
				<td>10.1</td>
				<td>10.1</td>
				<td>10.2</td>
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
				<td class="td-middle" >电流（A）</td>
				<td>＜IN</td>
				<td>52</td>
				<td>81</td>
				<td>92</td>
				<td>77</td>
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
				<td class="td-middle" >有功功率（KWH)</td>
				<td>KWH</td>
				<td>4507.34</td>
				<td>4507.77</td>
				<td>4508.24</td>
				<td>4508.69</td>
				<td>4509.14</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="td-middle" rowspan="4" >变压器温度</td>
				<td class="td-middle" >1TM（℃）</td>
				<td>≤90</td>
				<td>71.2</td>
				<td>75.1</td>
				<td>72.6</td>
				<td>70.6</td>
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
				<td class="td-middle" >2TM（℃）</td>
				<td>≤90</td>
				<td>73.5</td>
				<td>77.8</td>
				<td>80.2</td>
				<td>72.8</td>
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
				<td class="td-middle" >3TM（℃）</td>
				<td>≤90</td>
				<td>63.2</td>
				<td>78</td>
				<td>79.6</td>
				<td>68.6</td>
				<td>73.2</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="td-middle" >4TM（℃）</td>
				<td>≤90</td>
				<td>69.6</td>
				<td>60.5</td>
				<td>58.8</td>
				<td>57.9</td>
				<td>57.3</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="td-middle" rowspan="4" >功率因数</td>
				<td class="td-middle" >401</td>
				<td>0.9≤1</td>
				<td>0.96</td>
				<td>0.96</td>
				<td>0.96</td>
				<td>0.97</td>
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
				<td class="td-middle" >402</td>
				<td>0.9≤1</td>
				<td>0.95</td>
				<td>0.96</td>
				<td>0.96</td>
				<td>0.97</td>
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
				<td class="td-middle" >403</td>
				<td>0.9≤1</td>
				<td>0.82</td>
				<td>0.87</td>
				<td>0.87</td>
				<td>0.86</td>
				<td>0.87</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="td-middle" >404</td>
				<td>0.9≤1</td>
				<td>0.8</td>
				<td>0.81</td>
				<td>0.82</td>
				<td>0.82</td>
				<td>0.82</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="td-middle" rowspan="2" >低压1＃进线</td>
				<td class="td-middle" >电压（V）</td>
				<td>400±20</td>
				<td>404</td>
				<td>398</td>
				<td>391</td>
				<td>401</td>
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
				<td class="td-middle" >电流（A）</td>
				<td>＜IN</td>
				<td>839</td>
				<td>1324</td>
				<td>1735</td>
				<td>1376</td>
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
				<td class="td-middle" rowspan="2" >低压2＃进线</td>
				<td class="td-middle" >电压（V）</td>
				<td>400±20</td>
				<td>406</td>
				<td>404</td>
				<td>391</td>
				<td>401</td>
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
				<td class="td-middle" >电流（A）</td>
				<td>＜IN</td>
				<td>1039</td>
				<td>1888</td>
				<td>2013</td>
				<td>1822</td>
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
				<td class="td-middle" rowspan="2" >低压2＃进线</td>
				<td class="td-middle" >电压（V）</td>
				<td>400±20</td>
				<td>406</td>
				<td>404</td>
				<td>391</td>
				<td>401</td>
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
				<td class="td-middle" >电流（A）</td>
				<td>＜IN</td>
				<td>1039</td>
				<td>1888</td>
				<td>2013</td>
				<td>1822</td>
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
				<td class="td-middle" rowspan="2" >低压3＃进线</td>
				<td class="td-middle" >电压（V）</td>
				<td>400±20</td>
				<td>400</td>
				<td>392</td>
				<td>390</td>
				<td>398</td>
				<td>396</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="td-middle" >电流（A）</td>
				<td>＜IN</td>
				<td>730</td>
				<td>944</td>
				<td>933</td>
				<td>935</td>
				<td>933</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="td-middle" rowspan="2" >低压4＃进线</td>
				<td class="td-middle" >电压（V）</td>
				<td>400±20</td>
				<td>407</td>
				<td>404</td>
				<td>404</td>
				<td>405</td>
				<td>406</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="td-middle" >电流（A）</td>
				<td>＜IN</td>
				<td>198</td>
				<td>200</td>
				<td>197</td>
				<td>198</td>
				<td>197</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
				<tr>
				<td class="td-middle">联络1</td>
				<td class="td-middle" >电流（A）</td>
				<td>＜IN</td>
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
				<td class="td-middle">联络2</td>
				<td class="td-middle" >电流（A）</td>
				<td>＜IN</td>
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
				<td class="td-middle">直流屏</td>
				<td class="td-middle" >电压（V）</td>
				<td>220±10</td>
				<td>220</td>
				<td>220</td>
				<td>220</td>
				<td>220</td>
				<td>220</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td class="td-middle">直流屏</td>
				<td class="td-middle" >电流（A））</td>
				<td>＜IN</td>
				<td>0.6</td>
				<td>0.6</td>
				<td>0.6</td>
				<td>0.6</td>
				<td>0.6</td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
		</tbody>
	</table>
</div>
</html>