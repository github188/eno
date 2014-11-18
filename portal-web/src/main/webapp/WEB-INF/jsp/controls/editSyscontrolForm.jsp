<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>

<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="<spring:url value="/resources/plugins/jeasyui/themes/default/easyui.css"></spring:url>" />
<link rel="stylesheet" href="<spring:url value="/resources/plugins/jeasyui/themes/icon.css"></spring:url>" />
<script src="<spring:url value="/resources/plugins/jeasyui/jquery.easyui.min.js"></spring:url>"></script>
</head>

<div class="row-fluid">
	<div class="span2">
		<div class="widget">
			<div class="widget-header">
				<h3 class="icon">组件列表</h3>
				<button class="btn btn-info pull-right" data-bind="click:addControl">添加组件</button>
			</div>
			<div class="widget-content"
				style="overflow: auto;height: 700px;width: 256px;">
				<ul data-bind="foreach:controls" class="nav nav-tabs nav-stacked">
					<li><a href="#"
						data-bind="text: ctrlname,click:$root.editControl"></a></li>
				</ul>

			</div>
		</div>
		<!-- /sidebar -->
	</div>
	<div class="span8" style="width: 80%;">

		<form id="syscontrol" method="post"
			action="${pageContext.request.contextPath}/okcsys/controls"
			class="form-horizontal" role="form">

			<fieldset>
				<legend>控件设置</legend>
			</fieldset>


			<div class="control-group">
				<label for="controlid" class="control-label">控件信息：</label>
				<div class="controls">
					<input type="hidden" id="controluid" name="controluid"
						data-bind="value: currentRow().controluid" /> <input type="text"
						class="form-control" placeholder="标识" id="controlid"
						name="controlid" data-bind="value: currentRow().controlid"
						required="required" /> <input type="text" class="form-control"
						placeholder="名称" id="ctrlname" name="ctrlname"
						data-bind="value: currentRow().ctrlname" required="required" />
				</div>
			</div>
			<div class="control-group">
				<label for="description" class="control-label">控件描述：</label>
				<div class="controls">
					<textarea style="width: 431px;" id="description" name="description" class="form-control" rows="1"
						placeholder="描述" data-bind="value: currentRow().description"></textarea>
				</div>
			</div>

			<ul id="contTab" class="nav nav-tabs">
				<li class="active"><a href="#format" data-toggle="tab">基础设置</a></li>
				<li><a href="#statusSetting" data-toggle="tab">状态反馈</a></li>
				<li><a href="#deviceSetting" data-toggle="tab">设备设置</a></li>
				<li><a href="#controlSetting" data-toggle="tab">控制设置</a></li>
				<li><a href="#eventSetting" data-toggle="tab">程序设置</a></li>
				<li><a href="#templateContent" data-toggle="tab">模板设置</a></li>
				<li><a href="#panelContent" onclick="dele_model(this)" id="panel_set" data-toggle="tab">统计面板设置</a></li>
			</ul>
			<div id="contTabTabContent" class="tab-content" style="overflow: hidden;width: 100%;height: 550px;">
				<div class="tab-pane fade in active" id="format">
					<fieldset>
						<legend>格式设置：</legend>
						<div class="span12">
							<div class="span6">
								<div style="background:#f1f1f1;">
									<div class="control-group">
										<label for="fontFamily" class="control-label">默认字体：</label>
										<div class="controls">
											<select name="fontFamily" id="fontFamily"
												data-bind="options: fontfamilies,
                       optionsText: 'text',
                       optionsValue: 'value'">
											</select>

										</div>
									</div>
									<div class="control-group">
										<label for="fontSize" class="control-label">默认字号：</label>
										<div class="controls">
											<select name="fontSize" id="fontSize"
												data-bind="options: fontSizes,
                       optionsText: 'value',
                       optionsValue: 'value'">
											</select>
										</div>
									</div>
									<div class="control-group">
										<label for="color" class="control-label">默认颜色：</label>
										<div class="controls">
											<div class="input-prepend colorselector">
												<input type="text" placeholder="默认颜色" id="color"
													name="color" /> <span class="add-on"><i></i></span>
											</div>

										</div>
									</div>
								</div>
								<div style="background:#f1f1f1;">
									<div class="control-group">
										<label for="showlabel" class="control-label">显示标签？</label>
										<div class="controls">
											<select name="showlabel" id="showlabel">
												<option value="true">显示</option>
												<option value="false">隐藏</option>
											</select>
										</div>
									</div>
									<div class="control-group label-group">
										<label for="labelFontSize" class="control-label">标签字号：</label>
										<div class="controls">
											<select name="labelFontSize" id="labelFontSize"
												data-bind="options: fontSizes,
                       optionsText: 'value',
                       optionsValue: 'value'">
											</select>
										</div>
									</div>
									<div class="control-group label-group">
										<label for="labelClassName" class="control-label">标签样式：</label>
										<div class="controls">
											<input type="text" placeholder="标签样式名称" id="labelClassName"
												name="labelClassName" data-bind="" />
										</div>
									</div>
									<div class="control-group label-group">
										<label for="labelColor" class="control-label">标签颜色：</label>
										<div class="controls">
											<div class="input-prepend colorselector">
												<input type="text" placeholder="标签颜色" id="labelColor"
													name="labelColor" /> <span class="add-on"><i></i></span>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="span6">
								<div style="background:#f1f1f1;">
									<div class="control-group">
										<label for="showValue" class="control-label">显示测量值？</label>
										<div class="controls">
											<select name="showValue" id="showValue">
												<option value="false">隐藏</option>
												<option value="true">显示</option>
											</select>
										</div>
									</div>
									<div class="control-group value-group">
										<label for="valueFontSize" class="control-label">数值字号：</label>
										<div class="controls">
											<select name="valueFontSize" id="valueFontSize"
												data-bind="options: fontSizes,
                       optionsText: 'value',
                       optionsValue: 'value'">
											</select>
										</div>
									</div>
									<div class="control-group value-group">
										<label for="valueClassName" class="control-label">数值样式：</label>
										<div class="controls">
											<input type="text" placeholder="数值样式名称" id="valueClassName"
												name="valueClassName" data-bind="" />
										</div>
									</div>
									<div class="control-group value-group">
										<label for="valueColor" class="control-label">数值颜色：</label>
										<div class="controls">
											<div class="input-prepend colorselector">
												<input type="text" placeholder="valueColor" id="valueColor"
													name="valueColor" /> <span class="add-on"><i></i></span>
											</div>
										</div>
									</div>
								</div>
								<div style="background:#f1f1f1;">
									<div class="control-group">
										<label for="showMeasureunit" class="control-label">显示计量单位？</label>
										<div class="controls">
											<select name="showMeasureunit" id="showMeasureunit">
												<option value="false">隐藏</option>
												<option value="true">显示</option>
											</select>
										</div>
									</div>
									<div class="control-group value-group">
										<label for="unitFontSize" class="control-label">单位字号：</label>
										<div class="controls">
											<select name="unitFontSize" id="unitFontSize"
												data-bind="options: fontSizes,
                       optionsText: 'value',
                       optionsValue: 'value'">
											</select>
										</div>
									</div>
									<div class="control-group value-group">
										<label for="unitClassName" class="control-label">单位样式：</label>
										<div class="controls">
											<input type="text" placeholder="计量单位样式名称" id="unitClassName"
												name="unitClassName" data-bind="" />
										</div>
									</div>
									<div class="control-group value-group">
										<label for="unitColor" class="control-label">单位颜色：</label>
										<div class="controls">
											<div class="input-prepend colorselector">
												<input type="text" placeholder="计量单位颜色" id="unitColor"
													name="unitColor" /> <span class="add-on"><i></i></span>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</fieldset>
				</div>
				<div class="tab-pane fade" id="statusSetting">
					<fieldset>
						<legend>值类型：</legend>
						<div class="control-group">
							<label for="datatype" class="control-label">值类型：</label>
							<div class="controls">
								<select id="datatype" name="datatype">
									<option>开关量</option>
									<option>模拟值</option>
									<option>字符串</option>
								</select>
							</div>
						</div>
						<div class="control-group">
							<label for="hint" class="control-label">提示信息：</label>
							<div class="controls">
								<input type="text" placeholder="提示信息" id="hint" name="hint"
									data-bind="" />
							</div>
						</div>

						<div class="control-group state-indication">
							<label for="state" class="control-label">状态提示：</label>
							<div class="controls">
								<select id="state" name="state" class="state-indication">
									<option value="icon">图标</option>
									<option value="text">文字</option>
								</select>
							</div>
						</div>
						<div class="control-group switch">
							<label for="state1" class="control-label">基础图标：</label>
							<div class="controls">
								<input type="hidden" id="state1" name="state1" data-bind="" />
								<i id="state1_icon"></i> <a id="state1_modal" href="#"
									role="button" class="btn" data-toggle="modal">浏览</a> 
									<a href="#" class="btn" onclick="reset('state1')">重置</a>
							</div>
						</div>
<!-- 						<div class="control-group switch"> -->
<!-- 							<label for="state2" class="control-label">关闭图标：</label> -->
<!-- 							<div class="controls"> -->
<!-- 								<input type="hidden" id="state2" name="state2" data-bind="" /> -->
<!-- 								<i id="state2_icon"></i> <a id="state2_modal" href="#" -->
<!-- 									role="button" class="btn" data-toggle="modal">浏览</a>  -->
<!-- 									<a href="#" class="btn" onclick="reset('state2')">重置</a> -->
<!-- 							</div> -->
<!-- 						</div> -->

						<div class="control-group switch_text">
							<label for="state1_text" class="control-label">打开时(1)：</label>
							<div class="controls">
								<input type="text" id="state1_text" name="state1_text"
									placeholder="打开时文字" data-bind="" />
							</div>
						</div>
						<div class="control-group switch_text">
							<label for="state2_text" class="control-label">关闭时(0)：</label>
							<div class="controls">
								<input type="text" id="state2_text" name="state2_text"
									placeholder="关闭时文字" data-bind="" />
							</div>
						</div>

						<div class="control-group number">
							<label for="number_max" class="control-label">最大值：</label>
							<div class="controls">
								<input type="number" placeholder="请输入样式或图标地址" id="number_max"
									name="number_max" data-bind="" />

							</div>
						</div>

						<div class="control-group number">
							<label for="number_min" class="control-label">最小值：</label>
							<div class="controls">
								<input type="number" placeholder="请输入样式或图标地址" id="number_min"
									name="number_min" data-bind="" />

							</div>
						</div>

					</fieldset>
				</div>
				<div class="tab-pane fade" id="controlSetting">
					<div class="control-group">
						<label for="ifaceinvoke" class="control-label">访问属性：</label>
						<div class="controls">
							<select id="access" name="access">
								<option value="read">只读</option>
								<option value="read,write">读写</option>
							</select>
						</div>
					</div>
					<div class="control-group">
						<label for="ifaceinvoke" class="control-label">操作元素：</label>
						<div class="controls">
							<select id="ctlElement" name="ctlElement">
								<option value="">无(在图标上直接操作)</option>
								<option value="textbox">输入框</option>
								<option value="switch">开关</option>
								<option value="modal">弹出框</option>
							</select>
						</div>
					</div>
				</div>
				<div class="tab-pane fade" id="eventSetting">
					<div class="control-group">
						<label for="ifaceinvoke" class="control-label">调用接口：</label>
						<div class="controls">
							<input type="text" name="ifacename" id="ifacename"/>
						</div>
					</div>
					<div class="control-group">
						<label for="ifaceexitclass" class="control-label">请求处理类：</label>
						<div class="controls">
							<input type="text" id="ifaceexitclass" placeholder="package.classname" name="ifaceexitclass" />
						</div>
					</div>
					<div class="control-group">
						<label for="ifacedesc" class="control-label">接口描述：</label>
						<div class="controls">
							<input type="text" id="ifacedesc" name="ifacedesc" />
						</div>
					</div>
				</div>
				<div class="tab-pane fade" id="templateContent">
					<!-- textarea class="template" name="template"></textarea-->
					<div style="height: 30px;">
						<a id="insertButton" href="javascript:void(0)" class="easyui-linkbutton">button</a>
						<a id="insertInput" href="javascript:void(0)" class="easyui-linkbutton">input</a>
						<a id="insertText" href="javascript:void(0)" class="easyui-linkbutton">Text</a>
						<a id="insertLine" href="javascript:void(0)" class="easyui-linkbutton">line</a>
						<a id="insertTitle" href="javascript:void(0)" class="easyui-linkbutton">title</a>
						<a id="insertSelect" href="javascript:void(0)" class="easyui-linkbutton">select</a>
					</div>
					<div id="setting2" style="height: 300px;width: 98%;"></div>
				</div>
				<div class="tab-pane fade" id="panelContent">
				<!-- 统计面板设计 -->
					<div class="control-group">
						<label for="exit_title" class="control-label">是否需要标题</label>
						<div class="controls">
							<select id="exit_title" style="width:55px;">
								<option value="0">否</option>
								<option value="1">是</option>
							</select>
						</div>
					</div>
					<div class="control-group">
						<div class="line_model">
							<label class="control-label">第<span class="line_n">1</span>行内容</label>
							<div class="controls">
								<div class="ele_model">
									<select class="value" style="width:108px;" onchange="show_colorbox(this)">
										<option value="0">手写数据</option>
										<option value="1">正常状态</option>
										<option value="2">故障状态</option>
										<option value="3">报警状态</option>
										<option value="4">自定义状态</option>
									</select>
									<input type="text" class="name" placeholder="请键入内容名称">
									<input type="text" style="width:28px;" title="请输入数据单位" class="unit" placeholder="单位">
									<div class="input-prepend colorselector" style="display:none;">
										<input type="text" placeholder="计量单位颜色" class="color"/>
											<span class="add-on"><i></i></span>
									</div>
									<button onclick="dele_ele(this)">删除</button>
								</div>
								<input type="button" class="addAele" onclick="add_ele(this)" value="新增行内容"/>
								<button onclick="dele_line(this)">删除行</button>
							</div>
						</div>
						<input type="button" id="addAline" onclick="add_line(this)" value="新增面板行"/>
					</div>
				</div>
				<!-- 设备设置 -->
				<div class="tab-pane fade" id="deviceSetting">
					<div class="control-group">
						<input type="hidden" id="attribute" name="attribute" />
						<div class="device_left controls" id="device_attr">
							
							 <!-- ko foreach: extendList -->
							 <fieldset>
								<legend>设备属性<span data-bind="text: $index"> </span>：</legend>
								<div class="control-group">
									<label class="control-label">属性：</label>
									<div class="controls device_float">
										<select class="attrValue"
											data-bind="options: $root.attrlists,
											value: d_attrValue,
											optionsText: 'key',
					                        optionsValue: 'value',
					                        attr: {index: $index},
											select2: { width : '300px' }">
										</select>
									</div>
									
									<label class="control-label">值类型：</label>
									<div class="controls">
										<select class="d_datatype" id="cate"  data-bind="options: $root.d_valueType, value: d_selValueType, optionsText: 'value', optionsValue: 'value', attr: {index: $index}">
										</select>
									</div>
								</div>

								<div class="control-group">
									<label class="control-label">状态提示：</label>
									<div class="controls device_float">
										<select style="width: 301px;" data-bind="options: $root.d_stateTip, value: d_selStateTip, optionsText: 'text', optionsValue: 'value', attr: {index: $index}" class="d_state state-indication">
										</select>
									</div>
									
									<label class="control-label">提示信息：</label>
									<div class="controls">
										<input type="text" placeholder="提示信息" tip="d_tip" data-bind="value: d_tip" />
									</div>
								</div>
		
								<div class="control-group d_switch">
									<label class="control-label">对应值：</label>
									<div class="controls">
										<input style="width: 284px;" type="text" title="当值类型为[开关量]或[模拟量]时显示下方图标对应的值" placeholder="显示下方图标对应的值" data-bind="value: d_icon_value" />
									</div>
								</div>
								<div class="control-group d_switch">
									<label class="control-label">对应图标：</label>
									<div class="controls">
										<input type="hidden" class="d_state1" tip="d_state1" data-bind="value: d_icon" />
										<i data-bind="css: d_icon"></i> 
										<a id="d_state1_modal" data-bind="attr: {index: $index}" href="#" role="button" class="btn" data-toggle="modal">浏览</a> 
										<a href="#" class="btn reset" onclick="resetDynamic('d_state1')">重置</a>
									</div>
								</div>

								<div class="control-group d_switch_text hide">
									<label class="control-label">打开时(1)：</label>
									<div class="controls">
										<input style="width: 284px;" type="text" placeholder="打开时文字" data-bind="value: d_openText" />
									</div>
								</div>

								<!-- li Hui Hui -->
								<div class="control-group">
									<label class="control-label">最大值：</label>
									<div class="controls device_float">
										<input style="width: 284px;" type="number" title="当值类型为[模拟量]时需要填值" placeholder="当值类型为[模拟量]时需要填值" data-bind="value: m_maxValue" />
									</div>

									<label class="control-label">最小值：</label>
									<div class="controls">
										<input type="number" title="当值类型为[模拟量]时需要填值" placeholder="当值类型为[模拟量]时需要填值" data-bind="value: m_minValue" />
									</div>
								</div>

								<div class="control-group">
									<label class="control-label"><a href="#" class="btn"  data-bind="click: $parent.removeRow">删除</a></label>
								</div>

							</fieldset>
							<!-- /ko -->
							
							<!-- , enable: extendList().length < 5 -->
							<button data-bind="click: addRow">添加</button>
						</div>
					</div>
				</div>
			
			<hr />
			<div class="control-group">
				<div class="controls">
					<button type="button" class="btn btn-default"
						data-bind="click:saveControl">保存</button>
					<button type="button" class="btn btn-default"
						data-bind="click:removeControl">删除</button>
					<button type="button" class="btn btn-default" data-bind="click:doClick">自定义样式</button>
				</div>
				
			</div>
			<input type="hidden" id="controluid">
			
			</div>

			
		</form>
		
	<!-- 自定义样式弹出框 LiHuiHui-->
		<form method="post"  action="${pageContext.request.contextPath}/self/style/">
		<table id="alertstyle" style="display:none;position: absolute;left:700px;top:300px;" >
			<tr><td><textarea rows="30"  style="width:400px;height:500px" name="definedStyle" id="definedStyle" ></textarea></td></tr>
			<tr><td style="margin-left:40px;"><button type="submit"  data-bind="">保存</button><input type="button" onclick="closeDialog()" value="关闭"></td></tr>
		</table>

		</form>
		
		<script type="text/javascript">
				function doClick(){
					var returnValue = document.getElementById("alertstyle");
		        	returnValue.style.display = 'block';
		          	jQuery.ajax({
		    			url : CONTEXT_PATH + "/self/style/read",
		    			type : "post",
		    			success : function(data) {
		    				$("#definedStyle").val(data);
		    			},error:function(data){
		    				alert("有错误");
		    			}
		    		});
				}
				function closeDialog()
				{
					 if (confirm('你确认要关闭当前窗口吗?')) {
						 var returnValue = document.getElementById("alertstyle");
						 returnValue.style.display = 'none';
			           }
				}
				</script>
	</div>

	<!-- Modal -->
	<div id="myModal" class="modal hide fade middle" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">×</button>
			<h3 id="myModalLabel">图标列表</h3>
		</div>
		<div class="modal-body">
			<fieldset>
				<legend>直梯</legend>
				<ul class="the-icons clearfix">
					<li><i class="msem_online"></i> 直梯(正常)</li>
					<li><i class="msem_offline"></i> 直梯(离线)</li>
					<li><i class="cicon cicon84 cicon_32_60"></i> 直(报警)</li>
				</ul>
			</fieldset>
			<fieldset>
				<legend>扶梯</legend>
				<ul class="the-icons clearfix">
					<li><i class="cicon cicon86 cicon_32_48"></i> 扶梯(上)</li>
					<li><i class="cicon cicon85 cicon_32_48"></i> 扶梯(下)</li>
					<li><i class="cicon cicon87 cicon_32_48"></i> 扶梯(报警)</li>
				</ul>
			</fieldset>
			<fieldset>
				<legend>门禁</legend>
				<ul class="the-icons clearfix">
					<li><i class="cicon cicon37 cicon_32_48"></i> 门禁(报警)</li>
					<li><i class="warning_gif"></i> 门禁(报警动态)</li>
					<li><i class="cicon cicon39 cicon_32_48"></i> 门禁(打开)</li>
					<li><i class="cicon cicon38 cicon_32_48"></i> 门禁(关闭)</li>
					<li><i class="cicon cicon40 cicon_32_48"></i> 门禁(故障)</li>
					
				</ul>
			</fieldset>
			<fieldset>
				<legend>巡更</legend>
				<ul class="the-icons clearfix">
					<li><i class="cicon cicon41"></i> 巡更(正常)</li>
					<li><i class="cicon cicon43"></i> 巡更(未联网)</li>
					<li><i class="cicon cicon42"></i> 巡更((故障)</li>
				</ul>
			</fieldset>
			<fieldset>
				<legend>冷源</legend>
				<ul class="the-icons clearfix">
					<li><i class="meter-coolingsource-normal"></i> 冷源(正常)</li>
					<li><i class="meter-coolingsource-fault"></i> 冷源(故障)</li>
				</ul>
			</fieldset>
			<fieldset>
				<legend>消防报警</legend>
				<ul class="the-icons clearfix">
					<li><i class="cicon cicon01"></i> 正常</li>
					<li><i class="cicon cicon02"></i> 未联网</li>
					<li><i class="cicon cicon03"></i> 报警</li>
				</ul>
			</fieldset>
			<fieldset>
				<legend>感温电缆</legend>
				<ul class="the-icons clearfix">
					<li><i class="cicon cicon04"></i> 正常</li>
					<li><i class="cicon cicon05"></i> 未联网</li>
					<li><i class="cicon cicon06"></i> 报警</li>
					
					
				</ul>
			</fieldset>
			<fieldset>
				<legend>卷帘门</legend>
				<ul class="the-icons clearfix">
					<li><i class="cicon cicon07"></i> 正常</li>
					<li><i class="cicon cicon08"></i> 未联网</li>
					<li><i class="cicon cicon09"></i> 报警</li>
				</ul>
			</fieldset>
			<fieldset>
				<legend>漏电火灾</legend>
				<ul class="the-icons clearfix">
					<li><i class="cicon cicon10"></i> 正常</li>
					<li><i class="cicon cicon11"></i> 未联网</li>
					<li><i class="cicon cicon12"></i> 报警</li>
				</ul>
			</fieldset>
			<fieldset>
				<legend>消防水炮</legend>
				<ul class="the-icons clearfix">
					<li><i class="cicon cicon13"></i> 正常</li>
					<li><i class="cicon cicon14"></i> 未联网</li>
					<li><i class="cicon cicon15"></i> 报警</li>
				</ul>
			</fieldset>
			<fieldset>
				<legend>气体灭火</legend>
				<ul class="the-icons clearfix">
					<li><i class="cicon cicon139"></i> 正常</li>
					<li><i class="cicon cicon140"></i> 未联网</li>
					<li><i class="cicon cicon141"></i> 报警</li>
				</ul>
			</fieldset>
			<fieldset>
				<legend>燃气报警 </legend>
				<ul class="the-icons clearfix">
					<li><i class="cicon cicon142"></i> 正常</li>
					<li><i class="cicon cicon143"></i> 未联网</li>
					<li><i class="cicon cicon144"></i> 报警</li>
				</ul>
			</fieldset>
			<fieldset>
				<legend>消火栓</legend>
				<ul class="the-icons clearfix">
					<li><i class="cicon cicon13"></i> 正常</li>
					<li><i class="cicon cicon14"></i> 未联网</li>
					<li><i class="cicon cicon15"></i> 报警</li>
				</ul>
			</fieldset>
			<fieldset>
				<legend>烟感</legend>
				<ul class="the-icons clearfix">
					<li><i class="cicon cicon16"></i> 正常</li>
					<li><i class="cicon cicon17"></i> 未联网</li>
					<li><i class="cicon cicon18"></i> 报警</li>
				</ul>
			</fieldset>
			<fieldset>
				<legend>温感</legend>
				<ul class="the-icons clearfix">
					<li><i class="cicon cicon19"></i> 正常</li>
					<li><i class="cicon cicon20"></i> 未联网</li>
					<li><i class="cicon cicon21"></i> 报警</li>
				</ul>
			</fieldset>
			<fieldset>
				<legend>手报</legend>
				<ul class="the-icons clearfix">
					<li><i class="cicon cicon22"></i> 正常</li>
					<li><i class="cicon cicon23"></i> 未联网</li>
					<li><i class="cicon cicon24"></i> 报警</li>
				</ul>
			</fieldset>
			<fieldset>
				<legend>半球</legend>
				<ul class="the-icons clearfix">
					<li><i class="cicon cicon25"></i> 正常</li>
					<li><i class="cicon cicon26"></i> 未联网</li>
					<li><i class="cicon cicon27"></i> 故障</li>
				</ul>
			</fieldset>
			<fieldset>
				<legend>球机</legend>
				<ul class="the-icons clearfix">
					<li><i class="cicon cicon28 cicon_36_40"></i> 正常</li>
					<li><i class="cicon cicon29 cicon_36_40"></i> 未联网</li>
					<li><i class="cicon cicon30 cicon_36_40"></i> 故障</li>
				</ul>
			</fieldset>
			<fieldset>
				<legend>枪机</legend>
				<ul class="the-icons clearfix">
					<li><i class="cicon cicon31"></i> 正常</li>
					<li><i class="cicon cicon32"></i> 未联网</li>
					<li><i class="cicon cicon33"></i> 故障</li>
				</ul>
			</fieldset>
			<fieldset>
				<legend>防盗报警</legend>
				<ul class="the-icons clearfix">
					<li><i class="cicon cicon34"></i> 正常</li>
					<li><i class="cicon cicon35"></i> 未联网</li>
					<li><i class="cicon cicon36"></i> 故障</li>
				</ul>
			</fieldset>
			<fieldset>
				<legend>冷源</legend>
				<ul class="the-icons clearfix">
					<li><i class="cicon cicon44"></i> 正常</li>
					<li><i class="cicon cicon45"></i> 停机</li>
					<li><i class="cicon cicon46"></i> 故障</li>
				</ul>
			</fieldset>
			<fieldset>
				<legend>空调机组</legend>
				<ul class="the-icons clearfix">
					<li><i class="cicon cicon47"></i> 正常</li>
					<li><i class="cicon cicon48"></i> 停机</li>
					<li><i class="cicon cicon49"></i> 故障</li>
				</ul>
			</fieldset>
			<fieldset>
				<legend>新风机组</legend>
				<ul class="the-icons clearfix">
					<li><i class="cicon cicon50"></i> 正常</li>
					<li><i class="cicon cicon51"></i> 停机</li>
					<li><i class="cicon cicon52"></i> 故障</li>
				</ul>
			</fieldset>
			<fieldset>
				<legend>风机盘管</legend>
				<ul class="the-icons clearfix">
					<li><i class="cicon cicon53"></i> 正常</li>
					<li><i class="cicon cicon54"></i> 停机</li>
					<li><i class="cicon cicon55"></i> 故障</li>
				</ul>
			</fieldset>
			<fieldset>
				<legend>通风机组</legend>
				<ul class="the-icons clearfix">
					<li><i class="cicon cicon56"></i> 正常</li>
					<li><i class="cicon cicon57"></i> 停机</li>
					<li><i class="cicon cicon58"></i> 故障</li>
				</ul>
			</fieldset>
			<fieldset>
				<legend>风幕</legend>
				<ul class="the-icons clearfix">
					<li><i class="cicon cicon59"></i> 正常</li>
					<li><i class="cicon cicon60"></i> 停机</li>
					<li><i class="cicon cicon61"></i> 故障</li>
				</ul>
			</fieldset>
			<fieldset>
				<legend>室内环境</legend>
				<ul class="the-icons clearfix">
					<li><i class="cicon cicon71 cicon_35_68"></i> 温度小于25度</li>
					<li><i class="cicon cicon72 cicon_35_68"></i> 温度大于等于26度</li>
					<li><i class="cicon cicon73 cicon_35_60"></i> 湿度监测</li>
					<li><i class="cicon cicon74"></i> 照度监测</li>
					<li><i class="cicon cicon75 cicon_32_14"></i> CO2浓度监测</li>
					<li><i class="cicon cicon76 cicon_32_14"></i> CO浓度监测</li>
				</ul>
			</fieldset>
			<fieldset>
				<legend>给水排水</legend>
				<ul class="the-icons clearfix">
					<li><i class="cicon cicon62"></i> 正常</li>
					<li><i class="cicon cicon63"></i> 停机</li>
					<li><i class="cicon cicon64"></i> 故障</li>
					
				</ul>
			</fieldset>
			<fieldset>
				<legend>变配电</legend>
				<ul class="the-icons clearfix">
					<li><i class="cicon cicon65"></i> 正常</li>
					<li><i class="cicon cicon66"></i> 停机</li>
					<li><i class="cicon cicon67"></i> 故障</li>
					<li><i class="cicon cicon132"></i> 透明图</li>
				</ul>
			</fieldset>
			<fieldset>
				<legend>公共照明</legend>
				<ul class="the-icons clearfix">
					<li><i class="cicon cicon68"></i> 开启</li>
					<li><i class="cicon cicon69"></i> 关闭</li>
					<li><i class="cicon cicon70"></i> 故障</li>
					<li><i class="cicon cicon77"></i> 基础照明A开启</li>
					<li><i class="cicon cicon78"></i> 基础照明A关闭</li>
				</ul>
			</fieldset>
						<fieldset>
				<legend>公共照明-回路</legend>
				<ul class="the-icons clearfix">
					<li><i class="cicon cicon89 cicon_500_100"></i> 开启</li>
					<li><i class="cicon cicon90 cicon_500_100"></i> 关闭</li>
					<li><i class="cicon cicon91 cicon_500_100"></i> 故障报警</li>

				</ul>
			</fieldset>
			<fieldset>
				<legend>公共照明-分类</legend>
				<ul class="the-icons clearfix">
					<li><i class="cicon cicon148"></i> 条带灯_开</li>
					<li><i class="cicon cicon149"></i> 条带灯_关</li>
					<li><i class="cicon cicon150"></i> 筒灯_开</li>
					<li><i class="cicon cicon151"></i> 筒灯_关</li>
					<li><i class="cicon cicon152"></i> 装饰灯_开</li>
					<li><i class="cicon cicon153"></i> 装饰灯_关</li>

				</ul>
			</fieldset>
			<fieldset>
				<legend>夜景照明</legend>
				<ul class="the-icons clearfix">
					<li><i class="cicon cicon154 cicon322_84"></i></li>
				</ul>
			</fieldset>
			<fieldset>
				<legend>客流统计</legend>
				<ul class="the-icons clearfix">
					<li><i class="cicon cicon79"></i> 正常运行</li>
					<li><i class="cicon cicon80"></i> 未联网</li>
					<li><i class="cicon cicon81"></i> 故障报警</li>

				</ul>
			</fieldset>
			<fieldset>
				<legend>信息发布</legend>
				<ul class="the-icons clearfix">
					<li><i class="cicon cicon98 cicon88_40"></i> 正常运行</li>
					<li><i class="cicon cicon99 cicon88_40"></i> 未联网</li>
					<li><i class="cicon cicon100 cicon88_40"></i> 故障报警</li>

				</ul>
			</fieldset>
			<fieldset>
				<legend>背景音乐</legend>
				<ul class="the-icons clearfix">
					<li><i class="cicon cicon92"></i> 正常运行</li>
					<li><i class="cicon cicon93"></i> 未联网</li>
					<li><i class="cicon cicon94"></i> 故障报警</li>
				</ul>
			</fieldset>
			<fieldset>
				<legend>能源管理</legend>
				<ul class="the-icons clearfix">
					<li><i class="cicon cicon95"></i> 正常运行</li>
					<li><i class="cicon cicon96"></i> 未联网</li>
					<li><i class="cicon cicon97"></i> 故障报警</li>

				</ul>
			</fieldset>
			<fieldset>
				<legend>遮阳帘</legend>
				<ul class="the-icons clearfix">
					<li><i class="cicon cicon105"></i> 正常运行</li>
				</ul>
			</fieldset>
			<fieldset>
				<legend>停车管理</legend>
				<ul class="the-icons clearfix">
					<li><i class="cicon cicon106 cicon20_40"></i> 有车</li>
					<li><i class="cicon cicon123 cicon20_40"></i> 无车</li>
					<li><i class="cicon cicon145 cicon20_20"></i> 探头正常</li>
					<li><i class="cicon cicon146 cicon20_20"></i> 探头报警</li>
					<li><i class="cicon cicon147 cicon20_20"></i> 探头离线</li>
				</ul>
			</fieldset>
			<fieldset>
				<legend>视频监控管理</legend>
				<ul class="the-icons clearfix">
					<li><i class="cicon cicon107"></i></li>
					<li><i class="cicon cicon108"></i></li>
					<li><i class="cicon cicon109"></i></li>
					<li><i class="cicon cicon110"></i></li>
					<li><i class="cicon cicon111"></i></li>
					<li><i class="cicon cicon112"></i></li>
					<li><i class="cicon cicon113"></i></li>
					<li><i class="cicon cicon114"></i></li>
					<li><i class="cicon cicon115"></i></li>
					<li><i class="cicon cicon116"></i></li>
					<li><i class="cicon cicon117"></i></li>
					<li><i class="cicon cicon118"></i></li>
					<li><i class="cicon cicon119"></i></li>
					<li><i class="cicon cicon120"></i></li>
					<li><i class="cicon cicon121"></i></li>
					<li><i class="cicon cicon122"></i></li>
					<li><i class="cicon cicon129"></i>扳手</li>
				</ul>
			</fieldset>
			
		</div>
		<div class="modal-footer">
			<button class="btn btn-primary">选择</button>
			<button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
		</div>
	</div>

</div>
<div id="addBuutonDialog" style="display:none;padding:5px;width:400px;height:auto;" title="添加按钮">
	<label class="lbInfo" for="buttonname">名称：</label>
	<input id="buttonname" type="text" class="easyui-validatebox"/>
</div> 

<div id="addInputDialog" style="display:none;padding:5px;width:400px;height:auto;" title="添加文本框">
	<label class="lbInfo" for="inputname">名称：</label>
	<input id="inputname" type="text" class="easyui-validatebox"/>
</div>

<div id="addTextDialog" style="display:none;padding:5px;width:400px;height:auto;" title="添加文本">
	<label class="lbInfo" for="textname">名称：</label>
	<input id="textname" type="text" class="easyui-validatebox"/>
</div>

<div id="addLineDialog" style="display:none;padding:5px;width:400px;height:auto;" title="添加水平线">
	<label class="lbInfo">宽度：</label>
	<input id="linewidth" type="text" class="easyui-validatebox"/>
</div>

<div id="addTitleDialog" style="display:none;padding:5px;width:400px;height:auto;" title="添加文本">
	<div style="width:60%;display: inline-table;">
		<label class="lbInfo" for="titlecontent">title内容：</label>
		<input id="titlecontent" type="text" class="easyui-validatebox"/>
		<label class="lbInfo" for="titlesize">字号：</label>
		<select id="titlesize" onchange="fontchange(this)">
			<option value="18px">18px</option>
			<option value="20px">20px</option>
			<option value="24px">24px</option>
			<option value="36px">36px</option>
		</select>
	</div>
	<div style="width:38%;display:inline-table;height:129px;text-align:center;">
		<span id="testchar">正</span>
	</div>
</div>

<div id="addSelectDialog" style="display:none;padding:5px;width:400px;height:auto;" title="添加下拉列表">
	<fieldset>
		<legend>选项名称 <button>添加</button><button>删除</button></legend>
		<input id="textname1" type="text" class="easyui-validatebox" value="textname1"/>
		<input id="textname2" type="text" class="easyui-validatebox" value="textname2"/>
		<input id="textname3" type="text" class="easyui-validatebox" value="textname3"/>
	</fieldset>
	
	<label></label>
</div>

<!-- 配置文件 -->
<script src="<spring:url value="/resources/plugins/ueditor/ueditor.config.js"></spring:url>"></script>
<!-- 编辑器源码文件 -->
<script src="<spring:url value="/resources/plugins/ueditor/ueditor.all.js"></spring:url>"></script>
<!-- 语言包文件(建议手动加载语言包，避免在ie下，因为加载语言失败导致编辑器加载失败) -->
<script src="<spring:url value="/resources/plugins/ueditor/lang/zh-cn/zh-cn.js"></spring:url>"></script>
<script type="text/javascript">
$(window).load(function() {
	UE.getEditor('setting2');
});
$(document).ready(function() {
	//插入按钮元素
	jQuery("#insertButton").click(function() {
		$('#addBuutonDialog').show();
		$('#addBuutonDialog').dialog({
			collapsible : true,
			minimizable : true,
			maximizable : true,
			toolbar : [ ],
			buttons : [ {
				text : '提交',
				iconCls : 'icon-ok',
				handler : function() {
					var onclickStr="panelButtionEvent(this)";
					//获取button的个数并加1
					var num = ($("#ueditor_0").contents().find("button").length)+1;
					var value = "<button class='mb_button' id='panel_button_"+num+"' script_com='' onclick="+onclickStr+">"+$('#buttonname').val()+"</button>";
					UE.getEditor('setting2').execCommand('insertHtml',value);
					$('#addBuutonDialog').dialog('close');
				}
			}, {
				text : '取消',
				handler : function() {
					$('#addBuutonDialog').dialog('close');
				}
			} ]
		});
	});
	
	
	//插入文本框元素
	jQuery("#insertInput").click(function() {
		$('#addInputDialog').show();
		$('#addInputDialog').dialog({
			collapsible : true,
			minimizable : true,
			maximizable : true,
			toolbar : [ ],
			buttons : [ {
				text : '提交',
				iconCls : 'icon-ok',
				handler : function() {
					var onclickStr="panelInputEvent(this)";
					var dbonclickStr="panelInputOndblclickEvent(this)";
					var bind_val="'value: "+$('#inputname').val()+"'";
					var value = "<input id="+$('#inputname').val()+" name="+$('#inputname').val()+" value='' onclick="+onclickStr+" ondblclick="+dbonclickStr+" style='width:50px;'>";
					UE.getEditor('setting2').execCommand('insertHtml',value);
					$('#addInputDialog').dialog('close');
				}
			}, {
				text : '取消',
				handler : function() {
					$('#addInputDialog').dialog('close');
				}
			} ]
		});
	}); 
	
	//插入文本元素
	jQuery("#insertText").click(function() {
		$('#addTextDialog').show();
		$('#addTextDialog').dialog({
			collapsible : true,
			minimizable : true,
			maximizable : true,
			toolbar : [ ],
			buttons : [ {
				text : '提交',
				iconCls : 'icon-ok',
				handler : function() {
					var onclickStr="panelTextEvent(this)";
					var value = "<input id="+$('#textname').val()+" class='mb_text' readonly='true' onclick="+onclickStr+" script_com='' name="+$('#textname').val()+" style='width:50px;border:0px;background-color:transparent;'/>";
					UE.getEditor('setting2').execCommand('insertHtml',value);
					$('#addTextDialog').dialog('close');
				}
			}, {
				text : '取消',
				handler : function() {
					$('#addTextDialog').dialog('close');
				}
			} ]
		});
	});
	
	//插入下拉框元素
	jQuery("#insertSelect").click(function() {
		$('#addSelectDialog').show();
		$('#addSelectDialog').dialog({
			collapsible : true,
			minimizable : true,
			maximizable : true,
			toolbar : [ ],
			buttons : [ {
				text : '提交',
				iconCls : 'icon-ok',
				handler : function() {
				
					var optionval="";
					$("#insertSelect input").each(function(i,obj){
						optionval+="<option id='"+obj.id+"' value='"+obj.value+"'>"+obj.value+"</option>";
		            });
					var bind_val="<select id='test1' name='test1'>"+optionval+"</select>";
				
				alert(bind_val);
					
					var value = "<select id='test1' name='test1'><option id='1' value='1'>1</option><option id='2' value='2'>2</option><option id='3' value='3'>3</option></select>";
					UE.getEditor('setting2').execCommand('insertHtml',value);
					$('#addSelectDialog').dialog('close');
				}
			}, {
				text : '取消',
				handler : function() {
					$('#addSelectDialog').dialog('close');
				}
			} ]
		});
	});
	
	//插入水平线元素
	jQuery("#insertLine").click(function() {
		$('#addLineDialog').show();
		$('#addLineDialog').dialog({
			collapsible : true,
			minimizable : true,
			maximizable : true,
			toolbar : [ ],
			buttons : [ {
				text : '提交',
				iconCls : 'icon-ok',
				handler : function() {
					var value = "<hr width="+$('#linewidth').val()+"/>";
					UE.getEditor('setting2').execCommand('insertHtml',value);
					$('#addLineDialog').dialog('close');
				}
			}, {
				text : '取消',
				handler : function() {
					$('#addLineDialog').dialog('close');
				}
			} ]
		});
	});
	
	//插入标题
	jQuery("#insertTitle").click(function() {
		$('#addTitleDialog').show();
		$('#addTitleDialog').dialog({
			collapsible : true,
			minimizable : true,
			maximizable : true,
			toolbar : [ ],
			buttons : [ {
				text : '提交',
				iconCls : 'icon-ok',
				handler : function() {
					var clickStr="panelTitleEvent(this)";
					var value = "<span class='mb_title' onclick="+clickStr+" style='font-size:"+$('#titlesize').val()+";width:50px;border:0px;background-color:transparent;'>"+$('#titlecontent').val()+"</span>";
					UE.getEditor('setting2').execCommand('insertHtml',value);
					$('#addTitleDialog').dialog('close');
				}
			}, {
				text : '取消',
				handler : function() {
					$('#addTitleDialog').dialog('close');
				}
			} ]
		});
	});
	
	/*  jQuery("#btnSavePagetag").click(function() {
	  	if(confirm("确认保存移动的坐标点！！！")){
	  		jQuery.ajax({
	          url: baseUrl+"/okcsys/page/addPagetag",
	          type: "post",
	          success: function(data) {
	          //alert(data.message);
	         	}
	     	});
		}
	 });       */

});
function fontchange(obj){
	var num = $(obj).val();
	$("#testchar").css("font-size",num);
}
</script>