<%--
  Created by IntelliJ IDEA.
  User: zc
  Date: 13-7-17
  Time: 下午1:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglib.jsp" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/notimemode.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/pattern/notimemode.js"></script>

<script type="text/javascript">
    var systemCode="${systemCode}";
    if (systemCode=='HVAC')
    {
        document.write(unescape("%3Cscript%20src='${pageContext.request.contextPath}/resources/scripts/pattern/hvacnotimemode.js'%20type='text/javascript'%3E%3C/script%3E"));
    }
    else if (systemCode=='LSPUB')
    {
        document.write(unescape("%3Cscript%20src='${pageContext.request.contextPath}/resources/scripts/pattern/lspubnotimemode.js'%20type='text/javascript'%3E%3C/script%3E"));
    }
    else
    	  document.write(unescape("%3Cscript%20src='${pageContext.request.contextPath}/resources/scripts/pattern/othernotimemode.js'%20type='text/javascript'%3E%3C/script%3E"));

</script>
<div class="time_mode">
    <%--<div class="span12 mode_change">--%>
        <%--<div class="Btn-big cur"><a href="#"><i class="icon_btn icon_wrench"></i><p>模式配置</p></a></div>--%>
    <%--</div>--%>
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
<%--</div>--%>
<div id="mask">
</div>

<script type="text/javascript">


    var systemId = "${systemCode}";
    var current_time_mode_id = "";
    var current_time_mode_item_id = "";
    initModeLi();
    //    冷源
    var systemCode="${systemCode}";
    var  property =[{"name":"偏移量","code":"offset","dbCode":"","type":"offset","measurementUnit":"分钟"},{"name":"设备名称","code":"deviceName","dbCode":"","type":"deviceTree","measurementUnit":""},{"name":"启停状态","code":"onOff","dbCode":"status_sp","type":"onOff","measurementUnit":""}];
    if(systemCode=='LSPUB' || systemCode=='LSN'){
      property =[{"name":"偏移量","code":"offset","dbCode":"","type":"offset","measurementUnit":"分钟"},{"name":"设备名称","code":"deviceName","dbCode":"","type":"deviceTree_deviceGroup","measurementUnit":""},{"name":"启停状态","code":"onOff","dbCode":"status_sp","type":"onOff","measurementUnit":""}];
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
