$(function(){
	$(".state_search").toggle(function(){
		$(".disshow").siblings().show();
	},function(){
		$(".disshow").siblings().hide();
	});

	$(".user_table>tbody>tr:gt(0)").each(function(index){
		$(this).click(function(){
			$(this).siblings().removeClass("highLight");
			$(this).addClass("highLight");
			console.log(index);
		});
	});
	$(".cancel").click(function(){
		$("#dialog-modal").dialog("close");
		$("#dialog-modal1").dialog("close");
		$("#dialog-modal2").dialog("close");
		$("#dialog-modal3").dialog("close");
	});

    $(".dialog-popover").draggable();





    // $(".top_animate").toggle(function(){
	// 	$("#reikenli").height(0);
	// 	$("#reikenli1").height(0);
	// 	$("#reikenli").animate({
	// 		height : "150px"
	// 	},function(){
	// 		$("#reikenli1").animate({
	// 			height : "150px"
	// 		})
	// 	});
	// })

	// $(".top_animate").toggle(function(){
	// 	$("#reikenli").height(0);
	// 	$("#reikenli1").height(0);
	// 	$("#reikenli").animate({
	// 		height: "150px"
	// 	});
	// },function(){
	// 	$("#reikenli").height(0);
	// 	$("#reikenli1").height(0);
	// 	$("#reikenli1").animate({
	// 		height: "150px"
	// 	});
	// })
	// $(".top_animate").click(function(){
	// 	var s = $(".swap_module").length;
	// 	console.log(s);

	// })
	// var $domOk = $("<img>", {
	// 	src : "./images/weather.png",
	// 	class : "okicon"
	// });

	// $domOk.wrap($(".permission"));
//	$(".swap_module>div").each(function(index){
//		$(this).click(function(){
//			$(this).siblings().find(".choose").hide();
//			$(this).siblings().find(".job").removeClass("highLight2");
//			$(this).find(".job").addClass("highLight2");
//			$(this).find(".choose").show();
//			$(".permission_detail").show();
//		});
//	});

	$(".okicon").each(function(){
		$(this).toggle(function(){
			$(this).addClass("press");
		},function(){
			$(this).removeClass("press");
		});
	});

	$(".okicon_2").each(function(){
		$(this).toggle(function(){
			$(this).addClass("press2");
		},function(){
			$(this).removeClass("press2");
		});
	});

	$(".select_table>table>tbody>tr:odd").addClass("changeColor")

	$(".permission_select>div").each(function(){
		$(this).toggle(function(){
			$(this).find("span").show();
			$(this).addClass("changeBg");

		},function(){
			$(this).find("span").hide();
			$(this).removeClass("changeBg");
		})
	})
	
	$(".dialog_content3 li").live("click", function(){
		$(".dialog_content3 li").removeClass("bgjs");
		$(this).addClass("bgjs");
	});

	$(".move_left").live("click", function(){
		if ($(".bgjs").parent().parent().attr("class") == "left_user") {
			return false;
		} else {
			$(".left_user").append($(".bgjs"));
		}
	});

	$(".move_right").live("click", function(){
		if ($(".bgjs").parent().parent().attr("class") == "right_user") {
			return false;
		} else {
			$(".right_user").append($(".bgjs"));
		}
	})
});