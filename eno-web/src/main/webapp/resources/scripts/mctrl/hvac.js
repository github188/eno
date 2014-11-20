var baseUrl = CONTEXT_PATH;

// 设备位置配置信息
var customConfig = {
    "MAHU_B1" : [
        {"name" : "KW_B1_2_1",		 "pos" : [{"left" : "497px","top" : "140px"}]},
        {"name" : "KW_B1_2_2",		 "pos" : [{"left" : "497px","top" : "175px"}]},
        {"name" : "KW_B1_4_1",		 "pos" : [{"left" : "643px","top" : "141px"}]},
        {"name" : "KW_B1_4_2",		 "pos" : [{"left" : "643px","top" : "176px"}]},
        {"name" : "KW_B1_3_1",		 "pos" : [{"left" : "474px","top" : "225px"}]},
        {"name" : "KW_B1_3_2",		 "pos" : [{"left" : "490px","top" : "260px"}]},
        {"name" : "KW_B1_1_1",		 "pos" : [{"left" : "292px","top" : "199px"}]},
        {"name" : "KW_B1_1_2",		 "pos" : [{"left" : "292px","top" : "234px"}]}

    ],
    "MAHU_F1" : [
        {"name" : "KW_1F_1",		 "pos" : [{"left" : "971px","top" : "181px"}]},
        {"name" : "KW_1F_2",		 "pos" : [{"left" : "1002px","top" : "214px"}]},
        {"name" : "KW_1F_3",		 "pos" : [{"left" : "1023px","top" : "247px"}]},
        {"name" : "KW_1F_4",		 "pos" : [{"left" : "1049px","top" : "280px"}]}
    ],
    "MAHU_F5" : [
        {"name" : "KW_5F_1",		 "pos" : [{"left" : "151px","top" : "264px"}]},
        {"name" : "KW_5F_2",		 "pos" : [{"left" : "174px","top" : "301px"}]},
        {"name" : "KW_5F_3",		 "pos" : [{"left" : "1106px","top" : "106px"}]},
        {"name" : "KW_5F_4",		 "pos" : [{"left" : "877px","top" : "185px"}]},
        {"name" : "KW_5F_5",		 "pos" : [{"left" : "883px","top" : "298px"}]},
        {"name" : "KW_5F_6",		 "pos" : [{"left" : "680px","top" : "195px"}]},
        {"name" : "KW_5F_7",		 "pos" : [{"left" : "731px","top" : "160px"}]},
        {"name" : "KW_5F_8",		 "pos" : [{"left" : "868px","top" : "144px"}]},
        {"name" : "KW_5F_9",		 "pos" : [{"left" : "1001px","top" : "164px"}]},
        {"name" : "KW_5F_10",		 "pos" : [{"left" : "1031px","top" : "198px"}]}
    ],
    "MAHU_F6" : [
        {"name" : "KW_6F_1",		 "pos" : [{"left" : "1004px","top" : "285px"}]}
    ],
    "FAVU_B2" : [
        {"name" : "KDX_B2_15_1",		 "pos" : [{"left" : "126px","top" : "121px"}]},
        {"name" : "KDX_B2_16_1",		 "pos" : [{"left" : "124px","top" : "217px"}]}
    ],
    "FAVU_F3" : [
        {"name" : "KDX_3F_1",		 "pos" : [{"left" : "690px","top" : "163px"}]}
    ],
    "FAVU_F4" : [
        {"name" : "KWX_4F_4",		 "pos" : [{"left" : "133px","top" : "290px"}]},
        {"name" : "KWX_4F_5",		 "pos" : [{"left" : "441px","top" : "483px"}]},
        {"name" : "KWX_4F_6",		 "pos" : [{"left" : "818px","top" : "452px"}]},
        {"name" : "KWX_4F_7",		 "pos" : [{"left" : "1024px","top" : "409px"}]},
        {"name" : "KWX_4F_8",		 "pos" : [{"left" : "1304px","top" : "285px"}]},
        {"name" : "KWX_4F_9",		 "pos" : [{"left" : "1283px","top" : "145px"}]},
        {"name" : "KDX_4F_1",		 	 "pos" : [{"left" : "726px","top" : "157px"}]},
        {"name" : "KDX_4F_2",		     "pos" : [{"left" : "751px","top" : "191px"}]}
    ],
    "FAVU_F5" : [
        {"name" : "KDX_5F_1",		 "pos" : [{"left" : "99px","top" : "238.99px"}]},
		{"name" : "KDX_5F_2",		 "pos" : [{"left" : "116.99px","top" : "273.99px"}]},
		{"name" : "KWX_5F_2",		 "pos" : [{"left" : "633.99px","top" : "491px"}]},
		{"name" : "KWX_5F_1",		 "pos" : [{"left" : "347px","top" : "233px"}]},
		{"name" : "KDX_5F_5",		 "pos" : [{"left" : "897.99px","top" : "465px"}]},
		{"name" : "KDX_5F_6",		 "pos" : [{"left" : "659px","top" : "267px"}]},
		{"name" : "KDX_5F_7",		 "pos" : [{"left" : "728px","top" : "227px"}]},
		{"name" : "KDX_5F_3",		 "pos" : [{"left" : "1035px","top" : "145px"}]},
		{"name" : "KDX_5F_10",		 "pos" : [{"left" : "982px","top" : "241.99px"}]},
		{"name" : "KDX_5F_9",		 "pos" : [{"left" : "953px","top" : "203px"}]},
		{"name" : "KDX_5F_4",		 "pos" : [{"left" : "828.99px","top" : "260px"}]},
		{"name" : "KDX_5F_8",		 "pos" : [{"left" : "841px","top" : "161px"}]}
    ],
    "FAVU_F6" : [
        {"name" : "KWX_6F_1",		 "pos" : [{"left" : "361px","top" : "242px"}]}
    ],
    "BUAHU_B2" : [
        {"name" : "KD_B2_15_1",		 "pos" : [{"left" : "132px","top" : "77px"}]},
        {"name" : "KD_B2_15_2",		 "pos" : [{"left" : "151px","top" : "111px"}]},
        {"name" : "KD_B2_15_3",		 "pos" : [{"left" : "173px","top" : "145px"}]},
        {"name" : "KD_B2_16_1",		 "pos" : [{"left" : "142px","top" : "189px"}]},
        {"name" : "KD_B2_16_2",		 "pos" : [{"left" : "142px","top" : "223px"}]},
        {"name" : "KD_B2_16_3",		 "pos" : [{"left" : "144px","top" : "256px"}]},
        {"name" : "KD_B2_16_4",		 "pos" : [{"left" : "146px","top" : "292px"}]},
        {"name" : "KD_B2_16_5",		 "pos" : [{"left" : "160px","top" : "326px"}]}
    ],
    "BUAHU_F1" : [
        {"name" : "KD_1F_1",		 "pos" : [{"left" : "140px","top" : "429px"}]},
        {"name" : "KD_1F_2",		 "pos" : [{"left" : "140px","top" : "466px"}]},
        {"name" : "KD_1F_3",		 "pos" : [{"left" : "207px","top" : "362px"}]},
        {"name" : "KD_1F_4",		 "pos" : [{"left" : "228px","top" : "396px"}]},
        {"name" : "KD_1F_5",		 "pos" : [{"left" : "211px","top" : "259px"}]},
        {"name" : "KD_1F_6",		 "pos" : [{"left" : "233px","top" : "293px"}]},
        {"name" : "KD_1F_7",		 "pos" : [{"left" : "335px","top" : "328px"}]},
        {"name" : "KD_1F_8",		 "pos" : [{"left" : "354px","top" : "363px"}]},
        {"name" : "KD_1F_9",		 "pos" : [{"left" : "274px","top" : "159px"}]},
        {"name" : "KD_1F_10",		 "pos" : [{"left" : "234px","top" : "192px"}]},
        {"name" : "KD_1F_11",		 "pos" : [{"left" : "250px","top" : "225px"}]},
        {"name" : "KD_1F_12",		 "pos" : [{"left" : "851px","top" : "303px"}]},
        {"name" : "KD_1F_13",		 "pos" : [{"left" : "833px","top" : "347px"}]},
        {"name" : "KD_1F_14",		 "pos" : [{"left" : "888px","top" : "385px"}]},
        {"name" : "KD_1F_15",		 "pos" : [{"left" : "1097px","top" : "296px"}]},
        {"name" : "KD_1F_16",		 "pos" : [{"left" : "1076px","top" : "261px"}]},
        {"name" : "KD_1F_17",		 "pos" : [{"left" : "1292px","top" : "278px"}]},
        {"name" : "KD_1F_18",		 "pos" : [{"left" : "1205px","top" : "313px"}]},
        {"name" : "KD_1F_19",		 "pos" : [{"left" : "1304px","top" : "346px"}]},
        {"name" : "KD_1F_20",		 "pos" : [{"left" : "1227px","top" : "214px"}]},
        {"name" : "KD_1F_21",		 "pos" : [{"left" : "1242px","top" : "131px"}]},
        {"name" : "KD_1F_22",		 "pos" : [{"left" : "1133px","top" : "119px"}]},
        {"name" : "KD_1F_23",		 "pos" : [{"left" : "1266px","top" : "169px"}]},
        {"name" : "KD_1F_24",		 "pos" : [{"left" : "576px","top" : "393px"}]},
        {"name" : "KD_1F_25",		 "pos" : [{"left" : "466px","top" : "384px"}]},
        {"name" : "KD_1F_26",		 "pos" : [{"left" : "575px","top" : "349px"}]},
        {"name" : "KD_1F_27",		 "pos" : [{"left" : "471px","top" : "330px"}]}
    ],
    "BUAHU_F2" : [
        {"name" : "KD_2F_1",		 "pos" : [{"left" : "183px","top" : "219px"}]},
        {"name" : "KD_2F_2",		 "pos" : [{"left" : "155px","top" : "455px"}]},
        {"name" : "KD_2F_3",		 "pos" : [{"left" : "71px","top" : "419px"}]},
        {"name" : "KD_2F_4",		 "pos" : [{"left" : "249px","top" : "280px"}]},
        {"name" : "KD_2F_5",		 "pos" : [{"left" : "146px","top" : "300px"}]},
        {"name" : "KD_2F_6",		 "pos" : [{"left" : "236px","top" : "394px"}]},
        {"name" : "KD_2F_7",		 "pos" : [{"left" : "132px","top" : "378px"}]},
        {"name" : "KD_2F_8",		 "pos" : [{"left" : "252px","top" : "319px"}]},
        {"name" : "KD_2F_9",		 "pos" : [{"left" : "357px","top" : "336px"}]},
        {"name" : "KD_2F_10",		 "pos" : [{"left" : "574px","top" : "338px"}]},
        {"name" : "KD_2F_11",		 "pos" : [{"left" : "469px","top" : "336px"}]},
        {"name" : "KD_2F_12",		 "pos" : [{"left" : "567px","top" : "376px"}]},
        {"name" : "KD_2F_13",		 "pos" : [{"left" : "460px","top" : "377px"}]},
        {"name" : "KD_2F_14",		 "pos" : [{"left" : "854px","top" : "325px"}]},
        {"name" : "KD_2F_15",		 "pos" : [{"left" : "750px","top" : "308px"}]},
        {"name" : "KD_2F_16",		 "pos" : [{"left" : "887px","top" : "365px"}]},
        {"name" : "KD_2F_17",		 "pos" : [{"left" : "778px","top" : "359px"}]},
        {"name" : "KD_2F_18",		 "pos" : [{"left" : "881px","top" : "134px"}]},
        {"name" : "KD_2F_19",		 "pos" : [{"left" : "893px","top" : "99px"}]},
        {"name" : "KD_2F_20",		 "pos" : [{"left" : "875px","top" : "176px"}]},
        {"name" : "KD_2F_21",		 "pos" : [{"left" : "1053px","top" : "111px"}]},
        {"name" : "KD_2F_22",		 "pos" : [{"left" : "1170px","top" : "115px"}]},
        {"name" : "KD_2F_23",		 "pos" : [{"left" : "1055px","top" : "147px"}]},
        {"name" : "KD_2F_24",		 "pos" : [{"left" : "1171px","top" : "155px"}]},
        {"name" : "KD_2F_25",		 "pos" : [{"left" : "1295px","top" : "169px"}]},
        {"name" : "KD_2F_26",		 "pos" : [{"left" : "1080px","top" : "262px"}]},
        {"name" : "KD_2F_27",		 "pos" : [{"left" : "1106px","top" : "302px"}]},
        {"name" : "KD_2F_28",		 "pos" : [{"left" : "1310px","top" : "281px"}]},
        {"name" : "KD_2F_29",		 "pos" : [{"left" : "1213px","top" : "314px"}]},
        {"name" : "KD_2F_30",		 "pos" : [{"left" : "1307px","top" : "350px"}]},
        {"name" : "KD_2F_31",		 "pos" : [{"left" : "1239px","top" : "206px"}]}
    ],
    "BUAHU_F3" : [
        {"name" : "KD_3F_1",		 "pos" : [{"left" : "72px","top" : "428px"}]},
        {"name" : "KD_3F_2",		 "pos" : [{"left" : "154px","top" : "461px"}]},
        {"name" : "KD_3F_3",		 "pos" : [{"left" : "126px","top" : "369px"}]},
        {"name" : "KD_3F_4",		 "pos" : [{"left" : "229px","top" : "401px"}]},
        {"name" : "KD_3F_5",		 "pos" : [{"left" : "144px","top" : "300px"}]},
        {"name" : "KD_3F_6",		 "pos" : [{"left" : "248px","top" : "282px"}]},
        {"name" : "KD_3F_7",		 "pos" : [{"left" : "252px","top" : "323px"}]},
        {"name" : "KD_3F_8",		 "pos" : [{"left" : "360px","top" : "318px"}]},
        {"name" : "KD_3F_9",		 "pos" : [{"left" : "453px","top" : "351px"}]},
        {"name" : "KD_3F_10",		 "pos" : [{"left" : "562px","top" : "349px"}]},
        {"name" : "KD_3F_11",		 "pos" : [{"left" : "461px","top" : "386px"}]},
        {"name" : "KD_3F_12",		 "pos" : [{"left" : "569px","top" : "389px"}]},
        {"name" : "KD_3F_13",		 "pos" : [{"left" : "879px","top" : "342px"}]},
        {"name" : "KD_3F_14",		 "pos" : [{"left" : "770px","top" : "350px"}]},
        {"name" : "KD_3F_15",		 "pos" : [{"left" : "749px","top" : "303px"}]},
        {"name" : "KD_3F_16",		 "pos" : [{"left" : "858px","top" : "306px"}]},
        {"name" : "KD_3F_17",		 "pos" : [{"left" : "1083px","top" : "296px"}]},
        {"name" : "KD_3F_18",		 "pos" : [{"left" : "1068px","top" : "259px"}]},
        {"name" : "KD_3F_19",		 "pos" : [{"left" : "1293px","top" : "293px"}]},
        {"name" : "KD_3F_20",		 "pos" : [{"left" : "1181px","top" : "302px"}]},
        {"name" : "KD_3F_21",		 "pos" : [{"left" : "1210px","top" : "337px"}]},
        {"name" : "KD_3F_22",		 "pos" : [{"left" : "1165px","top" : "220px"}]},
        {"name" : "KD_3F_23",		 "pos" : [{"left" : "1276px","top" : "184px"}]},
        {"name" : "KD_3F_24",		 "pos" : [{"left" : "1047px","top" : "106px"}]},
        {"name" : "KD_3F_25",		 "pos" : [{"left" : "1160px","top" : "117px"}]},
        {"name" : "KD_3F_26",		 "pos" : [{"left" : "1035px","top" : "145px"}]},
        {"name" : "KD_3F_27",		 "pos" : [{"left" : "1147px","top" : "156px"}]}
    ],
    "BUAHU_F4" : [
        {"name" : "KD_4F_1",		 "pos" : [{"left" : "777px","top" : "167px"}]},
        {"name" : "KD_4F_2",		 "pos" : [{"left" : "853px","top" : "205px"}]},
        {"name" : "KD_4F_3",		 "pos" : [{"left" : "970px","top" : "182px"}]}
    ]

};

$(function() {

	// 监测点击系统平面图
	$("#btn_ascplanar").click(function() {
		// var id = $(".current").attr("id"); // 获取当前选中的id
		var cid = $(".current").parent().parent().prev().attr("id"); // 选择的三级菜单
		if(cid == 'BUAHU'|| cid == 'MAHU' || cid=='FAVU') { // 组合式空调机组和吊装式空调机组此处做特殊处理
//			$(".custom_rank").hide(); // 显示设备列表
//            closeAllWindow();
//			buildHvacPlan(cid); // 生成暖通平面图
            addDeviceHvac();
		}
	});

	// 监测点击系统结构图
	$("#btn_asccompn").click(function() {
        if($(".cur i").attr("id") == "HVAC"){
            var current = $(".cataloge_class_three .current");
            var subSystemCode='';
            if(current.length==0){
                subSystemCode = $(".cataloge_class_two .current").attr("id");
            }else{
                subSystemCode = current.parent().parent().prev().attr("id");
            }
            if(subSystemCode=='BUAHU'|| subSystemCode=='MAHU' || subSystemCode=='FAVU'|| subSystemCode=='SAV'||subSystemCode=='EAV'){
                //这些子系统查询设备，添加到列表中
                closeAllWindow();
                    addDeviceHvac();
                $('.post_div').remove(); // 移除平面图中的div
                $(".custom_rank").hide(); // 显示设备列表
                $(".pmt_air").css("background", "url('../resources/css/images/"+subSystemCode+"_close.jpg')"); // 更换背景图片
            }
        }
	});

    $(".equip_list_con li:last-child").css("border","0");
    $(".show_btn").toggle(function(){
        $(".show_btn").css("right","130px");
        $(".equip_list_con").css({"display":"block","right":"0"});
    },function(){
        $(".show_btn").css("right","0");
        $(".equip_list_con").css("display","none");
    });
    
    $("#content_1").mCustomScrollbar({
        scrollButtons:{
            enable:true
        },
        advanced:{
            updateOnContentResize:true
        }
    });
    
    $(".flip").on("click", function(e){
        $(".custom_rank").animate({
            "margin-right" : "-285px",
            "opacity" : "0"
        }, 1000, function(){
            $(".custom_rank").hide();
        });
    });

    $(".hide_btn").on("click", function(){
        $(".custom_rank").show();
        $(".custom_rank").animate({
            "margin-right" : "0px",
            "opacity" : "1"
        }, 1000, function(){
            $(".custom_rank").show();
        });
    });

    //加载设备列表
    $(".cataloge_class_three li").live("click", function(e){

        if($(".cur i").attr("id") == "HVAC"){
            var current = $(".cataloge_class_three .current");
            var subSystemCode='';
            if(current.length==0){
                subSystemCode = $(".cataloge_class_two .current").attr("id");
            }else{
                subSystemCode = current.parent().parent().prev().attr("id");
            }
            if(subSystemCode=='BUAHU'|| subSystemCode=='MAHU' || subSystemCode=='FAVU'|| subSystemCode=='SAV'||subSystemCode=='EAV' ||subSystemCode=='FCU'){
               //这些子系统查询设备，添加到列表中

                if($("#btn_ascplanar").hasClass("cur")) { // 系统平面图
                    if(subSystemCode=='BUAHU'|| subSystemCode=='MAHU' || subSystemCode=='FAVU'){
//                        closeAllWindow();
//                        buildHvacPlan(subSystemCode); // 生成暖通平面图
                        //这些子系统平面图，不显示C/S画面
                        addDeviceHvac();
                    }
                }
                if($("#btn_asccompn").hasClass("cur")) { // 系统结构图，则关闭掉C/S画面
                    $(".pmt_air").css("background", "url('../resources/css/images/" + subSystemCode + "_close.jpg')"); // 更换背景图片
                    //这些子系统结构图不显示C/S画面
                    closeAllWindow();
                    addDeviceHvac();
                }
            }
        }
    });


    //加载设备列表
    $("#backgroundHvac .text_class").live("click", function(e){
        //转换成C/S的windows的名字
        var asset=$(this).text();
        var windowName= asset.replace(/-/gm,'');
        showAssetCS(windowName);
    });
});

function showAssetCS(win){
    var current = $(".cataloge_class_three .current");
    var subSystemCode='';
    if(current.length==0){
        subSystemCode = $(".cataloge_class_two .current").attr("id");
    }else{
        subSystemCode = current.parent().parent().prev().attr("id");
    }
    if(subSystemCode=='SAV'||subSystemCode=='EAV' ){
        showwindowNoClose("X"+win);
    }else{
        showwindowNoClose(win);
    }
}

function addDeviceHvac(){
    var url = baseUrl+'/mctrl/getAssets';
    var systemId = $(".cur i").attr("id");
    var location = "";
    var subSystemCode = "";
    var current = $(".cataloge_class_three .current");
    if(current.length==0){
        subSystemCode = $(".cataloge_class_two .current").attr("id");
    }else{
        subSystemCode = current.parent().parent().prev().attr("id");
        location = $(".cataloge_class_two .current");
        if(location.length == 0){
            location = "F1";
        } else {
            location = $(".current > a").attr("location");
        }
       console.log("--location----"+location);
    }

    var params = {location:location,systemCode:systemId,subSystemCode:subSystemCode};
    $.ajax({
        url:url,
        type:'POST',
        data:params,
        dataType:'json',
        async:true,
        success:function(data){
            var html="";
            var addre= $("#hvacAssets");
            addre.html(html);
            if(data!=null&&data.length>0) {
                for (var i = 0; i < data.length; ++i) {
                    var windowName = data[i].description;
                    if (i == 0) {
                        //默认打一个设备的C/S界面
                        checkAsset(windowName);
                    }
                    html = html + '<tr id="' + data[i].assetnum + '"  name="' + windowName + '"    onclick="selectAsset(this);"><td style="padding:0 "><a href="#"  >' + windowName + '</a></td></tr>';
                }
                addre.html(html);
            }
        },
        error : function(){
            console.log("error");
        }
    });
}

// 在平面图上创建设备信息
function createPosInfo(model_id, posArr){
    if(posArr!=null){
        for(var i = 0; i < posArr.length; ++i){
            var $storePos = $("<div/>",{
                class : "post_div"
            }).hover(function(){
                $(this).css({"z-index": "200"});
            }, function(){
                $(this).css({"z-index": "100"});
            });
            var $rankIcon = $("<i/>",{
                class : (model_id == "FAVU" ? "favu_icon_style" : "icon_style")
            });
            var $rankNum = $("<div/>",{
                class : "text_class",
                text : posArr[i]["name"]
            });
            $rankIcon.appendTo($storePos);
            $rankNum.appendTo($storePos);
            $storePos.css({
                "left" : posArr[i]["pos"][0].left,
                "top" : posArr[i]["pos"][0].top
            });
            $storePos.appendTo($(".pmt_air"));
        }
    }
}

// 生成暖通平面图
function buildHvacPlan(model_id) {
	$('.post_div').remove(); // 移除平面图中的div
	var floor = $(".current > a").attr("location");
	$(".pmt_air").css("background", "url('../resources/css/images/ggzm_" + floor + ".jpg')"); // 更换背景图片
	createPosInfo(model_id, customConfig[model_id + "_" + floor]);
}

//设置选择的设备的开关状态
function selectAsset(asset){
    //转换成C/S的windows的名字
//    var windowName= $(asset).attr('name').replace(/_/gm,'');
    checkAsset(asset);
}

//设置选择的设备的开关状态
function checkAsset(asset){

    //转换成C/S的windows的名字
//    var windowName= asset.replace(/_/gm,'');
    var windowName= $(asset).attr('name');
    if(windowName==undefined){
        showAssetCS(asset);
    }else{
        showAssetCS(windowName);
    }

    //得到设备的状态
    var url = baseUrl+'/mctrl/getAssetStatus';
    var params = {assetIds:asset.id};
    $.ajax({
        url:url,
        type:'POST',
        data:params,
        dataType:'json',
        async:true,
        success:function(data){
            if($(".cur i").attr("id") == "HVAC"){
                var current = $(".cataloge_class_three .current");
                var subSystemCode='';
                if(current.length==0){
                    subSystemCode = $(".cataloge_class_two .current").attr("id");
                }else{
                    subSystemCode = current.parent().parent().prev().attr("id");
                }
                if(data.msg=='Y'){
                    if($("#btn_ascplanar").hasClass("cur")) { // 系统平面图
                    }else{
                        $(".pmt_air").css("background", "url('../resources/css/images/" + subSystemCode + "_open.gif')"); // 更换背景图片
                    }
                }else{
                    if($("#btn_ascplanar").hasClass("cur")) { // 系统平面图
                    }else{
                        $(".pmt_air").css("background", "url('../resources/css/images/" + subSystemCode + "_close.jpg')"); // 更换背景图片
                    }
                }
            }
        },
        error : function(){
            console.log("error");
        }
    });
}