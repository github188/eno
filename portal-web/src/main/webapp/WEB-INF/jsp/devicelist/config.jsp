<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!-- HTML START -->
<!-- 
	AUTHOR: zouzhixiang
	Created Date: 2014年8月24日 22:19:57
	LastModified Date: 
	Description: 设备列表配置页面
 -->
<form class="form-horizontal" id="devicelist" action="#">
	<div class="row-fluid">
		<div class="span6">
			<div class="control-group">
				<label class="control-label assetclass">描述：</label>
				<div class="controls">
					<input id="modul" name="modul" class="modul" />
				</div>
			</div>
<!-- 			<div class="control-group"> -->
<!-- 				<label class="control-label assetclass">系统：</label> -->
<!-- 				<div class="controls"> -->
<!-- 					<select id="system" name="system"></select> -->
<!-- 				</div> -->
<!-- 			</div> -->
			<div class="control-group">
				<label class="control-label assetclass">模块：</label>
				<div class="controls">
					<select id="classid" name="classid"></select>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label assetclass">属性：</label>
				<div class="controls">
					<select id="attribute" name="attribute" multiple="true"></select>
				</div>
			</div>
			<div class="control-group">
				<div class="controls btnleft">
					<button class="btn btn-primary" id="buildAssetAttrInfo">保存</button>
<!-- 					<button class="btn btn-default" id="btnClose">关闭</button> -->
				</div>
			</div>
		</div>
	</div>
</form>
<div style="margin: 0px; padding: 0px;">
	<table id="newt"></table>
</div>