var userList = [];

$(function() {
    $(".users>div").each(function(index){
        $(this).hover(function(){
            user_swap(index);
            $(this).find("p").addClass("fontChange")
        },function(){
            remove_user_swap(index);
            $(this).find("p").removeClass("fontChange");
        });
    });
	
	$(".users>div").each(function(index) {
		$(this).click(function() {
            user_swap_click(index);
            $(this).siblings().find("i").removeClass();
            $(this).siblings().removeClass("selected");
            $(this).siblings().find("p").removeClass("fontChange_2");
            $(this).addClass("selected");
            $(this).find("p").addClass("fontChange_2");
            $(this).siblings().hide();
            $(".back_icon").show();
            $(this).css({
                "margin-right" : "253px"
            });
            $(".user_name_pwd").show();
			
			//当前选择的用户组ID
			var currUserCategory = $(this).attr('id');
			if(typeof(currUserCategory)!='undefined') {
				console.log(currUserCategory);
				var url = CONTEXT_PATH + "/login/";
				$.post(url,{"category":currUserCategory},function(data){
					
					$.each(data,function(index,obj){
						var userface = obj.userface;
						if(userface==null || userface.length==0) {
							userface = CONTEXT_PATH + "/resources/images/userface.png";
						}
						userList.push({"img":userface,"value":obj.loginid,"name":obj.fullname});
						//console.log(obj);
					});
					
					
					
				});
			}
		});
	});

    $(".back_icon").click(function(){
        $(this).hide();
        $(".users>div").css({
            "diplay" : "inline",
            "margin-right" : "0px"
        }).show();
        $(".user_name_pwd").hide();
        $(".user_name>input").val("");
        $(".passwd").val("");
        $(".users>div").each(function(index){
            // remove_user_swap(index);
            remove_user_swap_2(index);
            $(this).removeClass("selected");
            $(this).find("p").removeClass("fontChange_2");
        });
    });

	$(".user_list>li").live("hover", function() {
		var thisValue = $(this).find("span").attr("value");
		$(".user").val("");
		$(".user").val(thisValue);
		$(this).siblings().removeClass("user_selected");
		$(this).addClass("user_selected");
	});

	$(".user").on("keyup", function() {
		$(".user_list>li").remove();
		var keyStr = $(".user").val();
		createUserList(keyStr);
		$(".user_group").show();
		if ($(".mCustomScrollBox").length) {
			return false;
		} else {
			ss = $("#content_1").mCustomScrollbar({
				scrollButtons : {
					enable : true
				},
				advanced : {
					updateOnContentResize : true
				}
			});
		}
	});

	$(".user_list>li").on("click", function() {
		var thisName = $(this).find("span").attr("value");
		$(".user_name>input").val(thisName);
		$(".user_group").hide();
		
		
	});

	$(".user").blur(function() {
		$(".user_group").hide();
	});

	$(".user_group").click(function() {
		$(".user").focus();
	});

	$(".confirm").click(function() {
		$(".submit-form").trigger("click");
	});
	

});

//var userList = [
//{"img" : CONTEXT_PATH + "/resources/images/user1.png", "value" : "用户001", "name" : "用户001"},
//{"img" : CONTEXT_PATH + "/resources/images/user1.png", "value" : "用户002", "name" : "用户002"},
//{"img" : CONTEXT_PATH + "/resources/images/user1.png", "value" : "用户003", "name" : "用户003"},
//{"img" : CONTEXT_PATH + "/resources/images/user1.png", "value" : "用户004", "name" : "用户004"},
//{"img" : CONTEXT_PATH + "/resources/images/user1.png", "value" : "用户005", "name" : "用户005"}
//];

function createUserList(keyStr) {
	var $liDom, $imgDom, $spanDom;
	for ( var i = 0; i < userList.length; ++i) {
		$liDom = $("<li/>");
		$imgDom = $("<img/>", {
			src : userList[i].img + ""
		});
		$spanDom = $("<span/>", {
			value : userList[i].value + "",
			text : userList[i].name + ""
		});
		$liDom.appendTo($(".user_list"));
		$imgDom.appendTo($liDom);
		$spanDom.insertAfter($imgDom);
		if ($spanDom.text().indexOf(keyStr) != -1) {
			var text = $spanDom.text();
			text = text.replace(keyStr,
					"<span style='background-color: #1f9295'>" + keyStr
							+ "</span>");
			//console.log(text);
			$spanDom.html(text);
		}
	}
}

function user_swap(index) {
	userList = [];
	switch (index) {
	case 0:
		$(".normal_user>i").addClass("normal_user1");
		$.ajax({
			 type : "GET",
			 url : "login/findUserListByDmValue?DmValue=NORMAL",
			 cache : false,
			 async:false,
			 success : function(data) {
				 var loginUsers = data;
				 for(var i=0;i<loginUsers.length;i++){
				 userList.push({"img":CONTEXT_PATH +"/resources/images/"+loginUsers[i].img,"value":loginUsers[i].value,"name":loginUsers[i].name});
			  }
			}
		});
		break;
	case 1:
		$(".tech_user>i").addClass("tech_user1");
		$.ajax({
			 type : "GET",
			 url : "login/findUserListByDmValue?DmValue=TECH",
			 cache : false,
			 async:false,
			 success : function(data) {
			 var loginUsers = data;
			 for(var i=0;i<loginUsers.length;i++){
			 userList.push({"img":CONTEXT_PATH +"/resources/images/"+loginUsers[i].img,"value":loginUsers[i].value,"name":loginUsers[i].name});
			 }
			}
		});
		break;
	case 2:
		$(".admin_user>i").addClass("admin_user1");
		$.ajax({
			 type : "GET",
			 url :  "login/findUserListByDmValue?DmValue=ADMIN",
			 cache : false,
			 async:false,
			 success : function(data) {
				 var loginUsers = data;
				 for(var i=0;i<loginUsers.length;i++){
				 userList.push({"img":CONTEXT_PATH +"/resources/images/"+loginUsers[i].img,"value":loginUsers[i].value,"name":loginUsers[i].name});
				 }
			}
		});
		break;
	default:
		return false;
	}
	;
};

function remove_user_swap(index) {
	switch (index) {
	case 0:
		$(".normal_user>i").removeClass("normal_user1");
		break;
	case 1:
		$(".tech_user>i").removeClass("tech_user1");
		break;
	case 2:
		$(".admin_user>i").removeClass("admin_user1");
		break;
	default:
		return false;
	}
	;
}

function remove_user_swap_2(index) {
	switch (index) {
	case 0:
		$(".normal_user>i").removeClass("normal_user2");
		break;
	case 1:
		$(".tech_user>i").removeClass("tech_user2");
		break;
	case 2:
		$(".admin_user>i").removeClass("admin_user2");
		break;
	default:
		return false;
	}
	;
}

function user_swap_click(index) {
	switch (index) {
	case 0:
		$(".normal_user>i").addClass("normal_user2");
		break;
	case 1:
		$(".tech_user>i").addClass("tech_user2");
		break;
	case 2:
		$(".admin_user>i").addClass("admin_user2");
		break;
	default:
		return false;
	}
	;
}

