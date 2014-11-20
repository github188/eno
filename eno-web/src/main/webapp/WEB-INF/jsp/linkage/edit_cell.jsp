<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>

<!-- 
	AUTHOR: ZOUZHIXIANG
	Created Date: 2014-07-23 11:36
	LastModified Date:
	Description: 设备动作——预案编辑页面
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

		<input type="hidden" id="tag" value="${m}">
		<form id="form1"
			action="${pageContext.request.contextPath }/linkage/${m }_cell"
			method="post">
			<input type="hidden" id="cellid" name="cellid" value="${obj.cellid }">
			<div>
				<table class="table_style">
					<tr>
						<td><div class="label_style">预案名称</div></td>
						<td><input class="input_style" id="cellname" name="cellname"
							value="${obj.cellname }"></td>

						<td><div class="label_style">预案描述</div></td>
						<td><input class="input_style" name="cellcomment"
							value="${obj.cellcomment }"></td>
					</tr>
				</table>
			</div>
			<div class="row" style="margin-top: 15px;">
				<table id="actionTable" class="striped_color table_style"
					style="margin-left: 40px; margin-top: 10px;">
					<thead>
						<tr>
							<th>编号</th>
							<th>添加人</th>
							<th>动作描述</th>
							<th>动作内容</th>
							<th>动作类型</th>
							<th>执行时间</th>
							<th>是否可用</th>
							<th>更新时间</th>
							<th>编辑操作</th>
						</tr>
					</thead>
					<tbody id="tbodyID">
						<c:forEach items="${actionList}" var="act">
							<tr>
								<td>${act.actionid }</td>
								<td>${act.authorname }</td>
								<td>${act.actioncomment }</td>
								<td><textarea readonly="readonly" style="margin: 6px 0px 6px 0px;">${act.actionc }</textarea>
								</td>
								<td><select disabled="disabled">
										<c:forEach items="${actionNames}" var="mapList">
											<c:if test="${mapList.key == act.actiontype}">
												<option value="${mapList.key}" selected="selected">${mapList.value}</option>
											</c:if>
											<c:if test="${mapList.key != act.actiontype}">
												<option value="${mapList.key}">${mapList.value}</option>
											</c:if>
										</c:forEach>
								</select></td>
								<td>${act.offsett }</td>
								<td><select disabled="disabled">
										<c:if test="${act.enabled == '0'}">
											<option value="0" selected="selected">不可用</option>
										</c:if>
										<c:if test="${act.enabled == '1'}">
											<option value="1" selected="selected">可用</option>
										</c:if>
								</select></td>
								<td>${act.updatet }</td>
								<td><a href="javascript:void(0);"
									style="margin-left: 10px;"
									onclick="delAction(${act.actionid },this);">删除</a> <a
									href="javascript:void(0);" style="margin-left: 10px;"
									onclick="editAction(this);">编辑</a></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
			
		</form>



		<div id="addCellAction" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-header">
				<div class="dialog-close"  data-dismiss="modal" aria-hidden="true">×</div>
				<p class="dialog-title" id="myModalLabel">添加预案动作</p>
			</div>
			<div class="modal-body">		
				
				<div class="add_cell">
					<p class="pCss">动作类型：</p>
					<select id="action_category">
						<option value="0">请选择</option>
						<c:forEach items="${actionNames}" var="map">
							<option value="${map.key}">${map.value}</option>
						</c:forEach>
					</select>
					<div id="add_cell"></div>
				</div>
				
			</div>
<!-- 			<div class="modal-footer"> -->
<!-- 				<button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button> -->
<!-- 			</div> -->
		</div>



		<div class="control-group">
				<div class="btnMargin">
					<button type="button" class="btn" id="submitObj">确认创建</button>
					<button type="button" class="btn" id="addTR">添加预案动作</button>
					<button type="button" class="btn" id="delObj">删除</button>
					<button type="button" class="btn" id="back">返回</button>
				</div>
		</div>
	</div>
</div>

<script type="text/javascript">
	var i=0;
	var idList = [];
	var pathName=window.document.location.pathname;//获取主机地址之后的目录
	var projectName=pathName.substring(0,pathName.substring(1).indexOf('/')+1); //获取带"/"的项目名
    
	$(document).ready(function(){
    	if("add" == $("#tag").val()){
    		$("#actionTable").hide(); //隐藏动作列表 
    	}
    	$('#addTR').click(function (){
    		$('#addCellAction').modal('show');
    		$(".add_cell").show(); //显示动作添加区域
    	});
    	$("#action_category").change(function(){ //选择动作类型
    		addAction(this);//添加动作设置窗口内容生成
    	});
    	
    	$('#submitObj').click(function (){
    		if($.trim($("#cellname").val()).length>0){
    			if(idList.length>0){
    				$.ajax({
        	    		type : "GET",
        	    		async:false,
        	    		url : projectName + '/linkage/del/' + $("#tag").val() + '/cell/' + $("#cellid").val() + '/'+ idList,
        	    		success : function(data) {
        	    			
        	    		}
        	    	});
    			}
    			$('#form1').submit();
    		}else{
    			alert("预案名称不能为空！");
    		}
    	});
    	
    	// 删除事件
		$('#delObj').click(function() {
				if ($.trim($("#cellid").val()).length > 0) {
					window.location.href = CONTEXT_PATH + '/linkage/del/cell/' + $("#cellid").val();
				} else {
					return false;
				}
			});
    	
		// 返回事件
		$('#back').click(function() {
			window.location.href = CONTEXT_PATH + '/linkage/view_cell';
		});
    	
    });
    
    // 确认动作创建
    function confirmToAdd(){
    	$("#actionTable").show(); //显示动作信息区域
    	var content = new Object();  //动作内容对象 
    	var key = '';
    	var para = [];
    	var actionContent = '';
    	/* 获取json字符串 */
    	if($.trim($("#setactioncomment").val()).length>0){
    		content.setactioncomment = $("#setactioncomment").val();
    	}
    	if($.trim($("#setoffsett").val()).length>0){
    		content.setoffsett = $("#setoffsett").val();
    	}
    	if($.trim($("#setsite").val()).length>0){
    		content.setsite = $("#setsite").val();
    	}
    	content.action_enabled = $("#action_enabled").val();
    	content.show_enabled = $("#show_enabled").val();
    	
    	if($.trim($("#add_cell input:checked").val()).length>0){
    		$("#add_cell input:checked").parent("td").siblings().children("input").each(function(){
        		para.push($.trim($(this).val()));
        	});
    		content[$("#add_cell input:checked").val()] = para;
    	}
    	
    	/* for(var index = 0 ;index<para.length;index++){
    		actionContent += para[index];
    		if(i<para.length-1){
    			actionContent += " to ";
    		}
    	}
    	content = $("#add_cell input:checked").val() + " " + actionContent;  */
    	var json = JSON.stringify(content); //将json字符串转换为json对象 
  		var trObj='';
  		trObj+='<tr>';
  		trObj+='<td><input style="width: 100px" readonly="readonly" name="uclcActionArray['+i+'].actionid" value=' + $("#setactionid").val() + '></td>';
  		trObj+='<td><input style="width: 100px" readonly="readonly" name="uclcActionArray['+i+'].authorname"></td>';
		trObj+='<td><input style="width: 100px" readonly="readonly" name="uclcActionArray['+i+'].actioncomment" value=\''+ $("#setactioncomment").val() +'\'></td>';
		if($("#action_category").val()==2){
			trObj+='<td><input style="width: 100px" readonly="readonly" id="actionc'+ i + '" name="uclcActionArray['+i+'].actionc"></td>';
		}else if($("#action_category").val()==3){
			trObj+='<td><input style="width: 100px" readonly="readonly" id="actionc'+ i + '" name="uclcActionArray['+i+'].actionc"></td>';
		}
		trObj+='<td id="actionType'+ i +'"><input style="width: 100px;display:none" name="uclcActionArray['+i+'].actiontype"></td>';
		trObj+='<td><input style="width: 100px" readonly="readonly" name="uclcActionArray['+i+'].offsett" value='+ $("#setoffsett").val() +'></td>';
		trObj+='<td id="actionEnabled'+ i +'"><input style="width: 100px;display:none;" name="uclcActionArray['+i+'].enabled"></td>';
		trObj+='<td><input style="width: 100px" readonly="readonly"></td>'; 
		trObj+='<td align="center"><a href="javascript:void(0)" onclick="delTR(this)">-</a>';
		trObj+='</td>';
		trObj+='</tr>';
  		$('#tbodyID').append(trObj);
  		
  		if($("#action_category").val()==2){
			$("#actionc" + i).val(json);
			
		}else if($("#action_category").val()==3){
			$("#actionc" + i).val($("#setactionc").val());
		}
  		/* 实现动作类型由下拉框取值 */
  		$("#action_category").clone().prependTo("#actionType" + i);
  		$("#actionType" + i + " select").removeAttr("id");
  		$("#actionType" + i + " select").val($("#action_category").val());
  		$("#actionType" + i + " select").show();
 		$("#actionType" + i + " select").attr("disabled","disabled");
  		$("#actionType" + i + " input").val($("#actionType" + i + " select").val());
  		/* 实现动作是否可用由下拉框取值 */
  		$("#action_enabled").clone().prependTo("#actionEnabled" + i);
  		$("#actionEnabled" + i + " select").removeAttr("id");
  		$("#actionEnabled" + i + " select").val($("#action_enabled").val());
  		$("#actionEnabled" + i + " select").show();
  		$("#actionEnabled" + i + " select").attr("disabled","disabled");
  		$("#actionEnabled" + i + " input").val($("#actionEnabled" + i + " select").val());
  		i++;
    	reset(); //重置动作创建框内容 
    	$('#addCellAction').modal('hide');
    }
    
    /*取消动作创建 */
    function cancelToAdd(){
    	reset(); //重置动作创建框内容 
    	$('#addCellAction').modal('hide');
    }
    /*重置动作创建框内容 */
    function reset(){
    	$("#action_category").val(0);//重置类型选择下拉框 
    	$("#action_category").removeAttr("disabled");
    	$("#add_cell").empty();//清空内容 
    	$(".add_cell").hide(); //隐藏动作添加区域
    }
    /* 删除动作信息，保存其唯一id，并移除其所在行 */
    function delAction(id,obj){
    	idList.push(id); //存入要删除动作的id
    	$(obj).parent().parent().remove();//移除该行
    }
   	/*  编辑动作信息 */
    function editAction(obj){
   		var tdObj = $(obj).parent().parent();
   		$(tdObj).hide();
   		$('#addCellAction').modal('show');
   		$(".add_cell").show(); //显示动作添加区域
   		$("#action_category").val($(tdObj).children("td:eq(4)").children("select").val());
   		$("#action_category").attr("disabled","disabled");
   		addAction($(tdObj).children("td:eq(4)").children("select")); // 添加动作设置窗口内容生成
   		$("#setactionid").val($(tdObj).children("td:eq(0)").text());
   		if($(tdObj).children("td:eq(4)").children("select").val()==2){
   			console.log($(tdObj).find("td:eq(3) textarea").text());
   	    	var json = JSON.parse($(tdObj).find("td:eq(3) textarea").text()); //由JSON字符串转换为JSON对象;
   	    	for(var key in json){
   	    		if(key == "setactioncomment" || key == "setoffsett" || key == "setsite" || key == "action_enabled" || key == "show_enabled"){
   	    			$("#" + key).val(json[key]);
   	    		}else{
   	    			$("input[name=setParameter]").each(function(){
   	    				if($(this).val()==key){
   	    					$(this).attr("checked","checked");
   	    					$(this).parent().siblings().children("input").each(function(){
   	    						$(this).val(json[key][$(this).index()])
   	    	         		});
   	    				}
   	    			})
   	    		}
   	    	}
   		}else if($(tdObj).children("td:eq(4)").children("select").val()==3){
   	    	$("#setactioncomment").val($(tdObj).children("td:eq(2)").text());
   	    	$("#setactionc").val($(tdObj).children("td:eq(3) textarea").text());
   	    	$("#setoffsett").val($(tdObj).children("td:eq(5)").text());
   	    	$("#action_enabled").val($(tdObj).children("td:eq(6)").children("select").val());
   		}
    }
   	
   	function addAction(obj){
   		$("#add_cell").empty();//清空内容 
		var divObj = ''; //编辑区域生成语句存储对象
		if($(obj).val()==2){
			divObj+='<input id="setactionid" style="width: 30px;display:none;"><br>';
			divObj+='<table><tbody>';
			divObj+='<tr><td>描述：</td><td><input id="setactioncomment" style="width: 100px"></td>';
			divObj+='<td>时间点：</td><td><input id="setoffsett" style="width: 100px" value="0"></td></tr>';
			divObj+='<tr><td>摄像机号：</td><td><input id="setsite" style="width: 100px"></td>';
			divObj+='<td>到预置位置：</td><td><select id="action_enabled" style="width: 104px;margin-top: 8px;"><option value="0">不可用</option><option value="1">可用</option></select></td></tr>';
			divObj+='<tr><td>显示到大屏：</td><td><select id="show_enabled" style="width: 104px;"><option value="0">不可用</option><option value="1">可用</option></select></td></tr>';
			divObj+='<tr><td><input type="radio" name="setParameter" class="radioCss" value="num">设置窗格数：</td>';
			divObj+='<td><input style="width: 30px" class="radioCss" value="0">&nbsp;(0/4/9/16 0=不变)</td></tr>';
			divObj+='<tr><td><input type="radio" class="radioCss" name="setParameter" value="showWindow">显示视频到窗口：</td>';
			divObj+='<td><input style="width: 30px" class="radioCss" value="0">&nbsp;(0=轮换)&nbsp;</td></tr>';
			divObj+='<tr><td><input type="radio" class="radioCss" name="setParameter" value="takePhoto">抓拍图片：</td><td></td></tr>';
			divObj+='<tr><td><input type="radio" class="radioCss" name="setParameter" value="takeRadio">自动开始录像：</td>';
			divObj+='<td><input style="width: 30px" value="60" class="radioCss">&nbsp;秒&nbsp;</td></tr>';
			divObj+='<tr><td><input type="radio" name="setParameter" value="beginRoll" class="radioCss">启动轮询组：</td>';
			divObj+='<td><input style="width: 30px" value="0" class="radioCss">&nbsp;到窗口：<input style="width: 30px" value="0" class="radioCss"></td></tr>';
			divObj+='<tr><td><input type="radio" name="setParameter" class="radioCss" value="stopRoll">停止轮询组：</td>';
			divObj+='</tbody></table>';
			divObj+='<button type="button" class="btn confirm_btn" onclick="confirmToAdd();">确定</button>';
			divObj+='<button type="button" class="btn cancel_btn" onclick="cancelToAdd();">取消</button>';
		}else if($(obj).val() == 3) {
			divObj+='<input id="setactionid" style="width: 30px;display:none;"><br>';
			divObj+='<table><tbody>';
			divObj+='<tr><td>描述：</td><td><input id="setactioncomment" class="inputCss"></td>';
			divObj+='<td class="timeline">时间点：</td><td><input id="setoffsett" class="inputCss" value="0"></td></tr>';
			divObj+='<tr><td>动作：</td><td><select id="action_enabled" class="actionCss"><option value="0">不可用</option><option value="1">可用</option></select></td></tr>';
			divObj+='<tr><td>脚本：</td><td colspan="3"><textarea id="setactionc" class="jsCss"></textarea></td></tr>';
			divObj+='</tbody></table>';
			divObj+='<button type="button" class="btn confirm_btn" onclick="confirmToAdd();">确定</button>';
			divObj+='<button type="button" class="btn cancel_btn" onclick="cancelToAdd();">取消</button>';
		}
		$("#add_cell").append(divObj);
   	}
    function delTR(obj){
		$(obj).parent().parent().remove();
	}
</script>