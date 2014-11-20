<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!-- 
	AUTHOR: CHENPING
	Created Date: 2013-8-29 上午8:52:12
	LastModified Date:
	Description:测点技术规范
 -->

<c:if test="${not empty message}">
	<div class="alert alert-success">
		<button type="button" class="close" data-dismiss="alert">&times;</button>
		${message}
	</div>
</c:if>
<div class="box">
	<div class="box-content">
		<form:form modelAttribute="measurePoint" method="put"
			class="form-horizontal" style="margin-bottom:0px;">
			<div class="row-fluid">


				<div class="control-group">
					<form:label path="pointnum" class="control-label">测点：</form:label>
					<div class="controls">
						<form:hidden path="measurepointid"/>
						<form:input path="pointnum" class="input-small" readonly="true" />
						<form:input path="description" />
					</div>
				</div>
				<spring:bind path="metername">
					<c:set var="cssGroup"
						value="control-group ${status.error ? 'error' : '' }" />
					<div class="${cssGroup}">
						<form:label path="metername" class="control-label">* 计量器:</form:label>
						<div class="controls">
							<c:choose>
								<c:when test="${empty measurepointid}">
									<form:input path="metername" class="input-small"
										title="必填字段计量器不能为空" data-rule-required="true"
										data-placement="bottom" />
									<div class="btn-group">
										<button class="btn dropdown-toggle" data-toggle="dropdown">
											&gt;&gt;</button>
										<ul class="dropdown-menu">
											<li><a href="#" id="showMeterlist"
												link="<spring:url value="/meter/list/dialog" />">选择值</a></li>
										</ul>
									</div>
								</c:when>
								<c:otherwise>
									<form:input path="metername" class="input-small"
										readonly="true" />
								</c:otherwise>
							</c:choose>
							<input type="text" name="meter.description" readonly="readonly">
							<span class="help-inline">${status.errorMessage}</span>
						</div>
					</div>
				</spring:bind>
				<spring:bind path="classstructureid">
					<c:set var="cssGroup"
						value="control-group ${status.error ? 'error' : '' }" />
					<div class="${cssGroup}">
						<form:label path="classstructureid" class="control-label">* 分类:</form:label>
						<div class="controls">
							<c:choose>
								<c:when test="${empty measurepointid}">
									<form:input path="metername" class="input-small"
										title="必填字段分类不能为空" data-rule-required="true"
										data-placement="bottom" />
									<div class="btn-group">
										<button class="btn dropdown-toggle" data-toggle="dropdown">
											&gt;&gt;</button>
										<ul class="dropdown-menu">
											<li><a href="#" id="showClassStructurelist"
												link="<spring:url value="/meter/list/dialog" />">选择值</a></li>
										</ul>
									</div>
								</c:when>
								<c:otherwise>
									<form:input path="classstructureid" class="input-small"
										readonly="true" />
								</c:otherwise>
							</c:choose>
							<input type="text" name="classstructure.description" readonly="readonly">
							<span class="help-inline">${status.errorMessage}</span>
						</div>
					</div>
				</spring:bind>
			</div>
		</form:form>
	</div>
</div>

<div class="row-fluid">
	<div class="widget widget-table">
		<div class="widget-content">
			<table class="table table-bordered table-striped" id="measurespectable">
				<thead>
					<tr>
						<th></th>
						<th width="60">序号</th>
						<th>属性</th>
						<th>描述</th>
						<th>字母数字值</th>
						<th>数字值</th>
						<th>计量单位</th>
						<th>更新频率</th>
						<th>频率单位</th>
						<th>参考值</th>
						<th></th>
					</tr>
				</thead>
				<tbody></tbody>
				<tfoot>
				<tr>
					<td colspan="11" style="margin:0px;padding:0px;">
					<%@ include file="editMeasureSpecForm.jsp" %>
					</td>
				</tr>
				</tfoot>
			</table>
		</div>
	</div>
</div>

