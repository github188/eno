<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!-- 
	AUTHOR: CHENPING
	Created Date: 2013-10-18 下午9:21:52
	LastModified Date:
	Description:
 -->

<div class="span12">

	<form class="form-horizontal">
		<table class="">
			<thead>
				<tr>
					<th class="">开始时间: <input type="text" id="startdate"
						data-date-format="yyyy-mm-dd"></th>
					<th>结束时间: <input type="text" id="enddate"
						data-date-format="yyyy-mm-dd"></th>
					<th>日志级别: <select id="level">
							<option value="ERROR">错误</option>
							<option value="WARN">警告</option>
							<option value="INFO">信息</option>
					</select></th>
					<th><button id="query" class="btn btn-info">查询</button></th>
				</tr>
			</thead>
		</table>
	</form>

	<div class="box box-bordered">
		<div class="box-title"><h3>系统日志</h3></div>
		<div class="box-content nopadding">
			<table class="table table-hover table-nomargin table-bordered" id="logstable">
				<thead>
					<tr>
						<th width="60"></th>
						<!-- <th width="100">用户</th> -->
						<th width="120">时间</th>
						<th width="120">位置</th>
						<th width="120">等级</th>
						<th>描述</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
	</div>

</div>