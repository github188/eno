<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp" %>
<!-- 
	AUTHOR: CHENPING
	Created Date: 2013-8-26 上午9:22:37
	LastModified Date:
	Description: 公司列表
 -->
<div class="widget widget-table">
	<div class="widget-content">
		 <script>
			//编辑信息
			function editable(data, type, entity) {
				return '<a href="${pageContext.request.contextPath}/companies/edit/'+ entity.companiesid + '.html"" style="color:blue">'
						+ data + '</a>';
			}
		</script>
		<spring:url value="/companies/list" var="url"></spring:url>
		<datatables:table id="companiestable" url="${url}" serverSide="true"
			processing="true">
			<datatables:column title="公司" property="company" filterable="true"
				renderFunction="editable" />
			<datatables:column title="描述" property="name"
				filterable="true" />
			<datatables:column title="父级" property="parentcompany" filterable="true" />
			<datatables:column title="类别" property="type" filterable="true" />
			<datatables:column title="货币" property="currencycode" filterable="true" />
			<datatables:column title="组织" property="orgid" filterable="true" />
		</datatables:table>

	</div>
	<!-- .widget-content -->
</div>
<!-- .widget -->
 