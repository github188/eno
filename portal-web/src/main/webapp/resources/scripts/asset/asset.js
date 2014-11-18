/**
 * 资产台账JS操作代码
 * 
 * @author CHENPING
 * */

$(function() {
	//add location system enery
	$("#btnAddDetail").click(function(){
		//show locsytem form content
		$("#detailcontent").show();
		$(".savebutton").show();
	});
	
	$(".date").datetimepicker({
	      language: 'zh-CN',
	      pick12HourFormat: true
	   });
	
	//验证新增资产编码是否存在
	$("#assetnum").change(function(){
		var checkUrl = $(this).attr("data-check-url");
		//console.log(checkUrl);
		$.get(checkUrl,{assetnum:$("#assetnum").val()},function(result){
			console.log(result);
			if(result=='false') {
				alert('数据已经存在');
				$("#assetnum").focus();
			} else {
				$("#assetnum").focus();
			}
		});	
	});
	
	
	//提交保存资产信息
	$("#submit").click(function(){
		$("#asset").submit();
	});
	

	
	//显示资产台账列表对话框
	dialog.asset();
	
	//显示位置列表对话框
	dialog.location();
	
	//显示计量器组列表对话框
	dialog.metergroup();

	//显示计量器列表对话框
	dialog.meter();
		
	//显示类别属性定义窗口
	dialog.assetattribute();
	
	//显示公司列表对话框
	dialog.companies();
	
	//显示故障代码列表对话框
	dialog.failure();
	
	//显示类结构
	dialog.classstructure();
	
	//显示字典数据
	dialog.dictionary();
	
	
	//保存资产计量器
	$("#assetMeter").validate({
		submitHandler: function(form) {
			$.post($(form).attr('action'),$(form).serialize(),function(data){
				console.log(data);
				var alert_css = "",msg = "";
				if(data.success==false) {
					alert_css = "alert-block";
					msg = data.msgs!=null ? data.msgs : data.msg;
				} else {
					alert_css = "alert-success";
					msg = "操作成功!";
					//刷新记量器数据表格
					$('#assetspectable').dataTable().fnDraw();
				}
				var $message = $("#detailmsg");
				if($message.length) {
					if($message.html().length>0) {
						$message.empty();
					}
					$message.append("<div class=\"alert "+ alert_css +"\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">×</button>"+ msg +"</div>");
				}
			});
			
		  }
	});
	
	
	
	
	
	/**
	 * 资产计量器
	 * */
	var oTableAssetmeter;
	var selected_id,num,description;
	oTableAssetmeter = $("#assetmetertable").dataTable({ 
		"bFilter" : false,
        "fnInitComplete": function () {
            $("div.widget-header").append('<div style="width:130px;left:0px;position:absolute;"><span class="icon-list"></span><h3 class="">计量器</h3></div>');
         },
         "oLanguage": {
	         "sUrl":CONTEXT_PATH+"/resources/plugins/datatables/jquery.dataTables.zh_cn.txt"
	      },
       // "sDom": '<"widget-header"frtipr>t',
        "sDom": '<"widget-header custom-table assetmetertable-header"frtipr>t',
        "aoColumnDefs": [ { "bSortable": false, "aTargets": [ 0] },
                          { "bSortable": false, "aTargets": [ 6 ] },
                          { "bSortable": false, "aTargets": [ 7 ] }],
       "fnDrawCallback": function(oSettings) {
    	   $('#assetmetertable tbody tr').each(function(data) {
               selected_id = null,num=null,description=null;
               //第一列
               firstColumn = $(this).children(":first");
               //单击第一列
               $(firstColumn).click(function() {
            	   oTableAssetmeter.$('tr.row_selected').removeClass('row_selected');
                   $(this).parent().addClass('row_selected');
                   selected_id = $(this).attr("id");
                   if (selected_id != null) {
                 
                   	 //显示编辑		                        	  
                       $("#assetmeterFormContent").toggle();
                       $(".savebutton").show();
                       $("#metername").attr("readonly","readonly");

                       //为表单赋值
                       $.each(data, function(field, value) {
                           //console.log(field);
                           $("#" + field).val(value);
                       });

                   }
               });
               //删除设置计量器
               delColumn = $(this).children(":eq(7)").find('a');
               $(delColumn).click(function(){
            	   var url = $(this).attr('link');
            	   console.log(url);
            	   $.post(url,function(result){
            		   
            		   $('#assetmetertable').dataTable().fnDraw();
            	   });
            	   
               });
               
               $(this).hover(function() {
                   $(this).addClass('row_highlighted');
               },
               function() {
                   $(this).removeClass('row_highlighted');
               });
           });            	  
       }
    });
	
	$("#btnAddAssetmeter").click(function(){
		$("#assetmeterFormContent").show();
		$(".savebutton").show();
	});


	//显示计量器列表对话框
	dialog.measureunit();
	
	$("#assetattrid").change(function(){
		var checkUrl = $("#checkAssetattridUrl").val();
		console.log('check url:' + checkUrl);
		$.get(checkUrl,{assetnum:$("#assetnum").val(),assetattrid:$(this).val()},function(result){
			console.log(result);
			if(!result) {
				alert('数据已经存在');
				$(this).focus();
			} else {
				$("#measureunitid").focus();
			}
		});
	});

	
	//保存资产技术规范参数
	$("#assetSpec").validate({
		submitHandler: function(form) {
			//$(form).attr('action')
			$.post($(form).attr('action'),$(form).serialize(),function(data){
				console.log('test constrol');
				console.log(data);
				var alert_css = "",msg = "";
				if(data.success==false) {
					alert_css = "alert-block";
					msg = data.msgs!=null ? data.msgs : data.msg;
				} else {
					alert_css = "alert-success";
					if(data.msg=="") {
						msg = "操作成功!";
					} 
				}
				if(document.getElementById('detailmsg')==null) {
					$("#detailmsg").append("<div id=\"detailmsg\" class=\"alert "+ alert_css +"\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">×</button>"+ msg +"</div>");
				}
				else {
					$("#detailmsg").append("<div class=\"alert "+ alert_css +"\"><button type=\"button\" class=\"close\" data-dismiss=\"alert\">×</button>"+ msg +"</div>");
				}
			});
			
		  }
	});
	
	
	showAssetSpecTable();
	
});

var oAssetSpecTable;
/**
 * 显示类别规范列表
 * */
function showAssetSpecTable() {
	//类别结构定义ID
	var assetuid = $("form#asset #assetuid").val();
	console.log(assetuid);
	if(typeof(assetuid)=='undefined' || assetuid.length<0) {
		return false;
	}
	
	oAssetSpecTable = $('#assetspectable').dataTable({
		 "bFilter": false,
	     "oLanguage": {
	            "sUrl":CONTEXT_PATH+"/resources/plugins/datatables/jquery.dataTables.zh_cn.txt"
	         },
	     "fnInitComplete": function () {
	          $("div.assetspectable-header").append('<div style="width:180px;left:0px;position:absolute;"><span class="icon-list"></span><h3 class="">规范</h3></div>');
	      },
	     "sDom": '<"widget-header custom-table assetspectable-header"frtipr>t',
		"bStateSave" : false,
		"aaSorting" : [ [ 0, "asc" ] ],
		"bProcessing" : true,
		"bServerSide" : true,
		"bSortable" :false,
		"sAjaxSource" : CONTEXT_PATH + "/asset/"+ assetuid +"/specs",
		"aoColumns" : [ {"mData" : "assetSpec.assetspecid"},
		       			{"mData" : "assetSpec.assetattrid","bSortable": false },
		                {"mData" : "assetAttribute.description","bSortable": false},
		                {"mData" : "assetAttribute.datatype","bSortable": false},
		                {"mData" : "assetSpec.alnvalue","bSortable": false},
		                {"mData" : "assetSpec.numvalue","bSortable": false},
		                {"mData" : "assetSpec.measureunitid","bSortable": false},
		                {"mData" : "assetSpec.tablevalue","bSortable": false},
		                {"mData": null, "sClass": "center","sDefaultContent": '<a href="#"><img  src="'+ CONTEXT_PATH +'/resources/images/btn_garbage.gif" alt="标记要删除的行" /></a>'}
		               ],
		"aoColumnDefs": [
						 { "sName": "assetSpec.assetspecid",   "aTargets": [ 0 ] },
		                 { "aTargets": [ 8 ],"bSortable":false},
		                 { "sClass" : "hide",   "aTargets" : [ 0 ]}
		               ],           
	  "fnDrawCallback": function ( oSettings ) {
		                   $('#assetspectable tbody tr').each( function () {
		                	   selected_id = null;
		                       $(this).click( function () {
		                    	   oAssetSpecTable.$('tr.row_selected').removeClass('row_selected');
		                           $(this).addClass('row_selected');
		                           selected_id =  $(this).children(":first").html();
		                           if(selected_id!=null) {
		                        	   
			                       }
		                       });
		                       $(this).hover( function () {
		                    	   $(this).addClass( 'row_highlighted');
		                       }, function(){
		                    	   $(this).removeClass('row_highlighted');
		                       });
		                   });
		               }
	});
}
