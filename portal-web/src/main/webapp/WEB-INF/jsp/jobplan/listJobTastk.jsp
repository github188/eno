<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!-- 
	AUTHOR: CHENPING
	Created Date: 2013-8-28 下午1:11:04
	LastModified Date:
	Description: 作业计划任务
 -->
<div class="widget widget-table">
	<div class="widget-content">
		<table
			class="table table-hover table-nomargin table-bordered table-striped dataTable-columnfilter dataTable"
			id="jobtasktable">
			<thead>
				<tr>
					<th></th>
					<th>序号</th>
					<th>任务</th>
					<th>描述</th>
					<th>持续时间</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="6" style="padding: 0px;"><%@ include
							file="editJobTaskForm.jsp"%></td>
				</tr>
			</tfoot>
		</table>

	</div>
	<!-- .widget-content -->
</div>
<!-- .widget -->