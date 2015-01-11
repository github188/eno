// 新版客流js页面
var trendMap = new HashMap(); // 右下角客流趋势
//var currentFloor = '1F'; // 默认显示的层数
var posMap = new HashMap(), hashMap = new HashMap(), floorMap = new HashMap(), hundredMap = new HashMap(); // map
var arrayList = [];
var hundredList = [ '百货一层', '百货二层', '百货三层', '百货四层', '百货五层', '百货六层'];
for (var i = 1; i <= 6; i++) {
    floorMap.put("F" + i, i + "F");
    hundredMap.put(i + "F", hundredList[i - 1]);
}
for (var i = 0; i < arrayList.length; ++i) {
    hashMap.put(arrayList[i][0], arrayList[i][1]);
}
floorMap.put("B1", "B1");
hundredMap.put("B1", "B1");
var background1 = "#68C8CA", background2 = "#B9B9B9"; // 声明两个图标的背景颜色
var passinfo = new HashMap(); // 存储客流信息，用于鼠标到客流点上

$(function() {
	//客流概览左右切换事件
	initSwitchFloor();
	//getFloorPassengerInfo(); // 获取店铺的场内人数
	getTotalPassengerFlowHour(); // 获取每小时的总客流
	
	$(".f_n").text(currentFloor); // 设置左上角的楼层信息
	$(".menu_layout").hide(); // 隐藏页面主体的顶部视图部分
	$(".cur_time").text(new Date().Format("hh:mm")); // 修改截止时间
	// 菜单事件，点击左侧第三级菜单（楼层菜单）的处理事件
	$(".cataloge_class_three li").click(function() {
		$(".custom_info_box").hide(); // 隐藏提示框
		var text = $(this).find('a').text();
		if (text == "监控概览") {
			
			$("#pfeView").hide(); // 隐藏客流楼层数据
			$(".menu_layout").hide(); // 隐藏页面主体的顶部视图部分
			$("#floorView").show(); // 显示客流楼层数据
		
		} else {

			currentFloor = floorMap.get(text); // 当前楼层
			posMap = new HashMap(); //  重置楼层数据
			for (var i = 0; i < customConfig[currentFloor].length; ++i) { // 存放店铺名称，以及店铺对应的位置信息(left，top值)
				posMap.put(customConfig[currentFloor][i].name.toUpperCase(), customConfig[currentFloor][i].pos);
			}
			$("#pfeView").show(); // 显示客流楼层数据
			$("#floorView").hide(); // 隐藏客流楼层数据
			$(".menu_layout").show(); // 隐藏页面主体的顶部视图部分
			$(".custom_pmt").css("background", "url('../resources/images/pfe/none_" + text + ".png')"); // 修改画面的楼层背景图片
			$('.cus_div').remove(); // 移除平面图中的div
			
			getFloorPassengerInfo(); // 获取店铺的场内人数
			
		}
		
	});
	// 点击叉号，隐藏框
	$(".close_btn").click(function(e) {
				$(".custom_info_box").hide(); // 隐藏提示框
				$(".cus_div").children().removeClass("text_on");
				$(".cus_div").children().removeClass("icon_on");
				e.preventDefault();
			});

	// 点击右下角的图标，处理平面图上的店铺
	$(".alarm_level_key i").click(function() {
				$(".cus_div").hide();
				$(".custom_info_box").hide(); // 隐藏提示框
				$(".p_" + $(this).attr("tip")).show();
			});

	// 修改右下角客流趋势图样式
	$(".trendlist a").click(function() {
			$(this).siblings().removeClass('trendChart');
			$(this).addClass('trendChart');
		});

	// 修改弹出框的客流趋势图样式
	$(".wd_custom_list a").click(function() {
			$(this).siblings().removeClass('trendChart');
			$(this).addClass('trendChart');
		});

	// 双击右下角的图标，显示平面图上的店铺
	$(".alarm_level_key i").dblclick(function() {
				$(".cus_div").show();
			});
			
	// 鼠标浮在某个店铺上，显示对应的内容框
	$(".cus_div").live("mouseenter", function() {
			$(this).find("i").addClass("icon_on");
			$(this).find("span").addClass("text_on");
			showTip(this);
		});
	
	getMonitoringInfo(); // 获取客流监控数据
	getWorkDayAndHoliDayAveragePassengerOfDay(); // 获取工作日和节假日日均客流
	getCompareYestodayInfo(); // 获取同比昨日的信息
});

// 获取同比昨日的信息
function getCompareYestodayInfo() {
	var url = baseurl + '/pfe/getCompareYestodayInfo';
    $.ajax({
        type: "POST",
        url: url,
        success: function (result) {
        	if(result.today != null){
        		try {
                    var todayTotal = result.today.inCount;
                    var yestodayTotal = result.yesterday.inCount;
                    var percent = ((todayTotal - yestodayTotal) / yestodayTotal * 100 ).toFixed(0);
                    if (parseInt(percent) < 0) {
                        percent = percent.substring(1);
                        $("#compareImg").text("↓");
                    } else {
                        $("#compareImg").text("↑");
                    }
                    $("#compareOfYestoday").text(percent); // 同比昨日
                } catch (e) {
                    console.log('error' + e);
                }
        	}
        }
    });
}

// 获取工作日和节假日日均客流
function getWorkDayAndHoliDayAveragePassengerOfDay() {
	var url = baseurl + '/pfe/findWorkDayAndHoliDayAveragePassengerOfDay';
	$.ajax({
				type : "POST",
				url : url,
				success : function(result) {
					$("#avgHolidayCount").text(result.avgHolidayCount);
					$("#avgWorkDayCount").text(result.avgWorkDayCount);
				},
				error : function(result) {
					console.log('error');
				}
			});
}

// 获取客流监控数据
function getMonitoringInfo(type) {
	var url = baseurl + '/pfe/' + (type == 'inside' ? 'getAllShopInsideOrder' : 'getAllShopOrder');
//	console.log("获取客流监控数据--前台url----" + url);
    $.ajax({
        type: "POST",
        url: url,
        data: {
            location: currentFloor,
            hundredText: hundredMap.get(currentFloor)
        },
        async: false, // 同步
        success: function (result) {
            try {
//            	console.log(result)
            	var floor = ["1F", "2F", "3F", "4F", "5F", "6F"];
				for (var i = 0; i < floor.length; i++) {
	            	var posMap = new HashMap(); //  重置楼层数据
					for (var a = 0; a < customConfig[floor[i]].length; a++) { // 存放店铺名称，以及店铺对应的位置信息(left，top值)
			            posMap.put(customConfig[floor[i]][a].name.toUpperCase(), customConfig[floor[i]][a].pos);
			        }
					var ge = 0, sum = 0; // 统计top4和楼层场内人数用
					for (var k = 0; k < result.length; k++) { // 存放店铺名称，以及店铺对应的位置信息(left，top值)

						var block = result[k].blockName.toUpperCase();
						if(posMap.get(block) != null) {

							$(".custom_floor_box:eq(" + i + ") > .hot_store:eq(" + ge + ") > .hot_i > .hot_name").attr("title", result[k].blockName.toUpperCase()); // 鼠标浮动显示店铺名

							var length = block.replace(/[^\x00-\xff]/g, 'xx').length; // 计算店铺名字占用的字节数
							if (length > 12) { // 店铺名字太长会导致，页面显示变形，在此做截串处理
								block = block.substring(0, 10) + "...";
							}
							var inCount = result[k].inCount < 0 ? 0 : result[k].inCount;
							$(".custom_floor_box:eq(" + i + ") > .hot_store:eq(" + ge + ") > .hot_i > .hot_name").text(block); // 店铺名
							$(".custom_floor_box:eq(" + i + ") > .hot_store:eq(" + ge + ") > .hot_i > .hot_num").text(inCount); // 店铺场内人数
							//sum = sum + inCount;
							ge++;
							//
							if (ge > 3 || i == 4 || i == 5) { // 个数大于3，标识统计出来了top4的热点店铺的场内人数总和，i=4和5表示五层、六层计算完百货数据即可
								//	$(".custom_floor_box > .person_count:eq(" + i + ") > .per_num").text(sum < 0 ? 0 : sum); // 楼层总的场内人数
								break;
							}
						}
					}
				}
				
            } catch (e) {
                console.log(e);
            }
            
        },
        error: function (result) {
            console.log('error');
        }
    });
}


// 查询具体店铺的客流数据
function getShopPassengerFlow(shopName) {
	$.ajax({
				type : "POST",
				url : baseurl + '/pfe/getShopPassengerFlow',
				data : {
					shopName : shopName
				},
				success : function(result) {
					try {
						var total = 0, num = 0, inDatalist = [], catalist = [], inSideDatalist = [], outDatalist = [],tempInner=0;
						for (var i = 0; i < result.length; i++) {
							total += result[i].inCount;
							inDatalist.push(result[i].inCount);
							tempInner=tempInner+result[i].insidePerson;
							inSideDatalist.push(tempInner);
							outDatalist.push(result[i].outCount);
							catalist.push(result[i].countTime);
						}

						trendMap.put("tip_totalInCountChart", inDatalist);
						trendMap.put("tip_totalOutCountChart", outDatalist);
						trendMap.put("tip_totalInSideChart", inSideDatalist);
						trendMap.put("catalist", catalist);

						staticPfeChart("tipChart", background2, false, inDatalist, catalist,'in'); // 渲染今日图表
						
					} catch (e) {
						console.log('error' + e);
					}
				},
				error : function(result) {
					console.log('error');
				}
			});
}



// 获取每小时的总客流
function getTotalPassengerFlowHour() {
	// 获取柱状图需要的数据
	var url = baseurl + '/pfe/getTotalPassengerFlowHour';
	$.ajax({
				type : "POST",
				url : url,
				success : function(result) {
					var totalInCount = 0, totalOutCount = 0, totalInSide = 0,totalInCountHn= 0,totalOutCountHn= 0,hn=0;
					try {
						var inDatalist = [], catalist = [], inSideDatalist = [], outDatalist = [];
						for (var i = 0; i < result.length; i++) {
							if(result[i].status=='HN'){
								totalInCountHn=result[i].inCount;
								totalOutCountHn=result[i].outCount;
								hn = 1;
							}else{
								totalInCount = totalInCount + result[i].inCount;
								totalOutCount = totalOutCount + result[i].outCount;
//							totalInSide = totalInSide + (result[i].insidePerson);
								inDatalist.push(result[i].inCount);
								inSideDatalist.push(result[i].insidePerson);
								outDatalist.push(result[i].outCount);
								catalist.push(result[i].countTime);
							}
						}

						// 以下是本地缓存
						trendMap.put("totalInCountChart", inDatalist);
						trendMap.put("totalOutCountChart", outDatalist);
						trendMap.put("totalInSideChart", inSideDatalist);
						trendMap.put("catalist", catalist);
						if(hn==1){
							totalInCount=totalInCountHn;
							totalOutCount=totalOutCountHn;
						}
						$("#totalInCount").text(totalInCount);
						$("#totalOutCount").text(totalOutCount);
						var insidecount = totalInCount - totalOutCount < 0 ? 0 : totalInCount - totalOutCount; // 场内人数如果小于0的话，则置为0
						$("#totalInSide").text(insidecount);
						staticPfeChart("todayChart", background1, false, inDatalist, catalist,'in'); // 渲染今日图表

					} catch (e) {
						console.log('error' + e);
					}
				},
				error : function(result) {
					console.log('error');
				}
			});
}

// 获取平面图中对应的客流信息
function getFloorPassengerInfo(type) {
	var url = baseurl + '/pfe/' + (type == 'inside' ? 'getAllShopInsideOrder' : 'getAllShopOrder');
//	console.log("客流量--前台url----" + url);
    var buildOrderList = [];
    $.ajax({
        type: "POST",
        url: url,
        data: {
            location: currentFloor,
            hundredText: hundredMap.get(currentFloor)
        },
        async: false, // 同步
        success: function (result) {
            try {
                hashMap = new HashMap();
                passinfo = new HashMap(); // 存储客流信息，用于鼠标到客流点上
                arrayList = [];

                for (var i = 0; i < result.length; ++i) {
                    var blockname = result[i].blockName, incount = result[i].inCount, outcount = result[i].outCount, insideCount = result[i].insidePerson;
                    if (blockname.indexOf("消防通道") != -1 || blockname.indexOf("步行街") != -1 || blockname.indexOf("外门") != -1) { // 过滤设备
                        continue;
                    } else {
                        var newblockname = blockname;
                        if (posMap.get(newblockname.toUpperCase()) == null)
                            continue;
                        tempList = [];
                        tempList.push({
                        	"blockname" : newblockname,
                        	"incount" : incount,
                        	"outcount" : outcount,
                        	"insideCount" : insideCount,
                        	"pos" : posMap.get(newblockname.toUpperCase())
                        });
                        passinfo.put(newblockname.toUpperCase() + "", tempList); // 存储客流信息，用于鼠标到客流点上
                        arrayList.push(tempList);
                        buildOrderList.push(tempList);
                        hashMap.put(newblockname, incount);
                    }
                }
                
                renderShopData(arrayList); // 生成客流店铺数据
            } catch (e) {
                console.log(e);
            }
            
        },
        error: function (result) {
            console.log('error');
        }
    });
}

// 生成图表
function staticPfeChart(id, color, isStatic, datalist, catalist) {
	// 客流量
//	var list = [0,0,0,0,0,0,0,73,100,120,140,200,180,130,300,400,460,460,370,350,180,100,73,0];
	if(isStatic) {
		catalist = [], datalist = [];
		var list = [50, 20, 30, 40, 50, 60, 100, 173, 300, 220, 140, 200, 180, 130,300,400,460,460,370,350,180,100,73,0];
		for(var i = 0; i < new Date().getHours(); i++) {
			catalist.push(i + ":00"); 
			datalist.push(list[i]);
		}
	}
	renderPassengerCharts(id, datalist, catalist, color); // 生成图表
}

// 切换客流趋势图
function trendChart(type, source) {

	if(source == "tip") { // 提示框内容
	
		var catalist = trendMap.get("tip_catalist"), datalist = []
		if (type == "in") { // 总进
			datalist = trendMap.get("tip_totalInCountChart");
		} else if (type == "out") { // 总出
			datalist = trendMap.get("tip_totalOutCountChart");
		} else if (type == "inside") { // 场内
			datalist = trendMap.get("tip_totalInSideChart");
		}
		staticPfeChart("tipChart", background2, false, datalist, catalist); // 渲染今日图表
		
	} else {
		
		var catalist = trendMap.get("catalist"), datalist = [];
		if (type == "in") { // 总进
			datalist = trendMap.get("totalInCountChart");
		} else if (type == "out") { // 总出
			datalist = trendMap.get("totalOutCountChart");
		} else if (type == "inside") { // 场内
			datalist = trendMap.get("totalInSideChart");
		}
		staticPfeChart("todayChart", background1, false, datalist, catalist); // 渲染今日图表
		
	}
	
	
}

// 生成客流店铺数据
function renderShopData(data) {
	var autolist = [];
	for (var i = 0; i < data.length; ++i) { // 存放店铺名称，以及店铺对应的位置信息(left，top值)
		var blockname = data[i][0].blockname, pos = data[i][0].pos[0];
		alert(blockname)
		if (i == 0) {
			$(".hot_shop").text(blockname); // 设置热点店铺
		}
		createPosInfo(blockname, pos.left, pos.top, data[i][0].incount);// 初始化显示B1层客流数据
		autolist.push(blockname);
	}
	// 搜索框自动补全
//	console.log('autolist');
//	$("#querytext").autocomplete({
//			source: autolist
//		});
//	console.log(autolist);
	
//	var autolist = [];
//	$(".cus_div").each(function() {
//				autolist.push($(this).attr("id"));
//			});
//			console.log(autolist); 
	$.AutoComplete('querytext', autolist);
}

// 根据店铺编号高亮显示该店铺
function findShopByCode(code) {
	var curClass = $("#" + code).attr("class"); // 当前样式
	$("#" + code).addClass(curClass.substring(curClass.indexOf(" ") + 1) + "_deep");
	showTip("#" + code);
}

// 处理显示信息框
function showTip(selector) {
	$(".cus_div").removeClass("p_fault_deep p_high_deep p_middle_deep p_low_deep"); // 移除背景加深样式
	$(selector).addClass($(selector).attr("class").substring($(selector).attr("class").indexOf(" ") + 1) + "_deep"); // 添加背景颜色加深样式
	$(".custom_info_box").css("left", $(selector).offset().left-20); // 重新确定left位置
	$(".custom_info_box").css("top", $(selector).offset().top+40); // 重新确定top位置
	$(".custom_info_box").show();
	$("#tipText").text($(selector).attr("id")); // 更新弹出框店铺名
	var zongjin = $(selector).find("input[name='inCount']").val();
	var zongchu = $(selector).find("input[name='outCount']").val();
	$("#zongjin").text(zongjin);
	$("#zongchu").text(zongchu);
	
	var changnei = parseInt(zongjin) - parseInt(zongchu);
	changnei = changnei > 0 ? changnei : 0;
	$("#changnei").text(changnei);
	staticPfeChart("tipChart", "#B9B9B9"); // 渲染提示图表
	getShopPassengerFlow($(selector).attr("id")); // 查询店铺客流趋势图
}

// 客流概览左右切换事件
function initSwitchFloor(){
	var maxFloor = $(".custom_floor_box").length;
	if(maxFloor > 6) {
		$("#switch_floor").find("#a").css('display','block');
	}else{
		$("#switch_floor").find("a").css('display','none');
	}
	var i = 0;
	$("#switch_floor a").click(function(){
		var text = $(this).text();
		if(text == '‹' && i > 0) {
			i--;
			if(i==0){
				$('.custom_floor_box:eq(0)').show();
			}else{
				$('.custom_floor_box:gt(0)').each(function(){
					$(this).show();
				});
			}
		} else if(text == '›' && (maxFloor - i > 6)) {
			i++;
			$('.custom_floor_box:lt(' + i + ')').each(function(){
				$(this).hide();
			});
		}
		return false;
	});
}