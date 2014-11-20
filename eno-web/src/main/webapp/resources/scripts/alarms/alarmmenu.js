/*
 * 子菜单相关操作
 * 
 * @author CHENPING
 * @version 1.0 build 20131014
 * */
$(function(){
	
	/**
	 * 左侧菜单
	 * */
	$("#accordion > li > div").click(function(){
		
		 $("#accordion > li > div").find("img").hide();
		 $("#accordion > li > div").find("img:eq(0)").show();
		 $("#accordion > li > div").find("img:eq(1)").hide();
		
	    if(false == $(this).next().is(':visible')) {
	    	
	        $('#accordion ul').slideUp(300);
			$(this).find("img:eq(1)").show();
			$(this).find("img:eq(0)").hide();
	    } 
	    $(this).next().slideToggle(300);
	});
	
	
	//页面菜单默认选项项
	var reqPath = window.location.pathname ;
	var arrPath = reqPath.split('/');
	var currPath = arrPath[arrPath.length-1].replace(".html","");
	if(currPath=="") {
		$('#accordion ul:eq(0)').show();
	} else {
		 $("#"+ currPath).parent().show();
		
		 $("#accordion > li > div").find("img").hide();
		 $("#accordion > li > div").find("img:eq(0)").show();
		 $("#accordion > li > div").find("img:eq(1)").hide();
		 $("#"+ currPath).parent().parent().find("img:eq(1)").show();
		 $("#"+ currPath).parent().parent().find("img:eq(0)").hide();
	}

}); 