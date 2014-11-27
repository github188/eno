
$(function() {
	
	findUsersList(); // 
	
});


/**
 * 用户管理用户信息查询
 */
function findUsersList(){
	var url = CONTEXT_PATH + "/user/findUserList";
	$.ajax({
		type: "GET",
		url: url,
		success: function (data) {
			try {
				if(data!=null){
					//加载列表数据
					$("#tbodyQz").html(findUserListHtml(data));
				}
			} catch (e) {
				
			}
		}
	});
}


/**
 * 用户管理用户新建确定
 */
$(".btn_red").on("click", function(e) {
	// 确定事件代码
	var url = CONTEXT_PATH + "/user/saveUser?"+$("form").serialize();
	$.ajax({
		type: "GET",
		url: url,
		success: function (data) {
			if(data!=null){
				//加载列表数据
				$("#tbodyQz").html(findUserListHtml(data));
			}
		}
	});
});

/**
 * 用户管理用户修改查询
 */
$(".tab_edit").on("click", function(e) {
	var url = CONTEXT_PATH + "/user/findUserId?"+$("userid").val();
	var userid=$("userid").val();
	console.log(userid);
	$.ajax({
		type: "GET",
		url: url,
		data: userid,
		success: function (data) {
			$("#username").val("");
		}
	});
});

/**
 * 用户管理用户删除
 */
$(".tab_delete").on("click", function(e) {
	var url = CONTEXT_PATH + "/user/findUserId?"+$("userid").val();
	var userid=$("userid").val();
	console.log(userid);
	$.ajax({
		type: "GET",
		url: url,
		success: function (data) {
			if(data!=null){
				//加载列表数据
				$("#tbodyQz").html(findUserListHtml(data));
			}
		}
	});

});

/**
 * @param obj 数据列表
 * return 返回用户信息列表html
 */
function findUserListHtml(obj){
	var html = " <tr><th class='edit'>&nbsp;</th><th class='delete'>&nbsp;</th><th>姓名</th><th>部门</th><th>性别</th><th>生日</th><th>入职日期</th><th>手机号码</th></tr>";
	if(obj!=null){
		for (var k = 0; k < obj.length; k++) {
			var userList = obj[k];
				html += '<tr>'
				+ '<td class="edit"><a href="#edit_user" data-toggle="modal" class="icon_small tab_edit"></a><hidden name="userid" id="userid" value="' + userList.userid+ '" /></td>'
				+ '<td class="delete"><a href="#" class="icon_small tab_delete"></a><hidden name="userid" id="userid" value="' + userList.userid+ '" /></td>'
				+ '<td>' + userList.firstname+ '</td>'
				+ '<td>' + userList.department + '</td>'
				+ '<td>' + userList.sex + '</td>' 
				+ '<td>' + userList.birthday + '</td>' 
				+ '<td>' + userList.workdate + '</td>' 
				+ '<td>' + userList.phone + '</td></tr>';
		}
	}
	return html;
}
