<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<spring:url value="/jobplan/${jobplanid}/jobtask/save" var="action"></spring:url>
<form:form modelAttribute="jobTask" action="${action}"
	cssClass="form-horizontal" style="margin:0px;">
	<div id="jobTaskFormContent" style="display: none;">
		<div class="row-fluid">
			<div class=" alert alert-info">详细信息</div>
		</div>
		<div class="row-fluid">
			<div class="span6">
				<div class="control-group ">
					<form:label path="tasksequence" cssClass="control-label ">序号：</form:label>
					<div class="controls">
						<form:input path="tasksequence" cssClass="input-small" />
					</div>
				</div>
				<div class="control-group ">
					<form:label path="taskduration" cssClass="control-label ">持续时间：</form:label>
					<div class="controls">
						<form:input path="taskduration" cssClass="input-small" />
					</div>
				</div>
				<spring:bind path="description">
					<c:set var="cssGroup"
						value="control-group ${status.error ? 'error' : '' }" />
					<div class="${cssGroup}">
						<form:label path="jptask" cssClass="control-label ">作务：</form:label>
						<div class="controls">
							<c:choose>
								<c:when test="${empty jobtaskid}">
									<spring:url
										value="/jobplan/${jobPlan['jobplanid']}/jobtask/check.html"
										var="checkUrl"></spring:url>
									<form:input path="jptask" class="input-small"
										title="必填字段作业任务编号不能为空" data-rule-required="true"
										data-placement="bottom" data-check-url="${checkUrl}" />
								</c:when>
								<c:otherwise>
									<form:input path="jptask" class="input-medium" readonly="true" />
								</c:otherwise>
							</c:choose>
							<form:input path="description" class="input-medium" />
							<form:hidden path="jobplanid" />
							<form:hidden path="jpnum" />
						</div>
					</div>
				</spring:bind>

			</div>
			<div class="span6">
				<div class="control-group ">
					<form:label path="orgid" cssClass="control-label ">组织：</form:label>
					<div class="controls">
						<form:input path="orgid" cssClass="input-small" />
					</div>
				</div>
				<div class="control-group ">
					<form:label path="siteid" cssClass="control-label ">地点：</form:label>
					<div class="controls">
						<form:input path="siteid" cssClass="input-small" />
					</div>
				</div>

			</div>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span6 pull-right"
			style="text-align: right; padding: 5px 15px 5px 0;">
			<a class="btn" id="btnAddJobTask"><span><i
					class="icon-plus"></i> 新增</span></a>
			<button type="submit" class="btn btn-primary savebutton"
				style="display: none;">
				<i class="icon-ok-sign"></i> 保存
			</button>
		</div>
	</div>
</form:form>
