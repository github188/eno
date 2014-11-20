var nameList = new Array();
var codeList = new Array();
$(function(){
	$.ajax({
		type:"post",
		url:CONTEXT_PATH+"/okcsys/expression/get/all",
		success:function(data){
			for (var i = 0; i < data.length; i++) {
				nameList.push(data[i].expname);
				codeList.push(data[i].expcode);
			}
		}
	});
	$("#expressInfo").click(function(){
		var name = $("#expname").val();
		var code = $("#expcode").val();
		var content = ($("#expcontent").val()).replace(/\+/g,"%2B").replace(/\&/g,"%26");
		var index = $("#expindex").val();
		var code_c = $("#codecheck").css("display");
		var name_c = $("#namecheck").css("display");
		var null_c = $("#check_0").css("display");
		if(code_c!="none"||name_c!="none"||null_c!="none"){
			return;
		}
		if(name==""||code==""||content==""){
			$("#check_0").show();
			return;
		}
		$.ajax({
			type:"post",
			url:CONTEXT_PATH+"/okcsys/expression/save",
			data:"expname="+name+"&expcode="+code+"&expcontent="+content+"&expindex="+index,
			success:function(b){
				$("#success_m").show();
			}
		});
		
	});
});
//验证
function check(obj,str){
	var ismodi = $("#ismodi").val();
	var name_t = nameList;
	var code_t = codeList;
	if(ismodi==1){
		var code = $("#code_o").val();
		var name = $("#name_o").val();
		for (var i = 0; i < nameList.length; i++) {
			if(nameList[i]==name){
				nameList[i]="";
			}
		}
		for (var i = 0; i < codeList.length; i++) {
			if(codeList[i]==code){
				codeList[i]="";
			}
		}
	}else{
		nameList = name_t;
		codeList = code_t;
	}
	var b=false;
	if($(obj).val()==""){
		$("#check_0").show();
	}else{
		$("#check_0").hide();
		if(str==="codecheck"){
			var expcode = $(obj).val();
			$("#check_0").hide();
			for (var i = 0; i < codeList.length; i++) {
				if(expcode==codeList[i]){
					b=true;
					break;
				}
			}
		}else{
			var expname = $(obj).val();
			$("#check_0").hide();
			for (var i = 0; i < nameList.length; i++) {
				if(expname==nameList[i]){
					b=true;
					break;
				}
			}
		}
	}
	if(b){
		$("#"+str).show();
	}else{
		$("#"+str).hide();
	}
}
function nullcheck(obj,str){
	var content = $(obj).val();
	if(content==""){
		$("#"+str).show();
	}else{
		$("#"+str).hide();
	}
}
//双击显示表达式
function showContent(obj){
	var content = $(obj).siblings(".content:eq(0)").html();
	alert(content);
}
//分页跳转
function getInfos(obj){
	var page = $(obj).val();
	var size = $("#page_s").val();
	window.location.href=CONTEXT_PATH+"/okcsys/expression/get/"+page+"/"+size;
}
//修改
function modify(expindex,expcode,expname,expcontent){
	$("#ismodi").val(1);
	$("#expindex").val(expindex);
	$("#expcode").val(expcode);
	$("#expname").val(expname);
	$("#code_o").val(expcode);
	$("#name_o").val(expname);
	$("#expcontent").val(expcontent);
}
//删除
function deleteone(expindex){
	window.location.href=CONTEXT_PATH+"/okcsys/expression/delete/"+expindex;
}
