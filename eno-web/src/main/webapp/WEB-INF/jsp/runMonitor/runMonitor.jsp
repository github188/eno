<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!--container start-->
<div class="container">
    <div class="span12 top_content">
        <!--暖通空调-->
        <div class="span3 w450 mt20 ml20">
            <div class="pubCon h660 bg01">
                <h2 class="monitor_tit"><a href="systemList.html" title="暖通空调">暖通空调</a></h2>
                <div class="pubCon_blackOne h325">
                    <div class="systemBox">
                        <h3 class="pt10 pb5"><i class="flag flag_green mr15 mt8"></i>冷冻水/冷却水温度</h3>
                        <div class="chwp_left_unit">(℃)</div>
						<div class="chwp_right_unit">(％)</div>
                        <div class="chartBbox">
                            <div class="chartPic_monoitorOne">
								<div id="waterTemperature"></div>
							</div>
                        </div>
                        <h3 class="pt10 pb5"><i class="flag flag_green mr15 mt8"></i>冷冻水/冷却水流量</h3>
                        <div class="flow_left_unit">(m³/h)</div>
                        <div class="chartBbox">
                            <div class="chartPic_monoitorOne">
								<div id="waterFlow"></div>
							</div>
                        </div>
                    </div>
                </div>
                <div class="bg_grain">
                    <ul class="ulSystemOne">
                        <li><span class="right"><em class="ft24 mr5">3</em><span class="unit col9fa0a2">℃</span></span>室外干球温度</li>
                        <li><span class="right"><em class="ft24 mr5">3</em><span class="unit col9fa0a2">%</span></span>室外相对湿度</li>
                    </ul>
                </div>
                <div class="ulSystemTwo pubCon_grey">
                    <ul>
                        <li>
                            <p class="common ft18">冷机</p>
                            <p class="curent">3</p>
                            <p class="common">共 <em>4</em> 台</p>
                        </li>
                        <li>
                            <p class="common ft18">新风机组</p>
                            <p class="curent">189</p>
                            <p class="common">共 <em>240</em></p>
                        </li>
                        <li>
                            <p class="common ft18">空调箱</p>
                            <p class="curent">147</p>
                            <p class="common">共 <em>240</em></p>
                        </li>
                        <li>
                            <p class="common ft18">通风系统</p>
                            <p class="curent">62</p>
                            <p class="common">共 <em>240</em></p>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
        <!--照明系统-->
        <div class="span3 w450 mt20 ml25">
            <div class="pubCon h660 bg02">
                <h2 class="monitor_tit"><a href="systemList_publicLight.html" title="照明系统">照明系统</a></h2>
                <div class="pubCon_blackOne h560">
                    <div class="systemBox">
                        <h3 class="pt10 pb5"><i class="flag flag_blue mr15 mt8"></i>公共照明</h3>
                        <div class="filter_widget right mt15 mr10">
                            <select class="selectpicker">
                                <option>一层</option>
                                <option>二层</option>
                                <option>三层</option>
                                <option>四层</option>
                            </select>
                        </div>
                    </div>
                    <div class="pubCon_grey" style="height: 380px;">
                        <div class="ulSystemTwo ulSystemTwo_B ps-container ps-active-y" id="js_scroll_container">
                            <ul id="js_scroll_content">
                                <li>
                                    <p class="common ft18">区域A</p>
                                    <p class="curent">3</p>
                                    <p class="common">共 <em>4</em> 台</p>
                                </li>
                                <li>
                                    <p class="common ft18">区域B</p>
                                    <p class="curent">189</p>
                                    <p class="common">共 <em>240</em></p>
                                </li>
                                <li>
                                    <p class="common ft18">区域C</p>
                                    <p class="curent">147</p>
                                    <p class="common">共 <em>240</em></p>
                                </li>
                                <li>
                                    <p class="common ft18">区域D</p>
                                    <p class="curent">62</p>
                                    <p class="common">共 <em>240</em></p>
                                </li>
                                <li>
                                    <p class="common ft18">区域E</p>
                                    <p class="curent">3</p>
                                    <p class="common">共 <em>4</em> 台</p>
                                </li>
                                <li>
                                    <p class="common ft18">区域F</p>
                                    <p class="curent">189</p>
                                    <p class="common">共 <em>240</em></p>
                                </li>
                                <li>
                                    <p class="common ft18">区域G</p>
                                    <p class="curent">147</p>
                                    <p class="common">共 <em>240</em></p>
                                </li>
                                <li>
                                    <p class="common ft18">区域H</p>
                                    <p class="curent">62</p>
                                    <p class="common">共 <em>240</em></p>
                                </li>
                                <li>
                                    <p class="common ft18">区域I</p>
                                    <p class="curent">3</p>
                                    <p class="common">共 <em>4</em> 台</p>
                                </li>
                                <li>
                                    <p class="common ft18">区域J</p>
                                    <p class="curent">189</p>
                                    <p class="common">共 <em>240</em></p>
                                </li>
                                <li>
                                    <p class="common ft18">区域K</p>
                                    <p class="curent">147</p>
                                    <p class="common">共 <em>240</em></p>
                                </li>
                                <li>
                                    <p class="common ft18">区域L</p>
                                    <p class="curent">62</p>
                                    <p class="common">共 <em>240</em></p>
                                </li>
                                <li>
                                    <p class="common ft18">区域I</p>
                                    <p class="curent">3</p>
                                    <p class="common">共 <em>4</em> 台</p>
                                </li>
                                <li>
                                    <p class="common ft18">区域J</p>
                                    <p class="curent">189</p>
                                    <p class="common">共 <em>240</em></p>
                                </li>
                                <li>
                                    <p class="common ft18">区域K</p>
                                    <p class="curent">147</p>
                                    <p class="common">共 <em>240</em></p>
                                </li>
                                <li>
                                    <p class="common ft18">区域L</p>
                                    <p class="curent">62</p>
                                    <p class="common">共 <em>240</em></p>
                                </li>
                                <li>
                                    <p class="common ft18">区域M</p>
                                    <p class="curent">3</p>
                                    <p class="common">共 <em>4</em> 台</p>
                                </li>
                                <li>
                                    <p class="common ft18">区域N</p>
                                    <p class="curent">189</p>
                                    <p class="common">共 <em>240</em></p>
                                </li>
                                <li>
                                    <p class="common ft18">区域O</p>
                                    <p class="curent">147</p>
                                    <p class="common">共 <em>240</em></p>
                                </li>
                                <li>
                                    <p class="common ft18">区域P</p>
                                    <p class="curent">62</p>
                                    <p class="common">共 <em>240</em></p>
                                </li>
                            </ul>
                            <div class="ps-scrollbar-y-rail">
                                <div class="ps-scrollbar-y"></div>
                            </div>
                        </div>
                    </div>
                    <div class="pubCon_blackOne h120">
                        <div class="systemBox">
                            <h3 class="pt5"><i class="flag flag_blue mr15 mt8"></i>夜景照明</h3>
                        </div>
                        <div class="ml20 mr20">
                            <ul class="ulSystemOne">
                                <li><span class="right"><em class="ft24 mr5">541</em><span class="unit col9fa0a2">台</span></span>总数</li>
                                <li><span class="right"><em class="ft24 mr5">123</em><span class="unit col9fa0a2">台</span></span>开启</li>
                            </ul>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!--变配电系统-->
        <div class="span3 w450 mt20 ml25">
            <div class="pubCon h660 bg03">
                <h2 class="monitor_tit"><a href="systemList_electricity.html" title="变配电系统">变配电系统</a></h2>
                <div class="pubCon_blackOne h250">
                    <div class="systemBox">
                        <h3 class="pt10 pb5"><i class="flag flag_red mr15 mt8"></i>高压</h3>
                        <div class="highPressure_left_unit">(v)</div>
                        <div class="chartBbox">
                            <div class="chartPic_monoitorOne">
								<div id="highPressure"></div>
							</div>
                        </div>
                        <div class="ml20 mr20 mt10">
                            <ul class="ulSystemOne">
                                <li><span class="right"><em class="ft24 mr5">3</em><span class="unit col9fa0a2">A</span></span>电流</li>
                                <li><span class="right"><em class="ft24 mr5">3</em><span class="unit col9fa0a2">V</span></span>电压</li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div class="bg_grain_big">
                    <div class="systemBox">
                        <h3 class="pt10 pb5"><i class="flag flag_red mr15 mt8"></i>变压器负载</h3>
                    </div>
                    <div class="ulSystemTwo" style="border-top:#444 solid 1px; margin-top:4px;">
                        <ul>
                            <li>
                                <p class="common ft18 mt5">1号变压器</p> 
                                <p class="curent mt20"><em class="ft45 colfff mr5">3</em><span class="unit">%</span></p>
                            </li>
                            <li>
                                <p class="common ft18 mt5">2号变压器</p>
                                <p class="curent mt20"><em class="ft45 colfff mr5">89</em><span class="unit">%</span></p>
                            </li>
                            <li>
                                <p class="common ft18 mt5">3号变压器</p>
                                <p class="curent mt20"><em class="ft45 colfff mr5">47</em><span class="unit">%</span></p>
                            </li>
                            <li>
                                <p class="common ft18 mt5">4号变压器</p>
                                <p class="curent mt20"><em class="ft45 colfff mr5">62</em><span class="unit">%</span></p>
                            </li>
                        </ul>
                    </div>
                </div>
                <div class="pubCon_blackOne h125">
                    <div class="systemBox">
                        <h3 class="pt5">
                        	<i class="flag flag_red mr15 mt8"></i>
                            <div class="filter_widget">
                                <select class="selectpicker" id="etdSelect">
                                    <option>1号变压器</option>
                                    <option>2变压器</option>
                                    <option>3号变压器</option>
                                    <option>4号变压器</option>
                                </select>
                            </div>
                        </h3>
                        <div class="etdTemperature_left_unit">(v)</div>
                        <div class="chartPic_monoitorOne">
							<div id="etdTemperature"></div>
						</div>
                    </div>
                </div>
            </div>
        </div>
        <!--给排水系统-->
        <div class="span3 w450 mt20 ml25">
            <div class="pubCon h660 bg04">
                <h2 class="monitor_tit"><a href="systemList_Drainage.html" title="给排水系统">给排水系统</a></h2>
                <div class="pubCon_blackOne h435">
                    <div class="systemBox">
                        <h3 class=" mt10 mb5"><i class="flag flag_purple mr15 mt8"></i>1号水泵频率</h3>
                        <div class="wsdFrequency_left_unit">(Hz)</div>
                        <div class="chartBbox">
                            <div class="chartPic_monoitorTwo">
								<div id="wsdFrequency"></div>
							</div>
                        </div>
                        <h3 class="mt10 mb5"><i class="flag flag_purple mr15 mt8"></i>水泵房给水</h3>
                        <div class="giveWater_left_unit">(℃)</div>
                        <div class="chartBbox">
                            <div class="chartPic_monoitorTwo">
								<div id="giveWater"></div>
							</div>
                        </div>
                    </div>
                    
                </div>
                <div class="ulSystemTwo pubCon_grey">
                    <ul>
                        <li>
                            <p class="common ft18">给水阀</p>
                            <p class="curent">3</p>
                            <p class="common">共 <em>4</em> 台</p>
                        </li>
                        <li>
                            <p class="common ft18">污水泵</p>
                            <p class="curent">189</p>
                            <p class="common">共 <em>240</em></p>
                        </li>
                        <li>
                            <p class="common ft18">浮球阀</p>
                            <p class="curent">147</p>
                            <p class="common">共 <em>240</em></p>
                        </li>
                        <li>
                            <p class="icon_big videoPic"></p>
                            <p class="common ft18">视频监控</p>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
    <div class="span12 bottom_content">
        <!-- 安防监控 -->
    	<div class="span2 w260 ml20">
            <div class="pubCon_blue h220">
                <h3><i class="flag flag_blue_light mt25"></i><a href="systemList_security.html" title="安防监控">安防监控</a></h3>
                <div class="pubCon_blackOne h145">
                    <div class="pt20 pr20 pl20">
                        <ul class="ulSystemOne">
                            <li><span class="right"><em class="ft24">500</em><span class="col9fa0a2">/良好</span></span>硬盘录像机</li>
                            <li><span class="right"><em class="ft24">120</em><span class="col9fa0a2">/良好</span></span>矩阵</li>
                            <li><span class="right"><em class="ft24">74</em><span class="col9fa0a2">/良好</span></span>摄像机</li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <!-- 入侵报警 -->
        <div class="span2 w260 ml10">
            <div class="pubCon_blue h220">
                <h3><i class="flag flag_blue_light mt25"></i><a href="systemList_intrusionAlarm.html" title="入侵报警">入侵报警</a></h3>
                <div class="pubCon_blackOne h145">
                    <div class="pt20 pr20 pl20">
                        <ul class="ulSystemOne">
                            <li><span class="right"><em class="ft24">500</em></span>设备编号</li>
                            <li><span class="right ft18 col6cf">无</span>安装地址</li>
                            <li><span class="right"><em class="ft24">IF3522</em></span>设备型号</li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <!-- 门禁管理 -->
        <div class="span2 w260 ml10">
            <div class="pubCon_blue h220">
                <h3><i class="flag flag_blue_light mt25"></i><a href="systemList_doorGuard.html" title="门禁管理">门禁管理</a></h3>
                <div class="pubCon_blackOne h145">
                    <div class="pt20 pr20 pl20">
                        <ul class="ulSystemOne">
                            <li><span class="right"><em class="ft24">500</em></span>总数</li>
                            <li><span class="right"><em class="ft24">120</em></span>开启</li>
                            <li><span class="right"><em class="ft24">74</em></span>位置</li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <!-- 电子巡查 -->
        <div class="span2 w260 ml10">
            <div class="pubCon_blue h220">
                <h3><i class="flag flag_blue_light mt25"></i><a href="systemList_nightPatrol.html" title="电子巡查">电子巡查</a></h3>
                <div class="pubCon_blackOne h145">
                    <div class="pt20 pr20 pl20">
                        <ul class="ulSystemOne">
                            <li><span class="right"><em class="ft24">500</em></span>总数</li>
                            <li><span class="right"><em class="ft24">120</em></span>开启</li>
                            <li><span class="right"><em class="ft24">2013-1-2</em></span>巡更时间</li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <!-- 消防监控 -->
        <div class="span2 w260 ml10">
            <div class="pubCon_blue h220">
                <h3><i class="flag flag_red mt25"></i><a href="systemList_fireDefence.html" title="消防监控">消防监控</a></h3>
                <div class="pubCon_blackOne h145">
                    <div class="pt5 pr20 pl20">
                        <ul class="ulSystemOne">
                            <li><span class="right"><em class="ft24">500</em></span>报警信息</li>
                            <li><span class="right"><em class="ft24">120</em></span>火源区域</li>
                            <li><span class="right"><em class="ft24">74</em></span>总人数</li>
                            <li><span class="right"><em class="ft24">74</em></span>可用出口</li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <!-- 电梯运行 -->
        <div class="span2 w260 ml10">
            <div class="pubCon_blue h220">
                <h3><i class="flag flag_blue_light mt25"></i><a href="systemList_lift.html" title="电梯运行">电梯运行</a></h3>
                <div class="pubCon_blackOne h145">
                    <div class="pt5 pr20 pl20">
                        <ul class="ulSystemOne">
                            <li><span class="right"><em class="ft24">500</em></span>运行总数</li>
                            <li><span class="right"><em class="ft24">120</em></span>运行开启</li>
                            <li><span class="right"><em class="ft24">74</em></span>机房总数</li>
                            <li><span class="right"><em class="ft24">74</em></span>机房开启</li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <!-- 停车管理 -->
        <div class="span2 w260 ml10">
            <div class="pubCon_blue h220">
                <h3><i class="flag flag_blue_light mt25"></i><a href="systemList_park.html" title="停车管理">停车管理</a></h3>
                <div class="pubCon_blackOne h145">
                    <div class="pt5 pr20 pl20">
                        <ul class="ulSystemOne">
                            <li><span class="right"><em class="ft24">500</em></span>运行总数</li>
                            <li><span class="right"><em class="ft24">120</em></span>运行开启</li>
                            <li><span class="right"><em class="ft24">74</em></span>机房总数</li>
                            <li><span class="right"><em class="ft24">74</em></span>机房开启</li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
$("#js_scroll_container").perfectScrollbar(); // 使照明系统-公共照明滚动条生效
</script>