<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!-- 
	AUTHOR: CHENPING
	Created Date: 2013-8-26 上午9:22:57
	LastModified Date:
	Description: 编辑公司信息
 -->
<c:choose>
	<c:when test="${companies['companiesid'] eq ''}">
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
<div id="detailmsg"></div>
<div class="box">
	<div class="box-content">
		<form:form modelAttribute="companies" method="${method}"
			class="form-horizontal">
			<div class="row-fluid">
				<div class="span6">
					<spring:bind path="company">
						<c:set var="cssGroup"
							value="control-group ${status.error ? 'error' : '' }" />
						<div class="${cssGroup}">
							<form:label path="company" class="control-label">代码:</form:label>
							<div class="controls">
								<c:choose>
									<c:when test="${empty companiesid}">
										<spring:url value="/companies/new/check.html" var="checkUrl"></spring:url>
										<form:input path="company" class="input-medium"
											title="必填字段公司代码不能为空" data-rule-required="true"
											data-placement="bottom" data-check-url="${checkUrl}" />
									</c:when>
									<c:otherwise>
										<form:input path="company" class="input-medium"
											readonly="true" />
									</c:otherwise>
								</c:choose>
								<span class="help-inline">${status.errorMessage}</span>
							</div>
						</div>
					</spring:bind>
					<spring:bind path="name">
						<c:set var="cssGroup"
							value="control-group ${status.error ? 'error' : '' }" />
						<div class="${cssGroup}">
							<form:label path="name" class="control-label">公司:</form:label>
							<div class="controls">
								<form:input path="name" class="input-medium" />
								<span class="help-inline">${status.errorMessage}</span>
							</div>
						</div>
					</spring:bind>

					<div class="control-group ">
						<form:label path="parentcompany" class="control-label">父级:</form:label>
						<div class="controls">
							<form:input path="parentcompany" class="input-medium" />
							<div class="btn-group">
								<button class="btn dropdown-toggle" data-toggle="dropdown">
									&gt;&gt;</button>
								<ul class="dropdown-menu">
									<li><a href="#" class="showCompaniesWindow"
										link="<spring:url value="/companies/list/dialog" />">选择值</a></li>
								</ul>
							</div>
							<!-- /btn-group -->
							<input type="text" name="companies.name"
								id="companies.name" disabled />
						</div>
					</div>
					<div class="control-group">
						<form:label path="homepage" class="control-label">主页:</form:label>
						<div class="controls">
							<form:input path="homepage" class="input-medium" />
						</div>
					</div>

				</div>
				<div class="span6">
					<div class="control-group ">
						<form:label path="type" class="control-label">公司类型:</form:label>
						<div class="controls">
						<%-- 	<form:input path="type" class="input-medium" />
							<i class="icon-zoom-in"
								style="cursor: pointer"
								link="<spring:url value="/companies/list/dialog.html" />"
								title="选择公司类型"></i> --%>
								<form:select path="type" class="input-medium" >
								<form:option value="I">内部</form:option>
								<form:option value="C">承运人</form:option>
								<form:option value="M">制造商</form:option>
								<form:option value="V">供应商</form:option>
								</form:select>
						</div>
					</div>
					<div class="control-group ">
						<form:label path="orgid" class="control-label">组织:</form:label>
						<div class="controls">
							<form:input path="orgid" class="input-medium" />
						</div>
					</div>
					<div class="control-group ">
						<form:label path="currencycode" class="control-label">货币:</form:label>
						<div class="controls">
							<form:input path="currencycode" class="input-medium" />
						</div>
					</div>
				</div>
			</div>



		</form:form>
	</div>
</div>

<%@ include file="../common/dialog/dlgCompanies.jsp" %>
