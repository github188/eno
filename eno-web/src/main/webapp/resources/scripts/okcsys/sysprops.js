/*var cur_info = ""; //保存选中tr
var pathName=window.document.location.pathname;//获取主机地址之后的目录
var projectName=pathName.substring(0,pathName.substring(1).indexOf('/')+1);
var i=parseInt($("#i").val());//总页数
var j=parseInt($("#j").val());//页号
var syspropid=$("#propid").val();//系统属性主键
var propname=$("#propname").val();//系统属性名
//选中要编辑或删除的行
function chooseOne(choose){
	if($(choose).children("td").hasClass("green_bg")){
		$(choose).children("td").removeClass("green_bg");
		$(choose).children("td").removeClass("orange_font");
		cur_info = "";
	}else{
		$(choose).children("td").addClass("green_bg");
		$(choose).children("td").addClass("orange_font");
		cur_info = choose;
	}
	$(choose).siblings("tr").children("td").removeClass("green_bg");
	$(choose).siblings("tr").children("td").removeClass("orange_font");
}

//新增系统属性
$("#create_sysprop").click(function() {
	window.location.href = projectName + "/okcsys/sysprops/newSysprop/"+propname;
});

//编辑系统属性
$("#edit_sysprop").click(function() {
	if(cur_info!=""){
		window.location.href = projectName + "/okcsys/sysprop/updateSysprop/"+ $(cur_info).children("td:eq(0)").text();//传主键参数给编辑页面
	}else{
		alert("请先选中需要编辑的行");
	}
});

//删除系统属性
$("#del_sysprop").click(function() {
	if(cur_info!=""){
		if(confirm("确认删除？")){
			window.location.href =projectName+"/okcsys/sysprop/removeSysprop/"+$(cur_info).children("td:eq(0)").text();
		}
	}else{
		alert("请先选中需要删除的行");
	}
});
*/

$(document).ready(function() {
	
//dataTable实现功能
	$('#W_tab').dataTable( {
		"bFilter":true,//是否启用搜索(显示搜索)
		"bDestroy":true,
		"bAutoWidth":false,
		"bJQueryUI":true,//使用jqueryUI样式，需引入dataTable下的样式
		"sDom": 'rt<"bottom"p>',//表格元素位置（top/bottom/clear||f<搜索>t<表格主体>l<每页显示>p<分页选项>r<查询显示>）
		"sAjaxSource":"./sysprops/getAllSysprop",//数据请求ul，可返回list、map等
		"fnServerData": function ( sSource, aoData, fnCallback ) {
			$.ajax( {
			"type": "POST",
			"url": sSource,
			"data": aoData,
			"success": fnCallback
			} );
		},
		"sAjaxDataProp":"",//返回集合map对象的key值：默认为"aadata";若返回为list，应为""。
		"sPaginationType": "full_numbers",
		"aLengthMenu": [5, 8],//每页显示-菜单栏
		"iDisplayLength":5,//默认显示行数
		"oLanguage": {
            "sProcessing": "正在加载中......",
            "sLengthMenu": "每页显示 _MENU_ 条记录",
            "sZeroRecords": "对不起，查询不到相关数据！",
            "sEmptyTable": "表中无数据存在！",
            "sInfo": "当前显示 _START_ 到 _END_ 条，共 _TOTAL_ 条记录",
            "sInfoFiltered": "数据表中共为 _MAX_ 条记录",
            "sSearch": "搜索",
            "oPaginate": {
                "sFirst": "首页",
                "sPrevious": "上一页",
                "sNext": "下一页",
                "sLast": "末页"
            }
		},
		"aoColumns":[//表头所对应的对象字段名--在页面上可重命名字段
		             {"mData":"propid", "bVisible": false,"bSearchable":false},
		             {"mData":"propname"},
		             {"mData":"description"},
		             {"mData":"sysdefault","bSearchable":false},//bSearchable:是否参加搜索；[bVisible：是否可见]
		             {"mData":"propvalue"},
		             {"mData":"nullsallowed","bSearchable":false},
		             {"mData":"encrypted","bSearchable":false},
		             {"mData":"masked","bSearchable":false},
		             {"mData":"securelevel","bSearchable":false},
		             {"mData":"changeby","bSearchable":false},
		             {"mData":null,"bSearchable":false}//操作
		             ],
		   //行的回调函数
         "fnRowCallback": function( nRow, aData, iDisplayIndex, iDisplayIndexFull ) {
             // Bold the grade for all 'A' grade browsers
        	 	var propid=aData.propid;
        	 	console.log(propid);
        	 	//编辑和删除功能
               $('td:eq(9)', nRow).html(function(){
            	   var edit="<a href='./sysprops/updateSysprop?propid="+propid+"' class='btn'>编辑</a>";
            	   var delcli='return confirm("删除数据不可恢复，你确定要删除？")';
            	   var del="<a href='./sysprops/removeSysprop?propid="+propid+"' class='btn' onclick='"+delcli+"' >删除</a>";
            	   return edit+del;
               });
               //‘是否’数据的显示
               $('td:eq(4)', nRow).html(function(){
            	   var YesOrNo=aData.nullsallowed;
            	   if(YesOrNo==1){
            		   return "是";
            	   }else{
            		   return "否";
            	   }
               });
               $('td:eq(5)', nRow).html(function(){
            	   var YesOrNo=aData.encrypted;
            	   if(YesOrNo==1){
            		   return "是";
            	   }else{
            		   return "否";
            	   }
               });
               $('td:eq(6)', nRow).html(function(){
            	   var YesOrNo=aData.masked;
            	   if(YesOrNo==1){
            		   return "是";
            	   }else{
            		   return "否";
            	   }
               });
           }
		
	});

});