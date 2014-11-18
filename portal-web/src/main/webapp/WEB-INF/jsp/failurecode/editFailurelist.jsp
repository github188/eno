<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!-- 
	AUTHOR: CHENPING
	Created Date: 2013-7-9 下午5:22:34
	LastModified Date:
	Description: 编辑故障代码
 -->
<c:choose>
	<c:when test="${failureList['failurelist'] eq ''}">
		<c:set var="method" value="post" />
	</c:when>
	<c:otherwise>
		<c:set var="method" value="put" />
	</c:otherwise>
</c:choose>
<c:if test="${not empty message}">
	<div class="alert alert-success">
		<button type="button" class="close" data-dismiss="alert">&times;</button>
		${message}
	</div>
</c:if>
<div class="row-fluid">
	<div class="box">
		<div class="box-content">
			<form:form modelAttribute="failureCode" method="${method}"
				class="form-horizontal">
				<div class="row">
				<div class="span6">
						<spring:bind path="failurecode">
						<c:set var="cssGroup"
							value="control-group ${status.error ? 'error' : '' }" />
						<div class="${cssGroup}">
							<form:label path="failurecode" class="control-label">故障类别:</form:label>
							<div class="controls">
								<c:choose>
									<c:when test="${empty failurecodeid}">
										<spring:url value="/failurecode/new/check.html" var="checkUrl"></spring:url>
										<form:input path="failurecode" class="input-small"
											title="必填字段资产编号不能为空" data-rule-required="true"
											data-placement="bottom" data-check-url="${checkUrl}" />
									</c:when>
									<c:otherwise>
										<form:input path="failurecode" class="input-medium"
											readonly="true" />
									</c:otherwise>
								</c:choose>
								<form:input path="description"  class="input-medium" />
								<span class="help-inline">${status.errorMessage}</span>
							</div>
						</div>
					</spring:bind>
				</div>
				
				<div class="span6">
					<div class="control-group">
						<form:label path="orgid" class="control-label">组织机构:</form:label>
						<div class="controls">
							<form:input path="orgid" class="input-medium" disabled="true" />
						</div>
					</div>
				</div>
				</div>
			</form:form>
		</div>
	</div>
</div>
<div class="row-fluid">
	<!-- 故障问题描述 -->
	<div class="widget widget-table">
		<div class="widget-content">
			<table class="table table-bordered table-striped" ID="problemtable"
				data-source="${pageContext.request.contextPath}/failurecode/${failureList['failurelist']}/problems">
				<thead>
					<tr>
						<th></th>
						<th width="40%">故障代码</th>
						<th>问题描述</th>
						<th width="10%"></th>
					</tr>
				</thead>
				<tbody>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="4" style="padding: 0px;"><spring:url
								value="/failurecode/${failureList['failurelist']}/problems/save"
								var="action"></spring:url> <form:form
								modelAttribute="failureListSet" action="${action}"
								cssClass="form-horizontal" style="margin:0px;">

								<div id="problemFormContent" style="display: none;">
									<div class="row-fluid">
										<div class=" alert alert-info">详细信息</div>
									</div>
									<%@ include file="includeFailurecode.jsp"%>
								</div>
								<div class="row-fluid">
									<div class="span6 pull-right"
										style="text-align: right; padding: 5px 15px 5px 0;">
										<a class="btn" id="btnAddProblem"><span>新增</span></a>
										<button type="submit" id="btnSaveProblem"
											class="btn btn-primary savebutton" style="display: none;">
											保存
										</button>
									</div>
								</div>

							</form:form></td>
					</tr>
				</tfoot>
			</table>

		</div>
		<!-- .widget-content -->
	</div>
	<!-- 原因 -->
	<!-- .widget -->
	<div class="widget widget-table">
		<div class="widget-content">
			<table class="table table-bordered table-striped" ID="causetable"
				data-source="${pageContext.request.contextPath}/failurecode/{failurelist}/causes">
				<thead>
					<tr>
						<th></th>
						<th width="40%">故障代码</th>
						<th>问题描述</th>
						
					</tr>
				</thead>
				<tbody>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="4" style="padding: 0px;"><spring:url
								value="/failurecode/${failurelist}/causes/save" var="action"></spring:url>
							<form:form modelAttribute="failureCode" action="${action}"
								cssClass="form-horizontal" style="margin:0px;">
								<div id="problemFormContent" style="display: none;">
									<div class="row-fluid">
										<div class=" alert alert-info">详细信息</div>
									</div>
									<%@ include file="includeFailurecode.jsp"%>
								</div>
								<div class="row-fluid">
									<div class="span6 pull-right"
										style="text-align: right; padding: 5px 15px 5px 0;">
										<a class="btn" id="btnAddProblem"><span>新增</span></a>
										<button type="submit" class="btn btn-primary savebutton"
											style="display: none;">
											保存
										</button>
									</div>
								</div>
							</form:form></td>
					</tr>
				</tfoot>
			</table>

		</div>
		<!-- .widget-content -->
	</div>
	<!-- 解决方法 -->
	<!-- .widget -->
	<div class="widget widget-table">

		<div class="widget-content">
			<table class="table table-bordered table-striped" ID="remedytable"
				data-source="${pageContext.request.contextPath}/failurecode/${failureList['failurelist']}/remedys">
				<thead>
					<tr>
						<th></th>
						<th width="40%">故障代码</th>
						<th>问题描述</th>
						<th width="10%"></th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>

		</div>
		<!-- .widget-content -->
	</div>
	<!-- .widget -->
</div>

