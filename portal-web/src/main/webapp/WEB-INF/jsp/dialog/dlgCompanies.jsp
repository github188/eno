<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!-- 
	AUTHOR: CHENPING
	Created Date: 2013-7-24 下午1:48:37
	LastModified Date:
	Description: 公司定义对话框
 -->
 <script>
	//编辑信息
	function editable(data, type, entity) {
		return '<a href="#" onclick=setValue("'+ entity.company +'","'+ entity.name +'") style="color:blue">'+ data +'</a>';
	}
			
	function setValue(num,description) {
		
		if(parent.$("form#childCompany").length) {

			parent.$("#childCompany #company").val(num);
			parent.$("#childCompany #companiesid").val(selected_id);
        	if(name.length>0) {
            	if(parent.$("#childCompany #name").length) {
            		parent.$("#childCompany #name").val(description);
                }
        	}
		}
		if(parent.$("form#companies").length) {
			parent.$("#companies #parentcompany").val(num);
			parent.$("#companies #companies\\.name").val(description);
		}

		//资产
		if(parent.$("form#asset").length) {
			if('${type}'=='V') {
				parent.$("#asset #vendor").val(num);
				parent.$("#asset #vendor\\.description").val(description);
			}
			if('${type}'=='M') {
				parent.$("#asset #manufacturer").val(num);
				parent.$("#asset #manufacturer\\.description").val(description);
			}
		}						
        parent.$('#dlgCompanies').modal('hide');

	}
			
			
</script>
<div class="row-fluid">
	<div class="widget widget-table">
		<div class="widget-header">
			<i class="icon-th-list"></i><h3>公司</h3> 
		</div>
		<div class="widget-content">
			
		<spring:url value="/companies/list" var="url"></spring:url>
		<datatables:table id="companiestable" url="${url}" serverSide="true"
			processing="true">
			<datatables:column title="公司" property="company" filterable="true"
				renderFunction="editable" />
			<datatables:column title="描述" property="name"
				filterable="true" />
			
			<datatables:column title="类别" property="type" filterable="true" />
			
			<datatables:column title="组织" property="orgid" filterable="true" />
		</datatables:table>
		</div>
		<!-- .widget-content -->
	</div>
	<!-- .widget -->
</div>
