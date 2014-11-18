<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!-- 
	AUTHOR: CHENPING
	Created Date: 2013-8-27 下午5:14:13
	LastModified Date:
	Description: 工作计划表单
 -->
<c:choose>
	<c:when test="${jobPlan['jobplanid'] eq ''}">
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
<div class="box">
	<div class="box-content">
		<form:form modelAttribute="jobPlan" method="${method}"
			class="form-horizontal">

			<div class="row-fluid">
				<div class="span4">
					<spring:bind path="jpnum">
						<c:set var="cssGroup"
							value="control-group ${status.error ? 'error' : '' }" />
						<div class="${cssGroup}">
							<form:label path="jpnum" class="control-label">作业计划:</form:label>
							<div class="controls">
								<c:choose>
									<c:when test="${empty jobplanid}">
										<spring:url value="/jobplan/new/check.html" var="checkUrl"></spring:url>
										<form:input path="jpnum" class="input-small"
											title="必填字段作业编号不能为空" data-rule-required="true"
											data-placement="bottom" data-check-url="${checkUrl}" />
									</c:when>
									<c:otherwise>
										<form:input path="jpnum" class="input-small" readonly="true" />
									</c:otherwise>
								</c:choose>
								<form:input path="description" />
								<form:hidden path="jobplanid"/>
								<span class="help-inline">${status.errorMessage}</span>
							</div>
						</div>
					</spring:bind>
					<div class="control-group">
						<form:label path="status" class="control-label">状态：</form:label>
						<div class="controls">
							<form:input path="status" class="input-medium" readonly="true" />
						</div>
					</div>
				</div>
				<div class="span4">
					<div class="control-group">
						<form:label path="jpduration" class="control-label"> 持续时间:</form:label>
						<div class="controls">
							<form:input path="jpduration" class="input-medium" />
						</div>
					</div>
					<div class="control-group">
						<form:label path="priority" class="control-label">优先级：</form:label>
						<div class="controls">
							<form:input path="priority" class="input-medium number" title="必须为整数" data-rule-required="false" data-placement="bottom" />
						</div>
					</div>

				</div>
				<div class="span4">
					<div class="control-group">
						<form:label path="orgid" class="control-label"> 组织:</form:label>
						<div class="controls">
							<form:input path="orgid" class="input-medium" readonly="true"  />
						</div>
					</div>
					<div class="control-group">
						<form:label path="siteid" class="control-label">地点：</form:label>
						<div class="controls">
							<form:input path="siteid" class="input-medium" readonly="true" />
						</div>
					</div>

				</div>


			</div>
			<div class="row-fluid">
				<div class="widget">
					<div class="widget-header">
						<h3 class="icon">责任</h3>
					</div>
					<div class="widget-content">
						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<form:label path="supervisor" class="control-label">主管人:</form:label>
									<div class="controls">
										<form:input path="supervisor" class="input-medium" />
										<i class="icon-zoom-in" id="showValue"></i>
									</div>
								</div>
								<div class="control-group">
									<form:label path="crewid" class="control-label">班组:</form:label>
									<div class="controls">
										<form:input path="crewid" class="input-medium" />
										<i class="icon-zoom-in" id="showValue"></i>
									</div>
								</div>
								<div class="control-group">
									<form:label path="laborcode" class="control-label">责任人:</form:label>
									<div class="controls">
										<form:input path="laborcode" class="input-medium" />
										<i class="icon-zoom-in" id="showValue"></i>
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<form:label path="personGroup" class="control-label">工作组:</form:label>
									<div class="controls">
										<form:input path="personGroup" class="input-medium" />
										<i class="icon-zoom-in" id="showValue"></i>
									</div>
								</div>
								<div class="control-group">
									<form:label path="owner" class="control-label">所有者:</form:label>
									<div class="controls">
										<form:input path="owner" class="input-medium" />
										<i class="icon-zoom-in" id="showValue"></i>
									</div>
								</div>
								<div class="control-group">
									<form:label path="ownergroup" class="control-label">所有者组:</form:label>
									<div class="controls">
										<form:input path="ownergroup" class="input-medium" />
										<i class="icon-zoom-in" id="showValue"></i>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

		</form:form>
	</div>
</div>
<%@ include file="listJobTastk.jsp"%>

