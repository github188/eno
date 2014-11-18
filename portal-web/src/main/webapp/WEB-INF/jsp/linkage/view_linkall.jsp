<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>

<!-- 
	AUTHOR: ZOUZHIXIANG
	Created Date: 2014-07-23 11:36
	LastModified Date:
	Description: 事件库页面
-->

<div class="row-fluid">
	<div class="span2">
		<div class="widget">
			<div class="widget-header">
				<h3 class="icon">联动控制</h3>
			</div>
			<div class="widget-content linkageDiv">
				<ul class="nav nav-tabs nav-stacked">
					<li class="<c:if test="${fn:contains(requestURL,'condition')}">liCss</c:if>"><a
						href="<spring:url value="/linkage/view_condition.html"></spring:url>">联动事件库</a></li>
					<li class="<c:if test="${fn:contains(requestURL,'cell')}">liCss</c:if>"><a
						href="<spring:url value="/linkage/view_cell.html"></spring:url>">联动预案</a></li>
					<li class="<c:if test="${fn:contains(requestURL,'linkall')}">liCss</c:if>"><a
						href="<spring:url value="/linkage/view_linkall.html"></spring:url>">联动规划</a></li>
				</ul>
			</div>
		</div>
	</div>

	<div class="span10">

		<div class="tab-content">		 
			<div class="tab-pane fade in active">
				<fieldset>
					<legend>
						<button type="button" class="btn" id="createLinkAll">创建规划</button>
					</legend>
					<div class="eventList">规划列表：</div>
					<div class="widget widget-table">
						<div class="widget-content">
							<table class="table table-bordered table-striped " id="linkalltable">
								<thead>
									<tr>
										<th>编号</th>
										<th>规划名称</th>
										<th>规划描述</th>
										<th>创建人</th>
										<th>触发条件</th>
										<th>是否终止</th>
										<th>是否活动</th>
										<th>更新时间</th>
									</tr>
								</thead>
							</table>
						</div>
					</div>
				</fieldset>
			</div>
		</div>

	</div>
</div>