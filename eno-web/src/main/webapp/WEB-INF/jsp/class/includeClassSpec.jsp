<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>

<!-- 规范列表 -->
<div class="widget widget-table">
	<div class="widget-content">
		<table class="table table-bordered table-striped" id="classspectable">
			<thead>
				<tr>
					<th width="20"></th>
					<th width="60" align="right">属性</th>
					<th>描述</th>
					<th width="150">部分</th>
					<th width="120">域</th>
					<th width="120">数据类型</th>
					<th width="120">计量单位</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="9" style="padding: 0px;"><spring:url
							value="/class/edit/${classstructureuid}/classspec/save" var="action"></spring:url>
						<form:form modelAttribute="classSpec" action="${action}"
							cssClass="form-horizontal" style="margin:0px;">
							<div id="classSpecFormContent" style="display: none;">

								<div class="row-fluid">
									<div class=" alert alert-info">详细信息</div>
								</div>
								<div id="detailmsg"></div>
								<div class="row-fluid">
									<div class="span4">
										<div class="control-group ">
											<form:label path="assetattrid" cssClass="control-label">* 属性：</form:label>
											<div class="controls">
												<form:input path="assetattrid" cssClass="input-small"
													title="必填字段不能为空" data-rule-required="true"
													data-placement="bottom" />
												<form:hidden path="assetattributeid" />
												<form:hidden path="classstructureid"
													value="${classStructure.classstructureid}" />
												<input type="hidden" id="checkAssetattridUrl"
													value="${pageContext.request.contextPath}/class/edit/${classStructure.classstructureuid}/classspec/check" />
												<i class="icon-zoom-in showAssetattributeWindow"
													style="cursor: pointer"
													link="<spring:url value="/class/assetattribute/dialog.html" />"
													title="选择属性"></i> <input type="text"
													id="assetAttribute.description" class="input-medium" disabled />
											</div>
										</div>
										<div class="control-group ">
											<label for="datatype" class="control-label">数据类型：</label>
											<div class="controls">
												<input type="text" name="datatype" class="input-small"
													disabled />
											</div>
										</div>
										<div class="control-group ">
											<label for="domainid" class="control-label">域：</label>
											<div class="controls">
												<input type="text" name="domainid" class="input-small"
													disabled />
											</div>
										</div>
										<div class="control-group ">
											<label for="tableattribute" class="control-label">表属性：</label>
											<div class="controls">
												<input type="text" name="tableattribute" class="input-small" />
											</div>
										</div>
										<div class="control-group ">
											<label for="lookupname" class="control-label">查找名称：</label>
											<div class="controls">
												<input type="text" name="lookupname" class="input-small" />
											</div>
										</div>
									</div>
									<div class="span4">
										<div class="control-group ">
											<form:label path="section" class="control-label">部分：</form:label>
											<div class="controls">
												<form:input path="section" class="input-small" />
											</div>
										</div>
										<div class="control-group ">
											<form:label path="measureunitid" class="control-label">计量单位：</form:label>
											<div class="controls">
												<form:input path="measureunitid" class="input-small" />
												<i class="icon-zoom-in showMeasureunitWindow"
													link="<spring:url value="/measureunit/list.html" />"
													style="cursor: pointer" title="选择计量单位"></i>
											</div>

										</div>
										<div class="control-group ">
											<form:label path="attrdescprefix" class="control-label">描述前缀：</form:label>
											<div class="controls">
												<form:input path="attrdescprefix" class="input-small" />
											</div>
										</div>
										<div class="control-group ">
											<form:label path="applydownhier" class="control-label">应用下行层次结构?</form:label>
											<div class="controls">
												<form:checkbox path="applydownhier" />
											</div>
										</div>
										<div class="control-group ">
											<form:label path="inheritedfrom" class="control-label">继承自：</form:label>
											<div class="controls">
												<form:input path="inheritedfrom" class="input-small" />
											</div>
										</div>

									</div>

									<div class="span4">
										<div class="control-group ">
											<form:label path="orgid" cssClass="control-label"> 组织：</form:label>
											<div class="controls">
												<form:input path="orgid" cssClass="input-small" />
											</div>
										</div>
										<div class="control-group ">
											<form:label path="siteid" cssClass="control-label">地点：</form:label>
											<div class="controls">
												<div class="input-append">
													<form:input path="siteid" cssClass="input-small" />
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>

							<div class="row-fluid">
								<div class="span6 pull-right"
									style="text-align: right; padding: 5px 15px 5px 0;">
									<a class="btn" id="btnAddClassSpec"><span><i
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

