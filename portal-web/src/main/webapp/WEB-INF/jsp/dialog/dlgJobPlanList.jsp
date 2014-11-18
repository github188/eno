<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp" %>
<!-- 作业计划窗口数据 -->
<script>
			//编辑信息
			function editable(data, type, entity) {
				return '<a href="#" onclick=setValue("'+ entity.jpnum +'","'+ entity.description +'") style="color:blue">'+ data +'</a>';
			}
			
			function setValue(num,description) {
				console.log(num);
				 if(parent.$("#jpnum").length) {
              		 parent.$("#jpnum").val(num);
              	 }
              	 if(parent.$("jobPlan\\.descritpion").length) {
              		parent.$("#jobPlan\\.descritpion=").val(descritpion);
              	 }
              	 
              	 if(parent.$("#pmSequence\\.jpnum").length) {
              		 parent.$("#pmSequence\\.jpnum").val(num);
              	 }
              	 if(parent.$("jobPlan\\.descritpion").length) {
              		parent.$("#jobPlan\\.descritpion=").val(descritpion);
              	 }

				parent.$('#dlgJobPlanList').modal('hide'); 
			}
			
			
		</script>
<div class="widget widget-table">
	<div class="widget-header">
		<i class="icon-th-list"></i><h3>作业计划</h3> 
	</div>
	<div class="widget-content">		
		<spring:url value="/jobplan/list" var="url"></spring:url>
		<datatables:table id="jobplantable" url="${url}" serverSide="true" processing="true">
		   <datatables:column title="作业计划" property="jpnum" filterable="true" renderFunction="editable" />
		   <datatables:column title="描述" property="description" filterable="true" />
		   <datatables:column title="组织" property="orgid" filterable="true" />
		   <datatables:column title="地点" property="siteid" filterable="true" />   
		</datatables:table>
	</div>
	<!-- .widget-content -->
</div>
<!-- .widget -->