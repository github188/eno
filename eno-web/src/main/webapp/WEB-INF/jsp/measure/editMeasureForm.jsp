<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!-- 
	AUTHOR: CHENPING
	Created Date: 2013-8-22 上午8:40:32
	LastModified Date:
	Description: 编辑状态监控
 -->
<c:choose>
	<c:when test="${measurePoint['new']}">
		<c:set var="method" value="post" />
		<c:set var="editStatus" value="false" />
	</c:when>
	<c:otherwise>
		<c:set var="method" value="put" />
		<c:set var="editStatus" value="true" />
		<c:set var="readOnly" value="readOnly" />
	</c:otherwise>
</c:choose>
<c:if test="${not empty message}">
	<div class="alert alert-success">
		<button type="button" class="close" data-dismiss="alert">&times;</button>
		${message}
	</div>
</c:if>
<div class="box">
	<div class="box-content">
		<form:form modelAttribute="measurePoint" method="${method}"
			class="form-horizontal">
			<div class="row-fluid">
				<div class="span6">
				   <spring:bind path="pointnum">
					<div class="control-group">
						<form:label path="pointnum" class="control-label">* 测点:</form:label>
						<div class="controls">
							<form:hidden path="measurepointid" />
							<form:input path="pointnum" class="input-small"
								readOnly="${readOnly}" />
							<i style="padding-left: 34px;"></i>
							<form:input path="description" />
						</div>
					</div>
					</spring:bind>
					<spring:bind path="location">
						<c:set var="cssGroup"
							value="control-group ${status.error ? 'error' : '' }" />
						<div class="${cssGroup}">
							<form:label path="location" class="control-label">位置:</form:label>
							<div class="controls">
								<c:choose>
									<c:when test="${!empty location}">
										<form:input path="location" class="input-small"
											title="必填字段位置不能为空" data-rule-required="true"
											data-placement="bottom" />
										<div class="btn-group">
											<button class="btn dropdown-toggle" data-toggle="dropdown">
												&gt;&gt;</button>
											<ul class="dropdown-menu">
												<li><a href="#" class="showLocationList"
													link="<spring:url value="/locations/list/dialog" />">选择值</a></li>
											</ul>
										</div>
									</c:when>
									<c:otherwise>
										<form:input path="location" class="input-small"
											readonly="true" /><i style="padding-left: 34px;"></i>
									</c:otherwise>
								</c:choose>
								<input type="text" name="locations.description"
									readonly="readonly" /> <span class="help-inline">${status.errorMessage}</span>
							</div>
						</div>
					</spring:bind>
					<spring:bind path="assetnum">
						<c:set var="cssGroup"
							value="control-group ${status.error ? 'error' : '' }" />
						<div class="${cssGroup}">
							<form:label path="assetnum" class="control-label">资产:</form:label>
							<div class="controls">
								<c:choose>
									<c:when test="${empty assetnum}">
										<form:input path="assetnum" class="input-small"
											title="必填字段资产编号不能为空" data-rule-required="true" />
										<div class="btn-group">
											<button class="btn dropdown-toggle" data-toggle="dropdown">
												&gt;&gt;</button>
											<ul class="dropdown-menu">
												<li><a href="#" class="showAssetList"
													link="<spring:url value="/asset/list/dialog" />">选择值</a></li>
											</ul>
										</div>
									</c:when>
									<c:otherwise>
										<form:input path="assetnum" class="input-small"
											readonly="true" /><i style="padding-left: 34px;"></i>
									</c:otherwise>
								</c:choose>
								<input type="text" name="asset.description" readonly="readonly">
								<span class="help-inline">${status.errorMessage}</span>
							</div>
						</div>
					</spring:bind>
					<spring:bind path="metername">
						<c:set var="cssGroup"
							value="control-group ${status.error ? 'error' : '' }" />
						<div class="${cssGroup}">
							<form:label path="metername" class="control-label">* 计量器:</form:label>
							<div class="controls">
								<c:choose>
									<c:when test="${empty metername}">
										<form:input path="metername" class="input-small"
											title="必填字段计量器不能为空" data-rule-required="true" />
										<div class="btn-group">
											<button class="btn dropdown-toggle" data-toggle="dropdown">
												&gt;&gt;</button>
											<ul class="dropdown-menu">
												<li><a href="#" class="showMeterlist"
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
					<div class="control-group">
						<form:label path="isspec" class="control-label">技术规范读数?</form:label>
						<div class="controls">
							<form:checkbox path="isspec" />
						</div>
					</div>
					<div class="control-group" id="cssClassstructureid">
						<form:label path="classstructureid" class="control-label">分类：</form:label>
						<div class="controls">
							<form:input path="classstructureid" class="input-small" />
							<div class="btn-group">
								<button class="btn dropdown-toggle" data-toggle="dropdown">
									&gt;&gt;</button>
								<ul class="dropdown-menu">
									<li><a href="#" class="showClassStructureWindow"
										link="<spring:url value="/class/list/dialog" />">选择值</a></li>
								</ul>
							</div>
							<input type="text" name="classStructure.description" readonly="readonly">
							<span class="help-inline"></span>
						</div>
					</div>
				</div>
				<div class="span6">
					<div class="control-group">
						<form:label path="orgid" class="control-label">组织：</form:label>
						<div class="controls">
							<form:input path="orgid" class="input-medium" readonly="true" />
						</div>
					</div>
					<div class="control-group">
						<form:label path="siteid" class="control-label">地点：</form:label>
						<div class="controls">
							<form:input path="siteid" class="input-medium" readonly="true" />
						</div>
					</div>
					<div class="control-group">
						<label for="meter.metertype" class="control-label">计量器类型:</label>
						<div class="controls">
							<input type="text" name="meter.metertype" readonly="readonly">
						</div>
					</div>
					<div class="control-group">
						<label for="measureUnit.measureunitid" class="control-label">计量单位:</label>
						<div class="controls">
							<input type="text" name="measureUnit.measureunitid"
								readonly="readonly">
						</div>
					</div>
				</div>
			</div>

		</form:form>
	</div>
</div>
<%@ include file="listMeasurement.jsp" %>
<%@ include file="../common/dialog/dlgAssetList.jsp" %>
<%@ include file="../common/dialog/dlgLocationList.jsp" %>
<%@ include file="../common/dialog/dlgMeter.jsp" %>
<%@ include file="../common/dialog/dlgClassStructureList.jsp" %>

