<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp" %>
<!-- 资产列表 -->
<script>
	//编辑信息
	function editable(data, type, entity) {
		return '<a href="#" onclick=setValue("'+ entity.assetnum +'","'+ entity.description +'") style="color:blue">'+ data +'</a>';
	}
			
	function setValue(num,description) {
		console.log(description);

		if(parent.$("#asset").length) {
			parent.$("#asset #parent").val(num);
		}
		if(parent.$("#measurePoint").length) {
			parent.$("#measurePoint #assetnum").val(num);
			parent.$("#measurePoint #asset\\.description").val(description);
		}
		if(parent.$("#assetnum")) {
			parent.$("#assetnum").val(num);
			parent.$("#asset\\.description").val(description);
		}
		parent.$('#dlgAssetList').modal('hide'); 
	}
	
	var specClassValues = ['HVAC', 'WSD', 'ETD','FAS','SASSA','MSVDO','SASAC','EP','LSPUB','LSN','MSEM','PFE','INFP','BGMB','PARKM','INENV'];
	
	 function transSpecClass(data, type, entity) {
		 switch(data) {
		 case "HVAC": return "暖通空调";
		 case "LSN": return "夜景照明";
		 case "LSPUB": return "公共照明";
		 case "WSDS": return "给排水系统";
		 case "MSEM": return "电梯系统";
		 case "ESDS": return "变配电系统";
		 default:return data;
		 }
	 }
			
			
</script>
<div class="widget widget-table">
	<div class="widget-header">
		<i class="icon-th-list"></i><h3>资产</h3> 
	</div>
	<div class="widget-content">		
		<spring:url value="/asset/list" var="url"></spring:url>
		<datatables:table id="assettable" url="${url}" serverSide="true" processing="true" theme="bootstrap2" cssClass="table table-hover table-nomargin table-bordered table-striped ">
		   <datatables:column title="资产" property="assetnum" filterable="true" renderFunction="editable" />
		   <datatables:column title="描述" property="description" filterable="true" />
		   <datatables:column title="专业" property="specclass" filterable="true"  renderFunction="transSpecClass" filterType="select" filterValues="specClassValues" />
		   <datatables:column title="位置" property="location" filterable="true" />
		   <datatables:column title="父级" property="parent"  filterable="true" />	   
		</datatables:table>
	</div>
	<!-- .widget-content -->
</div>
<!-- .widget -->

