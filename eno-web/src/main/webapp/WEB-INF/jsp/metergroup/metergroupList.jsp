<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp" %>
<!-- 
	AUTHOR: CHENPING
	Created Date: 2013-7-15 上午9:52:40
	LastModified Date:
	Description: 测量仪表组列表
 -->
<div class="widget widget-table">
	<div class="widget-header">
		<span class="icon-list"></span>
		<h3 class="icon chart">计量器组</h3>
	</div>
	<div class="widget-content">
	
		<script>
			//编辑信息
			function editable(data, type, entity) {
				return '<a href="${pageContext.request.contextPath}/metergroup/edit/'+ entity.metergroupid + '.html"" style="color:blue">'
						+ data + '</a>';
			}
		</script>

		<spring:url value="/metergroup/list" var="url"></spring:url>
		<datatables:table id="metergroup" url="${url}" serverSide="true"
			processing="true" theme="bootstrap2" filterPlaceholder="head_after">
			<datatables:column title="计量器组" property="groupname" filterable="true"
				renderFunction="editable" />
			<datatables:column title="描述" property="description"
				filterable="true" />
		</datatables:table>
		

	</div>
	<!-- .widget-content -->
</div>
<!-- .widget -->
 

