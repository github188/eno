package com.energicube.eno.common;

import com.energicube.eno.common.dto.DeviceCommand;
import com.energicube.eno.common.weather.Weather;
import com.energicube.eno.monitor.model.UcEventLog;
import com.energicube.eno.monitor.service.OpLogService;
import com.energicube.eno.pattern.service.PatternService;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.quartz.*;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-8-19
 * Time: 下午5:22
 * 关于模式的时间任务
 */
public class PatternTimeTask {

    private static Log logger = LogFactory.getLog(PatternTimeTask.class);

    private Config config = new Config();

    private  ManualProperties manualProperties=ManualProperties.getInstance();

    @Resource
    private PatternService patternService;

    @Resource
    private OpLogService opLogService;

    public void setPatternService(PatternService patternService) {
        this.patternService = patternService;
    }

    @Resource
    private Scheduler scheduler;

    // 设值注入，通过setter方法传入被调用者的实例scheduler
    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    private int runNum = 0;

    /**
     * 执行会务命令列表，每分钟一次
     */
    public void getDayCommand() {
        try {

            WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
            Object obj = wac.getServletContext().getAttribute(PatternConst.CONTEXT_COMMAND_DAY_LIST);
//            logger.debug("------PatternTimeTask---1---getDayCommand---" + obj);
            if (obj != null) {
                List<DeviceCommand> deviceCommandList = (List<DeviceCommand>) obj;

                long nowDateLong = System.currentTimeMillis();
                for (DeviceCommand deviceCommand : deviceCommandList) {
                    Date sendDate = deviceCommand.getSendTime();
                    long sendLong = sendDate.getTime();
                    //时间相同或是在1分钟之内
//                    logger.info("------PatternTimeTask---2---time---"+sendLong+"-------"+nowDateLong );
                    if (nowDateLong == sendLong || (nowDateLong > sendLong && nowDateLong < sendLong + 59000)) {

                        //未被执行
                        if (deviceCommand.getExecuteStatus().equals(PatternConst.DEVICE_EXECUTE_NO)) {

                            //是否策略命令
                            if (deviceCommand.getStrategy().equals(PatternConst.IS_STRATEGY_YES)) {
                                //不需要处理，策略的命令单独处理。
                            } else {
                                //未执行过此命令
                                logger.info("------PatternTimeTask-----execute---" + new DateTime(deviceCommand.getSendTime()).toString("yyyy-MM-dd HH:mm:ss") + "--" + deviceCommand.getSystemId() + "---" + deviceCommand.getDeviceId() + "---" + deviceCommand.getStatus());
                                SendCommandToSystem.sendCommand(deviceCommand);
//                              if(PatternConst.SYSTEM_CODE_LSPUB.equals(deviceCommand.getSystemId()) || PatternConst.SYSTEM_CODE_LSN.equals(deviceCommand.getSystemId())){
//                                 //设置C/S界面的按钮的样式
//                                  patternService.setCSButtonStyle(deviceCommand.getSystemId());
//                              }
                            }
                        }
                    }
                }
            } else {
                // 没有生成一天的时间表 ，重新生成
                if (runNum == 0) {
                    runNum = 1;
//                    logger.info("--------start-----------"+System.currentTimeMillis());
                    Date nowDate = new Date();
                    List<DeviceCommand> deviceCommandList = patternService.findDayTask(nowDate);

//                    logger.info("--------end-----------"+System.currentTimeMillis());
                    wac.getServletContext().setAttribute(PatternConst.CONTEXT_COMMAND_DAY_LIST, deviceCommandList);
                    //用于命令生成的模版，只是用每个设备的tagID,因为查询会很慢，如果查询够快，可以去掉
                    HashMap<String, DeviceCommand> commandHashMap = new HashMap<String, DeviceCommand>();
                    for (DeviceCommand command : deviceCommandList) {
                        commandHashMap.put(command.getDeviceId(), command);
                    }
                    wac.getServletContext().setAttribute(PatternConst.CONTEXT_COMMAND_TEMPLATE, commandHashMap);

                    String fileName = config.getProps().getProperty("manualDeviceFileName");
                    String devices = ManualProperties.getValue(this.getClass().getClassLoader().getResource("")+"/"+fileName, PatternConst.MANUAL_DEVICE_ID_KEY);
                    diffManualCommand(devices, deviceCommandList);
                }

            }
        } catch (Exception ex) {
            //todo 有错误，警告用户模式运行出现错误
            logger.error("------------", ex);
        }
    }

    /**
     * 标识手动的命令
     *
     * @param devices 手动的设备ID
     * @param base    命令列表
     */
    private void diffManualCommand(String devices, List<DeviceCommand> base) {

        if (devices != null && !"".equals(devices)) {
            String[] deviceIds = devices.split(",");
            for (DeviceCommand deviceCommand : base) {
                String dId = deviceCommand.getDeviceId();
                for (String id : deviceIds) {
                    if (dId.equals(id)) {
                        deviceCommand.setExecuteStatus(PatternConst.DEVICE_EXECUTE_COVER);
                    }
                }
            }
        }

    }

    /**
     * 每天要执行的任务
     * 生成当天的时间任务列表
     */
    public void doDayWork() {
        try {
            logger.debug("------doDayWork---------");
            Date nowDate = new Date();
            List<DeviceCommand> deviceCommandList = patternService.findDayTask(nowDate);
            WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
            wac.getServletContext().setAttribute(PatternConst.CONTEXT_COMMAND_DAY_LIST, deviceCommandList);

            int i = 1;
            //通过SchedulerFactory来获取一个调度器
            // Grab the Scheduler instance from the Factory
            //  Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            for (DeviceCommand deviceCommand : deviceCommandList) {
                String isStrategy = deviceCommand.getStrategy();
                if (isStrategy.equals(PatternConst.IS_STRATEGY_YES)) {

                    JobDataMap jobDataMap = new JobDataMap();
                    jobDataMap.put("patternService", patternService);
                    jobDataMap.put("deviceCommand", deviceCommand);

                    //是策略命令
                    String name = "command" + i;
                    String triggerName = "trigger" + i;

                    // 获取一个任务
                    JobDetail job = JobBuilder.newJob(StrategyTask.class).withIdentity(name, "strategy").setJobData(jobDataMap).build();

                    //设置一个触发器 设置开始时间、结束时间、间隔多长时间执行一次
                    String st = deviceCommand.getStartDate();
                    String ed = deviceCommand.getEndDate();
                    SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder.newTrigger().withIdentity(triggerName, "strategy").startAt(DateTime.parse(st, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate()).endAt(DateTime.parse(ed, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate()).withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(300)).forJob(job).build();

                    //已经存在同名触发器，删除触发器
                    boolean isExistTrigger = scheduler.checkExists(trigger.getKey());
                    if (isExistTrigger) {
                        scheduler.unscheduleJob(trigger.getKey());
                    }
                    //已经存在同名任务，删除JOB
                    boolean isExistJob = scheduler.checkExists(job.getKey());
                    if (isExistJob) {
                        scheduler.deleteJob(job.getKey());
                    }

                    // Tell quartz to schedule the job using our trigger
                    scheduler.scheduleJob(job, trigger);
                    i++;
                }
            }
            //任务启动
            scheduler.start();

        } catch (Exception e) {
            logger.error("-----生成当天的时间命令列表错误-----------", e);
        }
    }


    /**
     * 每年要执行的任务
     * 生成明年的日历
     */
    public void doYearWork() {
        //获得明年的年份

        DateTime dateTime = new DateTime();
        int year = dateTime.getYear();
        dateTime = dateTime.withDate(dateTime.getYear() + 1, 1, 1);
        boolean isFinish = true;
        do {
            patternService.saveCalendar(dateTime);
            dateTime = dateTime.plusDays(1);
            int tempYear = dateTime.getYear();
            //判断是否完成所有
            logger.debug("------------" + tempYear + "----------" + year);
            if (tempYear == year + 2) {
                //循环完成
                isFinish = false;
            }
        } while (isFinish);
    }


    /**
     * 每天要执行的任务
     * 更新天气信息
     */
    public void doFindWeather() {
        try {
            logger.debug("------doFindWeather---------");
            //更新未来9天的信息
            String forcastUrl = config.getProps().getProperty("weatherForecast");
            Weather weather = findJsonWeather(forcastUrl);
            if (weather != null) {
                logger.debug("------doFindWeather--weatherForecast-------" + weather.toString());
                patternService.updateWeatherForTimeTask(weather, PatternConst.GET_WEATHER_FORCAST);
            } else {
                //todo 获取天气信息错误
            }
            //更新当天的天气信息;
            String currentUrl = config.getProps().getProperty("weatherConditions");
            Weather weather1 = findJsonWeather(currentUrl);
            if (weather1 != null) {
                logger.debug("------doFindWeather--weatherConditions-------" + weather1.toString());
                patternService.updateWeatherForTimeTask(weather1, PatternConst.GET_WEATHER_CURRENT);
            } else {
                //todo 获取天气信息错误
            }
        } catch (Exception e) {
            logger.error("-----获取天气信息-----------", e);
        }
    }

    /**
     * 每天要执行的任务
     * 更新昨天的天气信息
     */
    public void doUpdateYesterDayWeather() {
        try {
            logger.debug("------doFindWeather---------");
            //更新未来9天的信息
            String yesterdayUrl = config.getProps().getProperty("weatherYesterday");
            Weather weather = findJsonWeather(yesterdayUrl);
            if (weather != null) {
                patternService.updateWeatherForTimeTask(weather, PatternConst.GET_WEATHER_YESTERDAY);
            } else {
                //todo 获取天气信息错误
            }
        } catch (Exception e) {
            logger.error("-----获取天气信息-----------", e);
        }
    }


    /**
     * 获取天气信息
     *
     * @param url 请求的URL
     * @return 封装的天气对象
     */
    public Weather findJsonWeather(String url) {
        Weather weather = null;
        try {
            GetMethod postMethod = new GetMethod(url);
            HttpMethodParams param = postMethod.getParams();
            param.setContentCharset("UTF-8");
            //添加头信息
            List<Header> headers = new ArrayList<Header>();
            headers.add(new Header("Referer", url));
            headers.add(new Header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0"));
            HttpClient client = new HttpClient();
            client.getHostConfiguration().getParams().setParameter("http.default-headers", headers);
            client.executeMethod(postMethod);
            int status = postMethod.getStatusCode();

            if (status != 200) {
                logger.debug("请求天气信息失败！");
                return weather;
            }
            String result = postMethod.getResponseBodyAsString();
            ObjectMapper mapper = new ObjectMapper();
            //忽略没有的字段
            mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            weather = mapper.readValue(result, Weather.class);

            logger.debug("=======" + result);

        } catch (Exception e) {
            logger.error("请求天气信息失败！");
        }
        return weather;
    }
    /**
     * 实时将C/S记录的日志转为运行日志
     */
    public void processLog(){
        try {
            //取上次的取到的最大LOG ID
            String id = manualProperties.getProperty(PatternConst.LOG_ID);
            long logId = Long.parseLong(id);
            List<UcEventLog> ucEventLogList = opLogService.findUcEventlog(logId);
            if(ucEventLogList!=null) {
                String user=getUserLoginDetail();
                logger.info(logId + "--------processLog----------" + ucEventLogList.size()+"==="+user);
                String lastLogId = patternService.saveTransferRunLog(user,ucEventLogList);
                if(StringUtils.isNotEmpty(lastLogId)){
                    manualProperties.setValue( PatternConst.LOG_ID, lastLogId);
                }
            }
        }catch (Exception e){
            logger.error("----auto log-------",e);
        }
    }

    /**
     * 得到登录的用户
     * @return UserLoginDetails
     */
    private String getUserLoginDetail(){
        try {
            Subject currentUser = SecurityUtils.getSubject();
            Object userDetail = currentUser.getPrincipal();
            if(userDetail!=null){
                return userDetail.toString();
            }
        }catch (Exception e){
            logger.error("----processLog----getUserLoginDetail---",e);
        }
        return "admin";
    }
}
