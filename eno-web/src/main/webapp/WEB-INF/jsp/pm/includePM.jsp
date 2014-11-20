<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!-- common pm form -->
<div class="row-fluid">
	<div class="span4">
		<spring:bind path="pmnum">
			<c:set var="cssGroup"
				value="control-group ${status.error ? 'error' : '' }" />
			<div class="${cssGroup}">
				<form:label path="pmnum" class="control-label">PM:</form:label>
				<div class="controls">
					<c:choose>
						<c:when test="${empty pmid or pmid eq '0'}">
							<spring:url value="/pm/new/check.html" var="checkUrl"></spring:url>
							<form:input path="pmnum" class="input-small" title="必填字段不能为空"
								data-rule-required="true" data-placement="bottom"
								data-check-url="${checkUrl}" />
						</c:when>
						<c:otherwise>
							<form:input path="pmnum" class="input-small" readonly="true" />
							<form:hidden path="pmid" />
						</c:otherwise>
					</c:choose><i style="padding-right:36px"></i>
					<form:input path="description" class="input-medium" />
					<span class="help-inline">${status.errorMessage}</span>
				</div>
			</div>
		</spring:bind>
	</div>
	<div class="span4">
		<div class="control-group">
			<form:label path="siteid" class="control-label">地点:</form:label>
			<div class="controls">
				<form:input path="siteid" class="input-small" readOnly="true"  />
			</div>
		</div>
	</div>
	<div class="span4">
		<div class="control-group">
			<form:label path="status" class="control-label">状态:</form:label>
			<div class="controls">
				<form:input path="status" class="input-small" readOnly="true" />
			</div>
		</div>
	</div>
</div>
<!-- / common pm form -->