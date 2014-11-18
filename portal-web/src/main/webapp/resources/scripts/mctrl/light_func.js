var baseUrl = CONTEXT_PATH;
var codeSwitch = {"CI":"闭店中","R":"营业前准备","O":"营业","B":"营业中","CR":"闭店后保洁","C":"闭店后保安"};
var modeDiv = [
    {"name" : "营业前准备", "code" : "R"},
    {"name" : "营业", "code" : "O"},
    {"name" : "闭店后保洁", "code" : "CR"},
    {"name" : "闭店后保安", "code" : "C"}
];

var lsnDiv = [
    {"name" : "T1:日落前后", "code" : "R"},
    {"name" : "T2:日落后-商场闭店", "code" : "O"},
    {"name" : "T3:商场闭店-娱乐闭店", "code" : "CR"},
    {"name" : "T4:娱乐闭店-日出", "code" : "C"}
];


var pics = {
    "1_pic" : "1",
    "2_pic" : "2",
    "3_pic" : "3",
    "4_pic" : "4",
    "5_pic" : "5",
    "6_pic" : "6",
    "7_pic" : "7",
    "8_pic" : "8",
    "9_pic" : "9",
    "10_pic" : "10",
    "11_pic" : "11",
    "12_pic" : "12"
}
function addMode(){
    var systemId = $(".cur i").attr("id");
    var url = baseUrl+'/mctrl/getSystemPattern';
    var params = {"systemCode" : systemId};
    $.ajax({
        url:url,
        type:'POST',
        data:params,
        dataType:'json',
        async:true,
        success:function(data){
            switch(systemId){
                case "LSPUB" :
                    addLspubModeBtn(data);
                    getNowMode();
                    break;
                case "LSN" :
                    addLsnModeBtn(data);
                    getNowMode();
                    var nowPic = $(".btn_press").attr("num");
                    refreshPic(nowPic);
                    break;
                default :
                    //console.log("error");
            }
        },
        error : function(){
           console.log("error");
        }
    });
}

function addLspubModeBtn(data){
    var $allopendom = $("<div/>",{
        class : "allopenclose"
    });
    var $modeTitle2 = $("<h1/>",{
        class : "mode_name",
        text : "全部"
    });
    $modeTitle2.appendTo($allopendom);
    for(var m = 0; m < data.length; ++m){
        if(data[m].name == "全开" ||
            data[m].name =="全关"){
            var $sonDiv2 = $("<div/>",{
                class : "mode_btn",
                id: data[m].id,
                actionType: data[m].ucPatternActions[0].actionType,
                text : data[m].name
            });
            $sonDiv2.appendTo($allopendom);
        }
    }

    $allopendom.appendTo($(".mode_operate"));
    for(var i = 0; i < data.length; ++i){
        if(data[i].name == "全开" ||
            data[i].name == "全关"){
            continue;
        }
        var $divFather = $("<div/>",{
            class : "eachFatherBlock",
            id : data[i].id
        });
        var $modeTitle = $("<h1/>", {
            class : "mode_name"
        });
        $modeTitle.text(data[i].name);
        $divFather.append($modeTitle);
        for(var j = 0; j < modeDiv.length; ++j){
            var $sonDiv = $("<div/>",{
                class : "mode_btn",
                actionType : modeDiv[j].code,
                text : modeDiv[j].name
            });
            $divFather.append($sonDiv);
        }
//                for(var j = data[i].ucPatternActions.length-1; j > 0; --j){
//                    if(data[i].ucPatternActions[j].actionType == "B" ||
//                       data[i].ucPatternActions[j].actionType == "CR" ){
//                       continue;
//                    } else {
//                        var $sonDiv = $("<div/>", {
//                            class : "mode_btn"
//                        });
//                        $divFather.append($sonDiv);
//
//                        $sonDiv.text(codeSwitch[data[i].ucPatternActions[j].actionType]);
//                    }
//                }
        $(".allopenclose").before($divFather);
    }
    $(".eachFatherBlock").each(function(){
        $(this).find(".mode_btn:first").addClass("nomargin");
    });
    $(".eachFatherBlock:first").addClass("marginleft");
}

function clearControlBtn(){
    $(".mode_operate *, .yjzm_control *").remove();
}

function addLsnModeBtn(data){
    for(var i = 0; i < data.length; ++i){
        if(data[i].name == "全开" || data[i].name == "全关"){
            //执行开全关
            continue;
        }
        var $divFather = $("<div/>",{
            id : data[i].id
        });
        var $modeBtnTitle = $("<h1>",{
            text : data[i].name
        });
        $modeBtnTitle.appendTo($divFather);
        for(var j = 0; j < lsnDiv.length; ++j){
            var $sonDiv = $("<div/>",{
                class : "mode_btn",
                num : j+1,
                actionType : modeDiv[j].code,
                text : lsnDiv[j].name
            });
            $divFather.append($sonDiv);
        }
        $divFather.appendTo($(".yjzm_control"));
    }

}

function postModeSwitch($dom){
    var url = baseUrl + "/mctrl/setPatternManual";
	var thisId;
    var thisAc = $dom.attr("actionType");

    if(($dom.attr("id")) == undefined){
        thisId = $dom.parent().attr("id");
    } else {
        thisId = $dom.attr("id");
    }
    var params = {patternId:thisId,actionType:thisAc};
    $.ajax({
        url:url,
        type : "POST",
        data:params,
        dataType:'json',
        async:true,
        success: function(data){
            addDeviceList();
        }
    });
}

function renderEachLoopContent(arr){
	var thisDes = $(".legend li[name='"+ arr[2] +"'" + "] .legend_describe span:eq(1)").text();
	$(".floor_num").text(arr[0]);
	$(".block_num").text(arr[1]);
	$(".kind_light").text(arr[3]);
	$(".light_des").text(thisDes);
}

function addDeviceList(){
    var url = baseUrl+'/mctrl/getAssetGroup';
//    var systemId = $("#accordion .cur").attr("id");
    var systemId = $(".cur i").attr("id");;
    var location = $(".cataloge_class_two .current");
    if(location.length == 0){
        location = "F1";
    } else {
        location = location.text();
    }
    var params = {location:location,systemCode:systemId};
    $.ajax({
        url:url,
        type:'POST',
        data:params,
        dataType:'json',
        async:true,
        success:function(data){
            for(var i = 0; i < data.length; ++ i){
                    if(data[i].coordinate == null){
                        continue;
                    } else {
                        var arrData = string2Arr(data[i].coordinate);
                        var info = brokeString(data[i].location);
                        info[2] = data[i].enName;
                        var $div = $("<div/>", {
                            text : data[i].name,
                            style : "left:" + arrData[0] + "px;" + "top:" + arrData[1] + "px",
                            info : "" + info[0] + "_" + info[1] + "_" + info[2],
                            locationinfo : data[i].location,
                            switchState : data[i].status,
                            text : data[i].enName,
                            cnName : data[i].groupName,
                            "classstructureid" : data[i].classstructureid,
                            "class" : "point"
                        });
                        $div.appendTo($(".pmt"));
                    }
            };
            changeBjPic(location);
            $(".point[switchState='Y']").css({"background-color": "#37A1A9"});
            //恢复显示模式界面
            $(".returnIndex").css("background-color","#b9b9b9");
            $(".loop_info").hide();
            $(".mode_operate").show();
        },
        error : function(){
            console.log("error");
        }
    });
}

function getNowMode(){
    var url = baseUrl + "/mctrl/setPatternButton";
    var systemId = $(".cur i").attr("id");;
    var params = {systemCode:systemId};
    $.ajax({
        url:url,
        type:"POST",
        data:params,
        dataType: "json",
        async: true,
        success: function(data){
            //console.log(".mode_btn[actionType=\'"  + data.actionType + "\']");
            //console.log($("#" + data.patternId).find(".mode_btn[actionType=\'" + data.actionType + "\']"));
            $("#" + data.patternId).find(".mode_btn[actionType=\'" + data.actionType + "\']").addClass("btn_press");
            var imgNum = $(".yjzm_control .btn_press").attr("num");
            if(systemId == "LSN") {
                refreshPic(imgNum);
            }
        }
    });
}

function setAllopenclose(idArr,stat){
    var url = baseUrl + "/mctrl/setSingleAssetStatus";
    if((typeof idArr) == "string"){

    } else {
        idArr = idArr.join(",");
    }
//    var loopStr = idArr.join(",");
    var status = stat;
//    console.log(stat);
    var params = {assetIds : idArr,status: stat };
    $.ajax({
        url:url,
        type:"POST",
        data:params,
        dataType: "json",
        async: true,
        success: function(data){
//            console.log("ok");
        }
    });
}

function getPointInfo($dom){
    var systemId = $(".left_menu .cur i").attr("id"),
        locationinfo = $dom.attr("locationinfo");
        url = baseUrl + "/mctrl/getAssetByClassstructureid",
        classstructureid = $dom.attr("classstructureid");
    var thisdominfo = $dom.attr("info");
        params = {groupId:classstructureid,systemCode:systemId,location : locationinfo};
    $.ajax({
        url:url,
        type:"POST",
        dataType:"json",
        async: true,
        data: params,
        success: function(data){
            addSwitchBtn(data);
            addLoopPic(thisdominfo);
        }
    });
}

function refreshPic(picNum){
    //console.log(pics[picNum +"_pic"]);
    $(".yjbj").css({
        "background-image" : "url('"+ baseUrl + "/resources/images/yjbj/"+ pics[picNum +"_pic"] +".png')"
    })
}

function addLoopPic(dominfo){
    var $img = $("<img>",{
        src : baseUrl + "/resources/images/floor/" + dominfo + ".PNG"
    });
    $(".loop_pic").children().remove();

    $img.appendTo($(".loop_pic"));
    $img.error(function(){
        $(this).hide();
    });
}

function addSwitchBtn(data){
    clearBlockData();
    for(var i = 0; i < data.length; ++i){
        $divFather = $("<div/>");
        $label = $("<label/>", {
            text : data[i].description
        });
        $switchBtn = $("<input/>",{
            type : "checkbox",
            class : "toggle_switch",
            "assetnum" : data[i].assetnum
        });

//        if (data[i].state == "0") {
//            $switchBtn.bootstrapSwitch('setState', false);
//        } else {
//            $switchBtn.bootstrapSwitch('setState', true);
//        }
        $switchBtn.bootstrapSwitch('setState', false);

        $label.appendTo($divFather);
        $switchBtn.appendTo($divFather);
        $divFather.appendTo($(".each_loop_state"));
        $switchBtn.bootstrapSwitch();
        $switchBtn.bootstrapSwitch("setDisabled", true);


    }
}

function clearBlockData(){
    $(".each_loop_state > div").remove();
}

function string2Arr(str){
    if(str){
        var arr = str.split(",");
        return arr;
    } else {
        return [0,0];
    }
}

function brokeString(str){
    var floor = str.slice(0,2);
    var block = str.slice(str.length - 1, str.length);

    return [floor,block];
}

function string2Arr2(str){
    var arr = str.split("_");
    return arr;
}

function changeBjPic(floor){
    $(".pmt").css({
        "background-image" : "url('"+ baseUrl + "/resources/images/ggzm_"+ floor +".jpg')"
    });
}


function initLight(){
	addMode();
    var elementvalue=$(".cur i").attr("id");
    if(elementvalue == "LSPUB"){
        addDeviceList();
    }


    $(".mode_operate .mode_btn").live("click", function(){
        if($(".current_mode p").text() == "手动模式"){
            $(".mode_operate .mode_btn").removeClass("btn_press");
            $(this).addClass("btn_press");
            postModeSwitch($(this));

        } else {
            return false;
        }
    });

    $(".cataloge_class_three li").live("click", function(e){
        addDeviceList();
    });

    $(".yjzm_control .mode_btn").live("click", function(e){
        if($(".current_mode p").text() == "手动模式"){
            var thisDomNum = $(this).attr("num");
            $(".yjzm_control .mode_btn").removeClass("btn_press");
            $(this).addClass("btn_press");
           // console.log(thisDomNum);
            postModeSwitch($(this));
            refreshPic(thisDomNum);
        } else {
            return false;
        }
    })



}

//电子巡更数据  翻页 **spb
var pageNum = 1; // 当前页
var pageCount = 1; // 总页数
var pageSize = 12; // 每页显示数据条数
function selectPatrolFy(para) {
	var pagef = "";
	if (para == "Before") {
		if (pageNum > 1) {
			pageNum = pageNum - 1;
		}
		pagef = pageNum;
	}
	if (para == "After") {
		if (pageNum < pageCount) {
			pageNum = pageNum + 1;
		}
		pagef = pageNum;
	}
	if (para == "End") {
		pageNum = pageCount;
		pagef = pageCount;
	}
	if (para == "First") {
		pageNum = 1;
		pagef = 1;
	}
	if (pageCount == "0") {
		pagef = 0;
	}
	$(".bc").text(pagef);
	var checkDate = $("#startDate").val();
	getSubPatrols(pageNum, pageSize, checkDate);

}

// 电子巡更点击查询事件 **spb
function selectShow() {
	var checkDate = $("#startDate").val();
	getSubPatrols(pageNum, pageSize, checkDate);
}

// 与后台交互，发送查询，处理返回数据 --PE **spb
function getSubPatrols(pageNum, pageSize, checkDate) {
	var url = base_url + '/elecpatrol/getPatrolList';
	$.ajax({
		url : url,
		type : 'GET',
		dataType : 'json',
		data : {
			pageNum : pageNum,
			pageSize : pageSize,
			checkDate : checkDate
		},
		success : function(data) {
			console.log(data);
			var dataHtml = '';
			var sub;
			for (var i = 0; i < data.pageItems.length; i++) {
				sub = data.pageItems[i];
				dataHtml += '<tr><td>'+sub.id+'</td><td>'+sub.lineNum+'</td><td>'+sub.lineName+'</td><td>'+sub.startTime+'</td><td>'+sub.endTime+'</td><td>'+sub.checkTime+'</td><td>'+sub.checkResult+'</td>'
					+'<td>'+sub.userName+'</td><td>'+sub.missedNum+'</td><td>'+sub.onTimeNum+'</td><td>'+sub.earlyNum+'</td><td>'+sub.lateNum+'</td><td>'+sub.shifts+'</td></tr>';
			}
			$("#tbodyContent tr:gt(0)").remove();
			$("#tbodyContent").append(dataHtml);
			pageCount = data.pagesAvailable;
			$(' .paging .pageCount').html(pageCount);
			$("#tbodyContent>tr").click(function(){
				$(this).siblings().removeClass("highlight");
				$(this).addClass("highlight");
				})
		},
		error : function(e) {
			console.log("get EpList error---:" + e);
		}
	});
}
// 初次加载电子巡更列表数据  **spb
function initPE(){
	var Nowdate = new Date();
	Nowdate.setDate(Nowdate.getDate()-1);
	var year = Nowdate.getFullYear();
	var month = (Nowdate.getMonth()+1)+"";
	month = (month.length == 1 ? ("0" + month) : month);
	var day = Nowdate.getDate()+"";
	day = day.length == 1 ? ("0" + day) : day;
	$("#startDate").val(year+"-"+month+"-"+day);
	selectShow()
}







