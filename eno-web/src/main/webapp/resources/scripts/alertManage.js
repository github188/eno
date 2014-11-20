$(function(){
	$(".selectpicker").selectpicker();
	$(".btn").addClass("reiken");

	$(".alert_list>tbody>tr:odd:not(0)").addClass("changeColor");

	$(".popover_nav>ul>li").each(function(index){
		$(this).click(function(){
			$(this).siblings().removeClass("cur");
			$(this).addClass("cur")
			$($(".alert_tab>div")[index]).siblings().hide();
			$($(".alert_tab>div")[index]).show();
		});
	});

	




// 	var imgList = ["public/images/file1.png", "public/images/file2.png"];
// 	var $imgDoms = [];
// 	for (var i = 0; i < imgList.length; i++) {
// 		$imgDoms[i] = $("<img >", {
// 			src : imgList[i]
// 		});
// 	};
// 	try{
//    //标准的创建方法,如果在IE中可能会抛出异常
//    $(".alert_list").after($imgDoms[1]);
// }catch(e){
//   //IE中创建方式
//   return false;
// }
		
	

	// // $(".btn_style2>p:gt(0)").;
})