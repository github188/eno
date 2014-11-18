<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!-- 记量器列表 -->
<script>
	//编辑信息
	function editable(data, type, entity) {
		return '<a href="#" onclick=setValue("'+ entity.metername +'","'+ entity.description +'","'+ entity.metertype +'") style="color:blue">'+ data +'</a>';
	}
			
	function setValue(num,description,metertype) {
		parent.$("#metername").val(num);
		parent.$("#meter\\.description").val(description);
		parent.$("#metertype").val(metertype);
		
		if(parent.$("form#pmMeterSet").length) {
			parent.$("#pmMeterSet #pmMeter\\.metername").val(num);
		}
		
		if(parent.$("#meter_description").length) {
			parent.$("#meter_description").val(description);
		}
		
		
        parent.$('#dlgMeter').modal('hide');
	}
			
			
		</script>
<div class="widget widget-table">
	<div class="widget-content">
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
