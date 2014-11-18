<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!-- HTML START -->
<!-- 
	AUTHOR: shangpeibao
	Created Date: 2014年9月22日 15:42:00
	LastModified Date: 
	Description: 面板配置页面
 -->
<form class="form-horizontal" id="devicelist" action="#">
	<div class="row-fluid">
		<div class="span8">
			<div class="control-group">
				<label class="control-label assetclass">描述：</label>
				<div class="controls">
					<input id="modul" name="modul" />
				</div>
			</div>
			<div class="control-group">
				<label class="control-label assetclass">系统：</label>
				<div class="controls">
					<select id="system" name="system"></select>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label assetclass">模块：</label>
				<div class="controls">
					<select id="classid" name="classid"></select>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label assetclass">非可设定值：</label>
				<div class="controls">
					<table id="property_not" class="easyui-datagrid" title="非可设定值" style="width:200px;height:250px;">
				       <thead>
				           <tr>
				               <th data-options="field:'ck',checkbox:true"></th>
				               <th data-options="field:'assetattrid',width:150,hidden:true">assetattrid</th>
				               <th data-options="field:'classstructureid',width:150,hidden:true">classstructureid</th>
				               <th data-options="field:'description',width:150">属性名称</th>
				           </tr>
				       </thead>
			  	 	</table>
			  	</div>
			</div>
			<div class="control-group">
				<label class="control-label assetclass">可设定值：</label>
				<div class="controls">
					<table id="property_yes" class="easyui-datagrid" title="可设定值" style="width:200px;height:250px;">
				       <thead>
				           <tr>
				               <th data-options="field:'ck',checkbox:true"></th>
				               <th data-options="field:'assetattrid',width:150,hidden:true">assetattrid</th>
				               <th data-options="field:'classstructureid',width:150,hidden:true">classstructureid</th>
				               <th data-options="field:'description',width:150">属性名称</th>
				           </tr>
				       </thead>
			  	 	</table>
				</div>
			</div>
			<div class="control-group">
				<div class="controls btnleft">
					<button class="btn btn-primary" id="savePanelConfig">保存</button>
					<button class="btn btn-default" id="btnClose">关闭</button>
				</div>
			</div>
		</div>
	</div>
</form>
