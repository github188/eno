<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<div class="widget widget-table">
	<div class="widget-content">
		<table
			class="table table-hover table-nomargin table-bordered table-striped dataTable-columnfilter dataTable"
			id="sequencetable">
			<thead>
				<tr>
					<th width="5%"></th>
					<th width="20%">作业计划</th>
					<th>描述</th>
					<th width="15%">序号</th>
					<th width="5%"></th>
				</tr>
			</thead>
			<tbody>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="5" style="padding: 0px;"><spring:url
							value="/pm/${pmid}/sequence/save" var="action"></spring:url> <form:form
							modelAttribute="pmSequenceSet" action="${action}"
							cssClass="form-horizontal" style="margin:0px;">
							<div id="detailcontent" style="display: none;">
								<div class="row-fluid">
									<div class=" alert alert-info">详细信息</div>
								</div>
								<div id="detailmsg"></div>
								<div class="row-fluid">
									<div class="span6">
										<div class="control-group ">
											<form:label path="pmSequence.jpnum" cssClass="control-label">* 作业计划：</form:label>
											<div class="controls">
												<form:input path="pmSequence.jpnum" cssClass="input-small" data-rule-required="true" />
												<form:hidden path="pmSequence.pmnum" />
												<form:hidden path="pmSequence.orgid" />
												<form:hidden path="pmSequence.siteid" />
												<input type="hidden" id="checkJpNumUrl"
													value="${pageContext.request.contextPath}/pm/${pmid}/sequence/check.html" />
												<i class="icon-zoom-in showJobPlanList"
													style="cursor: pointer"
													link="<spring:url value="/jobplan/list/dialog.html" />"
													title="选择作业计划"></i>
												

											</div>
										</div>
									</div>
									<div class="span6">
										<div class="control-group ">
											<form:label path="pmSequence.interval" cssClass="control-label">* 序号：</form:label>
											<div class="controls">
												<form:input path="pmSequence.interval" cssClass="input-small" data-rule-required="true" data-rule-number="true"/>
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
</div>

<%@ include file="../common/dialog/dlgJobPlanList.jsp" %>