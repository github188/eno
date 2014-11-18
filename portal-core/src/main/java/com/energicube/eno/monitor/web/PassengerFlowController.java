package com.energicube.eno.monitor.web;

import com.energicube.eno.common.Config;
import com.energicube.eno.common.dto.PfeObjectDTO;
import com.energicube.eno.monitor.service.OtherSystemService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by EnergyUser on 14-1-20.
 * 客流系统
 */
@Controller
public class PassengerFlowController extends BaseController {

    private static Log logger = LogFactory.getLog(PassengerFlowController.class);

    private Config config = new Config();

    @Autowired
    OtherSystemService otherSystemService;

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

            //今天某层店铺的实时客流累计
            List<PfeObjectDTO> pfeObjectDTOList = otherSystemService.findAllShopOrder(location, hundredText, getTodayStartTime(), "");

            int hour = DateTime.now().getHourOfDay();
            DateTime temp = null;
            if (hour < 6) {
                temp = DateTime.now().plusDays(-2);
            } else {
                temp = DateTime.now().plusDays(-1);
            }
            String yesterday = temp.toString("yyyy-MM-dd ") + "06:00:00";
            //昨天同时刻的客流累计值
            List<PfeObjectDTO> pfeObjectDTOListYesterday = otherSystemService.findAllShopOrder(location, hundredText, yesterday, DateTime.now().plusDays(-1).toString("yyyy-MM-dd HH:mm:ss"));

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
     *
     * @param model
     * @param httpServletRequest
     * @return 排名的列表
     */
    @RequestMapping(value = "/pfe/getAllShopInsideOrder")
    @ResponseBody
    public Object getAllShopInsideOrder(Model model, HttpServletRequest httpServletRequest) {
        try {
            String location = httpServletRequest.getParameter("location"); // 楼层
            String hundredText = httpServletRequest.getParameter("hundredText"); // 对应楼层百货名称
            // 今天某层店铺的实时客流累计
            List<PfeObjectDTO> pfeObjectDTOList = otherSystemService.findAllShopInsidePerson(location, hundredText, getTodayStartTime(), "");

            String curTime = getCurrentTime();
            //客流的时间在20多分钟的时候，只有10分的数据，没有20分的数据所以以10分的数据为最近的实时数据
            DateTime dateTime = DateTime.parse(curTime, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss"));
            dateTime = dateTime.plusMinutes(-10);

            String pre = dateTime.toString("yyyy-MM-dd HH:mm:ss");
            //某层当前时间点的实时客流数据
            List<PfeObjectDTO> pfeObjects = otherSystemService.findAllShopPassengerFlow(location, pre);

            //某层上一时间点的实时客流数据

            dateTime = dateTime.plusMinutes(-10);
            pre = dateTime.toString("yyyy-MM-dd HH:mm:ss");
            logger.info("-----curTime-----" + curTime + "====" + pre);
            List<PfeObjectDTO> preCounts = otherSystemService.findAllShopPassengerFlow(location, pre);

            logger.debug("-----size-----" + pfeObjects.size() + "====" + preCounts.size());
            //两个时间点的数据比较进行升降的状态的比较
            for (PfeObjectDTO pfeObjectDTO : pfeObjects) {

                for (PfeObjectDTO preObject : preCounts) {
                    if (preObject.getId().equals(pfeObjectDTO.getId())) {
                        long cur = pfeObjectDTO.getInCount() - pfeObjectDTO.getOutCount();
                        long preCount = preObject.getInCount() - preObject.getOutCount();
                        if (cur < 0) {
                            cur = 0;
                        }
                        if (preCount < 0) {
                            preCount = 0;
                        }
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
            //给总排名添加升降状态
            for (PfeObjectDTO pfeObjectDTO : pfeObjectDTOList) {
                for (PfeObjectDTO objectDTO : pfeObjects) {
                    if (pfeObjectDTO.getId().equals(objectDTO.getId())) {
                        logger.debug("-----status-----" + objectDTO.getStatus() + "====" + objectDTO.getId());
                        pfeObjectDTO.setStatus(objectDTO.getStatus());
                        break;
                    }
                }
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
            List<PfeObjectDTO> pfeObjectDTOList = otherSystemService.findShopPassengerFlow(shopName, queryDate);

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
            int hour = DateTime.now().getHourOfDay();
            List<PfeObjectDTO> pfeObjectDTOList = otherSystemService.findShopTotalPassengerFlowByDay(shopName, getTodayStartTime(), "");
            String curpfCount = "";
            if (pfeObjectDTOList != null && pfeObjectDTOList.size() > 0) {
                curpfCount = pfeObjectDTOList.get(0).getInCount() + "";
            }
            DateTime temp = null;
            if (hour < 6) {
                temp = DateTime.now().plusDays(-2);
            } else {
                temp = DateTime.now().plusDays(-1);
            }
            String yesterday = temp.toString("yyyy-MM-dd ") + "06:00:00";
            List<PfeObjectDTO> pfeObjectDTOListYesterday = otherSystemService.findShopTotalPassengerFlowByDay(shopName, yesterday, DateTime.now().plusDays(-1).toString("yyyy-MM-dd HH:mm:ss"));

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
        if (hour < 6) {
            DateTime temp = DateTime.now().plusDays(-1);
            return temp.toString("yyyy-MM-dd ") + "06:00:00";
        } else {
            return dateTime.toString("yyyy-MM-dd ") + "06:00:00";
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
            //todo 当天客流是从早晨6点到第二天早晨6点
            //计算今天的客流
            int hour = DateTime.now().getHourOfDay();
            String curpfCount = otherSystemService.findTotalPassengerFlow(getTodayStartTime(), "", totalId);
            DateTime temp = null;
            if (hour < 6) {
                temp = DateTime.now().plusDays(-2);
            } else {
                temp = DateTime.now().plusDays(-1);
            }
            String yesterday = temp.toString("yyyy-MM-dd ") + "06:00:00";
            String yesterdaypf = otherSystemService.findTotalPassengerFlow(yesterday, DateTime.now().plusDays(-1).toString("yyyy-MM-dd HH:mm:ss"), totalId);

            pfeObjectDTO.setTodayTotal(curpfCount);
            pfeObjectDTO.setYesterdayTotal(yesterdaypf);
            return pfeObjectDTO;
        } catch (Exception e) {
            logger.error("-----------", e);
        }
        return pfeObjectDTO;
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
            String startDate = httpServletRequest.getParameter("startDate");
            String totalId = config.getProps().getProperty("pfe.totalId");
            if (startDate == null || "".equals(startDate)) {
                startDate = DateTime.now().toString("yyyy-MM-dd");
            }
            DateTime dateTime = DateTime.parse(startDate, DateTimeFormat.forPattern("yyyy-MM-dd"));
            List<PfeObjectDTO> pfeObjectDTOList = otherSystemService.findTotalPassengerFlowDay(dateTime.toString("yyyy"), dateTime.toString("MM"), dateTime.toString("dd"), totalId);

            return pfeObjectDTOList;
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

            List<PfeObjectDTO> pfeObjectDTOList = otherSystemService.findShopTotalPassengerFlowByMonth(shopName, DateTime.now().toString("yyyy"));

            return pfeObjectDTOList;
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

            List<PfeObjectDTO> pfeObjectDTOList = otherSystemService.findShopTotalPassengerFlowByDay(shopName, dateTime.toString("yyyy-MM-dd"), dateTime.toString("yyyy-MM-dd HH:mm:ss"));

            return pfeObjectDTOList;
        } catch (Exception e) {
            logger.error("-----------", e);
        }
        return null;
    }
}
