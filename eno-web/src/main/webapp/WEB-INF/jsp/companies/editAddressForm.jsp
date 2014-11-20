<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!-- 
	AUTHOR: CHENPING
	Created Date: 2013-8-26 上午9:25:31
	LastModified Date:
	Description:
 -->
<c:if test="${not empty message}">
	<div class="alert alert-success">
		<button type="button" class="close" data-dismiss="alert">&times;</button>
		${message}
	</div>
</c:if>
<form:form modelAttribute="companies" method="post"
	class="form-horizontal">
	<div class="box">
		<div class="box-content">
			<div class="row-fluid">
				<div class="span6">
					<div class="control-group ">
						<form:label path="company" class="control-label">公司:</form:label>
						<div class="controls">
							<form:input path="company" class="input-small" readonly="true" />
							<form:input path="name" class="input-medium" />
						</div>
					</div>

				</div>
				<div class="span6">
					<div class="control-group ">
						<form:label path="orgid" class="control-label">组织:</form:label>
						<div class="controls">
							<form:input path="orgid" class="input-small" readonly="true" />
						</div>
					</div>

				</div>
			</div>

		</div>
	</div>
	<div class="row-fluid">
		<div class="widget">
			<div class="widget-header">
				<h3 class="icon ">概要信息</h3>
			</div>
			<div class="widget-content">
				<div class="row-fluid">
					<div class="control-group ">
						<form:label path="address5" class="control-label">国家:</form:label>
						<div class="controls">
							<form:input path="address5" class="input-medium" />
						</div>
					</div>
					<div class="control-group ">
						<form:label path="address3" class="control-label">省/直辖市/自治区:</form:label>
						<div class="controls">
							<form:input path="address3" class="input-medium" />
						</div>
					</div>
					<div class="control-group ">
						<form:label path="address2" class="control-label">城市:</form:label>
						<div class="controls">
							<form:input path="address2" class="input-medium" />
						</div>
					</div>
					<div class="control-group ">
						<form:label path="address1" class="control-label">县/区:</form:label>
						<div class="controls">
							<form:input path="address1" class="input-medium" />
						</div>
					</div>
					<div class="control-group ">
						<form:label path="address0" class="control-label">街道地址:</form:label>
						<div class="controls">
							<form:input path="address0" class="input-medium" />
						</div>
					</div>
					<div class="control-group ">
						<form:label path="address4" class="control-label">邮编:</form:label>
						<div class="controls">
							<form:input path="address4" class="input-medium" />
						</div>
					</div>

					<div class="control-group ">
						<form:label path="cellphone" class="control-label">电话:</form:label>
						<div class="controls">
							<form:input path="cellphone" class="input-medium" />
						</div>
					</div>
					<div class="control-group ">
						<form:label path="fax" class="control-label">公司传真:</form:label>
						<div class="controls">
							<form:input path="fax" class="input-medium" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</form:form>
