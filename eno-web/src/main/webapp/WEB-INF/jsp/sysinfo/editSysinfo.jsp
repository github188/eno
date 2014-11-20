<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!-- 
	AUTHOR: CHENPING
	Created Date: 2014年10月14日上午10:03:58
	LastModified Date:
	Description: 
 -->
<style type="text/css">
    .file-box{ position:relative;width: 760px}
    .filename{ height:22px; border:1px solid #cdcdcd;width: 500px}
    .file{ position:absolute; top:0; right:80px; height:24px; filter:alpha(opacity:0);opacity: 0;width:260px }
    .button {
        background-color:#78CDD0;
        color:#ffffff;
        height:30px;
        line-height:30px;
        width:60px;
        display:inline-block;
        text-align:center;
        font-size:14px;
        cursor:pointer;
    }
    .bar {
        height: 18px;
        background: green;
    }
</style>
<div class="easyui-panel" title="系统信息" style="width:auto;height:600px;padding:10px;">
		<form action="${pageContext.request.contextPath}/okcsys/sysinfo/update" method="post" enctype="multipart/form-data" class="form-horizontal">
	<div class="row-fluid">
		<div class="span6">
			<div class="control-group">
				<label class="control-label assetclass">系统名称:</label>
				<div class="controls">
					<input name="sysname" value="${sysInfo.sysname }" class="f1 easyui-textbox" data-options="required:true,missingMessage:'系统名称不能为空！'"></input>
				</div>
			</div>

			<div class="control-group">
				<label class="control-label assetclass">显示标题:</label>
				<div class="controls">
					<input name="title" value="${sysInfo.title }" class="f1 easyui-textbox" data-options="required:true,missingMessage:'标题不能为空！'"></input>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label assetclass">首页背景图:</label>
				<div class="controls">
					<div class="file-box">

                    <div class="button">浏览</div>
                    <input type="text" style="margin-bottom:0px" class="filename" id="filetextfield" name="filetextfield">
                    <input type="file" data-url="/eno/okcsys/import/dataconfig/index/upload" onchange="document.getElementById('filetextfield').value=this.value" name="files[]" style="left:0px;width:60px" class="file" id="fileupload">
                    <div class="button startupload2">上传</div>
                    <div >请点击"浏览"按钮选择文件,选择后会自动上传</div>
                    <br/>
                    <div id="progress"  class="progress progress-striped active" style="width: 645px;display: none">
                        <div class="bar" style="width: 0%;"></div>
                    </div>
                    <div id="alertSuccess"  class="alert alert-success" style="display: none">
                        <button type="button" class="close" data-dismiss="alert">&times;</button>
                        <div class="alertMsg"> </div>
                    </div>
                    <div id="alertError"  class="alert alert-error" style="display: none">
                        <button type="button" class="close" data-dismiss="alert">&times;</button>
                        <div class="alertMsg"> </div>
                    </div>
                </div>
				</div>
			</div>
			<div class="control-group">
				<div class="controls btnleft">
				 <input type="submit" class="btn btn-primary" value="保存" ></input>
				</div>
			</div>
		</div>
	</div>
</form>
</div>






