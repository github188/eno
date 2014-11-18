function HashMap() {
    var size = 0; // Map大小
    var entry = new Object(); // 对象
    this.put = function(key, value) { // Map的存put方法
        if (!this.containsKey(key)) {
            size++;
            entry[key] = value;
        }
    };
    this.get = function(key) { // Map取get方法
        return this.containsKey(key) ? entry[key] : null;
    };
    this.remove = function(key) { // Map删除remove方法
        if (this.containsKey(key) && (delete entry[key])) {
            size--;
        }
    };
    this.containsKey = function(key) { // 是否包含Key
        return (key in entry);
    };
    this.containsValue = function(value) { // 是否包含Value
        for ( var prop in entry) {
            if (entry[prop] == value) {
                return true;
            }
        }
        return false;
    };
    this.values = function() { // 所有的Value
        var values = new Array();
        for ( var prop in entry) {
            values.push(entry[prop]);
        }
        return values;
    };
    this.keys = function() { // 所有的 Key
        var keys = new Array();
        for ( var prop in entry) {
            keys.push(prop);
        }
        return keys;
    };
    this.size = function() { // Map size
        return size;
    };
    this.clear = function() { // 清空Map
        size = 0;
        entry = new Object();
    };
};

// 创建平面图上对应的客流点位
function createPosInfo(datalist, posArr){
    for(var i = 0; i < datalist.length; ++i){
        var posname = datalist[i][0];
        var $storePos = $("<div/>",{
            class : "storepos",
        }).hover(function(){
                $(this).css({"z-index": "200"});
            }, function(){
                $(this).css({"z-index": "100"});
            });
        var $rankIcon = $("<div/>",{
            class : "rank_icon"
        });
        var $rankNum = $("<p/>",{
            class : "rank_num",
            text : i + 1
        });
        var $arrow = $("<div/>",{
            class : "arrow"
        });
        var $cusInfo = $("<div/>",{
            class : "cus_info"
        });
        var $storeName = $("<div/>",{
            class : "store_name",
            fullname : datalist[i][3],
            person : hashMap.get(posname),
            text : posname // posArr[i].name
        });
        var $storeCusNum = $("<div/>",{
            class : "store_custom_num",
            text : hashMap.get(posname) + "人"
        });
        // 特殊处理排名前三的店铺样式
        if (i < 3) {
            $storePos.addClass("number" + (i+1));
        }
        // 第10名以后的店铺不显示店铺名称和当前人数
        if (i > 9) {
            $cusInfo.hide();
        }

        $rankNum.appendTo($rankIcon);
        $arrow.appendTo($rankIcon);

        $storeName.appendTo($cusInfo);
        $storeCusNum.appendTo($cusInfo);

        $rankIcon.appendTo($storePos);
        $cusInfo.appendTo($storePos);
        $storePos.css({
            "left" : posMap.get(posname)[0].left,
            "top" : posMap.get(posname)[0].top
        });
        $storePos.appendTo($(".pmt"));
    }

    // 设置店铺div的高度
    $(".storepos").each(function(){
        $(this).find(".rank_icon").css({
            "line-height" : $(this).css("height")
        });
        $(this).find(".rank_icon .arrow").css({
            "top": $(this).css("height")
        });
    });

    // 设置第10名以后的店铺的悬浮事件
    $(".storepos:gt(9)").each(function(){
        var thisHeight = $(this).css("height");
        var thisArrowHeight = $(this).find(".arrow").css("top");
        $(this).hover(function(){
            $(this).find(".cus_info").show();
            $(this).find(".rank_icon").css({
                "height" : $(this).css("height"),
                "line-height" : $(this).css("height")
            });
            $(this).find(".arrow").css({
                "top" : $(this).css("height")
            });
        }, function(){
            $(this).find(".cus_info").hide();
            $(this).find(".rank_icon").css({
                "height" : thisHeight,
                "line-height" : thisHeight
            });
            $(this).find(".arrow").css({
                "top" : thisArrowHeight
            });
        });
    });

    // 右侧客流排名收缩触发的事件
    $(".flip").on("click", function(e){
        $(".custom_rank").animate({
            "margin-right" : "-285px",
            "opacity" : "0"
        }, 500, function(){
            $(".custom_rank").hide();
        });
    });

    // 右侧客流排名展开触发的事件
    $(".show_rank").on("click", function(){
        $(".custom_rank").show();
        $(".custom_rank").animate({
            "margin-right" : "0px",
            "opacity" : "1"
        }, 500, function(){
            $(".custom_rank").show();
        });
    });

    // 只在停留在客流量视图的时候才获取对应的店铺详情
    $(".storepos").on("click", function(){
    	if ($(".auto_btn > .passengerview").hasClass("cur")) { // 当前处于客流视图画面
            $(".store_name_display").text($(this).find(".store_name").text()); // 修改下方总客流文字
            var fullname = $(this).find(".store_name").attr("fullname"); // 对应的店铺全名
            var person = $(this).find(".store_name").attr("person"); // 对应的店铺当前人数
            getSingleShop(fullname, $(this).find(".store_name").text(), person); // 获取店铺信息
		}
    });
}

// 创建右侧排名事件
function createRankList(customArr){
    $("#top20Passenger").html('');
    for(var i = 0; i < customArr.length; ++i){
        var $tr = $("<tr/>");
        var $td1 = $("<td/>",{
            text : customArr[i][0]
        });
        var $td2 = $("<td/>",{
            text : customArr[i][1] + "人"
        });
        var imgname = 'smalluparrow'; // 上升
        if(customArr[i][2] == 0)
            imgname = 'smalldownarrow';// 下降

        var $td3 = $("<td/>",{
            html : "<img src='../resources/images/" + imgname + ".png'>"
        });

        $tr.append($td1);
        $tr.append($td2);
        $tr.append($td3);

        $tr.appendTo($("#content_1 table>tbody"));
    }
}


// 生成柱状图图表 
function renderChart(chartId,chartType,width,height,categories,data,dataName,fillColor, isTrue, buttom){
    $("#"+chartId).highcharts({
        chart:{
            type:chartType,
            height:height,
            width:width,
            borderRadius:3,
            spacingBottom: (buttom == undefined || buttom == '' ? 0 : buttom),
            spacingTop:30
        },
        credits:{//引用网页
            enabled:false
        },
        exporting:{//导出表
            enabled:false
        },
        title:{//表标题
            text:null
        },
        legend:{//图例
            enabled:false
        },
        xAxis:{
            title:{//x轴标题
                enabled:isTrue
            },
            categories:categories,//x轴坐标在tooltip中的显示
            labels:{//x轴坐标数字
                step: 4,
                enabled: isTrue
            },
            lineColor:'#C8D59D',//x轴线颜色
            lineWidth:1,//x轴线宽度
            tickLength:0//X轴短线长度
        },
        yAxis: {
            title:{//y轴标题
                //enabled:false
				text : '人数'
            },
            gridLineDashStyle:'ShortDashDot',//网格横线类型
            labels:{//y轴坐标数字
               // enabled:false
            }
        },
        plotOptions: {
            series: {
                lineWidth: 1,//线宽
                marker:{
                    states:{
                        hover:{
                            fillColor:fillColor,
                            lineWidth:2,
                            radius:0
                        }
                    },
                    radius: 0
                }
            }
        },
        series: [{
            color : fillColor,
            name: dataName,//数据名：显示在tooltip第一行
            data: data
        }]
    });
}

// 获取客流页面下方的具体的店铺信息
function getSingleShop(shopName, name, person) {
    all_shopName = name, all_shopNamePerson = person;
    var url = baseurl + '/pfe/getShopPassengerFlow';
    $.ajax({
        type : "POST",
        url : url,
		data : {
			shopName : shopName
		},
        success : function(result) {
            try{
                var total = 0, datalist = [], catalist = [], num = 0;
                for(var i=0;i<result.length;i++){
                    total += result[i].inCount;
                    if(i != 0 && i % 6 == 0) {
                        datalist.push(total);
                        catalist.push(num ); // + ":00"
                        total = 0;
                        num++;
                    }
                }
                $("#totalPassOfToday").text(person); // 今日累计客流
                renderChart("passenger_chart","column",700,260,catalist,datalist,'客流',"#68C8CA", true, 10); // 生成下方图表
            } catch(e) {
                console.log('error'+e);
            }
        },
        error : function(result) {
            console.log('error');
        }
    });

	// 求具体店铺的同比昨日信息
	if(all_shopName == '总客流信息') { // 求某层的总客流
		var url = baseurl + '/pfe/getTotalPassengerFlow';
		$.ajax({
			type : "POST",
			url : url,
			success : function(result) {
				try{
					var todayTotal = result.todayTotal;
					var yestodayTotal = result.yesterdayTotal;
					var percent = ((todayTotal - yestodayTotal) / yestodayTotal * 100 ).toFixed(0);
					if(parseInt(percent) < 0) {
						percent = percent.substring(1);
						$("#compareImg").attr("src","../resources/images/bigdownarrow.png");
					} else{
						$("#compareImg").attr("src","../resources/images/biguparrow.png");
					}
					$("#compareOfYestoday").text(''); // 同比昨日
					$("#compareOfYestoday").text(percent); // 同比昨日
				} catch(e) {
					console.log('error'+e);
				}
			},
			error : function(result) {
				console.log('error');
			}
		});
	} else { // 求具体店铺的详情
		var url = baseurl + '/pfe/getShopPassengerFlowCompare';
		$.ajax({
			type : "POST",
			url : url,
			data : {
				shopName : shopName
			},
			success : function(result) {
				try{
					var todayTotal = result.todayTotal;
					var yestodayTotal = result.yesterdayTotal;
					var percent = ((todayTotal - yestodayTotal) / yestodayTotal * 100 ).toFixed(0);
					if(parseInt(percent) < 0) {
						percent = percent.substring(1);
						$("#compareImg").attr("src","../resources/images/bigdownarrow.png");
					} else{
						$("#compareImg").attr("src","../resources/images/biguparrow.png");
					}
					$("#compareOfYestoday").text(''); // 同比昨日
					$("#compareOfYestoday").text(percent); // 同比昨日
				} catch(e) {
					console.log('error'+e);
				}
			},
			error : function(result) {
				console.log('error');
			}
		});
	}
	
    // 本月、本年日均数据
    var url = baseurl + '/pfe/getShopTotalPassengerFlowByMonth';
    $.ajax({
        type : "POST",
        url : url,
		data : {
			shopName : shopName
		},
        success : function(result) {
            var currentMonthCount = 0, currentYearCount = 0; // 当前月份的总客流
            var currentMonth = new Date().getMonth() + 1; // 当前月份
            var yearTotal = 0;
            for(var i=0;i<result.length;i++) {
                if(result[i].countTime == currentMonth){
                    currentMonthCount = result[i].inCount;
                }
                if(result[i].countTime != currentMonth){
                    currentYearCount += new Date(new Date().getFullYear(), result[i].countTime, 0).getDate();
                }
                yearTotal += result[i].inCount;
            }
            currentYearCount += new Date().getDate();
            $("#avgPassOfMonth").text((currentMonthCount / new Date().getDate()).toFixed(0)); // 右下角月平均客流
            $("#avgPassOfYear").text((yearTotal / currentYearCount).toFixed(0)); // 右下角年平均客流
        },
        error : function(result) {
            console.log('error');
        }
    });
}

// 查询总客流及下方图表数据
function getTotalPassengerFlow() {
	// 获取总客流信息和同比昨日的涨幅
    var url = baseurl + '/pfe/getTotalPassengerFlow';
    $.ajax({
        type : "POST",
        url : url,
        success : function(result) {
            try{
                var todayTotal = result.todayTotal;
                var yestodayTotal = result.yesterdayTotal;
				$("#totalPassOfToday").text(todayTotal); // 一层客流信息
                var percent = ((todayTotal - yestodayTotal) / yestodayTotal * 100 ).toFixed(0);
				if(parseInt(percent) < 0) {
					percent = percent.substring(1);
					$("#compareImg").attr("src","../resources/images/bigdownarrow.png");
				} else{
					$("#compareImg").attr("src","../resources/images/biguparrow.png");
				}
				$("#compareOfYestoday").text(''); // 同比昨日
				$("#compareOfYestoday").text(percent); // 同比昨日
            } catch(e) {
                console.log('error'+e);
            }
        },
        error : function(result) {
            console.log('error');
        }
    });

    // 获取柱状图需要的数据
    var url = baseurl + '/pfe/getTotalPassengerFlowHour';
    $.ajax({
        type : "POST",
        url : url,
        success : function(result) {
            try{
                var datalist = [], catalist = [];
                for(var i=0;i<result.length;i++){
                    datalist.push(result[i].inCount);
                    catalist.push(i + ":00");
                }

                if(all_shopName == '总客流信息')
                    renderChart("passenger_chart","column",700,260,catalist,datalist,'客流',"#68C8CA", true, 10); // 生成下方图表
                else
                    getSingleShop(all_shopName,all_shopName, hashMap.get(all_shopName));
            } catch(e) {
                console.log('error'+e);
            }
        },
        error : function(result) {
            console.log('error');
        }
    });
}
// 存放店铺点位信息
var customConfig = {
    "1F" : [
		{"name" : "百货一层",		     "pos" : [{"left" : "825px","top" : "383px"}]},  //
		{"name" : "SELECTED",		 "pos" : [{"left" : "314px","top" : "205px"}]},  //
		{"name" : "RIVERSTONE",	     "pos" : [{"left" : "362px","top" : "182px"}]}, //
		{"name" : "PEACE BIRD",	     "pos" : [{"left" : "415px","top" : "191px"}]}, //
		{"name" : "JACK JONES",	     "pos" : [{"left" : "523px","top" : "193px"}]}, //
		{"name" : "SW JEANS",		 "pos" : [{"left" : "583px","top" : "192px"}]}, //
		{"name" : "Hopeshow",		 "pos" : [{"left" : "621px","top" : "188px"}]}, //
		{"name" : "broadcast",		 "pos" : [{"left" : "661px","top" : "184px"}]}, //
		{"name" : "蒂奥莎",			 "pos" : [{"left" : "748px","top" : "159px"}]}, //
		{"name" : "ONLY",			 "pos" : [{"left" : "785px","top" : "167px"}]}, //
		{"name" : "乐町",			     "pos" : [{"left" : "815px","top" : "145px"}]}, //
		{"name" : "PEACE BIRD(F)",	 "pos" : [{"left" : "844px","top" : "157px"}]}, //
		{"name" : "圣妮",			     "pos" : [{"left" : "879px","top" : "148px"}]}, //
		{"name" : "VERSACE",		 "pos" : [{"left" : "949px","top" : "140px"}]}, //
		{"name" : "AMASS",			 "pos" : [{"left" : "989px","top" : "139px"}]}, //
		{"name" : "MEACHEAL",		 "pos" : [{"left" : "1026px","top" : "137px"}]}, //
		{"name" : "五色风马",		     "pos" : [{"left" : "1079px","top" : "139px"}]}, //
		{"name" : "Joannada",		 "pos" : [{"left" : "1150px","top" : "171px"}]}, //
		{"name" : "Girdear",		 "pos" : [{"left" : "1174px","top" : "205px"}]}, //
		{"name" : "ZUKKA",			 "pos" : [{"left" : "1186px","top" : "246px"}]}, //
		{"name" : "JETEZO",		     "pos" : [{"left" : "1198px","top" : "295px"}]}, //
		{"name" : "PIZZA HUT",		 "pos" : [{"left" : "1253px","top" : "342px"}]}, //
		{"name" : "Caffe bene",	     "pos" : [{"left" : "1143px","top" : "347px"}]}, //
		{"name" : "电影售票点",		     "pos" : [{"left" : "1110px","top" : "305px"}]},
		{"name" : "Desies",		     "pos" : [{"left" : "358px","top" : "399px"}]}, //
		{"name" : "TISSOT",		     "pos" : [{"left" : "267px","top" : "407px"}]}, //
		{"name" : "KFC",			 "pos" : [{"left" : "237px","top" : "472px"}]}, //
		{"name" : "蒂爵珠宝",		     "pos" : [{"left" : "262px","top" : "359px"}]}, //
		{"name" : "绝代佳人",		     "pos" : [{"left" : "264px","top" : "317px"}]}, //
		{"name" : "dealuna",		 "pos" : [{"left" : "220px","top" : "278px"}]}, //
		{"name" : "OUHTEU集合店",	     "pos" : [{"left" : "264px","top" : "245px"}]}, //
		{"name" : "NBY+CROQUE",	     "pos" : [{"left" : "415px","top" : "286px"}]}, //
		{"name" : "GOELIA",		     "pos" : [{"left" : "472px","top" : "371px"}]}, //
		{"name" : "Adidas-Originals","pos" : [{"left" : "520px","top" : "270px"}]}, //
		{"name" : "Lachapelle",	     "pos" : [{"left" : "367px","top" : "351px"}]}, //
		{"name" : "优衣库",		     "pos" : [{"left" : "556px","top" : "356px"}]}, //
		{"name" : "时尚表集合",	         "pos" : [{"left" : "590px","top" : "274px"}]}, //
		{"name" : "CASIO",			 "pos" : [{"left" : "658px","top" : "265px"}]}, //
		{"name" : "VERO MODA",		 "pos" : [{"left" : "750px","top" : "259px"}]}, //
		{"name" : "T-YSKJ",		     "pos" : [{"left" : "840px","top" : "230px"}]}, //
		{"name" : "CACHE CACHE",	 "pos" : [{"left" : "929px","top" : "203px"}]}, //
		{"name" : "Effusive",		 "pos" : [{"left" : "989px","top" : "232px"}]}, //
		{"name" : "COLUN",		     "pos" : [{"left" : "1078px","top" : "268px"}]}
	],
	"2F" : [
		{"name" : "百货二层",		     "pos" : [{"left" : "845px","top" : "358px"}]},  //
		{"name" : "中国移动",	         "pos" : [{"left" : "294px","top" : "207px"}]},  //
		{"name" : "佐丹奴",		     "pos" : [{"left" : "330px","top" : "193px"}]}, //
		{"name" : "K-BOXING",		 "pos" : [{"left" : "367px","top" : "182px"}]}, //
		{"name" : "与狼共舞",		     "pos" : [{"left" : "403px","top" : "178px"}]}, //
		{"name" : "HAFACE",	         "pos" : [{"left" : "445px","top" : "176px"}]}, //
		{"name" : "Tan_s",		     "pos" : [{"left" : "502px","top" : "182px"}]}, //
		{"name" : "JUST US",		 "pos" : [{"left" : "521px","top" : "179px"}]}, //
		{"name" : "元素",			     "pos" : [{"left" : "547px","top" : "177px"}]}, //
		{"name" : "MOVE UP", 		 "pos" : [{"left" : "577px","top" : "176px"}]}, //
		{"name" : "CC_DD",	         "pos" : [{"left" : "610px","top" : "174px"}]}, //
		{"name" : "S.DEER",		 	 "pos" : [{"left" : "649px","top" : "162px"}]}, //
		{"name" : "蛋卷鞋",		 	 "pos" : [{"left" : "683px","top" : "172px"}]}, //
		{"name" : "ZIPPO",		 	 "pos" : [{"left" : "734px","top" : "165px"}]}, //
		{"name" : "HOTWIND",	 	 "pos" : [{"left" : "787px","top" : "152px"}]}, //
		{"name" : "OMI",			 "pos" : [{"left" : "833px","top" : "145px"}]}, //
		{"name" : "LNO",			 "pos" : [{"left" : "869px","top" : "145px"}]}, //
		{"name" : "AJIDOU",			 "pos" : [{"left" : "907px","top" : "132px"}]}, //
		{"name" : "韩氏化妆品",			 "pos" : [{"left" : "966px","top" : "128px"}]}, //
		{"name" : "天福茗茶",	    	 "pos" : [{"left" : "1011px","top" : "122px"}]}, //
		{"name" : "奇仁堂",		     "pos" : [{"left" : "1047px","top" : "126px"}]}, //
		{"name" : "名好记",			 "pos" : [{"left" : "1089px","top" : "124px"}]}, //
		{"name" : "慧美美发",		     "pos" : [{"left" : "1147px","top" : "153px"}]},
		{"name" : "中国电信3G",		 "pos" : [{"left" : "1168px","top" : "192px"}]}, //
		{"name" : "庆安数码",		     "pos" : [{"left" : "1184px","top" : "215px"}]}, //
		{"name" : "好利来",			 "pos" : [{"left" : "1197px","top" : "244px"}]}, //
		{"name" : "皇封参",			 "pos" : [{"left" : "1222px","top" : "279px"}]}, //
		{"name" : "PIZZA HUT",		 "pos" : [{"left" : "1189px","top" : "342px"}]}, //--
		{"name" : "CAFFE BENE",		 "pos" : [{"left" : "1120px","top" : "344px"}]}, //--
		{"name" : "陶宜家",			 "pos" : [{"left" : "1086px","top" : "270px"}]}, //
		{"name" : "paul frank",		 "pos" : [{"left" : "1006px","top" : "212px"}]}, //
		{"name" : "VAAKAV",			 "pos" : [{"left" : "964px","top" : "204px"}]}, //
		{"name" : "15MINUTE_S",		 "pos" : [{"left" : "924px","top" : "206px"}]}, //
		{"name" : "启路",			     "pos" : [{"left" : "887px","top" : "217px"}]}, //
		{"name" : "LOAD MAX",		 "pos" : [{"left" : "857px","top" : "221px"}]}, //
		{"name" : "The Shoes Bar",	 "pos" : [{"left" : "819px","top" : "225px"}]}, //
		{"name" : "CONVERSE LIFE",	 "pos" : [{"left" : "755px","top" : "239px"}]}, //
		{"name" : "coolmax",		 "pos" : [{"left" : "719px","top" : "250px"}]}, //
		{"name" : "CROCS",			 "pos" : [{"left" : "654px","top" : "254px"}]}, //
		{"name" : "Kissmango",		 "pos" : [{"left" : "587px","top" : "256px"}]}, //
		{"name" : "PAT_S",			 "pos" : [{"left" : "544px","top" : "252px"}]}, //
		{"name" : "林清轩",			 "pos" : [{"left" : "520px","top" : "263px"}]}, //
		{"name" : "VISION UP",		 "pos" : [{"left" : "480px","top" : "264px"}]}, //
		{"name" : "SAMSUNG",		 "pos" : [{"left" : "440px","top" : "266px"}]}, //
		{"name" : "享购数码",		     "pos" : [{"left" : "412px","top" : "279px"}]}, //
		{"name" : "Balabala生活馆",	 "pos" : [{"left" : "356px","top" : "324px"}]}, //
		{"name" : "Watsons",		 "pos" : [{"left" : "408px","top" : "359px"}]}, //
		{"name" : "BINF",			 "pos" : [{"left" : "359px","top" : "391px"}]}, //
		{"name" : "妈咪宝贝",		     "pos" : [{"left" : "297px","top" : "466px"}]}, //
		{"name" : "lovely lace",	 "pos" : [{"left" : "264px","top" : "360px"}]}, //
		{"name" : "思瑞福纯银首饰",		 "pos" : [{"left" : "270px","top" : "320px"}]}, //
		{"name" : "俏佳人",		     "pos" : [{"left" : "245px","top" : "293px"}]}, //
		{"name" : "哈动园",			 "pos" : [{"left" : "227px","top" : "237px"}]}
	],
	"3F" : [
		{"name" : "百货三层",	         "pos" : [{"left" : "845px","top" : "365px"}]},  //
		{"name" : "草原狼",	         "pos" : [{"left" : "321px","top" : "200px"}]},  //
		{"name" : "碧海云香",		     "pos" : [{"left" : "367px","top" : "186px"}]}, //
		{"name" : "锦官芙蓉",		     "pos" : [{"left" : "431px","top" : "177px"}]}, //
		{"name" : "DF",		         "pos" : [{"left" : "481px","top" : "185px"}]}, //
		{"name" : "刘一锅",	         "pos" : [{"left" : "519px","top" : "171px"}]}, //
		{"name" : "聚品荟",		     "pos" : [{"left" : "575px","top" : "167px"}]}, //
		{"name" : "粥全粥到",		     "pos" : [{"left" : "648px","top" : "168px"}]}, //
		{"name" : "yopapa",		 	 "pos" : [{"left" : "720px","top" : "168px"}]}, //
		{"name" : "比格",		         "pos" : [{"left" : "758px","top" : "163px"}]}, //
		{"name" : "月影",	             "pos" : [{"left" : "804px","top" : "154px"}]}, //
		{"name" : "豪客来",			 "pos" : [{"left" : "852px","top" : "148px"}]}, //
		{"name" : "汉拿山",			 "pos" : [{"left" : "946px","top" : "128px"}]}, //
		{"name" : "八佰宴",			 "pos" : [{"left" : "1017px","top" : "128px"}]}, //
		{"name" : "欢乐家",		     "pos" : [{"left" : "1150px","top" : "143px"}]}, //
		{"name" : "阿香米线",			 "pos" : [{"left" : "1172px","top" : "190px"}]}, //
		{"name" : "豪口福",			 "pos" : [{"left" : "1189px","top" : "229px"}]}, //
		{"name" : "彤德莱",			 "pos" : [{"left" : "1213px","top" : "272px"}]}, //
		{"name" : "阿尔卑斯",			 "pos" : [{"left" : "1192px","top" : "348px"}]}, //
		{"name" : "厚粮私房面",			 "pos" : [{"left" : "1088px","top" : "287px"}]}, //
		{"name" : "喜家德",			 "pos" : [{"left" : "1007px","top" : "207px"}]}, //
		{"name" : "南粉北面",			 "pos" : [{"left" : "927px","top" : "212px"}]}, //
		{"name" : "香芒山",			 "pos" : [{"left" : "835px","top" : "226px"}]}, //
		{"name" : "樱婷",			     "pos" : [{"left" : "740px","top" : "254px"}]}, //
		{"name" : "悦荟",			     "pos" : [{"left" : "663px","top" : "270px"}]}, //
		{"name" : "蜀江烤鱼",			 "pos" : [{"left" : "612px","top" : "272px"}]}, //
		{"name" : "蜀江果汁吧",			 "pos" : [{"left" : "540px","top" : "256px"}]}, //
		{"name" : "无名小子鸡公煲",		 "pos" : [{"left" : "490px","top" : "290px"}]}, //
		{"name" : "仙踪林",			 "pos" : [{"left" : "427px","top" : "260px"}]}, //
		{"name" : "红虾馆",			 "pos" : [{"left" : "399px","top" : "302px"}]}, //
		{"name" : "站亭",				 "pos" : [{"left" : "372px","top" : "357px"}]}, //
		{"name" : "鑫铭苑",			 "pos" : [{"left" : "303px","top" : "463px"}]}, //
		{"name" : "泰尼派",			 "pos" : [{"left" : "262px","top" : "340px"}]}, //
		{"name" : "兰桂坊",			 "pos" : [{"left" : "225px","top" : "309px"}]}, //
		{"name" : "过锅瘾",			 "pos" : [{"left" : "247px","top" : "248px"}]}
	],
	"4F" : [
		{"name" : "百货四层",	         "pos" : [{"left" : "845px","top" : "365px"}]}  //
	],
	"5F" : [
		{"name" : "百货五层",	         "pos" : [{"left" : "845px","top" : "365px"}]}  //
	],
	"6F" : [
		{"name" : "百货六层",	         "pos" : [{"left" : "845px","top" : "365px"}]}  //
	]
};
var arrayList = []; 
var all_shopName = '总客流信息', all_shopNamePerson = '20,328'; // 店铺名称
var currentFloor = '1F'; // 默认显示的层数
var posMap = new HashMap(), hashMap = new HashMap(), floorMap = new HashMap(), hundredMap = new HashMap(); // map
var hundredList = [ '百货一层', '百货二层', '百货三层', '百货四层', '百货五层', '百货六层'];
for(var i = 1; i <= 6; i++) {
	floorMap.put("F" + i, i + "F");
	hundredMap.put(i + "F", hundredList[i - 1]);
}
for(var i = 0; i < arrayList.length; ++i) {
    hashMap.put(arrayList[i][0], arrayList[i][1]);
}

for(var i = 0; i < customConfig[currentFloor].length; ++i) { // 存放店铺名称，以及店铺对应的位置信息(left，top值)
    posMap.put(customConfig[currentFloor][i].name, customConfig[currentFloor][i].pos);
}

// 获取并创建平面图中对应的客流信息
function getFloorPassengerInfo(type) {
    var url = baseurl + '/pfe/' + (type == 'inside' ? 'getAllShopInsideOrder' : 'getAllShopOrder');
    // console.log("客流量--前台url----" + url);
    var buildOrderList = [];
    $.ajax({
        type : "POST",
        url : url,
        data : {
        	location : currentFloor,
        	hundredText : hundredMap.get(currentFloor)
        },
        async: false, // 同步
        success : function(result) {
            try{
                hashMap = new HashMap();
                arrayList = [];
                for(var i = 0; i < result.length; ++i){
//                    if(arrayList.length == 20){ // 只显示前20位的店铺
//                        break;
//                    } else {
                        var blockname = result[i].blockName, incount = (type == 'inside' ? result[i].insidePerson : result[i].inCount);
                        if(blockname.indexOf("消防通道") != -1 || blockname.indexOf("步行街") != -1 || blockname.indexOf("外门") != -1){ // 过滤设备
                            continue;
                        } else {
                            newblockname = (blockname.indexOf("百货") != -1) ? blockname : blockname.substring(3).trim();
                            if(posMap.get(newblockname) == null)
                                continue;
                            tempList = [];
                            tempList.push(newblockname), tempList.push(incount), tempList.push(result[i].status), tempList.push(blockname);
                            //if(arrayList.length <= 20) { // 平面图中只显示前20名店铺
                            	arrayList.push(tempList);
                            //}
                            buildOrderList.push(tempList);
                            hashMap.put(newblockname, incount);
                        }
//                    }
                }
            } catch(e) {
                console.log('error'+e);
            }
        },
        error : function(result) {
            console.log('error');
        }
    });
    createPosInfo(arrayList, customConfig[currentFloor]);
    createRankList(buildOrderList); // 右侧排名
}

$(function(){
	// 菜单事件，点击左侧第三级菜单（楼层菜单）的处理事件
	$(".cataloge_class_three li").click(function() {
		
		var text = $(this).find('a').text();
		currentFloor = floorMap.get(text); // 当前楼层
		
		$("#passengerBackground").css("background", "url('../resources/css/images/ggzm_" + text + ".jpg')"); // 修改画面的楼层背景图片
		for(var i = 0; i < customConfig[currentFloor].length; ++i) { // 存放店铺名称，以及店铺对应的位置信息(left，top值)
			posMap.put(customConfig[currentFloor][i].name, customConfig[currentFloor][i].pos);
		}
		$('.storepos').remove(); // 移除平面图中的div
		
		if ($(".auto_btn > .passengerview").hasClass("cur")) { // 当前处于客流视图画面
			$("#passenger_order").text('客流量排名');
			all_shopName = '总客流信息', all_shopNamePerson = '20,328'; // 店铺名称
			$(".store_name_display").text(all_shopName); 
			getTotalPassengerFlow(); // 查询总客流及下方图表数据
			getFloorPassengerInfo(); // 获取并创建平面图中对应的客流信息
		}
		
		if ($(".auto_btn > .inperson").hasClass("cur")) { // 当前处于场内人数画面
			$("#passenger_order").text('场内人数排名');
			getFloorPassengerInfo('inside'); // 获取并创建平面图中对应的场内人数信息
		}
		
	});

    getTotalPassengerFlow(); // 查询总客流及下方图表数据
    getFloorPassengerInfo(); // 获取并创建平面图中对应的客流信息

    setInterval(function(){ // 每10分钟获取并创建平面图中对应的客流信息
        $('.storepos').remove();

        getFloorPassengerInfo();
        getTotalPassengerFlow(); // 查询总客流及下方图表数据
    },1000 * 60 * 10);
});