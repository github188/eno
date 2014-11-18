<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
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
		<div class="sysBody">
			<form:form modelAttribute="sysconfig" method="post">
				<div class="sysPart"
					style="width: 82%; height: 100%; border-right-width: 2px;">
					<div class="sysPart"
						style="width: 70%; height: 30%; border-right-width: 2px; border-bottom-width: 2px;">
						<div id="files_tree" style="height:550px;">
							<div style="display: inline; float: left;">
								<ul id="file_tree" class="ztree"></ul>
							</div>
							<div class="btn_op" style="display: inline; float: right;margin-right: 4em;">
								<a id="add_file" href="#" class="btn btn-primary btn-lg" role="button" >新增文件夹</a>
								<a id="edit_file" href="#" class="btn btn-primary btn-lg" role="button">重命名文件夹</a>
								<a id="remove_file" href="#" class="btn btn-primary btn-lg" role="button">删除文件夹/文件</a>
								<a id="expandNodes_file" href="#" class="btn btn-primary btn-lg" role="button">扩展树形图</a>
								<a id="collapseNodes_file" href="#" class="btn btn-primary btn-lg" role="button">收缩树形图</a>
								<a id="checkout_file" href="#" class="btn btn-primary btn-lg" role="button">标记为主文件</a>
								<a id="files_upload" href="#" style="height:103px;font-size: 23px;line-height: 45px;" class="btn btn-primary btn-lg" role="button">
								文件上传<br><span style="font-size: 6px;">可同时上传多个文件</span></a>
							</div>
						</div>
						<div>
							<div style="display: inline;">文件拖至此处上传</div>
						</div>
					</div>

					<div class="sysPart"
						style="width: 30%; height: 30%; border-bottom-width: 2px; margin-left: -2px;">
						<p style="margin-top: 15px;margin-left: 10px;">
							<form:checkbox path="useCamereList" value="1"
								style="margin-left: 10px;" />
							<label for="useCamereList" style="cursor: pointer;">不使用摄像机列表</label>
						</p>
						<p style="margin-top: 15px; margin-left: 10px;">
							<label for="PTZspeed" style="cursor: pointer;">云台速度（0=默认）</label>
							<form:input path="PTZspeed"
								onkeyup="value=value.replace(/[^\d]/g,'')"
								onbeforepaste="clipboardData.setData('text',clipboardData('text').replace(/[^\d]/g,''))"
								style="width: 60px; margin: 15px 5px;" />
						</p>
						<p style="margin-top: 15px;margin-left: 10px;">
							<label for="Windowtitle" style="cursor: pointer;">窗口标题</label>
							<form:input path="Windowtitle"
								style="margin-top: 15px; margin-left: 10px;" />
						</p>
						<p style="margin-top: 15px;margin-left: 10px;">
							<form:checkbox path="stickied" value="1"
								style="margin-left: 10px;" />
							<label for="stickied" style="cursor: pointer;">窗口置顶</label>
						</p>
					</div>
					<div class="sysPart"
						style="float: right;width: 30%; height: 20%; border-bottom-width: 2px; margin-top: -2px; margin-left: -2px;">
						<p style="margin-top: 30px; margin-left: 15px;">
							<label>录像保存到 <input type="text" id="video_address"
								style="width: 60%;" disabled="disabled">
							</label>
						</p>
						<p style="margin-top: 30px; margin-left: 15px;">
							<label>图片保存到 <input type="text" id="photo_address"
								style="width: 60%;" disabled="disabled">
							</label>
						</p>
					</div>
					<div class="sysPart"
						style="width: 100%; height: 30%; border-bottom-width: 2px; margin-top: -2px;">
						<p style="margin: 10px 15px;">记录以下事件</p>
						<table style="width: 96%; height: 80%; margin: auto;">
							<tr>
								<td style="width: 33%;"><label style="cursor: pointer;"><input
										type="checkbox" value="启动和退出" />启动和退出</label></td>
								<td style="width: 33%;"><label style="cursor: pointer;"><input
										type="checkbox" value="云镜控制" />云镜控制</label></td>
								<td style="width: 33%;"><label style="cursor: pointer;"><input
										type="checkbox" value="查看历史录像" />查看历史录像</label></td>
							</tr>
							<tr>
								<td style="width: 33%;"><label style="cursor: pointer;"><input
										type="checkbox" value="修改配置" />修改配置</label></td>
								<td style="width: 33%;"><label style="cursor: pointer;"><input
										type="checkbox" value="本地录像" />本地录像</label></td>
								<td style="width: 33%;"></td>
							</tr>
							<tr>
								<td style="width: 33%;"><label style="cursor: pointer;"><input
										type="checkbox" value="切换摄像头" />切换摄像头</label></td>
								<td style="width: 33%;"><label style="cursor: pointer;"><input
										type="checkbox" value="抓拍图片" />抓拍图片</label></td>
								<td style="width: 33%;"></td>
							</tr>
						</table>
					</div>
				</div>
				<div class="sysPart"
					style="width: 18%; height: 100%; margin-left: -2px;">
					<div class="sysPart"
						style="width: 100%; height: 30%; border-bottom-width: 2px;">
						<div class="sysPart"
							style="width: 100%; height: 50%; border-bottom-width: 2px;">
							<p class="radioMargin">
								<label style="cursor: pointer;"><form:radiobutton
										path="toolBarShow" value="0" />不显示工具条</label>
							</p>
							<p class="radioMargin">
								<label style="cursor: pointer;"><form:radiobutton
										path="toolBarShow" value="1" />固定的工具条</label>
							</p>
							<p class="radioMargin">
								<label style="cursor: pointer;"><form:radiobutton
										path="toolBarShow" value="2" />浮动的工具条</label>
							</p>
						</div>
						<div class="sysPart"
							style="width: 100%; height: 50%; border-width: 0px;">
							<p class="radioMargin">
								<label style="cursor: pointer;"><form:radiobutton
										path="controlKeyboard" value="0" />不显示控制键盘</label>
							</p>
							<p class="radioMargin">
								<label style="cursor: pointer;"><form:radiobutton
										path="controlKeyboard" value="1" />固定的控制键盘</label>
							</p>
							<p class="radioMargin">
								<label style="cursor: pointer;"><form:radiobutton
										path="controlKeyboard" value="2" />浮动的控制键盘</label>
							</p>
						</div>
					</div>
					<div class="sysPart"
						style="width: 100%; height: 20%; border-bottom-width: 2px; margin-top: -2px;">
						<p class="radioMargin">
							<label style="cursor: pointer;"><form:radiobutton
									path="openShow" value="0" />启动后隐藏</label>
						</p>
						<p class="radioMargin">
							<label style="cursor: pointer;"><form:radiobutton
									path="openShow" value="1" />启动后显示空白窗</label>
						</p>
						<p class="radioMargin">
							<label style="cursor: pointer;"><form:radiobutton
									path="openShow" value="2" />启动后显示摄像机</label>
							<form:input path="openCamera"
								style="margin-left: 20px; width: 60px;" disabled="true" />
						</p>
						<p class="radioMargin">
							<label style="cursor: pointer;"><form:radiobutton
									path="openShow" value="3" />启动开始轮显组</label>
							<form:input path="openRotation"
								style="margin-left: 20px; width: 60px;" disabled="true" />
						</p>
					</div>
					<div class="sysPart" style="width: 100%; height: 40%;">
						<p style="margin: 10px 15px;">窗口位置(左、上、右、下)</p>
						<form:input path="left" class="siteInput"
							onkeyup="value=value.replace(/[^\d]/g,'')"
							onbeforepaste="clipboardData.setData('text',clipboardData('text').replace(/[^\d]/g,''))" />
						<form:input path="top" class="siteInput"
							onkeyup="value=value.replace(/[^\d]/g,'')"
							onbeforepaste="clipboardData.setData('text',clipboardData('text').replace(/[^\d]/g,''))" />
						<form:input path="right" class="siteInput"
							onkeyup="value=value.replace(/[^\d]/g,'')"
							onbeforepaste="clipboardData.setData('text',clipboardData('text').replace(/[^\d]/g,''))" />
						<form:input path="bottom" class="siteInput"
							onkeyup="value=value.replace(/[^\d]/g,'')"
							onbeforepaste="clipboardData.setData('text',clipboardData('text').replace(/[^\d]/g,''))" />
						<p class="radioMargin">
							<label style="cursor: pointer;"><form:checkbox
									path="hiddenTitle" value="1" />隐藏标题条</label>
						</p>
						<p class="radioMargin">
							<label style="cursor: pointer;"><form:checkbox
									path="hiddenMenu" value="1" />隐藏菜单</label>
						</p>
						<p class="radioMargin">
							<label style="cursor: pointer;"><form:checkbox
									path="hiddenStatusBar" value="1" />隐藏状态条</label>
						</p>
						<p class="radioMargin">
							<label style="cursor: pointer;"><form:checkbox
									path="controlKeyboardAutohide" value="1" />自动隐藏控制键盘</label>
						</p>
					</div>
				</div>
			</form:form>
		</div>
		<div
			style="width: 25%; height: 20px; border: 2px groove; float: left; margin: 10px 0px 0px 10px;">(修改系统参数后，请重新启动本软件)</div>
		<div
			style="width: 73%; float: left; text-align: right; margin-top: 10px;">
			<button class="blue_btn" type="button" onclick="subsysconfig()">保存</button>
			<button class="blue_btn" type="button"
				style="margin-left: 10%; margin-right: 10%;"
				onclick="window.location.href='${pageContext.request.contextPath }/okcsys/video/sysconfig'">还原</button>
		</div>
	</div>
	<div id="uploadModal" class="modal hide fade" tabindex="-1"
		role="dialog" aria-labelledby="uploadModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h3 id="uploadModalLabel">驱动上传</h3>
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
</div>
<script type="text/javascript">
$("input[name='openShow']").on("click", function() {
	var openshow = $("input[name='openShow']:checked").val();
	if (openshow == 2) {
		$("#openCamera").attr("disabled", false);
		$("#openRotation").attr("disabled", true);
	}
	if (openshow == 3) {
		$("#openCamera").attr("disabled", true);
		$("#openRotation").attr("disabled", false);
	}
});
$(function(){
	getFilesdata();
});
</script>