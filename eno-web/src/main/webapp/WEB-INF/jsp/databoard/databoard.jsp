<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<style>
<!--
.allSummary_box input {
	width: 245px;
}
.allSummary_box select {
	width: 257px;
	border: 0 !important;
	background-color: #000 !important;
	border-radius: 0;
	color: #fff;
	padding: 5px 6px;
}
-->
</style>
<!--container start-->
<div class="container">
    <div class="span12 wrapper">
        <!--left content-->
        <div class="span3 w400 mt20 ml20">
            <div class="pubCon h930">
                <div class="allSummary_box" style="padding: 26px 0px 0px 40px;">
                    <form action="#" id="datamodel" modelAttribute="datamodel">
                    	<div><span style="float: left;">点位名称：</span><input type="text" name="pointname" id="pointname" /></div>
                    	<div><span style="float: left;">查询时间：</span>
                    	<select id="timescales" name="timescales">
                    		<option value="HOUR">小时</option>
                    		<option value="DAY">天</option>
                    		<option value="WEEK">周</option>
                    		<option value="MONTH">月</option>
                    		<option value="YEAR">年</option>
                    	</select></div>
                    	<div><span style="float: left;">求值范围：</span>
                    	<select id="aggregatefunction" name="aggregatefunction">
                    		<option value="SUM">SUM</option>
                    		<option value="MAX">MAX</option>
                    		<option value="MIN">MIN</option>
                    		<option value="AVG">AVG</option>
                    		<option value="CHANGE">CHANGE</option>
                    		<option value="PERCENT">PERCENT</option>
                    	</select></div>
                    	<div><span style="float: left;">取值规则：</span>
                    	<select id="range" name="range" disabled>
                    		<option value="">仅当求值范围为PERCENT时有用</option>
                    		<option value="sum">sum</option>
                    		<option value="avg">avg</option>
                    		<option value="max">max</option>
                    		<option value="min">min</option>
                    	</select></div>
                    	<div><span style="float: left;">值变化度：</span><input type="text" name="additioncontion" id="additioncontion" placeholder="范围为CHANGE,该项为SUM或AVG" /></div>
                    	<div><span style="float: left;">起始时间：</span><input type="text" name="timestart" id="timestart" value="2014-12-09" /></div>
                    	<div><span style="float: left;">终止时间：</span><input type="text" name="timeend" id="timeend" value="2014-12-10"/></div>
                    	<div><span style="float: left;">时间格式：</span>
                    	<select id="timeformat" name="timeformat">
                    		<option value="">请选择</option>
                    		<option value="HH:mm">HH:mm</option>
                    		<option value="MM-dd">MM-dd</option>
                    		<option value="yyyy">yyyy</option>
                    		<option value="yyyy-MM-dd">yyyy-MM-dd</option>
                    		<option value="yyyy-MM-dd HH:mm:ss">yyyy-MM-dd HH:mm:ss</option>
                    	</select></div>
                    	<div>
                    		<input type="button" value="渲染" onclick="renderChart()" style="width: 64px; margin-left: 122px;background-color: #5ab3d7;" />
                    	</div>
                    </form>
                </div>
            </div>
        </div>
        <!--center content-->
        <div class="span9 w1060 mt20 ml10">
            <div class="pubCon h480 ">
                <div class="pubCon_black con_indexT">
                    <div class="con_i_l">
                        <div class="index_tit"><h2>今日用能状况</h2></div>
                        <div class="chartBbox">
<!--                             <p class="chart_tit">今日实时用电功率</p> -->
                            <div class="chartPic_indexOne">
								<div id="todayenergy" style="width: 1060px; height: 300px;"></div>
							</div>
                        </div>
                    </div>
                </div>
                <div id="sql"></div>
            </div>
        </div>
        
        
        <div>
        	<form action="#" id="datamodel" modelAttribute="datamodel">
                    	<div><span style="float: left;">点位名称：</span><input type="text" id="pointname1" value="CO10_RA#KT_3F_2,CO18_RA#KT_5_5" /></div>
                    	<div><span style="float: left;">查询时间：</span><input type="text" id="timescales1" value="hour,hour" /></div>
                    	<div><span style="float: left;">求值范围：</span><input type="text" id="aggregatefunction1" value="sum,sum" /></div>
                    	<div><span style="float: left;">点位名称：</span><input type="text" id="timestart1" value="2014-12-09,2014-12-09" /></div>
                    	<div><span style="float: left;">点位名称：</span><input type="text" id="timeend1" value="2014-12-10,2014-12-10" /></div>
                    	<div>
                    		<input type="button" value="渲染" onclick="testRenderChart()" style="width: 64px; margin-left: 122px;background-color: #5ab3d7;" />
                    	</div>
                    </form>
        </div>
        
        
    </div>
</div>
