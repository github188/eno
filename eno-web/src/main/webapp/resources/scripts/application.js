
var console=console||{"log":function(){}};


function t(){
	  return Math.floor(Math.random() * ( 1000000));
}

/**
 * 显示窗口
 * @param str 窗口名称
 * */
function showwindow(str)
{
	try {
		console.log("打开窗口中名称："+ str);
		closeAllWindow();
		window.external.ExecuteUScript("showwindow(\""+ str  +"\");");
	}catch(err) {
		//console.log(err);
	}
}

/**
 * 判断所有画面窗口
 * */
function closeAllWindow() {
	try {
		var scp = "CloseAllWindow(\"主页\",\"\",\"\",\"\");";
		window.external.ExecuteUScript(scp);
	}catch(err) {
		//console.log(err);
	}
}

//子系统键值
var arrSubsys = [{"key":"HVAC","value":"暖通空调"},
              {"key":"WSD","value":"给排水控制"},
              {"key":"ETD","value":"变配电"},
              {"key":"FAS","value":"消防系统"},
              {"key":"SASSA","value":"防盗报警"},
              {"key":"MSVDO","value":"视频监控"},
              {"key":"SASAC","value":"门禁管理"},
              {"key":"EP","value":"电子巡更"},
              {"key":"LSPUB","value":"公共照明"},
              {"key":"LSN","value":"夜景照明"},
              {"key":"MSEM","value":"电梯监视"},
              {"key":"PFE","value":"客流统计"},
              {"key":"INFP","value":"信息发布"},
              {"key":"BGMB","value":"背景音乐"},
              {"key":"PARKM","value":"停车管理"},
              {"key":"INENV","value":"室内环境"},
              {"key":"ENVMS","value":"能源管理"}
              ];


var countSubSys = 0;
/**
 * 联动显示画面和菜单
 * 
 * @param subsys 子系统名称
 * @param winName 窗口名称
 * */
function showWindowAndMenu(subsys,winName) {
	//如：http://192.168.1.254:8080/portal/mctrl/INFP.html
	var reqFullPath = window.location.href;
	//拆分地址，取最后一个
	if(reqFullPath!='' && reqFullPath.indexOf("/")) {
		arrFullPath = reqFullPath.split("/");
	}
	//页面地址与名称
	var pageUrl="",pageName="";
	if(arrFullPath.length>1) {
		pageUrl = arrFullPath[arrFullPath.length-1]
	}
	pageName = pageUrl;
	if(pageUrl!="" && pageUrl.indexOf(".")>-1) {
		pageName = pageUrl.split(".")[0];
	}
	
	
	/*
	 * 当用户操作处理别的页面时，系统正在报警，自动跳转到报警页面
	 * 此方法必须明确所有正在操作的用户是否具备此权限以及用户体验
	 */
	var subsysId = ""; 
	for(i=0;i<arrSubsys.length;i++) {
		if(arrSubsys[i].value==subsys) {
			subsysId = arrSubsys[i].key;
			//如果子系统标识与当前页面相同，这不在跳转
			if(countSubSys==0 && subsysId!=pageName) {
				window.location.href= CONTEXT_PATH+ "/mctrl/"+ subsysId + ".html?src=alarm&winname="+ escape(winName); 
				countSubSys++;
			}
			return;
		}	
	}
}



/**
 * 窗口切换
 */
var windowSwitch = function(){
	
	var leftUrl = $("#windowSwitch").attr("leftUrl"),
		rightUrl = $("#windowSwitch").attr("rightUrl"),
		currentPath = window.location.pathname;
	
	$("#windowSwitch").click(function(){
		
		if(currentPath!=leftUrl) {
			window.location.href = leftUrl;
		} else {
			window.location.href = rightUrl;
		}
	});
	
};



$(function(){
	
	windowSwitch();
	
	$("#navigation li").live("click",function(obj){
        //窗口切换不关闭
        if($(this).attr("id")=='windowChange'){
            //console.log("---"+ $(this));
        }else{
            closeAllWindow();
           // console.log("--2-"+$(this).attr("id"));
        }
	});
	
	
	$(document.body).css({
	    "overflow-x":"hidden",
	    "overflow-y":"hidden"
	 });
	
	setInterval("GetTime()", 1000);
	
	
});


/**
 * 动态设置日期时间和星期
 */
function GetTime() {
	var mon, day, now, hour, min, sec;

	mon = new Array("01", "02", "03", "04", "05", "06", "07", "08", "09", "10",
			"11", "12");
	day = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");
	now = new Date();
	hour = now.getHours();
	min = now.getMinutes();
	sec = now.getSeconds();
	if (hour < 10) {
		hour = "0" + hour;
	}
	if (min < 10) {
		min = "0" + min;
	}
	if (sec < 10) {
		sec = "0" + sec;
	}

	var time = hour + ":" + min;
	var date = now.getFullYear() + "-" + mon[now.getMonth()] + "-"
			+ now.getDate();
	var week = day[now.getDay()];

	if ($("#global_time").length) {
		$("#global_time").html(time);
	}
	if ($("#global_date").length) {
		$("#global_date").html(date);
	}
	if ($("#global_week").length) {
		$("#global_week").html(week);
	}

	$("#Timer").html(
			"<nobr>" + day[now.getDay()] + ", " + mon[now.getMonth()] + " "
					+ now.getDate() + ", " + now.getFullYear() + " " + hour
					+ ":" + min + ":" + sec + "</nobr>");

}