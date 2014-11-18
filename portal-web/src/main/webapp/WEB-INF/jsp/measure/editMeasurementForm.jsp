<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!-- 
	AUTHOR: CHENPING
	Created Date: 2013-8-30 下午4:53:33
	LastModified Date:
	Description: 编辑测量信息
 -->
<spring:url value="/measure/${measurepointid}/measurements/save"
	var="action"></spring:url>
<form:form modelAttribute="measurement" cssClass="form-horizontal"
	action="${action}" style="margin:0px;">
	<div id="detailcontent" style="display: none;">
		<div class="row-fluid">
			<div class=" alert alert-info">详细信息</div>
		</div>
		<div id="detailmsg"></div>
		<div class="row-fluid">
			<div class="span4">
				<div class="control-group ">
					<label for="displaysequence" class="control-label">属性：</label>
					<div class="controls">
						<input type="text" name="assetattrid" id="assetattrid" class="input-medium" /> <input
							type="text" id="assetAttribute.description" class="input-medium"
							readonly="readonly" />
						<form:hidden path="measurespecid" />
						<form:hidden path="pointnum"/>
						<form:hidden path="assetnum"/>
						<form:hidden path="assetid"/>
						<form:hidden path="location"/>
						<form:hidden path="orgid"/>
						<form:hidden path="siteid"/>
						<form:hidden path="measurementid"/>
					</div>
				</div>

				<div class="control-group ">
					<form:label path="basemeasureunitid" class="control-label">计量单位：</form:label>
					<div class="controls">
						<form:input path="basemeasureunitid" class="input-medium" />
						<i class="icon-zoom-in"
							link="<spring:url value="/measureunit/list.html" />"
							style="cursor: pointer" title="选择计量单位" class="showMeasureUnit"></i>
					</div>
				</div>
				<div class="control-group ">
					<form:label path="measuresourcesys" class="control-label">数据源：</form:label>
					<div class="controls">
						<form:input path="measuresourcesys" class="input-medium" />
					</div>
				</div>
				<div class="control-group ">
					<form:label path="valuetag" class="control-label">* TagID：</form:label>
					<div class="controls">
						<form:input path="valuetag" class="input-medium" title="必填字段不能为空" data-rule-required="true"
										data-rule-number="true"	data-placement="top"  />
					</div>
				</div>
			</div>

			<div class="span4">
				<div class="control-group ">
					<form:label path="measurementvalue" class="control-label">测量值：</form:label>
					<div class="controls">
						<form:input path="measurementvalue" class="input-medium" />
					</div>
				</div>
				<div class="control-group ">
					<form:label path="inspector" cssClass="control-label">测量人：</form:label>
					<div class="controls">
						<form:input path="inspector" cssClass="input-medium" />
					</div>
				</div>
				<div class="control-group ">
					<form:label path="measuretime" cssClass="control-label">测量时间：</form:label>
					<div class="controls">
						<form:input path="measuretime" cssClass="input-medium" />
					</div>
				</div>
			</div>
			<div class="span4">
				<div class="control-group ">
					<form:label path="calcmethod" cssClass="control-label">计算方法：</form:label>
					<div class="controls">
						<form:input path="calcmethod" cssClass="input-medium" />
					</div>
				</div>

				<div class="control-group">
					<form:label path="ownersysid" cssClass="control-label">所有者：</form:label>
					<div class="controls">
						<form:input path="ownersysid" cssClass="input-medium" />
					</div>
				</div>
				<div class="control-group">
					<form:label path="sendersysid" cssClass="control-label">集成接口：</form:label>
					<div class="controls">
						<form:input path="sendersysid" cssClass="input-medium" />
					</div>
				</div>
				<div class="control-group">
					<label class="control-label"><button type="submit" class="btn btn-primary savebutton"
						style="display: none;">
						 更新
					</button></label>
					
				</div>
			</div>
		</div>
	</div>
		
</form:form>