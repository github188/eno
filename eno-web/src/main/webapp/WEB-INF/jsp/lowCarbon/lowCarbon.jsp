<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>

<!--container start-->
<div class="container">
	<div class="span12 js_subnav">
        <ul>
        	<li class="js_subnav_current">碳足迹</li>
            <li>诺金酒店低碳环境标准</li>
        </ul>
    </div>
    <!-- 碳足迹 -->
    <div class="span12 js_tab_content">
        <div class="span12 lowCarbon_top_content">
        	<div class="span8 lowC_box_left pubCon_blackOne">
            	<div class="pub_title">
                    <h3><i class="flag flag_big flag_red mr15"></i>总体碳足迹</h3>
                    <div class="lowC_viewBar">
                    	<ul class="viewList">
                            <li dtype="day"><i class="icon_small"></i>日视图</li>
                            <li class="changeColor" dtype="week"><i class="icon_small"></i>周视图</li>
                            <li dtype="month"><i class="icon_small"></i>月视图</li>
                            <li dtype="year"><i class="icon_small"></i>年视图</li>
                            <li dtype="all"><i class="icon_small"></i>全生命周期</li>
                        </ul>
                    </div>
                    <div class="download_btn mt25 mr20"><a href="#" class="btn_pub btn_blue">下载图表</a></div>
                </div>
                <div class="chartBbox">
                	<div class="chartPic_lowC_One center">
                    	<img src="<spring:url value="/resources/images/lowcarbon_pic01.jpg"></spring:url>">
                    </div>
                </div>
            </div>
            <!-- 碳足迹计算器 -->
            <div class="span4 lowC_box_right pubCon_blackOne" style="margin-top: -459px !important;">
            	<div class="pub_title">
                    <h3 class="ft30"><i class="flag flag_h40 flag_purple mt15 mr15"></i>碳足迹计算器</h3>
                    <ul class="js_lowC_nav">
                    	<li class="trafficDiv js_lowC_nav_blue"><i class="icon_small traffic"></i><span>交通</span></li>
                    	<li class="buildDiv"><i class="icon_small build"></i><span>建筑</span></li>
                    	<!--<li class="reduceDiv"><i class="icon_small reduce_carbon"></i><span>我要减少排放</span></li>-->
                    </ul>
                </div>
                <!-- 碳足迹计算器—交通 -->
                <div class="js_lowC_content show">
                	<table border="0" cellpadding="0" cellspacing="0" class="tab_lowC">
                    	<tr>
                    		<td>
                    			<span class="formCheck">
                                	<input type="checkbox" name="airplane" id="airplane" class="css-checkbox" disabled>
                                    <label for="airplane" class="css-label"></label>
                                </span>
                    			<i class="icon_small icon_airplane"></i>
                    			<span class="fromName">飞机</span>
                    			<div class="valueBox"><input type="text" class="lowC_input" value="50" readonly /><span class="unit">km</span></div>
                    		</td>
                    		<td>
                    			<span class="formCheck">
                                	<input type="checkbox" name="bus" id="bus" class="css-checkbox" checked="checked">
                                    <label for="bus" class="css-label"></label>
                                </span>
                    			<i class="icon_small icon_bus"></i>
                    			<span class="fromName">公交车</span>
                    			<div class="valueBox"><input type="text" class="lowC_input" value="50" /><span class="unit">km</span></div>
                    		</td>
                    	</tr>
                    	<tr>
                    		<td>
                    			<span class="formCheck">
                                	<input type="checkbox" name="train" id="train" class="css-checkbox" checked="checked">
                                    <label for="train" class="css-label"></label>
                                </span>
                    			<i class="icon_small icon_train"></i>
                    			<span class="fromName">火车</span>
                    			<div class="valueBox"><input type="text" class="lowC_input" value="50" /><span class="unit">km</span></div>
                    		</td>
                    		<td>
                    			<span class="formCheck">
                                	<input type="checkbox" name="subway" id="subway" class="css-checkbox" checked="checked">
                                    <label for="subway" class="css-label"></label>
                                </span>
                    			<i class="icon_small icon_subway"></i>
                    			<span class="fromName">地铁</span>
                    			<div class="valueBox"><input type="text" class="lowC_input" value="50" /><span class="unit">km</span></div>
                    		</td>
                    	</tr>
                    	<tr>
                    		<td>
                    			<span class="formCheck">
                                	<input type="checkbox" name="car" id="car" class="css-checkbox" checked="checked">
                                    <label for="car" class="css-label"></label>
                                </span>
                    			<i class="icon_small icon_car"></i>
                    			<span class="fromName">汽车</span>
                    			<div class="valueBox"><input type="text" class="lowC_input" value="50" /><span class="unit">km</span></div>
                    		</td>
                    		<td>
                    			<span class="formCheck">
                                	<input type="checkbox" name="trolleybus" id="trolleybus" class="css-checkbox" checked="checked">
                                    <label for="trolleybus" class="css-label"></label>
                                </span>
                    			<i class="icon_small icon_trolleybus"></i>
                    			<span class="fromName">电车</span>
                    			<div class="valueBox"><input type="text" class="lowC_input" value="50" /><span class="unit">km</span></div>
                    		</td>
                    	</tr>
                    	<tr>
                    		<td>
                    			<span class="formCheck">
                                	<input type="checkbox" name="ship" id="ship" class="css-checkbox" checked="checked">
                                    <label for="ship" class="css-label"></label>
                                </span>
                    			<i class="icon_small icon_ship"></i>
                    			<span class="fromName">轮船</span>
                    			<div class="valueBox"><input type="text" class="lowC_input" value="50" /><span class="unit">km</span></div>
                    		</td>
                    		<td>
                                <div class="filter_widget">
                                    <select class="selectpicker">
                                        <option>汽油</option>
                                        <option>汽油</option>
                                        <option>柴油</option>
                                    </select>
                                </div>
                            </td>
                    	</tr>
                    </table>
                    <div class="lowC_detail">
                    	<div class="chartBbox">
                        	<div class="lowCPic_bom mt15"><img src="<spring:url value="/resources/images/lowcarbon_pic02.jpg"></spring:url>"></div>
                        </div>
                        <div class="lowC_data">
                        	<h1>总建筑碳足迹</h1>
                            <p class="mt15"><strong>3000</strong></p>
                            <p><em>kg CO2</em></p>
                        </div>
                    </div>
                </div>
                <!-- 碳足迹计算器—建筑 -->
                <div class="js_lowC_content">
                	<table border="0" cellpadding="0" cellspacing="0" class="tab_lowC">
                    	<tr>
                    		<td>
                    			<span class="formCheck">
                                	<input type="checkbox" name="electric" id="electric" class="css-checkbox" checked="checked">
                                    <label for="electric" class="css-label"></label>
                                </span>
                    			<i class="icon_small icon_electric"></i>
                    			<span class="fromName">用电</span>
                    			<div class="valueBox"><input type="text" class="lowC_input" /><span class="unit">kWh</span></div>
                    		</td>
                    		<td>
                    			<span class="formCheck">
                                	<input type="checkbox" name="kerosene" id="kerosene" class="css-checkbox" checked="checked">
                                    <label for="kerosene" class="css-label"></label>
                                </span>
                    			<i class="icon_small icon_kerosene"></i>
                    			<span class="fromName">煤油</span>
                    			<div class="valueBox"><input type="text" class="lowC_input" /><span class="unit">kg</span></div>
                    		</td>
                    	</tr>
                    	<tr>
                    		<td>
                    			<span class="formCheck">
                                	<input type="checkbox" name="gas" id="gas" class="css-checkbox" checked="checked">
                                    <label for="gas" class="css-label"></label>
                                </span>
                    			<i class="icon_small icon_gas"></i>
                    			<span class="fromName">天然气</span>
                    			<div class="valueBox"><input type="text" class="lowC_input" /><span class="unit">m³</span></div>
                    		</td>
                    		<td>
                    			<span class="formCheck">
                                	<input type="checkbox" name="lp_gas" id="lp_gas" class="css-checkbox" checked="checked">
                                    <label for="lp_gas" class="css-label"></label>
                                </span>
                    			<i class="icon_small icon_lp_gas"></i>
                    			<span class="fromName">液化石油气</span>
                    			<div class="valueBox"><input type="text" class="lowC_input" /><span class="unit">m³</span></div>
                    		</td>
                    	</tr>
                    	<tr>
                    		<td>
                    			<span class="formCheck">
                                	<input type="checkbox" name="coal_gas" id="coal_gas" class="css-checkbox">
                                    <label for="coal_gas" class="css-label"></label>
                                </span>
                    			<i class="icon_small icon_gas"></i>
                    			<span class="fromName">煤气</span>
                    			<div class="valueBox"><input type="text" class="lowC_input" /><span class="unit">m³</span></div>
                    		</td>
                    		<td>
                    			<span class="formCheck">
                                	<input type="checkbox" name="coal_heating" id="coal_heating" class="css-checkbox">
                                    <label for="coal_heating" class="css-label"></label>
                                </span>
                    			<i class="icon_small icon_heating"></i>
                    			<span class="fromName">燃煤供暖</span>
                    			<div class="valueBox"><input type="text" class="lowC_input" /><span class="unit">m³</span></div>
                    		</td>
                    	</tr>
                    	<tr>
                    		<td>
                    			<span class="formCheck">
                                	<input type="checkbox" name="coal" id="coal" class="css-checkbox">
                                    <label for="coal" class="css-label"></label>
                                </span>
                    			<i class="icon_small icon_coal"></i>
                    			<span class="fromName">煤炭</span>
                    			<div class="valueBox"><input type="text" class="lowC_input" /><span class="unit">kg</span></div>
                    		</td>
                    		<td>
                            	<span class="formCheck">
                                	<input type="checkbox" name="gas_heating" id="gas_heating" class="css-checkbox">
                                    <label for="gas_heating" class="css-label"></label>
                                </span>
                    			<i class="icon_small icon_heating"></i>
                    			<span class="fromName">燃气供暖</span>
                    			<div class="valueBox"><input type="text" class="lowC_input" /><span class="unit">m³</span></div>
                            </td>
                    	</tr>
                    </table>
                    <div class="lowC_detail">
                    	<div class="chartBbox">
                        	<div class="lowCPic_bom mt15"><img src="<spring:url value="/resources/images/lowcarbon_pic02.jpg"></spring:url>"></div>
                        </div>
                        <div class="lowC_data">
                        	<h1>总建筑碳足迹</h1>
                            <p class="mt15"><strong>3000</strong></p>
                            <p><em>kg CO2</em></p>
                        </div>
                    </div>
                </div>
                <!-- 碳足迹计算器—我要减少碳排放 -->
                <!--<div class="js_lowC_content">
                    <div class="lowC_detail">
                        <div class="chartBbox">
                            <div class="lowCPic_top mt15"><img src="<spring:url value="/resources/images/lowcarbon_pic02.jpg"></spring:url>"></div>
                        </div>
                        <div class="lowC_data">
                            <h1>总建筑碳足迹</h1>
                            <p class="mt15"><strong>3000</strong></p>
                            <p><em>kg CO2</em></p>
                        </div>
                    </div>
                    <table border="0" cellpadding="0" cellspacing="0" class="tab_lowC tab_lowC_pl20">
                    	<tr><td class="ft20">从垂手可及的小事做起，减少你的碳排放</td></tr>
                    	<tr><td>换节能灯泡：11 瓦节能灯就相当约 80 瓦的白炽灯，大大减少用电量，省钱又环保。</td></tr>
                    	<tr><td>二十六度空调： 夏天二十六度已经广为使用，冬天也不要高于十八度。</td></tr>
                    	<tr><td>选择有“ 能效标志 ”的冰箱和空调</td></tr>
                    </table>
                </div>-->
            </div>
        </div>
        <div class="span12 lowCarbon_bottom_content">
        	<!-- 会议室碳足迹 -->
        	<div class="span4 lowC_Box pubCon_blackOne" style=" margin-left: -40px;">
            	<div class="pub_title">
                    <h3 class="ft30"><i class="flag flag_h40 flag_green mt15 mr15"></i>会议室碳足迹</h3>
                    <div class="download_btn mt25 mr20"><a href="#" class="btn_pub btn_blue">下载图表</a></div>
                </div>
                <div class="lowC_viewBar_s">
                	<ul class="viewList">
                        <li dtype="day"><i class="icon_small"></i>日视图</li>
                        <li class="changeColor" dtype="week"><i class="icon_small"></i>周视图</li>
                        <li dtype="month"><i class="icon_small"></i>月视图</li>
                        <li dtype="year"><i class="icon_small"></i>年视图</li>
                        <li dtype="all"><i class="icon_small"></i>全生命周期</li>
                    </ul>
                </div>
                <div class="chartBbox">
                	<div class="chartPic_lowC_Three">
                    	<img src="<spring:url value="/resources/images/lowcarbon_pic03.jpg"></spring:url>">
                    </div>
                </div>
            </div>
            <!-- 客房碳足迹 -->
            <div class="span4 lowC_Box pubCon_blackOne" style=" margin-left: 1px;">
            	<div class="pub_title">
                    <h3 class="ft30"><i class="flag flag_h40 flag_blue mt15 mr15"></i>客房碳足迹</h3>
                    <div class="download_btn mt25 mr20"><a href="#" class="btn_pub btn_blue">下载图表</a></div>
                </div>
                <div class="lowC_viewBar_s">
                	<ul class="viewList">
                        <li dtype="day"><i class="icon_small"></i>日视图</li>
                        <li class="changeColor" dtype="week"><i class="icon_small"></i>周视图</li>
                        <li dtype="month"><i class="icon_small"></i>月视图</li>
                        <li dtype="year"><i class="icon_small"></i>年视图</li>
                        <li dtype="all"><i class="icon_small"></i>全生命周期</li>
                    </ul>
                </div>
                <div class="chartBbox">
                	<div class="chartPic_lowC_Three">
                    	<img src="<spring:url value="/resources/images/lowcarbon_pic03.jpg"></spring:url>">
                    </div>
                </div>
            </div>
            <!-- 其他碳足迹 -->
            <div class="span4 lowC_Box pubCon_blackOne">
            	<div class="pub_title">
                    <h3 class="ft30"><i class="flag flag_h40 flag_red mt15 mr15"></i>其他碳足迹</h3>
                    <div class="download_btn mt25 mr20"><a href="#" class="btn_pub btn_blue">下载图表</a></div>
                </div>
                <div class="lowC_viewBar_s">
                	<ul class="viewList">
                        <li dtype="day"><i class="icon_small"></i>日视图</li>
                        <li class="changeColor" dtype="week"><i class="icon_small"></i>周视图</li>
                        <li dtype="month"><i class="icon_small"></i>月视图</li>
                        <li dtype="year"><i class="icon_small"></i>年视图</li>
                        <li dtype="all"><i class="icon_small"></i>全生命周期</li>
                    </ul>
                </div>
                <div class="chartBbox">
                	<div class="chartPic_lowC_Three">
                    	<img src="<spring:url value="/resources/images/lowcarbon_pic03.jpg"></spring:url>">
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- 诺金酒店低碳环境标准 -->
    <div class="span12 js_tab_content mt15" style="display:none;">
        <div class="span12 lowCarbon_content">
            <div class="pub_title">
                <h3><i class="flag flag_big flag_red mr15"></i>诺金酒店低碳环境标准</h3>
                <div class="lowC_viewBar">
                    <ul class="viewList_language">
                        <li>中文版</li>
                        <li class="changeColor">English</li>
                    </ul>
                </div>
                <div class="download_btn mt25 mr20"><a href="#" class="btn_pub btn_blue">下载PDF</a></div>
            </div>
            <div class="lowCarbon_documentBox pubCon_blackOne">
                <div class="lowCarbon_pdfBox" id="pdf_doc"></div>
            </div>
        </div>
    </div>
</div>  
