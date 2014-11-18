<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>

<input id="showDateTime" type="hidden" />
<div class="span12 main row-fluid" id="energyChart">
	<div class="span10 right_content nomargin"
		style="width: 100% !important;">
		<div class="energy_chart">
			<p class="build">建筑总用电趋势与室外温度</p>
			<div>
				<div class="button_group">
					<div class="changeColor"
						onclick="renderDateTime('day',this,'build_electricity')">日</div>
					<div onclick="renderDateTime('week',this,'build_electricity')">周</div>
					<div onclick="renderDateTime('month',this,'build_electricity')">月</div>
					<div onclick="renderDateTime('year',this,'build_electricity')">年</div>

					<div style="width: 180px;">
						&nbsp;与&nbsp; <input class="input_class" name="comparetime1"
							id="comparetime1"
							onclick="comparetime('comparetime1','build_electricity')" />&nbsp;对比&nbsp;
					</div>
				</div>
				<div class="chart_class" id="build_electricity"></div>
			</div>
		</div>
		<div class="energy_chart">
			<p class="fans">建筑总用电趋势与客流量</p>
			<div>
				<div class="button_group">
					<div class="changeColor"
						onclick="renderDateTime('day',this,'build_electricity_passenger')">日</div>
					<div
						onclick="renderDateTime('week',this,'build_electricity_passenger')">周</div>
					<div
						onclick="renderDateTime('month',this,'build_electricity_passenger')">月</div>
					<div
						onclick="renderDateTime('year',this,'build_electricity_passenger')">年</div>

					<div style="width: 180px;">
						&nbsp;与&nbsp; <input class="input_class"
							name="comparetime1_passenger" id="comparetime1_passenger"
							onclick="comparetime('comparetime1_passenger','build_electricity_passenger')" />&nbsp;对比&nbsp;
					</div>
				</div>
				<div class="chart_class" id="build_electricity_passenger"></div>
			</div>
		</div>
		<div class="energy_chart">
			<p class="build_detail">建筑用电分项</p>
			<div>
				<div class="button_group">
					<div class="changeColor"
						onclick="renderDateTime('day',this,'build_ele_trend')">日</div>
					<div onclick="renderDateTime('week',this,'build_ele_trend')">周</div>
					<div onclick="renderDateTime('month',this,'build_ele_trend')">月</div>
					<div onclick="renderDateTime('year',this,'build_ele_trend')">年</div>

					<div style="width: 180px;">
						&nbsp;与&nbsp; <input class="input_class" name="comparetime2"
							id="comparetime2"
							onclick="comparetime('comparetime2','build_ele_trend')" />&nbsp;对比&nbsp;
					</div>
				</div>
				<div class="chart_class" id="build_ele_trend"></div>
			</div>
		</div>
		<div class="energy_chart">
			<p class="air_tend">空调系统用电趋势</p>
			<div>
				<div class="button_group">
					<div class="changeColor"
						onclick="renderDateTime('day',this,'air_system_trend')">日</div>
					<div onclick="renderDateTime('week',this,'air_system_trend')">周</div>
					<div onclick="renderDateTime('month',this,'air_system_trend')">月</div>
					<div onclick="renderDateTime('year',this,'air_system_trend')">年</div>

					<div style="width: 180px;">
						&nbsp;与&nbsp; <input class="input_class" name="comparetime3"
							id="comparetime3"
							onclick="comparetime('comparetime3','air_system_trend')" />&nbsp;对比&nbsp;
					</div>
				</div>
				<div class="chart_class" id="air_system_trend"></div>
			</div>
		</div>
		<div class="energy_chart">
			<p class="air">空调系统用电分项</p>
			<div>
				<div class="button_group">
					<div class="changeColor"
						onclick="renderDateTime('day',this,'air_ele_item')">日</div>
					<div onclick="renderDateTime('week',this,'air_ele_item')">周</div>
					<div onclick="renderDateTime('month',this,'air_ele_item')">月</div>
					<div onclick="renderDateTime('year',this,'air_ele_item')">年</div>

					<div style="width: 180px;">
						&nbsp;与&nbsp; <input class="input_class" name="comparetime4"
							id="comparetime4"
							onclick="comparetime('comparetime4','air_ele_item')" />&nbsp;对比&nbsp;
					</div>
				</div>
				<div class="chart_class" id="air_ele_item"></div>
			</div>
		</div>
		<div class="energy_chart">
			<p class="light">照明系统用电趋势</p>
			<div>
				<div class="button_group">
					<div class="changeColor"
						onclick="renderDateTime('day',this,'light_system')">日</div>
					<div onclick="renderDateTime('week',this,'light_system')">周</div>
					<div onclick="renderDateTime('month',this,'light_system')">月</div>
					<div onclick="renderDateTime('year',this,'light_system')">年</div>

					<div style="width: 180px;">
						&nbsp;与&nbsp; <input class="input_class" name="comparetime5"
							id="comparetime5"
							onclick="comparetime('comparetime5','light_system')" />&nbsp;对比&nbsp;
					</div>
				</div>
				<div class="chart_class" id="light_system"></div>
			</div>
		</div>
		<div class="energy_chart" style="display: none;">
			<p class="fans">冷机用电趋势</p>
			<div>
				<div class="button_group">
					<div class="changeColor"
						onclick="renderDateTime('day',this,'cool_refrigeration')">日</div>
					<div onclick="renderDateTime('week',this,'cool_refrigeration')">周</div>
					<div onclick="renderDateTime('month',this,'cool_refrigeration')">月</div>
					<div onclick="renderDateTime('year',this,'cool_refrigeration')">年</div>

					<div style="width: 180px;">
						&nbsp;与&nbsp; <input class="input_class" name="comparetime6"
							id="comparetime6"
							onclick="comparetime('comparetime6','cool_refrigeration')" />&nbsp;对比&nbsp;
					</div>
				</div>
				<div class="chart_class" id="cool_refrigeration"></div>
			</div>
		</div>
	</div>
</div>
<style type="text/css">
.energy_chart {
	width: 710px;
	height: 315px;
	float: left;
	margin: 0px 0px 0px 15px;
}

.energy_chart>p {
	background-color: #26c3bc;
	line-height: 40px;
	color: #fff;
	text-indent: .8em;
	font-size: 20px;
}

.energy_chart>div {
	height: 264px;
	background-color: #fff;
	position: relative;
}

.button_group {
	position: absolute;
	top: 0px;
}

.button_group>div {
	float: left;
	width: 65px;
	height: 29px;
	line-height: 29px;
	color: #fff;
	border-radius: 4px;
	text-align: center;
	background-color: #c1c1c1;
	font-size: 18px;
	margin-left: 15px;
	margin-top: 5px;
	cursor: pointer;
}

.changeColor {
	background-color: #39a6ad !important;
}

.build {
	background-color: #26c3bc !important;
}

.light {
	background-color: #5cc8e4 !important;
}

.fans {
	background-color: #e66e4c !important;
}

.air {
	background-color: #faab35 !important;
}

.build_detail {
	background-color: #5cc8e4 !important;
}

.air_tend {
	background-color: #469fe3 !important;
}
</style>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/scripts/mctrl/DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/scripts/systemList.js"></script>
<!-- customVar.js主要是用于声明获取数据的方式以及数据获取的地址 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/mctrl/customVar.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/scripts/renderChart.js"></script>
<script type="text/javascript">
$(function() {
	// 获取当前图表
	var _currenttime = getCurrentTime();
	getBuildElectricityChart('day', _currenttime); // 1.建筑总用电趋势与室外温度图表
	getBuildElectricityPassengerChart('day', _currenttime); // 2.建筑总用电趋势与客流量图表
	getBuildEleTrendChart('day', _currenttime); // 3.建筑用电分项图表
	getAirEleTrendChart('day', _currenttime); // 4.空调系统用电趋势图表
	getAirEleItemChart('day', _currenttime); // 5.空调系统用电分项图表
	getLightSystemChart('day', _currenttime); // 6.照明系统用电趋势图表
	getCoolRefrigerationChart('day', _currenttime); // 7.冷机用电趋势图表
});
</script>