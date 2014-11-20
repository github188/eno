<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<div class="row-fluid">
	<div class="control-group ">
		<label for="failureCode.failurecode" class="control-label">* 故障代码：</label>
		<div class="controls">
		<spring:bind path="failureCode.failurecode">
			<input type="text" name="${status.expression}" id="${status.expression}" class="input-small" title="必填字段'故障代码'不能为空" data-rule-required="true"
				data-placement="bottom"></spring:bind>
			<i class="icon-zoom-in" title="选择值" id="showFailurecode"></i>

			<input type="text" name="failureCode.description" id="failureCode.description" class="input-medium" />
			<input type="hidden" name="failureCode.failurecodeid" id="failureCode.failurecodeid" /> 
			<input type="hidden" name="failureList.failurelist" id="failureList.failurelist" /> 
		</div>
	</div>
</div>