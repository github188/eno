<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>

<!-- 
	AUTHOR: ZOUZHIXIANG
	Created Date: 2014-07-23 20:36
	LastModified Date:
	Description: 设备动作——事件库编辑页面（添加、修改）
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
			action="${pageContext.request.contextPath }/linkage/${m }_condition"
			method="post">
			<input type="hidden" id="condid" name="condid" value="${obj.condid }">
			<div class="tableMargin">
				<table class="table_style">
					<tr>
						<td><div class="label_style">事件名称：</div></td>
						<td><input class="input_width input_style desc_padding" id="condname" name="condname"
							value="${obj.condname }"></td>
					</tr>
					<tr>
						<td><div class="label_style input_desc" style="float: right;">事件描述：</div></td>
						<td><input class="input_desc input_width input_style desc_padding" name="condcomment"
							value="${obj.condcomment }"></td>
					</tr>
					<tr>
						<td style="width: 70px;"><div class="label_style">触发条件：</div></td>
						<td>
							<textarea rows="1" cols="3" class="condexp_css" id="condexp"
								name="condexp">${obj.condexp }</textarea>
						</td>
					</tr>
					
					<tr>
						<td><div class="label_style">快捷输入：</div></td>
						<td>
							<select class="selectCss" id="select1" size="100">
<!-- 								<option value="$方位权限">$方位权限</option> -->
<!-- 								<option value="$分">$分</option> -->
<!-- 								<option value="$毫秒">$毫秒</option> -->
<!-- 								<option value="$计数器1">$计数器1</option> -->
<!-- 								<option value="$计数器2">$计数器2</option> -->
<!-- 								<option value="$计数器3">$计数器3</option> -->
<!-- 								<option value="$秒">$秒</option> -->
<!-- 								<option value="$秒数">$秒数</option> -->
<!-- 								<option value="$年">$年</option> -->
<!-- 								<option value="$启动报警记录">$启动报警记录</option> -->
<!-- 								<option value="$启动后台程序">$启动后台程序</option> -->
<!-- 								<option value="$启动历史记录">$启动历史记录</option> -->
<!-- 								<option value="$启动事件记录">$启动事件记录</option> -->
<!-- 								<option value="$日">$日</option> -->
<!-- 								<option value="$时">$时</option> -->
<!-- 								<option value="$双机热备">$双机热备</option> -->
<!-- 								<option value="$天数">$天数</option> -->
<!-- 								<option value="$新报警">$新报警</option> -->
<!-- 								<option value="$星期">$星期</option> -->
<!-- 								<option value="$月">$月</option> -->
							</select>
							
							
							<select class="selectCss" id="select2" size="100">
								<option value="==">==</option>
								<option value="&lt;&gt;">&lt;&gt;</option>
								<option value="&gt;">&gt;</option>
								<option value="&gt;=">&gt;=</option>
								<option value="&lt;">&lt;</option>
								<option value="&lt;=">&lt;=</option>
								<option value="(">(</option>
								<option value=")">)</option>
								<option value=" and "> and </option>
								<option value=" or "> or </option>
								<option value=" not "> not </option>
							</select>
							
							
							
							<select class="selectCss" id="select3" size="100">
								<option value="+">+</option>
								<option value="-">-</option>
								<option value="*">*</option>
								<option value="/">/</option>
								<option value="0">0</option>
								<option value="1">1</option>
								<option value="2">2</option>
								<option value="3">3</option>
								<option value="4">4</option>
								<option value="5">5</option>
								<option value="6">6</option>
								<option value="7">7</option>
								<option value="8">8</option>
								<option value="9">9</option>
							</select>
							
						</td>
					</tr>
				</table>
			</div>

			<div class="control-group">
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
    $(document).ready(function(){
    	getInputList();
    	
    	$('#select1').dblclick(function(){
    		$('#condexp').val($('#condexp').val() + $('#select1 option:selected').text());
    	});
    	$('#select2').dblclick(function(){
    		$('#condexp').val($('#condexp').val() + $('#select2 option:selected').text());
    	});
    	$('#select3').dblclick(function(){
    		$('#condexp').val($('#condexp').val() + $('#select3 option:selected').text());
    	}); 
    	
    	// 确认创建
		$('#submitObj').click(function() {
				if ($.trim($("#condname").val()).length > 0) {
					$('#form1').submit();
				} else {
					alert("事件名称不能为空！");
					return false;
				}
			});
		// 删除事件
		$('#delObj').click(function() {
				if ($.trim($("#condid").val()).length > 0) {
					window.location.href = CONTEXT_PATH + '/linkage/del/condition/' + $("#condid").val();
				} else {
					return false;
				}
			});
		// 返回事件
		$('#back').click(function() {
			window.location.href = CONTEXT_PATH + '/linkage/view_condition';
		});
	});
    
    // 获取快捷输入的变量
    function getInputList() {
    	$.ajax({
            type : "POST",
            url : CONTEXT_PATH + "/linkage/getInputList",
            success : function(result) {
                for(var i = 0; i < result.length; i++ ) {
                	$("#select1").append('<option value="' + result[i] + '">' + result[i] + '</option>');
                }
            },
            error : function(result) {
                console.log('--getInputList_error--');
            }
        });
    }
</script>