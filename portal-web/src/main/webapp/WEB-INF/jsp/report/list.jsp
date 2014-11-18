<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!-- 
	AUTHOR: CHENPING
	Created Date: 2013-10-18 下午5:27:27
	LastModified Date:
	Description: 报表列表
 -->
 <!-- 时间控件用 -->

 <div class="clearfix"></div>
<div class="form_list row" id="report_choose">
	<div class="span3">
		<div class="form_title">
			<p>建筑总用能报表</p>
		</div>
		<div class="form_content">
			<h5>文字描述</h5>
			<div class="choice_date">
				<select class="format_select" id="select_type1">
					<option selected="selected" value="day">日报</option>
					<option value="week">周报</option>
					<option value="month">月报</option>
					<option value="year">年报</option>
				</select>
				<div class="date_mark">
					<input type="text" class="date_pick" id="starttime1"
					onclick="selectdate('starttime1','endtime1','select_type1')"
						/> <label
						for="to">至</label> <input type="text" class="date_pick"
						id="endtime1" readonly="readonly" /> <label>√</label>
				</div>
			</div>
			<div class="division1"></div>
			<div class="button_group preview_margin">
				<div class="preview" onclick="queryReport('starttime1','endtime1','select_type1', '1')">查&nbsp;&nbsp;&nbsp;&nbsp;询</div>
			</div>
		</div>
	</div>
	<div class="span3">
		<div class="form_title">
			<p>建筑总用电报表</p>
		</div>
		<div class="form_content">
			<h5>文字描述</h5>
			<div class="choice_date">
				<select class="format_select" id="select_type2">
					<option selected="selected" value="day">日报</option>
					<option value="week">周报</option>
					<option value="month">月报</option>
					<option value="year">年报</option>
				</select>
				<div class="date_mark">
					<input type="text" class="date_pick" id="starttime2"
						onclick="selectdate('starttime2','endtime2','select_type2')" /> <label
						for="to">至</label> <input type="text" class="date_pick"
						id="endtime2" readonly="readonly" /> <label>√</label>
				</div>
			</div>
			<div class="division1"></div>
			<div class="button_group preview_margin">
				<div class="preview" onclick="queryReport('starttime2','endtime2','select_type2', '2')">查&nbsp;&nbsp;&nbsp;&nbsp;询</div>
			</div>
		</div>
		
	</div>
	<div class="span3">
		<div class="form_title">
			<p>空调总用电报表</p>
		</div>
		<div class="form_content">
			<h5>文字描述</h5>
			<div class="choice_date">
				<select class="format_select" id="select_type3">
					<option selected="selected" value="day">日报</option>
					<option value="week">周报</option>
					<option value="month">月报</option>
					<option value="year">年报</option>
				</select>
				<div class="date_mark">
					<input type="text" class="date_pick" id="starttime3"
						onclick="selectdate('starttime3','endtime3','select_type3')" /> <label
						for="to">至</label> <input type="text" class="date_pick"
						id="endtime3" readonly="readonly" /> <label>√</label>
				</div>
			</div>
			<div class="division1"></div>
			<div class="button_group preview_margin">
				<div class="preview" onclick="queryReport('starttime3','endtime3','select_type3', '3')">查&nbsp;&nbsp;&nbsp;&nbsp;询</div>
			</div>
		</div>
	</div>
	<div class="span3">
		<div class="form_title">
			<p>冷机总用电报表</p>
		</div>
		<div class="form_content">
			<h5>文字描述</h5>
			<div class="choice_date">
				<select class="format_select" id="select_type4">
					<option selected="selected" value="day">日报</option>
					<option value="week">周报</option>
					<option value="month">月报</option>
					<option value="year">年报</option>
				</select>
				<div class="date_mark">
					<input type="text" class="date_pick" id="starttime4"
						onclick="selectdate('starttime4','endtime4','select_type4')" /> <label
						for="to">至</label> <input type="text" class="date_pick"
						id="endtime4" readonly="readonly" /> <label>√</label>
				</div>
			</div>
			<div class="division1"></div>
			<div class="button_group preview_margin">
				<div class="preview" onclick="queryReport('starttime4','endtime4','select_type4', '4')">查&nbsp;&nbsp;&nbsp;&nbsp;询</div>
			</div>
		</div>
	</div>

</div>

	
	<!-- 报表内容展示页面 -->
	<div class="row" style="margin:20px 10px;">
		<div class="form_style">
			<div class="form_func_title">
				<span class="list_title">建筑总能耗报表</span>
				<div class="block build_select">
					<span class="select_name">选择建筑</span>
					<select>
						<option>全部</option>
						<option>B2</option>
						<option>B1</option>
						<option>1F</option>
						<option>2F</option>
						<option>3F</option>
						<option>4F</option>
						<option>5F</option>
						<option>6F</option>
					</select>
				</div>
				<div class="block date_select">
					<span class="select_name">选择类型</span>
					<select id="select_time_type5">
						<option selected="selected" value="day">日报</option>
						<option value="week">周报</option>
						<option value="month">月报</option>
						<option value="year">年报</option>
					</select>
				</div>
				<div class="block">
					<span class="select_name">选择日期</span>
					<input type="text" id="from_time5" onclick="selectdate('from_time5','to_time5','select_time_type5')">
					<span class="select_name select_name_2">至</span>
					<input type="text" id="to_time5" readonly="readonly">
				</div>
				<div class="btn_group">
					<div id="querydata" class="cursor">查询</div>
					<div class="cursor">输出</div>
					<div id="backpage" class="cursor">返回</div>
				</div>
			</div>
			
			<div id="reportdiv"></div>
		</div>
	</div>
