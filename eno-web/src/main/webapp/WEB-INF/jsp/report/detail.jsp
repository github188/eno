<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp" %>
<!-- 
	AUTHOR: CHENPING
	Created Date: 2013-10-18 下午5:27:57
	LastModified Date:
	Description:报表明细
 -->
	<!-- 报表内容展示页面 -->
	<div class="form_style">
		<div class="form_func_title">
			<span class="list_title">建筑总能耗报表</span>
			<div class="block build_select">
				<span class="select_name">选择建筑</span>
				<select>
					<option>整个园区</option>
					<option>1号楼</option>
					<option>2号楼</option>
				</select>
			</div>
			<div class="block date_select">
				<span class="select_name">选择类型</span>
				<select id="select_time_type">
					<option selected="true" value="day">日报</option>
					<option value="week">周报</option>
					<option value="month">月报</option>
					<option value="year">年报</option>
				</select>
			</div>
			<div class="block">
				<span class="select_name">选择日期</span>
				<input type="text" id="from_time" onclick="selectdate('from_time','to_time','select_time_type')">
				<span class="select_name select_name_2">至</span>
				<input type="text" id="to_time" readonly="readonly">
			</div>
			<div class="btn_group">
				<div id="querydata" class="cursor">查询</div>
				<div class="cursor">输出</div>
				<div id="backpage" class="cursor">返回</div>
			</div>
		</div>
		
		<div id="reportdiv"></div>
	</div>