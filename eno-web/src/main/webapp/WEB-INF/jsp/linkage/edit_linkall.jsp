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
			<div class="widget-content"
				style="overflow: auto; height: 300px; width: 165px;">
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

		<form id="form1"
			action="${pageContext.request.contextPath }/linkage/${m }_linkall"
			method="post">
			<input type="hidden" name="linkageid" id="linkageid" value="${obj.linkageid }">
			<input type="hidden" id="cellnames" name="cellnames">
			<input type="hidden" id="offsetts" name="offsetts">
			<input type="hidden" id="shiftsnames" name="shiftsnames">
			<table class="table_style">
				<tr>
					<td><div class="label_style">规划名称</div></td>
					<td><input class="input_style" id="linkagename"
						name="linkagename" value="${obj.linkagename }"></td>
					<td><div class="label_style">规划描述</div></td>
					<td><input class="input_style" name="linkagecomment"
						value="${obj.linkagecomment }"></td>
				</tr>
				<tr>
					<td><div class="label_style">创建人</div></td>
					<td><input class="input_style" name="authorname"
						value="${obj.authorname }"></td>
					<td><div class="label_style">触发条件</div></td>
					<td><select class="input_style" name="conditionname" style="margin-top: 10px;height: auto;">
							<c:forEach items="${conditionList}" var="condition">
								<option value="${condition.condname }"
									<c:if test='${obj.conditionname==condition.condname}'>selected='selected'</c:if>>${condition.condname }</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td><div class="label_style">是否终止</div></td>
					<td><select class="input_style" name="paused" style="height: auto;">
							<option value="0"
								<c:if test='${obj.paused == 0}'>selected='selected'</c:if>>否</option>
							<option value="1"
								<c:if test='${obj.paused == 1}'>selected='selected'</c:if>>是</option>
					</select>
					<td><div class="label_style">是否活动</div></td>
					<td><select class="input_style" name="checkactive" style="height: auto;">
							<option value="0"
								<c:if test='${obj.checkactive == 0}'>selected='selected'</c:if>>否</option>
							<option value="1"
								<c:if test='${obj.checkactive == 1}'>selected='selected'</c:if>>是</option>
					</select>
				</tr>

			</table>



			<div class="row" style="margin-top: 15px;">
				<table id="" class="striped_color table_style"
					style="margin-left: 40px; margin-top: 10px;">
					<thead>
						<tr>
							<!-- <th>编号</th> -->
							<th>选1择</th>
							<th>预案名称</th>
							<th>执行时间</th>
							<th>SHIFTNAME</th>
							<th>更新时间</th>
							<!-- <th>编辑操作</th> -->
						</tr>
					</thead>
					<tbody id="tbodyID">
						<c:forEach items="${cellList}" var="cellList">
							<tr>
								<c:set var="tag" value="true" />
								<c:forEach items="${linkcellList}" var="list">
									<c:if test="${list.cellname == cellList.cellname}">
										<td><input type="checkbox" name="cbox" class="checkboxCss" value="${list.linkageid }" checked="checked"
											value="${cellList.cellname}"> <input
											value="${list.lcid}" style="display: none;"></td>
										<td>${cellList.cellname}</td>
										<td><input value="${list.offsett}"></td>
										<td><input value="${list.shiftname}"></td>
										<c:set var="tag" value="false" />
									</c:if>
								</c:forEach>
								<c:if test="${tag}">
									<td><input type="checkbox" name="cbox" class="checkboxCss" value="${cellList.cellid }" value="${cellList.cellname}">
										<input style="display: none;"></td>
									<td>${cellList.cellname}</td>
									<td><input value="${list.offsett}"></td>
									<td><input value="${list.shiftname}"></td>
								</c:if>
								<td><input value="${cellList.updatet }" readonly="readonly"></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			
			<div class="control-group" style="margin: 20px 0 0 -25px;">
				<div class="btnMargin">
					<button type="button" class="btn" id="submitObj">确认创建</button>
					<button type="button" class="btn" id="delObj">删除</button>
					<button type="button" class="btn" id="back">返回</button>
				</div>
			</div>
		</form>

	</div>
</div>

<script type="text/javascript">
	$(document)
		.ready(function() {
			var i = 0;
			var attr = ["","offsett","shiftname","updatet"];
			$('#submitObj').click(function() {
				var cells = [], times = [], shifts = [];
				$("#tbodyID input:checkbox:checked").each(function(){
					var $tr = $(this).parent().parent(); // 当前选中行
					cells.push($tr.find("td:eq(1)").text()); // 预案名称
					times.push($tr.find("td:eq(2) > input").val()); // 执行时间
					shifts.push($tr.find("td:eq(3) > input").val()); // shiftname
				});
				
				$("#cellnames").val(cells.join());
				$("#offsetts").val(times.join());
				$("#shiftsnames").val(shifts.join());
				
				if($.trim($("#linkagename").val()).length>0){
					$('#form1').submit();
				}else{
					alert("规划名称不能为空！");
				}
			});
			
			// 删除事件
			$('#delObj').click(function() {
					if ($.trim($("#linkageid").val()).length > 0) {
						window.location.href = CONTEXT_PATH + '/linkage/del/linkall/' + $("#linkageid").val();
					} else {
						return false;
					}
				});
			// 返回事件
			$('#back').click(function() {
				window.location.href = CONTEXT_PATH + '/linkage/view_linkall';
			});
		});
</script>