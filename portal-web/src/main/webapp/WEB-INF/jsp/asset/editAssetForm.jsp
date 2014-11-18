<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<c:choose>
	<c:when test="${isNew}">
		<c:set var="method" value="post" />
		<c:set var="editStatus" value="false" />
	</c:when>
	<c:otherwise>
		<c:set var="method" value="put" />
		<c:set var="editStatus" value="true" />
	</c:otherwise>
</c:choose>
<c:if test="${not empty message}">
	<div class="alert alert-success">
		<button type="button" class="close" data-dismiss="alert">&times;</button>
		${message}
	</div>
</c:if>
<form:form modelAttribute="asset" method="${method}"
	class="form-horizontal">
	<div class="box">
		<div class="box-content">
			<div class="row-fluid">
				<div class="span6">
					<spring:bind path="assetnum">
						<c:set var="cssGroup"
							value="control-group ${status.error ? 'error' : '' }" />
						<div class="${cssGroup}">
							<form:label path="assetnum" class="control-label">资产:</form:label>
							<div class="controls">
								<c:choose>
									<c:when test="${empty assetuid}">
										<spring:url value="/asset/new/check.html" var="checkUrl"></spring:url>
										<form:input path="assetnum" class="input-medium"
											title="必填字段资产编号不能为空" data-rule-required="true"
											data-placement="bottom" data-check-url="${checkUrl}" />
									</c:when>
									<c:otherwise>
										<form:input path="assetnum" class="input-medium"
											readonly="true" />
									</c:otherwise>
								</c:choose>
								<form:input path="description" />
								<span class="help-inline">${status.errorMessage}</span>
							</div>
						</div>
					</spring:bind>
					<div class="control-group">
						<form:label path="status" class="control-label">状态:</form:label>
						<div class="controls">
							<form:input path="status" class="input-medium" disabled="true" />
						</div>
					</div>
				</div>
				<div class="span6">
					<div class="control-group">
						<form:label path="siteid" class="control-label">地点：</form:label>
						<div class="controls">
							<form:input path="siteid" class="input-medium" readonly="true" />
						</div>
					</div>
					<div class="control-group">
						<form:label path="assettype" class="control-label">类型:</form:label>
						<div class="controls">
							<%-- <form:input path="assettype" class="input-medium" />
							<i class="icon-zoom-in" title="选择值" id="showAssetType"></i> --%>
							<form:select path="assettype" class="input-medium">
								<form:option value="FACILITIES">设备资产</form:option>
								<form:option value="IT">信息技术资产</form:option>
								<form:option value="PRODUCTION">房屋建筑资产</form:option>
								<form:option value="INSTRUMENT">仪器仪表工具</form:option>
							</form:select>
						</div>
					</div>					
					<div class="control-group ">
						<form:label path="specclass" class="control-label">专业：</form:label>
						<div class="controls">
							<form:input path="specclass" class="input-medium" />
							<i class="icon-zoom-in showDictionary" link="<spring:url value="/common/dictionary/aln?dictid=SUBSYS" />"></i>	
						</div>
					</div>
					
				</div>
			</div>
		</div>
	</div>

	<div class="row-fluid">
		<div class="widget">
			<div class="widget-header">
				<h3 class="icon ">详细信息</h3>
			</div>
			<div class="widget-content">
				<div class="row-fluid">
					<div class="span6">
						<div class="control-group ">
							<form:label path="parent" class="control-label">父资产:</form:label>
							<div class="controls">
								<form:input path="parent" class="input-medium" />
								<div class="btn-group">
									<button class="btn dropdown-toggle" data-toggle="dropdown">
										&gt;&gt;</button>
									<ul class="dropdown-menu">
										<li><a href="#" class="showAssetList" link="<spring:url value="/asset/list/dialog" />">选择值</a></li>
										<!-- <li><a href="#">打开明细数据</a></li>
										<li><a href="#">分类</a></li>
										<li><a href="#">属性</a></li> -->
									</ul>
								</div>
								<!-- /btn-group -->
							</div>
						</div>
						<div class="control-group">
							<form:label path="mainthierchy" class="control-label">维护层次结构:</form:label>
							<div class="controls">
								<form:checkbox path="mainthierchy" />
							</div>
						</div>
						<div class="control-group ">
							<form:label path="location" class="control-label">位置:</form:label>
							<div class="controls">
								<form:input path="location" class="input-medium" />
								<div class="btn-group">
									<button class="btn dropdown-toggle" data-toggle="dropdown">
										&gt;&gt;</button>
									<ul class="dropdown-menu">
										<li><a href="#" class="showLocationList" link="<spring:url value="/locations/list/dialog" />">选择值</a></li>
									</ul>
								</div>
								<!-- /btn-group -->
								<input type="text" name="location.description"
									id="location.description" disabled />
							</div>
						</div>
						<div class="control-group ">
							<form:label path="groupname" class="control-label">计量器组:</form:label>
							<div class="controls">
								<form:input path="groupname" class="input-medium" />
								<div class="btn-group">
									<button class="btn dropdown-toggle" data-toggle="dropdown">
										&gt;&gt;</button>
									<ul class="dropdown-menu">
										<li><a href="#"  class="showMetergroupList" link="<spring:url value="/metergroup/list/dialog" />">选择值</a></li>
										<li><a href="<spring:url value="/metergroup/list.html?redirectUrl="/>" id="redirectMeterGroup" >转到计量器组</a></li>
									</ul>
								</div>
								<!-- /btn-group -->
								<input type="text" name="metergroup.description"
									id="metergroup.description" disabled />
							</div>
						</div>
						<div class="control-group">
							<form:label path="usage" class="control-label">使用情况:</form:label>
							<div class="controls">
								<form:input path="usage" class="input-medium" />
								<i class="icon-zoom-in" id="showValue"></i>
							</div>
						</div>
					</div>
					<div class="span6">
						<div class="control-group">
							<form:label path="calnum" class="control-label">日历:</form:label>
							<div class="controls">
								<form:input path="calnum" class="input-medium" />
								<i class="icon-zoom-in" id="showCalendar"></i>
							</div>
						</div>
						<div class="control-group">
							<form:label path="shiftnum" class="control-label">班次:</form:label>
							<div class="controls">
								<form:input path="shiftnum" class="input-medium" />
								<i class="icon-zoom-in" id="showShift"></i>
							</div>
						</div>
						<div class="control-group">
							<form:label path="priority" class="control-label">优先级:</form:label>
							<div class="controls">
								<form:input path="priority" class="input-medium" />
							</div>
						</div>
						<div class="control-group">
							<form:label path="serialnum" class="control-label">序列#：</form:label>
							<div class="controls">
								<form:input path="serialnum" class="input-medium" />
							</div>
						</div>
						<div class="control-group">
							<form:label path="failurecode" class="control-label">故障类:</form:label>
							<div class="controls">
								<form:input path="failurecode" class="input-medium" />
								<div class="btn-group">
									<button class="btn dropdown-toggle" data-toggle="dropdown">
										&gt;&gt;</button>
									<ul class="dropdown-menu">
										<li><a href="#" class="showFailureList" link="<spring:url value="/failurecode/list/dialog" />">选择值</a></li>
										<li><a href="<spring:url value="/failurecode/list.html"></spring:url>">转到故障代码</a></li>
									</ul>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="row-fluid">
		<div class="span6">
			<div class="widget">
				<div class="widget-header">
					<h3 class="icon">采购信息</h3>
				</div>
				<div class="widget-content">

					<div class="control-group">
						<form:label path="vendor" class="control-label">代理商:</form:label>
						<div class="controls">
							<form:input path="vendor" class="input-medium" />
							<div class="btn-group">
								<button class="btn dropdown-toggle" data-toggle="dropdown">
									&gt;&gt;</button>
								<ul class="dropdown-menu">
									<li><a href="#" class="showCompaniesWindow" 
									link="<spring:url value="/companies/list/dialog?type=V" />">选择值</a></li>
									<li><a href="<spring:url value="/companies/list" />">转到公司</a></li>
								</ul>
							</div>
							<!-- /btn-group -->
							<input type="text" name="vendor.description"
								id="vendor.description" readonly="readonly" />
						</div>
					</div>
					<div class="control-group">
						<form:label path="manufacturer" class="control-label">制造商:</form:label>
						<div class="controls">
							<form:input path="manufacturer" class="input-medium" />
							<div class="btn-group">
								<button class="btn dropdown-toggle" data-toggle="dropdown">
									&gt;&gt;</button>
								<ul class="dropdown-menu">
									<li><a href="#" class="showCompaniesWindow" 
									link="<spring:url value="/companies/list/dialog?type=M" />">选择值</a></li>
									<li><a href="<spring:url value="/companies/list" />">转到公司</a></li>
								</ul>
							</div>
							<!-- /btn-group -->
							<input type="text" name="manufacturer.description"
								id="manufacturer.description" readonly="readonly"  />
						</div>
					</div>
					<div class="control-group ">
						<form:label path="installdate" cssClass="control-label">安装日期：</form:label>
						<div class="controls">
							<div id="installdatedatepicker" class="input-append date">
								<form:input path="installdate" cssClass="input-medium"
									data-format="yyyy-MM-dd hh:mm:ss" data-placement="bottom"
									title="字段值必须为日期" />
								<span class="add-on"> <i data-time-icon="icon-time"
									data-date-icon="icon-calendar"> </i>
								</span>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="span6">
			<div class="widget">
				<div class="widget-header">
					<h3 class="icon">停工时间</h3>
				</div>
				<div class="widget-content">

					<div class="control-group">
						<form:label path="isrunning" class="control-label">资产运行?</form:label>
						<div class="controls">
							<form:checkbox path="isrunning" />
						</div>
					</div>
					<div class="control-group">
						<form:label path="statusdate" class="control-label">上次变更日期：</form:label>
						<div class="controls">
							<form:input path="statusdate" class="input-medium"
								readonly="true" />
						</div>
					</div>
					<div class="control-group">
						<form:label path="changeby" class="control-label">变更人:</form:label>
						<div class="controls">
							<form:input path="changeby" class="input-medium" readonly="true" />
						</div>
					</div>
					<div class="control-group">
						<form:label path="changedate" class="control-label">变更日期日期</form:label>
						<div class="controls">
							<form:input path="changedate" class="input-medium"
								readonly="true" />
						</div>
					</div>
				</div>
			</div>

		</div>
	</div>

</form:form>


<%@ include file="../common/dialog/dlgAssetList.jsp" %>
<%@ include file="../common/dialog/dlgDictionary.jsp" %>
<%@ include file="../common/dialog/dlgCompanies.jsp" %>
<%@ include file="../common/dialog/dlgLocationList.jsp" %>
<%@ include file="../common/dialog/dlgMeterGroupList.jsp" %>
<%@ include file="../common/dialog/dlgFailureList.jsp" %>

