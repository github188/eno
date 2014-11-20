<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/taglib.jsp" %>

<div id="content">
    <div id="calendar_mode_switch">
        <ul>
            <li><a href="${pageContext.request.contextPath}/calendar/showDayCalendar?systemId=${systemCode}">日</a></li>
            <li><a href="${pageContext.request.contextPath}/calendar/showWeekCalendar?systemId=${systemCode}">周</a></li>
            <li class="current"><a href="#">月</a></li>
            <li><a href="${pageContext.request.contextPath}/calendar/showScheduler?systemId=${systemCode}">日程表</a></li>
        </ul>
    </div>
    <div class="actionbar">
        <div class="left">
            <div id="year_selector"><span id="current_year">${year}</span>

                <div class="arrow_down right"></div>
            </div>
            <div class="year_list">
                <ul>
                    <li>${year-3}</li>
                    <li>${year-2}</li>
                    <li>${year-1}</li>
                    <li class="current">${year}</li>
                    <li>${year+1}</li>
                    <li>${year+2}</li>
                </ul>
            </div>
            <div id="month_selector"><span id="current_month">${month}</span>

                <div class="arrow_down right"></div>
            </div>
            <div class="month_list">
                <ul>
                    <li <c:if test="${month==1}">class="current"</c:if>>1月</li>
                    <li
                            <c:if test="${month==2}">class="current"</c:if> >2月
                    </li>
                    <li
                            <c:if test="${month==3}">class="current"</c:if> >3月
                    </li>
                    <li
                            <c:if test="${month==4}">class="current"</c:if> >4月
                    </li>
                    <li
                            <c:if test="${month==5}">class="current"</c:if> >5月
                    </li>
                    <li
                            <c:if test="${month==6}">class="current"</c:if> >6月
                    </li>
                    <li
                            <c:if test="${month==7}">class="current"</c:if> >7月
                    </li>
                    <li
                            <c:if test="${month==8}">class="current"</c:if> >8月
                    </li>
                    <li
                            <c:if test="${month==9}">class="current"</c:if> >9月
                    </li>
                    <li
                            <c:if test="${month==10}">class="current"</c:if> >10月
                    </li>
                    <li
                            <c:if test="${month==11}">class="current"</c:if> >11月
                    </li>
                    <li
                            <c:if test="${month==12}">class="current"</c:if> >12月
                    </li>
                </ul>
            </div>
            <div id="goto_current_month" onclick="javascript:gotoCurrentMonth()"><span>本月</span></div>

        </div>

        <div class="right">
            <div id="create_event" onclick="javascript:showChoiceEventTypeDialog();">创建事件
                <%--<div class="cross right"></div>--%>
            </div>
            <ul style="width:280px">
                <li style="width:80px">
                    <div class="square color_5CC8E4"></div>
                    <span class="v_top">活动</span></li>
                <li style="width:80px">
                    <div class="square color_459FE0"></div>
                    <span class="v_top">模式</span></li>

            </ul>
        </div>
    </div>

    <div id="month">

        <div id="month_slider">
            <div class="circle_arrow_left" onclick="javascript:lastMonth()"></div>
            <span>${year}年${month}月</span>

            <div class="circle_arrow_right" onclick="javascript:nextMonth()"></div>
        </div>
        <div id="sunday_to_saturday">
            <ul>
                <li>星期日</li>
                <li>星期一</li>
                <li>星期二</li>
                <li>星期三</li>
                <li>星期四</li>
                <li>星期五</li>
                <li>星期六</li>
            </ul>
        </div>

        <div id="day_grid">
            <table>
                <tbody>
                <tr>
                    <td id="day1" class="current_month_day">
                        <div class="date_holiday">
                            <div class="date"></div>
                            <div class="holiday"></div>
                        </div>
                        <div class="weather_list" onclick="showOneDayWeather(1)">
                            <div class="weather_icon"></div>
                            <div class="weather_desc_temperature">
                                <div class="weather_desc"></div>
                                <div class="temperature"></div>
                            </div>
                            <div class="sun_up_sun_down">
                                <div class="sun_up">
                                    <div class="sun_up_icon"></div>
                                    <div class="sun_up_time"></div>
                                </div>
                                <div class="sun_down">
                                    <div class="sun_down_icon"></div>
                                    <div class="sun_down_time"></div>
                                </div>
                            </div>
                        </div>
                        <div class="mode_activity">
                            <div class="mode"><span class="mode_name"></span></div>
                            <div class="activity"><span class="activity_name"></span></div>
                        </div>
                    </td>
                    <td id="day2" class="current_month_day">
                        <div class="date_holiday">
                            <div class="date"></div>
                            <div class="holiday"></div>
                        </div>
                        <div class="weather_list" onclick="showOneDayWeather(2)">
                            <div class="weather_icon "></div>
                            <div class="weather_desc_temperature">
                                <div class="weather_desc"></div>
                                <div class="temperature"></div>
                            </div>
                            <div class="sun_up_sun_down">
                                <div class="sun_up">
                                    <div class="sun_up_icon"></div>
                                    <div class="sun_up_time"></div>
                                </div>
                                <div class="sun_down">
                                    <div class="sun_down_icon"></div>
                                    <div class="sun_down_time"></div>
                                </div>
                            </div>
                        </div>
                        <div class="mode_activity">
                            <div class="mode"><span class="mode_name"></span></div>
                            <div class="activity"><span class="activity_name"></span></div>
                        </div>
                    </td>

                    <td id="day3" class="current_month_day">
                        <div class="date_holiday">
                            <div class="date"></div>
                            <div class="holiday"></div>
                        </div>
                        <div class="weather_list" onclick="showOneDayWeather(3)">
                            <div class="weather_icon "></div>
                            <div class="weather_desc_temperature">
                                <div class="weather_desc"></div>
                                <div class="temperature"></div>
                            </div>
                            <div class="sun_up_sun_down">
                                <div class="sun_up">
                                    <div class="sun_up_icon"></div>
                                    <div class="sun_up_time"></div>
                                </div>
                                <div class="sun_down">
                                    <div class="sun_down_icon"></div>
                                    <div class="sun_down_time"></div>
                                </div>
                            </div>
                        </div>
                        <div class="mode_activity">
                            <div class="mode"><span class="mode_name"></span></div>
                            <div class="activity"><span class="activity_name"></span></div>
                        </div>
                    </td>
                    <td id="day4" class="current_month_day">
                        <div class="date_holiday">
                            <div class="date"></div>
                            <div class="holiday"></div>
                        </div>
                        <div class="weather_list" onclick="showOneDayWeather(4)">
                            <div class="weather_icon "></div>
                            <div class="weather_desc_temperature">
                                <div class="weather_desc"></div>
                                <div class="temperature"></div>
                            </div>
                            <div class="sun_up_sun_down">
                                <div class="sun_up">
                                    <div class="sun_up_icon"></div>
                                    <div class="sun_up_time"></div>
                                </div>
                                <div class="sun_down">
                                    <div class="sun_down_icon"></div>
                                    <div class="sun_down_time"></div>
                                </div>
                            </div>
                        </div>
                        <div class="mode_activity">
                            <div class="mode"><span class="mode_name"></span></div>
                            <div class="activity"><span class="activity_name"></span></div>
                        </div>
                    </td>

                    <td id="day5" class="current_month_day">
                        <div class="date_holiday">
                            <div class="date"></div>
                            <div class="holiday"></div>
                        </div>
                        <div class="weather_list" onclick="showOneDayWeather(5)">
                            <div class="weather_icon "></div>
                            <div class="weather_desc_temperature">
                                <div class="weather_desc"></div>
                                <div class="temperature"></div>
                            </div>
                            <div class="sun_up_sun_down">
                                <div class="sun_up">
                                    <div class="sun_up_icon"></div>
                                    <div class="sun_up_time"></div>
                                </div>
                                <div class="sun_down">
                                    <div class="sun_down_icon"></div>
                                    <div class="sun_down_time"></div>
                                </div>
                            </div>
                        </div>
                        <div class="mode_activity">
                            <div class="mode"><span class="mode_name"></span></div>
                            <div class="activity"><span class="activity_name"></span></div>
                        </div>
                    </td>
                    <td id="day6" class="current_month_day">
                        <div class="date_holiday">
                            <div class="date"></div>
                            <div class="holiday"></div>
                        </div>
                        <div class="weather_list" onclick="showOneDayWeather(6)">
                            <div class="weather_icon "></div>
                            <div class="weather_desc_temperature">
                                <div class="weather_desc"></div>
                                <div class="temperature"></div>
                            </div>
                            <div class="sun_up_sun_down">
                                <div class="sun_up">
                                    <div class="sun_up_icon"></div>
                                    <div class="sun_up_time"></div>
                                </div>
                                <div class="sun_down">
                                    <div class="sun_down_icon"></div>
                                    <div class="sun_down_time"></div>
                                </div>
                            </div>
                        </div>
                        <div class="mode_activity">
                            <div class="mode"><span class="mode_name"></span></div>
                            <div class="activity"><span class="activity_name"></span></div>
                        </div>
                    </td>
                    <td id="day7" class="current_month_day">
                        <div class="date_holiday">
                            <div class="date"></div>
                            <div class="holiday"></div>
                        </div>
                        <div class="weather_list" onclick="showOneDayWeather(7)">
                            <div class="weather_icon "></div>
                            <div class="weather_desc_temperature">
                                <div class="weather_desc"></div>
                                <div class="temperature"></div>
                            </div>
                            <div class="sun_up_sun_down">
                                <div class="sun_up">
                                    <div class="sun_up_icon"></div>
                                    <div class="sun_up_time"></div>
                                </div>
                                <div class="sun_down">
                                    <div class="sun_down_icon"></div>
                                    <div class="sun_down_time"></div>
                                </div>
                            </div>
                        </div>
                        <div class="mode_activity">
                            <div class="mode"><span class="mode_name"></span></div>
                            <div class="activity"><span class="activity_name"></span></div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td id="day8" class="current_month_day">
                        <div class="date_holiday">
                            <div class="date"></div>
                            <div class="holiday"></div>
                        </div>
                        <div class="weather_list" onclick="showOneDayWeather(8)">
                            <div class="weather_icon "></div>
                            <div class="weather_desc_temperature">
                                <div class="weather_desc"></div>
                                <div class="temperature"></div>
                            </div>
                            <div class="sun_up_sun_down">
                                <div class="sun_up">
                                    <div class="sun_up_icon"></div>
                                    <div class="sun_up_time"></div>
                                </div>
                                <div class="sun_down">
                                    <div class="sun_down_icon"></div>
                                    <div class="sun_down_time"></div>
                                </div>
                            </div>
                        </div>
                        <div class="mode_activity">
                            <div class="mode"><span class="mode_name"></span></div>
                            <div class="activity"><span class="activity_name"></span></div>
                        </div>
                    </td>
                    <td id="day9" class="current_month_day">
                        <div class="date_holiday">
                            <div class="date"></div>
                            <div class="holiday"></div>
                        </div>
                        <div class="weather_list" onclick="showOneDayWeather(9)">
                            <div class="weather_icon "></div>
                            <div class="weather_desc_temperature">
                                <div class="weather_desc"></div>
                                <div class="temperature"></div>
                            </div>
                            <div class="sun_up_sun_down">
                                <div class="sun_up">
                                    <div class="sun_up_icon"></div>
                                    <div class="sun_up_time"></div>
                                </div>
                                <div class="sun_down">
                                    <div class="sun_down_icon"></div>
                                    <div class="sun_down_time"></div>
                                </div>
                            </div>
                        </div>
                        <div class="mode_activity">
                            <div class="mode"><span class="mode_name"></span></div>
                            <div class="activity"><span class="activity_name"></span></div>
                        </div>
                    </td>
                    <td id="day10" class="current_month_day">
                        <div class="date_holiday">
                            <div class="date"></div>
                            <div class="holiday"></div>
                        </div>
                        <div class="weather_list" onclick="showOneDayWeather(10)">
                            <div class="weather_icon "></div>
                            <div class="weather_desc_temperature">
                                <div class="weather_desc"></div>
                                <div class="temperature"></div>
                            </div>
                            <div class="sun_up_sun_down">
                                <div class="sun_up">
                                    <div class="sun_up_icon"></div>
                                    <div class="sun_up_time"></div>
                                </div>
                                <div class="sun_down">
                                    <div class="sun_down_icon"></div>
                                    <div class="sun_down_time"></div>
                                </div>
                            </div>
                        </div>
                        <div class="mode_activity">
                            <div class="mode"><span class="mode_name"></span></div>
                            <div class="activity"><span class="activity_name"></span></div>
                        </div>
                    </td>
                    <td id="day11" class="current_month_day">
                        <div class="date_holiday">
                            <div class="date"></div>
                            <div class="holiday"></div>
                        </div>
                        <div class="weather_list" onclick="showOneDayWeather(11)">
                            <div class="weather_icon "></div>
                            <div class="weather_desc_temperature">
                                <div class="weather_desc"></div>
                                <div class="temperature"></div>
                            </div>
                            <div class="sun_up_sun_down">
                                <div class="sun_up">
                                    <div class="sun_up_icon"></div>
                                    <div class="sun_up_time"></div>
                                </div>
                                <div class="sun_down">
                                    <div class="sun_down_icon"></div>
                                    <div class="sun_down_time"></div>
                                </div>
                            </div>
                        </div>
                        <div class="mode_activity">
                            <div class="mode"><span class="mode_name"></span></div>
                            <div class="activity"><span class="activity_name"></span></div>
                        </div>
                    </td>
                    <td id="day12" class="current_month_day">
                        <div class="date_holiday">
                            <div class="date"></div>
                            <div class="holiday"></div>
                        </div>
                        <div class="weather_list" onclick="showOneDayWeather(12)">
                            <div class="weather_icon "></div>
                            <div class="weather_desc_temperature">
                                <div class="weather_desc"></div>
                                <div class="temperature"></div>
                            </div>
                            <div class="sun_up_sun_down">
                                <div class="sun_up">
                                    <div class="sun_up_icon"></div>
                                    <div class="sun_up_time"></div>
                                </div>
                                <div class="sun_down">
                                    <div class="sun_down_icon"></div>
                                    <div class="sun_down_time"></div>
                                </div>
                            </div>
                        </div>
                        <div class="mode_activity">
                            <div class="mode"><span class="mode_name"></span></div>
                            <div class="activity"><span class="activity_name"></span></div>
                        </div>
                    </td>
                    <td id="day13" class="current_month_day">
                        <div class="date_holiday">
                            <div class="date"></div>
                            <div class="holiday"></div>
                        </div>
                        <div class="weather_list" onclick="showOneDayWeather(13)">
                            <div class="weather_icon "></div>
                            <div class="weather_desc_temperature">
                                <div class="weather_desc"></div>
                                <div class="temperature"></div>
                            </div>
                            <div class="sun_up_sun_down">
                                <div class="sun_up">
                                    <div class="sun_up_icon"></div>
                                    <div class="sun_up_time"></div>
                                </div>
                                <div class="sun_down">
                                    <div class="sun_down_icon"></div>
                                    <div class="sun_down_time"></div>
                                </div>
                            </div>
                        </div>
                        <div class="mode_activity">
                            <div class="mode"><span class="mode_name"></span></div>
                            <div class="activity"><span class="activity_name"></span></div>
                        </div>
                    </td>
                    <td id="day14" class="current_month_day">
                        <div class="date_holiday">
                            <div class="date"></div>
                            <div class="holiday"></div>
                        </div>
                        <div class="weather_list" onclick="showOneDayWeather(14)">
                            <div class="weather_icon "></div>
                            <div class="weather_desc_temperature">
                                <div class="weather_desc"></div>
                                <div class="temperature"></div>
                            </div>
                            <div class="sun_up_sun_down">
                                <div class="sun_up">
                                    <div class="sun_up_icon"></div>
                                    <div class="sun_up_time"></div>
                                </div>
                                <div class="sun_down">
                                    <div class="sun_down_icon"></div>
                                    <div class="sun_down_time"></div>
                                </div>
                            </div>
                        </div>
                        <div class="mode_activity">
                            <div class="mode"><span class="mode_name"></span></div>
                            <div class="activity"><span class="activity_name"></span></div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td id="day15" class="current_month_day">
                        <div class="date_holiday">
                            <div class="date"></div>
                            <div class="holiday"></div>
                        </div>
                        <div class="weather_list" onclick="showOneDayWeather(15)">
                            <div class="weather_icon "></div>
                            <div class="weather_desc_temperature">
                                <div class="weather_desc"></div>
                                <div class="temperature"></div>
                            </div>
                            <div class="sun_up_sun_down">
                                <div class="sun_up">
                                    <div class="sun_up_icon"></div>
                                    <div class="sun_up_time"></div>
                                </div>
                                <div class="sun_down">
                                    <div class="sun_down_icon"></div>
                                    <div class="sun_down_time"></div>
                                </div>
                            </div>
                        </div>
                        <div class="mode_activity">
                            <div class="mode"><span class="mode_name"></span></div>
                            <div class="activity"><span class="activity_name"></span></div>
                        </div>
                    </td>
                    <td id="day16" class="current_month_day">
                        <div class="date_holiday">
                            <div class="date"></div>
                            <div class="holiday"></div>
                        </div>
                        <div class="weather_list" onclick="showOneDayWeather(16)">
                            <div class="weather_icon "></div>
                            <div class="weather_desc_temperature">
                                <div class="weather_desc"></div>
                                <div class="temperature"></div>
                            </div>
                            <div class="sun_up_sun_down">
                                <div class="sun_up">
                                    <div class="sun_up_icon"></div>
                                    <div class="sun_up_time"></div>
                                </div>
                                <div class="sun_down">
                                    <div class="sun_down_icon"></div>
                                    <div class="sun_down_time"></div>
                                </div>
                            </div>
                        </div>
                        <div class="mode_activity">
                            <div class="mode"><span class="mode_name"></span></div>
                            <div class="activity"><span class="activity_name"></span></div>
                        </div>
                    </td>
                    <td id="day17" class="current_month_day">
                        <div class="date_holiday">
                            <div class="date"></div>
                            <div class="holiday"></div>
                        </div>
                        <div class="weather_list" onclick="showOneDayWeather(17)">
                            <div class="weather_icon "></div>
                            <div class="weather_desc_temperature">
                                <div class="weather_desc"></div>
                                <div class="temperature"></div>
                            </div>
                            <div class="sun_up_sun_down">
                                <div class="sun_up">
                                    <div class="sun_up_icon"></div>
                                    <div class="sun_up_time"></div>
                                </div>
                                <div class="sun_down">
                                    <div class="sun_down_icon"></div>
                                    <div class="sun_down_time"></div>
                                </div>
                            </div>
                        </div>
                        <div class="mode_activity">
                            <div class="mode"><span class="mode_name"></span></div>
                            <div class="activity"><span class="activity_name"></span></div>
                        </div>
                    </td>
                    <td id="day18" class="current_month_day">
                        <div class="date_holiday">
                            <div class="date"></div>
                            <div class="holiday"></div>
                        </div>
                        <div class="weather_list" onclick="showOneDayWeather(18)">
                            <div class="weather_icon "></div>
                            <div class="weather_desc_temperature">
                                <div class="weather_desc"></div>
                                <div class="temperature"></div>
                            </div>
                            <div class="sun_up_sun_down">
                                <div class="sun_up">
                                    <div class="sun_up_icon"></div>
                                    <div class="sun_up_time"></div>
                                </div>
                                <div class="sun_down">
                                    <div class="sun_down_icon"></div>
                                    <div class="sun_down_time"></div>
                                </div>
                            </div>
                        </div>
                        <div class="mode_activity">
                            <div class="mode"><span class="mode_name"></span></div>
                            <div class="activity"><span class="activity_name"></span></div>
                        </div>
                    </td>
                    <td id="day19" class="current_month_day">
                        <div class="date_holiday">
                            <div class="date"></div>
                            <div class="holiday"></div>
                        </div>
                        <div class="weather_list" onclick="showOneDayWeather(19)">
                            <div class="weather_icon "></div>
                            <div class="weather_desc_temperature">
                                <div class="weather_desc"></div>
                                <div class="temperature"></div>
                            </div>
                            <div class="sun_up_sun_down">
                                <div class="sun_up">
                                    <div class="sun_up_icon"></div>
                                    <div class="sun_up_time"></div>
                                </div>
                                <div class="sun_down">
                                    <div class="sun_down_icon"></div>
                                    <div class="sun_down_time"></div>
                                </div>
                            </div>
                        </div>
                        <div class="mode_activity">
                            <div class="mode"><span class="mode_name"></span></div>
                            <div class="activity"><span class="activity_name"></span></div>
                        </div>
                    </td>
                    <td id="day20" class="current_month_day">
                        <div class="date_holiday">
                            <div class="date"></div>
                            <div class="holiday"></div>
                        </div>
                        <div class="weather_list" onclick="showOneDayWeather(20)">
                            <div class="weather_icon "></div>
                            <div class="weather_desc_temperature">
                                <div class="weather_desc"></div>
                                <div class="temperature"></div>
                            </div>
                            <div class="sun_up_sun_down">
                                <div class="sun_up">
                                    <div class="sun_up_icon"></div>
                                    <div class="sun_up_time"></div>
                                </div>
                                <div class="sun_down">
                                    <div class="sun_down_icon"></div>
                                    <div class="sun_down_time"></div>
                                </div>
                            </div>
                        </div>
                        <div class="mode_activity">
                            <div class="mode"><span class="mode_name"></span></div>
                            <div class="activity"><span class="activity_name"></span></div>
                        </div>
                    </td>
                    <td id="day21" class="current_month_day">
                        <div class="date_holiday">
                            <div class="date"></div>
                            <div class="holiday"></div>
                        </div>
                        <div class="weather_list" onclick="showOneDayWeather(21)">
                            <div class="weather_icon "></div>
                            <div class="weather_desc_temperature">
                                <div class="weather_desc"></div>
                                <div class="temperature"></div>
                            </div>
                            <div class="sun_up_sun_down">
                                <div class="sun_up">
                                    <div class="sun_up_icon"></div>
                                    <div class="sun_up_time"></div>
                                </div>
                                <div class="sun_down">
                                    <div class="sun_down_icon"></div>
                                    <div class="sun_down_time"></div>
                                </div>
                            </div>
                        </div>
                        <div class="mode_activity">
                            <div class="mode"><span class="mode_name"></span></div>
                            <div class="activity"><span class="activity_name"></span></div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td id="day22" class="current_month_day">
                        <div class="date_holiday">
                            <div class="date"></div>
                            <div class="holiday"></div>
                        </div>
                        <div class="weather_list" onclick="showOneDayWeather(22)">
                            <div class="weather_icon "></div>
                            <div class="weather_desc_temperature">
                                <div class="weather_desc"></div>
                                <div class="temperature"></div>
                            </div>
                            <div class="sun_up_sun_down">
                                <div class="sun_up">
                                    <div class="sun_up_icon"></div>
                                    <div class="sun_up_time"></div>
                                </div>
                                <div class="sun_down">
                                    <div class="sun_down_icon"></div>
                                    <div class="sun_down_time"></div>
                                </div>
                            </div>
                        </div>
                        <div class="mode_activity">
                            <div class="mode"><span class="mode_name"></span></div>
                            <div class="activity"><span class="activity_name"></span></div>
                        </div>
                    </td>
                    <td id="day23" class="current_month_day">
                        <div class="date_holiday">
                            <div class="date"></div>
                            <div class="holiday"></div>
                        </div>
                        <div class="weather_list" onclick="showOneDayWeather(23)">
                            <div class="weather_icon "></div>
                            <div class="weather_desc_temperature">
                                <div class="weather_desc"></div>
                                <div class="temperature"></div>
                            </div>
                            <div class="sun_up_sun_down">
                                <div class="sun_up">
                                    <div class="sun_up_icon"></div>
                                    <div class="sun_up_time"></div>
                                </div>
                                <div class="sun_down">
                                    <div class="sun_down_icon"></div>
                                    <div class="sun_down_time"></div>
                                </div>
                            </div>
                        </div>
                        <div class="mode_activity">
                            <div class="mode"><span class="mode_name"></span></div>
                            <div class="activity"><span class="activity_name"></span></div>
                        </div>
                    </td>
                    <td id="day24" class="current_month_day">
                        <div class="date_holiday">
                            <div class="date"></div>
                            <div class="holiday"></div>
                        </div>
                        <div class="weather_list" onclick="showOneDayWeather(24)">
                            <div class="weather_icon "></div>
                            <div class="weather_desc_temperature">
                                <div class="weather_desc"></div>
                                <div class="temperature"></div>
                            </div>
                            <div class="sun_up_sun_down">
                                <div class="sun_up">
                                    <div class="sun_up_icon"></div>
                                    <div class="sun_up_time"></div>
                                </div>
                                <div class="sun_down">
                                    <div class="sun_down_icon"></div>
                                    <div class="sun_down_time"></div>
                                </div>
                            </div>
                        </div>
                        <div class="mode_activity">
                            <div class="mode"><span class="mode_name"></span></div>
                            <div class="activity"><span class="activity_name"></span></div>
                        </div>
                    </td>
                    <td id="day25" class="current_month_day">
                        <div class="date_holiday">
                            <div class="date"></div>
                            <div class="holiday"></div>
                        </div>
                        <div class="weather_list" onclick="showOneDayWeather(25)">
                            <div class="weather_icon "></div>
                            <div class="weather_desc_temperature">
                                <div class="weather_desc"></div>
                                <div class="temperature"></div>
                            </div>
                            <div class="sun_up_sun_down">
                                <div class="sun_up">
                                    <div class="sun_up_icon"></div>
                                    <div class="sun_up_time"></div>
                                </div>
                                <div class="sun_down">
                                    <div class="sun_down_icon"></div>
                                    <div class="sun_down_time"></div>
                                </div>
                            </div>
                        </div>
                        <div class="mode_activity">
                            <div class="mode"><span class="mode_name"></span></div>
                            <div class="activity"><span class="activity_name"></span></div>
                        </div>
                    </td>
                    <td id="day26" class="current_month_day">
                        <div class="date_holiday">
                            <div class="date"></div>
                            <div class="holiday"></div>
                        </div>
                        <div class="weather_list" onclick="showOneDayWeather(26)">
                            <div class="weather_icon "></div>
                            <div class="weather_desc_temperature">
                                <div class="weather_desc"></div>
                                <div class="temperature"></div>
                            </div>
                            <div class="sun_up_sun_down">
                                <div class="sun_up">
                                    <div class="sun_up_icon"></div>
                                    <div class="sun_up_time"></div>
                                </div>
                                <div class="sun_down">
                                    <div class="sun_down_icon"></div>
                                    <div class="sun_down_time"></div>
                                </div>
                            </div>
                        </div>
                        <div class="mode_activity">
                            <div class="mode"><span class="mode_name"></span></div>
                            <div class="activity"><span class="activity_name"></span></div>
                        </div>
                    </td>
                    <td id="day27" class="current_month_day">
                        <div class="date_holiday">
                            <div class="date"></div>
                            <div class="holiday"></div>
                        </div>
                        <div class="weather_list" onclick="showOneDayWeather(27)">
                            <div class="weather_icon "></div>
                            <div class="weather_desc_temperature">
                                <div class="weather_desc"></div>
                                <div class="temperature"></div>
                            </div>
                            <div class="sun_up_sun_down">
                                <div class="sun_up">
                                    <div class="sun_up_icon"></div>
                                    <div class="sun_up_time"></div>
                                </div>
                                <div class="sun_down">
                                    <div class="sun_down_icon"></div>
                                    <div class="sun_down_time"></div>
                                </div>
                            </div>
                        </div>
                        <div class="mode_activity">
                            <div class="mode"><span class="mode_name"></span></div>
                            <div class="activity"><span class="activity_name"></span></div>
                        </div>
                    </td>
                    <td id="day28" class="current_month_day">
                        <div class="date_holiday">
                            <div class="date"></div>
                            <div class="holiday"></div>
                        </div>
                        <div class="weather_list" onclick="showOneDayWeather(28)">
                            <div class="weather_icon "></div>
                            <div class="weather_desc_temperature">
                                <div class="weather_desc"></div>
                                <div class="temperature"></div>
                            </div>
                            <div class="sun_up_sun_down">
                                <div class="sun_up">
                                    <div class="sun_up_icon"></div>
                                    <div class="sun_up_time"></div>
                                </div>
                                <div class="sun_down">
                                    <div class="sun_down_icon"></div>
                                    <div class="sun_down_time"></div>
                                </div>
                            </div>
                        </div>
                        <div class="mode_activity">
                            <div class="mode"><span class="mode_name"></span></div>
                            <div class="activity"><span class="activity_name"></span></div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td id="day29" class="current_month_day">
                        <div class="date_holiday">
                            <div class="date"></div>
                            <div class="holiday"></div>
                        </div>
                        <div class="weather_list" onclick="showOneDayWeather(29)">
                            <div class="weather_icon "></div>
                            <div class="weather_desc_temperature">
                                <div class="weather_desc"></div>
                                <div class="temperature"></div>
                            </div>
                            <div class="sun_up_sun_down">
                                <div class="sun_up">
                                    <div class="sun_up_icon"></div>
                                    <div class="sun_up_time"></div>
                                </div>
                                <div class="sun_down">
                                    <div class="sun_down_icon"></div>
                                    <div class="sun_down_time"></div>
                                </div>
                            </div>
                        </div>
                        <div class="mode_activity">
                            <div class="mode"><span class="mode_name"></span></div>
                            <div class="activity"><span class="activity_name"></span></div>
                        </div>
                    </td>
                    <td id="day30" class="current_month_day">
                        <div class="date_holiday">
                            <div class="date"></div>
                            <div class="holiday"></div>
                        </div>
                        <div class="weather_list" onclick="showOneDayWeather(30)">
                            <div class="weather_icon "></div>
                            <div class="weather_desc_temperature">
                                <div class="weather_desc"></div>
                                <div class="temperature"></div>
                            </div>
                            <div class="sun_up_sun_down">
                                <div class="sun_up">
                                    <div class="sun_up_icon"></div>
                                    <div class="sun_up_time"></div>
                                </div>
                                <div class="sun_down">
                                    <div class="sun_down_icon"></div>
                                    <div class="sun_down_time"></div>
                                </div>
                            </div>
                        </div>
                        <div class="mode_activity">
                            <div class="mode"><span class="mode_name"></span></div>
                            <div class="activity"><span class="activity_name"></span></div>
                        </div>
                    </td>
                    <td id="day31" class="current_month_day">
                        <div class="date_holiday">
                            <div class="date"></div>
                            <div class="holiday"></div>
                        </div>
                        <div class="weather_list" onclick="showOneDayWeather(31)">
                            <div class="weather_icon "></div>
                            <div class="weather_desc_temperature">
                                <div class="weather_desc"></div>
                                <div class="temperature"></div>
                            </div>
                            <div class="sun_up_sun_down">
                                <div class="sun_up">
                                    <div class="sun_up_icon"></div>
                                    <div class="sun_up_time"></div>
                                </div>
                                <div class="sun_down">
                                    <div class="sun_down_icon"></div>
                                    <div class="sun_down_time"></div>
                                </div>
                            </div>
                        </div>
                        <div class="mode_activity">
                            <div class="mode"><span class="mode_name"></span></div>
                            <div class="activity"><span class="activity_name"></span></div>
                        </div>
                    </td>
                    <td id="day32" class="current_month_day">
                        <div class="date_holiday">
                            <div class="date"></div>
                            <div class="holiday"></div>
                        </div>
                        <div class="weather_list" onclick="showOneDayWeather(32)">
                            <div class="weather_icon "></div>
                            <div class="weather_desc_temperature">
                                <div class="weather_desc"></div>
                                <div class="temperature"></div>
                            </div>
                            <div class="sun_up_sun_down">
                                <div class="sun_up">
                                    <div class="sun_up_icon"></div>
                                    <div class="sun_up_time"></div>
                                </div>
                                <div class="sun_down">
                                    <div class="sun_down_icon"></div>
                                    <div class="sun_down_time"></div>
                                </div>
                            </div>
                        </div>
                        <div class="mode_activity">
                            <div class="mode"><span class="mode_name"></span></div>
                            <div class="activity"><span class="activity_name"></span></div>
                        </div>
                    </td>
                    <td id="day33" class="current_month_day">
                        <div class="date_holiday">
                            <div class="date"></div>
                            <div class="holiday"></div>
                        </div>
                        <div class="weather_list" onclick="showOneDayWeather(33)">
                            <div class="weather_icon "></div>
                            <div class="weather_desc_temperature">
                                <div class="weather_desc"></div>
                                <div class="temperature"></div>
                            </div>
                            <div class="sun_up_sun_down">
                                <div class="sun_up">
                                    <div class="sun_up_icon"></div>
                                    <div class="sun_up_time"></div>
                                </div>
                                <div class="sun_down">
                                    <div class="sun_down_icon"></div>
                                    <div class="sun_down_time"></div>
                                </div>
                            </div>
                        </div>
                        <div class="mode_activity">
                            <div class="mode"><span class="mode_name"></span></div>
                            <div class="activity"><span class="activity_name"></span></div>
                        </div>
                    </td>
                    <td id="day34" class="current_month_day">
                        <div class="date_holiday">
                            <div class="date"></div>
                            <div class="holiday"></div>
                        </div>
                        <div class="weather_list" onclick="showOneDayWeather(34)">
                            <div class="weather_icon "></div>
                            <div class="weather_desc_temperature">
                                <div class="weather_desc"></div>
                                <div class="temperature"></div>
                            </div>
                            <div class="sun_up_sun_down">
                                <div class="sun_up">
                                    <div class="sun_up_icon"></div>
                                    <div class="sun_up_time"></div>
                                </div>
                                <div class="sun_down">
                                    <div class="sun_down_icon"></div>
                                    <div class="sun_down_time"></div>
                                </div>
                            </div>
                        </div>
                        <div class="mode_activity">
                            <div class="mode"><span class="mode_name"></span></div>
                            <div class="activity"><span class="activity_name"></span></div>
                        </div>
                    </td>
                    <td id="day35" class="current_month_day">
                        <div class="date_holiday">
                            <div class="date"></div>
                            <div class="holiday"></div>
                        </div>
                        <div class="weather_list" onclick="showOneDayWeather(35)">
                            <div class="weather_icon "></div>
                            <div class="weather_desc_temperature">
                                <div class="weather_desc"></div>
                                <div class="temperature"></div>
                            </div>
                            <div class="sun_up_sun_down">
                                <div class="sun_up">
                                    <div class="sun_up_icon"></div>
                                    <div class="sun_up_time"></div>
                                </div>
                                <div class="sun_down">
                                    <div class="sun_down_icon"></div>
                                    <div class="sun_down_time"></div>
                                </div>
                            </div>
                        </div>
                        <div class="mode_activity">
                            <div class="mode"><span class="mode_name"></span></div>
                            <div class="activity"><span class="activity_name"></span></div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td id="day36" class="current_month_day">
                        <div class="date_holiday">
                            <div class="date"></div>
                            <div class="holiday"></div>
                        </div>
                        <div class="weather_list" onclick="showOneDayWeather(36)">
                            <div class="weather_icon "></div>
                            <div class="weather_desc_temperature">
                                <div class="weather_desc"></div>
                                <div class="temperature"></div>
                            </div>
                            <div class="sun_up_sun_down">
                                <div class="sun_up">
                                    <div class="sun_up_icon"></div>
                                    <div class="sun_up_time"></div>
                                </div>
                                <div class="sun_down">
                                    <div class="sun_down_icon"></div>
                                    <div class="sun_down_time"></div>
                                </div>
                            </div>
                        </div>
                        <div class="mode_activity">
                            <div class="mode"><span class="mode_name"></span></div>
                            <div class="activity"><span class="activity_name"></span></div>
                        </div>
                    </td>
                    <td id="day37" class="current_month_day">
                        <div class="date_holiday">
                            <div class="date"></div>
                            <div class="holiday"></div>
                        </div>
                        <div class="weather_list" onclick="showOneDayWeather(37)">
                            <div class="weather_icon "></div>
                            <div class="weather_desc_temperature">
                                <div class="weather_desc"></div>
                                <div class="temperature"></div>
                            </div>
                            <div class="sun_up_sun_down">
                                <div class="sun_up">
                                    <div class="sun_up_icon"></div>
                                    <div class="sun_up_time"></div>
                                </div>
                                <div class="sun_down">
                                    <div class="sun_down_icon"></div>
                                    <div class="sun_down_time"></div>
                                </div>
                            </div>
                        </div>
                        <div class="mode_activity">
                            <div class="mode"><span class="mode_name"></span></div>
                            <div class="activity"><span class="activity_name"></span></div>
                        </div>
                    </td>
                    <td id="day38" class="current_month_day">
                        <div class="date_holiday">
                            <div class="date"></div>
                            <div class="holiday"></div>
                        </div>
                        <div class="weather_list" onclick="showOneDayWeather(38)">
                            <div class="weather_icon "></div>
                            <div class="weather_desc_temperature">
                                <div class="weather_desc"></div>
                                <div class="temperature"></div>
                            </div>
                            <div class="sun_up_sun_down">
                                <div class="sun_up">
                                    <div class="sun_up_icon"></div>
                                    <div class="sun_up_time"></div>
                                </div>
                                <div class="sun_down">
                                    <div class="sun_down_icon"></div>
                                    <div class="sun_down_time"></div>
                                </div>
                            </div>
                        </div>
                        <div class="mode_activity">
                            <div class="mode"><span class="mode_name"></span></div>
                            <div class="activity"><span class="activity_name"></span></div>
                        </div>
                    </td>
                    <td id="day39" class="current_month_day">
                        <div class="date_holiday">
                            <div class="date"></div>
                            <div class="holiday"></div>
                        </div>
                        <div class="weather_list" onclick="showOneDayWeather(39)">
                            <div class="weather_icon "></div>
                            <div class="weather_desc_temperature">
                                <div class="weather_desc"></div>
                                <div class="temperature"></div>
                            </div>
                            <div class="sun_up_sun_down">
                                <div class="sun_up">
                                    <div class="sun_up_icon"></div>
                                    <div class="sun_up_time"></div>
                                </div>
                                <div class="sun_down">
                                    <div class="sun_down_icon"></div>
                                    <div class="sun_down_time"></div>
                                </div>
                            </div>
                        </div>
                        <div class="mode_activity">
                            <div class="mode"><span class="mode_name"></span></div>
                            <div class="activity"><span class="activity_name"></span></div>
                        </div>
                    </td>
                    <td id="day40" class="current_month_day">
                        <div class="date_holiday">
                            <div class="date"></div>
                            <div class="holiday"></div>
                        </div>
                        <div class="weather_list" onclick="showOneDayWeather(40)">
                            <div class="weather_icon "></div>
                            <div class="weather_desc_temperature">
                                <div class="weather_desc"></div>
                                <div class="temperature"></div>
                            </div>
                            <div class="sun_up_sun_down">
                                <div class="sun_up">
                                    <div class="sun_up_icon"></div>
                                    <div class="sun_up_time"></div>
                                </div>
                                <div class="sun_down">
                                    <div class="sun_down_icon"></div>
                                    <div class="sun_down_time"></div>
                                </div>
                            </div>
                        </div>
                        <div class="mode_activity">
                            <div class="mode"><span class="mode_name"></span></div>
                            <div class="activity"><span class="activity_name"></span></div>
                        </div>
                    </td>
                    <td id="day41" class="current_month_day">
                        <div class="date_holiday">
                            <div class="date"></div>
                            <div class="holiday"></div>
                        </div>
                        <div class="weather_list" onclick="showOneDayWeather(41)">
                            <div class="weather_icon "></div>
                            <div class="weather_desc_temperature">
                                <div class="weather_desc"></div>
                                <div class="temperature"></div>
                            </div>
                            <div class="sun_up_sun_down">
                                <div class="sun_up">
                                    <div class="sun_up_icon"></div>
                                    <div class="sun_up_time"></div>
                                </div>
                                <div class="sun_down">
                                    <div class="sun_down_icon"></div>
                                    <div class="sun_down_time"></div>
                                </div>
                            </div>
                        </div>
                        <div class="mode_activity">
                            <div class="mode"><span class="mode_name"></span></div>
                            <div class="activity"><span class="activity_name"></span></div>
                        </div>
                    </td>
                    <td id="day42" class="current_month_day">
                        <div class="date_holiday">
                            <div class="date"></div>
                            <div class="holiday"></div>
                        </div>
                        <div class="weather_list" onclick="showOneDayWeather(42)">
                            <div class="weather_icon "></div>
                            <div class="weather_desc_temperature">
                                <div class="weather_desc"></div>
                                <div class="temperature"></div>
                            </div>
                            <div class="sun_up_sun_down">
                                <div class="sun_up">
                                    <div class="sun_up_icon"></div>
                                    <div class="sun_up_time"></div>
                                </div>
                                <div class="sun_down">
                                    <div class="sun_down_icon"></div>
                                    <div class="sun_down_time"></div>
                                </div>
                            </div>
                        </div>
                        <div class="mode_activity">
                            <div class="mode"><span class="mode_name"></span></div>
                            <div class="activity"><span class="activity_name"></span></div>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>

        <div id="grid_bottom_line"></div>

    </div>
</div>

<div id="mask">

</div>
<script type="text/javascript">
    var weekday = new Array(7);
    weekday[0] = "周日";
    weekday[1] = "周一";
    weekday[2] = "周二";
    weekday[3] = "周三";
    weekday[4] = "周四";
    weekday[5] = "周五";
    weekday[6] = "周六";


    var systemId = "${systemCode}";
    var dayIndex = 0;
    var todayWeatherType = "";
    var todayWeatherDesc = "";
    var todayMinTemperature = "";
    var todayMaxTemperature = "";
    var todaySunriseTime = "";
    var todaySundownTime = "";

    var current_yyyy = "${year}";
    var current_mm = "${month}";
    var current_dd = "${day}";

    if (parseInt(current_mm) < 10)
        current_mm = "0" + parseInt(current_mm);

    if (parseInt(current_dd) < 10)
        current_dd = "0" + parseInt(current_dd);

    var selected_yyyy = current_yyyy;
    var selected_mm = current_mm;
    var selected_first_day = current_dd;
    var selected_last_day = current_dd;
    var max_date = 31;
    var first_day = "";
    var last_day = "";
    $(function () {

        resetMaxDate(selected_yyyy, selected_mm);
        resetMonthRange(selected_yyyy, selected_mm);
        clearMonthView();
        setDefaultDaysBgInMonthView();
        highLightCurrentDayInMonthView();
        setLastMonthDayBgInMonthView();
        setNextMonthDayBgInMonthView();
        getMonthSchedule();

        $('#year_selector').on('click', function (event) {
                    if ($('.year_list').css('display') == 'none')
                        $('.year_list').show();
                    else
                        $('.year_list').hide();
                    event.stopPropagation();
                }
        );

        $('.year_list > ul > li').on('mouseover', function (event) {
            $('.year_list >ul >li').removeClass('current');
            $(event.target).addClass('current');
        });

        $('.year_list > ul > li').on('click', function (event) {

            $('#current_year').html($(event.target).html());
            $('.year_list').hide();
            selected_yyyy = $(event.target).html();
            $("#month_slider > span").html(selected_yyyy + "年" + selected_mm + "月");
            resetMaxDate(selected_yyyy, selected_mm);
            resetMonthRange(selected_yyyy, selected_mm);

            clearMonthView();
            setDefaultDaysBgInMonthView();
            highLightCurrentDayInMonthView();
            setLastMonthDayBgInMonthView();
            setNextMonthDayBgInMonthView();
            getMonthSchedule();
            event.stopPropagation();
        });


        $('#month_selector').on('click', function (event) {
                    if ($('.month_list').css('display') == 'none')
                        $('.month_list').show();
                    else
                        $('.month_list').hide();
                    event.stopPropagation();
                }
        );

        document.onclick = function () {
            $(".year_list, .month_list").hide();
        };


        $('.month_list > ul > li').on('mouseover', function (event) {
            $('.month_list >ul >li').removeClass('current');
            $(event.target).addClass('current');
        });

        $('.month_list > ul > li').on('click', function (event) {

            $('#current_month').html($(event.target).html());
            $('.month_list').hide();
            selected_mm = $(event.target).html();
            $("#month_slider > span").html(selected_yyyy + "年" + selected_mm);
            resetMaxDate(selected_yyyy, selected_mm);
            resetMonthRange(selected_yyyy, selected_mm);

            clearMonthView();
            setDefaultDaysBgInMonthView();
            highLightCurrentDayInMonthView();
            setLastMonthDayBgInMonthView();
            setNextMonthDayBgInMonthView();
            getMonthSchedule();
            event.stopPropagation();
        });

        $("#day_grid td ").click(function () {
            $("#day_grid  td:not('.current_month_current_day')").css({"outline": "none"});
            if ($(this).attr("class") == "current_month_current_day") {
                return false;
            } else {
                $(this).css({"outline": "4px solid #ffa64c"});
            }
        })
    });
</script>

