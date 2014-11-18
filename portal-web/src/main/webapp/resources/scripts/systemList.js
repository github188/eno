$(function(){
	$(".navigation>li:gt(0):lt(10)").addClass("nav_bar");

	$(".reikenli").each(function(index){
		$(this).click(function(){
			if ($($(".sub_ul")[index]).css("display") == "none"){
				$(".sub_ul").hide();
				$($(".sub_ul")[index]).show();
				$(".reikenli>img").hide();
				$(".reikenli").find("img:eq(1)").show();
				$(this).find("img").hide();
				$(this).find("img:eq(0)").show();
			} else {
				$($(".sub_ul")[index]).hide();
				$(this).find("img").hide();
				$(this).find("img:eq(1)").show();
			}
		});
	});

	for(var i= 0; i< $(".sub_li").length; ++i){
		$($(".sub_li")[i]).attr({"systemId" : i});
	}
    
	
	$(".sub_li").each(function(){
		$(this).click(function(){
			var n = $(this).attr("systemId");
		});
	});

	$(".cataloge_class_two>li:not('.cataloge_class_three')").click(function(){
		$(".cataloge_class_two>li:not('.cataloge_class_three')").removeClass("current");
		if ($(this).next().attr("class") == "cataloge_class_three") {
			$(".cataloge_class_three").hide();
			$(this).siblings().removeClass("current");
			$(this).next().show();
			$(this).next().find("li").click(function(){
				$(this).addClass("current");
				$(this).siblings().removeClass("current");
			});
		} else {
			$(this).siblings().removeClass("current");
			$(".cataloge_class_three").hide();
			$(this).addClass("current");
		}
	});

	$(".Btn-big").click(function(){
		$(this).siblings().removeClass("cur");
		$(this).addClass("cur");
	})

	$(".sub_li").click(function(){
		$(".sub_li").removeClass("changeBg");
		$(this).addClass("changeBg")
	});

	$(".device_tab>ul>li").click(function(){
		$(this).siblings().removeClass("cur_device");
		$(this).addClass("cur_device");
	});

	$(".mode_switch>ul>li").click(function(){
		$(this).siblings().removeClass("current_mode");
		$(this).addClass("current_mode");
	});

	$(".auto_switch").click(function(){
		if($(this).text() == "手动模式") {
			$(this).text("自动模式");
		} else {
			$(this).text("手动模式");
		}
	});

	$(".device_list").click(function(){
		$(".mask").show();
	})

	$(".close").click(function(){
		$(".mask").hide();
	})

	$(".return_first").click(function(){
		$(".cataloge_class_three").find("li").removeClass("current");
		$(".cataloge_class_three").find("li:first").addClass("current");
	})
});