$(function(){
	$(".navigation>li:gt(0):lt(10)").addClass("nav_bar");

	$(".root_li:not('.root_li:first')").each(function(index){
		$(this).click(function(){
			if ($($(".sub_ul")[index]).css("display") == "none"){
				$(".sub_ul").hide();
				$($(".sub_ul")[index]).show();
				$(".root_li>img").hide();
				$(".root_li").find("img:eq(1)").show();
				$(this).find("img").hide();
				$(this).find("img:eq(0)").show();
			} else {
				$($(".sub_ul")[index]).hide();
				$(this).find("img").hide();
				$(this).find("img:eq(1)").show();
			}
		});
	});

	// for(var i= 0; i< $(".sub_ul li").length; ++i){
	// 	$($(".sub_li li")[i]).attr({"systemId" : i});
	// }
	
	// $(".sub_li li").each(function(){
	// 	$(this).click(function(){
	// 		var n = $(this).attr("systemId");
	// 	});
	// });

	$(".cataloge_class_two>li:not('.cataloge_class_three')").click(function(){
		var curdom = $(".current");
		curdom.removeClass("current");
		$(this).addClass("current");
	});

	$(".sub_ul li").click(function(){
		$(".sub_ul li").removeClass("changeBg");
		$(this).addClass("changeBg")
	});

	
	
	$(".dialog-close, .close_btn, .qd_btn").on("click", function(){
		$("#mask,.shop").hide();
		$(".client_para span, .client_para sup").show();
		$(".client_para input").hide();
		$(".shop-close, .shop-open, .normal_info").hide();
		$(".shop-state").val("");
	});
	
    $(".logo").on("click", function(){
//    	$("#global_mask").show();
    	$(".normal_info, .normal_info_content").show();
    	$(".normal_info .dialog-title").text("基本属性");
    });

    $(".normal_info .dialog-close, .normal_info .qd_btn, .normal_info .close_btn").on("click", function(){
//        $("#global_mask").hide();
        $(".normal_info").hide();
    });


    $(".edit_info_btn").on("click", function(){
        $(".client_para input").show();
        $(".client_para span,.client_para sup").hide();
        $(".client_para input").each(function(){
            var value = $(this).parent().find("span").text();
            $(this).val(value);
        })
    });

    $(".preview_btn").on("click", function(){
        $(".client_para input").hide();
        $(".client_para span,.client_para sup").show();
        $(".client_para span").each(function(){
            var value = $(this).parent().find("input").val();
            $(this).text(value);
        });
    });
	
	
	
});

var textInfo1 = "确定要切换模式?",
	textInfo2 = "确定切换到手动模式?",
	textInfo3 = "确定切换到自动模式？";

