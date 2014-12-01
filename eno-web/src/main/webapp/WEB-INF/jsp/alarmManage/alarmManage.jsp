<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!--container start-->
<div class="container">
	<div class="span12 js_subnav">
        <ul>
        	<li class="js_subnav_current">实时报警</li>
            <li>历史报警</li>
        </ul>
    </div>
    <!--实时报警-->
    <div class="span12 js_tab_content">
        <!-- 上边内容 -->
        <div class="span12 alarm_top_content">
        	<div class="span6 alarmBox pubCon_blackOne mr20">
            	<div class="pub_title">
                	<h3><i class="flag_big flag_green mr20"></i>报警统计</h3>
                    <div class="formBar mt30" style="margin-left:65px;">
                    	<label>选择子系统</label>
                        <div class="filter_widget">
                            <select class="selectpicker">
                                <option>全部系统</option>
                                <option>暖通空调</option>
                                <option>照明系统</option>
                                <option>变配电</option>
                            </select>
                        </div>
                    </div>
                    <span class="right alarm_time mt30">截至<em>15:21</em></span>
                </div>
                <div class="chartBbox left">
                	<div class="chartPic_alarmOne">
                    	<img src="../resources/images/alarm_pic01.jpg" />
                        <strong class="chartTxt">33</strong>
                    </div>
                    <p class="alarm_count">报警总数</p>
                </div>
                <div class="chart_alarmOne_detail">
                    <ul>
                        <li>
                            <span class="chart_d_t">报警总数</span>
                            <div class="chart_d_i">
                                <p><img src="../resources/images/alarm_pic02.jpg" /></p>
                                <p><em class="colfff ft22">13</em> 次</p>
                            </div>
                        </li>
                        <li>
                            <span class="chart_d_t">已处理数</span>
                            <div class="chart_d_i">
                                <p><img src="../resources/images/alarm_pic03.jpg" /></p>
                                <p><em class="colfff ft22">5</em> 次</p>
                            </div>
                        </li>
                        <li>
                            <span class="chart_d_t">未处理数</span>
                            <div class="chart_d_i">
                                <p><img src="../resources/images/alarm_pic04.jpg" /></p>
                                <p><em class="colfff ft22">8</em> 次</p>
                            </div>
                        </li>
                    </ul>
                </div>
            </div>
        	<div class="span6 alarmBox pubCon_blackOne">
            	<div class="pub_title">
                	<h3><i class="flag_big flag_blue mr20"></i>报警趋势</h3>
                    <div class="formBar mt30" style="margin-left:65px;">
                    	<label>选择子系统</label>
                        <div class="filter_widget">
                            <select class="selectpicker" id="choosechildsystem">
                                <option>全部系统</option>
                                <option>暖通空调</option>
                                <option>照明系统</option>
                                <option>变配电</option>
                            </select>
                        </div>
                    </div>
                    <span class="right alarm_time mt30">截至<em>15:21</em></span>
                </div>
                <div class="chartBbox">
                	<div class="chartPic_alarmTwo">
						<div id="alarmTrendChart"></div>
					</div>
                </div>
            </div>
        </div>
        <!-- 下边表格内容 -->
        <div class="span12 alarm_bottom_content pubCon_blackOne">
        	<div class="pub_title">
        		<h3><i class="flag_big flag_red mr20"></i>待处理列表</h3>
                <div class="formBar mt30" style="margin-left:65px;">
                    <label>选择子系统</label>
                    <div class="filter_widget">
                        <select class="selectpicker">
                            <option>全部系统</option>
                            <option>暖通空调</option>
                            <option>照明系统</option>
                            <option>变配电</option>
                        </select>
                    </div>
                </div>
                <span class="right alarm_time mt30">截至<em>15:21</em></span>
        	</div>
            <div class="tableBox mt10 ml25 mr25">
            	<table cellpadding="0" cellspacing="0" border="0" class="tableList">
                	<tr>
                    	<th>报警日期</th>
                        <th>报警时间</th>
                        <th>报警描述</th>
                        <th>子系统</th>
                        <th>设备</th>
                        <th>位置</th>
                        <th>报警级别</th>
                        <th>报警处理</th>
                    </tr>
                    <tr>
                    	<td width="8%"><em>2013-11-09</em></td>
                    	<td width="8%"><em>21：15：21</em></td>
                    	<td width="25%">注意报警描述最多二十四个字报警描述最多二十三字</td>
                    	<td width="8%">防盗报警</td>
                    	<td width="14%">注意设备名称最多十二个字</td>
                    	<td width="25%">注意位置描述最多二十四个字报警描述最多二十三字</td>
                    	<td width="5%"><font class="red">极高</font></td>
                    	<td width="7%"><a href="#deal" data-toggle="modal" class="btn_pub btn_red">处理</a></td>
                    </tr>
                    <tr>
                    	<td><em>2013-11-09</em></td>
                    	<td><em>21：15：21</em></td>
                    	<td>注意报警描述最多二十四个字报警描述最多二十三字</td>
                    	<td>暖通空调</td>
                    	<td>注意设备名称最多十二个字</td>
                    	<td>注意位置描述最多二十四个字报警描述最多二十三字</td>
                    	<td><font class="orange">高</font></td>
                    	<td><a href="#deal" data-toggle="modal" class="btn_pub btn_red">处理</a></td>
                    </tr>
                    <tr>
                    	<td><em>2013-11-09</em></td>
                    	<td><em>21：15：21</em></td>
                    	<td>注意报警描述最多二十四个字报警描述最多二十三字</td>
                    	<td>消防系统</td>
                    	<td>注意设备名称最多十二个字</td>
                    	<td>注意位置描述最多二十四个字报警描述最多二十三字</td>
                    	<td><font>中</font></td>
                    	<td><a href="#deal" data-toggle="modal" class="btn_pub btn_red">处理</a></td>
                    </tr>
                    <tr>
                    	<td><em>2013-11-09</em></td>
                    	<td><em>21：15：21</em></td>
                    	<td>注意报警描述最多二十四个字报警描述最多二十三字</td>
                    	<td>防盗报警</td>
                    	<td>注意设备名称最多十二个字</td>
                    	<td>注意位置描述最多二十四个字报警描述最多二十三字</td>
                    	<td><font class="blue">低</font></td>
                    	<td><a href="#deal" data-toggle="modal" class="btn_pub btn_red">处理</a></td>
                    </tr>
                    <tr>
                    	<td><em>2013-11-09</em></td>
                    	<td><em>21：15：21</em></td>
                    	<td>注意报警描述最多二十四个字报警描述最多二十三字</td>
                    	<td>暖通空调</td>
                    	<td>注意设备名称最多十二个字</td>
                    	<td>注意位置描述最多二十四个字报警描述最多二十三字</td>
                    	<td><font class="green">极低</font></td>
                    	<td><a href="#deal" data-toggle="modal" class="btn_pub btn_red">处理</a></td>
                    </tr>
                    <tr>
                    	<td><em>2013-11-09</em></td>
                    	<td><em>21：15：21</em></td>
                    	<td>注意报警描述最多二十四个字报警描述最多二十三字</td>
                    	<td>消防系统</td>
                    	<td>注意设备名称最多十二个字</td>
                    	<td>注意位置描述最多二十四个字报警描述最多二十三字</td>
                    	<td><font>中</font></td>
                    	<td></td>
                    </tr>
                    <tr>
                    	<td><em>2013-11-09</em></td>
                    	<td><em>21：15：21</em></td>
                    	<td>注意报警描述最多二十四个字报警描述最多二十三字</td>
                    	<td>消防系统</td>
                    	<td>注意设备名称最多十二个字</td>
                    	<td>注意位置描述最多二十四个字报警描述最多二十三字</td>
                    	<td><font>中</font></td>
                    	<td></td>
                    </tr>
                </table>
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
    </div>
    <!--历史报警-->
    <div class="span12 js_tab_content" style="display:none;">
        <div class="span12 alarm_top_content">
        	<div class="alarmCon_history pubCon_blackOne">
            	<div class="pub_title">
                	<div class="formBar formBar_h60">
                    	<label>选择日期</label>
                        <div class="calendarBar"><input class="Wdate" type="text" onClick="WdatePicker()"></div>
                        <span class="to">至</span>
                        <div class="calendarBar"><input class="Wdate" type="text" onClick="WdatePicker()"></div>
                    </div>
                    <div class="formBar formBar_h60 borderL">
                    	<label>选择子系统</label>
                        <div class="filter_widget">
                            <select class="selectpicker">
                                <option>全部系统</option>
                                <option>暖通空调</option>
                                <option>照明系统</option>
                                <option>变配电</option>
                            </select>
                        </div>
                    </div>
                    <div class="formBar formBar_h60 borderL">
                    	<label>选择级别</label>
                        <div class="filter_widget">
                            <select class="selectpicker">
                                <option>选择级别</option>
                                <option>全部级别</option>
                                <option>极高</option>
                                <option>高</option>
                                <option>中</option>
                                <option>低</option>
                            </select>
                        </div>
                    </div>
                    <div class="funBtnBar"><a href="#" class="btn_pub btn_blue">下载列表</a></div>
                </div>
                <div class="tableBox mt10 ml25 mr25">
                	<table cellpadding="0" cellspacing="0" border="0" class="tableList">
                    	<tr>
                            <th>报警日期</th>
                            <th>报警时间</th>
                            <th>报警描述</th>
                            <th>子系统</th>
                            <th>设备</th>
                            <th>位置</th>
                            <th>报警级别</th>
                            <th>报警来源</th>
                        </tr>
                        <tr>
                        	<td width="8%"><em>2013-11-09</em></td>
                            <td width="8%"><em>21：15：21</em></td>
                            <td width="25%">注意报警描述最多二十四个字报警描述最多二十三字</td>
                            <td width="8%">防盗报警</td>
                            <td width="14%">注意设备名称最多十二个字</td>
                            <td width="25%">注意位置描述最多二十四个字报警描述最多二十三字</td>
                            <td width="5%"><font class="red">极高</font></td>
                            <td width="7%">温感报警</td>
                        </tr>
                        <tr>
                            <td><em>2013-11-09</em></td>
                            <td><em>21：15：21</em></td>
                            <td>注意报警描述最多二十四个字报警描述最多二十三字</td>
                            <td>暖通空调</td>
                            <td>注意设备名称最多十二个字</td>
                            <td>注意位置描述最多二十四个字报警描述最多二十三字</td>
                            <td><font class="orange">高</font></td>
                            <td>烟感报警</td>
                        </tr>
                        <tr>
                            <td><em>2013-11-09</em></td>
                            <td><em>21：15：21</em></td>
                            <td>注意报警描述最多二十四个字报警描述最多二十三字</td>
                            <td>消防系统</td>
                            <td>注意设备名称最多十二个字</td>
                            <td>注意位置描述最多二十四个字报警描述最多二十三字</td>
                            <td><font>中</font></td>
                            <td>手动报警器</td>
                        </tr>
                        <tr>
                            <td><em>2013-11-09</em></td>
                            <td><em>21：15：21</em></td>
                            <td>注意报警描述最多二十四个字报警描述最多二十三字</td>
                            <td>防盗报警</td>
                            <td>注意设备名称最多十二个字</td>
                            <td>注意位置描述最多二十四个字报警描述最多二十三字</td>
                            <td><font class="blue">低</font></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td><em>2013-11-09</em></td>
                            <td><em>21：15：21</em></td>
                            <td>注意报警描述最多二十四个字报警描述最多二十三字</td>
                            <td>暖通空调</td>
                            <td>注意设备名称最多十二个字</td>
                            <td>注意位置描述最多二十四个字报警描述最多二十三字</td>
                            <td><font class="green">极低</font></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td><em>2013-11-09</em></td>
                            <td><em>21：15：21</em></td>
                            <td>注意报警描述最多二十四个字报警描述最多二十三字</td>
                            <td>暖通空调</td>
                            <td>注意设备名称最多十二个字</td>
                            <td>注意位置描述最多二十四个字报警描述最多二十三字</td>
                            <td><font class="orange">高</font></td>
                            <td>烟感报警</td>
                        </tr>
                        <tr>
                            <td><em>2013-11-09</em></td>
                            <td><em>21：15：21</em></td>
                            <td>注意报警描述最多二十四个字报警描述最多二十三字</td>
                            <td>消防系统</td>
                            <td>注意设备名称最多十二个字</td>
                            <td>注意位置描述最多二十四个字报警描述最多二十三字</td>
                            <td><font>中</font></td>
                            <td>手动报警器</td>
                        </tr>
                        <tr>
                            <td><em>2013-11-09</em></td>
                            <td><em>21：15：21</em></td>
                            <td>注意报警描述最多二十四个字报警描述最多二十三字</td>
                            <td>防盗报警</td>
                            <td>注意设备名称最多十二个字</td>
                            <td>注意位置描述最多二十四个字报警描述最多二十三字</td>
                            <td><font class="blue">低</font></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td><em>2013-11-09</em></td>
                            <td><em>21：15：21</em></td>
                            <td>注意报警描述最多二十四个字报警描述最多二十三字</td>
                            <td>暖通空调</td>
                            <td>注意设备名称最多十二个字</td>
                            <td>注意位置描述最多二十四个字报警描述最多二十三字</td>
                            <td><font class="green">极低</font></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td><em>2013-11-09</em></td>
                            <td><em>21：15：21</em></td>
                            <td>注意报警描述最多二十四个字报警描述最多二十三字</td>
                            <td>暖通空调</td>
                            <td>注意设备名称最多十二个字</td>
                            <td>注意位置描述最多二十四个字报警描述最多二十三字</td>
                            <td><font class="orange">高</font></td>
                            <td>烟感报警</td>
                        </tr>
                        <tr>
                            <td><em>2013-11-09</em></td>
                            <td><em>21：15：21</em></td>
                            <td>注意报警描述最多二十四个字报警描述最多二十三字</td>
                            <td>消防系统</td>
                            <td>注意设备名称最多十二个字</td>
                            <td>注意位置描述最多二十四个字报警描述最多二十三字</td>
                            <td><font>中</font></td>
                            <td>手动报警器</td>
                        </tr>
                        <tr>
                            <td><em>2013-11-09</em></td>
                            <td><em>21：15：21</em></td>
                            <td>注意报警描述最多二十四个字报警描述最多二十三字</td>
                            <td>防盗报警</td>
                            <td>注意设备名称最多十二个字</td>
                            <td>注意位置描述最多二十四个字报警描述最多二十三字</td>
                            <td><font class="blue">低</font></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td><em>2013-11-09</em></td>
                            <td><em>21：15：21</em></td>
                            <td>注意报警描述最多二十四个字报警描述最多二十三字</td>
                            <td>暖通空调</td>
                            <td>注意设备名称最多十二个字</td>
                            <td>注意位置描述最多二十四个字报警描述最多二十三字</td>
                            <td><font class="green">极低</font></td>
                            <td></td>
                        </tr><tr>
                            <td><em>2013-11-09</em></td>
                            <td><em>21：15：21</em></td>
                            <td>注意报警描述最多二十四个字报警描述最多二十三字</td>
                            <td>暖通空调</td>
                            <td>注意设备名称最多十二个字</td>
                            <td>注意位置描述最多二十四个字报警描述最多二十三字</td>
                            <td><font class="orange">高</font></td>
                            <td>烟感报警</td>
                        </tr>
                        <tr>
                            <td><em>2013-11-09</em></td>
                            <td><em>21：15：21</em></td>
                            <td>注意报警描述最多二十四个字报警描述最多二十三字</td>
                            <td>消防系统</td>
                            <td>注意设备名称最多十二个字</td>
                            <td>注意位置描述最多二十四个字报警描述最多二十三字</td>
                            <td><font>中</font></td>
                            <td>手动报警器</td>
                        </tr>
                        <tr>
                            <td><em>2013-11-09</em></td>
                            <td><em>21：15：21</em></td>
                            <td>注意报警描述最多二十四个字报警描述最多二十三字</td>
                            <td>防盗报警</td>
                            <td>注意设备名称最多十二个字</td>
                            <td>注意位置描述最多二十四个字报警描述最多二十三字</td>
                            <td><font class="blue">低</font></td>
                            <td></td>
                        </tr>
                        <tr>
                            <td><em>2013-11-09</em></td>
                            <td><em>21：15：21</em></td>
                            <td>注意报警描述最多二十四个字报警描述最多二十三字</td>
                            <td>暖通空调</td>
                            <td>注意设备名称最多十二个字</td>
                            <td>注意位置描述最多二十四个字报警描述最多二十三字</td>
                            <td><font class="green">极低</font></td>
                            <td></td>
                        </tr>
                    </table>
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
        </div>
    </div>
</div>
<!--弹出框-->
<div class="modal hide fade alert_box w800" id="deal">
    <div class="modal-header alert_box_header"> <a href="#" class="icon_small alert_close" data-dismiss="modal"></a>
        <h4>报警处理</h4>
    </div>
    <div class="modal-body alert_body w776">
        <div class="moduleBox_black moduleBox_h200 mb10">
            <div class="systemBox">
                <h3 class="pt10"><i class="flag flag_green mr15 mt8"></i>冷机出力不足报警</h3>
            </div>
            <ul class="alarm_deal_list">
                <li><span>报警级别</span>低（运行维护相关）</li>
                <li><span>响应时间</span>1小时（还剩45分钟）</li>
                <li><span>当前状态</span>已经启动15分钟</li>
                <li><span>设备</span>冷机2</li>
                <li><span>位置</span>地下二层机房</li>
            </ul>
        </div>
        <div class="moduleBox_black moduleBox_h200 mb10">
            <div class="systemBox">
                <h3 class="pt10"><i class="flag flag_blue mr15 mt8"></i>可能原因</h3>
            </div>
            <ol class="alarm_deal_list">
                <li>1. 冷机全负荷运行未能达到额定制冷量；</li>
                <li>2. 冷机负荷加载异常；</li>
                <li>3. 阀门、水泵、冷却塔运行异常。</li>
            </ol>
        </div>
        <div class="moduleBox_black moduleBox_h200 mb10">
            <div class="systemBox">
                <h3 class="pt10"><i class="flag flag_yellow mr15 mt8"></i>处理流程</h3>
            </div>
            <ol class="alarm_deal_list">
                <li>1. 检查冷机运行参数；</li>
                <li>2. 检查阀门的状态；</li>
                <li>3. 检查冷冻泵和冷却泵的运行状态和参数；</li>
                <li>4. 检查冷却塔的运行状态和参数；</li>
                <li>5. 调整冷机、阀门、水泵、冷却塔的不合理的运行；</li>
                <li>6. 上报主管、分析原因</li>
            </ol>
        </div>
    </div>
    <div class="modal-footer alert_box_footer">
        <input type="button" class="btn_red" value="处理完毕" >
        <input type="button" class="btn_blue" value="确认后取消" >
        <input type="button" class="btn_black" value="取消" data-dismiss="modal">
    </div>
</div>