<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!-- 
	AUTHOR: CHENPING
	Created Date: 2013-7-11 下午2:16:15
	LastModified Date:
	Description:计量器列表
 -->
<div class="widget widget-table">
	<div class="widget-content">
		<script>
			//编辑信息
			function editable(data, type, entity) {
				return '<a href="${pageContext.request.contextPath}/meter/edit/'+ entity.meterid + '.html"" style="color:blue">'
						+ data + '</a>';
			}
		</script>
		<spring:url value="/meter/list" var="url"></spring:url>
		<datatables:table id="metertable" url="${url}" serverSide="true" processing="true">
		   <datatables:column title="计量器" property="metername" filterable="true" renderFunction="editable" />
		   <datatables:column title="描述" property="description" filterable="true" />
		   <datatables:column title="计量器类型" property="metertype" filterable="true" />   
		</datatables:table>
	</div>
	<!-- .widget-content -->
</div>
<!-- .widget -->
