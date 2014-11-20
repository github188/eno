<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<div class="widget widget-table">
	<div class="widget-content">
		<table
			class="table table-hover table-nomargin table-bordered table-striped dataTable-columnfilter dataTable"
			id="pmmetertable">
			<thead>
				<tr>
					<th width="60"></th>
					<th width="100">计量器</th>
					<th>描述</th>
					<th width="120">频率</th>
					<th width="120">提前生成工单</th>
					<th width="120">预警期</th>
					<th width="60"></th>
				</tr>
			</thead>
			<tbody>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="7" style="padding: 0px;"><spring:url
							value="/pm/${pmid}/frequency/save" var="action"></spring:url> 
							<form:form modelAttribute="pmMeterSet" action="${action}"
							cssClass="form-horizontal" style="margin:0px;">
							<div id="detailcontent" style="display: none;">
								<div class="row-fluid">
									<div class=" alert alert-info">详细信息</div>
								</div>
								<div id="detailmsg"></div>
								<div class="row-fluid">
									<div class="span6">
										<div class="control-group ">
											<form:label path="pmMeter.metername" cssClass="control-label">* 计量器：</form:label>
											<div class="controls">
												<form:input path="pmMeter.metername" cssClass="input-small"
													data-rule-required="true" />
												<form:hidden path="pmMeter.pmnum" />
												<form:hidden path="pmMeter.orgid" />
												<form:hidden path="pmMeter.siteid" />
												<input type="hidden" id="checkJpNumUrl"
													value="${pageContext.request.contextPath}/pm/${pmid}/sequence/check.html" />
												<i class="icon-zoom-in showMeterlist"
													style="cursor: pointer"
													link="<spring:url value="/meter/list/dialog.html" />"
													title="选择值"></i>


											</div>
										</div>

										<div class="control-group ">
											<form:label path="pmMeter.frequency" cssClass="control-label">频率：</form:label>
											<div class="controls">
												<form:input path="pmMeter.frequency" cssClass="input-small"
													data-rule-required="true" data-rule-number="true" />
											</div>
										</div>

									</div>
									<div class="span6">

										<div class="control-group ">
											<form:label path="pmMeter.alertlead" cssClass="control-label">预警期：</form:label>
											<div class="controls">
												<form:input path="pmMeter.alertlead" cssClass="input-small"
													 data-rule-number="true" />
											</div>
										</div>

										<div class="control-group ">
											<form:label path="pmMeter.tolerance" cssClass="control-label">生成工单：</form:label>
											<div class="controls">
												<form:input path="pmMeter.tolerance" cssClass="input-small" />
											</div>
										</div>
									</div>
								</div>

								<div class="row-fluid">
									<div class="span6">
										<div class=" alert alert-info">上一工单信息</div>
										<div class="control-group ">
											<form:label path="pmMeter.lastpmwogenread" cssClass="control-label">记量器读数：</form:label>
											<div class="controls">
												<form:input path="pmMeter.lastpmwogenread" cssClass="input-small"
													data-rule-required="true" data-rule-number="true"
													readOnly="true" />
											</div>
										</div>

										<div class="control-group ">
											<form:label path="pmMeter.lastpmwogenreaddt" cssClass="control-label">记量器读数日期：</form:label>
											<div class="controls">
												<form:input path="pmMeter.lastpmwogenreaddt" cssClass="input-medium"
													data-format="yyyy-MM-dd HH:mm:ss" readOnly="true" />
											</div>
										</div>

									</div>
									<div class="span6">
										<div class=" alert alert-info">下一工单信息</div>
										<div class="control-group ">
											<form:label path="pmMeter.readingatnextwo" cssClass="control-label">下次记量器读数：</form:label>
											<div class="controls">
												<form:input path="pmMeter.readingatnextwo" cssClass="input-small" readOnly="true" />
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