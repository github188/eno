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
						<button type="button" class="btn" id="createCell">创建预案</button>
						<button type="button" class="btn" onclick="exportCell();">导出</button>
					</legend>
					<div class="eventList">预案列表：</div>
					<div class="widget widget-table">
						<div class="widget-content">
							<table class="table table-bordered table-striped " id="celltable">
								<thead>
									<tr>
										<th>编号</th>
										<th>预案名称</th>
										<th>预案描述</th>
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

<script>
$(function(){
	if("${uploadStatus}" == "success") {
		window.opener.location.reload(); // 重新刷新页面
		window.close();
	}
})
</script>