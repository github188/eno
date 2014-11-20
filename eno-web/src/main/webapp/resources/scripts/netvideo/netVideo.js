var i = 0; //计数器，用来批量命名
var runNum = -1;//用来判断方法是否有第一次调用 ，-1时为初次调用，否则不是
var tag = ''; //操作标识 ，用来区分操作类型
var isSort = 0;//判断是否进行过移动操作，0为未执行过移动操作，否则为执行过移动操作
var idList = []; //id集合，用来批量删除操作时保存删除数据id
var sortList = []; //用来保存排序后的sequence
var maxSequence = parseInt($("#maxSequence").val());//显示顺序最大值
/*显示和隐藏cover层,及显示其上层模块*/
function cover_show(id){
	if($(".cover_div").css("display") == "none"){
		$(".cover_div").show();
		$("#" + id).show();
	}else{
		$(".cover_div").hide();
		$("#" + id).hide();
		tag = '';
	}
}
/*修改标记*/
function changeTag(tagValue){
	tag = tagValue;
}

/*显示和隐藏密码*/
$("#show_password").change(function(){
	if($("#show_password").attr("checked") == "checked"){
		$(".password_td").attr("type","text");
	}else{
		$(".password_td").attr("type","password");
	}
});

/*选中行*/
function select_row(row){
	if($(row).hasClass("on")){
		$(row).removeClass("on");
		$(row).find("input").removeClass("on");
	}else{
		$(row).siblings().removeClass("on");
		$(row).siblings().find("input").removeClass("on");
		$(row).addClass("on");
		$(row).find("input").addClass("on");
	}
}

/*获取选中行的数据到编辑窗口,通过operate进行区分不同模块*/
function editRow(operate){
	var row = $("tr.on");
	if(tag == "edit"){
		if($(row).length>0){
			/*获取原有数据*/
			if(operate == "dvr"){ //DVR配置原有数据信息
				$("#dvrname").val($(row).children("td:eq(2)").text());
				$("#dvrtype").val($(row).children("td:eq(3)").text());
				$("#ipaddress").val($(row).children("td:eq(4)").text());
				$("#port").val($(row).children("td:eq(5)").text());
				$("#resourceid").val($(row).children("td:eq(8)").children("p").text());
				$("#username").val($(row).children("td:eq(6)").text());
				$("#password").val($(row).children("td:eq(7)").children("input").val());
				$("#repassword").val($(row).children("td:eq(7)").children("input").val());
				cover_show('add_dvr');
			}else if(operate == "rotation"){ //轮显组配置原有数据信息
				$("#rotationID").val($(row).children("td:eq(1)").text());
				$("#rotationName").val($(row).children("td:eq(2)").text());
				$("#rotationTime").val($(row).children("td:eq(3)").text());
				var str = $(row).children("td:eq(4)").children("p:eq(0)").html();
				var cameras =str.split(",");
				$("#cameras input[type='checkbox']").each(function(){
					if($.inArray($(this).val(), cameras)>=0){
						$(this).attr("checked","checked");
					}
				});
				cover_show('add_rotation');
			}
		}else{
			alert("请选择操作行！");
		}
	}else if(tag == "del"){
		if($(row).length>0){
			idList.push($(row).children("td:eq(1)").text());
			$("#delIds").val(idList);
			$(row).remove();
			$("#ischange").val(1);
		}else{
			alert("请选择操作行！");
		}
	}
}

/*编辑DVR配置*/
function editDvr(){
	var errorContent = '';//警告信息
	if(tag == "add"){
		$("#detailTable tbody tr").each(function(){
			if($(this).children("td:eq(2)").text() == $.trim($("#dvrname").val())){
				errorContent = "DVR名称已存在！";
			}
		});
		if($.trim(errorContent).length == 0){
			$("#detailTable tbody tr").each(function(){
				if($(this).children("td:eq(4)").text() == $.trim($("#ipaddress").val())){
					errorContent = "IP地址已被占用！";
				}
			});
		}
	}else if(tag == "edit"){
		$("#detailTable tbody tr").each(function(){
			if($(this).children("td:eq(2)").text() == $.trim($("#dvrname").val())){
				if(!$(this).hasClass("on")){
					errorContent = "DVR名称已存在！";
				}
			}
		});
		if($.trim(errorContent).length == 0){
			$("#detailTable tbody tr").each(function(){
				if($(this).children("td:eq(4)").text() == $.trim($("#ipaddress").val())){
					if(!$(this).hasClass("on")){
						errorContent = "IP地址已被占用！";
					}
				}
			});
		}
	}
	
	if($.trim(errorContent).length > 0){
		alert(errorContent);
	}else if($.trim($("#dvrname").val()).length == 0){
		alert("请输入DVR名称!");
	}else if($.trim($("#ipaddress").val()).length == 0){
		alert("请输入DVR的IP地址!");
	}else if($.trim($("#port").val()).length == 0){
		alert("请输入DVR的端口号!");
	}else if($("#password").val() != $("#repassword").val()){
		alert("两位输入的密码不一致 ！");
	}else{
		var tr_content = '';
		if(tag == "add"){
			tr_content+='<tr onclick="select_row(this);" style="cursor: pointer;">';
			tr_content+='<td style="display:none;">' + (maxSequence + i + 1) + '<input name="dvrcfgArray[' + i + '].dvrsequence" type="text" style="display:none;" value="' + (maxSequence + i + 1) + '"></td>';//显示顺序所在列
			tr_content+='<td style="display:none;"></td>';//id所在列
		}else if(tag == "edit"){
			tr_content+='<td style="display:none;">' + $("tr.on").children("td:eq(0)").text() + '<input name="dvrcfgArray[' + i + '].dvrsequence" type="text" style="display:none;" value="' +$("tr.on").children("td:eq(0)").text() + '"></td>';//显示顺序所在列
			tr_content+='<td style="display:none;">' + $("tr.on").children("td:eq(1)").text() + '<input name="dvrcfgArray[' + i + '].dvrcfgid" type="text" style="display:none;" value="' +$("tr.on").children("td:eq(1)").text() + '"></td>';//id所在列
		}
		tr_content+='<td style="width: 13%;">' + $("#dvrname").val() + '<input name="dvrcfgArray[' + i + '].dvrname" type="text" style="display:none;" value="' + $("#dvrname").val() + '"></td>';//DVR名称所在列
		tr_content+='<td style="width: 13%;">' + $("#dvrtype").val() + '<input name="dvrcfgArray[' + i + '].dvrtype" type="text" style="display:none;" value="' + $("#dvrtype").val() + '"></td>';//DVR类型所在列
		tr_content+='<td style="width: 13%;">' + $("#ipaddress").val() + '<input name="dvrcfgArray[' + i + '].ipaddress" type="text" style="display:none;" value="' + $("#ipaddress").val() + '"></td>';//DVR的IP地址所在列
		tr_content+='<td style="width: 13%;">' + $("#port").val() + '<input name="dvrcfgArray[' + i + '].port" type="text" style="display:none;" value="' + $("#port").val() + '"></td>';//DVR端口所在列
		tr_content+='<td style="width: 13%;">' + $("#username").val() + '<input name="dvrcfgArray[' + i + '].username" type="text" style="display:none;" value="' + $("#username").val() + '"></td>';//用户名所在列
		if(tag == "add"){
			tr_content+='<td style="width: 13%;"><input name="dvrcfgArray[' + i + '].password" type="password" readonly="readonly" class="password_td" value="' + $("#password").val() + '"></td>';//密码所在列
		}else if(tag == "edit"){
			tr_content+='<td style="width: 13%;"><input name="dvrcfgArray[' + i + '].password" type="password" readonly="readonly" class="password_td on" value="' + $("#password").val() + '"></td>';
		}
		if($("#resourceid").val().length > 30){ //当资源信息长度超过指定长度时，进行显示截取
			tr_content+='<td style="width:22%;">' + $("#resourceid").val().substr(0,30) + '...';
		}else{
			tr_content+='<td style="width:22%;">' + $("#resourceid").val();
		}
		tr_content+='<p style="display:none">' + $("#resourceid").val() + '</p><input name="dvrcfgArray[' + i + '].resourceid" type="text" style="display:none;" value="' + $("#resourceid").val() + '"></td>'//资源信息完整信息
		if(tag == "add"){
			tr_content+='</tr>';
			$("#detailTable tbody").append(tr_content);
		}else if(tag == "edit"){
			$("tr.on").empty();
			$("tr.on").append(tr_content);
		}
		cover_show("add_dvr");
		i++;
	}
}

/*编辑轮显组配置*/
function editRotation(){
	$("#ischange").val(1);
	var errorContent = '';//警告信息
	var selectCamera = '';//用来前台显示的已选择摄像机信息
	if(tag == "add"){
		$("#detailTable tbody tr").each(function(){
			if($(this).children("td:eq(2)").text() == $.trim($("#rotationName").val())){
				errorContent = "轮显组名称已存在！";
			}
		});
	}else if(tag == "edit"){
		$("#detailTable tbody tr").each(function(){
			if($(this).children("td:eq(2)").text() == $.trim($("#rotationName").val())){
				if(!$(this).hasClass("on")){
					errorContent = "轮显组名称已存在！";
				}
			}
		});
	}
	if($.trim(errorContent).length == 0){
		$("#detailTable tbody tr").each(function(){
			if($.trim($("#rotationTime").val()).length == 0){
				errorContent = "请输入轮显间隔时间！";
			}
		});
	}

	if($.trim(errorContent).length > 0){
		alert(errorContent);
	}else if($.trim($("#rotationName").val()).length == 0){
		alert("请输入轮显组名称!");
	}else if($.trim($("#rotationTime").val()).length == 0){
		alert("请输入轮显间隔时间!");
	}else if($("#cameras input[type='checkbox']:checked").length == 0){
		alert("请至少选择一个摄像机!");
	}else{
		var tr_content = '';
		var n;
		if($("table#detailTable>tbody>tr:last").length==0){
			n=0;
		}else{
			n = $("table#detailTable>tbody>tr:last>td:eq(1)").html();
		}
		if(tag == "add"){
			tr_content+='<tr onclick="select_row(this);" style="cursor: pointer;">';
			tr_content+='<td style="display:none;"></td>';//显示顺序所在列
			tr_content+='<td style="width: 13%;">'+(parseInt(n)+1)+'</td>'; //id所在列
		}else if(tag == "edit"){
			tr_content+='<td style="display:none;">' + $("tr.on").children("td:eq(0)").text() + '</td>';//显示顺序所在列
			tr_content+='<td style="width: 13%;">' + $("tr.on").children("td:eq(1)").text() + '</td>';//id所在列
		}
		tr_content+='<td style="width: 13%;">' + $("#rotationName").val() + '</td>';
		tr_content+='<td style="width: 13%;">' + $("#rotationTime").val() + '</td>';
		var len = $("#cameras input[type='checkbox']:checked").length;//记录已选中摄像机个数
		//获取已选中摄像机的值
		$("#cameras input[type='checkbox']:checked").each(function(index){
			if(index<len-1){
				selectCamera+=$(this).val() + ",";
			}else{
				selectCamera+=$(this).val();
			}
		});
		tr_content+='<td style="width: 61%;">' + selectCamera + '</td>';
		if(tag == "add"){
			tr_content+='</tr>';
			$("#detailTable tbody").append(tr_content);
		}else if(tag == "edit"){ //替换编辑前的显示数据
			$("tr.on").empty(); //清空原有数据 
			$("tr.on").append(tr_content); //替换为编辑后数据
		}
		cover_show("add_rotation");//隐藏轮显组编辑窗口
		$("#cameras input[type='checkbox']").attr("checked",false);//清空已选择摄像机，其它信息不清空，有利于提高新增轮显组时的效率
		i++;
	}
}

$("#rotationsub").on("click",function(){
	var num = $("#ischange").val();
	if(num!=0){
		$("table#detailTable>tbody tr").each(function(index){
			var m_id = $(this).find("td:eq(1)").html();
			var m_name = $(this).find("td:eq(2)").html();
			var re_interval = $(this).find("td:eq(3)").html();
			var checked_camera = $(this).find("td:eq(4)").html();
			$(this).find("td:eq(0)").append($("<input type='hidden' name='rotationConfigs["+index+"].rotationId' value="+m_id+">"))
			.append($("<input type='hidden' name='rotationConfigs["+index+"].rotationName' value="+m_name+">"))
			.append($("<input type='hidden' name='rotationConfigs["+index+"].rotationInterval' value="+re_interval+">"))
			.append($("<input type='hidden' name='rotationConfigs["+index+"].checkedCamera' value="+checked_camera+">"));
		});
		$("#rotationform").submit();
	}else{
		return;
	}
});
/*通用上移*/
function up(){
	$("#ischange").val(1);
	var row = $("tr.on");
	if($(row).length>0){
		if($(row).prev().length > 0){
			$(row).prev().before($(row));
			isSort = 1; //修改标记值 ，不为0时，表示进行过移动操作
		}else{
			alert("已是第一条数据，无法上移！");
		}
	}else{
		alert("请选择操作行！");
	}
}

/*通用下移*/
function down(){
	$("#ischange").val(1);
	var row = $("tr.on");
	if($(row).length>0){
		if($(row).next().length > 0){
			$(row).next().after($(row));
			isSort = 1; //修改标记值 ，不为0时，表示进行过移动操作
		}else{
			alert("已是最后一条数据，无法下移！");
		}
	}else{
		alert("请选择操作行！");
	}
}

/*通用提交方法，需显示顺序信息在第一行*/
function saveCommon(){
	if(isSort!=0){
		/*保存移动后的数据id顺序信息*/
		$("#detailTable tbody tr").each(function(){
			sortList.push($(this).children("td:eq(0)").text());
		});
	}
	$("#sortList").val(sortList);//显示顺序信息
	$("#delIds").val(idList);//已删除数据信息
	$("#tableFrom").submit();
}

/*仅当第一次调用时激活滚动条*/
function addRoll(){
	if(runNum == -1){
		$(".rollcontent").mCustomScrollbar();
		runNum++;
	}
}

/*添加滚动条*/
$(window).load(function() {
	$(".rollcontent").mCustomScrollbar();
});

/*上传样式修改，获取上传文件地址,displayDiv参数为显示文件地址框，fileInput为原文件上传按钮*/
function getUploadInfo(displayDiv,fileInput){
	$("#" + fileInput).click();//模拟点击事件
	$("#" + fileInput).change(function(){ //根据上传文件按钮的值来改变显示栏内容 
		$("#" + displayDiv).val($(this).val());
	});
}

/*监---视---器---模---块*/
var path = window.location.pathname;
if(path.indexOf("/monitorconfig")!=-1){
	//进入监视器配置页面执行
	$("table#detail tr").on("click",function(){
		if($(this).hasClass("clicked")){
			$(this).removeClass("clicked");
		}else{
			$(this).addClass("clicked").siblings().removeClass("clicked");
		}
	});
}else if(path.indexOf("/cameracfg")!=-1){
	$("table#cam_detail tr").on("click",function(){
		if($(this).hasClass("clicked")){
			$(this).removeClass("clicked");
		}else{
			$(this).addClass("clicked").siblings().removeClass("clicked");
		}
	});
}


//添加监视器配置
function add_moni(id){
	var choosen_tr = $("tr.clicked");
	var monitorid = "";
	var name = "";
	var resource = "";
	if($(choosen_tr).hasClass("clicked")){//有选中的情况
		monitorid = $(choosen_tr).find("td:eq(0)").html();
		name = $(choosen_tr).find("td:eq(1)").html();
		resource = $(choosen_tr).find("td:eq(2)").html();
	}
	$("#monitorID0").val(monitorid);
	$("#monitorname").val(name);
	$("#resourceid").val(resource);
	
	$(".cover_div").show();
	$("#" + id).show();
	$("button#sub").val(1);
}
$("button#sub").on("click",function(){
	var b = $(this).val();
	var str = new Array();//监视器名称数组
	var ids = new Array();//监视器编号数组
	var choosen_tr = $("tr.clicked");
	var last = $("table#detail>tbody>tr:last").index();
	for (var i = 0; i <= last; i++) {
		if(b==1){
			ids.push($("table#detail>tbody>tr:eq("+i+")").find("td:eq(0)").html());
			str.push($("table#detail>tbody>tr:eq("+i+")").find("td:eq(1)").html());
		}else{
			if($(choosen_tr).index()==i){
				break;
			}
			ids.push($("table#detail>tbody>tr:eq("+i+")").find("td:eq(0)").html());
			str.push($("table#detail>tbody>tr:eq("+i+")").find("td:eq(1)").html());
		}
	}
	var monitorid = $("#monitorID0").val();
	var monitorname = $("#monitorname").val();
	var resourceid = $("#resourceid").val();
	if(monitorid==""||monitorname==""){
		alert("ID编号或监视器名称不能为空！！");
		return;
	}
	for (var i = 0; i < str.length; i++) {
		if(str[i]==monitorname){
			alert(monitorname+"重复！！");
			return;
		}
		if(ids[i]==monitorid){
			alert("编号"+monitorid+"重复！！");
			return;
		}
	}
	
	if(b==1){
		var displaysequence = 0;
		if($("table#detail>tbody>tr:last").index()!=-1){
			displaysequence = $("table#detail>tbody>tr:last").find("td:eq(3)").html();
		}
		var tbody = $("table#detail>tbody");
		var tr = $("<tr>");
		$(tr).append($("<td>").html(monitorid))
			 .append($("<td>").html(monitorname))
			 .append($("<td>").html(resourceid))
			 .append($("<td style='display:none;'>").html(parseInt(displaysequence)+1))
			 .append($("<td style='display:none;'>"))
			 .appendTo(tbody);
		$(tr).on("click",function(){//tr点击事件
			if($(this).hasClass("clicked")){
				$(this).removeClass("clicked");
			}else{
				$(this).addClass("clicked").siblings().removeClass("clicked");
			}
		});
		
		if($(tr).index()%2==0){
			$(tr).addClass("even");
		}else{
			$(tr).addClass("odd");
		}
	}else{
		$(choosen_tr).find("td:eq(0)").html($("#monitorID0").val());
		$(choosen_tr).find("td:eq(1)").html($("#monitorname").val());
		$(choosen_tr).find("td:eq(2)").html($("#resourceid").val());
	}
	$("#id_change").val(1);
	rem("add_dvr");//移除添加
});

//取消添加监视器配置
function rem(id){
	$(".cover_div").hide();
	$("#" + id).hide();
	$("#monitorID0").val("");
	$("#monitorname").val("");
	$("#resourceid").val("");
}

//删除监视器配置
function dele(tableId){
	var tbody = $("table#"+tableId+">tbody");
	var choosen_tr = $("tr.clicked");
	if(!$(choosen_tr).hasClass("clicked")){
		alert("请选中一行数据！！");
	}else{
		$(choosen_tr).remove();
		$(tbody).find("tr").removeClass("odd");$(tbody).find("tr").removeClass("even");
		$(tbody).find("tr:odd").addClass("odd");$(tbody).find("tr:even").addClass("even");
		$("#id_change").val(1);
	}
}

//修改监视器配置
function modi(classname,id){
	var choosen_tr = $("tr."+classname);
	if(!$(choosen_tr).hasClass(classname)){
		alert("请选中一行数据！！");
	}else{
		var monitorid = $(choosen_tr).find("td:eq(0)").html();
		var name = $(choosen_tr).find("td:eq(1)").html();
		var resource = $(choosen_tr).find("td:eq(2)").html();
		$("#monitorID0").val(monitorid);
		$("#monitorname").val(name);
		$("#resourceid").val(resource);
		$(".cover_div").show();
		$("#" + id).show();
		$("button#sub").val(0);
	}
}

//上移
function goUp(tableId,num){
	var choosen_tr = $("tr.clicked");
	if(!$(choosen_tr).hasClass("clicked")){
		alert("请选中一行数据！！");
	}else{
		var index = $(choosen_tr).index();
		if(index==0){
			return;
		}else{
			var up_tr = $("table#"+tableId+">tbody tr:eq("+(index-1)+")");
			//交换序号
			var dis_up = $(up_tr).find("td:eq("+num+")").html();
			var dis_ch = $(choosen_tr).find("td:eq("+num+")").html();
			$(up_tr).find("td:eq("+num+")").html(dis_ch);
			$(choosen_tr).find("td:eq("+num+")").html(dis_up);
			
			$(choosen_tr).insertBefore($(up_tr));
			var tbody = $("table#"+tableId+">tbody");
			$(tbody).find("tr").removeClass("odd");$(tbody).find("tr").removeClass("even");
			$(tbody).find("tr:odd").addClass("odd");$(tbody).find("tr:even").addClass("even");
			$("#id_change").val(1);
		}
	}
}
//下移
function goDown(tableId,num){
	var choosen_tr = $("tr.clicked");
	if(!$(choosen_tr).hasClass("clicked")){
		alert("请选中一行数据！！");
	}else{
		var index = $(choosen_tr).index();
		if(index==$("table#"+tableId+">tbody tr:last").index()){
			return;
		}else{
			var down_tr = $("table#"+tableId+">tbody tr:eq("+(index+1)+")");
			//交换序号
			var dis_down = $(down_tr).find("td:eq("+num+")").html();
			var dis_ch = $(choosen_tr).find("td:eq("+num+")").html();
			$(down_tr).find("td:eq("+num+")").html(dis_ch);
			$(choosen_tr).find("td:eq("+num+")").html(dis_down);
			$(choosen_tr).insertAfter($(down_tr));
			var tbody = $("table#"+tableId+">tbody");
			$(tbody).find("tr").removeClass("odd");$(tbody).find("tr").removeClass("even");
			$(tbody).find("tr:odd").addClass("odd");$(tbody).find("tr:even").addClass("even");
			$("#id_change").val(1);
		}
	}
}
//保存
function sub(formID){
	var id_change = $("#id_change").val();
	if(id_change!=0){
		$("table#detail>tbody tr").each(function(index){
			var m_id = $(this).find("td:eq(0)").html();
			var m_name = $(this).find("td:eq(1)").html();
			var re_id = $(this).find("td:eq(2)").html();
			var dis_ce = $(this).find("td:eq(3)").html();
			$(this).find("td:eq(4)").append($("<input type='hidden' name='monitorcfgArray["+index+"].monitorid' value="+m_id+">"))
									.append($("<input type='hidden' name='monitorcfgArray["+index+"].monitorname' value="+m_name+">"))
									.append($("<input type='hidden' name='monitorcfgArray["+index+"].resourceid' value="+re_id+">"))
									.append($("<input type='hidden' name='monitorcfgArray["+index+"].displaysequence' value="+dis_ce+">"));
		});
		$("#"+formID).submit();
	}else{
		return;
	}
}
//取消
function canc(url){
	window.location.href=""+url;
}


/************摄像机配置******************/
function show_detail(operatype,coverId,detailId){
	show_lt_tree();
	var choosen_tr = $("tr.clicked");
	if(operatype=="modi"){
		if(!$(choosen_tr).hasClass("clicked")){
			alert("请选中一行数据！！");
			return;
		}
		$("button#cam_sub").val(0);
	}else{
		$("button#cam_sub").val(1);
	}
	$("."+coverId).show();
	$("#"+detailId).show();
	if($(choosen_tr).hasClass("clicked")){
		var cam_id = "";
		var cam_name = "";
		var dvr_name = "";
		var dvr_chanell = "";
		var group_id = "";
		var matrix_name = "";
		var matrix_chanell = "";
		var matrixinde = "";
		var ptz = "";
		var ptzindex = "";
		var resourceid = "";
		var ptz_param1 = "";
		var ptz_param2 = "";
		var ptz_param3 = "";
		cam_id = $(choosen_tr).find("td:eq(1)").html();
		cam_name = $(choosen_tr).find("td:eq(2)").html();
		var DVR = $(choosen_tr).find("td:eq(3)").html().split("-");//dvr_name,dvr_ip,dvr_channel,
		dvr_name = DVR[0];dvr_chanell = DVR[2];
		group_id = $(choosen_tr).find("td:eq(11)").html();
		var group = $("#show_tree a").filter(function(index){
			return ($(this).attr("id").substr("show_tree".length+1)).split("_")[0]==group_id;
		});
		$(group).addClass("curSelectedNode");
		var Matrix = $(choosen_tr).find("td:eq(4)").html().split("-");//matrix,matrix_channel
		if(Matrix.length>1){
			matrix_name = Matrix[0];matrix_chanell = Matrix[1];
		}else{
			matrix_name = Matrix[0];matrix_chanell = "";
		}
		ptz = $(choosen_tr).find("td:eq(5)").html();
		resourceid = $(choosen_tr).find("td:eq(6)").find("input.res_id").val();
		var ptz_param = $(choosen_tr).find("td:eq(7)").html().split(",");
		ptz_param1 = ptz_param[0];ptz_param2 = ptz_param[1];ptz_param3 = ptz_param[2];
		$("div#add_cam #cam_id").val(cam_id);
		$("div#add_cam #cam_name").val(cam_name);
		$("div#add_cam #dvr_name option").each(function(){
			if($(this).html()==dvr_name){
				$(this).attr("selected","selected");
			}
		});
		$("div#add_cam #dvr_channel").val(dvr_chanell);
		$("div#add_cam #res_id").val(resourceid);
		$("div#add_cam #matrix option").each(function(){
			if($(this).html()==matrix_name){
				$(this).attr("selected","selected");
			}
		});
		$("div#add_cam #matrix_channel").val(matrix_chanell);
		$("div#add_cam input[name='ptz']").each(function(){
			if($(this).val().indexOf(ptz)>=0){
				$(this).siblings().attr("checked",false);
				$(this).attr("checked",true);
			}
		});
		$("div#add_cam #ptzparam_1").val(ptz_param1);
		$("div#add_cam #ptzparam_2").val(ptz_param2);
		$("div#add_cam #ptzparam_3").val(ptz_param3);
	}
}

//摄像机分组
function add_group(coverId,detailId){
	$("."+coverId).show();
	$("#"+detailId).show();
	var zNodes = new Array();
	$.post(CONTEXT_PATH+"/okcsys/video/get/g_info",function(data){
		if(data.length>0){
			getTreedata(JSON.parse(data));
		}else{
			getTreedata(zNodes);
		}
	});
}
//分组关闭
function clos_group(coverId,detailId){
	$("."+coverId).hide();
	$("#"+detailId).hide();
}

//关闭添加摄像机配置
function close_detail(coverId,detailId){
	$("."+coverId).hide();
	$("#"+detailId).hide();
	$("div#add_cam #cam_id").val("");
	$("div#add_cam #cam_name").val("");
	$("div#add_cam #dvr_channel").val("");
	$("div#add_cam #res_id").val("");
	$("div#add_cam #matrix_channel").val("");
	$("div#add_cam #ptzparam_1").val(0);
	$("div#add_cam #ptzparam_2").val(0);
	$("div#add_cam #ptzparam_3").val(0);
}

function add_cam(obj){
	var b = $(obj).val();
	var cam_id = $("#add_cam #cam_id").val();
	var cam_name = $("#add_cam #cam_name").val();
	var dvr = $("#add_cam #dvr_name").val()+"-"+$("#add_cam #dvr_channel").val();
	var group_id = "";
	if($("#show_tree a.curSelectedNode").length>0){
		group_id = ($("#show_tree a.curSelectedNode").attr("id").substr("show_tree".length+1)).split("_")[0];
	}
	var matrix = "";
	var matrixindex ="";
	if(($("#add_cam #matrix").val()).indexOf("不使用矩阵")>=0){
		matrix=$("#add_cam #matrix").val();
		matrixindex = "";
		matrix = matrix.split(",")[0];
	}else{
		matrix=$("#add_cam #matrix").val();
		matrixindex = matrix.split(",")[1];
		matrix = matrix.split(",")[0]+"-"+$("#matrix_channel").val();
	}
	
	var ptz = $("input[name='ptz']:checked").val();
	var ptzindex = ptz.split(",")[1];
	var ptz = ptz.split(",")[0];
	var resourceid = $("#add_cam #res_id").val();
	var res="";
	if(resourceid.length>30){
		res = resourceid.substr(0,30)+"...";
	}else{
		res = resourceid;
	}
	var ptz_param1 = $("#add_cam #ptzparam_1").val();
	var ptz_param2 = $("#add_cam #ptzparam_2").val();
	var ptz_param3 = $("#add_cam #ptzparam_3").val();
	var last =  $("table#cam_detail>tbody>tr:last").index();
	var choosen_tr = $("tr.clicked");
	var cam_id_lis = new Array();
	var cam_name_lis = new Array();
	for (var i = 0; i <= last; i++) {
		if(b==1){
			cam_id_lis.push($("table#cam_detail>tbody>tr:eq("+i+")").find("td:eq(1)").html());
			cam_name_lis.push($("table#cam_detail>tbody>tr:eq("+i+")").find("td:eq(2)").html());
		}else{
			if($(choosen_tr).index()==i){
				break;
			}
			cam_id_lis.push($("table#cam_detail>tbody>tr:eq("+i+")").find("td:eq(1)").html());
			cam_name_lis.push($("table#cam_detail>tbody>tr:eq("+i+")").find("td:eq(2)").html());
		}
	}
	
	//重复判断
	for (var i = 0; i < cam_name_lis.length; i++) {
		if(cam_name_lis[i]==cam_name){
			alert(cam_name+"重复！！");
			return;
		}
		if(cam_id_lis[i]==cam_id){
			alert("编号"+cam_id+"重复！！");
			return;
		}
	}
	//判断DVR输入通道（1-2147483647）
	var num_REG = new RegExp("^[1-9]{1}[0-9]{0,}");
	var num0 = $("#add_cam #dvr_channel").val();
	if(num0==""||!num_REG.test(num0)){
		alert("请输入一个整数！！");
		return;
	}else{
		if(parseInt(num0)>2147483647){
			alert("请输入1至2147483647的一个整数！！");
			return;
		}
	}
	//判断连接矩阵
	if(ptz=="云镜由矩阵控制"&&matrix.indexOf("不使用矩阵")!=-1){
		alert("请选择云镜控制矩阵！！");
		return;
	}
	if(matrix.indexOf("不使用矩阵")==-1){
		num1 = matrix.split("-")[1];
		if(num1==""||!num_REG.test(num1)){
			alert("请输入一个整数！！");
			return;
		}else{
			if(parseInt(num1)>2147483647){
				alert("请输入1至2147483647的一个整数！！");
				return;
			}
		}
	}
	
	//判断ptz参数：0和非数字输入提示
	var num_REG1 = new RegExp("^[0-9]{1}[0-9]{0,}");
	if(ptz_param1==""||!num_REG1.test(ptz_param1)||ptz_param2==""||!num_REG1.test(ptz_param2)||ptz_param3==""||!num_REG1.test(ptz_param3)){
		alert("请输入一个整数！！");
		return;
	}
	
	if(b==1){//添加时执行
		var dis_ce;
		if($("table#cam_detail>tbody>tr:last").index()!=-1){
			dis_ce = parseInt($("table#cam_detail>tbody>tr:last").find("td:eq(8)").html())+1;
		}else{
			dis_ce = 1;
		}
		var tr=$("<tr>");
		console.log(group_id);
		$(tr).append($("<td style='display: none;'>").html(""))
				 .append($("<td style='width:70px;'>").html(cam_id))
				 .append($("<td style='width:200px;'>").html(cam_name))
				 .append($("<td style='width:180px;'>").html(dvr))
				 .append($("<td style='width:180px;'>").html(matrix))
				 .append($("<td style='width:130px;'>").html(ptz))
				 .append($("<td style='width:241px;'>").html("<input class='res_id' type='hidden' value="+resourceid+">"+res))
				 .append($("<td style='display: none;'>").html(ptz_param1+","+ptz_param2+","+ptz_param3))
				 .append($("<td style='display: none;'>").html(dis_ce))
				 .append($("<td style='display: none;'>").html(matrixindex))
				 .append($("<td style='display: none;'>").html(ptzindex))
				 .append($("<td style='display: none;'>").html(group_id))
				 .append($("<td style='display: none;'>").html(""))
				 .appendTo($("table#cam_detail>tbody"));
		$(tr).on("click",function(){//tr点击事件
			if($(this).hasClass("clicked")){
				$(this).removeClass("clicked");
			}else{
				$(this).addClass("clicked").siblings().removeClass("clicked");
			}
		});
		
		if($(tr).index()%2==0){//相应行加相应的class
			$(tr).addClass("even");
		}else{
			$(tr).addClass("odd");
		}
	}else{//修改时执行
		var choosen_tr = $("#cam_detail>tbody>tr.clicked");
		$(choosen_tr).find("td:eq(1)").html(cam_id);
		$(choosen_tr).find("td:eq(2)").html(cam_name);
		$(choosen_tr).find("td:eq(3)").html(dvr);
		$(choosen_tr).find("td:eq(4)").html(matrix);
		$(choosen_tr).find("td:eq(5)").html(ptz);
		$(choosen_tr).find("td:eq(6)").html("<input class='res_id' type='hidden' value="+resourceid+">"+res);
		$(choosen_tr).find("td:eq(7)").html(ptz_param1+","+ptz_param2+","+ptz_param3);
		$(choosen_tr).find("td:eq(9)").html(matrixindex);
		$(choosen_tr).find("td:eq(10)").html(ptzindex);
		$(choosen_tr).find("td:eq(11)").html(group_id);
	}
	close_detail('cover_div','add_cam');
	$("#id_change").val(1);
}
//保存方法：获取table中的数据，用input元素填写在最后一列的td中，作为form表单提交的数据
function sub_cam(formId){
	var id_change = $("#id_change").val();
	if(id_change!=0){
		$("table#cam_detail>tbody tr").each(function(index){
			var td = $(this).find("td:eq(12)");
			var camcfg_id = "";
			var cam_id = "";
			var cam_name = "";
			var dvr_name = "";
			var dvr_chanell = "";
			var matrix_name = "";
			var matrix_chanell = "";
			var matrixindex = "";
			var ptz = "";
			var ptzindex = "";
			var dis_ce = "";
			var resourceid = "";
			var ptz_param1 = "";
			var ptz_param2 = "";
			var ptz_param3 = "";
			camcfg_id = $(this).find("td:eq(0)").html();
			if(camcfg_id!=""){
				$(td).append("<input type='hidden' name='cameracfgArray["+index+"].cameracfgid' value="+camcfg_id+">");
			}
			cam_id = $(this).find("td:eq(1)").html();
			$(td).append("<input type='hidden' name='cameracfgArray["+index+"].cameraid' value="+cam_id+">");
			cam_name = $(this).find("td:eq(2)").html();
			$(td).append("<input type='hidden' name='cameracfgArray["+index+"].cameraname' value="+cam_name+">");
			var DVR = $(this).find("td:eq(3)").html().split("-");//dvr_name,dvr_ip,dvr_channel,
			dvr_name = DVR[0];dvr_chanell = DVR[2];
			$(td).append("<input type='hidden' name='cameracfgArray["+index+"].dvrname' value="+dvr_name+">");
			$(td).append("<input type='hidden' name='cameracfgArray["+index+"].dvrchannel' value="+dvr_chanell+">");
			var Matrix = $(this).find("td:eq(4)").html().split("-");//matrix,matrix_channel
			if(Matrix.length>1){
				matrix_name = Matrix[0];matrix_chanell = Matrix[1];
			}else{
				matrix_name = Matrix[0];matrix_chanell = "";
			}
			var matrixindex = $(this).find("td:eq(9)").html();
			$(td).append("<input type='hidden' name='cameracfgArray["+index+"].matrixindex' value="+matrixindex+">");
			$(td).append("<input type='hidden' name='cameracfgArray["+index+"].matrix' value="+matrix_name+">");
			$(td).append("<input type='hidden' name='cameracfgArray["+index+"].matrixchannel' value="+matrix_chanell+">");
			var ptzindex = $(this).find("td:eq(10)").html();
			$(td).append("<input type='hidden' name='cameracfgArray["+index+"].ptzindex' value="+ptzindex+">");
			var group_id = $(this).find("td:eq(11)").html();
			$(td).append("<input type='hidden' name='cameracfgArray["+index+"].groupid' value="+group_id+">");
			ptz = $(this).find("td:eq(5)").html();
			$(td).append("<input type='hidden' name='cameracfgArray["+index+"].ptzcontrol' value="+ptz+">");
			resourceid = $(this).find("td:eq(6)").find("input.res_id").val();
			$(td).append("<input type='hidden' name='cameracfgArray["+index+"].resourceid' value="+resourceid+">");
			var ptz_param = $(this).find("td:eq(7)").html().split(",");
			ptz_param1 = ptz_param[0];ptz_param2 = ptz_param[1];ptz_param3 = ptz_param[2];
			$(td).append("<input type='hidden' name='cameracfgArray["+index+"].ptzparam1' value="+ptz_param1+">");
			$(td).append("<input type='hidden' name='cameracfgArray["+index+"].ptzparam2' value="+ptz_param2+">");
			$(td).append("<input type='hidden' name='cameracfgArray["+index+"].ptzparam3' value="+ptz_param3+">");
			dis_ce =  $(this).find("td:eq(8)").html();
			$(td).append("<input type='hidden' name='cameracfgArray["+index+"].displaysequence' value="+dis_ce+">");
		});
		$("#"+formId).submit();
	}else{
		return;
	}
}

//ztree显示
function getTreedata(zNodes){
	if(zNodes.length==0){
		zNodes=[{ id:1, pId:0, name:"根节点",open:true}];
	}
	var setting = {
			view: {
				selectedMulti: false
			},
			edit: {
				enable: true,
				showRemoveBtn: false,
				showRenameBtn: false
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				beforeRemove: beforeRemove,
				beforeRename: beforeRename
			}
		};

		function beforeRemove(treeId, treeNode) {//删除前提示
			return confirm("确认删除 节点 -- " + treeNode.name + " 吗？");
		}
		function beforeRename(treeId, treeNode, newName) {//重命名前执行，名称不能为“”
			if (newName.length == 0) {
				alert("节点名称不能为空.");
				var zTree = $.fn.zTree.getZTreeObj("group_tree");
				setTimeout(function(){zTree.editName(treeNode)}, 10);
				return false;
			}
			return true;
		}
		$(document).ready(function(){
			$.fn.zTree.init($("#group_tree"), setting, zNodes);//按配置初始化
		});
}
{
	var newCount = 1;
	function add(e) {//添加（兄弟/子）节点
		var zTree = $.fn.zTree.getZTreeObj("group_tree"),
		type = e.data.type,
		nodes = zTree.getSelectedNodes(),
		treeNode = nodes[0];
		if(treeNode){
			if (type=="chil") {
				treeNode = zTree.addNodes(treeNode, {id:(100 + newCount), pId:treeNode.id, name:"new node" + (newCount++)});
			} else {
				treeNode = zTree.addNodes(nodes[0].getParentNode(), {id:(100 + newCount), pId:0,name:"new node" + (newCount++)});
				
			}
			zTree.editName(treeNode[0]);
		}else{
			alert("请选中一个节点！！");
		}
		
	};
}
function edit() {
	var zTree = $.fn.zTree.getZTreeObj("group_tree"),
	nodes = zTree.getSelectedNodes(),
	treeNode = nodes[0];
	if (nodes.length == 0) {
		alert("请先选择一个节点");
		return;
	}
	zTree.editName(treeNode);
};
function remove(e) {//移除一个节点方法
	var zTree = $.fn.zTree.getZTreeObj("group_tree"),
	nodes = zTree.getSelectedNodes(),
	treeNode = nodes[0];
	if (nodes.length == 0) {
		alert("请先选择一个节点");
		return;
	}else if(nodes[0].getParentNode()==null){
		alert("根节点不可删除！！");
		return;
	}
	zTree.removeNode(treeNode, true);
};
function expandNode(e){//展开/收缩方法
	var zTree = $.fn.zTree.getZTreeObj("group_tree"),
	type = e.data.type;
	if (type == "expandAll") {
		zTree.expandAll(true);
	} else if (type == "collapseAll") {
		zTree.expandAll(false);
	} else {
		for (var i=0, l=nodes.length; i<l; i++) {
			zTree.setting.view.fontCss = {};
			if (type == "expand") {
				zTree.expandNode(nodes[i], true, null, null);
			} else if (type == "collapse") {
				zTree.expandNode(nodes[i], false, null, null);
			} else if (type == "toggle") {
				zTree.expandNode(nodes[i], null, null, null);
			} else if (type == "expandSon") {
				zTree.expandNode(nodes[i], true, true, null);
			} else if (type == "collapseSon") {
				zTree.expandNode(nodes[i], false, true, null);
			}
		}
	}
}

function getAllnode(){//获得树中数据
	var yNodes = new Array();
	$("#group_tree li").each(function(){
		var id = ($(this).children("a").attr("id").substr("group_tree".length+1)).split("_")[0];
		var pId = ($(this).parent("ul").attr("id").substr("group_tree".length+1)).split("_")[0];
		var name = $(this).children("a").attr("title");
		yNodes.push({
			id:parseInt(id),
			pId:pId==""?0:parseInt(pId),
			name:name,
			open:true
		});
	});
	clos_group('cover_div','group_op');
	var zTree = $.fn.zTree.getZTreeObj("group_tree");
	yNodes = JSON.stringify(yNodes);
	$.ajax({
		type:"post",
		url:CONTEXT_PATH+"/okcsys/video/save/g_info",
		data:"group="+yNodes,
		success:function(){
			//alert("分组保存成功");
		}
	});
	zTree.destroy();
}
//添加兄弟节点
$("#addsibl").bind("click", {type:'sibl'}, add);
//添加子节点
$("#addchil").bind("click", {type:'chil'}, add);
//节点编辑(重命名)
$("#edit").bind("click", edit);
//删除节点
$("#remove").bind("click", remove);
//展开
$("#expandNodes").bind("click",{type:"expandAll"}, expandNode);
//收缩
$("#collapseNodes").bind("click",{type:"collapseAll"}, expandNode);
//确定按钮
$("#sub_tree").bind("click",getAllnode);
//取消按钮
$("#tree_cel").on("click",function(){
	clos_group('cover_div','group_op');
	var zTree = $.fn.zTree.getZTreeObj("group_tree");
	zTree.destroy();
});
function show_lt_tree(){
	var setting0 = {
			view: {
				selectedMulti: false
			},
			edit: {
				enable: false,
			},
			data: {
				simpleData: {
					enable: true
				}
			}
		};
		var zNodes = new Array();
		$.ajax({
			url:CONTEXT_PATH+"/okcsys/video/get/g_info",
			success:function(data){
				if(data.length>0){
					$.fn.zTree.init($("#show_tree"), setting0, JSON.parse(data));//按配置初始化
				}
			},
			async:false,
			type:"post"
			});
}
//添加camera取消按钮事件
$("#add_cel").on("click",function(){
	clos_group('cover_div','add_cam');
	var zTree = $.fn.zTree.getZTreeObj("show_tree");
	zTree.destroy();
});
function show_sys(input_id){//系统树配置
	$("div#group_show").show();
	$("div.cover_div").css("z-index",3);
	var setting = {
			view: {
				selectedMulti: false//不支持多选
			},
			edit: {
				enable: false//不可编辑
			},
			data: {
				simpleData: {
					enable: true
				}
			}
		};
		var zNodes =[//树的数据（Id，父节点id，名称，是否展开（默认为false））
			{ id:1, pId:0, name:"子系统1",open:true},
			{ id:2, pId:1, name:"子系统1-1",open:true},
			{ id:3, pId:1, name:"子系统1-2",open:true},
			{ id:4, pId:3, name:"子系统3-1",open:true},
			{ id:5, pId:2, name:"子系统2-1",open:true}
		];
		$.fn.zTree.init($("#group_sys"), setting, zNodes);//按配置初始化
		$("#sub_sys").val(input_id);
}
function expandNode_sys(e){//展开/收缩方法
	var zTree = $.fn.zTree.getZTreeObj("group_sys"),
	type = e.data.type;
	if (type == "expandAll") {
		zTree.expandAll(true);
	} else if (type == "collapseAll") {
		zTree.expandAll(false);
	} else {
		for (var i=0, l=nodes.length; i<l; i++) {
			zTree.setting.view.fontCss = {};
			if (type == "expand") {
				zTree.expandNode(nodes[i], true, null, null);
			} else if (type == "collapse") {
				zTree.expandNode(nodes[i], false, null, null);
			} else if (type == "toggle") {
				zTree.expandNode(nodes[i], null, null, null);
			} else if (type == "expandSon") {
				zTree.expandNode(nodes[i], true, true, null);
			} else if (type == "collapseSon") {
				zTree.expandNode(nodes[i], false, true, null);
			}
		}
	}
}
//被保护选项展开按钮事件
$("#expandNodes_1").bind("click",{type:"expandAll"}, expandNode_sys);
//被保护选项收缩按钮事件
$("#collapseNodes_1").bind("click",{type:"collapseAll"}, expandNode_sys);
//被保护选项取消按钮事件
$("#sys_cel").on("click",function(){
	var zTree = $.fn.zTree.getZTreeObj("group_sys");
	$("div#group_show").hide();
	$("div.cover_div").css("z-index",1);
	zTree.destroy();
});
//被保护选项确定按钮事件
$("#sub_sys").on("click",function(){
	var zTree = $.fn.zTree.getZTreeObj("group_sys");
	var nodes = zTree.getSelectedNodes();
	$("#"+$(this).val()).val(nodes[0].id+":"+nodes[0].name);//得到值复制给input
	$("div#group_show").hide();
	$("div.cover_div").css("z-index",1);
	zTree.destroy();//回收树
});
//清空被保护的input
function clear_sys(input_id){
	$("#"+input_id).val("");
}


//上传驱动
$("#btnSubmitUpload").click(function(e){
	backgroundFileUpload();	
});

function backgroundFileUpload() {
	var file = $("#file").val();
	var driverName = $("#drivername").val();
	if(file==""){
		alert("未选择文件！");
		return false;
	}else{
		$.ajaxFileUpload({
			type:"post",
			url : CONTEXT_PATH +'/okcsys/video/upload/'+driverName,
			secureuri : false, 
			fileElementId : 'file',
			dataType : 'json',
			success : function(data, status) 
			{
				console.log(data);
				if (!data) {
					alert("文件类型错误，上传失败！");
				} else {
					alert("上传成功！");
				}
				$('#uploadModal').modal('hide');
			},
			error : function(data, status, e) // 相当于java中catch语句块的用法
			{
				alert("文件上传失败！");
			}
		});
	}
}

function subsysconfig(){
	$("#sysconfig").submit();
}
$("#matrixsub").on("click",function(){
	$("#matrixconfig").submit();
});

///////////////////////////////////////////
//dvr配置导入
$("#btnSubmitImport").click(function(){
	var type = $("#import_t").val();
	if(type<1){
		alert("请选择导入方式！！");
		return false;
	}
	var file = $("#file").val();
	if(file==""){
		alert("未选择文件！");
		return false;
	}else if(file.indexOf("\.xls")<0&&file.indexOf("\.xlsx")<0){
		alert("文件类型必须为.xls或.xlsx！");
		return false;
	}else{
		$.ajaxFileUpload({
			type:"post",
			url : CONTEXT_PATH +'/okcsys/import/dvrconfig/'+type,
			secureuri : false, 
			fileElementId : 'file',
			dataType : 'json',
			success : function(data, status) 
			{
				console.log(data);
				if (data==0) {
					alert("文件导入失败！");
				} else {
					alert("文件导入成功！，请刷新页面");
				}
				$('#importModal').modal('hide');
			},
			error : function(data, status, e) // 相当于java中catch语句块的用法
			{
				alert("文件导入失败！");
			}
		});
	}
});
//camera配置导入
$("#btnSubmit_c").click(function(){
	var type = $("#import_t").val();
	if(type<1){
		alert("请选择导入方式！！");
		return false;
	}
	var file = $("#file").val();
	if(file==""){
		alert("未选择文件！");
		return false;
	}else if(file.indexOf("\.xls")<0&&file.indexOf("\.xlsx")<0){
		alert("文件类型必须为.xls或.xlsx！");
		return false;
	}else{
		$.ajaxFileUpload({
			type:"post",
			url : CONTEXT_PATH +'/okcsys/import/camconfig/'+type,
			secureuri : false, 
			fileElementId : 'file',
			dataType : 'json',
			success : function(data, status) 
			{
				console.log(data);
				if (data==0) {
					alert("文件导入失败！");
				} else {
					alert("文件导入成功！，请刷新页面");
				}
				$('#importModal').modal('hide');
			},
			error : function(data, status, e) // 相当于java中catch语句块的用法
			{
				alert("文件导入失败！");
			}
		});
	}
});