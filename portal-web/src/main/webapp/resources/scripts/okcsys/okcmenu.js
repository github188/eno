/**
 * 系统菜单操作JS
 * 
 * 
 * */
$(function(){
	
	sysmenu.createOrUpdate();
	sysmenu.copy();
	sysmenu.del();
	sysmenu.upload();
	sysmenu.download();
});

var msgbox = function(success,msg) {
	var alert_css = "";
	if(success==false) {
		alert_css = "alert-block";
	} else {
		alert_css = "alert-success";
		if(msg=='' || msg==null) msg = "操作成功!";
	}
	var $message = $("#detailmsg");
	if($message.length) {
		if($message.html().length>0) {
			$message.empty();
		}
		$message.append("<div class=\"alert "+ alert_css +"\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">×</button>"+ msg +"</div>");
	}
}

var sysmenu = {
	createOrUpdate : function() {
		 //保存数据
		$(".savebutton").click(function(){
			var $form = $("#okcMenu");
			$form.validate();
			$form.submit();
		});
		
		
	},
	copy : function() {
		 //复制并保存
		$(".copy").click(function(){
			var okcmenuid = $("#okcmenuid").val();
			if(typeof(okcmenuid)=='undefined' || okcmenuid=='') {
				return false;
			}
			
			var $form = $("#okcMenu");
			$form.validate();

			var link = $(this).attr('link');

			$.post(link,$form.serialize(), function(message){
				console.log("msg:" + message.msg + ",flag:"+ message.success);
				
				msgbox(message.success,message.msg);
			}).done(function() {
				
				console.log("copy menu success");
			})
			.fail(function() {
				console.log("copy menu fail!");
			})
			.always(function() {
				
			});
		});
	},
	del : function() {
		//删除菜单
		$(".delete").click(function(){
			var okcmenuid = $("#okcmenuid").val();
			if(typeof(okcmenuid)=='undefined' || okcmenuid=='') {
				return false;
			}
			
			if(confirm('删除数据不可恢复，确认要删除码?')) {
				var action = $(this).attr('link');

				$.post(action,{"okcmenuid":okcmenuid},function(message){
					$("#okcmenuid").remove();
					msgbox(message.success,message.msg);
				})
				.done(function() {
					
					console.log("delete menu success");
				})
				.fail(function() {
					//alert( "delete menu fail!" );
					console.log("delete menu fail!");
				})
				.always(function() {
					
				});
			}

		});
		
	},
	
	
	
	// 上传菜单配置文件（导入） [ ChengKang 2014-07-25 ]
	upload:function()
	{
		$("#btnSubmitUpload").click					// 响应btnSubmitUpload按钮的click事件 [ ChengKang 2014-07-25 ]
		(
			function()
			{
				var filename = "MenuConfig";		// 传入重命名名称（这里设置自动将上传的文件重命名为【MenuConfig.文件后缀】） [ ChengKang 2014-07-25 ]
				var file = $("#file").val();				// 获取file控件中选择的需要上传的文件 [ ChengKang 2014-07-25 ]
				if(file == "")
				{
					alert("未选择需要上传的配置文件！");
					return false;
				}
				else
				{
					// [ ChengKang 2014-07-25 ]
					// 使用jQuery中的ajaxFileUpload()实现文件上传
					// 注意：需要在对应JSP文件开头引入ajaxfileupload.js文件，否则$.ajaxFileUpload会是undefined
					$.ajaxFileUpload(
						{
							// [ ChengKang 2014-07-25 ]
							// 文件上传到的url位置，上传请求将交由后台Java程序处理
							// 这里url中的/upload/menu作为参考依据，在PageManageController.java中被用来处理该请求
							// 注意：实际文件上传到的地址并不是url所表示的地址，实际地址是：
							// 【Tomcat目录】\webapps\【项目名】\【PagelayoutServiceImpl中saveMenuFile指定的目录】\【PageManageController中saveMenuFile传递的参数】
							url : CONTEXT_PATH +'/okcsys/page/upload/menu',
							secureuri : false, 
							fileElementId : 'file',
							data : { filename : filename },		// 传入重命名名称
							dataType : 'json',
							success : function(data, status) 
							{
								status = $.trim(status);
								if (status.length == 0 || status == "")
								{
									alert("文件类型错误，上传失败！");
								}
								else
								{
									alert("上传成功！");
								}
								console.log(status);
								//var fileName = data.substring(data.lastIndexOf('/')+1, data.length);
							},
							error : function(data, status, e) // 相当于java中catch语句块的用法
							{
								console.log(data);
								console.log(e);
								alert("配置文件上传失败！");
							}
						}	// End ajaxFileUpload Function
					);	// End ajaxFileUpload
				}	// End else
			}	// End Function
		);	// End click
	},	// End upload Function
	
	// 下载菜单配置文件（导出） [ ChengKang 2014-07-27 ]
	download:function()
	{
		$("#btnSubmitDownload").click						// 响应btnSubmitDownload按钮的click事件 [ ChengKang 2014-07-27 ]
		(
				function()
				{
					var url = CONTEXT_PATH +'/okcsys/page/download/menu';
					$.post(url, "", function(data)
					{
						if (data != "Error")
						{
							alert('菜单配置导出成功！');
							var Path = CONTEXT_PATH + "/" + data.substring(data.indexOf('/')+1, data.length);
							window.open(Path);
						}
						else
						{
							alert('Error', data.message.msg);
						}
					}	// End Post Function
					);	// End Post
				}	// End Function
		);	// End click
	}
	
};


