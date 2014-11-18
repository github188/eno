<%--
  Created by IntelliJ IDEA.
  User: EnergyUser
  Date: 14-1-3
  Time: 下午4:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp" %>
<link rel="stylesheet" type="text/css"
      href="${pageContext.request.contextPath}/resources/css/bootstrap-switch.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/bootstrap-switch.js"></script>
<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/mctrl/light_func.js"></script>--%>
<div id="LSPUB_F1_ASSET_LIST">
<div>
<div class="pmt_block">
    <div class="operate_board">
       	<div class="yjzm_control"></div>
        <div class="loop_info">
            <div class="left_board">
                <div class="loop_detail">
                    <div class="describe_block"><span class="floor_num">F1</span><span>-</span><span class="block_num">2</span><span>区</span><span class="kind_light"></span><p class="light_des">（步行街吊顶上的筒灯、金卤灯等灯具）20%</p></div>
                    <div class="operate_block">
                        <div class="if_operate"></div>
                        <div class="switch_btn allopen">全开</div>
                        <div class="switch_btn allclose">全关</div>
                    </div>
                </div>
                <div class="each_loop_state">


                </div>
            </div>
            <div class="right_board">
                <div class="loop_pic">

                </div>
            </div>
        </div>
        <!-- <div class="loop_info">
            <div class="left_board">
                <div class="each_para_display">
                    <div class="loop_name">
                        <label>回路名称：</label>
                        <span>0_0_8</span>
                    </div>
                    <div class="run_time">
                        <label>累计运行时间：</label>
                        <span>30</span>
                        <label>H</label>
                    </div>
                    <div class="run_state">
                        <label>运行状态：</label>
                        <span>关闭</span>
                    </div>
                </div>
            </div>
            <div class="right_board">
            </div>
        </div> -->
    </div>
</div>
<div class="block_zone">
<div class="each_block marginleft2">
    <div>
        <div class="block_title">
            <h1>二区</h1>
            <div><p>手动</p></div>
        </div>
        <div class="block_detail">
            <h1>基础照明1</h1>
            <div>
                <div>
                    <span class="light_kind">基础照明A</span>
                    <span class="light_state">开启</span>
                    <input type="checkbox" checked class="switch-large" disabled>
                    <div class="cancontrol"></div>
                </div>
                <div>
                    <span class="light_kind">基础照明A</span>
                    <span class="light_state">开启</span>
                    <input type="checkbox" checked class="switch-large" disabled>
                    <div class="cancontrol"></div>
                </div>
                <div>
                    <span class="light_kind">基础照明A</span>
                    <span class="light_state">开启</span>
                    <input type="checkbox" checked class="switch-large" disabled>
                    <div class="cancontrol"></div>
                </div>
                <div>
                    <span class="light_kind">基础照明A</span>
                    <span class="light_state">开启</span>
                    <input type="checkbox" checked class="switch-large" disabled>
                    <div class="cancontrol"></div>
                </div>
            </div>
            <h1>基础照明2</h1>
            <div>
                <div>
                    <span class="light_kind">基础照明A</span>
                    <span class="light_state">开启</span>
                    <input type="checkbox" checked class="switch-large" disabled>
                    <div class="cancontrol"></div>
                </div>
                <div>
                    <span class="light_kind">基础照明A</span>
                    <span class="light_state">开启</span>
                    <input type="checkbox" checked class="switch-large" disabled>
                    <div class="cancontrol"></div>
                </div>
            </div>
            <h1>装饰灯</h1>
            <div>
                <div>
                    <span class="light_kind">装饰灯G1</span>
                    <span class="light_state">开启</span>
                    <input type="checkbox" checked class="switch-large" disabled>
                    <div class="cancontrol"></div>
                </div>
                <div>
                    <span class="light_kind">装饰灯G2</span>
                    <span class="light_state">开启</span>
                    <input type="checkbox" checked class="switch-large" disabled>
                    <div class="cancontrol"></div>
                </div>
            </div>
            <h1>导向标识</h1>
            <div>
                <div>
                    <span class="light_kind">导向标识H</span>
                    <span class="light_state">开启</span>
                    <input type="checkbox" checked class="switch-large" disabled>
                    <div class="cancontrol"></div>
                </div>
            </div>
        </div>
    </div>
    <div>

    </div>
    <div>

    </div>

</div>
<div class="each_block">
    <div>
        <div class="block_title">
            <h1>区</h1>
            <div><p>手动</p></div>
        </div>
        <div class="block_detail">
            <h1>基础照明1</h1>
            <div>
                <div>
                    <span class="light_kind">基础照明A</span>
                    <span class="light_state">开启</span>
                    <input type="checkbox" checked class="switch-large" disabled>
                    <div class="cancontrol"></div>
                </div>
                <div>
                    <span class="light_kind">基础照明A</span>
                    <span class="light_state">开启</span>
                    <input type="checkbox" checked class="switch-large" disabled>
                    <div class="cancontrol"></div>
                </div>
                <div>
                    <span class="light_kind">基础照明A</span>
                    <span class="light_state">开启</span>
                    <input type="checkbox" checked class="switch-large" disabled>
                    <div class="cancontrol"></div>
                </div>
                <div>
                    <span class="light_kind">基础照明A</span>
                    <span class="light_state">开启</span>
                    <input type="checkbox" checked class="switch-large" disabled>
                    <div class="cancontrol"></div>
                </div>
            </div>
            <h1>基础照明2</h1>
            <div>
                <div>
                    <span class="light_kind">基础照明A</span>
                    <span class="light_state">开启</span>
                    <input type="checkbox" checked class="switch-large" disabled>
                    <div class="cancontrol"></div>
                </div>
                <div>
                    <span class="light_kind">基础照明A</span>
                    <span class="light_state">开启</span>
                    <input type="checkbox" checked class="switch-large" disabled>
                    <div class="cancontrol"></div>
                </div>
            </div>
            <h1>装饰灯</h1>
            <div>
                <div>
                    <span class="light_kind">装饰灯G1</span>
                    <span class="light_state">开启</span>
                    <input type="checkbox" checked class="switch-large" disabled>
                    <div class="cancontrol"></div>
                </div>
                <div>
                    <span class="light_kind">装饰灯G2</span>
                    <span class="light_state">开启</span>
                    <input type="checkbox" checked class="switch-large" disabled>
                    <div class="cancontrol"></div>
                </div>
            </div>
            <h1>导向标识</h1>
            <div>
                <div>
                    <span class="light_kind">导向标识H</span>
                    <span class="light_state">开启</span>
                    <input type="checkbox" checked class="switch-large" disabled>
                    <div class="cancontrol"></div>
                </div>
            </div>
        </div>
    </div>
    <div>

    </div>
    <div>

    </div>
</div>
<div class="each_block">
    <div>
        <div class="block_title">
            <h1>一区</h1>
            <div><p>手动</p></div>
        </div>
        <div class="block_detail">
            <h1>基础照明1</h1>
            <div>
                <div>
                    <span class="light_kind">基础照明A</span>
                    <span class="light_state">开启</span>
                    <input type="checkbox" checked class="switch-large" disabled>
                    <div class="cancontrol"></div>
                </div>
                <div>
                    <span class="light_kind">基础照明A</span>
                    <span class="light_state">开启</span>
                    <input type="checkbox" checked class="switch-large" disabled>
                    <div class="cancontrol"></div>
                </div>
                <div>
                    <span class="light_kind">基础照明A</span>
                    <span class="light_state">开启</span>
                    <input type="checkbox" checked class="switch-large" disabled>
                    <div class="cancontrol"></div>
                </div>
                <div>
                    <span class="light_kind">基础照明A</span>
                    <span class="light_state">开启</span>
                    <input type="checkbox" checked class="switch-large" disabled>
                    <div class="cancontrol"></div>
                </div>
            </div>
            <h1>基础照明2</h1>
            <div>
                <div>
                    <span class="light_kind">基础照明A</span>
                    <span class="light_state">开启</span>
                    <input type="checkbox" checked class="switch-large" disabled>
                    <div class="cancontrol"></div>
                </div>
                <div>
                    <span class="light_kind">基础照明A</span>
                    <span class="light_state">开启</span>
                    <input type="checkbox" checked class="switch-large" disabled>
                    <div class="cancontrol"></div>
                </div>
            </div>
            <h1>装饰灯</h1>
            <div>
                <div>
                    <span class="light_kind">装饰灯G1</span>
                    <span class="light_state">开启</span>
                    <input type="checkbox" checked class="switch-large" disabled>
                    <div class="cancontrol"></div>
                </div>
                <div>
                    <span class="light_kind">装饰灯G2</span>
                    <span class="light_state">开启</span>
                    <input type="checkbox" checked class="switch-large" disabled>
                    <div class="cancontrol"></div>
                </div>
            </div>
            <h1>导向标识</h1>
            <div>
                <div>
                    <span class="light_kind">导向标识H</span>
                    <span class="light_state">开启</span>
                    <input type="checkbox" checked class="switch-large" disabled>
                    <div class="cancontrol"></div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
</div>
</div>
