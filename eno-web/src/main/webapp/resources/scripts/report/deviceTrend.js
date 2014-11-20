var previewType = 4;
var colors = ['rgba(255,0,0,0.3)', 'rgba(141,182,205,0.3)', 'rgba(255,165,0,0.3)', 'rgba(155,205,155,0.3)', 'rgba(142,56,142,0.3)'];
$(function () {
    var date = new Date().Format("yyyy-MM-dd");
    $("#from_time").val(date);
    $("#to_time").val(date);

    getSystemNames();
});
// 获取子系统列表
function getSystemNames() {
    var url = CONTEXT_PATH + '/reportConfig/getSystemList';
    $.ajax({
        type: "GET",
        url: url,
        async: false, //同步
        success: function (result) {
            try {
                var optionHtml = "";
                for (var i = 0; i < result.length; i++) {
                    optionHtml += "<option>" + result[i] + "</option>";
                }
                $("#system_type").html('').append(optionHtml);
                $("#system_type").change(function () {
                    getDeviceTypeNames($("#system_type option:selected").val());
                });
                var system_type = $("#system_type option:selected").val();
                getDeviceTypeNames(system_type);
            } catch (e) {
                console.log('getSystemNames---error--' + e);
            }
        },
        error: function (result) {
            console.log('error---');
        }
    });
}
// 获取设备类型
function getDeviceTypeNames(systemType) {
    var url = CONTEXT_PATH + '/reportConfig/getDeviceType';
    $.ajax({
        type: "GET",
        data: {
            system: systemType
        },
        url: url,
        async: false, //同步
        success: function (result) {
//			console.log(result);
            try {
                var optionHtml = "";
                for (var i = 0; i < result.length; i++) {
                    optionHtml += "<option>" + result[i] + "</option>";
                }
                $("#device_type").empty().append(optionHtml);
                queryDevices();
            } catch (e) {
                console.log('getSystemNames---error--' + e);
            }
        },
        error: function (result) {
            console.log('error---');
        }
    });
}
// 获取设备
function getDevices(systemType, deviceType, select_time_type, sTime, eTime) {
    $('#report_page_num').hide();
    var url = CONTEXT_PATH + '/reportConfig/getDeviceList';
    $.ajax({
        type: "GET",
        data: {
            system: systemType,
            devicetype: deviceType
        },
        url: url,
//		 async : false, //同步
        success: function (result) {
//			console.log(result);
            try {
                $('#devices_ontent').html('');
                var html = '<div class="equip_list_title">';
                html += deviceType + '</div><div class="equip_list_box"><ul id="report_menu_ul" >';
                $("#deviceSelect option").remove();
                $("#deviceSource option").remove();
                for (var i = 0; i < result.length; i++) {
                    html += '<li num=' + i + '><a href="javascript:void(0)">' + result[i] + '</a></li>';
                    $("#deviceSource").append("<option value='" + result[i] + "'>" + result[i] + "</option>");
                }
                html += '</ul></div><div id="device_scroll" style="width:183px">';
                html += '<div id="device_list_down" class="device_page_list"><img src="../resources/images/arrow_down.png" title="向上翻页"/></div>';
                html += '<div id="device_list_up" class="device_page_list"><img src="../resources/images/arrow_up.png" title="向下翻页"/></div></div>';
                $('#devices_ontent').html('').html(html);
                var ul_height = $('.equip_list_box').height();
                if (ul_height < 600) {
                    $("#device_scroll").hide();
                }
                // 第一次加载报表
                var device_child_type = $('#report_menu_ul li:first').addClass('on').text();
                createReportDiv(device_child_type, select_time_type, sTime, eTime);
                // 加载设备列表相关事件
                deviceEvents(result.length - 1);
            } catch (e) {
                console.log('getSystemNames---error--' + e);
            }
        },
        error: function (result) {
            console.log('error---');
        }
    });
}
// 点击查询事件
function queryDevices() {
    var sTime = $('#from_time').val(); // 获取开始时间
    if (sTime == "") {
        alert("请选择要查询的时间段！");
        return false;
    }
    if (temp_tfrom != '') {
        rsTime = temp_tfrom;
    } else {
        rsTime = sTime;
    }
    $('#current_search_date').html('').html(sTime);
    var system_type = $('#system_type option:selected').html(); // 获取子系统
    var device_type = $('#device_type option:selected').html(); // 获取设备名称
    var select_time_type = $('#select_time_type option:selected').val(); // 获取当前选择的查询类型（日、周、月）
    rSystemType = system_type;
    rDeviceType = device_type;
    rDateType = select_time_type;
    getDevices(system_type, device_type, select_time_type, rsTime, rsTime);
}

// 
function deviceEvents(size) {
    //设备点击事件
    $('#report_menu_ul li').each(function () {
        $(this).click(function () {
            $('#report_menu_ul li.on').removeClass('on');
            $(this).addClass('on');
            var device_child_type = $(this).text();
            $('#report_page_num').hide();
            createReportDiv(device_child_type, rDateType);
        });
    });

    var $top = $("#device_list_up");
    var $bottom = $("#device_list_down");
    $top.click(function () {
        var num = $("#report_menu_ul li:eq(" + size + ")").attr('num');
        if (num == 0) {
            return;
        }
        $("#report_menu_ul").animate({
            marginTop: "-64px"
        }, 500, function () {
            $("#report_menu_ul li:eq(0)").appendTo($("#report_menu_ul"));
            $("#report_menu_ul").css("marginTop", "0px");
        });
    });
    $bottom.click(function () {
        var num = $("#report_menu_ul li:eq(0)").attr('num');
        if (num == 0) {
            return;
        }
        $("#report_menu_ul").animate({
            marginTop: "64px"
        }, 300, function () {
            $("#report_menu_ul li:last").insertBefore($("#report_menu_ul li:first"));
            $("#report_menu_ul").css("marginTop", "0px");
        });
    });
}
// 调用报表方法
function createReportDiv(device, selectTimeType) {
    var url = CONTEXT_PATH + '/reportConfig/getReportconfigsList';
    modelCode = device;
    $.ajax({
        type: "GET",
        data: {
            system: rSystemType,
            devicetype: rDeviceType,
            device: device
        },
        url: url,
        async: false, //同步
        beforeSend: function () {
            $("#progressMask").show();
        },
        success: function (result) {
//			console.log(result);
            try {
                var divSize = result.length;
                var style = '';
                var yLines = 5;
                if (divSize == 1) {
                    previewType = 1;
                    style = 'width:1630px; height:720px';
                    yLines = 12;
                } else if (divSize == 2) {
                    previewType = 2;
                    style = 'width:1630px; height:360px';
                    yLines = 5;
                } else {
                    previewType = 4;
                    style = 'width:815px; height:360px';
                    yLines = 5;
                }
//				return;
                $("#report_content").children().remove();
                for (var i = 0; i < divSize; i++) {
                    buildSingleCharts("report_" + i, style, result[i].params, result[i].name, result[i].id, result[i].ispd, result[i].unit, "line", colors[i % colors.length], result[i].params, true, yLines, selectTimeType);
                }
                //$("#report_content div:lt(5)").show();
                $("#progressMask").hide();
            } catch (e) {
                $("#progressMask").hide();
                console.log('getSystemNames---error--' + e);
            }
        },
        error: function (result) {
            console.log('error---');
        }
    });
}

function buildSingleCharts(divId, style, device, name, id, ispd, yName, charttype, color, seriesname, showLegend, yLines, selectTimeType) {
    var type = rDateType;
    var dayFormat = '&beforeFormat=yyyy-MM-dd HH:mm:ss&afterFormat=HH:mm';
    if (selectTimeType != 'day') {
        dayFormat = '&beforeFormat=yyyy-MM-dd HH:mm:ss&afterFormat=MM/dd HH:mm';
    }
    var legendName = new Array();
    legendName.push(seriesname);
    var url = CONTEXT_PATH + '/Chart/GetChartData?attribute=' + dayFormat;
    $.ajax({
        type: "POST",
        url: url,
        data: {
            name: name,
            id: id,
            type: type,
            tfrom: rsTime,
            tto: (type == 'day' ? getTimeByDays(rsTime, 1) : (type == 'year' ? (parseInt(rsTime.substring(0, 4)) + 1) + "-01-01" : (type == 'week' ? getTimeByDays(rsTime, 6) : (type == 'month' ? returnNextMonthfirstDay(rsTime) : '')))),
            ispd: ispd,
            ipaddress: ipaddress,
            port: port,
            isNotStatic: rIsNotStatic
        },
        async: false, //同步
        success: function (result) {
//			console.log("---"+result);
            try {
                var datalist = result.datalist; // 此处为数据列表，数组形式，如：datalist[0]即可取到第一组数据
                var catalist = result.catalist;
                var dataArray = [];
                var cataArray = [];
                var index = [];
                if (rSystemType == '能源管理1') {
                    for (var i = 0; i < catalist.length; i++) {
                        if (catalist[i].substring(catalist[i].length - 1, catalist[i].length) != 0) {
                            cataArray.push(catalist[i]);
                            index.push(i);
                        }
                    }
                    for (var n = 0; n < datalist.length; n++) {
                        var data = [];
                        for (var j = 0; j < index.length; j++) {
                            data.push(datalist[n][index[j]]);
                        }
                        dataArray.push(data);
                    }
//					console.log(cataArray);
                    var seriesList = []; // 初始化图表数据列表，元素个数代表对应的曲线
                    for (var i = 0; i < dataArray.length; i++) {
                        seriesList.push({
                            type: charttype,
                            name: seriesname,
                            symbol: 'none',
                            itemStyle: {
                                normal: {
                                    color: color,
                                    areaStyle: {
                                        color: color,
                                        type: 'default'
                                    }
                                }
                            },
                            data: dataArray[i]
                        });

                    }
                    if (seriesList.length > 0) {
                        if (cataArray.length > 0 && result.catalist[0] != null && seriesList != '') {
                            $('#report_content').append('<div id="' + divId + '" class="trend_chart_box" style="display:none;' + style + '" ></div>');
                            chartsFactory(divId, device, legendName, yName, cataArray, seriesList, color, yLines, selectTimeType);
                            $("#report_content").children().each(function (i) {
                                if (i >= 0 && i < 4)//显示第page页的记录
                                    $(this).show();
                            });
                        }
                        reportPageEvent();
                    }
                } else {
                    var seriesList = []; // 初始化图表数据列表，元素个数代表对应的曲线
                    for (var i = 0; i < datalist.length; i++) {
                        seriesList.push({
                            type: charttype,
                            name: seriesname,
                            symbol: 'none',
                            showAllSymbol: true,
                            itemStyle: {
                                normal: {
                                    color: color,
                                    areaStyle: {
                                        color: color,
                                        type: 'default'
                                    }
                                }
                            },
                            data: datalist[i]
                        });

                    }
                    if (seriesList.length > 0) {
                        if (result.catalist[0] != null && seriesList != '') {
                            $('#report_content').append('<div id="' + divId + '" class="trend_chart_box" style="display:none;' + style + '" ></div>');
                            chartsFactory(divId, device, legendName, yName, result.catalist, seriesList, color, yLines, selectTimeType);
                            $("#report_content").children().each(function (i) {
                                if (i >= 0 && i < 4)//显示第page页的记录
                                    $(this).show();
                            });
                        }
                        reportPageEvent();
                    }
                }
            } catch (e) {
                console.log('select talbe ---error--对象名' + name + '#' + id + '#span无效:' + e);
            }
        },
        error: function (result) {
            console.log('error---');
        }
    });
}
//在页面画出报表
function chartsFactory(divId, title, legendName, yName, xAxisData, seriesList, color, yLines, selectTimeType) {
    var myChart = echarts.init(document.getElementById(divId));
    myChart.showLoading({
        text: '加载中。。。。',
        effect: 'spin',
        textStyle: {
            fontSize: 20
        }
    });
    var option = {
        title: {
            text: title,
            textStyle: {
                fontSize: 28,
                fontWeight: 'normal'
            },
            x: 20,
            y: 20
        },
        backgroundColor: {
            color: '#fff'
        },
        toolbox: {
            show: true,
            feature: {
                magicType: {show: true, type: ['line', 'bar']},
                restore: {show: true}
            },
            x: 'right',
            padding: [20, 30, 0, 0]

        },
        tooltip: {
            trigger: 'axis',
            backgroundColor: color,
            formatter: function (params) {
                var res = params[0][0] + "<br/>" +
                    "<div style='width:100px;white-space:normal'>" + params[0][1] + "</div><div style='float:right; font-size:32px; margin:-25px 1px 1px 100px'>" + params[0][2] + "</div>";
                return res;
            }
        },
//        legend: {
//            data:legendName
//        },
        grid: {
            x: 80,
            x2: 50,
            y: (yLines == 5 ? '33%' : '20%'),
            height: (yLines == 5 ? '55%' : '70%'),
            borderWidth: 0
        },
        xAxis: [
            {
                type: 'category',
                splitNumber: 10,
                data: xAxisData,
                splitLine: false,
                axisTick: false,
                boundaryGap: true,
                axisLabel: {
                    interval: 0,
                    formatter: function (value) {
                        var len = xAxisData.length;
                        if (selectTimeType == 'month') {
                            var day = value.substring(3, 5);
                            /* 如何是月份， 横坐标每个5天显示一个日期并且是0点数据*/
                            if (value.indexOf('00:00') != -1 && parseInt(day) % 5 == 1) {
                                return value;
                            } else {
                                return '';
                            }
                        } else if (selectTimeType == 'day') {
                            var num = Math.ceil(len / 7);
                            /* 如何是天，横坐标显示7个数据*/
                            if ($.inArray(value, xAxisData) % num == 0) {
                                return value;
                            } else {
                                return '';
                            }
                        } else {
                            /* 如何是周，横坐标显示每天0点数据*/
                            if (value.indexOf('00:00') != -1) {
                                return value;
                            } else {
                                return '';
                            }
                        }
                    }
                },
                axisLine: {
                    lineStyle: {
                        color: 'rgba(0,0,0,0.5)',
                        width: 1
                    }
                }
            }
        ],
        yAxis: [
            {
                name: (yName == null || yName == '——') ? '' : yName,
                type: 'value',
                scale: true,
                splitNumber: yLines,
                nameTextStyle: {
                    color: '#000'
                },
                axisLine: {
                    lineStyle: {
                        width: 0
                    }
                },
                axisLabel: {
                    textStyle: {
                        align: 'right'
                    }
                }
            }
        ],
        series: seriesList
    };
    myChart.hideLoading();
    // 为echarts对象加载数据
    myChart.setOption(option);
}
// 图表翻页事件
function reportPageEvent() {
    var $reportDiv = $("#report_content").children();
    $reportDiv.each(function (i, data) {
        if (i < previewType) {
            $(data).show();
        } else {
            $(data).hide();
        }
    });
    var divSize = $reportDiv.length;
    if (divSize < 5) {
        return;
    }
    var divNum = Math.ceil(divSize / previewType);
    var numHtml = '<a href="javascript:void(0)" class="page_prev"></a>';
    for (var i = 1; i <= divNum; i++) {
        numHtml += '<a href="javascript:void(0)" class="page_num">' + i + '</a>';
    }
    numHtml += '<a href="javascript:void(0)" class="page_next"></a>';
    $("#report_page_num").html(numHtml);
    $("#report_page_num a.page_num:first").addClass('page_on');
    pageNumEvents();
    if (divSize < 2) {
        $("#report_page_num").hide();
        $("#preview_type").hide();
    } else {
        $("#report_page_num").show();
        $("#preview_type").show();
    }
}
// 图表翻页事件
function pageNumEvents() {
    //报表翻页-点击
    $('#report_page_num a.page_num').click(function () {
        $('#report_page_num a.page_on').removeClass('page_on');
        $(this).addClass('page_on');
    });
    //报表翻页-前进、后退
    $("#report_page_num .page_num").click(function () {
        var begin = (parseInt($(this).html()) - 1) * previewType;
        var end = begin + parseInt(previewType);
        $("#report_content").children().hide();
        $("#report_content").children().each(function (i) {
            if (i >= begin && i < end)//显示第page页的记录
                $(this).show();
        });
    });
    var $page_prev = $("#report_page_num a.page_prev");
    var $page_next = $("#report_page_num a.page_next");
    $page_prev.click(function () {
        var $current = $("#report_page_num a.page_on");
        var num = $current.html();
        var minNum = $("#report_page_num a.page_num:first").html();
        if (num > minNum) {
            $("#report_page_num a.page_on").removeClass('page_on');
            $current.prev().addClass('page_on');
            var begin = (parseInt($("#report_page_num a.page_on").html()) - 1) * previewType;
            var end = begin + parseInt(previewType);
            $("#report_content").children().hide();
            $("#report_content").children().each(function (i) {
                if (i >= begin && i < end)//显示第page页的记录
                    $(this).show();
            });
        }
    });
    $page_next.click(function () {
        var $current = $("#report_page_num a.page_on");
        var num = $current.html();
        var minNum = $("#report_page_num a.page_num:last").html();
        if (num < minNum) {
            $("#report_page_num a.page_on").removeClass('page_on');
            $current.next().addClass('page_on');
            var begin = parseInt(num) * previewType;
            var end = begin + parseInt(previewType);
            $("#report_content").children().hide();
            $("#report_content").children().each(function (i) {
                if (i >= begin && i < end)//显示第page页的记录
                    $(this).show();
            });
        }
    });
}

//批量导出数据到Excel
function exportSelect() {
    $('#mask .cancle').on('click', function (event) {
        $('#mask').hide();
    });
    moveall('deviceSelect', 'deviceSource');
    $('#mask').show();
}
function expBatchDataToExcel() {
    var devices = new Array();
    $("#deviceSelect option").each(function (i, item) {
        devices.push(item.value);
    });
    if (devices.length == 0) {
        alert("为选择设备！");
        return;
    }
    var tfrom = $('#from_time').val();
    var system = $('#system_type option:selected').val();
    var deviceType = $('#device_type option:selected').val();
    var timeType = $('#select_time_type option:selected').val();
    var hSrc = CONTEXT_PATH + "/Chart/exportToExcel?tfrom=" + tfrom + "&tto=" + (timeType == 'day' ? getTimeByDays(rsTime, 1) : (timeType == 'year' ? (parseInt(rsTime.substring(0, 4)) + 1) + "-01-01" : (timeType == 'week' ? getTimeByDays(rsTime, 6) : (timeType == 'month' ? returnNextMonthfirstDay(rsTime) : ''))))
        + "&system=" + system + "&deviceType=" + deviceType + "&devices=" + devices + "&type=" + timeType + "&decimals=0.0&ipaddress=" + ipaddress + "&port=" + port + "&isNotStatic=" + rIsNotStatic;
    window.open(hSrc);
    $('#mask').hide();
}

//多选列表插件事件
var sortitems = 1; // Automatically sort items within lists? (1 or 0)  

function move(fbox, tbox) {
    fbox = document.getElementById(fbox);
    tbox = document.getElementById(tbox);

    for (var i = 0; i < fbox.options.length; i++) {
        if (fbox.options[i].selected && fbox.options[i].value != "") {

            if (exits(tbox, fbox.options[i].value)) {
                fbox.options[i].value = "";
                fbox.options[i].text = "";
                continue;
            }
            var no = new Option();
            no.value = fbox.options[i].value;
            no.text = fbox.options[i].text;
            tbox.options[tbox.options.length] = no;
            fbox.options[i].value = "";
            fbox.options[i].text = "";
        }
    }
    BumpUp(fbox);
    if (sortitems) SortD(tbox);
}

function moveall(fbox, tbox) {
    fbox = document.getElementById(fbox);
    tbox = document.getElementById(tbox);


    for (var i = 0; i < fbox.options.length; i++) {
        if (fbox.options[i].value != "") {
            if (exits(tbox, fbox.options[i].value)) {
                fbox.options[i].value = "";
                fbox.options[i].text = "";
                continue;
            }
            var no = new Option();
            no.value = fbox.options[i].value;
            no.text = fbox.options[i].text;
            tbox.options[tbox.options.length] = no;
            fbox.options[i].value = "";
            fbox.options[i].text = "";
        }
    }
    BumpUp(fbox);
    if (sortitems) SortD(tbox);
}

//检测HTMLSelect中是否有value项Option  
function exits(HTMLSelect, value) {
    for (var i = 0; i < HTMLSelect.length; i++) {
        if (HTMLSelect[i].value == value) {
            return true;
        }
    }
    return false;
}

//把空值去掉并把下面的选项移到上面来  
function BumpUp(box) {
    for (var i = 0; i < box.options.length; i++) {
        if (box.options[i].value == "") {
            for (var j = i; j < box.options.length - 1; j++) {
                box.options[j].value = box.options[j + 1].value;
                box.options[j].text = box.options[j + 1].text;
            }
            var ln = i;
            break;
        }
    }
    if (ln < box.options.length) {
        box.options.length -= 1;
        BumpUp(box);
    }
}

function SortD(box) {
    var temp_opts = new Array();
    var temp = new Object();
    for (var i = 0; i < box.options.length; i++) {
        temp_opts[i] = box.options[i];
    }

    for (var x = 0; x < temp_opts.length - 1; x++) {
        for (var y = (x + 1); y < temp_opts.length; y++) {
            if (temp_opts[x].text > temp_opts[y].text) {
                temp = temp_opts[x].text;
                temp_opts[x].text = temp_opts[y].text;
                temp_opts[y].text = temp;
                temp = temp_opts[x].value;
                temp_opts[x].value = temp_opts[y].value;
                temp_opts[y].value = temp;
            }
        }
    }

    for (var i = 0; i < box.options.length; i++) {
        box.options[i].value = temp_opts[i].value;
        box.options[i].text = temp_opts[i].text;
    }
}

