<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/jquery.mCustomScrollbar.css"/>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/pfe/monitorControl.css"/>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/pfe/jquery.mCustomScrollbar.concat.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/pfe/bootstrap-switch.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/highcharts.js"></script>
<!-- customVar.js主要是用于声明获取数据的方式以及数据获取的地址 -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/mctrl/customVar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/pfe/customConfig.js"></script>

<div class="custompage">

<div class="span12 main row-fluid" id="passengerView">

<div class="span10 right_content nomargin">
<div>
<div class="pmt_block">
<div class="pmt" id="passengerBackground">
   
    <div class="custom_rank" style="z-index: 9999;">
        <div><h1 id="passenger_order">客流量排名</h1><div class="flip">>></div></div>
        <div id="content_1">
            <table>
                <tbody id="top20Passenger">

                </tbody>
            </table>
        </div>
    </div>
    <div class="show_rank"><<</div>
    <!-- <div class="storepos store_1 number1">
        <div class="rank_icon"><p class="rank_num">1</p><div class="arrow"></div></div>
        <div class="cus_info">
            <div class="store_name">SELECTED</div>
            <div class="store_custom_num">3000人</div>
        </div>
    </div> -->
    <!-- <div class="storepos store_2 number2">
        <div class="rank_icon">2</div>
        <div class="cus_info">
            <div class="store_name">PEACE BIRD(M)</div>
            <div class="store_custom_num">2000人</div>
        </div>
    </div>
    <div class="storepos store_3 number3">
        <div class="rank_icon">3</div>
        <div class="cus_info">
            <div class="store_name">RIVERSTONE</div>
            <div class="store_custom_num">1000人</div>
        </div>
    </div>
    <div class="storepos store_4">
        <div class="rank_icon">4</div>
        <div class="cus_info">
            <div class="store_name">SW JEANS</div>
            <div class="store_custom_num">500人</div>
        </div>
    </div>
    <div class="storepos store_5">
        <div class="rank_icon">5</div>
        <div class="cus_info">
            <div class="store_name">JACK&JONES</div>
            <div class="store_custom_num">500人</div>
        </div>
    </div>
    <div class="storepos store_6">
        <div class="rank_icon">6</div>
        <div class="cus_info">
            <div class="store_name">boardcast</div>
            <div class="store_custom_num">500人</div>
        </div>
    </div>
    <div class="storepos store_7">
        <div class="rank_icon">7</div>
        <div class="cus_info">
            <div class="store_name">Hopeshow</div>
            <div class="store_custom_num">500人</div>
        </div>
    </div>
    <div class="storepos store_8">
        <div class="rank_icon">8</div>
        <div class="cus_info">
            <div class="store_name">蒂奥莎</div>
            <div class="store_custom_num">500人</div>
        </div>
    </div>
    <div class="storepos store_9">
        <div class="rank_icon">9</div>
        <div class="cus_info">
            <div class="store_name">ONLY</div>
            <div class="store_custom_num">500人</div>
        </div>
    </div>
    <div class="storepos store_10">
        <div class="rank_icon">10</div>
        <div class="cus_info">
            <div class="store_name">PEACE BIRD(F)</div>
            <div class="store_custom_num">500人</div>
        </div>
    </div>
    <div class="storepos store_11">
        <div class="rank_icon">11</div>
        <div class="cus_info">
            <div class="store_name">乐町</div>
            <div class="store_custom_num">500人</div>
        </div>
    </div>
    <div class="storepos store_12">
        <div class="rank_icon">12</div>
        <div class="cus_info">
            <div class="store_name">圣妮</div>
            <div class="store_custom_num">500人</div>
        </div>
    </div>
    <div class="storepos store_13">
        <div class="rank_icon">13</div>
        <div class="cus_info">
            <div class="store_name">VERSACE</div>
            <div class="store_custom_num">500人</div>
        </div>
    </div>
    <div class="storepos store_14">
        <div class="rank_icon">14</div>
        <div class="cus_info">
            <div class="store_name">MEACHEAL</div>
            <div class="store_custom_num">500人</div>
        </div>
    </div>
    <div class="storepos store_15">
        <div class="rank_icon">15</div>
        <div class="cus_info">
            <div class="store_name">AMASS</div>
            <div class="store_custom_num">500人</div>
        </div>
    </div>
    <div class="storepos store_16">
        <div class="rank_icon">16</div>
        <div class="cus_info">
            <div class="store_name">五色风马</div>
            <div class="store_custom_num">500人</div>
        </div>
    </div>
    <div class="storepos store_17">
        <div class="rank_icon">17</div>
        <div class="cus_info">
            <div class="store_name">Joannade</div>
            <div class="store_custom_num">500人</div>
        </div>
    </div>
    <div class="storepos store_18">
        <div class="rank_icon">18</div>
        <div class="cus_info">
            <div class="store_name">Girdear</div>
            <div class="store_custom_num">500人</div>
        </div>
    </div> -->
    <!-- <div class="storepos store_6">SW JEANS</div>
    <div class="storepos store_7">boardcast</div>
    <div class="storepos store_8">Hopeshow</div>
    <div class="storepos store_9">蒂奥莎</div>
    <div class="storepos store_10">ONLY</div>
    <div class="storepos store_11">PEACE BIRD(F)</div>
    <div class="storepos store_12">乐町</div>
    <div class="storepos store_13">圣妮</div>
    <div class="storepos store_14">VERSACE</div>
    <div class="storepos store_15">MEACHEAL</div>
    <div class="storepos store_16">AMASS</div>
    <div class="storepos store_17">五色风马</div>
    <div class="storepos store_18">Joannade</div>
    <div class="storepos store_19">Girdear</div> --><!--
					<div class="storepos storepos_20">ZUKKA</div>
					<div class="storepos storepos_21">JETEZO</div>
					<div class="storepos storepos_22">PIZZA HUT</div>
					<div class="storepos storepos_23">电影售票点</div>
					<div class="storepos storepos_24">Caffe bene</div>
					<div class="storepos storepos_25">Desires</div>
					<div class="storepos storepos_26">TISSOT</div>
					<div class="storepos storepos_27">KFC</div>
					<div class="storepos storepos_28">蒂爵珠宝</div>
					<div class="storepos storepos_29">绝代佳人</div>
					<div class="storepos storepos_30">DEALUNA</div>
					<div class="storepos storepos_31">UHTEU集合店</div>
					<div class="storepos storepos_32">NBY+CROQUE</div>
					<div class="storepos storepos_33">GOELIA</div>
					<div class="storepos storepos_34">Adidas-Originals</div>
					<div class="storepos storepos_35">La Chapelle</div>
					<div class="storepos storepos_36">UNIQUE</div>
					<div class="storepos storepos_37">时尚表集合店</div>
					<div class="storepos storepos_38">CASIO</div>
					<div class="storepos storepos_39">VERO MODA</div>
					<div class="storepos storepos_40">T-YSKJ</div>
					<div class="storepos storepos_41">CACHE CACHE</div>
					<div class="storepos storepos_42">Effusive</div>
					<div class="storepos storepos_43">金麟珠宝</div> -->

</div>
</div>
</div>
<div class="data_display" style="width: 1440px;">
    <div class="left_bar">
        <h5 class="store_name_display">总客流信息</h5>
        <section class="today">
            <h3>今日累计客流</h3>
            <div><span class="num custom_count" id="totalPassOfToday">6,328</span><span class="unit">&nbsp;&nbsp;人</span></div>
        </section>
        <section class="comp_yes">
            <h3>同比昨日</h3>
            <div><div class="arrow_flag"><img style="height: 35px;" id="compareImg" src="${pageContext.request.contextPath}/resources/images/biguparrow.png"></div><span class="num per" id="compareOfYestoday">10</span><span class="unit">&nbsp;&nbsp;%</span></div>
        </section>
    </div>
    <div class="middle_bar" id="passenger_chart"></div>
    <div class="right_bar">
        <div class="day_cus">
            <h2>本月日均客流量</h2>
            <div>
                <h4><span id="avgPassOfMonth">2128</span><span>人</span></h4>
                <img src="${pageContext.request.contextPath}/resources/images/top_bar.png">
            </div>
        </div>
        <div class="year_cus">
            <h2>本年日均客流量</h2>
            <div>
                <h4><span id="avgPassOfYear">2541</span><span>人</span></h4>
                <img src="${pageContext.request.contextPath}/resources/images/down_bar.png">
            </div>
        </div>
    </div>
</div>
</div>

</div>

</div>

<script type="text/javascript">
    $(function() {
        $("#content_1").mCustomScrollbar({
            scrollButtons:{
                enable:true
            },
            advanced:{
                updateOnContentResize:true
            }
        });
    });
</script>