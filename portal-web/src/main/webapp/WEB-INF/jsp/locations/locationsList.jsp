<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<%@ taglib prefix="datatables"
	uri="http://github.com/dandelion/datatables"%>

<div class="widget widget-table">
	<div class="widget-content">		
		<script>
			//编辑信息
			function editable(data, type, entity) {
				return '<a href="${pageContext.request.contextPath}/locations/edit/'+ entity.locationsid + '.html"" style="color:blue">'
						+ data + '</a>';
			}
		</script>
		<spring:url value="/locations/list" var="url"></spring:url>
		<datatables:table id="locationstable" url="${url}" serverSide="true"
			processing="true">
			<datatables:column title="位置" property="location" filterable="true"
				renderFunction="editable" />
			<datatables:column title="描述" property="description"
				filterable="true" />
			<datatables:column title="类型" property="type" filterable="true" />
			<datatables:column title="状态" property="status" filterable="true" />
			<datatables:column title="地点" property="siteid" filterable="true" />
		</datatables:table>

	</div>
	<!-- .widget-content -->
</div>
<!-- .widget -->