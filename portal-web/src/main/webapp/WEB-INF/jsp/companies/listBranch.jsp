<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!-- 
	AUTHOR: CHENPING
	Created Date: 2013-8-26 上午9:27:36
	LastModified Date:
	Description:分支机构
 -->
<form:form modelAttribute="companies" method="post"
	class="form-horizontal">
	<div class="box">
		<div class="box-content">
			<div class="row-fluid">
				<div class="span6">
					<div class="control-group ">
						<form:label path="company" class="control-label">公司:</form:label>
						<div class="controls">
							<form:input path="company" class="input-small" readonly="true" />
							<form:input path="name" class="input-medium" />
							<form:hidden path="companiesid"/>
							
						</div>
					</div>

				</div>
				<div class="span6">
					<div class="control-group ">
						<form:label path="orgid" class="control-label">组织:</form:label>
						<div class="controls">
							<form:input path="orgid" class="input-small" readonly="true" />
						</div>
					</div>

				</div>
			</div>

		</div>
	</div>
</form:form>
<div class="widget widget-table">
	<div class="widget-content">
		<table
			class="table table-hover table-nomargin table-bordered table-striped dataTable-columnfilter dataTable"
			id="branchtable">
			<thead>
				<tr>
					<th></th>
					<th>公司</th>
					<th>描述</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="9" style="padding: 0px;"><spring:url
							value="/companies/${companiesid}/branch/save" var="action"></spring:url>
						<form:form modelAttribute="childCompany" action="${action}"
							cssClass="form-horizontal" style="margin:0px;">
							<div id="detailcontent" style="display: none;">
								<div class="row-fluid">
									<div class=" alert alert-info">详细信息</div>
								</div>
								<div id="detailmsg"></div>
								<div class="row-fluid">
									<div class="span6">
										<div class="control-group ">
											<form:label path="company" cssClass="control-label">* 公司：</form:label>
											<div class="controls">
												<form:input path="company" cssClass="input-small"
													title="必填字段不能为空" data-rule-required="true"
													data-placement="bottom" />
													<form:hidden path="companiesid"/>
												<form:hidden path="parentcompany"
													/>
												<input type="hidden" id="checkCompanyUrl"
													value="${pageContext.request.contextPath}/companies/${companies.companiesid}/branch/check.html" />
												<i class="icon-zoom-in showCompaniesWindow"
													style="cursor: pointer"
													link="<spring:url value="/companies/list/dialog.html" />"
													title="选择公司"></i>
												<form:input path="name" class="input-medium" />

											</div>
										</div>
									</div>
								</div>
							</div>

							<div class="row-fluid">
								<div class="span6 pull-right"
									style="text-align: right; padding: 5px 15px 5px 0;">
									<a class="btn" id="btnAddDetail"><span><i
											class="icon-plus"></i> 新增</span></a>
									<button type="submit" class="btn btn-primary savebutton"
										style="display: none;">
										<i class="icon-ok-sign"></i> 保存
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
<!-- .widget -->
<%@ include file="../common/dialog/dlgCompanies.jsp" %>