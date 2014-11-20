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
				<caption>系统中具有以下监视器</caption>
				<thead>
					<tr>
						<td>监视器ID</td>
						<td>监视器名称</td>
						<td>资源信息</td>
					</tr>
				</thead>
			</table>
			<div style="height: 740px; background-color: #D8E7E7;">
				<form
					action="${pageContext.request.contextPath}/okcsys/video/change/monitorcfg"
					method="post" id="change" style="height: 90%;">
					<div style="height: 100%; overflow-y: scroll;" class="">
						<table id="detail" style="width: 100%; text-align: center;">
							<tbody>
								<c:forEach items="${monitorcfgs}" var="monitorconfig"
									varStatus="sta">
									<tr
										class="<c:if test="${sta.index%2==0}">even</c:if><c:if test="${sta.index%2!=0}">odd</c:if>">
										<td>${monitorconfig.monitorid }</td>
										<td>${monitorconfig.monitorname }</td>
										<td>${monitorconfig.resourceid }</td>
										<td style="display: none;">${monitorconfig.displaysequence }</td>
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
							onclick="add_moni('add_dvr');">增加</button>
						<button class="blue_btn" type="button" onclick="dele('detail');">删除</button>
						<button class="blue_btn" type="button"
							onclick="modi('clicked','add_dvr');">修改</button>
						<button class="blue_btn" type="button" onclick="goUp('detail',3)">上移</button>
						<button class="blue_btn" type="button"
							onclick="goDown('detail',3)">下移</button>
						<button class="blue_btn" type="button">导入</button>
						<button class="blue_btn" type="button">导出</button>
						<button class="blue_btn" type="button" style="float: right;"
							onclick="canc('monitorconfig');">取消</button>
						<button class="blue_btn" type="button" style="float: right;"
							onclick="sub('change');">保存</button>
					</td>
				</tr>
			</table>
		</div>
		<div class="cover_div"></div>
		<div id="add_dvr" class="add_dvr">
			<h1>监视器设定</h1>

			<div class="form-group">
				<label>ID号码：</label> <input id="monitorID0" />
			</div>
			<div class="form-group">
				<label>监视器名称：</label> <input id="monitorname" placeholder="监视器名称" />
			</div>
			<div class="form-group">
				<label>资源信息：</label> <input id="resourceid" placeholder="资源信息" />
			</div>

			<div style="text-align: center;">
				<input type="hidden" id="id_change" value="0" />
				<button class="blue_btn" type="button" id="sub">确定</button>
				<button class="blue_btn" type="button" onclick="rem('add_dvr');">取消</button>
			</div>
		</div>
	</div>
</div>