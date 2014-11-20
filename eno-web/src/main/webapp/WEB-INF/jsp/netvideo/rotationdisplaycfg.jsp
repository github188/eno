<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!-- HTML START -->
<!-- 
	AUTHOR: LiuChao
	Created Date: 2014年5月9日 11:13:22
	LastModified Date: 
	Description: NetVideo模块——轮显组配置页面
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
		<div style="border-style: ridge; height: 100%; margin-right: 3px;">
			<p style="margin-left: 1%;">系统中具有以下轮显组</p>
			<div style="border-style: groove; width: 98%; margin-left: 1%;">
				<input type="hidden" id="ischange" value="0">
				<table class="green_table">
					<thead>
						<tr>
							<td style="display: none">DVR序号</td>
							<td style="width: 13%;">轮显组ID</td>
							<td style="width: 13%;">轮显组名称</td>
							<td style="width: 13%;">间隔</td>
							<td style="width: 61%;">摄像机</td>
						</tr>
					</thead>
				</table>
			</div>
			<form method="post" id="rotationform"
				action="${pageContext.request.contextPath }/okcsys/video/change/rotationcfg">
				<div
					style="border-style: groove; width: 98%; height: 89%; margin-left: 1%;"
					class="rollcontent">
					<table id="detailTable" class="commTable">
						<tbody>
							<c:forEach items="${rotationconfigs}" var="rotation">
								<tr onclick="select_row(this);" style="cursor: pointer;">
									<td style="display: none;"></td>
									<td style="width: 13%;">${rotation.rotationId}</td>
									<td style="width: 13%;">${rotation.rotationName}</td>
									<td style="width: 13%;">${rotation.rotationInterval}</td>
									<td style="width: 61%;"><c:if
											test="${rotation.checkedCamera.length()>30}">${fn:substring(rotation.checkedCamera,0,30)}...</c:if>
										<c:if
											test="${rotation.checkedCamera.length()>0&&rotation.checkedCamera.length()<=30}">${rotation.checkedCamera}</c:if>
										<p style="display: none">${rotation.checkedCamera}</p></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</form>
		</div>
		<div style="margin: 18px 10px;">
			<button class="blue_btn" type="button"
				onclick="cover_show('add_rotation');changeTag('add');addRoll();">增加</button>
			<button class="blue_btn" type="button"
				onclick="changeTag('del');editRow('rotation');">删除</button>
			<button class="blue_btn" type="button"
				onclick="changeTag('edit');editRow('rotation');addRoll();">修改</button>
			<button class="blue_btn" type="button" onclick="up();">上移</button>
			<button class="blue_btn" type="button" onclick="down();">下移</button>
			<button class="blue_btn" type="button">导出</button>
			<button class="blue_btn" type="button">导入</button>
			<button class="blue_btn" type="button" style="float: right;"
				onclick="window.location.href='${pageContext.request.contextPath }/okcsys/video/rotationcfg'">还原</button>
			<button class="blue_btn" type="button" style="float: right;"
				id="rotationsub">保存</button>
		</div>
		<div class="cover_div"></div>
		<div id="add_rotation" class="add_dvr" style="height: 360px;">
			<table style="margin: 15px 0px 0px 20px; width: 312px;">
				<tbody>
					<tr>
						<td>ID号码</td>
						<td><input id="rotationID" type="text" readonly="readonly"
							style="width: 190px;" placeholder="轮显组ID将由系统自动生成"></td>
					</tr>
					<tr>
						<td>组名称</td>
						<td><input id="rotationName" type="text"
							style="width: 190px;" placeholder="轮显组名称"></td>
					</tr>
					<tr>
						<td>轮显间隔</td>
						<td><input id="rotationTime" type="text"
							style="width: 160px;" onkeyup="value=value.replace(/[^\d]/g,'')"
							onbeforepaste="clipboardData.setData('text',clipboardData('text').replace(/[^\d]/g,''))"
							placeholder="轮显间隔">秒</td>
					</tr>
					<tr style="vertical-align: top;">
						<td>选中摄像机</td>
						<td>
							<div id="cameras" style="height: 220px; width: 190px;"
								class="rollcontent">
								<c:forEach items="${cameraconfigs}" var="camera" varStatus="sta">
									<p>
										<input type="checkbox" id="camera${sta.index }" name="camera"
											value="${camera.cameraid }"><label
											for="camera${sta.index }" style="cursor: pointer;">${camera.cameraname }</label>
									</p>
								</c:forEach>
							</div>
						</td>
					</tr>
				</tbody>
			</table>
			<div style="margin-top: 20px; text-align: center;">
				<button class="blue_btn" type="button" style="margin-right: 25px;"
					onclick="editRotation();">确认</button>
				<button class="blue_btn" type="button" style="margin-left: 25px;"
					onclick="cover_show('add_rotation');">取消</button>
			</div>
		</div>
	</div>
</div>