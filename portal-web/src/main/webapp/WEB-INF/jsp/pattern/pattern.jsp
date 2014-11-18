<%--
  Created by IntelliJ IDEA.
  User: zc
  Date: 13-7-17
  Time: 下午1:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglib.jsp" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/timemode.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/pattern/timemode.js"></script>
<script type="text/javascript">
    var systemCode="${systemCode}";
    if ((systemCode=='CoolingSource')||(systemCode=='HeatSource'))
    {
        document.write(unescape("%3Cscript%20src='${pageContext.request.contextPath}/resources/scripts/pattern/coolheattimemode.js'%20type='text/javascript'%3E%3C/script%3E"));
    }
    else if ((systemCode=='FAVU')||(systemCode=='MAHU')||(systemCode=='BUAHU')||(systemCode=='AVU'))
    {
        document.write(unescape("%3Cscript%20src='${pageContext.request.contextPath}/resources/scripts/pattern/hvactimemode.js'%20type='text/javascript'%3E%3C/script%3E"));
    }
    else if (systemCode=='LSPUB')
    {
        document.write(unescape("%3Cscript%20src='${pageContext.request.contextPath}/resources/scripts/pattern/lspubtimemode.js'%20type='text/javascript'%3E%3C/script%3E"));
    }
    else if (systemCode=='LSN')
    {
        document.write(unescape("%3Cscript%20src='${pageContext.request.contextPath}/resources/scripts/pattern/lsntimemode.js'%20type='text/javascript'%3E%3C/script%3E"));
    }
</script>

<div class="time_mode">
    <div class="span12 mode_change">
        <div class="Btn-big cur nomargin"><a href="#"><i class="icon_btn icon_wrench"></i><p>模式配置</p></a></div>
        <c:if test="${ systemCode=='FAVU' || systemCode=='MAHU' ||systemCode=='BUAHU'}">
        <div class="Btn-big"><a href="${pageContext.request.contextPath}/strategy/showStrategy?systemId=${systemCode}"><i class="icon_btn icon_tel"></i><p>策略配置</p></a></div>
        </c:if>
    </div>
    <div class="mode_list_top">
        <div class="mode_list_title">模式模版</div>
        <div class="templete">
            <ul class="templete_ul">
                <li class="display_templete" onclick="javascript:showChoiceTemplete(event)">
                    <span class="template_style">标准模板&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                    <span class="down_icon">▼</span>
                </li>
                <li class="hide_templete">
                    <ul>
                        <li onclick="javascript:hideChoiceTemplete(this,'standar')">标准模板&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>
                        <li onclick="javascript:hideChoiceTemplete(this,'customer')">自定义模板&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</li>
                    </ul>
                </li>
            </ul>
        </div>
        <div class="separate_line"></div>
    </div>
    <div class="mode_selector">
        <div class="mode_list">
            <ul>
            </ul>
        </div>
        <div class="scrollbar">
            <div class="up" onclick="javascript:increaseMarginTop()">
                <div class="arrow_up"></div>
            </div>
            <div class="down" onclick="javascript:decreaseMarginTop()">
                <div class="arrow_down"></div>
            </div>
            <div class="down_down">
                <div class="separate_line"></div>
                <div class="arrow_down"></div>
            </div>
        </div>
    </div>
    <div class="mode_detail">
        <div class="actionbar">
            <div class="mode_btn_group">
                <div onclick="javascript:createOneItemForOneMode()"><p>添加</p><i class="icon_btn icon_addnew"></i></div>
                <div onclick="javascript:editOneItemForOneMode()"><p>编辑</p><i class="icon_btn icon_file_right"></i></div>
                <div onclick="javascript:showConfirmDeleteModeItemDialog()"><p>删除</p><i class="icon_btn icon_jam"></i></div>
                <div onclick="javascript:uploadPatternItem()"><p>上传</p><i class="icon_btn icon_upload"></i></div>
                <div onclick="javascript:downloadPatternItem()"><p>下载</p><i class="icon_btn icon_download"></i></div>
            </div>
        </div>
        <div id="mode_table" class="mode_table">
            <table>
                <thead>
                <tr>

                </tr>
                </thead>
                <tbody>
                </tbody>
            </table>
        </div>
        <div class="right_slide_bar">
            <div class="idSliderUp"></div>
            <div id="idSlider">
                <div id="idBar"></div>
            </div>
            <div class="idSliderDown"></div>
        </div>
    </div>
</div>

<div id="mask">
</div>

<script type="text/javascript">

    var systemId = "${systemCode}";
    var current_time_mode_id = "";
    var current_time_mode_item_id = "";
    initModeLi();
    var scene = ${System_Factor};
//    冷源
    var systemCode="${systemCode}";
    var  property = [{"name":"时间","code":"time","dbCode":"","type":"time","measurementUnit":""},{"name":"偏移量","code":"offset","dbCode":"","type":"offset","measurementUnit":"分钟"},{"name":"设备名称","code":"deviceName","dbCode":"","type":"deviceTree","measurementUnit":""},{"name":"动作描述","code":"actionDesc","dbCode":"","type":"list","measurementUnit":""},{"name":"启停状态","code":"onOff","dbCode":"status_sp","type":"onOff","measurementUnit":""},{"name":"纳入群控的冷机","code":"groupControlCount","dbCode":"number_on","type":"checkboxGroup","measurementUnit":""},{"name":"最多开启的冷机","code":"maxControlCount","dbCode":"max_number_on","type":"multiList","measurementUnit":""},{"name":"供水温度","code":"waterTemperature","dbCode":"t_chw_s_sp","type":"number","measurementUnit":"℃"},{"name":"供回水压差","code":"waterPressure","dbCode":"dp_chw_s_sp","type":"number","measurementUnit":"mH2O"},{"name":"供回水温差","code":"waterDiff","dbCode":"dt_chw_s_s","type":"number","measurementUnit":"℃"}];
    if(systemCode=='CoolingSource'){
        property = [{"name":"时间","code":"time","dbCode":"","type":"time","measurementUnit":""},{"name":"偏移量","code":"offset","dbCode":"","type":"offset","measurementUnit":"分钟"},{"name":"设备名称","code":"deviceName","dbCode":"","type":"deviceTree","measurementUnit":""},{"name":"动作描述","code":"actionDesc","dbCode":"","type":"list","measurementUnit":""},{"name":"启停状态","code":"onOff","dbCode":"status_sp","type":"onOff","measurementUnit":""},{"name":"纳入群控的冷机","code":"groupControlCount","dbCode":"number_on","type":"checkboxGroup","measurementUnit":""},{"name":"最多开启的冷机","code":"maxControlCount","dbCode":"max_number_on","type":"multiList","measurementUnit":""},{"name":"供水温度","code":"waterTemperature","dbCode":"t_chw_s_sp","type":"number","measurementUnit":"℃"},{"name":"供回水压差","code":"waterPressure","dbCode":"dp_chw_s_sp","type":"number","measurementUnit":"mH2O"},{"name":"供回水温差","code":"waterDiff","dbCode":"dt_chw_s_s","type":"number","measurementUnit":"℃"}];
    }
    if(systemCode=='HeatSource'){
//    热源：
        property = [{"name":"时间","code":"time","dbCode":"","type":"time","measurementUnit":""},{"name":"偏移量","code":"offset","dbCode":"","type":"offset","measurementUnit":"分钟"},{"name":"设备名称","code":"deviceName","dbCode":"","type":"deviceTree","measurementUnit":""},{"name":"动作描述","code":"actionDesc","dbCode":"","type":"list","measurementUnit":""},{"name":"启停状态","code":"onOff","dbCode":"status_sp","type":"onOff","measurementUnit":""},{"name":"供水温度","code":"waterTemperature","dbCode":"t_hw_s_sp","type":"number","measurementUnit":"℃"},{"name":"供水压力","code":"waterPressure2","dbCode":"p_hw_s_sp","type":"number","measurementUnit":"mH2O"},{"name":"水泵频率","code":"hz","dbCode":"frequency_sp","type":"number","measurementUnit":"Hz"}];
    }
    if(systemCode=='FAVU'){
//    新风机组：
        property = [{"name":"时间","code":"time","dbCode":"","type":"time","measurementUnit":""},{"name":"偏移量","code":"offset","dbCode":"","type":"offset","measurementUnit":"分钟"},{"name":"设备名称","code":"deviceName","dbCode":"","type":"deviceTree","measurementUnit":""},{"name":"动作描述","code":"actionDesc","dbCode":"","type":"list","measurementUnit":""},{"name":"策略","code":"strategy","dbCode":"strategy","type":"strategy","measurementUnit":""},{"name":"启停状态","code":"onOff","dbCode":"status_sp","type":"onOff","measurementUnit":""},{"name":"送风温度","code":"windTemperature","dbCode":"t_sa_sp","type":"number","measurementUnit":"℃"},{"name":"防冻温度","code":"freezeTemperature","dbCode":"t_freeze_sp","type":"number","measurementUnit":"℃"},{"name":"风机频率","code":"hz","dbCode":"frequency_sf_sp","type":"number","measurementUnit":"Hz"}];
    }
    if(systemCode=='MAHU'){
//    组合式空调机组：
        property = [{"name":"时间","code":"time","dbCode":"","type":"time","measurementUnit":""},{"name":"偏移量","code":"offset","dbCode":"","type":"offset","measurementUnit":"分钟"},{"name":"设备名称","code":"deviceName","dbCode":"","type":"deviceTree","measurementUnit":""},{"name":"动作描述","code":"actionDesc","dbCode":"","type":"list","measurementUnit":""},{"name":"策略","code":"strategy","dbCode":"strategy","type":"strategy","measurementUnit":""},{"name":"启停状态","code":"onOff","dbCode":"status_sp","type":"onOff","measurementUnit":""},{"name":"送风温度","code":"windTemperature","dbCode":"t_sa_sp","type":"number","measurementUnit":"℃"},{"name":"防冻温度","code":"freezeTemperature","dbCode":"t_freeze_sp","type":"number","measurementUnit":"℃"},{"name":"回风温度","code":"lookWindTemperature","dbCode":"t_ra_sp","type":"number","measurementUnit":"℃"},{"name":"新风阀开度","code":"windDamper","dbCode":"u_damper_oa_sp","type":"number","measurementUnit":"%"},{"name":"风机频率","code":"hz","dbCode":"frequency_sf_sp","type":"number","measurementUnit":"Hz"},{"name":"回风CO2浓度","code":"co2_ra_sp","dbCode":"co2_ra_sp","type":"number","measurementUnit":"ppm"}];
    }
    if(systemCode=='BUAHU'){
//    吊装式空调机组
        property = [{"name":"时间","code":"time","dbCode":"","type":"time","measurementUnit":""},{"name":"偏移量","code":"offset","dbCode":"","type":"offset","measurementUnit":"分钟"},{"name":"设备名称","code":"deviceName","dbCode":"","type":"deviceTree","measurementUnit":""},{"name":"动作描述","code":"actionDesc","dbCode":"","type":"list","measurementUnit":""},{"name":"策略","code":"strategy","dbCode":"strategy","type":"strategy","measurementUnit":""},{"name":"启停状态","code":"onOff","dbCode":"status_sp","type":"onOff","measurementUnit":""},{"name":"送风温度","code":"windTemperature","dbCode":"t_sa_sp","type":"number","measurementUnit":"℃"},{"name":"防冻温度","code":"freezeTemperature","dbCode":"t_freeze_sp","type":"number","measurementUnit":"℃"},{"name":"回风温度","code":"lookWindTemperature","dbCode":"t_ra_sp","type":"number","measurementUnit":"℃"},{"name":"新风阀开度","code":"windDamper","dbCode":"u_damper_oa_sp","type":"number","measurementUnit":"%"},{"name":"风机频率","code":"hz","dbCode":"frequency_sf_sp","type":"number","measurementUnit":"Hz"},{"name":"回风CO2浓度","code":"co2_ra_sp","dbCode":"co2_ra_sp","type":"number","measurementUnit":"ppm"}];
    }
    if(systemCode=='AVU'){
//    通风机组
        property = [{"name":"时间","code":"time","dbCode":"","type":"time","measurementUnit":""},{"name":"偏移量","code":"offset","dbCode":"","type":"offset","measurementUnit":"分钟"},{"name":"设备名称","code":"deviceName","dbCode":"","type":"deviceTree","measurementUnit":""},{"name":"动作描述","code":"actionDesc","dbCode":"","type":"list","measurementUnit":""},{"name":"启停状态","code":"onOff","dbCode":"status_sp","type":"onOff","measurementUnit":""}];
    }
    if(systemCode=='LSPUB'){
//    公共照明
        property = [{"name":"时间","code":"time","dbCode":"","type":"time","measurementUnit":""},{"name":"偏移量","code":"offset","dbCode":"","type":"offset","measurementUnit":"分钟"},{"name":"设备名称","code":"deviceName","dbCode":"","type":"deviceTree_deviceGroup","measurementUnit":""},{"name":"动作描述","code":"actionDesc","dbCode":"","type":"list","measurementUnit":""},{"name":"启停状态","code":"onOff","dbCode":"status_sp","type":"onOff","measurementUnit":""}];
    }
    if(systemCode=='LSN'){
//    夜景照明
        property = [{"name":"时间","code":"time","dbCode":"","type":"time","measurementUnit":""},{"name":"偏移量","code":"offset","dbCode":"","type":"offset","measurementUnit":"分钟"},{"name":"设备名称","code":"deviceName","dbCode":"","type":"deviceTree_deviceGroup","measurementUnit":""},{"name":"动作描述","code":"actionDesc","dbCode":"","type":"list","measurementUnit":""},{"name":"启停状态","code":"onOff","dbCode":"status_sp","type":"onOff","measurementUnit":""}];
    }

    initTableTh();
    var modeList_standar =${Pattern_System_Time};
    var modeList_customer =${Pattern_Custom_Time};
    var modeType = "standar";
    var modeList = modeList_standar;
    renderModeList();

    $(".mode_detail").hide();
    document.onclick = function(){
        hideTemplete();
    }
</script>

