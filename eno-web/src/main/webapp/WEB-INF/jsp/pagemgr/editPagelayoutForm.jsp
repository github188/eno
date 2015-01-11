<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>



<c:choose>
	<c:when test="${isNew}">
		<c:set var="method" value="post" />
		<c:set var="editStatus" value="false" />
	</c:when>
	<c:otherwise>
		<c:set var="method" value="put" />
		<c:set var="editStatus" value="true" />
	</c:otherwise>
</c:choose>

<div class="row-fluid">
	<div class="span3">
		<div class="widget" id="navlayout">
			<div class="widget-header">
				<h3 class="icon ">布局列表</h3>

				<button class="btn btn-info pull-right" data-bind="click:newLayout">添加布局</button>
			</div>
			<div class="widget-content" style="height:790px;">
				查找：<input type="text" name="findLayout"
					data-bind="value:keyword,valueUpdate: 'afterkeydown'" />
				<ul data-bind="foreach:filteredItems()"
					class="nav nav-tabs nav-stacked" id="layouts">
					<li><a href="#"
						data-bind="text: layoutname,click:$root.editLayout"></a></li>
				</ul>

			</div>
		</div>

		<!-- /sidebar -->
	</div>
	<div class="span9" id="content">
	

		<c:if test="${not empty message}">
			<div class="alert alert-success">
				<button type="button" class="close" data-dismiss="alert">&times;</button>
				${message}
			</div>
		</c:if>

		<ul class="nav nav-tabs layoutData">
			<li class="active"><a href="#home" data-toggle="tab" onclick="$('#pagedatalist').empty();">页面信息</a></li>
			<li><a href="#profile" data-toggle="tab" onclick="showTagList(this)">数据列表</a></li>
		</ul>
		<div class="tab-content">
			<div class="tab-pane fade in active" id="home">
				<div class="widget">
					<div class="widget-header">
						<h3 class="icon ">页面布局信息</h3>
					</div>

					<div class="widget-content">
						<form:form modelAttribute="pagelayout" method="${method}"
							class="form-horizontal">
							<div class="row">
								<div class="span6">
									<div class="control-group">
										<form:label path="layoutid" class="control-label">布局编号：</form:label>
										<div class="controls">
											<input type="hidden" id="pagelayoutuid" name="pagelayoutuid"
												data-bind="value: currentRow().pagelayoutuid" /> <input
												type="text" id="layoutid" name="layoutid" class="input-mini"
												data-bind="value: currentRow().layoutid" placeholder="自动编号"
												readonly="readonly" />
											<form:input path="layoutname" class="input-medium"
												data-bind="value: currentRow().layoutname"
												placeholder="布局名称" required="required" />

										</div>
									</div>

								</div>
								<div class="span6">
									<div class="control-group">
										<form:label path="menuid" class="control-label">关联菜单：</form:label>

										<div class="controls">
											<form:hidden path="menuid"
												data-bind="value: currentRow().menuid" />
											<form:hidden path="ownerelement"
												data-bind="value: currentRow().ownerelement" />
											<form:input path="menuname" class="input-medium"
												data-bind="value: currentRow().menuname" required="required" />
											<a href="#myModal" class="btn" type="button" role="button"
												data-toggle="modal"> <i class="icon-zoom-in"></i>
											</a> <span class="help-inline">${status.errorMessage}</span>
										</div>
									</div>

								</div>
							</div>

							<div class="row">
								<div class="span6">
									<div class="control-group">
										<label for="listbg" class="control-label">设备列表图：</label>
										<div class="controls">
											<select name="listbg" id="listbg"
												data-bind="options: files,
						                       optionsText: 'key',
						                       optionsValue: 'value',
						                       value: currentRow().listbg,
						                       optionsCaption: '选择...'">
											</select> <a href="#" class="btn" data-toggle="modal"
												data-target="#uploadModal">上传图片</a>
										</div>
									</div>
									<div class="control-group">
										<label for="background" class="control-label">系统结构图：</label>
										<div class="controls">
											<select name="background" id="background"
												data-bind="options: files,
						                       optionsText: 'key',
						                       optionsValue: 'value',
						                       value: currentRow().background,
						                       optionsCaption: '选择...'">
											</select> <a href="#" class="btn" data-toggle="modal"
												data-target="#uploadModal">上传图片</a>
										</div>
									</div>
									<div class="control-group">
										<label for="planbg" class="control-label">系统平面图：</label>
										<div class="controls">
											<select name="planbg"
												id = "planbg"
												data-bind="options: files,
						                       optionsText: 'key',
						                       optionsValue: 'value',
						                       value: currentRow().planbg,
						                       optionsCaption: '选择...'">
											</select> <a href="#" class="btn" data-toggle="modal"
												data-target="#uploadModal">上传图片</a>
										</div>
									</div>
									<div class="control-group">
										<form:label path="hasPaging" class="control-label">是否多页 ：</form:label>
										<div class="controls">
											<select id="hasPaging" data-bind="value: currentRow().hasPaging">
												<option value="0">否</option>
												<option value="1">是</option>
											</select>
										</div>
									</div>
								</div>

								<div class="span6">
									<div class="control-group">
										<form:label path="width" class="control-label">  页面大小：</form:label>
										<div class="controls">
											宽
											<form:input path="width" class="input-mini"
												data-bind="value: currentRow().width" />
											高
											<form:input path="height" class="input-mini"
												data-bind="value: currentRow().height" />
										</div>
									</div>
									<div class="control-group">
										<form:label path="pageindex" class="control-label">  页码序号 ：</form:label>
										<div class="controls">
											<%-- <form:input path="pageindex" class="input-medium"
												data-bind="value: currentRow().pageindex" /> --%>
											<select id="pageindex" name="pageindex" data-bind="value: currentRow().pageindex" >
												<option>0</option>
												<option>1</option>
												<option>2</option>
												<option>3</option>
												<option>4</option>
												<option>5</option>
												<option>6</option>
												<option>7</option>
												<option>8</option>
												<option>9</option>
												<option>10</option>
												<option>11</option>
												<option>12</option>
												<option>13</option>
												<option>14</option>
												<option>15</option>
												<option>16</option>
												<option>17</option>
												<option>18</option>
												<option>19</option>
												<option>20</option>
												<option>21</option>
												<option>22</option>
												<option>23</option>
												<option>24</option>
												<option>25</option>
											</select>
												
										</div>
									</div>									
									<div class="control-group">
										<form:label path="configvalue" class="control-label">  客流编号 ：</form:label>
										<div class="controls">
											<form:input path="configvalue" class="input-medium" title="对应配置表中的paramter和name" placeholder="客流需填此项,#号分隔" data-bind="value: currentRow().configvalue" />
										</div>
									</div>									
									<div class="control-group">
										<form:label path="deviceconfigid" class="control-label">  设备列表 ：</form:label>
										<div class="controls">
<%-- 											<form:input path="deviceconfigid" class="input-medium" title="对应设备配置中的一项" placeholder="对应设备配置中的一项" data-bind="value: currentRow().deviceconfigid" /> --%>
											<select id="deviceconfigid" name="deviceconfigid"></select>
										</div>
									</div>									
								</div>

							</div>
							<div class="row">
								<div class="span8">
									<div class="control-group">
										<form:label path="description" class="control-label">备注说明：</form:label>
										<div class="controls">
											<form:textarea path="description" class="input-xxlarge"
												data-bind="value: currentRow().description" />
										</div>
									</div>
								</div>
							</div>

							<div class="row offset1">
								<button type="button" class="btn btn-primary"
									data-bind="click:saveLayout">保存</button>
								<input type="reset" class="btn btn-default" value="重置" /> <input
									type="button" class="btn btn-warning" value="删除"
									data-bind="enable:currentRow().pagelayoutid!=0,click:removeLayout" />
							</div>
							<div class="alert alert-info span10" style="margin-top:10px;">
								名称解释：<br /> 内容类型：是指在页面中呈现的内容，如设备点位\数据列表\资产数据 <br /> 设备点位：<br />
								数据列表： 外部数据列表，如巡更数据 <br /> 资产数据： 来源于资产台账的数据
							</div>
							<!-- <pre data-bind="text: ko.toJSON(currentRow(), null, 2)"></pre> -->
						</form:form>
					</div>
					<!-- /layout form -->
				</div>
			</div>
			
			<div class="tab-pane fade" id="profile">
				<div class="widget">
					<div class="widget-header">
						<h3 class="icon ">
							设备点列表 [<span data-bind="text: currentRow().layoutname"></span>]
						</h3>
					</div>
					<div class="widget-content">
						<div class="btn-toolbar">
							<button class="btn" type="button" id="quanxuan">全选</button>
							<button class="btn" type="button" id="fanxuan">反选</button>
<!-- 							<button class="btn" type="button" data-bind="click: $root.buildAsset">生成设备</button> -->
<!-- 							<button class="btn" type="button" data-bind="click: $root.newPagetag">添加设备(0)</button> -->
							<button class="btn" id="btnSubmitDrawMap">新建热区(1)</button>
<!-- 							<button class="btn" type="button" data-bind="">添加视频(2)</button> -->
							<button class="btn" type="button" data-bind="click: $root.addAssets">添加资产(3)</button>
							<button class="btn" type="button" data-bind="click: $root.addPassengers">添加客流(5)</button>
							<button class="btn" type="button" data-bind="click: $root.newPanelControl">添加统计面板(98)</button>
							<button class="btn" type="button" data-bind="click: $root.newTagControl">添加组件(99)</button>

							<a href="#" class="btn">批量添加设备</a> <a href="#" class="btn"
								data-bind="click:$root.batchSetTagProp">批量设置</a> <a href="#"
								class="btn btn-success" id="btnSetCoordinate">设置设备点位置</a>
							<button class="btn" type="button" data-bind="click: $root.deletePagetags">批量删除组件</button>
						</div>
						<div class="alert">
							<span data-bind="text:message"></span>
						</div>

						<div>
							资产类别： <select name="q_classstructureid" id="q_classstructureid"
								data-bind="options: classifications,
			                       optionsText: 'description',
			                       optionsValue: 'classificationid',
			                       optionsCaption: 'Choose...',
								   select2: { width : '220px' }"></select>
							<button id="queryListAssets" data-bind="click:queryListAssets"
								class="btn btn-default">查询</button>
						</div>

					</div>
					
				</div>
				<!-- /pagetag list -->
			</div>
			<div id="pagedatalist">
			</div>
					
					
		</div>
	</div>

</div>

<!-- ASSET MODEL -->
<div id="assetModal" class="modal middle hide fade" tabindex="-1"
	role="dialog" aria-labelledby="assetModalLabel" aria-hidden="true" style="width:710px;">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">×</button>
		<h3 id="assetModalLabel">
			资产信息<span data-bind="text:totalAssets"></span>
		</h3>
	</div>
	<div class="modal-body">
		<form id="assetQueryForm">
			资产类别： <select name="classstructureid"
				data-bind="options: classifications,
                       optionsText: 'description',
                       optionsValue: 'classificationid',
                       optionsCaption: 'Choose...',
					   select2: { width : '220px' }"></select>
			专业分类：<select name="specclass"
				data-bind="options: specclass,
                       optionsText: 'description',
                       optionsValue: 'dmvalue',
                       optionsCaption: 'Choose...',
					   select2: { width : '220px' }"></select>
			<br /> 资产位置： <select name="location"
				data-bind="options: locations,
                       optionsText: 'description',
                       optionsValue: 'location',
                       optionsCaption: 'Choose...'"></select>
			资产编码：<input type="text" name="assetnum" />
			<button id="queryAsset" data-bind="click:queryAssets"
				class="btn btn-default">查询</button>
		</form>
		<table class="table table-hover table-condensed table-striped">
			<thead>
				<tr>
					<td></td>
					<td>设备编码</td>
					<td>描述</td>
					<td>专业</td>
					<td>类别</td>
					<td>位置</td>
				</tr>
			</thead>
			<tbody data-bind="foreach:assets()">
				<tr>
					<td><input type="checkbox" name="chk"
						data-bind="checkedValue:assetnum,click:$root.chosenAssets"></td>
					<td data-bind="text:assetnum"></td>
					<td data-bind="text:description"></td>
					<td data-bind="text:specclass"></td>
					<td data-bind="text:classstructureid"></td>
					<td data-bind="text:location"></td>
				</tr>
			</tbody>
		</table>

		<div data-bind="foreach: $root.selectedAssets" style="display:none;">
			<span data-bind="text: assetnum"></span> <br />
		</div>
	</div>
	<div class="modal-footer">
		<button class="btn btn-primary" id="btnSubmitPagetag"
			data-bind="click:selectAll">全选</button>
		<button class="btn btn-primary" id="btnSubmitPagetag"
			data-bind="click:selectOthers">反选</button>
		<button class="btn btn-primary" id="btnSubmitPagetag"
			data-bind="click:selectAssets">选择</button>
		<button class="btn btn-default" data-dismiss="modal"
			aria-hidden="true">关闭</button>
	</div>
</div>

<!-- Passenger Modal -->
<div id="passengerModal"  class="modal middle hide fade" tabindex="-1"
	role="dialog" aria-labelledby="assetModalLabel" aria-hidden="true" style="width:710px;">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">×</button>
		<h3 id="assetModalLabel">
			客流信息<span data-bind="text:totalAssets"></span>
		</h3>
	</div>
	<div class="modal-body">
		<form id="passengerQueryForm">
			客流类别： <select name="classstructureid"
				data-bind="options: classifications,
                       optionsText: 'description',
                       optionsValue: 'classificationid',
                       optionsCaption: 'Choose...',
					   select2: { width : '220px' }"></select>
			专业分类：<select name="specclass"
				data-bind="options: specclass,
                       optionsText: 'description',
                       optionsValue: 'dmvalue',
                       optionsCaption: 'Choose...',
					   select2: { width : '220px' }"></select>
			<br /> 资产位置： <select name="location"
				data-bind="options: locations,
                       optionsText: 'description',
                       optionsValue: 'location',
                       optionsCaption: 'Choose...'"></select>
			资产编码：<input type="text" name="assetnum" />
			<button id="queryAsset" data-bind="click:queryPassengers"
				class="btn btn-default">查询</button>
		</form>
		<table class="table table-hover table-condensed table-striped">
			<thead>
				<tr>
					<td></td>
					<td>设备编码</td>
					<td>描述</td>
					<td>专业</td>
					<td>类别</td>
					<td>位置</td>
				</tr>
			</thead>
			<tbody data-bind="foreach:assets()">
				<tr>
					<td><input type="checkbox" name="chk"
						data-bind="checkedValue:assetnum,click:$root.chosenAssets"></td>
					<td data-bind="text:assetnum"></td>
					<td data-bind="text:description"></td>
					<td data-bind="text:specclass"></td>
					<td data-bind="text:classstructureid"></td>
					<td data-bind="text:location"></td>
				</tr>
			</tbody>
		</table>

		<div data-bind="foreach: $root.selectedAssets" style="display:none;">
			<span data-bind="text: assetnum"></span> <br />
		</div>
	</div>
	<div class="modal-footer">
		<button class="btn btn-primary" id="btnSubmitPagetag"
			data-bind="click:selectAll">全选</button>
		<button class="btn btn-primary" id="btnSubmitPagetag"
			data-bind="click:selectOthers">反选</button>
		<button class="btn btn-primary" id="btnSubmitPagetag"
			data-bind="click:selectPassengers">选择</button>
		<button class="btn btn-default" data-dismiss="modal"
			aria-hidden="true">关闭</button>
	</div>

</div>

<!-- Modal -->
<div id="editModal" class="modal small hide fade" tabindex="-1"
	role="dialog" aria-labelledby="editModalLabel" aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">×</button>
		<h3 id="editModalLabel">设备信息</h3>
	</div>
	<div class="modal-body">
		<form:form class="form-horizontal" modelAttribute="pagetag"
			action="${pageContext.request.contextPath}/okcsys/page/pagetag/${pagelayout.pagelayoutuid}"
			role="form">
			<div class="control-group">
				<label for="tagid" class="control-label">设备ID：</label>
				<div class="controls" style="z-index:10;">
					<input name="tagid" id="tagid" placeholder="请输入TagID、视频ID或资产编码"
						data-bind="value:currentPagetagRow().tagid" required="required"
						type="text" title="TagID或VideoID"> <input type="hidden"
						name="layoutid" data-bind="value:currentPagetagRow().layoutid" />
					<input type="hidden" name="pagetagid"
						data-bind="value:currentPagetagRow().pagetagid" />
				</div>
			</div>
			<div class="control-group">
				<form:label path="tagname" class="control-label">设备名称：</form:label>
				<div class="controls">
					<form:input path="tagname" required="required"
						data-bind="value:currentPagetagRow().tagname" placeholder="设备名称" />
				</div>
			</div>
			<div class="control-group">
				<form:label path="tagtype" class="control-label">值类型：</form:label>
				<div class="controls">
					<select name="tagtype"
						data-bind="value:currentPagetagRow().tagtype">
						<option value="1">布尔型</option>
						<option value="2">数值型</option>
						<option value="3">整数型</option>
						<option value="4">字符串</option>
					</select>
				</div>
			</div>
			<div class="control-group">
				<form:label path="label" class="control-label">标签名称：</form:label>
				<div class="controls">
					<form:input path="label" placeholder="设备描述名称"
						data-bind="value:currentPagetagRow().label" />
				</div>
			</div>

			<div class="control-group">
				<form:label path="measureunitid" class="control-label">计量单位：</form:label>
				<div class="controls">
					<select name="measureunitid"
						data-bind="options: measureunits,
                       optionsText: 'description',
                       optionsValue: 'measureunitid',
                       value: currentPagetagRow().measureunitid,
                       optionsCaption: '选择...'">
					</select>
				</div>
			</div>
			<div class="control-group">
				<form:label path="groupname"></form:label>
				<label for="groupname" class="control-label">设备分组：</label>
				<div class="controls">
					<div class="groupname">
						<input type="text" name="groupname" placeholder="设备分组名称"
							data-bind="value:currentPagetagRow().groupname">
					</div>
				</div>
			</div>

			<div class="control-group">
				<form:label path="controlid" class="control-label">标签组件：</form:label>
				<div class="controls">
					&nbsp;&nbsp;&nbsp;&nbsp;设备列表： <select id="controlid"
						name="controlid"
						data-bind="options: controls,
                       optionsText: 'ctrlname',
                       optionsValue: 'controlid',
                       value: currentPagetagRow().controlid,
                       optionsCaption: '选择...'">
					</select> <br /> 系统结构图： <select id="controlid" name="controlid"
						data-bind="options: controls,
                       optionsText: 'ctrlname',
                       optionsValue: 'controlid',
                       value: currentPagetagRow().controlid2,
                       optionsCaption: '选择...'">
					</select> <br /> 系统平面图： <select id="controlid" name="controlid"
						data-bind="options: controls,
                       optionsText: 'ctrlname',
                       optionsValue: 'controlid',
                       value: currentPagetagRow().controlid3,
                       optionsCaption: '选择...'">
					</select>

				</div>
			</div>
			<div class="control-group">
				<label for="showrange" class="control-label">作用范围：</label>
				<div class="controls">
					<select multiple="true" style="width:300px;"
						data-bind="options: showranges, 
						optionsText: 'name',
                        optionsValue: 'value',
						selectedOptions: currentPagetagRow().showrange,
						select2: { placeholder: '选择范围' }">
					</select>


				</div>
			</div>

			<div class="control-group">
				<form:label path="comments" class="control-label">设备备注：</form:label>
				<div class="controls">
					<textarea name="comments" placeholder="设备备注"
						data-bind="value:currentPagetagRow().comments"></textarea>
					<input type="hidden" name="pagetagtype"
						data-bind="value:currentPagetagRow().pagetagtype">
				</div>
			</div>
			<div class="control-group">
				<label for="zindex" class="control-label">空间位置：</label>
				<div class="controls">
					<input type="number" id="zindex" name="zindex" data-bind="value:currentPagetagRow().zindex"  required="required" />
				</div>
			</div>
		</form:form>

		<!-- <pre data-bind="text: ko.toJSON(currentPagetagRow(), null, 2)"></pre> -->
	</div>
	<div class="modal-footer">
		<button class="btn btn-primary" id="btnSubmitPagetag"
			data-bind="click:submitPagetag">保存</button>
		<button class="btn btn-default" data-dismiss="modal"
			aria-hidden="true">关闭</button>
	</div>
</div>

<!-- 自动生成设备的Modal，2014-08-17 zzx -->
<div id="buildAssetModal" class="modal small hide fade" tabindex="-1"
	role="dialog" aria-labelledby="buildAssetModalLabel" aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">×</button>
		<h3 id="buildAssetModalLabel">设备信息</h3>
	</div>
	<div class="modal-body">
		<div class="control-group">
			<label class="control-label">资产分类：</label>
			<div class="controls">
				<select id="b_specid" data-bind="options: specclasslist,
                      optionsText: 'specclassid',
                      optionsValue: 'specclassid',
                      optionsCaption: '选择...'">
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">资产位置：</label>
			<div class="controls">
				<select id="b_locationid" data-bind="options: locationlist,
                      optionsText: 'location',
                      optionsValue: 'locationid',
                      optionsCaption: '选择...'">
				</select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">资产类别：</label>
			<div class="controls">
				<select id="b_classstructureid" data-bind="options: classifications,
                       optionsText: 'description',
                       optionsValue: 'classificationid',
                       optionsCaption: '选择...'"></select>
			</div>
		</div>
	</div>
	<div class="modal-footer">
		<button class="btn btn-primary" id="buildAssetAttrInfo"
			data-bind="click:buildAssetAttrInfo">生成</button>
		<button class="btn btn-default" data-dismiss="modal" aria-hidden="true">关闭</button>
	</div>
</div>

<!-- 背景图片上传 -->
<div id="uploadModal" class="modal hide fade" tabindex="-1" role="dialog"
	aria-labelledby="uploadModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h3 id="uploadModalLabel">背景图片上传</h3>
			</div>
			<div class="modal-body">
				<input id="file" name="file" type="file" />
			</div>
			<div class="modal-footer">
				<button class="btn btn-primary" id="btnSubmitUpload">上传</button>
				<button class="btn btn-default" data-dismiss="modal"
					aria-hidden="true">关闭</button>
			</div>
		</div>
	</div>
</div>
<!--  绘制热区的 >
<a href="#" class="btn" data-toggle="modal"
data-target="#DrowMapModal" id="btnSubmitDrawMap">绘制热区</a-->
		
 <%-- <div id="DrowMapModal" class="modal hide fade" tabindex="-1" role="dialog" 
     aria-lable="DrawMapModalLable" aria-hidden="true">
       <div class="modal-dialog" height=100% width=100%> 
           <div class="modal-content">
               <div class="modal-header">   
                   <button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button> 
                   <h3 id=""DrawMapModalLable"">绘制热区</h3>
               </div> 
               <div class="modal-body" >
                  <iframe src="${pageContext.request.contextPath}/resources/plugins/map-tools/index.jsp" width="100%" height = "100%"></iframe>
               </div>
               <div class="modal-footer">               
                 <button class="btn btn-default" data-dismiss="modal" aria-hidden="true">关闭</button>
               </div>
           </div>
       </div>
 </div> 
 --%>




<!-- Modal -->
<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog"
	aria-labelledby="myModalLabel" aria-hidden="true">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal"
			aria-hidden="true">×</button>
		<h3 id="myModalLabel">菜单列表</h3>
	</div>
	<div class="modal-body">
		<div style="height:380px;overflow-y:scroll;">
			<ul id="ztree"></ul>
		</div>

	</div>
	<div class="modal-footer">
		<button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
	</div>
</div>


<!-- 批量更新属性 -->
<div id="batchSetPropModal" class="modal hide fade" tabindex="-1"
	role="dialog" aria-labelledby="batchSetPropModalLabel"
	aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-hidden="true">×</button>
				<h3 id="batchSetPropModalLabel">设置设备属性</h3>
			</div>
			<div class="modal-body">
				<form id="batchModify" method="post">
					<div class="control-group">
						<label>标签组件：</label>
						<div class="controls">
							&nbsp;&nbsp;&nbsp;&nbsp;设备列表： <select id="controlid"
								name="controlid"
								data-bind="options: controls,
                       optionsText: 'ctrlname',
                       optionsValue: 'controlid',
                       value: currentPagetagRow().controlid,
                       optionsCaption: '选择...'">
							</select> <br /> 系统结构图： <select id="controlid2" name="controlid2"
								data-bind="options: controls,
                       optionsText: 'ctrlname',
                       optionsValue: 'controlid',
                       optionsCaption: '选择...'">
							</select> <br /> 系统平面图： <select id="controlid3" name="controlid3"
								data-bind="options: controls,
                       optionsText: 'ctrlname',
                       optionsValue: 'controlid',
                       optionsCaption: '选择...'">
							</select>

						</div>
					</div>
					
					<div class="control-group">
						<label>面板组件：</label>
						<div class="controls">
							&nbsp;&nbsp;&nbsp;&nbsp;
							组件： <select id="attrctrlid" name="attrctrlid"
								data-bind="options: controls,
                       optionsText: 'ctrlname',
                       optionsValue: 'controluid',
                       optionsCaption: '选择...'">
							</select>

						</div>
					</div>
					
					<div class="control-group">
						<div class="controls">
							<label for="showrange" class="control-label">作用范围：</label>
							<select multiple="true" id="showrange" style="width:200px;"
								data-bind="options: showranges, 
								optionsText: 'name',
		                        optionsValue: 'value',
								select2: { placeholder: '选择范围' }">
							</select>
						</div>
					</div>
					
					<div class="control-group">
						<label>Z轴坐标（默认100）：</label>
						<input name="zindex" type="text" id="zindex" value="100">
					</div>
					
					<div class="control-group">
						<label for="pagetagtype" class="control-label">设备类型：</label>
						<div class="controls">
							<select name="pagetagtype">
								<option value="-1">不设置</option>
								<option value="0">设备点</option>
								<option value="1">热区</option>
								<option value="2">视频</option>
								<option value="3">资产</option>
								<option value="4">报警</option>
								<option value="99">组件</option>
							</select>
						</div>
					</div>
					<div class="control-group">
						<label for="measureunitid" class="control-label">计量单位：</label>
						<div class="controls">
							<select name="measureunitid"
								data-bind="options: measureunits,
                       optionsText: 'description',
                       optionsValue: 'measureunitid',
                       optionsCaption: '选择...'">
							</select>
						</div>
					</div>


					<div class="control-group">
						<label for="tagtype" class="control-label">值类型：</label>
						<div class="controls">
							<select name="tagtype" data-bind="value:currentRow().tagtype">
								<option value="1">布尔型</option>
								<option value="2">数值型</option>
								<option value="3">整数型</option>
								<option value="4">字符串</option>
							</select>
						</div>
					</div>

					<!-- div class="control-group">
						<label for="showrange" class="control-label">显示页面范围：</label>
						<div class="controls">

							<label class="checkbox inline"> <input type="checkbox" name="showrange"
								value="list"> 设备列表
							</label> <label class="checkbox inline"> <input type="checkbox"
								name="showrange" value="structure"> 系统结构图
							</label> <label class="checkbox inline"> <input type="checkbox"
								name="showrange" value="plan"> 系统平面图
							</label>

						</div>
					</div -->
					<input type="hidden" name="setting" id="setting" />
				</form>

			</div>
			<div class="modal-footer">
				<button class="btn btn-primary"
					data-bind="click:$root.submitTagPropChange">提交</button>
				<button class="btn btn-default" data-dismiss="modal"
					aria-hidden="true">关闭</button>
			</div>
		</div>
	</div>
</div>