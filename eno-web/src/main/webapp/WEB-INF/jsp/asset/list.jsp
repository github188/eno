<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>

<div class="widget widget-table">
	<div class="widget-header">
		<span class="icon-list"></span>
		<h3 class="">资产</h3>
	</div>
	<div class="widget-content">
		<script>
			//编辑信息
			function editable(data, type, entity) {
				return '<a href="${pageContext.request.contextPath}/asset/edit/'+ entity.assetuid + '.html"" style="color:blue">'
						+ data + '</a>';
			}
			
			var specClassValues = [{'value':'HVAC','label':'暖通空调'},
			                       {'value':'LSN','label':'夜景照明'},
			                       {'value':'LSPUB','label':'公共照明'},
			                       {'value':'WSDS','label':'给排水系统'},
			                       {'value':'MSEM','label':'电梯系统'},
			                       {'value':'ESDS','label':'变配电系统'},
			                       {'value':'MSVDO','label':'视频监控'},
			                       {'value':'SASSA','label':'防盗报警'}];  
			
			
			//, '', 'ETD','FAS','SASSA','','SASAC','EP','LSPUB','LSN','MSEM','PFE','INFP','BGMB','PARKM','INENV'
			
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
			
			var classifications;
			var locations;
			var classKeyValues = [];
			var locationKeyValues = [];
			
			var request = $.ajax({
				  async: false,
				  url: CONTEXT_PATH + "/class/classification",
				  type: "GET",
				  contentType: "application/json; charset=utf-8",
			      dataType: "json",
			      data: {"iDisplayStart":0,"iDisplayLength":100000}
				 // cache: false
				});
			request.done(function( data ) {
				//console.log(data);
				classifications = data.aaData;
				
				$.each(classifications,function(index,obj){
					classKeyValues.push({"value":obj.classificationid,"label":obj.description});
				});
				
			});
			
			function transClassName(data,type,entity) {
				var ret = "";
				$.each(classifications,function(index,obj){
					if(obj.classificationid==data) {
						ret = obj.description;
						return;
					}
				});
				
				return ret;
				
			}
			
			request = $.ajax({
				  async: false,
				  url: CONTEXT_PATH + "/locations/list",
				  type: "GET",
				  contentType: "application/json; charset=utf-8",
			      dataType: "json",
			      data: {"iDisplayStart":0,"iDisplayLength":100000}
				 // cache: false
				});
			request.done(function( data ) {
				//console.log(data);
				locations = data.aaData;
				
				$.each(locations,function(index,obj){
					locationKeyValues.push({"value":obj.location,"label":obj.description});
				});
			});
			
			
			
			function transLocation(data,type,entity) {
				var ret = "";
				$.each(locations,function(index,obj){
					if(obj.location==data) {
						ret = obj.description;
						return;
					}
				});
				return ret;
			}
			
		</script>
		
		<%
	    if (request.getParameter("specclass") == null) {
	        %>
	        <spring:url value="/asset/list" var="url"></spring:url>
	        <%
	    } else {
	        %> 
	        <c:set var="ReqScopeVar" value="${param.specclass}" scope="request"/>
	        <c:url value="/asset/list" var="url">
			   <c:param name="specclass" value="${ReqScopeVar}"/>
			</c:url>
	        <%
	    }
	%>

		<%-- <spring:url value="/asset/list" var="url"></spring:url> --%>
		<datatables:table id="assettable" url="${url}" serverSide="true"
			processing="true" theme="bootstrap2" filterPlaceholder="head_after" cssClass="table table-hover table-nomargin table-striped table-bordered table-striped">
			<datatables:column title="资产" property="assetnum" filterable="true"
				renderFunction="editable" />
			<datatables:column title="描述" property="description" 
				filterable="true" />
			<datatables:column title="专业" property="specclass" filterable="true" renderFunction="transSpecClass" filterType="select" filterValues="specClassValues" />
			<datatables:column title="类别" property="classstructureid" filterable="true" renderFunction="transClassName" filterType="select" filterValues="classKeyValues" />
			<datatables:column title="位置" property="location" filterable="true" renderFunction="transLocation" filterType="select" filterValues="locationKeyValues" />
			<datatables:column title="父级" property="parent" filterable="true" />
			<datatables:column title="地点" property="siteid" filterable="true" />
		</datatables:table>


	</div>
	<!-- .widget-content -->
</div>
<!-- .widget -->
