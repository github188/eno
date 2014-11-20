$(function () {
    $("#filetextfield").val('');
    var startupload = $(".startupload");
    var uploadUrl = "http://" + LOCALADDR + ":" + PORT + CONTEXT_PATH + "/okcsys/import/dataconfig/index/upload";
    $('#configTab a').click(function (e) {
        e.preventDefault();
        $(this).tab('show');
        $("#alertSuccess").hide();
        $("#alertError").hide();
        $("#filetextfield").val('');
        var tempHref = $(this).attr("href");
        if (tempHref == "#indexBackground" || tempHref == "#asset" || tempHref == "#night") {
            $("#home").addClass("active in");
            $("#report").removeClass("active in");
        }
        if (tempHref == "#indexBackground") {
            $("#expression").removeClass("active in");
            //上传首页背景图
            $("#fileupload").attr("data-url", CONTEXT_PATH + "/okcsys/import/pic/upload");
            uploadUrl = "http://" + LOCALADDR + ":" + PORT + CONTEXT_PATH + "/okcsys/import/pic/upload";
        }
        if (tempHref == "#asset") {
            $("#expression").removeClass("active in");
            //上传设备台账
            $("#fileupload").attr("data-url", CONTEXT_PATH + "/okcsys/import/asset/upload");
            uploadUrl = "http://" + LOCALADDR + ":" + PORT + CONTEXT_PATH + "/okcsys/import/asset/upload";
        }
        if (tempHref == "#night") {
            $("#expression").removeClass("active in");
            //上传夜景背景图
            $("#fileupload").attr("data-url", CONTEXT_PATH + "/okcsys/import/pic/night/upload");
            uploadUrl = "http://" + LOCALADDR + ":" + PORT + CONTEXT_PATH + "/okcsys/import/pic/night/upload";
        }
        if (tempHref == "#home") {
            $("#expression").removeClass("active in");
            //上传首页配置表
            $("#fileupload").attr("data-url", CONTEXT_PATH + "/okcsys/import/dataconfig/index/upload");
            uploadUrl = "http://" + LOCALADDR + ":" + PORT + CONTEXT_PATH + "/okcsys/import/dataconfig/index/upload";

        }
        if (tempHref == "#report") {
            $("#expression").removeClass("active in");
            //上传首页配置表
            $("#fileupload").attr("data-url", CONTEXT_PATH + "/reportConfig/upload");
            uploadUrl = "http://" + LOCALADDR + ":" + PORT + CONTEXT_PATH + "/reportConfig/upload";

        }
    });
    
    
    //登陆页参数配置--首页图片上传
    $(".startupload2").click(function () {
    	//上传首页背景图
    	$("#fileupload").attr("data-url", CONTEXT_PATH + "/okcsys/import/pic/upload");
    	uploadUrl = "http://" + LOCALADDR + ":" + PORT + CONTEXT_PATH + "/okcsys/import/pic/upload";
    	$.ajaxFileUpload
        (
            {
            	
                url: uploadUrl, //用于文件上传的服务器端请求地址
                secureuri: false, //是否需要安全协议，一般设置为false
                fileElementId: 'fileupload', //文件上传域的ID
                dataType: 'json', //返回值类型 一般设置为json
                success: function (data, status)  //服务器成功响应处理函数
                {
                    if (data.success == true) {
                        $("#alertSuccess").show();
                        $("#alertSuccess .alertMsg").text("上传成功!");
                        $("#progress").hide();
                    }
                    else {
                        $("#alertError").show();
                        $("#alertError .alertMsg").text("上传失败,请检查文件!");
                        $("#progress").hide();
                    }
                },
                error: function (data, status, e)//服务器响应失败处理函数
                {
                    $("#alertError").show();
                    $("#alertError .alertMsg").text("上传失败,请检查文件!");
                    $("#progress").hide();
                }
            }
        );
    });
    

//上传文件
    $(".startupload").click(function () {
        ajaxFileUpload();
    });

    function ajaxFileUpload() {
        $.ajaxFileUpload
        (
            {
                url: uploadUrl, //用于文件上传的服务器端请求地址
                secureuri: false, //是否需要安全协议，一般设置为false
                fileElementId: 'fileupload', //文件上传域的ID
                dataType: 'json', //返回值类型 一般设置为json
                success: function (data, status)  //服务器成功响应处理函数
                {
                    if (data.success == true) {
                        $("#alertSuccess").show();
                        $("#alertSuccess .alertMsg").text("上传成功!");
                        $("#progress").hide();
                    }
                    else {
                        $("#alertError").show();
                        $("#alertError .alertMsg").text("上传失败,请检查文件!");
                        $("#progress").hide();
                    }
                },
                error: function (data, status, e)//服务器响应失败处理函数
                {
                    $("#alertError").show();
                    $("#alertError .alertMsg").text("上传失败,请检查文件!");
                    $("#progress").hide();
                }
            }
        );
    }
    $(".startimport").click(function(){
    	var type = $("#import_T").val();//导入类型
    	var filepath = $("#excelfilePath").val();//文件路径
    	var urlPath = $("#file").attr("data-url");//方法路径
    	$.ajaxFileUpload
        (
            {
                url: "http://" + LOCALADDR + ":" + PORT + CONTEXT_PATH + urlPath + "/" + type, //用于文件上传的服务器端请求地址
                secureuri: false, //是否需要安全协议，一般设置为false
                fileElementId: 'file', //文件上传域的ID
                dataType: 'json', //返回值类型 一般设置为json
                success: function (data, status)  //服务器成功响应处理函数
                {
                    if (data==0) {
                        $("#alert_0").show();
                        $("#alert_0 .alertMsg").text("导入成功!");
                    }
                    else if(data==2||data==1){
                        $("#alert_12").show();
                        $("#alert_12 .alertMsg").text("导入失败,选择的文件内容有误!");
                    }
                },
                error: function (data, status, e)//服务器响应失败处理函数
                {
                }
            }
        );
    });
});
//过滤文件
function showfilePath(obj,divid){
	var filePath = $(obj).val();
	var str = filePath.split("\.");
	if(str[str.length-1]!="xlsx"&&str[str.length-1]!="xls"){
		$(obj).val("");
		alert("应导入excel文件（*.xlsx或*.xls）！！");
	}else{
		$("#"+divid).val(filePath);
	}
}
//选择导入类型的提示
function type_c(obj){
	if($(obj).val()==1){
		$("#note_1").show();
	}else{
		$("#note_1").hide();
	}
}