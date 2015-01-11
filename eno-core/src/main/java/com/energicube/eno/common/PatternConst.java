package com.energicube.eno.common;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-8-26
 * Time: 上午9:47
 * To change this template use File | Settings | File Templates.
 */
public class PatternConst {

    //一天的命令列表，存放在ServletContext中
    public static final String CONTEXT_COMMAND_DAY_LIST = "CONTEXT_COMMAND_DAY_LIST";
    public static final String CONTEXT_COMMAND_DAY_SEND = "CONTEXT_COMMAND_DAY_SEND";
    public static final String CONTEXT_COMMAND_DAY_MANUAL_DEVICE = "CONTEXT_COMMAND_DAY_MANUAL_DEVICE"; //手动的命令列表//todo 如果是服务器重启将有问题
    public static final String CONTEXT_MANUAL_PATTERN_DEVICE = "CONTEXT_MANUAL_PATTERN_DEVICE"; //手动切换模式的设备列表
    public static final String CONTEXT_COMMAND_TEMPLATE = "CONTEXT_COMMAND_TEMPLATE";  //用于减少设备的tagId的查询
    public static final String CONTEXT_COMMAND_OPEN = "CONTEXT_COMMAND_OPEN";
    //命令的执行状态
    public static final String DEVICE_EXECUTE_YES = "Y";
    public static final String DEVICE_EXECUTE_NO = "N";
    public static final String DEVICE_EXECUTE_COVER = "C"; //手动后，将关于此设备的所有命令标记为 C
    public static final String DEVICE_EXECUTE_GLOBAL = "M"; //全局模式运行后，将关于此设备的所有不用执行的命令标记为 C

    //假日相关
    public static final String HOLIDAY_YES = "Y";
    public static final String HOLIDAY_NO = "N";
    public static final String HOLIDAY_TYP_CUSTOM = "C"; //自定义假日
    public static final String HOLIDAY_TYPE_GB = "G"; //国家假日
    public static final String HOLIDAY_TYPE_ORDINARY = "O"; //平日
    public static final String HOLIDAY_TYPE_WEEK = "W"; //周末假日

    //模式影响的因素类型
    public static final String PATTERN_FACTOR_SEASON = "S"; //季节
    public static final String PATTERN_FACTOR_WEATHER = "W"; //天气
    public static final String PATTERN_FACTOR_HOLIDAY = "H";  //节假日

    //选择的设备的方式
    public static final String PATTERN_SELECT_ALL = "A";   //所有设备
    public static final String PATTERN_SELECT_LOGIC = "L";  //选择的是逻辑组
    public static final String PATTERN_SELECT_DEVICE = "D";  //选择的是设备
    public static final String PATTERN_SELECT_SPACE = "S";  //选择的是某个区域
    public static final String PATTERN_SELECT_CLASS = "M";  //选择的是某个区域


    //模式的类型
    public static final String PATTERN_TYPE_SYSTEM = "S";  //系统类型
    public static final String PATTERN_TYPE_SPECIAL = "T"; //特例
    public static final String PATTERN_TYPE_CUSTOM = "C"; //自定义
    public static final String PATTERN_TYPE_PREDICT = "P"; //预设

    //模式的类型
    public static final String PATTERN_ORDER_TYPE_TIME = "T";  //时序类型
    public static final String PATTERN_ORDER_TYPE_NOTIME = "N";  //非时序类型

    //活动的类型
    public static final String PATTERN_ACTION_CLOSEING = "CI";  //闭店中
    public static final String PATTERN_ACTION_READY_OPEN = "R"; //准备开店
    public static final String PATTERN_ACTION_OPEN = "O";       //开店
    public static final String PATTERN_ACTION_OPENING = "B";  //营业中
    public static final String PATTERN_ACTION_READY_CLOSE = "CR"; //闭店前准备
    public static final String PATTERN_ACTION_CLOSE = "C";       //闭店

    //模式是否全局
    public static final String PATTERN_TYPE_GLOBAL = "G";  //模式是全局模式
    public static final String PATTERN_TYPE_ORDINARY = "O"; //模式是普通模式

    //命令的类型
    public static final String COMMAND_TYPE_GLOBAL = "G"; //全局
    public static final String COMMAND_TYPE_PREDICT = "P"; //预设
    public static final String COMMAND_TYPE_SPECIAL = "T";  //特例
    public static final String COMMAND_TYPE_MANUAL = "M";   //手动
    public static final String COMMAND_TYPE_AUTO = "A";    //普通自运行

    //子系统是否需要自运行模式
    public static final String SYSTEM_RUN_PATTERN_YES = "Y";
    public static final String SYSTEM_RUN_PATTERN_NO = "N";

    //COOLINGSOURCE冷源
    public static final String SYSTEM_CODE_HVAC = "HVAC"; //暖通
    public static final String SYSTEM_CODE_COOLSOURCE = "COOLINGSOURCE";
    public static final String SYSTEM_CODE_HEATSOURCE = "HEATSOURCE";  //热源
    public static final String SYSTEM_CODE_FRESHAVU = "FAVU";   //新风机组
    public static final String SYSTEM_CODE_MAHU = "MAHU"; //组合式空调
    public static final String SYSTEM_CODE_BUILTUPAHU = "BUAHU";//调装式空调
    public static final String SYSTEM_CODE_AVU = "AVU";//通风机组
    public static final String SYSTEM_CODE_SAV = "SAV";//送风机组
    public static final String SYSTEM_CODE_EAV = "EAV";//排风机组
    public static final String SYSTEM_CODE_LSPUB = "LSPUB";//公共照明
    public static final String SYSTEM_CODE_LSN = "LSN";//夜景照明


    //日升、日落
    public static int SUNRISE = -90;
    public static int SUNSET = -91;

    //是否策略的命令
    public static final String IS_STRATEGY_YES = "Y";
    public static final String IS_STRATEGY_NO = "N";

    //全局模式的父ID
    public static final String GLOBAL_PARENTID = "999999";

    //json返回的成功或失败的信息
    public static final String JSON_SUCCESS = "Y";
    public static final String JSON_FAILURE = "N";

    //天气类型
    public static final String WEATHER_TYPE_REAL = "R";   //真实天气
    public static final String WEATHER_TYPE_PREDICT = "P"; //预测的天气
    //是否最新的天气信息
    public static final String WEATHER_IS_NEW_YES = "Y";
    public static final String WEATHER_IS_NEW_NO = "N";

    //表示开关的参数及开状态的值
    public static final String PATTERN_COMMAND_SWITCH = "status_sp";
    public static final String PATTERN_COMMAND_SWITCH_OPEN = "1";
    public static final String PATTERN_COMMAND_SWITCH_CLOSE = "0";
    public static final String PATTERN_COMMAND_SWITCH_Y = "Y";
    public static final String PATTERN_COMMAND_SWITCH_N = "N";

    public static final String PATTERN_DEFAULT_YES = "Y"; //是否子系统的默认模式
    public static final String PATTERN_DEFAULT_NO = "N"; //是否子系统的默认模式

    public static final String WEATHER_TYPE_SUNNY = "晴天";
    public static final String PATTERN_WEATHER_TYPE_SUNNY = "1";  //天气晴
    public static final String PATTERN_WEATHER_TYPE_CLOUDY = "0"; //天气阴 (除晴外的其它天气)
    public static final String PATTERN_HOLIDAY_YES = "1"; //是假日
    public static final String PATTERN_HOLIDAY_NO = "0"; //不是假日

    //策略的比较的值    T--布尔型真 F--布尔值假  N--数字 P---项目
    public static final String STRATEGY_COMPARE_TYPE_NUM = "N";
    public static final String STRATEGY_COMPARE_TYPE_ITEM = "P";
    public static final String STRATEGY_COMPARE_TYPE_T = "T";
    public static final String STRATEGY_COMPARE_TYPE_F = "F";

    public static final String MANUAL_DEVICE_ID_KEY = "manual.devices";

    public static final String LOG_TYPE_PATTERN = "PATTERN";// 模式运行日志
    public static final String LOG_TYPE_WEATER = "WEATHER";// 天气运行日志
    public static final String LOG_TYPE_EVENT = "EVENT";// 活动运行日志
    public static final String LOG_TYPE_HOLIDAY = "HOLIDAY";// 假日运行日志
    public static final String LOG_TYPE_AUTO = "AUTO";// 设备运行日志

    //门禁刷卡记录分3种
    public static final String SASAC_EVENT_TYPE_GENERAL = "G"; //普通事件
    public static final String SASAC_EVENT_TYPE_VALID = "V";   //有效事件
    public static final String SASAC_EVENT_TYPE_INVALID = "I"; //无效事件

    //日历视图类型
    public static final String SYSTEM_VIEW_DAY = "D"; //日视图
    public static final String SYSTEM_VIEW_WEEK = "W"; //周视图
    public static final String SYSTEM_VIEW_MONTH = "M"; //月视图

    //特殊菜单类型
    public static final String MENU_TYPE_MODLE = "Module"; //顶级菜单
    public static final String MENU_TYPE_PATTERN = "AppFilter|SchemaCfg"; //模式的菜单
    public static final String MENU_TYPE_CHANG = "CHANG"; //可变的菜单

    public static final String LOG_ID = "MAX_LOG_ID";//日志截止的logint
    public static final String LOGIN_USER = "LOGIN_USER"; //当前登录的用户

    //客流厂家
    public static final String PFE_HN = "HN";//汇纳
    public static final String PFE_NEC = "NEC";//NEC厂商

    /**
     * 获取是否国家假日
     *
     * @param month 月
     * @param day   日
     * @return
     */
    public static String getGBHoliday(int month, int day) {
        if (month == 10 && day == 1) {
            return "国庆节";

        } else if (month == 5 && day == 1) {
            return "国际劳动节";

        } else if (month == 1 && day == 1) {
            return "元旦";
        }
        return null;
    }

    //获取天气的情况  F--未来9天  Y---昨天  C--当天
    public static final String GET_WEATHER_FORCAST = "F";
    public static final String GET_WEATHER_CURRENT = "C";
    public static final String GET_WEATHER_YESTERDAY = "Y";

    /**
     * 获取天气的对应情况
     *
     * @param weather 天气描述
     * @return
     */
    public static String getWeatherDesc(String weather) {
        if ("晴".equals(weather)) {
            return "1";
        } else {
            return "0";
        }
    }

    /**
     * 根据英文获取中文的风向
     *
     * @param windDir 英文风向
     * @return
     */
    public static String getWindDirCN(String windDir) {
        if ("E".equals(windDir)) {
            return "东";
        }
        if ("S".equals(windDir)) {
            return "南";
        }
        if ("W".equals(windDir)) {
            return "西";
        }
        if ("N".equals(windDir) || "North".equals(windDir)) {
            return "北";
        }
        if ("SE".equals(windDir)) {
            return "东南";
        }
        if ("NE".equals(windDir)) {
            return "东北";
        }
        if ("SW".equals(windDir)) {
            return "西南";
        }
        if ("NW".equals(windDir)) {
            return "西北";
        }
        if ("SSE".equals(windDir)) {
            return "东南偏南";
        }
        if ("ESE".equals(windDir)) {
            return "东南偏东";
        }
        if ("NNE".equals(windDir)) {
            return "东北偏北";
        }
        if ("ENE".equals(windDir)) {
            return "东北偏东";
        }
        if ("SSW".equals(windDir)) {
            return "西南偏南";
        }
        if ("WSW".equals(windDir)) {
            return "西南偏西";
        }
        if ("NNW".equals(windDir)) {
            return "西北偏北";
        }
        if ("WNW".equals(windDir)) {
            return "西北偏西";
        }
        if ("Variable".equals(windDir)) {
            return "无固定风向";
        }
        return "无风";
    }

    /**
     * 比较策略的条件是否成立
     *
     * @param va     设备测量到的值
     * @param symbol 比较的方式
     * @param newVa  参考值
     * @return
     */
    public static boolean compareStrategyVa(String va, String symbol, String newVa) {

        if (symbol.equals("=")) {
            if (va.equals(newVa)) {
                return true;
            }
        }
        if (symbol.equals(">=")) {
            double v = Double.parseDouble(va);
            double nv = Double.parseDouble(newVa);
            if (v >= nv) {
                return true;
            }
        }
        if (symbol.equals(">")) {
            double v = Double.parseDouble(va);
            double nv = Double.parseDouble(newVa);
            if (v > nv) {
                return true;
            }
        }
        if (symbol.equals("<")) {
            double v = Double.parseDouble(va);
            double nv = Double.parseDouble(newVa);
            if (v < nv) {
                return true;
            }
        }
        if (symbol.equals("<=")) {
            double v = Double.parseDouble(va);
            double nv = Double.parseDouble(newVa);
            if (v <= nv) {
                return true;
            }
        }
        if (symbol.equals("≠")) {
            if (!va.equals(newVa)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 得到巡更的结果
     *
     * @param result 结果类型
     * @return 中文
     */
    public static String getEpResult(String result) {
        //0为准时，1为早，2为漏巡，3为未计划，4为顺序错误，5为晚巡，6为未巡，
        if ("0".equals(result)) {
            return "准时";
        }
        if ("1".equals(result)) {
            return "早巡";
        }
        if ("2".equals(result)) {
            return "漏巡";
        }
        if ("3".equals(result)) {
            return "未计划";
        }
        if ("4".equals(result)) {
            return "顺序错误";
        }
        if ("5".equals(result)) {
            return "晚巡";
        }
        if ("6".equals(result)) {
            return "未巡";
        }
        return result;
    }

    /**
     * 路线类型
     *
     * @param type 类型
     * @return 中文
     */
    public static String getRoadType(String type) {
        //    =0全无序，1为首点有序，2为首尾无序  =3为有序，=4为平均间隔，等误差，=5单独间隔、误差
        if ("0".equals(type)) {
            return "全无序";
        }
        if ("1".equals(type)) {
            return "首点有序";
        }
        if ("2".equals(type)) {
            return "首尾无序";
        }
        if ("3".equals(type)) {
            return "有序";
        }
        if ("4".equals(type)) {
            return "平均间隔,等误差";
        }
        if ("5".equals(type)) {
            return "单独间隔,误差";
        }
        return type;
    }

    public static String getShiftType(String type) {
        //1为计划排班，2为自动生成排班，3为未计划排班
        if ("1".equals(type)) {
            return "计划排班";
        }
        if ("2".equals(type)) {
            return "自动生成排班";
        }
        if ("3".equals(type)) {
            return "未计划排班";
        }
        return type;
    }

    /**
     * C/S 客户端设置天气
     *
     * @param desc
     * @return
     */
    public static String getCSWeatherIcon(String desc) {
        if ("sunny".equals(desc) || "clear".equals(desc) || "mostlysunny".equals(desc)) {
            return "1";
        }
        if ("snow".equals(desc)) {
            return "3";
        }
        if ("rain".equals(desc)) {
            return "5";
        }
        if ("cloudy".equals(desc)) {
            return "7";
        }
        if ("mostlycloudy".equals(desc) || "partlysunny".equals(desc)) {
            return "4";
        }
        if ("nt_cloudy".equals(desc) || "nt_partlycloudy".equals(desc) || "nt_mostlysunny".equals(desc)) {
            return "6";
        }
        if ("sleet".equals(desc) || "chancesleet".equals(desc)) {
            return "8";
        }
        if ("chancetstorms".equals(desc) || "tstorms".equals(desc)) {
            return "9";
        }
        if ("chancesnow".equals(desc)) {
            return "10";
        }
        if ("chancerain".equals(desc)) {
            return "11";
        }
        if ("nt_sunny".equals(desc) || "nt_clear".equals(desc)) {
            return "12";
        }
        if ("flurries".equals(desc) || "chanceflurries".equals(desc)) {
            return "13";
        }
        if ("hazy".equals(desc) || "fog".equals(desc)) {
            return "14";
        }
        if ("partlycloudy".equals(desc)) {
            return "15";
        }
        return "";
    }

    /**
     * 得到控制灯的开关
     *
     * @param status 状态
     * @return 状态
     */
    public static String getSwitch(String status) {
        if (status.equals("1")) {
            return "Y";
        } else {
            return "N";
        }
    }
}
