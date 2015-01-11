package com.energicube.eno.monitor.web;

import com.energicube.eno.asset.model.Asset;
import com.energicube.eno.asset.model.ClassStructure;
import com.energicube.eno.calendar.model.UcWeather;
import com.energicube.eno.common.Config;
import com.energicube.eno.common.Const;
import com.energicube.eno.common.PatternConst;
import com.energicube.eno.common.SendCommandToSystem;
import com.energicube.eno.common.dto.DeviceCommand;
import com.energicube.eno.common.dto.SystemMessage;
import com.energicube.eno.common.dto.lspub.AssetGroupDTO;
import com.energicube.eno.common.dto.lspub.LspubDTO;
import com.energicube.eno.message.redis.CommandInfo;
import com.energicube.eno.message.redis.RedisOpsService;
import com.energicube.eno.monitor.model.*;
import com.energicube.eno.monitor.service.*;
import com.energicube.eno.pattern.model.UcDeviceSystem;
import com.energicube.eno.pattern.model.UcPattern;
import com.energicube.eno.pattern.model.UcPatternAction;
import com.energicube.eno.pattern.model.UcPatternRecord;
import com.energicube.eno.pattern.service.PatternService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 监测与控制页面控制
 */
@Controller
public class MonitorCtrlController extends BaseController {

    private static final Log logger = LogFactory.getLog(MonitorCtrlController.class);

    @Autowired
    private PagelayoutService pagelayoutService;

    @Autowired
    private PagetagService pagetagService;

    @Autowired
    PatternService patternService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private SyscontrolService syscontrolService;

    @Autowired
    private ExpressionService expressionService;

    @Autowired
    private RedisOpsService redisOpsService;

    private Config config = new Config();

    private SimpleDateFormat simpleDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

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

        //System.out.println("currOwnerEle:" + currOwnerEle + ",filterMenus Size:"+ filterMenus.size());

        return filterMenus;
    }


    /**
     * 监测与控制页面控制首页（子系统概要页）
     */
    @RequestMapping(value = "/mctrl", method = RequestMethod.GET)
    public String initMctrlView(Model model) {
        List<OkcMenu> menus = menuService.findAll();
        model.addAttribute("menus", menus);
        return "mctrl/monitorsum";
    }

    /**
     * 监测与控制页面控制首页（子系统概要页）
     */
    @RequestMapping(value = "/mctrl/monitorsum", method = RequestMethod.GET)
    public String initMonitorSumView(Model model) {
        return "mctrl/monitorsum";
    }


    /**
     * 监测与控制子系统 <br />
     * 通过元素类型、元素值以及菜单类型获取子系统对应的子菜单,并显示子菜单对应的画面内容
     * menutype: APPFILTER|ASC
     * ownerelement: HVAC
     *
     * @param elementvalue 元素值,如HVAC
     */
    @RequestMapping(value = "/mctrl/{elementvalue}", method = RequestMethod.GET)
    public String showSubSystem(@PathVariable("elementvalue") String elementvalue, Model model) {

        // if (PatternConst.SYSTEM_CODE_LSPUB.equals(elementvalue) || PatternConst.SYSTEM_CODE_LSN.equals(elementvalue)) {
        //设置C/S界面的按钮的样式  改为BS，不需要设置了。
        //   patternService.setCSButtonStyle(elementvalue);
        //}

        //定义子菜单集合
        List<OkcMenuSet> ascChildrenItems = new ArrayList<OkcMenuSet>();  //设备列表
        List<OkcMenuSet> ascCompnChildrenItems = new ArrayList<OkcMenuSet>();  //设备组结构图
        List<OkcMenuSet> ascPlanarChildrenItems = new ArrayList<OkcMenuSet>();  //设备组平面图
        //子菜单对应的你菜单项
        OkcMenu parentMenuItem = new OkcMenu();
        //定义菜单类型
        String ascMenutype = "APPFILTER|ASC";
        String ascCompnMenutype = "APPFILTER|ASCCOMPN";
        String ascPlanarMenutype = "APPFILTER|ASCPLANAR";


        //获取对应的子菜单集合
        if (StringUtils.hasLength(elementvalue)) {
            ascChildrenItems = menuService.findOkcMenuSetList(ascMenutype, elementvalue);
            ascCompnChildrenItems = menuService.findOkcMenuSetList(ascCompnMenutype, elementvalue);
            ascPlanarChildrenItems = menuService.findOkcMenuSetList(ascPlanarMenutype, elementvalue);
            parentMenuItem = menuService.findMenuItem("APPFILTER", "ASC", elementvalue);
        }

        //是否存在设备列表
        boolean hasAsc = ascChildrenItems.size() > 0;
        //是否存在结构图
        boolean hasCompn = ascCompnChildrenItems.size() > 0;
        //是否存在平面图
        boolean hasPlanar = ascPlanarChildrenItems.size() > 0;

        //显示设备列表
        boolean showAsc = ascChildrenItems.size() > 0;

        //显示结构图列表
        boolean showCompn = ascChildrenItems.size() == 0 && ascCompnChildrenItems.size() > 0;

        boolean showPlanar = ascChildrenItems.size() == 0 && ascCompnChildrenItems.size() == 0 && ascPlanarChildrenItems.size() > 0;


        model.addAttribute("ascChildrenItems", ascChildrenItems);
        model.addAttribute("ascCompnChildrenItems", ascCompnChildrenItems);
        model.addAttribute("ascPlanarChildrenItems", ascPlanarChildrenItems);
        model.addAttribute("parentMenuItem", parentMenuItem);
        model.addAttribute("elementvalue", elementvalue);

        model.addAttribute("hasAsc", hasAsc);
        model.addAttribute("hasCompn", hasCompn);
        model.addAttribute("hasPlanar", hasPlanar);

        model.addAttribute("showAsc", showAsc);
        model.addAttribute("showCompn", showCompn);
        model.addAttribute("showPlanar", showPlanar);

        return "mctrl/subsys";
    }


    /**
     * 监测与控制子系统 <br />
     * 通过元素类型、元素值以及菜单类型获取子系统对应的子菜单,并显示子菜单对应的画面内容
     * menutype: APPFILTER|ASC
     * ownerelement: HVAC
     *
     * @param elementvalue 元素类型,如 ASC
     * @param menuid       菜单值,如HVAC
     */
    @RequestMapping(value = "/mctrl/{elementvalue}/{menuid}", method = RequestMethod.GET)
    public String showSubSystemView(@PathVariable("elementvalue") String elementvalue,
                                    @PathVariable("menuid") String menuid, @RequestParam(value = "pageindex", required = false, defaultValue = "0") Integer pageindex, Model model) {

        //定义子菜单集合
        List<OkcMenuSet> ascChildrenItems = new ArrayList<OkcMenuSet>();  //设备列表
        List<OkcMenuSet> ascCompnChildrenItems = new ArrayList<OkcMenuSet>();  //设备组结构图
        List<OkcMenuSet> ascPlanarChildrenItems = new ArrayList<OkcMenuSet>();  //设备组平面图
        //子菜单对应的你菜单项
        OkcMenu parentMenuItem = new OkcMenu();
        //定义菜单类型
        String ascMenutype = "APPFILTER|ASC";
        String ascCompnMenutype = "APPFILTER|ASCCOMPN";
        String ascPlanarMenutype = "APPFILTER|ASCPLANAR";


        //获取对应的子菜单集合
        if (StringUtils.hasLength(elementvalue)) {
            ascChildrenItems = menuService.findOkcMenuSetList(ascMenutype, elementvalue);
            ascCompnChildrenItems = menuService.findOkcMenuSetList(ascCompnMenutype, elementvalue);
            ascPlanarChildrenItems = menuService.findOkcMenuSetList(ascPlanarMenutype, elementvalue);
            parentMenuItem = menuService.findMenuItem("APPFILTER", "ASC", elementvalue);
        }

        //是否存在设备列表
        boolean hasAsc = ascChildrenItems.size() > 0;
        //是否存在结构图
        boolean hasCompn = ascCompnChildrenItems.size() > 0;
        //是否存在平面图
        boolean hasPlanar = ascPlanarChildrenItems.size() > 0;

        //显示设备列表
        boolean showAsc = ascChildrenItems.size() > 0;

        //显示结构图列表
        boolean showCompn = ascChildrenItems.size() == 0 && ascCompnChildrenItems.size() > 0;

        boolean showPlanar = ascChildrenItems.size() == 0 && ascCompnChildrenItems.size() == 0 && ascPlanarChildrenItems.size() > 0;


        model.addAttribute("ascChildrenItems", ascChildrenItems);
        model.addAttribute("ascCompnChildrenItems", ascCompnChildrenItems);
        model.addAttribute("ascPlanarChildrenItems", ascPlanarChildrenItems);
        model.addAttribute("parentMenuItem", parentMenuItem);
        model.addAttribute("elementvalue", elementvalue);

        model.addAttribute("hasAsc", hasAsc);
        model.addAttribute("hasCompn", hasCompn);
        model.addAttribute("hasPlanar", hasPlanar);

        model.addAttribute("showAsc", showAsc);
        model.addAttribute("showCompn", showCompn);
        model.addAttribute("showPlanar", showPlanar);


        //通过MenuID获取菜单对应的布局列表。
        Long lmenuid = 0l;
        List<Pagelayout> pagelayouts = null;
        OkcMenu currOkcMenu = null;
        Pagelayout currentPagelayout = new Pagelayout();
        if (StringUtils.hasLength(menuid) && NumberUtils.isNumber(menuid)) {
            lmenuid = NumberUtils.toLong(menuid);
            pagelayouts = pagelayoutService.findLayoutsByMenuid(menuid);
            currOkcMenu = menuService.findByMenuid(lmenuid);
            currentPagelayout = pagelayoutService.findLayoutsByMenuid(menuid, pageindex);
        }

        //布局列表
        model.addAttribute("pagelayouts", pagelayouts);
        model.addAttribute("menuid", lmenuid); //当前菜单ID
        model.addAttribute("okcMenu", currOkcMenu);  //当前菜单对象
        logger.debug("current menu object:" + currOkcMenu);

        //菜单对应的总页数
        int totalPages = pagelayouts.size();

        List<Pagelayout> listViews = new ArrayList<Pagelayout>();
        List<Pagelayout> structureViews = new ArrayList<Pagelayout>();
        List<Pagelayout> planViews = new ArrayList<Pagelayout>();

        Pagelayout currentListPagelayout = new Pagelayout();
        Pagelayout currentStructurePagelayout = new Pagelayout();
        Pagelayout currentPlanPagelayout = new Pagelayout();

        //列表视图
        for (int i = 0; i < pagelayouts.size(); i++) {
            if (StringUtils.hasLength(pagelayouts.get(i).getListbg())) {
                listViews.add(pagelayouts.get(i));
            }
        }

        if (listViews != null && listViews.size() > 0) {
            currentListPagelayout = listViews.size() > 1 ? currentPagelayout : listViews.get(0);
        }


        //结构视图列表
        for (int j = 0; j < pagelayouts.size(); j++) {
            if (StringUtils.hasLength(pagelayouts.get(j).getBackground())) {
                structureViews.add(pagelayouts.get(j));
            }
        }

        if (structureViews != null && structureViews.size() > 0) {
            currentStructurePagelayout = structureViews.size() > 1 ? currentPagelayout : structureViews.get(0);
        }

        //平面视图列表
        for (int k = 0; k < pagelayouts.size(); k++) {
            if (StringUtils.hasLength(pagelayouts.get(k).getPlanbg())) {
                planViews.add(pagelayouts.get(k));
            }
        }

        if (planViews != null && planViews.size() > 0) {
            currentPlanPagelayout = planViews.size() > 1 ? currentPlanPagelayout : planViews.get(0);
        }

        //获取表达式集合
        List<Expression> expressions = new ArrayList<Expression>();
        expressions = expressionService.findAllExp();

        model.addAttribute("exps", expressions);
        model.addAttribute("listViews", listViews);
        model.addAttribute("structureViews", structureViews);
        model.addAttribute("planViews", planViews);
        model.addAttribute("currentListPagelayout", currentListPagelayout);
        model.addAttribute("currentStructurePagelayout", currentStructurePagelayout);
        model.addAttribute("currentPlanPagelayout", currentPlanPagelayout);
        model.addAttribute("currentlayout", pagelayouts.get(0));
        model.addAttribute("layoutid", pagelayouts.get(0).getLayoutid());
        //面板控件增添到控件配置中，此处以下为判断pagetag的setting字段（原存放面板）
/*        List<Pagetag> pagetagList=pagetagService.findByLayoutid(pagelayouts.get(0).getLayoutid());
        Set<String> set=new HashSet<String>();
        int i=0;
        for(Pagetag p:pagetagList){
        	set.add(p.getSetting());
        	i++;
        }
        if(set.size()==0){//没有面板组件
        	model.addAttribute("syscontrol", null);
        }else if(set.size()==1&&i==pagetagList.size()){//子页面只有一个面板组件
        	String st=pagetagList.get(0).getSetting();
			if (st != null && !"null".equals(st) && StringUtils.hasLength(st)) {
        		//Syscontrol syscontrol=syscontrolService.findOne(Integer.parseInt(st));
        		//JSONArray array = JSONArray.fromObject(syscontrol.getSettting()); 
				Syscontrol syscontrol = new Syscontrol();
				JSONArray array = JSONArray.fromObject(st);
				for (int j = 0; j < array.size(); j++) {
					JSONObject jsonObject = array.getJSONObject(j);
					if (jsonObject.getString("name").equals("editorValue")) {
						String editorValue = jsonObject.getString("value");
						syscontrol.setSettting(editorValue.replace("\"", "\'"));
						model.addAttribute("syscontrol", syscontrol);
					}
				}
        	}
        }
*/
        return "mctrl/mctrl";
    }

    /**
     * 点击设备后对应的面板属性组件
     *
     * @param pagetagid
     * @param request
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/mctrl/panelBackground/{pagetagid}", method = RequestMethod.POST, produces = "plain/text; charset=utf-8")
    @ResponseBody
    public String changePanelBackground(@PathVariable("pagetagid") long pagetagid, HttpServletRequest request) throws UnsupportedEncodingException {
        String msg = "";
        try {

            Pagetag pt = pagetagService.findOne(pagetagid);
            //Syscontrol syscontrol=syscontrolService.findOne(Integer.parseInt(pt.getSetting()));
            if (StringUtils.hasLength(pt.getSetting())) {
                ObjectMapper objectMapper = new ObjectMapper();
                ArrayList list = objectMapper.readValue(pt.getSetting(), ArrayList.class);
//            JSONArray array = JSONArray.fromObject(pt.getSetting());
                for (int j = 0; j < list.size(); j++) {
                    HashMap map = objectMapper.readValue(list.get(j).toString(), HashMap.class);
                    if ("editorValue".equals(map.get("name").toString())) {
                        msg = map.get("value").toString();
                    }
                }
            }

        } catch (Exception e) {
            logger.error("changePanelBackground", e);
        }
        return msg;
    }


    /**
     * 查询公共照明的设备
     *
     * @return
     */
    @RequestMapping(value = "/mctrl/getLspubGroup")
    @ResponseBody
    public Object getLspubGroup(HttpServletRequest httpServletRequest) {
        String location = httpServletRequest.getParameter("location");
        String systemCode = httpServletRequest.getParameter("systemCode");
        List<DeviceCommand> deviceCommandList = (List<DeviceCommand>) httpServletRequest.getSession().getServletContext().getAttribute(PatternConst.CONTEXT_COMMAND_DAY_LIST);
        //排序
        orderCommandAsc(deviceCommandList);

        UcDeviceSystem ucDeviceSystem = patternService.findDeviceSystemByCode(systemCode);
        String code = ucDeviceSystem.getSystemClass();
        long nowDate = System.currentTimeMillis();

        List<LspubDTO> lspubDTOs = new ArrayList<LspubDTO>();

        if (code != null && !"".equals(code)) {
            StringTokenizer stringTokenizer = new StringTokenizer(code, ",");
            while (stringTokenizer.hasMoreTokens()) {
                String system = stringTokenizer.nextToken();
                //查询设备 根据specclass 、层(模糊)
                List<Asset> assetList = patternService.findByLikeLocationAndSpecclassAndClassstructureidAndSiteid("%" + location.trim() + "%", systemCode, system, "");

                for (Asset asset : assetList) {

                    String local = asset.getLocation();
                    LspubDTO lspubDTO = new LspubDTO();
                    lspubDTO.setAssetName(asset.getDescription());
                    lspubDTO.setAssetNum(asset.getAssetnum());
                    lspubDTO.setClassstructureid(asset.getClassstructureid());
                    lspubDTO.setLocation(asset.getLocation());
                    lspubDTO.setSpecclass(asset.getSpecclass());

                    for (DeviceCommand deviceCommand : deviceCommandList) {
                        logger.debug("-----getLspubGroup---" + lspubDTO.getAssetNum() + "----" + deviceCommand.getDeviceId());
                        //在已经找出的命令里不存在此设备的命令。避免过多已经执行的命令被加入
                        if (lspubDTO.getAssetNum().equals(deviceCommand.getDeviceId())) {

                            long a = deviceCommand.getSendTime().getTime();
                            logger.debug("-----getLspubGroup----" + a + "----" + nowDate);
                            if (a < nowDate) {//小于当前时间的第一条命令
                                logger.debug("------getLspubGroup-----" + lspubDTO.getAssetNum() + "----" + nowDate);
                                //是否开关
                                //todo 手动的设备显示的 状态会不准确
                                lspubDTO.setStatus(deviceCommand.getStatus());
                                break;
                            }
                        }
                    }
                    //设备的坐标
                    int a = local.length();
                    String area = local.substring(a - 1, a);
                    //取标识
                    String name = asset.getDescription();
                    int b = name.length();
                    String end = name.substring(b - 2, b);

                    if ((end.charAt(0) >= 'A' && end.charAt(0) <= 'Z') || (end.charAt(0) >= 'a' && end.charAt(0) <= 'z')) {
                        String coordinate = config.getProps().getProperty(location + "_" + area + "_" + end);
                        lspubDTO.setCoordinate(coordinate);
                    } else {
                        end = end.substring(1, 2);
                        String coordinate = config.getProps().getProperty(location + "_" + area + "_" + end);
                        lspubDTO.setCoordinate(coordinate);
                    }
                    lspubDTOs.add(lspubDTO);
                }
            }
        }
        return lspubDTOs;
    }

    /**
     * 对列表进行按时间排序进行 降序排列
     */
    private List<DeviceCommand> orderCommandAsc(List<DeviceCommand> baseCommands) {
        if (baseCommands == null || baseCommands.size() == 0)
            return baseCommands;
        Collections.sort(baseCommands, new Comparator<DeviceCommand>() {
            public int compare(DeviceCommand arg0, DeviceCommand arg1) {
                return arg1.getSendTime().compareTo(arg0.getSendTime());
            }
        });
        return baseCommands;
    }

    /**
     * 对列表进行按时间排序 升序
     *
     * @param baseCommands 命令列表
     * @return
     */
    private List<DeviceCommand> orderCommand(List<DeviceCommand> baseCommands) {
        if (baseCommands == null || baseCommands.size() == 0)
            return baseCommands;
        Collections.sort(baseCommands, new Comparator<DeviceCommand>() {
            public int compare(DeviceCommand arg0, DeviceCommand arg1) {
                return arg0.getSendTime().compareTo(arg1.getSendTime());
            }
        });
        return baseCommands;
    }

    /**
     * 根据组得到此组的所有真实设备
     *
     * @param httpServletRequest
     * @return 设备列表
     */
    @RequestMapping(value = "/mctrl/getAssetByClassstructureid", method = RequestMethod.POST)
    @ResponseBody
    public Object getAssetByClassstructureid(HttpServletRequest httpServletRequest) {
        //根据选择的设备虚拟组查询此组下的设备。
        String groupId = httpServletRequest.getParameter("groupId");
        String systemCode = httpServletRequest.getParameter("systemCode");
        String location = httpServletRequest.getParameter("location");  //层
        //List<Asset> assetList=patternService.findBySpecclassAndClassstructureidAndSiteid(systemCode,groupId,"");
        List<Asset> assetList = patternService.findByLikeLocationAndSpecclassAndClassstructureidAndSiteid("%" + location.trim() + "%", systemCode, groupId, "");
        return assetList;
    }

    /**
     * 得到所有设备组
     *
     * @param httpServletRequest
     * @return 设备列表
     */
    @RequestMapping(value = "/mctrl/getAssetGroup", method = RequestMethod.POST)
    @ResponseBody
    public Object getAssetGroup(HttpServletRequest httpServletRequest) {
        //根据选择的设备虚拟组查询此组下的设备。
        String systemCode = httpServletRequest.getParameter("systemCode");
        String location = httpServletRequest.getParameter("location");  //层
        List<DeviceCommand> deviceCommandList = (List<DeviceCommand>) httpServletRequest.getSession().getServletContext().getAttribute(PatternConst.CONTEXT_COMMAND_DAY_LIST);
        List<DeviceCommand> deviceCommands = (List<DeviceCommand>) httpServletRequest.getSession().getAttribute(PatternConst.CONTEXT_COMMAND_OPEN);
        //排序
        deviceCommandList = orderCommandAsc(deviceCommandList);

//        String area = httpServletRequest.getParameter("area");  //区
        long nowDate = System.currentTimeMillis();
        String classStructure = config.getProps().getProperty(systemCode + ".CLASS");
        List<ClassStructure> classStructures = patternService.findClassStructuresByParent(classStructure);
        List<AssetGroupDTO> assetGroupDTOList = new ArrayList<AssetGroupDTO>();

        for (ClassStructure classStructure1 : classStructures) {

            for (int i = 1; i <= 6; i++) {

                AssetGroupDTO assetGroupDTO = new AssetGroupDTO();
                assetGroupDTO.setSpecclass(systemCode);
                assetGroupDTO.setLocation(location.trim() + "00" + i);
                assetGroupDTO.setClassstructureid(classStructure1.getClassstructureid());

                // List<Asset> assetList=patternService.findBySpecclassAndClassstructureidAndSiteid(systemCode,classStructure1.getClassstructureid(),"");
                List<Asset> assetList = patternService.findByLikeLocationAndSpecclassAndClassstructureidAndSiteid("%" + location.trim() + "00" + i + "%", systemCode, classStructure1.getClassstructureid(), "");
                Asset asset = null;
                if (assetList != null && assetList.size() > 0) {
                    asset = assetList.get(0); //选择其中一个设备来判断是否开着，这样有可能这个设备处于手动，不够准确。
                }
                if (asset != null) {
                    if (deviceCommands != null && deviceCommands.size() > 0) {
                        for (DeviceCommand deviceCommand : deviceCommands) {
                            //在已经找出的命令里不存在此设备的命令。避免过多已经执行的命令被加入
                            if (asset.getAssetnum() != null && asset.getAssetnum().equals(deviceCommand.getDeviceId())) {
                                //todo 手动的设备显示的 状态会不准确
                                assetGroupDTO.setStatus(PatternConst.getSwitch(deviceCommand.getStatus()));
                            }
                        }
                    } else {
                        for (DeviceCommand deviceCommand : deviceCommandList) {
                            //在已经找出的命令里不存在此设备的命令。避免过多已经执行的命令被加入
                            if (asset.getAssetnum() != null && asset.getAssetnum().equals(deviceCommand.getDeviceId())) {

                                long a = deviceCommand.getSendTime().getTime();
                                // logger.debug("-----getAssetGroup--true--+"+deviceCommand.getStatus()+":---" + simpleDateTimeFormat.format(deviceCommand.getSendTime()) + "------"+asset.getAssetnum());
                                if (a < nowDate) {//小于当前时间的第一条命令
                                    //是否开关
                                    //todo 手动的设备显示的 状态会不准确
                                    assetGroupDTO.setStatus(PatternConst.getSwitch(deviceCommand.getStatus()));
                                    break;
                                }
                            }
                        }
                    }
                }

                //坐标
                String name = classStructure1.getDescription();
                int b = name.length();
                String end = name.substring(b - 2, b);

                if ((end.charAt(0) >= 'A' && end.charAt(0) <= 'Z') || (end.charAt(0) >= 'a' && end.charAt(0) <= 'z')) {
                    String coordinate = config.getProps().getProperty(location + "_" + i + "_" + end);
                    assetGroupDTO.setCoordinate(coordinate);
                    assetGroupDTO.setEnName(end);
                } else {
                    end = end.substring(1, 2);
                    String coordinate = config.getProps().getProperty(location + "_" + i + "_" + end);
                    assetGroupDTO.setCoordinate(coordinate);
                    assetGroupDTO.setEnName(end);
                }
                assetGroupDTO.setGroupName(classStructure1.getDescription());
                assetGroupDTOList.add(assetGroupDTO);
            }
        }
        httpServletRequest.getSession().setAttribute(PatternConst.CONTEXT_COMMAND_OPEN, new ArrayList<DeviceCommand>());
        return assetGroupDTOList;
    }

    /**
     * 得到运行的模式
     * 根据子系统编码查询出此子系统的内置模式
     *
     * @param httpServletRequest
     * @return 只返回内置模式
     */
    @RequestMapping(value = "/mctrl/getSystemPattern", method = RequestMethod.POST)
    @ResponseBody
    public Object getSystemPattern(HttpServletRequest httpServletRequest) {
        //根据子系统编码查询出此子系统的内置模式。
        String systemCode = httpServletRequest.getParameter("systemCode");
        List<UcPattern> ucPatternList = patternService.findUcPatternBySystem(systemCode, PatternConst.PATTERN_TYPE_SYSTEM, PatternConst.PATTERN_ORDER_TYPE_TIME);

        return ucPatternList;
    }

    /**
     * 设置模式的手动的变更(照明地方的各个时间段的设置)
     * 执行相应的命令，考虑自动的运行的模式的改变
     *
     * @param httpServletRequest
     * @return 是否成功
     */
    @RequestMapping(value = "/mctrl/setPatternManual", method = RequestMethod.POST)
    @ResponseBody
    public Object setPatternManual(HttpServletRequest httpServletRequest) {

        String patternId = httpServletRequest.getParameter("patternId");
        String actionType = httpServletRequest.getParameter("actionType");
        SystemMessage message = new SystemMessage();
        try {
            HashMap<String, DeviceCommand> templateCommand = (HashMap<String, DeviceCommand>) httpServletRequest.getSession().getServletContext().getAttribute(PatternConst.CONTEXT_COMMAND_TEMPLATE);
            List<DeviceCommand> deviceCommands = patternService.findDeviceCommandByPatternAndActionType(patternId, actionType, templateCommand);
            //执行命令
            for (DeviceCommand command : deviceCommands) {
                SendCommandToSystem.sendCommand(command);
            }
            //用于前台的显示哪些设备组高亮
            httpServletRequest.getSession().setAttribute(PatternConst.CONTEXT_COMMAND_OPEN, deviceCommands);
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
     * 设置模式的按钮的开关状态及设备组的开关状态
     *
     * @param httpServletRequest
     * @return 是否成功
     */
    @RequestMapping(value = "/mctrl/setPatternButton")
    @ResponseBody
    public Object setPatternButton(HttpServletRequest httpServletRequest) {

        String systemCode = httpServletRequest.getParameter("systemCode");
        SystemMessage message = new SystemMessage();
        String actionType = "";
        String patternId = "";
        try {
            logger.debug("-------patternType-----" + systemCode);
            List<UcPatternRecord> ucPatternRecordList = patternService.findUcPatternRecordBySystemIdAndDateLazy(systemCode, DateTime.now().toString("yyyyMMdd"));
            for (UcPatternRecord ucPatternRecord : ucPatternRecordList) {
                String patternType = ucPatternRecord.getPatternType();
                logger.debug("-------patternType-----" + patternType);
                if (patternType.equals(PatternConst.PATTERN_TYPE_PREDICT) || patternType.equals(PatternConst.PATTERN_TYPE_SYSTEM) || patternType.equals(PatternConst.PATTERN_TYPE_CUSTOM)) { //已经有预设或是已经保存模式到记录里都算是已经知道运行什么模式了。
                    UcPattern ucPattern = patternService.findUcPatternById(ucPatternRecord.getPatternId());
                    patternId = ucPatternRecord.getPatternId();
                    actionType = PatternConst.PATTERN_ACTION_CLOSE;
                    Set<UcPatternAction> ucPatternActionSet = ucPattern.getUcPatternActions();
                    if (PatternConst.SYSTEM_CODE_LSN.equals(systemCode)) {  //夜景模式有日出、日落的时间
                        List<UcPatternAction> list = new ArrayList<UcPatternAction>();
                        UcWeather ucWeather = patternService.findWeatherByCalendarAndNew(DateTime.now().toString("yyyyMMdd"), PatternConst.WEATHER_IS_NEW_YES);
                        //  logger.debug("-------patternType---1--" + patternType);
                        for (UcPatternAction ucPatternAction : ucPatternActionSet) {
                            int base = ucPatternAction.getBaseTime();
                            //  logger.debug("-------for---------" + base + "----" + ucPatternAction.getId() + "------" + ucPatternAction.getOffsetTime());
                            // 考虑基础时间是日升、日落的情况
                            if (base < 0) {
                                String sunTime = ucWeather.getSunrise();
                                if (base == PatternConst.SUNSET) {
                                    sunTime = ucWeather.getSunset();
                                }
                                int baseTime = DateTime.parse(sunTime, DateTimeFormat.forPattern("HH:mm")).getMinuteOfDay();
                                baseTime = baseTime + ucPatternAction.getOffsetTime();
                                ucPatternAction.setBaseTime(baseTime);
                            } else {
                                int time = ucPatternAction.getBaseTime() + ucPatternAction.getOffsetTime();
                                ucPatternAction.setBaseTime(time);
                            }
                            list.add(ucPatternAction);
                        }

                        //进行升序排序
                        Collections.sort(list, new Comparator<UcPatternAction>() {
                            public int compare(UcPatternAction arg0, UcPatternAction arg1) {
                                return arg0.getBaseTime().compareTo(arg1.getBaseTime());
                            }
                        });


                        //找到正在执行的阶段
                        int nowTime = DateTime.now().getMinuteOfDay();
                        for (UcPatternAction ucPatternAction : list) {
                            if (nowTime > ucPatternAction.getBaseTime()) {
                                actionType = ucPatternAction.getActionType();
                            } else {
                                break;
                            }
                        }

                        message.setActionType(actionType);
                        message.setPatternId(patternId);
                        message.setSuccess(true);
                        message.setMsg(PatternConst.JSON_SUCCESS);
                        return message;
                    } else {

                        for (UcPatternAction ucPatternAction : ucPatternActionSet) {
                            //此方法是当前时间大于等于action里的时间，所以依次循环，取第一次不成立的上一次的数据为当前开关状态
                            boolean bool = patternService.findCurrentStage(ucPatternAction);
                            if (bool) {
                                actionType = ucPatternAction.getActionType();
                            } else {
                                //从0开始向后排序，所以取第一次非真。
                                message.setActionType(actionType);
                                message.setPatternId(patternId);
                                message.setSuccess(true);
                                message.setMsg(PatternConst.JSON_SUCCESS);
                                return message;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("-----------", e);
        }
        message.setSuccess(false);
        message.setMsg(PatternConst.JSON_FAILURE);
        return message;
    }

    /**
     * 设置设备的开关
     * //todo 只用于照明设备
     *
     * @param httpServletRequest
     * @return 是否成功
     */
    @RequestMapping(value = "/mctrl/setSingleAssetStatus", method = RequestMethod.POST)
    @ResponseBody
    public Object setSingleAssetStatus(HttpServletRequest httpServletRequest) {

        String assetIds = httpServletRequest.getParameter("assetIds");
        String status = httpServletRequest.getParameter("status");
        List<DeviceCommand> deviceCommandList = (List<DeviceCommand>) httpServletRequest.getSession().getServletContext().getAttribute(PatternConst.CONTEXT_COMMAND_DAY_LIST);
        SystemMessage message = new SystemMessage();
        try {
            StringTokenizer stringTokenizer = new StringTokenizer(assetIds, ",");
            while (stringTokenizer.hasMoreTokens()) {
                String assetId = stringTokenizer.nextToken();
                for (DeviceCommand command : deviceCommandList) {
                    if (command.getDeviceId().equals(assetId)) {   //相同的设备
                        List<DeviceCommand> deviceCommandList1 = command.getParams();

                        String url = config.getProps().getProperty("sendCommandUrl");
                        for (DeviceCommand command1 : deviceCommandList1) {

                            if (command1.getParamName().equals(PatternConst.PATTERN_COMMAND_SWITCH)) {//取开关状态的 tagID
                                logger.debug(command1.getTagId() + "----------" + status);
                                if (status != null && status.equals(PatternConst.PATTERN_COMMAND_SWITCH_Y)) {
                                    status = PatternConst.PATTERN_COMMAND_SWITCH_OPEN;  //开
                                } else {
                                    status = PatternConst.PATTERN_COMMAND_SWITCH_CLOSE; //关
                                }
                                String cmd = "tagid=" + command1.getTagId() + "&value=" + status + "&jsoncallback=?";
                                SendCommandToSystem.sendCommand(url, cmd);
                                break;
                            }
                        }
                        break;
                    }
                }
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
     * 添加面板属性id编号
     *
     * @param layoutid
     * @param pagetagid
     * @param setting
     * @param model
     * @return
     */
    @RequestMapping(value = "/mctrl/changePagetegSettting")
    public String changePagetegSettting(String layoutid, String pagetagid, String setting, Model model) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {

            if (StringUtils.hasLength(pagetagid)) {
                Pagetag p = pagetagService.findOne(Long.parseLong(pagetagid));
                ArrayList list = objectMapper.readValue(p.getSetting(), ArrayList.class);
                for (int j = 0; j < list.size(); j++) {
                    HashMap map = objectMapper.readValue(list.get(j).toString(), HashMap.class);
                    if ("editorValue".equals(map.get("name"))) {
                        map.put("value", setting);
                    }
                }
                p.setSetting(objectMapper.writeValueAsString(list));
                pagetagService.savePagetag(p);
            } else {
                List<Pagetag> ps = pagetagService.findByLayoutid(layoutid);
                for (Pagetag p : ps) {
                    ArrayList list = objectMapper.readValue(p.getSetting(), ArrayList.class);
                    for (int j = 0; j < list.size(); j++) {
                        HashMap map = objectMapper.readValue(list.get(j).toString(), HashMap.class);
                        if ("editorValue".equals(map.get("name"))) {
                            map.put("value", setting);
                        }
                    }
                    p.setSetting(objectMapper.writeValueAsString(list));
                    pagetagService.savePagetag(p);
                }
            }
        } catch (Exception e) {
            logger.error("changePagetegSettting", e);
        }
        return null;
    }

    /**
     * 保存页面面板配置的表达式
     *
     * @param panelcode 格式：[taguid:xxx,express:[{id:xxx,exp:xxx},{...},{...}]]
     * @return
     * @author ztl
     */
    @RequestMapping(value = "/okcsys/savepanel", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public boolean processSavepanel(String panelcode) {
        try {
        	JSONArray jsonArray =  (JSONArray) JSONValue.parse(panelcode);
        	JSONObject jsonObject = (JSONObject) jsonArray.get(0);
        	Pagetag pagetag = pagetagService.findOne(Long.valueOf(jsonObject.get("taguid").toString()));
        	pagetag.setExpressions(jsonObject.get("express").toString());
        	pagetagService.savePagetag(pagetag);
    		return true;

        } catch (Exception e) {
            logger.error("processSavepanel", e);
        }
        return false;
    }

    /**
     * 得到运行的模式
     * 根据子系统编码查询出此子系统的内置模式
     *
     * @param httpServletRequest
     * @return 只返回内置模式
     */
    @RequestMapping(value = "/msum/getMonitorSumData", method = RequestMethod.GET)
    @ResponseBody
    public Object getMonitorSumData(HttpServletRequest httpServletRequest) {
        Map<String,Object> retMap = new HashMap<String,Object>();
        List<Map<String, String>> retList = new ArrayList<Map<String, String>>();
        //读取配置文件
        Properties monitorSumConfig = new Properties();
        Map<String, String> expPropertiesMap = new HashMap<String, String>();
        Resource resource = new ClassPathResource("/properties/monitorsum.properties");
        EncodedResource encodedResource = new EncodedResource(resource, "UTF-8");
        try {
            monitorSumConfig = PropertiesLoaderUtils.loadProperties(encodedResource);
        } catch (IOException e1) {
            e1.printStackTrace();
            logger.error("读取/properties/monitorsum.properties文件失败!", e1);
        }

        List<String> expList = new ArrayList<String>();

        for (String name : monitorSumConfig.stringPropertyNames()) {
            if (name.startsWith("Exp")) {
                //把变量对应的id发送给Redis
                String tagsValkey = Const.TAGS_VAL + ":" + monitorSumConfig.getProperty(name);
                expPropertiesMap.put(monitorSumConfig.getProperty(name), name);
                expList.add(tagsValkey);
            } else {
                //静态数据直接显示
                Map<String, String> monitorSumDataMap = new HashMap<String, String>();
                monitorSumDataMap.put("name", name);
                monitorSumDataMap.put("value", monitorSumConfig.getProperty(name));
                retList.add(monitorSumDataMap);
            }
        }

        Map<String,Object> expMap = redisOpsService.mGetByKeysMap(expList.toArray(new String[expList.size()]));
        for (String key : expMap.keySet()) {
            Map<String, String> monitorSumDataMap = new HashMap<String, String>();
            monitorSumDataMap.put("name", expPropertiesMap.get(key));
            monitorSumDataMap.put("value", (String)expMap.get(key));
            retList.add(monitorSumDataMap);
        }

        retMap.put("initData", retList);
        retMap.put("expPropertiesMap", expPropertiesMap);
        return retMap;

    }

}
