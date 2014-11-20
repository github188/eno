$(function() {
			$("#content_1").mCustomScrollbar({
						scrollButtons : {
							enable : true
						},
						advanced : {
							updateOnContentResize : true
						}
					});

			$(".pfe_flip").on("click", function(e) {
						$(".pfe_custom_rank").animate({
									"margin-right" : "-285px",
									"opacity" : "0"
								}, 1000, function() {
									$(".pfe_custom_rank").hide();
								});
					});

			$(".pfe_show_rank").on("click", function() {
						$(".pfe_custom_rank").show();
						$(".pfe_custom_rank").animate({
									"margin-right" : "0px",
									"opacity" : "1"
								}, 1000, function() {
									$(".pfe_custom_rank").show();
								});
					})
		})

/**
 * 生成图表，并给下方面板赋值
 * @param {} catalist
 * @param {} datalist
 * @param {} buttomValueList
 */
function buildChart(catalist, datalist, buttomValueList) {
	
//	var cataList = [];
//	for (var i = 0; i < 12; i++) {
//		cataList.push(i + ":00");
//	}
	$('#pfe_hour').highcharts({
            chart: {
                type : 'column',
				borderRadius : 3,
				spacingBottom : 10,
				spacingTop : 30,
				width: $("#pfe_hour").attr("width")
            },
            title: {
                text: ''
            },
            subtitle: {
                text: ''
            },
            xAxis: {
                categories: catalist,
                labels : {// x轴坐标数字
					step : 4
				},
				lineColor : '#C8D59D',// x轴线颜色
				lineWidth : 1,// x轴线宽度
				tickLength : 0 // X轴短线长度
            },
            yAxis: {
                min : 0,
	            title : {//y轴标题
	                enabled : false,
					text : '人数'
	            },
	            gridLineDashStyle:'ShortDashDot',//网格横线类型
	            labels:{//y轴坐标数字
	               enabled:false
	            }
            },
            legend:{ // 图例
	            enabled : false
	        },
            plotOptions: {
	            series: {
	                lineWidth: 1,//线宽
	                marker:{
	                    states:{
	                        hover:{
	                            fillColor:"#68C8CA",
	                            lineWidth:2,
	                            radius:0
	                        }
	                    },
	                    radius: 0
	                }
	            }
	        },
            series: [{
            	color : "#68C8CA",
                name : '客流',
                data : datalist
            }]
        });
        
        if(buttomValueList) {
	        $(".pfe_total_text").text(buttomValueList.pfe_total); // 总人数
	        $(".compare_day").text(Math.floor(buttomValueList.compare_day * 100) + "%"); // 同比昨日
	        $(".pfe_avg_month:eq(0)").text(buttomValueList.pfe_avg_month + "人"); // 本月日均客流量
	        $(".pfe_avg_year").text(buttomValueList.pfe_avg_year + "人"); // 本年日均客流量
        }
        // 以下为备份内容
//        $(".pfe_total_text").text(Math.floor(Math.random() * 10000 + 1)); // 总人数
//        $(".compare_day").text(Math.floor(Math.random() * 10 + 1)); // 同比昨日
//        $(".pfe_avg_month:eq(0)").text(Math.floor(Math.random() * 3000 + 1806)); // 本月日均客流量
//        $(".pfe_avg_year").text(Math.floor(Math.random() * 3000 + 2104)); // 本年日均客流量
}

// 排名
function createRankList(shopname, sum, sort) {
	var $tr = $("<tr/>");
	var $td1 = $("<td/>",{
		text : shopname
	});
	var $td2 = $("<td/>",{
		text : sum + "人"
	});
	var $td3 = $("<td/>",{
		html : "<img src='../../resources/images/smalluparrow.png'>"
	});

	$tr.append($td1);
	$tr.append($td2);
	$tr.append($td3);

	$tr.appendTo($("#pfe_content table > tbody"));
}