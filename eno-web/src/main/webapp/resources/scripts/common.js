$(function(){

    //用户管理下拉
    $(".login_user").on("click", function() {
        if($(this).hasClass("showDown")) {
            $(this).removeClass("showDown").addClass("hideUp");
        } else {
            $(this).removeClass("hideUp").addClass("showDown");
        }
        $(".login_user_box").slideToggle("slow");
    });

	//二级菜单切换
	$(".js_subnav ul li").click(function(){
		$(this).siblings().removeClass("js_subnav_current");
		$(this).addClass("js_subnav_current");
		$(".js_tab_content").hide().eq($(".js_subnav ul li").index(this)).show();
	});

    //能源管理一宫格、六宫格、九宫格切换
    $(".js_Echart_nav li").click(function(){
        $(this).siblings().removeClass("js_Echart_nav_current");
        $(this).addClass("js_Echart_nav_current");
        $(this).parent().siblings(".js_Echart_content").hide();
        $(this).parent().parent().find(".js_Echart_content:eq("+ $(this).index() +")").show();
    });
	
	$(".Echart_sidebar_subcon>ul>li").click(function(){
		$(this).siblings().removeClass("selecton");
        $(this).addClass("selecton");
	});
    //设备管理预防性维护 左侧菜单切换
    $(".device_sidebar_subcon ul li").click(function(){
        $(this).siblings().removeClass("selecton");
        $(this).addClass("selecton");
        $(".device_main").hide().eq($(".device_sidebar_subcon ul li").index(this)).show();
    });
    //下拉列表
    $('.selectpicker').selectpicker({
        "width" : "130px"
    });
    $('.selectpicker_w150').selectpicker({
        "width" : "150px"
    });
    $('.selectpicker_w197').selectpicker({
        "width" : "197px"
    });
    $('.selectpicker_w218').selectpicker({
        "width" : "218px"
    });
    //运行监测二级页面左侧菜单
    $(".main_menu>ul>li").on("click", function(){
        $(this).siblings().removeClass("main_menu_current");
        $(this).addClass("main_menu_current");
    });

    $(".second_menu li").click(function(){
        var subCon=$(this);
        if(subCon.hasClass('on')){
            subCon.find('.subcon').slideUp('fast',function(){
                subCon.removeClass('on');
            });
        }else{
            subCon.find('.subcon').slideDown('fast',function(){
                subCon.addClass('on');
            });
            subCon.siblings().removeClass('on').find('.subcon').slideUp('fast');
        }
    });
    $(".second_menu>ul>li").on("click", function(){
        $(this).siblings().removeClass("on");
        $(this).addClass("on");
    });

    // li标签下增加点击事件
    $(".second_menu a").click(function(){
        var subCon=$(this).addClass("focus");
        $(this).siblings().removeClass('focus');
        return false;
    });
    //暖通空调结构图点击弹框事件
    $(".tooltipBox:has('.tooltip_showBox')").click(function(){
        $(this).children(".tooltip_showBox").stop(true,true).show();
    });
    $(".tooltip_closeBtn").click(function(){
        $(".tooltip_showBox").hide();
        return false;
    });
    //日周月年视图的点击事件
    $(".viewList>li:not(.compare)").click(function(){
        $(this).siblings().removeClass("changeColor");
        $(this).addClass("changeColor");

        var dtype = $(this).attr("dtype"); // 获取li标签的dtype属性
        var format = "yyyy-MM-dd";
        if (dtype == "day") {
            format = "yyyy-MM-dd";  //日视图日历格式为yyyy-MM-dd显示
        } else if (dtype == "week") {
            format = "yyyy-MM-dd";  //周视图日历格式为yyyy-MM-ww显示
        } else if (dtype == "month") {
            format = "yyyy-MM";     //年视图日历格式为yyyy显示
        } else if (dtype == "year") {
            format = "yyyy";
        } else if (dtype == "all") {
            format = "yyyy-MM-dd";
        }
//        console.log(format);
        chooseTime(format);
    });
    $(".viewList_language li").click(function(){
        $(this).siblings().removeClass("changeColor");
        $(this).addClass("changeColor");
    });
//    $(".yminput").attr("readOnly",true);

    //设备管理点击编辑/删除前边出现编辑/删除按钮
    $(".btn_edit").on("click", function() {
        if($(this).hasClass("btn_green")) {
            $(this).removeClass("btn_green").addClass("btn_grey");
            $(".tableList .edit").hide();
            $(".tableList .delete").hide();
        } else {
            $(this).removeClass("btn_grey").addClass("btn_green");
            $(this).siblings(".btn_delete").removeClass("btn_green").addClass("btn_grey");
            $(".tableList .edit").show();
            $(".tableList .delete").hide();
        }
    });
    $(".btn_delete").on("click", function() {
        if($(this).hasClass("btn_green")) {
            $(this).removeClass("btn_green").addClass("btn_grey");
            $(".tableList .delete").hide();
            $(".tableList .edit").hide();
        } else {
            $(this).removeClass("btn_grey").addClass("btn_green");
            $(this).siblings(".btn_edit").removeClass("btn_green").addClass("btn_grey");
            $(".tableList .delete").show();
            $(".tableList .edit").hide();
        }
    });

    //运行监测二级页面三种视图切换
    $(".js_system_viewBar>ul>li").click(function(){
        $(this).siblings().removeClass("js_system_viewBar_on");
        $(this).addClass("js_system_viewBar_on");
        $(".js_system_content").hide().eq($(".js_system_viewBar>ul>li").index(this)).show();
    });

	//报表内容展开收起

    $(".triangle_show").on("click", function() {
        if($(this).hasClass("triangle_hide")) {
            $(this).removeClass("triangle_hide").addClass("triangle_show");
            $(".tableBox table").css("height","600px");
            $(".tableBox .reporF_noCon").css("height","440px");
        } else {
            $(this).removeClass("triangle_show").addClass("triangle_hide");
            $(".tableBox table").css("height","726px");
            $(".tableBox .reporF_noCon").css("height","566px");
        }
        $(".reportForm_category").slideToggle("slow");
    });

    //定额管理的配置定额 历史数据展开收起
    $(".export_showBtn").on("click", function() {
        if($(this).hasClass("export_hideBtn")) {
            $(this).removeClass("export_hideBtn").addClass("export_showBtn");
             //console.log("has");
        } else {
             //console.log("nohas");
            $(this).removeClass("export_showBtn").addClass("export_hideBtn");
        }
        $(".history_showhide").slideToggle("slow");
        //console.log(event);
    });
});

// 选择时间
function chooseTime(format) {
    WdatePicker({
        el : 'hidden_date',	//只操作这个日历控件
        dateFmt : format, //定义时间的格式
        onpicked : function(dp) {
            // 具体的对比时间，格式为（yyyy-MM-dd），此时间会传递到后台
            var paretime= dp.cal.getDateStr(format);
        }
    });
}


	
