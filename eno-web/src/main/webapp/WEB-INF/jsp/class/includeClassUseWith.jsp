<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>

<!-- 分类结构使用的对象 -->
<div class="widget widget-table">
	<div class="widget-header">
		<span class="icon-list"></span>
		<h3 class="icon chart">用于</h3>
	</div>
	<div class="widget-content">
		<table class="table table-bordered table-striped">
			<thead>
				<tr>
					<th width="20"></th>
					<th width="60" align="right">用于对象</th>
					<th>描述</th>
					<th width="150">顶级</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${assetSpecSets}" var="row">
					<tr>
						<td class="showContent" id="${row.assetSpec.assetspecid}"
							class="center"><i class="icon-arrow-right"></i></td>
						<td>${row.assetSpec.assetattrid}</td>
						<td>${row.assetAttribute.description}</td>
						<td>${row.assetAttribute.datatype}</td>
						<td class="center"><a href="#" class="dellocsystem"
							link="<spring:url value="/asset/${assetuid}/specs/delete/${row.assetSpec.assetspecid}.html"></spring:url>">
								<img
								src="<spring:url value="/resources/images/btn_garbage.gif" />"
								alt="标记要删除的行" />
						</a></td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="9" style="padding: 0px;"><spring:url
							value="/class/${classstructureuid}/classusewith/save"
							var="action"></spring:url> <form:form
							modelAttribute="classUseWith" action="${action}"
							cssClass="form-horizontal" style="margin:0px;">
							<div id="classUseWithFormContent" style="display: none;">

								<div class="row-fluid">
									<div class=" alert alert-info">详细信息</div>
								</div>
								<div id="detailmsg"></div>
								<div class="row-fluid">

									<div class="control-group ">
										<form:label path="objectname" cssClass="control-label">* 用于对象：</form:label>
										<div class="controls">
											<form:input path="objectname" cssClass="input-small"
												title="必填字段对象名称不能为空" data-rule-required="true"
												data-placement="bottom" />
											<form:hidden path="classstructureid"
												value="${classStructure.classstructureid}" />
											<input type="hidden" id="checkAssetattridUrl"
												value="${pageContext.request.contextPath}/asset/${classStructure.classstructureid}/classusewith/check" />
											<i class="icon-zoom-in" id="showAssetattrlist"
												style="cursor: pointer"
												link="<spring:url value="/meter/list/dialog/1.html" />"
												title="选择对象"></i>
											<form:input path="description" class="input-medium" />
										</div>
									</div>
								</div>
							</div>

							<div class="row-fluid">
								<div class="span6 pull-right"
									style="text-align: right; padding: 5px 15px 5px 0;">
									<a class="btn" id="btnAddClassUseWith"><span><i
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