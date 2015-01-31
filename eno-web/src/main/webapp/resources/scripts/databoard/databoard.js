$(function() {

			var catalist = [], datalist = [];
			for (var i = 0; i < 24; i++) {
				catalist.push(i); // + ":00"
				datalist.push(parseFloat((Math.random() * 50).toFixed(1)));
			}
			dashboard.buildTodayEnergyChart(datalist, catalist); // 生成今日用能图表

			// 改变“求值范围”触发的事件
			$("#aggregatefunction").on("change", function() {
						var val = $(this).val().toUpperCase();
						$("#range").val(""); // 重置取值规则的值
						$("#range").attr("disabled","disabled"); // 禁用下拉列表
						if (val == "CHANGE") {
							$("#additioncontion").attr("placeholder", "该项为SUM或AVG");
						} else if (val == "PERCENT") {
							$("#range").removeAttr("disabled"); // 启用下拉列表
							$("#additioncontion").attr("placeholder", "该项为被除数的名称");
						}
					});

		});

// 点击“渲染”触发的事件
function renderChart() {
	var action = CONTEXT_PATH + "/databoard/findRequestData";
	var params = {
		pointname : $("#pointname").val(),
		additioncontion : $("#additioncontion").val(),
		aggregatefunction : $("#aggregatefunction").val(),
		timeend : $("#timeend").val(),
		timescales : $("#timescales").val(),
		timestart : $("#timestart").val(),
		range : $("#range").val(),
		timeformat : $("#timeformat").val()
	};
	$.post(action, params, function(data) {
				console.log(data);
				$("#sql").html(); // 设置sql语句到页面上
				if (data) {
					$("#sql").html("查询的sql语句：" + data.sql == undefined ? "" : data.sql); // 设置sql语句到页面上
					dashboard.buildTodayEnergyChart(data.datalist, data.catalist); // 生成今日用能图表
					if($("#aggregatefunction").val() == "PERCENT") { // percent进行提示
						alert("percent计算出来的值为【" + data.percent + "】");
					}
				}
			});
}


// 点击“1渲染1”触发的事件
function testRenderChart() {
	var action = CONTEXT_PATH + "/energyManage/getDataAndCataList";
	var params = {
		pointname : $("#pointname1").val(),
//		additioncontion : '1,1', //$("#additioncontion1").val(),
		aggregatefunction : $("#aggregatefunction1").val(),
		timeend : $("#timeend1").val(),
		timescales : $("#timescales1").val(),
		timestart : $("#timestart1").val(),
//		range : '1,1', //$("#range1").val(),
		timeformat : 'HH'
	};
	$.post(action, params, function(data) {
				console.log(data);
				var catalist = data.cata;
				console.log(catalist[0].length>catalist[1].length);
				console.log(catalist);
				var datalist = data.data;
				console.log(datalist);
			});
}

var dashboard = {

	// 生成今日用能图表
	buildTodayEnergyChart : function(datalist, catalist) {

//		var catalist = [], data1 = [], data2 = [];
//		for (var i = 0; i < 24; i++) {
//			catalist.push(i); // + ":00"
//			data1.push(parseFloat((Math.random() * 50).toFixed(1)));
//			data2.push(parseFloat((Math.random() * 15).toFixed(1)));
//		}
		var columnColor = '#01C690', lineColor = '#FEC000';
		$('#todayenergy').highcharts({
					chart : {
						backgroundColor : '#000000',
						marginBottom : 50
					},
					legend : {
						enabled : false,
						x : 240,
						y : 10
					},
					xAxis : {
						categories : catalist,
						labels : {
							step : parseInt(catalist.length / 6),
							style : {
								color : '#F3F3F3',
								fontSize : '12px',
								fontFamily : 'RobotoRegular'
							}
						}
					},
//					yAxis : [{ // Primary yAxis
//							}, { // Secondary yAxis
//								opposite : true
//							}],
					tooltip : {
						crosshairs : true,
						shared : true
					},
					series : [{
//								type : 'column',
//								borderColor : columnColor,
////								name : '实时用电（kWh）',
//								color : columnColor,
//								data : datalist
//							}, {
								type : 'line',
//								name : '室外温度（℃）',
								color : lineColor,
								data : datalist,
//								yAxis : 1,
								lineWidth : 2,
								marker : {
//									radius : 3,
									lineColor : lineColor
									,lineWidth : 2,
									fillColor : 'white'
								}
							}]
				});

	}

};
