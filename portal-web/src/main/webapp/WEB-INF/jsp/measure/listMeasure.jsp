<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!-- 
	AUTHOR: CHENPING
	Created Date: 2013-8-21 下午5:40:35
	LastModified Date:
	Description: 状态监测测点模块
 -->
<div class="widget widget-table">
	<div class="widget-header">
		<span class="icon-list"></span>
		<h3 class="">状态监测</h3>
	</div>
	<div class="widget-content">
		<script>
			//编辑信息
			function editable(data, type, entity) {
				return '<a href="${pageContext.request.contextPath}/measure/edit/'+ entity.measurepointid + '.html"" style="color:blue">'
						+ data + '</a>';
			}
		</script>


		<spring:url value="/measure/list" var="url"></spring:url>
		<datatables:table id="assettable" url="${url}" serverSide="true"
			processing="true" theme="bootstrap2" filterPlaceholder="head_after">
			<datatables:column title="测点" property="pointnum" filterable="true"
				renderFunction="editable" />
			<datatables:column title="描述" property="description"
				filterable="true" />
			
			<datatables:column title="位置" property="location" filterable="true" />
			<datatables:column title="资产" property="assetnum" filterable="true" />
			<datatables:column title="计量器" property="metername" filterable="true" />
			<datatables:column title="地点" property="siteid" filterable="true" />
		</datatables:table>

		<!-- <table class="table table-bordered table-striped"
			id="measurepointtable">
			<thead>
				<tr>
					<th width="20"></th>
					<th width="12%" align="right">测点</th>
					<th>描述</th>
					<th width="12%">位置</th>
					<th width="12%">资产</th>
					<th width="12%">计量器</th>
					<th width="12%">地点</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table> -->
	</div>
</div>


