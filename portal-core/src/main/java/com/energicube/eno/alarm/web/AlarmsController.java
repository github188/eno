package com.energicube.eno.alarm.web;

import com.energicube.eno.alarm.model.UcAlarmactive;
import com.energicube.eno.alarm.model.UcAlarmgroup;
import com.energicube.eno.alarm.model.UcAlarmlog;
import com.energicube.eno.alarm.model.UcAlarmsList;
import com.energicube.eno.alarm.repository.AlarmInfoRepository;
import com.energicube.eno.alarm.repository.UcAlarmactiveRepository;
import com.energicube.eno.alarm.repository.UcAlarmlogRepository;
import com.energicube.eno.alarm.service.UcAlarmgroupService;
import com.energicube.eno.alarm.service.UcAlarmlogService;
import com.energicube.eno.asset.model.Asset;
import com.energicube.eno.asset.repository.FailureListRepository;
import com.energicube.eno.asset.service.AssetService;
import com.energicube.eno.asset.service.FailureCodeService;
import com.energicube.eno.common.Config;
import com.energicube.eno.common.Const;
import com.energicube.eno.common.PatternExcel;
import com.energicube.eno.common.SendCommandToSystem;
import com.energicube.eno.common.jsonquery.jpa.util.DateTimeUtil;
import com.energicube.eno.monitor.model.OkcDMAlphaNum;
import com.energicube.eno.monitor.repository.OkcDMAlphaNumRepository;
import com.energicube.eno.monitor.service.DictionaryService;
import com.energicube.eno.monitor.web.BaseController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 报警模块管理
 *
 * @author ZHANGTAO
 */
@Controller
@RequestMapping("/pwarn")
public class AlarmsController extends BaseController {

    private Log logger = LogFactory.getLog(AlarmsController.class);

    @Autowired
    private UcAlarmlogRepository ucAlarmlogRepository;

    @Autowired
    private UcAlarmlogService ucAlarmlogService;

    @Autowired
    private UcAlarmgroupService ucAlarmgroupService;

    @Autowired
    private UcAlarmactiveRepository ucAlarmactiveRepository;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private OkcDMAlphaNumRepository okcDMAlphaNumRepository;

    @Autowired
    private AssetService assetService;

    @Autowired
    private FailureCodeService failureCodeService;

    @Autowired
    private FailureListRepository failureListRepository;

    @Autowired
    AlarmInfoRepository alarmInfoRepository;

    private Config config = new Config();

    /**
     * @param model
     * @return
     */
    @RequestMapping(value = "/alarms/list", method = RequestMethod.GET)
    public String userList(Model model) {
        List<UcAlarmgroup> list = new ArrayList<UcAlarmgroup>();
        try {
            List<UcAlarmgroup> pUcAlarmgroups = ucAlarmgroupService
                    .findListByPid(-1);
            if (pUcAlarmgroups != null && pUcAlarmgroups.size() > 0) {
                List<UcAlarmgroup> mainUcAlarmgroups = ucAlarmgroupService
                        .findListByPid(pUcAlarmgroups.get(0).getGroupid());
                if (mainUcAlarmgroups != null && mainUcAlarmgroups.size() > 0) {
                    for (UcAlarmgroup ucAlarmgroup : mainUcAlarmgroups) {
                        ucAlarmgroup.setMainTemp(1);
                        list.add(ucAlarmgroup);
                        list.addAll(ucAlarmgroupService
                                .findListByPid(ucAlarmgroup.getGroupid()));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("userList", e);
        }
        model.addAttribute("listAlarmgroups", list);
        return "alarms/list";
    }

    /**
     * 跳转到报警列表
     *
     * @param groupname 子系统
     * @return
     */
    @RequestMapping(value = "/alarm", method = RequestMethod.GET)
    public String selectUcAlarmlog(String groupname, Model model) {
        if (!StringUtils.isEmpty(groupname)) {
            try {
                groupname = new String(groupname.getBytes("ISO-8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                logger.debug("selectUcAlarmlog groupname UnsupportedEncodingException", e.fillInStackTrace());
            }
        }

        model.addAttribute("groupname", groupname);
        return "alarms/logList";
    }

    /**
     * 统计本周/本月报警数量
     *
     * @param viewtype
     * @param subsystem
     * @return
     */
    @RequestMapping(value = "/getalarmcount", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Integer> getAlarmCount(String viewtype, String subsystem) {
        Map<String, Integer> alarmCountMap = new HashMap<String, Integer>();

        if (!StringUtils.isEmpty(subsystem)) {
            try {
                logger.debug("-1-subsystem--"+subsystem);
                subsystem = new String(subsystem.getBytes("ISO-8859-1"), "utf-8");
                logger.debug("-2-subsystem--"+subsystem);
            } catch (UnsupportedEncodingException e) {
                logger.debug("getAlarmCount subsystem UnsupportedEncodingException", e.fillInStackTrace());
            }
        }

        Date sDate;//开始时间
        Date eDate = new Date();//结束时间
        eDate = setStartTime(eDate);
        eDate = DateUtils.addDays(eDate, 1);//查询当天的数据时,需要把起止日期设置为 2014-11-16 00:00:00 ~ 2014-11-17 00:00:00
        if (viewtype.equals("week")) {
            //计算本周第一天
            sDate = getFirstDateForWeek();
            sDate = setStartTime(sDate);
        } else if (viewtype.equals("month")) {
            //计算本月第一天
            sDate = getFirstDateForMonth();
            sDate = setStartTime(sDate);
        } else {
            sDate = new Date();
            sDate = setStartTime(sDate);
        }

        alarmCountMap = getAlarmCouGroupByPriority(sDate, eDate, subsystem);

        return alarmCountMap;
    }


    /**
     * 统计本周/本月每天的报警数量 展现成曲线图
     *
     * @param viewtype
     * @param subsystem
     * @return
     */
    @RequestMapping(value = "/getalarmrec", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getAlarmRec(String viewtype, String subsystem) {

        Map<String, Object> alarmRecMap = new HashMap<String, Object>();

        List<Integer> eAlaramRecList = new ArrayList<Integer>();//本周/月每天紧急报警数量
        List<Integer> sAlaramRecList = new ArrayList<Integer>();//本周/月每天严重报警数量
        List<Integer> gAlaramRecList = new ArrayList<Integer>();//本周/月每天普通报警数量

        List<String> dateStrList = new ArrayList<String>();//本周/月已过去的日期

        if (!StringUtils.isEmpty(subsystem)) {
            try {
                subsystem = new String(subsystem.getBytes("ISO-8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                logger.debug("getAlarmRec subsystem UnsupportedEncodingException", e.fillInStackTrace());
            }
        }

        Date sDate;//开始时间
        Date eDate = new Date();//结束时间
        eDate = setEndTime(eDate);

        if (viewtype.equals("week")) {
            //计算本周第一天
            sDate = getFirstDateForWeek();
            sDate = setStartTime(sDate);

        } else if (viewtype.equals("month")) {
            //计算本月第一天
            sDate = getFirstDateForMonth();
            sDate = setStartTime(sDate);
        } else {
            sDate = new Date();
            sDate = setStartTime(sDate);
            logger.error("切换报警记录折线图周,月视图 异常，没有匹配的选项!");
        }

        while (sDate.before(eDate)) {
            //统计当天的报警数据
            Date tmpEndDate = DateUtils.addDays(sDate, 1);
            Map<String, Integer> alarmCountMap = getAlarmCouGroupByPriority(sDate, tmpEndDate, subsystem);
            //把不同级别的报警数量保存到对应的List
            eAlaramRecList.add(alarmCountMap.get("eAlaramCount"));
            sAlaramRecList.add(alarmCountMap.get("sAlaramCount"));
            gAlaramRecList.add(alarmCountMap.get("gAlaramCount"));
            //把当前日期保存的时间轴List
            dateStrList.add(DateTimeUtil.format(sDate, "yyyy-MM-dd"));
            //日期移到下一天
            sDate = DateUtils.addDays(sDate, 1);
        }

        alarmRecMap.put("eAlaramRecList", eAlaramRecList);
        alarmRecMap.put("sAlaramRecList", sAlaramRecList);
        alarmRecMap.put("gAlaramRecList", gAlaramRecList);
        alarmRecMap.put("dateStrList", dateStrList);

        return alarmRecMap;
    }

    /**
     * 分页查询报警记录,展示成列表
     *
     * @param sDate
     * @param eDate
     * @param alarmpriority
     * @param subsystem
     * @param pageNumber
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/getalarmlogs", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getAlarmLogs(String sDate, String eDate, String alarmpriority, String subsystem, int pageNumber, int pageSize) {

        //组织分页条件
        Pageable pageable = new PageRequest(pageNumber, pageSize, Sort.Direction.DESC, "almt");

        //组织查询条件
        if (!StringUtils.isEmpty(subsystem)) {
            try {
                subsystem = new String(subsystem.getBytes("ISO-8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                logger.debug("getAlarmLogs subsystem UnsupportedEncodingException", e.fillInStackTrace());
            }
        }

        Date startDate = null;
        Date endDate = null;
        try {
            if (!StringUtils.isEmpty(sDate)) {
                startDate = DateUtils.parseDate(sDate, "yyyy-MM-dd");
            }
            if (!StringUtils.isEmpty(eDate)) {
                endDate = DateUtils.parseDate(eDate, "yyyy-MM-dd");
                endDate = DateUtils.addDays(endDate, 1);
            }
        } catch (ParseException e) {
            logger.error("getalarmlogs parseDate Error,sDate:" + sDate + ",+endDate:" + eDate, e.fillInStackTrace());
        }

        //查询数据
        long searchUcAlarmlogListRunTime = System.currentTimeMillis();
        Page<UcAlarmlog> tmpPage = ucAlarmlogService.searchUcAlarmlogList(subsystem, Integer.parseInt(alarmpriority), startDate, endDate, pageable);
        logger.debug("searchUcAlarmlogList end :" + (System.currentTimeMillis() - searchUcAlarmlogListRunTime));
        //声明返回数据对象
        Map<String, Object> alarmLogsMap = new HashMap<String, Object>();

        //设置分页属性
        Map<String, Integer> pagingMap = new HashMap<String, Integer>();
        pagingMap.put("page", tmpPage.getNumber());
        pagingMap.put("total", tmpPage.getTotalPages());
        alarmLogsMap.put("paging", pagingMap);

        //设置当前页数据
        alarmLogsMap.put("alarmlogList", tmpPage.getContent());

        //设置报警级别
        alarmLogsMap.put("alarmPriorityDef", selectUcAlarmdef());

        return alarmLogsMap;
    }

    /**
     * 查找报警记录详细信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/ucAlarmlogfindById", method = RequestMethod.GET)
    @ResponseBody
    public UcAlarmlog ucAlarmlogfindById(int id) {
        UcAlarmlog ucAlarmlog = new UcAlarmlog();
        ucAlarmlog = ucAlarmlogRepository.findOne(id);
        return ucAlarmlog;
    }

    /**
     * 更新报警记录 批注
     *
     * @param id
     * @param reviewcontent
     * @return
     */
    @RequestMapping(value = "/upDateucAlarmlog", method = RequestMethod.GET)
    @ResponseBody
    public UcAlarmlog upDateucAlarmlog(int id, String reviewcontent) {
        try {
            reviewcontent = new String(reviewcontent.getBytes("ISO-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e) {
            logger.debug("upDateucAlarmlog reviewcontent UnsupportedEncodingException", e.fillInStackTrace());
        }
        UcAlarmlog ucAlarmlog = new UcAlarmlog();
        ucAlarmlog = ucAlarmlogRepository.findOne(id);
        ucAlarmlog.setReviewt(new Date());
        ucAlarmlog.setReviewcontent(reviewcontent);
        ucAlarmlog.setReviewer("1");
        ucAlarmlogRepository.save(ucAlarmlog);
        return ucAlarmlog;
    }

    /**
     * 查询报警记录的可能原因和处理流程
     *
     * @param dictid
     * @param description
     * @return
     */
    @RequestMapping(value = "/findByDictidAndDescriptionBy", method = RequestMethod.GET)
    @ResponseBody
    public Map findByDictidAndDescriptionBy(String dictid, String description) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            description = new String(description.getBytes("ISO-8859-1"), "utf-8");
            List<OkcDMAlphaNum> okcDMAlphaNums = okcDMAlphaNumRepository
                    .findByDictidAndDescription(dictid, description);
            OkcDMAlphaNum okcDMAlphaNum = new OkcDMAlphaNum();
            String dmvalue = "";
            if (okcDMAlphaNums.size() > 0) {
                okcDMAlphaNum = okcDMAlphaNums.get(0);
                dmvalue = okcDMAlphaNum.getDmvalue();
            }
            List<Asset> AssetList = assetService.findBySpecclass(dmvalue);
            map.put("CAUSE", "");
            map.put("REMEDY", "");
            String CAUSE_STR = "";
            List CAUSE_List = new ArrayList();
            String REMEDY_STR = "";
            List REMEDY_List = new ArrayList();
            if (AssetList.size() > 0) {
                for (int i = 0; i < AssetList.size(); i++) {
                    String code = AssetList.get(i).getFailurecode();
                    if (failureListRepository.findByFailurecode(code) != null && failureListRepository.findByFailurecode(code).getType() != null) {
                        if (failureListRepository.findByFailurecode(code)
                                .getType().equals("CAUSE")) {
                            CAUSE_List.add(failureCodeService.findFailureCodeBycode(code).getDescription());
                        }
                        if (failureListRepository.findByFailurecode(code)
                                .getType().equals("REMEDY")) {
                            REMEDY_List.add(failureCodeService.findFailureCodeBycode(code).getDescription());
                        }
                    }
                }
            }
            for (int i = 0; i < unsameList(CAUSE_List).size(); i++) {
                CAUSE_STR = CAUSE_STR + (i + 1) + "." + unsameList(CAUSE_List).get(i) + "\n";
            }
            map.put("CAUSE", CAUSE_STR);
            for (int i = 0; i < unsameList(REMEDY_List).size(); i++) {
                REMEDY_STR = REMEDY_STR + (i + 1) + "." + unsameList(REMEDY_List).get(i) + "\n";
            }
            map.put("REMEDY", REMEDY_STR);
        } catch (Exception e) {
            logger.error("findByDictidAndDescriptionBy", e);
        }
        return map;
    }

    /**
     * 获取定义的报警级别
     *
     * @return
     */
    private List<Map<String, String>> selectUcAlarmdef() {
        List<Map<String, String>> retList = new ArrayList<Map<String, String>>();
        retList.add(new HashMap<String, String>() {
            {
                put("value", "0");
                put("description", "全部");
            }
        });
        List<OkcDMAlphaNum> alarmLevels = dictionaryService.findALNDictionaryByDictid("PWARNLEVEL");
        for (int i = 0; i < alarmLevels.size(); i++) {
            Map<String, String> retMap = new HashMap<String, String>();
            OkcDMAlphaNum alphaNum = alarmLevels.get(i);
            retMap.put("value", alphaNum.getDmvalue());
            retMap.put("description", alphaNum.getDescription());
            retList.add(retMap);
        }

        return retList;
    }

    /**
     * 统计某段时间内各报警级别报警的数量
     *
     * @param sDate
     * @param eDate
     * @return Map key:eAlaramCount 紧急，sAlaramCount 严重，gAlaramCount 普通; value:对应报警级别的报警数量
     */
    private Map<String, Integer> getAlarmCouGroupByPriority(Date sDate, Date eDate, String groupName) {
        String strStartDate = DateTimeUtil.format(sDate, "yyyy-MM-dd HH:mm:ss");
        String strEndDate = DateTimeUtil.format(eDate, "yyyy-MM-dd HH:mm:ss");
        Map<String, Integer> alarmCountMap = new HashMap<String, Integer>();
        List<Object[]> alarmCouList;
        if (StringUtils.isEmpty(groupName)) {
            alarmCouList = ucAlarmlogRepository.getAlarmCouGroupByPriority(strStartDate, strEndDate);
        } else {
            alarmCouList = ucAlarmlogRepository.getAlarmCouGroupByPriority(strStartDate, strEndDate, groupName);
        }

        if (alarmCouList != null && alarmCouList.size() > 0) {
            for (int i = 0; i < alarmCouList.size(); i++) {
                Object[] tmpAlarmP = alarmCouList.get(i);
                if (tmpAlarmP[0] == Const.AlarmPriority_1) {
                    alarmCountMap.put("eAlaramCount", ((Integer) tmpAlarmP[1]));
                } else if (tmpAlarmP[0] == Const.AlarmPriority_2 || tmpAlarmP[0] == Const.AlarmPriority_3) {
                    alarmCountMap.put("sAlaramCount", ((Integer) tmpAlarmP[1]));
                } else if (tmpAlarmP[0] == Const.AlarmPriority_4) {
                    alarmCountMap.put("gAlaramCount", ((Integer) tmpAlarmP[1]));
                }
            }
        } else {
            alarmCountMap.put("eAlaramCount", 0);
            alarmCountMap.put("sAlaramCount", 0);
            alarmCountMap.put("gAlaramCount", 0);
        }
        return alarmCountMap;
    }

    public String getDateBefore(Date d, int day) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) - day);
        String str = sdf.format(now.getTime());
        return str;
    }

    public Date getFirstDayForWeek(Calendar c) {
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            return c.getTime();
        } else {
            c.roll(Calendar.DATE, false);
            return getFirstDayForWeek(c);
        }
    }


    /**
     * 报警列表
     *
     * @param pageNumber
     * @param pageSize
     * @param model
     * @return
     */
    @RequestMapping(value = "/alarmfindByfDateValue", method = RequestMethod.GET)
    public String alarmfindByfDateValue(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            Model model, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            Pageable pageable = new PageRequest(pageNumber - 1, pageSize,
                    Sort.Direction.DESC, "almt");
            Page<UcAlarmlog> page = null;
            Calendar cal = Calendar.getInstance();
            String fDateValue = request.getParameter("fDateValue");
            String groupname = new String(request.getParameter("groupname")
                    .getBytes("ISO-8859-1"), "utf-8");
            String fDateValueBl = fDateValue;
            if (fDateValue.equals(" ") || fDateValue.equals("")) {
                fDateValueBl = "1900-01-01";
                fDateValue = "0";
            }
            String tDateValue = request.getParameter("tDateValue");
            String tDateValueBl = tDateValue;
            if (tDateValue.equals(" ") || tDateValue.equals("")) {
                tDateValueBl = "2100-01-01";
                tDateValue = "0";
            }
            String alarmpriorityValue = request
                    .getParameter("alarmpriorityValue");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 小写的mm表示的是分钟
            if (alarmpriorityValue.equals("0")) {
                if (!groupname.equals("groupname"))
                    page = ucAlarmlogRepository.findByAlmtBetweenAndGroupname(
                            sdf.parse(fDateValueBl), sdf.parse(tDateValueBl),
                            groupname, pageable);
                else {
                    page = ucAlarmlogRepository.findByAlmtBetween(
                            sdf.parse(fDateValueBl), sdf.parse(tDateValueBl),
                            pageable);
                }
            } else {
                if (!groupname.equals("groupname"))
                    page = ucAlarmlogRepository
                            .findByAlmpriorityAndAlmtBetweenGroupname(
                                    sdf.parse(fDateValueBl),
                                    sdf.parse(tDateValueBl),
                                    Integer.valueOf(alarmpriorityValue),
                                    groupname, pageable);
                else {
                    page = ucAlarmlogRepository
                            .findByAlmpriorityAndAlmtBetween(
                                    sdf.parse(fDateValueBl),
                                    sdf.parse(tDateValueBl),
                                    Integer.valueOf(alarmpriorityValue),
                                    pageable);
                }
            }
            int end = page.getTotalPages();
            model.addAttribute("ucAlarmlogs", page);
            model.addAttribute("endIndex", end);
            model.addAttribute("fDateValue", "\"" + fDateValue + "\"");
            model.addAttribute("tDateValue", "\"" + tDateValue + "\"");
            List<OkcDMAlphaNum> alarmLevels = dictionaryService
                    .findALNDictionaryByDictid("PWARNLEVEL");
            model.addAttribute("alarmLevels", alarmLevels);
            model.addAttribute("alarmpriorityValue", "\"" + alarmpriorityValue
                    + "\"");
            model.addAttribute("groupname", groupname);
            List<UcAlarmlog> ucAlarmactiveList = new ArrayList<UcAlarmlog>();
            if (!groupname.equals("groupname"))
                ucAlarmactiveList = ucAlarmlogRepository.findByGroupname(groupname);
            else
                ucAlarmactiveList = ucAlarmlogRepository.findAll();
            List dataList1 = new ArrayList();
            List dataList2 = new ArrayList();
            List dataList3 = new ArrayList();
            int zjs1 = 0;
            int zjs2 = 0;
            int zjs3 = 0;
            int dayInt = cal.get(Calendar.DAY_OF_MONTH);
            int monthInt = cal.get(Calendar.MONTH) + 1;
            int yearInt = cal.get(Calendar.YEAR);
            for (int i = 1; i <= dayInt; i++) {
                int js1 = 0;
                int js2 = 0;
                int js3 = 0;
                for (int j = 0; j < ucAlarmactiveList.size(); j++) {
                    String htStringYL = sdf.format(ucAlarmactiveList.get(j)
                            .getAlmt());
                    String htString = htStringYL.substring(0, 7);
                    if (htString.equals(yearInt + "-" + monthInt)
                            || htString.equals(yearInt + "-0" + monthInt)) {
                        if (htStringYL.substring(8, 10).equals(i + "")
                                || htStringYL.substring(8, 10).equals("0" + i)) {
                            if (ucAlarmactiveList.get(j).getAlmpriority() == 1) {
                                js1 = js1 + 1;
                            }
                            if (ucAlarmactiveList.get(j).getAlmpriority() == 2
                                    || ucAlarmactiveList.get(j)
                                    .getAlmpriority() == 3) {
                                js2 = js2 + 1;
                            }
                            if (ucAlarmactiveList.get(j).getAlmpriority() == 4) {
                                js3 = js3 + 1;
                            }
                        }
                    }
                }
                zjs1 = zjs1 + js1;
                zjs2 = zjs2 + js2;
                zjs3 = zjs3 + js3;
                dataList1.add(js1);
                dataList2.add(js2);
                dataList3.add(js3);

            }
            model.addAttribute("dataList1", dataList1);
            model.addAttribute("dataList2", dataList2);
            model.addAttribute("dataList3", dataList3);
            model.addAttribute("zjs1", zjs1);
            model.addAttribute("zjs2", zjs2);
            model.addAttribute("zjs3", zjs3);

            //统计周
            List dataWeekList1 = new ArrayList();
            List dataWeekList2 = new ArrayList();
            List dataWeekList3 = new ArrayList();
            for (int i = 1; i <= 7; i++) {
                int jsWeek1 = 0;
                int jsWeek2 = 0;
                int jsWeek3 = 0;
                String strDate = getDateBefore(new Date(), 7 - i);
                for (int j = 0; j < ucAlarmactiveList.size(); j++) {
                    String htStringYL = sdf.format(ucAlarmactiveList.get(j)
                            .getAlmt());
                    if (strDate.equals(htStringYL)) {
                        if (ucAlarmactiveList.get(j).getAlmpriority() == 1) {
                            jsWeek1 = jsWeek1 + 1;
                        }
                        if (ucAlarmactiveList.get(j).getAlmpriority() == 2
                                || ucAlarmactiveList.get(j).getAlmpriority() == 3) {
                            jsWeek2 = jsWeek2 + 1;
                        }
                        if (ucAlarmactiveList.get(j).getAlmpriority() == 4) {
                            jsWeek3 = jsWeek3 + 1;
                        }
                    }
                }
                dataWeekList1.add(jsWeek1);
                dataWeekList2.add(jsWeek2);
                dataWeekList3.add(jsWeek3);
            }
            model.addAttribute("dataWeekList1", dataWeekList1);
            model.addAttribute("dataWeekList2", dataWeekList2);
            model.addAttribute("dataWeekList3", dataWeekList3);
        } catch (Exception e) {
            logger.error("alarmfindByfDateValue", e);
        }
        return "alarms/alarmlogList";
    }

    @RequestMapping(value = "/ucAlarmlogfindByfDateValuePage", method = RequestMethod.GET)
    public
    @ResponseBody
    Page<UcAlarmlog> ucAlarmlogfindByfDateValuePage(
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            Model model, HttpServletRequest request,
            HttpServletResponse response) {
        Page<UcAlarmlog> page = null;
        try {
            int pageNumber = Integer.valueOf(request.getParameter("pagef"));
            if (pageNumber == 0) {
                pageNumber = 1;
            }
            Pageable pageable = new PageRequest(pageNumber - 1, pageSize,
                    Sort.Direction.DESC, "almt");
            String fDateValue = request.getParameter("fDateValue");
            String fDateValueBl = fDateValue;
            if (fDateValue.equals(" ") || fDateValue.equals("")) {
                fDateValueBl = "1900-01-01";
                fDateValue = "0";
            }
            String tDateValue = request.getParameter("tDateValue");
            String groupname = new String(request.getParameter("groupname")
                    .getBytes("ISO-8859-1"), "utf-8");
            String tDateValueBl = tDateValue;
            if (tDateValue.equals(" ") || tDateValue.equals("")) {
                tDateValueBl = "2100-01-01";
                tDateValue = "0";
            }
            String alarmpriorityValue = request
                    .getParameter("alarmpriorityValue");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 小写的mm表示的是分钟
            if (alarmpriorityValue.equals("0")) {
                if (!groupname.equals("groupname"))
                    page = ucAlarmlogRepository.findByAlmtBetweenAndGroupname(
                            sdf.parse(fDateValueBl), sdf.parse(tDateValueBl),
                            groupname, pageable);
                else {
                    page = ucAlarmlogRepository.findByAlmtBetween(
                            sdf.parse(fDateValueBl), sdf.parse(tDateValueBl),
                            pageable);
                }
            } else {
                if (!groupname.equals("groupname"))
                    page = ucAlarmlogRepository
                            .findByAlmpriorityAndAlmtBetweenGroupname(
                                    sdf.parse(fDateValueBl),
                                    sdf.parse(tDateValueBl),
                                    Integer.valueOf(alarmpriorityValue),
                                    groupname, pageable);
                else {
                    page = ucAlarmlogRepository
                            .findByAlmpriorityAndAlmtBetween(
                                    sdf.parse(fDateValueBl),
                                    sdf.parse(tDateValueBl),
                                    Integer.valueOf(alarmpriorityValue),
                                    pageable);
                }
            }
        } catch (Exception e) {
            logger.error("ucAlarmlogfindByfDateValuePage", e);
        }
        return page;
    }


    @RequestMapping(value = "/ucAlarmfindById", method = RequestMethod.GET)
    public
    @ResponseBody
    UcAlarmactive ucAlarmfindById(Model model, HttpServletRequest request,
                                  HttpServletResponse response) {
        UcAlarmactive ucAlarmactive = new UcAlarmactive();
        try {

            int id = Integer.valueOf(request.getParameter("id"));
//            String date_almt = request.getParameter("date_almt");
            //原先是通过这个ID查询的ucalarmlog表。改为查询ucalarmactive表。log表里这个id有重复。
            ucAlarmactive = ucAlarmactiveRepository.findOne(id);
        } catch (Exception e) {
            logger.error("---ucAlarmfindById----", e);
        }
        return ucAlarmactive;
    }

    /**
     * 实时报警
     */
    @RequestMapping(value = "/alarmcurr", method = RequestMethod.GET)
    public String warningList(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            Model model, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            LocalDateTime localdatetime = LocalDateTime.now();
            int yearNow = localdatetime.getYear();
            int monthNow = localdatetime.getMonthOfYear();
            int dateNow = localdatetime.getDayOfMonth();
            int hourNow = localdatetime.getHourOfDay();
            int minNow = localdatetime.getMinuteOfHour();
            int secNow = localdatetime.getSecondOfMinute();

            List<UcAlarmactive> ucAlarmactives = new ArrayList<UcAlarmactive>();
            Pageable pageable = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.DESC, "almt");
            // 查所有的当天的报警信息
            Page pageAlarm = ucAlarmactiveRepository.findAlarmpriorityByAlmtAndPage(yearNow, monthNow, dateNow, pageable);

            ucAlarmactives = pageAlarm.getContent();
            //查询是否存在设备通讯故障的报警
            String telnet = "N";
            for (UcAlarmactive ucAlarmactive : ucAlarmactives) {
                int grade = ucAlarmactive.getAlmpriority();
                if (grade == 4) {
                    telnet = "Y";
                    break;
                }
            }
//提供新的报警的详细信息
            UcAlarmactive ucAlarmactive = new UcAlarmactive();
            if (ucAlarmactives.size() > 0) {
                //取第一个消防系统报警，前台弹框报警
                UcAlarmactive ucAlarmactive_xf = ucAlarmactives.get(0);
                Date date = ucAlarmactive_xf.getAlmt();
                DateTime dt = new DateTime(date);
                long hour = dt.getHourOfDay();
                long min = dt.getMinuteOfHour();
                long sec = dt.getSecondOfMinute();
                //前台弹窗报警，10秒内的
                if (ucAlarmactive_xf.getGroupname().equals("消防系统") && hour == hourNow && min == minNow && (secNow - sec) <= 10) {
                    ucAlarmactive = ucAlarmactive_xf;
                }
            }
            model.addAttribute("alarmAlert", ucAlarmactive);

            model.addAttribute("ucAlarmactives", ucAlarmactives);
            model.addAttribute("endIndex", pageAlarm.getTotalPages());

            List<OkcDMAlphaNum> alarmLevels = dictionaryService
                    .findALNDictionaryByDictid("PWARNLEVEL");
            model.addAttribute("alarmLevels", alarmLevels);
            model.addAttribute("alarmpriorityValue", "0");
            model.addAttribute("groupname", "");
            model.addAttribute("TELNET", telnet);

            List dataList1 = new ArrayList();
            List dataList2 = new ArrayList();
            List dataList3 = new ArrayList();
            List cataList = new ArrayList();
            int zjs1 = 0;
            int zjs2 = 0;
            int zjs3 = 0;
            int hourInt = 0;
            hourInt = localdatetime.getHourOfDay();

            List data = alarmInfoRepository.findCountByTimeAndAlmpriorityGroup(null, yearNow + "", monthNow + "", dateNow + "");
            Map<String, Integer> hashMap1 = new HashMap<String, Integer>();
            Map<String, Integer> hashMap2 = new HashMap<String, Integer>();
            Map<String, Integer> hashMap3 = new HashMap<String, Integer>();

            if (data != null) {
                int sum = data.size();

                //总共数据条数
                for (int i = 0; i < sum; i++) {
                    Object[] da = (Object[]) data.get(i);
                    //单行数据循环
                    //1级别
                    //2小时
                    //3数量
                    Object grade = da[0];
                    Object hour = da[1];
                    Object num = da[2];

                    int n = Integer.parseInt(num.toString());

                    if (grade.toString().equals("1")) {
                        zjs1 += n;
                        Integer old = hashMap1.get(hour.toString());
                        int x = n;
                        if (old != null) {
                            x = n + old;
                        }

                        hashMap1.put(hour.toString(), x);
                    }
                    if (grade.toString().equals("2") || grade.toString().equals("3")) {
                        zjs2 += n;
                        Integer old = hashMap2.get(hour.toString());
                        int x = n;
                        if (old != null) {
                            x = n + old;
                        }
                        hashMap2.put(hour.toString(), x);
                    }
                    if (grade.toString().equals("4")) {
                        zjs3 += n;
                        Integer old = hashMap3.get(hour.toString());
                        int x = n;
                        if (old != null) {
                            x = n + old;
                        }
                        hashMap3.put(hour.toString(), x);
                    }
                }
            }
            //图表数据
            for (int i = 0; i <= hourInt; i++) {
                Integer grade1 = hashMap1.get(i + "");
                Integer grade2 = hashMap2.get(i + "");
                Integer grade3 = hashMap3.get(i + "");
                if (grade1 != null) {
                    dataList1.add(grade1);
                } else {
                    dataList1.add(0);
                }

                if (grade2 != null) {
                    dataList2.add(grade2);
                } else {
                    dataList2.add(0);
                }

                if (grade3 != null) {
                    dataList3.add(grade3);
                } else {
                    dataList3.add(0);
                }

                cataList.add(i);
            }
            model.addAttribute("dataList1", dataList1);
            model.addAttribute("dataList2", dataList2);
            model.addAttribute("dataList3", dataList3);
            model.addAttribute("zjs1", zjs1);
            model.addAttribute("zjs2", zjs2);
            model.addAttribute("zjs3", zjs3);
            model.addAttribute("cataList", cataList);
        } catch (Exception e) {
            e.printStackTrace();
            //设置默认数据
            model.addAttribute("endIndex", 0);
            model.addAttribute("groupname", "");
            List list = new ArrayList();
            model.addAttribute("dataList1", list);
            model.addAttribute("dataList2", list);
            model.addAttribute("dataList3", list);
            model.addAttribute("zjs1", 0);
            model.addAttribute("zjs2", 0);
            model.addAttribute("zjs3", 0);
            model.addAttribute("cataList", list);
            model.addAttribute("alarmpriorityValue", "0");
        }
        return "alarms/activeList";
    }

    /**
     * 根据groupname获取对应模块的实时报警信息
     *
     * @param pageNumber
     * @param pageSize
     * @param request
     * @param groupname  // 模块名称(如：暖通空调)
     * @return 报警列表
     */
    @RequestMapping(value = "/alarmcurrfindByAlaram", method = RequestMethod.GET)
    @ResponseBody
    public Object alarmcurrfindByAlaram(
            @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize, HttpServletRequest request,
            String groupname) {
        Map map = new HashMap();
        try {
            LocalDateTime localdatetime = LocalDateTime.now();
            int yearNow = localdatetime.getYear();
            int monthNow = localdatetime.getMonthOfYear();
            int dateNow = localdatetime.getDayOfMonth();

//            List<UcAlarmactive> ucAlarmactives = new ArrayList<UcAlarmactive>();
            // 模块名称(如：暖通空调)
            logger.debug("--groupname---"+groupname);
            Pageable pageable = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.DESC, "almt");
            Page pageAlarm = null;
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(groupname)) { // 查具体的子系统的报警
                pageAlarm = ucAlarmactiveRepository.findAlmtAndGroupname(yearNow, monthNow, dateNow, groupname, pageable);
            } else { // 查所有的当天的报警信息
                pageAlarm  = ucAlarmactiveRepository.findAlarmpriorityByAlmtAndPage(yearNow, monthNow, dateNow, pageable);
            }
            map.put("ucAlarmactives", pageAlarm);

            List<OkcDMAlphaNum> alarmLevels = dictionaryService.findALNDictionaryByDictid("PWARNLEVEL"); // 获取报警级别
            map.put("alarmLevels", alarmLevels);
            map.put("alarmpriorityValue", "0");
            map.put("groupname", groupname);

            List dataList1 = new ArrayList();
            List dataList2 = new ArrayList();
            List dataList3 = new ArrayList();
            List cataList = new ArrayList();
            int zjs1 = 0;
            int zjs2 = 0;
            int zjs3 = 0;
            int hourInt = 0;
            hourInt = localdatetime.getHourOfDay();

            String time = yearNow + "-" + monthNow + "-" + dateNow; // 年月日
            for (int i = 0; i <= hourInt; i++) {
                String starttime = time + " " + i + ":00:00";
                String endtime = time + " " + (i + 1 == 24 ? 0 : i + 1) + ":00:00";
                List<Object> l1 = alarmInfoRepository.findCountByTimeAndAlmpriority(groupname, starttime, endtime, 1, 1);
                List<Object> l2 = alarmInfoRepository.findCountByTimeAndAlmpriority(groupname, starttime, endtime, 2, 3);
                List<Object> l3 = alarmInfoRepository.findCountByTimeAndAlmpriority(groupname, starttime, endtime, 4, 4);
                zjs1 += l1.size();
                zjs2 += l2.size();
                zjs3 += l3.size();
                dataList1.add(l1.size());
                dataList2.add(l2.size());
                dataList3.add(l3.size());
                cataList.add(i);
            }
            map.put("dataList1", dataList1);
            map.put("dataList2", dataList2);
            map.put("dataList3", dataList3);
            map.put("zjs1", zjs1);
            map.put("zjs2", zjs2);
            map.put("zjs3", zjs3);
            map.put("cataList", cataList);
        } catch (Exception e) {
            logger.error("---alarmcurrfindByAlaram---"+e);
            map.put("endIndex", 0);
            map.put("groupname", "");
            List list = new ArrayList();
            map.put("dataList1", list);
            map.put("dataList2", list);
            map.put("dataList3", list);
            map.put("zjs1", 0);
            map.put("zjs2", 0);
            map.put("zjs3", 0);
            map.put("cataList", list);
            map.put("alarmpriorityValue", "0");
        }
        return map;
    }


    /**
     * 报警应答兼批注
     * 2014-10-14
     *
     * @param pageSize
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/upDatealarm", method = RequestMethod.GET)
    public
    @ResponseBody
    Object upDatealarm(@RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                       Model model, HttpServletRequest request,
                       HttpServletResponse response) {
        Map map = new HashMap(1);
        try {
            String reviewcontent = new String(request.getParameter(
                    "reviewcontent").getBytes("ISO-8859-1"), "utf-8");
            int id = Integer.valueOf(request.getParameter("id"));

            String tagid = request.getParameter("tagid");

            //调用UCview的服务进行应答，UCview的服务分为两个服务，所以分两次，一个应答，一个批注。
            //进行应答
            String answerUrl = config.getProps().getProperty("setAnswerAlarmUrl");
            SendCommandToSystem.sendCommand(answerUrl, "tagid=" + tagid);
            //进行批注
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(reviewcontent)) {
                Subject currentUser = SecurityUtils.getSubject();
                String userName = currentUser.getPrincipal().toString();

                answerUrl = config.getProps().getProperty("setAlarmDescUrl");
                userName = URLEncoder.encode(userName, "utf-8");
                reviewcontent = URLEncoder.encode(reviewcontent, "utf-8");
                logger.info("----reviewcontent----" + reviewcontent + "--userName-" + userName);
                SendCommandToSystem.sendCommand(answerUrl, "sn=" + id + "&author=" + userName + "&content=" + reviewcontent);
            }

            map.put("status", 0);
        } catch (Exception e) {
            logger.error("--upDatealarm--" + e);
            map.put("status", -1);
        }
        return map;
    }


    /**
     * 刷新中间三个级别的报警数和右侧趋势图的显示
     *
     * @param pageSize
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/alarmReashTp", method = RequestMethod.GET)
    public
    @ResponseBody
    UcAlarmsList alarmReashTp(
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            Model model, HttpServletRequest request,
            HttpServletResponse response) {
        UcAlarmsList ucAlarmsList = new UcAlarmsList();
        try {
            LocalDateTime localdatetime = LocalDateTime.now();
            int yearNow = localdatetime.getYear();
            int monthNow = localdatetime.getMonthOfYear();
            int dateNow = localdatetime.getDayOfMonth();
            String groupname = new String(request.getParameter("groupname")
                    .getBytes("ISO-8859-1"), "utf-8");

            List dataList1 = new ArrayList();
            List dataList2 = new ArrayList();
            List dataList3 = new ArrayList();
            List cataList = new ArrayList();
            int zjs1 = 0;
            int zjs2 = 0;
            int zjs3 = 0;
            int hourInt = 0;
            hourInt = localdatetime.getHourOfDay();

            List data = alarmInfoRepository.findCountByTimeAndAlmpriorityGroup(groupname, yearNow + "", monthNow + "", dateNow + "");
            Map<String, Integer> hashMap1 = new HashMap<String, Integer>();
            Map<String, Integer> hashMap2 = new HashMap<String, Integer>();
            Map<String, Integer> hashMap3 = new HashMap<String, Integer>();

            if (data != null) {
                int sum = data.size();

                //总共数据条数
                for (int i = 0; i < sum; i++) {
                    Object[] da = (Object[]) data.get(i);
                    //单行数据循环
                    //1级别
                    //2小时
                    //3数量
                    Object grade = da[0];
                    Object hour = da[1];
                    Object num = da[2];

                    int n = Integer.parseInt(num.toString());

                    if (grade.toString().equals("1")) {
                        zjs1 += n;
                        Integer old = hashMap1.get(hour.toString());
                        int x = n;
                        if (old != null) {
                            x = n + old;
                        }
                        hashMap1.put(hour.toString(), x);
                    }
                    if (grade.toString().equals("2") || grade.toString().equals("3")) {
                        zjs2 += n;
                        Integer old = hashMap2.get(hour.toString());
                        int x = n;
                        if (old != null) {
                            x = n + old;
                        }
                        hashMap2.put(hour.toString(), x);
                    }
                    if (grade.toString().equals("4")) {
                        zjs3 += n;
                        Integer old = hashMap3.get(hour.toString());
                        int x = n;
                        if (old != null) {
                            x = n + old;
                        }
                        hashMap3.put(hour.toString(), x);
                    }
                }
            }
            //图表数据
            for (int i = 0; i <= hourInt; i++) {
                Integer grade1 = hashMap1.get(i + "");
                Integer grade2 = hashMap2.get(i + "");
                Integer grade3 = hashMap3.get(i + "");
                if (grade1 != null) {
                    dataList1.add(grade1);
                } else {
                    dataList1.add(0);
                }

                if (grade2 != null) {
                    dataList2.add(grade2);
                } else {
                    dataList2.add(0);
                }

                if (grade3 != null) {
                    dataList3.add(grade3);
                } else {
                    dataList3.add(0);
                }

                cataList.add(i);
            }
            ucAlarmsList.setDataList1(dataList1);
            ucAlarmsList.setDataList2(dataList2);
            ucAlarmsList.setDataList3(dataList3);
            ucAlarmsList.setZjs1(zjs1 + "");
            ucAlarmsList.setZjs2(zjs2 + "");
            ucAlarmsList.setZjs3(zjs3 + "");
            ucAlarmsList.setCataList(cataList);
        } catch (Exception e) {
            logger.error("--------query alarmReashTp----------------------", e);
        }
        return ucAlarmsList;
    }

    @RequestMapping(value = "/alarms/alarmActive_detail", method = RequestMethod.GET)
    public String handleList(Model model, HttpServletRequest request,
                             HttpServletResponse response) {
        return "alarms/alarmActive_detail";
    }

    @RequestMapping(value = "/source/pid/img", method = RequestMethod.GET)
    public String sourcePieImg(Model model, HttpServletRequest request,
                               HttpServletResponse response) {

        return "alarms/source_pidimg";
    }

    @RequestMapping(value = "/source/line/img", method = RequestMethod.GET)
    public String sourceLineImg(Model model, HttpServletRequest request,
                                HttpServletResponse response) {
        return "alarms/source_lineimg";
    }


    @RequestMapping(value = "/findByLjDictidAndDescriptionBy", method = RequestMethod.GET)
    public
    @ResponseBody
    Map findByLjDictidAndDescriptionBy(Model model, HttpServletRequest request,
                                       HttpServletResponse response) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            String dictid = request.getParameter("dictid");
            String description = new String(request.getParameter("description")
                    .getBytes("ISO-8859-1"), "utf-8");
            List<OkcDMAlphaNum> okcDMAlphaNums = okcDMAlphaNumRepository
                    .findByDictidAndDescription(dictid, description);
            OkcDMAlphaNum okcDMAlphaNum = new OkcDMAlphaNum();
            String dmvalue = "";
            if (okcDMAlphaNums.size() > 0) {
                okcDMAlphaNum = okcDMAlphaNums.get(0);
                dmvalue = okcDMAlphaNum.getDmvalue();
            }
            List<Asset> AssetList = assetService.findBySpecclass(dmvalue);
            map.put("CAUSE", "");
            map.put("REMEDY", "");
            String CAUSE_STR = "";
            List CAUSE_List = new ArrayList();
            String REMEDY_STR = "";
            List REMEDY_List = new ArrayList();
            if (AssetList.size() > 0) {
                for (int i = 0; i < AssetList.size(); i++) {
                    String code = AssetList.get(i).getFailurecode();
                    if (failureListRepository.findByFailurecode(code) != null && failureListRepository.findByFailurecode(code).getType() != null) {
                        if (failureListRepository.findByFailurecode(code)
                                .getType().equals("CAUSE")) {
                            CAUSE_List.add(failureCodeService.findFailureCodeBycode(code).getDescription());
                        }
                        if (failureListRepository.findByFailurecode(code)
                                .getType().equals("REMEDY")) {
                            REMEDY_List.add(failureCodeService.findFailureCodeBycode(code).getDescription());
                        }
                    }
                }
            }
            for (int i = 0; i < unsameList(CAUSE_List).size(); i++) {
                CAUSE_STR = CAUSE_STR + (i + 1) + "." + unsameList(CAUSE_List).get(i) + "\n";
            }
            map.put("CAUSE", CAUSE_STR);
            for (int i = 0; i < unsameList(REMEDY_List).size(); i++) {
                REMEDY_STR = REMEDY_STR + (i + 1) + "." + unsameList(REMEDY_List).get(i) + "\n";
            }
            map.put("REMEDY", REMEDY_STR);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public List unsameList(List list) {
        HashSet h = new HashSet(list);
        list.clear();
        list.addAll(h);
        return list;
    }

    private List<UcAlarmactive> getUcAlarmactiveResult(String tagUrl) throws Exception {
        List<UcAlarmactive> list = new ArrayList<UcAlarmactive>();
        String resultString = "";
        URL url = null;
        try {
            url = new URL(tagUrl);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
            URLConnection URLconnection = null;
            URLconnection = url.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) URLconnection;
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // 获取查询结果
                InputStream urlStream = httpConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(urlStream, "utf-8"));
                String currentLine = "";
                while ((currentLine = bufferedReader.readLine()) != null) {
                    resultString += currentLine;
                }
                urlStream.close();
                bufferedReader.close();
                ObjectMapper mapper = new ObjectMapper();
                List<HashMap> listObject = mapper.readValue(resultString, ArrayList.class);
                for (int i = listObject.size() - 1; i >= 0; i--) {
                    Map jsonObject = mapper.readValue(listObject.get(i).toString(), Map.class);
//                    JSONObject jsonObject = array.getJSONObject(i);
                    if (jsonObject.get("AlmTime").toString().substring(0, 10).equals(sdf1.format(new Date()))) {
                        List<UcAlarmlog> ucAlarmlogs = ucAlarmlogRepository.findByAlmlogid(Integer.parseInt(jsonObject.get("SerialNo").toString()));
                        if (ucAlarmlogs.size() > 0) {
//	            	UcAlarmlog ucAlarmlog = ucAlarmlogs.get(0);
                            UcAlarmlog ucAlarmlog = new UcAlarmlog();
                            for (int j = 0; j < ucAlarmlogs.size(); j++) {
                                if (sdf.format(ucAlarmlogs.get(j).getAlmt()).substring(0, 19).equals(jsonObject.get("AlmTime"))) {
                                    ucAlarmlog = ucAlarmlogs.get(j);
                                }
                            }
                            UcAlarmactive ucAlarmactive = new UcAlarmactive();
                            ucAlarmactive.setAlmlogid(ucAlarmlog.getAlmlogid());
                            ucAlarmactive.setTagid(ucAlarmlog.getTagid());
                            ucAlarmactive.setAlmt(ucAlarmlog.getAlmt());
                            ucAlarmactive.setAlmcomment(ucAlarmlog.getAlmcomment());
                            ucAlarmactive.setGroupname(ucAlarmlog.getGroupname());
                            ucAlarmactive.setDevicename(ucAlarmlog.getDevicename());
                            ucAlarmactive.setTagcomment(ucAlarmlog.getTagcomment());
                            ucAlarmactive.setAlmpriority(ucAlarmlog.getAlmpriority());
                            ucAlarmactive.setReviewer(ucAlarmlog.getReviewer());
                            ucAlarmactive.setReviewt(ucAlarmlog.getReviewt());
                            ucAlarmactive.setReviewcontent(ucAlarmlog.getReviewcontent());
                            list.add(ucAlarmactive);
                        }
                    }
                }
            } else {
                throw new Exception("HttpURLConnection Status:" + responseCode);
            }
        } catch (MalformedURLException e1) {
            logger.error("getUcAlarmactiveResult MalformedURLException ", e1);
        }
        return list;
    }

    private void getUrlResult(String tagUrl) throws Exception {
        String resultString = "";
        URL url = null;
        try {
            url = new URL(tagUrl);
            URLConnection URLconnection = null;
            URLconnection = url.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) URLconnection;
            int responseCode = httpConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
            } else {
                throw new Exception("HttpURLConnection Status:" + responseCode);
            }
        } catch (MalformedURLException e1) {
            e1.printStackTrace();
        }
    }

    private List<UcAlarmactive> getUcAlarmactiveByGroupname(List<UcAlarmactive> ucAlarmactives, String groupname) {
        List<UcAlarmactive> ucAlarmactiveList = new ArrayList<UcAlarmactive>();
        for (int i = 0; i < ucAlarmactives.size(); i++) {
            UcAlarmactive ucAlarmactive = ucAlarmactives.get(i);
            if (ucAlarmactive.getGroupname().equals(groupname)) {
                ucAlarmactiveList.add(ucAlarmactive);
            }
        }
        return ucAlarmactiveList;
    }

    private List<UcAlarmactive> getUcAlarmactiveByAlarmpriority(List<UcAlarmactive> ucAlarmactives, String alarmpriority) {
        List<UcAlarmactive> ucAlarmactiveList = new ArrayList<UcAlarmactive>();
        for (int i = 0; i < ucAlarmactives.size(); i++) {
            UcAlarmactive ucAlarmactive = ucAlarmactives.get(i);
            if (String.valueOf(ucAlarmactive.getAlmpriority()).equals(alarmpriority)) {
                ucAlarmactiveList.add(ucAlarmactive);
            }
        }
        return ucAlarmactiveList;
    }


    public long[] getDistanceTimes(String str1, String str2) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff;
            if (time1 < time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        } catch (Exception e) {
            e.printStackTrace();
        }
        long[] times = {day, hour, min, sec};
        return times;
    }

    /**
     * 获取报警个数
     *
     * @param request
     * @param response
     * @return
     * @author zouzhixiang 2014-03-04
     */
    @RequestMapping(value = "/alarms/getAlarmsCountByGroup")
    public
    @ResponseBody
    Map<String, Object> alarmcurrfindByAlaram(
            @RequestParam(value = "groupname", required = false, defaultValue = "") String groupname,// 子系统名称
            @RequestParam(value = "alarmpriority", required = false, defaultValue = "4") String alarmpriority,// 报警级别
            HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<String, Object>(), tempmap = new HashMap<String, Object>();
        List<UcAlarmactive> ucAlarmas = null;
        try {
            String activeurl = config.getProps().getProperty("getAlarmActiveUrl") + "alarm/active/list";
            ucAlarmas = getUcAlarmactiveResult(activeurl);
            map.put("activelist", ucAlarmas); // 存放活动的报警列表
            List<UcAlarmactive> ucAlarmactives = getUcAlarmactiveByGroupname(ucAlarmas, groupname); // 存放对应groupname的子系统报警列表
            tempmap = getAlarmpriorityMap(ucAlarmactives); // 对应子系统各报警程度列表
            map.put("alarmprioritymap", tempmap); // 存放对应groupname的子系统报警
        } catch (Exception e) {
            logger.error("alarmcurrfindByAlaram", e);
        }
        return map;
    }

    /**
     * 得到对应报警级别的个数
     *
     * @param ucAlarmactives
     * @return
     */
    private Map<String, Object> getAlarmpriorityMap(List<UcAlarmactive> ucAlarmactives) {
        Map<String, Object> map = new HashMap<String, Object>();
        int a = 0, b = 0, c = 0, d = 0; // 分别存储不同的报警级别(极高（紧急报警）,高（严重报警）,中（严重报警）,低（普通报警）)
        for (int i = 0; i < ucAlarmactives.size(); i++) {
            UcAlarmactive ucAlarmactive = ucAlarmactives.get(i);
            if (String.valueOf(ucAlarmactive.getAlmpriority()).equals("1")) // 极高（紧急报警）
                a++;
            else if (String.valueOf(ucAlarmactive.getAlmpriority()).equals("2")) // 高（严重报警）
                b++;
            else if (String.valueOf(ucAlarmactive.getAlmpriority()).equals("3")) // 中（严重报警）
                c++;
            else if (String.valueOf(ucAlarmactive.getAlmpriority()).equals("4")) // 低（普通报警）
                d++;
        }
        map.put("level_1", a);
        map.put("level_2", b);
        map.put("level_3", c);
        map.put("level_4", d);
        return map;
    }


    @RequestMapping(value = "/alarms/confirmReported")
    public String confirmReported(String alarms, String ids, Model model) {
        System.out.println("alarms:" + alarms);
        return null;
    }

    /**
     * 下载 报警列表
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/alarm/download")
    public ModelAndView download(String sDate, String eDate, String alarmpriority, String subsystem, HttpServletRequest request, HttpServletResponse response) throws Exception {

        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {

            String storeName = System.currentTimeMillis() + ".xls";
            String contentType = "application/octet-stream";
            String patternExcelPath = config.getProps().getProperty("patternExcelPath");
            String ctxPath = request.getSession().getServletContext().getRealPath("/") + patternExcelPath;

            File file = new File(ctxPath);
            if (!file.exists()) {
                file.mkdir();
            }
            String downLoadPath = ctxPath + storeName;

            //组织查询条件
            if (!StringUtils.isEmpty(subsystem)) {
                try {
                    subsystem = new String(subsystem.getBytes("ISO-8859-1"), "utf-8");
                } catch (UnsupportedEncodingException e) {
                    logger.error("getAlarmLogs subsystem UnsupportedEncodingException", e.fillInStackTrace());
                }
            }

            Date startDate = null;
            Date endDate = null;
            try {
                if (!StringUtils.isEmpty(sDate)) {
                    startDate = DateUtils.parseDate(sDate, "yyyy-MM-dd");
                }
                if (!StringUtils.isEmpty(eDate)) {
                    endDate = DateUtils.parseDate(eDate, "yyyy-MM-dd");
                }
            } catch (ParseException e) {
                logger.error("getalarmlogs parseDate Error,sDate:" + sDate + ",+endDate:" + eDate, e.fillInStackTrace());
            }

            //查询数据
            long searchUcAlarmlogListRunTime = System.currentTimeMillis();
            List<UcAlarmlog> tmpList = ucAlarmlogService.searchUcAlarmlogList(subsystem, Integer.parseInt(alarmpriority), startDate, endDate);
            logger.debug("exportAlarm searchUcAlarmlogList end :" + (System.currentTimeMillis() - searchUcAlarmlogListRunTime));
            //生成excel文件
            PatternExcel.getAlarmToFile(tmpList, downLoadPath);

            //向客流端返回文件流
            request.setCharacterEncoding("UTF-8");
            long fileLength = new File(downLoadPath).length();

            response.setContentType(contentType);
            response.setHeader("Content-disposition", "attachment; filename=" + new String(storeName.getBytes("utf-8"), "ISO8859-1"));
            response.setHeader("Content-Length", String.valueOf(fileLength));

            bis = new BufferedInputStream(new FileInputStream(downLoadPath));
            bos = new BufferedOutputStream(response.getOutputStream());
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            bis.close();
            bos.close();
        } catch (IOException io) {
            logger.error("---file export fail---", io);
        } finally {
            try {
                bis.close();
                bos.close();
            } catch (Exception io) {
                logger.error("--file export fail-2--", io);
            }
        }
        return null;
    }


    /**
     * 计算本周第一天的日期
     *
     * @return
     */
    private Date getFirstDateForMonth() {
        Date sDate;
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        sDate = c.getTime();
        return sDate;
    }

    /**
     * 计算本月第一天的日期
     *
     * @return
     */
    private Date getFirstDateForWeek() {
        Date sDate;
        Calendar c = Calendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY); // 默认时，每周第一天为星期日
        c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        sDate = c.getTime();
        return sDate;
    }

    /**
     * 设置日期的时,分,秒为 00:00:00
     *
     * @param date
     * @return
     */
    private Date setStartTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 00);
        c.set(Calendar.MINUTE, 00);
        c.set(Calendar.SECOND, 00);
        return c.getTime();
    }

    /**
     * 设置日期的时,分,秒为 23:59:59
     *
     * @param date
     * @return
     */
    private Date setEndTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }

    /**
     * 查询报警
     *
     * @param pageSize
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/alarmfindByalarmpriorityPage", method = RequestMethod.GET)
    public
    @ResponseBody
    Object alarmfindByalarmpriorityPage(
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            Model model, HttpServletRequest request,
            HttpServletResponse response) {
        Map map = new HashMap(3);
        try {
            int pageNumber = Integer.valueOf(request.getParameter("pagef"));
            if (pageNumber == 0) {
                pageNumber = 1;
            }
            String alarmpriority = request.getParameter("alarmpriority");
            String groupname = new String(request.getParameter("groupname")
                    .getBytes("ISO-8859-1"), "utf-8");
            List<UcAlarmactive> ucAlarmas = new ArrayList<UcAlarmactive>();

            LocalDateTime localdatetime = LocalDateTime.now();
            int yearNow = localdatetime.getYear();
            int monthNow = localdatetime.getMonthOfYear();
            int dateNow = localdatetime.getDayOfMonth();
            int hourNow = localdatetime.getHourOfDay();
            int minNow = localdatetime.getMinuteOfHour();
            int secNow = localdatetime.getSecondOfMinute();
            //查询今天的报警分页(报警列表的数据)
            Pageable pageable = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.DESC, "almt");
            Page pageUcAlarmas = new PageImpl(new ArrayList());
            boolean queryFlag = false;
            if (alarmpriority.equals("0")) {
                if (org.apache.commons.lang3.StringUtils.isNotEmpty(groupname)) {
                    pageUcAlarmas = ucAlarmactiveRepository.findAlmtAndGroupname(yearNow, monthNow, dateNow, groupname, pageable);
                    queryFlag = true;
                } else {
                    pageUcAlarmas = ucAlarmactiveRepository.findAlarmpriorityByAlmtAndPage(yearNow, monthNow, dateNow, pageable);
                }
            } else {
                if (org.apache.commons.lang3.StringUtils.isNotEmpty(groupname)) {
                    pageUcAlarmas = ucAlarmactiveRepository.findListByAlmpriorityAndGroupname(Integer.parseInt(alarmpriority), yearNow, monthNow, dateNow, groupname, pageable);
                    queryFlag = true;
                } else {
                    pageUcAlarmas = ucAlarmactiveRepository.findAlarmpriorityByAlmtAndPage(Integer.parseInt(alarmpriority), yearNow, monthNow, dateNow, pageable);
                    queryFlag = true;
                }
            }
            ucAlarmas = pageUcAlarmas.getContent();
            //查询是否存在设备通讯故障的报警    20140306
            String telnet = "N";
            for (UcAlarmactive ucAlarmactive : ucAlarmas) {
                int grade = ucAlarmactive.getAlmpriority();
                if (grade == 4) {
                    telnet = "Y";
                    break;
                }
            }
            map.put("alarmPage", pageUcAlarmas);
//            list.add(0, ucAlarmas);//列表数据
//            list.add(1, pageUcAlarmas.getTotalPages());//总页数
//            list.add(2, telnet);//是否存在设备故障的报警
            map.put("telnet", telnet);
            Page<UcAlarmactive> ucAlarmactiveResult = pageUcAlarmas;
            //可以减少一次查询
            if (queryFlag) {
                pageable = new PageRequest(pageNumber - 1, 2, Sort.Direction.DESC, "almt");
                ucAlarmactiveResult = ucAlarmactiveRepository.findAlarmpriorityByAlmtAndPage(yearNow, monthNow, dateNow, pageable);
            }
            //提供新的报警的详细信息
            UcAlarmactive ucAlarmactive = new UcAlarmactive();
            if (ucAlarmactiveResult.getContent().size() > 0) {
                //取第一个消防系统报警，前台弹框报警
                UcAlarmactive ucAlarmactive_xf = ucAlarmactiveResult.getContent().get(0);
                Date date = ucAlarmactive_xf.getAlmt();
                DateTime dt = new DateTime(date);
                long hour = dt.getHourOfDay();
                long min = dt.getMinuteOfHour();
                long sec = dt.getSecondOfMinute();
                //前台弹窗报警，10秒内的
                if (ucAlarmactive_xf.getGroupname().equals("消防系统") && hour == hourNow && min == minNow && (secNow - sec) <= 10) {
                    ucAlarmactive = ucAlarmactive_xf;
                }
            }
            map.put("alarmAlert", ucAlarmactive);

        } catch (Exception e) {
            logger.error("------query alarm----------", e);
        }
        return map;
    }
}
