<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp" %>
<!-- 
	AUTHOR: CHENPING
	Created Date: 2013-9-2 上午11:33:07
	LastModified Date:
	Description:
 -->
<div class="widget widget-table">
	<div class="widget-content">
		<table
			class="table table-hover table-nomargin table-bordered table-striped dataTable-columnfilter dataTable"
			id="seasonstable">
			<thead>
				<tr>
					<th width="5%"></th>
					<th width="30%">开始月份</th>
					<th width="15%">开始日</th>
					<th width="30%">结束月份</th>
					<th width="15%">结束日</th>
					<th width="5%"></th>
				</tr>
			</thead>
			<tbody>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="6" style="padding: 0px;">
					<spring:url
							value="/pm/${pmid}/seasons/save" var="action"></spring:url> <form:form
							modelAttribute="pmSeasons" action="${action}"
							cssClass="form-horizontal" style="margin:0px;">
							<div id="detailcontent" style="display: none;">
								<div class="row-fluid">
									<div class=" alert alert-info">详细信息</div>
								</div>
								<div id="detailmsg"></div>
								<div class="row-fluid">
									<div class="span6">
										<div class="control-group ">
											<form:label path="startmonth" cssClass="control-label">* 开始月份：</form:label>
											<div class="controls">
												<form:input path="startmonth" cssClass="input-small" data-rule-required="true"
													/>
												<i class="icon-zoom-in showDictWindow"
													style="cursor: pointer"
													link="<spring:url value="/jobplan/list/dialog.html" />"
													title="选择值"></i>
											</div>
										</div>
										<div class="control-group ">
											<form:label path="startday" cssClass="control-label">* 开始日：</form:label>
											<div class="controls">
												<form:input path="startday" cssClass="input-small" data-rule-required="true" data-rule-number="true"  />
											</div>
										</div>
									</div>
									<div class="span6">
										<div class="control-group ">
											<form:label path="endmonth" cssClass="control-label">* 结束月份：</form:label>
											<div class="controls">
												<form:input path="endmonth" cssClass="input-small" data-rule-required="true" />
												<i class="icon-zoom-in showDictWindow"
													style="cursor: pointer"
													link="<spring:url value="/jobplan/list/dialog.html" />"
													title="选择值"></i>
											</div>
										</div>
										<div class="control-group ">
											<form:label path="endday" cssClass="control-label">* 结束日：</form:label>
											<div class="controls">
												<form:input path="endday"  cssClass="input-small" data-rule-required="true" data-rule-number="true"  />
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