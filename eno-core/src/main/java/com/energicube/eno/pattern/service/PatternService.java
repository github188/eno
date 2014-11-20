package com.energicube.eno.pattern.service;

import com.energicube.eno.asset.model.*;
import com.energicube.eno.calendar.model.UcCalendar;
import com.energicube.eno.calendar.model.UcWeather;
import com.energicube.eno.common.dto.DeviceCommand;
import com.energicube.eno.common.model.Tree;
import com.energicube.eno.common.weather.Weather;
import com.energicube.eno.monitor.model.UcEventLog;
import com.energicube.eno.pattern.model.*;
import org.joda.time.DateTime;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-7-11
 * Time: 下午1:01
 * To change this template use File | Settings | File Templates.
 */
public interface PatternService {


    /**
     * 查询要运行模式的子系统
     *
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<UcDeviceSystem> findRunPatternSystem();

    /**
     * 获取一天的时间任务表
     *
     * @param nowDate 日期
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = DataAccessException.class)
    public List<DeviceCommand> findDayTask(Date nowDate);

    /**
     * 根据模式ID生成设备的命令
     *
     * @param patternId      模式ID
     * @param commandType    命令类型
     * @param commandHashMap 缓存中的命令列表 ，用于减少设备的tagID的查询
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<DeviceCommand> findPatternToDeviceCommand(String commandType, String patternId, Date nowDate, HashMap<String, DeviceCommand> commandHashMap);

    /**
     * 各子系统某天要运行的模式
     *
     * @param systemId 子系统ID
     * @param nowDate  日期
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public UcPattern findDayRunPattern(String systemId, Date nowDate);

    /**
     * 根据合局模式的ID生成设备的命令
     *
     * @param nowDate        日期
     * @param patternId      全局模式ID
     * @param commandHashMap 存在的命令列表，用于减少设备的tagId的查询
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<DeviceCommand> findGlobalPatternToDeviceCommand(Date nowDate, String patternId, HashMap<String, DeviceCommand> commandHashMap);

    /**
     * 组合各命令列表  用于自定义或特例与自运行命令的组合
     *
     * @param baseCommands 基准命令列表
     * @param newCommands  新的命令列表
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<DeviceCommand> combinationDeviceCommand(List<DeviceCommand> baseCommands, List<DeviceCommand> newCommands);

    /**
     * 用于全局模式运行后，未执行的命令的列表
     * 全局模式为非时序，不存在时序模式
     *
     * @param baseCommands   基准命令列表
     * @param startDate      开始的时间
     * @param endDate        结束的时间
     * @param globalCommands 全局命令列表
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<DeviceCommand> combinationGlobalCommand(List<DeviceCommand> baseCommands, Date startDate, Date endDate, List<DeviceCommand> globalCommands);

    /**
     * 将手动命令插入全日时间表
     *
     * @param baseCommands 基准命令列表
     * @param commandType  命令类型
     * @param deviceId     设备ID
     * @param status       状态
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<DeviceCommand> manualDeviceCommand(List<DeviceCommand> baseCommands, String commandType, String deviceId, String status);

    /**
     * 将使用的模式存入模式的使用记录   未处理全局时序模式
     *
     * @param ucPattern       普通模式
     * @param ucGlobalPattern 全局模式
     * @param nowDate         日期 格式 yyyyMMdd
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW, rollbackFor = DataAccessException.class)
    public void savePatternToRecord(UcPattern ucPattern, UcGlobalPattern ucGlobalPattern, String nowDate);

    /**
     * 保存或更新某天的日历信息
     *
     * @param dateTime 日期
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = DataAccessException.class)
    public void saveCalendar(DateTime dateTime);


    /**
     * 查询日历
     *
     * @param id 日历ID
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public UcCalendar findUcCalendarById(String id);

    /**
     * 查询日历信息
     *
     * @param startDate 开始时间   格式yyyyMMdd
     * @param endDate   结束时间     格式yyyyMMdd
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<UcCalendar> findUcCalendarByCalendar(String startDate, String endDate);

    /**
     * 查询某天的天气
     *
     * @param calendarId 日历ID
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<UcWeather> findUcWeatherByCalendarId(String calendarId);


    /**
     * 将模式转为模式记录
     *
     * @param ucPattern 模式
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public UcPatternRecord findUcPatternToUcPatternRecord(UcPattern ucPattern);

    /**
     * 保存某段时间的天气
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = DataAccessException.class)
    public void saveUcWeathers(String startDate, String endDate, UcWeather ucWeather);

    /**
     * 保存某段时间的假日
     *
     * @param startDate   开始时间
     * @param endDate     结束时间
     * @param holidayName 节日的名字
     * @param holidayType 节日的类型
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = DataAccessException.class)
    public void saveUcHolidays(String startDate, String endDate, String holidayName, String holidayType);


    /**
     * 查询子系统运行的模式
     *
     * @param systemId 子系统ID
     * @param nowDate  日期  格式yyyyMMdd
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<UcPatternRecord> findUcPatternRecordBySystemIdAndDate(String systemId, String nowDate);


    /**
     * 查询子系统的在某时间段内的运行模式
     *
     * @param systemId  子系统ID
     * @param startDate 开始时间   格式yyyyMMdd
     * @param endDate   结束时间   格式yyyyMMdd
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<UcPatternRecord> findUcPatternRecordBySystemIdAndDate(String dateType, String systemId, String startDate, String endDate);


    /**
     * 保存影响因素
     *
     * @param ucFactor 因素实体
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = DataAccessException.class)
    public void saveFactor(UcFactor ucFactor);

    /**
     * 查询某系统需要的影响因素
     *
     * @param systemId 子系统ID
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<UcSysFactor> findFactorBySystemId(String systemId);

    /**
     * 查询某因素
     *
     * @param parentId 父因素ID
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<UcFactor> findFactorByParentId(String parentId);

    /**
     * 查询所有因素
     *
     * @param pageable 分页
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Page<UcFactor> findFactorAll(Pageable pageable);


    /**
     * 保存模式
     *
     * @param ucPattern        模式实体
     * @param ucPatternFactors 模式的影响因素
     * @param ucPatternAction  模式的动作
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = DataAccessException.class)
    public void savePattern(UcPattern ucPattern, List<UcPatternFactor> ucPatternFactors, UcPatternAction ucPatternAction);

    /**
     * 删除模式
     *
     * @param id 模式ID
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = DataAccessException.class)
    public void delPattern(String id);

    /**
     * 通过子系统查询其可运行的模式
     *
     * @param systemId 子系统ID
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<UcPattern> findPatternBySystemId(String systemId);

    /**
     * 通过parentId查询所有子系统
     *
     * @param parentId 父Id
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<UcDeviceSystem> findDeviceSystemByParentId(String parentId);

    /**
     * 查询某子系统的可预设的模式
     *
     * @param systemId    子系统ID
     * @param patternType 模式类型
     * @param orderType   模式类型  时序或非时序
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<UcPattern> findUcPatternBySystem(String systemId, String patternType, String orderType);

    /**
     * 查询某系统是否有子系统。
     *
     * @param systemId 系统ID
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<UcDeviceSystem> findSubSystemBySystem(String systemId);

    /**
     * 保存预设模式
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param patternId 模式的ID
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = DataAccessException.class)
    public void savePredictPattern(String startDate, String endDate, String patternId);

    /**
     * 查询模式
     *
     * @param patternId 模式ID
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public UcPattern findUcPatternById(String patternId);


    /**
     * 更新天气信息，根据天气服务提供的信息
     *
     * @param weather 天气信息
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = DataAccessException.class)
    public void updateWeatherForTimeTask(Weather weather, String getType);


    /**
     * 保存全局模式
     *
     * @param ucGlobalPattern 全局模式实体
     * @return 实体的ID
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = DataAccessException.class)
    public String saveGlobalPattern(UcGlobalPattern ucGlobalPattern);

    /**
     * 保存全局模式关联的子模式
     *
     * @param ucCombinationPatterns 关联模式实体
     * @return 实体的ID
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = DataAccessException.class)
    public String saveGlobalPatternToPattern(UcCombinationPattern ucCombinationPatterns);


    /**
     * 删除全局模式
     *
     * @param patternId 模式ID
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = DataAccessException.class)
    public void deleteGlobalPattern(String patternId);

    /**
     * 查询全局模式下的子系统的模式
     *
     * @param globalId 全局模式ID
     * @param systemId 子系统ID
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<UcCombinationPattern> findGlobalPatternOfUcPattern(String globalId, String systemId);

    /**
     * 保存模式
     *
     * @param ucPattern // 模式
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = DataAccessException.class)
    public String saveUcPattern(UcPattern ucPattern);

    /**
     * 删除模式
     *
     * @param patternIds //多个模式ID 格式逗号分隔
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = DataAccessException.class)
    public void deleteUcPattern(String patternIds);

    /**
     * 删除模式应用记录
     *
     * @param patternId  模式ID
     * @param calendarId 日期
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = DataAccessException.class)
    public void changeUcPatternRecord(String patternId, String systemId, String calendarId);

    /**
     * 查询全局模式
     *
     * @param id 全局模式的ID
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public UcGlobalPattern findUcGlobalPatternById(String id);

    /**
     * 查询全局模式根据不同类型
     *
     * @param orderType 时序或非时序
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<UcGlobalPattern> findUcGlobalPatternByAll(String orderType);


    /**
     * 查询模式的活动时间表
     *
     * @param id 活动时间表Id
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public UcPatternAction findUcPatternActionById(String id);

    /**
     * 查询全局模式与子系统模式的关联
     *
     * @param globalId 全局模式ID
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<UcCombinationPattern> findUcCombinationPatternByGlobalId(String globalId);

    /**
     * 保存全局模式与子系统的模式的关联
     *
     * @param ucCombinationPattern 关联的实体
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = DataAccessException.class)
    public String saveUcCombinationPattern(UcCombinationPattern ucCombinationPattern);

    /**
     * 查询所有子系统的时序或非时序模式
     *
     * @param orderType 模式类型  时序或非时序
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<UcPattern> findUcPatternByOrderType(String orderType);

    /**
     * 保存模式的时间表
     *
     * @param ucPatternAction 实体
     * @return
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = DataAccessException.class)
    public UcPatternAction saveUcPatternAction(UcPatternAction ucPatternAction);

    /**
     * 查询现有的策略
     *
     * @param id 策略ID
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public UcDeviceStrategy findDeviceStrategy(String id);

    /**
     * 查询设备的某参数的tagId
     *
     * @param deviceId  设备ID
     * @param paramCode 参数编码
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public String findTagId(String deviceId, String paramCode);

    /**
     * 删除模式的时间动作
     *
     * @param id patternAction的id
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = DataAccessException.class)
    public void deletePatternAction(String id);


    /**
     * 查询天气
     *
     * @param calendarId 日期
     * @param isNew      是否最新
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public UcWeather findWeatherByCalendarAndNew(String calendarId, String isNew);

    /**
     * 查询或预测某子系统某日期的运行模式
     * 为日视图提供运行记录
     *
     * @param systemId  子系统的ID
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 模式的集合
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<UcPatternRecord> findSubSystemPattern(String dateType, String systemId, String startDate, String endDate);

    /**
     * 计算运行的某模式的设备的运行最大时长
     * 为日视图提供数据显示
     *
     * @param ucPatternRecord 运行的模式
     * @return 运行的模式
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public UcPatternRecord calculateDeviceRunTime(UcPatternRecord ucPatternRecord, UcWeather ucWeather);

    /**
     * 查询策略
     *
     * @param systemId 子系统的Id
     * @return 策略对象
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<UcDeviceStrategy> findStrategyBySystemId(String systemId);

    /**
     * 删除策略
     *
     * @param id 策略ID
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = DataAccessException.class)
    public void deleteStrategy(String id);

    /**
     * 保存策略
     *
     * @param ucDeviceStrategy 策略对象
     * @return 策略的ID
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = DataAccessException.class)
    public String saveStrategy(UcDeviceStrategy ucDeviceStrategy);

    /**
     * 查询某子系统的影响因素
     *
     * @param systemId 子系统的ID
     * @return 因素的集合
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<UcSysFactor> findSystemFactor(String systemId);

    /**
     * 查询某因素下的子因素
     *
     * @param id 因素的ID
     * @return 子因素的集合
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<UcFactor> findSubFactors(String id);

    /**
     * 查询模式子系统
     *
     * @param id 子系统Id
     * @return 子系统对象
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public UcDeviceSystem findUcDeviceSystemById(String id);

    /**
     * 查询位置树
     *
     * @param siteId 地点
     * @return 地点的位置树
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<Tree> findLocHierarchyTree(String siteId);

    /**
     * 查询设备通过位置、专业设备类型、设备的分类
     *
     * @param location   位置
     * @param systemType 专业分类(子系统)
     * @param classType  设备分类
     * @param siteId     地点
     *                   List<Asset> findByLocationAndSpecclassAndClassstructureidAndSiteid(String location,String specclass,String classstructureid,String siteid);
     *                   List<Asset> findByLocationAndSpecclassAndSiteid(String location,String specclass,String siteid);
     * @return 设备列表
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<Asset> findDeviceByLocation(String location, String systemType, String classType, String siteId);

    /**
     * 查询设备组
     *
     * @param systemId 子系统ID
     * @return 子系统的设备组
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<UcDeviceGroup> findDeviceGroupBySystemType(String systemId);

    /**
     * 查询位置树
     *
     * @param siteid 地点
     * @return 位置列表
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<LocHierarchySet> findLocHierarchyList(String siteid);

    /**
     * 切换模式某子系统
     *
     * @param deviceCommands 命令列表
     * @param patternId      模式ID
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = DataAccessException.class)
    public List<DeviceCommand> changePattern(List<DeviceCommand> deviceCommands, String patternId, List<String> manualDevices);

    /**
     * 切换模式某子系统
     *
     * @param deviceCommands 命令列表
     * @param patternId      模式ID
     */
//    @Transactional(readOnly = false,propagation = Propagation.REQUIRED,rollbackFor = DataAccessException.class)
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Collection<DeviceCommand> changePatternByTemplate(List<DeviceCommand> deviceCommands, String patternId, List<String> manualDevices, HashMap<String, DeviceCommand> commandHashMap);

    /**
     * 查询子系统通过系统编码
     *
     * @param systemCode 系统编码
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public UcDeviceSystem findDeviceSystemByCode(String systemCode);

    /**
     * 查询子秕的逻辑组
     *
     * @param systemId 子系统的ID
     * @return 所有逻辑组
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<UcLogicGroup> findLogicGroup(String systemId);

    /**
     * 通过过父ID 查询策略的项目
     *
     * @param parentId 父ID
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<UcStrategyItem> findStrategyItemByParentId(String parentId);


    /**
     * 查询运行模式的记录
     *
     * @param id 运行的模式的ID
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public UcPatternRecord findUcPatternRecordById(String id);

    /**
     * 通过tagId查询设备的Id
     *
     * @param tagid tagId
     * @return 设备
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Asset findDeviceIdByTagId(String tagid);


    /**
     * 查询所有子系统
     *
     * @return 所有子系统的列表
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<UcDeviceSystem> findDeviceSystemAll();

    /**
     * 清除假日信息
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = DataAccessException.class)
    public void clearHoliday(String startDate, String endDate);


    /**
     * 发送短信
     *
     * @param alarmReporMsg 报告的内容
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = DataAccessException.class)
    public void saveAlarmReport(String alarmReporMsg);

    /**
     * 保存EXCEL的模式时间表
     *
     * @param ucPatternActions 上传的时间列表
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = DataAccessException.class)
    public void saveExcelOfPattern(String ucPatternId, Collection<UcPatternAction> ucPatternActions);


    /**
     * 查找所有类别列表
     *
     * @param orgid 组枳机构ID,默认为空
     * @return 类别列表
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<ClassStructure> findAllClassStructure(String orgid);

    /**
     * 保存开店、闭店时间
     *
     * @param date      日期
     * @param startTime 开店时间
     * @param endTime   闭店时间
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = DataAccessException.class)
    public void saveBusinessTime(String date, Date startTime, Date endTime);

    /**
     * 保存设备应用例外模式
     *
     * @param deviceId  设备ID
     * @param patternId 模式ID
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 此设备的命令列表
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = DataAccessException.class)
    public void saveDeviceApplyPattern(String deviceId, String patternId, String startDate, String endDate) throws Exception;

    /**
     * 查询设备
     *
     * @param specclass        专业分类
     * @param classstructureid 设备级别分类
     * @param siteid           地点
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<Asset> findBySpecclassAndClassstructureidAndSiteid(String specclass, String classstructureid, String siteid);


    /**
     * 查询子系统运行的模式
     *
     * @param systemId 子系统ID
     * @param nowDate  日期  格式yyyyMMdd
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<UcPatternRecord> findUcPatternRecordBySystemIdAndDateLazy(String systemId, String nowDate);

    /**
     * 设置某子秕的CS界面的按钮的样式，界面上的模式的按钮。
     *
     * @param systemCode 子系统编码
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public void setCSButtonStyle(String systemCode);


    /**
     * 查找指定类别定义ID的对象
     *
     * @param classificationid 类别ID
     * @return 类别定义对象
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public Classification findClassificationByClassificationid(String classificationid);


    /**
     * 通过设备参数封装命令列表
     *
     * @param commandType     命令的类型
     * @param startTime       命令发送的时间
     * @param patternType     模式的类型
     * @param nowDate         命令的日期
     * @param ucDevicePattern 设备模式
     * @param ucDeviceRecord  设备记录
     * @param templateCommand 命令模版
     * @param commandHashMap  缓存中的命令列表 ，用于减少设备的tagID的查询
     * @return
     */
    public List<DeviceCommand> findDeviceTransferCommand(String commandType, String startTime, String patternType, Date nowDate, UcDevicePattern ucDevicePattern, UcDeviceRecord ucDeviceRecord, DeviceCommand templateCommand, HashMap<String, DeviceCommand> commandHashMap);


    /**
     * 判断是滞当前的执行阶段
     *
     * @param ucPatternAction 模式动作
     * @return 是、否
     */
    public boolean findCurrentStage(UcPatternAction ucPatternAction);

    /**
     * 获取指定位置、专业分类和资产类别的资产数据
     *
     * @param location         位置代码 模糊查询
     * @param specclass        专业分类
     * @param classstructureid 类别结构ID
     * @return 资产列表数据
     */
    public List<Asset> findByLikeLocationAndSpecclassAndClassstructureidAndSiteid(String location, String specclass, String classstructureid, String siteid);


    /**
     * 查找指定ID的所有子类别层次定义
     *
     * @param parent 父级ID
     * @return 子类别层次定义列表
     */
    public List<ClassStructure> findClassStructuresByParent(String parent);

    /**
     * 查询模式的某动作时的命令列表
     *
     * @param patternId       模式ID
     * @param actionType      动作类型
     * @param templateCommand 命令模版
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<DeviceCommand> findDeviceCommandByPatternAndActionType(String patternId, String actionType, HashMap<String, DeviceCommand> templateCommand);

    /**
     * 多线程发送命令，减少占用时间
     *
     * @param deviceCommands 命令列表
     * @throws Exception
     */
    public void threadSendCommand(Collection<DeviceCommand> deviceCommands) throws Exception;

    /**
     * 查询设备的所有参数
     * @param assetId 设备ID
     * @param siteId 地点
     * @param orgId 组织
     * @return
     */
    public List<MeasurementAttribute> findMeasurementByAssetId(String assetId,String siteId,String orgId);

    /**
     * 获取指定位置、专业分类
     *
     * @param location 位置代码
     *
     * @param specclass 专业分类
     *
     *
     * @return 资产列表数据
     * */
    public List<Asset> findByLocationAndSpecclassAndSiteid(String location,String specclass,String siteid);

    /**
     *  查找指定地址和机构的所有未移动资产信息
     *  @param location 楼层
     *  @param specclass 专业分类
     *  @param siteid 地点ID
     *  @param orgid 组织ID
     *  @param pageable {@link Pageable} objects
     *
     *  @return {@link Asset} Page
     * */
    public Page<Asset> findByLikeLocationAndSpecclassAndSiteidAndOrgid(String location,String specclass ,String siteid,String orgid,Pageable pageable);

    /**
     * 保存设备运行日志
     * @param user 操作的用户
     * @param ucEventLogList 事件日志集合
     * @return
     */

    public String  saveTransferRunLog(String user,List<UcEventLog> ucEventLogList);



    /**
     * 查询运行的模式
     * @param startDate 开始时间
     * @param endDate  结束时间
     */
    @Transactional(readOnly = true,propagation = Propagation.REQUIRED,rollbackFor = DataAccessException.class)
    public List<UcPatternRecord>  findPatternRecordByStartDateAndEndDate(String startDate,String endDate);
}
