<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!-- 
	AUTHOR: CHENPING
	Created Date: 2013-7-15 上午9:53:53
	LastModified Date:
	Description: 编辑测量仪表组信息
 -->
<c:choose>
	<c:when test="${meterGroup['metergroupid'] eq ''}">
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


<div class="row-fluid">
	<div class="box">
		<div class="box-content">
			<form:form modelAttribute="meterGroup" method="${method}"
				class="form-horizontal">
				<c:set var="cssGroup"
					value="control-group ${status.error ? 'error' : '' }" />
				<div class="${cssGroup}">
					<form:label path="groupname" class="control-label">组名称</form:label>
					<div class="controls">
						<form:input path="groupname" class="input-small"
							readonly="${editStatus}" />
						<form:input path="description" placeholder="描述" />
						<span class="help-inline">${status.errorMessage}</span>
					</div>
				</div>
			</form:form>
		</div>
	</div>
</div>

<div class="row-fluid">
	<div class="widget widget-table">
		<div class="widget-header">
			<span class="icon-list"></span>
			<h3 class="icon chart">成组的计量器</h3>
		</div>
		<div class="widget-content">
			<table class="table table-bordered table-striped">
				<thead>
					<tr>
						<th></th>
						<th>序号</th>
						<th>计量器</th>
						<th>描述</th>
						<th>计量器类型</th>
						<th>计量单位</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${meterInGroups}" var="row">
						<tr>
							<td class="detail" width="50" class="center"><i
								class="icon-arrow-right"></i></td>
							<td>${row.sequence}</td>
							<td>${row.metername}</td>
							<td class="center">1.5</td>
							<td class="center">1.5</td>
							<td class="center">1.5</td>
							<td class="center"><a href="#" class="deldetail"
								link="<spring:url value="/meteringroup/delete/${row.meteringroupid}.html"></spring:url>">删除</a></td>
						</tr>
					</c:forEach>
				</tbody>
				<tfoot>
					<tr id="detail" style="display: none;">
						<td colspan="7"><spring:url
								value="/metergroup/meteringroup/save" var="action"></spring:url>
							<form:form modelAttribute="meterInGroup" action="${action}"
								cssClass="form-horizontal">
								<input type="hidden" id="groupname" name="groupname"
									value="${meterGroup.groupname}" />
								<div class="row-fluid">
									<div class="alert alert-info">详细信息</div>
								</div>
								<div class="row-fluid">
									<div class="span6">
										<div class="control-group ">
											<form:label path="sequence" cssClass="control-label">序号：</form:label>
											<div class="controls">
												<form:input path="sequence" cssClass="input-mini number"
													rel="popover" data-content="必须为数字" />
												<span class="help-inline"></span>
											</div>
										</div>
										<div class="control-group ">
											<form:label path="metername" cssClass="control-label">* 记量器：</form:label>
											<div class="controls">
												<form:input path="metername"
													cssClass="input-medium required" rel="popover"
													data-content="请选择或填写记量器" />
													<div class="btn-group">
													<button class="btn dropdown-toggle"
														data-toggle="dropdown">>></button>
													<ul class="dropdown-menu">
														<li><a href="#" class="showMeterlist"
															link="<spring:url value="/meter/list/dialog" />">选择值</a></li>
														<li><a
															href="<spring:url value="/meter/list.html"></spring:url>"
															id="redirectMeterList">转到计量器</a></li>
													</ul>
													</div>
													<input type="text"
													id="meter_description" class="input-medium"  name="meter_description" /> <span
													class="help-inline"></span>
											</div>
										</div>
										<div class="control-group ">
											<label for="metertype" class="control-label">记量器类型：</label>
											<div class="controls">
												<input type="text" id="metertype" name="metertype"
													class="input-medium" /> <span class="help-inline"></span>
											</div>
										</div>
										<div class="control-group ">
											<form:label path="rollover" cssClass="control-label">记量器回滚：</form:label>
											<div class="controls">
												<form:input path="rollover" cssClass="input-medium" />
												<span class="help-inline"></span>
											</div>
										</div>

									</div>
									<div class="span6">
										<div class="control-group ">
											<form:label path="avgcalcmethod" cssClass="control-label">* 平均计算方法：</form:label>
											<div class="controls">
												<form:input path="avgcalcmethod"
													cssClass="input-medium required" rel="popover"
													data-content="请选择平均计算方法" />
												<i class="icon-zoom-in"></i>
											</div>
										</div>
										<div class="control-group ">
											<form:label path="sequence" cssClass="control-label">计量单位：</form:label>
											<div class="controls">
												<form:input path="sequence" cssClass="input-medium" />

											</div>
										</div>
										<div class="control-group ">
											<form:label path="slidingwindowsize" cssClass="control-label">可调整窗口大小：</form:label>
											<div class="controls">
												<form:input path="slidingwindowsize" cssClass="input-mini" />

											</div>
										</div>
										<div class="control-group ">
											<form:label path="staticaverage" cssClass="control-label">静态平均值：</form:label>
											<div class="controls">
												<form:input path="staticaverage" cssClass="input-medium" />

											</div>
										</div>
										<div class="control-group ">
											<div class="controls">
												<button type="submit" class="btn btn-primary">保存明细</button>
											</div>
										</div>


									</div>

								</div>
							</form:form></td>
					</tr>
					<tr>
						<td colspan="7" style="text-align:right;"><a class="btn" id="btnAddMeter"><span>新增</span></a></td>
					</tr>
				</tfoot>
			</table>
		</div>
		<!-- .widget-content -->
	</div>
	<!-- .widget -->
</div>
<spring:url value="/metergroup/delete/${meterGroup.metergroupid}.html"
	var="deleteUrl"></spring:url>
<spring:url value="/metergroup/meteringroup/validate.html"
	var="validate"></spring:url>
<script>
	$(function() {
		//提交保存
		$("#submit").click(function() {
			$("#meterGroup").submit();
		});

		//删除数据
		$("#linkDelGroup").click(function() {
			$.messager.confirm(DELETE_TITLE, DELETE_WARN, function(r) {
				if (r) {
					window.location.href = "${deleteUrl}";
				}
			});
		});

		$("#linkDuplicationGroup").click(function() {
			$("#meter").show();
		});

		//新增计量器
		$("#btnAddMeter").click(function() {
			$("#detail").show();
			$("#sequence").focus();
		});

		//删除计量器
		$("#btnDelMeter").click(function() {

		});

		// 验证并提交数据
		validateForm('#meterInGroup');

		//验证计量器是否存在
		$("#metername").change(function() {
			if ($(this).val().length > 0) {
				$.post('${validate}', {
					metername : $(this).val(),
					groupname : $("#groupname").val()
				}, function(data) {
					if (data.success == true)
						alert("数据已存在");
				});
			}
			return false;
		});

		$(".deldetail").click(function() {
			var link = $(this).attr("link");
			if (link.length > 0) {
				$.get(link, function(data) {
					if (data.success) {
						$(this).remove();
					}
				});
			}
		});

	});
</script>
<%@ include file="../common/dialog/dlgMeter.jsp"%>