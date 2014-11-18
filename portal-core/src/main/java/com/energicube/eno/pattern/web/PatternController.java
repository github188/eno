package com.energicube.eno.pattern.web;

import com.energicube.eno.asset.model.Asset;
import com.energicube.eno.asset.model.ClassStructure;
import com.energicube.eno.asset.model.LocHierarchySet;
import com.energicube.eno.calendar.model.UcCalendar;
import com.energicube.eno.calendar.model.UcHoliday;
import com.energicube.eno.calendar.model.UcWeather;
import com.energicube.eno.calendar.service.CalendarService;
import com.energicube.eno.common.ManualProperties;
import com.energicube.eno.common.PatternConst;
import com.energicube.eno.common.SendCommandToSystem;
import com.energicube.eno.common.dto.DeviceCommand;
import com.energicube.eno.common.dto.ItemJSON;
import com.energicube.eno.common.dto.SystemMessage;
import com.energicube.eno.common.model.Message;
import com.energicube.eno.common.model.Tree;
import com.energicube.eno.monitor.model.Event;
import com.energicube.eno.monitor.model.OkcMenu;
import com.energicube.eno.monitor.model.OkcMenuSet;
import com.energicube.eno.monitor.model.UserInfo;
import com.energicube.eno.monitor.service.MenuService;
import com.energicube.eno.monitor.service.OpLogService;
import com.energicube.eno.monitor.web.BaseController;
import com.energicube.eno.pattern.model.*;
import com.energicube.eno.pattern.service.PatternService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.quartz.Scheduler;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-7-11
 * Time: 下午4:19
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class PatternController extends BaseController {

    private static Log logger = LogFactory.getLog(PatternController.class);

    private ManualProperties manualProperties = ManualProperties.getInstance();
    @Autowired
    PatternService patternService;

    @Autowired
    CalendarService calendarService;

    @Resource
    private org.quartz.Scheduler scheduler;

    @Autowired
    OpLogService opLogService;

    public void setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    @Autowired
    private MenuService menuService;

    @ModelAttribute(value = "appFilterMenus")
    public List<OkcMenu> TypeFilterMenu(HttpServletRequest request) {
        //通过请求路径，解析菜单中对应的元素的归属元素
        //当前请求路径，与OkcMenu中的url一致
        String reqPath = request.getRequestURI().substring(request.getContextPath().length());

        String ret = reqPath.replace(".html", "");
        String[] arrPath = ret.split("/");
        //	String currElementVal = arrPath[arrPath.length-1].toUpperCase();
        String currOwnerEle = arrPath[arrPath.length - 2].toUpperCase();

        //List<OkcMenu> filterMenus = menuService.findAppFilterList(currOwnerEle);
        //让菜单保持一致，始终用ASC的菜单
        List<OkcMenu> filterMenus = menuService.findAppFilterList("ASC");

        return filterMenus;
    }

    /**
     * 设置菜单
     *
     * @param model      model
     * @param systemCode 系统编码
     */
    private void setModelAttribute(Model model, String systemCode) {
        //定义子菜单集合
        List<OkcMenuSet> ascChildrenItems = new ArrayList<OkcMenuSet>();  //设备列表
        //子菜单对应的你菜单项
        OkcMenu parentMenuItem = new OkcMenu();
        //定义菜单类型
        String ascMenutype = "APPFILTER|SchemaCfg";

        UcDeviceSystem ucDeviceSystem = patternService.findDeviceSystemByCode(systemCode);
        if (ucDeviceSystem != null) {
            systemCode = ucDeviceSystem.getSpecclass();
        }

        List<UcDeviceSystem> ucDeviceSystemList = patternService.findDeviceSystemAll();
        StringBuilder stringBuilder = new StringBuilder();
        if (ucDeviceSystemList != null) {

            for (UcDeviceSystem deviceSystem : ucDeviceSystemList) {
                stringBuilder.append(deviceSystem.getSystemCode() + ",");
            }
        }
        logger.debug("-----code list------" + stringBuilder.toString());
        model.addAttribute("patternMenu", stringBuilder.toString());
        //获取对应的子菜单集合
        if (StringUtils.hasLength(systemCode)) {
            ascChildrenItems = menuService.findOkcMenuSetList(ascMenutype, systemCode);
            parentMenuItem = menuService.findMenuItem("APPFILTER", "SchemaCfg", systemCode);
        }

        //是否存在设备列表
        boolean hasAsc = ascChildrenItems.size() > 0;
        //显示设备列表
        boolean showAsc = ascChildrenItems.size() > 0;

        model.addAttribute("ascChildrenItems", ascChildrenItems);

        model.addAttribute("parentMenuItem", parentMenuItem);
        model.addAttribute("elementvalue", systemCode);

        model.addAttribute("hasAsc", hasAsc);
        model.addAttribute("showAsc", showAsc);


    }

    /**
     * 保存子系统的非时序模式
     *
     * @param model
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/pattern/saveSystemPatternNoTime")
    @ResponseBody
    public Message saveSystemPatternNoTime(Model model, HttpServletRequest httpServletRequest) {

        Message message = new Message();
        try {
            //前天传过来的参数
            String systemId = httpServletRequest.getParameter("systemId");
            String patternId = httpServletRequest.getParameter("patternId");
            String patternName = httpServletRequest.getParameter("patternName");
            String patternDescription = httpServletRequest.getParameter("patternDescription");
            UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();

            //修改模式关联的子系统
            if (patternId != null && !"".equals(patternId)) {
                UcPattern ucPattern = patternService.findUcPatternById(patternId);
                ucPattern.setName(patternName);
                ucPattern.setDescription(patternDescription);
                if (systemId != null && !"".equals(systemId)) {
                    ucPattern.setSystemId(systemId);
                }
                patternService.saveUcPattern(ucPattern);
            } else {
                UcPattern ucPattern = new UcPattern();
                ucPattern.setPatternType(PatternConst.PATTERN_TYPE_SYSTEM);
                ucPattern.setSystemId(systemId);
                ucPattern.setVersion("");
                ucPattern.setCreateDate(new Date());
                ucPattern.setCreateUser(user.getLoginid());
                ucPattern.setIsNew("Y");
                ucPattern.setDefaultPattern(PatternConst.PATTERN_DEFAULT_NO);
                ucPattern.setOrderType(PatternConst.PATTERN_ORDER_TYPE_NOTIME);
                ucPattern.setOrgId("");
                ucPattern.setSiteId("");
                ucPattern.setName(patternName);
                ucPattern.setDescription(patternDescription);
                String id = patternService.saveUcPattern(ucPattern);
                message.setPatternId(id);
            }

            opLogService.saveLogByUserAndMsg(user.getLoginid(), "创建系统触发模式,名称：" + patternName, "", systemId, "PatternController", PatternConst.LOG_TYPE_PATTERN);

            patternService.saveAlarmReport(user.getLoginid() + "在 " + DateTime.now().toString("yyyy-MM-dd HH:mm:ss") + " 创建模式[" + patternName + "]");

            message.setSuccess(true);
            return message;

        } catch (Exception e) {
            logger.error("-----------", e);
        }
        message.setSuccess(false);
        message.setMsg(PatternConst.JSON_FAILURE);
        return message;
    }

    /**
     * 显示全局所有非时序模式
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/pattern/showGlobalPatternNoTime", method = RequestMethod.GET)
    public String showPattern(Model model, HttpServletRequest httpServletRequest) {

        try {
            setModelAttribute(model, "");
            List<UcGlobalPattern> list = patternService.findUcGlobalPatternByAll(PatternConst.PATTERN_ORDER_TYPE_NOTIME);
            for (UcGlobalPattern ucGlobalPattern : list) {
                Set<UcCombinationPattern> patternList = ucGlobalPattern.getUcCombinationPatterns();
                for (UcCombinationPattern ucCombinationPattern : patternList) {
                    ucCombinationPattern.setUcGlobalPattern(null);
                    ucCombinationPattern.setUcPattern(null);
                }
            }

            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(list);
            model.addAttribute("Global_NoTime", json);

            List<UcPattern> listPattern = patternService.findUcPatternByOrderType(PatternConst.PATTERN_ORDER_TYPE_NOTIME);

            List<ItemJSON> itemJSONs = new ArrayList<ItemJSON>();
            List<UcDeviceSystem> ucDeviceSystemList = patternService.findDeviceSystemAll();
            for (UcDeviceSystem ucDeviceSystem : ucDeviceSystemList) {
                ItemJSON itemJSON = new ItemJSON();
                itemJSON.setId(ucDeviceSystem.getId());
                itemJSON.setCode(ucDeviceSystem.getSystemCode());
                itemJSON.setName(ucDeviceSystem.getSystemName());
                List<UcPattern> patternList = new ArrayList<UcPattern>();
                for (UcPattern ucPattern : listPattern) {
//                logger.info("------getSystemId-------"+ucPattern.getSystemId());
//                logger.info("-------------"+ucPattern.getId());
//                logger.info("-------------"+ucPattern.getName());
                    if (ucPattern.getSystemId().equals(ucDeviceSystem.getSystemCode())) {
                        patternList.add(ucPattern);
                    }
                }
                itemJSON.setItem(patternList);
                itemJSONs.add(itemJSON);
            }

            json = objectMapper.writeValueAsString(itemJSONs);
            model.addAttribute("SubSystem_NoTime", json);
        } catch (Exception e) {
            logger.error("showPattern", e);
        }
        return "pattern/globalPatternNoTime";
    }

    /**
     * 显示子系统所有非时序模式
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/pattern/menu/nt/{elementvalue}", method = RequestMethod.GET)
    public String showSubSystemPatternNoTime(@PathVariable("elementvalue") String elementvalue, Model model, HttpServletRequest httpServletRequest) {

        String systemId = elementvalue;
        setModelAttribute(model, elementvalue);
        if (systemId == null || "".equals(systemId)) {
            systemId = "HVAC";
        }
        try {
            List<UcPattern> listSystemTime = patternService.findUcPatternBySystem(systemId, PatternConst.PATTERN_TYPE_SYSTEM, PatternConst.PATTERN_ORDER_TYPE_NOTIME);
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(listSystemTime);

            model.addAttribute("Pattern_System_Time", json);

            List<UcPattern> listCustomTime = patternService.findUcPatternBySystem(systemId, PatternConst.PATTERN_TYPE_CUSTOM, PatternConst.PATTERN_ORDER_TYPE_NOTIME);

            json = objectMapper.writeValueAsString(listCustomTime);
            model.addAttribute("Pattern_Custom_Time", json);

            model.addAttribute("systemCode", systemId);
        } catch (Exception e) {
            logger.error("showSubSystemPatternNoTime", e);
        }
        return "pattern/subSystemNoTime";
    }

    /**
     * 显示子系统所有时序模式
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/pattern/menu/{elementvalue}", method = RequestMethod.GET)
    public String showSystemPatternTime(@PathVariable("elementvalue") String elementvalue, Model model, HttpServletRequest httpServletRequest) {

        try {
            String systemCode = elementvalue;
            setModelAttribute(model, elementvalue);

            if (systemCode == null || "".equals(systemCode) || systemCode.equals("HVAC")) {
                systemCode = "CoolingSource";
            }
            List<UcSysFactor> listG = patternService.findSystemFactor(systemCode);

            List objList = new ArrayList();
            for (UcSysFactor ucSysFactor : listG) {
                String id = ucSysFactor.getUcFactor().getId();
                List<UcFactor> ucFactors = patternService.findSubFactors(id);

                for (UcFactor ucFactor : ucFactors) {
                    ucFactor.setUcSysFactors(null);
                    ucFactor.setUcPatternFactors(null);
                }

                ItemJSON itemJSON = new ItemJSON();
                itemJSON.setCode(ucSysFactor.getId());
                itemJSON.setName(ucSysFactor.getDescription());
                itemJSON.setItem(ucFactors);

                objList.add(itemJSON);
            }

            ObjectMapper objectMapper = new ObjectMapper();

            String json = objectMapper.writeValueAsString(objList);
            model.addAttribute("System_Factor", json);

            List<UcPattern> listSystemTime = patternService.findUcPatternBySystem(systemCode, PatternConst.PATTERN_TYPE_SYSTEM, PatternConst.PATTERN_ORDER_TYPE_TIME);

            String jsonString = objectMapper.writeValueAsString(listSystemTime);

            model.addAttribute("Pattern_System_Time", jsonString);

            List<UcPattern> listCustomTime = patternService.findUcPatternBySystem(systemCode, PatternConst.PATTERN_TYPE_CUSTOM, PatternConst.PATTERN_ORDER_TYPE_TIME);

            String customTime = objectMapper.writeValueAsString(listCustomTime);
            model.addAttribute("Pattern_Custom_Time", customTime);

            model.addAttribute("systemCode", systemCode);
        } catch (Exception e) {
            logger.error("showSystemPatternTime", e);
        }
        return "pattern/pattern";
    }

    /**
     * 显示子系统所有时序模式
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/pattern/showSystemPatternTime", method = RequestMethod.GET)
    public String showSystemPatternTime(Model model, HttpServletRequest httpServletRequest) {
        String systemId = httpServletRequest.getParameter("systemId");
        if (systemId == null || "".equals(systemId)) {
            systemId = "CoolingSource";
        }
        List<UcSysFactor> listG = patternService.findSystemFactor(systemId);

        List objList = new ArrayList();
        for (UcSysFactor ucSysFactor : listG) {
            String id = ucSysFactor.getUcFactor().getId();
            List<UcFactor> ucFactors = patternService.findSubFactors(id);

            for (UcFactor ucFactor : ucFactors) {
                ucFactor.setUcSysFactors(null);
                ucFactor.setUcPatternFactors(null);
            }

            ItemJSON itemJSON = new ItemJSON();
            itemJSON.setCode(ucSysFactor.getId());
            itemJSON.setName(ucSysFactor.getDescription());
            itemJSON.setItem(ucFactors);

            objList.add(itemJSON);
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(objList);

            model.addAttribute("System_Factor", json);

            List<UcPattern> listSystemTime = patternService.findUcPatternBySystem(systemId, PatternConst.PATTERN_TYPE_SYSTEM, PatternConst.PATTERN_ORDER_TYPE_TIME);

            json = objectMapper.writeValueAsString(listSystemTime);
            model.addAttribute("Pattern_System_Time", json);

            List<UcPattern> listCustomTime = patternService.findUcPatternBySystem(systemId, PatternConst.PATTERN_TYPE_CUSTOM, PatternConst.PATTERN_ORDER_TYPE_TIME);

            json = objectMapper.writeValueAsString(listCustomTime);
            model.addAttribute("Pattern_Custom_Time", json);

            model.addAttribute("systemId", systemId);
            model.addAttribute("systemCode", systemId);
        } catch (Exception e) {
            logger.error("showSystemPatternTime", e);
        }
        return "pattern/pattern";
    }


    /**
     * 保存全局非时序模式
     *
     * @return
     */
    @RequestMapping(value = "/pattern/saveGlobalPatternNoTime")
    @ResponseBody
    public Message saveGlobalPatternNoTime(Model model, HttpServletRequest httpServletRequest) {

        Message message = new Message();
        try {
            String systemIds = httpServletRequest.getParameter("systemList");
            String patternId = httpServletRequest.getParameter("patternId");
            String patternName = httpServletRequest.getParameter("patternName");
            String patternDescription = httpServletRequest.getParameter("patternDescription");
            UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            Set<UcCombinationPattern> ucCombinationPatterns = new HashSet<UcCombinationPattern>();
            if (patternId != null && !"".equals(patternId)) {
                UcGlobalPattern ucGlobalPattern = patternService.findUcGlobalPatternById(patternId);
                ucGlobalPattern.setPatternDescription(patternDescription);
                ucGlobalPattern.setPatternName(patternName);
                ucGlobalPattern.setOrderType(PatternConst.PATTERN_ORDER_TYPE_NOTIME);

                if (systemIds != null && !"".equals(systemIds)) {
                    StringTokenizer stringTokenizer = new StringTokenizer(systemIds, ",");
                    while (stringTokenizer.hasMoreTokens()) {
                        String str = stringTokenizer.nextToken();
                        UcCombinationPattern ucCombinationPattern = new UcCombinationPattern();
                        ucCombinationPattern.setSystemId(str);
                        ucCombinationPattern.setUcGlobalPattern(ucGlobalPattern);
                        ucCombinationPatterns.add(ucCombinationPattern);
                    }

                    ucGlobalPattern.setUcCombinationPatterns(ucCombinationPatterns);
                }
                patternService.saveGlobalPattern(ucGlobalPattern);
            } else {
                UcGlobalPattern ucGlobalPattern = new UcGlobalPattern();
                ucGlobalPattern.setCreateDate(new Date());
                ucGlobalPattern.setCreateUser("");
                ucGlobalPattern.setIsNew("Y");
                ucGlobalPattern.setOrgId("");
                ucGlobalPattern.setSiteId("");
                ucGlobalPattern.setOrderType(PatternConst.PATTERN_ORDER_TYPE_NOTIME);
                ucGlobalPattern.setPatternDescription(patternDescription);
                ucGlobalPattern.setPatternName(patternName);
                StringTokenizer stringTokenizer = new StringTokenizer(systemIds, ",");
                while (stringTokenizer.hasMoreTokens()) {
                    String str = stringTokenizer.nextToken();
                    UcCombinationPattern ucCombinationPattern = new UcCombinationPattern();
                    ucCombinationPattern.setSystemId(str);
                    ucCombinationPattern.setUcGlobalPattern(ucGlobalPattern);
                    ucCombinationPatterns.add(ucCombinationPattern);
                }
                ucGlobalPattern.setUcCombinationPatterns(ucCombinationPatterns);
                ucGlobalPattern.setPatternType(PatternConst.PATTERN_ORDER_TYPE_NOTIME);
                String id = patternService.saveGlobalPattern(ucGlobalPattern);
                message.setPatternId(id);
            }

            opLogService.saveLogByUserAndMsg(user.getLoginid(), "创建系统全局触发模式,名称：" + patternName, "", "A", "PatternController", PatternConst.LOG_TYPE_PATTERN);

            patternService.saveAlarmReport(user.getLoginid() + "在 " + DateTime.now().toString("yyyy-MM-dd HH:mm:ss") + " 创建系统全局触发模式[" + patternName + "]");
            message.setSuccess(true);
            return message;

        } catch (Exception e) {
            logger.error("-----------", e);
        }
        message.setSuccess(false);
        message.setMsg(PatternConst.JSON_FAILURE);
        return message;
    }

    /**
     * 保存全局非时序模式
     *
     * @return
     */
    @RequestMapping(value = "/pattern/saveGlobalPatternToPattern")
    @ResponseBody
    public Message saveGlobalPatternToPattern(Model model, HttpServletRequest httpServletRequest) {

        Message message = new Message();
        try {
            String glbalPattern = httpServletRequest.getParameter("glbalPattern");

            if (glbalPattern != null && !"".equals(glbalPattern)) {
                UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
                ///////////////////////////////////////////
//                Collection objs = JSONArray.toCollection(JSONArray.fromObject(glbalPattern), UcCombinationPattern.class);
                ObjectMapper objectMapper = new ObjectMapper();
                UcCombinationPattern value = objectMapper.readValue(glbalPattern, UcCombinationPattern.class);
                UcCombinationPattern ucCombinationPattern = value;
                String id = patternService.saveGlobalPatternToPattern(ucCombinationPattern);

                opLogService.saveLogByUserAndMsg(user.getLoginid(), "关联系统全局触发模式", "", ucCombinationPattern.getSystemId(), "PatternController", PatternConst.LOG_TYPE_PATTERN);
                message.setPatternId(id);
                message.setSuccess(true);
                return message;
            }
        } catch (Exception e) {
            logger.error("-----------", e);
        }
        message.setSuccess(false);
        message.setMsg(PatternConst.JSON_FAILURE);
        return message;
    }

    /**
     * 保存非时序模式的时间表
     *
     * @return
     */
    @RequestMapping(value = "/pattern/saveNoTimePatternItem")
    @ResponseBody
    public Object saveNoTimePatternItem(Model model, HttpServletRequest httpServletRequest) {
        SystemMessage message = new SystemMessage();
        try {
            UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();

            String patternName = httpServletRequest.getParameter("patternAction");
            logger.debug("saveNoTimePatternItem------------------" + patternName);

            ObjectMapper objectMapper = new ObjectMapper();

            UcPatternAction value = objectMapper.readValue(patternName, UcPatternAction.class);
            UcPatternAction ucPatternAction = value;

            ucPatternAction = patternService.saveUcPatternAction(ucPatternAction);
            opLogService.saveLogByUserAndMsg(user.getLoginid(), "添加或编辑系统触发模式时间列表", "", ucPatternAction.getUcPattern().getSystemId(), "PatternController", PatternConst.LOG_TYPE_PATTERN);

            message.setAuthor("");
            message.setPatternId(ucPatternAction.getId());
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
     * 查询模式的时间表
     *
     * @param model
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/pattern/getPatternItem")
    @ResponseBody
    public Object getPatternItem(Model model, HttpServletRequest httpServletRequest) {

        UcPattern ucPattern = new UcPattern();
        try {
            //前天传过来的参数
            String patternId = httpServletRequest.getParameter("patternId");
            ucPattern = patternService.findUcPatternById(patternId);

            ucPattern.setUcPatternFactors(null);
            return ucPattern;

        } catch (Exception e) {
            logger.error("-----------", e);
        }
        return ucPattern;
    }

    /**
     * 查询位置树
     *
     * @param model
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/pattern/getLocalTree")
    @ResponseBody
    public Object getLocalTree(Model model, HttpServletRequest httpServletRequest) {
        try {
            //前天传过来的参数
            String siteId = httpServletRequest.getParameter("siteId");
//             List<Tree> locHierarchyTree = patternService.findLocHierarchyTree(siteId);
            if (siteId == null) {
                siteId = "";
            }
            List<LocHierarchySet> locHierarchyTree = patternService.findLocHierarchyList(siteId);
            return transferLocalToTree(locHierarchyTree);

        } catch (Exception e) {
            logger.error("-----------", e);
        }
        return null;
    }

    /**
     * 将区域节点转为封装的树节点
     *
     * @param locHierarchySets 区域集合
     * @return
     */
    private List<Tree> transferLocalToTree(List<LocHierarchySet> locHierarchySets) {
        List<Tree> treeList = new ArrayList<Tree>();
        for (LocHierarchySet locHierarchySet : locHierarchySets) {
            Tree tree = new Tree();
            tree.setId(locHierarchySet.getLocHierarchy().getLocation());
            tree.setNodetype("L");
            tree.setText(locHierarchySet.getLocations().getDescription());
            tree.setParentid(locHierarchySet.getLocHierarchy().getParent());
            tree.setUrl("/pattern/getLocalDevices");
            treeList.add(tree);
        }
        return treeList;
    }

    /**
     * 查询专业树
     *
     * @param model
     * @param httpServletRequest
     */
    @RequestMapping(value = "/pattern/getSpecialtyTree")
    @ResponseBody
    public Object getSpecialtyTree(Model model, HttpServletRequest httpServletRequest) {
        try {
            //前天传过来的参数
            String siteId = httpServletRequest.getParameter("siteId");

//             List<Tree> locHierarchyTree = patternService.findLocHierarchyTree(siteId);
            if (siteId == null) {
                siteId = "";
            }
            List<ClassStructure> locHierarchyTree = patternService.findAllClassStructure(siteId);

            return locHierarchyTree;
        } catch (Exception e) {
            logger.error("-----------", e);
        }
        return null;
    }

    /**
     * 查询位置树
     *
     * @param model
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/pattern/getLocalDevices")
    @ResponseBody
    public Object getLocalDevices(Model model, HttpServletRequest httpServletRequest) {
        try {
            //前天传过来的参数
            String siteId = httpServletRequest.getParameter("siteId");
            String systemType = httpServletRequest.getParameter("systemType");
            String location = httpServletRequest.getParameter("location");
            String classType = httpServletRequest.getParameter("classType");
            if (siteId == null) {
                siteId = "";
            }
            if (systemType != null && !"".equals(systemType)) {
                UcDeviceSystem ucDeviceSystem = patternService.findDeviceSystemByCode(systemType);
                systemType = ucDeviceSystem.getSpecclass();
                classType = ucDeviceSystem.getSystemClass();
            }
            if (classType == null) {
                classType = "";
            }

            List<Asset> locHierarchyTree = patternService.findDeviceByLocation(location, systemType, classType, siteId);

            return locHierarchyTree;
        } catch (Exception e) {
            logger.error("-----------", e);
        }
        return null;
    }

    /**
     * 查询全局模式
     *
     * @param model
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/pattern/getGlobalPattern")
    @ResponseBody
    public Object getGlobalPattern(Model model, HttpServletRequest httpServletRequest) {
        try {

            List<UcGlobalPattern> ucGlobalPatternByAll = patternService.findUcGlobalPatternByAll(PatternConst.PATTERN_ORDER_TYPE_NOTIME);
            for (UcGlobalPattern ucGlobalPattern : ucGlobalPatternByAll) {
                ucGlobalPattern.setUcCombinationPatterns(null);
            }
            return ucGlobalPatternByAll;
        } catch (Exception e) {
            logger.error("-----------", e);
        }
        return null;
    }

    /**
     * 查询某设备的运行状态
     *
     * @param model
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/pattern/getDeviceRunStatus")
    @ResponseBody
    public Object getDeviceRunStatus(Model model, HttpServletRequest httpServletRequest) {
        try {
            List<SystemMessage> systemMessages = new ArrayList<SystemMessage>();
            String deviceIds = httpServletRequest.getParameter("deviceIds");
            Object obj = httpServletRequest.getSession().getServletContext().getAttribute(PatternConst.CONTEXT_COMMAND_DAY_LIST);
            logger.debug("------getDeviceRunStatus---------" + deviceIds);
            if (obj != null) {
                Date nowDate = new Date();

                List<DeviceCommand> deviceCommandList = (List<DeviceCommand>) obj;
                StringTokenizer stringTokenizer = new StringTokenizer(deviceIds, ",");
                while (stringTokenizer.hasMoreTokens()) {
                    String id = stringTokenizer.nextToken();
                    SystemMessage systemMessage = new SystemMessage();
                    systemMessage.setDeviceId(id);

                    for (DeviceCommand deviceCommand : deviceCommandList) {
                        String deviceId = deviceCommand.getDeviceId();
                        if (deviceId.equals(id)) {
                            if (deviceCommand.getSendTime().after(nowDate)) {
                                //当前时间以后的状态
                                if (deviceCommand.getExecuteStatus().equals(PatternConst.DEVICE_EXECUTE_COVER)) {
                                    //表示由自动改为了手动，所以目前是手动状态。
                                    systemMessage.setRunPattern(PatternConst.SYSTEM_RUN_PATTERN_NO);
                                    break;
                                } else {
                                    systemMessage.setRunPattern(PatternConst.SYSTEM_RUN_PATTERN_YES);
                                    break;
                                }
                            }
                        }
                    }
                    systemMessages.add(systemMessage);
                }
            }
            return systemMessages;
        } catch (Exception e) {
            logger.error("-----------", e);
        }
        return null;
    }

    /**
     * 查询某设备的运行状态
     *
     * @param model
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/pattern/getSubSystemRunStatus")
    @ResponseBody
    public Object geSubSystemRunStatus(Model model, HttpServletRequest httpServletRequest) {
        SystemMessage message = new SystemMessage();
        try {

            String systemCode = httpServletRequest.getParameter("systemCode");


            List<UcPatternRecord> ucPatternRecordList = patternService.findUcPatternRecordBySystemIdAndDateLazy(systemCode, DateTime.now().toString("yyyyMMdd"));
            if (ucPatternRecordList != null && ucPatternRecordList.size() > 0) {
                message.setRunPattern(PatternConst.SYSTEM_RUN_PATTERN_YES);

                //查找冷源的运行模式提供给暖通系统
                for (UcPatternRecord ucPatternRecord : ucPatternRecordList) {

                    if (PatternConst.SYSTEM_CODE_MAHU.equals(systemCode.toUpperCase())) {//取冷源的运行模式来显示 暖通系统的运行模式
                        message.setPatternName(ucPatternRecord.getPatternName());
                    }
                }
                String name = message.getPatternName();

                if (name == null || "".equals(name)) {
                    message.setRunPattern(PatternConst.SYSTEM_RUN_PATTERN_NO);
                }
            } else {
                message.setRunPattern(PatternConst.SYSTEM_RUN_PATTERN_NO);
            }

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
     * 通过系统ID查询该子系统的策略
     *
     * @param model
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/pattern/getStrategy")
    @ResponseBody
    public Object getStrategy(Model model, HttpServletRequest httpServletRequest) {
        try {
            String systemId = httpServletRequest.getParameter("systemId");
            List<UcDeviceStrategy> ucDeviceStrategy = patternService.findStrategyBySystemId(systemId);

            return ucDeviceStrategy;
        } catch (Exception e) {
            logger.error("-----------", e);
        }
        return null;
    }

    /**
     * 通过系统编码查询该系统的时序模式包括系统和自定义
     *
     * @param model
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/pattern/getPatternBySystemCode")
    @ResponseBody
    public Object getPatternBySystemCode(Model model, HttpServletRequest httpServletRequest) {
        List<UcPattern> ucPatternList = new ArrayList<UcPattern>();
        try {
            String systemCode = httpServletRequest.getParameter("systemCode");

            if (systemCode.equals(PatternConst.SYSTEM_CODE_EAV) || systemCode.equals(PatternConst.SYSTEM_CODE_SAV)) {
                systemCode = PatternConst.SYSTEM_CODE_AVU;
            }

            List<UcPattern> ucPatterns = patternService.findUcPatternBySystem(systemCode, PatternConst.PATTERN_TYPE_SYSTEM, PatternConst.PATTERN_ORDER_TYPE_TIME);
            ucPatternList.addAll(ucPatterns);
            ucPatterns = patternService.findUcPatternBySystem(systemCode, PatternConst.PATTERN_TYPE_CUSTOM, PatternConst.PATTERN_ORDER_TYPE_TIME);
            ucPatternList.addAll(ucPatterns);

            //去掉不需要的属性, 否则转JSON会报错
            for (UcPattern ucPattern : ucPatternList) {
                ucPattern.setUcCombinationPatterns(null);
                ucPattern.setUcPatternFactors(null);
                ucPattern.setUcPatternActions(null);
            }
            return ucPatternList;
        } catch (Exception e) {
            logger.error("-----------", e);
        }
        return ucPatternList;
    }

    /**
     * 删除全局模式
     *
     * @return
     */
    @RequestMapping(value = "/pattern/deleteGlobalPattern")
    @ResponseBody
    public Object deleteGlobalPattern(Model model, HttpServletRequest httpServletRequest) {

        Message message = new Message();
        try {
            String patternId = httpServletRequest.getParameter("patternId");
            UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            patternService.deleteGlobalPattern(patternId);
            logger.info(user.getLoginid() + "删除系统触发模式[" + patternId + "]");
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
     * 删除子系统的某模式
     *
     * @return
     */
    @RequestMapping(value = "/pattern/deletePattern")
    @ResponseBody
    public Object deletePattern(Model model, HttpServletRequest httpServletRequest) {

        Message message = new Message();
        try {
            String patternId = httpServletRequest.getParameter("patternId");
            UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            patternService.delPattern(patternId);
            logger.info(user.getLoginid() + "删除系统时序模式[" + patternId + "]");
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
     * 删除模式的时间动作
     *
     * @return
     */
    @RequestMapping(value = "/pattern/deletePatternAction")
    @ResponseBody
    public Object deletePatternAction(Model model, HttpServletRequest httpServletRequest) {

        Message message = new Message();
        try {
            String patternId = httpServletRequest.getParameter("itemId");
            UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            patternService.deletePatternAction(patternId);
            logger.info(user.getLoginid() + "删除系统时序模式的时间列表[" + patternId + "]");
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
     * 手动控制某设备
     *
     * @return
     * @todo 安全问题，C/S端直接请求
     */
    @RequestMapping(value = "/pattern/manual/manualPatternRun")
    @ResponseBody
    public Object manualPattern(Model model, HttpServletRequest httpServletRequest) {

        SystemMessage message = new SystemMessage();
        try {
            //将此设备的其它运行命令去除。
            String run = httpServletRequest.getParameter("value");
            List<DeviceCommand> deviceCommandList = (List<DeviceCommand>) httpServletRequest.getSession().getServletContext().getAttribute(PatternConst.CONTEXT_COMMAND_DAY_LIST);
            Object sendCommand = httpServletRequest.getSession().getServletContext().getAttribute(PatternConst.CONTEXT_COMMAND_DAY_SEND);
            List<String> manualDevice = (List<String>) httpServletRequest.getSession().getServletContext().getAttribute(PatternConst.CONTEXT_COMMAND_DAY_MANUAL_DEVICE);
            if (manualDevice == null) {
                manualDevice = new ArrayList<String>();
            }
            String systemCode = "";
            String tagId = httpServletRequest.getParameter("tagId");
            //通过tagId转为设备ID;
            Asset asset = patternService.findDeviceIdByTagId(tagId);
            String deviceId = asset.getAssetnum();

            logger.debug("------manualPatternRun---------" + run);
            UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            if (deviceCommandList != null) {
                TriggerKey triggerKey = new TriggerKey("getDayCommand");
                if (run.equals("1")) {

                    scheduler.pauseTrigger(triggerKey);

                    if (deviceId != null && !"".equals(deviceId)) {
                        //将此设备的其它运行命令去除。 保持手动状态
                        logger.debug("------manualPatternRun-if--deviceIds------" + deviceId);
                        //将设备加入手动列表
                        manualDevice.add(deviceId);
                        addManualList(manualDevice, deviceId);
                        httpServletRequest.getSession().getServletContext().setAttribute(PatternConst.CONTEXT_COMMAND_DAY_MANUAL_DEVICE, manualDevice);
                        if (deviceCommandList != null) {
                            patternService.manualDeviceCommand(deviceCommandList, "", deviceId, PatternConst.COMMAND_TYPE_MANUAL);

                            //更新命令列表里未执行的命令的状态
                        }
                    }

                    for (DeviceCommand deviceCommand : deviceCommandList) {
                        if (deviceId.equals(deviceCommand.getDeviceId())) {
                            systemCode = deviceCommand.getSystemId();
                            break;
                        }
                    }
                    opLogService.saveLogByUserAndMsg(user.getLoginid(), "设备切换到手动模式", "", systemCode, "PatternController", PatternConst.LOG_TYPE_PATTERN);
                    logger.info(user.getLoginid() + "设备切换到手动模式");
                } else {
                    //恢复模式运行,恢复命令列表
                    scheduler.resumeTrigger(triggerKey);
                    if (deviceId != null && !"".equals(deviceId)) {

                        //将设备从手动列表中去掉，因为恢复了自动运行。
                        for (String ob : manualDevice) {
                            manualDevice.remove(ob);
                            break;
                        }
                        addManualList(manualDevice, deviceId);
                        //将此设备添加到自动运行
                        logger.debug("------manualPatternRun--else---deviceIds----" + deviceId);

                        patternService.manualDeviceCommand(deviceCommandList, "", deviceId, PatternConst.COMMAND_TYPE_AUTO);
                        logger.debug(deviceCommandList.size() + "---size---manualPatternRun--else---deviceIds----" + deviceId);
                        //需要执行此设备的最近一条命令列表
                        orderCommandAsc(deviceCommandList);
                        if (sendCommand == null) {
                            sendCommand = new ArrayList<DeviceCommand>();
                        }
                        logger.debug("---3---manualPatternRun--else---deviceIds----" + deviceId);
                        long nowDate = System.currentTimeMillis();
                        long up = 0;
                        for (DeviceCommand deviceCommand : deviceCommandList) {
                            logger.debug("------manual--deviceIds----" + deviceId + "----" + deviceCommand.getDeviceId());
                            //在已经找出的命令里不存在此设备的命令。避免过多已经执行的命令被加入
                            if (deviceId.equals(deviceCommand.getDeviceId())) {
                                systemCode = deviceCommand.getSystemId();
                                long a = deviceCommand.getSendTime().getTime();
                                logger.debug("------manual--deviceIds----" + a + "----" + nowDate);
                                if (a < nowDate) {//小于当前时间的第一条命令
                                    logger.debug("--------send command-----" + deviceId + "----" + nowDate);

                                    if (!((List<DeviceCommand>) sendCommand).contains(deviceCommand)) {
                                        ((List<DeviceCommand>) sendCommand).add(deviceCommand);
                                    }
                                    break;
                                }
                            }
                        }
                        httpServletRequest.getSession().getServletContext().setAttribute(PatternConst.CONTEXT_COMMAND_DAY_SEND, sendCommand);
                    }
                    logger.info(user.getLoginid() + "设备切换到自动模式");
                    opLogService.saveLogByUserAndMsg(user.getLoginid(), "设备切换到自动模式", "", systemCode, "PatternController", PatternConst.LOG_TYPE_PATTERN);
                }


            }
            //将此手动
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
     * 保存手动的设备ID
     *
     * @param manual   手动列表
     * @param deviceId 设备ID
     */
    private void addManualList(List<String> manual, String deviceId) {
        String result = listToString(manual);
        manualProperties.setValue(PatternConst.MANUAL_DEVICE_ID_KEY, result);
    }

    /**
     * List转为字符串
     *
     * @param stringList
     * @return
     */
    private String listToString(List<String> stringList) {

        StringBuilder result = new StringBuilder();
        boolean flag = false;
        for (String string : stringList) {
            if (flag) {
                result.append(",");
            } else {
                flag = true;
            }
            result.append(string);
        }
        return result.toString();
    }

    /**
     * 对列表进行按时间排序进行降序排列
     */
    private List<DeviceCommand> orderCommandAsc(List<DeviceCommand> baseCommands) {
        Collections.sort(baseCommands, new Comparator<DeviceCommand>() {
            public int compare(DeviceCommand arg0, DeviceCommand arg1) {
                long a = arg0.getSendTime().getTime();
                long b = arg1.getSendTime().getTime();
                if (a > b) {
                    return 1;
                } else {
                    if (a == b) {
                        return 0;
                    } else {
                        return -1;
                    }
                }
            }
        });
        return baseCommands;
    }

    /**
     * 切换模式
     */
    @RequestMapping(value = "/pattern/control/changePatternRun", method = RequestMethod.POST)
    @ResponseBody
    public Object changePatternRun(Model model, HttpServletRequest httpServletRequest) {
        SystemMessage message = new SystemMessage();
        try {
            UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            //将此设备的其它运行命令去除。
            String patternId = httpServletRequest.getParameter("patternId");
            String reason = httpServletRequest.getParameter("reason");
            Object obj = httpServletRequest.getSession().getServletContext().getAttribute(PatternConst.CONTEXT_COMMAND_DAY_LIST);
            Object manualDevice = httpServletRequest.getSession().getServletContext().getAttribute(PatternConst.CONTEXT_COMMAND_DAY_MANUAL_DEVICE);
            HashMap<String, DeviceCommand> templateCommand = (HashMap<String, DeviceCommand>) httpServletRequest.getSession().getServletContext().getAttribute(PatternConst.CONTEXT_COMMAND_TEMPLATE);
            logger.debug("------changePatternRun---------" + patternId);
            if (manualDevice == null) {
                manualDevice = new ArrayList<String>();
            }
            if (obj != null) {
                UcPattern ucPattern = patternService.findUcPatternById(patternId);
                TriggerKey triggerKey = new TriggerKey("getDayCommand");
                //暂停自动运行
                scheduler.pauseTrigger(triggerKey);

                List<DeviceCommand> deviceCommandList = (List<DeviceCommand>) obj;
                logger.info("---changePatternRun--start-------");
                patternService.changeUcPatternRecord(patternId, ucPattern.getSystemId(), DateTime.now().toString("yyyyMMdd"));
                logger.info("---changePatternRun--start--2-----");
                Collection<DeviceCommand> deviceCommands = patternService.changePatternByTemplate(deviceCommandList, patternId, (List<String>) manualDevice, templateCommand);
                //  logger.info("---changePatternRun--end-------" );
                //执行所有设备最近的上一条命令
//                for (DeviceCommand deviceCommand : deviceCommands) {
//                    SendCommandToSystem.sendCommand(deviceCommand);
//                }//
                //执行所有设备最近的上一条命令
                patternService.threadSendCommand(deviceCommands);


                //   logger.info("---threadSendCommand--start-------");
                scheduler.resumeTrigger(triggerKey);

                //设置应该高亮的模式的按钮 改为BS，不需要再设置
                //patternService.setCSButtonStyle(ucPattern.getSystemId());
                //日志
                opLogService.saveLogByUserAndMsg(user.getLoginid(), "切换模式为:" + ucPattern.getName(), "", ucPattern.getSystemId(), "PatternController", PatternConst.LOG_TYPE_PATTERN);
                //短信
                patternService.saveAlarmReport(user.getLoginid() + "在 " + DateTime.now().toString("yyyy-MM-dd HH:mm:ss") + " 切换模式为:" + ucPattern.getName());
                logger.info("---saveAlarmReport--start-------");

            }
            logger.info(user.getLoginid() + " 切换模式[" + patternId + "]");
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
     * 全局模式执行
     *
     * @return
     */
    @RequestMapping(value = "/pattern/global/globalPatternRun")
    public void globalPatternRun(Model model, HttpServletRequest httpServletRequest) {

        try {
            HashMap<String, DeviceCommand> templateCommand = (HashMap<String, DeviceCommand>) httpServletRequest.getSession().getServletContext().getAttribute(PatternConst.CONTEXT_COMMAND_TEMPLATE);
            String patternId = httpServletRequest.getParameter("patternId");
            String run = httpServletRequest.getParameter("run");
            if (patternId != null && !"".equals(patternId)) {
                TriggerKey triggerKey = new TriggerKey("getDayCommand");
                if (run.equals("Y")) {
                    //暂停 一日的命令的运行

                    scheduler.pauseTrigger(triggerKey);
                    //生成全局模式的命令
                    List<DeviceCommand> deviceCommands = patternService.findGlobalPatternToDeviceCommand(new Date(), patternId, templateCommand);

                    httpServletRequest.getSession().getServletContext().setAttribute("GLOBAL_COMMAND", deviceCommands);
                    httpServletRequest.getSession().getServletContext().setAttribute("GLOBAL_RUN_DATE", new Date());
                    //执行全局命令
                    SendCommandToSystem.sendLazyTimeCommand(deviceCommands);
                } else {
                    //停止全局模式,恢复命令列表
                    scheduler.resumeTrigger(triggerKey);
                    //找出未执行的命令
                    Object obj = httpServletRequest.getSession().getServletContext().getAttribute(PatternConst.CONTEXT_COMMAND_DAY_LIST);
                    Object objCommand = httpServletRequest.getSession().getServletContext().getAttribute("GLOBAL_COMMAND");
                    Object objDate = httpServletRequest.getSession().getServletContext().getAttribute("GLOBAL_RUN_DATE");
                    List<DeviceCommand> bases = (List<DeviceCommand>) obj;
                    List<DeviceCommand> globals = (List<DeviceCommand>) objCommand;
                    Date start = (Date) objDate;
                    List<DeviceCommand> deviceCommands = patternService.combinationGlobalCommand(bases, start, new Date(), globals);
                    //去执行
                    for (DeviceCommand deviceCommand : deviceCommands) {
                        SendCommandToSystem.sendCommand(deviceCommand);
                    }
                    //更新命令列表里未执行的命令的状态，在合并检查命令的时候已经处理
                }
            }
        } catch (Exception e) {
            logger.error("---------", e);
        }
    }

    /**
     * 显示策略的页面
     *
     * @param model
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/strategy/showStrategy")
    public String showStrategy(Model model, HttpServletRequest httpServletRequest) {
        try {
            String systemId = httpServletRequest.getParameter("systemId");

            setModelAttribute(model, PatternConst.SYSTEM_CODE_HVAC);

            List<UcDeviceStrategy> ucDeviceStrategy = patternService.findStrategyBySystemId(systemId);
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(ucDeviceStrategy);

            model.addAttribute("DeviceStrategy", json);

            List<UcStrategyItem> ucStrategyItems = patternService.findStrategyItemByParentId("999999");

            json = objectMapper.writeValueAsString(ucStrategyItems);
            model.addAttribute("StrategyItem", json);

            List<UcStrategyItem> params = new ArrayList<UcStrategyItem>();
            for (UcStrategyItem ucStrategyItem : ucStrategyItems) {
                String parentId = ucStrategyItem.getId();
                List<UcStrategyItem> strategyItems = patternService.findStrategyItemByParentId(parentId);
                params.addAll(strategyItems);
            }

            json = objectMapper.writeValueAsString(params);
            model.addAttribute("StrategyParam", json);
            model.addAttribute("systemCode", systemId);
            model.addAttribute("systemId", systemId);
            return "pattern/strategy";

        } catch (Exception e) {
            logger.error("-----------", e);
        }
        return "pattern/strategy";
    }

    /**
     * 查询某策略的详细信息
     *
     * @param model
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/strategy/getStrategyDetail")
    @ResponseBody
    public Object getStrategyDetail(Model model, HttpServletRequest httpServletRequest) {
        try {
            String strategyId = httpServletRequest.getParameter("strategyId");
            UcDeviceStrategy ucDeviceStrategy = patternService.findDeviceStrategy(strategyId);
            return ucDeviceStrategy;

        } catch (Exception e) {
            logger.error("-----------", e);
        }
        return null;
    }

    /**
     * 删除策略
     *
     * @return
     */
    @RequestMapping(value = "/strategy/deleteStrategy")
    @ResponseBody
    public Object deleteStrategy(Model model, HttpServletRequest httpServletRequest) {

        Message message = new Message();
        try {
            UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            String strategyId = httpServletRequest.getParameter("strategyId");
            patternService.deleteStrategy(strategyId);
            logger.info(user.getLoginid() + "删除策略");
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
     * 保存策略
     *
     * @return
     */
    @RequestMapping(value = "/strategy/saveStrategy")
    @ResponseBody
    public Object saveStrategy(Model model, HttpServletRequest httpServletRequest) {

        SystemMessage message = new SystemMessage();
        try {
            String strategy = httpServletRequest.getParameter("strategy");
            UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            ObjectMapper objectMapper = new ObjectMapper();

            UcDeviceStrategy value = objectMapper.readValue(strategy, UcDeviceStrategy.class);
            UcDeviceStrategy ucDeviceStrategy = value;

            String id = patternService.saveStrategy(ucDeviceStrategy);
            logger.info(user.getLoginid() + "创建策略:" + ucDeviceStrategy.getStrategyName());
            message.setPatternId(id);
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
     * 保存时序模式
     *
     * @return
     */
    @RequestMapping(value = "/pattern/saveSystemPatternTime")
    @ResponseBody
    public Object saveSystemPatternTime(Model model, HttpServletRequest httpServletRequest) {
        SystemMessage message = new SystemMessage();
        try {
            UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            String patternName = httpServletRequest.getParameter("patternName");
            String patternId = httpServletRequest.getParameter("patternId");
            String patternType = httpServletRequest.getParameter("patternType");
            String patternDescription = httpServletRequest.getParameter("patternDescription");
            String systemId = httpServletRequest.getParameter("systemId");
            String factor = httpServletRequest.getParameter("factor");


            UcPattern ucPattern = new UcPattern();
            ucPattern.setId(patternId);
            ucPattern.setName(patternName);
            ucPattern.setSystemId(systemId);
            ucPattern.setDescription(patternDescription);
            ucPattern.setIsNew("Y"); //为最新的版本
            ucPattern.setDefaultPattern("N");
            ucPattern.setPatternType(patternType);
            ucPattern.setOrderType(PatternConst.PATTERN_ORDER_TYPE_TIME);//时序模式
//            ucPattern.setCreateUser(user.getLoginid());
            ucPattern.setCreateDate(new Date());

            HashSet<UcPatternFactor> ucPatternFactorHashSet = new HashSet<UcPatternFactor>();
            if (factor != null && !"".equals(factor)) {
                ObjectMapper objectMapper = new ObjectMapper();

                Collection objs = objectMapper.readValue(factor, List.class);

                for (Object obj : objs) {

                    ItemJSON itemJSON = (objectMapper.readValue(obj.toString(), ItemJSON.class));

                    UcFactor ucFactor = new UcFactor();
                    ucFactor.setId(itemJSON.getCode());
                    UcPatternFactor ucPatternFactor = new UcPatternFactor();
                    ucPatternFactor.setUcFactor(ucFactor);
                    ucPatternFactor.setUcPattern(ucPattern);

                    ucPatternFactorHashSet.add(ucPatternFactor);
                }
                ucPattern.setUcPatternFactors(ucPatternFactorHashSet);
            }
            String id = patternService.saveUcPattern(ucPattern);
            logger.info(user.getLoginid() + "创建模式:" + ucPattern.getName());

            opLogService.saveLogByUserAndMsg(user.getLoginid(), "创建模式:" + ucPattern.getName(), "", systemId, "PatternController", PatternConst.LOG_TYPE_PATTERN);

            patternService.saveAlarmReport(user.getLoginid() + "在 " + DateTime.now().toString("yyyy-MM-dd HH:mm:ss") + " 创建模式");
            message.setAuthor("");
            message.setPatternId(id);
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
     * 保存时序模式
     *
     * @return
     */
    @RequestMapping(value = "/pattern/saveTimePatternItem", method = RequestMethod.POST)
    @ResponseBody
    public Object saveTimePatternItem(HttpServletRequest httpServletRequest) {

        SystemMessage message = new SystemMessage();
        try {
            UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            String patternName = httpServletRequest.getParameter("patternAction");
            logger.debug("0--------------------" + patternName);
            ObjectMapper objectMapper = new ObjectMapper();

            UcPatternAction value = objectMapper.readValue(patternName, UcPatternAction.class);
            UcPatternAction ucPatternAction = value;

            ucPatternAction = patternService.saveUcPatternAction(ucPatternAction);
            logger.info(user.getLoginid() + "创建模式的时间列表");
            opLogService.saveLogByUserAndMsg(user.getLoginid(), "添加或编辑模式的时间列表", "", ucPatternAction.getUcPattern().getSystemId(), "PatternController", PatternConst.LOG_TYPE_PATTERN);
            message.setAuthor(user.getLoginid());
            message.setPatternId(ucPatternAction.getId());
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
     * 查询某子系统的影响因素
     *
     * @param model
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/factor/getSystemFactors")
    @ResponseBody
    public Object getSystemFactors(Model model, HttpServletRequest httpServletRequest) {
        try {
            String systemId = httpServletRequest.getParameter("systemId");
            List<UcSysFactor> sysFactors = patternService.findSystemFactor(systemId);

            return sysFactors;
        } catch (Exception e) {
            logger.error("-----------", e);
        }
        return null;
    }

    /**
     * 查询各子系统的运行模式及事件
     *
     * @param model
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/device/getTodaySystemRunPattern")
    @ResponseBody
    public Object getSystemCheck(Model model, HttpServletRequest httpServletRequest) {
        try {
            String nowDate = DateTime.now().toString("yyyyMMdd");
            List<SystemMessage> systemMessages = new ArrayList<SystemMessage>();
            List<UcDeviceSystem> ucDeviceSystemList = patternService.findRunPatternSystem();
            for (UcDeviceSystem ucDeviceSystem : ucDeviceSystemList) {
                SystemMessage systemMessage = new SystemMessage();
                systemMessage.setSystemName(ucDeviceSystem.getSystemName());
                systemMessage.setSystemId(ucDeviceSystem.getId());
                systemMessage.setSystemCode(ucDeviceSystem.getSystemCode());
                List<UcPatternRecord> ucPatternRecordList = patternService.findUcPatternRecordBySystemIdAndDateLazy(ucDeviceSystem.getSystemCode(), nowDate);
                if (ucPatternRecordList != null && ucPatternRecordList.size() > 0) {
                    for (UcPatternRecord ucPatternRecord : ucPatternRecordList) {
                        //不是特例
                        if (!ucPatternRecord.getPatternType().equals(PatternConst.PATTERN_TYPE_SPECIAL)) {
                            systemMessage.setPatternName(ucPatternRecord.getPatternName());
                            systemMessage.setPatternId(ucPatternRecord.getPatternId());
                        }
                    }
                }
                systemMessages.add(systemMessage);
            }
            Date start = DateTime.now().plusDays(-1).toDate();
            Date end = DateTime.now().plusDays(1).toDate();
            List<Event> eventList = calendarService.findEventByStartDateAndEndDate(start, end);
            for (Event event : eventList) {
                SystemMessage systemMessage = new SystemMessage();
                systemMessage.setSystemName("事件");
                systemMessage.setPatternName(event.getTitle());
                systemMessages.add(systemMessage);
            }

            return systemMessages;
        } catch (Exception e) {
            logger.error("-----------", e);
        }
        return null;
    }

    /**
     * 查询某子系统的有哪些逻辑组
     *
     * @param model
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/device/getSystemDeviceGroup")
    @ResponseBody
    public Object getTodaySystemRunPattern(Model model, HttpServletRequest httpServletRequest) {
        try {
            String systemId = httpServletRequest.getParameter("systemId");
            List<UcLogicGroup> ucLogicGroups = patternService.findLogicGroup(systemId);
            for (UcLogicGroup ucLogicGroup : ucLogicGroups) {
                ucLogicGroup.setUcGroupDevices(null);
            }
            return ucLogicGroups;
        } catch (Exception e) {
            logger.error("-----------", e);
        }
        return null;
    }

    /**
     * 准备手动操作
     *
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/pattern/readyManual", method = RequestMethod.POST)
    @ResponseBody
    public Object readyManual(HttpServletRequest httpServletRequest) {

        SystemMessage message = new SystemMessage();
        try {
            UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            String reason = httpServletRequest.getParameter("reason");
            String type = httpServletRequest.getParameter("type");

            logger.debug("0----------reason----------" + reason + "---" + type);
            if ("Y".equals(type)) {
                String systemCode = httpServletRequest.getParameter("systemCode");
                logger.info(user.getLoginid() + "准备手动操作,原因:" + reason);
                opLogService.saveLogByUserAndMsg(user.getLoginid(), "切换为手动,原因:" + reason, "", systemCode, "PatternController", PatternConst.LOG_TYPE_PATTERN);

                logger.debug("------showPatternRunTime---------" + systemCode);
                httpServletRequest.getSession().setAttribute("SYSTEMCODE", systemCode);
                // patternService.setCSButtonStyle(systemCode);   改为BS，不需要设置了。

            } else {
                String systemCode = "";
                List<DeviceCommand> sendCommand = (List<DeviceCommand>) httpServletRequest.getSession().getServletContext().getAttribute(PatternConst.CONTEXT_COMMAND_DAY_SEND);
                HashMap<String, String> maPatternDevice = (HashMap<String, String>) httpServletRequest.getSession().getServletContext().getAttribute(PatternConst.CONTEXT_MANUAL_PATTERN_DEVICE);
                if (sendCommand != null) {
                    for (DeviceCommand deviceCommand : sendCommand) {

                        systemCode = deviceCommand.getSystemId();
                        logger.debug("-readyManual--send command-----" + deviceCommand.getDeviceId());
                        SendCommandToSystem.sendCommand(deviceCommand);
                    }
                    sendCommand.clear();
                    httpServletRequest.getSession().getServletContext().setAttribute(PatternConst.CONTEXT_COMMAND_DAY_SEND, new ArrayList<DeviceCommand>());
                }
                //手动切换模式后，恢复自动后的执行最后一次命令
                if (maPatternDevice != null) {

                    TriggerKey triggerKey = new TriggerKey("getDayCommand");
                    scheduler.resumeTrigger(triggerKey);
                    List<DeviceCommand> deviceCommandList = (List<DeviceCommand>) httpServletRequest.getSession().getServletContext().getAttribute(PatternConst.CONTEXT_COMMAND_DAY_LIST);
                    //需要执行此设备的最近一条命令列表
                    orderCommandAsc(deviceCommandList);

                    long nowDate = System.currentTimeMillis();

                    for (String deviceId : maPatternDevice.keySet()) {

                        for (DeviceCommand deviceCommand : deviceCommandList) {
                            logger.debug("------manual--deviceIds----" + deviceId + "----" + deviceCommand.getDeviceId());
                            //在已经找出的命令里不存在此设备的命令。避免过多已经执行的命令被加入
                            if (deviceId.equals(deviceCommand.getDeviceId())) {
                                systemCode = deviceCommand.getSystemId();
                                long a = deviceCommand.getSendTime().getTime();

                                logger.debug("------manual--deviceIds----" + a + "----" + nowDate);
                                if (a < nowDate) {//小于当前时间的第一条命令
                                    logger.debug("--------send command-----" + deviceId + "----" + nowDate);
                                    //发送命令
                                    SendCommandToSystem.sendCommand(deviceCommand);
                                    break;
                                }
                            }
                        }
                    }

                    maPatternDevice.clear();
                }

                logger.debug("-readyManual--systemCode--1---" + systemCode);
                if (systemCode == null || "".equals(systemCode)) {
                    //恢复自动时，没有systemCode传入
                    systemCode = (String) httpServletRequest.getSession().getAttribute("SYSTEMCODE");
                }
                logger.debug("-readyManual--systemCode--2---" + systemCode);
                //patternService.setCSButtonStyle(systemCode);   改为BS，不需要设置了。
                logger.info(user.getLoginid() + "恢复自动运行.");
                opLogService.saveLogByUserAndMsg(user.getLoginid(), "恢复自动运行", "", systemCode, "PatternController", PatternConst.LOG_TYPE_PATTERN);
            }
            message.setAuthor("");
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
     * 获取子系统的非时序
     *
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/pattern/getSystemNoTimePattern", method = RequestMethod.GET)
    @ResponseBody
    public Object getSystemNoTimePattern(HttpServletRequest httpServletRequest) {
        List<UcPattern> listCustomTime = null;
        try {
            String systemCode = httpServletRequest.getParameter("systemCode");
            if (systemCode != null && !"".equals(systemCode)) {

                listCustomTime = patternService.findUcPatternBySystem(systemCode, PatternConst.PATTERN_TYPE_CUSTOM, PatternConst.PATTERN_ORDER_TYPE_NOTIME);
                //去掉不需要的属性, 否则转JSON会报错
                for (UcPattern ucPattern : listCustomTime) {
                    ucPattern.setUcCombinationPatterns(null);
                    ucPattern.setUcPatternFactors(null);
                    ucPattern.setUcPatternActions(null);
                }
            }
            return listCustomTime;

        } catch (Exception e) {
            logger.error("-----------", e);
        }
        return null;
    }

    /**
     * 设置某设备运行某模式,只设置未来，不设置今天
     *
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/pattern/setDeviceRunPattern", method = RequestMethod.POST)
    @ResponseBody
    public Object setDeviceRunPattern(HttpServletRequest httpServletRequest) {
        SystemMessage message = new SystemMessage();
        try {
            UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            String deviceId = httpServletRequest.getParameter("deviceId");
            String patternId = httpServletRequest.getParameter("patternId");
            String startDate = httpServletRequest.getParameter("startDate");
            String endDate = httpServletRequest.getParameter("endDate");
            if (deviceId != null && !"".equals(deviceId) && patternId != null && !"".equals(patternId) && startDate != null && !"".equals(startDate) && endDate != null && !"".equals(endDate)) {

                patternService.saveDeviceApplyPattern(deviceId, patternId, startDate, endDate);

                opLogService.saveLogByUserAndMsg(user.getLoginid(), "设备[" + deviceId + "]运行特殊模式[" + patternId + "]", "", "", "PatternController", PatternConst.LOG_TYPE_PATTERN);
                message.setSuccess(true);
                message.setMsg(PatternConst.JSON_SUCCESS);
                return message;
            }

        } catch (Exception e) {
            logger.error("-----------", e);
        }
        message.setSuccess(false);
        message.setMsg(PatternConst.JSON_FAILURE);
        return message;
    }

    /**
     * 获取开店时间和闭店时间
     *
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/pattern/getListBusinessTime", method = RequestMethod.GET)
    @ResponseBody
    public Object getListBusinessTime(HttpServletRequest httpServletRequest) {
        try {
            String start = DateTime.now().toString("yyyyMMdd");
            String end = DateTime.now().plusDays(6).toString("yyyyMMdd");
            List<UcCalendar> ucCalendars = patternService.findUcCalendarByCalendar(start, end);
            //去掉不需要的属性, 否则转JSON会报错
            for (UcCalendar ucCalendar : ucCalendars) {

                Set<UcHoliday> ucHolidays = ucCalendar.getUcHolidays();

                for (UcHoliday ucHoliday : ucHolidays) {
                    ucHoliday.setUcCalendar(null);
                }
                Set<UcWeather> ucWeatherSet = ucCalendar.getUcWeathers();
                for (UcWeather ucWeather : ucWeatherSet) {
                    ucWeather.setUcCalendar(null);
                }
                ucCalendar.setUcPatternRecords(null);
            }
            return ucCalendars;

        } catch (Exception e) {
            logger.error("-----------", e);
        }
        return null;
    }

    /**
     * 设置开店时间和闭店时间
     *
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/pattern/setBusinessTime", method = RequestMethod.POST)
    @ResponseBody
    public Object setBusinessTime(HttpServletRequest httpServletRequest) {
        SystemMessage message = new SystemMessage();
        try {
            UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            String startDate = httpServletRequest.getParameter("startDate");
            String startTime = httpServletRequest.getParameter("startTime");
            String endDate = httpServletRequest.getParameter("endDate");
            String endTime = httpServletRequest.getParameter("endTime");
            logger.debug("--------" + startDate + "--" + startTime + "----" + endDate + "====" + endTime);
            if (startDate != null && !"".equals(startDate)) {
                String sd = DateTime.parse(startDate, DateTimeFormat.forPattern("yyyy-MM-dd")).toString("yyyyMMdd");
                Date st = DateTime.parse(startTime, DateTimeFormat.forPattern("HH:mm")).toDate();
                patternService.saveBusinessTime(sd, st, null);
            }
            if (endDate != null && !"".equals(endDate)) {
                String ed = DateTime.parse(endDate, DateTimeFormat.forPattern("yyyy-MM-dd")).toString("yyyyMMdd");
                Date et = DateTime.parse(endTime, DateTimeFormat.forPattern("HH:mm")).toDate();
                patternService.saveBusinessTime(ed, null, et);
            }

            opLogService.saveLogByUserAndMsg(user.getLoginid(), "修改开店闭店时间", "", "", "PatternController", PatternConst.LOG_TYPE_PATTERN);
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
     * 查询设备通过专业分类和设备级别
     *
     * @param model
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/pattern/getDeviceByClass")
    @ResponseBody
    public Object getDeviceByClass(Model model, HttpServletRequest httpServletRequest) {
        try {
            //前天传过来的参数
            String siteId = httpServletRequest.getParameter("siteId");
            String systemType = httpServletRequest.getParameter("systemType");
            String classType = httpServletRequest.getParameter("classType");
            if (siteId == null) {
                siteId = "";
            }
            if (systemType != null && !"".equals(systemType)) {
                UcDeviceSystem ucDeviceSystem = patternService.findDeviceSystemByCode(systemType);
                systemType = ucDeviceSystem.getSpecclass();
                classType = ucDeviceSystem.getSystemClass();
            }
            List<Asset> assetList = patternService.findBySpecclassAndClassstructureidAndSiteid(systemType, classType, siteId);

            return assetList;
        } catch (Exception e) {
            logger.error("-----------", e);
        }
        return null;
    }

    /**
     * 手动切换模式，C/S切换到某个时间点的模式。
     *
     * @return
     * @deprecated 改成B/S后此连接失效
     */
    @RequestMapping(value = "/pattern/manual/manualChangePattern")
    @ResponseBody
    public Object manualChangePattern(Model model, HttpServletRequest httpServletRequest) {

        SystemMessage message = new SystemMessage();
        try {
            //将此设备的其它运行命令去除。
            List<DeviceCommand> obj = (List) httpServletRequest.getSession().getServletContext().getAttribute(PatternConst.CONTEXT_COMMAND_DAY_LIST);
            HashMap<String, String> manualDevice = (HashMap<String, String>) httpServletRequest.getSession().getServletContext().getAttribute(PatternConst.CONTEXT_MANUAL_PATTERN_DEVICE);
            if (manualDevice == null) {
                manualDevice = new HashMap<String, String>();
            }

            String specClass = httpServletRequest.getParameter("specClass");
            String classType = httpServletRequest.getParameter("classType");
            //通过tagId转为设备ID;
            List<Asset> asset = patternService.findBySpecclassAndClassstructureidAndSiteid(specClass, classType, "");

            UserInfo user = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            if (obj != null) {
                // TriggerKey triggerKey = new TriggerKey("getDayCommand");
                //todo 此处需处理 自动的线程，考虑界面的随意点击。目前不适合暂停
                // scheduler.pauseTrigger(triggerKey);
                if (asset != null) {
                    //将此设备的其它运行命令去除。 保持手动状态
                    logger.debug("------manualPatternRun-if--deviceIds------" + asset.size());
                    for (Asset asset1 : asset) {
                        //将设备加入手动列表
                        manualDevice.put(asset1.getAssetnum(), asset1.getAssetnum());

                        //将此设备的命令设置成手动状态
                        for (DeviceCommand deviceCommand : obj) {
                            if (deviceCommand.getDeviceId().equals(asset1.getAssetnum())) {
                                deviceCommand.setExecuteStatus(PatternConst.DEVICE_EXECUTE_COVER);
                            }
                        }
                    }
                    httpServletRequest.getSession().getServletContext().setAttribute(PatternConst.CONTEXT_MANUAL_PATTERN_DEVICE, manualDevice);
                }
                logger.info(user.getLoginid() + "手动切换模式");
                opLogService.saveLogByUserAndMsg(user.getLoginid(), "手动切换模式", "", specClass, "PatternController", PatternConst.LOG_TYPE_PATTERN);
            }
            //将此手动
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
     * 手动切换模式里模式的显示的时间段
     *
     * @return
     */
    @RequestMapping(value = "/pattern/showPatternRunTime")
    @ResponseBody
    public Object showPatternRunTime(Model model, HttpServletRequest httpServletRequest) {

        SystemMessage message = new SystemMessage();
        try {

            String systemCode = httpServletRequest.getParameter("systemCode");

            logger.debug("------showPatternRunTime---------" + systemCode);
            //patternService.setCSButtonStyle(systemCode);   改为BS，不需要设置了。
            //将此手动
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
}
