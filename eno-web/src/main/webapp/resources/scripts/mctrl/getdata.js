var temp_step = 4 * 12; // 间隔4个小时

//判断当前时间是否是处于冬季（冬季：11.1~4.1，空调水温度和空调水流量显示【热水】）
var isNotSummer = false; // 默认是冬季
var nowYear = new Date().getFullYear();
var now_year_min = Date.parse(nowYear + "-04-01"), now_year_max = Date
		.parse(nowYear + "-11-01");
var now_date_ = Date.parse(getCurrentTime());
// 判断是否处于夏季
if ((parseInt(now_year_min) <= parseInt(now_date_)) && (parseInt(now_date_) < parseInt(now_year_max)))
	isNotSummer = true;

/*******************************************************************************
 * 数据列表，同配置表中的内容 
 * 
 */
var numberfas_temperature = [{'name':'number','id':'FAS_temperature'}]; // 消防监控_温感报警总数
var numberfas_smoke = [{'name':'number','id':'FAS_smoke'}]; // 消防监控_烟感报警总数
var numberfas_manual = [{'name':'number','id':'FAS_manual'}]; // 消防监控_手动报警器总数
var numberfas_others = [{'name':'number','id':'FAS_others'}]; // 消防监控_其他
var electricityenvms_total = [{'name':'electricity','id':'ENVMS_total'}]; // 能耗分析_总用电量
var powerenvms_total = [{'name':'power','id':'ENVMS_total'}]; // 能耗分析_总用电功率
var number_onhvac_chiller = [{'name':'number_on','id':'HVAC_chiller'}]; // 暖通空调_冷机开启台数
var number_onhvac_fahu = [{'name':'number_on','id':'HVAC_fahu'}]; // 暖通空调_新风机组开启台数
var number_onhvac_ahu = [{'name':'number_on','id':'HVAC_ahu'}]; // 暖通空调_组合空调开启台数
var number_onhvac_fcu = [{'name':'number_on','id':'HVAC_fcu'}]; // 暖通空调_吊装空调开启台数
var numberhvac_chiller = [{'name':'number','id':'HVAC_chiller'}]; // 暖通空调_冷机总台数
var numberhvac_fahu = [{'name':'number','id':'HVAC_fahu'}]; // 暖通空调_新风机组总台数
var numberhvac_ahu = [{'name':'number','id':'HVAC_ahu'}]; // 暖通空调_组合空调总台数
var numberhvac_fcu = [{'name':'number','id':'HVAC_fcu'}]; // 暖通空调_吊装空调总台数
var power_rateetd_1t = [{'name':'power_rate','id':'ETD_1T'}]; // 变配电_T1负载率
var power_rateetd_2t = [{'name':'power_rate','id':'ETD_2T'}]; // 变配电_T2负载率
var power_rateetd_3t = [{'name':'power_rate','id':'ETD_3T'}]; // 变配电_T3负载率
var power_rateetd_4t = [{'name':'power_rate','id':'ETD_4T'}]; // 变配电_T4负载率
var power_rateetd_5t = [{'name':'power_rate','id':'ETD_5T'}]; // 变配电_T5负载率
var power_rateetd_6t = [{'name':'power_rate','id':'ETD_6T'}]; // 变配电_T6负载率
var power_rateetd_7t = [{'name':'power_rate','id':'ETD_7T'}]; // 变配电_T7负载率
var power_rateetd_8t = [{'name':'power_rate','id':'ETD_8T'}]; // 变配电_T8负载率
var number_onwsd_pump_supply = [{'name':'number_on','id':'WSD_pump_supply'}]; // 给水排水_生活水泵开启台数
var numberwsd_pump_supply = [{'name':'number','id':'WSD_pump_supply'}]; // 给水排水_生活水泵总台数
var number_onwsd_tank = [{'name':'number_on','id':'WSD_tank'}]; // 给水排水_生活水箱开启台数
var numberwsd_tank = [{'name':'number','id':'WSD_tank'}]; // 给水排水_生活水箱总台数
var number_onwsd_pump_sewage = [{'name':'number_on','id':'WSD_pump_sewage'}]; // 给水排水_排水泵开启台数
var numberwsd_pump_sewage = [{'name':'number','id':'WSD_pump_sewage'}]; // 给水排水_排水泵总台数
var number_onwsd_sump = [{'name':'number_on','id':'WSD_sump'}]; // 给水排水_集水坑开启台数
var numberwsd_sump = [{'name':'number','id':'WSD_sump'}]; // 给水排水_集水坑总台数
var number_onlspub_awdf1 = [{'name':'number_on','id':'LSPUB_AWDF1'}]; // 公共照明_一层一区开启台数
var number_onlspub_awdf2 = [{'name':'number_on','id':'LSPUB_AWDF2'}]; // 公共照明_二层一区开启台数
var number_onlspub_awdf3 = [{'name':'number_on','id':'LSPUB_AWDF3'}]; // 公共照明_三层一区开启台数
var numberlspub_awdf1 = [{'name':'number','id':'LSPUB_AWDF1'}]; // 公共照明_一层总台数
var numberlspub_awdf2 = [{'name':'number','id':'LSPUB_AWDF2'}]; // 公共照明_二层总台数
var numberlspub_awdf3 = [{'name':'number','id':'LSPUB_AWDF3'}]; // 公共照明_三层总台数
var number_onlsn_total = [{'name':'number_on','id':'LSN_total'}]; // 夜景照明_总开启台数
var numberlsn_total = [{'name':'number','id':'LSN_total'}]; // 夜景照明_总台数
var number_onmsem_lift = [{'name':'number_on','id':'MSEM_lift'}]; // 电梯运行_直梯开启台数
var numbermsem_lift = [{'name':'number','id':'MSEM_lift'}]; // 电梯运行_直梯台数
var number_onmsem_elevator = [{'name':'number_on','id':'MSEM_elevator'}]; // 电梯运行_扶梯开启台数
var numbermsem_elevator = [{'name':'number','id':'MSEM_elevator'}]; // 电梯运行_扶梯台数
var numbermsvdo_dvr = [{'name':'number','id':'MSVDO_dvr'}]; // 视频监控_硬盘录像机数量
var numbermsvdo_videomatrix = [{'name':'number','id':'MSVDO_videomatrix'}]; // 视频监控_矩阵数量
var numbermsvdo_camera = [{'name':'number','id':'MSVDO_camera'}]; // 视频监控_摄像机数量
var number_onmsvdo_dvr = [{'name':'number_on','id':'MSVDO_dvr'}]; // 视频监控_硬盘录像机开启数量
var number_onmsvdo_videomatrix = [{'name':'number_on','id':'MSVDO_videomatrix'}]; // 视频监控_矩阵开启数量
var number_onmsvdo_camera = [{'name':'number_on','id':'MSVDO_camera'}]; // 视频监控_摄像机开启数量
var statussassa_total = [{'name':'status','id':'SASSA_total'}]; // 防盗警报_运行状态
var numbersassa_total = [{'name':'number','id':'SASSA_total'}]; // 防盗警报_总数
var number_alarmsassa_total = [{'name':'number_alarm','id':'SASSA_total'}]; // 防盗警报_报警数量
var numbersasac_total = [{'name':'number','id':'SASAC_total'}]; // 门禁管理_总数
var number_onsasac_total = [{'name':'number_on','id':'SASAC_total'}]; // 门禁管理_开启
var number_offsasac_total = [{'name':'number_off','id':'SASAC_total'}]; // 门禁管理_关闭
var numberep_total = [{'name':'number','id':'EP_total'}]; // 电子巡更_总数
var number_onep_total = [{'name':'number_on','id':'EP_total'}]; // 电子巡更_开启
var sourceep_total = [{'name':'source','id':'EP_total'}]; // 电子巡更_数据接入点
var passenger_flowpfe_total = [{'name':'passenger_flow','id':'PFE_total'}]; // 客流统计_总人数
var numberinfp_total = [{'name':'number','id':'INFP_total'}]; // 信息发布_总数
var number_oninfp_total = [{'name':'number_on','id':'INFP_total'}]; // 信息发布_开启
var statusinfp_temperature = [{'name':'status','id':'INFP_temperature'}]; // 信息发布_温度
var temperatureinfp_lux = [{'name':'temperature','id':'INFP_lux'}]; // 信息发布_亮度
var areabgmb_total = [{'name':'area','id':'BGMB_total'}]; // 背景音乐_播放区域
var statusbgmb_total = [{'name':'status','id':'BGMB_total'}]; // 背景音乐_播放状态
var tracksbgmb_total = [{'name':'tracks','id':'BGMB_total'}]; // 背景音乐_播放音源
var volumebgmb_total = [{'name':'volume','id':'BGMB_total'}]; // 背景音乐_播放音量
var numberparkm_total = [{'name':'number','id':'PARKM_total'}]; // 停车管理_总车位
var number_onparkm_total = [{'name':'number_on','id':'PARKM_total'}]; // 停车管理_可用车位

// 客流管理
var Passenger_Manager = {
		getChart : function() {
			if(isNotStatic) { // true则使用静态数据
				
				Passenger_Manager.getStatic();
				
			} else {
				
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

							renderPassengerCharts(datalist, catalist);
						} catch(e) {
							console.log('error'+e);
						}
					},
					error : function(result) {
						console.log('error');
					}
				});

			}
		},
		getStatic : function() {
			
			// 客流量
			var list = [0,0,0,0,0,0,0,73,100,120,140,200,180,130,300,400,460,460,370,350,180,100,73,0];
			var catalist = [];
			for(var i =0;i<24;i++)
				catalist.push(i + ":00");
			renderPassengerCharts(list, catalist); // 生成图表
			
		}
};

//获取当前时间，格式为：2013-12-05
function getCurrentTime()
{
	var Nowdate = new Date();
	var day = (Nowdate.getDate() + "");
	day = day.length == 1 ? ("0" + day) : day;
	var month = ((Nowdate.getMonth()+1) + "");
	month = month.length == 1 ? ("0" + month) : month;
	t = [Nowdate.getFullYear(), month, day ];
	return t.join('-');
}

//查询总客流数据
function getTotalPassengerFlow() {
    var url = baseurl + '/pfe/getTotalPassengerFlow';
    $.ajax({
        type : "POST",
        url : url,
        success : function(result) {
            try{
                var todayTotal = result.todayTotal;
                $("#PFE>div:eq(2)>.pee_info>.chart_bottom>.chart_bottom_data:eq(0)>.data_value").text(todayTotal); // 客流统计_今日总客流
            } catch(e) {
                console.log('error'+e);
            }
        },
        error : function(result) {
            console.log('error');
        }
    });
}

// 刷新背景音乐
var music_i = 0;
function modifyBgMusic() {
	var bfqy_list = ['一层中庭','一层西区','一层东区','二层西区','二层东区','三层西区','三层东区']; // 背景音乐_播放区域
	var bfzt_list = ['播放','播放','播放','播放','播放','播放','播放']; // 背景音乐_播放状态 
	var bfyy_list = ['CD1','CD1','CD1','CD2','CD2','CD3','CD3']; // 背景音乐_播放音源
	var bfyl_list = ['高','中','高','中','高','中','高']; // 背景音乐_播放音量
	$("#BGMB>.include>div:eq(0)>.child_3_value>p").text(bfqy_list[music_i]); // 背景音乐_播放区域
	$("#BGMB>.include>div:eq(1)>.child_3_value>p").text(bfzt_list[music_i]); // 背景音乐_播放状态 
	$("#BGMB>.include>div:eq(2)>.child_3_value>p").text(bfyy_list[music_i]); // 背景音乐_播放音源
	$("#BGMB>.include>div:eq(3)>.child_3_value>p").text(bfyl_list[music_i]); // 背景音乐_播放音量
	if (music_i == (bfqy_list.length - 1)) {
		music_i = 0;
	} else {
		music_i++;
	}
}

// 渲染“客流统计”图表
function renderPassengerCharts(datalist, catalist){
	
	$('#passengerTotal').highcharts({
		chart : {
			type : 'spline',
			marginBottom : 20
		},
		title : {
			text : ''
		},
		credits : {
			enabled : false
		},
		xAxis : {
			tickColor : '#FFFFFF',
			labels : {
				step : 4 // x轴显示的间隔
			},
			categories:catalist
		},
		yAxis : {
			gridLineDashStyle : 'LongDash',
			title: {
			   text : ''
			},
			min: 0
		},
		tooltip : {
			
		},
		legend: {
			enabled : false
		},
		series : [{
			name : '客流量',
			color : '#469FE3',
			marker : {
				radius : 0
			},
			data : datalist
		}]
	});		
	
}

// 根据array和type为相应位置求值，并赋值
function getSingleValue(type,array) {
//	var current_date = new Date();
//	current_date.setMinutes(current_date.getMinutes() - 5);
//	var temp_tto = current_date.getFullYear() + "-" + (current_date.getMonth()+1) + "-" + current_date.getDate() + "T" + current_date.getHours() + ":" + current_date.getMinutes() + ":" + current_date.getSeconds();

	var url = "http://" + ipaddress + ":" + port  + '/ChinaArtsPalace/history?name=' + array[0].name + '&id=' + array[0].id + '&type=span&ispd=1';
				// + '/ChinaArtsPalace/history?name=address&id=total&type=span&tto=' + temp_tto + '&attribute=&tspan=&ispd=2';
	
	if(type == "electricityenvms_total" || type == "electricity_envms_total" || type == "passenger_flow_pfe_total" || type == 'passenger_flow_pfe_store' || type == 'passenger_flow_pfe_game' || type == 'passenger_flow_pfe_ktv' || type == 'passenger_flow_pfe_cinema') 
		url = "http://" + ipaddress + ":" + port  + '/ChinaArtsPalace/history?name=' + array[0].name + '&id=' + array[0].id + '&type=day&tfrom=' + _now_tfrom + '&tto=' + _now_tto + '&ispd=1&attribute=sum'; 
	
	console.log("url----" + url);
	$.ajax({
		url : url,
		dataType : "jsonp",
		success : function(data) {
           try{
			var val = 0;
			try{
				val = parseInt((data[0].value == undefined) ? 0 : data[0].value);
			}catch(e){
            	val = 0;
                console.log("getSingleValue-error---" +e);
			}
			
			if(type == 'numberfas_temperature') 
				$("#FAS>.include>.father:eq(0)>.child_3_value>p").text(val);  // 消防监控_温感报警总数
			else if(type == 'numberfas_smoke') 
				$("#FAS>.include>.father:eq(1)>.child_3_value>p").text(val); // 消防监控_烟感报警总数
			else if(type == 'numberfas_manual') 
				$("#FAS>.include>.father:eq(2)>.child_3_value>p").text(val); // 消防监控_手动报警器总数
			else if(type == 'numberfas_others') 
				$("#FAS>.include>.father:eq(3)>.child_3_value>p").text(val); // 消防监控_其他
				
			else if(type == 'electricityenvms_total') 
				$("#ENVMS>.include_2>div>.energy_chart>div:eq(0)>.chart_bottom_data:eq(0)>.data_value").text(val); // 能源管理-今日总能耗
			else if(type == 'powerenvms_total') 
				$("#ENVMS>.include_2>div>.energy_chart>div:eq(0)>.chart_bottom_data:eq(1)>.data_value").text(val); // 能源管理-当前用电功率
				
			else if(type == 'number_onhvac_chiller') 
				$("#HVAC>.hvac_config>div:eq(0)>.para_box>.current_run_monitor").text(val); // 暖通空调_冷机开启台数
			else if(type == 'number_onhvac_fahu') 
				$("#HVAC>.hvac_config>div:eq(1)>.para_box>.current_run_monitor").text(val); // 暖通空调_新风机组开启台数 
			else if(type == 'number_onhvac_ahu') 
				$("#HVAC>.hvac_config>div:eq(2)>.para_box>.current_run_monitor").text(val); // 暖通空调_组合空调开启台数
			else if(type == 'number_onhvac_fcu') 
				$("#HVAC>.hvac_config>div:eq(3)>.para_box>.current_run_monitor").text(val); // 暖通空调_吊装空调开启台数
			else if(type == 'numberhvac_chiller') 
				$("#HVAC>.hvac_config>div:eq(0)>.para_box>.total_moitors").text("共" + val + "台"); // 暖通空调_冷机总台数
			else if(type == 'numberhvac_fahu') 
				$("#HVAC>.hvac_config>div:eq(1)>.para_box>.total_moitors").text("共" + val + "台"); // 暖通空调_新风机组总台数 
			else if(type == 'numberhvac_ahu') 
				$("#HVAC>.hvac_config>div:eq(2)>.para_box>.total_moitors").text("共" + val + "台"); // 暖通空调_组合空调总台数
			else if(type == 'numberhvac_fcu') 
				$("#HVAC>.hvac_config>div:eq(3)>.para_box>.total_moitors").text("共" + val + "台"); // 暖通空调_吊装空调总台数
				
			else if(type == 'power_rateetd_1t') {
				var zhi = val;
				try{
					zhi = toDecimal(val / 20);
				}catch(e){zhi = 0;}
				$("#ETD>.ERAD_chart>table>tbody>tr:eq(0)>td:eq(1)>.float_right").text(zhi+"%"); // 变配电_T1负载率
			} else if(type == 'power_rateetd_2t') {
				var zhi = val;
				try{
					zhi = toDecimal(val / 20);
				}catch(e){zhi = 0;}
				$("#ETD>.ERAD_chart>table>tbody>tr:eq(1)>td:eq(1)>.float_right").text(zhi+"%"); // 变配电_T2负载率
            } else if(type == 'power_rateetd_3t') {
            	var zhi = val;
				try{
					zhi = toDecimal(val / 20);
				}catch(e){zhi = 0;}
				$("#ETD>.ERAD_chart>table>tbody>tr:eq(2)>td:eq(1)>.float_right").text(zhi+"%"); // 变配电_T3负载率
			} else if(type == 'power_rateetd_4t') {
				var zhi = val;
				try{
					zhi = toDecimal(val / 20);
				}catch(e){zhi = 0;}
				$("#ETD>.ERAD_chart>table>tbody>tr:eq(3)>td:eq(1)>.float_right").text(zhi+"%"); // 变配电_T4负载率
	        } else if(type == 'power_rateetd_5t') {
	        	var zhi = val;
				try{
					zhi = toDecimal(val / 12.5);
				}catch(e){zhi = 0;}
				$("#ETD>.ERAD_chart>table>tbody>tr:eq(0)>td:eq(3)>.float_right").text(zhi+"%"); // 变配电_T5负载率
			} else if(type == 'power_rateetd_6t') {
				var zhi = val;
				try{
					zhi = toDecimal(val / 12.5);
				}catch(e){zhi = 0;}
				$("#ETD>.ERAD_chart>table>tbody>tr:eq(1)>td:eq(3)>.float_right").text(zhi+"%"); // 变配电_T6负载率
			} else if(type == 'power_rateetd_7t') {
				var zhi = val;
				try{
					zhi = toDecimal(val / 16);
				}catch(e){zhi = 0;}
				$("#ETD>.ERAD_chart>table>tbody>tr:eq(2)>td:eq(3)>.float_right").text(zhi+"%"); // 变配电_T7负载率
			} else if(type == 'power_rateetd_8t') {
				var zhi = val;
				try{
					zhi = toDecimal(val / 16);
				}catch(e){zhi = 0;}
				$("#ETD>.ERAD_chart>table>tbody>tr:eq(3)>td:eq(3)>.float_right").text(zhi+"%"); // 变配电_T8负载率
			}	
			
			else if(type == 'number_onwsd_pump_supply') 
				$("#WSD>.hvac_config >div:eq(0)>div>p:eq(0)").text(val); // 给水排水_生活水泵开启台数
			else if(type == 'number_wsd_pump_supply') 
				$("#WSD>.hvac_config >div:eq(0)>div>p:eq(1)").text("共" + val + "台"); // 给水排水_生活水泵总台数 
			else if(type == 'number_onwsd_tank') 
				$("#WSD>.hvac_config >div:eq(1)>div>p:eq(0)").text(val); // 给水排水_生活水箱开启台数
			else if(type == 'numberwsd_tank') 
				$("#WSD>.hvac_config >div:eq(1)>div>p:eq(1)").text("共" + val + "台"); // 给水排水_生活水箱总台数
			else if(type == 'number_onwsd_pump_sewage') 
				$("#WSD>.hvac_config >div:eq(2)>div>p:eq(0)").text(val); // 给水排水_排水泵开启台数 
			else if(type == 'numberwsd_pump_sewage') 
				$("#WSD>.hvac_config >div:eq(2)>div>p:eq(1)").text("共" + val + "台"); // 给水排水_排水泵总台数 
			else if(type == 'number_onwsd_sump') 
				$("#WSD>.hvac_config >div:eq(3)>div>p:eq(0)").text(val);// 给水排水_集水坑开启台数
			else if(type == 'numberwsd_sump') 
				$("#WSD>.hvac_config >div:eq(3)>div>p:eq(1)").text("共" + val + "台"); // 给水排水_集水坑总台数
				
			else if(type == 'number_onlspub_awdf1') 
				$("#LSPUB>.hvac_config>div:eq(0)>.para_box>.current_run_monitor").text(val); // 公共照明_一层一区开启台数
			else if(type == 'number_onlspub_awdf2') 
				$("#LSPUB>.hvac_config>div:eq(1)>.para_box>.current_run_monitor").text(val); // 公共照明_二层一区开启台数
			else if(type == 'number_onlspub_awdf3') 
				$("#LSPUB>.hvac_config>div:eq(2)>.para_box>.current_run_monitor").text(val); // 公共照明_三层一区开启台数
			else if(type == 'numberlspub_awdf1') 
				$("#LSPUB>.hvac_config>div:eq(0)>.para_box>.total_moitors").text('共' + val + '路'); // 公共照明_一层总台数
			else if(type == 'numberlspub_awdf2') 
				$("#LSPUB>.hvac_config>div:eq(1)>.para_box>.total_moitors").text('共' + val + '路'); // 公共照明_二层总台数
			else if(type == 'numberlspub_awdf3') 
				$("#LSPUB>.hvac_config>div:eq(2)>.para_box>.total_moitors").text('共' + val + '路'); // 公共照明_三层总台数
				
			else if(type == 'number_onlsn_total') 
				$("#LSN>table>tbody>tr:eq(1)>td:eq(0)>.float_right").text(val); // 夜景照明_总开启台数
			else if(type == 'numberlsn_total') 
				$("#LSN>table>tbody>tr:eq(0)>td:eq(1)>.float_right").text(val); // 夜景照明_总台数
				
			else if(type == 'number_onmsem_lift') 
				$("#MSEM>table:eq(0)>tbody>tr:eq(0)>td:eq(1)>.float_right").text(val); // 电梯运行_直梯开启台数
			else if(type == 'numbermsem_lift') 
				$("#MSEM>table:eq(0)>tbody>tr:eq(1)>td:eq(0)>.float_right").text(val); // 电梯运行_直梯台数
			else if(type == 'number_onmsem_elevator') 
				$("#MSEM>table:eq(1)>tbody>tr:eq(0)>td:eq(1)>.float_right").text(val); // 电梯运行_扶梯开启台数
			else if(type == 'numbermsem_elevator') 
				$("#MSEM>table:eq(1)>tbody>tr:eq(1)>td:eq(0)>.float_right").text(val); // 电梯运行_扶梯台数
				
			else if(type == 'numbermsvdo_dvr') 
				$("#MSVDO>.hvac_config>div:eq(0)>.para_box>.total_moitors").text("共"+ val + "台"); // 视频监控_硬盘录像机_总台数
			else if(type == 'numbermsvdo_videomatrix') 
				$("#MSVDO>.hvac_config>div:eq(2)>.para_box>.total_moitors").text("共"+ val + "台"); // 视频监控_矩阵
			else if(type == 'numbermsvdo_camera') 
				$("#MSVDO>.hvac_config>div:eq(1)>.para_box>.total_moitors").text("共"+ val + "台"); // 视频监控_摄像机_总台数
			else if(type == 'number_onmsvdo_dvr') 
				$("#MSVDO>.hvac_config>div:eq(0)>.para_box>.current_run_monitor").text(val); // 视频监控_硬盘录像机_开启
			else if(type == 'number_onmsvdo_videomatrix') 
				$("#MSVDO>.hvac_config>div:eq(2)>.para_box>.current_run_monitor").text(val); // 视频监控_矩阵开启数量
			else if(type == 'number_onmsvdo_camera') 
				$("#MSVDO>.hvac_config>div:eq(1)>.para_box>.current_run_monitor").text(val); // 视频监控_摄像机_开启
			
			else if(type == 'statussassa_total') 
				$("#SASSA>.include>div:eq(0)>.child_3_value>p").text(val); // 防盗警报_运行状态
			else if(type == 'numbersassa_total') 
				$("#SASSA>.include>div:eq(1)>.child_3_value>p").text(val); // 防盗警报_总数
			else if(type == 'number_alarmsassa_total') 
				$("#SASSA>.include>div:eq(2)>.child_3_value>p").text(val); // 防盗警报_报警点数
			
			else if(type == 'numbersasac_total') 
				$("#SASAC>table>tbody>tr:eq(0)>td:eq(1)>.float_right").text(val); // 门禁管理_总数
			else if(type == 'number_onsasac_total') 
				$("#SASAC>table>tbody>tr:eq(1)>td:eq(0)>.float_right").text(val); // 门禁管理_开启
			else if(type == 'number_offsasac_total') 
				$("#SASAC>table>tbody>tr:eq(2)>td:eq(0)>.float_right").text(val); // 门禁管理_关闭
			
			else if(type == 'numberep_total') 
				$("#EP>table:eq(0)>tbody>tr:eq(0)>td:eq(1)>.float_right").text(val); // 电子巡更-总数
			else if(type == 'number_onep_total') 
				$("#EP>table:eq(0)>tbody>tr:eq(1)>td:eq(0)>.float_right").text(val); // 电子巡更-开启
			else if(type == 'sourceep_total') 
				$("#EP>table:eq(1)>tbody>tr:eq(0)>td:eq(1)>.float_right").text(val); // 电子巡更-数据接入点
			
			else if(type == 'passenger_flow_pfe_total') 
				$("#PFE>div:eq(2)>.pee_info>.chart_bottom>.chart_bottom_data:eq(0)>.data_value").text(val); // 客流统计_今日总客流
			else if(type == 'passenger_flow_pfe_store') 
				$("#PFE>.include:eq(1)>div:eq(0)>.child_3_value>p").text(val); // 客流统计_步行街人数
			else if(type == 'passenger_flow_pfe_game') 
				$("#PFE>.include:eq(1)>div:eq(1)>.child_3_value>p").text(val); // 客流统计_大玩家人数
			else if(type == 'passenger_flow_pfe_ktv') 
				$("#PFE>.include:eq(1)>div:eq(2)>.child_3_value>p").text(val); // 客流统计_KTV人数
			else if(type == 'passenger_flow_pfe_cinema') 
				$("#PFE>.include:eq(1)>div:eq(3)>.child_3_value>p").text(val); // 客流统计_影城人数
			
			else if(type == 'numberinfp_total') 
				$("#INFP>table:eq(0)>tbody>tr:eq(0)>td:eq(1)>.float_right").text(val); // 信息发布-运行-总数
			else if(type == 'number_oninfp_total') 
				$("#INFP>table:eq(0)>tbody>tr:eq(1)>td:eq(0)>.float_right").text(val); // 信息发布-运行-开启 
			else if(type == 'statusinfp_temperature') 
				$("#INFP>table:eq(1)>tbody>tr:eq(0)>td:eq(1)>.float_right").text(val); // 信息发布-大屏-温度
			else if(type == 'temperatureinfp_lux') 
				$("#INFP>table:eq(1)>tbody>tr:eq(1)>td:eq(0)>.float_right").text(val); // 信息发布-大屏-亮度
			
			else if(type == 'area_bgmb_total') 
				$("#BGMB>.include>div:eq(0)>.child_3_value>p").text(val); // 背景音乐_播放区域
			else if(type == 'status_bgmb_total') 
				$("#BGMB>.include>div:eq(1)>.child_3_value>p").text(val); // 背景音乐_播放状态 
			else if(type == 'tracks_bgmb_total') 
				$("#BGMB>.include>div:eq(2)>.child_3_value>p").text(val); // 背景音乐_播放音源
			else if(type == 'volume_bgmb_total') 
				$("#BGMB>.include>div:eq(3)>.child_3_value>p").text(val); // 背景音乐_播放音量
				
			else if(type == 'numberparkm_total') 
				$("#PARKM>table>tbody>tr:eq(0)>td:eq(1)>.float_right").text(val); // 停车管理_总车位
			else if(type == 'number_on_parkm_total') 
				$("#PARKM>table>tbody>tr:eq(1)>td:eq(0)>.float_right").text(val); // 停车管理_可用车位
			
            }catch(e){
                console.log('error'+e);
            }
		},
		error : function() {
			console.log('error');
		}
	});
}

// 保留一位小数   
// 功能：将浮点数四舍五入，取小数点后1位  
function toDecimal(x) {  
    var f = parseFloat(x);  
    if (isNaN(f)) return 0;
    f = Math.round(f*10)/10;  
    return f;  
}

// 获取并处理报警个数在首页上的显示
function getAlarmsCount(groupname) {
	$.ajax({
		type : "POST",
		async : false,
		url : baseurl + "/pwarn/alarms/getAlarmsCountByGroup",
		data : {
			groupname : (groupname == '' ? '' : groupname)
		},
		success : function(result) {
			var left1 = 180,left2 = 258, left3 = 335; // 图表居左距离
			var alarmprioritymap = result.alarmprioritymap; // 对应子系统报警个数
			var level_h = alarmprioritymap.level_1; // 极高级别，红色背景
			var level_m = alarmprioritymap.level_2 + alarmprioritymap.level_3; // 2,3级，橙色背景
			var level_l = alarmprioritymap.level_4; // 4级，黄色背景
			
//			level_h = 22,level_m = 20, level_l = 30;
			var qz = ''; // 前缀
				
			if(groupname == "消防系统") { // 消防系统
				qz = "#FAS>.title>";
			} else if(groupname == "变配电") { // 变配电
				qz = "#ETD>.title>";
			} else if(groupname == "给水排水") { // 给水排水
				qz = "#WSD>.title>";
			} else if(groupname == "视频监控") { // 视频监控
				qz = "#MSVDO>.title>";
			} else if(groupname == "防盗警报") { // 防盗警报
				qz = "#SASSA>.title>";
			} else if(groupname == "暖通空调") { // 暖通空调
				qz = "#HVAC>.title>";
				left1 = 170,left2 = 170, left3 = 170; // 图表居左距离
			}

			if(level_h == 0) { // h==0
				$(qz + ".alert_h").css("display", "none");
				if(level_m == 0) { // h==0 && m==0
					$(qz + ".alert_m").css("display", "none");
					if(level_l == 0) { // h==0 && m==0 && l==0
						$(qz + ".alert_l").css("display", "none");
					} else { // h==0 && m==0 && l!=0
						$(qz + ".alert_l").css("display", "block");
						$(qz + ".alert_l").css("left", left1);
						$(qz + ".alert_l>.bjgs").text(level_l);
					}
				} else { // h==0 && m!=0
					$(qz + ".alert_m").css("display", "block");
					$(qz + ".alert_m").css("left", left1);
					$(qz + ".alert_m>.bjgs").text(level_m);
					if(level_l == 0) { // h==0 && m!=0 && l==0
						$(qz + ".alert_l").css("display", "none");
					} else { // h==0 && m!=0 && l!=0
						$(qz + ".alert_l").css("display", "block");
						$(qz + ".alert_l").css("left", left2);
						$(qz + ".alert_l>.bjgs").text(level_l);
					}
				}
			} else { // h!=0
				$(qz + ".alert_h").css("display", "block");
				$(qz + ".alert_h").css("left", left1);
				$(qz + ".alert_h>.bjgs").text(level_h);
				
				if(level_m == 0) { // h!=0 && m==0 
					$(qz + ".alert_m").css("display", "none");
					if(level_l == 0) { // h!=0 && m==0 && l==0
						$(qz + ".alert_l").css("display", "none");
					} else { // h!=0 && m==0 && l!=0
						$(qz + ".alert_l").css("display", "block");
						$(qz + ".alert_l").css("left", left2);
						$(qz + ".alert_l>.bjgs").text(level_l);
					}
				} else { // h!=0 && m!=0 
					$(qz + ".alert_m").css("display", "block");
					$(qz + ".alert_m").css("left", left2);
					$(qz + ".alert_m>.bjgs").text(level_m);
					
					if(level_l == 0) { // h!=0 && m!=0 && l==0
						$(qz + ".alert_l").css("display", "none");
					} else { // h!=0 && m!=0 && l!=0
						$(qz + ".alert_l").css("display", "block");
						$(qz + ".alert_l").css("left", left3);
						$(qz + ".alert_l>.bjgs").text(level_l);
					}
				}
			}
		},
		error : function(result) {
			console.log('error');
		}
	});
}

$(function(){
    //弃用此方式
	//getAlarmsCount("消防系统"); // 消防报警个数
	//getAlarmsCount("变配电");  // 变配电个数
	//getAlarmsCount("给水排水"); // 给水排水个数
	//getAlarmsCount("视频监控"); // 视频监控个数
	//getAlarmsCount("防盗警报"); // 防盗警报个数
	//getAlarmsCount("暖通空调"); // 暖通空调个数

//	Passenger_Manager.getChart(); // 客流统计
//	getTotalPassengerFlow(); // 今日总客流
	
//	window.setInterval("Passenger_Manager.getChart()", 1000 * 60 * 5); // 5分钟定时执行 客流统计
	
	if (!isNotStatic){

		// 消防管理
		getSingleValue('numberfas_temperature',numberfas_temperature);   // 消防监控_温感报警总数
		getSingleValue('numberfas_smoke',numberfas_smoke);  // 消防监控_烟感报警总数
		getSingleValue('numberfas_manual',numberfas_manual);  // 消防监控_手动报警器总数
		getSingleValue('numberfas_others',numberfas_others);  // 消防监控_其他
		
		
		// 能源管理
		getSingleValue('electricityenvms_total',electricityenvms_total);  // 能源管理-今日总能耗
		getSingleValue('powerenvms_total',powerenvms_total);  // 能源管理-当前用电功率
		window.setInterval(function() {
			getSingleValue('electricityenvms_total',electricityenvms_total);  // 能源管理-今日总能耗
			getSingleValue('powerenvms_total',powerenvms_total);  // 能源管理-当前用电功率
		}, 1000 * 60 * 5); // 每隔5分钟
		
		// 暖通空调
		getSingleValue('number_onhvac_chiller',number_onhvac_chiller); // 暖通空调_冷机开启台数
		getSingleValue('number_onhvac_fahu',number_onhvac_fahu); // 暖通空调_新风机组开启台数
		getSingleValue('number_onhvac_ahu',number_onhvac_ahu); // 暖通空调_组合空调开启台数
		getSingleValue('number_onhvac_fcu',number_onhvac_fcu); // 暖通空调_吊装空调开启台数
		getSingleValue('numberhvac_chiller',numberhvac_chiller); // 暖通空调_冷机总台数
		getSingleValue('numberhvac_fahu',numberhvac_fahu); // 暖通空调_新风机组总台数
		getSingleValue('numberhvac_ahu',numberhvac_ahu); // 暖通空调_组合空调总台数
		getSingleValue('numberhvac_fcu',numberhvac_fcu); // 暖通空调_吊装空调总台数
		
		// 变配电
		getSingleValue('power_rateetd_1t',power_rateetd_1t);// 变配电_T1负载率
		getSingleValue('power_rateetd_2t',power_rateetd_2t);// 变配电_T2负载率
		getSingleValue('power_rateetd_3t',power_rateetd_3t);// 变配电_T3负载率
		getSingleValue('power_rateetd_4t',power_rateetd_4t);// 变配电_T4负载率
		getSingleValue('power_rateetd_5t',power_rateetd_5t);// 变配电_T5负载率
		getSingleValue('power_rateetd_6t',power_rateetd_6t);// 变配电_T6负载率
		getSingleValue('power_rateetd_7t',power_rateetd_7t);// 变配电_T7负载率
		getSingleValue('power_rateetd_8t',power_rateetd_8t);// 变配电_T8负载率
		
		// 给水排水
		getSingleValue('number_onwsd_pump_supply',number_onwsd_pump_supply); // 给水排水_生活水泵开启台数
		getSingleValue('numberwsd_pump_supply',numberwsd_pump_supply); // 给水排水_生活水泵总台数
		getSingleValue('number_onwsd_tank',number_onwsd_tank); // 给水排水_生活水箱开启台数
		getSingleValue('numberwsd_tank',numberwsd_tank); // 给水排水_生活水箱总台数
		getSingleValue('number_onwsd_pump_sewage',number_onwsd_pump_sewage); // 给水排水_排水泵开启台数 
		getSingleValue('numberwsd_pump_sewage',numberwsd_pump_sewage); // 给水排水_排水泵总台数
		getSingleValue('number_onwsd_sump',number_onwsd_sump); // 给水排水_集水坑开启台数
		getSingleValue('numberwsd_sump',numberwsd_sump); // 给水排水_集水坑总台数
		
		// 公共照明
		getSingleValue('number_onlspub_awdf1',number_onlspub_awdf1);// 公共照明_一层一区开启台数
		getSingleValue('number_onlspub_awdf2',number_onlspub_awdf2);// 公共照明_二层一区开启台数
		getSingleValue('number_onlspub_awdf3',number_onlspub_awdf3);// 公共照明_三层一区开启台数
		getSingleValue('numberlspub_awdf1',numberlspub_awdf1);// 公共照明_一层总台数
		getSingleValue('numberlspub_awdf2',numberlspub_awdf2);// 公共照明_二层总台数
		getSingleValue('numberlspub_awdf3',numberlspub_awdf3);// 公共照明_三层总台数
		
		// 夜景照明
		getSingleValue('number_onlsn_total',number_onlsn_total); // 夜景照明_总开启台数
		getSingleValue('numberlsn_total',numberlsn_total);  // 夜景照明_总台数
		
		// 电梯运行
		getSingleValue('number_onmsem_lift',number_onmsem_lift); // 电梯运行_直梯开启台数
		getSingleValue('numbermsem_lift',numbermsem_lift); // 电梯运行_直梯台数
		getSingleValue('number_onmsem_elevator',number_onmsem_elevator); // 电梯运行_扶梯开启台数
		getSingleValue('numbermsem_elevator',numbermsem_elevator); // 电梯运行_扶梯台数
		
		// 视频监控
		getSingleValue('numbermsvdo_dvr',numbermsvdo_dvr);// 硬盘录像机
		getSingleValue('numbermsvdo_videomatrix',numbermsvdo_videomatrix);// 矩阵
		getSingleValue('numbermsvdo_camera',numbermsvdo_camera);// 摄像机
		getSingleValue('number_onmsvdo_dvr',number_onmsvdo_dvr);// 视频监控_硬盘录像机开启数量
		getSingleValue('number_onmsvdo_videomatrix',number_onmsvdo_videomatrix);// 视频监控_矩阵开启数量
		getSingleValue('number_onmsvdo_camera',number_onmsvdo_camera);// 视频监控_摄像机开启数量

		// 防盗警报
		//getSingleValue('statussassa_total',statussassa_total); // 防盗警报_运行状态
		$("#SASSA>.include>div:eq(0)>.child_3_value>p").text('布防'); // 防盗警报_运行状态
		getSingleValue('numbersassa_total',numbersassa_total); // 防盗警报_总数
		getSingleValue('number_alarmsassa_total',number_alarmsassa_total);  // 防盗警报_报警数量
		
		// 门禁管理
		getSingleValue('numbersasac_total',numbersasac_total);  // 门禁管理_总数
		getSingleValue('number_onsasac_total',number_onsasac_total);  // 门禁管理_开启
		getSingleValue('number_offsasac_total',number_offsasac_total); // 门禁管理_关闭
		
		// 电子巡更
		getSingleValue('numberep_total',numberep_total); // 电子巡更-总数
		getSingleValue('number_onep_total',number_onep_total); // 电子巡更-开启
//		getSingleValue('sourceep_total',sourceep_total); // 电子巡更-数据接入点
		$("#EP>.include>div:eq(2)>.child_3_value>p").text('廊坊中心'); // 电子巡更-数据接入点
		
		// 客流管理
//		getSingleValue('passenger_flow_pfe_total',passenger_flow_pfe_total);  // 客流统计_总人数
//		getSingleValue('passenger_flow_pfe_store',passenger_flow_pfe_store);  // 客流统计_步行街人数
//		getSingleValue('passenger_flow_pfe_game',passenger_flow_pfe_game);  // 客流统计_大玩家人数
//		getSingleValue('passenger_flow_pfe_ktv',passenger_flow_pfe_ktv);  // 客流统计_KTV人数
//		getSingleValue('passenger_flow_pfe_cinema',passenger_flow_pfe_cinema);  // 客流统计_影城人数
		
		// 信息发布
		getSingleValue('numberinfp_total',numberinfp_total); // 信息发布-运行-总数
		getSingleValue('number_oninfp_total',number_oninfp_total); // 信息发布-运行-开启 
		getSingleValue('statusinfp_temperature',statusinfp_temperature); // 信息发布_温度
		getSingleValue('temperatureinfp_lux',temperatureinfp_lux); // 信息发布_亮度
		
		// 背景音乐 
		//getSingleValue('area_bgmb_total',area_bgmb_total); // 背景音乐_播放区域
		//getSingleValue('status_bgmb_total',status_bgmb_total); // 背景音乐_播放状态
		//getSingleValue('tracks_bgmb_total',tracks_bgmb_total); // 背景音乐_播放音源
		//getSingleValue('volume_bgmb_total',volume_bgmb_total); // 背景音乐_播放音量
//		$("#BGMB>.include>div:eq(0)>.child_3_value>p").text('一层中庭'); // 背景音乐_播放区域
//		$("#BGMB>.include>div:eq(1)>.child_3_value>p").text('播放'); // 背景音乐_播放状态 
//		$("#BGMB>.include>div:eq(2)>.child_3_value>p").text('CD1'); // 背景音乐_播放音源
//		$("#BGMB>.include>div:eq(3)>.child_3_value>p").text('高'); // 背景音乐_播放音量
		
		// 停车管理
		getSingleValue('numberparkm_total',numberparkm_total); // 停车管理_总车位
		getSingleValue('number_onparkm_total',number_onparkm_total); // 停车管理_可用车位
		
	}
	
//	window.setInterval("modifyBgMusic()",2500);//两秒钟定时执行背景音乐
});