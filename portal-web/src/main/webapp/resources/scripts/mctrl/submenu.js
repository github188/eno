/*
 * 子菜单相关操作
 * 
 * @author CHENPING
 * @version 1.0 build 20131014
 * 
 *  last Modified: 2014-06-06
 *  change log: 
 *  	1、删除不必要的代码
 *      2、重写并优化了菜单逻辑
 * */
$(function(){
	

	//页面菜单默认选项,获取URL地址相关的子系统，如果没有默认为展开第一个菜单
	var reqPath = window.location.pathname ;
	var arrPath = reqPath.split('/');
	var currPath = arrPath[arrPath.length-1].replace(".html","");

	submenu.defClick();

	/**
	 * 处理报警联动，由Client传入地址如： http://192.168.1.254:8080/portal/mctrl/HVAC.html?src=alarm&winname=%u9632%u76D7%u62A5%u8B66B1_%u5E73%u9762%u56FE
	 * 拆分传入地址，获取来源和画面名称
	 * */
	var reqFullPath = window.location.href;
	var paramters = "",arrKeyValues = "",src="",winname="";
	if(reqFullPath.indexOf('?')>-1) {
		//取URL参数字符部分
		paramters = reqFullPath.split('?')[1];
		
		if(paramters.indexOf("&")>-1) {
			arrKeyValues = paramters.split("&");
		}
		//获取来源
		if((arrKeyValues[0]!=null || typeof(arrKeyValues[0])!='undefined')&&(arrKeyValues[0].indexOf("=")>-1)) {
			src = arrKeyValues[0].split("=")[1];
		}
		//获取画面名称
		if((arrKeyValues[1]!=null || typeof(arrKeyValues[1])!='undefined') && (arrKeyValues[1].indexOf("=")>-1)) {
			winname = arrKeyValues[1].split("=")[1];
		}
		
		//来源为报警，执行菜单事件
		if(src=='alarm') {
			
			console.log("报警目标画面名称："+ unescape(winname));
			
			submenu.handleMenuEvent(unescape(winname));
			submenu.fixed(unescape(winname));
		}
	}
	
	/**
	 * 处理能源菜单
	 * */
	$(".auto_btn button").click(function(){
		$(".auto_btn button").removeClass("cur");
		if($(this).hasClass("energyDashbord")) { // 能源管理-仪表盘
			$(this).addClass("cur");
			$("#energyDashbord").show();
			$("#energyChart").hide();
		} else if($(this).hasClass("passengerview")) { // 客流统计-客流量视图
			$(this).addClass("cur");
            var text = $(".cataloge_class_three .current").find('a').text();
            $("#passengerBackground").attr('style','');
            $("#passengerBackground").css("background", "url('../resources/css/images/ggzm_" + text + ".jpg')"); // 修改画面的楼层背景图片
            $("#passengerBackground").removeClass("pmt");
            $("#passengerBackground").addClass("pmt"); // 修改画面的楼层背景图片
            $("#passengerView").show();
            $(".data_display").show();
            $(".custom_rank").show();
            $(".show_rank").show();
			$("#passenger_order").text('客流量排名');
			$('.storepos').remove(); // 移除平面图中的div
			getTotalPassengerFlow(); // 查询总客流
			getFloorPassengerInfo(); // 获取并创建平面图中对应的客流信息
            $(".store_name_display").text('总客流信息');
            $(".today >h3").text('今日累计客流');
            $("#showListDevice").hide();
		} else if($(this).hasClass("deviceall")) { // 客流统计-设备列表
            $(this).addClass("cur");
            $(".data_display").hide();
            $(".custom_rank").hide();
            $(".show_rank").hide();
            $("#showListDevice").show();
            $("#passengerBackground").removeClass("pmt");
            $("#passengerBackground").attr('style','');

            $("#passengerBackground .storepos").each(function(index){
                $(this).hide();
            });

            getFloorPassengerInfo();

		} else if($(this).hasClass("passengerstruction")) { // 客流统计-结构图
            $(this).addClass("cur");
            $(".data_display").hide();
            $(".custom_rank").hide();
            $(".show_rank").hide();
            $("#passengerBackground").attr('style','');
            $("#passengerBackground").css("background", "url('../resources/images/pfe_struction.png')"); // 修改画面的楼层背景图片
            $("#passengerBackground").css("no-repeat","center"); // 修改画面的楼层背景图片
            $("#passengerBackground").css("height","893px"); // 修改画面的楼层背景图片
            $("#passengerBackground").css("width","1288px"); // 修改画面的楼层背景图片
            $("#passengerBackground").css("margin-left","88px"); // 修改画面的楼层背景图片
            $("#passengerBackground").removeClass("pmt");
            $("#passengerBackground").addClass("pmt");
            $("#showListDevice").hide();
            $("#passengerBackground .storepos").each(function(index){
                $(this).hide();
            });
		} else if($(this).hasClass("inperson")) { // 客流统计-场内人数
			$(this).addClass("cur");

            var text = $(".cataloge_class_three .current").find('a').text();
            $("#passengerBackground").attr('style','');
            $("#passengerBackground").css("background", "url('../resources/css/images/ggzm_" + text + ".jpg')"); // 修改画面的楼层背景图片
            $("#passengerBackground").removeClass("pmt");
            $("#passengerBackground").addClass("pmt"); // 修改画面的楼层背景图片
            $(".data_display").show();
            $(".custom_rank").show();
            $(".show_rank").show();
			$("#passenger_order").text('场内人数排名');
			$('.storepos').remove(); // 移除平面图中的div
			getFloorPassengerInfo('inside'); // 获取并创建平面图中对应的场内人数信息
            getTotalInsiderPerson();
            $(".store_name_display").text('场内人数信息');
            $(".today >h3").text('当前场内总人数');
            $("#showListDevice").hide();
		} else if($(this).hasClass("energyChart")) { // 能源管理-能效分析
			$(this).addClass("cur");
			$("#energyDashbord").hide();
			$("#energyChart").show();
		} else {
			//$(this).addClass("cur");
		}
	});
	
	
	var fullPath = window.location.href;
	var jumpPageView = "";
	if(fullPath.indexOf("?")>0 && fullPath.indexOf("#")>0) {
		jumpPageView = fullPath.split("#")[1];
	}
		
	//defaultView为页面全局变量，记录当前页面的默认视图;默认视图不为空的情况下自动定义到默认视图
	if(defaultView!="") {
		if(jumpPageView!="")
			defaultView = jumpPageView;
		$('#pagetabs a[href="#'+ defaultView +'"]').tab('show');
	}
	
	//展开子菜单
	$(".cataloge_class_two>li:not('.cataloge_class_three')").click(function () {
        $(".cataloge_class_two > li").removeClass("current");
        $(this).addClass("current");
        if($(this).next(".cataloge_class_three").css("display") == "list-item"){
            $(this).next().css({"display" : "none"});
        } else {
            if ($(this).next().attr("class") == "cataloge_class_three") {
                $(".cataloge_class_three").hide();
                $(this).siblings().removeClass("current");
                $(this).next().show();
                $(this).next().find("li").click(function () {
                    $(this).addClass("current");
                    $(this).siblings().removeClass("current");
                });
            } else {
                $(this).siblings().removeClass("current");
                $(".cataloge_class_three").hide();
                $(this).addClass("current");
            }
        }
    });
	
});

var showMode = true;
//子菜单操作
var submenu = {
	toolbar : function() {
		
		$(".cataloge_class_three li").click(function(){
			$(".cataloge_class_two li").removeClass("current");
			$(".cataloge_class_three li").removeClass("current");
			$(this).addClass('current');
		});

		var flag = true;
		
		
		$(".cataloge_class_two > li").each(function(){
			
			$(this).click(function(){
				
				showMode = true;
				
				if(typeof(thisid)!='undefined') {
					if(showMode) {
						$(".mode_switch").show();
                        changePattern();
					} else {
						$(".mode_switch").hide();
					}
				}
			});

		});
		
		$(".cataloge_class_three > ul > li").each(function(){
			
			var thisid = $.trim($(this).attr("id"));
			$(this).click(function(){
				showMode = true;
				
				if(typeof(thisid)!='undefined') {
					if(showMode) {
						$(".mode_switch").show();
					} else {
						$(".mode_switch").hide();
					}
				}
				
				
			});

			
		});		
	},
	defClick : function() {
		/*
		//如果没有选择菜单，默认为第一个
		var activeButton = $(".cataloge_class_two").find('.current');
		var oneLevel="",curSelText="";
		
		console.log("activeButton length:"+ activeButton.length);
		
		//console.log($(activeButton));
		
		//如果二级菜单下没有选择项目，默认选择第一个链接
		if(activeButton.length==0) {
			$(".cataloge_class_two .cataloge_class_three:first").show();
			var firstli = $(".cataloge_class_two .cataloge_class_three:first li:first");
			$(firstli).addClass('current');

			//链接地址 @todo 此处如何二级菜单有两个，一个只有设备列表，一个有设备列表和平面图，则默认是只有设备列表的那个。非正常需要的默认
			curSelHref = $(".mode_cataloge .current a").attr("href");
            console.log("curSelHref-----:"+ curSelHref);
			//跳转到链接
			window.location.href = curSelHref;

			
			return false;
		} else {
			
			var hasHref = false;
			$(".cataloge_class_two a").each(function(index,link){
				var href = $(link).attr("href");
				$(link).parent().removeClass('current');
				if(window.location.pathname==href) {
					var cataloge_class_three;
					if($(link).parent().parent().parent().hasClass("cataloge_class_three")) {
						cataloge_class_three = $(link).parent().parent().parent();
						$(cataloge_class_three).show();
					} 
					hasHref = true;
					$(link).parent().addClass('current');
				}
				
			});
			//如果没有三级菜单且请求地址不等于默认的第一个链接地址，则按以下方式处理。
			//如暖通空调模块默认请求地址“/portal/mctrl/HVAC.html”和 实际处理地址 "/portal/mctrl/HVAC/144.html"
			if(!hasHref) {
				$(".cataloge_class_two>li:not('.cataloge_class_three')").each(function (index,link) {
					if ($(this).next().attr("class") == "cataloge_class_three") {
		
					} else {
						var href = $(this).find("a").attr("href");
						console.log("href2:"+ href);
						if(index==0 && window.location.pathname!=href) {
							window.location.href = href;
						}
					}
				});
			}

			
        }
        */
	},
	
	//定位菜单,text = 画面名称
	fixed : function(text) {
		
		//特殊处理下划线问题
		//根所画面名称定位菜单 ，如冷源或组合式空调机组B1_结构图 
		var winname = submenu.getWinName(text);
		
		    //移除样式
			$(".cataloge_class_two li").removeClass("current");
			//
			$(".cataloge_class_two>li:not('.cataloge_class_three')").each(function () {
				if ($(this).next().attr("class") == "cataloge_class_three") {
					//var submenuName = $(this).next().find("a").attr("title");
					$(this).show();
					$(this).next().find("a").each(function(){
						var title = submenu.getWinName($(this).attr('title'));
						if(title==winname) {
							console.log("设置联动报警画面,标题："+ title + "名称："+ winname);
							$(this).parent().addClass('current');
						}
					});
				} else {
					submenuName = submenu.getWinName($(this).text());
					if(submenuName==winname) {
						$(this).addClass('current');
					}
				}
			});
	},getWinName : function(text) {
		if(text.indexOf("_")>-1) {
			text = text.split("_")[0];
		}
		return text;
	}
	
}; 



