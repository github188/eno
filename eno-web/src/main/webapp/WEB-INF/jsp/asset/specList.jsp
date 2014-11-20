<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<c:choose>
	<c:when test="${asset['assetuid'] eq ''}">
		<c:set var="method" value="post" />
	</c:when>
	<c:otherwise>
		<c:set var="method" value="put" />
	</c:otherwise>
</c:choose>
<form:form modelAttribute="asset" cssClass="form-horizontal">
	<div class="box">
		<div class="box-content">
			<div class="control-group">
				<form:label path="assetnum" class="control-label">资产:</form:label>
				<div class="controls">
					<form:input path="assetnum" class="input-medium" readonly="true" />
					<form:input path="description" />
					<form:hidden path="assetuid" />
				</div>
			</div>
			<div class="control-group ">
				<form:label path="classstructureid" class="control-label">分类:</form:label>
				<div class="controls">
					<form:input path="classstructureid" class="input-medium" />
					<div class="btn-group">
						<button class="btn dropdown-toggle" data-toggle="dropdown">
							&gt;&gt;</button>
						<ul class="dropdown-menu">
							<li><a href="#" class="showClassStructureWindow" link="<spring:url value="/class/list/dialog.html?domainid=assetspec$value=${asset['assetuid']}" />">选择值</a></li>
							<li><a href="<spring:url value="/class/list.html" />">转到 分类</a></li>
						</ul>  
					</div>
					<!-- /btn-group -->
					<input type="text" name="classstructure.description"
						id="classstructure.description" disabled />

				</div>
			</div>
		</div>
	</div>
</form:form>


<!-- 规范列表 -->
<div class="widget widget-table">
	<div class="widget-content">
		<table class="table table-bordered table-striped" id="assetspectable">
			<thead>
				<tr>
					<th width="20"></th>
					<th width="60" align="right">属性</th>
					<th>描述</th>
					<th width="150">数据类型</th>
					<th width="120">字母数字值</th>
					<th width="120">数字值</th>
					<th width="120">计量单位</th>
					<th width="120">表值</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="9" style="padding: 0px;"><spring:url
							value="/asset/${assetuid}/specs/save" var="action"></spring:url>
						<form:form modelAttribute="assetSpec" action="${action}"
							cssClass="form-horizontal" style="margin:0px;">
							<div id="detailcontent" style="display: none;">

								<div class="row-fluid">
									<div class=" alert alert-info">详细信息</div>
								</div>
								<div id="detailmsg"></div>
								<div class="row-fluid">
									<div class="span6">


										<div class="control-group ">
											<form:label path="assetattrid" cssClass="control-label">* 属性：</form:label>
											<div class="controls">
												<form:input path="assetattrid" cssClass="input-small"
													title="必填字段计量器不能为空" data-rule-required="true"
													data-placement="bottom" />
												<form:hidden path="assetnum" value="${asset.assetnum}" />
												<input type="hidden" id="checkAssetattridUrl"
													value="${pageContext.request.contextPath}/asset/${asset.assetuid}/specs/check" />
												<i class="icon-zoom-in showAssetattributeWindow"
													style="cursor: pointer"
													link="<spring:url value="/class/assetattribute/dialog.html" />"
													title="选择属性"></i> <input type="text"
													id="assetAttribute.description" class="input-medium"
													disabled />
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
											<form:label path="measureunitid" class="control-label">计量单位：</form:label>
											<div class="controls">
												<form:input path="measureunitid" class="input-small" />
												<i class="icon-zoom-in showMeasureunitWindow"
													link="<spring:url value="/measureunit/list.html" />"
													style="cursor: pointer" title="选择计量单位"></i>
											</div>

										</div>
										<div class="control-group ">
											<form:label path="section" class="control-label">部分：</form:label>
											<div class="controls">
												<form:input path="section" class="input-small" />
											</div>
										</div>
									</div>

									<div class="span6">
										<div class="control-group ">
											<form:label path="alnvalue" cssClass="control-label">字母数字值：</form:label>
											<div class="controls">
												<form:input path="alnvalue" cssClass="input-small number"
													data-content="字段值必须为数值" />
											</div>
										</div>
										<div class="control-group ">
											<form:label path="numvalue" cssClass="control-label">数字值：</form:label>
											<div class="controls">
												<div class="input-append date">
													<form:input path="numvalue" cssClass="input-small"
														data-content="字段值必须为数值" />
												</div>
											</div>
										</div>
										<div class="control-group ">
											<form:label path="tablevalue" cssClass="control-label">表值：</form:label>
											<div class="controls">
												<form:input path="tablevalue" cssClass="input-small" />
											</div>
										</div>

									</div>
								</div>
							</div>

							<div class="row-fluid">
								<div class="span6 pull-right"
									style="text-align: right; padding: 5px 15px 5px 0;">
									<a class="btn" id="btnAddDetail"><span> 新增</span></a>
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
<!-- .widget -->

<%@ include file="../common/dialog/dlgMeasureUnit.jsp"%>
<%@ include file="../common/dialog/dlgAssetAttribute.jsp"%>
<%@ include file="../common/dialog/dlgClassStructureList.jsp"%>
