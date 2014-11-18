<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!DOCTYPE html>
<html lang="en">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/skin.css">	
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/bootstrap-select.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ui/jquery.ui.all.css">	
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/main.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/user.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/bootstrap-select.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/main.js"></script>	
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/user.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/jquery-migrate-1.2.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/jquery.jcarousel.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/jquery.slides.js"></script>

<div class="span12 content_title" style="margin-left: 30px">
		<h3 style="margin-left: 20px">角色选择</h3>
		<div class="division_line"></div>
	</div>
	<div class="span12 slide_module">
		<div class="span12 total_job">
			<div id="wrap">
				<div id="mycarousel" class="jcarousel-skin-tango">
					<ul id="totaljob">
					</ul>
					<img id="rei"
						class="jcarousel-prev jcarousel-prev-vertical jcarousel-prev-disabled jcarousel-prev-disabled-vertical"
						src="${pageContext.request.contextPath}/resources/images/topBtn.png" />
					<img class="jcarousel-next"
						src="${pageContext.request.contextPath}/resources/images/bottomBtn.png" />
				</div>
			</div>
		</div>
	</div>
	<div class="span12 content_title" id="contenttitle"></div>
	<div class="span12 nomargin1" style="margin-left: 45px">
		<div class="permission_detail" id="permissionDetail"></div>
	</div>


<!-- <div class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable" -->
<!-- 	tabindex="-1" role="dialog" aria-labelledby="ui-id-1" -->
<!-- 	style="display: none; outline: 0px; z-index: 1000;"> -->
<!-- 	<div -->
<!-- 		class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix"> -->
<!-- 		<span id="ui-id-1" class="ui-dialog-title">冻结用户</span><a href="#" -->
<!-- 			class="ui-dialog-titlebar-close ui-corner-all" role="button"><span -->
<!-- 			class="ui-icon ui-icon-closethick">close</span></a> -->
<!-- 	</div> -->
<!-- 	<div id="dialog-modal" -->
<!-- 		class="popover2 ui-dialog-content ui-widget-content" > -->
<!-- 		<div class="popnav" id="popnavApp"></div> -->
<!-- 		<div class="right_content" > -->
<!-- <!-- 			<div class="top_nav" id="moduleChoice"> --> 
<!--            <div class="top_nav"> -->
<!-- 			  <div id="slides"></div> -->
<!-- 			</div> -->
<!-- 			<div class="select_table" style="height: auto;"> -->
<!-- 				<table  id="GridView1"  > -->
<!-- 					<tbody id="selectTable"> -->
<!-- 						<tr class="first_tr"> -->
<!-- 							<th class="device_title" style="width: 200px;">应用名称</th> -->
<!-- <!-- 							<th class="page_title" style="width: 150px;">页面名称</th> --> 
<!-- 							<th class="operation">功能操作</th> -->
<!-- 						</tr> -->
<!-- 					</tbody> -->
<!-- 				</table> -->
<!-- 			</div> -->
<!-- 			<div class="btn_include"> -->
<!-- 				<div class="modal_btn modal_btn2"> -->
<!-- 					<input id="groupId" name="groupId" type="text" -->
<!-- 						style="width: 200px; display: none"> -->
<!-- 					<div class="sure"  onclick="sureSave();">确定</div> -->
<!-- 					<div class="cancel" onclick="sureCancel();">取消</div> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
<!-- </div> -->

<%--<div--%>
	<%--class="ui-dialog ui-widget ui-widget-content ui-corner-all ui-draggable"--%>
	<%--tabindex="-1" role="dialog" aria-labelledby="ui-id-2"--%>
	<%--style="display: none; outline: 0px; z-index: 1000;">--%>
	<%--<div--%>
		<%--class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix">--%>
		<%--<span id="ui-id-2" class="ui-dialog-title">删除岗位</span><a href="#"--%>
			<%--class="ui-dialog-titlebar-close ui-corner-all" role="button"><span--%>
			<%--class="ui-icon ui-icon-closethick">close</span></a>--%>
	<%--</div>--%>
	<%--<div id="dialog-modal1" class="ui-dialog-content ui-widget-content">--%>
		<%--<div class="dialog_content1">--%>
			<%--<p>您确定要删除此岗位吗？</p>--%>
		<%--</div>--%>
		<%--<hr>--%>
		<%--<div class="modal_btn">--%>
			<%--<div class="sure" onclick="deleteUserGroupBy();">确定</div>--%>
			<%--<div class="cancel">取消</div>--%>
		<%--</div>--%>
	<%--</div>--%>
<%--</div>--%>
<div class="mask">
    <div class="dialog-popover dialog-createjob">
        <div class="dialog-header">
            <p class="dialog-title">创建岗位</p>
            <div class="dialog-close close_popX">×</div>
        </div>
        <div class="dialog-content">
            <div class="dialog_content2">
                <div>
      <input id="ugroupId" name="ugroupId" type="text" style="display: none;">
                    岗位名称： <input id="jobGw" name="jobGw" type="text"
                                 style="width: 200px">
                </div>
                <div>
                    用户选择：
                    <select id="roleSelect" name="roleSelect" class="span1"
					style="width: 215px; height: 30px">
					<c:forEach items="${userclasses}" var="userclass">
						<option value="${userclass.dmvalue }">${userclass.description}</option>
					</c:forEach>
				   </select>
                </div>
                <div style="margin-top: 10px;">
                    附加说明：
                    <textarea id="groupcomment" name="groupcomment" type="text"
                              style="width: 200px; height: 100px"></textarea>
                </div>
                <div class="permission_select" id="permissionSelect"></div>
            </div>
        </div>
        <div class="dialog-footer">
            <div class="modal_btn_2" >
                <input id="pd" style="display: none;">
                <input  class="sure dialog-btn"  onclick="save();"  name="Submit" type="submit"  value="保存" class="btn btn-primary">
                <input  class="cancel dialog-btn" onclick="sureCancelX();"  name="Submit1" type="button"   value="关闭" class="btn btn-primary">
            </div>
        </div>
    </div>
    <div class="dialog-popover dialog-confirm">
        <div class="dialog-header">
            <p class="dialog-title">删除岗位</p>
            <div class="dialog-close close_popY">×</div>
        </div>
        <div class="dialog-content">
            <div class="confirm-dialog">
                您确定要删除此岗位吗？
            </div>
        </div>
        <div class="dialog-footer">
            <div>
                <input  class="sure dialog-btn"  onclick="deleteUserGroupBy();"  name="Submit" type="submit"  value="确定" class="btn btn-primary">
                <input  class="cancel dialog-btn" onclick="sureCancelY();"  name="Submit1" type="button"   value="取消" class="btn btn-primary">
            </div>
        </div>
    </div>
	<div class="dialog-popover dialog-roleselect">
        <div class="dialog-header">
            <p class="dialog-title">角色选择</p>
            <div class="dialog-close close_popZ">×</div>
        </div>
        <div class="dialog-content">
            <div class="popnav" id="popnavApp"></div>
            <div class="right_content" >
                <!-- 			<div class="top_nav" id="moduleChoice"> -->
                <div class="top_nav" style="height: 0px">
                    <div id="slides"></div>
                </div>
<!--                 <div class="select_table" style="height: auto;"> -->
                <div class="select_table" style="height: auto; background-color: white">
                    <table  id="GridView1"  >
                        <tbody id="selectTable">
                        <tr class="first_tr">
                            <th class="device_title" style="width: 200px;">应用名称</th>
                            <th class="operation">功能操作</th>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <div class="dialog-footer">
            <div>
                <input id="groupId" name="groupId" type="text" style="width: 200px; display: none">
                <input  class="sure dialog-btn"  onclick="sureSave();"  value="确定" class="btn btn-primary">
                <input  class="cancel dialog-btn" onclick="sureCancelZ();"  value="取消" class="btn btn-primary">
            </div>
        </div>
    </div>
</div>
<script type="text/javascript">
	$(function() {		
		$.ajax({
					type : "GET",
					url : "${pageContext.request.contextPath}/okcsys/usergroup/findModuleList",
					cache : false,
					success : function(data) {
						var moduleList = data;
                            $("#permissionSelect").html("");
						var html = "";
						for ( var i = 0; i < moduleList.length; i++) {
							var htmlSpan = '<div value = "'+moduleList[i].elementvalue+'"><p>'+ moduleList[i].headerdescription+ '</p><span>√</span></div>';
							if(moduleList[i].elementvalue == "PWARN"){
								htmlSpan = "";
							}	
							html = html+htmlSpan;
						}
						$("#permissionSelect").append(html);
						$(".permission_select>div").each(function() {
							$(this).toggle(function() {
								$(this).find("span").show();
								$(this).addClass("changeBg");

							}, function() {
								$(this).find("span").hide();
								$(this).removeClass("changeBg");
							});
						});
					}
				});

// 		$(".permission_title").live("click", function() {;
// 			$(".mask").show();
// 			$(".dialog-roleselect").show();
// 			$('#slides').slidesjs({
// 				width: 400,
// 				height: 34,
// 				play: {
// 					active: false,
// 					auto: false,
// 					interval: 4000,
// 					swap: true,
// 					pauseOnHover: true,
// 					restartDelay: 2500
// 				},
// 				navigation: {
// 					active: true,
// 					effect: "slide"
// 				},
// 				pagination: {
// 					active: false,
// 					effect: "slide"
// 				},
// 				effect: {
// 					slide: {
// 						speed: 1000
// 					},
// 					fade: {
// 						speed: 1800,
// 						crossfade: true
// 					}
// 				}
// 			});
// 			return false;
// 		});

		$("#dialog-modal1").dialog({
			width : "645px",
			autoOpen : false,
			show : "blind",
			hide : "explode",
			modal : true
		});

		$(".del_btn").live("click", function(event) {
            $(".mask").show();
            $(".dialog-confirm").show();
            event.stopPropagation();
		});

		$("#dialog-modal2").dialog({
			width : "767px",
			autoOpen : false,
			show : "blind",
			hide : "explode",
			modal : true
		});
		
		$(".ui-icon-closethick").click(function(){
			window.location.href = "${pageContext.request.contextPath}/okcsys/usergroup";
		});
		
		$(".special").live("click", function() {
			$(".mask").show();
            $(".dialog-createjob").show();
            $(".dialog-createjob>.dialog-header>p").text("创建岗位");
            $("#ugroupId").val("");
            $("#jobGw").val("");
            $("#roleSelect").val("NORMAL");
            $("#groupcomment").val("");
            $(".permission_select>div").each(function() {
            	$(this).removeClass("changeBg");
            	$(this).find("span").hide();
			});
			var pat = "";
			return false;
		});

        $(".edit_btn").live("click", function(){
            $(".mask").show();
            $(".dialog-createjob").show();
//            var text = $(".dialog-createjob>.dialog-header>p").text();
            $(".dialog-createjob>.dialog-header>p").text("编辑岗位");
            editUserGroup();
            return false;
            
        });
		$.ajax({
					type : "GET",
					url : "${pageContext.request.contextPath}/okcsys/usergroup/refrashUserGroup",
					cache : false,
					success : function(data) {
						var userGroupsList = data;
// 						document.getElementById('totaljob').innerHTML = "";
                        $("#totaljob").html("");
						var html = "";
						var htmlKt = "";
						if (userGroupsList.length > 0) {
							for ( var j = 0; j < userGroupsList.length; j++) {
								var groupname = userGroupsList[j].groupName;
								var groupid = userGroupsList[j].groupId;
								var menuUserGroupsList = userGroupsList[j].appAuthList;
								if (j % 6 == 0) {
									htmlKt = '<li><div><div class="swap_module"  style="margin-left: 10px;cursor : pointer"><div class="nomargin"  onclick="showGroupid(\''
											+ groupname
											+ '\',\''
											+ groupid
											+ '\');">';
								} else {
									htmlKt = '<div onclick="showGroupid(\''
											+ groupname + '\',\'' + groupid
											+ '\');">';
								}
								if (menuUserGroupsList.length > 0) {
									var ms = "<ul>";
									for ( var menu = 0; menu < menuUserGroupsList.length; menu++) {
										ms = ms
												+ '<li>'
												+ menuUserGroupsList[menu].appcode
												+ '</li>';
									}
									ms + "</ul>";
									html = html
											+ htmlKt
											+ '<div class="job"><p>'
											+ groupname
											+ '</p><div class="del_btn"></div><div class="edit_btn"></div></div><div class="permission">'
											+ ms
											+ '<div class="choose">a</div></div></div>';
								} else {
									html = html
											+ htmlKt
											+ '<div class="job"><p>'
											+ groupname
											+ '</p><div class="del_btn"></div><div class="edit_btn"></div></div><div class="permission">'
											+ '<div class="choose">a</div></div></div>';
								}
								if (j % 6 == 5
										|| j == userGroupsList.length - 1) {
									html = html
											+ '<div class="special"><div class="cross">+</div></div></div></li>';
								}
							}
						} else {
							html = html
									+ '<div class="special"><div class="cross">+</div></div>';
						}
						$("#totaljob").append(html);
// 						$(".permission_select>div").each(function() {
// 							$(this).toggle(function() {
// 								$(this).find("span").show();
// 								$(this).addClass("changeBg");

// 							}, function() {
// 								$(this).find("span").hide();
// 								$(this).removeClass("changeBg");
// 							});
// 						});
						jQuery('#mycarousel').jcarousel({
							vertical : true,
							scroll : 1
						});

						$(".swap_module>div:not('.special,.del_btn')").each(
								function(index) {
									$(this).click(
											function() {
												$(this).siblings().find(
														".choose").hide();
												$(this).siblings().find(".job")
														.removeClass(
																"highLight2");
												$(this).find(".job").addClass(
														"highLight2");
												$(this).find(".choose").show();
												$(".permission_detail").show();
											});
								});
					}
				});
		$(".close_popX").click(function(){
			$(".mask").hide();
			$(".dialog-createjob").hide();
		});
		
		$(".close_popY").click(function(){
			$(".mask").hide();
			$(".dialog-confirm").hide();
// 			window.location.href = "${pageContext.request.contextPath}/okcsys/usergroup";
		});
		
		$(".close_popZ").click(function(){
			$(".mask").hide();
			$(".dialog-roleselect").hide();
		});
	});
     
	var groupIdJz = ""; 
	var gwNameQx = "";
	function showGroupid(gwName, groupid) {
		gwNameQx = gwName;
		groupIdJz = groupid;
		groupId.value = groupid;
        $("#contenttitle").html("");
		var html = '<h3 style="margin-left: 20px">功能权限</h3><span>' + gwName
				+ '的功能权限</span><div class="division_line short"></div>';
		$("#contenttitle").append(html);
		$.ajax({
					type : "GET",
					url : "${pageContext.request.contextPath}/okcsys/usergroup/findMenuByUserGroupName?groupname="+encodeURIComponent(gwName),
					cache : false,
					success : function(data) {
						var menuList = data;
						var shtml = "";
						var htmlKt = "";
                        $("#permissionDetail").html("");
						for ( var j = 0; j < menuList.length; j++) {
							var okcMenuListHtml = "<table><tbody>";
							var menuName = menuList[j].signame;
							var appcode = menuList[j].appcode;
							var okcMenuList = menuList[j].okcMenuList;
							var paraIntList = [];
							for ( var k = 0; k < okcMenuList.length; k++) {
								var okcMenu = okcMenuList[k];
// 隐藏√							var paraInthtml = '<tr><td class="okicon">√</td><td class="func_des">'+ okcMenu.headerdescription+ '</td><td style="display:none" class="func_okcmenuid">'+ okcMenu.okcmenuid+'</td><td></td></tr>';
                                var paraInthtml = '<tr><td class="func_des">'+ okcMenu.headerdescription+ '</td><td style="display:none" class="func_okcmenuid">'+ okcMenu.okcmenuid+'</td><td></td></tr>';
                                if(okcMenu.headerdescription != '监测概要' && menuName == '监测与控制')
                                paraInthtml = '';
/**	隐藏初始化√							$.ajax({
									type : "GET",
									url : "${pageContext.request.contextPath}/okcsys/usergroup/findMenuByMenuOption?groupname="+encodeURIComponent(gwName)+"&appcode="+appcode,
									cache : false,
									async : false,
									success : function(data) {
										paraIntList = data;
									}
								})
								 for(var kt=0; kt<paraIntList.length; kt++){
									 if(paraIntList[kt].headerdescription == okcMenuList[k].headerdescription)
										 paraInthtml = '<tr><td   class="okicon press">√</td><td class="func_des">'+ okcMenu.headerdescription+ '</td><td style="display:none" class="func_okcmenuid">'+ okcMenu.okcmenuid+'</td><td></td></tr>';
								 }*/
								okcMenuListHtml = okcMenuListHtml+paraInthtml;
							}
							okcMenuListHtml = okcMenuListHtml
									+ "</tbody></table>";
							var bs = j + 1;
							if (j % 5 == 0) {
								htmlKt = '<div class="nomargin"><div style="cursor:pointer" onclick="showPopnav(\''
										+ appcode
										+ '\',this)" class="permission_title per'
										+ bs
										+ '"><p>'
										+ menuName
										+ '</p></div>';
							} else {
								htmlKt = '<div><div style="cursor:pointer"  onclick="showPopnav(\''
										+ appcode
										+ '\',this)" class="permission_title per'
										+ bs + '"><p>' + menuName
										+ '</p></div>';
							}
							shtml = shtml + htmlKt + okcMenuListHtml + "</div>";
						}
						$("#permissionDetail").append(shtml);
						$(".okicon").each(function() {
							$(this).on("click",function(event){
								if($(this).hasClass("press")){
									$(this).removeClass("press")
								} else {
									$(this).addClass("press")
								}
							})
						});
					}
				});
	}
	function showPopnav(code,thisdom) {
		/**
		隐藏判断勾选菜单项
		var  func_okcmenuids = "";
		var abc = $(thisdom).parent().find("table tr");
		for(var i = 0; i < abc.length; ++i){
			if(i<abc.length-1){
			if($($(abc)[i]).find("td:eq(0)").hasClass("press")){
				func_okcmenuids = func_okcmenuids + $($(abc)[i]).find("td:eq(2)").text()+",";
			}
			}
			else{
				if($($(abc)[i]).find("td:eq(0)").hasClass("press")){
					func_okcmenuids = func_okcmenuids + $($(abc)[i]).find("td:eq(2)").text();
				}	
			}
		}
		if(func_okcmenuids == ""){
			alert("请选择菜单");
		}
	    
		else{
		*/	
		$(".mask").show();
		$(".dialog-roleselect").show();
		$.ajax({
			type : "GET",
			url : "${pageContext.request.contextPath}/okcsys/usergroup/findAppMenu?appcode="
					+ code,
			cache : false,
			success : function(data) {
				var appList = data;
				var html = "";
               $("#popnavApp").html("");
				for ( var k = 0; k < appList.length; k++) {
					var appMenu = appList[k];
					var appCode = appMenu.elementvalue;
					var appName = appMenu.headerdescription;
					var htmlTr = '<div class="sub_popnav"><div onclick="moduleChoice(\''
						         + appCode
						         + '\')" style="width: 180px;cursor: pointer;"><p style="color:#333">'
						         + appName + '</p></div></div>';
					if(code=='MCTRL' && appName!='监测概要')
					{htmlTr = '';}
					html = html+htmlTr;
// 					html = html
// 							+ '<div class="sub_popnav"><div onclick="moduleChoice(\''
// 							+ appCode
// 							+ '\')" style="width: 180px;cursor: pointer;"><p style="color:#333">'
// 							+ appName + '</p></div></div>';
				}
				$("#popnavApp").append(
						html + '<div class="blank"></div>');
			}
		});
		$("#slides").html("");
		$("#selectTable").html("");
		$("#selectTable").html('<tr class="first_tr"><th class="device_title" style="width: 200px;">应用名称</th><th class="operation">功能操作</th></tr>');
// 		$.ajax({
// 					type : "GET",
// 					url : "${pageContext.request.contextPath}/okcsys/usergroup/findMenuByFuncokcmenuid?func_okcmenuids="+func_okcmenuids,
// 					cache : false,
// 					success : function(data) {
// 						var appList = data;
// 						var html = "";
//                        $("#popnavApp").html("");
// 						for ( var k = 0; k < appList.length; k++) {
// 							var appMenu = appList[k];
// 							var appCode = appMenu.elementvalue;
// 							var appName = appMenu.headerdescription;
// 							html = html
// 									+ '<div class="sub_popnav"><div onclick="moduleChoice(\''
// 									+ appCode
// 									+ '\')" style="width: 180px;cursor: pointer;"><p style="color:#333">'
// 									+ appName + '</p></div></div>';
// 						}
// 						$("#popnavApp").append(html + '<div  id = "blankfuncokcmenuids"  name="blankfuncokcmenuids" value = "'+func_okcmenuids + '"></div><div  id = "blankcode"  name="blankcode" value = "'+code+'" ></div><div class="blank"></div>');
// 					}
// 				});
	  }	
//	}
	
	function moduleChoice(code) {
		$("#selectTable").html("");
		$("#selectTable").html('<tr class="first_tr"><th class="device_title" style="width: 200px;">应用名称</th><th class="operation">功能操作</th></tr>');
		$.ajax({
					type : "GET",
					url : "${pageContext.request.contextPath}/okcsys/usergroup/findFilterMenu?appcode="
							+ code,
					cache : false,
					success : function(data) {
						var appFilterList = data;
// 						var html = '<img class="left_btn slidesjs-previous slidesjs-navigation"  src="${pageContext.request.contextPath}/resources/images/left_arrow.png"><div><ul class="module_choice" >';
                        var html = '<div><ul class="module_choice" >';
						$("#slides").html("");
						for ( var k = 0; k < appFilterList.length; k++) {
							var appFilterMenu = appFilterList[k];
							var appFilterCode = appFilterMenu.elementvalue;
							var menuType = appFilterMenu.menutype + '|'
									+ appFilterMenu.ownerelement;
							var appFilterName = appFilterMenu.headerdescription;
							var htmlTr = '<li><div style="cursor:pointer">' + appFilterName + '</div></li>';
// 							html = html + '<li><div style="cursor:pointer" onclick="selectTable(\''
// 									+ appFilterCode + '\',\'' + menuType
// 									+ '\')">' + appFilterName + '</div></li>';
                           if(code == 'MonitorSum'){
                        	   htmlTr = '';
                        	   $.ajax({
               					type : "GET",
               					url : "${pageContext.request.contextPath}/okcsys/usergroup/findByGroupnameAndAppcode?groupname="
               						   +encodeURIComponent(gwNameQx),
               					cache : false,
               					async:false,
               				   success : function(data) {
               				   var signame = data.signame;   
               				  
                        	   var htmlSelect = '<tr class="first_tr"><th class="device_title" style="width: 200px;">应用名称</th><th class="operation">功能操作</th></tr>';
                        	   $("#selectTable").html("");
                        	   var html_Nt = '<tr><td class ="td1" style="display:none">1</td><td td2>暖通空调</td><td class="mult_select">';
                        	   var html_Gg = '<tr><td class ="td1" style="display:none">2</td><td td2>公共照明</td><td class="mult_select">';
                        	   var html_Yj = '<tr><td class ="td1" style="display:none">3</td><td td2>夜景照明</td><td class="mult_select">';
                        	   var signameStr = "";
                        	   if(signame!=null){
                        	   var checkList = signame.split("|"); 
                        	   var optionname_Nt = checkList[0].substring(2, 4);
                        	   var optionname_Gg = checkList[1].substring(2, 4);
                        	   var optionname_Yj = checkList[2].substring(2, 4);
                        	   var check1 = "" , check2="" , check3="";
//                         	   if(optionname_Nt.substring(0, 1)=="A")
//                         		   check1 = '<input type="checkbox" id="box" checked="checked" value="AUTO" style="margin-left: 5px"><span>自动运行</span>';
//                                if(optionname_Nt.substring(0, 1)=="0")
//                             	   check1 = '<input type="checkbox" id="box" value="AUTO" style="margin-left: 5px"><span>自动运行</span>';
                               if(optionname_Nt.substring(0, 1)=="M")
                            	   check2 = '<input type="checkbox" id="box" checked="checked" value="MANUL" style="margin-left: 5px"><span>手动模式</span>';
                               if(optionname_Nt.substring(0, 1)=="0")
                            	   check2 = '<input type="checkbox" id="box" value="MANUL" style="margin-left: 5px"><span>手动模式</span>';
                               if(optionname_Nt.substring(1, 2)=="C")
                            	   check3 = '<input type="checkbox" id="box" checked="checked" value="CHANGE" style="margin-left: 5px"><span>模式切换</span>';
                               if(optionname_Nt.substring(1, 2)=="0")
                            	   check3 = '<input type="checkbox" id="box" value="CHANGE" style="margin-left: 5px"><span>模式切换</span>'; 
                               html_Nt	=  html_Nt + check1+check2+check3;
//                                if(optionname_Gg.substring(0, 1)=="A")
//                         		   check1 = '<input type="checkbox" id="box" checked="checked" value="AUTO" style="margin-left: 5px"><span>自动运行</span>';
//                                if(optionname_Gg.substring(0, 1)=="0")
//                             	   check1 = '<input type="checkbox" id="box" value="AUTO" style="margin-left: 5px"><span>自动运行</span>';
                               if(optionname_Gg.substring(0, 1)=="M")
                            	   check2 = '<input type="checkbox" id="box" checked="checked" value="MANUL" style="margin-left: 5px"><span>手动模式</span>';
                               if(optionname_Gg.substring(0, 1)=="0")
                            	   check2 = '<input type="checkbox" id="box" value="MANUL" style="margin-left: 5px"><span>手动模式</span>';
                               if(optionname_Gg.substring(1, 2)=="C")
                            	   check3 = '<input type="checkbox" id="box" checked="checked" value="CHANGE" style="margin-left: 5px"><span>模式切换</span>';
                               if(optionname_Gg.substring(1, 2)=="0")
                            	   check3 = '<input type="checkbox" id="box" value="CHANGE" style="margin-left: 5px"><span>模式切换</span>'; 
                               html_Gg	=  html_Gg + check1+check2+check3;
//                                if(optionname_Yj.substring(0, 1)=="A")
//                         		   check1 = '<input type="checkbox" id="box" checked="checked" value="AUTO" style="margin-left: 5px"><span>自动运行</span>';
//                                if(optionname_Yj.substring(0, 1)=="0")
//                             	   check1 = '<input type="checkbox" id="box" value="AUTO" style="margin-left: 5px"><span>自动运行</span>';
                               if(optionname_Yj.substring(0, 1)=="M")
                            	   check2 = '<input type="checkbox" id="box" checked="checked" value="MANUL" style="margin-left: 5px"><span>手动模式</span>';
                               if(optionname_Yj.substring(0, 1)=="0")
                            	   check2 = '<input type="checkbox" id="box" value="MANUL" style="margin-left: 5px"><span>手动模式</span>';
                               if(optionname_Yj.substring(1, 2)=="C")
                            	   check3 = '<input type="checkbox" id="box" checked="checked" value="CHANGE" style="margin-left: 5px"><span>模式切换</span>';
                               if(optionname_Yj.substring(1, 2)=="0")
                            	   check3 = '<input type="checkbox" id="box" value="CHANGE" style="margin-left: 5px"><span>模式切换</span>'; 
                               html_Yj	=  html_Yj + check1+check2+check3;
                               signameStr = html_Nt+html_Gg+html_Yj;
                        	   }
                        	   else{
                        	   var signameTable = '<input type="checkbox" id="box" value="MANUL" style="margin-left: 5px"><span>手动模式</span>'+'<input type="checkbox" id="box" value="CHANGE" style="margin-left: 5px"><span>模式切换</span>';
                        	   html_Nt = html_Nt+signameTable;
                        	   html_Gg = html_Gg+signameTable;
                        	   html_Yj = html_Yj+signameTable;
                        	   signameStr = html_Nt+html_Gg+html_Yj;
                        	   }
                        	    htmlSelect = htmlSelect+signameStr;
                                $("#selectTable").append(htmlSelect);
               				   }
               				})
                           }
                           html = html + htmlTr;
						}
// 						$("#slides").append(html+ '</ul></div><img class="right_btn slidesjs-next slidesjs-navigation" src="${pageContext.request.contextPath}/resources/images/right_arrow.png">');
						$("#slides").append(html+ '</ul></div>');
					}
				});
	}

	function selectTable(appCode, menuType) {
		$.ajax({
					type : "GET",
					url : "${pageContext.request.contextPath}/okcsys/usergroup/findByMenutypeAndOwnerelement?appcode="
							+ appCode + "&menuType=" + menuType,
					cache : false,
					async:false,
					success : function(data) {
						var dataList = data;
						var html = '<tr class="first_tr"><th class="device_title" style="width: 200px;">应用名称</th><th class="operation">功能操作</th></tr>';
						$("#selectTable").html("");
						for ( var k = 0; k < dataList.length; k++) {
							var htmlzhyc = "";
							var appFilterMenu = dataList[k];
							var okcmenuId = appFilterMenu.okcmenuid;
							var appFilterName = appFilterMenu.headerdescription;
							html = html+'<tr><td class ="td1" style="display:none">'+okcmenuId+'</td><td td2>'+ appFilterName+'</td><td class="mult_select">'
//  							           +'<input type="checkbox" id="box" value="C" style="margin-left: 5px"><span>新增</span><input type="checkbox" id="box" value="M" ><span>修改</span><input type="checkbox" id="box" value="D"><span>删除</span><input type="checkbox"  id="box" value="V"><span>查看</span>';
                                $.ajax({
								type : "GET",
								url : "${pageContext.request.contextPath}/okcsys/usergroup/findByOkcmenuidAndGroupId?okcmenuId="+okcmenuId+"&groupId="+groupIdJz,
								cache : false,
								async : false,
								success : function(data) {
									var optionnameList = data;
// 									var htmlzhyc = '<input type="checkbox" id="box" value="C" style="margin-left: 5px"><span>新增</span><input type="checkbox" id="box" value="M" ><span>修改</span><input type="checkbox" id="box" value="D"><span>删除</span><input type="checkbox"  id="box" value="V"><span>查看</span>';
									if(optionnameList.length>0){
									optionnamePj = optionnameList[0].optionname;
									var optionname1 = optionnamePj.substring(0, 1);
									var optionname2 = optionnamePj.substring(1, 2);
									var optionname3 = optionnamePj.substring(2, 3);
									var optionname4 = optionnamePj.substring(3, 4);
                                    var chck1 = "" , check2="" , check3="" , check4="";
                                    if(optionname1=="C")
                                    	chck1 = '<input type="checkbox" checked="checked" id="box" value="C" style="margin-left: 5px"><span>新增</span>';
                                    if(optionname1=="0")
                                    	chck1 = '<input type="checkbox" id="box" value="C" style="margin-left: 5px"><span>新增</span>';
                                    if(optionname2=="M")
                                    	check2 = '<input type="checkbox" checked="checked" id="box" value="M"><span>修改</span>';
                                    if(optionname2=="0")
                                    	check2 = '<input type="checkbox" id="box" value="M"><span>修改</span>';
                                    if(optionname3=="D")
                                    	check3 = '<input type="checkbox" checked="checked" id="box" value="D"><span>删除</span>';
                                    if(optionname3=="0")
                                    	check3 = '<input type="checkbox" id="box" value="D"><span>删除</span>';
                                    if(optionname4=="V")
                                    	check4 = '<input type="checkbox" checked="checked" id="box" value="V"><span>查看</span>';
                                    if(optionname4=="0")
                                    	check4 = '<input type="checkbox" id="box" value="V"><span>查看</span>'; 
                                    	htmlzhyc = 	chck1+check2+check3+check4;
									}
									else{
										htmlzhyc = 	'<input type="checkbox" id="box" value="C" style="margin-left: 5px"><span>新增</span><input type="checkbox" id="box" value="M" ><span>修改</span><input type="checkbox" id="box" value="D"><span>删除</span><input type="checkbox"  id="box" value="V"><span>查看</span>';
									}	
								}
						   })
						   html = html+htmlzhyc;
						}
						 $("#selectTable").append(html+'</td></tr>');
					}
		})
	}					
	function deleteUserGroupBy() {
		var groupIdValue = groupId.value;
		var url = "${pageContext.request.contextPath}/okcsys/usergroup/deleteUserGroupBygroupId?groupId="
				+ groupIdValue;
		window.location.href = url;
	}
	
	function editUserGroup() {
		var groupIdValue = groupId.value;
		var url = "${pageContext.request.contextPath}/okcsys/usergroup/usergroupUpdate?groupId="+groupIdValue;
		$.ajax({
			 type:"GET",
			 url:url ,
			 cache:false,
			 success:function(data){
			$("#ugroupId").val(data.groupId);
			$("#jobGw").val(data.groupName);		 
		    $("#roleSelect").val(data.langcode);
		    $("#groupcomment").val(data.groupComment);
		    var appDataAuthList = data.appAuthList;
		    $.ajax({
				type : "GET",
				url : "${pageContext.request.contextPath}/okcsys/usergroup/findModuleList",
				cache : false,
				success : function(data) {
					var moduleList = data;
                        $("#permissionSelect").html("");
					var html = "";
					for ( var i = 0; i < moduleList.length; i++) {
						var htmlk = '<div  value = "'+moduleList[i].elementvalue+'"><p>'+ moduleList[i].headerdescription+'</p><span>√</span></div>';
						if(moduleList[i].elementvalue == "PWARN"){
							htmlk = '';
						}
						for(var j=0; j<appDataAuthList.length;j++){
							if(appDataAuthList[j].appcode == moduleList[i].headerdescription)
								htmlk = '<div class="changeBg" value = "'+moduleList[i].elementvalue+'"><p>'+ moduleList[i].headerdescription+'</p><span style="display: inline;">√</span></div>'
					  }
						html = html+htmlk;
				  }
					$("#permissionSelect").append(html);
					$(".permission_select>div").each(function() {
// 						$(this).toggle(function() {
// 							$(this).find("span").show();
// 							$(this).addClass("changeBg");

// 						}, function() {
// 							$(this).find("span").hide();
// 							$(this).removeClass("changeBg");
// 						});
					$(this).on("click", function(){
						if($(this).hasClass("changeBg")){
							$(this).find("span").hide();
							$(this).removeClass("changeBg");
						} else {
							$(this).find("span").show();
							$(this).addClass("changeBg");
						}
					})
					});
				}
			});
		}
		});
	}

	var pat = "";
	function save() {
		var jobGwValue = jobGw.value;
		if (jobGwValue == "") {
			alert("名称不能为空");
			return;
		}
		var groupcommentValue = groupcomment.value;
		if (groupcommentValue == "") {
			alert("附加说明不能为空");
			return;
		}
		var roleSelectValue = $('#roleSelect').val();
		var ugroupIdValue = $('#ugroupId').val();
		var len = $(".permission_select>div").length;
		if (len > 0) {
			for ( var i = 0; i < len; ++i) {
				var disOrInline =  $($(".permission_select>div>span")[i]).css("display");
				if (disOrInline == "block" || disOrInline =="inline") {
					pat = pat + $($(".permission_select>div")[i]).attr("value")
							+ ",";
				}
			}
		}
		var url = "${pageContext.request.contextPath}/okcsys/usergroup/doInsertUserGroup?jobGw="+ encodeURIComponent(jobGwValue)+ "&groupcomment="+ encodeURIComponent(groupcommentValue)+ "&pat=" + pat +"&roleSelectValue="+roleSelectValue+"&ugroupIdValue="+ugroupIdValue;
		$.ajax({
			type : "GET",
			url : url,
			cache : false,
			success : function(data) {
				if (data[0] == "error"){
					alert("岗位名称不能重复");
					pat = "";
				}
				else {
					window.location.href = "${pageContext.request.contextPath}/okcsys/usergroup";
				}
			}
		});
	}
	
	var dataListCg = []
	function sureSave(){
// 		$('#GridView1 tr').each(function() {
// 			var menuOption=new Object();
// 			$(this).find("td").each(function(){
//                 if($(this).index()==0){
// 	                menuOption.okcmenuid = $(this).text();
//                 }	
//                 if($(this).index()==1){
//                  	menuOption.groupname = groupIdJz;
//                 }
//                 var optionname = "";
//                 if($(this).index()==2){
//                 	if ($(this).children().eq(0).prop("checked") == true){
//                 		optionname=optionname+"C";
//                 	}
//                 	else{
//                 		optionname=optionname+"0";
//                 	}
//                 	if ($(this).children().eq(2).prop("checked") == true){
//                 		optionname=optionname+"M";
//                 	}
//                 	else{
//                 		optionname=optionname+"0";
//                 	}
//                 	if ($(this).children().eq(4).prop("checked") == true){
//                 		optionname=optionname+"D";
//                 	}
//                 	else{
//                 		optionname=optionname+"0";
//                  	}
//                 	if ($(this).children().eq(6).prop("checked") == true){
//                 		optionname=optionname+"V";
//                 	}
//                 	else{
//                 		optionname=optionname+"0";
//                 	}
//                 }
//                    menuOption.optionname = optionname;
// 			});
// 			dataListCg.push(menuOption);
// 			console.log(dataListCg);
// 		});
// 		var url = "${pageContext.request.contextPath}/okcsys/usergroup/doInsertDataListCg";
// 		$.ajax({   
// 			 type:"POST", 
// 	         url:url, 
// 	         dataType:"json",      
// 	         contentType:"application/json",               
// 	         data:JSON.stringify(dataListCg), 
// 	         success:function(data){
// 	        }    
// 	    });
//         var para1 =  $('#blankfuncokcmenuids').attr("value");
//         var para2 =  $('#blankcode').attr("value");
// 		var url = "${pageContext.request.contextPath}/okcsys/usergroup/doInsertMenuOption?para1="+para1+"&para2="+para2+"&para3="+ encodeURIComponent(gwNameQx);
// 		$.ajax({ 
// 			 type:"GET", 
// 	         url:url, 
// 	         cache : false,
// 	         success:function(data){
// 	        }    
// 	    });
// 		$("#dialog-modal").dialog("close");
        	$('#GridView1 tr').each(function() {
			var menuOption=new Object();
			$(this).find("td").each(function(){
                if($(this).index()==0){
	                menuOption.okcmenuid = $(this).text();
                }	
                if($(this).index()==1){
                 	menuOption.groupname = gwNameQx;
                }
                var optionname = "";
                if($(this).index()==2){
                	if ($(this).children().eq(0).prop("checked") == true){
                		optionname=optionname+"M";
                	}
                	else{
                		optionname=optionname+"0";
                	}
                	if ($(this).children().eq(2).prop("checked") == true){
                		optionname=optionname+"C";
                	}
                	else{
                		optionname=optionname+"0";
                 	}
                }
                   menuOption.optionname = optionname;
			});
			dataListCg.push(menuOption);
		});
		var url = "${pageContext.request.contextPath}/okcsys/usergroup/doInsertDataListCg";
		$.ajax({   
			 type:"POST", 
	         url:url, 
	         dataType:"json",      
	         contentType:"application/json",               
	         data:JSON.stringify(dataListCg), 
	         success:function(data){
	        	 window.location.href = "${pageContext.request.contextPath}/okcsys/usergroup";
	        }    
	    });
	}
	
	function sureCancelX(){
		$(".mask").hide();
		$(".dialog-createjob").hide();
// 		window.location.href = "${pageContext.request.contextPath}/okcsys/usergroup";
	}
	
	function sureCancelY(){
		$(".mask").hide();
		$(".dialog-confirm").hide();
// 		window.location.href = "${pageContext.request.contextPath}/okcsys/usergroup";
	}
	
	function sureCancelZ(){
		$(".mask").hide();
		$(".dialog-roleselect").hide();
// 		window.location.href = "${pageContext.request.contextPath}/okcsys/usergroup";
	}
</script>
</html>