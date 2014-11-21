$(function(){
	$(".js_lowC_nav>li").each(function(index){
		$(this).on("click", function(){
			var thisClass = $(this).attr("class");
			thisClass = thisClass.split(" ");
			$(this).siblings().each(function(){
				var otherClass = $(this).attr("class");
				otherClass = otherClass.split(" ");
				$(this).removeClass(otherClass[1]);
			})
			changeBtnColor($(this), thisClass[0]);
			$(".js_lowC_content").removeClass("show");
			$($(".js_lowC_content")[index]).addClass("show");
		});
	});
});
function changeBtnColor($thisdom,thisclass){
	switch(thisclass) {
		case "trafficDiv" :
			if ($thisdom.hasClass("js_lowC_nav_blue")) {
				return false;
			} else {
				$thisdom.addClass("js_lowC_nav_blue");
			}
			break;
		case "buildDiv" :
			if ($thisdom.hasClass("js_lowC_nav_blue")) {
				return false;
			} else {
				$thisdom.addClass("js_lowC_nav_blue");
			}
			break;
		default :
			return false;
	}
}