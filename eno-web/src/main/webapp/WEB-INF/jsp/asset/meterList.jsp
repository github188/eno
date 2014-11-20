<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<c:choose>
	<c:when test="${asset['assetuid'] eq ''}">
		<c:set var="method" value="post" />
	</c:when>
	<c:otherwise>
		<c:set var="method" value="put" />
	</c:otherwise>
</c:choose>
<form:form modelAttribute="asset" cssClass="form-horizontal">
	<div class="box">
		<div class="box-content">
			<div class="control-group">
				<form:label path="assetnum" class="control-label">资产:</form:label>
				<div class="controls">
					<form:input path="assetnum" class="input-medium" readonly="true" />
					<form:input path="description" />
				</div>
			</div>
			<div class="control-group ">
				<form:label path="groupname" class="control-label">计量器组:</form:label>
				<div class="controls">
					<form:input path="groupname" class="input-medium" />
					<div class="btn-group">
						<button class="btn dropdown-toggle" data-toggle="dropdown">
							&gt;&gt;</button>
						<ul class="dropdown-menu">
							<li><a href="#" class="showMetergroupList" link="<spring:url value="/metergroup/list/dialog" />">选择值</a></li>
							<li><a href="<spring:url value="/metergroup/list.html"/>" id="redirectMeterGroup">转到 计量器组</a></li>
						</ul>
					</div>
					<!-- /btn-group -->
					<input type="text" name="metergroup.description"
						id="metergroup.description" disabled />
				</div>
			</div>
		</div>
	</div>
</form:form>


<!-- 计量器列表 -->
<div class="widget widget-table">
	<div class="widget-content">
		
		<%-- <script>
			//编辑信息
			function editable(data, type, entity) {
				return '<a href="${pageContext.request.contextPath}/asset/edit/'+ entity.assetuid + '.html"" style="color:blue">'
						+ data + '</a>';
			}
		</script>

		<c:url value="${pageContext.request.contextPath}/${asset.assetuid}/meters" var="url"></c:url>

		<datatables:table id="assetmetertable" url="${url}" serverSide="true">
			<datatables:column title="序号" property="assetMeter.sequence" filterable="true"
				renderFunction="editable" />
			<datatables:column title="计量器" property="assetMeter.metername" filterable="true" />
			<datatables:column title="描述" property="meter.description"
				filterable="true" />
			
			<datatables:column title="计量器类型" property="location" filterable="true" />
			<datatables:column title="计量单位" property="assetMeter.measureunitid" filterable="true" />
			<datatables:column title="活动" property="assetMeter.active" />
		</datatables:table> --%>
		
	
	
		<table class="table table-bordered" id="assetmetertable">
			<thead>
				<tr>
					<th width="20"></th>
					<th width="60" align="right">序号</th>
					<th>计量器</th>
					<th>描述</th>
					<th width="150">计量器类型</th>
					<th width="120">计量单位</th>
					<th width="50">活动</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${assetMeters}" var="row">
					<tr>
						<td class="showContent" id="${row.assetMeter.assetmeterid}"
							class="center"><i class="icon-arrow-right"></i></td>
						<td>${row.assetMeter.sequence}</td>
						<td>${row.assetMeter.metername}</td>
						<td>${row.meter.description}</td>
						<td class="center"></td>
						<td class="center">${row.assetMeter.measureunitid}</td>
						<td class="center"><input type="checkbox" name="active"
							checked="${row.assetMeter.active}"></td>
						<td class="center">
						<a href="#" class="dellocsystem" link="<spring:url value="/asset/${assetuid}/meters/delete/${row.assetMeter.assetmeterid}.html"></spring:url>">
								<img src="<spring:url value="/resources/images/btn_garbage.gif" />" alt="标记要删除的行" />
						</a></td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="8" style="padding: 0px;"><spring:url
							value="/asset/${assetuid}/meters/save" var="action"></spring:url>
						<form:form modelAttribute="assetMeter" action="${action}"
							cssClass="form-horizontal" style="margin:0px;">
							<div id="assetmeterFormContent" style="display: none;">

								<div class="row-fluid">
									<div class=" alert alert-info">详细信息</div>
								</div>
								<div class="row-fluid">
									<div class="span6">
										<div class="control-group ">
											<form:label path="sequence" cssClass="control-label ">序号：</form:label>
											<div class="controls">
												<form:input path="sequence" cssClass="input-small number"
													title="必须为整数" data-rule-required="false"
													data-placement="bottom" />
												<form:hidden path="assetnum" value="${asset.assetnum}" />
											</div>
										</div>

										<div class="control-group ">
											<form:label path="metername" cssClass="control-label">* 计量器：</form:label>
											<div class="controls">
												<form:input path="metername" cssClass="input-small"
													title="必填字段'计量器'不能为空" data-rule-required="true"
													data-placement="bottom" />
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

												<input type="text" id="meter.description"
													class="input-medium" disabled />
											</div>
										</div>
										<div class="control-group ">
											<label for="metertype" class="control-label">计量器类型：</label>
											<div class="controls">
												<input type="text" name="metertype" class="input-small"
													readonly="readonly" />
											</div>
										</div>
										<div class="control-group ">
											<label for="measureunitid" class="control-label">计量单位：</label>
											<div class="controls">
												<input type="text" name="measureunitid" class="input-small"
													disabled />
											</div>

										</div>
										<div class="control-group ">
											<form:label path="active" class="control-label">活动?</form:label>
											<div class="controls">
												<form:checkbox path="active" checked="true" />
											</div>
										</div>
									</div>

									<div class="span6">
										<div class="control-group ">
											<form:label path="lastreading" cssClass="control-label">上次读数：</form:label>
											<div class="controls">
												<form:input path="lastreading" cssClass="input-small number"
													rel="popover" data-content="字段值必须为数值" />
											</div>
										</div>
										<div class="control-group ">
											<form:label path="lastreadingdate" cssClass="control-label">上次读数日期：</form:label>
											<div class="controls">
												<div id="lastreadingdatepicker" class="input-append date">
													<form:input path="lastreadingdate" cssClass="input-medium"
														data-format="yyyy-MM-dd hh:mm:ss" title="字段值必须为日期" />
													<span class="add-on"> <i data-time-icon="icon-time"
														data-date-icon="icon-calendar"> </i>
													</span>
												</div>
											</div>
										</div>
										<div class="control-group ">
											<form:label path="lastreadinginspctr"
												cssClass="control-label">上次读数检验员：</form:label>
											<div class="controls">
												<form:input path="lastreadinginspctr" cssClass="input-small" />
											</div>
										</div>
										<div class="control-group ">
											<form:label path="remarks" cssClass="control-label">备注：</form:label>
											<div class="controls">
												<form:input path="remarks" cssClass="input-large" />
											</div>
										</div>
									</div>
								</div>
								<div class="row-fluid">
									<div class=" alert alert-info">持续运行计量器的详细信息</div>
								</div>
								<div class="row-fluid">
									<div class="span6">
										<div class="control-group ">
											<form:label path="avgcalcmethod" cssClass="control-label ">平均计算方法：</form:label>
											<div class="controls">
												<form:input path="avgcalcmethod" cssClass="input-small"
													disabled="true" />
											</div>
										</div>
										<div class="control-group ">
											<form:label path="slidingwindowsize"
												cssClass="control-label ">可调整窗口大小：</form:label>
											<div class="controls">
												<form:input path="slidingwindowsize"
													cssClass="input-small number" disabled="true" />
											</div>
										</div>
										<div class="control-group ">
											<form:label path="average" cssClass="control-label ">日平均单位：</form:label>
											<div class="controls">
												<form:input path="average" cssClass="input-small number"
													readonly="true" />
											</div>
										</div>

										<div class="control-group ">
											<form:label path="lifetodate" cssClass="control-label ">位置的使用期限：</form:label>
											<div class="controls">
												<form:input path="lifetodate" cssClass="input-small number"
													readonly="true" />
											</div>
										</div>
										<div class="control-group ">
											<form:label path="lifetodate" cssClass="control-label ">资产的寿命：</form:label>
											<div class="controls">
												<form:input path="lifetodate" cssClass="input-small number"
													readonly="true" />
											</div>
										</div>

									</div>
									<div class="span6">
										<div class="control-group ">
											<form:label path="rollover" cssClass="control-label ">回滚：</form:label>
											<div class="controls">
												<form:input path="rollover" cssClass="input-small"
													disabled="true" />
											</div>
										</div>
										<div class="control-group ">
											<form:label path="readingtype" cssClass="control-label ">读数类型：</form:label>
											<div class="controls">
												<form:input path="readingtype" cssClass="input-small"
													disabled="true" />
											</div>
										</div>
										<div class="control-group ">
											<form:label path="readingtype" cssClass="control-label ">接受下滚源：</form:label>
											<div class="controls">
												<form:input path="rolldownsource" cssClass="input-small"
													readonly="true" />
											</div>
										</div>
									</div>
								</div>
							</div>

							<div class="row-fluid">
								<div class="span6 pull-right"
									style="text-align: right; padding: 5px 15px 5px 0;">
									<a class="btn" id="btnAddAssetmeter"><span><i
											class="icon-plus"></i> 新增</span></a>
									<button type="submit" class="btn btn-primary savebutton"
										style="display: none;">
										<i class="icon-ok-sign"></i> 保存
									</button>
								</div>
							</div>

						</form:form></td>
				</tr>
			</tfoot>
		</table>
	</div>
	<!-- .widget-content -->
</div>
<!-- .widget -->

<%@ include file="../common/dialog/dlgMeter.jsp"%>
<%@ include file="../common/dialog/dlgMeterGroupList.jsp" %>
