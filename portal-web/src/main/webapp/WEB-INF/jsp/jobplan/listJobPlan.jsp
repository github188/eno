<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp" %>
<!-- 
	AUTHOR: CHENPING
	Created Date: 2013-8-27 下午5:13:49
	LastModified Date:
	Description: 作业计划列表
 -->
<div class="widget widget-table">
	<div class="widget-content">
		<script>
			//编辑信息
			function editable(data, type, entity) {
				return '<a href="${pageContext.request.contextPath}/jobplan/edit/'+ entity.jobplanid + '.html"" style="color:blue">'
						+ data + '</a>';
			}
		</script>

		<spring:url value="/jobplan/list" var="url"></spring:url>
		<datatables:table id="pmtable" url="${url}" serverSide="true" processing="true">
			<datatables:column title="作业计划" property="jpnum" filterable="true"
				renderFunction="editable" />
			<datatables:column title="描述" property="description"
				filterable="true" />
			<datatables:column title="持续时间" property="jpduration" filterable="true" />
			<datatables:column title="主管人" property="supervisor" filterable="true" />
			<datatables:column title="状态" property="status" filterable="true" />
			<datatables:column title="组织" property="orgid" filterable="true" />
			<datatables:column title="地点" property="siteid" filterable="true" />
		</datatables:table>

	</div>
	<!-- .widget-content -->
</div>
<!-- .widget -->