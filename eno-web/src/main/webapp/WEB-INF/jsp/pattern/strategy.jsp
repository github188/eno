<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglib.jsp" %>
    <link href="${pageContext.request.contextPath}/resources/css/strategy.css" rel="stylesheet" type="text/css" />
    <script src="${pageContext.request.contextPath}/resources/scripts/pattern/strategy.js"></script>

<div class="strategy">
    <div class="menu_switch">
        <div class="Btn-big"><a href="${pageContext.request.contextPath}/pattern/menu/${systemId}?systemId=${systemId}"><i class="icon_btn icon_wrench"></i><p>模式配置</p></a></div>
        <div class="Btn-big cur"><a href="#"><i class="icon_btn icon_tel"></i><p>策略配置</p></a></div>
    </div>
    <div class="strategy_list_top">
        <div class="strategy_list_title">策略模版</div>
        <div class="separate_line"></div>
    </div>
    <div class="strategy_selector">
        <div class="strategy_list">
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

    <div class="swap_mod_1">
        <div class="mode_title">
            <h3>设定表</h3>
            <div class="division_line division_line_style2"></div>
        </div>
        <div class="mode_strategy">
            <div class="desc"></div>
            <div class="mode_btn_group">
                <div onclick="begainEditStrategy()"><p>编辑</p><i class="icon_btn icon_file_right"></i></div>
                <div onclick="showConfirmDeleteStrategyDialog()"><p>删除</p><i class="icon_btn icon_jam"></i></div>
            </div>
        </div>
        <div class="para_and_chart">
            <div class="setting_table">
                <table>
                    <tbody>
                    </tbody>
                </table>
            </div>
            <div class="setting_chart"></div>
        </div>
    </div>

    <div class="swap_mod_2" >
        <div class="mode_title">
            <h3>策略选择</h3>
            <div class="division_line"></div>
        </div>
        <div class="progress_bar">
            <div class="circle cur_circle pro_cur"><i class="icon_ok"></i></div>
            <div class="circle cir_step_2"><i class="icon_file2"></i></div>
            <div class="circle cir_step_3"><i class="icon_file2"></i></div>
        </div>
        <div class="strategy_panel">
            <div class="sub_mode_choice step_1_para" style="background-color:#ffffff">

            </div>
            <div class="temp_setting choice_wind step_2_para">

            </div>
            <div class="mode_btn_group">
                <div class="next_step_3"><p>下一步</p></div>
                <div class="cancel_create"><p>取消</p></div>
            </div>
        </div>
    </div>

    <div class="swap_mod_3" >
        <div class="mode_title">
            <h3>策略选择</h3>
            <div class="division_line"></div>
        </div>
        <div class="progress_bar">
            <div class="circle cur_circle pro_cur"><i class="icon_ok"></i></div>
            <div class="circle cir_step_2 cur_circle pro_cur"><i class="icon_ok"></i></div>
            <div class="circle cir_step_3"><i class="icon_file2"></i></div>
            <div class="long_bar item1 pro_cur"></div>
        </div>
        <div class="strategy_panel" style="height:130px">
            <div class="title_setting">
                <h3></h3>
                <select class="selectpicker_sign step_3_para" style="display: none;" autocomplete="off">
                    <option>选择运算符号</option>
                    <option>=</option>
                    <option>!=</option>
                    <option>&gt;</option>
                    <option>&lt;</option>
                    <option>&gt;=</option>
                    <option>&lt;=</option>
                </select>
                <select class="selectpicker_value value_choice" style="display: none;" onchange="resetValue()">
                    <option>数值</option>
                    <option>True</option>
                    <option>False</option>
                    <option>项目</option>
                </select>
                <input type="number" name="strategyValue" value="" />
            </div>

            <div class="sub_mode_choice sub_mode_2 step_4_para" style="display:none">
            </div>
            <div class="temp_setting choice_wind step_5_para" style="display:none">
            </div>
            <div class="mode_btn_group">
                <div class="prev_step"><p>上一步</p></div>
                <div class="next_step_4"><p>下一步</p></div>
                <div class="cancel_create"><p>取消</p></div>
            </div>
        </div>
    </div>

    <div class="swap_mod_4">
        <div class="mode_title">
            <h3>策略选择</h3>
            <div class="division_line"></div>
        </div>
        <div class="progress_bar">
            <div class="circle cur_circle pro_cur"><i class="icon_ok"></i></div>
            <div class="circle cir_step_2 cur_circle pro_cur"><i class="icon_ok"></i></div>
            <div class="circle cir_step_3 cur_circle pro_cur"><i class="icon_ok"></i></div>
            <div class="long_bar item1 pro_cur"></div>
            <div class="long_bar item2 pro_cur"></div>
        </div>

        <div class="strategy_panel">
            <div class="sub_mode_choice sub_mode_3">
                <div>
                    <div><p>IF</p></div>
                </div>
                <div>
                    <div class="if_name"><p></p></div>
                    <i class="icon_arrow icon_arrow_2"></i>
                </div>
                <div>
                    <div class="operator"><p></p></div>
                </div>
                <div class="strategy_value" style="display:none">
                    <div class=""><p></p></div>
                </div>
                <div class="strategy_item" style="display:none">
                    <div class="comparison_name"><p></p></div>
                    <i class="icon_arrow icon_arrow_2"></i>
                </div>
                <div>
                    <div><p>Then</p></div>
                </div>
            </div>
            <div class="temp_setting3">
            </div>
            <div class="name_setting">
                <div style="margin-left:60px">
                    <p>请设置一个策略名称</p>
                    <input type="text" class="strategy_name" value="" autocomplete="off">
                </div>
                <div style="margin-left:100px;">
                    <p>请设置一个策略描述</p>
                    <textarea class="strategy_desc" autocomplete="off"></textarea>
                </div>
            </div>
            <div class="operator_tip">
                <div id="dialogTip" class="tip"></div>
                <div class="mode_btn_group">
                    <div class="prev_step"><p>上一步</p></div>
                    <div class="complete"><p>完成</p></div>
                    <div class="cancel_create"><p>取消</p></div>
                </div>
            </div>
        </div>
    </div>

</div>

<div id="mask">
</div>

<script type="text/javascript">

    var systemId = "${systemId}";
    var current_strategy_id = "";
    initStrategyLi();
    var deviceItem_stl = '${StrategyItem}';
     var deviceItem =eval('('+deviceItem_stl+')');

    var deviceParam_sp ='${StrategyParam}';
    var deviceParam =eval('('+deviceParam_sp+')');

    var systemCode="${systemCode}";
    var deviceIndex =  [{"name":"启停状态","code":"status_sp","type":"onOff","measurementUnit":""},{"name":"送风温度","code":"t_sa_sp","type":"number","measurementUnit":"℃"},{"name":"防冻温度","code":"t_freeze_sp","type":"number","measurementUnit":"℃"},{"name":"风机频率","code":"frequency_sf_sp","type":"number","measurementUnit":"Hz"}];
    if(systemCode=='FAVU'){
//    新风机组：
        deviceIndex = [{"name":"启停状态","code":"status_sp","type":"onOff","measurementUnit":""},{"name":"送风温度","code":"t_sa_sp","type":"number","measurementUnit":"℃"},{"name":"防冻温度","code":"t_freeze_sp","type":"number","measurementUnit":"℃"},{"name":"风机频率","code":"frequency_sf_sp","type":"number","measurementUnit":"Hz"}];
    }
    if(systemCode=='MAHU'){
//    组合式空调机组：
        deviceIndex = [{"name":"启停状态","code":"status_sp","type":"onOff","measurementUnit":""},{"name":"送风温度","code":"t_sa_sp","type":"number","measurementUnit":"℃"},{"name":"回风温度","code":"t_ra_sp","type":"number","measurementUnit":"℃"},{"name":"新风阀开度","code":"u_damper_oa_sp","type":"number","measurementUnit":"%"},{"name":"风机频率","code":"frequency_sf_sp","type":"number","measurementUnit":"Hz"},{"name":"回风CO2浓度","code":"co2_ra_sp","dbCode":"co2_ra_sp","type":"number","measurementUnit":"ppm"}];
    }
    if(systemCode=='BUAHU'){
//    吊装式空调机组
        deviceIndex = [{"name":"启停状态","code":"status_sp","type":"onOff","measurementUnit":""},{"name":"送风温度","code":"t_sa_sp","type":"number","measurementUnit":"℃"},{"name":"回风温度","code":"t_ra_sp","type":"number","measurementUnit":"℃"},{"name":"新风阀开度","code":"u_damper_oa_sp","type":"number","measurementUnit":"%"},{"name":"风机频率","code":"frequency_sf_sp","type":"number","measurementUnit":"Hz"},{"name":"回风CO2浓度","code":"co2_ra_sp","dbCode":"co2_ra_sp","type":"number","measurementUnit":"ppm"}];
    }
    var strategyJson='${DeviceStrategy}';
    var strategyList = eval('('+strategyJson+')');
    renderStrategyList();

    $(".swap_mod_1").hide();
    $(".swap_mod_2").hide();
    $(".swap_mod_3").hide();
    $(".swap_mod_4").hide();
</script>
