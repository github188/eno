<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglib.jsp" %>

<div id="content">
<div id="calendar_mode_switch">
    <ul>
        <li class="current"><a href="#">日</a></li>
        <li><a href="${pageContext.request.contextPath}/calendar/showWeekCalendar?systemId=${systemCode}">周</a></li>
        <li><a href="${pageContext.request.contextPath}/calendar/showMonthCalendar?systemId=${systemCode}">月</a></li>
        <li><a href="${pageContext.request.contextPath}/calendar/showScheduler?systemId=${systemCode}">日程表</a></li>
    </ul>
</div>
<div id="actionbar">
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
        <div id="month_selector"><span id="current_month">${month}月</span><div class="arrow_down right"></div></div>
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
        <div id="day_selector">
            <div class="arrow_left" onclick="javascript:gotoLastDate()"></div>
           <span id="selected_day"></span>
           <div class="arrow_right" onclick="javascript:gotoNextDate()"></div>
        </div>
        <div id="today" onclick="javascript:gotoCurrentDate()"><span id="current_day">本日</span></div>
    </div>

    <div class="right">
        <div id="create_event" onclick="javascript:showChoiceEventTypeDialog();">创建事件
            <%--<div class="cross right"></div>--%>
        </div>
        <c:if test="${systemCode=='HVAC'}">
        <ul style="width:630px">
            <li style="width:90px"><div class="square color_F9AB35"></div><span class="v_top">冷源</span></li>
            <li style="width:90px"><div class="square color_EF9E87"></div><span class="v_top">热源</span></li>
            <li style="width:90px"><div class="square color_459FE0"></div><span  class="v_top">新风机组</span></li>
            <li style="width:90px"><div class="square color_5CC84E"></div><span  class="v_top">组合空调</span></li>
            <li style="width:90px"><div class="square color_E66E4C"></div><span  class="v_top">吊装空调</span></li>
            <li style="width:90px"><div class="square color_FAC434"></div><span  class="v_top">通风机组</span></li>
        </ul>
        </c:if>
    </div>
</div>

<div id="day">
    <div id="day_timeline">
        <div class="time_slider">
            <div id="hour_00" class="hour_time">00:00</div>
            <div id="hour_02" class="hour_time">02:00</div>
            <div id="hour_04" class="hour_time">04:00</div>
            <div id="hour_06" class="hour_time">06:00</div>
            <div id="hour_08" class="hour_time">08:00</div>
            <div id="hour_10" class="hour_time">10:00</div>
            <div id="hour_12" class="hour_time">12:00</div>
            <div id="hour_14" class="hour_time">14:00</div>
            <div id="hour_16" class="hour_time">16:00</div>
            <div id="hour_18" class="hour_time">18:00</div>
            <div id="hour_20" class="hour_time">20:00</div>
            <div id="hour_22" class="hour_time">22:00</div>
            <div id="hour_24" class="hour_time">24:00</div>
        </div>
        <div class="current_clock">
            <div class="current_clock_time"></div>
            <div class="red_arrow_down"></div>
        </div>
        <div id="now_time_line"></div>
        <div class="line_slider">
            <div class="left_gray_line"></div>
            <div id="hour_line_00" class="clock_line">
                <div class="big_hour_dot"></div>
                <div class="gray_line"></div>
                <div class="small_hour_dot"></div>
                <div class="gray_line"></div>
            </div>
            <div id="hour_line_02" class="clock_line">
                <div class="big_hour_dot"></div>
                <div class="gray_line"></div>
                <div class="small_hour_dot"></div>
                <div class="gray_line"></div>
            </div>
            <div id="hour_line_04" class="clock_line">
                <div class="big_hour_dot"></div>
                <div class="gray_line"></div>
                <div class="small_hour_dot"></div>
                <div class="gray_line"></div>
            </div>
            <div id="hour_line_06" class="clock_line">
                <div class="big_hour_dot"></div>
                <div class="gray_line"></div>
                <div class="small_hour_dot"></div>
                <div class="gray_line"></div>
            </div>
            <div id="hour_line_08" class="clock_line">
                <div class="big_hour_dot"></div>
                <div class="gray_line"></div>
                <div class="small_hour_dot"></div>
                <div class="gray_line"></div>
            </div>
            <div id="hour_line_10" class="clock_line">
                <div class="big_hour_dot"></div>
                <div class="gray_line"></div>
                <div class="small_hour_dot"></div>
                <div class="gray_line"></div>
            </div>
            <div id="hour_line_12" class="clock_line">
                <div class="big_hour_dot"></div>
                <div class="gray_line"></div>
                <div class="small_hour_dot"></div>
                <div class="gray_line"></div>
            </div>
            <div id="hour_line_14" class="clock_line">
                <div class="big_hour_dot"></div>
                <div class="gray_line"></div>
                <div class="small_hour_dot"></div>
                <div class="gray_line"></div>
            </div>
            <div id="hour_line_16" class="clock_line">
                <div class="big_hour_dot"></div>
                <div class="gray_line"></div>
                <div class="small_hour_dot"></div>
                <div class="gray_line"></div>
            </div>
            <div id="hour_line_18" class="clock_line">
                <div class="big_hour_dot"></div>
                <div class="gray_line"></div>
                <div class="small_hour_dot"></div>
                <div class="gray_line"></div>
            </div>
            <div id="hour_line_20" class="clock_line">
                <div class="big_hour_dot"></div>
                <div class="gray_line"></div>
                <div class="small_hour_dot"></div>
                <div class="gray_line"></div>
            </div>
            <div id="hour_line_22" class="clock_line">
                <div class="big_hour_dot"></div>
                <div class="gray_line"></div>
                <div class="small_hour_dot"></div>
                <div class="gray_line"></div>
            </div>
            <div id="hour_line_24" class="clock_line">
                <div class="big_hour_dot"></div>
                <div class="right_gray_line"></div>
            </div>
        </div>
    </div>

    <div id="today_weather" onclick="javascript:showOneDayWeather()">
        <span></span>
    </div>
    <div id="today_mode" onclick="javascript:showOneDayDetailMode()">
        <span></span>
    </div>
    <div id="today_activity" onclick="javascript:showOneDayActivity()">
        <span></span>
    </div>

    <div id="mode_view">
        <div class="clock_line_00"></div>
        <div class="clock_line_04"></div>
        <div class="clock_line_08"></div>
        <div class="clock_line_12"></div>
        <div class="clock_line_16"></div>
        <div class="clock_line_20"></div>
        <div class="clock_line_24"></div>
        <div class="night_left" style="width:320px"></div>
        <div class="mode_list_for_oneday">
            <ul>




            </ul>
        </div>
        <div class="night_right" style="width:320px"></div>
    </div>
</div>
</div>

<div id="mask">

</div>
<div id="mode_toast">
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


    resetDayCurrentClockLine('${hour}','${minute}');
    resetDaySunUpClockLine('${sunriseH}','${sunriseM}');
    resetDaySunDownClockLine('${sunsetH}','${sunsetM}');


    var todayWeatherType = "";
    var todayWeatherDesc = "";
    var todayMinTemperature = "";
    var todayMaxTemperature = "";
    var todaySunriseTime = "";
    var todaySundownTime = "";

    var systemId = "${systemCode}";
    var selected_yyyy = current_yyyy;
    var selected_mm = current_mm;
    var selected_dd = current_dd;
    selectDate(selected_yyyy,selected_mm,selected_dd);
    var max_date = 31;
    resetMaxDate(selected_yyyy,selected_mm);

    getDayModeApply();
    getDayActivity();
    getDayWeather();

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

    $('.year_list > ul > li').on('click',function(event){

        $('#current_year').html($(event.target).html());
        $('.year_list').hide();
        var yyyy = $('#current_year').html();
        var mm = $('#current_month').html();
        var tmm = mm.slice(0,mm.length-1);
        resetMaxDate(yyyy,tmm);
        gotoFirstDate(yyyy,tmm);
        clearDayView();
        getDayModeApply();
        getDayActivity();
        getDayWeather();
        event.stopPropagation();
    });


    $('#month_selector').on('click',function(event){
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

    document.onclick = function(){
        $(".year_list, .month_list").hide();
    }

    $('.month_list > ul > li').on('click',function(event){

        $('#current_month').html($(event.target).html());
        $('.month_list').hide();
        var yyyy = $('#current_year').html();
        var mm = parseInt($('#current_month').html());
        if (mm < 10)
            mm = "0"+mm;
        resetMaxDate(yyyy,mm);
        gotoFirstDate(yyyy,mm);
        clearDayView();
        getDayModeApply();
        getDayActivity();
        getDayWeather();
        event.stopPropagation();
    });

</script>