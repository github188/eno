<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!-- 
	AUTHOR: CHENPING
	Created Date: 2013-8-29 上午11:14:54
	LastModified Date:
	Description:
 -->
<spring:url value="/measure/${measurepointid}/specs/save" var="action"></spring:url>
<form:form modelAttribute="measureSpec" cssClass="form-horizontal" action="${action}"
	style="margin:0px;">
	<div id="detailcontent" style="display: none;">

		<div class="row-fluid">
			<div class=" alert alert-info">详细信息</div>
		</div>
		<div id="detailmsg"></div>
		<div class="row-fluid">
			<div class="span4">
				<div class="control-group ">
					<label for="displaysequence" class="control-label">序号：</label>
					<div class="controls">
						<form:input path="displaysequence" data-rule-required="true"  data-rule-number="true" class="input-small"/>
					</div>
				</div>
				<div class="control-group ">
					<form:label path="assetattrid" cssClass="control-label">* 属性：</form:label>
					<div class="controls">
						<form:input path="assetattrid" cssClass="input-small"
							title="必填字段属性不能为空" data-rule-required="true"
							data-placement="bottom" />
						<input type="hidden" id="checkAssetattridUrl"
							value="${pageContext.request.contextPath}/measure/${measurepointid}/specs/check" />
						<i class="icon-zoom-in showAssetattributeWindow"
							style="cursor: pointer"
							link="<spring:url value="/class/assetattribute/dialog.html?dlgtype=select" />"
							title="选择属性"></i> <input type="text"
							id="assetAttribute.description" class="input-medium" disabled />
							<form:hidden path="assetnum"/>
							<form:hidden path="metername"/>
					</div>
				</div>

				<div class="control-group ">
					<form:label path="measureunitid" class="control-label">计量单位：</form:label>
					<div class="controls">
						<form:input path="measureunitid" class="input-small" />
						<i class="icon-zoom-in"
							link="<spring:url value="/measureunit/list.html" />"
							style="cursor: pointer" title="选择计量单位" class="showMeasureUnit"></i>
					</div>

				</div>

			</div>

			<div class="span4">
				<div class="control-group ">
					<form:label path="section" class="control-label">部分：</form:label>
					<div class="controls">
						<form:input path="section" class="input-small" />
					</div>
				</div>
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
						<form:input path="numvalue" cssClass="input-small"
							data-content="字段值必须为数值" />
					</div>
				</div>
			</div>
			<div class="span4">
				<div class="control-group ">
					<form:label path="calcmethod" cssClass="control-label">计算方法：</form:label>
					<div class="controls">
						<form:input path="calcmethod" cssClass="input-small" />
					</div>
				</div>
				<div class="control-group ">
					<form:label path="frequnit" cssClass="control-label">频率单位：</form:label>
					<div class="controls">
						<div class="input-append">
							<form:select path="frequnit" cssClass="input-small">
								<form:option value=""></form:option>
								<form:option value="MUNITES">分</form:option>
								<form:option value="DAYS">天</form:option>
								<form:option value="WEEKS">周</form:option>
								<form:option value="MONTHS">月</form:option>
								<form:option value="YEARS">年</form:option>
							</form:select>
						</div>
					</div>
				</div>
				<div class="control-group">
					<form:label path="measurefreq" cssClass="control-label">更新频率</form:label>
					<div class="controls">
						<form:input path="measurefreq" cssClass="input-small" />
					</div>
				</div>
				<div class="control-group">
					<form:label path="refvalue" cssClass="control-label">参考值</form:label>
					<div class="controls">
						<form:input path="refvalue" cssClass="input-small" />
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="row-fluid">
		<div class="span6 pull-right"
			style="text-align: right; padding: 5px 15px 5px 0;">
			<a class="btn" id="btnAddDetail"><span><i
					class="icon-plus"></i> 新增</span></a>
			<button type="submit" class="btn btn-primary savebutton"
				style="display: none;">
				<i class="icon-ok-sign"></i> 保存
			</button>
		</div>
	</div>
</form:form>

<%@ include file="../common/dialog/dlgMeasureUnit.jsp"%>
<%@ include file="../common/dialog/dlgAssetAttribute.jsp"%>