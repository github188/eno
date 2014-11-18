<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!-- netvideo 监视器配置页面
	 @author  ztl
 -->
<div class="row-fluid">
	<div class="span2">
		<div class="widget">
			<div class="widget-header">
				<h3 class="icon">小牛配置</h3>
			</div>
			<div class="widget-content"
				style="overflow: auto; height: 300px; width: 165px;">
				<ul class="nav nav-tabs nav-stacked">
					<li><a
						href="<spring:url value="/okcsys/video/sysconfig"></spring:url>"><i
							class="fa fa-home"></i><span>系统配置</span></a></li>
					<li><a href="<spring:url value="/okcsys/video/dvrcfg"></spring:url>"><i
							class="fa fa-users"></i><span>DVR配置</span></a></li>
					<li><a
						href="<spring:url value="/okcsys/video/cameracfg"></spring:url>"><i
							class="fa fa-home"></i><span>摄像机配置</span></a></li>
					<li><a
						href="<spring:url value="/okcsys/video/matrixcfg"></spring:url>"><i
							class="fa fa-home"></i><span>矩阵配置</span></a></li>
					<li><a
						href="<spring:url value="/okcsys/video/monitorconfig"></spring:url>"><i
							class="fa fa-home"></i><span>监视器配置</span></a></li>
					<li><a
						href="<spring:url value="/okcsys/video/rotationcfg"></spring:url>"><i
							class="fa fa-home"></i><span>轮显配置</span></a></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="span10">
		<div style="padding: 0.7em; width: 98%; height: 97%;">
			<table id="head" class="green_table" style="text-align: center;">
				<caption>系统中具有以下摄像机</caption>
				<thead>
					<tr>
						<td style="width: 70px;">摄像机ID</td>
						<td style="width: 200px;">摄像机名称</td>
						<td style="width: 180px;">连接到DVR</td>
						<td style="width: 173px;">连接到矩阵</td>
						<td style="width: 142px;">云镜</td>
						<td style="width: 245px;">资源信息</td>
					</tr>
				</thead>
			</table>
			<div style="height: 740px; background-color: #D8E7E7;">
				<form
					action="${pageContext.request.contextPath}/okcsys/video/change/cameracfg"
					method="post" id="change" style="height: 90%;">
					<div style="height: 100%; overflow-y: scroll;" class="">
						<table id="cam_detail" style="width: 100%; text-align: center;">
							<tbody>
								<c:forEach items="${cameracfgs}" var="cameraconfig"
									varStatus="sta">
									<tr
										class="<c:if test="${sta.index%2==0}">even</c:if><c:if test="${sta.index%2!=0}">odd</c:if>">
										<td style="display: none;">${cameraconfig.cameracfgid }</td>
										<td style="width: 70px;">${cameraconfig.cameraid }</td>
										<td style="width: 200px;">${cameraconfig.cameraname }</td>
										<c:forEach items="${dvrcfgs}" var="dvr">
											<c:if test="${dvr.dvrname==cameraconfig.dvrname }">
												<td style="width: 180px;">${dvr.dvrname}-${dvr.ipaddress}-${cameraconfig.dvrchannel}</td>
											</c:if>
										</c:forEach>
										<td style="width: 180px;">${cameraconfig.matrix}<c:if
												test="${!empty cameraconfig.matrixchannel}">-${cameraconfig.matrixchannel}</c:if></td>
										<td style="width: 130px;">${cameraconfig.ptzcontrol}</td>
										<td style="width: 241px;"><input class="res_id"
											type="hidden" value="${cameraconfig.resourceid}" /> <c:if
												test="${cameraconfig.resourceid.length()>30}">${fn:substring(cameraconfig.resourceid,0,30)}...</c:if>
											<c:if test="${cameraconfig.resourceid.length()<30}">${cameraconfig.resourceid}</c:if></td>
										<td style="display: none;">${cameraconfig.ptzparam1},${cameraconfig.ptzparam2},${cameraconfig.ptzparam3}</td>
										<td style="display: none;">${cameraconfig.displaysequence }</td>
										<td style="display: none;">${cameraconfig.matrixindex }</td>
										<td style="display: none;">${cameraconfig.ptzindex }</td>
										<td style="display: none;">${cameraconfig.groupid }</td>
										<td style="display: none;"></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</form>
			</div>
			<table style="text-align: left; width: 100%;">
				<tr>
					<td>
						<button class="blue_btn" type="button"
							onclick="show_detail('add','cover_div','add_cam')">增加</button>
						<button class="blue_btn" type="button"
							onclick="dele('cam_detail')">删除</button>
						<button class="blue_btn" type="button"
							onclick="show_detail('modi','cover_div','add_cam')">修改</button>
						<button class="blue_btn" type="button"
							onclick="goUp('cam_detail',8)">上移</button>
						<button class="blue_btn" type="button"
							onclick="goDown('cam_detail',8)">下移</button>
						<button class="blue_btn" type="button">导出</button>
						<a class="blue_btn" role="button" href="#"
							style="font-size: 16px; padding: 3px 18px 3px 18px; color: white; font-family:'Helvetica
							Neue',Helvetica,Arial,sans-serif;" data-toggle="modal" data-target="#importModal">导入</a>
						<button class="blue_btn" type="button"
							onclick="add_group('cover_div','group_op')">分组</button>
						<button class="blue_btn" type="button" style="float: right;"
							onclick="canc('cameracfg');">取消</button>
						<button class="blue_btn" type="button" style="float: right;"
							onclick="sub_cam('change')">保存</button>
					</td>
				</tr>
			</table>
		</div>
		<div class="cover_div"></div>
		<div id="add_cam" class="add_cam">
			<h1>摄像机设定</h1>
			<table
				style="border-collapse: collapse; border: groove; width: 700px;">
				<tr>
					<td>Id号码</td>
					<td><input id="cam_id" type="text" /></td>
					<td colspan="2" rowspan="4">
						<ul style="width: 15px; float: left;">
							<li>分</li>
							<li>组</li>
						</ul>
						<div style="float: right;">
							<ul id="show_tree" class="ztree"></ul>
						</div>
					</td>
				</tr>
				<tr>
					<td>摄像机名称</td>
					<td><input id="cam_name" type="text" /></td>
				</tr>
				<tr>
					<td>连接到DVR</td>
					<td><select id="dvr_name" style="width: 137px;">
							<c:forEach items="${dvrcfgs}" var="dvr">
								<option value="${dvr.dvrname}-${dvr.ipaddress}">${dvr.dvrname}</option>
							</c:forEach>
					</select></td>
				</tr>
				<tr>
					<td>DVR输入通道</td>
					<td><input id="dvr_channel" type="text" /></td>
				</tr>
				<tr>
					<td>资源信息</td>
					<td><input id="res_id" type="text" /></td>
					<td>连接矩阵</td>
					<td><select id="matrix">
							<option value="不使用矩阵,0">不使用矩阵</option>
							<option value="1#矩阵,1">1#矩阵</option>
							<option value="2#矩阵,2">2#矩阵</option>
							<option value="3#矩阵,3">3#矩阵</option>
							<option value="4#矩阵,4">4#矩阵</option>
					</select></td>
				</tr>
				<tr>
					<td rowspan="2">云镜控制</td>
					<td rowspan="2"><input name="ptz" type="radio"
						value="没有云镜控制,0" checked="checked" />没有云镜控制 <br> <input
						name="ptz" type="radio" value="云镜由矩阵控制,1" />云镜由矩阵控制 <br> <input
						name="ptz" type="radio" value="云镜由DVR控制,2" />云镜由DVR控制<br></td>
					<td>矩阵输入通道</td>
					<td><input id="matrix_channel" type="text" /></td>
				</tr>
				<tr>
					<td>PTZ附加参数</td>
					<td><input style="width: 35px;" id="ptzparam_1" type="text"
						value="0"> <input style="width: 35px;" id="ptzparam_2"
						type="text" value="0"> <input style="width: 35px;"
						id="ptzparam_3" type="text" value="0"></td>
				</tr>
			</table>
			<table
				style="border-collapse: collapse; border: groove; margin-top: -3px; width:700px;">
				<tr>
					<td style="width: 170px;"><input type="text" value="查看图像"
						disabled="disabled" /></td>
					<td>被保护为</td>
					<td style="width: 170px;"><input type="text" id="tuxiang"
						value="" disabled="disabled" /></td>
					<td>
						<button value="" onclick="show_sys('tuxiang')">?</button>
						<button value="" onclick="clear_sys('tuxiang')">X</button>
					</td>
				</tr>
				<tr>
					<td style="width: 170px;"><input type="text" value="拍照录像"
						disabled="disabled" /></td>
					<td>被保护为</td>
					<td style="width: 170px;"><input type="text" id="luxiang"
						value="" disabled="disabled" /></td>
					<td>
						<button value="" onclick="show_sys('luxiang')">?</button>
						<button value="" onclick="clear_sys('luxiang')">X</button>
					</td>
				</tr>
				<tr>
					<td style="width: 170px;"><input type="text" value="PTZ控制"
						disabled="disabled" /></td>
					<td>被保护为</td>
					<td style="width: 170px;"><input type="text" id="ptz_con"
						value="" disabled="disabled" /></td>
					<td>
						<button value="" onclick="show_sys('ptz_con')">?</button>
						<button value="" onclick="clear_sys('ptz_con')">X</button>
					</td>
				</tr>
				<tr>
					<td style="width: 170px;"><input type="text" value="修改预置位"
						disabled="disabled" /></td>
					<td>被保护为</td>
					<td style="width: 170px;"><input type="text" id="yuzhiwei"
						value="" disabled="disabled" /></td>
					<td>
						<button value="" onclick="show_sys('yuzhiwei')">?</button>
						<button value="" onclick="clear_sys('yuzhiwei')">X</button>
					</td>
				</tr>
				<tr>
					<td colspan="4"><textarea
							style="width: 491px; height: 33px; resize: none; overflow-y: hidden;"
							rows="" disabled="disabled" cols="">摄像机的ID号需要和主系统保持一致，请慎重修改。
如果云镜由矩阵控制，必须选择“连接矩阵”。
			</textarea></td>
				</tr>
			</table>
			<div style="text-align: center; margin-top: 10px;">
				<input type="hidden" id="id_change" value="0" />
				<button class="blue_btn" type="button" id="cam_sub"
					onclick="add_cam(this)">确定</button>
				<button id="add_cel" class="blue_btn" type="button">取消</button>
			</div>
		</div>
		<div id="group_op" class="group">
			<div style="display: inline; float: left;">
				<ul id="group_tree" class="ztree"></ul>
			</div>
			<div class="btn_op" style="display: inline; float: right;">
				<button id="edit" style="margin-top: 10px;">修改</button>
				<button id="remove" style="margin-top: 5px;">删除</button>
				<button id="addchil" style="margin-top: 5px;">增加子节点</button>
				<button id="addsibl" style="margin-top: 5px;">增加兄弟节点</button>
				<button id="expandNodes" style="margin-top: 100px;">扩展项目</button>
				<button id="collapseNodes" style="margin-top: 5px;">收缩项目</button>
				<button id="sub_tree" style="margin-top: 87px;">确定</button>
				<button id="tree_cel" style="margin-top: 5px;">取消</button>
			</div>
		</div>
		<div id="group_show" class="group">
			<div style="display: inline; float: left;">
				<ul id="group_sys" class="ztree"></ul>
			</div>
			<div class="btn_op" style="display: inline; float: right;">
				<button id="expandNodes_1" style="margin-top: 10px;">扩展项目</button>
				<button id="collapseNodes_1" style="margin-top: 5px;">收缩项目</button>
				<button id="sub_sys" style="margin-top: 87px;">确定</button>
				<button id="sys_cel" style="margin-top: 5px;">取消</button>
			</div>
		</div>
	</div>
	<div id="importModal" class="modal hide fade" tabindex="-1"
			role="dialog" aria-labelledby="importModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h3 id="importModalLabel">文件导入</h3>
				</div>
				<div class="modal-body">
					<input id="file" name="file" type="file" />
					<select id="import_t">
						<option value="0">选择导入方式</option>
						<option value="1">覆盖原数据</option>
						<option value="2">原数据追加</option>
					</select>
				</div>
				<div class="modal-footer">
					<button class="btn btn-primary" id="btnSubmit_c">导入</button>
					<button class="btn btn-default" data-dismiss="modal"
						aria-hidden="true">关闭</button>
				</div>
			</div>
		</div>
	</div>
</div>