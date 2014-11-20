// 格式化数字格式，如：123456.789格式化成123,456.789
function formatNumber(numStr, separator) {
	return (numStr + "").replace(/(\d{3})\B/g, "$1" + (!separator ? "," : separator));
}

// 根据day得到对应的中文汉字
function getTimeText(day){
	var text = '一';
	switch(day){
		case 1:
			text = '一';
			break;
		case 2:
			text = '二';
			break;
		case 3:
			text = '三';
			break;
		case 4:
			text = '四';
			break;
		case 5:
			text = '五';
			break;
		case 6:
			text = '六';
			break;
		case 7:
			text = '日';
			break;
	}
	return text;
}

// 获取   访客、冷热量变化、室内温度变化    图表 
function getHicharts(chartId,chartType,backgroundColor,width,height,borderRadius,spacingBottom,categories,data,dataName,dataU,fillColor){
	$("#"+chartId).highcharts({
		chart:{
			type:chartType,
			//backgroundColor:backgroundColor,
			height:height,
			width:width,
			borderRadius:borderRadius,
			spacingBottom:spacingBottom,
			spacingTop:1
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
				enabled:false
			},
			categories:categories,//x轴坐标在tooltip中的显示
			labels:{//x轴坐标数字
				enabled:false
			},
			lineColor:'#C8D59D',//x轴线颜色
			lineWidth:1,//x轴线宽度
			tickLength:0//X轴短线长度
		},
		yAxis: {
			title:{//y轴标题
				enabled:false
			},
			gridLineDashStyle:'ShortDashDot',//网格横线类型
			labels:{//y轴坐标数字
				enabled:false
			}
		},
		tooltip: {
			// shared:true,
			// useHTML: true,//使用html标签
            // headerFormat: '<small>{point.key}</small><table>',//标签第一行：显示x轴数据
            // pointFormat: '<tr><td>{series.name}: </td>' +
            // '<td style="text-align: right"><b>{point.y}'+dataU+'</b></td></tr>',//标签第二行：显示y轴数据+单位（人次）
            // footerFormat: '</table>'
		},
		colors:['#999999'],//线色
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
			name:dataName,//数据名：显示在tooltip第一行
			data: data
		}]
	});
}

// 获取  空调系统用电、照明系统用电、办公设备   图表 
function getMixCharts(chartId,data1,data0,data1Name,PieColor,Yzong,Tzong,Ydata,Tdata){
	$("#"+chartId).highcharts({
			chart:{
				borderRadius:0,
				backgroundColor:'#FFFFFF',
				borderWidth:0,
				spacingTop:0,
				spacingBottom:10,
				height:210
			},
			title:{//设置一级标题
				y:32,
				x:200,
				text: '<span style="font-family: Microsoft YaHei;color: #999;">kWh(度)</span>' //标题的名称
			},
			subtitle:{//设置二级标题
				text:Math.floor((data1/data0)*100)+"%",//-----------------------data1/data0-----------------
				verticalAlign:'middle',
				y:22,
				x:-115,
				style:{
					fontSize:'40px',
					//fontWeight:'bold',
					fontFamily: 'Roboto Th'
				}
			},
			plotOptions:{
				column:{
					stacking:'normal'//设置可以让柱形图相叠
					//,borderWidth: 1
					//,pointWidth :48	//设置column的粗细
					//,pointPadding : 0
			        // 如果x轴一个点有两个柱，则这个属性设置的是这两个柱的间距。
			       // ,groupPadding : 0
				},
				pie:{
					allowPointSelect:false,
					dataLabels:{
						enabled:false//可以设置圆的标识是否隐藏
					},
					marker: {
		                enabled: false,   //不显示点
		                states: {
		                    hover: {
		                        enabled: false
		                    }
		                }
		            }
				}
			},
			xAxis:{
				categories:['','','\n昨日','今日'],
				lineWidth:0,//去掉x轴
				tickWidth:0,//去掉x轴上的点
				labels:{//设置‘今日’的粗细
					style:{
						fontWeight:'bold',
						fontSize:'15px',
						fontFamily:'Roboto Th'
					}
				}
			},
			yAxis:{
				title:{
					text:null
				},
				labels:{
					enabled:false
				},
				gridLineWidth:0//去掉y轴的线
			},
			series:[{
				type:'pie',
				innerSize:'16',
				name:" ",
				size:105,//饼图环宽
				data:[{
					name:'其他',
					y:data0-data1,//-----------------------------------------------data0-data1
					color:'#E4E5DE'		
				},{
					name:data1Name,//---------------------------------data1Name
					y:data1,//------------------------------------------------data1
					color:PieColor//----------------------------------------
				}],
				borderWidth: 1,
				center:[90,40] // 可以设置圆的位置 
			},{
				type:'column',
				name:'',
				data:[null,null,Yzong-Ydata,Tzong-Tdata],
				showInLegend: false,
				dataLabels: {
					y :55
				}
			},{
				type:'column',
				name:'普通',
				data:[null,null,{y:Ydata,color:'#5DDBD8'},{y:Tdata,color:'#7E4E74'}],
				showInLegend: false
			}],
			colors:['#E4E5DE','#C40A12'],
			credits:{//去掉右下角的官网链接
				enabled:false
			},
			tooltip:{
				pointFormat: '{series.name}用电量:<b>{point.y}kWh</b>'
			}
		});
}

var _width = 470; // 本页面中有几处地方用到
var _height = 210; // 本页面中有几处地方用到

var begin=7/12;
var end=29/12;
var lang=11/6;
var width=12;
var canB1;
var canM1;
var canT1;
var Olddata1;
var Newdata1;
var base1;
var colB1;
var cole1;
var incre1;
var canB2;
var canM2;
var canT2;
var base2;
var colB2;
var cole2;
var incre2;
var canB3;
var canM3;
var canT3;
var Olddata3;
var Newdata3;
var base3;
var colB3;
var cole3;
var incre3;
var count;
// 生成动态图
function getCircles(can1, can2, can3, color10, color11) {
	canB1 = can1;
	canM1 = can2;
	canT1 = can3;
	colB1 = color10;
	colE1 = color11;
	count = 0;
	drawBottom();
}

function drawBottom(){
	var canvas=document.getElementById(canB1);//底环
	var e=(2+5/12);//底环结束弧度
	//画底层环1
	var context1=canvas.getContext("2d");
	context1.clearRect(0,0,200,200);
	context1.beginPath();
	context1.arc(95,105,70,begin*Math.PI,e*Math.PI,false);
	context1.lineWidth=width;//环宽
	context1.strokeStyle = "#C0C0C0";//环色
	context1.stroke();
	canvas=document.getElementById(canB2);//底环
	circleInit();
}

function circleInit(){
	count=count+1;//全局计数+1
	
	var canvas1=document.getElementById(canM1);//表层环
	var e1=precents_val*lang/200;//把应画的圆环（r*L）分为200份
	var context1=canvas1.getContext("2d");
	context1.clearRect(0,0,200,200);//清理背景
	context1.beginPath();
	context1.arc(95,105,70,begin*Math.PI,(begin+e1*count)*Math.PI,false);
	context1.lineWidth=width;//环宽
	var grd1=context1.createLinearGradient(0,0,150,150);//颜色渲染（x0,y0,x1,y1）
	grd1.addColorStop(0,colB1);//起始颜色
	grd1.addColorStop(1,colE1);//结束颜色
	context1.strokeStyle = grd1;//环色
	context1.stroke();
	
	if(count<200){//递归调用画表层圆环的方法，直到完整画出表层园环
		t=setTimeout("circleInit()",5);//每1ms调用一次
	}else{
		clearTimeout(t);
		//画表层环
	}	
}

// 仪表盘-访客信息
var Dashboard_Visitor = {
		getChart : function() {
			if(isNotStatic) { // true则使用静态数据
				
				Dashboard_Visitor.getStatic();
				
			} else {
				
				// 此处是请求图表生成的数据
				name = 'flow_passenger'; // 此处是假设的name的参数值，前台ajax请求时请传递对应个数的值
				id = 'indoor_1'; // 此处是假设的id值，前台ajax请求时请传递对应个数的值
				ispd = '1'; // 此处是假设的id值，前台ajax请求时请传递对应个数的值
				
				var url = baseurl + '/Chart/GetChartData?name=' + name + '&id=' + id + '&type=' + type + '&tfrom=' + _now_tfrom + '&tto=' + _now_tto + '&ispd=' + ispd + '&ipaddress=' + ipaddress + '&port=' + port + '&beforeFormat=yyyy-MM-dd HH:mm:ss&afterFormat=HH:mm';
				console.log("仪表盘--客流量--前台url----" + url);
				$.ajax({
					type : "POST",
					url : url,
					success : function(result) {
			           try{
			        	   var dataList = result.datalist; // 此处为数据列表，数组形式，如：datalist[0]即可取到第一组数据 
			   			   getHicharts("wd_visitors","line","#A5BA51",_width,_height,3,10,result.catalist,dataList[0],'访客','人次',"#A5BA51"); // 生成图表
			           } catch(e) {
			        	   Dashboard_Visitor.getStatic();
			               console.log('Dashboard_Visitor--error'+e);
			           }
					},
					error : function(result) {
						console.log('error_Dashboard_Visitor');
					}
				});
				
			}
		},
		getStatic : function() {
			
			// 显示访客信息fk
			var categories = [],tempData = [], data = [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,20,20,20,20,20,20,20,20,20,40,40,40,40,40,40,40,40,10,10,10,10,10,10,140,30,110,110,110,110,110,110,420,120,120,120,330,130,130,130,130,130,130,130,130,1540,1540,1540,190,190,140,140,140,140,460,180,180,80,80,80,80,80,80,820,820,190,190,190,570,570,220,220,140,140,140,140,140,140,140,140,140,1290,80,80,80,80,80,780,780,780,780,780,780,1340,1340,1340,1340,440,440,440,440,440,440,440,440,440,440,440,440,440,440,1640,1640,1640,1640,1640,1640,1640,1640,1640,1640,1640,1640,1640,1640,1640,610,610,610,610,610,610,610,610,610,610,610,610,610,610,610,610,610,610,610,610,610,610,610,610,610,610,610,610,610,610,610,610,610,610,610,610,610,610,610,610,610,610,610,610,610,610,610,610,610,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10,10];
			var loop = (new Date().getHours() * 60 + new Date().getMinutes()) / 5; // 定义循环次数
			var hour = 0; // 时
			for ( var i = 0; i < loop; i++ ) {
				var min = (i % 12 * 5);
				categories.push( ((hour + "").length == 1 ? ("0" + hour) : hour) + ":" + ((min + "").length == 1 ? ("0" + min) : min));
				tempData.push(data[i]);
				if( (i + 1) % 12 == 0 ) hour = parseInt(hour) + 1; // 重置小时数
			}
			getHicharts("wd_visitors","line","#A5BA51",_width,_height,3,10,categories,tempData,'访客','人次',"#A5BA51"); // 生成图表
			
		}
};

// 仪表盘-冷热量变化信息
var Dashboard_Temp = {
		getChart : function() {
			if(isNotStatic) { // true则使用静态数据
				
				Dashboard_Temp.getStatic();
				
			} else {
				
				// 此处是请求图表生成的数据
				name = 'cooling_capacity'; // 此处是假设的name的参数值，前台ajax请求时请传递对应个数的值
				id = 'chiller'; // 此处是假设的id值，前台ajax请求时请传递对应个数的值
				ispd = '1'; // 此处是假设的id值，前台ajax请求时请传递对应个数的值
				
				var url = baseurl + '/Chart/GetChartData?name=' + name + '&id=' + id + '&type=' + type + '&tfrom=' + _now_tfrom + '&tto=' + _now_tto + '&ispd=' + ispd + '&ipaddress=' + ipaddress + '&port=' + port + '&beforeFormat=yyyy-MM-dd HH:mm:ss&afterFormat=HH:mm';
				console.log("仪表盘--冷热量变化--前台url----" + url);
				$.ajax({
					type : "POST",
					url : url,
					success : function(result) {
						try{
							var dataList = result.datalist; // 此处为数据列表，数组形式，如：datalist[0]即可取到第一组数据 
							getHicharts("wd_temperature","line","#0D0502",_width,_height,0,22,result.catalist,dataList[0],'冷热量','℃',"gray");// 生成图表
						} catch(e) {
							Dashboard_Temp.getStatic();
							console.log('Dashboard_Temp--error'+e);
						}
						
					},
					error : function(result) {
						console.log('error_Dashboard_Temp');
					}
				});
			}
		},
		getStatic : function() {
			
			// 显示冷热量变化
			var categories = [],tempData = [], data = [ 10, 10, 10, 10, 10, 10, 15, 30, 75, 130, 150,160, 120, 170, 220, 160, 80, 20, 10, 10, 10, 10, 10, 10];
			for ( var i = 0; i <= new Date().getHours(); i++) {
				categories.push(i + ":00");
				tempData.push(data[i]);
			}
			getHicharts("wd_temperature","line","#0D0502",_width,_height,0,22,categories,tempData,'冷热量','℃',"gray");// 生成图表
			
		}
};

// 仪表盘-室内温度变化信息
var Dashboard_IndoorTemp = {
		getChart : function() {
			if(isNotStatic) { // true则使用静态数据
				
				Dashboard_IndoorTemp.getStatic();
				
			} else {
				
				// 此处是请求图表生成的数据
				name = 't_ia'; // 此处是假设的name的参数值，前台ajax请求时请传递对应个数的值
				id = 'indoor_1'; // 此处是假设的id值，前台ajax请求时请传递对应个数的值
				ispd = '1'; // 此处是假设的id值，前台ajax请求时请传递对应个数的值
				
				var url = baseurl + '/Chart/GetChartData?name=' + name + '&id=' + id + '&type=' + type + '&tfrom=' + _now_tfrom + '&tto=' + _now_tto + '&ispd=' + ispd + '&ipaddress=' + ipaddress + '&port=' + port + '&beforeFormat=yyyy-MM-dd HH:mm:ss&afterFormat=HH:mm';
				console.log("仪表盘--室内温度变化--前台url----" + url);
				$.ajax({
					type : "POST",
					url : url,
					success : function(result) {
						try{
							var dataList = result.datalist; // 此处为数据列表，数组形式，如：datalist[0]即可取到第一组数据 
							
							var temp_cata = [],temp_data = [];
							for(var i=0; i<result.catalist.length;i++){
								if(i%2 == 0){
									temp_cata.push(result.catalist[i]);
									temp_data.push(dataList[0][i]);
								}
							}
							
							getHicharts("wd_indoor_temperature","line","#0E0402",_width,_height,0,2,temp_cata,temp_data,'温度','℃',"#0E0402");// 生成图表
						}catch(e){
							Dashboard_IndoorTemp.getStatic();
							console.log('Dashboard_IndoorTemp--error'+e);
						}
					},
					error : function(result) {
						console.log('error_Dashboard_IndoorTemp');
					}
				});
				
			}
		},
		getStatic : function() {
			
			// 显示室内温度变化
			var categories = [],tempData = [], data = [-7.6,-7.7,-7.7,-7.8,-7.9,-7.9,-7.9,-7.9,-7.9,-7.9,-7.9,-7.8,-7.8,-7.8,-7.8,-7.8,-7.8,-7.9,-7.8,-7.9,-7.9,-7.9,-7.9,-7.9,-7.9,-7.9,-7.9,-7.9,-7.9,-7.9,-7.9,-7.9,-7.8,-7.8,-7.8,-7.7,-7.7,-7.7,-7.7,-7.6,-7.6,-7.6,-7.6,-7.6,-7.6,-7.6,-7.6,-7.5,-7.5,-7.5,-7.5,-7.4,-7.5,-7.5,-7.5,-7.5,-7.5,-7.5,-7.5,-7.5,-7.5,-7.5,-7.5,-7.5,-7.5,-7.5,-7.4,-7.4,-7.4,-7.4,-7.4,-7.4,-7.4,-7.4,-7.3,-7.3,-7.2,-7.3,-7.3,-7.3,-7.0,-7.1,-7.1,-6.9,-6.9,-6.8,-6.8,-6.6,-6.5,-6.4,-6.2,-6.0,-5.9,-5.9,-6.0,-6.0,-6.0,-6.0,-5.9,-5.8,-5.7,-5.8,-5.8,-5.3,-5.0,-4.9,-4.9,-5.0,-5.0,-4.9,-4.6,-3.8,-3.0,-2.7,-2.6,-2.7,-3.1,-3.2,-3.1,-2.4,-2.3,-2.2,-2.7,-3.2,-2.9,-2.3,-2.1,-1.7,-1.7,-2.8,-3.3,-3.4,-3.4,-3.5,-2.8,-2.8,-2.8,-3.2,-3.2,-3.4,-3.1,-2.8,-2.2,-2.1,-2.1,-2.2,-2.3,-2.3,-2.3,-4.4,-4.6,-4.4,-4.0,-1.4,-1.9,-1.8,-2.0,-1.8,-2.2,-1.5,-1.3,-1.2,-1.1,-1.6,-1.3,-1.2,-1.8,-1.7,-1.5,-1.4,-1.5,-1.4,-1.1,-1.5,-1.3,-1.2,-1.7,-1.5,-1.4,-1.8,-1.7,-1.7,-1.8,-1.0,-1.1,-1.6,-1.6,-1.6,-1.7,-1.8,-2.1,-2.1,-2.4,-2.3,-2.3,-2.4,-2.0,-2.2,-2.2,-2.4,-2.4,-2.7,-2.9,-2.9,-2.8,-2.7,-2.7,-2.7,-5.6,-5.6,-5.6,-5.8,-5.9,-6.0,-6.0,-4.0,-4.1,-4.3,-4.2,-4.3,-4.5,-4.5,-4.6,-4.7,-4.7,-4.7,-4.8,-4.9,-5.1,-5.1,-5.2,-5.3,-5.5,-5.6,-5.6,-5.7,-5.8,-5.8,-5.9,-5.9,-5.9,-6.0,-5.9,-6.0,-6.0,-5.9,-5.9,-6.0,-6.0,-5.9,-6.0,-6.0,-6.1,-6.2,-6.3,-6.3,-6.4,-6.4,-6.3,-6.3,-6.3,-6.3,-6.4,-6.5,-6.5,-6.5,-6.5,-6.6,-6.6,-6.7,-6.7,-6.8,-6.7,-6.8,-6.8,-6.8,-6.8,-6.9,-6.8,-6.8,-6.7,-6.7,-6.7,-6.7,-6.7,-6.8,-6.7,-6.7];
			var loop = (new Date().getHours() * 60 + new Date().getMinutes()) / 5; // 定义循环次数
			var hour = 0; // 时
			for ( var i = 0; i < loop; i++ ) {
				var min = (i % 12 * 5);
				categories.push( ((hour + "").length == 1 ? ("0" + hour) : hour) + ":" + ((min + "").length == 1 ? ("0" + min) : min));
				tempData.push(data[i]);
				if( (i + 1) % 12 == 0 ) hour = parseInt(hour) + 1; // 重置小时数
			}
			getHicharts("wd_indoor_temperature","line","#0E0402",_width,_height,0,2,categories,tempData,'温度','℃',"#0E0402");// 生成图表
			
		}
};

// 仪表盘-总能耗、今日能耗、总碳排放量、总用电量、今日用电量
var Dashboard_Energy_Total = {
		getChart : function() {
			if(isNotStatic) { // true则使用静态数据
				
				Dashboard_Energy_Total.getStatic();
				
			} else {
				
				var temp_today_ele = 0,temp_total_ele = 0;
				// 此处是请求图表生成的数据
				name = 'energy,electricity'; // 此处是假设的name的参数值，前台ajax请求时请传递对应个数的值
				id = 'total,total'; // 此处是假设的id值，前台ajax请求时请传递对应个数的值
				ispd = '1,1'; // 此处是假设的id值，前台ajax请求时请传递对应个数的值
				
				var url = baseurl + '/Chart/GetChartData?name=' + name + '&id=' + id + '&type=' + type + '&ispd=' + ispd + '&tfrom=' + _now_tfrom + '&tto=' + _now_tto + '&attribute=sum&ipaddress=' + ipaddress + '&port=' + port;
				console.log("仪表盘--今日能耗、今日用电量--前台url----" + url);
				$.ajax({
					type : "POST",
					url : url,
					async : false,
					success : function(result) {
						try{
							var dataList = result.datalist; // 此处为数据列表，数组形式，如：datalist[0]即可取到第一组数据 ，分别是今日能耗、今日用电量
							$('#today_energy_total').text(formatNumber(dataList[0][0])); // 为今日总能耗赋值
							temp_today_ele = parseFloat(dataList[1][0]);
							$('#ele_today').text(formatNumber(temp_today_ele)); // 今日用电量
						}catch(e){
							temp_today_ele = parseFloat('16183');
							$('#today_energy_total').text('528,123'); // 为今日总能耗赋值
							$('#ele_today').text(formatNumber(temp_today_ele)); // 今日用电量
							console.log('Dashboard_Energy_Total--error'+e);
						}
					},
					error : function(result) {
						console.log('error_Dashboard_Energy_Total');
					}
				});

				
				// 此处是请求图表生成的数据
				name = 'energy,carbon,electricity'; // 此处是假设的name的参数值，前台ajax请求时请传递对应个数的值
				id = 'total,total,total'; // 此处是假设的id值，前台ajax请求时请传递对应个数的值
				ispd = '0,0,0'; // 此处是假设的id值，前台ajax请求时请传递对应个数的值
				
				var url = baseurl + '/Chart/GetChartData?name=' + name + '&id=' + id + '&type=year' + '&ispd=' + ispd + '&tfrom=' + new Date().getFullYear()  + '-01-01&tto=' + (new Date().getFullYear() + 1) + '-01-01&attribute=sum&ipaddress=' + ipaddress + '&port=' + port;
				console.log("仪表盘--总能耗、总碳排放、总用电量--前台url----" + url);
				$.ajax({
					type : "POST",
					url : url,
					success : function(result) {
						try{
							var dataList = result.datalist; // 此处为数据列表，数组形式，如：datalist[0]即可取到第一组数据 ，分别是今日能耗、今日用电量
							$('#energy_total_num').text(formatNumber(dataList[0][0])); // 为今日总能耗赋值
							$('#energy_carbon_total').text(formatNumber(dataList[1][0])); // 为总碳排放赋值
							$('#ele_total_current').text(formatNumber(dataList[2][0])); // 累积用电量
							//$('#flow_passenger_total').text(formatNumber(dataList[3][0])); // 访客总人数
						}catch(e){
//							$('#energy_total_num').text('528,123'); // 为今日总能耗赋值
//							$('#energy_carbon_total').text('2,768,123'); // 为总碳排放赋值
//							$('#ele_total_current').text(formatNumber(temp_total_ele)); // 累积用电量
//							$('#flow_passenger_total').text('45,325'); // 访客总人数
							console.log('Dashboard_Energy_Total--error'+e);
						}
					},
					error : function(result) {
						console.log('error_Dashboard_Energy_Total');
					}
				});

				// 圆环初始化圆
				try{
					precents_val = temp_today_ele / temp_total_ele ;
					precents_val = (temp_total_ele == 0) ? 0 : precents_val;
				}catch(e){}
		        getCircles("canvas1","canvas2","canvas3","#F5B551","#B86E4B");
		        
		        // 求访客总人数
		        var url = baseurl + '/pfe/getTotalPassengerFlow';
		        $.ajax({
		            type : "POST",
		            url : url,
		            success : function(result) {
		                try{
		                    var todayTotal = result.todayTotal;
		                    $('#flow_passenger_total').text(formatNumber(todayTotal)); // 访客总人数
		                } catch(e) {
		                	$('#flow_passenger_total').text('15,325'); // 访客总人数
		                }
		            },
		            error : function(result) {
		                $('#flow_passenger_total').text('15,325'); // 访客总人数
		            }
		        });
			}
		},
		getStatic : function() {
			$('#energy_total_num').text('2,528,123'); // 为总能耗赋值
			$('#today_energy_total').text('528,123'); // 为今日总能耗赋值
			$('#energy_carbon_total').text('2,768,123'); // 为总碳排放赋值
			$('#ele_today').text('16,183'); // 今日用电量
			$('#ele_total_current').text('241,052'); // 累积用电量
			$('#flow_passenger_total').text('15,325'); // 访客总人数

		    var elec0 = 2410512, elec1 = 1200012; precents_val = elec1 / elec0;
		    getCircles("canvas1", "canvas2", "canvas3", "#F5B551", "#B86E4B");
		}
};

// 仪表盘-右侧用电量
var Dashboard_Electricity = {
		getChart : function() {
			if(isNotStatic) { // true则使用静态数据
				
				Dashboard_Electricity.getStatic();
				
			} else {
				
				// 此处是请求图表生成的数据
				name = 'electricity,electricity,electricity'; // 此处是假设的name的参数值，前台ajax请求时请传递对应个数的值
				id = 'hvac,lighting,elevator'; // 此处是假设的id值，前台ajax请求时请传递对应个数的值
				ispd = '1,1,1'; // 此处是假设的id值，前台ajax请求时请传递对应个数的值
				attribute = 'percents';
				var url = baseurl + '/Chart/GetChartData?name=' + name + '&id=' + id + '&type=' + type + '&tfrom=' + _now_tfrom + '&tto=' + _now_tto + '&attribute=' + attribute + '&ispd=' + ispd + '&ipaddress=' + ipaddress + '&port=' + port;
				console.log("仪表盘--右侧用电量--前台url----" + url);
				$.ajax({
					type : "POST",
					url : url,
					async : false,
					success : function(result) {
						try{
							// 求出饼图的占比
							var dataList = result.datalist; // 此处为数据列表，数组形式，如：datalist[0]即可取到第一组数据
							
							// 求昨日的用电情况
							var url = baseurl + '/Chart/GetChartData?name=' + name + '&id=' + id + '&type=' + type + '&tfrom=' + _lastDay + '&tto=' + _now_tfrom + '&attribute=sum' + '&ispd=' + ispd + '&ipaddress=' + ipaddress + '&port=' + port;
							console.log("仪表盘---右侧昨日用电量--前台url----" + url);
							$.ajax({
								type : "POST",
								url : url,
								async : false,
								success : function(result) {
									// 昨日的用电量
									var lastDayData = result.datalist;
									
									// 今日的用电量
									var url = baseurl + '/Chart/GetChartData?name=' + name + '&id=' + id + '&type=' + type + '&tfrom=' + _now_tfrom + '&tto=' + _now_tto + '&attribute=sum' + '&ispd=' + ispd + '&ipaddress=' + ipaddress + '&port=' + port;
									console.log("仪表盘---右侧今日的用电量--前台url----" + url);
									$.ajax({
										type : "POST",
										url : url,
										async : false,
										success : function(result) {
											// 昨日的用电量
											var toDayData = result.datalist;
											
											getMixCharts("air_system_chart",parseInt(dataList[0][0]),100,'空调系统',"#FFAF58",150,160,parseInt(toDayData[0][0]),parseInt(lastDayData[0][0]));
											getMixCharts("light_system_chart",parseInt(dataList[1][0]),100,'照明系统',"#3DB4DF",140,150,parseInt(toDayData[1][0]),parseInt(lastDayData[1][0]));
											getMixCharts("elevator_deivce",parseInt(dataList[2][0]),100,'电梯',"#A8BD54",145,155,parseInt(toDayData[2][0]),parseInt(lastDayData[2][0]));
										},
										error : function(result) {
											console.log('3_error_Dashboard_Electricity');
										}
									});
									
								},
								error : function(result) {
									console.log('2_error_Dashboard_Electricity');
								}
							});
							
						}catch(e){
							Dashboard_Electricity.getStatic();
							console.log('Dashboard_Electricity--error'+e);
						}
						
					},
					error : function(result) {
						console.log('error_Dashboard_Electricity');
					}
				});
				
			}
		},
		getStatic : function() {
			
			getMixCharts("air_system_chart",50,100,'空调系统',"#FFAF58",150,160,100,110);
			getMixCharts("light_system_chart",20,100,'照明系统',"#3DB4DF",140,150,110,120);
			getMixCharts("elevator_deivce",30,100,'电梯',"#A8BD54",145,155,100,115);
			
		}
};