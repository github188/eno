<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<script src="<spring:url value="/resources/scripts/angular.min.js" />"></script>
<!-- customVar.js主要是用于声明获取数据的方式以及数据获取的地址 -->
<%-- <script src="<spring:url value="/resources/scripts/mctrl/customVar.js" />"></script> --%>
<%-- <script src="<spring:url value="/resources/scripts/mctrl/getdata.js" />"></script> --%>
<script src="<spring:url value="/resources/plugins/sockjs/sockjs.js"></spring:url>"></script>
<script src="<spring:url value="/resources/plugins/stomp/dist/stomp.js"></spring:url>"></script>

<div class="span12 main row-fluid">
    <div class="left_menu">
        <table id="MENU_START">
        </table>
    </div>
    <div class="right_content1">
        <div class="mode">

            <ul class="mode_select">
                <li id="current_mode" class="self-inspection Btn-big"><a href="#">&nbsp;开店自检&nbsp;</a></li>
                <li id="eventImportent" class="Btn-big"><a href="#">&nbsp;重大活动&nbsp;</a></li>
                <li id="current_mode1" class="shop_close Btn-big"><a href="#">&nbsp;闭店时间&nbsp;</a></li>
                <li id="current_mode2" class="shop_open Btn-big"><a href="#">&nbsp;开店时间&nbsp;</a></li>
            </ul>
        </div>      
        <div ng-controller="monitorSumController">
            <div id="FAS" class="container-block">
                <div class="mb">
                    <div class="dx-icon">
                        <div>
                            <div class="dx"></div>
                            <span>子系统通讯故障</span>
                        </div>
                    </div>
                </div>
                <div class="title title_ERAD spec">
                    <h1>
                    	<c:forEach items="${menus}" var="men">
                    		<c:if test="${men.elementvalue eq 'FAS'}">
		                        <a href="${men.url}">消防系统</a>
                    		</c:if>
                    	</c:forEach>
                    </h1>
                    <div class="alert_h"><span class="operator">×</span><span class="bjgs">0</span></div>
                    <div class="alert_m"><span class="operator">×</span><span class="bjgs">0</span></div>
                    <div class="alert_l"><span class="operator">×</span><span class="bjgs">0</span></div>
                </div>
                <div class="include">
                    <div class="father">
                        <div class="child icon child_1_icon icon_red"></div>
                        <div class="child child_2_describe">
                            <p>温感报警</p>
                        </div>
                        <div class="child child_3_value color_1">
                            <p>{{FAS_wgbj}}</p>
                        </div>
                    </div>
                    <div class="father">
                        <div class="child icon child_1_icon icon_red"></div>
                        <div class="child child_2_describe">
                            <p>烟感报警</p>
                        </div>
                        <div class="child child_3_value color_1">
                            <p>{{FAS_ygbj}}</p>
                        </div>
                    </div>
                    <div class="father">
                        <div class="child icon child_1_icon icon_red"></div>
                        <div class="child child_2_describe">
                            <p>手动报警器</p>
                        </div>
                        <div class="child child_3_value color_1">
                            <p>{{FAS_sdbj}}</p>
                        </div>
                    </div>
                    <div class="father">
                        <div class="child icon child_1_icon icon_red"></div>
                        <div class="child child_2_describe">
                            <p>其他</p>
                        </div>
                        <div class="child child_3_value color_1">
                            <p>{{FAS_qt}}</p>
                        </div>
                    </div>
                </div>
            </div>

            <div id="ENVMS" class="container-block">
                <div class="mb">
                    <div class="dx-icon">
                        <div>
                            <div class="dx"></div>
                            <span>子系统通讯故障</span>
                        </div>
                    </div>
                </div>
                <div class="title title_ERAD spec energey_static">
                    <h1>
                    	<c:forEach items="${menus}" var="men">
                    		<c:if test="${men.elementvalue eq 'ENVMS'}">
		                        <a href="${men.url}">能耗计量</a>
                    		</c:if>
                    	</c:forEach>
                    </h1>
                </div>
                <div class="include_2">
                    <div>
                        <div class="energy_chart">
                            <div class="chart_bottom">
                                <div style="height: 5px;"></div>
                                <span class="chart_bottom_title">今日总能耗</span>
                                <div class="chart_bottom_data"><span class="data_value">{{ENVMS_jrznh}}</span><span class="num_unit">kWh</span></div>
                                <span class="chart_bottom_title">当前用电功率</span>
                                <div class="chart_bottom_data"><span class="data_value">{{ENVMS_dqydgl}}</span><span class="num_unit">kW</span></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div id="HVAC" class="container-block" >
                <div class="mb">
                    <div class="dx-icon">
                        <div>
                            <div class="dx"></div>
                            <span>子系统通讯故障</span>
                        </div>
                    </div>
                </div>
                <div class="title title_havc">
                    <h1>
                    	<c:forEach items="${menus}" var="men">
                    		<c:if test="${men.elementvalue eq 'HVAC'}">
		                        <a href="${men.url}">暖通空调</a>
                    		</c:if>
                    	</c:forEach>
                    </h1>
                    <div class="alert_h"><span class="operator">×</span><span class="bjgs">0</span></div>
                    <div class="alert_m"><span class="operator">×</span><span class="bjgs">0</span></div>
                    <div class="alert_l"><span class="operator">×</span><span class="bjgs">0</span></div>
                    <div class="custom_select">
                        <p id="HVAC_PATTERN"></p>
                    </div>
                </div>
                <div class="hvac_config row-fluid">
                    <div>
                        <p class="device">冷机</p>
                        <div class="para_box" id="para_box_lj">
                            <p class="current_run_monitor">{{HVAC_lj_currentrun}}</p>
                            <p class="total_moitors">共{{HVAC_lj_total}}台</p>
                        </div>
                    </div>
                    <div>
                        <p class="device">新风机组</p>
                        <div class="para_box" id="para_box_fj">
                            <p class="current_run_monitor">{{HVAC_xfjz_currentrun}}</p>
                            <p class="total_moitors">共{{HVAC_xfjz_total}}台</p>
                        </div>
                    </div>
                    <div>
                        <p class="device">组合空调</p>
                        <div class="para_box" id="para_box_ktx">
                            <p class="current_run_monitor">{{HVAC_zhkt_currentrun}}</p>
                            <p class="total_moitors">共{{HVAC_zhkt_total}}台</p>
                        </div>
                    </div>
                    <div>
                        <p class="device">吊装空调</p>
                        <div class="para_box" id="para_box_ktxt">
                            <p class="current_run_monitor">{{HVAC_dzkt_currentrun}}</p>
                            <p class="total_moitors">共{{HVAC_dzkt_total}}台</p>
                        </div>
                    </div>
                </div>
            </div>

            <div id="ETD" class="container-block">
                <div class="mb">
                    <div class="dx-icon">
                        <div>
                            <div class="dx"></div>
                            <span>子系统通讯故障</span>
                        </div>
                    </div>
                </div>
                <div class="title title_ERAD spec">
                    <h1>
                    	<c:forEach items="${menus}" var="men">
                    		<c:if test="${men.elementvalue eq 'ETD'}">
		                        <a href="${men.url}">变配电</a>
                    		</c:if>
                    	</c:forEach>
                    </h1>
                    <div class="alert_h"><span class="operator">×</span><span class="bjgs">0</span></div>
                    <div class="alert_m"><span class="operator">×</span><span class="bjgs">0</span></div>
                    <div class="alert_l"><span class="operator">×</span><span class="bjgs">0</span></div>
                </div>
                <div class="ERAD_chart">
                    <table>
                        <tr>
                            <td class="btl">
                                <div class="">1T</div>
                            </td>
                            <td>
                                <span class="float_right num_col">{{ETD_1T}}%</span>
                            </td>
                            <td>
                                <div class="">5T</div>
                            </td>
                            <td>
                                <span class="float_right num_col">{{ETD_5T}}%</span>
                            </td>
                        </tr>
                        <tr>
                            <td class="btl">
                                <div class="">2T</div>
                            </td>
                            <td>
                                <span class="float_right num_col">{{ETD_2T}}%</span>
                            </td>
                            <td>
                                <div class="">6T</div>
                            </td>
                            <td>
                                <span class="float_right num_col">{{ETD_6T}}%</span>
                            </td>
                        </tr>
                        <tr>
                            <td class="btl">
                                <div class="">3T</div>
                            </td>
                            <td>
                                <span class="float_right num_col">{{ETD_3T}}%</span>
                            </td>
                            <td>
                                <div class="">7T</div>
                            </td>
                            <td>
                                <span class="float_right num_col">{{ETD_7T}}%</span>
                            </td>
                        </tr>
                        <tr>
                            <td class="btl">
                                <div class="">4T</div>
                            </td>
                            <td>
                                <span class="float_right num_col">{{ETD_4T}}%</span>
                            </td>
                            <td>
                                <div class="">8T</div>
                            </td>
                            <td>
                                <span class="float_right num_col">{{ETD_8T}}%</span>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>

            <div id="WSD" class="container-block">
                <div class="mb">
                    <div class="dx-icon">
                        <div>
                            <div class="dx"></div>
                            <span>子系统通讯故障</span>
                        </div>
                    </div>
                </div>
                <div class="title title_in_out_water spec">
                	<h1>
                    	<c:forEach items="${menus}" var="men">
                    		<c:if test="${men.elementvalue eq 'WSD'}">
		                        <a href="${men.url}">给水排水</a>
                    		</c:if>
                    	</c:forEach>
                    </h1>
                    <div class="alert_h"><span class="operator">×</span><span class="bjgs">0</span></div>
                    <div class="alert_m"><span class="operator">×</span><span class="bjgs">0</span></div>
                    <div class="alert_l"><span class="operator">×</span><span class="bjgs">0</span></div>
                </div>
                <div class="hvac_config row-fluid" style = "margin-top: 22px;">
                    <div>
                        <p class="device">生活水泵</p>
                        <div class="para_box">
                            <p class="current_run_monitor">{{WSD_shsb_currentrun}}</p>
                            <p class="total_moitors">共{{WSD_shsb_total}}台</p>
                        </div>
                    </div>
                    <div>
                        <p class="device">生活水箱</p>
                        <div class="para_box">
                            <p class="current_run_monitor">{{WSD_shsx_currentrun}}</p>
                            <p class="total_moitors">共{{WSD_shsx_total}}台</p>
                        </div>
                    </div>
                    <div>
                        <p class="device">排水泵</p>
                        <div class="para_box">
                            <p class="current_run_monitor">{{WSD_psb_currentrun}}</p>
                            <p class="total_moitors">共{{WSD_psb_total}}台</p>
                        </div>
                    </div>
                    <div>
                        <p class="device">集水坑</p>
                        <div class="para_box">
                            <p class="current_run_monitor">{{WSD_jsk_currentrun}}</p>
                            <p class="total_moitors">共{{WSD_jsk_total}}台</p>
                        </div>
                    </div>
                </div>
            </div>

            <div id="LSPUB" class="container-block">
                <div class="mb">
                    <div class="dx-icon">
                        <div>
                            <div class="dx"></div>
                            <span>子系统通讯故障</span>
                        </div>
                    </div>
                </div>
                <div class="title title_light spec">
                    <h1>
                        <c:forEach items="${menus}" var="men">
                    		<c:if test="${men.elementvalue eq 'LSPUB'}">
		                        <a href="${men.url}">公共照明</a>
                    		</c:if>
                    	</c:forEach>
                    </h1>
                    <div class="custom_select">
                        <p id="LSPUB_PATTERN"></p>
                    </div>
                </div>
                <div class="hvac_config">
                    <div>
                        <p class="device">一层</p>
                        <div class="para_box" id="para_box_green_qy">
                            <p class="current_run_monitor">{{LSPUB_1_currentrun}}</p>
                            <p class="total_moitors">共{{LSPUB_1_total}}路</p>
                        </div>
                    </div>
                    <div>
                        <p class="device">二层</p>
                        <div class="para_box">
                            <p class="current_run_monitor">{{LSPUB_2_currentrun}}</p>
                            <p class="total_moitors">共{{LSPUB_2_total}}路</p>
                        </div>
                    </div>
                    <div>
                        <p class="device">三层</p>
                        <div class="para_box">
                            <p class="current_run_monitor">{{LSPUB_3_currentrun}}</p>
                            <p class="total_moitors">共{{LSPUB_3_total}}路</p>
                        </div>
                    </div>
                </div>
            </div>

            <div id="LSN" class="container-block">
                <div class="mb">
                    <div class="dx-icon">
                        <div>
                            <div class="dx"></div>
                            <span>子系统通讯故障</span>
                        </div>
                    </div>
                </div>
                <div class="title title_light spec">
                    <h1>
                    	<c:forEach items="${menus}" var="men">
                    		<c:if test="${men.elementvalue eq 'LSN'}">
		                        <a href="${men.url}">夜景照明</a>
                    		</c:if>
                    	</c:forEach>
                    </h1>
                    <div class="custom_select">
                        <p id="LSN_PATTERN"></p>
                    </div>
                </div>
                <table>
                    <tr>
                        <td class="btl" rowspan="2">
                            <div class="">照明</div>
                        </td>
                        <td>
                            <span class="float_left">总数</span>
                            <span class="float_right">{{LSN_total}}</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <span class="float_left">开启</span>
                            <span class="float_right num_col">{{LSN_currentrun}}</span>
                        </td>
                    </tr>
                </table>
            </div>

            <div id="MSEM" class="container-block">
                <div class="mb">
                    <div class="dx-icon">
                        <div>
                            <div class="dx"></div>
                            <span>子系统通讯故障</span>
                        </div>
                    </div>
                </div>
                <div class="title title_light spec">
                    <h1>
                    	<c:forEach items="${menus}" var="men">
                    		<c:if test="${men.elementvalue eq 'MSEM'}">
		                        <a href="${men.url}">电梯运行</a>
                    		</c:if>
                    	</c:forEach>
                    </h1>
                </div>
                <table>
                    <tr>
                        <td class="btl" rowspan="2">
                            <div class="">直梯</div>
                        </td>
                        <td>
                            <span class="float_left">总数</span>
                            <span class="float_right">{{MSEM_zt_total}}</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <span class="float_left">开启</span>
                            <span class="float_right num_col">{{MSEM_zt_currentrun}}</span>
                        </td>
                    </tr>
                </table>
                <table class="last_table">
                    <tr>
                        <td class="btl" rowspan="2">
                            <div class="">扶梯</div>
                        </td>
                        <td>
                            <span class="float_left">总数</span>
                            <span class="float_right">{{MSEM_ft_total}}</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <span class="float_left">开启</span>
                            <span class="float_right num_col">{{MSEM_ft_currentrun}}</span>
                        </td>
                    </tr>
                </table>
            </div>

            <div id="MSVDO" class="container-block">
                <div class="mb">
                    <div class="dx-icon">
                        <div>
                            <div class="dx"></div>
                            <span>子系统通讯故障</span>
                        </div>
                    </div>
                </div>
                <div class="title title_light spec">
                    <h1>
                    	<c:forEach items="${menus}" var="men">
                    		<c:if test="${men.elementvalue eq 'MSVDO'}">
		                        <a href="${men.url}">视频监控</a>
                    		</c:if>
                    	</c:forEach>
                    </h1>
                    <div class="alert_h"><span class="operator">×</span><span class="bjgs">0</span></div>
                    <div class="alert_m"><span class="operator">×</span><span class="bjgs">0</span></div>
                    <div class="alert_l"><span class="operator">×</span><span class="bjgs">0</span></div>
                </div>
                <div class="hvac_config">
                    <div>
                        <p class="device">硬盘录像机</p>
                        <div class="para_box" id="para_box_green_qy">
                            <p class="current_run_monitor">{{MSVDO_yplxj_currentrun}}</p>
                            <p class="total_moitors">共{{MSVDO_yplxj_total}}台</p>
                        </div>
                    </div>
                    <div>
                        <p class="device">摄像机</p>
                        <div class="para_box">
                            <p class="current_run_monitor">{{MSVDO_sxj_currentrun}}</p>
                            <p class="total_moitors">共{{MSVDO_sxj_total}}台</p>
                        </div>
                    </div>
                    <div>
                        <p class="device">矩阵</p>
                        <div class="para_box">
                            <p class="current_run_monitor">{{MSVDO_jz_currentrun}}</p>
                            <p class="total_moitors">共{{MSVDO_jz_total}}台</p>
                        </div>
                    </div>
                </div>
            </div>

            <div id="SASSA" class="container-block">
                <div class="mb">
                    <div class="dx-icon">
                        <div>
                            <div class="dx"></div>
                            <span>子系统通讯故障</span>
                        </div>
                    </div>
                </div>
                <div class="title title_light spec">
                    <h1>
                    	<c:forEach items="${menus}" var="men">
                    		<c:if test="${men.elementvalue eq 'SASSA'}">
		                        <a href="${men.url}">防盗报警</a>
                    		</c:if>
                    	</c:forEach>
                    </h1>
                    <div class="alert_h"><span class="operator">×</span><span class="bjgs">0</span></div>
                    <div class="alert_m"><span class="operator">×</span><span class="bjgs">0</span></div>
                    <div class="alert_l"><span class="operator">×</span><span class="bjgs">0</span></div>
                </div>
                <div class="include">
                    <div class="father">
                        <div class="child icon child_1_icon icon_red"></div>
                        <div class="child child_2_describe">
                            <p>状态</p>
                        </div>
                        <div class="child child_3_value color_1">
                            <p class="num_col">{{SASSA_zt}}</p>
                        </div>
                    </div>
                    <div class="father">
                        <div class="child icon child_1_icon icon_red"></div>
                        <div class="child child_2_describe">
                            <p>总数</p>
                        </div>
                        <div class="child child_3_value color_1">
                            <p class="num_col">{{SASSA_total}}</p>
                        </div>
                    </div>
                    <div class="father">
                        <div class="child icon child_1_icon icon_red"></div>
                        <div class="child child_2_describe">
                            <p>报警点数</p>
                        </div>
                        <div class="child child_3_value color_1">
                            <p class="num_col">{{SASSA_currentrun}}</p>
                        </div>
                    </div>
                </div>
            </div>

            <div id="SASAC" class="container-block">
                <div class="mb">
                    <div class="dx-icon">
                        <div>
                            <div class="dx"></div>
                            <span>子系统通讯故障</span>
                        </div>
                    </div>
                </div>
                <div class="title title_light spec">
                    <h1>
                    	<c:forEach items="${menus}" var="men">
                    		<c:if test="${men.elementvalue eq 'SASAC'}">
		                        <a href="${men.url}">门禁管理</a>
                    		</c:if>
                    	</c:forEach>
                    </h1>
                </div>
                <table>
                    <tr>
                        <td class="btl" rowspan="3">
                            <div class="">状态</div>
                        </td>
                        <td>
                            <span class="float_left">总数</span>
                            <span class="float_right">{{SASAC_total}}</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <span class="float_left">开启</span>
                            <span class="float_right num_col">{{SASAC_currentrun}}</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <span class="float_left">关闭</span>
                            <span class="float_right num_col">{{SASAC_total - SASAC_currentrun}}</span>
                        </td>
                    </tr>
                </table>
            </div>

            <div id="EP" class="container-block">
                <div class="mb">
                    <div class="dx-icon">
                        <div>
                            <div class="dx"></div>
                            <span>子系统通讯故障</span>
                        </div>
                    </div>
                </div>
                <div class="title title_light spec">
                    <h1>
                    	<c:forEach items="${menus}" var="men">
                    		<c:if test="${men.elementvalue eq 'EP'}">
		                        <a href="${men.url}">电子巡更</a>
                    		</c:if>
                    	</c:forEach>
                    </h1>
                </div>
                <table>
                    <tr>
                        <td class="btl" rowspan="2">
                            <div class="">采集</div>
                        </td>
                        <td>
                            <span class="float_left">总数</span>
                            <span class="float_right">{{EP_total}}</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <span class="float_left">开启</span>
                            <span class="float_right num_col">{{EP_currentrun}}</span>
                        </td>
                    </tr>
                </table>
                <table class="last_table">
                    <tr>
                        <td class="btl">数据来源</td>
                        <td><span class="float_right">{{EP_datasource}}</span></td>
                    </tr>
                </table>
            </div>

            <div id="PFE" class="container-block" >
                <div class="mb">
                    <div class="dx-icon">
                        <div>
                            <div class="dx"></div>
                            <span>子系统通讯故障</span>
                        </div>
                    </div>
                </div>
                <div class="title title_light spec">
                    <h1>
                    	<c:forEach items="${menus}" var="men">
                    		<c:if test="${men.elementvalue eq 'PFE'}">
		                        <a href="${men.url}">客流统计</a>
                    		</c:if>
                    	</c:forEach>
                    </h1>
                </div>
                <div>
                    <div class="pee_chart" id="passengerTotal"></div>
                    <div class="pee_info">
                        <div class="chart_bottom">
                            <div style="height: 15px;"></div>
                            <span class="chart_bottom_title">今日总客流</span>
                            <div class="chart_bottom_data"><span class="data_value">{{PFE_jrzkl}}</span></div>
                        </div>
                    </div>
                </div>
            </div>

            <div id="INFP" class="container-block">
                <div class="mb">
                    <div class="dx-icon">
                        <div>
                            <div class="dx"></div>
                            <span>子系统通讯故障</span>
                        </div>
                    </div>
                </div>
                <div class="title title_light spec">
                    <h1>
                    	<c:forEach items="${menus}" var="men">
                    		<c:if test="${men.elementvalue eq 'INFP'}">
		                        <a href="${men.url}">信息发布</a>
                    		</c:if>
                    	</c:forEach>
                    </h1>
                </div>
                <table>
                    <tr>
                        <td class="btl" rowspan="2">
                            <div class="">运行</div>
                        </td>
                        <td>
                            <span class="float_left">总数</span>
                            <span class="float_right">{{INFP_yx_total}}</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <span class="float_left">开启</span>
                            <span class="float_right num_col">{{INFP_yx_currentrun}}</span>
                        </td>
                    </tr>
                </table>
                <table class="last_table">
                    <tr>
                        <td class="btl" rowspan="2">
                            <div class="">大屏</div>
                        </td>
                        <td>
                            <span class="float_left">温度</span>
                            <span class="float_right num_col">{{INFP_dp_wd}}</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <span class="float_left">亮度</span>
                            <span class="float_right num_col">{{INFP_dp_ld}}</span>
                        </td>
                    </tr>
                </table>
            </div>

            <div id="BGMB" class="container-block">
                <div class="mb">
                    <div class="dx-icon">
                        <div>
                            <div class="dx"></div>
                            <span>子系统通讯故障</span>
                        </div>
                    </div>
                </div>
                <div class="title title_light spec">
                    <h1>
                    	<c:forEach items="${menus}" var="men">
                    		<c:if test="${men.elementvalue eq 'BGMB'}">
		                        <a href="${men.url}">背景音乐</a>
                    		</c:if>
                    	</c:forEach>
                    </h1>
                </div>
                <div class="include">
                    <div class="father">
                        <div class="child icon child_1_icon icon_red"></div>
                        <div class="child child_2_describe">
                            <p>播放区域</p>
                        </div>
                        <div class="child child_3_value color_1">
                            <p class="num_col">{{BGMB_bfqy}}</p>
                        </div>
                    </div>
                    <div class="father">
                        <div class="child icon child_1_icon icon_red"></div>
                        <div class="child child_2_describe">
                            <p>播放状态</p>
                        </div>
                        <div class="child child_3_value color_1">
                            <p class="num_col">{{BGMB_bfzt}}</p>
                        </div>
                    </div>
                    <div class="father">
                        <div class="child icon child_1_icon icon_red"></div>
                        <div class="child child_2_describe">
                            <p>播放音源</p>
                        </div>
                        <div class="child child_3_value color_1">
                            <p class="num_col">{{BGMB_bfyy}}</p>
                        </div>
                    </div>
                    <div class="father">
                        <div class="child icon child_1_icon icon_red"></div>
                        <div class="child child_2_describe">
                            <p>播放音量</p>
                        </div>
                        <div class="child child_3_value color_1">
                            <p class="num_col">{{BGMB_bfyl}}</p>
                        </div>
                    </div>
                </div>
            </div>

            <div id="PARKM" class="container-block">
                <div class="mb">
                    <div class="dx-icon">
                        <div>
                            <div class="dx"></div>
                            <span>子系统通讯故障</span>
                        </div>
                    </div>
                </div>
                <div class="title title_light spec">
                    <h1>
                    	<c:forEach items="${menus}" var="men">
                    		<c:if test="${men.elementvalue eq 'PARKM'}">
		                        <a href="${men.url}">停车管理</a>
                    		</c:if>
                    	</c:forEach>
                    </h1>
                </div>
                <table>
                    <tr>
                        <td class="btl" rowspan="2">
                            <div class="">运行</div>
                        </td>
                        <td>
                            <span class="float_left">总车位</span>
                            <span class="float_right">{{PARKM_total}}</span>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <span class="float_left">可用车位</span>
                            <span class="float_right num_col">{{PARKM_kycw}}</span>
                        </td>
                    </tr>
                </table>
            </div>

        </div>
    </div>
</div>

<div id="mask">
	<div class="dialog-popover shop" ng-controller="businessTime">

		<div class="dialog-header">
			<p class="dialog-title">设置开/闭店时间</p>
			<div class="dialog-close close_pop">×</div>
		</div>

		<div class="shop-content shop-open">

			<div class="shop-panel open-shop">

				开店日期：
				<div id="datetimepicker1" class="input-append date">
					<input data-format="yyyy-MM-dd" class="shop-state open-date"
						type="text" ng-model="cal.startDate" name="startDate" /> <span
						class="add-on"> <i data-time-icon="icon-time"
						data-date-icon="icon-calendar"> </i>
					</span>
				</div>
				开店时间：
				<div id="datetimepicker2" class="input-append date">
					<input data-format="hh:mm" class="shop-state open-time" type="text"
						ng-model="cal.startTime" name="startTime" /> <span class="add-on">
						<i data-time-icon="icon-time" data-date-icon="icon-calendar">
					</i>
					</span>
				</div>

				<table class="shop_table">
					<thead>
						<tr>
							<th>开店日期</th>
							<th>开店时间</th>
							<th>备注</th>
						</tr>
					</thead>
					<tbody ng-class-odd="'tbody_show_tr_diff'"
						ng-class-even="'tbody_show_tr_diff2'"
						ng-repeat="calendar in calendars">
						<tr>
							<td>{{calendar.calendarYear}}年{{calendar.calendarMonth}}月{{calendar.calendarDay}}日</td>
							<td>{{calendar.openTime}}</td>
							<td>系统默认</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="self-inspection-popover-footer">
				<div class="dialogTip"></div>
				<div class="confirm_btn dialog-btn" ng-click="saveOpen(cal)">确定</div>
				<div class="close_btn dialog-btn">取消</div>
			</div>
		</div>

		<div class="shop-content shop-close">
			<div class="shop-panel close-shop">

				闭店日期：
				<div id="datetimepicker3" class="input-append date">
					<input data-format="yyyy-MM-dd" class="shop-state open-date"
						type="text" name="endDate" /> <span class="add-on"> <i
						data-time-icon="icon-time" data-date-icon="icon-calendar"> </i>
					</span>
				</div>
				闭店时间：
				<div id="datetimepicker4" class="input-append date">
					<input data-format="hh:mm" class="shop-state open-time" type="text"
						name="endTime" /> <span class="add-on"> <i
						data-time-icon="icon-time" data-date-icon="icon-calendar"> </i>
					</span>
				</div>
				<table class="shop_table">
					<thead>
						<tr>
							<th>闭店日期</th>
							<th>闭店时间</th>
							<th>备注</th>
						</tr>
					</thead>
					<tbody ng-class-odd="'tbody_show_tr_diff'"
						ng-class-even="'tbody_show_tr_diff2'"
						ng-repeat="calendar in calendars">
						<tr>
							<td>{{calendar.calendarYear}}年{{calendar.calendarMonth}}月{{calendar.calendarDay}}日</td>
							<td>{{calendar.closeTime}}</td>
							<td>系统默认</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="self-inspection-popover-footer">
				<div class="dialogTip"></div>
				<div class="confirm_btn dialog-btn" ng-click="saveClose()">确定</div>
				<div class="close_btn dialog-btn">取消</div>
			</div>
		</div>

	</div>
	<div class="dialog-popover self-inspection-popover">
		<div class="dialog-header">
			<p class="dialog-title">子系统服务概况</p>
			<div class="dialog-close close_pop">×</div>
		</div>
		<div class="self-inspection-popover-content">
			<div class="left_nav">
				<ul class="nav_list_ul">
					<li>
						<h1>服务运行状况</h1>
						<p>无服务问题</p>
					</li>
					<li>
						<h1>消息中心</h1>
						<p>今日有5条消息</p>
					</li>
					<li>
						<h1>计划内维护</h1>
						<p>未安排计划内维护</p>
					</li>
				</ul>
			</div>
			<div class="right_content_block">
				<div class="current_block">
					<h1>当前运行状态</h1>
					<div class="mode_list_block">
						<ul class="mode_list">
							<li>
								<div class="sys_name">暖通空调</div>
								<div class="run_mode" id="HAVC_CHECK_PATTERN"></div>
								<div class="has_info">
									<img
										src="${pageContext.request.contextPath}/resources/images/information.png">
								</div>
							</li>
							<li>
								<div class="sys_name">给排水</div>
								<div class="run_mode">无</div>
								<div class="has_info"></div>
							</li>
							<li>
								<div class="sys_name">变配电</div>
								<div class="run_mode">无</div>
								<div class="has_info"></div>
							</li>
							<li>
								<div class="sys_name">火灾报警</div>
								<div class="run_mode">无</div>
								<div class="has_info"></div>
							</li>
							<li>
								<div class="sys_name">消防漏电</div>
								<div class="run_mode">无</div>
								<div class="has_info"></div>
							</li>
							<li>
								<div class="sys_name">消防水泡</div>
								<div class="run_mode">无</div>
								<div class="has_info"></div>
							</li>
							<li>
								<div class="sys_name">视频监控</div>
								<div class="run_mode">无</div>
								<div class="has_info"></div>
							</li>
							<li>
								<div class="sys_name">门禁系统</div>
								<div class="run_mode">无</div>
								<div class="has_info"></div>
							</li>
							<li>
								<div class="sys_name">公共照明</div>
								<div class="run_mode" id="LSPUB_CHECK_PATTERN"></div>
								<div class="has_info">
									<img
										src="${pageContext.request.contextPath}/resources/images/information.png">
								</div>
							</li>
							<li>
								<div class="sys_name">夜景照明</div>
								<div class="run_mode" id="LSN_CHECK_PATTERN"></div>
								<div class="has_info">
									<img
										src="${pageContext.request.contextPath}/resources/images/information.png">
								</div>
							</li>
							<li>
								<div class="sys_name">信息发布</div>
								<div class="run_mode">无</div>
								<div class="has_info"></div>
							</li>
							<li>
								<div class="sys_name">停车管理</div>
								<div class="run_mode">无</div>
								<div class="has_info"></div>
							</li>
							<li>
								<div class="sys_name">背景音乐</div>
								<div class="run_mode">无</div>
								<div class="has_info"></div>
							</li>
							<li>
								<div class="sys_name">电梯监控</div>
								<div class="run_mode">无</div>
								<div class="has_info"></div>
							</li>
							<li>
								<div class="sys_name">能耗计量</div>
								<div class="run_mode">无</div>
								<div class="has_info"></div>
							</li>
						</ul>
					</div>
				</div>
				<div ng-controller="msgCenter">
					<table class="now_info">
						<thead>
							<tr>
								<th class="title_now_info"><h2>当前消息</h2></th>
								<th class="title_operate"><h2>需要执行的操作</h2></th>
							</tr>
						</thead>
						<tbody ng-class-odd="'tbody_show_tr_diff'"
							ng-class-even="'tbody_show_tr_diff2'"
							ng-repeat="itemEvent in itemEvents">
							<tr>
								<td style="font-size: 16px;">{{itemEvent.name}}</td>
								<td>无</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div>
					<div class="now_operate_state">
						<h1>当前运行状态</h1>
						<div>
							<p>公共照明子系统将于今天23：00至明天2：00进行维护</p>
							<h4>公共照明子系统</h4>
						</div>
						<div>
							<p>变配电子系统将于今天2：30至明天3：00进行维护</p>
							<h4>变配电子系统</h4>
						</div>
						<div>
							<p>夜景照明子系统将于今天6：00至明天10：00进行维护</p>
							<h4>夜景照明子系统</h4>
						</div>
						<div>
							<p>视频监控子系统将于今天15：00至明天16：00进行维护</p>
							<h4>视频监控子系统</h4>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="self-inspection-popover-footer">
			<div class="lunxun_btn dialog-btn">视频轮巡</div>
			<div class="ok_btn dialog-btn">确定</div>
		</div>
	</div>

</div>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/highcharts.js"></script>
<script type="text/javascript">
	var elementvalue = "${elementvalue}"; // 模块id（如：PFE）

	var expPropertiesMap;//存储所有变量名称和id的对应关系

	function getMenu()		// 获取菜单 [ ChengKang 2014-08-28 ]
	{
		$.post(CONTEXT_PATH + '/okcsys/okcmenu/init', {}, 
		function(data)
		{
			if(data != null && typeof(data) != "undefined" && data.length>0)
			{
				//console.log(data);
				var MenuJson = $.parseJSON(data);
				var HtmlStr = "";
				HtmlStr += "<tbody>";
				for(var i=0; i<MenuJson.length; i++)
				{
					HtmlStr += "<tr>";
					HtmlStr += "<td class=\"" + MenuJson[i].code + " bt\"";
					if(MenuJson[i].children.length>1)
					{
						HtmlStr += " rowspan=\"" + MenuJson[i].children.length + "\"";
					}
					HtmlStr += ">";
					if(MenuJson[i].children.length <= 2)
					{
						HtmlStr += MenuJson[i].name.substr(0, 2) + "<br>";
						HtmlStr += MenuJson[i].name.substr(2);
					}
					else
					{
						for(var j=0; j<MenuJson[i].children.length-1; j++)
						{
							HtmlStr += MenuJson[i].name.substr(j, 1) + "<br>";
						}
						HtmlStr += MenuJson[i].name.substr(MenuJson[i].children.length-1, 1);
					}
					HtmlStr += "</td>";
					
					var ChildrenJson = MenuJson[i].children;
					var childrenNum = ChildrenJson.length;
					for(var j=0; j<childrenNum; j++)
					{
						if(j>0)
						{
							HtmlStr += "<tr>";
						}
						HtmlStr += "<td><i class=\"icon_"+ ChildrenJson[j].code.toLowerCase() +"\"></i>";
						if( ChildrenJson[j].url != "undefined" && ChildrenJson[j].url != "")
						{
							HtmlStr += "<a href=\""+ ChildrenJson[j].url +"\">";
							HtmlStr += ChildrenJson[j].name;
							HtmlStr += "</a></td>";
						}
						else
						{
							HtmlStr += ChildrenJson[j].name;
							HtmlStr += "</td>";
						}
						HtmlStr += "</tr>";
					}
				}
				HtmlStr += "</tbody>";
				//console.log(HtmlStr);
				$(HtmlStr).insertAfter("#MENU_START");
			}
		})
	}
	getMenu();

	/*掉线上线函数*/
	function showOffLine(systemId, state) {
		if (state == 0) {
			$("#" + systemId + " .mb").show();
		} else {
			$("#" + systemId + " .mb").hide();
		}
	}
	function msgCenter($scope, $http) {
		$http.get(CONTEXT_PATH + '/calendar/getTodayEvent').success(
				function(data) {
					$scope.itemEvents = data;
				});
	}
	function businessTime($scope, $http) {
		$http.get(CONTEXT_PATH + '/pattern/getListBusinessTime').success(
				function(data) {
					$scope.calendars = data;
				});

		$scope.saveOpen = function(user) {
			var date = angular.element($("[name='startDate']")).val();
			var time = angular.element($("[name='startTime']")).val();
			if (isTime(time) && isDate(date)) {
				var vTime = "startDate=" + date + "&startTime=" + time;
				$http({
					method : "POST",
					url : CONTEXT_PATH + '/pattern/setBusinessTime',
					data : vTime,
					headers : {
						'Content-Type' : 'application/x-www-form-urlencoded'
					}
				}).success(
						function(data, status, headers, config) {
							showMessageInDialog("数据保存成功", "tip");
							setTimeout(function() {
								$("#mask").hide();
								$(".shop-close, .shop-open").hide();
								$(".shop-state").val("");
							}, 2000);
							$http.get(
									CONTEXT_PATH
											+ '/pattern/getListBusinessTime')
									.success(function(data) {
										$scope.calendars = data;
									});
						}).error(function(data, status, headers, config) {
					showMessageInDialog("数据保存失败", "tip");
				})
			} else {
				showMessageInDialog("请填写日期和时间", "error");
			}
		};

		$scope.saveClose = function(user) {
			var date = angular.element($("[name='endDate']")).val();
			var time = angular.element($("[name='endTime']")).val();
			if (isTime(time) && isDate(date)) {
				//                var vTime={endDate:date,endTime:time};
				var vTime = "endDate=" + date + "&endTime=" + time;
				$http.post({
					method : "POST",
					url : CONTEXT_PATH + '/pattern/setBusinessTime',
					data : vTime,
					headers : {
						'Content-Type' : 'application/x-www-form-urlencoded'
					}
				}).success(
						function(data, status, headers, config) {
							showMessageInDialog("数据保存成功", "tip");
							setTimeout(function() {
								$("#mask").hide();
								$(".shop-close, .shop-open").hide();
								$(".shop-state").val("");
							}, 2000);
							$http.get(
									CONTEXT_PATH
											+ '/pattern/getListBusinessTime')
									.success(function(data) {
										$scope.calendars = data;
									});
						}).error(function(data, status, headers, config) {
					showMessageInDialog("数据保存失败", "tip");
				})
			} else {
				showMessageInDialog("请填写日期和时间", "error");
			}
		}
	}

	function monitorSumController($scope, $http) {
		//声明ViewModel
		$scope.FAS_wgbj = 1;//温感报警
		$scope.FAS_ygbj = 2;//烟感报警
		$scope.FAS_sdbj = 3;//手动报警器
		$scope.FAS_qt = 4;//其他
		$scope.ENVMS_jrznh = 100;//今日总能耗
		$scope.ENVMS_dqydgl = 200;//当前用电功率

		$scope.HVAC_lj_currentrun=0;
		$scope.HVAC_lj_total=4;
		$scope.HVAC_xfjz_currentrun=20;
		$scope.HVAC_xfjz_total=24;
		$scope.HVAC_zhkt_currentrun=22;
		$scope.HVAC_zhkt_total=27;
		$scope.HVAC_dzkt_currentrun=15;
		$scope.HVAC_dzkt_total=19;
		$scope.ETD_1T=50;
		$scope.ETD_2T=62;
		$scope.ETD_3T=30;
		$scope.ETD_4T=47;
		$scope.ETD_5T=55;
		$scope.ETD_6T=71;
		$scope.ETD_7T=20;
		$scope.ETD_8T=17;
		$scope.WSD_shsb_currentrun=3;
		$scope.WSD_shsb_total=4;
		$scope.WSD_shsx_currentrun=2;
		$scope.WSD_shsx_total=2;
		$scope.WSD_psb_currentrun=105;
		$scope.WSD_psb_total=105;
		$scope.WSD_jsk_currentrun=79;
		$scope.WSD_jsk_total=79;
		$scope.LSPUB_1_currentrun=153;
		$scope.LSPUB_1_total=216;
		$scope.LSPUB_2_currentrun=163;
		$scope.LSPUB_2_total=240;
		$scope.LSPUB_3_currentrun=105;
		$scope.LSPUB_3_total=225;
		$scope.LSN_currentrun=21;
		$scope.LSN_total=160;
		$scope.MSEM_zt_currentrun=17;
		$scope.MSEM_zt_total=17;
		$scope.MSEM_ft_currentrun=23;
		$scope.MSEM_ft_total=23;
		$scope.MSVDO_yplxj_currentrun=33;
		$scope.MSVDO_yplxj_total=33;
		$scope.MSVDO_sxj_currentrun=448;
		$scope.MSVDO_sxj_total=448;
		$scope.MSVDO_jz_currentrun=1;
		$scope.MSVDO_jz_total=1;
		$scope.SASSA_zt='布防';
		$scope.SASSA_total=170;
		$scope.SASSA_currentrun=5;
		$scope.SASAC_total=84;
		$scope.SASAC_currentrun=21;
		$scope.EP_total=400;
		$scope.EP_currentrun=102;
		$scope.EP_datasource='廊坊数据中心';
		$scope.PFE_jrzkl=12546;
		$scope.INFP_yx_total=3;
		$scope.INFP_yx_currentrun=3;
		$scope.INFP_yx_total=3;
		$scope.INFP_dp_wd=45;
		$scope.INFP_dp_ld=213;
		$scope.BGMB_bfqy='二层东区';
		$scope.BGMB_bfzt='播放';
		$scope.BGMB_bfyy='CD2';
		$scope.BGMB_bfyl='高';
		$scope.PARKM_total=764;
		$scope.PARKM_kycw=123;
		
		$http({method: 'GET', url: CONTEXT_PATH + '/msum/getMonitorSumData'}).success(function(data, status, headers){
            expPropertiesMap = data.expPropertiesMap;
			for (var i=0; i<data.initData.length; i++){
                var initName = data.initData[i].name;
                var initVal = data.initData[i].value;
                if(initName.indexOf("Exp_") >= 0){
                    initName = initName.substring(4);
                }
                if(initName == "SASSA_zt") {
                    initVal = initVal == 1 ? "布防" : "撤防";
                }
				var funStr = "$scope."+ initName +"='"+ initVal +"'";
				eval(funStr);
			}
		});
		
		var wsUrl = "<spring:url value="/ws"></spring:url>";
		var socket = new SockJS(wsUrl);
		var stompClient = Stomp.over(socket);
		stompClient.connect('guest', 'guest', function(frame) {
			console.log('Connected ' + frame);
			if (frame.headers['user-name'] == '') {
				self.username("guest");
			}
			console.log("websocket connection successed!");

			// 订阅首页变量实时推送
			stompClient.subscribe("/topic/value.tag.*", function(message) {
				var body = JSON.parse(message.body);
                var expName = expPropertiesMap[body.id];
                var expVal = body.v;
                if(expName != undefined) {
                    expName = expName.substring(4);
                    if(expName == "SASSA_zt") {
                        expVal = expVal == 1 ? "布防" : "撤防";
                    }
                    var funStr = "$scope."+expName+"='"+ expVal +"'";
                    eval(funStr);
                    $scope.$apply();
                }
			});
			
		}, function(error) {
			console.log("STOMP protocol error " + error);
		});		
	}
	
	$(function() {
		$(".shop_open").on("click", function() {
			$("#mask,.shop").show();
			$(".shop .dialog-title").text("设置开店时间");
			$(".shop-open").show();
		});

		$(".shop_close").on("click", function() {
			$("#mask,.shop").show();
			$(".shop .dialog-title").text("设置闭店时间");
			$(".shop-close").show();
		});

		$(".dialog-close, .close_btn").on("click", function() {
			$("#mask,.shop").hide();
			$(".shop-close, .shop-open").hide();
			$(".shop-state").val("");
		});

		$('#datetimepicker1, #datetimepicker3').datetimepicker({
			language : 'zh-CN'
		});

		$('#datetimepicker2, #datetimepicker4').datetimepicker({
			pickDate : false
		});
	});
	
</script>
<script type = "text/javascript" charset="utf-8">
//window.onload=
	
	
	
</script>