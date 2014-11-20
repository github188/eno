package com.energicube.eno.pattern.service;


import com.energicube.eno.alarm.model.AlarmReport;
import com.energicube.eno.alarm.repository.AlarmReportRepository;
import com.energicube.eno.asset.model.*;
import com.energicube.eno.asset.service.AssetService;
import com.energicube.eno.asset.service.ClassService;
import com.energicube.eno.asset.service.LocationsService;
import com.energicube.eno.asset.service.MeasurementService;
import com.energicube.eno.calendar.model.UcCalendar;
import com.energicube.eno.calendar.model.UcHoliday;
import com.energicube.eno.calendar.model.UcWeather;
import com.energicube.eno.calendar.repository.UcCalendarRepository;
import com.energicube.eno.calendar.repository.UcHolidayRepository;
import com.energicube.eno.calendar.repository.UcWeatherRepository;
import com.energicube.eno.common.Config;
import com.energicube.eno.common.PatternConst;
import com.energicube.eno.common.SendCommandTask;
import com.energicube.eno.common.SendCommandToSystem;
import com.energicube.eno.common.dto.DeviceCommand;
import com.energicube.eno.common.model.Tree;
import com.energicube.eno.common.util.LunarUtil;
import com.energicube.eno.common.util.SunriseSunset;
import com.energicube.eno.common.weather.*;
import com.energicube.eno.monitor.model.OpLogs;
import com.energicube.eno.monitor.model.UcEventLog;
import com.energicube.eno.monitor.repository.OpLogRepository;
import com.energicube.eno.pattern.model.*;
import com.energicube.eno.pattern.repository.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-7-11
 * Time: 下午1:01
 * To change this template use File | Settings | File Templates.
 */
@Service
public class PatternServiceImpl implements PatternService {

    private static Log logger = LogFactory.getLog(PatternServiceImpl.class);

    @Autowired
    UcFactorRepository ucFactorRepository;

    @Autowired
    UcPatternActionRepository ucPatternActionRepository;

    @Autowired
    UcPatternRepository ucPatternRepository;

    @Autowired
    UcPatternFactorRepository ucPatternFactorRepository;

    @Autowired
    UcWeatherRepository ucSubSystemRepository;

    @Autowired
    UcSysFactorRepository ucSysFactorRepository;

    @Autowired
    UcPatternRecordRepository ucPatternRecordRepository;

    @Autowired
    UcSystemBusinessTimeRepository ucSystemBusinessTimeRepository;

    @Autowired
    UcCalendarRepository ucCalendarRepository;

    @Autowired
    UcGlobalPatternRepository ucGlobalPatternRepository;

    @Autowired
    UcLogicGroupRepository ucLogicGroupRepository;

    @Autowired
    UcDeviceSystemRepository ucDeviceSystemRepository;

    @Autowired
    UcDevicePatternRepository ucDevicePatternRepository;

    @Autowired
    UcFactorRecordRepository ucFactorRecordRepository;

    @Autowired
    UcParamRecordRepository ucParamRecordRepository;

    @Autowired
    UcDeviceRecordRepository ucDeviceRecordRepository;

    @Autowired
    UcWeatherRepository ucWeatherRepository;

    @Autowired
    UcHolidayRepository ucHolidayRepository;

    @Autowired
    UcDeviceStrategyRepository ucDeviceStrategyRepository;

    @Autowired
    UcCombinationPatternRepository ucCombinationPatternRepository;

    @Autowired
    UcRunParamRepository ucRunParamRepository;

    @Autowired
    UcStrategyParamRepository ucStrategyParamRepository;

    @Autowired
    UcDeviceGroupRepository ucDeviceGroupRepository;

    @Autowired
    UcStrategyItemRepository ucStrategyItemRepository;

    @Autowired
    AlarmReportRepository alarmReportRepository;

    @Autowired
    AssetService assetService;

    @Autowired
    MeasurementService measurementService;

    @Autowired
    LocationsService locationsService;

    @Autowired
    ClassService classService;

    @Autowired
    OpLogRepository opLogRepository;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat monthFormat = new SimpleDateFormat("MM-dd");
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
    private SimpleDateFormat simpleDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Config config = new Config();



    public List<UcDeviceSystem> findRunPatternSystem() {
        List<UcDeviceSystem> ucDeviceSystemList = ucDeviceSystemRepository.findByIsRunPattern(PatternConst.SYSTEM_RUN_PATTERN_YES);
        return ucDeviceSystemList;
    }


    public List<DeviceCommand> findDayTask(Date nowDate) {
        List<DeviceCommand> deviceCommands = new ArrayList<DeviceCommand>();
        try {

            // 查询有哪些子系统需要系统来自动判断用哪种模式。查询出要运行的模式
            List<UcDeviceSystem> ucDeviceSystemList = ucDeviceSystemRepository.findByIsRunPattern(PatternConst.SYSTEM_RUN_PATTERN_YES);

            //各子系统的预设模式
            List<UcPatternRecord> ucPatternRecordList = ucPatternRecordRepository.findByUcCalendar_Id(dateFormat.format(nowDate));

            //特例的查询
            //根据今天的日期，去模式库查询是否有特例要运行
//            Date today = simpleDateFormat.parse(simpleDateFormat.format(nowDate));
//            List<UcPattern> ucPatternSpecial = ucPatternRepository.findByPatternTypeAndStartDateLessThanAndEndDateGreaterThan(PatternConst.PATTERN_TYPE_SPECIAL, today, today);
            List<UcPatternRecord> ucPatternSpecial = new ArrayList<UcPatternRecord>();

            List<DeviceCommand> predictCommands = null;
            List<DeviceCommand> specialCommands = new ArrayList<DeviceCommand>();
            UcPattern ucPattern = null;
            //得到各子系统要运行的模式
            for (UcDeviceSystem ucDeviceSystem : ucDeviceSystemList) {
                boolean isPredict = false;
                //此子系统的预设模式
                if (ucPatternRecordList != null && ucPatternRecordList.size() > 0) {
                    for (UcPatternRecord ucPatternRecord : ucPatternRecordList) {
                        //将模式拆成tagID的方式
                        String patternType = ucPatternRecord.getPatternType();
                        if (ucPatternRecord.getSystemId().equals(ucDeviceSystem.getSystemCode())) {
                            // if (patternType.equals("GS")) {
                            //预设的全局模式  提前开店是设置开店时间，而不是预计模式，所以无全局的预设。
                            //todo 目前不会有全局模式的预设
                            // }
                            logger.debug("------findDayTask----patternType---------" + patternType + "----" + ucDeviceSystem.getSystemCode());
                            if (patternType.equals(PatternConst.PATTERN_TYPE_PREDICT) || patternType.equals(PatternConst.PATTERN_TYPE_SYSTEM) || patternType.equals(PatternConst.PATTERN_TYPE_CUSTOM)) { //已经有预设或是已经保存模式到记录里都算是已经知道运行什么模式了。
                                ucPattern = ucPatternRepository.findOne(ucPatternRecord.getPatternId());
                                logger.debug("------findDayTask----ucPattern--" + ucPattern + "----" + ucDeviceSystem.getSystemCode());
                                isPredict = true;
                            }
                            if (patternType.equals(PatternConst.PATTERN_TYPE_SPECIAL)) {  //设置的特例
                                ucPatternSpecial.add(ucPatternRecord);
                            }
                        }
                    }
                }
                if (isPredict) { //预设或是已经
                    logger.debug("------findDayTask---isPredict--true--" + ucDeviceSystem.getSystemCode());
                    predictCommands = findPatternToDeviceCommand(PatternConst.COMMAND_TYPE_PREDICT, ucPattern.getId(), nowDate, null);
                } else {
                    //系统自运行的模式
                    logger.debug("------findDayTask----patternType-----else----" + ucDeviceSystem.getSystemCode());
                    ucPattern = findDayRunPattern(ucDeviceSystem.getSystemCode(), nowDate);

                    predictCommands = findPatternToDeviceCommand(PatternConst.COMMAND_TYPE_AUTO, ucPattern.getId(), nowDate, null);
                    // 保存今天要运行的模式   已经预设了模式的不用保存要运行的模式。
                    savePatternToRecord(ucPattern, null, dateFormat.format(nowDate));
                }

                //此子系统的特例
                if (ucPatternSpecial.size() > 0) {
//                    logger.info("------ucPatternSpecial---------" + ucPatternSpecial.size());
                    //存在特例
                    for (UcPatternRecord ucPatternSpe : ucPatternSpecial) {
                        if (ucPattern.getSystemId().equals(ucPatternSpe.getSystemId())) {
                            List<DeviceCommand> device = findPatternRecordToCommand(PatternConst.COMMAND_TYPE_SPECIAL, ucPatternSpe, nowDate);
                            specialCommands.addAll(device);
                        }
                    }
                    //将特例与普通的命令合并
                    List<DeviceCommand> commands = combinationSpecialCommand(predictCommands, specialCommands);
                    deviceCommands.addAll(commands);
                    //清除此子系统的特例
                    specialCommands.clear();
                } else {
                    deviceCommands.addAll(predictCommands);
                }
            }
            //进行总排序
            orderCommand(deviceCommands);
            logger.debug("----findDayTask finish-------------------");
        } catch (Exception ex) {
            logger.error("----findDayTask--------", ex);
        }
        return deviceCommands;
    }

    /**
     * 将特殊命令与预设的命令进行合并
     *
     * @param bases   基础命令集
     * @param special 特例的命令集
     * @return
     */
    private List<DeviceCommand> combinationSpecialCommand(List<DeviceCommand> bases, List<DeviceCommand> special) {
        //获取普通里的含有的特殊的命令
        List<DeviceCommand> news = new ArrayList<DeviceCommand>();
        for (DeviceCommand deviceCommand : bases) {
            String deviceId = deviceCommand.getDeviceId();
            for (DeviceCommand deviceCommand1 : special) {
                String devId = deviceCommand1.getDeviceId();
                if (deviceId.equals(devId)) {
                    news.add(deviceCommand);
                }
            }
        }
        //删除存在设备
        bases.removeAll(news);
        //将设备的特殊模式加入
        bases.addAll(special);
        return bases;
    }

    /**
     * 将模式记录转为命令列表
     *
     * @param commandType     命令类型
     * @param ucPatternRecord 模式记录
     * @param nowDate         日期
     * @return
     */
    private List<DeviceCommand> findPatternRecordToCommand(String commandType, UcPatternRecord ucPatternRecord, Date nowDate) {
        List<DeviceCommand> deviceCommands = new ArrayList<DeviceCommand>();
        try {

            //查询今天的开店时间
            String calendarId = dateFormat.format(nowDate); //今天的日期
            UcCalendar ucCalendar = ucCalendarRepository.findOne(calendarId);
            Date openTime = ucCalendar.getOpenTime();
            Date closeTime = ucCalendar.getCloseTime();
            DateTime sunRise = null;
            DateTime sunSet = null;

            //查询最新的天气信息
            UcWeather ucWeather = findWeatherByCalendarAndNew(calendarId, PatternConst.WEATHER_IS_NEW_YES);

            sunRise = findSunTime(nowDate, true, ucWeather);
            sunSet = findSunTime(nowDate, false, ucWeather);
            //系统的开店和闭店时间
            Date sysOpenTime = null;
            Date sysCloseTime = null;
            sysOpenTime = findBusinessTime(nowDate, true);
            sysCloseTime = findBusinessTime(nowDate, false);

            //获取开店、闭店的状态
            int open = 0;
            int close = 0;
            DateTime openDateTime = DateTime.parse(timeFormat.format(openTime), DateTimeFormat.forPattern("HH:mm"));
            DateTime closeDateTime = DateTime.parse(timeFormat.format(closeTime), DateTimeFormat.forPattern("HH:mm"));
            DateTime systemOpenTime = DateTime.parse(timeFormat.format(sysOpenTime), DateTimeFormat.forPattern("HH:mm"));
            DateTime systemCloseTime = DateTime.parse(timeFormat.format(sysCloseTime), DateTimeFormat.forPattern("HH:mm"));
            open = findBusinessStatus(openDateTime, systemOpenTime);
            close = findBusinessStatus(closeDateTime, systemCloseTime);

            String da = DateTime.now().toString("yyyy-MM-dd");//今天的日期
            //模式拥有的时间点的所有设备动作
            Set<UcDeviceRecord> ucDeviceRecordSet = ucPatternRecord.getUcDeviceRecords();
            List<DeviceCommand> deviceCommands1 = null;
            boolean isFirst = true;
            DeviceCommand templateCommand = null;
            for (UcDeviceRecord ucDeviceRecord : ucDeviceRecordSet) {//所有动作
                //获取命令的发送时间
                String startTime = findSendTime(ucDeviceRecord.getBaseTime(), ucDeviceRecord.getOffsetTime(), ucDeviceRecord.getActionType(), sunRise, sunSet, open, close, da, openDateTime, closeDateTime);

                //处理设备
                String selectType = ucDeviceRecord.getSelectType();
                if (selectType.equals(PatternConst.PATTERN_SELECT_ALL)) {
                    if (isFirst) {
                        //处理设备
                        deviceCommands1 = findDeviceTransferCommand(commandType, startTime, PatternConst.PATTERN_TYPE_ORDINARY, nowDate, null, ucDeviceRecord, templateCommand, null);
                        deviceCommands.addAll(deviceCommands1);
                        if (deviceCommands1.size() > 0) {
                            templateCommand = deviceCommands1.get(0);
                        }
                        isFirst = false;
                    } else {
                        List<DeviceCommand> deviceCommands2 = findAllDeviceCommand(deviceCommands1, startTime, nowDate, null, ucDeviceRecord);
                        deviceCommands.addAll(deviceCommands2);
                    }
                } else {
                    deviceCommands1 = findDeviceTransferCommand(commandType, startTime, PatternConst.PATTERN_TYPE_ORDINARY, nowDate, null, ucDeviceRecord, templateCommand, null);
                    if (deviceCommands1.size() > 0) {
                        templateCommand = deviceCommands1.get(0);
                    }
                    deviceCommands.addAll(deviceCommands1);
                }
            }
        } catch (Exception ex) {
            logger.error("-----------------", ex);
        }
        return deviceCommands;
    }


    public List<DeviceCommand> findPatternToDeviceCommand(String commandType, String patternId, Date nowDate, HashMap<String, DeviceCommand> commandHashMap) {

        List<DeviceCommand> deviceCommands = new ArrayList<DeviceCommand>();
        try {

            logger.debug(commandType + "---patternId--" + patternId + "---nowDate----" + nowDate);
            //查询模式
            UcPattern ucPattern = ucPatternRepository.findOne(patternId);
            //查询今天的开店时间
            String calendarId = dateFormat.format(nowDate); //今天的日期
            UcCalendar ucCalendar = ucCalendarRepository.findOne(calendarId);
            Date openTime = ucCalendar.getOpenTime();
            Date closeTime = ucCalendar.getCloseTime();
            //获取日升、日落时间
            DateTime sunRise = null;
            DateTime sunSet = null;
            UcWeather ucWeather = findWeatherByCalendarAndNew(calendarId, PatternConst.WEATHER_IS_NEW_YES);
            sunRise = findSunTime(nowDate, true, ucWeather);
            sunSet = findSunTime(nowDate, false, ucWeather);

            //系统的开店和闭店时间
            Date sysOpenTime = null;
            Date sysCloseTime = null;
            sysOpenTime = findBusinessTime(nowDate, true);
            sysCloseTime = findBusinessTime(nowDate, false);

            //获取开店、闭店的状态
            int open = 0;
            int close = 0;
            DateTime openDateTime = DateTime.parse(timeFormat.format(openTime), DateTimeFormat.forPattern("HH:mm"));
            DateTime closeDateTime = DateTime.parse(timeFormat.format(closeTime), DateTimeFormat.forPattern("HH:mm"));
            DateTime systemOpenTime = DateTime.parse(timeFormat.format(sysOpenTime), DateTimeFormat.forPattern("HH:mm"));
            DateTime systemCloseTime = DateTime.parse(timeFormat.format(sysCloseTime), DateTimeFormat.forPattern("HH:mm"));
            open = findBusinessStatus(openDateTime, systemOpenTime);
            close = findBusinessStatus(closeDateTime, systemCloseTime);

            String da = new DateTime(nowDate).toString("yyyy-MM-dd");//今天的日期
            //模式拥有的时间点的所有设备动作
            Set<UcPatternAction> ucPatternActions = ucPattern.getUcPatternActions();
            boolean isFirst = true;
            List<DeviceCommand> deviceCommands1 = null; //得到的设备命令
            DeviceCommand templateCommand = null;//命令模版
            Map<String, List<DeviceCommand>> listMap = new HashMap<String, List<DeviceCommand>>();// 保存已经查询过的分类或是分组，减少查询和循环。
            for (UcPatternAction ucPatternAction : ucPatternActions) {//所有动作

                String startTime = findSendTime(ucPatternAction.getBaseTime(), ucPatternAction.getOffsetTime(), ucPatternAction.getActionType(), sunRise, sunSet, open, close, da, openDateTime, closeDateTime);
                //每个动作拥有的设备
                Set<UcDevicePattern> ucDevicePatterns = ucPatternAction.getUcDevicePatterns();

                UcDevicePattern ucDevicePattern = ucDevicePatterns.iterator().next();
                String selectType = ucDevicePattern.getSelectType();
                logger.debug("---selectType---" + selectType + "--isFirst----" + isFirst + "---startTime--" + startTime);
                if (selectType.equals(PatternConst.PATTERN_SELECT_ALL)) {
                    if (isFirst) {
                        //处理设备
                        for (UcDevicePattern ucDevicePattern1 : ucDevicePatterns) {
                            //处理设备
                            deviceCommands1 = findDeviceTransferCommand(commandType, startTime, PatternConst.PATTERN_TYPE_ORDINARY, nowDate, ucDevicePattern1, null, templateCommand, commandHashMap);
                            logger.debug("---selectType---" + selectType + "--isFirst----" + isFirst);
                            if (deviceCommands1.size() > 0) {
                                templateCommand = deviceCommands1.get(0);
                            }
                            deviceCommands.addAll(deviceCommands1);
                        }
                        isFirst = false;
                    } else {
                        List<DeviceCommand> deviceCommands2 = findAllDeviceCommand(deviceCommands1, startTime, nowDate, ucDevicePattern, null);
                        deviceCommands.addAll(deviceCommands2);
                    }
                } else {

                    for (UcDevicePattern ucDevicePattern1 : ucDevicePatterns) {
                        if (listMap.containsKey(ucDevicePattern1.getDevice())) {
                            //存在已经提取过设备的命令列表   ，直接替换参数
                            deviceCommands1 = findAllDeviceCommand(listMap.get(ucDevicePattern1.getDevice()), startTime, nowDate, ucDevicePattern1, null);
                            deviceCommands.addAll(deviceCommands1);
                        } else {
                            //处理设备
                            deviceCommands1 = findDeviceTransferCommand(commandType, startTime, PatternConst.PATTERN_TYPE_ORDINARY, nowDate, ucDevicePattern1, null, templateCommand, commandHashMap);
                            listMap.put(ucDevicePattern1.getDevice(), deviceCommands1);
                            logger.debug("-------deviceCommands----" + deviceCommands1.size());
                            if (deviceCommands1.size() > 0) {
                                templateCommand = deviceCommands1.get(0);
                            }
                            deviceCommands.addAll(deviceCommands1);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            logger.error("----findPatternToDeviceCommand--------", ex);
        }
        logger.debug("-------deviceCommands----" + deviceCommands.size());
        return deviceCommands;
    }

    /**
     * 如果是全选，则优化，直接替换不同的参数即可
     *
     * @param deviceCommands
     * @param startTime
     * @param nowDate
     * @param ucDevicePattern
     * @param ucDeviceRecord
     * @return
     */
    private List<DeviceCommand> findAllDeviceCommand(List<DeviceCommand> deviceCommands, String startTime, Date nowDate, UcDevicePattern ucDevicePattern, UcDeviceRecord ucDeviceRecord) {

        String strategyId = "";
        String sDate = null;
        String eDate = null;
        boolean isStrategy = false;

        DateTime dt = new DateTime(nowDate);
        if (ucDevicePattern != null) {
            strategyId = ucDevicePattern.getStrategyId();
            if (strategyId != null && !"".equals(strategyId)) {

                String start = timeString(ucDevicePattern.getStartTime());
                String end = timeString(ucDevicePattern.getEndTime());
                sDate = dt.toString("yyyy-MM-dd") + " " + start + ":00";
                eDate = dt.toString("yyyy-MM-dd") + " " + end + ":00";
                //应用的是策略
                isStrategy = true;
            }
        }
        if (ucDeviceRecord != null) {
            strategyId = ucDeviceRecord.getStrategyId();
            if (strategyId != null && !"".equals(strategyId)) {
                DateTime start = new DateTime(ucDeviceRecord.getStartTime());
                DateTime end = new DateTime(ucDeviceRecord.getEndTime());
                sDate = dt.toString("yyyy-MM-dd") + " " + start.toString("HH:mm") + ":00";
                eDate = dt.toString("yyyy-MM-dd") + " " + end.toString("HH:mm") + ":00";
                //应用的是策略
                isStrategy = true;
            }
        }
        List<DeviceCommand> deviceCommandList = new ArrayList<DeviceCommand>();
        Date sendT = DateTime.parse(startTime, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
        for (DeviceCommand deviceCommand : deviceCommands) {
            DeviceCommand newCommand = new DeviceCommand();
            BeanUtils.copyProperties(deviceCommand, newCommand);
            List<DeviceCommand> newParams = new ArrayList<DeviceCommand>();
            //时间不一样
            newCommand.setSendTime(sendT);
            //是否有策略命令
            newCommand.setStrategyId(strategyId);
            setCommandStrategy(newCommand, isStrategy, sDate, eDate);
            //参数不同
            List<DeviceCommand> params = deviceCommand.getParams();
            for (DeviceCommand param : params) {
                String paramName = param.getParamName();
                DeviceCommand par = new DeviceCommand();
                par.setParamName(paramName);
                par.setTagId(param.getTagId());
                if (ucDevicePattern != null) {
                    Set<UcRunParam> ucRunParams = ucDevicePattern.getUcRunParams();
                    //找到控制参数的tagId
                    for (UcRunParam ucRunParam : ucRunParams) {
                        String paramN = ucRunParam.getParamName();
//                        logger.debug("----------paramN-----" + paramN);
                        if (paramN.equals(paramName)) {  //控制参数相同
                            par.setParamValue(ucRunParam.getParamValue());
                            if (paramName.equals(PatternConst.PATTERN_COMMAND_SWITCH)) {
                                newCommand.setStatus(ucRunParam.getParamValue());//开关、控制参数加入命令里
                            }
                            break;
                        }
                    }
                }
                if (ucDeviceRecord != null) {
                    Set<UcParamRecord> ucParamRecords = ucDeviceRecord.getUcParamRecords();
                    for (UcParamRecord ucParamRecord : ucParamRecords) {
                        String paramN = ucParamRecord.getParamName();
//                        logger.debug("----------paramN-----" + paramN);
                        if (paramN.equals(paramName)) {  //控制参数相同
                            par.setParamValue(ucParamRecord.getParamValue());
                            if (paramName.equals(PatternConst.PATTERN_COMMAND_SWITCH)) {
                                newCommand.setStatus(ucParamRecord.getParamValue());//开关、控制参数加入命令里
                            }
                            break;
                        }
                    }
                }
                newParams.add(par);
            }
            newCommand.setParams(newParams);
            deviceCommandList.add(newCommand);
        }
        return deviceCommandList;
    }

    /**
     * 获取营业时间
     *
     * @param nowDate 日期
     * @param isOpen  是否是开店时间：true为开店 flase 为闭店
     * @return 营业时间
     * @throws Exception
     */
    private Date findBusinessTime(Date nowDate, boolean isOpen) throws Exception {
        //系统的开店和闭店时间
        Date sysOpenTime = null;
        Date sysCloseTime = null;
        Date nowMoth = dateFormat.parse(monthFormat.format(nowDate));
        //@todo 系统定义的规定的开店时间  不设置则默认上午10点开店，晚上10点闭店
        List<UcSystemBusinessTime> ucSystemBusinessTimes = ucSystemBusinessTimeRepository.findAll();
        if (ucSystemBusinessTimes != null && ucSystemBusinessTimes.size() > 0) {
            for (UcSystemBusinessTime ucSystemBusinessTime : ucSystemBusinessTimes) {
                if (ucSystemBusinessTime.getStartDate() != null) {
                    //多个开店日期，先判断信是否在此时间段内
                    Date start = ucSystemBusinessTime.getStartDate();
                    Date end = ucSystemBusinessTime.getEndDate();
                    if ((nowMoth.equals(start) || nowMoth.after(start)) && (nowMoth.equals(end) || nowMoth.before(end))) {
                        sysOpenTime = ucSystemBusinessTime.getOpenTime();
                        sysCloseTime = ucSystemBusinessTime.getCloseTime();
                    }
                } else {
                    sysOpenTime = ucSystemBusinessTime.getOpenTime();
                    sysCloseTime = ucSystemBusinessTime.getCloseTime();
                }
            }
        } else {
            sysOpenTime = timeFormat.parse("10:00");
            sysCloseTime = timeFormat.parse("22:00");
        }
        if (isOpen) {
            return sysOpenTime;
        } else {
            return sysCloseTime;
        }
    }

    /**
     * 获取开店或闭店的状态：提前、延后、正常
     *
     * @param genTime    今天设置的时间
     * @param systemTime 系统设置的时间
     * @return
     */
    private int findBusinessStatus(DateTime genTime, DateTime systemTime) {
        int open = 0;
        if (genTime.isEqual(systemTime)) {
            //正常开店
            open = 0;
        } else {
            if (genTime.isBefore(systemTime)) {
                //提前开店
                open = -1;
            } else {
                //延时开店
                open = 1;
            }
        }
        return open;
    }

    /**
     * 获取日升、日落时间
     *
     * @param isSunRise true 日升   false为日落
     * @param ucWeather 天气
     * @param nowDate   日期时间
     */
    private DateTime findSunTime(Date nowDate, boolean isSunRise, UcWeather ucWeather) {
        //查询最新的天气信息
        DateTime sunRise = null;
        DateTime sunSet = null;

        if (ucWeather != null && ucWeather.getSunrise() != null && ucWeather.getSunset() != null) {

            sunRise = DateTime.parse(ucWeather.getSunrise(), DateTimeFormat.forPattern("HH:mm"));
            sunSet = DateTime.parse(ucWeather.getSunset(), DateTimeFormat.forPattern("HH:mm"));
//            logger.info("--------sunRise------" + sunRise);
        } else {
            //@todo 查询该地点的经度、纬度
            String localTimeZone = config.getProps().getProperty("localTimeZone");
            String localLatitude = config.getProps().getProperty("localLatitude");
            String localLongitude = config.getProps().getProperty("localLongitude");
            String sr = SunriseSunset.getSunrise(nowDate, Double.parseDouble(localLatitude), Double.parseDouble(localLongitude), Integer.parseInt(localTimeZone));
            String suns = SunriseSunset.getSunset(nowDate, Double.parseDouble(localLatitude), Double.parseDouble(localLongitude), Integer.parseInt(localTimeZone));
            sunRise = DateTime.parse(sr, DateTimeFormat.forPattern("HH:mm"));
            sunSet = DateTime.parse(suns, DateTimeFormat.forPattern("HH:mm"));
//            logger.info("------sunRise--------" + sunRise);
        }
        if (isSunRise) {
            return sunRise;
        } else {
            return sunSet;
        }
    }

    /**
     * 获取命令发送时间
     *
     * @param base          基础时间
     * @param offset        偏移时间
     * @param actionType    动作类型
     * @param sunRise       日升
     * @param sunSet        日落
     * @param open          是否提前开店
     * @param close         是否提前闭店
     * @param nowDate       命令的发送日期
     * @param openDateTime  开店时间
     * @param closeDateTime 闭店时间
     * @return
     */
    private String findSendTime(Integer base, Integer offset, String actionType, DateTime sunRise, DateTime sunSet, int open, int close, String nowDate, DateTime openDateTime, DateTime closeDateTime) {
        //整理时间
        if (base == PatternConst.SUNRISE) {//日升
            //根据设置的系统的坐标算出日升时间
            int x = sunRise.getHourOfDay();
            int y = sunRise.getMinuteOfHour();
            base = x * 60 + y;
        }
        if (base == PatternConst.SUNSET)//日落
        {
            //根据系统设置的坐标算出日落时间
            int x = sunSet.getHourOfDay();
            int y = sunSet.getMinuteOfHour();
            base = x * 60 + y;
        }

        Integer realTime = 0;
        if (offset != null) {
            realTime = base + offset;
        } else {
            realTime = base;
        }

        String startTime = null;
        logger.debug("-----realTime---" + realTime);
        //如果开店时间相同
        if (open == 0) {   //根据当天开店时间、活动类型来决定设备的运行时间
            String hm = timeString(realTime);
            startTime = nowDate + " " + hm + ":00";
        } else {
            if (open > 0) {
                //延时开店
                //如果开店时间不同
                if (actionType.equals(PatternConst.PATTERN_ACTION_READY_OPEN) || actionType.equals(PatternConst.PATTERN_ACTION_OPEN)) {
                    int m = openDateTime.getMinuteOfDay();
                    int relativeTime = m - realTime;
                    realTime = realTime + relativeTime;
                    String hm = timeString(realTime);
                    startTime = nowDate + " " + hm + ":00";
                }
            } else {
                //提前开店
                if (actionType.equals(PatternConst.PATTERN_ACTION_READY_OPEN) || actionType.equals(PatternConst.PATTERN_ACTION_OPEN)) {
                    int m = openDateTime.getMinuteOfDay();
                    int relativeTime = m - realTime;
                    realTime = realTime - relativeTime;
                    String hm = timeString(realTime);
                    startTime = nowDate + " " + hm + ":00";
                }
            }
        }


        if (close == 0) {   //根据当天开店时间、活动类型来决定设备的运行时间
            String hm = timeString(realTime);
            startTime = nowDate + " " + hm + ":00";
        } else {
            if (close > 0) {
                //延时闭店
                if (actionType.equals(PatternConst.PATTERN_ACTION_READY_CLOSE) || actionType.equals(PatternConst.PATTERN_ACTION_CLOSE)) {
                    int m = closeDateTime.getMinuteOfDay();
                    int relativeTime = m - realTime;
                    realTime = realTime + relativeTime;
                    String hm = timeString(realTime);
                    startTime = nowDate + " " + hm + ":00";
                }
            } else {
                //提前闭店
                if (actionType.equals(PatternConst.PATTERN_ACTION_READY_CLOSE) || actionType.equals(PatternConst.PATTERN_ACTION_CLOSE)) {
                    int m = closeDateTime.getMinuteOfDay();
                    int relativeTime = m - realTime;
                    realTime = realTime - relativeTime;
                    String hm = timeString(realTime);
                    startTime = nowDate + " " + hm + ":00";
                }
            }
        }
        return startTime;
    }

    /**
     * 通过设备参数封装命令列表
     *
     * @param commandType     命令的类型
     * @param startTime       命令发送的时间
     * @param patternType     模式的类型
     * @param nowDate         命令的日期
     * @param ucDevicePattern 设备模式
     * @param ucDeviceRecord  设备记录
     * @return
     */
    public List<DeviceCommand> findDeviceTransferCommand(String commandType, String startTime, String patternType, Date nowDate, UcDevicePattern ucDevicePattern, UcDeviceRecord ucDeviceRecord, DeviceCommand deviceCommand, HashMap<String, DeviceCommand> commandHashMap) {
        List<DeviceCommand> deviceCommands = new ArrayList<DeviceCommand>();
        try {
            // logger.debug("---selectType--findDevice--start----"+DateTime.now().toString("HH:mm:ss") );
            String deviceType = "";
            String systemCode = "";
            String ucDeviceId = "";
            String strategyId = "";
            Set<UcRunParam> ucRunParams = null;
            Set<UcParamRecord> ucParamRecords = null;
            //设备要设置的参数
            if (ucDevicePattern != null) {
                ucRunParams = ucDevicePattern.getUcRunParams();
                deviceType = ucDevicePattern.getSelectType();
                systemCode = ucDevicePattern.getUcPatternAction().getUcPattern().getSystemId();
                ucDeviceId = ucDevicePattern.getDevice();
                strategyId = ucDevicePattern.getStrategyId();
            }
            if (ucDeviceRecord != null) {
                ucParamRecords = ucDeviceRecord.getUcParamRecords();
                deviceType = ucDeviceRecord.getSelectType();
                systemCode = ucDeviceRecord.getUcPatternRecord().getSystemId();
                ucDeviceId = ucDeviceRecord.getDeviceId();
            }

            UcDeviceSystem ucDeviceSystem = ucDeviceSystemRepository.findBySystemCode(systemCode);
            String deviceCode = ucDeviceSystem.getSystemClass();
            String specclass = ucDeviceSystem.getSpecclass();

            String sDate = null;
            String eDate = null;
            boolean isStrategy = false;
            if (strategyId != null && !"".equals(strategyId)) {
                DateTime dt = new DateTime(nowDate);
                if (ucDevicePattern != null) {
                    String start = timeString(ucDevicePattern.getStartTime());
                    String end = timeString(ucDevicePattern.getEndTime());
                    sDate = dt.toString("yyyy-MM-dd") + " " + start + ":00";
                    eDate = dt.toString("yyyy-MM-dd") + " " + end + ":00";
                }
                if (ucDeviceRecord != null) {
                    DateTime start = new DateTime(ucDeviceRecord.getStartTime());
                    DateTime end = new DateTime(ucDeviceRecord.getEndTime());
                    sDate = dt.toString("yyyy-MM-dd") + " " + start.toString("HH:mm") + ":00";
                    eDate = dt.toString("yyyy-MM-dd") + " " + end.toString("HH:mm") + ":00";
                }
                //应用的是策略
                isStrategy = true;
            }
            //策略的命令
            if (deviceType.equals(PatternConst.PATTERN_SELECT_LOGIC)) {
                //选择的是逻辑组

                UcLogicGroup ucLogicGroup = ucLogicGroupRepository.findOne(ucDeviceId);
                Set<UcGroupDevice> ucGroupDeviceSet = ucLogicGroup.getUcGroupDevices();

                for (UcGroupDevice ucGroupDevice : ucGroupDeviceSet) {
                    String deviceId = ucGroupDevice.getDeviceId();

                    if (commandHashMap != null && commandHashMap.get(deviceId) != null) { //命令模版存在
                        deviceCommand = transferDeviceToCommandChang(deviceId, commandType, patternType, startTime, strategyId, isStrategy, sDate, eDate, ucRunParams, commandHashMap);
                    } else {
                        // if (deviceCommand == null) {
                        deviceCommand = transferDeviceToCommand(deviceId, commandType, patternType, startTime, ucRunParams, ucParamRecords);
                        // } else {
                        //     deviceCommand = transferReadyCommand(deviceCommand, deviceId);
                        //  }
                    }
                    addCommandToList(deviceCommands, deviceCommand, strategyId, systemCode, isStrategy, sDate, eDate);
                }
            }
            if (deviceType.equals(PatternConst.PATTERN_SELECT_DEVICE)) {
                //直接设备
                //命令的参数
                if (commandHashMap != null && commandHashMap.get(ucDeviceId) != null) { //命令模版存在
                    deviceCommand = transferDeviceToCommandChang(ucDeviceId, commandType, patternType, startTime, strategyId, isStrategy, sDate, eDate, ucRunParams, commandHashMap);
                } else {
                    // if (deviceCommand == null) {
                    deviceCommand = transferDeviceToCommand(ucDeviceId, commandType, patternType, startTime, ucRunParams, ucParamRecords);
                    // } else {
                    //     deviceCommand = transferReadyCommand(deviceCommand, ucDeviceId);
                    // }
                }
                addCommandToList(deviceCommands, deviceCommand, strategyId, systemCode, isStrategy, sDate, eDate);
            }
            if (deviceType.equals(PatternConst.PATTERN_SELECT_ALL)) {
                //所有设备
                int x = deviceCode.indexOf(",");
                if (x > 0) {
                    StringTokenizer stringTokenizer = new StringTokenizer(deviceCode, ",");

                    while (stringTokenizer.hasMoreTokens()) {
                        String code = stringTokenizer.nextToken();

                        List<Asset> assets = assetService.findByClassstructureid(code, "", "");

                        for (Asset asset : assets) {
                            String deviceId = asset.getAssetnum();

                            if (commandHashMap != null && commandHashMap.get(deviceId) != null) { //命令模版存在
                                deviceCommand = transferDeviceToCommandChang(deviceId, commandType, patternType, startTime, strategyId, isStrategy, sDate, eDate, ucRunParams, commandHashMap);
                            } else {
                                // if (deviceCommand == null) {
                                deviceCommand = transferDeviceToCommand(deviceId, commandType, patternType, startTime, ucRunParams, ucParamRecords);
                                // } else {
                                //      deviceCommand = transferReadyCommand(deviceCommand, deviceId);
                                //  }
                            }
                            addCommandToList(deviceCommands, deviceCommand, strategyId, systemCode, isStrategy, sDate, eDate);
                        }
                    }
                } else {
                    List<Asset> assets = assetService.findByClassstructureid(deviceCode, "", "");

                    for (Asset asset : assets) {
                        String deviceId = asset.getAssetnum();

                        if (commandHashMap != null && commandHashMap.get(deviceId) != null) { //命令模版存在
                            deviceCommand = transferDeviceToCommandChang(deviceId, commandType, patternType, startTime, strategyId, isStrategy, sDate, eDate, ucRunParams, commandHashMap);
                        } else {
                            //if (deviceCommand == null) {
                            deviceCommand = transferDeviceToCommand(deviceId, commandType, patternType, startTime, ucRunParams, ucParamRecords);
                            // } else {
                            //     deviceCommand = transferReadyCommand(deviceCommand, deviceId);
                            // }
                        }
                        addCommandToList(deviceCommands, deviceCommand, strategyId, systemCode, isStrategy, sDate, eDate);
                    }
                }
            }
            if (deviceType.equals(PatternConst.PATTERN_SELECT_SPACE)) {
                //区域设备
                List<Asset> assetList = assetService.findByLocationAndSpecclassAndClassstructureidAndSiteid(ucDeviceId, specclass, deviceCode, "");

                for (Asset asset : assetList) {
                    String deviceId = asset.getAssetnum();
                    if (commandHashMap != null && commandHashMap.get(deviceId) != null) { //命令模版存在
                        deviceCommand = transferDeviceToCommandChang(deviceId, commandType, patternType, startTime, strategyId, isStrategy, sDate, eDate, ucRunParams, commandHashMap);
                    } else {
                        //if (deviceCommand == null) {
                        deviceCommand = transferDeviceToCommand(deviceId, commandType, patternType, startTime, ucRunParams, ucParamRecords);
                        // } else {
                        //    deviceCommand = transferReadyCommand(deviceCommand, deviceId);
                        // }
                    }
                    addCommandToList(deviceCommands, deviceCommand, strategyId, systemCode, isStrategy, sDate, eDate);
                }
            }
            if (deviceType.equals(PatternConst.PATTERN_SELECT_CLASS)) {
                //级别分类设备
                //   logger.debug("----findDevice---M---1----"+DateTime.now().toString("HH:mm:ss") );
                List<Asset> assets = assetService.findByClassstructureid(ucDeviceId, "", "");
                //  logger.debug("----findDevice---M---2----"+DateTime.now().toString("HH:mm:ss")+"-----"+assets.size() );
                for (Asset asset : assets) {
                    String deviceId = asset.getAssetnum();
                    if (commandHashMap != null && commandHashMap.get(deviceId) != null) { //命令模版存在
                        deviceCommand = transferDeviceToCommandChang(deviceId, commandType, patternType, startTime, strategyId, isStrategy, sDate, eDate, ucRunParams, commandHashMap);
                    } else {
//                        if (deviceCommand == null) {
                        deviceCommand = transferDeviceToCommand(deviceId, commandType, patternType, startTime, ucRunParams, ucParamRecords);
//                        } else {
//                            deviceCommand = transferReadyCommand(deviceCommand, deviceId);
//                        }
                    }
                    addCommandToList(deviceCommands, deviceCommand, strategyId, systemCode, isStrategy, sDate, eDate);
                }
                logger.debug("----findDevice---M---3----" + DateTime.now().toString("HH:mm:ss") + "-----" + assets.size());
            }
        } catch (Exception e) {
            logger.error("--findDevice--", e);
        }
        //    logger.debug("---selectType--findDevice--end----"+DateTime.now().toString("HH:mm:ss") );
        return deviceCommands;
    }

    /**
     * 将设备转换成命令列表
     *
     * @param deviceCommand 命令模版  同类设备相同
     * @param deviceId      设备的ID
     * @return 设备的命令
     */
    private DeviceCommand transferReadyCommand(DeviceCommand deviceCommand, String deviceId) {
        try {
            DeviceCommand newCommand = new DeviceCommand();//设备命令
            BeanUtils.copyProperties(deviceCommand, newCommand);
            newCommand.setDeviceId(deviceId);
            //替换tagID.
            List<MeasurementAttribute> measurementAttributeList = measurementService.findMeasurementsByAssetnum(deviceId, "", "");
            //   System.out.println("----transferReadyCommand-------------:" +measurementAttributeList.size() );
            List<DeviceCommand> params = newCommand.getParams();
            for (MeasurementAttribute measurementAttribute : measurementAttributeList) {
                String measureName = measurementAttribute.getAssetattrid();
                for (DeviceCommand command : params) {
                    String param = command.getParamName();
                    if (param.equals(measureName)) {
                        //  System.out.println(param+"----transferReadyCommand-------------:" +measurementAttribute.getValuetag() );
                        command.setTagId(measurementAttribute.getValuetag());
                        if (param.equals(PatternConst.PATTERN_COMMAND_SWITCH)) {
                            newCommand.setStatus(command.getParamValue());//开关、控制参数加入命令里
                        }
                        break;
                    }
                }
            }
            return newCommand;
        } catch (Exception e) {
            logger.error("----transferReadyCommand-------------:" + deviceId, e);
        }
        return null;
    }

    /**
     * 将命令加入命令列表
     *
     * @param deviceCommands 命令列表
     * @param deviceCommand  命令
     * @param strategyId     策略ID
     * @param systemCode     系统编码
     * @param isStrategy     是否是策略
     * @param sDate          开始时间
     * @param eDate          结束时间
     */
    private void addCommandToList(List<DeviceCommand> deviceCommands, DeviceCommand deviceCommand, String strategyId, String systemCode, boolean isStrategy, String sDate, String eDate) {
        if (deviceCommand != null) {
            deviceCommand.setStrategyId(strategyId);
            setCommandStrategy(deviceCommand, isStrategy, sDate, eDate);
            deviceCommand.setSystemId(systemCode);
            deviceCommands.add(deviceCommand);
        }
    }

    /**
     * 设备命令的策略属性
     *
     * @param deviceCommand 命令
     * @param isStrategy    是否是策略
     * @param startDate     开始时间
     * @param endDate       结束时间
     */
    private void setCommandStrategy(DeviceCommand deviceCommand, boolean isStrategy, String startDate, String endDate) {
        if (isStrategy) {
            deviceCommand.setStrategy(PatternConst.IS_STRATEGY_YES);
            deviceCommand.setStartDate(startDate);
            deviceCommand.setEndDate(endDate);
        } else {
            deviceCommand.setStrategy(PatternConst.IS_STRATEGY_NO);
        }
    }

    /**
     * 将设备的设置生成命令
     *
     * @param deviceId       设备ID
     * @param commandType    命令类型
     * @param patternType    模式类型
     * @param startTime      开始时间
     * @param ucParamRecords 设备运行的参数
     * @return
     */
    private DeviceCommand transferDeviceToCommand(String deviceId, String commandType, String patternType, String startTime, Set<UcRunParam> ucRunParams, Set<UcParamRecord> ucParamRecords) {
        try {
            DeviceCommand deviceCommand = new DeviceCommand();//设备命令
            deviceCommand.setCommandType(commandType);
            deviceCommand.setExecuteStatus(PatternConst.DEVICE_EXECUTE_NO);
            deviceCommand.setDeviceId(deviceId);

            if (patternType.equals(PatternConst.PATTERN_TYPE_GLOBAL)) {
                //全局的模式  命令是延时处理，而非定时
                if (startTime.contains("-")) {
                    //设置了时间点，非只有偏移时间
                    //todo 不支持时序模式
                } else {
                    deviceCommand.setSpaceTime(Integer.parseInt(startTime));
                }
            }
            if (patternType.equals(PatternConst.PATTERN_TYPE_ORDINARY)) {
                Date sendT = DateTime.parse(startTime, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
                deviceCommand.setSendTime(sendT);
            }

//            logger.debug("----------deviceId-----" + deviceId);
            //通过设备ID查询可以设置的tagId
            List<AssetMeterSet> assetMeter = assetService.findAssetMeterByAssetnum(deviceId, "");
            //  logger.debug(commandType + "----------deviceId-----" + deviceId + "----------assetMeter-----" + assetMeter.size());
//            循环所有测试器具，得到需要的参数 tagId
            for (AssetMeterSet assetMeterSet : assetMeter) {
                //得到测试器具
                AssetMeter asset = assetMeterSet.getAssetMeter();
                List<MeasurementSet> measurementSets = measurementService.findMeasurementsByAssetnumAndMetername(deviceId, asset.getMetername(), "", "");
                for (MeasurementSet measurementSet : measurementSets) {
                    AssetAttribute assetAttribute = measurementSet.getAssetAttribute();
                    String paramName = assetAttribute.getAssetattrid();

                    if (ucRunParams != null) {
                        //找到控制参数的tagId
                        for (UcRunParam ucRunParam : ucRunParams) {
                            String paramN = ucRunParam.getParamName();
//                            logger.debug(paramN.equals(paramName) + "-----ucRunParams-----paramN-----:" + paramN + "::" + paramName + ":");
                            if (paramN.equals(paramName)) {  //控制参数相同
                                String tagId = measurementSet.getMeasurement().getValuetag();
                                DeviceCommand command = new DeviceCommand();
                                command.setTagId(tagId);
                                command.setParamName(paramName);
                                String va = ucRunParam.getParamValue();
//                                logger.debug("----------tagId-----" + tagId + "------" + va);
                                command.setParamValue(va);
                                deviceCommand.getParams().add(command);
                                if (paramName.equals(PatternConst.PATTERN_COMMAND_SWITCH)) {
                                    deviceCommand.setStatus(command.getParamValue());//开关、控制参数加入命令里
                                }
                                break;
                            }
                        }
                    }
                    if (ucParamRecords != null) {
                        for (UcParamRecord ucParamRecord : ucParamRecords) {
                            String paramN = ucParamRecord.getParamName();
//                            logger.debug(paramN.equals(paramName) + "-----ucRunParams-----paramN-----:" + paramN + "::" + paramName + ":");
                            if (paramN.equals(paramName)) {  //控制参数相同
                                String tagId = measurementSet.getMeasurement().getValuetag();
                                DeviceCommand command = new DeviceCommand();
                                command.setTagId(tagId);
                                command.setParamName(paramName);
                                String va = ucParamRecord.getParamValue();
//                                logger.debug("----------tagId-----" + tagId + "------" + va);
                                command.setParamValue(va);
                                deviceCommand.getParams().add(command);
                                if (paramName.equals(PatternConst.PATTERN_COMMAND_SWITCH)) {
                                    deviceCommand.setStatus(command.getParamValue());//开关、控制参数加入命令里
                                }
                                break;
                            }
                        }

                    }

                }
            }
            return deviceCommand;
        } catch (Exception e) {
            logger.error("----transferDeviceToCommand-------:" + startTime + ":---:" + patternType + ":---:" + deviceId, e);
        }
        return null;
    }


    public UcPattern findDayRunPattern(String systemId, Date nowDate) {
        UcPattern ucPatternAll = null;
        try {
            String calendarId = dateFormat.format(nowDate); //今天的日期
            UcCalendar ucCalendar = ucCalendarRepository.findOne(calendarId);
            String todayWeather = null;
            String todayHoliday = null;
            UcWeather ucWeather = findWeatherByCalendarAndNew(calendarId, PatternConst.WEATHER_IS_NEW_YES);
            if (ucWeather != null && ucWeather.getSunrise() != null && ucWeather.getSunset() != null) {
                todayWeather = ucWeather.getWeather();
            }

            if (todayWeather == null) {
                todayWeather = PatternConst.PATTERN_WEATHER_TYPE_SUNNY;
            }

            List<UcHoliday> ucHolidays = ucHolidayRepository.findByUcCalendarId(ucCalendar.getId());
            if (ucHolidays != null && ucHolidays.size() > 0) {
                UcHoliday ucHoliday = ucHolidays.iterator().next();
                todayHoliday = ucHoliday.getIsHoliday();
            }

            if (todayHoliday == null) {
                todayHoliday = PatternConst.PATTERN_HOLIDAY_NO;
            } else {
                if (todayHoliday.equals(PatternConst.HOLIDAY_YES)) {
                    todayHoliday = PatternConst.PATTERN_HOLIDAY_YES;
                } else {
                    todayHoliday = PatternConst.PATTERN_HOLIDAY_NO;
                }
            }
            //今天的月日
            Date nowMoth = dateFormat.parse(monthFormat.format(nowDate));

            List<UcPattern> ucPatternList = ucPatternRepository.findBySystemIdAndPatternTypeAndOrderType(systemId, PatternConst.PATTERN_TYPE_SYSTEM, PatternConst.PATTERN_ORDER_TYPE_TIME);
            for (UcPattern ucPattern : ucPatternList) {
                //条件是否满足
                int isSeason = 0;
                int isWeather = 0;
                int isHoliday = 0;
                List<UcPatternFactor> ucPatternFactorSet = ucPatternFactorRepository.findByUcPattern_Id(ucPattern.getId());
                for (UcPatternFactor ucPatternFactor : ucPatternFactorSet) {
                    UcFactor ucFactor = ucPatternFactor.getUcFactor();
                    String factorType = ucFactor.getFactorType();
                    if (factorType.equals(PatternConst.PATTERN_FACTOR_SEASON)) {
                        //季节
                        String id = ucFactor.getId();
                        List<UcFactor> ucFactors = ucFactorRepository.findByParentId(id);
                        if (ucFactors != null && ucFactors.size() > 0) {
                            for (UcFactor factor : ucFactors) {
                                Date start = factor.getStartDate();
                                Date end = factor.getEndDate();
                                if ((nowMoth.equals(start) || nowMoth.after(start)) && (nowMoth.equals(end) || nowMoth.before(end))) {
                                    isSeason = 1;
                                    break;//符合条件，退出循环
                                } else {
                                    isSeason = -1;
                                }

                            }
                        } else {
                            Date start = ucFactor.getStartDate();
                            Date end = ucFactor.getEndDate();
                            if ((nowMoth.equals(start) || nowMoth.after(start)) && (nowMoth.equals(end) || nowMoth.before(end))) {
                                isSeason = 1;
                            } else {
                                isSeason = -1;
                            }
                        }
                    }
                    if (factorType.equals(PatternConst.PATTERN_FACTOR_WEATHER)) {
                        //天气
                        //取今天的天气
                        String factorWeather = ucFactor.getMinValue();
                        logger.debug("-------factorWeather------:" + factorWeather);
                        if (todayWeather.equals(factorWeather)) {
                            isWeather = 1;
                        } else {
                            isWeather = -1;
                        }
                    }
                    if (factorType.equals(PatternConst.PATTERN_FACTOR_HOLIDAY)) {
                        //节假日
                        String holiday = ucFactor.getMinValue();
                        if (todayHoliday.equals(holiday)) {
                            isHoliday = 1;
                        } else {
                            isHoliday = -1;
                        }
                    }
                }
                logger.debug("-----isSeason-----[" + isSeason + "]----isHoliday--[" + isHoliday + "]---isWeather---[" + isWeather);
                if (isSeason >= 0 && isHoliday >= 0 && isWeather >= 0) {
                    //条件达成
                    ucPatternAll = ucPattern;
                    break;
                }
            }
            logger.debug("-----ucPatternAll-----" + ucPatternAll);
            if (ucPatternAll == null) {
                //未找到合适的运行模式，将运行默认的模式
                List<UcPattern> ucPatterns = ucPatternRepository.findBySystemIdAndDefaultPattern(systemId, PatternConst.PATTERN_DEFAULT_YES);
                if (ucPatterns != null && ucPatterns.size() > 0) {
                    ucPatternAll = ucPatterns.get(0);
                }
            }
        } catch (Exception ex) {
            logger.error("----findDayRunPattern--------", ex);
        }
        return ucPatternAll;
    }


    public List<DeviceCommand> combinationDeviceCommand(List<DeviceCommand> baseCommands, List<DeviceCommand> newCommands) {
        try {
            int k = 0;//某设备的出现次数
            DeviceCommand start = null;
            DeviceCommand end = null;
            boolean isFinish = true;
            do {
                for (DeviceCommand deviceCommand : newCommands) {
                    //先取出特例里的一个设备，且有开始命令和最近一次的结束命令
                    if (k == 0) {
                        start = deviceCommand;
                        newCommands.remove(start);
                        k++;
                    }
                    if (k == 1) {
                        if (start.getDeviceId().equals(deviceCommand.getDeviceId())) {
                            //与start是同一设备
                            end = deviceCommand;
                            newCommands.remove(end);
                            k++;
                        }
                    }
                    //开始处理组合;
                    if (k == 2) {
                        Date startDate = start.getSendTime();
                        Date endDate = end.getSendTime();
                        //推算前后半小时的时间
                        long nStart = startDate.getTime() - 1800000;
                        long nEnd = endDate.getTime() + 1800000;

                        Date preStartDate = new Date();
                        preStartDate.setTime(nStart);
                        Date preEndDate = new Date();
                        preEndDate.setTime(nEnd);

                        boolean isExist = false;
                        for (DeviceCommand baseDeviceCommand : baseCommands) {
                            //同一设备
                            if (baseDeviceCommand.getDeviceId().equals(start.getDeviceId())) {

                                if (baseDeviceCommand.getSendTime().equals(preStartDate) || (baseDeviceCommand.getSendTime().after(preStartDate) && baseDeviceCommand.getSendTime().before(startDate))) {
                                    //处于提前半小时内的命令
                                    if (!baseDeviceCommand.getStatus().equals(start.getStatus())) {
                                        //启停状态不同 删除
                                        baseCommands.remove(baseDeviceCommand);
                                    }//启停状态相同 保留
                                }
                                if (baseDeviceCommand.getSendTime().equals(startDate)) {
                                    //相同时间点的同一命令 删除
                                    baseCommands.remove(baseDeviceCommand);
                                }
                                if (baseDeviceCommand.getSendTime().after(startDate) && baseDeviceCommand.getSendTime().before(endDate)) {
                                    //处于相同时间内的命令  全部删除
                                    baseCommands.remove(baseDeviceCommand);
                                }
                                if (baseDeviceCommand.getSendTime().equals(endDate)) {
                                    //相同时间点的同一命令 删除
                                    baseCommands.remove(baseDeviceCommand);
                                }

                                if (baseDeviceCommand.getSendTime().equals(preEndDate) || (baseDeviceCommand.getSendTime().after(endDate) && baseDeviceCommand.getSendTime().before(preEndDate))) {
                                    //处于提前半小时内的命令
                                    if (!baseDeviceCommand.getStatus().equals(start.getStatus())) {
                                        //启停状态不同 删除
                                        isExist = true;
                                    }//启停状态相同 保留
                                }

                            }
                        }
                        //将特殊的命令插入 时间任务列表
                        baseCommands.add(start);
                        if (!isExist) {
                            //在结束后的半小时内无命令，所以将结束命令放入
                            baseCommands.add(end);
                        }
                        k = 0;
                        start = null;
                        end = null;
                    }
                }
                if (start == null) {
                    //已经没有可比对的数据，完成。
                    isFinish = false;
                } else {
                    if (end == null) {
                        newCommands.remove(start);
                        k = 0;
                        //todo 有特例或自定义模式的定义时缺少闭合  此处是否将信息放入信息系统。
                        logger.error(" pattern is error");
                    }
                }
                if (newCommands.size() == 0) {
                    isFinish = false;
                }
            } while (isFinish);
        } catch (Exception e) {
            logger.error("----combination  command------", e);
        }
        // 命令按时间的排序
        orderCommand(baseCommands);
        return baseCommands;
    }


    public List<DeviceCommand> findGlobalPatternToDeviceCommand(Date nowDate, String patternId, HashMap<String, DeviceCommand> commandHashMap) {
        List<DeviceCommand> deviceCommands = new ArrayList<DeviceCommand>();
        try {
            //查询全局模式
            UcGlobalPattern ucGlobalPattern = ucGlobalPatternRepository.findOne(patternId);

            Set<UcCombinationPattern> ucCombinationPatterns = ucGlobalPattern.getUcCombinationPatterns();
            for (UcCombinationPattern ucCombinationPattern : ucCombinationPatterns) {

                //计算各子系统的偏移时间
                //todo 基础时间未处理 全局模式运行时不设置此时间
                Integer baseSub = ucCombinationPattern.getBaseTime();
                Integer offsetSub = ucCombinationPattern.getOffsetTime();


                UcPattern ucPattern = ucCombinationPattern.getUcPattern();
                //模式拥有的时间点的所有设备动作
                Set<UcPatternAction> ucPatternActions = ucPattern.getUcPatternActions();

                for (UcPatternAction ucPatternAction : ucPatternActions) {//所有动作
                    //每个动作拥有的设备
                    Set<UcDevicePattern> ucDevicePatterns = ucPatternAction.getUcDevicePatterns();
                    for (UcDevicePattern ucDevicePattern : ucDevicePatterns) {

                        //整理时间
                        Integer base = ucPatternAction.getBaseTime();
                        Integer offset = ucPatternAction.getOffsetTime();

                        Integer spaceTime = 0;
                        String startTime = null;

                        if (base == -1) {
                            //表示立即执行
                            spaceTime = offsetSub + offset;

                            List<DeviceCommand> deviceCommands1 = findDeviceTransferCommand(PatternConst.COMMAND_TYPE_GLOBAL, spaceTime.toString(), PatternConst.PATTERN_TYPE_GLOBAL, nowDate, ucDevicePattern, null, null, commandHashMap);
                            deviceCommands.addAll(deviceCommands1);

                        } else {
                            Integer realTime = 0;
                            if (offset != null) {
                                realTime = base + offsetSub + offset;
                            }
                            int hour = realTime / 60;
                            int minute = realTime % 60;

                            String da = simpleDateFormat.format(nowDate);
                            startTime = da + " " + hour + ":" + minute + ":00";

                            //处理设备
                            List<DeviceCommand> deviceCommands1 = findDeviceTransferCommand(PatternConst.COMMAND_TYPE_GLOBAL, startTime, PatternConst.PATTERN_TYPE_GLOBAL, nowDate, ucDevicePattern, null, null, commandHashMap);
                            deviceCommands.addAll(deviceCommands1);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            logger.error("----------------", ex);
        }
        orderCommand(deviceCommands);
        return deviceCommands;
    }


    public List<DeviceCommand> combinationGlobalCommand(List<DeviceCommand> baseCommands, Date startDate, Date endDate, List<DeviceCommand> globalCommands) {

        boolean isExist = false;
        List<DeviceCommand> runCommand = new ArrayList<DeviceCommand>();  //全局运行过的命令
        List<DeviceCommand> noRunCommand = new ArrayList<DeviceCommand>(); //普通未运行过的命令
        List<DeviceCommand> otherCommand = new ArrayList<DeviceCommand>();  //全局结束后，半小时内要运行的命令

        long nEnd = endDate.getTime() + 1800000;

        Date preEndDate = new Date();
        preEndDate.setTime(nEnd);

        for (DeviceCommand deviceCommand : baseCommands) {
            if (deviceCommand.getSendTime().after(startDate) && deviceCommand.getSendTime().before(endDate)) {
                //未执行的命令
                noRunCommand.add(deviceCommand);
            }
            if (deviceCommand.getSendTime().after(endDate) && deviceCommand.getSendTime().before(preEndDate)) {
                //半小时内要执行的命令
                otherCommand.add(deviceCommand);
            }
        }

        //noRun的去重复
        noRunCommand = removeRepeatCommand(noRunCommand);

        //noRun的排序
        orderCommand(noRunCommand);

        for (DeviceCommand deviceCommand : noRunCommand) {

            for (DeviceCommand baseDeviceCommand : globalCommands) {
                if (deviceCommand.getDeviceId().equals(baseDeviceCommand.getDeviceId())) {
                    //同一设备
                    if (deviceCommand.getStatus().equals(baseDeviceCommand.getStatus())) {
                        //设备启停状态相同，不用处理，设备已经处于正常状态。
                    } else {
                        boolean isRun = false;
                        for (DeviceCommand wCommand : otherCommand) {
                            if (deviceCommand.getDeviceId().equals(wCommand.getDeviceId())) {
                                if (deviceCommand.getStatus().equals(wCommand.getStatus())) {
                                    runCommand.add(deviceCommand);
                                } else {
                                    //不处理，状态不同，造成延后处理，
                                }
                                isRun = true;
                            }
                        }
                        if (!isRun) {
                            //未来半小时无此设备的操作，直接加入
                            runCommand.add(deviceCommand);
                        }
                    }
                    isExist = true;
                    break;
                }
            }
            if (!isExist) {
                //全局模式没对此设备进行操作，直接加入运行列表
                runCommand.add(deviceCommand);
            } else {
                //全局模式时已经对此设备进行了操作。开启或关闭
                deviceCommand.setExecuteStatus(PatternConst.DEVICE_EXECUTE_GLOBAL);
            }
        }
        return runCommand;
    }


    public List<DeviceCommand> manualDeviceCommand(List<DeviceCommand> baseCommands, String commandType, String deviceIds, String status) {

        Date nowDate = new Date();
        StringTokenizer stringTokenizer = new StringTokenizer(deviceIds, ",");

        if (status.equals(PatternConst.COMMAND_TYPE_MANUAL)) {  //是自动转手动的设备
            for (DeviceCommand deviceCommand : baseCommands) {

                while (stringTokenizer.hasMoreTokens()) {
                    String id = stringTokenizer.nextToken();
                    if (deviceCommand.getDeviceId().equals(id)) {
                        if (deviceCommand.getSendTime().after(nowDate)) {
                            //相同设备的 当前时间后的命令  将其全部设置成  覆盖，防止再次执行，影响手动命令。
                            deviceCommand.setExecuteStatus(PatternConst.DEVICE_EXECUTE_COVER);
                        }
                    }

                }
            }
        }
        if (status.equals(PatternConst.COMMAND_TYPE_AUTO)) {//手动转为自动
            while (stringTokenizer.hasMoreTokens()) {
                String id = stringTokenizer.nextToken();
                boolean isExits = false;
                for (DeviceCommand deviceCommand : baseCommands) {

                    if (deviceCommand.getDeviceId().equals(id)) {
                        if (deviceCommand.getSendTime().after(nowDate)) {
                            //相同设备的 当前时间后的命令  将其全部设置成  覆盖，防止再次执行，影响手动命令。
                            deviceCommand.setExecuteStatus(PatternConst.DEVICE_EXECUTE_NO);
                        }
                        isExits = true;
                    }
                }
                if (!isExits) {//原有自动运行的设备命令里没有此设备
                    //通过设备编码得到此设备所属的子系统

                    String systemClass = id.substring(0, 6);
                    String deviceId = null;
                    //取相同类型的设备的时间表附给此设备
                    for (DeviceCommand deviceCommand : baseCommands) {
                        String code = deviceCommand.getDeviceId();
                        String cl = code.substring(0, 6);
                        if (cl.equals(systemClass)) {
                            if (deviceId == null) {//不能重复赋值
                                deviceId = code;

                                //将新的设备的命令加入命令列表
                                DeviceCommand deviceCommand1 = new DeviceCommand();

                                BeanUtils.copyProperties(deviceCommand, deviceCommand1);

                                deviceCommand1.setDeviceId(id);
                                deviceCommand1.setExecuteStatus(PatternConst.DEVICE_EXECUTE_NO);

                                baseCommands.add(deviceCommand1);
                            } else {
                                if (code.equals(deviceId)) {
                                    //将新的设备的命令加入命令列表
                                    DeviceCommand deviceCommand1 = new DeviceCommand();

                                    BeanUtils.copyProperties(deviceCommand, deviceCommand1);

                                    deviceCommand1.setDeviceId(id);
                                    deviceCommand1.setExecuteStatus(PatternConst.DEVICE_EXECUTE_NO);

                                    baseCommands.add(deviceCommand1);
                                }
                            }
                        }
                    }
                }
            }
        }
//        DeviceCommand deviceCommand = new DeviceCommand();
//        deviceCommand.setDeviceId(deviceId);
//        deviceCommand.setStatus(PatternConst.DEVICE_EXECUTE_YES);
//        deviceCommand.setExecuteStatus(PatternConst.DEVICE_EXECUTE_YES);
//        deviceCommand.setCommandType(PatternConst.COMMAND_TYPE_MANUAL);
//        deviceCommand.setStrategy(PatternConst.IS_STRATEGY_NO);
//        deviceCommand.setSendTime(nowDate);
//        baseCommands.add(deviceCommand);
        //命令排序
//        orderCommand(baseCommands);

        return baseCommands;
    }

    /**
     * 对列表进行按时间排序  升序
     *
     * @param baseCommands 命令列表
     * @return
     */
    private List<DeviceCommand> orderCommand(List<DeviceCommand> baseCommands) {
        Collections.sort(baseCommands, new Comparator<DeviceCommand>() {
            public int compare(DeviceCommand arg0, DeviceCommand arg1) {
                return arg0.getSendTime().compareTo(arg1.getSendTime());
            }
        });
        return baseCommands;
    }

    /**
     * 去除列表里对同一设备的重复的命令
     *
     * @param baseCommands 命令列表
     * @return
     */
    private List<DeviceCommand> removeRepeatCommand(List<DeviceCommand> baseCommands) {

        List<DeviceCommand> deviceCommands = new ArrayList<DeviceCommand>();
        deviceCommands.add(baseCommands.get(0));
        for (DeviceCommand command : baseCommands) {
            for (DeviceCommand deviceCommand : deviceCommands) {
                if (command.getDeviceId().equals(deviceCommand.getDeviceId())) {
                    deviceCommands.remove(deviceCommand);
                    deviceCommands.add(command);
                }
            }
        }
        return deviceCommands;
    }


    public void savePatternToRecord(UcPattern ucPattern, UcGlobalPattern ucGlobalPattern, String nowDate) {
        try {
            if (ucGlobalPattern != null) {

                UcPatternRecord ucPatternRecord = new UcPatternRecord();

                ucPatternRecord.setCreateDate(ucGlobalPattern.getCreateDate());
                ucPatternRecord.setCreateUser(ucGlobalPattern.getCreateUser());
                ucPatternRecord.setPatternId(ucGlobalPattern.getId());
                ucPatternRecord.setParentId(PatternConst.GLOBAL_PARENTID);
                ucPatternRecord.setPatternName(ucGlobalPattern.getPatternName());
                ucPatternRecord.setPatternType(ucGlobalPattern.getPatternType());

                ucPatternRecord.setSystemId(ucGlobalPattern.getSystemId());
                ucPatternRecord.setVersion(ucGlobalPattern.getVersion());

                UcCalendar ucCalendar = ucCalendarRepository.findOne(nowDate);
                ucPatternRecord.setUcCalendar(ucCalendar);
                ucPatternRecordRepository.save(ucPatternRecord);

                Set<UcCombinationPattern> ucCombinationPatterns = ucGlobalPattern.getUcCombinationPatterns();

                for (UcCombinationPattern ucCombinationPattern : ucCombinationPatterns) {
                    UcPattern ucPatternOld = ucCombinationPattern.getUcPattern();

                    savePatternRecord(ucPatternOld, ucPatternRecord, ucCombinationPattern, nowDate);
                }
            }
            if (ucPattern != null) {
                savePatternRecord(ucPattern, null, null, nowDate);
            }
        } catch (Exception e) {
            logger.error("-------------", e);
        }
    }

    /**
     * 保存模式
     *
     * @param ucPattern
     */
    private void savePatternRecord(UcPattern ucPattern, UcPatternRecord ucPatternRecord, UcCombinationPattern ucCombinationPattern, String nowDate) {
        try {
            ucPattern = ucPatternRepository.findOne(ucPattern.getId());
            UcPatternRecord ucPatternRecordSub = new UcPatternRecord();

            ucPatternRecordSub.setCreateDate(ucPattern.getCreateDate());
            ucPatternRecordSub.setCreateUser(ucPattern.getCreateUser());
            ucPatternRecordSub.setPatternId(ucPattern.getId());

            ucPatternRecordSub.setPatternName(ucPattern.getName());
            ucPatternRecordSub.setPatternType(ucPattern.getPatternType());
            ucPatternRecordSub.setSystemId(ucPattern.getSystemId());
            ucPatternRecordSub.setVersion(ucPattern.getVersion());

//                ucPatternRecordSub.setStartTime(ucPatternOld.getStartDate());
//                ucPatternRecordSub.setEndTime(ucPatternOld.getEndDate());
            UcCalendar ucCalendar = ucCalendarRepository.findOne(nowDate);
            UcWeather ucWeather = ucWeatherRepository.findByUcCalendar_IdAndIsNew(ucCalendar.getId(), PatternConst.WEATHER_IS_NEW_YES);
            ucPatternRecordSub.setUcCalendar(ucCalendar);
            if (ucCombinationPattern != null) {
                ucPatternRecordSub.setOffsetTime(ucCombinationPattern.getOffsetTime());
            }
            if (ucPatternRecord != null) {
                ucPatternRecordSub.setParentId(ucPatternRecord.getId());
            }
            ucPatternRecordRepository.save(ucPatternRecordSub);

            //保存影响因素
            Set<UcPatternFactor> ucFactors = ucPattern.getUcPatternFactors();
            for (UcPatternFactor ucPatternFactor : ucFactors) {
                UcFactor ucFactor = ucPatternFactor.getUcFactor();

                UcFactorRecord ucFactorRecord = new UcFactorRecord();
                ucFactorRecord.setName(ucFactor.getName());
                ucFactorRecord.setDescription(ucFactor.getDescription());
                ucFactorRecord.setEndDate(ucFactor.getEndDate());
                ucFactorRecord.setStartDate(ucFactor.getStartDate());
                ucFactorRecord.setFactorType(ucFactor.getFactorType());
                ucFactorRecord.setMaxValue(ucFactor.getMaxValue());
                ucFactorRecord.setMinValue(ucFactor.getMinValue());
                ucFactorRecord.setUcPatternRecord(ucPatternRecordSub);

                ucFactorRecordRepository.save(ucFactorRecord);
            }

            //保存设备
            Set<UcPatternAction> ucPatternActions = ucPattern.getUcPatternActions();

            for (UcPatternAction ucPatternAction : ucPatternActions) {

                Set<UcDevicePattern> ucDevicePatterns = ucPatternAction.getUcDevicePatterns();

                for (UcDevicePattern ucDevicePattern : ucDevicePatterns) {

                    UcDeviceRecord ucDeviceRecord = new UcDeviceRecord();
                    ucDeviceRecord.setUcPatternRecord(ucPatternRecordSub);
                    ucDeviceRecord.setBaseTime(ucPatternAction.getBaseTime());
                    if (ucPatternAction.getBaseTime().equals(PatternConst.SUNRISE)) {
                        int sunRise = DateTime.parse(ucWeather.getSunrise(), DateTimeFormat.forPattern("HH:mm")).getMinuteOfDay();
                        ucDeviceRecord.setBaseTime(sunRise);
                    }
                    if (ucPatternAction.getBaseTime().equals(PatternConst.SUNSET)) {
                        int sunSet = DateTime.parse(ucWeather.getSunset(), DateTimeFormat.forPattern("HH:mm")).getMinuteOfDay();
                        ucDeviceRecord.setBaseTime(sunSet);
                    }
                    ucDeviceRecord.setDescription(ucPatternAction.getDescription());
                    ucDeviceRecord.setOffsetTime(ucPatternAction.getOffsetTime());
                    ucDeviceRecord.setRelativeTime(ucPatternAction.getRelativeTime());
                    ucDeviceRecord.setActionType(ucPatternAction.getActionType());

                    ucDeviceRecord.setDeviceId(ucDevicePattern.getDevice());
                    ucDeviceRecord.setSelectType(ucDevicePattern.getSelectType());

                    if (ucDevicePattern.getStrategyId() != null && !"".equals(ucDevicePattern.getStrategyId())) {
                        ucDeviceRecord.setStrategyId(ucDevicePattern.getStrategyId());
                        Date s = timeFormat.parse(timeString(ucDevicePattern.getStartTime()));
                        Date e = timeFormat.parse(timeString(ucDevicePattern.getEndTime()));
                        ucDeviceRecord.setStartTime(s);
                        ucDeviceRecord.setEndTime(e);
                    }
                    ucDeviceRecordRepository.save(ucDeviceRecord);
                    //保存此设备或设备组的运行参数
                    Set<UcRunParam> ucRunParams = ucDevicePattern.getUcRunParams();

                    for (UcRunParam ucRunParam : ucRunParams) {
                        UcParamRecord ucParamRecord = new UcParamRecord();
                        ucParamRecord.setParamName(ucRunParam.getParamName());
                        ucParamRecord.setParamValue(ucRunParam.getParamValue());
                        ucParamRecord.setUcDeviceRecord(ucDeviceRecord);
                        ucParamRecordRepository.save(ucParamRecord);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("-----Exception--------", e);
        }
    }

    /**
     * 将分钟数变为时间。一天之内的。
     *
     * @param minutes 分钟数
     * @return
     */
    private String timeString(double minutes) {
        double floatHour = minutes / 60;
        double hour = Math.floor(floatHour);
        long h = Math.round(hour);

        double floatMinute = 60 * (floatHour - Math.floor(floatHour));
        long m = Math.round(floatMinute);

        String timeStr = h + ":";
        if (h < 10) {
            timeStr = "0" + h + ":";
        }
        if (m < 10)    //	i.e. only one digit
            timeStr += "0" + m;
        else
            timeStr += m;

        return timeStr;
    }


    public void saveCalendar(DateTime dateTime) {
        try {
//        Date nowDate=dateFormat.parse(date);
//            Calendar calendar=Calendar.getInstance();
//            calendar.setTime(nowDate);
            int year = dateTime.getYear();
            int month = dateTime.getMonthOfYear();
            int day = dateTime.getDayOfMonth();
            String date = dateTime.toString("yyyyMMdd");
            LunarUtil lunar = new LunarUtil();
            String ss = lunar.getLunarDate(year, month, day);


            UcCalendar ucCalendar = new UcCalendar();
            ucCalendar.setCalendarYear(year + "");
            ucCalendar.setId(date);
            ucCalendar.setCalendarDay(day + "");
            ucCalendar.setCalendarMonth(month + "");

            ucCalendar.setDayWeek(dateTime.getDayOfWeek() + "");

            //系统的开店和闭店时间
            Date sysOpenTime = null;
            Date sysCloseTime = null;
            Date nowMoth = dateFormat.parse(dateTime.toString("yyyy-MM-dd"));

            List<UcSystemBusinessTime> ucSystemBusinessTimes = ucSystemBusinessTimeRepository.findAll();
            if (ucSystemBusinessTimes != null && ucSystemBusinessTimes.size() > 0) {
                for (UcSystemBusinessTime ucSystemBusinessTime : ucSystemBusinessTimes) {
                    if (ucSystemBusinessTime.getStartDate() != null) {
                        //多个开店日期，先判断信是否在此时间段内
                        Date start = ucSystemBusinessTime.getStartDate();
                        Date end = ucSystemBusinessTime.getEndDate();
                        if ((nowMoth.equals(start) || nowMoth.after(start)) && (nowMoth.equals(end) || nowMoth.before(end))) {
                            sysOpenTime = ucSystemBusinessTime.getOpenTime();
                            sysCloseTime = ucSystemBusinessTime.getCloseTime();
                        }
                    } else {
                        sysOpenTime = ucSystemBusinessTime.getOpenTime();
                        sysCloseTime = ucSystemBusinessTime.getCloseTime();
                    }
                }
            } else {
                sysOpenTime = timeFormat.parse("10:00");
                sysCloseTime = timeFormat.parse("22:00");
            }
            ucCalendar.setCloseTime(sysCloseTime);
            ucCalendar.setOpenTime(sysOpenTime);

            ucCalendar.setWeekYear(dateTime.getWeekOfWeekyear() + "");

            ucCalendar.setLunarCalendar(ss);
            ucCalendar.setLunarYear(lunar.getYear() + "");
            ucCalendar.setLunarMonth(lunar.getMonth() + "");
            ucCalendar.setLunarDay(lunar.getDay() + "");
            ucCalendar = ucCalendarRepository.save(ucCalendar);

            UcWeather ucWeather = new UcWeather();
            String localTimeZone = config.getProps().getProperty("localTimeZone");
            String localLatitude = config.getProps().getProperty("localLatitude");
            String localLongitude = config.getProps().getProperty("localLongitude");
            String sr = SunriseSunset.getSunrise(dateTime.toDate(), Double.parseDouble(localLatitude), Double.parseDouble(localLongitude), Integer.parseInt(localTimeZone));
            String suns = SunriseSunset.getSunset(dateTime.toDate(), Double.parseDouble(localLatitude), Double.parseDouble(localLongitude), Integer.parseInt(localTimeZone));
            ucWeather.setSunrise(sr);
            ucWeather.setSunset(suns);
            ucWeather.setUcCalendar(ucCalendar);

            ucWeather.setIsNew(PatternConst.WEATHER_IS_NEW_YES);
            ucWeather.setWeatherType(PatternConst.WEATHER_TYPE_PREDICT);


            ucWeatherRepository.save(ucWeather);

            UcHoliday ucHoliday = new UcHoliday();
            String holiday = lunar.getHoliday();
            if (holiday != null && !"".equals(holiday)) {
                ucHoliday.setHolidayName(holiday);
                ucHoliday.setIsHoliday(PatternConst.HOLIDAY_YES);
                ucHoliday.setHolidayType(PatternConst.HOLIDAY_TYPE_GB);
            } else {
                holiday = PatternConst.getGBHoliday(month, day);
                if (holiday != null) {
                    ucHoliday.setHolidayName(holiday);
                    ucHoliday.setIsHoliday(PatternConst.HOLIDAY_YES);
                    ucHoliday.setHolidayType(PatternConst.HOLIDAY_TYPE_GB);
                } else {
                    ucHoliday.setIsHoliday(PatternConst.HOLIDAY_NO);
                    ucHoliday.setHolidayType(PatternConst.HOLIDAY_TYPE_ORDINARY);
                }
            }
            ucHoliday.setUcCalendar(ucCalendar);
            ucHolidayRepository.save(ucHoliday);

        } catch (Exception e) {
            logger.error("------saveCalendar---------", e);
        }
        //To change body of implemented methods use File | Settings | File Templates.
    }

    //====================================================================================


    public UcCalendar findUcCalendarById(String id) {
        UcCalendar ucCalendar = ucCalendarRepository.findOne(id);

        List<UcHoliday> ucHolidays = ucHolidayRepository.findByUcCalendarId(ucCalendar.getId());
        ucCalendar.setUcHolidays(new HashSet<UcHoliday>(ucHolidays));
        //查询最新的天气
        UcWeather ucWeather = ucWeatherRepository.findByUcCalendar_IdAndIsNew(ucCalendar.getId(), PatternConst.WEATHER_IS_NEW_YES);

        Set<UcWeather> ucWeathers = new HashSet<UcWeather>();
        ucWeathers.add(ucWeather);
        ucCalendar.setUcWeathers(ucWeathers);

        return ucCalendar;
    }


    public List<UcCalendar> findUcCalendarByCalendar(String startDate, String endDate) {
        List<UcCalendar> ucCalendars = ucCalendarRepository.findByIdBetweenOrderByIdAsc(startDate, endDate);
        for (UcCalendar ucCalendar : ucCalendars) {
            List<UcHoliday> ucHolidays = ucHolidayRepository.findByUcCalendarId(ucCalendar.getId());
            ucCalendar.setUcHolidays(new HashSet<UcHoliday>(ucHolidays));
            UcWeather ucWeather = ucWeatherRepository.findByUcCalendar_IdAndIsNew(ucCalendar.getId(), PatternConst.WEATHER_IS_NEW_YES);
            ucWeather.setUcCalendar(null);
            logger.debug(ucCalendar.getId()+"ucWeather---"+ucWeather.toString());
            Set ucWeatherSet=new HashSet<UcWeather>();
            ucWeatherSet.add(ucWeather);
            ucCalendar.setUcWeathers(ucWeatherSet);
            ucCalendar.setUcPatternRecords(null);
        }
        return ucCalendars;
    }


    public List<UcWeather> findUcWeatherByCalendarId(String calendarId) {
        return ucWeatherRepository.findByUcCalendar_Id(calendarId);
    }


    public UcPatternRecord findUcPatternToUcPatternRecord(UcPattern ucPattern) {

        UcPatternRecord ucPatternRecordSub = new UcPatternRecord();
        try {

            ucPattern = ucPatternRepository.findOne(ucPattern.getId());
            ucPatternRecordSub.setCreateDate(ucPattern.getCreateDate());
            ucPatternRecordSub.setCreateUser(ucPattern.getCreateUser());
            ucPatternRecordSub.setPatternId(ucPattern.getId());

            ucPatternRecordSub.setPatternName(ucPattern.getName());
            ucPatternRecordSub.setPatternType(ucPattern.getPatternType());
            ucPatternRecordSub.setSystemId(ucPattern.getSystemId());
            ucPatternRecordSub.setVersion(ucPattern.getVersion());

//                ucPatternRecordSub.setStartTime(ucPatternOld.getStartDate());
//                ucPatternRecordSub.setEndTime(ucPatternOld.getEndDate());


            //保存影响因素
            List<UcPatternFactor> ucFactors = ucPatternFactorRepository.findByUcPattern_Id(ucPattern.getId());
            for (UcPatternFactor ucPatternFactor : ucFactors) {
                UcFactor ucFactor = ucPatternFactor.getUcFactor();

                UcFactorRecord ucFactorRecord = new UcFactorRecord();
                ucFactorRecord.setName(ucFactor.getName());
                ucFactorRecord.setDescription(ucFactor.getDescription());
                ucFactorRecord.setEndDate(ucFactor.getEndDate());
                ucFactorRecord.setStartDate(ucFactor.getStartDate());
                ucFactorRecord.setFactorType(ucFactor.getFactorType());
                ucFactorRecord.setMaxValue(ucFactor.getMaxValue());
                ucFactorRecord.setMinValue(ucFactor.getMinValue());
                ucFactorRecord.setUcPatternRecord(ucPatternRecordSub);

                ucPatternRecordSub.getUcFactorRecords().add(ucFactorRecord);
            }

            //保存设备
            List<UcPatternAction> ucPatternActions = ucPatternActionRepository.findByUcPattern_Id(ucPattern.getId());

            for (UcPatternAction ucPatternAction : ucPatternActions) {

                List<UcDevicePattern> ucDevicePatterns = ucDevicePatternRepository.findByUcPatternAction_Id(ucPatternAction.getId());

                for (UcDevicePattern ucDevicePattern : ucDevicePatterns) {

                    UcDeviceRecord ucDeviceRecord = new UcDeviceRecord();
                    ucDeviceRecord.setUcPatternRecord(ucPatternRecordSub);
                    ucDeviceRecord.setBaseTime(ucPatternAction.getBaseTime());
                    ucDeviceRecord.setDescription(ucPatternAction.getDescription());
                    ucDeviceRecord.setOffsetTime(ucPatternAction.getOffsetTime());
                    ucDeviceRecord.setRelativeTime(ucPatternAction.getRelativeTime());
                    ucDeviceRecord.setActionType(ucPatternAction.getActionType());

                    ucDeviceRecord.setDeviceId(ucDevicePattern.getDevice());
                    ucDeviceRecord.setSelectType(ucDevicePattern.getSelectType());

                    if (ucDevicePattern.getStrategyId() != null && !"".equals(ucDevicePattern.getStrategyId())) {
                        ucDeviceRecord.setStrategyId(ucDevicePattern.getStrategyId());
                        Date s = timeFormat.parse(timeString(ucDevicePattern.getStartTime()));
                        Date e = timeFormat.parse(timeString(ucDevicePattern.getEndTime()));
                        ucDeviceRecord.setStartTime(s);
                        ucDeviceRecord.setEndTime(e);
                    }

                    //保存此设备或设备组的运行参数
                    List<UcRunParam> ucRunParams = ucRunParamRepository.findByUcDevicePattern_Id(ucDevicePattern.getId());

                    for (UcRunParam ucRunParam : ucRunParams) {
                        UcParamRecord ucParamRecord = new UcParamRecord();
                        ucParamRecord.setParamName(ucRunParam.getParamName());
                        ucParamRecord.setParamValue(ucRunParam.getParamValue());
                        ucParamRecord.setUcDeviceRecord(ucDeviceRecord);
                        ucDeviceRecord.getUcParamRecords().add(ucParamRecord);
                    }

                    ucPatternRecordSub.getUcDeviceRecords().add(ucDeviceRecord);
                }
            }
        } catch (Exception e) {
            logger.error("-------------------", e);
        }
        return ucPatternRecordSub;
    }


    public void saveUcWeathers(String startDate, String endDate, UcWeather ucWeather) {
        if (startDate == null || "".equals(startDate)) {
            startDate = dateFormat.format(new Date());
        }
        if (endDate == null || "".equals(endDate)) {
            endDate = dateFormat.format(new Date());
        }
        if (startDate.equals(endDate)) {
            //目前只支持一个
            UcWeather ucWeather1 = ucWeatherRepository.findByUcCalendar_IdAndIsNew(startDate, PatternConst.WEATHER_IS_NEW_YES);

            transferWeather(ucWeather, ucWeather1);
            ucWeatherRepository.saveAndFlush(ucWeather1);
        } else {
            List<UcWeather> ucWeathers = ucWeatherRepository.findByUcCalendar_IdBetweenAnd_IsNew(startDate, endDate, PatternConst.WEATHER_IS_NEW_YES);
            logger.debug("-----ucWeathers---------" + ucWeathers.size());
            for (UcWeather ucWeather1 : ucWeathers) {

                transferWeather(ucWeather, ucWeather1);
                ucWeatherRepository.saveAndFlush(ucWeather1);
            }
        }
    }

    private void transferWeather(UcWeather newWeather, UcWeather oldWeather) {
        if (newWeather.getWeather() != null && !"".equals(newWeather.getWeather()))
            oldWeather.setWeather(newWeather.getWeather());
        if (newWeather.getWeatherDescription() != null && !"".equals(newWeather.getWeatherDescription()))
            oldWeather.setWeatherDescription(newWeather.getWeatherDescription());
        if (newWeather.getSunset() != null && !"".equals(newWeather.getSunset()))
            oldWeather.setSunset(newWeather.getSunset());
        if (newWeather.getSunrise() != null && !"".equals(newWeather.getSunrise()))
            oldWeather.setSunrise(newWeather.getSunrise());
        if (newWeather.getMinTemperature() != null)
            oldWeather.setMinTemperature(newWeather.getMinTemperature());
        if (newWeather.getMaxTemperature() != null)
            oldWeather.setMaxTemperature(newWeather.getMaxTemperature());
        if (newWeather.getHumidity() != null && !"".equals(newWeather.getHumidity()))
            oldWeather.setHumidity(newWeather.getHumidity());
        if (newWeather.getWeatherIcon() != null && !"".equals(newWeather.getWeatherIcon()))
            oldWeather.setWeatherIcon(newWeather.getWeatherIcon());
    }


    public void saveUcHolidays(String startDate, String endDate, String holidayName, String holidayType) {
        if (startDate == null || "".equals(startDate)) {
            startDate = dateFormat.format(new Date());
        }
        if (endDate == null || "".equals(endDate)) {
            endDate = dateFormat.format(new Date());
        }
        List<UcHoliday> ucHolidays = ucHolidayRepository.findByUcCalendar_IdBetween(startDate, endDate);
        UcHoliday ucHoliday = ucHolidays.get(0);
        ucHoliday.setHolidayType(holidayType);
        ucHoliday.setIsHoliday(PatternConst.HOLIDAY_YES);
        ucHoliday.setHolidayName(holidayName);
        ucHolidayRepository.saveAndFlush(ucHoliday);

    }


    public List<UcPatternRecord> findUcPatternRecordBySystemIdAndDate(String systemId, String nowDate) {
        logger.debug("-findUcPatternRecordBySystemIdAndDate---------" + systemId + "-----" + nowDate);
        List<UcPatternRecord> ucPatternRecordList = ucPatternRecordRepository.findBySystemIdAndUcCalendar_Id(systemId, nowDate);
        for (UcPatternRecord ucPatternRecord : ucPatternRecordList) {
//            UcCalendar ucCalendar= ucCalendarRepository.findOne(ucPatternRecord.getUcCalendar().getId());
            UcCalendar ucCalendar = ucPatternRecord.getUcCalendar();
            ucCalendar.setUcWeathers(null);
            ucCalendar.setUcHolidays(null);
            ucCalendar.setUcPatternRecords(null);
            ucPatternRecord.setUcCalendar(ucCalendar);
            logger.debug("-getPatternName----2-----" + ucPatternRecord.getPatternName());

            Set<UcDeviceRecord> ucDeviceRecords = ucPatternRecord.getUcDeviceRecords();
            for (UcDeviceRecord ucDeviceRecord : ucDeviceRecords) {
                Set<UcParamRecord> ucParamRecords = ucDeviceRecord.getUcParamRecords();
                for (UcParamRecord ucParamRecord : ucParamRecords) {
                    ucParamRecord.setUcDeviceRecord(null);
                }

                ucDeviceRecord.setUcParamRecords(ucParamRecords);
                ucDeviceRecord.setUcPatternRecord(null);
            }
            logger.debug("-findUcPatternRecordBySystemIdAndDate-----3----");
            ucPatternRecord.setUcDeviceRecords(ucDeviceRecords);
            ucPatternRecord.setUcFactorRecords(null);
        }
        return ucPatternRecordList;
    }


    public List<UcPatternRecord> findUcPatternRecordBySystemIdAndDate(String dateType, String systemId, String startDate, String endDate) {

        logger.debug("-findUcPatternRecordBySystemIdAndDate-----start----");
        List<UcPatternRecord> ucPatternRecordList = ucPatternRecordRepository.findBySystemIdAndUcCalendar_IdGreaterThanAndUcCalendar_IdLessThanOrderByUcCalendar_IdAsc(systemId, startDate, endDate);
        logger.debug("-findUcPatternRecordBySystemIdAndDate-----go----");
        if (dateType != null && dateType.equals(PatternConst.SYSTEM_VIEW_MONTH)) {  //日视图和周视图不查询具体记录。
            for (UcPatternRecord ucPatternRecord : ucPatternRecordList) {
                ucPatternRecord.setUcDeviceRecords(null);
                ucPatternRecord.setUcFactorRecords(null);
                UcCalendar ucCalendar = ucPatternRecord.getUcCalendar();
                ucCalendar.setUcHolidays(null);
                ucCalendar.setUcWeathers(null);
                ucCalendar.setUcPatternRecords(null);
            }
        } else {
            for (UcPatternRecord ucPatternRecord : ucPatternRecordList) {
//                logger.debug("---1------go----");
                UcCalendar ucCalendar = ucPatternRecord.getUcCalendar();

//                logger.debug("---2------go----");
                Set<UcHoliday> ucHolidays = ucCalendar.getUcHolidays();
                for (UcHoliday ucHoliday : ucHolidays) {
                    ucHoliday.setUcCalendar(null);
                }

//                logger.debug("---3------go----");
                UcWeather ucWeather = ucWeatherRepository.findByUcCalendar_IdAndIsNew(ucCalendar.getId(), PatternConst.WEATHER_IS_NEW_YES);
                ucWeather.setUcCalendar(null);
                ucCalendar.setUcPatternRecords(null);

                ucPatternRecord.setUcCalendar(ucCalendar);
//                logger.debug("---4------go----");

                Set<UcDeviceRecord> ucDeviceRecords = ucPatternRecord.getUcDeviceRecords();
                for (UcDeviceRecord ucDeviceRecord : ucDeviceRecords) {
                    Set<UcParamRecord> ucParamRecords = ucDeviceRecord.getUcParamRecords();
                    for (UcParamRecord ucParamRecord : ucParamRecords) {
                        ucParamRecord.setUcDeviceRecord(null);
                    }

                    ucDeviceRecord.setUcParamRecords(ucParamRecords);
                    ucDeviceRecord.setUcPatternRecord(null);
                }
//                logger.debug("---5------go----");
                ucPatternRecord.setUcDeviceRecords(ucDeviceRecords);
                ucPatternRecord.setUcFactorRecords(null);
            }
        }
        logger.debug("-findUcPatternRecordBySystemIdAndDate-----end----");
        return ucPatternRecordList;
    }


    public void saveFactor(UcFactor ucFactor) {
        ucFactorRepository.saveAndFlush(ucFactor);
    }


    public List<UcSysFactor> findFactorBySystemId(String sysId) {
        List<UcSysFactor> sysFactors = ucSysFactorRepository.findBySystemId(sysId);
        for (UcSysFactor ucSysFactor : sysFactors) {
            String fId = ucSysFactor.getUcFactor().getId();
            List<UcFactor> ucFactors = ucFactorRepository.findByParentId(fId);
            //   ucSysFactor.getUcFactor().setChildUcFactor(ucFactors);
        }
        return sysFactors;
    }


    public List<UcFactor> findFactorByParentId(String parentId) {
        return ucFactorRepository.findByParentId(parentId);
    }


    public Page<UcFactor> findFactorAll(Pageable pageable) {
        return ucFactorRepository.findAll(pageable);
    }


    public void savePattern(UcPattern ucPattern, List<UcPatternFactor> ucPatternFactors, UcPatternAction ucPatternAction) {

        ucPatternRepository.save(ucPattern);
        if (ucPatternAction != null) {
            ucPatternActionRepository.save(ucPatternAction);
        }
        if (ucPatternFactors != null) {
            for (UcPatternFactor ucPatternFactor : ucPatternFactors) {
                ucPatternFactorRepository.save(ucPatternFactor);
            }
        }
    }


    public void delPattern(String id) {
        ucPatternRepository.delete(id);
    }


    public List<UcPattern> findPatternBySystemId(String sysId) {
        return ucPatternRepository.findBySystemId(sysId);
    }


    public List<UcDeviceSystem> findDeviceSystemByParentId(String parentId) {
        return ucDeviceSystemRepository.findByParentId(parentId);
    }


    public List<UcPattern> findUcPatternBySystem(String systemId, String patternType, String orderType) {

        logger.debug("-----1----" + systemId + "----patternType---" + patternType + "---orderType---" + orderType);
        List<UcPattern> ucPatternList = ucPatternRepository.findBySystemIdAndPatternTypeAndOrderType(systemId, patternType, orderType);
        for (UcPattern ucPattern : ucPatternList) {

            ucPattern.setUcCombinationPatterns(null);
            List<UcPatternFactor> ucPatternFactorList = ucPatternFactorRepository.findByUcPattern_Id(ucPattern.getId());
            logger.debug("-----2----" + systemId + "----patternType---" + patternType + "---orderType---" + orderType);
            for (UcPatternFactor ucPatternFactor : ucPatternFactorList) {
                UcFactor ucFactor = ucPatternFactor.getUcFactor();
                ucPatternFactor.setUcPattern(null);
            }
            ucPattern.setUcPatternFactors(new HashSet<UcPatternFactor>(ucPatternFactorList));
            List<UcPatternAction> ucPatternActionList = ucPatternActionRepository.findByUcPattern_Id(ucPattern.getId());
            logger.debug("-----3----" + systemId + "----patternType---" + patternType + "---orderType---" + orderType);
            for (UcPatternAction ucPatternAction : ucPatternActionList) {
                List<UcDevicePattern> ucDevicePatternSet = ucDevicePatternRepository.findByUcPatternAction_Id(ucPatternAction.getId());
                logger.debug("-----4----" + systemId + "----patternType---" + patternType + "---orderType---" + orderType);
                for (UcDevicePattern ucDevicePattern : ucDevicePatternSet) {
                    List<UcRunParam> ucRunParams = ucRunParamRepository.findByUcDevicePattern_Id(ucDevicePattern.getId());
                    ucDevicePattern.setUcRunParams(new HashSet<UcRunParam>(ucRunParams));
                    logger.debug("-----5----" + systemId + "----patternType---" + patternType + "---orderType---" + orderType);
                }
                ucPatternAction.setUcDevicePatterns(new HashSet<UcDevicePattern>(ucDevicePatternSet));
            }
            ucPattern.setUcPatternActions(new HashSet<UcPatternAction>(ucPatternActionList));
        }
        logger.debug("-----6----" + systemId + "----patternType---" + patternType + "---orderType---" + orderType);
        return ucPatternList;
    }


    public List<UcDeviceSystem> findSubSystemBySystem(String systemId) {
        return ucDeviceSystemRepository.findByParentId(systemId);
    }


    public void savePredictPattern(String startDate, String endDate, String patternId) {
        if (startDate != null && endDate != null && !"".equals(startDate) && !"".equals(endDate)) {

//            DateTime nowDate = DateTime.parse(startDate, DateTimeFormat.forPattern("yyyyMMdd"));
            logger.debug("--------patternId--------" + patternId);
            StringTokenizer stringTokenizer = new StringTokenizer(patternId, ",");
            while (stringTokenizer.hasMoreTokens()) {
                String pId = stringTokenizer.nextToken();
                UcPattern ucPattern = ucPatternRepository.findOne(pId);
                DateTime nowDate = DateTime.parse(startDate, DateTimeFormat.forPattern("yyyyMMdd"));
                boolean isAdd = true;
                do {
                    String da = nowDate.toString("yyyyMMdd");
                    List<UcPatternRecord> ucPatternRecords = ucPatternRecordRepository.findBySystemIdAndUcCalendar_Id(ucPattern.getSystemId(), da);
                    if (ucPatternRecords != null && ucPatternRecords.size() > 0) {
                        //已经存在今天的运行模式，将不在存入。进行删除
                        for (UcPatternRecord ucPatternRecord : ucPatternRecords) {
                            ucPatternRecordRepository.delete(ucPatternRecord);
                        }
                    }
                    savePatternToRecord(ucPattern, null, da);
                    logger.debug("------savePredictPattern----------" + startDate + "----" + endDate + "----" + da);
                    if (da.equals(endDate)) {
                        isAdd = false;
                    } else {
                        nowDate = nowDate.plusDays(1);
                    }
                } while (isAdd);
            }
        }
    }


    public UcPattern findUcPatternById(String patternId) {
        UcPattern ucPattern = ucPatternRepository.findOne(patternId);
        ucPattern.setUcCombinationPatterns(null);
        List<UcPatternFactor> ucPatternFactorList = ucPatternFactorRepository.findByUcPattern_Id(ucPattern.getId());
        for (UcPatternFactor ucPatternFactor : ucPatternFactorList) {
            ucPatternFactor.getUcFactor().setUcSysFactors(null);
            ucPatternFactor.getUcFactor().setUcPatternFactors(new HashSet<UcPatternFactor>(ucPatternFactorList));
            ucPatternFactor.setUcPattern(null);
        }
        ucPattern.setUcPatternFactors(new HashSet<UcPatternFactor>(ucPatternFactorList));
        List<UcPatternAction> ucPatternActionList = ucPatternActionRepository.findByUcPattern_Id(ucPattern.getId());
        for (UcPatternAction ucPatternAction : ucPatternActionList) {
            List<UcDevicePattern> ucDevicePatternSet = ucDevicePatternRepository.findByUcPatternAction_Id(ucPatternAction.getId());
            ucPatternAction.setUcPattern(null);
            ucPatternAction.setUcDevicePatterns(new HashSet<UcDevicePattern>(ucDevicePatternSet));
            for (UcDevicePattern ucDevicePattern : ucDevicePatternSet) {
                ucDevicePattern.setUcPatternAction(null);
                List<UcRunParam> ucRunParams = ucRunParamRepository.findByUcDevicePattern_Id(ucDevicePattern.getId());
                for (UcRunParam ucRunParam : ucRunParams) {
                    ucRunParam.setUcDevicePattern(null);
                }
                ucDevicePattern.setUcRunParams(new HashSet<UcRunParam>(ucRunParams));
            }
        }
        ucPattern.setUcPatternActions(new HashSet<UcPatternAction>(ucPatternActionList));
        return ucPattern;
    }


    public void updateWeatherForTimeTask(Weather weather, String getType) {
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss +0800", Locale.ENGLISH);
        try {
            if (getType.equals(PatternConst.GET_WEATHER_CURRENT)) {
                Observation observation = weather.getCurrent_observation();

                //todo 初始化时间的时候，将天气变为 isNew Y前台显示 要查询 isNew
                UcWeather weather1 = new UcWeather();
                Date date = simpleDateFormat1.parse(observation.getObservation_time());
                DateTime dt = new DateTime(date.getTime());

                weather1.setIssueTime(dt.toDate());

                weather1.setWeatherDescription(observation.getWeather());
                weather1.setHumidity(observation.getRelative_humidity());
                weather1.setTemperature(observation.getTemp_c());
                weather1.setWindDir(observation.getWind_dir());
                String wins = observation.getWind_kph();
                if (wins != null && !"".equals(wins)) {
                    weather1.setWindSpeed(observation.getWind_kph());
                }
                String solar = observation.getSolarradiation();
                if (solar != null && !"".equals(solar) && !"--".equals(solar)) {
                    weather1.setWindSpeed(observation.getSolarradiation());
                }

                String airPress = observation.getPressure_mb();
                if (airPress != null && !"".equals(airPress)) {
                    weather1.setAirPressure(airPress);
                }
                weather1.setWeatherIcon(observation.getIcon());
                weather1.setWeather(PatternConst.getWeatherDesc(weather1.getWeatherDescription()));

                //查询日历
                //日历ID
                String id = dt.toString("yyyyMMdd");
                UcCalendar ucCalendar = ucCalendarRepository.findOne(id);

                List<UcWeather> ucWeathers = ucWeatherRepository.findByUcCalendar_Id(id);
                for (UcWeather ucWeather : ucWeathers) {
                    String isNew = ucWeather.getIsNew();
                    if (isNew.equals(PatternConst.WEATHER_IS_NEW_YES)) {
                        ucWeather.setWeather(PatternConst.getWeatherDesc(weather1.getWeatherDescription()));
                        ucWeather.setWeatherDescription(weather1.getWeatherDescription());
                        ucWeather.setIssueTime(weather1.getIssueTime());
                        ucWeather.setWeatherIcon(weather1.getWeatherIcon());
                        ucWeather.setAirPressure(weather1.getAirPressure());
                        ucWeather.setSolarradiation(weather1.getSolarradiation());
                        ucWeather.setWindDir(weather1.getWindDir());
                        ucWeather.setWindSpeed(weather1.getWindSpeed());
                        ucWeather.setTemperature(weather1.getTemperature());
                        ucWeatherRepository.save(ucWeather);
                    }
                }
                weather1.setUcCalendar(ucCalendar);
                weather1.setWeatherType(PatternConst.WEATHER_TYPE_REAL);
                weather1.setIsNew(PatternConst.WEATHER_IS_NEW_NO);
                ucWeatherRepository.save(weather1);

            }
            if (getType.equals(PatternConst.GET_WEATHER_FORCAST)) {

                Observation observation = weather.getForecast();
                Simpleforecast simpleforecast = observation.getSimpleforecast();
                List<Observation> observationList = simpleforecast.getForecastday();
                for (Observation obser : observationList) {

                    UcWeather weather1 = new UcWeather();
                    weather1.setWeatherDescription(obser.getConditions());
                    weather1.setWeather(PatternConst.getWeatherDesc(weather1.getWeatherDescription()));
                    weather1.setHumidity(obser.getAvehumidity());
                    Avewind avewind = obser.getAvewind();

                    weather1.setWindDir(avewind.getDir());
                    String wins = avewind.getKph();
                    if (wins != null && !"".equals(wins)) {
                        weather1.setWindSpeed(wins);
                    }
                    Temperatrue high = obser.getHigh();
                    Temperatrue low = obser.getLow();
                    if (high != null) {
                        if (high.getCelsius() != null && !"".equals(high.getCelsius())) {
                            weather1.setMaxTemperature(Integer.parseInt(high.getCelsius()));
                        }

                    }
                    if (low != null) {
                        if (low.getCelsius() != null && !"".equals(low.getCelsius())) {
                            weather1.setMinTemperature(Integer.parseInt(low.getCelsius()));
                        }
                    }

                    WeatherDate weatherDate = obser.getDate();
                    String moth = null;
                    if (weatherDate.getMonth().length() < 2) {
                        moth = "0" + weatherDate.getMonth();
                    } else {
                        moth = weatherDate.getMonth();
                    }
                    String day = null;
                    if (weatherDate.getDay().length() < 2) {
                        day = "0" + weatherDate.getDay();
                    } else {
                        day = weatherDate.getDay();
                    }
                    String dd = weatherDate.getYear() + moth + day;

                    logger.debug("---weather is ---" + dd);
                    UcCalendar ucCalendar = ucCalendarRepository.findOne(dd);

                    weather1.setWeatherIcon(obser.getIcon());
                    weather1.setUcCalendar(ucCalendar);
                    weather1.setWeatherType(PatternConst.WEATHER_TYPE_PREDICT);
                    weather1.setIsNew(PatternConst.WEATHER_IS_NEW_NO);

                    List<UcWeather> ucWeathers = ucWeatherRepository.findByUcCalendar_Id(dd);
                    for (UcWeather ucWeather : ucWeathers) {
                        String isNew = ucWeather.getIsNew();
                        if (isNew.equals(PatternConst.WEATHER_IS_NEW_YES)) {
                            ucWeather.setWeatherDescription(weather1.getWeatherDescription());
                            ucWeather.setWeather(weather1.getWeather());
                            ucWeather.setWeatherIcon(weather1.getWeatherIcon());
                            ucWeather.setWindDir(weather1.getWindDir());
                            ucWeather.setWindSpeed(weather1.getWindSpeed());
                            ucWeather.setMaxTemperature(weather1.getMaxTemperature());
                            ucWeather.setMinTemperature(weather1.getMinTemperature());
                            ucWeather.setHumidity(weather1.getHumidity());
                            ucWeather.setIssueTime(weather1.getIssueTime());
                            ucWeatherRepository.save(ucWeather);
                        }
                    }

                    ucWeatherRepository.save(weather1);
                }
            }
            if (getType.equals(PatternConst.GET_WEATHER_YESTERDAY)) {

                History history = weather.getHistory();
                List<Observation> observations = history.getDailysummary();
                if (observations == null || observations.size() == 0) {
                    //todo 没有取到天气信息
                } else {
                    Observation observation = observations.get(0);
                    UcWeather weather1 = new UcWeather();
                    weather1.setFog(observation.getFog());
                    weather1.setRain(observation.getRain());
                    weather1.setSnow(observation.getSnow());
                    String mintempm = observation.getMintempm();
                    if (mintempm != null && !"".equals(mintempm)) {
                        weather1.setMinTemperature(Integer.parseInt(observation.getMintempm()));
                    }
                    String maxtempm = observation.getMaxtempm();
                    if (maxtempm != null && !"".equals(maxtempm)) {
                        weather1.setMaxTemperature(Integer.parseInt(observation.getMaxtempm()));
                    }

                    weather1.setWindDir(observation.getMeanwdire());
                    String wins = observation.getMeanwindspdm();
                    if (wins != null && !"".equals(wins)) {
                        weather1.setWindSpeed(observation.getMeanwindspdm());
                    }
                    String airPress = observation.getMeanpressurem();
                    if (airPress != null && !"".equals(airPress)) {
                        weather1.setAirPressure(airPress);
                    }
                    weather1.setHumidity(observation.getHumidity());
                    WeatherDate weatherDate = observation.getDate();

                    String moth = null;
                    if (weatherDate.getMon().length() < 2) {
                        moth = "0" + weatherDate.getMon();
                    } else {
                        moth = weatherDate.getMon();
                    }
                    String day = null;
                    if (weatherDate.getMday().length() < 2) {
                        day = "0" + weatherDate.getMday();
                    } else {
                        day = weatherDate.getMday();
                    }
                    String dd = weatherDate.getYear() + moth + day;


                    UcCalendar ucCalendar = ucCalendarRepository.findOne(dd);

                    List<UcWeather> ucWeathers = ucWeatherRepository.findByUcCalendar_Id(dd);
                    for (UcWeather ucWeather : ucWeathers) {
                        String isNew = ucWeather.getIsNew();
                        if (isNew.equals(PatternConst.WEATHER_IS_NEW_YES)) {
                            ucWeather.setFog(weather1.getFog());
                            ucWeather.setRain(weather1.getRain());
                            ucWeather.setSnow(weather1.getSnow());
                            ucWeather.setAirPressure(weather1.getAirPressure());
                            ucWeather.setWindDir(weather1.getWindDir());
                            ucWeather.setWindSpeed(weather1.getWindSpeed());
                            ucWeather.setMaxTemperature(weather1.getMaxTemperature());
                            ucWeather.setMinTemperature(weather1.getMinTemperature());
                            ucWeather.setHumidity(weather1.getHumidity());
                            ucWeatherRepository.save(ucWeather);
                        }
                    }
                    weather1.setUcCalendar(ucCalendar);
                    weather1.setWeatherType(PatternConst.WEATHER_TYPE_REAL);
                    weather1.setIsNew(PatternConst.WEATHER_IS_NEW_NO);
                    ucWeatherRepository.save(weather1);
                    logger.debug("" + weather1.toString());
                }
            }
        } catch (ParseException p) {
            logger.error("-----", p);
        }
    }


    public String saveGlobalPattern(UcGlobalPattern ucGlobalPattern) {
        Set<UcCombinationPattern> ucCombinationPatterns = ucGlobalPattern.getUcCombinationPatterns();
        UcGlobalPattern ucGlobalPattern1 = ucGlobalPatternRepository.save(ucGlobalPattern);
        for (UcCombinationPattern ucCombinationPattern : ucCombinationPatterns) {
            ucCombinationPattern.setUcGlobalPattern(ucGlobalPattern1);
            ucCombinationPatternRepository.save(ucCombinationPattern);
        }
        return ucGlobalPattern1.getId();
    }


    public String saveGlobalPatternToPattern(UcCombinationPattern ucCombinationPatterns) {
        String systemId = ucCombinationPatterns.getSystemId();
        String globalId = ucCombinationPatterns.getUcGlobalPattern().getId();
        UcCombinationPattern combinationPattern = ucCombinationPatternRepository.findBySystemIdAndUcGlobalPattern_Id(systemId, globalId);
        combinationPattern.setUcPattern(ucCombinationPatterns.getUcPattern());
        combinationPattern = ucCombinationPatternRepository.save(combinationPattern);
        return combinationPattern.getId();
    }


    public void deleteGlobalPattern(String patternId) {
        ucGlobalPatternRepository.delete(patternId);
    }


    public List<UcCombinationPattern> findGlobalPatternOfUcPattern(String globalId, String systemId) {
        List<UcCombinationPattern> ucCombinationPatterns = ucCombinationPatternRepository.findByUcGlobalPattern_Id(globalId);
        for (UcCombinationPattern ucCombinationPattern : ucCombinationPatterns) {
            UcPattern ucPattern = ucCombinationPattern.getUcPattern();
            Set<UcPatternFactor> ucPatternFactorList = ucPattern.getUcPatternFactors();
            for (UcPatternFactor ucPatternFactor : ucPatternFactorList) {
                ucPatternFactor.getUcFactor().setUcSysFactors(null);
                ucPatternFactor.getUcFactor().setUcPatternFactors(ucPatternFactorList);
                ucPatternFactor.setUcPattern(ucPattern);
            }
            Set<UcPatternAction> ucPatternActionList = ucPattern.getUcPatternActions();
            for (UcPatternAction ucPatternAction : ucPatternActionList) {
                Set<UcDevicePattern> ucDevicePatternSet = ucPatternAction.getUcDevicePatterns();
                for (UcDevicePattern ucDevicePattern : ucDevicePatternSet) {
                    Set<UcRunParam> ucRunParams = ucDevicePattern.getUcRunParams();
                }
            }
        }
        return ucCombinationPatterns;
    }


    public String saveUcPattern(UcPattern ucPattern) {

        String id = ucPattern.getId();
        if (id == null || "".equals(id)) {
            ucPattern = ucPatternRepository.save(ucPattern);
            Set<UcPatternFactor> set = ucPattern.getUcPatternFactors();
            for (UcPatternFactor ucPatternFactor : set) {
                ucPatternFactor.setUcPattern(ucPattern);
                ucPatternFactorRepository.save(ucPatternFactor);
            }
        } else {
//            ucPattern = ucPatternRepository.save(ucPattern);

            Set<UcPatternFactor> set = ucPattern.getUcPatternFactors();
            if (set != null && set.size() > 0) {
                UcPattern old = ucPatternRepository.findOne(id);

                //删除旧有的因素
                Set<UcPatternFactor> ucPatternFactorSet = old.getUcPatternFactors();
                if (ucPatternFactorSet != null) {
                    for (UcPatternFactor ucPatternFactor : ucPatternFactorSet) {
                        ucPatternFactorRepository.delete(ucPatternFactor.getId());
                    }
                }

                //保存新的影响因素
                for (UcPatternFactor ucPatternFactor : set) {
                    ucPatternFactor.setUcPattern(ucPattern);
                    ucPatternFactorRepository.save(ucPatternFactor);
                }
            }

            ucPattern = ucPatternRepository.save(ucPattern);
        }
        return ucPattern.getId();
    }


    public void deleteUcPattern(String patternIds) {
        StringTokenizer stringTokenizer = new StringTokenizer(patternIds, ",");
        while (stringTokenizer.hasMoreTokens()) {
            String patternId = stringTokenizer.nextToken();
            ucPatternRepository.delete(patternId);
        }
    }


    public void changeUcPatternRecord(String patternId, String systemId, String calendarId) {

        List<UcPatternRecord> ucPatternRecordList = ucPatternRecordRepository.findBySystemIdAndUcCalendar_Id(systemId, calendarId);
        logger.debug("--systemId-1--" + ucPatternRecordList.size());
        for (UcPatternRecord ucPatternRecord : ucPatternRecordList) {
            if (systemId.equals(ucPatternRecord.getSystemId()) && !ucPatternRecord.getPatternType().equals(PatternConst.PATTERN_TYPE_SPECIAL)) {//特例不删除
                //相同的子系统，删除命令
                ucPatternRecordRepository.delete(ucPatternRecord);
            }
        }
        logger.debug("--changeUcPatternRecord-1--" + systemId);
        UcPattern ucPattern = ucPatternRepository.findOne(patternId);
        savePatternToRecord(ucPattern, null, calendarId);
        logger.debug("--changeUcPatternRecord----end--" + systemId);
    }


    public UcGlobalPattern findUcGlobalPatternById(String id) {
        return ucGlobalPatternRepository.findOne(id);
    }


    public List<UcGlobalPattern> findUcGlobalPatternByAll(String orderType) {
        List<UcGlobalPattern> list = ucGlobalPatternRepository.findByOrderType(orderType);
        for (UcGlobalPattern ucGlobalPattern : list) {
            List<UcCombinationPattern> set = ucCombinationPatternRepository.findByUcGlobalPattern_Id(ucGlobalPattern.getId());
            ucGlobalPattern.setUcCombinationPatterns(new HashSet<UcCombinationPattern>(set));
        }
        return list;
    }


    public UcPatternAction findUcPatternActionById(String id) {
        UcPatternAction ucPatternAction = ucPatternActionRepository.findOne(id);
        ucPatternAction.getUcDevicePatterns();
        ucPatternAction.getUcPattern();
        return ucPatternAction;
    }


    public List<UcCombinationPattern> findUcCombinationPatternByGlobalId(String globalId) {
        return ucCombinationPatternRepository.findByUcGlobalPattern_Id(globalId);
    }


    public String saveUcCombinationPattern(UcCombinationPattern ucCombinationPattern) {
        ucCombinationPattern = ucCombinationPatternRepository.save(ucCombinationPattern);
        return ucCombinationPattern.getId();
    }


    public List<UcPattern> findUcPatternByOrderType(String orderType) {
        List<UcPattern> ucPatterns = ucPatternRepository.findByOrderType(orderType);
        for (UcPattern ucPattern : ucPatterns) {

            ucPattern.setUcCombinationPatterns(null);
            Set<UcPatternFactor> ucPatternFactorList = ucPattern.getUcPatternFactors();
            for (UcPatternFactor ucPatternFactor : ucPatternFactorList) {
                ucPatternFactor.getUcFactor().setUcSysFactors(null);
                ucPatternFactor.getUcFactor().setUcPatternFactors(ucPatternFactorList);
                ucPatternFactor.setUcPattern(null);
            }
            Set<UcPatternAction> ucPatternActionList = ucPattern.getUcPatternActions();
            for (UcPatternAction ucPatternAction : ucPatternActionList) {
                Set<UcDevicePattern> ucDevicePatternSet = ucPatternAction.getUcDevicePatterns();
                ucPatternAction.setUcPattern(null);
                for (UcDevicePattern ucDevicePattern : ucDevicePatternSet) {
                    ucDevicePattern.setUcPatternAction(null);
                    Set<UcRunParam> ucRunParams = ucDevicePattern.getUcRunParams();
                    for (UcRunParam ucRunParam : ucRunParams) {
                        ucRunParam.setUcDevicePattern(null);
                    }
                }
            }
        }
        return ucPatterns;
    }


    public UcPatternAction saveUcPatternAction(UcPatternAction ucPatternAction) {
        UcPatternAction patternAction = ucPatternActionRepository.save(ucPatternAction);
        patternAction.getUcPattern().getSystemId(); //controller输出日志使用

        //删除现有的设备列表
        List<UcDevicePattern> ucDevicePatterns = ucDevicePatternRepository.findByUcPatternAction_Id(patternAction.getId());
        for (UcDevicePattern devicePattern : ucDevicePatterns) {
            ucDevicePatternRepository.delete(devicePattern);
        }

        Set<UcDevicePattern> ucPatternActionSet = ucPatternAction.getUcDevicePatterns();
        for (UcDevicePattern ucDevicePattern : ucPatternActionSet) {
            ucDevicePattern.setUcPatternAction(patternAction);
            ucDevicePattern.setStartTime(patternAction.getBaseTime() + patternAction.getOffsetTime());
            //设置策略的结束时间

            //查询已经存在的参数并删除
            List<UcRunParam> ucRunParamList = ucRunParamRepository.findByUcDevicePattern_Id(ucDevicePattern.getId());
            for (UcRunParam ucRunParam : ucRunParamList) {
                ucRunParamRepository.delete(ucRunParam.getId());
            }

            //将新的参数保存进去
            Set<UcRunParam> ucRunParams = ucDevicePattern.getUcRunParams();
            logger.debug("-------ucDevicePattern-----:" + ucDevicePattern.getId() + ":");
            ucDevicePattern = ucDevicePatternRepository.save(ucDevicePattern);
            for (UcRunParam ucRunParam : ucRunParams) {
                logger.debug("-------getParamName-----" + ucRunParam.getParamName());
                ucRunParam.setUcDevicePattern(ucDevicePattern);
                ucRunParamRepository.save(ucRunParam);
            }
        }
        return patternAction;
    }


    public UcDeviceStrategy findDeviceStrategy(String id) {
        UcDeviceStrategy ucDeviceStrategy = ucDeviceStrategyRepository.findOne(id);
        Set<UcStrategyParam> ucStrategyParamSet = ucDeviceStrategy.getUcStrategyParams();
        for (UcStrategyParam ucStrategyParam : ucStrategyParamSet) {
            ucStrategyParam.setUcDeviceStrategy(null);
        }
        return ucDeviceStrategy;
    }


    public String findTagId(String deviceId, String paramCode) {
        //通过设备ID查询可以设置的tagId
        List<AssetMeterSet> assetMeter = assetService.findAssetMeterByAssetnum(deviceId, "");

        //循环所有测试器具，得到需要的参数 tagId
        for (AssetMeterSet assetMeterSet : assetMeter) {
            //得到测试器具
            AssetMeter asset = assetMeterSet.getAssetMeter();
            List<MeasurementSet> measurementSets = measurementService.findMeasurementsByAssetnumAndMetername(deviceId, asset.getMetername(), "", "");
            for (MeasurementSet measurementSet : measurementSets) {
                AssetAttribute assetAttribute = measurementSet.getAssetAttribute();
                String paramName = assetAttribute.getAssetattrid();

                if (paramCode.equals(paramName)) {  //控制参数编码相同
                    String tagId = measurementSet.getMeasurement().getValuetag();
                    return tagId;
                }
            }
        }
        return null;
    }


    public void deletePatternAction(String id) {
        ucPatternActionRepository.delete(id);
    }


    public UcWeather findWeatherByCalendarAndNew(String calendarId, String isNew) {
        UcWeather ucWeather = ucWeatherRepository.findByUcCalendar_IdAndIsNew(calendarId, isNew);
        return ucWeather;
    }


    public List<UcPatternRecord> findSubSystemPattern(String dateType, String systemId, String startDate, String endDate) {
        List<UcPatternRecord> ucPatternRecordList = new ArrayList<UcPatternRecord>();
        try {
            //判断是否最底层子系统 比如：暖通空调就有子系统。
            boolean isHaveSystem = false;
            List<UcDeviceSystem> ucDeviceSystemSubList = null;
            if (systemId != null && !"".equals(systemId)) {
                UcDeviceSystem ucDeviceSystem1 = findDeviceSystemByCode(systemId);
                String id = ucDeviceSystem1.getId();
                ucDeviceSystemSubList = findDeviceSystemByParentId(id);
                if (ucDeviceSystemSubList != null && ucDeviceSystemSubList.size() > 0) {
                    //有子系统
                    isHaveSystem = true;
                }
                logger.debug("------systemId-----" + systemId + "------isHaveSystem-----" + isHaveSystem + "---------" + endDate + "------" + startDate);

                if (startDate.equals(endDate)) {
                    if (isHaveSystem) {
                        //有子系统，预测每个子系统运行的模式。
                        for (UcDeviceSystem ucDeviceSystem : ucDeviceSystemSubList) {
                            //先查询此子系统是否有预设模式
                            logger.debug("------systemId-----" + systemId + "------isHaveSystem-----" + isHaveSystem + "---------" + endDate + "------" + startDate);
                            List<UcPatternRecord> tempList = findUcPatternRecordBySystemIdAndDate(ucDeviceSystem.getSystemCode(), startDate);
//                            logger.debug("------tempList--1---" + tempList);
                            if (tempList != null && tempList.size() > 0) {
                                ucPatternRecordList.addAll(tempList);
                            } else {
//                                logger.debug("------tempList---2--" + tempList);

                                UcPatternRecord ucPatternRecord = predictDatePattern(ucDeviceSystem.getSystemCode(), startDate);
                                if (ucPatternRecord != null) {
                                    ucPatternRecordList.add(ucPatternRecord);
                                }
                            }
                        }
                    } else {
                        logger.debug("------systemId-----" + systemId + "----else--isHaveSystem-----" + isHaveSystem + "---------" + endDate + "------" + startDate);
                        ucPatternRecordList = findUcPatternRecordBySystemIdAndDate(systemId, startDate);
                        //未查询到运行模式，检查是否需要进行预测 未来5天内需要进行预测
                        logger.debug("------ucPatternRecordList-----" + ucPatternRecordList);
                        if (ucPatternRecordList == null || ucPatternRecordList.size() == 0) {
                            ucPatternRecordList = new ArrayList<UcPatternRecord>();
                            UcPatternRecord ucPatternRecord = predictDatePattern(systemId, startDate);
                            if (ucPatternRecord != null) {
                                ucPatternRecordList.add(ucPatternRecord);
                            }
                        }
                    }
                } else {

                    if (isHaveSystem) {
                        //有子系统，预测每个子系统运行的模式。
                        logger.debug("------systemId-----" + systemId + "------isHaveSystem-----" + isHaveSystem + "---------" + endDate + "------" + startDate);
                        String endString = DateTime.parse(endDate, DateTimeFormat.forPattern("yyyyMMdd")).plusDays(1).toString("yyyyMMdd");

                        for (UcDeviceSystem ucDeviceSystem : ucDeviceSystemSubList) {
                            logger.debug("------ucDeviceSystem---2--" + ucDeviceSystem.getSystemName());
                            logger.debug("------ucDeviceSystem---2--" + ucDeviceSystem.getId());
                            //先查询此子系统是否有预设模式
                            DateTime starTemp = DateTime.parse(startDate, DateTimeFormat.forPattern("yyyyMMdd"));
                            String temDate = starTemp.toString("yyyyMMdd");
                            do {

                                List<UcPatternRecord> tempList = findUcPatternRecordBySystemIdAndDate(ucDeviceSystem.getSystemCode(), temDate);

                                if (tempList != null && tempList.size() > 0) {
                                    ucPatternRecordList.addAll(tempList);
                                } else {
                                    UcPatternRecord ucPatternRecord = predictDatePattern(ucDeviceSystem.getSystemCode(), temDate);
                                    if (ucPatternRecord != null) {
                                        ucPatternRecordList.add(ucPatternRecord);
                                    }
                                }

                                starTemp = starTemp.plusDays(1);
                                temDate = starTemp.toString("yyyyMMdd");

                            } while (!temDate.equals(endString));

                        }
                    } else {
                        logger.debug("------systemId-----" + systemId + "------isHaveSystem-----" + isHaveSystem + "---------" + endDate + "------" + startDate);
                        //查询没有大于等于，所以上限加1 ，下限减1.
                        DateTime star = DateTime.parse(startDate, DateTimeFormat.forPattern("yyyyMMdd"));
                        DateTime end = DateTime.parse(endDate, DateTimeFormat.forPattern("yyyyMMdd"));

                        star = star.plusDays(-1);
                        end = end.plusDays(1);

                        String st = star.toString("yyyyMMdd");
                        String et = end.toString("yyyyMMdd");
                        logger.debug("---Y--else-isHaveSystem---else--" + systemId + "=======" + st + "=====" + et);
                        ucPatternRecordList = findUcPatternRecordBySystemIdAndDate(dateType, systemId, st, et);

                        logger.debug("---Y--else-isHaveSystem---else--" + ucPatternRecordList.size());
                        //未查询到运行模式，检查是否需要进行预测 未来5天内需要进行预测
                        if (ucPatternRecordList == null || ucPatternRecordList.size() == 0) {
                            ucPatternRecordList = new ArrayList<UcPatternRecord>();
                            //预计每一天要运行的模式
                            String endString = DateTime.parse(endDate, DateTimeFormat.forPattern("yyyyMMdd")).plusDays(1).toString("yyyyMMdd");
                            DateTime starTemp = DateTime.parse(startDate, DateTimeFormat.forPattern("yyyyMMdd"));
                            String temDate = starTemp.toString("yyyyMMdd");

                            do {
                                //没有模式,进行预设
                                UcPatternRecord ucPatternRecord = predictDatePattern(systemId, temDate);
                                if (ucPatternRecord != null) {
                                    ucPatternRecordList.add(ucPatternRecord);
                                }

                                starTemp = starTemp.plusDays(1);
                                temDate = starTemp.toString("yyyyMMdd");

                            } while (!temDate.equals(endString));

                        } else {
                            //检查是否每一天都有运行模式
                            String endString = DateTime.parse(endDate, DateTimeFormat.forPattern("yyyyMMdd")).plusDays(1).toString("yyyyMMdd");
                            DateTime starTemp = DateTime.parse(startDate, DateTimeFormat.forPattern("yyyyMMdd"));
                            String temDate = starTemp.toString("yyyyMMdd");

                            do {
                                boolean isHave = false;
                                for (UcPatternRecord ucPatternRecord : ucPatternRecordList) {
                                    //  logger.debug("---Y--else-isHaveSystem---else--" + ucPatternRecord.getPatternName());
                                    String d = ucPatternRecord.getUcCalendar().getId();
                                    //先查询此子系统是否有预设模式
                                    if (d.equals(temDate)) {
                                        isHave = true;
                                    }
                                }

                                if (!isHave) {
                                    //没有模式,进行预设
                                    UcPatternRecord ucPatternRecord = predictDatePattern(systemId, temDate);
                                    if (ucPatternRecord != null) {
                                        ///    logger.debug("---predictDatePattern--" + ucPatternRecord.getId());
                                        //  logger.debug("---predictDatePattern--" + ucPatternRecord.getUcDeviceRecords().size());
                                        ucPatternRecordList.add(ucPatternRecord);
                                    }
                                }

                                starTemp = starTemp.plusDays(1);
                                temDate = starTemp.toString("yyyyMMdd");

                            } while (!temDate.equals(endString));
                        }
                    }
                }
            } else {
                logger.debug("----no--systemId-----" + systemId + "------isHaveSystem-----" + isHaveSystem + "---------" + endDate + "------" + startDate);
                List<UcDeviceSystem> ucDeviceSystemList = findRunPatternSystem();
                for (UcDeviceSystem ucDeviceSystem : ucDeviceSystemList) {
                    UcPatternRecord ucPatternRecord = predictDatePattern(ucDeviceSystem.getSystemCode(), startDate);
                    if (ucPatternRecord != null) {
                        ucPatternRecordList.add(ucPatternRecord);
                    }
                }
            }
        } catch (Exception e) {
            logger.error("-----findSubSystemPattern--------", e);
        }

        return ucPatternRecordList;
    }

    /**
     * 预测某天运行的模式
     *
     * @param systemId  子系统ID
     * @param queryDate 查询的日期  格式 yyyyMMdd
     * @return
     */
    private UcPatternRecord predictDatePattern(String systemId, String queryDate) {
        //同时是在未来的5天内
        String nowDate = DateTime.now().toString("yyyyMMdd");
        DateTime fDate = DateTime.parse(nowDate, DateTimeFormat.forPattern("yyyyMMdd")).plusDays(5);
        DateTime queryDateTime = DateTime.parse(queryDate, DateTimeFormat.forPattern("yyyyMMdd"));
        logger.debug("predictDatePattern  ucPattern=========" + systemId + "----" + fDate.toString("yyyyMMdd"));
        logger.debug("predictDatePattern  ucPattern=========" + systemId + "----" + queryDateTime.toString("yyyyMMdd"));
        long q = queryDateTime.getMillis();
        long t = fDate.getMillis();
        //未来5天内
        if (q <= t) {
            if (systemId != null && !"".equals(systemId)) {
                //有子系统ID，则只生成此子系统的预计模式
                logger.debug("predictDatePattern ========" + systemId + "----" + queryDateTime);
                UcPattern ucPattern = findDayRunPattern(systemId, queryDateTime.toDate());
                logger.debug("predictDatePattern =========" + ucPattern);
                UcPatternRecord ucPatternRecord = findUcPatternToUcPatternRecord(ucPattern);
                UcCalendar ucCalendar = findUcCalendarById(queryDate);
                UcWeather ucWeather = findWeatherByCalendarAndNew(queryDate, PatternConst.WEATHER_IS_NEW_YES);
                ucWeather.setUcCalendar(null);
                logger.debug("predictDatePattern =ucWeather=======" + ucWeather.getId());

                Set<UcWeather> ucWeathers = new HashSet<UcWeather>();
                ucWeathers.add(ucWeather);

                //ucCalendar.getUcWeathers().clear();
                ucCalendar.setUcWeathers(ucWeathers);
                logger.debug("predictDatePattern  ucCalendar.getId=========" + ucCalendar.getId());
                ucPatternRecord.setUcCalendar(ucCalendar);
                ucPatternRecord.setUcFactorRecords(null);
                return ucPatternRecord;
            }
        }
        return null;
    }


    public UcPatternRecord calculateDeviceRunTime(UcPatternRecord ucPatternRecord, UcWeather ucWeather) {
        Set<UcDeviceRecord> ucDeviceRecords = ucPatternRecord.getUcDeviceRecords();
        logger.debug("--------ucDeviceRecords-------" + ucDeviceRecords.size());
        List<UcDeviceRecord> sortList = new ArrayList<UcDeviceRecord>(ucDeviceRecords);
        //排序
        orderDeviceRecord(sortList);

        Integer strand = 0;
        int s = 0;
        int t = 0;
        Set<UcDeviceRecord> ucRecords = new HashSet<UcDeviceRecord>();

        UcDeviceRecord ucDeviceRecordNew = new UcDeviceRecord();

        int curTime = DateTime.now().getMinuteOfDay();
        boolean curBoolean = true;
        //取不同的时间
        for (UcDeviceRecord ucDeviceRecord : sortList) {
//            logger.debug("=========="+ucDeviceRecord.getBaseTime());
//            logger.debug("=========="+ucDeviceRecord.getDeviceId());
            Integer start = ucDeviceRecord.getBaseTime();
            if (start.equals(PatternConst.SUNRISE)) {
                //查询今天的日升、日落时间
                logger.debug("[======start====]" + ucWeather.getSunrise());
                start = DateTime.parse(ucWeather.getSunrise(), DateTimeFormat.forPattern("HH:mm")).getMinuteOfDay();
            }
            if (start.equals(PatternConst.SUNSET)) {
                logger.debug("[======start====]" + ucWeather.getSunset() + "====" + ucWeather.getId());
                start = DateTime.parse(ucWeather.getSunset(), DateTimeFormat.forPattern("HH:mm")).getMinuteOfDay();
            }
            Integer offset = ucDeviceRecord.getOffsetTime();
            if (offset == null) {
//                offset = 0;
            } else {
                start = start + offset;
            }

            //将当前的模式运行的参数取出
            if (curBoolean && curTime <= start) {
                curBoolean = false;
                ucDeviceRecordNew.setUcParamRecords(ucDeviceRecord.getUcParamRecords());
            }

            //计算开始和结束时间
            if (!strand.equals(start)) {
                //与上一个时间点不同
                strand = start;
//                logger.debug("----getStrategyId---:" + ucDeviceRecord.getStrategyId());
                if (ucDeviceRecord.getStrategyId() == null || "".equals(ucDeviceRecord.getStrategyId())) {
                    //过滤掉策略模式
                    boolean open = false;
                    boolean close = false;
                    for (UcDeviceRecord record : ucDeviceRecords) {
                        Integer startTmp = record.getBaseTime();
                        if (startTmp.equals(PatternConst.SUNRISE)) {
                            //查询今天的日升、日落时间
                            startTmp = DateTime.parse(ucWeather.getSunrise(), DateTimeFormat.forPattern("HH:mm")).getMinuteOfDay();
                        }
                        if (startTmp.equals(PatternConst.SUNSET)) {
                            startTmp = DateTime.parse(ucWeather.getSunset(), DateTimeFormat.forPattern("HH:mm")).getMinuteOfDay();
                        }

                        if (start.equals(startTmp)) {
                            //同一时间是否有设备开启，如果有则是此类设备的开始状态。
                            Set<UcParamRecord> ucRunParams = record.getUcParamRecords();

//                            logger.debug("----ucRunParams---"+ucRunParams.size());

                            for (UcParamRecord ucParamRecord : ucRunParams) {
                                String name = ucParamRecord.getParamName();
                                String switchVa = ucParamRecord.getParamValue();
//                                logger.debug("----switchVa---" + switchVa);
                                if (name.equals(PatternConst.PATTERN_COMMAND_SWITCH) && switchVa.equals(PatternConst.PATTERN_COMMAND_SWITCH_OPEN)) {
                                    //有一台设备是开的。//todo 开关状态为开
                                    open = true;
                                } else {
                                    close = true;
                                }
                            }
                            if (t == 0 && open) {
                                t = 1;
                                //发现第一台开的设备 退出循环
                                break;
                            } else {
                                //该时间节点，所有设备都关闭的状态。
                            }
                            if (t == 1 && close) {
                                //找到设备先开后的最近一次设备的关闭
                                break;
                            }
                        }
                    }
                    logger.debug("----open---" + open);
                    if (open) {
                        //至少有一台是开的,则算是模式的运行的开始。
                        if (s == 0) {
                            //保证只取第一次的值。其它中间值不要，过滤掉
                            s++;
                            ucDeviceRecordNew.setBaseTime(strand);

                            Date startT = DateTime.parse(timeString(strand), DateTimeFormat.forPattern("HH:mm")).toDate();
                            ucDeviceRecordNew.setStartTime(startT);
                        }
                    }
                    if (close) {
                        Date startT = DateTime.parse(timeString(strand), DateTimeFormat.forPattern("HH:mm")).toDate();
                        ucDeviceRecordNew.setEndTime(startT);
                    }

                }
            }
        }
        ucRecords.add(ucDeviceRecordNew);
        ucPatternRecord.getUcDeviceRecords().clear();
        logger.debug("------ucRecords-------" + ucRecords.size());
        ucPatternRecord.setUcDeviceRecords(ucRecords);
        return ucPatternRecord;
    }

    /**
     * 对列表进行按时间排序
     *
     * @param baseCommands 设备列表
     * @return 按时间先后排序的设备命令列表
     */
    private List<UcDeviceRecord> orderDeviceRecord(List<UcDeviceRecord> baseCommands) {
        Collections.sort(baseCommands, new Comparator<UcDeviceRecord>() {
            public int compare(UcDeviceRecord arg0, UcDeviceRecord arg1) {
                return arg0.getBaseTime().compareTo(arg1.getBaseTime());
            }
        });
        return baseCommands;
    }


    public List<UcDeviceStrategy> findStrategyBySystemId(String systemId) {

        List<UcDeviceStrategy> ucDeviceStrategies = ucDeviceStrategyRepository.findBySystemId(systemId);
        for (UcDeviceStrategy ucDeviceStrategy : ucDeviceStrategies) {
            Set<UcStrategyParam> ucStrategyParamSet = ucDeviceStrategy.getUcStrategyParams();
            for (UcStrategyParam ucStrategyParam : ucStrategyParamSet) {
                ucStrategyParam.setUcDeviceStrategy(null);
            }
        }
        return ucDeviceStrategies;
    }


    public void deleteStrategy(String id) {
        ucDeviceStrategyRepository.delete(id);
    }


    public String saveStrategy(UcDeviceStrategy ucDeviceStrategy) {
        UcDeviceStrategy ucDeviceStrategy1 = ucDeviceStrategyRepository.save(ucDeviceStrategy);

        Set<UcStrategyParam> ucStrategyParamSet = ucDeviceStrategy1.getUcStrategyParams();
        for (UcStrategyParam ucStrategyParam : ucStrategyParamSet) {
            ucStrategyParamRepository.delete(ucStrategyParam);
        }

        Set<UcStrategyParam> ucStrategyParams = ucDeviceStrategy.getUcStrategyParams();
        for (UcStrategyParam ucStrategyParam : ucStrategyParams) {
            ucStrategyParam.setUcDeviceStrategy(ucDeviceStrategy1);
            ucStrategyParamRepository.saveAndFlush(ucStrategyParam);
        }

        return ucDeviceStrategy1.getId();
    }


    public List<UcSysFactor> findSystemFactor(String systemId) {
        List<UcSysFactor> sysFactors = ucSysFactorRepository.findBySystemId(systemId);
        for (UcSysFactor ucSysFactor : sysFactors) {
            UcFactor ucFactor = ucSysFactor.getUcFactor();
        }
        return sysFactors;
    }


    public List<UcFactor> findSubFactors(String id) {
        return ucFactorRepository.findByParentId(id);
    }


    public UcDeviceSystem findUcDeviceSystemById(String id) {
        return ucDeviceSystemRepository.findOne(id);
    }


    public List<Tree> findLocHierarchyTree(String siteId) {
        return locationsService.getLocHierarchyTree(siteId);
    }


    public List<Asset> findDeviceByLocation(String location, String systemType, String classType, String siteId) {
        logger.debug("--------location---" + location + "--systemType--" + systemType + "---classType--" + classType + "---siteId--" + siteId);
        if (classType == null || "".equals(classType) || systemType.equals(PatternConst.SYSTEM_CODE_LSPUB) || systemType.equals(PatternConst.SYSTEM_CODE_LSN)) {
            return assetService.findByLocationAndSpecclassAndSiteid(location, systemType, siteId);
        } else {
            String[] ct = classType.split(",");
            List<Asset> assets = new ArrayList<Asset>();
            for (String classtype : ct) {
                List<Asset> assets1 = assetService.findByLocationAndSpecclassAndClassstructureidAndSiteid(location, systemType, classtype, siteId);
                assets.addAll(assets1);
            }
            return assets;
        }
    }


    public List<UcDeviceGroup> findDeviceGroupBySystemType(String systemId) {
        return ucDeviceGroupRepository.findByUcDeviceSystem_Id(systemId);
    }


    public List<LocHierarchySet> findLocHierarchyList(String siteid) {
        return locationsService.findLocHierarchyList(siteid);
    }


    public List<DeviceCommand> changePattern(List<DeviceCommand> deviceCommands, String patternId, List<String> manualDevices) {
        //先将模式查出
        UcPattern ucPattern = ucPatternRepository.findOne(patternId);
        String systemId = ucPattern.getSystemId();
        //删除今天的子系统的运行模式，保存新的模式
        logger.debug("--systemId---" + systemId);
        List<UcPatternRecord> ucPatternRecordList = ucPatternRecordRepository.findBySystemIdAndUcCalendar_Id(ucPattern.getSystemId(), dateFormat.format(new Date()));
        for (UcPatternRecord ucPatternRecord : ucPatternRecordList) {
            if (systemId.equals(ucPatternRecord.getSystemId()) && !ucPatternRecord.getPatternType().equals(PatternConst.PATTERN_TYPE_SPECIAL)) {//特例不删除
                //相同的子系统，删除命令
                ucPatternRecordRepository.delete(ucPatternRecord);
            }
        }

        savePatternToRecord(ucPattern, null, dateFormat.format(new Date()));

        //删除命令列表里的此子系统的命令

        List<DeviceCommand> special = new ArrayList<DeviceCommand>();
        List<DeviceCommand> del = new ArrayList<DeviceCommand>();
        for (DeviceCommand deviceCommand : deviceCommands) {
            if (systemId.equals(deviceCommand.getSystemId()) && !deviceCommand.getCommandType().equals(PatternConst.PATTERN_TYPE_SPECIAL)) {//特例不删除
                //相同的子系统，删除命令
                del.add(deviceCommand);
            }
            if (deviceCommand.getCommandType().equals(PatternConst.PATTERN_TYPE_SPECIAL)) {
                special.add(deviceCommand);
            }
        }

        deviceCommands.removeAll(del);
        del.clear();

        //生成新的模式的命令列表
        List<DeviceCommand> deviceCommands1 = findPatternToDeviceCommand(PatternConst.COMMAND_TYPE_AUTO, patternId, new Date(), null);
        logger.debug("--1---send  command  up-------" + deviceCommands1.size());
        //需要执行的最近一条命令列表
        orderCommandAsc(deviceCommands1);
        Map<String, DeviceCommand> commands = new HashMap<String, DeviceCommand>();
        long nowDate = System.currentTimeMillis();
        long up = 0;
        for (DeviceCommand deviceCommand : deviceCommands1) {
//            logger.debug("--2---send  command  up-------" + deviceCommand.getDeviceId());
            //在已经找出的命令里不存在此设备的命令。避免过多已经执行的命令被加入
            if (!commands.containsKey(deviceCommand.getDeviceId())) {
                long a = deviceCommand.getSendTime().getTime();

                logger.debug("---3--send  command  up---" + deviceCommand.getSendTime() + "---" + DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
                if (a < nowDate) {//小于当前时间的第一条命令

                    if (up == 0 || a == up) {//只取小于当前时间的第一条命令

                        if (manualDevices.contains(deviceCommand.getDeviceId())) {
                            //包含在手动列表里，所以不加入执行的命令
                            //将此设备命令设置成手动状态
                            deviceCommand.setExecuteStatus(PatternConst.DEVICE_EXECUTE_COVER);
                        } else {
                            //不包含在手动列表里,且不是特殊命令
                            if (special != null && special.size() > 0) {
                                for (DeviceCommand device : special) {
                                    String id = device.getDeviceId();
                                    if (id.equals(deviceCommand.getDeviceId())) {
                                        //是特殊命令，不去执行命令，且不加入命令列表
                                        del.add(deviceCommand);
                                    } else {
                                        commands.put(deviceCommand.getDeviceId(), deviceCommand);
                                    }
                                }
                            } else {
                                commands.put(deviceCommand.getDeviceId(), deviceCommand);
                            }
                        }
                        up = a;
                    }
                }
            }
        }
        deviceCommands1.removeAll(del);
        //添加到命令列表
        deviceCommands.addAll(deviceCommands1);

        //命令排序
        orderCommand(deviceCommands);

        return new ArrayList<DeviceCommand>(commands.values());
    }


    public Collection<DeviceCommand> changePatternByTemplate(List<DeviceCommand> deviceCommands, String patternId, List<String> manualDevices, HashMap<String, DeviceCommand> commandHashMap) {
        //先将模式查出
        UcPattern ucPattern = ucPatternRepository.findOne(patternId);
        String systemId = ucPattern.getSystemId();
        //删除今天的子系统的运行模式，保存新的模式
        logger.debug("--systemId---" + systemId);

        //  logger.debug("--changePatternByTemplate-2--" + systemId);
        //删除命令列表里的此子系统的命令

        List<DeviceCommand> special = new ArrayList<DeviceCommand>();
        List<DeviceCommand> del = new ArrayList<DeviceCommand>();
        for (DeviceCommand deviceCommand : deviceCommands) {
            if (systemId.equals(deviceCommand.getSystemId()) && !deviceCommand.getCommandType().equals(PatternConst.PATTERN_TYPE_SPECIAL)) {//特例不删除
                //相同的子系统，删除命令
                del.add(deviceCommand);
            }
            if (deviceCommand.getCommandType().equals(PatternConst.PATTERN_TYPE_SPECIAL)) {
                special.add(deviceCommand);
            }
        }

        deviceCommands.removeAll(del);
        del.clear();

        //生成新的模式的命令列表
        List<DeviceCommand> deviceCommands1 = findPatternToDeviceCommand(PatternConst.COMMAND_TYPE_AUTO, ucPattern.getId(), new Date(), commandHashMap);


        logger.debug("--1---send  command  up-------" + deviceCommands1.size());
        //需要执行的最近一条命令列表
        orderCommandAsc(deviceCommands1);
        //    logger.debug("--2---send  command  up-------" + deviceCommands1.size());
        Map<String, DeviceCommand> commands = new HashMap<String, DeviceCommand>();
        long nowDate = System.currentTimeMillis();
        for (DeviceCommand deviceCommand : deviceCommands1) {
            logger.info("--changePatternByTemplate-" + deviceCommand.getDeviceId() + "--tagId---" + deviceCommand.getParams().get(0).getTagId());
            //在已经找出的命令里不存在此设备的命令。避免过多已经执行的命令被加入
            if (!commands.containsKey(deviceCommand.getDeviceId())) {
                long a = deviceCommand.getSendTime().getTime();
                if (a < nowDate) {//小于当前时间的第一条命令
                    if (manualDevices.contains(deviceCommand.getDeviceId())) {
                        //包含在手动列表里，所以不加入执行的命令
                        //将此设备命令设置成手动状态
                        deviceCommand.setExecuteStatus(PatternConst.DEVICE_EXECUTE_COVER);
                    } else {
                        //不包含在手动列表里,且不是特殊命令
                        if (special != null && special.size() > 0) {
                            for (DeviceCommand device : special) {
                                String id = device.getDeviceId();
                                if (id.equals(deviceCommand.getDeviceId())) {
                                    //是特殊命令，不去执行命令，且不加入命令列表
                                    del.add(deviceCommand);
                                } else {
                                    commands.put(deviceCommand.getDeviceId(), deviceCommand);
                                }
                            }
                        } else {
                            commands.put(deviceCommand.getDeviceId(), deviceCommand);
                        }
                    }
                }
            }
        }
        deviceCommands1.removeAll(del);
        //添加到命令列表
        deviceCommands.addAll(deviceCommands1);

        //命令排序
        orderCommand(deviceCommands);
        // logger.debug("--4---send  command  up-------" + deviceCommands1.size());
        Collection<DeviceCommand> commandCollection = commands.values();
        //  logger.debug("--5---send  command  up-------" + deviceCommands1.size());
        return commandCollection;
    }

    /**
     * 将设备生成命令
     *
     * @param deviceId
     * @param commandType
     * @param patternType
     * @param startTime
     * @param strategyId
     * @param isStrategy
     * @param sDate
     * @param eDate
     * @param ucRunParams
     * @param commandHashMap
     * @return
     */
    private DeviceCommand transferDeviceToCommandChang(String deviceId, String commandType, String patternType, String startTime, String strategyId, boolean isStrategy, String sDate, String eDate, Set<UcRunParam> ucRunParams, HashMap<String, DeviceCommand> commandHashMap) {
        try {
            DeviceCommand deviceCommand = new DeviceCommand();//设备命令
            deviceCommand.setCommandType(commandType);
            deviceCommand.setExecuteStatus(PatternConst.DEVICE_EXECUTE_NO);
            deviceCommand.setDeviceId(deviceId);

            if (patternType.equals(PatternConst.PATTERN_TYPE_GLOBAL)) {
                //全局的模式  命令是延时处理，而非定时
                if (startTime.contains("-")) {
                    //设置了时间点，非只有偏移时间
                    //todo 不支持时序模式
                } else {
                    deviceCommand.setSpaceTime(Integer.parseInt(startTime));
                }
            }
            if (patternType.equals(PatternConst.PATTERN_TYPE_ORDINARY)) {
                Date sendT = DateTime.parse(startTime, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).toDate();
                deviceCommand.setSendTime(sendT);
            }

            //得到设备的模版
            DeviceCommand temp = commandHashMap.get(deviceId);
            //是否有策略命令
            deviceCommand.setStrategyId(strategyId);
            setCommandStrategy(deviceCommand, isStrategy, sDate, eDate);
            //参数不同
            List<DeviceCommand> params = temp.getParams();
            List<DeviceCommand> newParams = new ArrayList<DeviceCommand>();
            for (DeviceCommand param : params) {
                String paramName = param.getParamName();
                DeviceCommand par = new DeviceCommand();
                par.setParamName(paramName);
                par.setTagId(param.getTagId());
                if (ucRunParams != null) {
                    //找到控制参数的tagId
                    for (UcRunParam ucRunParam : ucRunParams) {
                        String paramN = ucRunParam.getParamName();
//                        logger.debug("----------paramN-----" + paramN);
                        if (paramN.equals(paramName)) {  //控制参数相同
                            par.setParamValue(ucRunParam.getParamValue());
                            if (paramName.equals(PatternConst.PATTERN_COMMAND_SWITCH)) {
                                deviceCommand.setStatus(ucRunParam.getParamValue());//开关、控制参数加入命令里
                            }
                            break;
                        }
                    }
                }
                newParams.add(par);
            }
            deviceCommand.setParams(newParams);
            return deviceCommand;
        } catch (Exception e) {
            logger.error("----transferDeviceToCommand-------:" + startTime + ":---:" + patternType + ":---:" + deviceId, e);
        }
        return null;
    }


    /**
     * 对列表进行按时间排序进行  降序排列
     */
    private List<DeviceCommand> orderCommandAsc(List<DeviceCommand> baseCommands) {
        Collections.sort(baseCommands, new Comparator<DeviceCommand>() {
            public int compare(DeviceCommand arg0, DeviceCommand arg1) {
                return arg1.getSendTime().compareTo(arg0.getSendTime());
            }
        });
        return baseCommands;
    }


    public UcDeviceSystem findDeviceSystemByCode(String systemCode) {
        UcDeviceSystem ucDeviceSystemList = ucDeviceSystemRepository.findBySystemCode(systemCode);

        return ucDeviceSystemList;
    }


    public List<UcLogicGroup> findLogicGroup(String systemId) {

        return ucLogicGroupRepository.findBySystemId(systemId);
    }


    public List<UcStrategyItem> findStrategyItemByParentId(String parentId) {
        return ucStrategyItemRepository.findByParentId(parentId);
    }


    public UcPatternRecord findUcPatternRecordById(String id) {
        UcPatternRecord ucPatternRecord = ucPatternRecordRepository.findOne(id);
        Set<UcDeviceRecord> ucDeviceRecordSet = ucPatternRecord.getUcDeviceRecords();
        for (UcDeviceRecord ucDeviceRecord : ucDeviceRecordSet) {

            ucDeviceRecord.setUcPatternRecord(null);

            Set<UcParamRecord> ucParamRecords = ucDeviceRecord.getUcParamRecords();
            for (UcParamRecord ucParamRecord : ucParamRecords) {
                ucParamRecord.setUcDeviceRecord(null);
            }
        }
        return ucPatternRecordRepository.findOne(id);
    }


    public Asset findDeviceIdByTagId(String tagid) {
        Asset asset = assetService.findByTagId(tagid);
        return asset;
    }


    public List<UcDeviceSystem> findDeviceSystemAll() {
        return ucDeviceSystemRepository.findAll();
    }


    public void clearHoliday(String startDate, String endDate) {
        List<UcHoliday> holidayList = ucHolidayRepository.findByUcCalendar_IdBetween(startDate, endDate);
        for (UcHoliday ucHoliday : holidayList) {
            ucHoliday.setIsHoliday(PatternConst.HOLIDAY_NO);
            ucHoliday.setHolidayName("");
        }
    }


    public void saveAlarmReport(String alarmReporMsg) {
        String mobile = config.getProps().getProperty("sendMobile");
        AlarmReport alarmReport = new AlarmReport();
        alarmReport.setExecFlag(0);
        alarmReport.setReporter("系统");
        alarmReport.setReportMessage(alarmReporMsg);
        alarmReport.setReportMobile(mobile);
        alarmReport.setAlarmMode(1);
        alarmReport.setReportTime(new Date());
        alarmReportRepository.save(alarmReport);
    }


    public void saveExcelOfPattern(String ucPatternId, Collection<UcPatternAction> ucPatternActions) {
        UcPattern ucPattern = ucPatternRepository.findOne(ucPatternId);
        for (UcPatternAction ucPatternAction : ucPatternActions) {
            ucPatternAction.setUcPattern(ucPattern);
            Set<UcDevicePattern> ucDevicePatternSet = ucPatternAction.getUcDevicePatterns();
            ucPatternActionRepository.save(ucPatternAction);

            for (UcDevicePattern ucDevicePattern : ucDevicePatternSet) {
                ucDevicePattern.setUcPatternAction(ucPatternAction);
                Set<UcRunParam> ucRunParamSet = ucDevicePattern.getUcRunParams();
                ucDevicePatternRepository.save(ucDevicePattern);
                for (UcRunParam ucRunParam : ucRunParamSet) {
                    ucRunParam.setUcDevicePattern(ucDevicePattern);
                    ucRunParamRepository.save(ucRunParam);

                }
            }
        }
    }


    public List<ClassStructure> findAllClassStructure(String orgid) {
        return classService.findAllClassStructure(orgid);
    }


    public void saveBusinessTime(String date, Date startTime, Date endTime) {
        UcCalendar ucCalendar = ucCalendarRepository.findOne(date);
        if (startTime != null) {
            ucCalendar.setOpenTime(startTime);
        }
        if (endTime != null) {
            ucCalendar.setCloseTime(endTime);
        }
        ucCalendarRepository.save(ucCalendar);
    }


    public void saveDeviceApplyPattern(String deviceId, String patternId, String startDate, String endDate) throws Exception {
        UcPattern ucPattern = ucPatternRepository.findOne(patternId);
        DateTime st = DateTime.parse(startDate, DateTimeFormat.forPattern("yyyy-MM-dd"));
        String ed = DateTime.parse(endDate, DateTimeFormat.forPattern("yyyy-MM-dd")).toString("yyyyMMdd");
        boolean isAdd = true;
        do {
            String da = st.toString("yyyyMMdd");

            saveUcPatternToRecord(ucPattern, da, deviceId);
            logger.debug("------savePredictPattern----------" + startDate + "----" + endDate + "----" + da);
            if (da.equals(ed)) {
                isAdd = false;
            } else {
                st = st.plusDays(1);
            }
        } while (isAdd);

    }

    /**
     * 将模式转为模式记录，专用于特例时，将所选设备放置于模式应用中
     *
     * @param ucPattern 模式
     * @param nowDate   日期
     * @param deviceIds 设备ID的集合
     * @throws Exception
     */
    private void saveUcPatternToRecord(UcPattern ucPattern, String nowDate, String deviceIds) throws Exception {

        String[] ids = deviceIds.split(",");
        UcPatternRecord ucPatternRecordSub = new UcPatternRecord();
        ucPatternRecordSub.setCreateDate(ucPattern.getCreateDate());
        ucPatternRecordSub.setCreateUser(ucPattern.getCreateUser());
        ucPatternRecordSub.setPatternId(ucPattern.getId());
        ucPatternRecordSub.setPatternName(ucPattern.getName());
        ucPatternRecordSub.setPatternType(PatternConst.PATTERN_TYPE_SPECIAL); //特例
        ucPatternRecordSub.setSystemId(ucPattern.getSystemId());
        ucPatternRecordSub.setVersion(ucPattern.getVersion());
        UcCalendar ucCalendar = ucCalendarRepository.findOne(nowDate);
        ucPatternRecordSub.setUcCalendar(ucCalendar);
        UcWeather ucWeather = ucWeatherRepository.findByUcCalendar_IdAndIsNew(ucCalendar.getId(), PatternConst.WEATHER_IS_NEW_YES);
        ucPatternRecordRepository.save(ucPatternRecordSub);

        //保存影响因素
        Set<UcPatternFactor> ucFactors = ucPattern.getUcPatternFactors();
        for (UcPatternFactor ucPatternFactor : ucFactors) {
            UcFactor ucFactor = ucPatternFactor.getUcFactor();

            UcFactorRecord ucFactorRecord = new UcFactorRecord();
            ucFactorRecord.setName(ucFactor.getName());
            ucFactorRecord.setDescription(ucFactor.getDescription());
            ucFactorRecord.setEndDate(ucFactor.getEndDate());
            ucFactorRecord.setStartDate(ucFactor.getStartDate());
            ucFactorRecord.setFactorType(ucFactor.getFactorType());
            ucFactorRecord.setMaxValue(ucFactor.getMaxValue());
            ucFactorRecord.setMinValue(ucFactor.getMinValue());
            ucFactorRecord.setUcPatternRecord(ucPatternRecordSub);

            ucFactorRecordRepository.save(ucFactorRecord);
        }

        //保存设备
        Set<UcPatternAction> ucPatternActions = ucPattern.getUcPatternActions();

        for (UcPatternAction ucPatternAction : ucPatternActions) {

            Set<UcDevicePattern> ucDevicePatterns = ucPatternAction.getUcDevicePatterns();
            //所有参数都一样，所以只取第一个的运行参数
            UcDevicePattern ucDevicePattern = ucDevicePatterns.iterator().next();
            //替换原有的设备的定义
            for (String id : ids) {

                UcDeviceRecord ucDeviceRecord = new UcDeviceRecord();
                ucDeviceRecord.setUcPatternRecord(ucPatternRecordSub);
                ucDeviceRecord.setBaseTime(ucPatternAction.getBaseTime());
                if (ucPatternAction.getBaseTime().equals(PatternConst.SUNRISE)) {
                    int sunRise = DateTime.parse(ucWeather.getSunrise(), DateTimeFormat.forPattern("HH:mm")).getMinuteOfDay();
                    ucDeviceRecord.setBaseTime(sunRise);
                }
                if (ucPatternAction.getBaseTime().equals(PatternConst.SUNSET)) {
                    int sunSet = DateTime.parse(ucWeather.getSunset(), DateTimeFormat.forPattern("HH:mm")).getMinuteOfDay();
                    ucDeviceRecord.setBaseTime(sunSet);
                }
                ucDeviceRecord.setDescription(ucPatternAction.getDescription());
                ucDeviceRecord.setOffsetTime(ucPatternAction.getOffsetTime());
                ucDeviceRecord.setRelativeTime(ucPatternAction.getRelativeTime());
                ucDeviceRecord.setActionType(ucPatternAction.getActionType());

                ucDeviceRecord.setDeviceId(id);
                ucDeviceRecord.setSelectType(PatternConst.PATTERN_SELECT_DEVICE);

                if (ucDevicePattern.getStrategyId() != null && !"".equals(ucDevicePattern.getStrategyId())) {
                    ucDeviceRecord.setStrategyId(ucDevicePattern.getStrategyId());
                    Date s = timeFormat.parse(timeString(ucDevicePattern.getStartTime()));
                    Date e = timeFormat.parse(timeString(ucDevicePattern.getEndTime()));
                    ucDeviceRecord.setStartTime(s);
                    ucDeviceRecord.setEndTime(e);
                }
                ucDeviceRecordRepository.save(ucDeviceRecord);
                //保存此设备或设备组的运行参数
                Set<UcRunParam> ucRunParams = ucDevicePattern.getUcRunParams();

                for (UcRunParam ucRunParam : ucRunParams) {
                    UcParamRecord ucParamRecord = new UcParamRecord();
                    ucParamRecord.setParamName(ucRunParam.getParamName());
                    ucParamRecord.setParamValue(ucRunParam.getParamValue());
                    ucParamRecord.setUcDeviceRecord(ucDeviceRecord);
                    ucParamRecordRepository.save(ucParamRecord);
                }
            }
        }
    }


    public List<Asset> findBySpecclassAndClassstructureidAndSiteid(String specclass, String classstructureid, String siteid) {
        return assetService.findBySpecclassAndClassstructureidAndSiteid(specclass, classstructureid, siteid);
    }


    public List<UcPatternRecord> findUcPatternRecordBySystemIdAndDateLazy(String systemId, String nowDate) {
        logger.debug("-findUcPatternRecordBySystemIdAndDateLazy---------" + systemId + "-----" + nowDate);
        return ucPatternRecordRepository.findBySystemIdAndUcCalendar_Id(systemId, nowDate);
    }


    public void setCSButtonStyle(String systemCode) {
        if (systemCode != null && (systemCode.equals(PatternConst.SYSTEM_CODE_LSN) || systemCode.equals(PatternConst.SYSTEM_CODE_LSPUB))) {
            List<UcPatternRecord> ucPatternRecordList = findUcPatternRecordBySystemIdAndDate(systemCode, DateTime.now().toString("yyyyMMdd"));
            if (ucPatternRecordList != null && ucPatternRecordList.size() > 0) {

                UcPatternRecord patternRecord = null;
                //可能有特例，所以过滤掉特例 。其它类型只会有一个
                for (UcPatternRecord ucPatternRecord : ucPatternRecordList) {
                    if (ucPatternRecord.getPatternType().equals(PatternConst.PATTERN_TYPE_SYSTEM) || ucPatternRecord.getPatternType().equals(PatternConst.PATTERN_TYPE_PREDICT) || ucPatternRecord.getPatternType().equals(PatternConst.PATTERN_TYPE_CUSTOM)) {
                        patternRecord = ucPatternRecord;
                        break;
                    }
                }
                if (patternRecord == null) {
                    //没有预设和系统选择的模式，选择特例。
                    patternRecord = ucPatternRecordList.get(0);
                }
                String patternId = patternRecord.getPatternId();
                UcPattern ucPattern = findUcPatternById(patternId);
                Set<UcPatternAction> ucPatternActions = ucPattern.getUcPatternActions();

                UcWeather ucWeather = findWeatherByCalendarAndNew(DateTime.now().toString("yyyyMMdd"), PatternConst.WEATHER_IS_NEW_YES);

                List<UcPatternAction> ucPatternActionList = new ArrayList<UcPatternAction>(ucPatternActions);

                int sunRise = DateTime.parse(ucWeather.getSunrise(), DateTimeFormat.forPattern("HH:mm")).getMinuteOfDay();
                int sunSet = DateTime.parse(ucWeather.getSunset(), DateTimeFormat.forPattern("HH:mm")).getMinuteOfDay();
//                logger.debug("--------setCSButtonStyle---3----------"+patternId);
                orderCommand(ucPatternActionList, sunRise, sunSet);
                //   logger.debug("--------setCSButtonStyle---4----------"+patternId+"---"+ucPattern.getName()+"----"+ucPatternActionList.size());
                int nowTime = DateTime.now().getMinuteOfDay();
                for (UcPatternAction ucPatternAction : ucPatternActionList) {
                    int base = ucPatternAction.getBaseTime();
                    logger.debug("-------for---------" + base + "----" + ucPatternAction.getId() + "------" + ucPatternAction.getOffsetTime());
                    // 考虑基础时间是日升、日落的情况
                    if (base < 0) {
                        String sunTime = ucWeather.getSunrise();
                        if (base == PatternConst.SUNSET) {
                            sunTime = ucWeather.getSunset();
                        }
                        int baseTime = DateTime.parse(sunTime, DateTimeFormat.forPattern("HH:mm")).getMinuteOfDay();
                        logger.debug("--------setCSButtonStyle---for-1------" + nowTime + "----" + baseTime);
                        if (nowTime >= baseTime) {
                            //当前正在执行的模式阶段
                            String tagName = ucPattern.getSystemId() + "_" + ucPattern.getSystemSecond() + ucPatternAction.getActionType();
                            String url = config.getProps().getProperty("sendCommandUrl");
                            String tag = config.getProps().getProperty(tagName);
                            if (tag != null) {
                                tag = tag.trim();
                            }
                            String tagN = config.getProps().getProperty(ucPattern.getSystemId());
                            SendCommandToSystem.sendCommandTagName(url, tagN, tag);
                            logger.debug("--------setCSButtonStyle---5---------" + patternId + "----" + tagName + "---" + tag + "----" + tagN);
                            break;
                        }
                    } else {
                        int time = ucPatternAction.getBaseTime() + ucPatternAction.getOffsetTime();
                        logger.debug("--------setCSButtonStyle---for-2------" + nowTime + "----" + time);
                        if (nowTime >= time) {
                            //当前正在执行的模式阶段
                            String tagName = ucPattern.getSystemId() + "_" + ucPattern.getSystemSecond() + ucPatternAction.getActionType();
                            String url = config.getProps().getProperty("sendCommandUrl");
                            String tag = config.getProps().getProperty(tagName);
                            if (tag != null) {
                                tag = tag.trim();
                            }
                            String tagN = config.getProps().getProperty(ucPattern.getSystemId());
                            SendCommandToSystem.sendCommandTagName(url, tagN, tag);
                            logger.debug("--------setCSButtonStyle---6---------" + patternId + "----" + tagName + "---" + tag + "----" + tagN);
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * 对列表进行按时间排序
     *
     * @param baseCommands 命令列表
     * @return
     */
    private List<UcPatternAction> orderCommand(List<UcPatternAction> baseCommands, final int sunRise, final int sunSet) {
        Collections.sort(baseCommands, new Comparator<UcPatternAction>() {
            public int compare(UcPatternAction arg0, UcPatternAction arg1) {

                int base = arg0.getBaseTime();
                int arg1BaseTime = arg1.getBaseTime();
                if (base == PatternConst.SUNRISE) {
                    base = sunRise;
                }
                if (base == PatternConst.SUNSET) {
                    base = sunSet;
                }
                if (arg1BaseTime == PatternConst.SUNRISE) {
                    arg1BaseTime = sunRise;
                }
                if (arg1BaseTime == PatternConst.SUNSET) {
                    arg1BaseTime = sunSet;
                }

                long a = base + arg0.getOffsetTime();
                long b = arg1BaseTime + arg1.getOffsetTime();
                if (a > b) {
                    return -1;
                } else {
                    if (a == b) {
                        return 0;
                    } else {
                        return 1;
                    }
                }
            }
        });
        return baseCommands;
    }


    public Classification findClassificationByClassificationid(String classificationid) {
        return classService.findClassificationByClassificationid(classificationid);
    }


    public boolean findCurrentStage(UcPatternAction ucPatternAction) {

        UcWeather ucWeather = findWeatherByCalendarAndNew(DateTime.now().toString("yyyyMMdd"), PatternConst.WEATHER_IS_NEW_YES);

        int nowTime = DateTime.now().getMinuteOfDay();

        int base = ucPatternAction.getBaseTime();
        logger.debug("-------for---------" + base + "----" + ucPatternAction.getId() + "------" + ucPatternAction.getOffsetTime());
        // 考虑基础时间是日升、日落的情况
        if (base < 0) {
            String sunTime = ucWeather.getSunrise();
            if (base == PatternConst.SUNSET) {
                sunTime = ucWeather.getSunset();
            }
            int baseTime = DateTime.parse(sunTime, DateTimeFormat.forPattern("HH:mm")).getMinuteOfDay();
            logger.debug("--------findCurrentStage---------" + nowTime + "----" + baseTime);
            if (nowTime >= baseTime) {
                //当前正在执行的模式阶段
                return true;
            }
        } else {
            int time = ucPatternAction.getBaseTime() + ucPatternAction.getOffsetTime();
            logger.debug("--------findCurrentStage--------" + nowTime + "----" + time);
            if (nowTime >= time) {
                //当前正在执行的模式阶段
                return true;
            }
        }

        return false;
    }


    public List<Asset> findByLikeLocationAndSpecclassAndClassstructureidAndSiteid(String location, String specclass, String classstructureid, String siteid) {
        return assetService.findByLikeLocationAndSpecclassAndClassstructureidAndSiteid(location, specclass, classstructureid, siteid);
    }


    public List<ClassStructure> findClassStructuresByParent(String parent) {
        return classService.findClassStructuresByParent(parent);
    }


    public List<DeviceCommand> findDeviceCommandByPatternAndActionType(String patternId, String actionType, HashMap<String, DeviceCommand> templateCommand) {
        UcPattern ucPattern = ucPatternRepository.findOne(patternId);
        List<DeviceCommand> deviceCommands = new ArrayList<DeviceCommand>();
        Set<UcPatternAction> ucPatternActionLis = ucPattern.getUcPatternActions();
        for (UcPatternAction ucPatternAction : ucPatternActionLis) {
            if (ucPatternAction.getActionType().equals(actionType)) { //相同的类型
                Set<UcDevicePattern> ucDevicePatterns = ucPatternAction.getUcDevicePatterns();
                for (UcDevicePattern ucDevicePattern : ucDevicePatterns) {
                    //处理设备
                    List<DeviceCommand> deviceCommands1 = findDeviceTransferCommand(PatternConst.COMMAND_TYPE_AUTO, DateTime.now().toString("yyyy-MM-dd HH:mm:ss"), PatternConst.PATTERN_TYPE_ORDINARY, new Date(), ucDevicePattern, null, null, templateCommand);
                    deviceCommands.addAll(deviceCommands1);
                }
            }
        }
        logger.info("----findDeviceCommandByPatternAndActionType----size-----" + deviceCommands.size());
        return deviceCommands;
    }


    public void threadSendCommand(Collection<DeviceCommand> deviceCommands) throws Exception {
        SchedulerFactory schedulerfactory = new StdSchedulerFactory();
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("deviceCommand", deviceCommands);
        JobDetail job = JobBuilder.newJob(SendCommandTask.class).withIdentity("command", "sendCommand").setJobData(jobDataMap).build();
        SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder.newTrigger().withIdentity("triggerName", "sendCommand").startNow().withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(30)).forJob(job).build();

        Scheduler scheduler = schedulerfactory.getScheduler();

        scheduler.scheduleJob(job, trigger);
        scheduler.start();
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<MeasurementAttribute> findMeasurementByAssetId(String assetId, String siteId, String orgId) {
        List<MeasurementAttribute> measurementAttributeList = measurementService.findMeasurementsByAssetnum(assetId, siteId, orgId);
        return measurementAttributeList;
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<Asset> findByLocationAndSpecclassAndSiteid(String location, String specclass, String siteid) {
        return assetService.findByLocationAndSpecclassAndSiteid(location, specclass, siteid);
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Page<Asset> findByLikeLocationAndSpecclassAndSiteidAndOrgid(String location, String specclass, String siteid, String orgid, Pageable pageable) {
        return assetService.findByLikeLocationAndSpecclassAndSiteidAndOrgid(location, specclass, siteid, orgid, pageable);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public String saveTransferRunLog(String user, List<UcEventLog> ucEventLogList) {

        String paramName = "";
        String lastId = "";
        for (UcEventLog ucEventLog : ucEventLogList) {

            String tagName = ucEventLog.getTagname();
//            logger.info(ucEventLog.getLogid()+"--------ucEventLog-------"+tagId);
            if (StringUtils.isNotEmpty(tagName)) {
                OpLogs opLogs = new OpLogs();
                DateTime dateTime = new DateTime(ucEventLog.getLogt());
                opLogs.setDated(dateTime.toString("yyyy-MM-dd HH:mm:ss"));
                opLogs.setLogger("CS");
                opLogs.setUserid(user);
                //查询设备
                Asset asset = assetService.findByTagId(tagName);
//                logger.info("--------asset-------"+asset);
                if (asset != null) {
                    //查询属性
                    List<MeasurementAttribute> measurementAttributeList = findMeasurementByAssetId(asset.getAssetnum() + "", "", "");
                    for (MeasurementAttribute measurementAttribute : measurementAttributeList) {
                        if (tagName.equals(measurementAttribute.getValuetag())) {
                            paramName = measurementAttribute.getDescrption();
                        }
                    }
                    //组合信息
                    String str = asset.getDescription() + "的参数：" + paramName + "从" + ucEventLog.getOldvalue() + "变为" + ucEventLog.getTagvalue();
                    logger.info("--------str-------" + str);
                    opLogs.setMessage(str);
                    opLogs.setSubsys(asset.getSpecclass());
                    opLogs.setCategory(PatternConst.LOG_TYPE_AUTO);
                    opLogRepository.save(opLogs);
                }
            }
            lastId = ucEventLog.getId() + "";
        }
        return lastId;
    }


    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<UcPatternRecord> findPatternRecordByStartDateAndEndDate(String startDate, String endDate) {

        return ucPatternRecordRepository.findByUcCalendar_IdBetweenOrderByUcCalendar_IdAsc(startDate,endDate);
    }
}
