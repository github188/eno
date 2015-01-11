package com.energicube.eno.monitor.web;


import com.energicube.eno.common.Config;
import com.energicube.eno.common.PatternConst;
import com.energicube.eno.common.dto.PfeObjectDTO;
import com.energicube.eno.monitor.model.UcPassengerFlowDay;
import com.energicube.eno.monitor.model.UcPassengerFlowDetail;
import com.energicube.eno.monitor.service.OtherSystemService;
import com.energicube.eno.monitor.service.PassengerFlowService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by EnergyUser on 14-1-20.
 * 客流系统
 */
@Controller
public class PassengerFlowController extends BaseController {

	private final Log logger = LogFactory.getLog(getClass());

    private Config config = new Config();

    @Autowired
    OtherSystemService otherSystemService;
    @Autowired
    PassengerFlowService passengerFlowService;

    /**
     * 查询所有店铺的排名
     *
     * @param model
     * @param httpServletRequest
     * @return 排名的列表
     */
    @RequestMapping(value = "/pfe/getAllShopOrder")
    @ResponseBody
    public Object getAllShopOrder(Model model, HttpServletRequest httpServletRequest) {
        try {
            String location = httpServletRequest.getParameter("location"); // 楼层
            String hundredText = httpServletRequest.getParameter("hundredText"); // 对应楼层百货名称
            String startTime = getTodayStartTime();
            //今天某层店铺的实时客流累计
            String brand = config.getProps().getProperty("pfe.brand");
            List<PfeObjectDTO> pfeObjectDTOList=null;
            if(PatternConst.PFE_HN.equals(brand)){
                String allShopType = config.getProps().getProperty("pfe.allShopType");
                pfeObjectDTOList = otherSystemService.findPassengerFlowAllShopHnRealTime(allShopType);
            }else {
                pfeObjectDTOList = otherSystemService.findAllShopPassengerFlow(location, startTime);
            }
            DateTime temp = null;
            temp = DateTime.parse(startTime, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).plusDays(-1);
            String yesterday = temp.toString("yyyy-MM-dd ") + config.getProps().getProperty("pfe.startHour") + ":00:00";
            //昨天同时刻的客流累计值
            List<PfeObjectDTO> pfeObjectDTOListYesterday = otherSystemService.findAllShopOrder(location, hundredText, yesterday, startTime);

            if (pfeObjectDTOListYesterday != null && pfeObjectDTOListYesterday.size() > 0 && pfeObjectDTOList != null && pfeObjectDTOList.size() > 0) {
                logger.debug("-----size-----" + pfeObjectDTOList.size() + "====" + pfeObjectDTOListYesterday.size());

                //两个时间点的数据比较进行升降的状态的比较
                for (PfeObjectDTO pfeObjectDTO : pfeObjectDTOList) {

                    for (PfeObjectDTO objectDTO : pfeObjectDTOListYesterday) {

                        if (pfeObjectDTO.getId().equals(objectDTO.getId())) {
                            long cur = pfeObjectDTO.getInCount();
                            long preCount = objectDTO.getInCount();
                            logger.debug("-----compare-----" + cur + "====" + preCount);
                            if (cur > preCount) {
                                pfeObjectDTO.setStatus("1"); //上升
                            } else {
                                pfeObjectDTO.setStatus("0"); //下降
                            }
                            break;
                        }
                    }
                }
            }
            //排序
            orderObjectDesc(pfeObjectDTOList);

            return pfeObjectDTOList;
        } catch (Exception e) {
            logger.error("-----------", e);
        }
        return null;
    }

    /**
     * 查询所有店铺的场内人数排名
     * 改为直接去取子系统的的接口数据。
     * @param model
     * @param httpServletRequest
     * @return 排名的列表
     */
    @RequestMapping(value = "/pfe/getAllShopInsideOrder")
    @ResponseBody
    public Object getAllShopInsideOrder(Model model, HttpServletRequest httpServletRequest) {
        try {
            String location = httpServletRequest.getParameter("location"); // 楼层
            // 今天某层店铺的实时客流累计
            List<PfeObjectDTO> pfeObjectDTOList=new ArrayList<PfeObjectDTO>();
            String brand = config.getProps().getProperty("pfe.brand");
            String allShopType = config.getProps().getProperty("pfe.allShopType");
            if(PatternConst.PFE_HN.equals(brand)){
                pfeObjectDTOList=otherSystemService.findPassengerFlowAllShopHnRealTime(allShopType);
            }else {
                pfeObjectDTOList = otherSystemService.findAllShopPassengerFlow(location, getTodayStartTime());
            }
            // 排序
            orderInsidePersonDesc(pfeObjectDTOList);
            return pfeObjectDTOList;

        } catch (Exception e) {
            logger.error("-----------", e);
        }
        return null;
    }

    /**
     * 查询单个店铺当天的客流数据
     *
     * @param model
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/pfe/getShopPassengerFlow")
    @ResponseBody
    public Object getShopPassengerFlow(Model model, HttpServletRequest httpServletRequest) {
        try {
            String shopName = httpServletRequest.getParameter("shopName");
            String queryDate = httpServletRequest.getParameter("queryDate");

            if (queryDate == null || "".equals(queryDate)) {
                queryDate = getTodayStartTime();
            }
            DateTime startDate = DateTime.parse(queryDate, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
            DateTime endDate = startDate.plusDays(1);
            logger.info("------------getShopPassengerFlow---------shopName--" + shopName);
//            List<PfeObjectDTO> pfeObjectDTOList = otherSystemService.findShopPassengerFlow(shopName, queryDate);
            List<UcPassengerFlowDetail> ucPassengerFlowDetails = passengerFlowService.findFlowByDay(shopName, startDate, endDate);
            List<PfeObjectDTO> pfeObjectDTOList = null;
            if (ucPassengerFlowDetails != null) {
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                pfeObjectDTOList = new ArrayList<PfeObjectDTO>(ucPassengerFlowDetails.size());
                for (UcPassengerFlowDetail ucPassengerFlowDetail : ucPassengerFlowDetails) {
                    PfeObjectDTO pfeObjectDTO = new PfeObjectDTO();
                    pfeObjectDTO.setInCount(ucPassengerFlowDetail.getInCount());
                    pfeObjectDTO.setCountTime(simpleDateFormat.format(ucPassengerFlowDetail.getCreateDate()));
                    pfeObjectDTO.setBlockName(ucPassengerFlowDetail.getShopName());
                    pfeObjectDTO.setOutCount(ucPassengerFlowDetail.getOutCount());
                    pfeObjectDTO.setInsidePerson(ucPassengerFlowDetail.getInCount() - ucPassengerFlowDetail.getOutCount());
                    pfeObjectDTO.setId(ucPassengerFlowDetail.getShopCode());
                    pfeObjectDTOList.add(pfeObjectDTO);
                }
            }
            return pfeObjectDTOList;
        } catch (Exception e) {
            logger.error("-----------", e);
        }
        return null;
    }

    /**
     * 查询单个店铺当天的客流数据和昨天的数据
     *
     * @param model
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/pfe/getShopPassengerFlowCompare")
    @ResponseBody
    public Object getShopPassengerFlowCompare(Model model, HttpServletRequest httpServletRequest) {
        PfeObjectDTO pfeObjectDTO = new PfeObjectDTO();
        try {
            String shopName = httpServletRequest.getParameter("shopName");
            //计算今天的客流
            String startTime = getTodayStartTime();
            List<PfeObjectDTO> pfeObjectDTOList = otherSystemService.findShopTotalPassengerFlowByDay(shopName, startTime, "");
            String curpfCount = "";
            if (pfeObjectDTOList != null && pfeObjectDTOList.size() > 0) {
                curpfCount = pfeObjectDTOList.get(0).getInCount() + "";
            }
            DateTime temp = null;
            temp = DateTime.parse(startTime, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).plusDays(-1);
            String yesterday = temp.toString("yyyy-MM-dd ") + config.getProps().getProperty("pfe.startHour") + ":00:00";
            List<PfeObjectDTO> pfeObjectDTOListYesterday = otherSystemService.findShopTotalPassengerFlowByDay(shopName, yesterday, startTime);

            String yesterdaypf = "";
            if (pfeObjectDTOList != null && pfeObjectDTOListYesterday.size() > 0) {
                yesterdaypf = pfeObjectDTOListYesterday.get(0).getInCount() + "";
            }
            pfeObjectDTO.setTodayTotal(curpfCount);
            pfeObjectDTO.setYesterdayTotal(yesterdaypf);
            return pfeObjectDTO;

        } catch (Exception e) {
            logger.error("-----------", e);
        }
        return pfeObjectDTO;
    }

    /**
     * 得到当前时间的格式 整分钟的且是10的整倍数;
     *
     * @return
     */
    private String getCurrentTime() {
        DateTime dateTime = DateTime.now();
        int x = dateTime.getMinuteOfHour();
        int y = 0;
        String min = "00";
        if (x >= 10) {
            y = x / 10;
            min = y + "0";
        }
        return dateTime.toString("yyyy-MM-dd HH") + ":" + min + ":00";
    }

    /**
     * 得到本日的统计的开始时间;
     * 统计时间从早晨6点开始
     *
     * @return 时间 “yyyy-MM-dd HH:mm:ss”
     */
    private String getTodayStartTime() {
        DateTime dateTime = DateTime.now();
        int hour = dateTime.getHourOfDay();
        String startHour = config.getProps().getProperty("pfe.startHour");
        if (hour < Integer.parseInt(startHour)) {
            DateTime temp = DateTime.now().plusDays(-1);
            return temp.toString("yyyy-MM-dd ") + startHour + ":00:00";
        } else {
            return dateTime.toString("yyyy-MM-dd ") + startHour + ":00:00";
        }
    }

    /**
     * 根据传递的时间计算开始时间
     * @zouzhixiang
     * @date 2014-11-12
     * @param dateTime
     * @return
     */
    private String getYesTodayStartTime(DateTime dateTime) {
        int hour = dateTime.getHourOfDay();
        String startHour = config.getProps().getProperty("pfe.startHour");
        if (hour < Integer.parseInt(startHour)) {
            DateTime temp = dateTime.plusDays(-1);
            return temp.toString("yyyy-MM-dd ") + startHour + ":00:00";
        } else {
            return dateTime.toString("yyyy-MM-dd ") + startHour + ":00:00";
        }
    }

    /**
     * 对列表进行按时间排序
     *
     * @param baseCommands 命令列表
     * @return
     */
    private List<PfeObjectDTO> orderObjectDesc(List<PfeObjectDTO> baseCommands) {
        Collections.sort(baseCommands, new Comparator<PfeObjectDTO>() {
            public int compare(PfeObjectDTO arg0, PfeObjectDTO arg1) {
                long a = arg0.getInCount();
                long b = arg1.getInCount();
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

    /**
     * 对场内人数进行排序
     *
     * @param baseCommands 命令列表
     * @return
     */
    private List<PfeObjectDTO> orderInsidePersonDesc(List<PfeObjectDTO> baseCommands) {
        Collections.sort(baseCommands, new Comparator<PfeObjectDTO>() {
            public int compare(PfeObjectDTO arg0, PfeObjectDTO arg1) {
                long a = arg0.getInsidePerson();
                long b = arg1.getInsidePerson();
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

    /**
     * 查询总客流数据
     *
     * @param model
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/pfe/getTotalPassengerFlow")
    @ResponseBody
    public Object getTotalPassengerFlow(Model model, HttpServletRequest httpServletRequest) {
        PfeObjectDTO pfeObjectDTO = new PfeObjectDTO();
        try {
            String totalId = config.getProps().getProperty("pfe.totalId");
            //计算今天的客流
            //当天客流是从早晨6点到第二天早晨6点
            String startDate = getTodayStartTime();
            PfeObjectDTO todayTotal = otherSystemService.findTotalPassengerFlow(startDate, totalId);
            DateTime sd = DateTime.parse(startDate, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
            PfeObjectDTO yesterdaypf = otherSystemService.findTotalPassengerFlow(sd.plusDays(-1).toString("yyyy-MM-dd HH:mm:ss"), sd.toString("yyyy-MM-dd HH:mm:ss"), totalId);
            if (todayTotal != null) {
                pfeObjectDTO.setTodayTotal(todayTotal.getInCount() + "");
            }
            if (yesterdaypf != null) {
                pfeObjectDTO.setYesterdayTotal(yesterdaypf.getInCount() + "");
            }
            return pfeObjectDTO;
        } catch (Exception e) {
            logger.error("-----------", e);
        }
        return pfeObjectDTO;
    }


    /**
     * 同比昨日的客流数据
     * @zouzhixiang
     * @date 2014-11-12
     * @param model
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/pfe/getCompareYestodayInfo")
    @ResponseBody
    public Map<String, UcPassengerFlowDetail> getCompareYestodayInfo(Model model, HttpServletRequest httpServletRequest) {
        Map<String, UcPassengerFlowDetail> map = new HashMap<String, UcPassengerFlowDetail>();
        try {
            String totalId = config.getProps().getProperty("pfe.allShopType300");

            String startHour = config.getProps().getProperty("pfe.startHour"); // 开始小时数
            String twentyFourHour = config.getProps().getProperty("pfe.twentyFourHour"); // 结束小时数24
            String zeroHour = config.getProps().getProperty("pfe.zeroHour"); // 开始小时数0

            // 计算到今天当前时刻的客流， 当天客流是从早晨6点到第二天早晨6点
            String startDate = getTodayStartTime();
            DateTime todayStart = DateTime.parse(startDate, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
            DateTime todayEnd = DateTime.now();

            // 计算到昨天当前时刻的客流， 昨天客流是从早晨6点到第二天早晨6点
            DateTime yestodayEnd = todayEnd.plusDays(-1);
            startDate = getYesTodayStartTime(yestodayEnd);
            DateTime yestodayStart = DateTime.parse(startDate, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));

            logger.debug("todayStart----" + todayStart.toString("yyyy-MM-dd HH:mm:ss"));
            logger.debug("todayEnd----" + todayEnd.toString("yyyy-MM-dd HH:mm:ss"));
            logger.debug("yestodayStart----" + yestodayStart.toString("yyyy-MM-dd HH:mm:ss"));
            logger.debug("yestodayEnd----" + yestodayEnd.toString("yyyy-MM-dd HH:mm:ss"));
            map = passengerFlowService.findTotalPassengerFlowBetweenTime(totalId, totalId, yestodayStart, yestodayEnd, todayStart, todayEnd, Integer.parseInt(startHour), Integer.parseInt(twentyFourHour), Integer.parseInt(zeroHour));
        } catch (Exception e) {
            logger.error("-----------", e);
        }
        return map;
    }

    /**
     * 查询总客流每小时数据
     *
     * @param model
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/pfe/getTotalPassengerFlowHour")
    @ResponseBody
    public Object getTotalPassengerFlowHour(Model model, HttpServletRequest httpServletRequest) {

        try {
            DateTime startDate = DateTime.parse(getTodayStartTime(), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
            DateTime endDate = startDate.plusDays(1);
            String allShopType = config.getProps().getProperty("pfe.allShopType300");
            List<PfeObjectDTO> pfeObjectDTOList = otherSystemService.findTotalPassengerFlowDay(allShopType, allShopType, startDate, endDate);
            pfeObjectDTOList=orderTimeInPerson(pfeObjectDTOList);

            String brand = config.getProps().getProperty("pfe.brand");
            if(PatternConst.PFE_HN.equals(brand)) {
                List<PfeObjectDTO> list = otherSystemService.findPassengerFlowAllShopHnRealTime(allShopType);
                if (list!=null && list.size()>0){
                    PfeObjectDTO pfeObjectDTO=list.get(0);
                    pfeObjectDTO.setStatus(PatternConst.PFE_HN);
                    pfeObjectDTOList.add(pfeObjectDTO);
                }

            }
            return pfeObjectDTOList;
        } catch (Exception e) {
            logger.error("-----------", e);
        }
        return null;
    }

    /**
     * 对场内人数进行排序
     *
     * @param baseCommands 命令列表
     * @return
     */
    private List<PfeObjectDTO> orderTimeInPerson(List<PfeObjectDTO> baseCommands) {
        Collections.sort(baseCommands, new Comparator<PfeObjectDTO>() {
            public int compare(PfeObjectDTO arg0, PfeObjectDTO arg1) {
                long a = DateTime.parse(arg0.getCountTime(),DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).getMillis();
                long b = DateTime.parse(arg1.getCountTime(),DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).getMillis();
                if (a < b) {
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

    /**
     * 查询总场内人数
     *
     * @param model
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/pfe/getTotalInsiderPerson")
    @ResponseBody
    public Object getTotalInsiderPerson(Model model, HttpServletRequest httpServletRequest) {
        PfeObjectDTO pfeObjectDTO = new PfeObjectDTO();
        try {
            String totalId = config.getProps().getProperty("pfe.totalId");
            // 当天客流是从早晨6点到第二天早晨6点
            //计算今天的客流
            String todayDate = getTodayStartTime();
            PfeObjectDTO todayTotal = otherSystemService.findTotalPassengerFlow(todayDate, totalId);

            DateTime dateTime = DateTime.parse(todayDate, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
            String yesterday = dateTime.plusDays(-1).toString("yyyy-MM-dd HH:mm:ss");
            PfeObjectDTO yesterdaypf = otherSystemService.findTotalPassengerFlow(yesterday, todayDate, totalId);

            pfeObjectDTO.setTodayTotal(todayTotal.getInsidePerson() + "");
            pfeObjectDTO.setYesterdayTotal(yesterdaypf.getInsidePerson() + "");
            return pfeObjectDTO;
        } catch (Exception e) {
            logger.error("-----------", e);
        }
        return pfeObjectDTO;
    }

    /**
     * 查询场内人数每小时数据
     *
     * @param model
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/pfe/getTotalInsiderPersonHour")
    @ResponseBody
    public Object getTotalInsiderPersonHour(Model model, HttpServletRequest httpServletRequest) {
        logger.info("----------------getTotalInsiderPersonHour---------------");
        try {
//            String startDate = httpServletRequest.getParameter("startDate");
//            String totalId = config.getProps().getProperty("pfe.totalId");
            DateTime startDate = DateTime.parse(getTodayStartTime(), DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
            DateTime endDate = startDate.plusDays(1);
//            DateTime dateTime = DateTime.parse(startDate, DateTimeFormat.forPattern("yyyy-MM-dd"));
//            List<PfeObjectDTO> pfeObjectDTOList = otherSystemService.findTotalPassengerFlowDay(dateTime.toString("yyyy"), dateTime.toString("MM"), dateTime.toString("dd"), totalId);
            String allShopType = config.getProps().getProperty("pfe.allShopType300");
            List list = null;
            List<UcPassengerFlowDetail> ucPassengerFlowDetails = passengerFlowService.findTotalPassengerFlow(allShopType, allShopType, startDate, endDate);
            if (ucPassengerFlowDetails != null) {
                list = new ArrayList(ucPassengerFlowDetails.size());
                int currenPersonNum = 0;//当前场内人数
                for (UcPassengerFlowDetail ucPassengerFlowDetail : ucPassengerFlowDetails) {
                    currenPersonNum += ucPassengerFlowDetail.getInCount() - ucPassengerFlowDetail.getOutCount();
                    logger.info("---------------insidePerson------" + currenPersonNum + "--------时间----" + ucPassengerFlowDetail.getDateHour() + ":00");
                    PfeObjectDTO pfeObjectDTO = new PfeObjectDTO();
                    pfeObjectDTO.setInCount(ucPassengerFlowDetail.getInCount());
                    pfeObjectDTO.setBlockName(ucPassengerFlowDetail.getShopName());
                    pfeObjectDTO.setOutCount(ucPassengerFlowDetail.getOutCount());
                    pfeObjectDTO.setInsidePerson(currenPersonNum);
                    pfeObjectDTO.setId(ucPassengerFlowDetail.getShopCode());
                    pfeObjectDTO.setCountTime(ucPassengerFlowDetail.getDateHour() + ":00");
                    list.add(pfeObjectDTO);
                }
            }
            logger.info("-------list-------" + (list == null ? 0 : list.size()));
            return list;
        } catch (Exception e) {
            logger.error("-----------", e);
        }
        return null;
    }

    /**
     * 查询各店的每月的总客流数据
     *
     * @param model
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/pfe/getShopTotalPassengerFlowByMonth")
    @ResponseBody
    public Object getShopTotalPassengerFlowByMonth(Model model, HttpServletRequest httpServletRequest) {
        try {
            String shopName = httpServletRequest.getParameter("shopName");
            DateTime dt = DateTime.now();
            if (dt.getHourOfDay() < 6) {
                dt = dt.plusDays(-1);
            }
            List<PfeObjectDTO> pfeObjectDTOList = otherSystemService.findShopTotalPassengerFlowByMonth(shopName, dt.getYear() + "");

            return pfeObjectDTOList;
        } catch (Exception e) {
            logger.error("-----------", e);
        }
        return null;
    }

    /**
     * 查询各店的每天的总客流数据
     *
     * @param model
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/pfe/getShopTotalPassengerFlowByDay")
    @ResponseBody
    public Object getShopTotalPassengerFlowByDay(Model model, HttpServletRequest httpServletRequest) {
        try {
            String shopName = httpServletRequest.getParameter("shopName");
            String startDate = httpServletRequest.getParameter("startDate");
            String t = getCurrentTime();
            DateTime dateTime = DateTime.parse(t, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")).plusDays(-1);
            if (startDate != null && !"".equals(startDate)) {
                String d = startDate + " " + dateTime.toString("HH:mm:ss");
                dateTime = DateTime.parse(d, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
            }
            List<PfeObjectDTO> pfeObjectDTOList = otherSystemService.findShopTotalPassengerFlowByDay(shopName, dateTime.toString("yyyy-MM-dd") + " 00:00:00", dateTime.toString("yyyy-MM-dd HH:mm:ss"));
            return pfeObjectDTOList;
        } catch (Exception e) {
            logger.error("-----------", e);
        }
        return null;
    }

    /**
     * 查询各店的当前客流数据
     *
     * @param model
     * @param pageNumber
     * @param pageSize
     * @param startDate
     * @return
     */
    @RequestMapping(value = "/pfe/getAllShopPassengerFlow")
    @ResponseBody
    public Object getAllShopPassengerFlow(Model model,
                                          @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                          @RequestParam(value = "pageSize", required = false, defaultValue = "20") int pageSize,
                                          @RequestParam(value = "startDate", required = false) String startDate) {
        DateTime dt = DateTime.now();
        if (StringUtils.isNotEmpty(startDate)) {
            dt = DateTime.parse(startDate, DateTimeFormat.forPattern("yyyy-MM-dd"));
        }
        logger.info("-------getAllShopPassengerFlow-----params---{pageNumber:" + pageNumber + ",pageSize:" + pageSize + ",startDate:" + startDate + "}");
        Pageable pageble = new PageRequest(pageNumber - 1, pageSize, Sort.Direction.DESC, "inCount");
        try {
            Page<UcPassengerFlowDay> pfeObjectDTOList = passengerFlowService.findAllShopPassengerFlow(config.getProps().getProperty("pfe.allShopType300"), config.getProps().getProperty("pfe.allShopType500"), config.getProps().getProperty("pfe.allShopType600"), dt, pageble);
//                    otherSystemService.findShopTotalPassengerFlowByDay(shopName, dateTime.toString("yyyy-MM-dd") + " 00:00:00", dateTime.toString("yyyy-MM-dd HH:mm:ss"));
            List<PfeObjectDTO> list = null;
            if (pfeObjectDTOList != null) {
                List items = pfeObjectDTOList.getContent();
                list = new ArrayList(items.size());
                for (Object obj : items) {
                    UcPassengerFlowDay ucPassengerFlowDay = (UcPassengerFlowDay) obj;
                    PfeObjectDTO pfeObjectDTO = new PfeObjectDTO();
                    pfeObjectDTO.setBlockName(ucPassengerFlowDay.getShopName());
                    pfeObjectDTO.setInCount(ucPassengerFlowDay.getInCount());
                    pfeObjectDTO.setOutCount(ucPassengerFlowDay.getOutCount());
                    pfeObjectDTO.setCountTime(ucPassengerFlowDay.getDateYear() + "-" + ucPassengerFlowDay.getDateMonth() + "-" + ucPassengerFlowDay.getDateDay());
                    list.add(pfeObjectDTO);
                }
            }
            com.energicube.eno.common.page.Page page = new com.energicube.eno.common.page.Page();
            page.setPageItems(list);
            page.setPageNumber(pageNumber);
            page.setPagesAvailable(pfeObjectDTOList.getTotalPages());
            return page;
        } catch (Exception e) {
            logger.error("-----------", e);
        }
        return null;
    }

    /**
     * 查询客流探头信息
     *
     * @return
     */
    @RequestMapping(value = "/pfe/findCameraList")
    @ResponseBody
    public Object getAllShopPassengerFlow() {
        return otherSystemService.findCameraState();
    }

    /**
     * 新版客流查询工作日和节假日日均客流
     * @author zouzhixiang
     * @date 2014-11-04
     *
     * @return
     */
    @RequestMapping(value = "/pfe/findWorkDayAndHoliDayAveragePassengerOfDay")
    @ResponseBody
    public Object findWorkDayAndHoliDayAveragePassengerOfDay() {
        Map<String, Object> result = new HashMap<String, Object>();
        try{
            result = otherSystemService.findWorkDayAndHoliDayAveragePassengerOfDay();
        } catch (Exception e) {
            logger.error("-----------", e);
        }
        return result;
    }
}