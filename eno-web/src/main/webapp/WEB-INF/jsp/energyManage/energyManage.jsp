<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/plugins/bootstrap/css/bootstrap.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/base.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/energy.css">
<script src="<spring:url value="/resources/scripts/jquery-1.9.1.min.js"></spring:url>"></script>
<script src="<spring:url value="/resources/plugins/bootstrap/js/bootstrap.js"></spring:url>"></script>
<script src="<spring:url value="/resources/plugins/My97DatePicker/WdatePicker.js"></spring:url>"></script>
<script src="<spring:url value="/resources/plugins/bootstrap-select/bootstrap-select.min.js"></spring:url>"></script>

<!--container start-->
<div class="container">
	<div class="span12 js_subnav">
        <ul>
        	<li class="js_subnav_current">建筑能耗情况统计</li>
            <li>建筑能效指标分析</li>
        </ul>
    </div>
    <!-- 建筑能耗情况统计 -->
    <div class="span12 js_tab_content w_max" style="margin-left: -2px !important;">
        <div class="span12 energy_content">
        	<ul class="js_Echart_nav" id="js_Echart_nav_one">
            	<li class="js_Echart_nav_current"><a href="#" class="icon_small Echart_viewOne"></a></li>
                <li><a href="#" class="icon_small Echart_viewSix"></a></li>
                <li><a href="#" class="icon_small Echart_viewNine"></a></li>
            </ul>
            <!-- 一宫格 开始 -->
            <div class="js_Echart_content" id="js_Echart_content_one">
            	<div class="pubCon_blackOne Echart_view_con">
                	<div class="Echart_sidebar pubCon_blackTwo">
                    	<h3><i class="flag flag_green mr15 mt20"></i>建筑总体能耗</h3>
                        <div class="Echart_sidebar_subcon">
                        	<ul>
                            	<li><a href="#">建筑总能耗</a></li>
                                <li class="selecton"><a href="#">建筑空调能耗</a></li>
                                <li><a href="#">建筑能耗分项</a></li>
                                <li><a href="#">建筑总用电</a></li>
                            </ul>
                        </div>
                        <h3><i class="flag flag_green mr15 mt20"></i>建筑总体能耗</h3>
                        <div class="Echart_sidebar_subcon">
                        	<ul>
                            	<li><a href="#">关键指标</a></li>
                                <li><a href="#">建筑总体指标</a></li>
                                <li><a href="#">建筑系统级指标</a></li>
                                <li><a href="#">建筑设备级指标</a></li>
                            </ul>
                        </div>
                    </div>
                    <div class="Echart_main">
                    	<div class="Echart_viewBar">
                        	<ul class="viewList">
                                <li dtype="day"><i class="icon_small"></i>日视图</li>
                                <li class="changeColor" dtype="week"><i class="icon_small"></i>周视图</li>
                                <li dtype="month"><i class="icon_small"></i>月视图</li>
                                <li dtype="year"><i class="icon_small"></i>年视图</li>
                                <li dtype="all"><i class="icon_small"></i>全生命周期</li>
                        		<li class="compare"><label>与</label>
                                    <div class="calendarBar"><input id="day_date" class="Wdate" type="text" onClick="WdatePicker()"></div>
                                    <label class="ml10">对比</label>
                                </li>
                        	</ul>
                            <ul class="tab_viewBtn">
                            	<li class="view_current"><a href="#" class="icon_small Echart_column_btn"></a></li>
                                <li><a href="#" class="icon_small Echart_line_btn"></a></li>
                                <li><a href="#" class="icon_small Echart_pie_btn"></a></li>
                            </ul>
                            <div class="download_btn"><a href="#" class="btn_pub btn_blue">下载图表</a></div>
                        </div>
                        <div class="Echart_detail">
                        	<div class="span12 Echart_e_t">
                            	<h4>建筑空调能耗</h4>
                                <div class="chartBbox center">
                                	<em>2014 - 8 -10 ~ 2014 - 8 - 16 </em>
                                    <div class="chartPic_energyOne">
                                    	<img src="<spring:url value="/resources/images/energy_pic01.jpg"></spring:url>">
                                    </div>
                                </div>
                            </div>
                            <div class="span12 Echart_e_b">
                            	<div class="span2 pubCon_blackTwo w240">
                                	<h3><i class="flag flag_green mr15 mt20"></i>建筑总能耗</h3>
                                    <p><strong class="ft50 colfff mr10">4000</strong><span class="unit">kWh</span></p>
                                </div>
                            	<div class="span2 pubCon_blackTwo w240 ml25">
                                	<h3><i class="flag flag_yellow mr15 mt20"></i>同比上周</h3>
                                    <p><i class="icon_small icon_rise"></i> <i class="icon_small icon_down"></i><strong class="ft50 colfff ml5 mr10">20</strong><span class="unit">%</span></p>
                                </div>
                                <div class="span2 pubCon_blackTwo w240 ml25">
                                	<h3><i class="flag flag_red mr15 mt20"></i>最大值</h3>
                                    <p><strong class="ft50 colfff mr10">2000</strong><span class="unit">kWh</span></p>
                                </div>
                                <div class="span2 pubCon_blackTwo w240 ml25">
                                	<h3><i class="flag flag_blue mr15 mt20"></i>最小值</h3>
                                    <p><strong class="ft50 colfff mr10">1000</strong><span class="unit">kWh</span></p>
                                </div>
                                <div class="span2 pubCon_blackTwo w240 ml25">
                                	<h3><i class="flag flag_yellow mr15 mt20"></i>平均功率</h3>
                                    <p><strong class="ft50 colfff mr10">10</strong><span class="unit">kWh</span></p>
                                </div>
                                <div class="span2 pubCon_blackTwo w240 ml25">
                                	<h3><i class="flag flag_green mr15 mt20"></i>参考费用</h3>
                                    <p><strong class="ft50 colfff mr10">2000</strong><span class="unit">yuan</span></p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- 一宫格 结束 -->
            <!-- 六宫格 开始 -->
            <div class="js_Echart_content" style="display:none;">
            	<div class="Echart_view_con">
                	<div class="Echart_view_box">
                        <div class="pubBox mr15">
                            <div class="pubCon_blackThree w613 h380">
                                <div class="Echart_view_title">
                                    <h3><i class="flag flag_green mr15 mt20"></i>建筑总能耗</h3>
                                    <div class="download_btn mt15 mr15"><a href="#" class="btn_pub btn_blue">下载图表</a></div>
                                </div>
                                <div class="Echart_viewBar_s">
                                    <ul class="viewList">
                                        <li dtype="day"><i class="icon_small"></i>日视图</li>
                                        <li dtype="week" class="changeColor"><i class="icon_small"></i>周视图</li>
                                        <li dtype="month"><i class="icon_small"></i>月视图</li>
                                        <li dtype="year"><i class="icon_small"></i>年视图</li>
                                        <li dtype="all"><i class="icon_small"></i>全生命周期</li>
                                    </ul>
                                </div>
                                <div class="chartBbox chartBboxh240 pubCon_grey">
                                    <div class="chartPic_energyTwo"><img src="<spring:url value="/resources/images/energy_pic02.jpg"></spring:url>"></div>
                                </div>
                            </div>
                        </div>
                        <div class="pubBox mr15">
                            <div class="pubCon_blackThree w613 h380">
                                <div class="Echart_view_title">
                                    <h3><i class="flag flag_red mr15 mt20"></i>建筑总能耗分项</h3>
                                    <div class="download_btn mt15 mr15"><a href="#" class="btn_pub btn_blue">下载图表</a></div>
                                </div>
                                <div class="Echart_viewBar_s">
                                    <ul class="viewList">
                                        <li dtype="day"><i class="icon_small"></i>日视图</li>
                                        <li dtype="week" class="changeColor"><i class="icon_small"></i>周视图</li>
                                        <li dtype="month"><i class="icon_small"></i>月视图</li>
                                        <li dtype="year"><i class="icon_small"></i>年视图</li>
                                        <li dtype="all"><i class="icon_small"></i>全生命周期</li>
                                    </ul>
                                </div>
                                <div class="chartBbox chartBboxh240 pubCon_grey">
                                    <div class="chartPic_energyTwo"><img src="<spring:url value="/resources/images/energy_pic03.jpg"></spring:url>"></div>
                                </div>
                            </div>
                        </div>
                        <div class="pubBox">
                            <div class="pubCon_blackThree w613 h380">
                                <div class="Echart_view_title">
                                    <h3><i class="flag flag_blue mr15 mt20"></i>建筑总用电</h3>
                                    <div class="download_btn mt15 mr15"><a href="#" class="btn_pub btn_blue">下载图表</a></div>
                                </div>
                                <div class="Echart_viewBar_s">
                                    <ul class="viewList">
                                        <li dtype="day" class="changeColor"><i class="icon_small"></i>日视图</li>
                                        <li dtype="week"><i class="icon_small"></i>周视图</li>
                                        <li dtype="month"><i class="icon_small"></i>月视图</li>
                                        <li dtype="year"><i class="icon_small"></i>年视图</li>
                                        <li dtype="all"><i class="icon_small"></i>全生命周期</li>
                                    </ul>
                                </div>
                                <div class="chartBbox chartBboxh240 pubCon_grey">
                                    <div class="chartPic_energyTwo"><img src="<spring:url value="/resources/images/energy_pic04.jpg"></spring:url>"></div>
                                </div>
                            </div>
                        </div>
                        <div class="pubBox mr15 mt15">
                            <div class="pubCon_blackThree w613 h380">
                                <div class="Echart_view_title">
                                    <h3><i class="flag flag_purple mr15 mt20"></i>空调系统用电分项</h3>
                                    <div class="download_btn mt15 mr15"><a href="#" class="btn_pub btn_blue">下载图表</a></div>
                                </div>
                                <div class="Echart_viewBar_s">
                                    <ul class="viewList">
                                        <li dtype="day"><i class="icon_small"></i>日视图</li>
                                        <li dtype="week"><i class="icon_small"></i>周视图</li>
                                        <li dtype="month" class="changeColor"><i class="icon_small"></i>月视图</li>
                                        <li dtype="year"><i class="icon_small"></i>年视图</li>
                                        <li dtype="all"><i class="icon_small"></i>全生命周期</li>
                                    </ul>
                                </div>
                                <div class="chartBbox chartBboxh240 pubCon_grey">
                                    <div class="chartPic_energyTwo"><img src="<spring:url value="/resources/images/energy_pic05.jpg"></spring:url>"></div>
                                </div>
                            </div>
                        </div>
                        <div class="pubBox mr15 mt15">
                            <div class="pubCon_blackThree w613 h380">
                                <div class="Echart_view_title">
                                    <h3><i class="flag flag_yellow mr15 mt20"></i>数据中心用电趋势</h3>
                                    <div class="download_btn mt15 mr15"><a href="#" class="btn_pub btn_blue">下载图表</a></div>
                                </div>
                                <div class="Echart_viewBar_s">
                                    <ul class="viewList">
                                        <li dtype="day"><i class="icon_small"></i>日视图</li>
                                        <li dtype="week"><i class="icon_small"></i>周视图</li>
                                        <li dtype="month"><i class="icon_small"></i>月视图</li>
                                        <li dtype="year" class="changeColor"><i class="icon_small"></i>年视图</li>
                                        <li dtype="all"><i class="icon_small"></i>全生命周期</li>
                                    </ul>
                                </div>
                                <div class="chartBbox chartBboxh240 pubCon_grey">
                                    <div class="chartPic_energyTwo"><img src="<spring:url value="/resources/images/energy_pic06.jpg"></spring:url>"></div>
                                </div>
                            </div>
                        </div>
                        <div class="pubBox mt15">
                            <div class="pubCon_blackThree w613 h380">
                                <div class="Echart_view_title">
                                    <h3><i class="flag flag_green_light mr15 mt20"></i>建筑设备能耗</h3>
                                    <div class="download_btn mt15 mr15"><a href="#" class="btn_pub btn_blue">下载图表</a></div>
                                </div>
                                <div class="Echart_viewBar_s">
                                    <ul class="viewList">
                                        <li dtype="day"><i class="icon_small"></i>日视图</li>
                                        <li dtype="week" class="changeColor"><i class="icon_small"></i>周视图</li>
                                        <li dtype="month"><i class="icon_small"></i>月视图</li>
                                        <li dtype="year"><i class="icon_small"></i>年视图</li>
                                        <li dtype="all"><i class="icon_small"></i>全生命周期</li>
                                    </ul>
                                </div>
                                <div class="chartBbox chartBboxh240 pubCon_grey">
                                    <div class="chartPic_energyTwo"><img src="<spring:url value="/resources/images/energy_pic07.jpg"></spring:url>"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="pagination">
                        <ul class="right">
                            <li><a href="#" class="icon_small pre_disabled"></a></li>
                            <li class="active"><a href="#">1</a></li>
                            <li><a href="#">2</a></li>
                            <li><a href="#">3</a></li>
                            <li><a href="#">4</a></li>
                            <li><a href="#" class="icon_small next"></a></li>
                        </ul>
                        <p>共 <em>24</em> 条数据 当前显示第1-8条</p>
                    </div>
                </div>
            </div>
            <!-- 六宫格 结束 -->
            <!-- 九宫格 开始 -->
            <div class="js_Echart_content js_Echart_content_special" style="display:none;">
            	<div class="Echart_view_con">
                	<div class="Echart_viewBar_n">
                    	<i class="flag_h60 flag_blue_light"></i>
                    	<ul class="viewList ml15 mt15">
                            <li dtype="day"><i class="icon_small"></i>日视图</li>
                            <li dtype="week" class="changeColor"><i class="icon_small"></i>周视图</li>
                            <li dtype="month"><i class="icon_small"></i>月视图</li>
                            <li dtype="year"><i class="icon_small"></i>年视图</li>
                            <li dtype="all"><i class="icon_small"></i>全生命周期</li>
                        </ul>
                        <div class="download_btn download_btn_bgB"><a href="#" class="btn_pub btn_blue">下载图表</a></div>
                    </div>
                	<div class="Echart_view_box">
                        <div class="pubBox mr8">
                            <div class="pubCon_blackThree w618 h240">
                                <div class="Echart_view_title"><h3><i class="flag flag_green mr15 mt20"></i>建筑总能耗</h3></div>
                                <div class="chartPic_energyTwo"><img src="<spring:url value="/resources/images/energy_pic08.jpg"></spring:url>"></div>
                            </div>
                        </div>
                        <div class="pubBox mr8">
                            <div class="pubCon_blackThree w618 h240">
                                <div class="Echart_view_title"><h3><i class="flag flag_red mr15 mt20"></i>建筑总能耗分项</h3></div>
                                <div class="chartPic_energyTwo"><img src="<spring:url value="/resources/images/energy_pic09.jpg"></spring:url>"></div>
                            </div>
                        </div>
                        <div class="pubBox">
                            <div class="pubCon_blackThree w618 h240">
                                <div class="Echart_view_title"><h3><i class="flag flag_blue mr15 mt20"></i>建筑总用电</h3></div>
                                <div class="chartPic_energyTwo"><img src="<spring:url value="/resources/images/energy_pic10.jpg"></spring:url>"></div>
                            </div>
                        </div>
                        <div class="pubBox mr8 mt8">
                            <div class="pubCon_blackThree w618 h240">
                                <div class="Echart_view_title"><h3><i class="flag flag_purple mr15 mt20"></i>建筑总用水</h3></div>
                                <div class="chartPic_energyTwo"><img src="<spring:url value="/resources/images/energy_pic11.jpg"></spring:url>"></div>
                            </div>
                        </div>
                        <div class="pubBox mr8 mt8">
                            <div class="pubCon_blackThree w618 h240">
                                <div class="Echart_view_title"><h3><i class="flag flag_yellow mr15 mt20"></i>建筑总用气</h3></div>
                                <div class="chartPic_energyTwo"><img src="<spring:url value="/resources/images/energy_pic12.jpg"></spring:url>"></div>
                            </div>
                        </div>
                        <div class="pubBox mt8">
                            <div class="pubCon_blackThree w618 h240">
                                <div class="Echart_view_title"><h3><i class="flag flag_purple_deep mr15 mt20"></i>建筑用电分项</h3></div>
                                <div class="chartPic_energyTwo"><img src="<spring:url value="/resources/images/energy_pic13.jpg"></spring:url>"></div>
                            </div>
                        </div>
                        <div class="pubBox mr8 mt8">
                            <div class="pubCon_blackThree w618 h240">
                                <div class="Echart_view_title"><h3><i class="flag flag_green_light mr15 mt20"></i>空调系统用电分项</h3></div>
                                <div class="chartPic_energyTwo"><img src="<spring:url value="/resources/images/energy_pic14.jpg"></spring:url>"></div>
                            </div>
                        </div>
                        <div class="pubBox mr8 mt8">
                            <div class="pubCon_blackThree w618 h240">
                                <div class="Echart_view_title"><h3><i class="flag flag_red mr15 mt20"></i>数据中心用电趋势</h3></div>
                                <div class="chartPic_energyTwo"><img src="<spring:url value="/resources/images/energy_pic15.jpg"></spring:url>"></div>
                            </div>
                        </div>
                        <div class="pubBox mt8">
                            <div class="pubCon_blackThree w618 h240">
                                <div class="Echart_view_title"><h3><i class="flag flag_blue_light mr15 mt20"></i>建筑设备能耗</h3></div>
                                <div class="chartPic_energyTwo"><img src="<spring:url value="/resources/images/energy_pic16.jpg"></spring:url>"></div>
                            </div>
                        </div>
                    </div>
                    <div class="pagination">
                        <ul class="right">
                            <li><a href="#" class="icon_small pre_disabled"></a></li>
                            <li class="active"><a href="#">1</a></li>
                            <li><a href="#">2</a></li>
                            <li><a href="#">3</a></li>
                            <li><a href="#">4</a></li>
                            <li><a href="#" class="icon_small next"></a></li>
                        </ul>
                        <p>共 <em>24</em> 条数据 当前显示第1-8条</p>
                    </div>
                </div>
            </div>
            <!-- 九宫格 结束 -->
        </div>
    </div>
    <!--建筑能耗指标分析-->
    <div class="span12 js_tab_content w_max" style=" margin-left: -2px !important; display:none;">
        <div class="span12 energy_content">
            <ul class="js_Echart_nav" id="js_Echart_nav_two">
                <li class="js_Echart_nav_current"><a href="#" class="icon_small Echart_viewOne"></a></li>
                <li><a href="#" class="icon_small Echart_viewSix"></a></li>
                <li><a href="#" class="icon_small Echart_viewNine"></a></li>
            </ul>
            <!-- 一宫格 开始 -->
            <div class="js_Echart_content" id="js_Echart_content_two">
                <div class="pubCon_blackOne Echart_view_con">
                    <div class="Echart_sidebar pubCon_blackTwo">
                        <h3><i class="flag flag_green mr15 mt20"></i>建筑总体能耗</h3>
                        <div class="Echart_sidebar_subcon">
                            <ul>
                                <li><a href="#">建筑总能耗</a></li>
                                <li class="selecton"><a href="#">建筑空调能耗</a></li>
                                <li><a href="#">建筑能耗分项</a></li>
                                <li><a href="#">建筑总用电</a></li>
                            </ul>
                        </div>
                        <h3><i class="flag flag_green mr15 mt20"></i>建筑总体能耗</h3>
                        <div class="Echart_sidebar_subcon">
                            <ul>
                                <li><a href="#">关键指标</a></li>
                                <li><a href="#">建筑总体指标</a></li>
                                <li><a href="#">建筑系统级指标</a></li>
                                <li><a href="#">建筑设备级指标</a></li>
                            </ul>
                        </div>
                    </div>
                    <div class="Echart_main">
                        <div class="Echart_viewBar">
                            <ul class="viewList">
                                <li dtype="day"><i class="icon_small"></i>日视图</li>
                                <li dtype="week"><i class="icon_small"></i>周视图</li>
                                <li class="changeColor" dtype="month"><i class="icon_small"></i>月视图</li>
                                <li dtype="year"><i class="icon_small"></i>年视图</li>
                                <li dtype="all"><i class="icon_small"></i>全生命周期</li>
                                <li class="compare"><label>与</label>
                                    <div class="calendarBar"><input id="day_date" class="Wdate" type="text" onClick="WdatePicker()"></div>
                                    <label class="ml10">对比</label>
                                </li>
                            </ul>
                            <ul class="tab_viewBtn">
                                <li class="view_current"><a href="#" class="icon_small Echart_column_btn"></a></li>
                                <li><a href="#" class="icon_small Echart_line_btn"></a></li>
                                <li><a href="#" class="icon_small Echart_pie_btn"></a></li>
                            </ul>
                            <div class="download_btn"><a href="#" class="btn_pub btn_blue">下载图表</a></div>
                        </div>
                        <div class="Echart_detail">
                            <div class="span12 Echart_e_t">
                                <h4>建筑空调能耗</h4>
                                <div class="chartBbox center">
                                    <em>2014 - 8 -10 ~ 2014 - 8 - 16 </em>
                                    <div class="chartPic_energyOne">
                                        <img src="<spring:url value="/resources/images/energy_pic01.jpg"></spring:url>">
                                    </div>
                                </div>
                            </div>
                            <div class="span12 Echart_e_b">
                                <div class="span2 pubCon_blackTwo w240">
                                    <h3><i class="flag flag_green mr15 mt20"></i>建筑总能耗</h3>
                                    <p><strong class="ft50 colfff mr10">4000</strong><span class="unit">kWh</span></p>
                                </div>
                                <div class="span2 pubCon_blackTwo w240 ml25">
                                    <h3><i class="flag flag_yellow mr15 mt20"></i>同比上周</h3>
                                    <p><i class="icon_small icon_rise"></i> <i class="icon_small icon_down"></i><strong class="ft50 colfff ml5 mr10">20</strong><span class="unit">%</span></p>
                                </div>
                                <div class="span2 pubCon_blackTwo w240 ml25">
                                    <h3><i class="flag flag_red mr15 mt20"></i>最大值</h3>
                                    <p><strong class="ft50 colfff mr10">2000</strong><span class="unit">kWh</span></p>
                                </div>
                                <div class="span2 pubCon_blackTwo w240 ml25">
                                    <h3><i class="flag flag_blue mr15 mt20"></i>最小值</h3>
                                    <p><strong class="ft50 colfff mr10">1000</strong><span class="unit">kWh</span></p>
                                </div>
                                <div class="span2 pubCon_blackTwo w240 ml25">
                                    <h3><i class="flag flag_yellow mr15 mt20"></i>平均功率</h3>
                                    <p><strong class="ft50 colfff mr10">10</strong><span class="unit">kWh</span></p>
                                </div>
                                <div class="span2 pubCon_blackTwo w240 ml25">
                                    <h3><i class="flag flag_green mr15 mt20"></i>参考费用</h3>
                                    <p><strong class="ft50 colfff mr10">2000</strong><span class="unit">yuan</span></p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- 一宫格 结束 -->
            <!-- 六宫格 开始 -->
            <div class="js_Echart_content" style="display:none;">
                <div class="Echart_view_con">
                    <div class="Echart_view_box">
                        <!-- 单位面积总能耗 -->
                        <div class="pubBox mr15">
                            <div class="pubCon_blackThree w613 h380">
                                <div class="Echart_view_title">
                                    <h3><i class="flag flag_green mr15 mt20"></i>单位面积总能耗</h3>
                                    <div class="download_btn mt15 mr15"><a href="#" class="btn_pub btn_blue">下载图表</a></div>
                                </div>
                                <div class="Echart_viewBar_s">
                                    <ul class="viewList">
                                        <li dtype="day"><i class="icon_small"></i>日视图</li>
                                        <li dtype="week"><i class="icon_small"></i>周视图</li>
                                        <li dtype="month" class="changeColor"><i class="icon_small"></i>月视图</li>
                                        <li dtype="year"><i class="icon_small"></i>年视图</li>
                                        <li dtype="all"><i class="icon_small"></i>全生命周期</li>
                                    </ul>
                                </div>
                                <div class="chartBbox chartBboxh240 pubCon_grey">
                                    <div class="chartPic_energyTwo"><img src="<spring:url value="/resources/images/energy_pic02.jpg"></spring:url>"></div>
                                </div>
                            </div>
                        </div>
                        <!-- 单位面积用电量 -->
                        <div class="pubBox mr15">
                            <div class="pubCon_blackThree w613 h380">
                                <div class="Echart_view_title">
                                    <h3><i class="flag flag_red mr15 mt20"></i>单位面积用电量</h3>
                                    <div class="download_btn mt15 mr15"><a href="#" class="btn_pub btn_blue">下载图表</a></div>
                                </div>
                                <div class="Echart_viewBar_s">
                                    <ul class="viewList">
                                        <li dtype="day"><i class="icon_small"></i>日视图</li>
                                        <li dtype="week" class="changeColor"><i class="icon_small"></i>周视图</li>
                                        <li dtype="month"><i class="icon_small"></i>月视图</li>
                                        <li dtype="year"><i class="icon_small"></i>年视图</li>
                                        <li dtype="all"><i class="icon_small"></i>全生命周期</li>
                                    </ul>
                                </div>
                                <div class="chartBbox chartBboxh240 pubCon_grey">
                                    <div class="chartPic_energyTwo"><img src="<spring:url value="/resources/images/energy_pic04.jpg"></spring:url>"></div>
                                </div>
                            </div>
                        </div>
                        <!-- 单位面积耗冷量 -->
                        <div class="pubBox">
                            <div class="pubCon_blackThree w613 h380">
                                <div class="Echart_view_title">
                                    <h3><i class="flag flag_blue mr15 mt20"></i>单位面积耗冷量</h3>
                                    <div class="download_btn mt15 mr15"><a href="#" class="btn_pub btn_blue">下载图表</a></div>
                                </div>
                                <div class="Echart_viewBar_s">
                                    <ul class="viewList">
                                        <li dtype="day" class="changeColor"><i class="icon_small"></i>日视图</li>
                                        <li dtype="week"><i class="icon_small"></i>周视图</li>
                                        <li dtype="month"><i class="icon_small"></i>月视图</li>
                                        <li dtype="year"><i class="icon_small"></i>年视图</li>
                                        <li dtype="all"><i class="icon_small"></i>全生命周期</li>
                                    </ul>
                                </div>
                                <div class="chartBbox chartBboxh240 pubCon_grey">
                                    <div class="chartPic_energyTwo"><img src="<spring:url value="/resources/images/energy_pic06.jpg"></spring:url>"></div>
                                </div>
                            </div>
                        </div>
                        <!-- 人均总能耗 -->
                        <div class="pubBox mr15 mt15">
                            <div class="pubCon_blackThree w613 h380">
                                <div class="Echart_view_title">
                                    <h3><i class="flag flag_purple mr15 mt20"></i>人均总能耗</h3>
                                    <div class="download_btn mt15 mr15"><a href="#" class="btn_pub btn_blue">下载图表</a></div>
                                </div>
                                <div class="Echart_viewBar_s">
                                    <ul class="viewList">
                                        <li dtype="day"><i class="icon_small"></i>日视图</li>
                                        <li dtype="week"><i class="icon_small"></i>周视图</li>
                                        <li dtype="month" class="changeColor"><i class="icon_small"></i>月视图</li>
                                        <li dtype="year"><i class="icon_small"></i>年视图</li>
                                        <li dtype="all"><i class="icon_small"></i>全生命周期</li>
                                    </ul>
                                </div>
                                <div class="chartBbox chartBboxh240 pubCon_grey">
                                    <div class="chartPic_energyTwo"><img src="<spring:url value="/resources/images/energy_pic02.jpg"></spring:url>"></div>
                                </div>
                            </div>
                        </div>
                        <!-- 人均用电量 -->
                        <div class="pubBox mr15 mt15">
                            <div class="pubCon_blackThree w613 h380">
                                <div class="Echart_view_title">
                                    <h3><i class="flag flag_yellow mr15 mt20"></i>人均用电量</h3>
                                    <div class="download_btn mt15 mr15"><a href="#" class="btn_pub btn_blue">下载图表</a></div>
                                </div>
                                <div class="Echart_viewBar_s">
                                    <ul class="viewList">
                                        <li dtype="day"><i class="icon_small"></i>日视图</li>
                                        <li dtype="week"><i class="icon_small"></i>周视图</li>
                                        <li dtype="month"><i class="icon_small"></i>月视图</li>
                                        <li dtype="year" class="changeColor"><i class="icon_small"></i>年视图</li>
                                        <li dtype="all"><i class="icon_small"></i>全生命周期</li>
                                    </ul>
                                </div>
                                <div class="chartBbox chartBboxh240 pubCon_grey">
                                    <div class="chartPic_energyTwo"><img src="<spring:url value="/resources/images/energy_pic17.jpg"></spring:url>"></div>
                                </div>
                            </div>
                        </div>
                        <!-- 人均耗冷量 -->
                        <div class="pubBox mt15">
                            <div class="pubCon_blackThree w613 h380">
                                <div class="Echart_view_title">
                                    <h3><i class="flag flag_green_light mr15 mt20"></i>人均耗冷量</h3>
                                    <div class="download_btn mt15 mr15"><a href="#" class="btn_pub btn_blue">下载图表</a></div>
                                </div>
                                <div class="Echart_viewBar_s">
                                    <ul class="viewList">
                                        <li dtype="day"><i class="icon_small"></i>日视图</li>
                                        <li dtype="week" class="changeColor"><i class="icon_small"></i>周视图</li>
                                        <li dtype="month"><i class="icon_small"></i>月视图</li>
                                        <li dtype="year"><i class="icon_small"></i>年视图</li>
                                        <li dtype="all"><i class="icon_small"></i>全生命周期</li>
                                    </ul>
                                </div>
                                <div class="chartBbox chartBboxh240 pubCon_grey">
                                    <div class="chartPic_energyTwo"><img src="<spring:url value="/resources/images/energy_pic18.jpg"></spring:url>"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- 分页 -->
                    <div class="pagination">
                        <ul class="right">
                            <li><a href="#" class="icon_small pre_disabled"></a></li>
                            <li class="active"><a href="#">1</a></li>
                            <li><a href="#">2</a></li>
                            <li><a href="#">3</a></li>
                            <li><a href="#">4</a></li>
                            <li><a href="#" class="icon_small next"></a></li>
                        </ul>
                        <p>共 <em>24</em> 条数据 当前显示第1-8条</p>
                    </div>
                </div>
            </div>
            <!-- 六宫格 结束 -->
            <!-- 九宫格 开始 -->
            <div class="js_Echart_content js_Echart_content_special" style="display:none;">
                <div class="Echart_view_con">
                    <div class="Echart_viewBar_n">
                        <i class="flag_h60 flag_blue_light"></i>
                        <ul class="viewList ml15 mt15">
                            <li dtype="day"><i class="icon_small"></i>日视图</li>
                            <li dtype="week" class="changeColor"><i class="icon_small"></i>周视图</li>
                            <li dtype="month"><i class="icon_small"></i>月视图</li>
                            <li dtype="year"><i class="icon_small"></i>年视图</li>
                            <li dtype="all"><i class="icon_small"></i>全生命周期</li>
                        </ul>
                        <div class="download_btn download_btn_bgB"><a href="#" class="btn_pub btn_blue">下载图表</a></div>
                    </div>
                    <div class="Echart_view_box">
                        <div class="pubBox mr8">
                            <div class="pubCon_blackThree w618 h240">
                                <div class="Echart_view_title"><h3><i class="flag flag_green mr15 mt20"></i>单位面积总能耗</h3></div>
                                <div class="chartPic_energyTwo"><img src="<spring:url value="/resources/images/energy_pic19.jpg"></spring:url>"></div>
                            </div>
                        </div>
                        <div class="pubBox mr8">
                            <div class="pubCon_blackThree w618 h240">
                                <div class="Echart_view_title"><h3><i class="flag flag_red mr15 mt20"></i>单位面积用电量</h3></div>
                                <div class="chartPic_energyTwo"><img src="<spring:url value="/resources/images/energy_pic20.jpg"></spring:url>.jpg"></div>
                            </div>
                        </div>
                        <div class="pubBox">
                            <div class="pubCon_blackThree w618 h240">
                                <div class="Echart_view_title"><h3><i class="flag flag_blue mr15 mt20"></i>单位面积耗冷量</h3></div>
                                <div class="chartPic_energyTwo"><img src="<spring:url value="/resources/images/energy_pic21.jpg"></spring:url>"></div>
                            </div>
                        </div>
                        <div class="pubBox mr8 mt8">
                            <div class="pubCon_blackThree w618 h240">
                                <div class="Echart_view_title"><h3><i class="flag flag_purple mr15 mt20"></i>人均总能耗</h3></div>
                                <div class="chartPic_energyTwo"><img src="<spring:url value="/resources/images/energy_pic22.jpg"></spring:url>"></div>
                            </div>
                        </div>
                        <div class="pubBox mr8 mt8">
                            <div class="pubCon_blackThree w618 h240">
                                <div class="Echart_view_title"><h3><i class="flag flag_yellow mr15 mt20"></i>人均用电量</h3></div>
                                <div class="chartPic_energyTwo"><img src="<spring:url value="/resources/images/energy_pic12.jpg"></spring:url>"></div>
                            </div>
                        </div>
                        <div class="pubBox mt8">
                            <div class="pubCon_blackThree w618 h240">
                                <div class="Echart_view_title"><h3><i class="flag flag_purple_deep mr15 mt20"></i>人均耗冷量</h3></div>
                                <div class="chartPic_energyTwo"><img src="<spring:url value="/resources/images/energy_pic23.jpg"></spring:url>"></div>
                            </div>
                        </div>
                        <div class="pubBox mr8 mt8">
                            <div class="pubCon_blackThree w618 h240">
                                <div class="Echart_view_title"><h3><i class="flag flag_green_light mr15 mt20"></i>单位面积供热用电量</h3></div>
                                <div class="chartPic_energyTwo"><img src="<spring:url value="/resources/images/energy_pic16.jpg"></spring:url>"></div>
                            </div>
                        </div>
                        <div class="pubBox mr8 mt8">
                            <div class="pubCon_blackThree w618 h240">
                                <div class="Echart_view_title"><h3><i class="flag flag_red mr15 mt20"></i>单位面积空调用电量</h3></div>
                                <div class="chartPic_energyTwo"><img src="<spring:url value="/resources/images/energy_pic19.jpg"></spring:url>"></div>
                            </div>
                        </div>
                        <div class="pubBox mt8">
                            <div class="pubCon_blackThree w618 h240">
                                <div class="Echart_view_title"><h3><i class="flag flag_blue_light mr15 mt20"></i>单位面积照明用电量</h3></div>
                                <div class="chartPic_energyTwo"><img src="<spring:url value="/resources/images/energy_pic16.jpg"></spring:url>"></div>
                            </div>
                        </div>
                    </div>
                    <div class="pagination">
                        <ul class="right">
                            <li><a href="#" class="icon_small pre_disabled"></a></li>
                            <li class="active"><a href="#">1</a></li>
                            <li><a href="#">2</a></li>
                            <li><a href="#">3</a></li>
                            <li><a href="#">4</a></li>
                            <li><a href="#" class="icon_small next"></a></li>
                        </ul>
                        <p>共 <em>24</em> 条数据 当前显示第1-8条</p>
                    </div>
                </div>
            </div>
            <!-- 九宫格 结束 -->
        </div>
    </div>
</div>  