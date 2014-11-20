<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/mctrl/modeswitch.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/mctrl/energy.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/highcharts.js"></script>
<!-- customVar.js主要是用于声明获取数据的方式以及数据获取的地址 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/mctrl/customVar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/mctrl/dashboard.js"></script>
<script type="text/javascript">
	var temp_num = 0;
	$(function() {
		$('#btn_asccompn').click(function(){
			if(temp_num == 0){
				temp_num = 1;
				var temp_text = (_now_date.getMonth() + 1) + '月' + _now_date.getDate() + '日  星期' + (_now_date.getDay() == 0 ? '日' : getTimeText(_now_date.getDay()));
				$('#current_flow_passenger_text').text(temp_text); // 为访客的标识具体的时间和星期 

				Dashboard_Visitor.getChart(); // 获取客流量的图表信息
			    Dashboard_Temp.getChart(); // 获取 冷热量变化 的图表信息
			    Dashboard_IndoorTemp.getChart(); // 获取 室内温度变化 的图表信息
			    Dashboard_Energy_Total.getChart(); // 总能耗、今日能耗、总碳排放量、总用电量、今日用电量
			    Dashboard_Electricity.getChart(); // 右侧的用电量取值 
			}
		});
	});
</script>

<div class="span12 main row-fluid nomargin" id="energyDashbord" style="display: none;">
    <div class="energy_content">
        <div>
            <h1>建筑动态</h1>
            <div class="energy_modle">
                <div class="energy_modle_title bg_color1">
                    <h2>访客</h2>
                    <div class="energy_title_detail">
                        <h4 id="current_flow_passenger_text">6月25日 星期二</h4>
                        <h3>
                            <span id="flow_passenger_total">45,325</span><span>人</span>
                        </h3>
                    </div>
                </div>
                <div class="energy_modle_content" id="wd_visitors"></div>
            </div>
            <div class="energy_modle">
                <div class="energy_modle_title bg_color2">
                    <h2>冷热量变化</h2>
                </div>
                <div class="energy_modle_content" id="wd_temperature"></div>
            </div>
            <div class="energy_modle">
                <div class="energy_modle_title bg_color3">
                    <h2>室内温度变化</h2>
                </div>
                <div class="energy_modle_content" id="wd_indoor_temperature"></div>
            </div>
        </div>
        <div style="width: 33%;">
            <h1>能源使用</h1>
            <div class="energy_modle znh">
                <div class="energy_modle_title bg_color4">
                    <h2>能耗情况</h2>
                </div>
                <div class="energy_modle_content">
                    <section class="total_energy">
                        <p>总能耗</p>
                        <div>
                            <p id="energy_total_num">2,528,123</p><span>kgce</span>
                        </div>
                    </section>
                    <section class="today_energy">
                        <p>今日能耗</p>
                        <div>
                            <p id="today_energy_total">528,123</p><span>kgce</span>
                        </div>
                    </section>
                </div>
            </div>
            <div class="energy_modle ydl">
                <div class="energy_modle_title bg_color5">
                    <h2>总碳排放</h2>
                </div>
                <div class="energy_modle_content">
                    <h1 id="energy_carbon_total">2,768,123</h1>
                    <h4>t</h4>
                </div>

            </div>
            <div class="energy_modle ysl">
                <div class="energy_modle_title bg_color6">
                    <h2>总用电量</h2>
                </div>
                <div class="energy_modle_content">
                    <div class="left_chart">
                        <h3 style="color: #C0C0C0;">今日用电量</h3>
                        <h1 id="ele_today" style="color: #F5B551;">12,345</h1>
                        <div class="unit_ele_class">kWh</div>
                        <div class="ele_amount_class"></div>
                        <canvas id="canvas1" width="200" height="200" style="border: 0px; position: absolute; left: 0; top: 0"></canvas>
                        <canvas id="canvas2" width="200" height="200" style="border: 0px; position: absolute; left: 0; top: 0"></canvas>
                        <canvas id="canvas3" width="200" height="200" style="border: 0px; position: absolute; left: 0; top: 0"></canvas>
                    </div>
                    <div class="right_detail">
                        <h3>up to</h3>
                        <h2 id="ele_total_current">241,052</h2>
                        <h4>累积用电量</h4>
                    </div>
                </div>
            </div>
        </div>
        <div>
            <h1>系统分项</h1>
            <div class="energy_modle ktxtydl">
                <div class="energy_modle_title bg_color7">
                    <h2>空调系统用电量</h2>
                </div>
                <div class="energy_modle_content chart_div_width" id="air_system_chart"></div>
            </div>
            <div class="energy_modle zmxtydl">
                <div class="energy_modle_title bg_color8">
                    <h2>照明系统用电量</h2>
                </div>
                <div class="energy_modle_content chart_div_width" id="light_system_chart"></div>
            </div>
            <div class="energy_modle">
                <div class="energy_modle_title bg_color9">
                    <h2>电梯系统用电量</h2>
                </div>
                <div class="energy_modle_content chart_div_width jrnh" id="elevator_deivce"></div>
            </div>
        </div>
    </div>
</div>