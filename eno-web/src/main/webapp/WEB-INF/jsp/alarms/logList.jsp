<%@page import="com.energicube.eno.monitor.model.KeyValue" %>
<%@page import="java.util.List" %>
<%@page import="com.energicube.eno.common.Const" %>
<%@page import="org.joda.time.DateTime" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ include file="../common/taglib.jsp" %>
<%@ taglib prefix="joda" uri="http://www.joda.org/joda/time/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css"
      href="${pageContext.request.contextPath}/resources/css/bootstrap-select.min.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/ui/jquery.ui.all.css">
<link rel="stylesheet" type="text/css"
      href="${pageContext.request.contextPath}/resources/css/main.css">
<link rel="stylesheet" type="text/css"
      href="${pageContext.request.contextPath}/resources/css/alertManage.css">
<script type="text/javascript"
        src="${pageContext.request.contextPath}/resources/scripts/bootstrap-select.min.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/resources/scripts/main.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/resources/scripts/alertManage.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/scripts/highcharts.js"></script>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/resources/scripts/My97DatePicker/WdatePicker.js"></script>
<c:set var="cxt" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">
    //子系统菜单跳转
    function showAlaramLog(para) {
        window.location.href = 'alarm?groupname=' + encodeURIComponent(para);
    }

    //切换报警记录曲线图周,月视图
    function alarmRecChangeView() {
        var viewtype = $("#alarmRec").find("option:selected").val();
        var subsystem = "${groupname}";
        var getAlarmRecUrl = "getalarmrec?viewtype=" + viewtype + "&subsystem=" + subsystem;
        $.ajax({
            type: "GET",
            url: getAlarmRecUrl,
            success: function (data) {
                console.log(data);
                var loop = data.dateStrList.length;
                var step=1;// x轴的间隔
                if(loop > 10 && loop <= 15){
                    step = 3;
                } else if(loop > 15 && loop <= 31){
                    step = 4;
                }
                drawChart(step,data.dateStrList,data.gAlaramRecList,data.sAlaramRecList,data.eAlaramRecList);
            }
        });
    }

    //切换报警概况周,月视图
    function alarmCouChangeView() {
        var viewtype = $("#alarmLogView").find("option:selected").val();
        var subsystem = "${groupname}";
        var getAlaramCountUrl = "getalarmcount?viewtype=" + viewtype + "&subsystem=" + subsystem;
        $.ajax({
            type: "GET",
            url: getAlaramCountUrl,
            success: function (data) {
                $('#num_one').text(data.eAlaramCount == undefined ? 0 : data.eAlaramCount);//本周紧急报警数量
                $('#num_two').text(data.sAlaramCount == undefined ? 0 : data.sAlaramCount);//本周严重报警数量
                $('#num_three').text(data.gAlaramCount == undefined ? 0 : data.gAlaramCount);//本周普通报警数量
            }
        });
    }

    //绘制报警日志记录曲线图
    function drawChart(step,cataList,gAlaramRec,sAlaramRec,eAlaramRec){
        $('#container').highcharts({
            chart: {
                type: 'areaspline'
            },
            title: {
                text: ''
            },
            legend: {
                enabled: false
            },
            xAxis: {
                labels : {
                    step : step
                },
                categories: cataList
            },
            yAxis: {
                title: {
                    text: ''
                }
            },
            tooltip: {
                crosshairs : true,
                shared: true,
                valueSuffix: ' '
            },
            credits: {
                enabled: false
            },
            plotOptions: {
                areaspline: {
                    fillOpacity: 0.5
                }
            },
            series: [{
                name: '普通',
                color : '#26C3BC',
                fillColor: {
                    linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1},
                    stops: [
                        [0, '#DEECEC'],
                        [1, Highcharts.Color('#DEECEC').setOpacity(0).get('rgba')]
                    ]
                },
                marker : {
                    lineWidth : 2,
                    lineColor : '#26C3BC',
                    fillColor : 'white',
                    symbol: 'circle'
                },
                data: gAlaramRec
            }, {
                name: '严重',
                color : '#5CC8E4',
                fillColor: {
                    linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1},
                    stops: [
                        [0, '#D0EAEB'],
                        [1, Highcharts.Color('#D0EAEB').setOpacity(0).get('rgba')]
                    ]
                },
                marker : {
                    lineWidth : 2,
                    lineColor : '#5CC8E4',
                    fillColor : 'white',
                    symbol: 'circle'
                },
                data: sAlaramRec
            }, {
                name: '紧急',
                color : '#E66E4C',
                fillColor: {
                    linearGradient: { x1: 0, y1: 0, x2: 0, y2: 1},
                    stops: [
                        [0, '#F0D7D0'],
                        [1, Highcharts.Color('#F0D7D0').setOpacity(0).get('rgba')]
                    ]
                },
                marker : {
                    lineWidth : 2,
                    lineColor : '#E66E4C',
                    fillColor : 'white',
                    symbol: 'circle'
                },
                data: eAlaramRec
            }]
        });
    }

    //定义分页对象
    var Paging = {
        createNew: function () {
            var paging = {};
            paging.page = 0;//当前页
            paging.size = 10;//每页显示行数
            paging.total = 0;//总页数
            //跳到第一页
            paging.toFirstPage = function () {
                paging.page = 0;
                paging.getPagingDate();
                paging.updatePagingHtml();
            };
            //跳到最后一页
            paging.toLastPage = function () {
                paging.page = paging.total-1;
                paging.getPagingDate();
                paging.updatePagingHtml();
            };
            //跳到上一页
            paging.toPrePage = function () {
                if(paging.page > 0){
                    paging.page = paging.page - 1;
                    paging.getPagingDate();
                    paging.updatePagingHtml();
                }
            };
            //跳到下一页
            paging.toNextPage = function () {
                if(paging.page < paging.total - 1){
                    paging.page = paging.page + 1;
                    paging.getPagingDate();
                    paging.updatePagingHtml();
                }
            };
            //获取显示用的当前页数 page+1
            paging.getDisplayPageNumber = function () {
                return paging.page + 1;
            };
            //获取分页数据
            paging.getPagingDate = function () {
                throw new Error("getPagingDate该方法必须重写!");
            };
            //更新分页Html
            paging.updatePagingHtml = function () {
                throw new Error("updatePagingHtml该方法必须重写!");
            };
            return paging;
        }
    }

    //分页设置
    var alarmLogsPaging = Paging.createNew();

    //重写 获取分页数据
    alarmLogsPaging.getPagingDate = function () {
        var subsystem = "${groupname}";
        var sDate = $("#sDate").val();
        var eDate = $("#eDate").val();
        var alarmpriority = $("#alarmpriority").find("option:selected").val();

        var getAlarmLogsUrl = "getalarmlogs";
        $.ajax({
            type: "GET",
            url: getAlarmLogsUrl,
            async:false,
            data: {
                sDate: sDate,
                eDate: eDate,
                alarmpriority: alarmpriority,
                subsystem: subsystem,
                pageNumber: alarmLogsPaging.page,
                pageSize: alarmLogsPaging.size
            },
            success: function (data) {
                console.log(data);

                alarmLogsPaging.page = data.paging.page;
                alarmLogsPaging.total = data.paging.total;

                getAlarmdef(data.alarmPriorityDef);

                updateAlarmLogsTable(data.alarmlogList,data.alarmPriorityDef);
            }
        });
    };

    //重写 更新分页Html
    alarmLogsPaging.updatePagingHtml = function () {
        $("#currPage").text(alarmLogsPaging.getDisplayPageNumber());
        $("#totalPage").text(alarmLogsPaging.total);
    };

    //更新 报警列表Html
    function updateAlarmLogsTable(alarmlogList, alarmPriorityDef){
        $("button")
        var tableHTML;
        $.each(alarmlogList,function(){
            tableHTML += '<tr onclick="openAlarmLogDetail('+this.id+');"  style="cursor: pointer;"><td>'+getYMD(this.almt)+'</td>'
                    +'<td>'+getHMS(this.almt)+'</td>'
                    +'<td>'+this.almcomment+'</td>'
                    +'<td>'+this.groupname+'</td>'
                    +'<td>'+this.devicename+'</td>'
                    +'<td>'+this.tagcomment+'</td>'
                    +'<td>'+getAlarmdefDes(alarmPriorityDef,this.almpriority)+'</td>'
                    +'<td>'+this.reviewer+'</td>';
        });
        $("#tbodyAlamLogs").empty();
        $("#tbodyAlamLogs").append(tableHTML);
    }

    //打开报警记录详细信息页面
    function openAlarmLogDetail(id){

        var getAlarmLogDetailUrl = "ucAlarmlogfindById?id=" + id;
        $.ajax({
            type: "GET",
            url: getAlarmLogDetailUrl,
            success: function (data) {
                console.log(data);
                $("#currAlarmLogId").val(data.id);
                $("#currAlarmLogGroupname").val(data.groupname);

                $(".bjLevel").text(data.almpriority);
                $(".bjLevel1").text(data.reviewer);
                $(".bjLevel3").text(data.devicename);
                $(".bjLevel4").text(data.tagcomment);
                $("#textareaId").val(data.reviewcontent);

            }
        });

        $("#dialog-modal").dialog("open");
        $(".ui-widget-content").height(400);
    }

    //应答 更新批注
    function updateAlarmLogDetail(){
        var currAlarmLogId = $("#currAlarmLogId").val();
        var reviewcontent = $("#textareaId").val();
        var updateAlarmLogUrl = "upDateucAlarmlog";
        $.ajax({
            type: "GET",
            url: updateAlarmLogUrl,
            data: {
                id: currAlarmLogId,
                reviewcontent: reviewcontent
            },
            success: function (data) {
                console.log(data);
            }
        });
        $("#dialog-modal").dialog("close");
    }

    //获取报警原因和处理流程
    function getAlarmLogReasonAndFlow(){
        var groupName = $("#currAlarmLogGroupname").val();
        var getAlarmLogReasonAndFlowUrl = 'findByDictidAndDescriptionBy?dictid=SUBSYS'+"&description="+encodeURIComponent(groupName);
        $.ajax({
            type:"GET",
            url:getAlarmLogReasonAndFlowUrl,
            cache:false,
            success:function(data){
                $("#textareaIdreason").val(data.CAUSE);
                $("#textareaIdflow").val(data.REMEDY);
            }
        });
    }

    //设置报警级别下拉列表
    function getAlarmdef(data) {
        if($("#alarmpriority option").length == 1){//只有第一次才初始化下拉列表
            $("#alarmpriority").empty();
            for (var i = 0; i < data.length; i++) {
                $("#alarmpriority").append("<option value='" + data[i].value + "'>" + data[i].description + "</option>");
            }
            $("#alarmpriority").val("0");
        }
    }

    //获取报警级别对应的描述
    function getAlarmdefDes(alarmdef, value){
        for (var i=0;i<alarmdef.length;i++) {
            if(alarmdef[i].value == value){
                return alarmdef[i].description;
            }
        }
    }

    //获取格式化的年月日 yyyy-MM-dd
    function getYMD(dateLongStr){
        var d = new Date();
        d.setTime(dateLongStr);
        return d.getFullYear()+"-"+ (d.getMonth()+1 > 9 ? d.getMonth()+1 : "0" + d.getMonth())+"-"+ (d.getDate() > 9 ? d.getDate() : "0" + d.getDate());
    }

    //获取格式化的时分秒 hh:mm:ss
    function getHMS(dateLongStr){
        var d = new Date();
        d.setTime(dateLongStr);
        return (d.getHours() > 9 ? d.getHours() : "0" + d.getHours())+":"+ (d.getMinutes() > 9 ? d.getMinutes() : "0"+d.getMinutes()) +":"+ (d.getSeconds() > 9 ? d.getSeconds() : "0" + d.getSeconds());
    }

    // 导出报警列表到Excel
    function exportAlarm() {
        var subsystem = "${groupname}";
        var sDate = $("#sDate").val();
        var eDate = $("#eDate").val();
        var alarmpriority = $("#alarmpriority").find("option:selected").val();

        var fileUrl = CONTEXT_PATH + "/pwarn/alarm/download?"
                + "sDate=" + sDate
                + "&eDate=" + eDate
                + "&alarmpriority=" + alarmpriority
                + "&subsystem=" + subsystem;
        window.open(fileUrl);
    }

    $(function () {

        //获取数据,更新各级别报警统计总数
        alarmCouChangeView();

        //获取数据,更新各级别报警统计总数折线图
        alarmRecChangeView();

        //获取数据,更新列表
        alarmLogsPaging.toFirstPage();

        //初始化弹出层-报警详细页
        $("#dialog-modal").dialog({
            width: "900px",
            autoOpen: false,
            show: "blind",
            hide: "explode",
            modal: true
        });

        $(".cancel").click(function () {
            $("#dialog-modal").dialog("close");
        });

    });



</script>
<div class="span10">
    <div class="span12 no_right_margin" style="height: 375px;">
        <!-- 报警概况 图表 -->
        <div class="span6 title alertSituation">
            <div class="alert_title">
                <div>
                    <span class="sub_title">报警概况&nbsp;&nbsp;</span>
                    <select id="alarmLogView" name="alarmLogView" style="width: 100px" onchange="alarmCouChangeView();">
                        <option value="week" selected>本周</option>
                        <option value="month">本月</option>
                    </select>

                    <div class="division_line"></div>
                </div>
            </div>
            <div class="chart_style"
                 style="background-image:url(${pageContext.request.contextPath}/resources/images/alertBj.png);">
                &nbsp;</div>
            <div id="num_one"
                 style="width : 100px; height : 100px ; line-height: 100px; text-align : center;position: absolute;left:335px;top:315px;font-size:44px;color:#FFF"></div>
            <div id="num_two"
                 style="width : 100px; height : 100px ; line-height: 100px; text-align : center;position: absolute;left:600px;top:315px;font-size:44px;color:#FFF"></div>
            <div id="num_three"
                 style="width : 100px; height : 100px ; line-height: 100px; text-align : center;position: absolute;left:850px;top:315px;font-size:44px;color:#FFF"></div>
        </div>

        <!-- 报警记录 图表 -->
        <div class="span6 title alertRecord">
            <div class="alert_title">
                <div>
                    <span class="sub_title">报警记录&nbsp;&nbsp;</span>
                    <select id="alarmRec" name="alarmRec" style="width: 100px" onchange="alarmRecChangeView();">
                        <option value="week" selected>本周</option>
                        <option value="month">本月</option>
                    </select>

                    <div class="division_line"></div>
                </div>
            </div>
            <div class="chart_style" id="container"></div>
        </div>
    </div>

    <!-- 报警列表 -->
    <div class="span12 no_right_margin" style="height: 80%">

        <div class="alert_title title">
            <div>
                <span class="sub_title float_l">报警列表</span>
                <span class="little_ico btn3 float_l">▼</span>

                <div class="division_line float_l div_style2"></div>
            </div>
        </div>
        <div class="span12 query_bar">
            <div>
                <span>起始时间：</span>
                <span><input id="sDate" type="text" onClick="WdatePicker()" style="height: 20px"/></span>
                <span>结束时间：</span>
                <span><input id="eDate" type="text" onClick="WdatePicker()" style="height: 20px"/></span>
            </div>
            <div>
                <span>报警级别：</span>
                <select id="alarmpriority" name="alarmpriority">
                    <option value="0">请选择</option>
                </select>
            </div>
            <div class="butt confirm" onclick="alarmLogsPaging.toFirstPage();">确 定</div>
            <div class="butt exportAlarm " onclick="exportAlarm();">导出报警</div>


        </div>
        <div class="span12 alert_detail">
            <table class="alert_list">
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
                <tbody id="tbodyAlamLogs">
                </tbody>
            </table>
            <div class="paging">
                <img src="${pageContext.request.contextPath}/resources/images/left_first.png"
                     onclick="alarmLogsPaging.toFirstPage();"/>
                <img src="${pageContext.request.contextPath}/resources/images/left.png"
                     onclick="alarmLogsPaging.toPrePage();"/>
                <span class="page_des1">Page</span><span id="currPage">1</span><span class="page_des2">of</span><span id="totalPage">100</span>
                <img src="${pageContext.request.contextPath}/resources/images/right.png"
                     onclick="alarmLogsPaging.toNextPage();"/>
                <img src="${pageContext.request.contextPath}/resources/images/right_end.png"
                     onclick="alarmLogsPaging.toLastPage();"/>
            </div>
        </div>
    </div>
</div>

<!-- 弹出层 报警详细页 -->
<input type="hidden" name="currAlarmLogId" id="currAlarmLogId" value="">
<input type="hidden" name="currAlarmLogGroupname" id="currAlarmLogGroupname" value="">
<div id="dialog-modal" class="popover1" title="报警详细页">
    <div class="popover_nav">
        <ul>
            <li class="cur" style="cursor:pointer"><i class="icon_file_pop"></i>

                <p>报警内容</p></li>
            <li style="cursor:pointer" onclick="getAlarmLogReasonAndFlow()"><i class="icon_file_question"></i>

                <p>可能原因</p></li>
            <li style="cursor:pointer" onclick="getAlarmLogReasonAndFlow()"><i class="icon_file_list"></i>

                <p>处理流程</p></li>
            <li style="cursor:pointer"><i class="icon_file_list1"></i>

                <p>批注</p></li>
        </ul>
    </div>
    <div class="alert_tab">
        <div class="alert_table">
            <table>
                <tbody>
                <tr>
                    <td class="rank">报警级别</td>
                    <td class="bjLevel"></td>
                </tr>
                <tr>
                    <td class="respond_time">响应时间</td>
                    <td class="bjLevel1"></td>
                </tr>
                <tr>
                    <td class="this_device">设备</td>
                    <td class="bjLevel3"></td>
                </tr>
                <tr>
                    <td class="device_pos">位置</td>
                    <td class="bjLevel4"></td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="alert_ol probably_reason">
            <textarea id="textareaIdreason" readonly="readonly" style="width: 80%; margin-left: 50px;" rows="10"
                      cols=""></textarea>
        </div>
        <div class="alert_ol handle_flow">
            <textarea id="textareaIdflow" readonly="readonly" style="width: 80%; margin-left: 50px;" rows="10"
                      cols=""></textarea>
        </div>
        <div class="alert_ol handle_flowd" align="center">
            <textarea id="textareaId" style="width: 80%" rows="10" cols=""></textarea>
        </div>
    </div>

    <div class="modal_btn">
        <div class="hand_ok" onclick="updateAlarmLogDetail();">应答</div>
        <div class="cancel">关闭</div>
    </div>
</div>