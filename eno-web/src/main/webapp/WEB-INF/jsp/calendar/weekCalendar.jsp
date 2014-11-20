<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglib.jsp" %>

<div id="content">
<div id="calendar_mode_switch">
    <ul>
        <li><a href="${pageContext.request.contextPath}/calendar/showDayCalendar?systemId=${systemCode}">日</a></li>
        <li class="current"><a href="#">周</a></li>
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
            <div class="arrow_left" onclick="javascript:gotoLastWeek()"></div>
            <span id="selected_week"></span>
            <div class="arrow_right" onclick="javascript:gotoNextWeek()"></div>
        </div>
        <div id="current_week" onclick="javascript:gotoCurrentWeek()"><span>本周</span></div>
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

<div id="week">
<div id="weather_slider">
    <div id="week_range" class="color_78CDD0"></div>
    <ul>
        <li>
            <div class="weather_for_oneday" onclick="showOneDayWeather(0)">
                <div class="weather_caption">
                    <div class="week_day left">周一</div>
                    <div class="holiday_desc right"></div>
                </div>
                <div class="weather_detail">
                    <div class="weather_label_normal"></div>
                    <div class="temperature"></div>
                    <div class="weather_desc right"></div>
                </div>
                <div class="weather_remark">
                    <div class="weather_am left">
                        <div class="sun_up_icon"></div>
                        <div class="sun_up_time"></div>
                    </div>
                    <div class="weather_pm right">
                        <div class="sun_down_icon"></div>
                        <div class="sun_down_time"></div>
                    </div>
                </div>
            </div>
        </li>

        <li>
            <div class="weather_for_oneday" onclick="showOneDayWeather(1)">
                <div class="weather_caption">
                    <div class="week_day left">周二</div>
                    <div class="holiday_desc right"></div>
                </div>
                <div class="weather_detail">
                    <div class="weather_label_normal"></div>
                    <div class="temperature"></div>
                    <div class="weather_desc right"></div>
                </div>
                <div class="weather_remark">
                    <div class="weather_am left">
                        <div class="sun_up_icon"></div>
                        <div class="sun_up_time"></div>
                    </div>
                    <div class="weather_pm right">
                        <div class="sun_down_icon"></div>
                        <div class="sun_down_time"></div>
                    </div>
                </div>
            </div>
        </li>

        <li>
            <div class="weather_for_oneday" onclick="showOneDayWeather(2)">
                <div class="weather_caption">
                    <div class="week_day left">周三</div>
                    <div class="holiday_desc right"></div>
                </div>
                <div class="weather_detail">
                    <div class="weather_label_normal"></div>
                    <div class="temperature"></div>
                    <div class="weather_desc right"></div>
                </div>
                <div class="weather_remark">
                    <div class="weather_am left">
                        <div class="sun_up_icon"></div>
                        <div class="sun_up_time"></div>
                    </div>
                    <div class="weather_pm right">
                        <div class="sun_down_icon"></div>
                        <div class="sun_down_time"></div>
                    </div>
                </div>
            </div>
        </li>

        <li>
            <div class="weather_for_oneday" onclick="showOneDayWeather(3)">
                <div class="weather_caption">
                    <div class="week_day left">周四</div>
                    <div class="holiday_desc right"></div>
                </div>
                <div class="weather_detail">
                    <div class="weather_label_normal"></div>
                    <div class="temperature"></div>
                    <div class="weather_desc right"></div>
                </div>
                <div class="weather_remark">
                    <div class="weather_am left">
                        <div class="sun_up_icon"></div>
                        <div class="sun_up_time"></div>
                    </div>
                    <div class="weather_pm right">
                        <div class="sun_down_icon"></div>
                        <div class="sun_down_time"></div>
                    </div>
                </div>
            </div>
        </li>

        <li>
            <div class="weather_for_oneday" onclick="showOneDayWeather(4)">
                <div class="weather_caption">
                    <div class="week_day left">周五</div>
                    <div class="holiday_desc right"></div>
                </div>
                <div class="weather_detail">
                    <div class="weather_label_normal"></div>
                    <div class="temperature"></div>
                    <div class="weather_desc right"></div>
                </div>
                <div class="weather_remark">
                    <div class="weather_am left">
                        <div class="sun_up_icon"></div>
                        <div class="sun_up_time"></div>
                    </div>
                    <div class="weather_pm right">
                        <div class="sun_down_icon"></div>
                        <div class="sun_down_time"></div>
                    </div>
                </div>
            </div>
        </li>

        <li>
            <div class="weather_for_oneday" onclick="showOneDayWeather(5)">
                <div class="weather_caption">
                    <div class="week_day left">周六</div>
                    <div class="holiday_desc right"></div>
                </div>
                <div class="weather_detail">
                    <div class="weather_label_normal"></div>
                    <div class="temperature"></div>
                    <div class="weather_desc right"></div>
                </div>
                <div class="weather_remark">
                    <div class="weather_am left">
                        <div class="sun_up_icon"></div>
                        <div class="sun_up_time"></div>
                    </div>
                    <div class="weather_pm right">
                        <div class="sun_down_icon"></div>
                        <div class="sun_down_time"></div>
                    </div>
                </div>
            </div>
        </li>

        <li>
            <div class="weather_for_oneday" onclick="showOneDayWeather(6)">
                <div class="weather_caption">
                    <div class="week_day left">周日</div>
                    <div class="holiday_desc right"></div>
                </div>
                <div class="weather_detail">
                    <div class="weather_label_normal"></div>
                    <div class="temperature"></div>
                    <div class="weather_desc right"></div>
                </div>
                <div class="weather_remark">
                    <div class="weather_am left">
                        <div class="sun_up_icon"></div>
                        <div class="sun_up_time"></div>
                    </div>
                    <div class="weather_pm right">
                        <div class="sun_down_icon"></div>
                        <div class="sun_down_time"></div>
                    </div>
                </div>
            </div>
        </li>

    </ul>
</div>
<div id="active_slider">
    <div id="active_whole_week" class="color_78CDD0" onclick="showOneWeekActivity()"></div>
    <ul>
        <li>
            <div class="mode_for_oneday">
                <div class="one_mode" style="border-left:5px solid #99CCFF"><div class="mode_icon LSPUB"></div><span class="mode_name"></span></div>
                <div class="one_mode" style="border-left:5px solid #FF9900"><div class="mode_icon LSN"></div><span class="mode_name"></span></div>
                <div class="one_mode" style="border-left:5px solid #FF3300"><div class="mode_icon HVAC"></div><div class="mode_des"><p class="mode_name"></p></div></div>
            </div>
        </li>
        <li>
            <div class="mode_for_oneday">
                <div class="one_mode" style="border-left:5px solid #99CCFF"><div class="mode_icon LSPUB"></div><span class="mode_name"></span></div>
                <div class="one_mode" style="border-left:5px solid #FF9900"><div class="mode_icon LSN"></div><span class="mode_name"></span></div>
                <div class="one_mode" style="border-left:5px solid #FF3300"><div class="mode_icon HVAC"></div><div class="mode_des"><p class="mode_name"></p></div></div>
            </div>
        </li>
        <li>
            <div class="mode_for_oneday">
                <div class="one_mode" style="border-left:5px solid #99CCFF"><div class="mode_icon LSPUB"></div><span class="mode_name"></span></div>
                <div class="one_mode" style="border-left:5px solid #FF9900"><div class="mode_icon LSN"></div><span class="mode_name"></span></div>
                <div class="one_mode" style="border-left:5px solid #FF3300"><div class="mode_icon HVAC"></div><div class="mode_des"><p class="mode_name"></p></div></div>
            </div>
        </li>
        <li>
            <div class="mode_for_oneday">
                <div class="one_mode" style="border-left:5px solid #99CCFF"><div class="mode_icon LSPUB"></div><span class="mode_name"></span></div>
                <div class="one_mode" style="border-left:5px solid #FF9900"><div class="mode_icon LSN"></div><span class="mode_name"></span></div>
                <div class="one_mode" style="border-left:5px solid #FF3300"><div class="mode_icon HVAC"></div><div class="mode_des"><p class="mode_name"></p></div></div>
            </div>
        </li>
        <li>
            <div class="mode_for_oneday">
                <div class="one_mode" style="border-left:5px solid #99CCFF"><div class="mode_icon LSPUB"></div><span class="mode_name"></span></div>
                <div class="one_mode" style="border-left:5px solid #FF9900"><div class="mode_icon LSN"></div><span class="mode_name"></span></div>
                <div class="one_mode" style="border-left:5px solid #FF3300"><div class="mode_icon HVAC"></div><div class="mode_des"><p class="mode_name"></p></div></div>
            </div>
        </li>
        <li>
            <div class="mode_for_oneday">
                <div class="one_mode" style="border-left:5px solid #99CCFF"><div class="mode_icon LSPUB"></div><span class="mode_name"></span></div>
                <div class="one_mode" style="border-left:5px solid #FF9900"><div class="mode_icon LSN"></div><span class="mode_name"></span></div>
                <div class="one_mode" style="border-left:5px solid #FF3300"><div class="mode_icon HVAC"></div><div class="mode_des"><p class="mode_name"></p></div></div>
            </div>
        </li>
        <li>
            <div class="mode_for_oneday">
                <div class="one_mode" style="border-left:5px solid #99CCFF"><div class="mode_icon LSPUB"></div><span class="mode_name"></span></div>
                <div class="one_mode" style="border-left:5px solid #FF9900"><div class="mode_icon LSN"></div><span class="mode_name"></span></div>
                <div class="one_mode" style="border-left:5px solid #FF3300"><div class="mode_icon HVAC"></div><div class="mode_des"><p class="mode_name"></p></div></div>
            </div>
        </li>
    </ul>

</div>
<%--<div id="active_slider">--%>
    <%--<div id="active_whole_week" class="color_78CDD0" onclick="showOneWeekActivity()"></div>--%>
    <%--<ul>--%>
        <%--<li>--%>
            <%--<div class="mode_for_oneday">--%>
                <%--<div class="one_mode" style="border-left:5px solid #99CCFF"><div class="mode_icon day_lighting"></div><span class="mode_name"></span></div>--%>
                <%--<div class="one_mode" style="border-left:5px solid #FF9900"><div class="mode_icon night_lighting"></div><span class="mode_name"></span></div>--%>
                <%--<div class="one_mode" style="border-left:5px solid #FF3300"><div class="mode_icon nuantong"></div><div class="mode_des"><p class="mode_name"></p></div></div>--%>
            <%--</div>--%>
        <%--</li>--%>
        <%--<li>--%>
            <%--<div class="mode_for_oneday">--%>
                <%--<div class="one_mode" style="border-left:5px solid #99CCFF"><div class="mode_icon day_lighting"></div><span class="mode_name"></span></div>--%>
                <%--<div class="one_mode" style="border-left:5px solid #FF9900"><div class="mode_icon night_lighting"></div><span class="mode_name"></span></div>--%>
                <%--<div class="one_mode" style="border-left:5px solid #FF3300"><div class="mode_icon nuantong"></div><div class="mode_des"><p class="mode_name"></p></div></div>--%>
            <%--</div>--%>
        <%--</li>--%>
        <%--<li>--%>
            <%--<div class="mode_for_oneday">--%>
                <%--<div class="one_mode" style="border-left:5px solid #99CCFF"><div class="mode_icon day_lighting"></div><span class="mode_name"></span></div>--%>
                <%--<div class="one_mode" style="border-left:5px solid #FF9900"><div class="mode_icon night_lighting"></div><span class="mode_name"></span></div>--%>
                <%--<div class="one_mode" style="border-left:5px solid #FF3300"><div class="mode_icon nuantong"></div><div class="mode_des"><p class="mode_name"></p></div></div>--%>
            <%--</div>--%>
        <%--</li>--%>
        <%--<li>--%>
            <%--<div class="mode_for_oneday">--%>
                <%--<div class="one_mode" style="border-left:5px solid #99CCFF"><div class="mode_icon day_lighting"></div><span class="mode_name"></span></div>--%>
                <%--<div class="one_mode" style="border-left:5px solid #FF9900"><div class="mode_icon night_lighting"></div><span class="mode_name"></span></div>--%>
                <%--<div class="one_mode" style="border-left:5px solid #FF3300"><div class="mode_icon nuantong"></div><div class="mode_des"><p class="mode_name"></p></div></div>--%>
            <%--</div>--%>
        <%--</li>--%>
        <%--<li>--%>
            <%--<div class="mode_for_oneday">--%>
                <%--<div class="one_mode" style="border-left:5px solid #99CCFF"><div class="mode_icon day_lighting"></div><span class="mode_name"></span></div>--%>
                <%--<div class="one_mode" style="border-left:5px solid #FF9900"><div class="mode_icon night_lighting"></div><span class="mode_name"></span></div>--%>
                <%--<div class="one_mode" style="border-left:5px solid #FF3300"><div class="mode_icon nuantong"></div><div class="mode_des"><p class="mode_name"></p></div></div>--%>
            <%--</div>--%>
        <%--</li>--%>
        <%--<li>--%>
            <%--<div class="mode_for_oneday">--%>
                <%--<div class="one_mode" style="border-left:5px solid #99CCFF"><div class="mode_icon day_lighting"></div><span class="mode_name"></span></div>--%>
                <%--<div class="one_mode" style="border-left:5px solid #FF9900"><div class="mode_icon night_lighting"></div><span class="mode_name"></span></div>--%>
                <%--<div class="one_mode" style="border-left:5px solid #FF3300"><div class="mode_icon nuantong"></div><div class="mode_des"><p class="mode_name"></p></div></div>--%>
            <%--</div>--%>
        <%--</li>--%>
        <%--<li>--%>
            <%--<div class="mode_for_oneday">--%>
                <%--<div class="one_mode" style="border-left:5px solid #99CCFF"><div class="mode_icon day_lighting"></div><span class="mode_name"></span></div>--%>
                <%--<div class="one_mode" style="border-left:5px solid #FF9900"><div class="mode_icon night_lighting"></div><span class="mode_name"></span></div>--%>
                <%--<div class="one_mode" style="border-left:5px solid #FF3300"><div class="mode_icon nuantong"></div><div class="mode_des"><p class="mode_name"></p></div></div>--%>
            <%--</div>--%>
        <%--</li>--%>
    <%--</ul>--%>

<%--</div>--%>

<div id="timeline">
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
    <div class="current_clock_time">${hour}:${minute}</div>
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

<div id="mode_group">
    <div class="clock_line_00"></div>
    <div class="clock_line_04"></div>
    <div class="clock_line_08"></div>
    <div class="clock_line_12"></div>
    <div class="clock_line_16"></div>
    <div class="clock_line_20"></div>
    <div class="clock_line_24"></div>
    <ul>
        <li id="mode_monday">
            <div class="night_left" style="width:320px"></div>
            <div class="monday mode_group_line">
            </div>
            <div class="night_right" style="width:320px"></div>
        </li>
        <li id="mode_tuesday">
            <div class="night_left" style="width:325px"></div>
            <div class="tuesday mode_group_line">
            </div>
            <div class="night_right" style="width:325px"></div>
        </li>
        <li id="mode_wedensday">
            <div class="night_left" style="width:330px"></div>
            <div class="wedensday mode_group_line">
            </div>
            <div class="night_right" style="width:330px"></div>
        </li>
        <li id="mode_thursday">
            <div class="night_left" style="width:335px"></div>
            <div class="thursday mode_group_line">
            </div>
            <div class="night_right" style="width:335px"></div>
        </li>
        <li id="mode_friday">
            <div class="night_left" style="width:340px"></div>
            <div class="friday mode_group_line">
            </div>
            <div class="night_right" style="width:340px"></div>
        </li>
        <li id="mode_saturday">
            <div class="night_left" style="width:345px"></div>
            <div class="saturday mode_group_line">
            </div>
            <div class="night_right" style="width:345px"></div>
        </li>
        <li id="mode_sunday">
            <div class="night_left" style="width:350px"></div>
            <div class="sunday mode_group_line">
            </div>
            <div class="night_right" style="width:350px"></div>
        </li>
    </ul>
</div>
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

    var systemId ="${systemCode}";

    var current_yyyy ="${year}";
    var current_mm = "${month}";
    var current_dd ="${day}";


    if (parseInt(current_mm) <10)
        current_mm = "0" + parseInt(current_mm);

    if (parseInt(current_dd) <10)
        current_dd = "0" + parseInt(current_dd);

    resetWeekCurrentClockLine('${hour}','${minute}');

    var dayIndex = 0;
    var todayWeatherType = "";
    var todayWeatherDesc = "";
    var todayMinTemperature = "";
    var todayMaxTemperature = "";
    var todaySunriseTime = "";
    var todaySundownTime = "";

    var selected_yyyy = current_yyyy;
    var selected_mm = current_mm;
    var selected_dd = current_dd;
    var selected_first_day = current_dd;
    var selected_last_day = current_dd;
    first_date = new Date();
    last_date = new Date();
    var max_date = 31;
    resetMaxDate(selected_yyyy,selected_mm);
    resetDateRangeCrossMonth(current_yyyy,current_mm,current_dd);
    $("#selected_week").html((first_date.getMonth()+1)+"/"+first_date.getDate()+"-"+(last_date.getMonth()+1)+"/"+last_date.getDate());
    $("#week_range").html((first_date.getMonth()+1)+"."+first_date.getDate()+"-"+(last_date.getMonth()+1)+"."+last_date.getDate());
    clearWeekView();
    highLightCurrentDayInWeekView();
    getWeekModeApply();
    getWeekActivity();
    getWeekWeather();

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
        if (yyyy != current_yyyy)
            selected_dd = 1;
        resetMaxDate(yyyy,mm);
        resetDateRangeCrossMonth(yyyy,mm,selected_dd);
        $("#selected_week").html((first_date.getMonth()+1)+"/"+first_date.getDate()+"-"+(last_date.getMonth()+1)+"/"+last_date.getDate());
        $("#week_range").html((first_date.getMonth()+1)+"."+first_date.getDate()+"-"+(last_date.getMonth()+1)+"."+last_date.getDate());
        clearWeekView();
        highLightCurrentDayInWeekView();
        getWeekModeApply();
        getWeekActivity();
        getWeekWeather();
        event.stopPropagation();
    });

    document.onclick = function(){
        $(".year_list, .month_list").hide();
    }

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
        var mm = parseInt($('#current_month').html());
        if (mm < 10)
            mm = "0"+mm;

        if (mm != current_mm)
            selected_dd = 1;

        resetMaxDate(yyyy,mm);
        resetDateRangeCrossMonth(yyyy,mm,selected_dd);
        $("#selected_week").html((first_date.getMonth()+1)+"/"+first_date.getDate()+"-"+(last_date.getMonth()+1)+"/"+last_date.getDate());
        $("#week_range").html((first_date.getMonth()+1)+"."+first_date.getDate()+"-"+(last_date.getMonth()+1)+"."+last_date.getDate());
        clearWeekView();
        highLightCurrentDayInWeekView();
        getWeekModeApply();
        getWeekActivity();
        getWeekWeather();
        event.stopPropagation();
    });

</script>  
