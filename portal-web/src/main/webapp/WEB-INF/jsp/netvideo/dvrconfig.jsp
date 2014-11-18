<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!-- HTML START -->
<!-- 
	AUTHOR: LiuChao
	Created Date: 2014年5月5日 14:47:57
	LastModified Date: 2014年5月8日 18:58:27
	Description: NetVideo模块——DVR配置页面
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
		<form id="tableFrom"
			action="${pageContext.request.contextPath }/okcsys/video/savedvr"
			method="post" style="height: 92%; width: 100%;">
			<input type="hidden" id="delIds" name="delIds"> <input
				type="hidden" id="sortList" name="sortList"> <input
				type="hidden" id="maxSequence" value="${maxSequence}">
			<div style="border-style: ridge; height: 100%; margin-right: 3px;">
				<label for="show_password" style="float: right; margin-right: 1%;">显示密码</label><input
					type="checkbox" id="show_password"
					style="float: right; margin: 5px 3px 3px 4px;">
				<p style="margin-left: 1%;">系统中具有以下DVR</p>
				<div style="border-style: groove; width: 98%; margin-left: 1%;">
					<table class="green_table">
						<thead>
							<tr>
								<td style="display: none">DVR序号</td>
								<td style="display: none">DVR编号</td>
								<td style="width: 13%;">DVR名称</td>
								<td style="width: 13%;">DVR类型</td>
								<td style="width: 13%;">IP地址</td>
								<td style="width: 13%;">连接端口</td>
								<td style="width: 13%;">登陆名称</td>
								<td style="width: 13%;">登陆密码</td>
								<td style="width: 22%;">资源信息</td>
							</tr>
						</thead>
					</table>
				</div>
				<div
					style="border-style: groove; width: 98%; height: 800px; margin-left: 1%;"
					class="rollcontent">
					<table id="detailTable" class="commTable">
						<tbody>
							<c:forEach items="${dvrList}" var="dvrs">
								<tr onclick="select_row(this);" style="cursor: pointer;">
									<td style="display: none;">${dvrs.dvrsequence}</td>
									<td style="display: none;">${dvrs.dvrcfgid}</td>
									<td style="width: 13%;">${dvrs.dvrname}</td>
									<td style="width: 13%;">${dvrs.dvrtype}</td>
									<td style="width: 13%;">${dvrs.ipaddress}</td>
									<td style="width: 13%;">${dvrs.port}</td>
									<td style="width: 13%;">${dvrs.username}</td>
									<td style="width: 13%;"><input type="password"
										readonly="readonly" class="password_td"
										value="${dvrs.password}"></td>
									<td style="width: 22%;"><c:if
											test="${dvrs.resourceid.length()>30}">${fn:substring(dvrs.resourceid,0,30)}...</c:if>
										<c:if
											test="${dvrs.resourceid.length()>0&&dvrs.resourceid.length()<=30}">${dvrs.resourceid}</c:if>
										<p style="display: none">${dvrs.resourceid}</p></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</form>
		<div style="margin: 18px 10px;">
			<button class="blue_btn" type="button"
				onclick="cover_show('add_dvr');changeTag('add');">增加</button>
			<button class="blue_btn" type="button"
				onclick="changeTag('del');editRow('dvr');">删除</button>
			<button class="blue_btn" type="button"
				onclick="changeTag('edit');editRow('dvr');">修改</button>
			<button class="blue_btn" type="button" onclick="up();">上移</button>
			<button class="blue_btn" type="button" onclick="down();">下移</button>
			<button class="blue_btn" type="button">导出</button>
			<a class="blue_btn" role="button" href="#"
				style="font-size: 16px; padding: 3px 18px 3px 18px; color: white; font-family:'Helvetica
				Neue',Helvetica,Arial,sans-serif;" data-toggle="modal" data-target="#importModal">导入</a>
			<button class="blue_btn" type="button" style="float: right;"
				onclick="window.location.href='${pageContext.request.contextPath }/okcsys/video/dvrcfg'">还原</button>
			<button class="blue_btn" type="button" style="float: right;"
				onclick="saveCommon();">保存</button>
		</div>
		<div class="cover_div"></div>
		<div id="add_dvr" class="add_dvr">
			<table style="margin: 15px 0px 0px 20px; width: 312px;">
				<tbody>
					<tr>
						<td>DVR主机名称</td>
						<td><input id="dvrname" type="text" style="width: 190px;"
							placeholder="DVR主机名称"></td>
					</tr>
					<tr>
						<td>主机类型</td>
						<td><input id="dvrtype" type="text" style="width: 194px;" placeholder="DVR品牌+DVR"/></td>
					</tr>
					<tr>
						<td>主机IP</td>
						<td><input id="ipaddress" type="text" style="width: 190px;"
							placeholder="DVR主机IP"></td>
					</tr>
					<tr>
						<td>连接端口</td>
						<td><input id="port" type="text" style="width: 190px;"
							placeholder="连接端口"></td>
					</tr>
					<tr>
						<td>资源信息</td>
						<td><input id="resourceid" type="text" style="width: 190px;"
							placeholder="资源信息"></td>
					</tr>
					<tr>
						<td>登陆名称</td>
						<td><input id="username" type="text" style="width: 190px;"
							placeholder="登陆名称"></td>
					</tr>
					<tr>
						<td>登陆密码</td>
						<td><input id="password" type="password"
							style="width: 190px;" placeholder="登陆密码"></td>
					</tr>
					<tr>
						<td>重输密码</td>
						<td><input id="repassword" type="password"
							style="width: 190px;" placeholder="密码确认"></td>
					</tr>
				</tbody>
			</table>
			<div style="margin-top: 20px; text-align: center;">
				<button class="blue_btn" type="button" style="margin-right: 25px;"
					onclick="editDvr();">确认</button>
				<button class="blue_btn" type="button" style="margin-left: 25px;"
					onclick="cover_show('add_dvr');">取消</button>
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
					<input id="file" name="file" type="file" /><span>注：密码中必须含有字母</span>
					<select id="import_t">
						<option value="0">选择导入方式</option>
						<option value="1">覆盖原数据</option>
						<option value="2">原数据追加</option>
					</select>
				</div>
				<div class="modal-footer">
					<button class="btn btn-primary" id="btnSubmitImport">导入</button>
					<button class="btn btn-default" data-dismiss="modal"
						aria-hidden="true">关闭</button>
				</div>
			</div>
		</div>
	</div>
</div>