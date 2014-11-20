<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp" %>
<!-- 
	AUTHOR: CHENPING
	Created Date: 2013-9-2 上午11:22:37
	LastModified Date:
	Description:预防性维护列表
 -->
<div class="widget widget-table">
	<div class="widget-content">
		<script>
			//编辑信息
			function editable(data, type, entity) {
				return '<a href="${pageContext.request.contextPath}/pm/edit/'+ entity.pmid + '.html"" style="color:blue">'
						+ data + '</a>';
			}
		</script>

		<spring:url value="/pm/list" var="url"></spring:url>
		<datatables:table id="pmtable" url="${url}" serverSide="true" processing="true">
			<datatables:column title="PM" property="pmnum" filterable="true"
				renderFunction="editable" />
			<datatables:column title="描述" property="description"
				filterable="true" />
			<datatables:column title="位置" property="location" filterable="true" />
			<datatables:column title="资产" property="assetnum" filterable="true" />
			<datatables:column title="优先级" property="priority" filterable="true" />
			<datatables:column title="地点" property="siteid" filterable="true" />
		</datatables:table>
		
		
	</div>
</div>