$(function(){
	// $(".mode_select>li:not('.self-inspection')").click(function(){		
	// 	$("#mask").show();
	// 	showPanel();
	// 	showConfirmInfo(textInfo1);
	// 	$(".dialog-close, .dialog-cancel").click(function(){
	// 		$(".dialog").remove();
	// 		$("#mask").hide();
	// 	})
	// });
	/**/
	$(".self-inspection").click(function(){
		$("#mask").show();
		$(".self-inspection-popover").show();
		$(".ok_btn, .close_pop").click(function(){
			$(".self-inspection-popover").hide();
			$("#mask").hide();
		})
	});

    $(".lunxun_btn").click(function(){
        var tex=$(".lunxun_btn").text();
        if(tex=='视频轮巡'){
            $(".lunxun_btn").html("关闭轮巡");
            $(".self-inspection-popover").hide();
            $("#mask").hide();
            var url = "http://"+ LOCALADDR +":8082/tag/rtdb/set?tagname=KDZJ&value=1&jsoncallback=?";
            $.getJSON(url, function () {});
        }else{
            $(".lunxun_btn").html("视频轮巡");
            $(".self-inspection-popover").hide();
            $("#mask").hide();
            var url = "http://"+ LOCALADDR +":8082/tag/rtdb/set?tagname=KDZJ&value=0&jsoncallback=?";
            $.getJSON(url, function () {});
        }
    })


	$(".nav_list_ul > li").each(function(index){
		$(this).click(function(){
			$(this).siblings().removeClass("nav_chage_color");
			$(this).addClass("nav_chage_color");
			$($(".right_content_block>div")[index]).siblings().removeClass("current_block");
			$($(".right_content_block>div")[index]).addClass("current_block");
		});
	});

	/**/
    $(".custom_select_2").on("click", function(event){
        $(".custom_select_2>ul").show();
        showSelect(floorInfoArr, $(".custom_select_2>ul"));
        $(".custom_select_2>ul>li").on("click", function(event){
            var text = $(this).text();
            $(".custom_select_2>p").text(text);
            $(".custom_select_2>ul").hide();
            event.stopPropagation();
        })
        event.stopPropagation();
    });


    //lsnpub
    $(".spec_custom_select").on("click", function(event){
        $(".spec_custom_select>ul").show();
        showSelect(floorInfoArr, $(".spec_custom_select>ul"));
        $(".spec_custom_select>ul>li").on("click", function(event){
            var text = $(this).text();
            $(".spec_custom_select>p").text(text);
            $(".spec_custom_select>ul").hide();
            event.stopPropagation();
        })
    });

    document.onclick = function(){
        $(".custom_select_2>ul").hide();
    }






//    document.onclick = function(){
//        $("").hide();
//    }

})

var floorInfoArr = ["一层","二层","三层"];

function showSelect(floorInfoArr,domUl){
    domUl.children().remove();
    for(var i = 0; i < floorInfoArr.length; ++i){
        var $li = $("<li/>",{
            text : floorInfoArr[i],
            id : "floor_" + i
        });
        $li.appendTo(domUl);
        domUl.css({"border": "1px solid #ccc"});
    }
    
    // 绑定公共照明选择层数
    $("#LSPUB>.floor_select>div:eq(0)>ul>li").each(function(index){
    	$(this).live("click", function () {
    		Public_Lighting.getChart(index + 1); // 根据选择，改变对应的公共照明数据 
    	});
    });
    
    // 绑定室内环境选择层数
    $("#INENV>.title_snhj>div:eq(0)>ul>li").each(function(index){
    	$(this).live("click", function () {
    		Indoor_Environment.getChart(index + 1); // 根据选择，改变对应的室内环境数据 
    	});
    });
}

function showPanel(textInfo){
	var $div = $("<div/>", {
		class : "dialog"
	});
	$div.id = "create_mode_apply";
	var $header = $("<div/>", {
		class : "dialog-header"
	});
	var $body = $("<div/>", {
		class : "dialog-body"
	});
	var $footer = $("<div/>", {
		class : "dialog-footer"
	});
	var $title = $("<p/>", {
		class : "dialog-title",
		text : "确认信息"
	});
	var $close = $("<div/>", {
		class : "dialog-close",
		text : "×"
	});
	var $cancel = $("<div/>", {
		class : "dialog-btn dialog-cancel",
		text : "取消"
	});
	var $confirm = $("<div/>", {
		class : "dialog-btn dialog-confirm",
		text : "确定"
	});

	$cancel.appendTo($footer);
	$confirm.appendTo($footer);

	$title.appendTo($header);
	$close.appendTo($header);

	$header.appendTo($div);
	$body.appendTo($div);
	$footer.appendTo($div);


	// $(".self-inspection-popover").draggable();

	$div.appendTo("#mask");
}

function showConfirmInfo(textInfo) {
	var $p = $("<p/>", {
		class : "dialog-body-content",
		text : textInfo
	});

	$p.appendTo($(".dialog-body"));
}

function showMessageInDialog(msg,message_type)
{
    if (message_type =="error")
        $(".self-inspection-popover-footer .dialogTip").css({"color": "#f00"});
    else
        $(".self-inspection-popover-footer .dialogTip").css({"color": "#78CDD0"});
    $(".dialogTip").html(msg);
    var t=setTimeout('clearMessageInDialog()',3000);
}
// 将弹出dialog中的操作提示删除掉
function clearMessageInDialog()
{
    $(".dialogTip").html('');
}

function isTime(input)
{
    var diffIndex = input.indexOf(":");
    if ((diffIndex <1)||(input.length<3))
        return false;

    var hour = input.substring(0,diffIndex);
    var minute = input.substring(diffIndex+1);

    if ((! isNumber(hour))||(! isNumber(minute))||(parseInt(hour)>23)||(parseInt(minute)>59))
        return false;
    else
        return true;
}

function isDate(input)
{
    if (input.length < 10)
        return false;
    var firstDiffIndex = input.indexOf("-");
    if (firstDiffIndex !=4)
        return false;
    var lastDiffIndex = input.lastIndexOf("-");
    if (lastDiffIndex !=7)
        return false;

    var yyyy = input.substring(0,firstDiffIndex);
    var mm = input.substring(firstDiffIndex+1,7);
    var dd = input.substring(lastDiffIndex+1);

    if ((! isNumber(yyyy))||(! isNumber(mm))||(! isNumber(dd))||(parseInt(mm)>12)||(parseInt(dd)>31))
        return false;
    else
        return true;
}

function isNumber(inputNumber)
{
    var sReNumber = /^[0-9]+$/;
    if (sReNumber.test(inputNumber)) {
        return true;
    }
    else {
        return false;
    }
}

function checkStatus(status){
    if(status=='1'){
        $(".lunxun_btn").html("关闭轮巡");
    }else{
        $(".lunxun_btn").html("视频轮巡");
    }
}