var textInfo1 = "确定要切换模式?",
    textInfo2 = "确定切换到手动模式?",
    textInfo3 = "确定切换到自动模式？";
textInfo4 = "系统处于自动模式运行中!";

var ajaxFlag = 0;
var curPosInfo;
$(function () {



    //如果有默认菜单，则发送菜单显示指令
    var curText = $(".cataloge_class_two .current").find('a').text();
    if (curText != null && curText.length > 0) {
        javascript:showwindow(curText);
    }
    //console.log(curText);

    $(".navigation>li:gt(0):lt(10)").addClass("nav_bar");

    /*
     $(".reikenli").each(function (index) {
     $(this).click(function () {
     if ($($(".sub_ul")[index]).css("display") == "none") {
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
     });*/

    for (var i = 0; i < $(".sub_li").length; ++i) {
        $($(".sub_li")[i]).attr({"systemId": i});
    }

    $(".sub_li").each(function () {
        $(this).click(function () {
            var n = $(this).attr("systemId");
        });
    });


    $(".sub_li").click(function () {
        $(".sub_li").removeClass("changeBg");
        $(this).addClass("changeBg")
    });

    $(".device_tab>ul>li").click(function () {
        $(this).siblings().removeClass("cur_device");
        $(this).addClass("cur_device");
    });


    $(".close").click(function () {
        $(".mask").hide();
    })

    $(".return_first").click(function () {
        $(".cataloge_class_three").find("li").removeClass("current");
        $(".cataloge_class_three").find("li:first").addClass("current");
    })


    var runPattern = '';
    var isAuto = false;
    $(".mode_switch>ul>li:not(:last)").click(function () {

        var thisText = $(this).find("p:eq(0)").text();
        var runCheck = $(".mode_switch .current_mode>p").text();
        // console.log(thisText,runCheck);
        $("#mask").show();
        showPanel2();

        //console.log(CtrlID);
        switch (thisText) {
            case "自动运行":
                closeAllWindow();
                if (runCheck == thisText) {
                    isAuto = true;
                    showConfirmInfo(textInfo4);
                } else {
                    isAuto = false;
                    showConfirmInfo(textInfo3);
                    runPattern = "N";
                }
                break;
            case "手动模式":
                closeAllWindow();
                isAuto = false;
                showConfirmInfo(textInfo2);
                showTextArea();
                runPattern = "Y";
                break;
        }
        $(this).siblings().removeClass("current_mode");
        $(this).addClass("current_mode");
        //console.log(thisText,runCheck);
        $(".dialog-close, .dialog-cancel").click(function () {
            if (runCheck == "自动运行") {
                $(".mode_switch li").removeClass("current_mode");
                $("#automode").addClass("current_mode");
            } else if (runCheck == "手动模式") {
                $(".mode_switch li").removeClass("current_mode");
                $("#handmode").addClass("current_mode");
            }
            $(".dialog").remove();
            $("#mask").hide();
            submenu.showCurWindow();
        });
    });

    function showTextArea() {
        var $p = $("<p/>", {
            style: "font-size:18px;margin-left:200px;margin-bottom:5px",
            text: "请输入切换原因:"
        });

        $p.appendTo($(".dialog-body"));

        var $t = $("<textarea/>", {
            style: "width:600px;height:50px;font-size:18px;margin-left:200px;margin-bottom:20px",
            text: ""
        });

        $t.appendTo($(".dialog-body"));
        $t.click(function () {
            this.focus()
        });
    }

    $(".dialog-confirm").live("click", function () {
        if (isAuto) {
            $(".dialog").remove();
            $("#mask").hide();
        } else {
            deviceRunPattern(runPattern);
        }
    });

    function deviceRunPattern(param) {

        var subment_current = $(".left_menu .cur i");
        var id = $(subment_current).attr('id');
        if (id == null || id == "" || id == "HVAC") {

            subment_current = $(".cataloge_class_three .current");
            if (subment_current == null || subment_current.length == 0) {

                subment_current = $(".cataloge_class_two .current");
                id = $(subment_current).attr('id');
            } else {

                var parent = subment_current.parent().parent();
                var el = parent.prev();
                id = el.attr("id");
            }
        }

        var url = baseurl + '/pattern/readyManual';

        var desc = $(".dialog-body textarea").val();
        if (desc == "") {
            showMessageInDialog2("请输入切换原因!");
            return false;
        }
        var params = {type: param, reason: desc, systemCode: id};

        //不同的子系统对应不同的模式变量
        var tagname = "ZDYX";
        var subsysid = $(".left_menu .cur i").attr("id");

        if (subsysid == "LSPUB") {
            tagname = "ZDYX_GGZM";
        } else if (subsysid == 'LSN') {
            tagname = "ZDYX_YJZM";
        } else {
            tagname = "ZDYX";
        }

        var flag = false;

        $.ajax({
            url: url, type: 'POST', dataType: 'json', async: true, data: params, success: function (data) {
                if (data.success == true) {

//                $(".dialog").remove();
//                $("#mask").hide();
//                submenu.showCurWindow();
                    $("#mask").html("");
                    $("#mask").hide();


                    if (LOCALADDR == "")
                        LOCALADDR = "127.0.0.1";
                    if (param == 'N') {
                        $(".mode_switch>ul>li").eq(0).addClass("current_mode");
                        $(".mode_switch>ul>li").eq(1).removeClass("current_mode");
                        $(".mb_nav, .left_menu_mb, .mode_cataloge_mb").hide();
                        clearControlBtn();
                        addMode();

                        var url = "http://" + LOCALADDR + ":8082/tag/rtdb/set?tagname=" + tagname + "&value=1&jsoncallback=?";
                        // var url = "http://192.168.1.254:8082/tag/rtdb/set?tagname=ZDYX&value=1&jsoncallback=?";
//                    console.log("模式切换请求地址:"+ url);
                        //alert("模式切换请求地址:"+ url);
                        $.getJSON(url, function () {
                        });
                        url = "http://" + LOCALADDR + ":8082/tag/rtdb/set?tagname=ZDYX&value=1&jsoncallback=?";
                        $.getJSON(url, function () {
                        });
                    }
                    if (param == 'Y') {
                        $(".mode_switch>ul>li").eq(1).addClass("current_mode");
                        $(".mode_switch>ul>li").eq(0).removeClass("current_mode");
                        $(".mb_nav, .left_menu_mb, .mode_cataloge_mb").show();
                        var url = "http://" + LOCALADDR + ":8082/tag/rtdb/set?tagname=" + tagname + "&value=0&jsoncallback=?";

                        $.getJSON(url, function () {
                        });
                        url = "http://" + LOCALADDR + ":8082/tag/rtdb/set?tagname=ZDYX&value=0&jsoncallback=?";
                        $.getJSON(url, function () {
                        });
                    }
                    showMessageInDialog2("切换完成!");
                    // submenu.showCurWindow();

                    flag = true;
                }
                else {
                    showMessageInDialog2("切换失败，请稍后再试!");

                    flag = false;
                    //
                }
            }
        }).done(function () {
            if (flag == true) {
                if (subsysid == "LSPUB" || subsysid == "LSN") {
                    //公共照明和夜景不显示CS窗口
                } else {
                    submenu.showCurWindow();
                }
            }

        });


    }

    $(".dialog-save").live("click", function () {
        //console.log($(".newT>tbody>tr:not('#copy_src')").length);
        if ($(".newT>tbody>tr:not('#copy_src')").length == 0) {
            showMessageInDialog2("请选择一个设备");
        } else {
            alert("ok");
        }
    })

    $(".addTr").live("click", function () {
        var cc = getDeviceNameList();
        if (cc == "") {
            showMessageInDialog2("请选中一个设备");
        } else {
            trdom = document.getElementById('copy_src');
            distr = trdom.cloneNode(true);
            distr.id = "";
            $(distr).find("td:eq(0)").text(cc);
            $(distr).appendTo($(".newT>tbody"));
        }

    });

    $(".del_btn").live("click", function () {
        $(this).parent().parent().remove();
    });

    //显示模式
    changePattern();

    //重大活动
    $("#eventImportent").click(function () {
        eventImportent();
    });
    //窗口切换
    $("#windowChange").click(function () {
        changeWindow();
    });
    $(".switch_legend_show").toggle(function () {
        $(this).addClass("btn_press");
        $(".legend").show();
    }, function () {
        $(this).removeClass("btn_press");
        $(".legend").hide();
    });


    function allopen($this) {
        var idArr = [];
        $(this).parentsUntil(".loop_info").find("input").each(function () {
            var thisId = $(this).attr("assetnum");
            idArr.push(thisId);

        })
        ajaxFlag = 1;
        $(this).parentsUntil(".loop_info").find("input").bootstrapSwitch('setState', true);

        ajaxFlag = 0;

        setAllopenclose(idArr, "Y");
    }

    function allclose($this) {
        var idArr = [];
        $(this).parentsUntil(".loop_info").find("input").each(function () {
            var thisId = $(this).attr("assetnum");
            idArr.push(thisId);

        });
        ajaxFlag = 1;

        $(this).parentsUntil(".loop_info").find("input").bootstrapSwitch('setState', false);

        setAllopenclose(idArr, "N");
        ajaxFlag = 0;
    }

    function ajustPress() {
        var len = $(".toggle_switch").length;
        var nowlen = $(".toggle_switch:checked").length;
//        console.log("len:"+len,"nowlen:"+ nowlen);
        if (nowlen > 0) {
            $(".point[info=\'" + curPosInfo + "\']").css({"background-color": "#37A1A9"});
        } else if (nowlen == 0) {
            $(".point[info=\'" + curPosInfo + "\']").css({"background-color": "#a3a3a3"});
        } else {

        }
    }

    function returnIndex() {
        $(".returnIndex").css("background-color", "#b9b9b9");
        $(".loop_info").hide();
        $(".mode_operate").show();
    }

    $(".toggle_switch").live("switch-change", function (e, data) {
        var len = $(".toggle_switch").length;
        var idArr = $(this).attr("assetnum");
//        console.log("3" +ajaxFlag);
        if (ajaxFlag == 0) {
            if (data.value == 0) {
                setAllopenclose(idArr, "N");
                $(".point[info=\'" + curPosInfo + "\']").css({"background-color": "#a3a3a3"});
            } else {
                setAllopenclose(idArr, "Y");
                $(".point[info=\'" + curPosInfo + "\']").css({"background-color": "#37A1A9"});
            }
        } else {

        }
        ajustPress();

    });


    $(".if_operate").on("click", function () {
        if ($(this).hasClass("cando")) {
            $(this).removeClass("cando");
        } else {
            $(this).addClass("cando");
            ;
        }
    })

    $(".if_operate").on("click", function () {
        var thisClass = $(this).attr("class");
        if (thisClass.indexOf("cando") == -1) {
            // console.log($(this).parentsUntil(".loop_info").find("input"));
            // $(this).parentsUntil(".loop_info").find("input").bootstrapSwitch("setDisabled",false);
            $(this).parentsUntil(".loop_info").find("input").each(function () {
                $(this).bootstrapSwitch("setDisabled", true);
            });

            $(".allopen").unbind("click", allopen, $(this));
            $(".allclose").unbind("click", allclose, $(this));

        } else {
            $(this).parentsUntil(".loop_info").find("input").each(function () {
                $(this).bootstrapSwitch("setDisabled", false);

            });

            $(".allopen").bind("click", allopen);
            $(".allclose").bind("click", allclose);
        }
    });

    $(".block_zone .cancontrol").each(function () {
        $(this).on("click", function () {
            var thisClass = $(this).attr("class");
            if (thisClass.indexOf("allowop") > -1) {
                $(this).removeClass("allowop");
                $(this).siblings().find("input").bootstrapSwitch("setDisabled", true);
            } else {
                $(this).addClass("allowop");
                $(this).siblings().find("input").bootstrapSwitch("setDisabled", false);
            }
        });
    });

    $(".point").live("click", function () {
        var floor = $(".cataloge_class_two > .current a");
        var thisName = $(this).attr("info");
        var thisCnName = $(this).attr("cnName");
        var thisInfo = thisName.split("_");
        thisInfo.push(thisCnName);
        $(".if_operate").removeClass("cando");
        $(".mode_operate").hide();
        $(".loop_info").show();
        $(".returnIndex").css("background-color", "#86D3D5").bind("click", returnIndex);
        renderEachLoopContent(thisInfo);
        getPointInfo($(this));
        curPosInfo = $(this).attr("info");
    });


});

var dd;
var trdom;

function showPanel2(textInfo) {
    var $div = $("<div/>", {
        class: "dialog"
    });
    $div.id = "create_mode_apply";
    var $header = $("<div/>", {
        class: "dialog-header"
    });
    var $body = $("<div/>", {
        class: "dialog-body"
    });
    var $footer = $("<div/>", {
        class: "dialog-footer"
    });
    var $tip = $("<div/>", {
        class: "dialog-tip"
    });

    var $title = $("<p/>", {
        class: "dialog-title",
        text: "确认信息"
    });
    var $close = $("<div/>", {
        class: "dialog-close",
        text: "×"
    });
    var $cancel = $("<div/>", {
        class: "dialog-btn dialog-cancel",
        text: "取消"
    });
    var $confirm = $("<div/>", {
        class: "dialog-btn dialog-confirm",
        text: "确定"
    });
    $tip.appendTo($footer);

    $confirm.appendTo($footer);
    $cancel.appendTo($footer);

    $title.appendTo($header);
    $close.appendTo($header);

    $header.appendTo($div);
    $body.appendTo($div);
    $footer.appendTo($div);
    $div.appendTo("#mask");
    $(".dialog").draggable();
}

function showConfirmInfo(textInfo) {
    var $p = $("<p/>", {
        class: "dialog-body-content",
        text: textInfo
    });

    $p.appendTo($(".dialog-body"));

}

function showDeviceList() {
    var $divLeft = $("<div/>", {
        class: "left_tree"
    });
    var $divRight = $("<div/>", {
        class: "right_table"
    });
    var $tree = $("<div/>", {
        id: "tree"
    });
    $tree.id = "tree";

    var $add = $("<div/>", {
        class: "addTr button",
        text: "添加"
    });

    $tree.appendTo($divLeft);
    $add.appendTo($divLeft);
    $divLeft.appendTo($(".dialog-body"));
    $divRight.appendTo($(".dialog-body"));
    $(".dialog-body").css({"height": "530px"});
    $(".dialog").css({"margin-top": "200px"});

    var deviceTree = '[{"Equpment":{"id":"1","name":"设备1","parent":"0"}},{"Equpment":{"id":"2","name":"设备2","parent":"0"}},{"Equpment":{"id":"3","name":"设备3","parent":"0"}},{"Equpment":{"id":"4","name":"设备4","parent":"2"}},{"Equpment":{"id":"5","name":"设备5","parent":"2"}}]';
    device_list = eval("(" + deviceTree + ")");

    var id = "";
    var name = "";
    var parent = "";
    dd.config.clickfolderEvent = "addNewDeviceException";
    dd.config.check = true;
    dd.add("0", -1, "所有设备");// id=0代表目录树的根
    var i = 0;
    for (i = 0; i < device_list.length; i++) {
        id = device_list[i].Equpment.id;
        name = device_list[i].Equpment.name;
        parent = device_list[i].Equpment.parent;
        dd.add(id, parent, name);
    }

    document.getElementById('tree').innerHTML = dd;

    var oldTable = document.getElementById('opera_table');
    var newTable = oldTable.cloneNode(true);
    $(newTable).attr({"class": "newT", "id": ""});
    $(newTable).appendTo($(".right_table"));

}

function getDeviceNameList() {
    var cc = dd.getCheckedNodes();
    var ccstr = "";
    var slid = "";
    var idArr = [];
    var nameArr = [];
    for (var i = 0; i < cc.length; ++i) {
        idArr.push(cc[i].id);
    }
    for (i = 0; i < cc.length; i++) {
        if (idArr.indexOf(cc[i].pid) == -1) {
            nameArr.push(cc[i].name);
        } else {
            continue;
        }
    }
    ccstr = nameArr.join(",");
    return ccstr;
}

function showMessageInDialog2(msg) {
    $(".dialog-tip").html(msg);
    var t = setTimeout('clearMessageInDialog2()', 3000);
}
// 将弹出dialog中的操作提示删除掉
function clearMessageInDialog2() {
    $(".dialog-tip").html('');
}

function changePattern() {
    $.ajax({
        type: "GET",
        url: CONTEXT_PATH + '/device/getTodaySystemRunPattern',
        cache: false,
        success: function (data) {
            var dataList = data;
            var html = "";
            var openCheck = $("#openCheck");
            if (openCheck) {
                openCheck.html("");
                for (var i = 0; i < dataList.length; i++) {
                    var datavalue = dataList[i];
                    html += '<tr><td>' + datavalue.systemName + '</td><td>' + datavalue.patternName + '</td></tr>';
                    if (datavalue.systemCode == 'MAHU') {
                        $("#HVAC_PATTERN").append(datavalue.patternName);
                        $("#HAVC_CHECK_PATTERN").append(datavalue.patternName);
                    }
                    if (datavalue.systemCode == 'LSPUB') {
                        $("#LSPUB_PATTERN").append(datavalue.patternName);
                        $("#LSPUB_CHECK_PATTERN").append(datavalue.patternName);
                    }
                    if (datavalue.systemCode == 'LSN') {
                        $("#LSN_PATTERN").append(datavalue.patternName);
                        $("#LSN_CHECK_PATTERN").append(datavalue.patternName);
                    }
                }
                openCheck.append(html);
            }

            var runCheck = $("#automode");
            if (runCheck) {
                $("#automode div p").html("");
                var subment_current = $(".left_menu .cur i");
                var id = $(subment_current).attr('id');
                if (id == null || id == "" || id == "HVAC") {

                    subment_current = $(".cataloge_class_three .current");
                    if (subment_current == null || subment_current.length == 0) {

                        subment_current = $(".cataloge_class_two .current");
                        id = $(subment_current).attr('id');
                    } else {

                        var parent = subment_current.parent().parent();
                        var el = parent.prev();
                        id = el.attr("id");
                    }
                }

                for (var i = 0; i < dataList.length; i++) {
                    datavalue = dataList[i];
                    html += '<tr><td>' + datavalue.systemName + '</td><td>' + datavalue.patternName + '</td></tr>';
                    if (datavalue.systemCode == id) {
                        $("#automode div p").html('(' + datavalue.patternName + ")");
                    } else {
                        if ((id == 'EAV' || id == 'SAV') && datavalue.systemCode == 'AVU') {
                            $("#automode div p").html('(' + datavalue.patternName + ")");
                        }
                    }

                }
            }

        }
    });
}

function eventImportent() {
    $.ajax({
        type: "GET",
        url: CONTEXT_PATH + '/calendar/eventImportent',
        cache: false,
        success: function (data) {
            url = "http://" + LOCALADDR + ":8082/tag/rtdb/set?tagname=ZDHD&value=1&jsoncallback=?";
            $.getJSON(url, function () {
            });
        }
    });
}

function eventImportentCS() {
    $.ajax({
        type: "GET",
        url: CONTEXT_PATH + '/calendar/eventImportent',
        cache: false,
        success: function (data) {
        }
    });
}

function changeWindow() {

    try {
        var str = "alarmWindow";
        window.external.ExecuteUScript("showwindow(\"" + str + "\");");
    } catch (err) {
        //console.log(err);
    }
//
//    var v=$("#windowChange").val();
//    if(v=='1'){
//        try {
//           var str="alarmWindow";
//            window.external.ExecuteUScript("showwindow(\""+ str  +"\");");
//        }catch(err) {
//            //console.log(err);
//        }
//       // showwindow("alarmWindow");
//        $("#windowChange").val('2');
//    }
//    if(v=='2'){
//       // showwindow("systemWindow");
//        try {
//            var str="主页";
//            window.external.ExecuteUScript("showwindow(\""+ str  +"\");");
//        }catch(err) {
//            //console.log(err);
//        }
//        $("#windowChange").val('1');
//    }
//    if(v=='3'){
//       // showwindow("videoWindow");
//        try {
//            window.external.ExecuteUScript("showwindow('videoWindow');");
//        }catch(err) {
//            //console.log(err);
//        }
//        $("#windowChange").val('1');
//    }
}