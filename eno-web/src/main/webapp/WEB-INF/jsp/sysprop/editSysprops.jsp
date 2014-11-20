<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>


<fieldset>
	<legend>系统属性信息</legend>

	<div>
		<form:form modelAttribute="sysprop" class="form-horizontal"
			id="sysprop">
			<div class="control-group">
				<form:label path="propname" class="control-label">属性名称：</form:label>
				<div class="controls">
					<form:input path="propname" id="propname" />
				</div>

			</div>
			<div class="control-group">
				<form:label path="description" class="control-label">属性描述：</form:label>
				<div class="controls">
					<form:input path="description" />
				</div>

			</div>
			<div class="control-group">
				<form:label path="sysdefault" class="control-label">系统默认值：</form:label>
				<div class="controls">
					<form:input path="sysdefault" />
				</div>

			</div>
			<div class="control-group">
				<form:label path="propvalue" class="control-label">当前值：</form:label>
				<div class="controls">
					<form:input path="propvalue" />
				</div>

			</div>

			<div class="control-group">
				<form:label path="nullsallowed" class="control-label">是否允许为空：</form:label>
				<div class="controls">
					<label class="radio"> <form:radiobutton path="nullsallowed"
							value="1" label="是" />
					</label> <label class="radio"> <form:radiobutton
							path="nullsallowed" value="0" label="否" />
					</label>
				</div>

			</div>
			<div class="control-group">
				<form:label path="encrypted" class="control-label">是否加密：</form:label>
				<div class="controls">
					<label class="radio"> <form:radiobutton path="encrypted"
							value="1" label="是" />
					</label> <label class="radio"> <form:radiobutton path="encrypted"
							value="0" label="否" />
					</label>
				</div>

			</div>

			<div class="control-group">
				<form:label path="masked" class="control-label">是否被屏蔽：</form:label>

				<div class="controls">
					<label class="radio"> <form:radiobutton path="masked"
							value="1" label="是" /></label> <label class="radio"> <form:radiobutton
							path="masked" value="0" label="否" />
					</label>
				</div>


				<div class="control-group">
					<form:label path="securelevel" class="control-label">权限级别：</form:label>
					<div class="controls">
						<form:input path="securelevel" />
						<form:hidden path="changeby" />
					</div>
				</div>
			</div>

			<div class="control-group">
				<div class="controls">
					<button type="button"
						onclick="document.getElementById('sysprop').submit()">保存</button>

					<a class="btn"
						href="${pageContext.request.contextPath }/okcsys/sysprops">取消</a>
				</div>
			</div>
		</form:form>
	</div>

</fieldset>
