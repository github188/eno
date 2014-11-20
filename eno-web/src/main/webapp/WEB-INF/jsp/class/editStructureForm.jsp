<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!-- 
	AUTHOR: CHENPING
	Created Date: 2013-8-1 上午9:06:36
	LastModified Date:
	Description: 资产类别定义
 -->
<c:choose>
	<c:when test="${classStructure['classstructureuid'] eq ''}">
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

<form:form modelAttribute="classStructure" method="${method}"
	class="form-horizontal">
	<div class="box">
		<div class="box-content">
			<div class="row-fluid">
				<div class="span6">
					<spring:bind path="classificationid">
						<c:set var="cssGroup"
							value="control-group ${status.error ? 'error' : '' }" />
						<div class="${cssGroup}">
							<form:label path="classificationid" class="control-label">* 分类:</form:label>
							<div class="controls">
								<c:choose>
									<c:when test="${empty classstructureuid}">
										<form:input path="classificationid" class="input-medium"
											title="必填字段分类不能为空" data-rule-required="true"
											data-placement="bottom" />
									</c:when>
									<c:otherwise>
										<form:input path="classificationid" class="input-medium"
											readonly="true" />
											<form:hidden path="classstructureid"/>
											<form:hidden path="classstructureuid"/>
									</c:otherwise>
								</c:choose>
								<a class="showClassificationWindow" link="<spring:url value="/class/classification/dialog?section=classStructure"></spring:url>" ><i class="icon-zoom-in" style="cursor:pointer;"></i></a>
								<input type="text" id="classificationdesc" name="classificationdesc" value="${classification.description}" disabled="disabled" />
								<span class="help-inline">${status.errorMessage}</span>
							</div>
						</div>
					</spring:bind>
					<div class="control-group">
						
						<label for="hierarchypath" class="control-label">分类路径:</label>
						<div class="controls">
							<input type="text" name="hierarchypath" id="hierarchypath" class="input-medium" disabled="disabled" />
							<i style="padding-left: 16px;"></i>
							<form:input path="description" />
						</div>
					</div>
					<div class="control-group">
						<label for="parentclassificationid" class="control-label">父级分类:</label>
						<div class="controls">
							<!-- <input type="text" name="parentclassificationid" id="parentclassificationid" class="input-medium" disabled="disabled" /> -->
							<form:input path="parent" readOnly="true" />
							<div class="btn-group">
								<button class="btn dropdown-toggle" data-toggle="dropdown">
									<i class="icon-chevron-right"></i>
								</button>
								<ul class="dropdown-menu">
									<li><a href="#" id="showAssetList"
										link="<spring:url value="/class/list/dialog" />">选择值</a></li>
									<li><a href="#">清除父级分类</a></li>
								</ul>
							</div>
						</div>
					</div>
					<div class="control-group">
						<form:label path="genassetdesc" class="control-label">生成描述?</form:label>
						<div class="controls">
							<form:checkbox path="genassetdesc" />
						</div>
					</div>


				</div>
				<div class="span6">
					<div class="control-group">
						<form:label path="orgid" class="control-label">组织:</form:label>
						<div class="controls">
							<form:input path="orgid" class="input-medium" />

						</div>
					</div>
					<div class="control-group">
						<form:label path="siteid" class="control-label">地点:</form:label>
						<div class="controls">
							<form:input path="siteid" class="input-medium" />
						</div>
					</div>
					<div class="control-group">
						<form:label path="useclassindesc" class="control-label">使用分类?</form:label>
						<div class="controls">
							<form:checkbox path="useclassindesc" />
						</div>
					</div>


				</div>
			</div>
		</div>
	</div>
</form:form>
<jsp:include page="includeClassUseWith.jsp"></jsp:include>
<jsp:include page="includeChildrens.jsp"></jsp:include>
<jsp:include page="includeClassSpec.jsp"></jsp:include>

