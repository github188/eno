
$(function() {
	
	/**
	 * 用户管理用户新建确定
	 */
	$("#saveUsers").on("click", function() {
		saveUsers();
	});
	
	/**
	 * 用户管理用户密码修改
	 */
	$("#updatePassword").on("click", function() {
		updateUsersPassword();
	});
	
	/**
	 * 用户管理用户信息查询
	 */
	findUsersList(); 
	
	$("#finddepartment").change(function() {
		var finddepartment = $(this).val();
		if("1"==finddepartment){
			finddepartment="产品部";
			findDepartment(finddepartment);
		}else if("2"==finddepartment){
			finddepartment="宣传部";
			findDepartment(finddepartment);
		}else if("3"==finddepartment){
			finddepartment="人事";
			findDepartment(finddepartment);
		}else if("4"==finddepartment){
			finddepartment="测试部";
			findDepartment(finddepartment);
		}else if("0"==finddepartment){
			findUsersList(); 
		}
	});
});

/**
 * 用户管理用户部门查询
 */
function findDepartment(finddepartment){
	var url = CONTEXT_PATH + "/okcsys/finddepartmentList?department="+finddepartment;
	console.log(url);
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
				console.log(e);
			}
		}
	});
}

/**
 * 用户管理用户修改查询
 */
function updateUsers(userid){
	var url = CONTEXT_PATH + "/okcsys/findUserid?userid="+userid;
	console.log(url);
	$.ajax({
		type: "GET",
		url: url,
		success: function (data) {
			try {
				if(data!=null){
					console.log(data);
					$("#useruid").val(data.userid);
					$("#firstname").val(data.firstname);
					$("#loginid").val(data.loginid);
					$("#password").val(data.password);
					$("#birthday").val(getSmpFormatDateByLong(data.birthday,false));
					$("#workdate").val(getSmpFormatDateByLong(data.workdate,false));
					$("#phone").val(data.phone);
					$("#department").val(data.department);
					
				}
			} catch (e) {
				console.log(e);
			}
			
		}
	});
}

/**
 * 用户管理用户新建
 */
function updateUsersPassword(){
	// 确定事件代码
	var url = CONTEXT_PATH + "/okcsys/updateUsersPassword?"+$("#updatepw").serialize();
	console.log(url);
	$.ajax({
		type: "GET",
		url: url,
		success: function (data) {
			try {
				if(data!=null){
					$("#changePwd").modal('hide');
				}
			} catch (e) {
				console.log(e);
			}
			
		}
	});
}

/**
 * 用户管理用户新建
 */
function saveUsers(){
	var sex;
	if($("#sex-male").attr("checked")=="checked"){
		sex="man";
	}
	if($("#sex-female").attr("checked")=="checked"){
		sex="woman";
	}
	// 确定事件代码
	var url = CONTEXT_PATH + "/okcsys/saveUser?sex="+sex+"&"+$("#userform").serialize();
	console.log(url);
	$.ajax({
		type: "GET",
		url: url,
		success: function (data) {
			try {
				if(data!=null){
					$("#new_user").modal('hide');
					findUsersList();
				}
			} catch (e) {
				console.log(e);
			}
			
		}
	});
}

/**
 * 用户管理用户信息查询
 */
function findUsersList(){
	var url = CONTEXT_PATH + "/okcsys/findUserList";
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
				console.log(e);
			}
		}
	});
}


/**
 * 用户管理用户删除
 */
function delUsers(userid){
	console.log(userid);
	var url = CONTEXT_PATH + "/okcsys/delUsers?userid="+userid;
	console.log(url);
	$.ajax({
		type: "GET",
		url: url,
		success: function (data) {
			try {
				if(data!=null){
					console.log(data);
					findUsersList();
				}
			} catch (e) {
				console.log(e);
			}
			
		}
	});
}

/**
 * @param obj 数据列表
 * return 返回用户信息列表html
 */
function findUserListHtml(obj){
	var html = " <tr><th class='edit'>&nbsp;</th><th class='delete'>&nbsp;</th><th>姓名</th><th>部门</th><th>性别</th><th>生日</th><th>入职日期</th><th>手机号码</th></tr>";
	var sex;
	if(obj!=null){
		for (var k = 0; k < obj.length; k++) {
			var userList = obj[k];
			if(userList.sex == "woman"){
				sex="女";
			}else{
				sex="男";
			}
			html += '<tr>'
			+ '<td class="edit"><a href="#new_user" data-toggle="modal" class="icon_small tab_edit" onclick="updateUsers(\''+userList.userid+'\');"></a></td>'
			+ '<td class="delete"><a href="#" class="icon_small tab_delete" onclick="delUsers(\''+userList.userid+'\');"></a></td>'
			+ '<td>' + userList.firstname+ '</td>'
			+ '<td>' + userList.department + '</td>'
			+ '<td>' + sex + '</td>' 
			+ '<td> '+ getSmpFormatDateByLong(userList.birthday,false)+'</td>' 
			+ '<td>' + getSmpFormatDateByLong(userList.workdate,false) + '</td>' 
			+ '<td>' + userList.phone + '</td></tr>';
		}
	}
	return html;
}
