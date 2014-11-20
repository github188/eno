<%--
  Created by IntelliJ IDEA.
  User: zc
  Date: 13-7-17
  Time: 下午1:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/notimeglobemode.css"/>
 <script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/pattern/notimeglobemode.js"></script>
 
<%@ include file="../common/taglib.jsp" %>

        <div class="mode_cataloge">
            <ul class="cataloge_class_one">
                <%--<li>--%>
                    <%--<div class="cataloge_name">时序模式</div>--%>
                    <%--<ul class="cataloge_class_two">--%>
                        <%--<li>全局时序</li>--%>
                    <%--</ul>--%>
                <%--</li>--%>
                <li>
                    <div class="cataloge_name">触发模式</div>
                    <ul class="cataloge_class_two">
                        <li class="current"><a href="${pageContext.request.contextPath}/pattern/showGlobalPatternNoTime">全局触发模式</a></li>
                    </ul>
                </li>
            </ul>
        </div>
        <div class="globe_mode">
            <div class="mode_list_top">
                <div class="mode_list_title">模式名称</div>
                <div class="separate_line"></div>
            </div>
            <div class="mode_selector">
                <div class="mode_list">
                    <ul>
                    </ul>
                </div>
                <div class="scrollbar">
                    <div class="up">
                        <div class="arrow_up"></div>
                    </div>
                    <div class="down">
                        <div class="arrow_down"></div>
                    </div>
                    <div class="down_down">
                        <div class="separate_line"></div>
                        <div class="arrow_down"></div>
                    </div>
                </div>
            </div>
            <div class="mode_relation_system">
                <div class="title">关联的子系统</div>
                <div class="relation_system_list">
                    <ul>
                    </ul>
                </div>
                <div class="system_slider">
                    <div class="system_slider_left">
                        <div class="arrow_left"></div>
                    </div>
                    <div class="system_slider_right">
                        <div class="arrow_right"></div>
                    </div>
                </div>
            </div>
            <div class="mode_detail">
                <div class="actionbar">
                    <div class="title">当前全局模式关联的子系统模式为:</div>
                    <div id="relation_mode_name" onclick="javascript:showRelationModeDetail()"></div>
                    <select onchange="showOtherModeDetail()">
                    </select>
                    <div class="button_group">
                        <div  onclick="" style="text-align:center;background-color:#999999"><span style="margin-left:0px;">关联此模式</span></div>
                    </div>
                </div>
                <div id="mode_table" class="mode_table">
                    <div class="mode_name_title"></div>
                    <table>
                        <thead>
                        <tr>
                            <th class="offset_time">偏移时间</th>
                            <th class="equpment_group">设备组</th>
                            <th class="cmd">控制指令</th>
                            <th class="comment">备注</th>
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
        var current_globe_notime_mode_name = "";
        var current_globe_notime_mode_id = "";
        var current_system_id = "";
        var current_system_code = "";
        var current_system_name = "";
        var current_notime_mode_id = "";
        var current_relation_notime_mode_id = "";
        var current_relation_notime_mode_name = "";
        initModeLi();
        var databaseGlobal='${Global_NoTime}';
        var databaseSubSystem='${SubSystem_NoTime}';
        var modeList = eval('('+databaseGlobal+')');
        var systemModeList = eval('('+databaseSubSystem+')');
        renderGlobeModeList();

        $(".mode_detail").hide();

    </script>

