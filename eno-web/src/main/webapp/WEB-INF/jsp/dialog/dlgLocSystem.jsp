<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<script src="<spring:url value="/resources/scripts/locations.js" />"></script>
<!-- 位置系统 -->
<div class="widget widget-table">
	<div class="widget-header">
		<span class="icon-list"></span>
		<h3 class="icon chart">管理系统</h3>
	</div>
	<div class="widget-content">
		<table class="table table-bordered table-striped">
			<thead>
				<tr>
					<th width="20"></th>
					<th>系统</th>
					<th>描述</th>
					<th>地点</th>
					<th width="50">主系统</th>
					<th width="50">网络</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${locSystemList.content}" var="row">
					<tr>
						<td class="showContent" id="${row.locsystemid}" class="center"><i
							class="icon-arrow-right"></i></td>
						<td>${row.systemid}</td>
						<td>${row.description}</td>
						<td class="center">${row.siteid}</td>
						<td class="center"><input type="checkbox" id="" /></td>
						<td class="center"><input type="checkbox" id="" /></td>
						<td class="center"><a href="#" class="dellocsystem"
							link="<spring:url value="/locations/locsystem/delete/${row.locsystemid}.html"></spring:url>">
								<img
								src="<spring:url value="/resources/images/btn_garbage.gif" />"
								alt="标记要删除的行" />
						</a></td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr id="detailcontent" style="display: none;">
					<td colspan="7" style="padding: 0px;"><spring:url
							value="/loctions/locsystems/save" var="action"></spring:url> <form:form
							modelAttribute="locSystem" action="${action}"
							cssClass="form-horizontal" style="margin:0px;">
							<div class="row-fluid">
								<div class=" alert alert-info">详细信息</div>
							</div>
							<div class="row-fluid">

								<div class="control-group ">
									<form:label path="systemid" cssClass="control-label">* 系统：</form:label>
									<div class="controls">
										<form:input path="systemid" cssClass="input-small required"
											rel="popover" data-content="请填写系统编码" />
										<form:input path="description" cssClass="input-medium" />
									</div>
								</div>
								<div class="control-group ">
									<form:label path="siteid" class="control-label">地点：</form:label>
									<div class="controls">
										<form:input path="siteid" cssClass="input-medium" />
									</div>
								</div>
								<div class="control-group ">
									<form:label path="network" class="control-label">网络?</form:label>
									<div class="controls">
										<form:checkbox path="network" />
									</div>
								</div>
								<div class="control-group ">
									<form:label path="primarysystem" class="control-label">主系统?</form:label>
									<div class="controls">
										<form:checkbox path="primarysystem" />
									</div>
								</div>
							</div>
						</form:form></td>
				</tr>
				<tr>
					<td colspan="7" style="text-align: right;"><a class="btn"
						id="btnAddDetail"><span><i class="icon-plus"></i> 新增</span></a>
						<a class="btn savebutton" id="btnSaveLocSystem" style="display:none;"><span><i
								class="icon-ok-sign"></i> 保存</span></a></td>
				</tr>
			</tfoot>
		</table>
	</div>
	<!-- .widget-content -->
</div>
<!-- .widget -->