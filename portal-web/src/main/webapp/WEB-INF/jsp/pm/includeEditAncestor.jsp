<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp" %>
<div class="widget widget-table">
	<div class="widget-content">
		<table
			class="table table-hover table-bordered table-striped"
			id="ancestortable">
			<thead>
				<tr>
					<th width="5%"></th>
					<th width="5%">序号</th>
					<th width="15%">PM</th>
					<th>描述</th>
					<th width="15%">资产</th>
					<th width="15%">位置</th>
					<th width="15%">状态</th>
					<th width="5%"></th>
				</tr>
			</thead>
			<tbody>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="8" style="padding: 0px;">
						<spring:url
							value="/pm/${pmid}/ancestor/save" var="action"></spring:url> <form:form
							modelAttribute="pmAncestorSet" action="${action}"
							cssClass="form-horizontal" style="margin:0px;">
							<div id="detailcontent" style="display: none;">
								<div class="row-fluid">
									<div class=" alert alert-info">详细信息</div>
								</div>
								<div id="detailmsg"></div>
								<div class="row-fluid">
									<div class="span6">
										<div class="control-group ">
											<form:label path="pm.wosequence" cssClass="control-label">序号：</form:label>
											<div class="controls">
												<form:input path="pm.wosequence" cssClass="input-small" data-rule-number="true"  />
											</div>
										</div>
										<div class="control-group ">
											<form:label path="pmAncestor.ancestor" cssClass="control-label">* PM：</form:label>
											<div class="controls">
												<form:input path="pmAncestor.ancestor" cssClass="input-small" data-rule-required="true"
													/>
												<i class="icon-zoom-in showPMWindow"
													style="cursor: pointer"
													link="<spring:url value="/pm/list/dialog.html" />"
													title="选择值"></i>
													<form:hidden path="pmAncestor.pmnum" />
											</div>
										</div>
										<div class="control-group ">
											<form:label path="pm.assetnum" cssClass="control-label">资产：</form:label>
											<div class="controls">
												<form:input path="pm.assetnum" cssClass="input-small" readOnly="true" />
												<form:input path="asset.description" cssClass="input-medium" readOnly="true"/>
											</div>
										</div>
										<div class="control-group ">
											<form:label path="pm.location" cssClass="control-label">位置：</form:label>
											<div class="controls">
												<form:input path="pm.location" cssClass="input-small" readOnly="true" />
												<form:input path="locations.description" cssClass="input-medium" readOnly="true"/>
											</div>
										</div>
									</div>
									<div class="span6">
										<div class="control-group ">
											<form:label path="pm.status" cssClass="control-label">状态：</form:label>
											<div class="controls">
												<form:input path="pm.status" cssClass="input-small" readOnly="true" />
												
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
						</form:form>
					
					</td>
				</tr>
			</tfoot>
		</table>
	</div>
</div>