<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!-- 
	AUTHOR: CHENPING
	Created Date: 2013-9-2 下午1:41:30
	LastModified Date:
	Description: 作业计划序列
 -->
<c:choose>
	<c:when test="${pm['pmid'] eq '' or pm['pmid'] eq '0'}">
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
<div id="detailmsg"></div>
<div class="box">
	<div class="box-content">
		<form:form modelAttribute="pm" method="${method}"
			class="form-horizontal">
			<%@ include file="includePM.jsp"%>
			<div class="row-fluid">
				<div class="span4">
					<div class="control-group">
						<form:label path="location" class="control-label">位置:</form:label>
						<div class="controls">
							<form:input path="location" class="input-small" />
							<div class="btn-group">
								<button class="btn dropdown-toggle" data-toggle="dropdown">
									&gt;&gt;</button>
								<ul class="dropdown-menu">
									<li><a href="#" id="showLocationList"
										link="<spring:url value="/locations/list/dialog" />">选择值</a></li>
								</ul>
							</div>
							<!-- /btn-group -->
							<input type="text" name="location.description"
								id="location.description" class="input-medium"
								readonly="readonly" />
						</div>
					</div>
				</div>
				<div class="span4">
					<div class="control-group">
						<form:label path="assetnum" class="control-label">资产:</form:label>
						<div class="controls">
							<form:input path="assetnum" class="input-small" />
							<div class="btn-group">
								<button class="btn dropdown-toggle" data-toggle="dropdown">
									&gt;&gt;</button>
								<ul class="dropdown-menu">
									<li><a href="#" id="showAssetList"
										link="<spring:url value="/asset/list/dialog" />">选择值</a></li>
								</ul>
							</div>
							<!-- /btn-group -->
							<input type="text" name="asset.description"
								id="asset.description" class="input-medium" readonly="readonly" />
						</div>
					</div>

				</div>
				<div class="span4">
					<div class="control-group">
						<form:label path="jpnum" class="control-label">作业计划:</form:label>
						<div class="controls">
							<form:input path="jpnum" class="input-small" />
							<div class="btn-group">
								<button class="btn dropdown-toggle" data-toggle="dropdown">
									&gt;&gt;</button>
								<ul class="dropdown-menu">
									<li><a href="#" id="showAssetList"
										link="<spring:url value="/jobplan/list/dialog" />">选择值</a></li>
								</ul>
							</div>
							<!-- /btn-group -->
							<input type="text" name="jobplan.description"
								id="jobplan.description" class="input-medium"
								readonly="readonly" />
						</div>
					</div>
				</div>
			</div>

		</form:form>
		<%@ include file="includeEditSequence.jsp" %>
	</div>
</div>
