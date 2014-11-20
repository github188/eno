$(function(){
	var dateNow = new Date();
	var dateMounth = dateNow.getMonth();
	var dateYear = dateNow.getFullYear();
	var length = $(".table_title>th").length-3;
	var thisDom;
	var text;
	var tddom = $(".table_frame>table>tbody>tr").find("td:gt(1):lt("+ length+")");
	var tdLentgh = $(".table_frame>table>tbody>tr").find("td:gt(1):lt("+ length+")").length;
	for (var i = 0; i < tdLentgh; i++) {
		var tdDomText = $(tddom[i]).text();
		if (tdDomText == "/") {
			continue;
		} else {
			$(tddom[i]).addClass("highLight");
		}
	};

	$(".table_frame>table>tbody>tr:odd").addClass("changeColor");
	$(".edit").toggle(function(){
		$(this).text("关闭编辑");
		openEdit();
	}, function(){
		$(this).text("开启编辑");
		$(".popSelect").hide();
		$(".table_frame>table>tbody>tr").find("td:gt(1):lt("+ length+")").unbind("click");
	});
	
	// $(".edit").click(function(){
	// 	++count;
	// 	if (count%2 == 0) {
			
	// 	} else {
	// 		openEdit();
	// 	}
	// })

	$(".left-icon").click(function(){
		var now = $(".nowYear").text();
		var nowValue = parseInt(now);
		nowValue -= 1;
		$(".nowYear").text(nowValue);
	});

	$(".right-icon").click(function(){
		var now = $(".nowYear").text();
		var nowValue = parseInt(now);
		nowValue += 1;
		$(".nowYear").text(nowValue);
	});

	$(".select_bar>ul>li").click(function(){
		$(this).siblings().removeClass("btn-small");
		$(this).addClass("btn-small");
	});

	$(".thisMonth").click(function(){
		$(".select_bar>ul>li").removeClass("btn-small");
		$($(".select_bar>ul>li")[dateMounth]).addClass("btn-small");
		$(".nowYear").text(dateYear);
	})
//
//	$(".table_style1>tbody>tr:not('th')").click(function(){
//		$(".right_over").addClass("showNow");
//	});

//	$(".submit").click(function(){
//		$(".right_over").removeClass("showNow");
//	})

});

function openEdit(){
	var length = $(".table_title>th").length-3;
	$(".table_frame>table>tbody>tr").find("td:gt(1):lt("+ length+")").click(function(){
		var xpoint = event.x+10;
		var ypoint = event.y+10;
		thisDom = "";
		thisDom = $(this);
		$(".popSelect").css({"left":""+xpoint+"px","top": ""+ypoint+"px"}).show();
		$(".popSelect>p").click(function(){
			$(this).parent().hide();
		});
		$(".popSelect>div").each(function(){
			$(this).removeClass("selected");
			$(this).click(function(){
				text = $(this).text();
				if (text == "/") {
					$(thisDom).removeClass("highLight");
				} else {
					$(thisDom).addClass("highLight");
				}
				$(this).siblings().removeClass("selected");
				$(this).addClass("selected");
				$(this).parent().hide();
				thisDom.text(text);
				text = "";
			});
		});
	});
}