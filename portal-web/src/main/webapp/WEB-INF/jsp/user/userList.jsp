<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bootstrap-select.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ui/jquery.ui.all.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/jquery.mCustomScrollbar.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/main.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/user.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/bootstrap-select.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/jquery.mCustomScrollbar.concat.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/main.js"></script>	
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/user.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript">
var userSta = ${userStaIndex}
$(function() {
	var endIndexInt = ${endIndex};
	if(endIndexInt>0){
	$(".bc").text(1);
	}
	else{
	$(".bc").text(0);	
	}
	qz.value = 1;
	<%
	java.util.Date d = new java.util.Date(); 
	java.text.SimpleDateFormat dformat = new java.text.SimpleDateFormat("yyyy-MM-dd"); 
	String datetime = "\""+dformat.format(d)+"\"";
	%>
	$("#birthdate").val(<%=datetime%>);
	$("#hiredate").val(<%=datetime%>);
	$( "#dialog-modal" ).dialog({
		width : "720px",
		autoOpen: false,
		show: "blind",
		hide: "explode",
		modal: true
	});

	$( "#new_user" ).click(function() {
        $(".mask").show();
        $(".dialog-createuser").show();
        $("#personid").val("");
		$("#firstname").val("");
		$("#lastname").val("");
		$("#workphone").val("");
		$("#sex").val("0");
		$("#employeetype").val("0");
		<%
		java.util.Date d1 = new java.util.Date(); 
		java.text.SimpleDateFormat dformat1 = new java.text.SimpleDateFormat("yyyy-MM-dd"); 
		String datetime1 = "\""+dformat1.format(d)+"\"";
		%>
		$("#birthdate").val(<%=datetime1%>);
		$("#hiredate").val(<%=datetime1%>);
		$("#department").val("");
		$("#jobcode").val("");
		$("#workemail").val("");
		$("#type").val("0");
		$("#loginid").val("");
		$("#pwd").val("");
		$("#pwdqr").val("");
		$("#roleSelect").val("NORMAL");
		$("#bgzt").val("");
		$("#statusx").val("0");
		tbodyJs.style.display="none";
		$("#pd").val("insert");
		return false;
 	});
	
	$("#update_user" ).click(function() {
		var obj = document.getElementsByName("cbox"); 
		 var personid = "";
		for(var i=0;i<obj.length;i++){
	       if(obj[i].checked){
	       	personid = 	obj[i].value;//这里得到复选框选中项的值
	       }
	    }
		if(personid == ""){
		}
		else{
		$("#pd").val("update");
		$(".mask").show();
        $(".dialog-createuser").show();
		}
		return false;
	});
	$("#change_user").click(function(){
		var obj = document.getElementsByName("cbox"); 
		 var personid = "";
		for(var i=0;i<obj.length;i++){
	       if(obj[i].checked){
	       	personid = 	obj[i].value;//这里得到复选框选中项的值
	       }
	    }
		if(personid == ""){
		}
		else{
            var url = "${pageContext.request.contextPath}/okcsys/user/changeUser?Personsid="+personid;
            $.ajax({
       		 type:"GET",
       		 url:url ,
       		 cache:false,
       		 success:function(data){
       			 var sta = data[0].stat;
				 if(sta=="false"){
					 alert("该用户未分配账户");
				 }
				 else{
					 $(".mask").show();
			         $(".dialog-changerole").show();
				 }
       		 }
       	  });
		}
		return false;
	});
	$("#view_user").click(function(){
		var obj = document.getElementsByName("cbox"); 
		 var personid = "";
		for(var i=0;i<obj.length;i++){
	       if(obj[i].checked){
	       	personid = 	obj[i].value;//这里得到复选框选中项的值
	       }
	    }
		if(personid == ""){
		}
		else{
            $(".mask").show();
            $(".dialog-scanrole").show();
		}
		return false;
	});
	$(".close_pop").click(function(){
		window.location.href = "${pageContext.request.contextPath}/okcsys/user";
	});

    $("#left_user").mCustomScrollbar({
        autoHideScrollbar:true,
        theme:"light-thin"
    });

    $("#right_user").mCustomScrollbar({
        autoHideScrollbar:true,
        theme:"light-thin"
    })
    
    $(".close_popZx").click(function(){
    	$(".mask").hide();
    	$(".dialog-confirm").hide();
	});
    
    $(".close_popBj").click(function(){
    	$(".mask").hide();
    	$(".dialog-createuser").hide();
	});
    
    $(".close_popXg").click(function(){
    	$(".mask").hide();
    	$(".dialog-changerole").hide();
	});
    
    $(".close_popView").click(function(){
    	$(".mask").hide();
    	$(".dialog-scanrole").hide();
	});
});

var aft = ${endIndex};
function selectFy(para){
	var pagef = "";
	if(para == "Before"){
		if(qz.value>1){
			qz.value = qz.value-1;
			}
		pagef = qz.value;
	}
	if(para == "After"){
		if(qz.value<aft){
			qz.value = qz.value+1;
	}
		pagef = qz.value;
	}
	if(para == "End"){
		qz.value = ${endIndex};
		pagef = ${endIndex};
	}
	if(para == "First"){
		qz.value = 1;
		pagef = 1;
	}
	if(aft=="0"){
		pagef = 0;
	}
	$(".bc").text(pagef);
	var url = "${pageContext.request.contextPath}/okcsys/user/selectUsersListView?userpagef="+pagef+"&sta="+userSta;
	$.ajax({
		 type:"GET",
		 url:url ,
		 cache:false,
		 success:function(data){
		 var dataList = data;
		 var html ='';
		 $("#content").html("");
		 for(var k=0;k<dataList.length;k++){
			 var person = dataList[k];
			 var htmlTr = '';
			 htmlTr = '<tr><td align="center"><input type="checkbox" name="cbox" value="'+person.personid+'"onClick="chooseOne(this);" style="zoom: 170%"></td>'
			         +'<td align="center">'+person.firstname+person.lastname+'</td>'
			         +'<td align="center">'+person.department+'</td>'
			         +'<td align="center">'+person.sex.split("&")[0]+'</td>'
			         +'<td align="center">'+DateChange(person.birthdate)+'</td>'
			         +'<td align="center">'+DateChange(person.hiredate)+'</td>'
			         +'<td align="center">'+person.employeetype+'</td>'
			         +'<td align="center">'+person.jobcode+'</td>'
			         +'<td align="center">'+person.workemail+'</td>'
			         +'<td align="center">'+person.workphone+'</td></tr>';
			 html =  html+htmlTr;      
		 }
		 $("#content").append(html); 
		}
	});
}

 function DateChange(StrDate){
	 var D = new Date(StrDate);
	 var y = D.getFullYear();
	 var m = D.getMonth() + 1;
	 if(m<10){
		m = '0'+m;
	 }
	 var d = D.getDate();
	 if(d<10){
		d = '0'+d;
	 }
	 var result = y+'-'+m+'-'+d;
	 return result
 }
	
function showJs() {
	var typeValue = $("#type").val();
	if(typeValue=="0"){
		tbodyJs.style.display="none";
	}
	if(typeValue=="1"){
		tbodyJs.style.display="inline";
	}
}
  function selectBystatusx(sta){
	var url = "${pageContext.request.contextPath}/okcsys/user/selectBystatusx?sta="+sta;
 	window.location.href = url;
  } 

function chooseOne(cb){    
    var obj = document.getElementsByName("cbox");      
    for (var i=0; i<obj.length; i++){  
        if (obj[i]!=cb) obj[i].checked = false;  
        else  obj[i].checked = cb.checked;  
    }      
} 

function changeTimeFormat(time) {
	var date = new Date(time);
	var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
	var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
	return date.getFullYear() + "-" + month + "-" + currentDate;
}

function personUpdate() {
	 var obj = document.getElementsByName("cbox"); 
	 var personid = "";
	for(var i=0;i<obj.length;i++){
        if(obj[i].checked){
        	personid = 	obj[i].value;//这里得到复选框选中项的值
        }
    }
	if(personid==""){
		alert("请选择用户");
		return ;
	}
	else{
	var url = "${pageContext.request.contextPath}/okcsys/user/personUpdate?Personsid="+personid;
	$.ajax({
		 type:"GET",
		 url:url ,
		 cache:false,
		 success:function(data){
		$("#personid").val(personid);		 
	    $("#firstname").val(data[0].firstname);
	    $("#lastname").val(data[0].lastname);
	    $("#workphone").val(data[0].workphone);
	    $("#sex").val(data[0].sex);
	    $("#employeetype").val(data[0].employeetype);
	    $("#hiredate").val(changeTimeFormat(data[0].hiredate));
	    $("#birthdate").val(changeTimeFormat(data[0].birthdate));
	    $("#department").val(data[0].department);
	    $("#jobcode").val(data[0].jobcode);
	    $("#workemail").val(data[0].workemail);
	    $("#type").val(data[1].type);
	    if(data[1].type == "0"){
		    $("#bgzt").val("");
		}
	    else{
	    $("#bgzt").val(data[1].status);
	    }
 		if(data[1].type=="1"){
 			 tbodyJs.style.display="inline";
 			 $("#loginid").val(data[1].loginid);
 			 $("#statusx").val(data[1].status);
 			 $("#pwd").val(data[1].pwd);
 			 $("#pwdqr").val(data[1].pwd);
 			 $("#roleSelect").val(data[1].clientip);
 		}
 		else{
			tbodyJs.style.display="none";
			$("#loginid").val("");
			$("#statusx").val("0");
			$("#pwd").val("");
			$("#pwdqr").val("");
 		}
	}
	});
	}
}

var dopersonDeleteId = "";
function personDelete() {
	var obj = document.getElementsByName("cbox"); 
	 var personid = "";
	for(var i=0;i<obj.length;i++){
       if(obj[i].checked){
       	personid = 	obj[i].value;//这里得到复选框选中项的值
       }
    }
	if(personid==""){
		alert("请选择用户");
		return;
	}
	else{
		 $(".mask").show();
         $(".dialog-confirm").show();
         dopersonDeleteId = personid;
//          event.stopPropagation();
	}
}

function deleteDoPerson() {
	var url = "${pageContext.request.contextPath}/okcsys/user/personDelete?Personsid="+dopersonDeleteId+"&sta="+userSta;
	window.location.href = url;
}

function deleteDoPersonsureCancel() {
	$(".mask").hide();
	$(".dialog-confirm").hide();
}

function locationCancelRole() {
	$(".mask").hide();
	$(".dialog-changerole").hide();
}

function locationCancelView() {
	$(".mask").hide();
	$(".dialog-scanrole").hide();
}

var stm="";
function changeUser() {
	 var obj = document.getElementsByName("cbox"); 
	 var personid = "";
	for(var i=0;i<obj.length;i++){
       if(obj[i].checked){
       	personid = 	obj[i].value;//这里得到复选框选中项的值
       	ycpersonid.value = personid;
       }
   }
	if(personid == ""){
		alert("请选择用户");
	}
	else{
	var url = "${pageContext.request.contextPath}/okcsys/user/changeUser?Personsid="+personid;
	$.ajax({
		 type:"GET",
		 url:url ,
		 cache:false,
		 success:function(data){
		 var sta = data[0].stat;
		 if(sta=="false"){
		 }
		    var userGroupsList = data[0].userGroupsTotal;
			$("#leftUser").html("");
			var html = '<ul id="leftUserul">';
			for(var i=0;i<userGroupsList.length;i++){
				var groupname = userGroupsList[i].groupname;
					html = 	html+'<li>'+groupname+'</li>';
			}
			$("#leftUser").append(html+'</ul>');
			var userGroupsUserList = data[0].userGroupsUser;
			$("#rightUser").html("");
			var html1 = '<ul id="rightUserul">';
			var st = '';
				for(var i=0;i<userGroupsUserList.length;i++){
					var groupname = userGroupsUserList[i].groupname;
					html1 = html1+'<li>'+groupname+'</li>';
					st = st + groupname+";";
				}
				stm = st;
			$("#rightUser").append(html1+'</ul>');
		 }
	});
	}
}

function viewUser() {
	 var obj = document.getElementsByName("cbox"); 
	 var personid = "";
	for(var i=0;i<obj.length;i++){
      if(obj[i].checked){
      	personid = 	obj[i].value;//这里得到复选框选中项的值
      	ycpersonid.value = personid;
      }
   }
	if(personid == ""){
		alert("请选择用户");
	}
	else{
	/**	
	var url = "${pageContext.request.contextPath}/okcsys/user/changeUser?personid="+personid;
	$.ajax({
		 type:"GET",
		 url:url ,
		 cache:false,
		 success:function(data){
		 var sta = data[0].stat;
		 if(sta=="false"){
		 }
			var userGroupsUserList = data[0].userGroupsUser;
			$("#chl").html("");
			var html1 = '';
				for(var i=0;i<userGroupsUserList.length;i++){
					var groupname = userGroupsUserList[i].groupname;
					html1 = html1+'<div style="text-align: center;">'+groupname+'</div>';
			 }
			$("#chl").append(html1+'');
		 }
	});
	*/
	var url = "${pageContext.request.contextPath}/okcsys/user/showAuthority?Personsid="+personid;
	$.ajax({
		 type:"GET",
		 url:url ,
		 cache:false,
		 success:function(data){
		 var OkcMenus = data;
		 var html ="";
		 $("#contentAuth").html("");
		 for(var k=0;k<OkcMenus.length;k++){
			 var okcMenu = OkcMenus[k];
			 html = html+"<tr><td>菜单</td><td>"+okcMenu.headerdescription+"</td></tr>";
		 }
		 $("#contentAuth").append(html);
		 }
	});
	}
 }

function saveGroup(){
	var arr = [];
	var len = $("#rightUser li").length;
	if(len>1){
		alert("只能选一个岗位");
	}
	else{
	for(var i = 0; i < len; ++i){
		arr.push($("#rightUser li:eq("+ i +")").text());
	}
	var groupnames="";
	for(var i = 0; i < len; ++i){
		groupnames = groupnames+arr[i]+";";
		}
		var url = "${pageContext.request.contextPath}/okcsys/user/insertgroupMember?Personsid="+ycpersonid.value+"&groupnames="+encodeURIComponent(groupnames);
		$.ajax({                             
			 type:"GET",
			 url:url ,
			 cache:false,
			 success:function(data){
				 location.reload();
			 }
	    });
	}	
}

function locationCancelCj(){
//   window.location.href = "${pageContext.request.contextPath}/okcsys/user";
	$(".mask").hide();
	$(".dialog-createuser").hide();
}

	function validate() {
		if ($("#firstname").val() == "") {
			alert("姓不能为空.");
			$("#firstname").focus();
			return false;
		}
		if ($("#lastname").val() == "") {
			alert("名不能为空.");
			$("#lastname").focus();
			return false;
		}
		if ($("#department").val() == "") {
			alert("部门不能为空.");
			$("#department").focus();
			return false;
		}
		if ($("#jobcode").val() == "") {
			alert("职位不能为空.");
			$("#jobcode").focus();
			return false;
		}
		if ($("#hiredate").val() == "") {
			alert("入职日期不能为空.");
			$("#hiredate").focus();
			return false;
		}
		if ($("#birthdate").val() == "") {
			alert("生日不能为空.");
			$("#birthdate").focus();
			return false;
		}
		if ($("#type").val() == "1") {
		if ($("#loginid").val() == "") {
				alert("帐号不能为空.");
				$("#loginid").focus();
				return false;
		}
		else{
			var url = "${pageContext.request.contextPath}/okcsys/user/checkLoginid?loginid="+encodeURIComponent($("#loginid").val())+"&Personsid="+$("#personid").val();
			var bol = true;
			$.ajax({
				type : "GET",
				url : url,
				cache : false,
				async : false,
				success : function(data) {
					if (data == "sb"){
						alert("账户名称重复");
					    bol = false;
					}
				}
			});
			if(bol ==  false)
			return bol;
		}
		if ($("#pwd").val() == "") {
			alert("密码不能为空.");
			$("#pwd").focus();
			return false;
		}
		if ($("#pwd").val() != $("#pwdqr").val()) {
			alert("您两次输入的密码不一样！请重新输入.");
			$("#pwdqr").focus();
			return false;
		}
	  }
		return true;
	}
</script>
<div id="qz" style="display: none;"></div>
<div class="span12 main row-fluid">
	<div class="span12 content">
			<div class="btn_group1">
				<div class="sm_btn state_search">
					<ul style="width: 90px;position: absolute">
						<li class="sm_btn disshow">状态 &nbsp;&nbsp;▼</li>
						<li onclick='selectBystatusx(3)'>全部</li>
						<li onclick='selectBystatusx(0)'>ACTIVE</li>
						<li onclick='selectBystatusx(1)'>INACTIVE</li>
					</ul>
				</div>
                <div class="sm_btn" id="new_user">创建用户</div>
                <div class="sm_btn" id="delete_user" onclick="personDelete();">注销用户</div>
			</div>
			<div class="btn_group2" style="margin-right: 125px;" >
			    <div class="sm_btn btn_style2" id="update_user" onclick="personUpdate();">修改用户</div>
				<div class="sm_btn btn_style2" id="change_user" onclick="changeUser();">修改角色</div>
				<div class="sm_btn btn_style2" id="view_user" onclick="viewUser();">查看权限</div>
			</div>
		
		<div class="span12 user_info">
			<div class="table_width">
				<table class="table user_table" >
                    <thead>
                        <tr class="table_head">
                            <th>选择</th>
                            <th>姓名</th>
                            <th>部门</th>
                            <th>性别</th>
                            <th>生日</th>
                            <th>入职日期</th>
                            <th>职员类别</th>
                            <th>职位</th>
                            <th>电子邮箱</th>
                            <th>联系电话</th>
                            <!-- 						<th>编辑</th> -->
                        </tr>
                    </thead>
					<tbody id="content">
						<c:forEach items="${Persons}" var="person">
						<tr>
						    <td align="center"><input type="checkbox" name="cbox" value="${person.personid }" onClick="chooseOne(this);" style="zoom: 170%"></td>
							<td align="center">${person.firstname}${person.lastname}</td>
							<td align="center">${person.department}</td>
							<td align="center">${fn:split(person.sex,"&")[0]}</td>
							<td align="center">${fn:split(person.birthdate," ")[0]}</td>
							<td align="center">${fn:split(person.hiredate," ")[0]}</td>
							<td align="center">${person.employeetype}</td>
							<td align="center">${person.jobcode}</td>
							<td align="center">${person.workemail}</td>
							<td align="center">${person.workphone}</td>
						</tr>
					</c:forEach>
					</tbody>
				</table>
				<div class="paging">
				<img src="${pageContext.request.contextPath}/resources/images/left_first.png" onclick="selectFy('First');"/> <img
					src="${pageContext.request.contextPath}/resources/images/left.png" onclick="selectFy('Before');"/>
					 <span class="page_des1">Page</span>
				     <span class="bc"></span><span class="page_des2">of
					 ${endIndex}</span> <img src="${pageContext.request.contextPath}/resources/images/right.png" onclick="selectFy('After');"/> <img
					src="${pageContext.request.contextPath}/resources/images/right_end.png" onclick="selectFy('End');"/>
			   </div>
			</div>
		</div>
	</div>
</div>
<div class="mask">
    <div class="dialog-popover dialog-createuser">
        <div class="dialog-header">
            <p class="dialog-title">编辑用户</p>
            <div class="dialog-close close_popBj">×</div>
        </div>
        <div class="dialog-content">
            <form action="${pageContext.request.contextPath}/okcsys/user/doInsertPerson" method="get" onsubmit="return validate();"> 
                <table class="register" style="margin-left: 51px;">
                    <tbody>
                    <tr>
                        <td>姓：</td>
                        <td>
                            <input name="personid" id="personid" style="display: none;">
                            <input name="firstname" id="firstname">
                        </td>
                        <td>名：</td>
                        <td><input name="lastname" id="lastname"></td>
                    </tr>
                    <tr>
                        <td>联系电话：</td>
                        <td><input name="workphone" id="workphone"></td>
                        <td>性别：</td>
                        <td>
                            <select id="sex" name="sex"  style="width: 122px; height: 33px">
                                <option value="0">男</option>
                                <option value="1">女</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>职员类别：</td>
                        <td>
                            <select id="employeetype" name="employeetype"  style="width: 122px; height: 33px">
                                <option value="0">全职员工</option>
                                <option value="1">兼职合同工</option>
                                <option value="2">临时合同工</option>
                                <option value="3">外包人员</option>
                            </select>
                        </td>
                        <td>入职日期：</td>
                        <td><input name="hiredate" id="hiredate" type="text" onClick="WdatePicker()"></td>
                    </tr>
                    <tr>
                        <td>生日：</td>
                        <td><input name="birthdate" id="birthdate" type="text" onClick="WdatePicker()"></td>
                        <td>部门：</td>
                        <td><input name="department" id="department"></td>
                    </tr>
                    <tr>
                        <td>职位：</td>
                        <td><input name="jobcode" id="jobcode"></td>
                        <td>电子邮箱：</td>
                        <td><input name="workemail" id="workemail"></td>
                    </tr>
                    <tr>
                        <td>用户类型：</td>
                        <td>
                            <select id="type" name="type"  style="width: 122px; height: 33px" onchange="showJs()">
                                <option value="0">人员</option>
                                <option value="1">用户</option>
                            </select>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <hr>
                <div style="margin-left: 51px;">
                    <table class="register" id = "tbodyJs"   style="display: none;">
                        <tbody>
                        <tr>
                            <td>帐号：</td>
                            <td ><input  style="margin-left: 7px" name="loginid" id="loginid"></td>
                            <td >&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp状态：</td>
                            <td >
                                <input   name="bgzt" id="bgzt" style="display: none;">
                                <select id="statusx" name="statusx"  style="width: 122px; height: 33px">
                                    <option value="0">ACTIVE</option>
                                    <option value="1">INACTIVE</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td >密码：</td>
                            <td ><input style="margin-left: 7px"  name="pwd" id="pwd" type="password"></td>
                            <td>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp确认密码：</td>
                            <td ><input style="" name="pwdqr" id="pwdqr" type="password"></td>
                        </tr>
                        <tr>
                            <td >用户选择：</td>
                            <td >
                            <select id="roleSelect" name="roleSelect"  style="width: 122px; height: 33px;margin-left: 7px">
					           <c:forEach items="${userclasses}" var="userclass">
						       <option value="${userclass.dmvalue }">${userclass.description}</option>
					           </c:forEach>
				            </select>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="alert alert-error" id="sxr" style="display: none;">
                    <button type="button" class="close" data-dismiss="alert">&times;</button>
                    <strong>Warning!</strong> 您两次输入的密码不一样！请重新输入.
                </div>
                <hr>
                  <div class="dialog-footer">
            <div class="modal_btn_2" >
                <input id="pd" style="display: none;">
                <input  class="sure dialog-btn"    name="Submit" type="submit"  value="保存" class="btn btn-primary">
                <input  class="cancel dialog-btn"  name="Submit1" type="button"  onclick="locationCancelCj();" value="关闭" class="btn btn-primary">
            </div>
        </div>
            </form>
        </div>
    </div>
    <div class="dialog-popover dialog-changerole">
        <div class="dialog-header">
            <p class="dialog-title">修改角色</p>
            <div class="dialog-close close_popXg">×</div>
        </div>
        <div class="dialog-content">
            <input id="ycpersonid" style="display: none;">
            <div class="dialog_content3">
                <div class="left_user" id="leftUser">
<!--                     <ul id="leftUserul"> -->
<!--                     </ul> -->
                </div>
                <div class="operator">
                    <div class="move_left"><i></i></div>
                    <div class="move_right"><i></i></div>
                </div>
                <div class="right_user" id="rightUser">
<!--                     <ul id="rightUserul"> -->
<!--                     </ul> -->
                </div>
            </div>
        </div>
        <div class="dialog-footer">
            <div class="modal_btn_2" >
                <input id="pd" style="display: none;">
                <input  class="sure dialog-btn"    name="Submit" type="submit"  onclick="saveGroup();"  value="保存" class="btn btn-primary">
                <input  class="cancel dialog-btn"  name="Submit1" type="button" onclick="locationCancelRole();"  value="关闭" class="btn btn-primary">
            </div>
        </div>
    </div>
    <div class="dialog-popover dialog-scanrole">
        <div class="dialog-header">
            <p class="dialog-title">查看权限</p>
            <div class="dialog-close close_popView">×</div>
        </div>
        <div class="dialog-content" id="chl">
          <table class="table user_table" >
                    <thead>
                        <tr class="table_head">
                            <th>类型</th>
                            <th>名称</th>
                        </tr>
                    </thead>
					<tbody id="contentAuth">
					</tbody>
			</table>
        </div>
        <div class="dialog-footer">
            <div class="modal_btn_2" >
                <input id="pd" style="display: none;">
                <input  class="cancel dialog-btn"  name="Submit1" type="button"  onclick="locationCancelView();" value="关闭" class="btn btn-primary">
            </div>
        </div>
    </div>
    
    <div class="dialog-popover dialog-confirm">
        <div class="dialog-header">
            <p class="dialog-title">注销用户</p>
            <div class="dialog-close close_popZx">×</div>
        </div>
        <div class="dialog-content">
            <div class="confirm-dialog">
                您确定要注销此用户吗？
            </div>
        </div>
        <div class="dialog-footer">
            <div>
                <input  class="sure dialog-btn"  onclick="deleteDoPerson();"  name="Submit" type="submit"  value="确定" class="btn btn-primary">
                <input  class="cancel dialog-btn" onclick="deleteDoPersonsureCancel();"  name="Submit1" type="button"   value="取消" class="btn btn-primary">
            </div>
        </div>
    </div>
</div>