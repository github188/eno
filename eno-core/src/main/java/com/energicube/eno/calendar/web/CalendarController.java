package com.energicube.eno.calendar.web;

import com.energicube.eno.calendar.model.UcCalendar;
import com.energicube.eno.calendar.model.UcHoliday;
import com.energicube.eno.calendar.model.UcWeather;
import com.energicube.eno.calendar.service.CalendarService;
import com.energicube.eno.common.Config;
import com.energicube.eno.common.PatternConst;
import com.energicube.eno.common.SendCommandToSystem;
import com.energicube.eno.common.dto.ItemJSON;
import com.energicube.eno.common.dto.PatternJSON;
import com.energicube.eno.common.model.Message;
import com.energicube.eno.monitor.model.Event;
import com.energicube.eno.monitor.model.EventType;
import com.energicube.eno.monitor.model.OkcMenu;
import com.energicube.eno.monitor.model.UserInfo;
import com.energicube.eno.monitor.service.MenuService;
import com.energicube.eno.monitor.service.OpLogService;
import com.energicube.eno.monitor.web.BaseController;
import com.energicube.eno.pattern.model.UcDeviceSystem;
import com.energicube.eno.pattern.model.UcPattern;
import com.energicube.eno.pattern.model.UcPatternRecord;
import com.energicube.eno.pattern.service.PatternService;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-7-2
 * Time: 上午10:31
 * 日历的操作
 */
@Controller
public class CalendarController extends BaseController {

    private static Log logger = LogFactory.getLog(CalendarController.class);

    private Config config = new Config();

    @Autowired
    CalendarService calendarService;

    @Autowired
    PatternService patternService;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private MenuService menuService;

    @Autowired
    OpLogService opLogService;

    @ModelAttribute(value = "appFilterMenus")
    public List<OkcMenu> TypeFilterMenu(HttpServletRequest request) {
        //通过请求路径，解析菜单中对应的元素的归属元素

        //让菜单保持一致，始终用ASC的菜单
        List<OkcMenu> filterMenus = menuService.findAppFilterList("ASC");

        return filterMenus;
    }

    /**
     * 显示日视图
     *
     * @param model
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/calendar/showDayCalendar")
    public String showCalendar(Model model, HttpServletRequest httpServletRequest) {

        //传过来的参数
        String nowDate = httpServletRequest.getParameter("nowDate");
        String systemId = httpServletRequest.getParameter("systemId");
        DateTime nowDateTime = null;

        if (nowDate == null || "".equals(nowDate)) {
            nowDate = simpleDateFormat.format(new Date());
        }
//        if (systemId == null || "".equals(systemId) || PatternConst.SYSTEM_CODE_MAHU.equals(systemId)) {
//            model.addAttribute("elementvalue", PatternConst.SYSTEM_CODE_HVAC);
//            systemId = PatternConst.SYSTEM_CODE_HVAC;
//        }else{
//            model.addAttribute("elementvalue",systemId);
//        }
        if (systemId == null || "".equals(systemId) || PatternConst.SYSTEM_CODE_MAHU.equals(systemId)) {
            systemId = PatternConst.SYSTEM_CODE_HVAC;
        }
        //目前只支持一个
        UcWeather ucWeather = patternService.findWeatherByCalendarAndNew(nowDate, "Y");

        model.addAttribute("weather", ucWeather);
        //今天的日期
        nowDateTime = new DateTime();
        model.addAttribute("year", nowDateTime.getYear());
        model.addAttribute("month", nowDateTime.getMonthOfYear());
        model.addAttribute("day", nowDateTime.getDayOfMonth());
        model.addAttribute("hour", nowDateTime.getHourOfDay());
        model.addAttribute("minute", nowDateTime.getMinuteOfHour());

        DateTime sunrise = DateTime.parse(ucWeather.getSunrise(), DateTimeFormat.forPattern("HH:mm"));
        DateTime sunset = DateTime.parse(ucWeather.getSunset(), DateTimeFormat.forPattern("HH:mm"));

        model.addAttribute("sunriseH", sunrise.toString("HH"));
        model.addAttribute("sunriseM", sunrise.toString("mm"));
        model.addAttribute("sunsetH", sunset.toString("HH"));
        model.addAttribute("sunsetM", sunset.toString("mm"));


        model.addAttribute("elementvalue", systemId);
        model.addAttribute("systemCode", systemId);
        return "calendar/dayCalendar";
    }

    /**
     * 获取天气信息
     *
     * @param httpServletRequest
     * @return json串
     */
    @RequestMapping(value = "/calendar/getCalendarWeather")
    @ResponseBody
    public Object getDayCalendarWeather(HttpServletRequest httpServletRequest) {

        List<UcCalendar> ucCalendars = null;
        try {

            String startDate = httpServletRequest.getParameter("startDate");
            String endDate = httpServletRequest.getParameter("endDate");

            if (startDate == null || "".equals(startDate)) {
                startDate = simpleDateFormat.format(new Date());
            }
            if (endDate == null || "".equals(endDate)) {
                endDate = simpleDateFormat.format(new Date());
            }
            //根据今天日期取今天的开气、节日、日出、日落
            ucCalendars = patternService.findUcCalendarByCalendar(startDate, endDate);

            //将天气信息只保留标志为最新的一条记录  isNew=Y

            for (UcCalendar ucCalendar : ucCalendars) {
                List<UcWeather> ucWeatherList = new ArrayList<UcWeather>();
                Set<UcWeather> ucWeathers = ucCalendar.getUcWeathers();
                //logger.debug("-----ucWeathers.size---"+ucWeathers.size());
                for (UcWeather ucWeather : ucWeathers) {

                    //  logger.debug("-----ucWeathers.size---:"+ucWeather.getIsNew());
                    if (ucWeather.getIsNew() == null || "N".equals(ucWeather.getIsNew())) {

                    } else {
                        ucWeatherList.add(ucWeather);
                    }
                }
                ucCalendar.getUcWeathers().clear();
                ucCalendar.setUcWeathers(new HashSet<UcWeather>(ucWeatherList));
            }

            return ucCalendars;
        } catch (Exception e) {
            logger.error("-----------", e);
        }
        return ucCalendars;
    }

    /**
     * 获取事件
     *
     * @param httpServletRequest
     * @return json串
     */
    @RequestMapping(value = "/calendar/getCalendarEvent")
    @ResponseBody
    public Object getDayCalendarEvent(HttpServletRequest httpServletRequest) {
        List<Event> events = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            String startDate = httpServletRequest.getParameter("startDate");
            String endDate = httpServletRequest.getParameter("endDate");

            if (startDate == null || "".equals(startDate)) {
                startDate = simpleDateFormat.format(new Date());
            }
            if (endDate == null || "".equals(endDate)) {
                endDate = simpleDateFormat.format(new Date());
            }
            Date startTime = DateTime.parse(startDate, DateTimeFormat.forPattern("yyyyMMdd")).plusSeconds(-1).toDate();
            Date endTime = DateTime.parse(endDate, DateTimeFormat.forPattern("yyyyMMdd")).plusDays(1).plusSeconds(-1).toDate();
            logger.debug("-----startTime----" + startTime);
            logger.debug("-----endTime----" + endTime);
            //查询今天的事件
            events = calendarService.findEventByStartDateAndEndDate(startTime, endTime);

            return events;
        } catch (Exception e) {
            logger.error("-----------", e);
        }
        return events;
    }

    /**
     * 获取准备运行的模式
     *
     * @param httpServletRequest
     * @return json
     */
    @RequestMapping(value = "/calendar/getCalendarPattern")
    @ResponseBody
    public Object getDayCalendarPattern(HttpServletRequest httpServletRequest) {
        List<UcPatternRecord> ucPatternRecordList = new ArrayList<UcPatternRecord>();

        try {
            String systemId = httpServletRequest.getParameter("systemId");
            String startDate = httpServletRequest.getParameter("startDate");
            String endDate = httpServletRequest.getParameter("endDate");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

            if (startDate == null || "".equals(startDate)) {
                startDate = simpleDateFormat.format(new Date());
            }
            if (endDate == null || "".equals(endDate)) {
                endDate = simpleDateFormat.format(new Date());
            }
            if (systemId == null || "".equals(systemId) || PatternConst.SYSTEM_CODE_MAHU.equals(systemId)) {
                systemId = PatternConst.SYSTEM_CODE_HVAC;
            }
            //查询子系统的模式
            ucPatternRecordList = patternService.findSubSystemPattern("", systemId, startDate, endDate);

            //计算各模式里各设备的运行时长
            for (UcPatternRecord ucPatternRecord : ucPatternRecordList) {

                //只保留最新的天气信息
                Set<UcWeather> ucWeatherSet = new HashSet<UcWeather>();
                UcWeather ucWeather = patternService.findWeatherByCalendarAndNew(ucPatternRecord.getUcCalendar().getId(), PatternConst.WEATHER_IS_NEW_YES);
                ucWeather.setUcCalendar(null);
                ucWeatherSet.add(ucWeather);
                patternService.calculateDeviceRunTime(ucPatternRecord, ucWeather);
                ucPatternRecord.getUcCalendar().setUcWeathers(ucWeatherSet);

            }

            return ucPatternRecordList;
        } catch (Exception e) {
            logger.error("----getCalendarPattern------", e);
        }
        return ucPatternRecordList;
    }

    /**
     * 获取某子系统的时序模式
     *
     * @param httpServletRequest
     * @return json
     */
    @RequestMapping(value = "/calendar/getPattern")
    @ResponseBody
    public Object getPattern(HttpServletRequest httpServletRequest) {
        List<UcPattern> ucPatternList = new ArrayList<UcPattern>();

        try {
            String systemId = httpServletRequest.getParameter("systemId");
            List<UcPattern> ucPatterns = patternService.findUcPatternBySystem(systemId, PatternConst.PATTERN_TYPE_SYSTEM, PatternConst.PATTERN_ORDER_TYPE_TIME);
            ucPatternList.addAll(ucPatterns);
            ucPatterns = patternService.findUcPatternBySystem(systemId, PatternConst.PATTERN_TYPE_CUSTOM, PatternConst.PATTERN_ORDER_TYPE_TIME);
            ucPatternList.addAll(ucPatterns);

            //去掉不需要的属性, 否则转JSON会报错
            for (UcPattern ucPattern : ucPatternList) {
                ucPattern.setUcCombinationPatterns(null);
                ucPattern.setUcPatternFactors(null);
                ucPattern.setUcPatternActions(null);
            }

            return ucPatternList;
        } catch (Exception e) {
            logger.error("----getPattern------", e);
        }
        return ucPatternList;
    }

    /**
     * 获取拥有的子系统
     *
     * @param httpServletRequest
     * @return json
     */
    @RequestMapping(value = "/calendar/getSubSystem")
    @ResponseBody
    public Object getSubSystem(HttpServletRequest httpServletRequest) {

        List<UcDeviceSystem> ucDeviceSystemList = new ArrayList<UcDeviceSystem>();
        try {
            String systemCode = httpServletRequest.getParameter("systemId");
            UcDeviceSystem ucDeviceSystem = patternService.findDeviceSystemByCode(systemCode);
            String systemId = ucDeviceSystem.getId();
            ucDeviceSystemList = patternService.findDeviceSystemByParentId(systemId);
            for (UcDeviceSystem ucDeviceSystem1 : ucDeviceSystemList) {
                ucDeviceSystem1.setUcDeviceGroups(null);
                ucDeviceSystem1.setUcSystemParams(null);
            }

            return ucDeviceSystemList;
        } catch (Exception e) {
            logger.error("----getSubSystem------", e);
        }
        return ucDeviceSystemList;
    }

    /**
     * 周视图
     *
     * @param httpServletRequest
     * @param model
     * @return
     */
    @RequestMapping(value = "/calendar/showWeekCalendar")
    public String showWeekCalendar(HttpServletRequest httpServletRequest, Model model) {

        String systemId = httpServletRequest.getParameter("systemId");
        String nowDate = httpServletRequest.getParameter("nowDate");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

        if (nowDate == null || "".equals(nowDate)) {
            nowDate = simpleDateFormat.format(new Date());
        }
        if (systemId == null || "".equals(systemId) || PatternConst.SYSTEM_CODE_MAHU.equals(systemId)) {
            systemId = PatternConst.SYSTEM_CODE_HVAC;
        }
        //根据今天日期取今天的开气、节日、日出、日落
        UcCalendar ucCalendar = patternService.findUcCalendarById(nowDate);
        model.addAttribute("calendar", ucCalendar);

        UcWeather ucWeather = patternService.findWeatherByCalendarAndNew(nowDate, PatternConst.WEATHER_IS_NEW_YES);

        //目前只支持一个
        model.addAttribute("weather", ucWeather);

        //取事件
        //今天的日期
        DateTime nowDateTime = new DateTime();
        model.addAttribute("year", nowDateTime.getYear());
        model.addAttribute("month", nowDateTime.getMonthOfYear());
        model.addAttribute("day", nowDateTime.getDayOfMonth());
        model.addAttribute("hour", nowDateTime.getHourOfDay());
        model.addAttribute("minute", nowDateTime.getMinuteOfHour());

        DateTime sunrise = DateTime.parse(ucWeather.getSunrise(), DateTimeFormat.forPattern("HH:mm"));
        DateTime sunset = DateTime.parse(ucWeather.getSunset(), DateTimeFormat.forPattern("HH:mm"));

        model.addAttribute("sunriseH", sunrise.toString("HH"));
        model.addAttribute("sunriseM", sunrise.toString("mm"));
        model.addAttribute("sunsetH", sunset.toString("HH"));
        model.addAttribute("sunsetM", sunset.toString("mm"));


        model.addAttribute("elementvalue", systemId);
        model.addAttribute("systemCode", systemId);
        return "calendar/weekCalendar";
    }

    /**
     * 月视图
     *
     * @param httpServletRequest
     * @param model
     * @return
     */
    @RequestMapping(value = "/calendar/showMonthCalendar")
    public String showMonthCalendar(HttpServletRequest httpServletRequest, Model model) {

        String systemId = httpServletRequest.getParameter("systemId");
        String nowDate = httpServletRequest.getParameter("nowDate");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");

        if (nowDate == null || "".equals(nowDate)) {

            nowDate = simpleDateFormat.format(new Date());
        }
        if (systemId == null || "".equals(systemId) || PatternConst.SYSTEM_CODE_MAHU.equals(systemId)) {
            systemId = PatternConst.SYSTEM_CODE_HVAC;
        }
        //根据今天日期取今天的开气、节日、日出、日落
        UcCalendar ucCalendar = patternService.findUcCalendarById(nowDate);
        model.addAttribute("calendar", ucCalendar);

        //目前只支持一个
        UcWeather ucWeather = patternService.findWeatherByCalendarAndNew(nowDate, PatternConst.WEATHER_IS_NEW_YES);

        model.addAttribute("weather", ucWeather);

        //取事件
        //今天的日期
        DateTime nowDateTime = new DateTime();
        model.addAttribute("year", nowDateTime.getYear());
        model.addAttribute("month", nowDateTime.getMonthOfYear());
        model.addAttribute("day", nowDateTime.getDayOfMonth());
        model.addAttribute("hour", nowDateTime.getHourOfDay());
        model.addAttribute("minute", nowDateTime.getMinuteOfHour());

        DateTime sunrise = DateTime.parse(ucWeather.getSunrise(), DateTimeFormat.forPattern("HH:mm"));
        DateTime sunset = DateTime.parse(ucWeather.getSunset(), DateTimeFormat.forPattern("HH:mm"));

        model.addAttribute("sunriseH", sunrise.toString("HH"));
        model.addAttribute("sunriseM", sunrise.toString("mm"));
        model.addAttribute("sunsetH", sunset.toString("HH"));
        model.addAttribute("sunsetM", sunset.toString("mm"));


        model.addAttribute("elementvalue", systemId);
        model.addAttribute("systemCode", systemId);
        return "calendar/monthCalendar";
    }

    /**
     * 获取日程表的信息
     * 某一天的事件、模式、天气封装
     *
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/calendar/getScheduler")
    @ResponseBody
    public Object getScheduler(HttpServletRequest httpServletRequest) {

        List<PatternJSON> jsonList = new ArrayList<PatternJSON>();

        try {
            String systemId = httpServletRequest.getParameter("systemId");
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            String startDate = httpServletRequest.getParameter("startDate");
            String endDate = httpServletRequest.getParameter("endDate");

            if (startDate == null || "".equals(startDate)) {
                startDate = simpleDateFormat.format(new Date());
            }
            if (endDate == null || "".equals(endDate)) {
                endDate = simpleDateFormat.format(new Date());
            }
            if (systemId == null || "".equals(systemId) || PatternConst.SYSTEM_CODE_MAHU.equals(systemId)) {
                systemId = PatternConst.SYSTEM_CODE_HVAC;
            }

            String referer = httpServletRequest.getHeader("Referer");
            String pDate = "";
            if (referer.contains("Month")) {
                if (systemId.equals(PatternConst.SYSTEM_CODE_HVAC)) {
                    systemId = PatternConst.SYSTEM_CODE_MAHU;
                }
                pDate = PatternConst.SYSTEM_VIEW_MONTH;  //日视图和周视图要计算模式运行的时间段
            }
            //根据今天日期取今天的开气、节日、日出、日落
            List<UcCalendar> ucCalendars = patternService.findUcCalendarByCalendar(startDate, endDate);

            logger.debug("-----ucCalendars----" + ucCalendars.size());

            Date startTime = DateTime.parse(startDate, DateTimeFormat.forPattern("yyyyMMdd")).plusSeconds(-1).toDate();
            Date endTime = DateTime.parse(endDate, DateTimeFormat.forPattern("yyyyMMdd")).plusDays(1).plusSeconds(-1).toDate();

            logger.debug("-----startTime----" + startTime);
            logger.debug("-----endTime----" + endTime);
            //查询今天的事件
            List<Event> events = calendarService.findEventByStartDateAndEndDate(startTime, endTime);
            logger.debug("--query---systemId----" + systemId);
            //查询运行的模式
            List<UcPatternRecord> ucPatternRecordList = new ArrayList<UcPatternRecord>();
            if (systemId != null && !"".equals(systemId)) {
                ucPatternRecordList = patternService.findSubSystemPattern(pDate, systemId, startDate, endDate);
            } else {

                List<UcDeviceSystem> ucDeviceSystemList = patternService.findRunPatternSystem();
                for (UcDeviceSystem ucDeviceSystem : ucDeviceSystemList) {
                    List<UcPatternRecord> ucPatternRecords = patternService.findSubSystemPattern("", ucDeviceSystem.getId(), startDate, endDate);
                    if (ucPatternRecords != null) {
                        ucPatternRecordList.addAll(ucPatternRecords);
                    }
                }

            }
            logger.debug("-----events--3--" + events.size());
            logger.debug("--FINISHI---ucCalendars---START-3");
//            logger.debug("-----ucCalendars----" + ucCalendars.size());
            for (UcCalendar ucCalendar : ucCalendars) {

                PatternJSON patternJSON = new PatternJSON();
                patternJSON.setDate(ucCalendar.getId());
                patternJSON.setUcCalendar(ucCalendar);

                //将天气信息只保留标志为最新的一条记录  isNew=Y
                Set<UcWeather> ucWeatherSet = new HashSet<UcWeather>();
                Set<UcWeather> ucWeathers = ucCalendar.getUcWeathers();
                UcWeather ucWeather = null;
                for (UcWeather weather : ucWeathers) {
                    if (weather.getIsNew() == null || PatternConst.WEATHER_IS_NEW_NO.equals(weather.getIsNew())) {

                    } else {
                        ucWeather = weather;
                        ucWeatherSet.add(ucWeather);
                    }
                }
                ucCalendar.getUcWeathers().clear();
                ucCalendar.setUcWeathers(ucWeatherSet);

                //查询当天的事件
                for (Event event : events) {
                    Date start = event.getStartDate();
                    Date end = event.getEndDate();

                    Date caStartTime = DateTime.parse(ucCalendar.getId(), DateTimeFormat.forPattern("yyyyMMdd")).plusSeconds(-1).toDate();
                    Date caEndTime = DateTime.parse(ucCalendar.getId(), DateTimeFormat.forPattern("yyyyMMdd")).plusDays(1).plusSeconds(-1).toDate();
                    logger.debug("-----start----" + start + "----" + end);
//                    logger.debug("-----caStartTime----" + caStartTime + "----" + caEndTime);

                    if (start.after(caStartTime) && start.before(caEndTime)) {
                        patternJSON.getEventList().add(event);
                    } else if (end.after(caStartTime) && end.before(caEndTime)) {
                        patternJSON.getEventList().add(event);
                    } else if (start.before(caStartTime) && end.after(caEndTime)) {
                        patternJSON.getEventList().add(event);
                    }
                }

                //将当天的模式加入
                if (ucPatternRecordList != null) {
                    for (UcPatternRecord ucPatternRecord : ucPatternRecordList) {
                        String ucId = ucPatternRecord.getUcCalendar().getId();
                        if (ucId.equals(ucCalendar.getId())) {
                            if (pDate != null && pDate.equals(PatternConst.SYSTEM_VIEW_MONTH)) {   //日视图和周视图要计算模式运行的时间段
                                patternJSON.getUcPatternRecords().add(ucPatternRecord);
                            } else {
                                //计算设备的运行时间，过滤掉其它设备
                                UcPatternRecord filterPattern = patternService.calculateDeviceRunTime(ucPatternRecord, ucWeather);
                                filterPattern.setUcCalendar(ucCalendar);
                                patternJSON.getUcPatternRecords().add(filterPattern);
                            }
                        }
                    }
                }

                jsonList.add(patternJSON);
            }
            return jsonList;
        } catch (Exception e) {
            logger.error("-----------", e);
        }
        return jsonList;
    }


    /**
     * 日程表
     *
     * @param httpServletRequest
     * @param model
     * @return
     */
    @RequestMapping(value = "/calendar/showScheduler")
    public String showScheduler(HttpServletRequest httpServletRequest, Model model) {

        //前天传过来的参数
        String systemId = httpServletRequest.getParameter("systemId");
        String nowDate = httpServletRequest.getParameter("nowDate");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");


        if (nowDate == null || "".equals(nowDate)) {
            nowDate = simpleDateFormat.format(new Date());
        }
        if (systemId == null || "".equals(systemId)) {
            systemId = PatternConst.SYSTEM_CODE_HVAC;
        }
        //根据今天日期取今天的开气、节日、日出、日落
        UcCalendar ucCalendar = patternService.findUcCalendarById(nowDate);
        model.addAttribute("calendar", ucCalendar);

        UcWeather ucWeather = patternService.findWeatherByCalendarAndNew(nowDate, PatternConst.WEATHER_IS_NEW_YES);
        model.addAttribute("weather", ucWeather);

        //取事件
        //今天的日期
        DateTime nowDateTime = new DateTime();
        model.addAttribute("year", nowDateTime.getYear());
        model.addAttribute("month", nowDateTime.getMonthOfYear());
        model.addAttribute("day", nowDateTime.getDayOfMonth());
        model.addAttribute("hour", nowDateTime.getHourOfDay());
        model.addAttribute("minute", nowDateTime.getMinuteOfHour());

        DateTime sunrise = DateTime.parse(ucWeather.getSunrise(), DateTimeFormat.forPattern("HH:mm"));
        DateTime sunset = DateTime.parse(ucWeather.getSunset(), DateTimeFormat.forPattern("HH:mm"));

        model.addAttribute("sunriseH", sunrise.toString("HH"));
        model.addAttribute("sunriseM", sunrise.toString("mm"));
        model.addAttribute("sunsetH", sunset.toString("HH"));
        model.addAttribute("sunsetM", sunset.toString("mm"));


        model.addAttribute("systemId", systemId);
        model.addAttribute("elementvalue", systemId);
        return "calendar/schedule";
    }


    /**
     * 保存天气
     *
     * @param httpServletRequest
     * @param model
     * @return
     */
    @RequestMapping(value = "/calendar/saveWeather", method = RequestMethod.POST)
    @ResponseBody
    public Message saveWeather(HttpServletRequest httpServletRequest, Model model) {
        Message message = new Message();
        try {
            UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            //前天传过来的参数
            String startDate = httpServletRequest.getParameter("startDate");
            String endDate = httpServletRequest.getParameter("endDate");
            String weather = httpServletRequest.getParameter("weather");
            String weatherIcon = httpServletRequest.getParameter("weatherIcon");
            String weatherDescription = httpServletRequest.getParameter("weatherDescription");
            String minTemperature = httpServletRequest.getParameter("minTemperature");
            String maxTemperature = httpServletRequest.getParameter("maxTemperature");
            String sunrise = httpServletRequest.getParameter("sunrise");
            String sunset = httpServletRequest.getParameter("sunset");
            String humidity = httpServletRequest.getParameter("humidity");

            UcWeather ucWeather = new UcWeather();
            ucWeather.setWeatherDescription(weatherDescription);
            ucWeather.setSunset(sunset);
            ucWeather.setSunrise(sunrise);
            ucWeather.setHumidity(humidity);
            if (maxTemperature != null && !"".equals(maxTemperature)) {
                ucWeather.setMaxTemperature(Integer.parseInt(maxTemperature));
            }
            if (minTemperature != null && !"".equals(minTemperature)) {
                ucWeather.setMinTemperature(Integer.parseInt(minTemperature));
            }
            if (weatherIcon != null && !"".equals(weatherIcon)) {
                ucWeather.setWeatherIcon(weatherIcon);
            }
            if (weather != null && !"".equals(weather)) {
                ucWeather.setWeather(weather);
            } else {
                if (weatherDescription.equals(PatternConst.WEATHER_TYPE_SUNNY)) {
                    ucWeather.setWeather(PatternConst.PATTERN_WEATHER_TYPE_SUNNY);
                } else {
                    ucWeather.setWeather(PatternConst.PATTERN_WEATHER_TYPE_CLOUDY);
                }
            }
            logger.debug("-------ucWeather------" + ucWeather.toString());
            patternService.saveUcWeathers(startDate, endDate, ucWeather);
            logger.info("修改天气信息");
            opLogService.saveLogByUserAndMsg(user.getLoginid(), user.getLoginid() + "修改天气信息", "", "", "PatternController", PatternConst.LOG_TYPE_WEATER);
            message.setSuccess(true);
            message.setMsg(PatternConst.JSON_SUCCESS);
            return message;

        } catch (Exception e) {
            logger.error("----------", e);
        }
        message.setSuccess(false);
        message.setMsg(PatternConst.JSON_FAILURE);
        return message;
    }

    /**
     * 保存模式的预设
     *
     * @param httpServletRequest
     * @param model
     * @return
     */
    @RequestMapping(value = "/calendar/savePredictPattern", method = RequestMethod.POST)
    @ResponseBody
    public Object savePredictPattern(HttpServletRequest httpServletRequest, Model model) {
        Message message = new Message();
        try {
            //前天传过来的参数
            String startDate = httpServletRequest.getParameter("startDate");
            String endDate = httpServletRequest.getParameter("endDate");
            String patternId = httpServletRequest.getParameter("patternId");
            UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            logger.debug("--patternId--" + patternId);
            patternService.savePredictPattern(startDate, endDate, patternId);
            logger.info(user.getLoginid() + "预设模式信息" + startDate + "--" + endDate);

            opLogService.saveLogByUserAndMsg(user.getLoginid(), user.getLoginid() + "预设模式信息", "", "", "PatternController", PatternConst.LOG_TYPE_PATTERN);

            message.setSuccess(true);
            message.setMsg(PatternConst.JSON_SUCCESS);
            return message;

        } catch (Exception e) {
            logger.error("-----------", e);
        }
        message.setSuccess(false);
        message.setMsg(PatternConst.JSON_FAILURE);
        return message;
    }

    /**
     * 保存假日
     *
     * @param httpServletRequest
     * @param model
     * @return
     */
    @RequestMapping(value = "/calendar/saveHoliday", method = RequestMethod.POST)
    @ResponseBody
    public Object saveHoliday(HttpServletRequest httpServletRequest, Model model) {
        Message message = new Message();
        try {
            //前天传过来的参数
            String startDate = httpServletRequest.getParameter("startDate");
            String endDate = httpServletRequest.getParameter("endDate");
            String holidayName = httpServletRequest.getParameter("holidayName");
            String holidayType = httpServletRequest.getParameter("holidayType");
            UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            patternService.saveUcHolidays(startDate, endDate, holidayName, holidayType);
            logger.info(user.getLoginid() + "修改假日信息" + startDate + "--" + endDate);
            opLogService.saveLogByUserAndMsg(user.getLoginid(), user.getLoginid() + "修改假日信息", "", "", "PatternController", PatternConst.LOG_TYPE_HOLIDAY);
            message.setSuccess(true);
            message.setMsg(PatternConst.JSON_SUCCESS);
            return message;

        } catch (Exception e) {
            logger.error("-----------", e);
        }
        message.setSuccess(false);
        message.setMsg(PatternConst.JSON_FAILURE);
        return message;
    }


    /**
     * 保存事件
     *
     * @param httpServletRequest
     * @return 成功或失败的JSON
     */
    @RequestMapping(value = "/calendar/saveEvent", method = RequestMethod.POST)
    @ResponseBody
    public Object saveEvent(HttpServletRequest httpServletRequest) {
        Message message = new Message();
        try {

            String title = httpServletRequest.getParameter("title");
            String all_day = httpServletRequest.getParameter("all_day");
            String start = httpServletRequest.getParameter("startDate");
            String end = httpServletRequest.getParameter("endDate");
            String startTime = httpServletRequest.getParameter("startTime");
            String endTime = httpServletRequest.getParameter("endTime");
            String description = httpServletRequest.getParameter("description");
            String organizer = httpServletRequest.getParameter("organizer");
            String eventAddress = httpServletRequest.getParameter("eventAddress");
            String eventType = httpServletRequest.getParameter("eventType");
            String id = httpServletRequest.getParameter("id");

            UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            logger.debug("-------save----event-----" + title + "----" + all_day + "----" + start + "----" + eventType + "----" + end + "----" + description + "");

            Event event = new Event();
            event.setStartDate(DateTime.parse(start, DateTimeFormat.forPattern("yyyyMMdd")).toDate());
            event.setEndDate(DateTime.parse(end, DateTimeFormat.forPattern("yyyyMMdd")).toDate());
            event.setStartTime(DateTime.parse(startTime, DateTimeFormat.forPattern("HHmm")).toDate());
            event.setEndTime(DateTime.parse(endTime, DateTimeFormat.forPattern("HHmm")).toDate());
            event.setAllDay(Boolean.parseBoolean(all_day));
            event.setTitle(title);
            if (id != null && !"".equals(id)) {
                event.setId(Long.parseLong(id));
            }

            EventType eventType1 = new EventType();
            eventType1.setTypeId(Long.parseLong("18"));   //todo 未完成
            event.setEventType(eventType1);
            event.setOrganizer(organizer);
            event.setEventAddress(eventAddress);
            event.setDescription(description);
            calendarService.saveEvent(event);
            logger.info(user.getLoginid() + "修改事件信息" + start + "--" + end);
            opLogService.saveLogByUserAndMsg(user.getLoginid(), user.getLoginid() + "编辑活动信息", "", "", "PatternController", PatternConst.LOG_TYPE_EVENT);
            message.setSuccess(true);
            message.setMsg(PatternConst.JSON_SUCCESS);
            return message;
        } catch (Exception e) {
            logger.error("--------------", e);
        }
        message.setSuccess(false);
        message.setMsg(PatternConst.JSON_FAILURE);
        return message;
    }

    /**
     * 删除事件
     *
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/calendar/delEvent", method = RequestMethod.POST)
    @ResponseBody
    public Object delEvent(HttpServletRequest httpServletRequest) {
        Message message = new Message();
        try {

            String id = httpServletRequest.getParameter("id");
            logger.debug("-------delEvent----event-----" + id);
            UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            if (id != null && !"".equals(id)) {
                calendarService.deleteEvent(id);
                opLogService.saveLogByUserAndMsg(user.getLoginid(), user.getLoginid() + "删除活动", "", "", "PatternController", PatternConst.LOG_TYPE_EVENT);
                message.setSuccess(true);
                message.setMsg(PatternConst.JSON_SUCCESS);
                return message;
            }
        } catch (Exception e) {
            logger.error("--------------", e);
        }
        message.setSuccess(false);
        message.setMsg(PatternConst.JSON_FAILURE);
        return message;
    }

    /**
     * 清除假日
     *
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/calendar/clearHoliday", method = RequestMethod.POST)
    @ResponseBody
    public Object clearHoliday(HttpServletRequest httpServletRequest) {
        Message message = new Message();
        try {

            String startDate = httpServletRequest.getParameter("startDate");
            String endDate = httpServletRequest.getParameter("endDate");
            logger.debug("-------delHoliday-----" + endDate + "---" + startDate);
            UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            if (startDate != null && !"".equals(startDate) && endDate != null && !"".equals(endDate)) {
                patternService.clearHoliday(startDate, endDate);
                logger.info(user.getLoginid() + "清除假日信息" + startDate + "--" + endDate);
                opLogService.saveLogByUserAndMsg(user.getLoginid(), user.getLoginid() + "删除假日", "", "", "PatternController", PatternConst.LOG_TYPE_HOLIDAY);
                message.setSuccess(true);
                message.setMsg(PatternConst.JSON_SUCCESS);
                return message;
            }
        } catch (Exception e) {
            logger.error("--------------", e);
        }
        message.setSuccess(false);
        message.setMsg(PatternConst.JSON_FAILURE);
        return message;
    }

    /**
     * 清除活动
     *
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/calendar/clearEvent", method = RequestMethod.POST)
    @ResponseBody
    public Object clearEvent(HttpServletRequest httpServletRequest) {
        Message message = new Message();
        try {
            UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            String id = httpServletRequest.getParameter("id");
            String startDate = httpServletRequest.getParameter("startDate");
            String endDate = httpServletRequest.getParameter("endDate");
            logger.debug("-------clearEvent-----" + endDate + "---" + startDate);

            if (startDate != null && !"".equals(startDate) && endDate != null && !"".equals(endDate)) {
                if (id == null || "".equals(id)) {
                    calendarService.clearEvent(startDate, endDate);
                } else {
                    calendarService.clearEvent(id, startDate, endDate);
                }
                logger.info(user.getLoginid() + "清除活动信息" + startDate + "--" + endDate);
                opLogService.saveLogByUserAndMsg(user.getLoginid(), user.getLoginid() + "删除活动", "", "", "PatternController", PatternConst.LOG_TYPE_HOLIDAY);
                message.setSuccess(true);
                message.setMsg(PatternConst.JSON_SUCCESS);
                return message;
            }
        } catch (Exception e) {
            logger.error("--------------", e);
        }
        message.setSuccess(false);
        message.setMsg(PatternConst.JSON_FAILURE);
        return message;
    }

    /**
     * 获取今天的活动、节日信息
     *
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/calendar/getTodayEvent", method = RequestMethod.GET)
    @ResponseBody
    public Object getTodayEvent(HttpServletRequest httpServletRequest) {
        List<ItemJSON> itemJSONs = new ArrayList<ItemJSON>();
        try {
            //查询今天的事件
            Date nowDate = new Date();
            List<Event> events = calendarService.findEventByStartDateAndEndDate(nowDate, nowDate);
            for (Event event : events) {
                if (event.getTitle() != null && !"".equals(event.getTitle())) {
                    ItemJSON itemJSON = new ItemJSON();
                    itemJSON.setName(event.getTitle());
                    itemJSONs.add(itemJSON);
                }
            }
            UcCalendar ucCalendar = patternService.findUcCalendarById(simpleDateFormat.format(nowDate));
            Set<UcHoliday> ucHolidays = ucCalendar.getUcHolidays();
            for (UcHoliday ucHoliday : ucHolidays) {
                if (ucHoliday.getHolidayName() != null && !"".equals(ucHoliday.getHolidayName())) {
                    ItemJSON itemJSON = new ItemJSON();
                    itemJSON.setName(ucHoliday.getHolidayName());
                    itemJSONs.add(itemJSON);
                }
            }

            return itemJSONs;
        } catch (Exception e) {
            logger.error("--------------", e);
        }
        return itemJSONs;
    }

    /**
     * 设置重大活动的内容
     *
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/calendar/eventImportent", method = RequestMethod.GET)
    @ResponseBody
    public Object eventImportent(HttpServletRequest httpServletRequest) {

        try {
            String commandUrl = config.getProps().getProperty("sendCommandUrl");
            //查询今天的天气信息
            UcWeather ucWeather = patternService.findWeatherByCalendarAndNew(DateTime.now().toString("yyyyMMdd"), PatternConst.WEATHER_IS_NEW_YES);
            String temperature = config.getProps().getProperty("event.temperature");
            String minTemperature = config.getProps().getProperty("event.minTemperature");
            String maxTemperature = config.getProps().getProperty("event.maxTemperature");
            String humidity = config.getProps().getProperty("event.humidity");
            String weatherDescription = config.getProps().getProperty("event.weatherDescription");
            SendCommandToSystem.sendCommand(commandUrl, temperature, ucWeather.getTemperature());
            SendCommandToSystem.sendCommand(commandUrl, minTemperature, ucWeather.getMinTemperature().toString());
            SendCommandToSystem.sendCommand(commandUrl, maxTemperature, ucWeather.getMaxTemperature().toString());
            SendCommandToSystem.sendCommand(commandUrl, humidity, ucWeather.getHumidity());
            logger.debug("---weather icon  cs--1--" + ucWeather.getWeatherIcon());

            String icon = PatternConst.getCSWeatherIcon(ucWeather.getWeatherIcon());
            logger.debug("---weather icon  cs----" + icon);
            SendCommandToSystem.sendCommand(commandUrl, weatherDescription, icon);

            String pmUrl = config.getProps().getProperty("eventpm25.url");
            GetMethod postMethod = new GetMethod(pmUrl);
            HttpMethodParams param = postMethod.getParams();
            param.setContentCharset("UTF-8");
            //添加头信息
            List<Header> headers = new ArrayList<Header>();
            headers.add(new Header("Referer", pmUrl));
            headers.add(new Header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0"));
            HttpClient client = new HttpClient();
            client.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
            client.executeMethod(postMethod);
            String result = postMethod.getResponseBodyAsString();
            if (result != null) {
                try {
                    int a = result.indexOf("span12 data");
                    result = result.substring(a);
                    a = result.indexOf("AQI");
                    result = result.substring(a + 3);
                    a = result.indexOf("value\">");
                    result = result.substring(a + 3);
                    a = result.indexOf(">");
                    int b = result.indexOf("<");
                    result = result.substring(a + 1, b).trim();
                    String pm25 = config.getProps().getProperty("event.pm25");
                    SendCommandToSystem.sendCommand(commandUrl, pm25, result);
                } catch (Exception e) {
                    logger.error("-pm25--", e);
                }
            }
            //设置活动的内容
            String now = DateTime.now().toString("yyyyMMdd");
            Date beforDate = DateTime.parse(now, DateTimeFormat.forPattern("yyyyMMdd")).plusDays(-1).toDate();

            Date aferDate = DateTime.parse(now, DateTimeFormat.forPattern("yyyyMMdd")).plusDays(1).toDate();
            List<Event> eventList = calendarService.findEventByEventTypeAndStartDateAndEndDate(18, beforDate, aferDate);
            if (eventList != null && eventList.size() > 0) {
                Event event = eventList.get(0);
                String name = config.getProps().getProperty("event.name");
                String startDate = config.getProps().getProperty("event.startDate");
                String endDate = config.getProps().getProperty("event.endDate");
                String address = config.getProps().getProperty("event.address");
                String organizer = config.getProps().getProperty("event.organizer");
                SendCommandToSystem.sendCommand(commandUrl, name, event.getTitle());
                String sd = simpleDateFormat.format(event.getStartDate()) + " " + timeFormat.format(event.getStartTime());
                String ed = simpleDateFormat.format(event.getEndDate()) + " " + timeFormat.format(event.getEndTime());
                SendCommandToSystem.sendCommand(commandUrl, startDate, sd);
                SendCommandToSystem.sendCommand(commandUrl, endDate, ed);
                SendCommandToSystem.sendCommand(commandUrl, address, event.getEventAddress());
                SendCommandToSystem.sendCommand(commandUrl, organizer, event.getOrganizer());
            }
            //设置室内照明的模式的名称
            List<UcPatternRecord> ucPatternRecordList = patternService.findUcPatternRecordBySystemIdAndDateLazy(PatternConst.SYSTEM_CODE_LSPUB, now);
            if (ucPatternRecordList != null && ucPatternRecordList.size() > 0) {
                for (UcPatternRecord ucPatternRecord : ucPatternRecordList) {
                    //不是特例
                    if (!ucPatternRecord.getPatternType().equals(PatternConst.PATTERN_TYPE_SPECIAL)) {
                        String name = config.getProps().getProperty("event.name");
                        SendCommandToSystem.sendCommand(commandUrl, name, ucPatternRecord.getPatternName());
                    }
                }
            }

        } catch (Exception e) {
            logger.error("--------------", e);
        }
        return "";
    }
}
