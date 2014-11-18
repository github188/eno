<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<c:choose>
	<c:when test="${locations['locationsid'] eq ''}">
		<c:set var="method" value="post" />
		<c:set var="editStatus" value="false" />
	</c:when>
	<c:otherwise>
		<c:set var="method" value="put" />
		<c:set var="editStatus" value="true" />
	</c:otherwise>
</c:choose>
<c:if test="${not empty message}">
	<div class="alert alert-success">
		<button type="button" class="close" data-dismiss="alert">&times;</button>
		${message}
	</div>
</c:if>

<div class="row-fluid">
	<form:form modelAttribute="locations" method="${method}"
		class="form-horizontal">
		<div class="box">
			<div class="box-content">
				<spring:bind path="locations.location">
					<c:set var="cssGroup"
						value="control-group ${status.error ? 'error' : '' }" />
					<div class="${cssGroup}">
						<form:label path="location" class="control-label">位置:</form:label>
						<div class="controls">
							<form:input path="location" class="input-small required"
								placeholder="位置代码" readonly="${editStatus}" />
							&nbsp;
							<form:input path="description" placeholder="位置描述" />
							<span class="help-inline">${status.errorMessage}</span>

						</div>
					</div>
				</spring:bind>
				<div class="control-group">
					<form:label path="type" class="control-label">类型:</form:label>
					<div class="controls">
						<form:select path="type"  class="input-small">
							<form:option value="OPERATING">操作位置</form:option>
							<form:option value="HOLDING">保存位置</form:option>
							<form:option value="COURIER">承运人位置</form:option>
							<form:option value="VENDOR">供应商位置</form:option>
							<form:option value="REPAIR">维修位置</form:option>
							<form:option value="SALVAGE">报废位置</form:option>
							<form:option value="LABOR">人工位置</form:option>
						</form:select>
						
					</div>
				</div>
				<div class="control-group">
					<form:label path="siteid" class="control-label">地点:</form:label>
					<div class="controls">
						<form:input path="siteid" class="input-small" />
					</div>
				</div>
				
			</div>
		</div>
	</form:form>
</div>