<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!-- 
	AUTHOR: CHENPING
	Created Date: 2013-9-2 上午11:27:26
	LastModified Date:
	Description: 季节性日期
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
<form:errors path="targstarttime" cssClass="alert alert-error" element="div"></form:errors>
<form:errors  cssClass="alert alert-error" element="div" /> 
<div id="detailmsg"></div>
<div class="box">
	<div class="box-content">
		<form:form modelAttribute="pm" method="${method}"
			class="form-horizontal">
			<%@ include file="includePM.jsp"%>
			<div class="row-fluid">
				<div>
					<div class="widget-header">
						<h3 class="icon ">有效日期</h3>
					</div>
					<div class="widget-content">
						<div class="row-fluid">
							<div class="span1">
								<label class="checkbox"> <form:checkbox path="sunday" />
									星期日?
								</label>
							</div>
							<div class="span1">
								<label class="checkbox"> <form:checkbox path="monday" />
									星期一?
								</label>
							</div>
							<div class="span1">
								<label class="checkbox"> <form:checkbox path="tuesday" />
									星期二?
								</label>
							</div>
							<div class="span1">
								<label class="checkbox"> <form:checkbox path="wednesday" />
									星期三?
								</label>								
							</div>
							<div class="span1">
								<label class="checkbox"> <form:checkbox path="thursday" />
									星期四?
								</label>
							</div>
							<div class="span1">
								<label class="checkbox"> <form:checkbox path="friday" />
									星期五?
								</label>
							</div>
							<div class="span1">
								<label class="checkbox"> <form:checkbox path="saturday" />
									星期六?
								</label>
							</div>
						</div>
						<div class="row-fluid" style="z-index: 9999">
							<div class="control-group">
								<div class="span6">
									<form:label path="targstarttime" class="control-label">目标开始时间?</form:label>
									<div class="controls">
										<div class="input-append bootstrap-timepicker">
											<form:input path="targstarttime" class="input-small" />
											<span class="add-on"> <i class="icon-time"></i>
											</span>
										</div>

									</div>
								</div>
								<div class="span6">
									<form:label path="schedearly" class="control-label">频率冲突时提前调度?</form:label>
									<div class="controls">
										<form:checkbox path="schedearly" />
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</form:form>
	</div>
</div>
<%@ include file="includeEditSeasons.jsp"%>