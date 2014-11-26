<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/quota.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/common.css">
<script src="<spring:url value="/resources/scripts/jquery-1.9.1.min.js"></spring:url>"></script>
<script src="<spring:url value="/resources/plugins/bootstrap/js/bootstrap.js"></spring:url>"></script>
<script src="<spring:url value="/resources/plugins/My97DatePicker/WdatePicker.js"></spring:url>"></script>
<script src="<spring:url value="/resources/plugins/bootstrap-select/bootstrap-select.min.js"></spring:url>"></script>
<script>
    function chooseTimeY(format) {
        WdatePicker({
            el : 'year_date',	//只操作这个日历控件
            dateFmt : "yyyy", //定义时间的格式
            onpicked : function(dp) {
                // 具体的对比时间，格式为（yyyy），此时间会传递到后台
                var paretime= dp.cal.getDateStr("yyyy");
            }
        });
    }
    function chooseTimeM(format) {
        WdatePicker({
            el : 'month_date',	//只操作这个日历控件
            dateFmt : "yyyy-MM", //定义时间的格式
            onpicked : function(dp) {
                // 具体的对比时间，格式为（yyyy-MM），此时间会传递到后台
                var paretime= dp.cal.getDateStr("yyyy-MM");
            }
        });
    }
</script>
<!--container start-->
<div class="container">
    <div class="span12 quota_content">
    	<div class="quota_top_content pubCon_blackOne">
        	<div class="pub_title">
        		<h3><i class="flag_big flag_red mr20"></i>建筑用电定额分析</h3>
                <div class="small_txt"><span class="ft18">年用电已达年定额的</span><strong class="ft40 colfff ml10 mr10">75</strong><span class="unit">%</span></div>
                <div class="small_txt"><span class="ft18">月用电已达月定额的</span><strong class="ft40 colfff ml10 mr10">55</strong><span class="unit">%</span></div>
                <div class="small_txt"><span class="ft18">本日用电已达日均定额的</span><strong class="ft40 colfff ml10 mr10">125</strong><span class="unit">%</span></div>
                <div class="funBtnBar mr10"><a href="#setAll" data-toggle="modal" class="btn_pub btn_green mr10">配置定额</a><a href="#" class="btn_pub btn_blue">下载图表</a></div>
        	</div>
            <!-- 年用电 -->
            <div class="year_count">
            	<div class="quota_dateBar">
                	<div class="formBar">
                    	<label>年用电</label>
                        <div class="calendarBar"><input class="Wdate" type="text" onfocus="WdatePicker({dateFmt:'yyyy'})"></div>
                    </div>
                </div>
                <div class="pubCon_grey Echart_quota">
                	<p class="ft18 center pt20">年用电情况仪表盘</p>
                    <div class="chartBbox">
                    	<div class="center mt30"><img src="<spring:url value="/resources/images/quota_pic05.jpg"></spring:url>"></div>
                        <p class="quota_chartValue"><strong class="ft40 colfff mr5">75</strong>%</p>
                    </div>
                </div>
                <p class="amount_num"><i class="icon_small arrow_up_y mr10"></i>截至到目前，2014年总用电快于定额计划10%</p>
            </div>
            <!-- 月用电 -->
            <div class="month_count">
            	<div class="quota_dateBar">
                	<div class="formBar">
                    	<label>月用电</label>
                        <div class="calendarBar"><input class="Wdate" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM'})"></div>
                    </div>
                </div>
                <div class="pubCon_grey Echart_quota">
                	<p class="ft18 center pt20">今年实际用电/定额</p>
                    <div class="chartBbox">
                    	<div class="center mt5"><img src="<spring:url value="/resources/images/quota_pic06.jpg"></spring:url>"></div>
                    </div>
                </div>
                <p class="amount_num"><i class="icon_small arrow_down_y mr10"></i>截至到目前，2014年总用电慢于定额计划10%</p>
            </div>
            <!-- 日用电 -->
            <div class="day_count">
            	<div class="quota_dateBar">
                	<div class="formBar">
                    	<label>日用电</label>
                        <div class="calendarBar"><input id="day_date" class="Wdate" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"></div>
                    </div>
                </div>
                <div class="pubCon_grey Echart_quota">
                	<p class="ft18 center pt20">九月每日用电情况</p>
                    <div class="chartBbox">
                    	<div class="center mt10"><img src="<spring:url value="/resources/images/quota_pic07.jpg"></spring:url>"></div>
                    </div>
                </div>
                <p class="amount_num"><i class="icon_small arrow_down_y mr10"></i>截至到目前，2014年总用电慢于定额计划10%</p>
            </div>
        </div>
        <!-- 建筑用电分项定额 -->
        <div class="quota_bottom_content">
            <!-- 暖通空调 -->
        	<div class="pubBox mr20">
                <div class="pubCon_blackThree w455 h400">
                    <div class="pub_title">
                        <h3 class="ft30"><i class="flag flag_h40 flag_purple mr15 mt15"></i>暖通空调</h3>
                        <div class="funBtnBar mr10"><a href="#setEvery" data-toggle="modal" class="btn_pub btn_green mr10">配置定额</a><a href="#" class="btn_pub btn_blue">下载图表</a></div>
                    </div>
                    <div class="quota_viewBar">
                        <ul class="viewList">
                            <li class="changeColor" dtype="month"><i class="icon_small"></i>月视图</li>
                            <li dtype="year"><i class="icon_small"></i>年视图</li>
                        </ul>
                        <span class="datetime"><em class="ft22">2014 - 9</em></span>
                    </div>
                    <div class="pubCon_grey h220">
                    	<div class="chart_quota_detail">
                        	<ul>
                        		<li>
                        			<span class="chart_quota_d_t">实际能耗</span>
                        			<div class="chart_quota_d_i">
                        				<p><img src="<spring:url value="/resources/images/quota_pic01.jpg"></spring:url>"></p>
                        				<p><strong class="colfff ft40">8,000 </strong><span class="unit">kWh</span></p>
                        			</div>
                        		</li>
                        		<li>
                        			<span class="chart_quota_d_t">月均定额</span>
                        			<div class="chart_quota_d_i">
                        				<p><img src="<spring:url value="/resources/images/quota_pic02.jpg"></spring:url>"></p>
                        				<p><strong class="colfff ft40">4,000 </strong><span class="unit">kWh</span></p>
                        			</div>
                        		</li>
                        	</ul>
                        </div>
                    </div>
                    <p class="amount_num"><i class="icon_small arrow_up_y mr10"></i>截至到目前，2014年总用电快于定额计划10%</p>
                </div>
            </div>
            <!-- 照明系统 -->
            <div class="pubBox mr20">
                <div class="pubCon_blackThree w455 h400">
                    <div class="pub_title">
                        <h3 class="ft30"><i class="flag flag_h40 flag_red mr15 mt15"></i>照明系统</h3>
                        <div class="funBtnBar mr10"><a href="#setEvery" data-toggle="modal" class="left btn_pub btn_green mr10">配置定额</a><a href="#" class="btn_pub btn_blue">下载图表</a></div>
                    </div>
                    <div class="quota_viewBar">
                        <ul class="viewList">
                            <li dtype="month" class="changeColor"><i class="icon_small"></i>月视图</li>
                            <li dtype="year"><i class="icon_small"></i>年视图</li>
                        </ul>
                        <span class="datetime"><em class="ft22">2014 - 9</em></span>
                    </div>
                    <div class="pubCon_grey h220">
                    	<div class="chart_quota_detail">
                        	<ul>
                        		<li>
                        			<span class="chart_quota_d_t">实际能耗</span>
                        			<div class="chart_quota_d_i">
                        				<p><img src="<spring:url value="/resources/images/quota_pic03.jpg"></spring:url>"></p>
                        				<p><strong class="colfff ft40">8,000 </strong><span class="unit">kWh</span></p>
                        			</div>
                        		</li>
                        		<li>
                        			<span class="chart_quota_d_t">月均定额</span>
                        			<div class="chart_quota_d_i">
                        				<p><img src="<spring:url value="/resources/images/quota_pic02.jpg"></spring:url>"></p>
                        				<p><strong class="colfff ft40">4,000 </strong><span class="unit">kWh</span></p>
                        			</div>
                        		</li>
                        	</ul>
                        </div>
                    </div>
                    <p class="amount_num"><i class="icon_small arrow_down_y mr10"></i>截至到目前，2014年总用电慢于定额计划10%</p>
                </div>
            </div>
            <!-- 办公设备 -->
            <div class="pubBox mr20">
                <div class="pubCon_blackThree w455 h400">
                    <div class="pub_title">
                        <h3 class="ft30"><i class="flag flag_h40 flag_yellow mr15 mt15"></i>办公设备</h3>
                        <div class="funBtnBar mr10"><a href="#setEvery" data-toggle="modal" class="left btn_pub btn_green mr10">配置定额</a><a href="#" class="btn_pub btn_blue">下载图表</a></div>
                    </div>
                    <div class="quota_viewBar">
                        <ul class="viewList">
                            <li dtype="month" class="changeColor"><i class="icon_small"></i>月视图</li>
                            <li dtype="year"><i class="icon_small"></i>年视图</li>
                        </ul>
                        <span class="datetime"><em class="ft22">2014 - 9</em></span>
                    </div>
                    <div class="pubCon_grey h220">
                    	<div class="chart_quota_detail">
                        	<ul>
                        		<li>
                        			<span class="chart_quota_d_t">实际能耗</span>
                        			<div class="chart_quota_d_i">
                        				<p><img src="<spring:url value="/resources/images/quota_pic01.jpg"></spring:url>"></p>
                        				<p><strong class="colfff ft40">8,000 </strong><span class="unit">kWh</span></p>
                        			</div>
                        		</li>
                        		<li>
                        			<span class="chart_quota_d_t">月均定额</span>
                        			<div class="chart_quota_d_i">
                        				<p><img src="<spring:url value="/resources/images/quota_pic02.jpg"></spring:url>"></p>
                        				<p><strong class="colfff ft40">4,000 </strong><span class="unit">kWh</span></p>
                        			</div>
                        		</li>
                        	</ul>
                        </div>
                    </div>
                    <p class="amount_num"><i class="icon_small arrow_up_y mr10"></i>截至到目前，2014年总用电快于定额计划10%</p>
                </div>
            </div>
            <!-- 其他系统 -->
            <div class="pubBox">
                <div class="pubCon_blackThree w455 h400">
                    <div class="pub_title">
                        <h3 class="ft30"><i class="flag flag_h40 flag_blue mr15 mt15"></i>其他系统</h3>
                        <div class="funBtnBar mr10"><a href="#setEvery" data-toggle="modal" class="left btn_pub btn_green mr10">配置定额</a><a href="#" class="btn_pub btn_blue">下载图表</a></div>
                    </div>
                    <div class="quota_viewBar">
                        <ul class="viewList">
                            <li dtype="month" class="changeColor"><i class="icon_small"></i>月视图</li>
                            <li dtype="year"><i class="icon_small"></i>年视图</li>
                        </ul>
                        <span class="datetime"><em class="ft22">2014 - 9</em></span>
                    </div>
                    <div class="pubCon_grey h220">
                    	<div class="chart_quota_detail">
                        	<ul>
                        		<li>
                        			<span class="chart_quota_d_t">实际能耗</span>
                        			<div class="chart_quota_d_i">
                        				<p><img src="<spring:url value="/resources/images/quota_pic04.jpg"></spring:url>"></p>
                        				<p><strong class="colfff ft40">8,000 </strong><span class="unit">kWh</span></p>
                        			</div>
                        		</li>
                        		<li>
                        			<span class="chart_quota_d_t">月均定额</span>
                        			<div class="chart_quota_d_i">
                        				<p><img src="<spring:url value="/resources/images/quota_pic02.jpg"></spring:url>"></p>
                        				<p><strong class="colfff ft40">4,000 </strong><span class="unit">kWh</span></p>
                        			</div>
                        		</li>
                        	</ul>
                        </div>
                    </div>
                    <p class="amount_num"><i class="icon_small arrow_down_y mr10"></i>截至到目前，2014年总用电慢于定额计划10%</p>
                </div>
            </div>
        </div>
    </div>
</div>
<!--弹出框-->
<!-- 建筑总用电定额弹出框 -->
<div class="modal hide fade alert_box w830" id="setAll">
    <div class="modal-header alert_box_header"> <a href="#" class="icon_small alert_close" data-dismiss="modal"></a>
        <h4>建筑总用电定额配置</h4>
    </div>
    <div class="modal-body alert_body">
        <div class="alert_bodyL" style="height: 530px;">
            <div class="moduleBox_black left mb10">
                <div class="systemBox">
                    <h3 class="pt10 pb10"><i class="flag flag_green mr15 mt8"></i>配置年定额</h3>
                </div>
                <div class="quota_setAll_list">
                    <div class="quota_setInfo">
                        <label>选择年份</label>
                        <div class="filter_widget">
                            <select class="selectpicker">
                                <option>2014</option>
                                <option>2013</option>
                                <option>2012</option>
                                <option>2011</option>
                            </select>
                        </div>
                    </div>
                    <div class="quota_setInfo">
                        <label>建筑总用电定额</label>
                        <div class="formInput"><input type="text" class="quota_input"><span class="unit">kWh</span></div>
                    </div>
                </div>
            </div>
            <div class="moduleBox_black left">
                <div class="systemBox">
                    <h3 class="pt10"><i class="flag flag_blue mr15 mt8"></i>配置月定额</h3>
                </div>
                <div class="quota_setAll_list quota_setAll_list_month">
                    <div class="quota_setInfo">
                        <label>一月</label>
                        <div class="formInput"><input type="text" class="quota_input"><span class="unit">kWh</span></div>
                    </div>
                    <div class="quota_setInfo">
                        <label>七月</label>
                        <div class="formInput"><input type="text" class="quota_input"><span class="unit">kWh</span></div>
                    </div>
                    <div class="quota_setInfo">
                        <label>二月</label>
                        <div class="formInput"><input type="text" class="quota_input"><span class="unit">kWh</span></div>
                    </div>
                    <div class="quota_setInfo">
                        <label>八月</label>
                        <div class="formInput"><input type="text" class="quota_input"><span class="unit">kWh</span></div>
                    </div>
                    <div class="quota_setInfo">
                        <label>三月</label>
                        <div class="formInput"><input type="text" class="quota_input"><span class="unit">kWh</span></div>
                    </div>
                    <div class="quota_setInfo">
                        <label>九月</label>
                        <div class="formInput"><input type="text" class="quota_input"><span class="unit">kWh</span></div>
                    </div>
                    <div class="quota_setInfo">
                        <label>四月</label>
                        <div class="formInput"><input type="text" class="quota_input"><span class="unit">kWh</span></div>
                    </div>
                    <div class="quota_setInfo">
                        <label>十月</label>
                        <div class="formInput"><input type="text" class="quota_input"><span class="unit">kWh</span></div>
                    </div>
                    <div class="quota_setInfo">
                        <label>五月</label>
                        <div class="formInput"><input type="text" class="quota_input"><span class="unit">kWh</span></div>
                    </div>
                    <div class="quota_setInfo">
                        <label>十一月</label>
                        <div class="formInput"><input type="text" class="quota_input"><span class="unit">kWh</span></div>
                    </div>
                    <div class="quota_setInfo">
                        <label>六月</label>
                        <div class="formInput"><input type="text" class="quota_input"><span class="unit">kWh</span></div>
                    </div>
                    <div class="quota_setInfo">
                        <label>十二月</label>
                        <div class="formInput"><input type="text" class="quota_input"><span class="unit">kWh</span></div>
                    </div>
                </div>
            </div>
        </div>
        <div class="alert_bodyR" style="height: 530px;">
            <div class="history_dataCon">
                <div class="systemBox">
                    <h3 class="pt10 pl20 pb10">历史数据</h3>
                    <span class="export_showBtn"></span>
                </div>
                <div class="history_showhide">
                    <div class="quota_setAll_list">
                        <div class="quota_setInfo">
                            <label>选择年份</label>
                            <div class="filter_widget">
                                <select class="selectpicker">
                                    <option>2014</option>
                                    <option>2013</option>
                                    <option>2012</option>
                                    <option>2011</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="quota_set_tab">
                        <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <th>建筑总用电定额</th>
                                <th><em>20,000</em></th>
                                <th><em>kWh</em></th>
                            </tr>
                            <tr>
                                <td>一月</td>
                                <td><em>1,000</em></td>
                                <td><em>kWh</em></td>
                            </tr>
                            <tr>
                                <td>二月</td>
                                <td><em>1,500</em></td>
                                <td><em>kWh</em></td>
                            </tr>
                            <tr>
                                <td>三月</td>
                                <td><em>3,000</em></td>
                                <td><em>kWh</em></td>
                            </tr>
                            <tr>
                                <td>四月</td>
                                <td><em>2,500</em></td>
                                <td><em>kWh</em></td>
                            </tr>
                            <tr>
                                <td>五月</td>
                                <td><em>2,500</em></td>
                                <td><em>kWh</em></td>
                            </tr>
                            <tr>
                                <td>六月</td>
                                <td><em>2,500</em></td>
                                <td><em>kWh</em></td>
                            </tr>
                            <tr>
                                <td>七月</td>
                                <td><em>2,500</em></td>
                                <td><em>kWh</em></td>
                            </tr>
                            <tr>
                                <td>八月</td>
                                <td><em>2,500</em></td>
                                <td><em>kWh</em></td>
                            </tr>
                            <tr>
                                <td>九月</td>
                                <td><em>2,500</em></td>
                                <td><em>kWh</em></td>
                            </tr>
                            <tr>
                                <td>十月</td>
                                <td><em>2,500</em></td>
                                <td><em>kWh</em></td>
                            </tr>
                            <tr>
                                <td>十一月</td>
                                <td><em>2,500</em></td>
                                <td><em>kWh</em></td>
                            </tr>
                            <tr>
                                <td>十二月</td>
                                <td><em>2,500</em></td>
                                <td><em>kWh</em></td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="modal-footer alert_box_footer">
        <input type="button" class="btn_red" value="确认" >
        <input type="button" class="btn_black" value="清空" >
        <input type="button" class="btn_black" value="取消" data-dismiss="modal">
    </div>
</div>
<!-- 建筑用电分项定额弹出框 -->
<div class="modal hide fade alert_box w830" id="setEvery">
 <div class="modal-header alert_box_header"> <a href="#" class="icon_small alert_close" data-dismiss="modal"></a>
        <h4>建筑用电分项定额配置</h4>
    </div>
    <div class="modal-body alert_body">
        <div class="alert_bodyL" style="height: 400px;">
            <div class="moduleBox_black left mb10">
                <div class="systemBox">
                    <h3 class="pt10 pb10"><i class="flag flag_green mr15 mt8"></i>配置年定额</h3>
                </div>
                <div class="quota_setAll_list">
                    <div class="quota_setInfo">
                        <label>选择年份</label>
                        <div class="filter_widget">
                            <select class="selectpicker">
                                <option>2014</option>
                                <option>2013</option>
                                <option>2012</option>
                                <option>2011</option>
                            </select>
                        </div>
                    </div>
                </div>
                <div class="quota_set_tab">
                    <table border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <th>建筑总用电定额</th>
                            <th><em>20,000</em></th>
                            <th><em>kWh</em></th>
                        </tr>
                        <tr>
                            <td colspan="3" class="col9fa0a2">请先配置建筑总用电定额</td>
                        </tr>
                    </table>
                </div>
                <div class="quota_setAll_list mt10">
                    <div class="quota_setInfo">
                        <label>暖通空调用电定额</label>
                        <div class="formInput"><input type="text" class="quota_input"><span class="unit">%</span></div>
                    </div>
                    <div class="quota_setInfo">
                        <label>照明系统用电定额</label>
                        <div class="formInput"><input type="text" class="quota_input"><span class="unit">%</span></div>
                    </div>
                    <div class="quota_setInfo">
                        <label>办公设备用电定额</label>
                        <div class="formInput"><input type="text" class="quota_input"><span class="unit">%</span></div>
                    </div>
                    <div class="quota_setInfo">
                        <label>其他系统用电定额</label>
                        <div class="formInput"><input type="text" class="quota_input"><span class="unit">%</span></div>
                    </div>
                </div>
            </div>
        </div>
        <div class="alert_bodyR" style="height: 400px;">
            <div class="history_dataCon" style="height: 398px;">
                <div class="systemBox">
                    <h3 class="pt10 pl20 pb10">历史数据</h3>
                    <span class="export_showBtn"></span>
                </div>
                <div class="history_showhide">
                    <div class="quota_setAll_list">
                        <div class="quota_setInfo">
                            <label>选择年份</label>
                            <div class="filter_widget">
                                <select class="selectpicker">
                                    <option>2014</option>
                                    <option>2013</option>
                                    <option>2012</option>
                                    <option>2011</option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="quota_set_tab">
                        <table border="0" cellpadding="0" cellspacing="0">
                            <tr>
                                <th>建筑总用电定额</th>
                                <th><em>20,000</em></th>
                                <th><em>kWh</em></th>
                            </tr>
                            <tr>
                                <td>暖通空调用电定额</td>
                                <td><em>1,000</em></td>
                                <td><em>kWh</em></td>
                            </tr>
                            <tr>
                                <td>照明系统用电定额</td>
                                <td><em>1,000</em></td>
                                <td><em>kWh</em></td>
                            </tr>
                            <tr>
                                <td>办公设备用电定额</td>
                                <td><em>1,000</em></td>
                                <td><em>kWh</em></td>
                            </tr>
                            <tr>
                                <td>其他系统用电定额</td>
                                <td><em>1,000</em></td>
                                <td><em>kWh</em></td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="modal-footer alert_box_footer">
        <input type="button" class="btn_red" value="确认" >
        <input type="button" class="btn_black" value="清空" >
        <input type="button" class="btn_black" value="取消" data-dismiss="modal">
    </div>
</div>

