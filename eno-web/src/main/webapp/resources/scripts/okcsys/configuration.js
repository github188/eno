var editable = false;
var receive = "";
/**
 * 组态配置
 * 
 * @author CHENPING
 * @version 1.0
 */
$(window).load(
function() {
	if(window.opener){
		editable = true;
		$("#structure-toolbar").show();
		
		ko.bindingHandlers.resizable = {
				init : function(element, valueAccessor) {
					var options = valueAccessor();
					$(element).resizable(options);
				}
		};
		//绑定拖拽方法
		ko.bindingHandlers.draggable = {
				init : function(element, valueAccessor) {
					var options = valueAccessor();
					if($(element).attr("pagetagtype")!=98){
						$(element).draggable(options);
					}
				}
		};
		
		ko.applyBindings({});
	}else{
		return;
	}
});
// 搜索指定的TagName并标记该设备 [ ChengKang 2014-09-10 ]
$("#btnSearchTag").click(function()
{
	var findName = $("#SearchTagName").attr("value");
	var meters = $(".meter");
});	// End Function
//显示设备点名称
$("#toshowTagname").live("click",function(){
	var sta = $("#PLAN").css("display");
	if(sta!="none"){
		var spa = $("#PLAN").find("span.showTagname");
		if(typeof(spa)!="undefined"){
			var isShow = $("#PLAN").find("span.showTagname:eq(0)").css("display");
			if(isShow=="none"){
				$(spa).show();
			}else{
				$(spa).hide();
			}
		}
	}
});


$("#btnSavePagetag").click(function() {
	
	var meters = $(".meter");
	var pagtags = new Array();
	$.each($(meters), function(index, obj) {
		// 获取测点结构图位置，并保存到数组中
		pagtags.push({
			"left" : $(obj).position().left,
			"top" : $(obj).position().top,
			"pagetagid" : $(obj).attr("pagetagid"),
			"pagetab" : $(obj).attr("pagetab")
		});
	});
	if (pagtags.length > 0) {
		var data = JSON.stringify(pagtags);
		var $layout = $(".structure");
		var layoutid;
		var id;
		$.each($layout, function(index, obj){
			id = $(obj).attr("layoutid");
			if(typeof(id)!='undefined' && id != '') {
				layoutid = id;
			}
		});
		//var layoutid = $(".structure").attr("layoutid");
		if(typeof(layoutid)=='undefined' || layoutid=='') {
			alert('操作异常，获取布局ID失败!');
			//return false;
		} else {
			var action = CONTEXT_PATH+"/okcsys/page/pagetag/"+layoutid + "/update";
			$.post(action,{"data":data},function(data){
				alert("计量点位置更新成功，请关闭窗口!");
			});
		}
	}
	
	//保存面板实例
	var parentdiv = $("#pagetabsContent>.active div.P_98").filter(function(index){
		return $(this).html()!="";
	});
	if(parentdiv.length>0){
		var Ptag_exp = new Array();
		for (var j = 0; j < $(parentdiv).length; j++) {
			//获取所有需绑定表达式的元素(按钮和占位符)
			//标题
			var title = $($(parentdiv).get(j)).find("#panel_title>span:eq(0)").html();
			//占位符内容
			var inputs = $($(parentdiv).get(j)).find("span.mb_text");
			//按钮内容
			var buttons = $($(parentdiv).get(j)).find("button.mb_button");
			var input_texts = new Array();
			if(inputs.length>0){
				for (var i = 0; i < inputs.length; i++) {
					input_texts.push(inputs[i]);
				}
			}
			if(buttons.length>0){
				for (var i = 0; i < buttons.length; i++) {
					input_texts.push(buttons[i]);
				}
			}
			//扫描获取表达式配置
			var taguid = $($(parentdiv).get(j)).parent().attr("pagetagid");
			var id_exp = new Array();
			id_exp.push({
				id:"panel_title",
				exp:title
			});
			for (var i = 0; i < input_texts.length; i++) {
				var str = "";
				if($(input_texts[i]).hasClass("write_n")){
					str = $(input_texts[i]).html();
				}else{
					str = $(input_texts[i]).attr("script_com");
					if(str.length==0){
						continue;
					}
				}
				id_exp.push({
					id:$(input_texts[i]).attr("id"),
					exp:str
				});
			}
			if(id_exp.length>0){
				Ptag_exp.push({
					taguid:taguid,
					express:id_exp
				});
			}
			//转化string
			var str = JSON.stringify(Ptag_exp);
		}
		if(Ptag_exp.length>0){
			$.post(CONTEXT_PATH+"/okcsys/savepanel",{panelcode:str},function(b){
				alert("面板配置已保存");
			});
		}
	}
	
});