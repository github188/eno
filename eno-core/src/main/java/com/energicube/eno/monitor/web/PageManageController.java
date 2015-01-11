package com.energicube.eno.monitor.web;

import com.energicube.eno.common.PatternConst;
import com.energicube.eno.common.model.Message;
import com.energicube.eno.common.model.MessageResult;
import com.energicube.eno.monitor.model.*;
import com.energicube.eno.monitor.service.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Pattern.Flag;

import java.io.IOException;
import java.util.*;

/**
 * 页面布局管理
 *
 * @author CHENPING
 */
@Controller
@RequestMapping("/okcsys/page")
public class PageManageController extends BaseController {

    private static final Log logger = LogFactory.getLog(PageManageController.class);

    @Autowired
    private PagelayoutService pagelayoutService;

    @Autowired
    private PagetagService pagetagService;

    @Autowired
    private SubSystemService subSystemService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private SyscontrolService syscontrolService;

    private static List<Pagetag> ptList = new ArrayList<Pagetag>();

    /**
     * 所有的组件列表
     */
    @ModelAttribute("syscontrols")
    public List<Syscontrol> findAllSyscontrol() {
        return syscontrolService.findAll();
    }

    /**
     * 页面布局列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public List<Pagelayout> findAllPagelayout() {
        return pagelayoutService.findAllPagelayout();
    }

    @RequestMapping("/files")
    @ResponseBody
    public List<KeyValue> getFiles(HttpServletRequest request) {
        return pagelayoutService.getFileNames(request, "DDWD");
    }


    /**
     * 上传背景图片文件
     *
     * @param request  HTTP请求对象
     * @param filename 文件名称
     */
    @RequestMapping(value = "/upload/background", method = RequestMethod.POST)
    @ResponseBody
    public String processBackgroundImageFileUpload(HttpServletRequest request,
                                                   String filename) {
        String path = "";
        try {
            path = pagelayoutService.saveBackgroundFile(request, "DDWD", filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.debug("background path:" + path);
        return path;
    }

    /**
     * 导出菜单配置文件
     *
     * @param request HTTP请求对象
     */
    // 服务器根据“/download/menu”来获悉该请求由下面这个函数做处理 [ ChengKang 2014-07-27 ]
    @RequestMapping(value = "/download/menu_old", method = RequestMethod.POST)
    @ResponseBody
    public String processMenuConfigFileDownload_old(HttpServletRequest request) {
        // 获取所有的菜单配置信息，保存到OkcMenu类型的List  [ ChengKang 2014-07-27 ]
        List<OkcMenu> Menus = menuService.findAll();
        // [ ChengKang 2014-07-27 ]
        // 调用pagelayoutService的saveMenuFile方法来响应请求
        // 该函数在pagelayoutService.java中声明，在pagelayoutServiceImpl.java中实现
        // 参数中"CONFIGFILE"是文件具体导出是在服务器的哪个“子目录”
        // 参数"MenuExport"是导出的菜单配置文件的名字
        // 注意：请确保服务器中存在该目录
        return pagelayoutService.exportMenuFile_old(request, "CONFIGFILE", Menus);
    }

    /**
     * 导出菜单配置文件
     *
     * @param request HTTP请求对象
     */
    // 服务器根据“/download/menu”来获悉该请求由下面这个函数做处理 [ ChengKang 2014-07-27 ]
    @RequestMapping(value = "/download/menu", method = RequestMethod.POST)
    @ResponseBody
    public String processMenuConfigFileDownload(HttpServletRequest request) {
//        JSONArray MenuJson = new JSONArray();
        List<OkcMenu> TopMenu = menuService.findMainMenu();
        List<Map> MenuJson = new ArrayList<>();
        // 循环获得顶级菜单的菜单项，并对每一项顶级菜单递归调用getMenuJsonObj()以获取其所有子菜单（多级） [ ChengKang 2014-08-28 ]
        for (OkcMenu okcmenu : TopMenu) {
            if (okcmenu.getMenutype() != null && (okcmenu.getMenutype().equals(PatternConst.MENU_TYPE_PATTERN) || okcmenu.getMenutype().equals(PatternConst.MENU_TYPE_MODLE))) {//todo 模式的菜单,顶级菜单不做导出

                logger.info("----1--------TopMenu--------------" + okcmenu.getMenutype() + "--" + okcmenu.getOwnerelement() + "---" + okcmenu.getHeaderdescription());
            } else {
                logger.info("----2--------TopMenu--------------" + okcmenu.getMenutype() + "--" + okcmenu.getOwnerelement() + "---" + okcmenu.getHeaderdescription());
                MenuJson.add(getMenuJsonObj(okcmenu));
            }
        }
        return pagelayoutService.exportMenuFile(request, "CONFIGFILE", MenuJson);
    }

    // 递归调用函数，获取指定顶级菜单的所有子菜单（多级），并作为一个JSON对象返回 [ ChengKang 2014-08-28 ]
    // 返回的JSON对象格式为：{ "name":"XXX", "code":"XXX", "image":"XXX", "url":"XXX", "menuid":"XXX", "views":"XXX", "default":"XXX", "visible":"XXX", "children":[ …… ]  }
    public Map getMenuJsonObj(OkcMenu okcmenu) {
        Map MenuObj = new HashMap();
        MenuObj.put("name", okcmenu.getHeaderdescription());
        MenuObj.put("code", okcmenu.getElementvalue());
        MenuObj.put("image", okcmenu.getImage());
        MenuObj.put("url", okcmenu.getUrl());
        MenuObj.put("menuid", okcmenu.getMenuid());
        MenuObj.put("views", okcmenu.getViews());
        MenuObj.put("default", okcmenu.getDefaultView());
        MenuObj.put("visible", okcmenu.getVisible());

        List<OkcMenu> MenuChildren = menuService.findByOwnerelement(okcmenu.getElementvalue());

        List<Map> children = new ArrayList<>();
        for (OkcMenu child : MenuChildren) {
            if (child.getMenutype() != null && (child.getMenutype().equals(PatternConst.MENU_TYPE_PATTERN) || child.getMenutype().equals(PatternConst.MENU_TYPE_MODLE))) {//todo 模式的菜单,顶级菜单不做导出
                logger.info("------1------getMenuJsonObj--------------" + child.getMenutype() + "--" + child.getOwnerelement() + "---" + child.getHeaderdescription());
            } else {
                logger.info("------2------getMenuJsonObj--------------" + child.getMenutype() + "--" + child.getOwnerelement() + "---" + child.getHeaderdescription());
                children.add(getMenuJsonObj(child));
            }
            //getMenuJsonObj(child);
        }

        MenuObj.put("children", children);
        return MenuObj;
    }

    /**
     * 上传菜单配置文件
     *
     * @param request  HTTP请求对象
     * @param filename 文件名称
     */
    // 服务器根据“/upload/menu”来获悉该请求由下面这个函数做处理 [ ChengKang 2014-07-25 ]
    @RequestMapping(value = "/upload/menu", method = RequestMethod.POST)
    @ResponseBody
    public String processMenuConfigFileUpload(HttpServletRequest request, String filename) {
        // [ ChengKang 2014-07-25 ]
        // 调用pagelayoutService的saveMenuFile方法来响应请求
        // 该函数在pagelayoutService.java中声明，在pagelayoutServiceImpl.java中实现
        // 参数中"CONFIGFILE"是文件具体需要上传到哪个服务器的“子目录”
        // 注意：如果服务器中没有该目录，会自动新建
        OkcMenu[] Menus = null;
        try {
            Menus = pagelayoutService.saveMenuFile(request, "CONFIGFILE", filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //start清空已经存在的数据，
        List<OkcMenu> okcMenuList = menuService.findByMenutype(PatternConst.MENU_TYPE_CHANG);
        for (OkcMenu okcMenu : okcMenuList) {
            menuService.deleteMenu(okcMenu);
        }
        //end
        if (Menus != null && Menus.length > 0) {
            for (int n = 0; n < Menus.length; n++) {
                //  [ ChengKang 2014-07-26 ]
                // 对数组中的每个OkcMenu对象都调用MenuService中saveOkcMenu()方法，将OkcMenu对象中的信息存储到数据库
                // saveOkcMenu()方法在MenuServiceImpl中实现
                Menus[n].setMenutype(PatternConst.MENU_TYPE_CHANG);
                menuService.saveOkcMenu(Menus[n]);
            }
            //  [ ChengKang 2014-07-26 ]
            // 在okcmenu.js的调用中，需要根据这里的返回来判定是否成功。
            // 判定的依据是：返回null表示失败，为空字符串为文件类型错误，其余为成功。
            // 这里给定返回一个"Success"
            return "Success";
        } else {
            return null;
        }
    }


    /**
     * 显示页面布局编辑页
     *
     * @param model
     */
    @RequestMapping
    public String showPageLayoutCreationForm(Model model,
                                             HttpServletRequest request) {

        Pagelayout pagelayout = new Pagelayout();
        model.addAttribute("isNew", true);
        model.addAttribute("pagelayout", pagelayout);

        // 页实设备点实例
        model.addAttribute("pagetag", new Pagetag());

        model.addAttribute("syscontrols", syscontrolService.findAll());

        return "pagemgr/editPagelayoutForm";
    }

    /**
     * 保存页面布局信息
     *
     * @throws IOException
     * @throws JsonMappingException
     * @throws JsonGenerationException
     */
    @RequestMapping(value = "/layout", method = RequestMethod.POST)
    @ResponseBody
    public MessageResult<Pagelayout> processPageLayoutForm(@Valid Pagelayout pagelayout,
                                                           BindingResult result, SessionStatus status,
                                                           RedirectAttributes redirectAttrs, Model model) throws JsonGenerationException, JsonMappingException, IOException {

        if (result.hasErrors()) {
            return new MessageResult<Pagelayout>(new Message(false, result), pagelayout);
        } else {
            pagelayout = pagelayoutService.savePagelayout(pagelayout);
            status.setComplete();

            return new MessageResult<Pagelayout>(new Message(true, "布局更新成功"), pagelayout);
        }
    }

    /**
     * 删除指定的布局
     */
    @RequestMapping(value = "/layout/remove/{pagelayoutuid}", method = RequestMethod.POST)
    @ResponseBody
    public Message processRemovePagelayout(@PathVariable long pagelayoutuid) {
        Message message = new Message();
        message.setSuccess(true);
        if (pagelayoutuid == 0) {
            message.setSuccess(false);
        }
        try {
            pagelayoutService.deletePagelayout(pagelayoutuid);

        } catch (Exception e) {
            message.setSuccess(false);
            message.setMsg(e.toString());
        }
        return message;
    }

    /**
     * 获取指定布局的设备列表
     *
     * @param pagelayoutuid 布局ID
     * @return 设备列表
     */
    @RequestMapping(value = "/pagetag/{pagelayoutuid}", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Pagetag> findPagetagsByLayoutid(@PathVariable long pagelayoutuid) {
        return pagetagService.findByPagelayoutuid(pagelayoutuid);
    }

    /**
     * 返回treegrid需要的json格式
     *
     * @param pagelayoutuid
     * @return
     */
    @RequestMapping(value = "/getpagetag/{pagelayoutuid}", method = RequestMethod.POST)
    @ResponseBody
    public String findPagetagsListByLayoutid(@PathVariable long pagelayoutuid, @RequestParam(value = "classid", defaultValue = "") String classid) {
        try {
            List<Pagetag> list = pagetagService.findByLayoutidAndClassId(pagelayoutuid, classid);
            com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
            String pagetag = objectMapper.writeValueAsString(list);
            String str = "{\"rows\":" + pagetag.replace("parentid", "_parentId").replace("undefined", "") + "}";
//            logger.info("str--" + str);
            return str;
        } catch (Exception e) {
            logger.error("findPagetagsListByLayoutid", e);
        }
        return "";
    }
    /**
     * 获取面板内容
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getPanelValue", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public List<Object> findPanelContent(HttpServletRequest request) {
        String layoutid = request.getParameter("layoutid"); // 布局id
        String pagetagid = request.getParameter("pagetagid"); // pagetag表主键
        return subSystemService.findPanelContent(layoutid, pagetagid);
    }

    /**
     * 保存页面TAG信息
     *
     * @param pagelayoutuid 页面布局ID
     * @param pagetag       页面TAG对象
     */
    @RequestMapping(value = "/pagetag/{pagelayoutuid}", method = RequestMethod.POST)
    @ResponseBody
    public MessageResult<Pagetag> processPagetagForm(
            @PathVariable("pagelayoutuid") Long pagelayoutuid,
            @Valid Pagetag pagetag, BindingResult result, SessionStatus status,
            RedirectAttributes redirectAttrs) {

        if (result.hasErrors()) {
            return new MessageResult<Pagetag>(new Message(false, result), pagetag);
        } else {

            pagetag = pagetagService.savePagetag(pagetag);
            status.setComplete();
            return new MessageResult<Pagetag>(new Message(true, "设备点更新成功"), pagetag);

        }

    }
    
    /**
     * 保存客流页面TAG信息
     *
     * @param pagelayoutuid 页面布局ID
     * @param pagetag       页面TAG对象
     */
    @RequestMapping(value = "/passengerPagetag/{pagelayoutuid}", method = RequestMethod.POST)
    @ResponseBody
    public MessageResult<Pagetag> processPassengerPagetagForm(
    		@PathVariable("pagelayoutuid") Long pagelayoutuid,
    		@Valid Pagetag pagetag, BindingResult result, SessionStatus status,
    		RedirectAttributes redirectAttrs) {
    	
    	if (result.hasErrors()) {
    		return new MessageResult<Pagetag>(new Message(false, result), pagetag);
    	} else {
    		
    		pagetag = pagetagService.savePassengerPagetag(pagetag);
    		status.setComplete();
    		return new MessageResult<Pagetag>(new Message(true, "设备点更新成功"), pagetag);
    		
    	}
    	
    }

    /**
     * 保存页面中热区的Setting修改
     *
     * @param pagetag 页面TAG对象
     * @author ChengKang [ ChengKang 2014-08-21 ]
     */
    @RequestMapping(value = "/pageSetting", method = RequestMethod.POST)
    @ResponseBody
    public MessageResult<Pagetag> processPagetagById(HttpServletRequest request,
                                                     @Valid Pagetag pagetag, BindingResult result, SessionStatus status,
                                                     RedirectAttributes redirectAttrs) {

        Long PageTagId = Long.valueOf(request.getParameter("PageTagId"));
        String SettingStr = request.getParameter("SettingJsonStr");
        SettingStr = SettingStr.replaceAll("\\\\\"", "\"");

        if (!StringUtils.hasLength(SettingStr)) {
            return new MessageResult<Pagetag>(new Message(false, result), pagetag);
        } else {
            try {
                com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();
                Map jsonObject = objectMapper.readValue(SettingStr, Map.class);
                String plan = jsonObject.get("plan").toString();
                Map object = objectMapper.readValue(plan, Map.class);
                Long TagId = Long.valueOf((String) object.get("TagID"));
                String TagName = String.valueOf(object.get("TagName"));
                String Setting = SettingStr;
                pagetag = pagetagService.findOne(PageTagId);
                pagetag.setTagid(String.valueOf(TagId));
                pagetag.setTagname(TagName);
                pagetag.setSetting(Setting);
                pagetag = pagetagService.savePagetag(pagetag);
                status.setComplete();
            } catch (Exception e) {
                logger.error("processPagetagById", e);
            }
            return new MessageResult<Pagetag>(new Message(true, "热区修改成功"), pagetag);
        }

    }

    /**
     * 获取指定的页面设备点对象
     *
     * @param pagetagid 页面设备点主键ID
     */
    @RequestMapping(value = "/pagetag/{pagetagid}", method = RequestMethod.GET)
    @ResponseBody
    public Pagetag findPagetag(@PathVariable("pagetagid") Long pagetagid) {
        Pagetag pagetag = pagetagService.findOne(pagetagid);
        return pagetag;
    }

    /**
     * 删除指定的TAGID
     *
     * @param pagetagid 页面TAGID
     * @return true or false
     */
    @RequestMapping(value = "/pagetag/delete/{pagetagid}", method = RequestMethod.POST)
    @ResponseBody
    public Message processDeletePagetag(
            @PathVariable("pagetagid") Long pagetagid) {
        Message message = new Message();
        if (pagetagid > 0) {
            pagetagService.deletePagetag(pagetagid);
            message.setSuccess(true);
        }
        return message;
    }

    /**
     * 批量更新设备属性
     *
     * @param pagelayoutuid 布局主键ID
     * @return {@link Message}
     */
    @RequestMapping(value = "/pagetag/porps/{pagelayoutuid}", method = RequestMethod.POST)
    @ResponseBody
    public Message processBatchUpdatePagetagProps(
            @PathVariable("pagelayoutuid") Long pagelayoutuid,
            Pagetag pagetag,
            HttpServletRequest request) {
        Message message = new Message();

        try {
            Pagelayout layout = pagelayoutService.findOne(pagelayoutuid);
            pagetagService.batchUpdatePagetagProps(layout.getLayoutid(), pagetag);
            message.setSuccess(true);
        } catch (Exception ex) {
            message.setSuccess(false);
            message.setMsg(ex.toString());
            if (logger.isErrorEnabled()) {
                logger.error(ex);
            }
        }

        return message;
    }


    /**
     * 批量更新坐标
     *
     * @param pagelayoutuid 布局ID
     * @param request       HTTP请求对象
     */
    @RequestMapping(value = "/pagetag/{pagelayoutuid}/update", method = RequestMethod.POST)
    @ResponseBody
    public String processUpdatePagetagCoordinate(
            @PathVariable("pagelayoutuid") Long pagelayoutuid,
            HttpServletRequest request, SessionStatus status,
            RedirectAttributes redirectAttrs) {
        Boolean ret = true;

        String jsonInput = request.getParameter("data");

        ObjectMapper mapper = new ObjectMapper();
        try {

            Collection<Pagetag> pagetags = mapper.readValue(
                    jsonInput,
                    TypeFactory.defaultInstance().constructParametricType(
                            Collection.class, Pagetag.class));

            logger.info(pagetags);

            Collection<Pagetag> pagetags1 = new ArrayList<Pagetag>();
            for (Pagetag p : pagetags) {
                if (p != null && !"0".equals(p.getLeft()) && !"0".equals(p.getTop())) {
                    pagetags1.add(p);
                }
            }
            pagetagService.updatePagetagCoords(pagetags1);


        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        status.setComplete();
        redirectAttrs.addFlashAttribute("message", "设备点更新成功");

        return ret.toString();
    }

    /**
     * 添加设备点   数据存储在request中的JSON
     *
     * @param request
     * @param status
     * @param redirectAttrs
     * @return
     */
    @RequestMapping(value = "/addtags", method = RequestMethod.POST)
    @ResponseBody
    public boolean processAddTags(HttpServletRequest request, SessionStatus status,
                                  RedirectAttributes redirectAttrs) {
        boolean ret = true;
        String str = request.getParameter("tagDatas");
        ObjectMapper mapper = new ObjectMapper();
        try {

            Collection<Pagetag> pagetags = mapper.readValue(
                    str,
                    TypeFactory.defaultInstance().constructParametricType(
                            Collection.class, Pagetag.class));

            pagetagService.addPagetags(pagetags);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        status.setComplete();
        redirectAttrs.addFlashAttribute("message", "设备点新增成功");
        return ret;
    }
    @RequestMapping(value = "/sorttags/{pagetagid}/{type}", method = RequestMethod.POST)
    @ResponseBody
    public boolean sortTags(@PathVariable long pagetagid, @PathVariable boolean type) {
    	logger.info(pagetagid + "-----------" + type);
    	boolean flag = true;
    	try {
			pagetagService.sortTags(pagetagid, type);
		} catch (Exception e) {
			logger.error(e);
		}
		return flag;
    }
    
    @RequestMapping(value = "/deletePagetags", method = RequestMethod.POST)
    @ResponseBody
    public boolean deletePageTags(HttpServletRequest request) {
    	String ids = request.getParameter("ids");
    	String[] pagetagids = ids.split(",");
    	boolean flag = true;
    	Message message = new Message();
    	try {
			pagetagService.deletePagetags(pagetagids);
			message.setSuccess(true);
		} catch (Exception e) {
			logger.error(e);
			flag = false;
		}
    	return flag;
    }
    /**
     * 保存临时坐标点
     *
     * @param pagetag
     * @param model
     * @return
     *//*
    @RequestMapping(value = "/savePagetag", method = RequestMethod.POST)
	public String savePagetag(Pagetag pagetag, Model model) {
		if (pagetag != null) {
			if (ptList != null && ptList.size() > 0) {
				for (int i = 0; i < ptList.size(); i++) {
					if (pagetag.getTagid() == ptList.get(i).getTagid()) {
						ptList.remove(i);
						break;
					}
				}
			}
			ptList.add(pagetag);
		}
		return null;
	}

	*//**
     * 保存临时坐标点到数据库清除保存的临时坐标点信息
     *
     * @param model
     * @return
     *//*
    @RequestMapping(value = "/removePagetag", method = RequestMethod.POST)
	public String removePagetag(Model model) {
		if (ptList != null && ptList.size() > 0) {
			for (int i = 0; i < ptList.size(); i++) {
				ptList.remove(i);
			}
		}
		return null;
	}

	*//**
     * 保存临时坐标点到数据库
     *
     * @param model
     * @return
     *//*
    @RequestMapping(value = "/addPagetag", method = RequestMethod.POST)
	public String addPagetag(Model model) {
		if (ptList != null && ptList.size() > 0) {
			for (int i = 0; i < ptList.size(); i++) {
				pagetagService.add(ptList.get(i));
			}
			for (int i = 0; i < ptList.size(); i++) {
				ptList.remove(i);
			}
		}
		return null;
	}
*/
}
