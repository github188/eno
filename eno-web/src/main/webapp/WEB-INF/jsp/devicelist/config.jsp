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
				<label class="control-label assetclass">系统：</label>
				<div class="controls">
					<input id="system" name="system" class="modul" />
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


<script src="<spring:url value="/resources/plugins/knockout/knockout-3.0.0.js"></spring:url>"></script>

<!-- <h2>Contacts</h2> -->
<!-- <div id='contactsList'> -->
<!--     <table class='contactsEditor'> -->
<!--         <tr> -->
<!--             <th>First name</th> -->
<!--             <th>Last name</th> -->
<!--             <th>Phone numbers</th> -->
<!--         </tr> -->
<!--         <tbody data-bind="foreach: contacts"> -->
<!--             <tr> -->
<!--                 <td> -->
<!--                     <input data-bind='value: firstName' /> -->
<!--                     <div><a href='#' data-bind='click: $root.removeContact'>Delete</a></div> -->
<!--                 </td> -->
<!--                 <td> -->
<!--                     <input data-bind='value: firstName' /> -->
<!--                     <div><a href='#' data-bind='click: $root.removeContact'>Delete</a></div> -->
<!--                 </td> -->
<!--                 <td> -->
<!--                     <input data-bind='value: firstName' /> -->
<!--                     <div><a href='#' data-bind='click: $root.removeContact'>Delete</a></div> -->
<!--                 </td> -->
<!--                 <td> -->
<!--                     <input data-bind='value: firstName' /> -->
<!--                     <div><a href='#' data-bind='click: $root.removeContact'>Delete</a></div> -->
<!--                 </td> -->
<!-- <!--                 <td> -->
<!-- <!-- 					<select name="showlabel" id="showlabel"> -->
<!-- <!-- 						<option value="true">原值</option> -->
<!-- <!-- 						<option value="false">开关</option> -->
<!-- <!-- 						<option value="false">文字</option> -->
<!-- <!-- 					</select> -->
<!-- <!-- 					<select name="fontFamily" id="fontFamily" data-bind="options: fontfamilies, -->
<!-- <!-- 	                       optionsText: 'text', -->
<!-- <!-- 	                       optionsValue: 'value'"> -->
<!-- <!-- 					</select> --> 
<!-- <!-- 				</td> -->
<!-- 				<td> -->
<!--                     <table> -->
<!--                         <tbody data-bind="foreach: phones"> -->
<!--                             <tr> -->
<!--                                 <td><input data-bind='value: type' /></td> -->
<!--                                 <td><input data-bind='value: number' /></td> -->
<!--                                 <td><a href='#' data-bind='click: $root.removePhone'>Delete</a></td> -->
<!--                             </tr> -->
<!--                         </tbody> -->
<!--                     </table> -->
<!--                     <a href='#' data-bind='click: $root.addPhone'>Add number</a> -->
<!--                 </td> -->
<!--             </tr> -->
<!--         </tbody> -->
<!--     </table> -->
<!-- </div> -->
 
<!-- <p> -->
<!--     <button data-bind='click: addContact'>Add a contact</button> -->
<!--     <button data-bind='click: save, enable: contacts().length > 0'>Save to JSON</button> -->
<!-- </p> -->
 
<!-- <textarea data-bind='value: lastSavedJson' rows='5' cols='160' disabled='disabled'> </textarea> -->