$(function() {
	// 往资产分类中填充数据
	$.getJSON(CONTEXT_PATH + '/asset/class/structurelist?parent=', function(data) {
		$("#system").empty();
		for (var i = 0; i < data.length; i++) {
			if (i == 0) {
				getclassid(data[i].classificationid);
			}
			$("#system").append("<option value='" + data[i].classificationid + "'>"	+ data[i].description + "</option>");
		}
	});
	// 监听分类change事件
	$("#system").live("change", function() {
		$("#attribute").select2('val',"");
		getclassid($(this).val());
	});
	// 模块切换
	$("#classid").live("change", function() {
		getNotSetList($(this).val());
		getSetList($(this).val());

	});
	// 保存按钮
	$("#savePanelConfig").live("click", function() {
		var description = $("#modul").val();
		var atrr_n=new Array(),atrr_y=new Array();
		var classstructureid = $("#classid").val();
		var atrrArray_n = $("#property_not").datagrid('getChecked');
		var atrrArray_y = $("#property_yes").datagrid('getChecked');
		
		$.post(CONTEXT_PATH + '/okcsys/panel/saveConfig',{classstructureid:classstructureid, description:description,
			panelConfig_not:JSON.stringify(atrrArray_n), panelConfig_yes:JSON.stringify(atrrArray_y)},function(b){
			if(b == "success") {
				alert("设置成功！");
			}else{
				alert("设置失败！");
			}
		});
		return false; // 为了防止普通浏览器进行表单提交和产生页面导航（防止页面刷新？）返回false
	});
});
/**
 * 根据parent找对应的子级
 */
function getclassid(parent) {
	// 往资产分类中填充数据
	$.getJSON(CONTEXT_PATH + '/asset/class/structurelist?parent=' + parent,	function(data) {
		$("#classid").empty();
		for (var i = 0; i < data.length; i++) {
			$("#classid").append("<option value='" + data[i].classificationid + "'>" + data[i].description + "</option>");
		}
		getNotSetList($("#classid").val());
		getSetList($("#classid").val());
	});
}
/**
 * 根据分类id找不可设置属性
 */
function getNotSetList(classId) {
	$('#property_not').datagrid({
        method:'get',
        url:CONTEXT_PATH + '/okcsys/panel/getClassSpec?classid=' + classId + '&type=not',//url调用Action方法  
        loadMsg : '数据装载中......',  
        rownumbers:true,
        singleSelect:false,//为true时只能选择单行  
        fitColumns:true,//允许表格自动缩放，以适应父容器  
	});
}

/**
 *  根据分类id找可设置属性
 * @param classId
 */
function getSetList(classId) {
	$('#property_yes').datagrid({
		method:'get',
		url:CONTEXT_PATH + '/okcsys/panel/getClassSpec?classid=' + classId + '&type=',//url调用Action方法  
		loadMsg : '数据装载中......',  
		rownumbers:true,
		singleSelect:false,//为true时只能选择单行  
		fitColumns:true,//允许表格自动缩放，以适应父容器  
	});
}