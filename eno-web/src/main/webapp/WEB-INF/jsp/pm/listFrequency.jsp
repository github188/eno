<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!-- 
	AUTHOR: CHENPING
	Created Date: 2013-9-2 下午3:51:16
	LastModified Date:
	Description:频率设置列表
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
				<div class="widget">
					<div class="widget-header">
						<h3 class="icon ">工单生成信息</h3>
					</div>
					<div class="widget-content">
						<div class="row-fluid">
							<div class="span6">
								<div class="control-group">
									<form:label path="usetargetdate" class="control-label span5">使用上次的工单开始信息计算下一到期频率?</form:label>
									<div class="controls">
										<form:checkbox path="interruptible" />
									</div>
								</div>
							</div>
							<div class="span6">
								<div class="control-group">
									<form:label path="pmassetwogen" class="control-label span4">到达计量器频率时生成工单?</form:label>
									<div class="controls">
										<form:checkbox path="pmassetwogen" />
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="tabbable">
				<!-- Only required for left/right tabs -->
				<ul class="nav nav-tabs">
					<li class="active"><a href="#tab1" data-toggle="tab">基于时间的频率</a></li>
					<li><a href="#tab2" data-toggle="tab">基于计量表的频率</a></li>
				</ul>
				<div class="tab-content">
					<div class="tab-pane active" id="tab1">
						<div class="row-fluid">
							<div class="span4">
								<div class="control-group">
									<form:label path="frequency" class="control-label">频率:</form:label>
									<div class="controls">
										<form:input path="frequency" class="input-small" data-rule-number="true" />
									</div>
								</div>
								<div class="control-group">
									<form:label path="frequnit" class="control-label">频率单位:</form:label>
									<div class="controls">
										<form:input path="frequnit" class="input-medium" />
										<i class="icon-zoom-in showSynonymDomainWindow"
										style="cursor: pointer;"
										link="/asset/class/classification/dialog?section=classStructure"></i>
									</div>
								</div>
							</div>
							<div class="span4">
								<div class="control-group">
									<form:label path="alertlead" class="control-label">预警期(天):</form:label>
									<div class="controls">
										<form:input path="alertlead" class="input-small" data-rule-number="true" />
									</div>
								</div>
								<div class="control-group">
									<form:label path="nextdate" class="control-label">估算的下一到期日:</form:label>
									<div class="controls">
										<div class="input-append date">
											<form:input path="nextdate" cssClass="input-small"
												data-format="yyyy-MM-dd" data-placement="bottom" data-rule-date="true" title="" />
											<span class="add-on"> <i data-time-icon="icon-time"
												data-date-icon="icon-calendar"> </i>
											</span>
										</div>
									</div>
								</div>
							</div>
							<div class="span4">
								<div class="control-group">
									<form:label path="extdate" class="control-label">延长日期:</form:label>
									<div class="controls">
										<div class="input-append date">
											<form:input path="extdate" cssClass="input-small"
												data-format="yyyy-MM-dd" data-rule-date="true" data-placement="bottom" title="" />
											<span class="add-on"> <i data-time-icon="icon-time"
												data-date-icon="icon-calendar"> </i>
											</span>
										</div>
									</div>
								</div>
								<div class="control-group">
									<form:label path="adjnextdue" class="control-label">调整下一截止日期:</form:label>
									<div class="controls">
										<form:checkbox path="adjnextdue"/>
									</div>
								</div>
							</div>

						</div>
					</div>
					</form:form>
					<div class="tab-pane" id="tab2">
						<%@ include file="includeEditMeterFrequency.jsp" %>
					</div>
				</div>
			</div>

		
	</div>
</div>

<%@ include file="../common/dialog/dlgMeter.jsp"%>

