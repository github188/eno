<%--
  Created by IntelliJ IDEA.
  User: shangpeibao
  Date: 14-1-3
  Time: 下午4:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%-- 客流系统下方公共部分Start --%>
<div class="custom_title">
	<h1 style="border-right: #666 solid 1px; padding-right: 20px;">总体客流</h1>
	<p class="custom_count">
		本月工作日日均客流量(人次)<strong id="avgWorkDayCount">9,518</strong><span
			class="line_green">|</span> 本月节假日日均客流量(人次)<strong
			id="avgHolidayCount">15,532</strong>
	</p>
</div>
<div class="custom_count_box">
	<ul>
		<li class="white">
        	<p class="custom_in"><i class="in_ico"></i><span class="fcolor_4">总进</span></p>
            <p class="custom_num fcolor_7" id="totalInCount">40,511</p>
            <p class="custom_match"><span class="custom_match_txt">同比昨日</span><span class="custom_match_per"><cite id="compareImg">↑</cite><span id="compareOfYestoday">2</span>%</span></p>
        </li>
		<li class="grey">
			<p class="custom_in">
				<i class="out_ico"></i><span class="fcolor_5">总出</span>
			</p>
			<p class="custom_num fcolor_8" id="totalOutCount">39,525</p>
		</li>
		<li class="blue">
			<p class="custom_in">
				<i class="inner_ico"></i><span class="fcolor_5">场内</span>
			</p>
			<p class="custom_num fcolor_8" id="totalInSide">9,851</p>
			<p class="custom_match">
				<span class="custom_match_txt fcolor_8">当前人数</span>
			</p> <!--                         <p><a href="#" class="edit_ico"></a></p> -->
		</li>
		<li class="chart">
			<div class="chart_pic">
				<div class="chart_time" id="todayChart">今日</div>
			</div>
			<div class="chart_state">
				<p class="custom_list trendlist">
					<a href="#" onClick="trendChart('in')">总进</a>|<a href="#"
						onClick="javascript: trendChart('out')">总出</a>|<a href="#"
						onClick="trendChart('inside')" class="trendChart">场内</a>
				</p>
				<span class="chart_tit">客流趋势图</span>
			</div>
		</li>
	</ul>
</div>
<%-- 客流系统下方公共部分End --%>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/pfe/pfecommon.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/format.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/pfe/passenger.js"></script>

