<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!-- 
	AUTHOR: CHENPING
	Created Date: 2013-8-30 下午1:15:09
	LastModified Date:
	Description:测量结果
 -->
<div class="widget widget-table">
	<div class="widget-content">
		<table class="table table-bordered table-striped"
			id="measurementtable">
			<thead>
				<tr>
					<th width="20"></th>
					<th width="14%" align="right">技术规范</th>
					<th>规范描述</th>
					<th width="12%">计量单位</th>
					<th width="10%">计算方法</th>
					<th width="12%">数据来源</th>
					<th width="10%">测量时间</th>
					<th width="12%">TagID</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
			</tbody>
			<tfoot>
				<Tr>
					<td colspan="9">
					<%@ include file="editMeasurementForm.jsp" %>
					</td>
				</Tr>
			</tfoot>
		</table>
	</div>
</div>