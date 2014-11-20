<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp" %>
<!-- 
	AUTHOR: CHENPING
	Created Date: 2013-7-31 下午3:07:42
	LastModified Date:
	Description:计量器组对话框
 -->
 <script>
	//编辑信息
	function editable(data, type, entity) {
		return '<a href="#" onclick=setValue("'+ entity.groupname +'","'+ entity.description +'") style="color:blue">'+ data +'</a>';
	}
			
	function setValue(num,description) {
		 parent.$("#groupname").val(num);
		 parent.$("#metergroup\\.description").val(description);
		   
         parent.$('#dlgMetergroupList').modal('hide');;

	}
					
</script>
<div class="widget widget-table">
	<div class="widget-content">
	
		<spring:url value="/metergroup/list" var="url"></spring:url>
		<datatables:table id="metergrouptable" url="${url}" serverSide="true"
			processing="true" cssStyle="100%">
			<datatables:column title="计量器组" property="groupname" filterable="true"
				renderFunction="editable" />
			<datatables:column title="描述" property="description"
				filterable="true" />
		</datatables:table>

	</div>
	<!-- .widget-content -->
</div>
