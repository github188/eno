<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>


<div class="row-fluid">
	<form class="form-horizontal">
		<div class="box">
			<div class="box-content">
				<div class="control-group">
					<label for="location" class="control-label">位置:</label>
					<div class="controls">
						<input type="text" name="location" class="input-small required"
							readonly="readonly" value="${locations.location}"> &nbsp;
						<input type="text" name="description"
							value="${locations.description}">
					</div>
				</div>
			</div>
		</div>
	</form>
</div>

<!-- 位置系统 -->
<div class="widget widget-table">
	<div class="widget-header">
		<span class="icon-list"></span>
		<h3 class="icon chart">计量器</h3>
	</div>
	<div class="widget-content">
		<table class="table table-bordered table-striped">
			<thead>
				<tr>
					<th width="20"></th>
					<th width="60">序号</th>
					<th>计量器</th>
					<th>描述</th>
					<th width="150">计量器类型</th>
					<th width="120">计量单位</th>
					<th width="50">活动</th>
					<th></th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${locationMeters}" var="row">
					<tr>
						<td class="showContent" id="${row.locationmeterid}" class="center"><i
							class="icon-arrow-right"></i></td>
						<td>${row.sequence}</td>
						<td>${row.metername}</td>
						<td></td>
						<td class="center"></td>
						<td class="center">${row.measureunitid}</td>
						<td class="center"><input type="checkbox" name="active"
							checked="${row.active}"></td>
						<td class="center"><a href="#" class="dellocsystem"
							link="<spring:url value="/locations/locationmeter/delete/${row.locationmeterid}.html"></spring:url>">
								<img
								src="<spring:url value="/resources/images/btn_garbage.gif" />"
								alt="标记要删除的行" />
						</a></td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="8" style="padding: 0px;"><spring:url
							value="/locations/${locationsid}/locationmeter/save" var="action"></spring:url>
						<form:form modelAttribute="locationMeter" action="${action}"
							cssClass="form-horizontal" style="margin:0px;">
							<div id="detailcontent" style="display: none;">

								<div class="row-fluid">
									<div class=" alert alert-info">详细信息</div>
								</div>
								<div class="row-fluid">
									<div class="span6">
										<div class="control-group ">
											<form:label path="sequence" cssClass="control-label ">序号：</form:label>
											<div class="controls">
												<form:input path="sequence" cssClass="input-small number"
													rel="popover" data-content="必须为整数" />
												<form:hidden path="location" value="${locations.location}" />
											</div>
										</div>

										<div class="control-group ">
											<form:label path="metername" cssClass="control-label">* 计量器：</form:label>
											<div class="controls">
												<form:input path="metername" cssClass="input-small"
													title="必填字段计量器不能为空" data-rule-required="true"
													data-placement="bottom" />
												<i class="icon-chevron-right showMeterlist"
													style="cursor: pointer"
													link="<spring:url value="/meter/list/dialog" />"
													title="选择计量器"></i> <input type="text"
													id="meter.description" class="input-medium" disabled />
											</div>
										</div>
										<div class="control-group ">
											<label for="metertype" class="control-label">计量器类型：</label>
											<div class="controls">
												<input type="text" name="metertype" class="input-small"
													disabled />
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
													<form:input path="lastreadingdate"
														cssClass="input-medium date"
														data-format="yyyy-MM-dd hh:mm:ss" rel="popover"
														data-content="字段值必须为日期" />
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
													disabled="true" />
											</div>
										</div>

										<div class="control-group ">
											<form:label path="lifetodate" cssClass="control-label ">位置的使用期限：</form:label>
											<div class="controls">
												<form:input path="lifetodate" cssClass="input-small number"
													disabled="true" />
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

									</div>
								</div>
							</div>

							<div class="row-fluid">
								<div class="span6 pull-right"
									style="text-align: right; padding: 5px 15px 5px 0;">
									<a class="btn" id="btnAddDetail"><span> 新增</span></a>
									<button type="submit" class="btn btn-primary savebutton"
										style="display: none;">
										保存
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