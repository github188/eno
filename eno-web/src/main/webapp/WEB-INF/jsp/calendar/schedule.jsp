<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglib.jsp" %>
    <div id="content">
        <div id="calendar_mode_switch">
            <ul>
                <li><a href="${pageContext.request.contextPath}/calendar/showDayCalendar?systemId=${systemCode}">日</a></li>
                <li><a href="${pageContext.request.contextPath}/calendar/showWeekCalendar?systemId=${systemCode}">周</a></li>
                <li><a href="${pageContext.request.contextPath}/calendar/showMonthCalendar?systemId=${systemCode}">月</a></li>
                <li class="current"><a href="#">日程表</a></li>
            </ul>
        </div>
        <div class="actionbar">
            <div class="left">
                <div id="year_selector"><span id="current_year">${year}</span><div class="arrow_down right"></div></div>
                <div class="year_list">
                    <ul>
                        <li>${year-3}</li>
                        <li >${year-2}</li>
                        <li >${year-1}</li>
                        <li class="current">${year}</li>
                        <li>${year+1}</li>
                        <li>${year+2}</li>
                    </ul>
                </div>
                <div id="month_selector"><span id="current_month">${month}</span><div class="arrow_down right"></div></div>
                <div class="month_list">
                    <ul>
                        <li <c:if test="${month==1}">class="current"</c:if>>1月</li>
                        <li <c:if test="${month==2}">class="current"</c:if> >2月</li>
                        <li <c:if test="${month==3}">class="current"</c:if> >3月</li>
                        <li <c:if test="${month==4}">class="current"</c:if> >4月</li>
                        <li <c:if test="${month==5}">class="current"</c:if> >5月</li>
                        <li <c:if test="${month==6}">class="current"</c:if> >6月</li>
                        <li <c:if test="${month==7}">class="current"</c:if> >7月</li>
                        <li <c:if test="${month==8}">class="current"</c:if> >8月</li>
                        <li <c:if test="${month==9}">class="current"</c:if> >9月</li>
                        <li <c:if test="${month==10}">class="current"</c:if> >10月</li>
                        <li <c:if test="${month==11}">class="current"</c:if> >11月</li>
                        <li <c:if test="${month==12}">class="current"</c:if> >12月</li>
                    </ul>
                </div>
                <div id="week_selector">
                    <div class="arrow_left" onclick="javascript:gotoLastWeekInListView()"></div>
                    <span id="selected_week"></span>
                    <div class="arrow_right" onclick="javascript:gotoNextWeekInListView()"></div>
                </div>
                <div id="current_week" onclick="javascript:gotoCurrentWeekInListView()"><span>本周</span></div>
            </div>

            <div class="right">
                <div id="create_event" onclick="javascript:showChoiceEventTypeDialog();">创建事件</div>
            </div>

        </div>

        <div id="list">
            <div id="month_slider">
                <div class="circle_arrow_left" onclick="javascript:gotoLastWeekInListView()"></div>
                <span></span>
                <div class="circle_arrow_right" onclick="javascript:gotoNextWeekInListView()"></div>
            </div>

            <div id="weather_slider">
            <ul>
            <li>
                <div class="weather_for_oneday">
                    <div class="weather_caption">
                        <div class="date">
                            <div class="day" style="color:#ffffff">00</div>
                            <div class="week_day">周一</div>
                        </div>
                    </div>
                    <div class="weather_detail_list" onclick="showOneDayWeather(0)">
                        <div class="holiday">
                            <div class="holiday_desc right"></div>
                        </div>
                        <div class="weather_desc">
                            <div class="weather_label_normal"></div>
                            <div class="temperature big_temperature"></div>
                            <div class="desc right"></div>
                        </div>
                        <div class="weather_remark">
                            <div class="weather_am left">
                                <div class="sun_up_icon"></div>
                                <div class="weather_time sunrise"></div>
                            </div>
                            <div class="weather_pm right">
                                <div class="sun_down_icon"></div>
                                <div class="weather_time sunset"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </li>
            <li>
                <div class="weather_for_oneday">
                    <div class="weather_caption">
                        <div class="date">
                            <div class="day" style="color:#ffffff">00</div>
                            <div class="week_day">周二</div>
                        </div>
                    </div>
                    <div class="weather_detail_list" onclick="showOneDayWeather(1)">
                        <div class="holiday">
                            <div class="holiday_desc right"></div>
                        </div>
                        <div class="weather_desc">
                            <div class="weather_label_normal"></div>
                            <div class="temperature big_temperature"></div>
                            <div class="desc right"></div>
                        </div>
                        <div class="weather_remark">
                            <div class="weather_am left">
                                <div class="sun_up_icon"></div>
                                <div class="weather_time sunrise"></div>
                            </div>
                            <div class="weather_pm right">
                                <div class="sun_down_icon"></div>
                                <div class="weather_time sunset"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </li>
            <li>
                <div class="weather_for_oneday">
                    <div class="weather_caption">
                        <div class="date">
                            <div class="day" style="color:#ffffff">00</div>
                            <div class="week_day">周三</div>
                        </div>
                    </div>
                    <div class="weather_detail_list" onclick="showOneDayWeather(2)">
                        <div class="holiday">
                            <div class="holiday_desc right"></div>
                        </div>
                        <div class="weather_desc">
                            <div class="weather_label_normal"></div>
                            <div class="temperature big_temperature"></div>
                            <div class="desc right"></div>
                        </div>
                        <div class="weather_remark">
                            <div class="weather_am left">
                                <div class="sun_up_icon"></div>
                                <div class="weather_time sunrise"></div>
                            </div>
                            <div class="weather_pm right">
                                <div class="sun_down_icon"></div>
                                <div class="weather_time sunset"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </li>
            <li>
                <div class="weather_for_oneday">
                    <div class="weather_caption">
                        <div class="date">
                            <div class="day" style="color:#ffffff">00</div>
                            <div class="week_day">周四</div>
                        </div>
                    </div>
                    <div class="weather_detail_list" onclick="showOneDayWeather(3)">
                        <div class="holiday">
                            <div class="holiday_desc right"></div>
                        </div>
                        <div class="weather_desc">
                            <div class="weather_label_normal"></div>
                            <div class="temperature big_temperature"></div>
                            <div class="desc right"></div>
                        </div>
                        <div class="weather_remark">
                            <div class="weather_am left">
                                <div class="sun_up_icon"></div>
                                <div class="weather_time sunrise"></div>
                            </div>
                            <div class="weather_pm right">
                                <div class="sun_down_icon"></div>
                                <div class="weather_time sunset"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </li>
            <li>
                <div class="weather_for_oneday">
                    <div class="weather_caption">
                        <div class="date">
                            <div class="day" style="color:#ffffff">00</div>
                            <div class="week_day">周五</div>
                        </div>
                    </div>
                    <div class="weather_detail_list" onclick="showOneDayWeather(4)">
                        <div class="holiday">
                            <div class="holiday_desc right"></div>
                        </div>
                        <div class="weather_desc">
                            <div class="weather_label_normal"></div>
                            <div class="temperature big_temperature"></div>
                            <div class="desc right"></div>
                        </div>
                        <div class="weather_remark">
                            <div class="weather_am left">
                                <div class="sun_up_icon"></div>
                                <div class="weather_time sunrise"></div>
                            </div>
                            <div class="weather_pm right">
                                <div class="sun_down_icon"></div>
                                <div class="weather_time sunset"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </li>
            <li>
                <div class="weather_for_oneday">
                    <div class="weather_caption">
                        <div class="date">
                            <div class="day" style="color:#ffffff">00</div>
                            <div class="week_day">周六</div>
                        </div>
                    </div>
                    <div class="weather_detail_list" onclick="showOneDayWeather(5)">
                        <div class="holiday">
                            <div class="holiday_desc right"></div>
                        </div>
                        <div class="weather_desc">
                            <div class="weather_label_normal"></div>
                            <div class="temperature big_temperature"></div>
                            <div class="desc right"></div>
                        </div>
                        <div class="weather_remark">
                            <div class="weather_am left">
                                <div class="sun_up_icon"></div>
                                <div class="weather_time sunrise"></div>
                            </div>
                            <div class="weather_pm right">
                                <div class="sun_down_icon"></div>
                                <div class="weather_time sunset"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </li>
            <li>
                <div class="weather_for_oneday">
                    <div class="weather_caption">
                        <div class="date">
                            <div class="day" style="color:#ffffff">00</div>
                            <div class="week_day">周日</div>
                        </div>
                    </div>
                    <div class="weather_detail_list" onclick="showOneDayWeather(6)">
                        <div class="holiday">
                            <div class="holiday_desc right"></div>
                        </div>
                        <div class="weather_desc">
                            <div class="weather_label_normal"></div>
                            <div class="temperature big_temperature"></div>
                            <div class="desc right"></div>
                        </div>
                        <div class="weather_remark">
                            <div class="weather_am left">
                                <div class="sun_up_icon"></div>
                                <div class="weather_time sunrise"></div>
                            </div>
                            <div class="weather_pm right">
                                <div class="sun_down_icon"></div>
                                <div class="weather_time sunset"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </li>

            </ul>
            </div>

            <div id="activity_slider">
            <ul>
            <li>
                <div class="activity_for_oneday">
                    <ul>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                    </ul>
                </div>
            </li>
            <li>
                <div class="activity_for_oneday">
                    <ul>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                    </ul>
                </div>
            </li>
            <li>
                <div class="activity_for_oneday">
                    <ul>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                    </ul>
                </div>
            </li>
            <li>
                <div class="activity_for_oneday">
                    <ul>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                    </ul>
                </div>
            </li>
            <li>
                <div class="activity_for_oneday">
                    <ul>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                    </ul>
                </div>
            </li>
            <li>
                <div class="activity_for_oneday">
                    <ul>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                    </ul>
                </div>
            </li>
            <li>
                <div class="activity_for_oneday">
                    <ul>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                        <li>
                            <div class="activity_time"></div>
                            <div class="activity_name"></div>
                        </li>
                    </ul>
                </div>
            </li>
            </ul>
            </div>


        </div>
    </div>
<div id="mask">

</div>
<script type="text/javascript">
    var weekday=new Array(7);
    weekday[0]="周日";
    weekday[1]="周一";
    weekday[2]="周二";
    weekday[3]="周三";
    weekday[4]="周四";
    weekday[5]="周五";
    weekday[6]="周六";


    var current_yyyy ="${year}";
    var current_mm = "${month}";
    var current_dd ="${day}";


    if (parseInt(current_mm) <10)
        current_mm = "0" + parseInt(current_mm);

    if (parseInt(current_dd) <10)
        current_dd = "0" + parseInt(current_dd);

    var selected_yyyy = current_yyyy;
    var selected_mm = current_mm;
    var selected_dd = current_dd;

    var systemId = "${systemCode}";
    var dayIndex = 0;
    var todayWeatherType = "";
    var todayWeatherDesc = "";
    var todayMinTemperature = "";
    var todayMaxTemperature = "";
    var todaySunriseTime = "";
    var todaySundownTime = "";

    var selected_first_day = current_dd;
    var selected_last_day = current_dd;

    first_date = new Date();
    last_date = new Date();
    var max_date = 31;
    resetMaxDate(selected_yyyy,selected_mm);
    resetDateRangeCrossMonth(current_yyyy,current_mm,current_dd);
    $("#selected_week").html((first_date.getMonth()+1)+"/"+first_date.getDate()+"-"+(last_date.getMonth()+1)+"/"+last_date.getDate());
    $("#month_slider > span").html(first_date.getFullYear()+"年"+(first_date.getMonth()+1)+"月"+first_date.getDate()+"日-"+last_date.getFullYear()+"年"+(last_date.getMonth()+1)+"月"+last_date.getDate()+"日");
    getListSchedule();


    $('#year_selector').on('click',function(event) {
                if($('.year_list').css('display')=='none')
                    $('.year_list').show();
                else
                    $('.year_list').hide();
                event.stopPropagation();
            }
    );

    $('.year_list > ul > li').on('mouseover',function(event){
        $('.year_list >ul >li').removeClass('current');
        $(event.target).addClass('current');
    });

    document.onclick = function(){
        $(".year_list, .month_list").hide();
    }

    $('.year_list > ul > li').on('click',function(event){

        $('#current_year').html($(event.target).html());
        $('.year_list').hide();
        var yyyy = $('#current_year').html();
        var mm = $('#current_month').html();

        if (yyyy != current_yyyy)
            selected_dd = 1;
        resetMaxDate(yyyy,mm);
        resetDateRangeCrossMonth(yyyy,mm,selected_dd);
        $("#selected_week").html((first_date.getMonth()+1)+"/"+first_date.getDate()+"-"+(last_date.getMonth()+1)+"/"+last_date.getDate());
        $("#month_slider > span").html(first_date.getFullYear()+"年"+(first_date.getMonth()+1)+"月"+first_date.getDate()+"日-"+last_date.getFullYear()+"年"+(last_date.getMonth()+1)+"月"+last_date.getDate()+"日");
        getListSchedule();
        event.stopPropagation();
    });


    $('#month_selector').on('click',function(event) {
                if($('.month_list').css('display')=='none')
                    $('.month_list').show();
                else
                    $('.month_list').hide();
                event.stopPropagation();
            }
    );

    $('.month_list > ul > li').on('mouseover',function(event){
        $('.month_list >ul >li').removeClass('current');
        $(event.target).addClass('current');
    });

    $('.month_list > ul > li').on('click',function(event){

        $('#current_month').html($(event.target).html());
        $('.month_list').hide();
        var yyyy = $('#current_year').html();
        var mm = $('#current_month').html();
        if (mm != current_mm)
            selected_dd = 1;

        resetMaxDate(yyyy,mm);
        resetDateRangeCrossMonth(yyyy,mm,selected_dd);
        $("#selected_week").html((first_date.getMonth()+1)+"/"+first_date.getDate()+"-"+(last_date.getMonth()+1)+"/"+last_date.getDate());
        $("#month_slider > span").html(first_date.getFullYear()+"年"+(first_date.getMonth()+1)+"月"+first_date.getDate()+"日-"+last_date.getFullYear()+"年"+(last_date.getMonth()+1)+"月"+last_date.getDate()+"日");
        getListSchedule();
        event.stopPropagation();
    });
</script>

