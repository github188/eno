<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!-- 
	AUTHOR: CHENPING
	Created Date: 2013-7-11 下午2:16:34
	LastModified Date:
	Description:编辑计量器信息
 -->
<c:choose>
	<c:when test="${meter['meterid'] eq ''}">
		<c:set var="method" value="post" />
		<c:set var="editStatus" value="" />
	</c:when>
	<c:otherwise>
		<c:set var="method" value="put" />
		<c:set var="editStatus" value="disabled" />
	</c:otherwise>
</c:choose>
<c:if test="${not empty message}">
	<div class="alert alert-success">
		<button type="button" class="close" data-dismiss="alert">&times;</button>
		${message}
	</div>
</c:if>

<form:form modelAttribute="meter" method="${method}"
	class="form-horizontal">
	<div class="box">
		<div class="box-content">
			<div class="row-fluid">
				<div class="span6">
					<spring:bind path="metername">
						<c:set var="cssGroup"
							value="control-group ${status.error ? 'error' : '' }" />
						<div class="${cssGroup}">
							<form:label path="metername" class="control-label">计量仪:</form:label>
							<div class="controls">
								<form:input path="metername" class="input-medium" placeholder="" />
								<i style="padding-left: 16px;"></i>
								<form:input path="description" placeholder="描述" />
								<span class="help-inline">${status.errorMessage}</span>
							</div>
						</div>
					</spring:bind>

					<c:set var="cssGroup"
						value="control-group ${status.error ? 'error' : '' }" />
					<div class="${cssGroup}">
						<form:label path="metertype" class="control-label">仪表类型:</form:label>
						<div class="controls">
							<form:input path="metertype" class="input-medium" placeholder="" />
							<i class="icon-zoom-in"
								onclick="$('#dlgMetertype').dialog('open')" style="cursor:pointer;"></i> <input
								type="text" id="metertype.description"
								value="${metertype.description}" disabled /> <span
								class="help-inline">${status.errorMessage}</span>
						</div>
					</div>
					<div class="control-group">
						<form:label path="readingtype" class="control-label">读数类型:</form:label>
						<div class="controls">
							<form:input path="readingtype" class="input-medium" placeholder="" />
							<i class="icon-zoom-in"
								onclick="$('#dlgReadingtype').dialog('open')"  style="cursor:pointer;"></i> <input
								type="text" id="readingtype.description"
								value="${readingtype.description}" disabled />
						</div>
					</div>
				</div>
				<div class="span6">
					<div class="control-group">
						<form:label path="domainid" class="control-label">域:</form:label>
						<div class="controls">
							<form:input path="domainid" class="input-medium" placeholder="" />
							<i class="icon-zoom-in" onclick="$('#dlgDomain').dialog('open')"  style="cursor:pointer;"></i>
							<input type="text" id="domain.description" value="" disabled />
						</div>
					</div>
					<div class="control-group">
						<form:label path="measureunitid" class="control-label">计量单位:</form:label>
						<div class="controls">
							<form:input path="measureunitid" class="input-medium"
								placeholder="" />
								<a class="showMeasureunitWindow" link="<spring:url value="/measureunit/list"></spring:url>" ><i class="icon-zoom-in" style="cursor:pointer;"></i></a>
							 <input type="text" id="measureunit.desscription" readonly="readonly" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</form:form>
<%@ include file="../common/dialog/dlgMeasureUnit.jsp"%>

<%-- 
<!-- 仪表类型选择框 -->
<div id="dlgMetertype" class="easyui-dialog" title="选择值"
	style="width: 580px; height: 280px; padding: 10px" closed="true"
	modal="true" draggable="false">
	<%@ include file="dlgMetertype.jsp"%>
</div>
<!-- 读数类型选择框 -->
<div id="dlgReadingtype" class="easyui-dialog" title="选择值"
	style="width: 580px; height: 280px; padding: 10px" closed="true"
	modal="true" draggable="false">
	<%@ include file="dlgReadingtype.jsp"%>
</div>
<!-- 计量单位 -->
<spring:url value="/dialog/measureunit" var="measureunitUrl"></spring:url>
<div id="dlgMeasureunit" class="easyui-dialog" title="选择值"
	style="width: 700px; height: 450px; padding: 0px" closed="true"
	modal="true" draggable="false">
	<iframe id="winMeasureunit" scrolling="no" marginheight="0"
		marginwidth="0" frameborder="0" src=""
		style="width: 100%; height: 100%;"></iframe>
</div> --%>

<script>
	$(function() {

		//提交保存
		$("#submit").click(function() {
			$("#meter").submit();
		});

		//绑定计量仪类型数据源		
		var $dgMetertype = $("#dgMetertype");
		$.post('<spring:url value="/meter/metertypes"></spring:url>', function(
				data) {
			$dgMetertype.datagrid("loadData", data);
		});
		//选择计量仪类型
		$dgMetertype.datagrid({
			onSelect : function(rowIndex) {
				var row = $dgMetertype.datagrid('getRows')[rowIndex];
				$("#metertype").val(row["metertype"]);
				$("#metertype\\.description").val(row["description"]);

				$('#dlgMetertype').dialog('close');
			}
		});

		//绑定计量仪读表类型数据源
		var $dgReadingtype = $("#dgReadingtype");
		$.post('<spring:url value="/meter/readingtypes"></spring:url>',
				function(data) {
					$dgReadingtype.datagrid("loadData", data);
				});

		//选择计量仪读表类型
		$dgReadingtype.datagrid({
			onSelect : function(rowIndex) {
				var row = $dgReadingtype.datagrid('getRows')[rowIndex];
				$("#readingtype").val(row["readingtype"]);
				$("#readingtype\\.description").val(row["description"]);
				$('#dlgReadingtype').dialog('close');
			}
		});


	});
</script>
