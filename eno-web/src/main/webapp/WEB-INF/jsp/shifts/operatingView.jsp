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
			<div class="btnStyle1 btn2 nomargin cur"><p><a href="${pageContext.request.contextPath}/shifts/operatingView">冷机运行</a></p></div>
			<div class="btnStyle1 btn2"><p><a href="${pageContext.request.contextPath}/shifts/operatingSnView">室内温度</a></p></div>
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
				<th></th>
				<th></th>
				<th></th>
				<th></th>
				<th>10:00</th>
				<th>12:00</th>
				<th>14:00</th>
				<th>16:00</th>
				<th>18:00</th>
				<th>20:00</th>
				<th>22:00</th>
				<th>0:00</th>
				<th>2:00</th>
			</tr>
			<tr>
				<td class="td-middle" rowspan="13" >冷水机组数据</td>
				<td class="td-middle" rowspan="5">冷水机组</td>
				<td>开启机组编号</td>
				<td>2#</td>
				<td>2#</td>
				<td>2#</td>
				<td>2#</td>
				<td>2#</td>
				<td>2#</td>
				<td>2#</td>
				<td>2#</td>
				<td>2#</td>
				<td>2#</td>
			</tr>
			<tr>
				<td>电流</td>
				<td>A</td>
				<td>800</td>
				<td>900</td>
				<td>960</td>
				<td>960</td>
				<td>960</td>
				<td>960</td>
				<td>840</td>
				<td>800</td>
				<td>800</td>
			</tr>
			<tr>
				<td>油温</td>
				<td>℃</td>
				<td>48.9</td>
				<td>49.1</td>
				<td>50.1</td>
				<td>50.3</td>
				<td>50.5</td>
				<td>50.5</td>
				<td>49.2</td>
				<td>47.2</td>
				<td>48.8</td>
			</tr>
			<tr>
				<td>油压差</td>
				<td>Kpa</td>
				<td>240.1</td>
				<td>242.5</td>
				<td>249.2</td>
				<td>244.0</td>
				<td>250.1</td>
				<td>237.2</td>
				<td>242</td>
				<td>249</td>
				<td>230</td>
			</tr>
			<tr>
				<td>负荷</td>
				<td>%</td>
				<td>78</td>
				<td>89</td>
				<td>89</td>
				<td>90</td>
				<td>88</td>
				<td>87</td>
				<td>81</td>
				<td>78</td>
				<td>73</td>
			</tr>
			<tr>
				<td class="td-middle" rowspan="2">蒸发器</td>
				<td>蒸发压力</td>
				<td>Kpa</td>
				<td>251</td>
				<td>242.1</td>
				<td>240.2</td>
				<td>248.9</td>
				<td>248.9</td>
				<td>247.5</td>
				<td>246</td>
				<td>250</td>
				<td>250</td>
			</tr>
			<tr>
				<td>蒸发温度</td>
				<td>%</td>
				<td>5.2</td>
				<td>5.1</td>
				<td>5</td>
				<td>5</td>
				<td>5</td>
				<td>4.9</td>
				<td>4.8</td>
				<td>5.1</td>
				<td>5.1</td>
			</tr>
			<tr>
				<td class="td-middle" rowspan="2">冷凝器</td>
				<td>冷凝压力</td>
				<td>Kpa</td>
				<td>813.7</td>
				<td>824.5</td>
				<td>868.2</td>
				<td>873.7</td>
				<td>873.1</td>
				<td>865.5</td>
				<td>828</td>
				<td>829</td>
				<td>805</td>
			  </tr>
			  <tr>
				<td>冷凝温度</td>
				<td>℃</td>
				<td>36.1</td>
				<td>37</td>
				<td>38.2</td>
				<td>38.4</td>
				<td>38.4</td>
				<td>38.1</td>
				<td>36.7</td>
				<td>36.7</td>
				<td>35.8</td>
			  </tr>
			  <tr>
				<td class="td-middle" rowspan="2">冷冻水</td>
				<td>出水温度</td>
				<td>℃</td>
				<td>6.2</td>
				<td>6.2</td>
				<td>6.1</td>
				<td>6.1</td>
				<td>6.1</td>
				<td>6</td>
				<td>5.8</td>
				<td>6.1</td>
				<td>6</td>
			  </tr>
			  <tr>
				<td>回水温度</td>
				<td>℃</td>
				<td>10.7</td>
				<td>10.7</td>
				<td>11.1</td>
				<td>11</td>
				<td>11</td>
				<td>11</td>
				<td>10.3</td>
				<td>10.1</td>
				<td>9.7</td>
			  </tr>
			  <tr>
				<td class="td-middle" rowspan="2">冷却水</td>
				<td>出水温度</td>
				<td>℃</td>
				<td>30.5</td>
				<td>30.1</td>
				<td>31</td>
				<td>32.2</td>
				<td>32.3</td>
				<td>31</td>
				<td>31</td>
				<td>31.6</td>
				<td>31</td>
			  </tr>
			  <tr>
				<td>回水温度</td>
				<td>℃</td>
				<td>25.1</td>
				<td>26</td>
				<td>26.1</td>
				<td>26.3</td>
				<td>26.5</td>
				<td>26.1</td>
				<td>25.6</td>
				<td>26.6</td>
				<td>26.6</td>
			  </tr>
			  <tr>
				<td class="td-middle" rowspan="3">一次冷冻泵#</td>
				<td class="td-middle" rowspan="3">循环泵数据</td>
				<td>出水压力</td>
				<td>Mpa</td>
				<td>0.55</td>
				<td>0.55</td>
				<td>0.55</td>
				<td>0.55</td>
				<td>0.55</td>
				<td>0.55</td>
				<td>0.55</td>
				<td>0.55</td>
				<td>0.55</td>
			  </tr>
			  <tr>
				<td>回水压力</td>
				<td>Mpa</td>
				<td>0.45</td>
				<td>0.45</td>
				<td>0.45</td>
				<td>0.45</td>
				<td>0.45</td>
				<td>0.45</td>
				<td>0.45</td>
				<td>0.45</td>
				<td>0.45</td>
			  </tr>
			  <tr>
				<td>电流</td>
				<td>A</td>
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
				<td class="td-middle" rowspan="3">二次冷冻泵#</td>
				<td class="td-middle" rowspan="3">循环泵数据</td>
				<td>出水压力</td>
				<td>Mpa</td>
				<td>0.55</td>
				<td>0.55</td>
				<td>0.55</td>
				<td>0.55</td>
				<td>0.55</td>
				<td>0.55</td>
				<td>0.55</td>
				<td>0.55</td>
				<td>0.55</td>
			  </tr>
			  <tr>
				<td>回水压力</td>
				<td>Mpa</td>
				<td>0.45</td>
				<td>0.45</td>
				<td>0.45</td>
				<td>0.45</td>
				<td>0.45</td>
				<td>0.45</td>
				<td>0.45</td>
				<td>0.45</td>
				<td>0.45</td>
			  </tr>
			  <tr>
				<td>电流</td>
				<td>A</td>
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
				<td class="td-middle" rowspan="3" colspan="2">冷却泵#</td>
				<td>出水压力</td>
				<td>Mpa</td>
				<td>0.6</td>
				<td>0.6</td>
				<td>0.6</td>
				<td>0.6</td>
				<td>0.6</td>
				<td>0.6</td>
				<td>0.6</td>
				<td>0.6</td>
				<td>0.6</td>
			  </tr>
			  <tr>
				<td>回水压力</td>
				<td>Mpa</td>
				<td>0.26</td>
				<td>0.26</td>
				<td>0.26</td>
				<td>0.26</td>
				<td>0.26</td>
				<td>0.26</td>
				<td>0.26</td>
				<td>0.26</td>
				<td>0.26</td>
			  </tr>
			  <tr>
				<td>电流</td>
				<td>A</td>
				<td>150</td>
				<td>150</td>
				<td>150</td>
				<td>150</td>
				<td>150</td>
				<td>150</td>
				<td>150</td>
				<td>150</td>
				<td>150</td>
			  </tr>
			  <tr>
				<td class="td-middle" rowspan="2">环境温度</td>
				<td class="td-middle" rowspan="2">室内</td>
				<td>温度</td>
				<td>℃</td>
				<td>26</td>
				<td>26</td>
				<td>26</td>
				<td>26</td>
				<td>26</td>
				<td>26</td>
				<td>25</td>
				<td>25</td>
				<td>25</td>
			  </tr>
			  <tr>
				<td>湿度</td>
				<td>%</td>
				<td>50</td>
				<td>50</td>
				<td>50</td>
				<td>50</td>
				<td>50</td>
				<td>50</td>
				<td>48</td>
				<td>48</td>
				<td>48</td>
			  </tr>
			  <tr>
				<td class="td-middle" rowspan="2">冷却塔</td>
				<td class="td-middle" rowspan="2">现场情况</td>
				<td>风扇情况</td>
				<td></td>
				<td>正常</td>
				<td>正常</td>
				<td>正常</td>
				<td>正常</td>
				<td>正常</td>
				<td>正常</td>
				<td>正常</td>
				<td>正常</td>
				<td>正常</td>
			  </tr>
			  <tr>
				<td>补水情况</td>
				<td></td>
				<td>正常</td>
				<td>正常</td>
				<td>正常</td>
				<td>正常</td>
				<td>正常</td>
				<td>正常</td>
				<td>正常</td>
				<td>正常</td>
				<td>正常</td>
			  </tr>
		</tbody>
	</table>
</div>
</html>