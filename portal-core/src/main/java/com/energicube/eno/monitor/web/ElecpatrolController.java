package com.energicube.eno.monitor.web;

import com.energicube.eno.common.Config;
import com.energicube.eno.common.PatternConst;
import com.energicube.eno.common.page.Page;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class ElecpatrolController extends BaseController {

    private static Log logger = LogFactory.getLog(ElecpatrolController.class);

    private Config config = new Config();

    @Autowired
    com.energicube.eno.monitor.service.OtherSystemService otherSystemService;

    @RequestMapping(value = "/elecpatrol/elecpatrolList", method = RequestMethod.GET)
    public String initelecpatrolList(Model model) {
        return "elecpatrol/elecpatrolList";
    }

    @RequestMapping(value = "/elecpatrol/entranceList", method = RequestMethod.GET)
    public String initentranceList(Model model, HttpServletRequest request) {

        return "elecpatrol/entranceList";
    }

    /**
     * 查询门禁系统信息
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping(value = "/elecpatrol/entranceListById", method = RequestMethod.GET)
    public String entranceListById(HttpServletRequest request, HttpServletResponse response,
                                   Model model) {
        String doorId = request.getParameter("doorId");
        String machine = "";
        String subpoint = "";
        if (doorId != null && !"".equals(doorId)) {
            int a = doorId.indexOf(",");
            if (a > 0) {
                machine = doorId.substring(0, a);
                subpoint = doorId.substring(a + 1);
                request.getSession().setAttribute("MACHINE", machine);
                request.getSession().setAttribute("SUBPOINT", subpoint);

            } else {
                machine = doorId;
                subpoint = "";
                request.getSession().setAttribute("MACHINE", doorId);
                request.getSession().setAttribute("SUBPOINT", "");
            }
            Page page = otherSystemService.getSasacObjects(10, 1, machine, subpoint, PatternConst.SASAC_EVENT_TYPE_GENERAL);
            model.addAttribute("SASACLIST", page);
        }
        return "elecpatrol/entranceList";
    }

    @RequestMapping(value = "/elecpatrol/parkmList", method = RequestMethod.GET)
    public String parkmList(HttpServletRequest request, Model model) {
        return "elecpatrol/parkmList";
    }

    @RequestMapping(value = "/elecpatrol/getPatrolList")
    @ResponseBody
    public Object getSubPatrolList(@RequestParam(value = "pageNum", required = false, defaultValue = "1") int pageNum,
                                   @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                   @RequestParam(value = "checkDate", required = false) String checkDate, Model model, HttpServletRequest httpServletRequest) {
        try {
//			logger.info("----pageNum=" + pageNum +"----pageSize=" + pageSize + "------checkDate=" + checkDate);
            Page page = otherSystemService.getSubPatrols(pageSize, pageNum, checkDate);
            return page;
        } catch (Exception e) {
            logger.error("getSubPatrolList ------" + e);
        }
        return null;
    }

    /**
     * 查询巡更列表(老版 --暂留)
     *
     * @param pageNumber         页号
     * @param pageSize           每页数量
     * @param shift              班次
     * @param road               路线
     * @param checkDate          检查日期
     * @param model
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/other/ep/getEpList")
    @ResponseBody
    public Object getEpList(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                            @RequestParam(value = "pageSize", required = false, defaultValue = "50") int pageSize,
                            @RequestParam(value = "shift", required = false) String shift,
                            @RequestParam(value = "road", required = false) String road,
                            @RequestParam(value = "checkDate", required = false) String checkDate, Model model, HttpServletRequest httpServletRequest) {
        try {
            logger.debug("----" + shift + "--" + road + "--" + checkDate);
            String areaId = config.getProps().getProperty("ep.areaId");

            Page page = otherSystemService.getEpObjects(pageSize, pageNumber, areaId, shift, road, checkDate);
            return page;
        } catch (Exception e) {
            logger.error("-----------", e);
        }
        return null;
    }

    /**
     * 查询停车信息
     *
     * @param pageNumber         页号
     * @param pageSize           每页数量
     * @param startInDate        进场开始时间
     * @param endInDate          进场结束时间
     * @param startOutDate       出场开始时间
     * @param endOutDate         出场结束时间
     * @param goName             出场口
     * @param comeName           进场口
     * @param carNum             车牌号
     * @param model
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/other/parkm/getParkmList")
    @ResponseBody
    public Object getParkmList(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                               @RequestParam(value = "pageSize", required = false, defaultValue = "50") int pageSize,
                               @RequestParam(value = "startInDate", required = false) String startInDate,
                               @RequestParam(value = "endInDate", required = false) String endInDate,
                               @RequestParam(value = "startOutDate", required = false) String startOutDate,
                               @RequestParam(value = "endOutDate", required = false) String endOutDate,
                               @RequestParam(value = "goName", required = false) String goName,
                               @RequestParam(value = "comeName", required = false) String comeName,
                               @RequestParam(value = "carNum", required = false) String carNum, Model model, HttpServletRequest httpServletRequest) {
        try {
            logger.debug("----" + startInDate + "--" + endInDate + "--" + carNum + "--" + goName + "--" + comeName + "--" + startOutDate + "--" + endOutDate);
            Page page = otherSystemService.getParkmObjects(pageSize, pageNumber, startInDate, endInDate, startOutDate, endOutDate, carNum, goName, comeName);
            return page;
        } catch (Exception e) {
            logger.error("-----------", e);
        }
        return null;
    }

    /**
     * 查询巡更班次
     *
     * @param model
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/other/ep/getShiftList")
    @ResponseBody
    public Object getShiftList(Model model, HttpServletRequest httpServletRequest) {
        try {
            String areaId = config.getProps().getProperty("ep.areaId");
            List page = otherSystemService.getShiftAll(areaId);
            return page;
        } catch (Exception e) {
            logger.error("-----------", e);
        }
        return null;
    }

    /**
     * 查询巡更线路
     *
     * @param model
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/other/ep/getRoadList")
    @ResponseBody
    public Object getRoadList(Model model, HttpServletRequest httpServletRequest) {
        try {
            String areaId = config.getProps().getProperty("ep.areaId");
            List page = otherSystemService.getRoadAll(areaId);
            return page;
        } catch (Exception e) {
            logger.error("-----------", e);
        }
        return null;
    }

    /**
     * 查询门禁信息
     *
     * @param pageNumber         页号
     * @param pageSize           每页数量
     * @param eventType          信息类型
     * @param model
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/other/sasac/getSasacList")
    @ResponseBody
    public Object getSasacList(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                               @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                               @RequestParam(value = "eventType", required = false, defaultValue = "G") String eventType,
                               Model model, HttpServletRequest httpServletRequest) {
        try {

            String machine = (String) httpServletRequest.getSession().getAttribute("MACHINE");
            String subpoint = (String) httpServletRequest.getSession().getAttribute("SUBPOINT");


            logger.debug("----" + pageNumber + "--" + pageSize + "--" + eventType + "====" + machine + "====" + subpoint);
            Page page = otherSystemService.getSasacObjects(pageSize, pageNumber, machine, subpoint, eventType);
            return page;
        } catch (Exception e) {
            logger.error("-----------", e);
        }
        return null;
    }
}
