<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!-- 
	AUTHOR: CHENPING
	Created Date: 2013-9-2 上午11:23:29
	LastModified Date:
	Description:编辑预防性维护信息
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

<form:form commandName="pm" method="${method}"
	class="form-horizontal">
	<div class="box">
		<div class="box-content">
			<%@ include file="includePM.jsp"%>
		</div>
	</div>
	<div class="row-fluid">
		<div class="widget">
			<div class="widget-header">
				<h3 class="icon ">详细信息</h3>
			</div>
			<div class="widget-content">
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
										<li><a href="#" class="showLocationList"
											link="<spring:url value="/locations/list/dialog" />">选择值</a></li>
									</ul>
								</div>
								<!-- /btn-group -->
								<input type="text" name="location.description"
									id="location.description" class="input-medium"
									readonly="readonly" />
							</div>
						</div>
						<div class="control-group">
							<form:label path="assetnum" class="control-label">资产:</form:label>
							<div class="controls">
								<form:input path="assetnum" class="input-small" />
								<div class="btn-group">
									<button class="btn dropdown-toggle" data-toggle="dropdown">
										&gt;&gt;</button>
									<ul class="dropdown-menu">
										<li><a href="#" class="showAssetList"
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
							<form:label path="leadtime" class="control-label">提前时间(天):</form:label>
							<div class="controls">
								<form:input path="leadtime" class="input-small" />
							</div>
						</div>
						<div class="control-group">
							<label for="leadtimeactive" class="control-label">提前时间有效?</label>
							<div class="controls">
								<input type="checkbox" id="leadtimeactive" name="leadtimeactive"
									checked="checked" />
							</div>
						</div>
					</div>
					<div class="span4">
						<div class="control-group">
							<form:label path="pmcounter" class="control-label">计数器:</form:label>
							<div class="controls">
								<form:input path="pmcounter" class="input-small" value="1"
									readOnly="true" />
							</div>
						</div>
						<div class="control-group">
							<form:label path="jpseqinuse" class="control-label">使用作业计划序列?</form:label>
							<div class="controls">
								<form:checkbox path="jpseqinuse" disabled="true" />
							</div>
						</div>
						<div class="control-group">
							<form:label path="haschildren" class="control-label">有子级?</form:label>
							<div class="controls">
								<form:checkbox path="haschildren" disabled="true" />
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="row-fluid">
		<div class="span8">
			<div class="widget">
				<div class="widget-header">
					<h3 class="icon">工单信息</h3>
				</div>
				<div class="widget-content">
					<div class="row-fluid">
						<div class="span6">
							<div class="control-group">
								<form:label path="jpnum" class="control-label">作业计划:</form:label>
								<div class="controls">
									<form:input path="jpnum" class="input-small" />
									<div class="btn-group">
										<button class="btn dropdown-toggle" data-toggle="dropdown">
											&gt;&gt;</button>
										<ul class="dropdown-menu">
											<li><a href="#" class="showJobPlanList"
												link="<spring:url value="/jobplan/list/dialog" />">选择值</a></li>
										</ul>
									</div>
									<!-- /btn-group -->
								</div>
							</div>
							<div class="control-group">
								<form:label path="worktype" class="control-label">工单类型:</form:label>
								<div class="controls">
									<form:input path="worktype" class="input-small" />
									<i class="icon-zoom-in showWorktypeWindow"
										style="cursor: pointer;"
										link="/asset/class/classification/dialog?section=classStructure"></i>
								</div>
							</div>
							<div class="control-group">
								<form:label path="wostatus" class="control-label">工单状态:</form:label>
								<div class="controls">
									<form:input path="wostatus" class="input-small" />
									<i class="icon-zoom-in showWoStatusWindow"
										style="cursor: pointer;"
										link="/asset/class/classification/dialog?section=classStructure"></i>
								</div>
							</div>
							<div class="control-group">
								<form:label path="priority" class="control-label">优先级:</form:label>
								<div class="controls">
									<form:input path="priority" class="input-small" />
								</div>
							</div>
						</div>
						<div class="span6">
							<div class="control-group">
								<label for="jobPlan.descritpion" class="control-label">描述:</label>
								<div class="controls">
									<input type="text" class="input-small" name="jobPlan.descritpion" id="jobPlan.descritpion" readOnly="readonly" />
								</div>
							</div>
							<div class="control-group ">
								<form:label path="laststartdate" cssClass="control-label">上次开始日期：</form:label>
								<div class="controls">
									<div class="input-append date">
										<form:input path="laststartdate" cssClass="input-small"
											data-format="yyyy-MM-dd" data-placement="bottom" title="" readOnly="true" />
										<span class="add-on"> <i data-time-icon="icon-time"
											data-date-icon="icon-calendar"> </i>
										</span>
									</div>
								</div>
							</div>
							<div class="control-group ">
								<form:label path="lastcompdate" cssClass="control-label">上一完成日期：</form:label>
								<div class="controls">
									<div class="input-append date">
										<form:input path="lastcompdate" cssClass="input-small"
											data-format="yyyy-MM-dd" data-placement="bottom" title="" readOnly="true"  />
										<span class="add-on"> <i data-time-icon="icon-time"
											data-date-icon="icon-calendar"> </i>
										</span>
									</div>
								</div>
							</div>
							<div class="control-group">
								<form:label path="interruptible" class="control-label">可中断?</form:label>
								<div class="controls">
									<form:checkbox path="interruptible" />
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="span4">
			<div class="widget">
				<div class="widget-header">
					<h3 class="icon">责任</h3>
				</div>
				<div class="widget-content">

					<div class="control-group">
						<form:label path="supervisor" class="control-label">主管人:</form:label>
						<div class="controls">
							<form:input path="supervisor" class="input-small" />
							<div class="btn-group">
								<button class="btn dropdown-toggle" data-toggle="dropdown">
									&gt;&gt;</button>
								<ul class="dropdown-menu">
									<li><a href="#" id="showLocationList"
										link="<spring:url value="/locations/list/dialog" />">选择值</a></li>
								</ul>
							</div>
							<!-- /btn-group -->
						</div>
					</div>

					<div class="control-group">
						<form:label path="crewid" class="control-label">班组:</form:label>
						<div class="controls">
							<form:input path="crewid" class="input-small" />
							<div class="btn-group">
								<button class="btn dropdown-toggle" data-toggle="dropdown">
									&gt;&gt;</button>
								<ul class="dropdown-menu">
									<li><a href="#" id="showLocationList"
										link="<spring:url value="/locations/list/dialog" />">选择值</a></li>
								</ul>
							</div>
							<!-- /btn-group -->
						</div>
					</div>
					<div class="control-group">
						<form:label path="lead" class="control-label">负责人:</form:label>
						<div class="controls">
							<form:input path="lead" class="input-small" />
							<div class="btn-group">
								<button class="btn dropdown-toggle" data-toggle="dropdown">
									&gt;&gt;</button>
								<ul class="dropdown-menu">
									<li><a href="#" id="showLocationList"
										link="<spring:url value="/locations/list/dialog" />">选择值</a></li>
								</ul>
							</div>
							<!-- /btn-group -->
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="row-fluid">
		<div class="widget">
			<div class="widget-header">
				<h3 class="icon ">其它信息</h3>
			</div>
			<div class="widget-content">
				<div class="row-fluid">
					<div class="control-group">
						<form:label path="usefrequency" class="control-label span2">使用该 PM 来触发“PM 层次结构”?</form:label>
						<div class="controls">
							<form:checkbox path="usefrequency" />
						</div>
					</div>
					<div class="control-group">
						<form:label path="parentchgsstatus" class="control-label span2">子工单和任务将继承状态变更?</form:label>
						<div class="controls">
							<form:checkbox path="parentchgsstatus" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

</form:form>

<%@ include file="../common/dialog/dlgAssetList.jsp" %>
<%@ include file="../common/dialog/dlgLocationList.jsp" %>
<%@ include file="../common/dialog/dlgJobPlanList.jsp" %>