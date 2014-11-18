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


    //显示设备列表
    $("#btn_asc").click(function(){
        var text = submenu.nodetext();
        if(typeof(text)!='undefined' && text.length>0) {
//            console.log(text);
//            showwindow(text);
        }
    });

    ///显示系统结构图
    $("#btn_asccompn").click(function(){
        var text = submenu.nodetext();
        submenu.showAsccompn(text);
    });

    //显示系统平面图
    $("#btn_ascplanar").click(function(){
        var text = submenu.nodetext();
        submenu.showascplanar(text);
    });


    submenu.handleSpecialMenu();
    submenu.defClick();
    submenu.menuEvent();
    submenu.toolbar();

    //showWindowAndMenu('信息发布','大屏控制_平面图');

    //http://192.168.1.254:8080/portal/mctrl/HVAC.html?src=alarm&winname=%u9632%u76D7%u62A5%u8B66B1_%u5E73%u9762%u56FE
    var reqFullPath = window.location.href;
    var paramters = "",arrKeyValues = "",src="",winname="";
    if(reqFullPath.indexOf('?')>-1) {
        paramters = reqFullPath.split('?')[1];

        if(paramters.indexOf("&")>-1) {
            arrKeyValues = paramters.split("&");
        }
        if((arrKeyValues[0]!=null || typeof(arrKeyValues[0])!='undefined')&&(arrKeyValues[0].indexOf("=")>-1)) {
            src = arrKeyValues[0].split("=")[1];
        }
        if((arrKeyValues[1]!=null || typeof(arrKeyValues[1])!='undefined') && (arrKeyValues[1].indexOf("=")>-1)) {
            winname = arrKeyValues[1].split("=")[1];
        }

        if(src=='alarm') {
            submenu.handleMenuEvent(unescape(winname));
            submenu.fixed(unescape(winname));
        }
    }

    $(".auto_btn button").click(function(){
        $(".auto_btn button").removeClass("cur");
        if($(this).hasClass("energyDashbord")) {
            $(this).addClass("cur");
            $("#energyDashbord").show();
            $("#energyChart").hide();
        } else {
            $(this).addClass("cur");
            $("#energyDashbord").hide();
            $("#energyChart").show();
        }
    });

});


//子菜单操作
var submenu = {
    nodetext : function() {
        var current = $(".current").find('a');
        return $(current).attr('title');
    },
    handleSpecialMenu : function() {

        //菜单处理，添加全局禁用工具栏按钮
        var parentMenuText = $(".left_menu .cur a").text();
        if(parentMenuText=='能源管理') {
            //$(".auto_btn .Btn-big").addClass("system_plan");
            $(".auto_btn .Btn-big").attr('disabled',false);

            //$("#btn_asccompn").text("统计仪表盘");
            //$("#btn_ascplanar").text("能耗分析");

        } else if (parentMenuText=='公共照明') {
            $(".auto_btn .Btn-big").removeClass("system_plan");
            $(".auto_btn .Btn-big").attr('disabled',false);

            $("#btn_asccompn").addClass("system_plan");
            $("#btn_asccompn").attr('disabled',true);
        } else if (parentMenuText=='消防系统' ||  parentMenuText=='防盗报警' ||  parentMenuText=='视频监控' ||  parentMenuText=='门禁管理' ) {

            $(".auto_btn .Btn-big").addClass("system_plan");
            $(".auto_btn .Btn-big").attr('disabled',true);

            $("#btn_ascplanar").removeClass("system_plan");
            $("#btn_ascplanar").attr('disabled',false);
        }else if (parentMenuText=='电子巡更') {
            $(".auto_btn .Btn-big").addClass("system_plan");
            $(".auto_btn .Btn-big").attr('disabled',true);

            $("#btn_asc").removeClass("system_plan");
            $("#btn_asc").attr('disabled',false);
        } else if (parentMenuText=='电梯监视') {
            $(".auto_btn .Btn-big").removeClass("system_plan");
            $(".auto_btn .Btn-big").attr('disabled',false);

            $("#btn_asccompn").addClass("system_plan");
            $("#btn_asccompn").attr('disabled',true);
        } else if (parentMenuText=='客流统计' || parentMenuText=='信息发布' || parentMenuText=='背景音乐' || parentMenuText=='停车管理' || parentMenuText=='室内环境') {
            $(".auto_btn .Btn-big").addClass("system_plan");
            $(".auto_btn .Btn-big").attr('disabled',true);

            $("#btn_ascplanar").removeClass("system_plan");
            $("#btn_ascplanar").attr('disabled',false);
        }

    },
    toolbar : function() {

        $(".cataloge_class_three li").click(function(){
            $(".cataloge_class_three li").removeClass("current");
            $(this).addClass('current');
        });

        var flag = true;

        $(".cataloge_class_two > li").each(function(){

            $(this).click(function(){

                var thisid = $(this).attr("id");

                if(thisid=='CoolingSource') {  //冷源
                    $("#btn_ascplanar").addClass('system_plan');
                    $("#btn_ascplanar").attr('disabled',true);
                } else if(thisid=='SHADING' || thisid=='WIREBELT') {  //风幕\遮阳
                    $(".auto_btn .Btn-big").addClass("system_plan");
                    $(".auto_btn .Btn-big").attr('disabled',true);

                    $("#btn_ascplanar").removeClass("system_plan");
                    $("#btn_ascplanar").attr('disabled',false);

                } else if (thisid=='WS') { //给水
                    $(".auto_btn .Btn-big").addClass("system_plan");
                    $(".auto_btn .Btn-big").attr('disabled',true);
                    $("#btn_asc").removeClass("system_plan");
                    $("#btn_asc").attr('disabled',false);
                } else if (thisid=='WD') { //排水
                    $(".auto_btn .Btn-big").removeClass("system_plan");
                    $(".auto_btn .Btn-big").attr('disabled',false);
                    $("#btn_asccompn").addClass("system_plan");
                    $("#btn_asccompn").attr('disabled',true);
                } else if(thisid=='UP' || thisid=='IPS') {  //地下停车场\室内步行街
                    $(".auto_btn .Btn-big").removeClass("system_plan");
                    $(".auto_btn .Btn-big").attr('disabled',false);

                    $("#btn_asccompn").addClass("system_plan");
                    $("#btn_asccompn").attr('disabled',true);

                } else {
                    if(flag) {
                        $(".auto_btn .Btn-big").removeClass('system_plan');
                        $(".auto_btn .Btn-big").attr('disabled',false);
                    }
                }

                submenu.handleSpecialMenu();
            });

        });

        $(".cataloge_class_three > ul > li").each(function(){

            var thisid = $.trim($(this).attr("id"));
            $(this).click(function(){

                console.log("当前ID："+ thisid);
                console.log("Index:"+ thisid.indexOf('风幕'));

                if (thisid.indexOf('遮阳帘')>-1 || thisid.indexOf('风幕')>-1) {
                    $(".auto_btn .Btn-big").addClass("system_plan");
                    $(".auto_btn .Btn-big").attr('disabled',true);

                    $("#btn_ascplanar").removeClass("system_plan");
                    $("#btn_ascplanar").attr('disabled',false);
                    flag = false;
                }
                else if (thisid.indexOf('LVIOL')>-1) {  //变配电
                    $(".auto_btn .Btn-big").addClass("system_plan");
                    $(".auto_btn .Btn-big").attr('disabled',true);

                    $("#btn_asccompn").removeClass("system_plan");
                    $("#btn_asccompn").attr('disabled',false);

                    flag = false;
                } else if (thisid.indexOf('排水系统')>-1) {
                    $(".auto_btn .Btn-big").removeClass("system_plan");
                    $(".auto_btn .Btn-big").attr('disabled',false);
                    $("#btn_asccompn").addClass("system_plan");
                    $("#btn_asccompn").attr('disabled',true);

                    flag = false;
                } else if (thisid.indexOf('地下停车场')>-1 || thisid.indexOf('室内步行街')>-1) {
                    $(".auto_btn .Btn-big").removeClass("system_plan");
                    $(".auto_btn .Btn-big").attr('disabled',false);

                    $("#btn_asccompn").addClass("system_plan");
                    $("#btn_asccompn").attr('disabled',true);

                    flag = false;
                } else {
                    //$(".auto_btn .Btn-big").removeClass('system_plan');
                    //$(".auto_btn .Btn-big").attr('disabled',false);
                    flag = true;
                }

                submenu.handleSpecialMenu();

            });


        });
    },
    transition: function() {
        //链接切换
        var activeButton = $(".auto_btn .cur").text();
        console.log("当前应用:"+activeButton);
    },
    defClick : function() {

        //如果没有选择菜单，默认为第一个
        var activeButton = $(".cataloge_class_two").find('.current');
        var oneLevel="",curSelText="";
        //console.log($(activeButton));

        if(activeButton.length==0) {
            $(".cataloge_class_two .cataloge_class_three:first").show();
            var firstli = $(".cataloge_class_two .cataloge_class_three:first li:first");
            //设置样式
            $(firstli).addClass('current');
            var text = $(firstli).find('a').attr("title");
            console.log("默认窗口:"+$.trim(text));

            oneLevel = $(".left_menu .cur a").text();
            curSelText = $(".mode_cataloge .current a").attr("title");
            if(curSelText=='' || typeof(curSelText)=='undefined') {
                curSelText = $(".mode_cataloge .current").attr("title");
            }

            console.log("一级菜单名称："+ oneLevel + ",选择菜单："+ curSelText);

            if(oneLevel=='公共照明' || oneLevel=='夜景照明' || oneLevel=='电梯监视') {
                $("#btn_ascplanar").addClass("cur");
                submenu.showascplanar($.trim(curSelText));
            } else if(oneLevel=='暖通空调') {
                $("#btn_asccompn").addClass("cur");
                submenu.showAsccompn($.trim(curSelText));
            }else {
                //发送窗口命令
//                showwindow($.trim(text));
            }
            return false;
        } else {
            oneLevel = $(".left_menu .cur a").text();
            curSelText = $(".mode_cataloge .current a").attr("title");
            if(oneLevel=='公共照明' || oneLevel=='夜景照明' || oneLevel=='电梯监视') {
                $("#btn_ascplanar").addClass("cur");
                submenu.showascplanar($.trim(curSelText));
            } else if(oneLevel=='暖通空调') {
                $("#btn_asccompn").addClass("cur");
                submenu.showAsccompn($.trim(curSelText));
            }
        }
    },
    menuEvent:function() {
        $(".cataloge_class_two>li:not('.cataloge_class_three')").click(function () {
            var text = $(this).find('a').attr('title');
            console.log("当前二级菜单选择：" + text);
            if(typeof(text)!='undefined') {
                submenu.handleMenuEvent(text);
                return;
            }
        });


        //菜单事件，点击第三级菜单时发送显示窗口画面
        $(".cataloge_class_three li").click(function(){
            var text = $(this).find('a').attr('title');
            console.log("当前选择：" + text);
            submenu.handleMenuEvent(text);
        });
    },
    handleMenuEvent : function(text) {
        var toolbarId = $(".auto_btn .cur").attr("id");
        //console.log(toolbarId);
        //获取设备列表、结构图、平面图相对应的画面，并显示
        if(typeof(toolbarId)=='undefined' || toolbarId==null || toolbarId=='') {
//            showwindow($.trim(text));
        } else {
            var btnType = toolbarId.replace('btn_','');
            if(btnType=='asccompn') {
                submenu.showAsccompn(text);
            } else if (btnType=='ascplanar') {
                submenu.showascplanar(text);
            } else {
//                showwindow($.trim(text));
            }
        }
    },
    showAsccompn : function(text) {
        //显示结构图窗口画面，判断画面名称，除去重复的的名称
        if(typeof(text)!='undefined' && text.length>0) {
            if(text.indexOf('结构图')>-1) {
                console.log(text);
//                showwindow($.trim(text));
            } else {
//                console.log(text+"_结构图");
//                showwindow($.trim(text)+"_结构图");
            }
        }
    },
    showascplanar : function(text) {
        //显示平面图窗口画面,判断画面名称,除去重复的名称
        if(typeof(text)!='undefined' && text.length>0) {
            if(text.indexOf('平面图')>-1){
                console.log(text);
//                showwindow($.trim(text));
            } else if (text.indexOf('_结构图')) {
                text = text.replace('_结构图','');
                console.log(text+'_平面图');
//                showwindow($.trim(text)+'_平面图');
            } else
            {
                console.log(text+'_平面图');
//                showwindow($.trim(text)+'_平面图');
            }

        }
    },
    showCurWindow : function() {
        //显示当前窗口,主要用于Mode窗口关闭时重新打开窗口画面

        //选择工具栏选中状态
        var btnTypeText = "";
        var toolbarId = $(".auto_btn .cur").attr("id");
        if(typeof(toolbarId)=='undefined' || toolbarId==null || toolbarId=='') {
            btnTypeText="";
        } else {
            var btnType = toolbarId.replace('btn_','');
            if(btnType=='asccompn') {
                btnTypeText = "_结构图";
            } else if (btnType=='ascplanar') {
                btnTypeText = "_平面图";
            } else {
                btnTypeText="";
            }
        }

        //选择左侧选中的子菜单
        var cursubmenu = $(".mode_cataloge .current a").attr("title");
        var retVal = "";
        //if($(cursubmenu).length) {
        //	retVal = $(cursubmenu).attr('id') + btnTypeText;
        //}

        retVal = cursubmenu + btnTypeText;
//        console.log("重新打开窗口："+retVal);

        if(retVal.length>0) {
//            showwindow(retVal);
        }
    }, //定位菜单,text = 画面名称
    fixed : function(text) {
        //特殊处理下划线问题
        //根所画面名称定位菜单 ，如冷源或组合式空调机组B1_结构图
        var winname = submenu.getWinName(text);

        $(".cataloge_class_two li").removeClass("current");
        $(".cataloge_class_two>li:not('.cataloge_class_three')").each(function () {
            if ($(this).next().attr("class") == "cataloge_class_three") {
                //var submenuName = $(this).next().find("a").attr("title");
                $(this).show();
                $(this).next().find("a").each(function(){
                    var title = submenu.getWinName($(this).attr('title'));
                    if(title==winname) {
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