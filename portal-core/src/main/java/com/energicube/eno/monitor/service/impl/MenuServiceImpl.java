package com.energicube.eno.monitor.service.impl;

import com.energicube.eno.common.PatternConst;
import com.energicube.eno.monitor.model.OkcMenu;
import com.energicube.eno.monitor.model.OkcMenuSet;
import com.energicube.eno.monitor.repository.OkcMenuRepository;
import com.energicube.eno.monitor.service.MenuService;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 菜单逻辑操作接口实现类
 *
 * @author CHENPING
 */
@Service
public class MenuServiceImpl implements MenuService {

    private static final Log logger = LogFactory.getLog(MenuServiceImpl.class);

    @Autowired
    private OkcMenuRepository okcMenuRepository;

    @Autowired
    private CacheManager ehcacheManager;

    @Transactional(readOnly = true)
    public List<OkcMenu> findAll() {
        return okcMenuRepository.findAll();

    }

//    @Cacheable("modules")
    @Transactional(readOnly = true)
    public List<OkcMenu> findModuleList() {
        logger.debug("----findModuleList---1--");
        return okcMenuRepository.findByOwnerelementOrderByPositionAsc();        // [ ChengKang 2014-08-26 ]
    }

//    @Cacheable("mainmenus")
    @Transactional(readOnly = true)
    public List<OkcMenu> findMainMenu() {
        List<OkcMenu> mainmenus = new LinkedList<OkcMenu>();
        logger.debug("----findMainMenu-----");
        List<OkcMenu> modules = findModuleList();
        logger.debug("----findMainMenu---1--");
        for (OkcMenu okcmenu : modules) {
            if (!StringUtils.hasLength(okcmenu.getUrl())) {
                String url = "/" + okcmenu.getElementvalue();
                // 查询模块应用
                List<OkcMenu> apps = findAppList(okcmenu.getElementvalue());
                if (apps.size() > 0) {
                    OkcMenu app = apps.get(0);
                    if (app != null) {
                        url += "/" + app.getElementvalue();
                    }
                }
                url += ".html";
                okcmenu.setUrl(url.toLowerCase());
            }
            mainmenus.add(okcmenu);
        }
        return mainmenus;
    }

    @Override
    @Cacheable(value = "mainmenusnopatter")
    public String findMainMenuNoPatter() {
        try {
            logger.debug("----findMainMenuNoPatter---1--");
            Cache cache = ehcacheManager.getCache("mainmenusnopatter");
            Element cacheEle = cache.get("mainmenusnopatter");
//            if (cacheEle != null) {
//                return cacheEle.getObjectValue().toString();
//            }else {
                List<Map> menuMap = new ArrayList<>();
                List<OkcMenu> TopMenu = findMainMenu();
            logger.debug("----findMainMenuNoPatter---2--");
                // 循环获得顶级菜单的菜单项，并对每一项顶级菜单递归调用getMenuJsonObj()以获取其所有子菜单（多级） [ ChengKang 2014-08-28 ]
                for (OkcMenu okcmenu : TopMenu) {
                    if (!okcmenu.getMenutype().equals(PatternConst.MENU_TYPE_PATTERN)) {//模式的菜单独立处理
                        menuMap.add(getMenuJsonObj(okcmenu));
                    }
                }
                ObjectMapper objectMapper = new ObjectMapper();
                String menuStr = objectMapper.writeValueAsString(menuMap);
                Element element = new Element("mainmenusnopatter",menuStr);
                cache.put(element);

                return menuStr;

//            }

        } catch (Exception e) {
            logger.error("findMainMenuNoPatter", e);
        }
        return "";
    }

    /**
     * 递归调用函数，获取指定顶级菜单的所有子菜单（多级），并作为一个JSON对象返回 [ ChengKang 2014-08-28 ]
     * 返回的JSON对象格式为：{ "name":"XXX", "code":"XXX", "image":"XXX", "url":"XXX", "menuid":"XXX", "children":[ …… ]  }
     * name：菜单名，code：菜单编码，image：图片地址，url：链接地址，children：子菜单项的JSON集合
     *
     * @param okcmenu
     * @return
     */
    @Override
    public Map getMenuJsonObj(OkcMenu okcmenu) {
        Map MenuObj = new HashMap();
        MenuObj.put("name", okcmenu.getHeaderdescription());
        MenuObj.put("code", okcmenu.getElementvalue());
        MenuObj.put("image", okcmenu.getImage());
        MenuObj.put("url", okcmenu.getUrl());
        MenuObj.put("menuid", okcmenu.getMenuid());

        List<OkcMenu> MenuChildren = findByOwnerelement(okcmenu.getElementvalue());
        List<Map> children = new ArrayList<>();
//        logger.info("---------MenuChildren--------------"+MenuChildren.size());
        for (OkcMenu child : MenuChildren) {
//            logger.debug("----for-----MenuChildren--------------"+child.getMenutype());
            if (child.getMenutype() != null && child.getMenutype().equals(PatternConst.MENU_TYPE_PATTERN)) {//模式的菜单独立处理

            } else {
                children.add(getMenuJsonObj(child));
//                logger.debug("------------child--------------"+okcmenu.getMenutype()+"--"+okcmenu.getOwnerelement());
            }
            //getMenuJsonObj(child);
        }

        MenuObj.put("children", children);
        return MenuObj;
    }

    @Cacheable(value = "modules", key = "#moduleId+ 'findByModuleId'")
    @Transactional(readOnly = true)
    public List<OkcMenu> findAppList(String moduleId) {
        // return
        // okcMenuRepository.findByMenutypeAndElementtypeAndOwnerelementOrderByPositionAsc("MODULE",
        // "APP", moduleId);
        if (moduleId == null) {
            return okcMenuRepository.findByOwnerelementOrderByPositionAsc();        //  [ ChengKang 2014-09-18 ]
        }
        return okcMenuRepository.findByOwnerelementOrderByPositionAsc(moduleId);        //  [ ChengKang 2014-08-26 ]

    }

    @Cacheable(value = "modules", key = "#ownerelement+ 'findTypeFilter'")
    @Transactional(readOnly = true)
    public List<OkcMenu> findTypeFilterList(String ownerelement) {
        return okcMenuRepository.findByTypeFilter(ownerelement);
    }

    public List<OkcMenu> findAppFilterList(String ownerelement) {
        return okcMenuRepository.findByAppFilterAndMenutype(ownerelement, "CHANG");
    }

    public List<OkcMenu> findMenuList(String ownerelement) {

        return null;
    }

    public List<OkcMenu> findByMenutypeAndElementtypeAndOwnerelement(
            String menutype, String elementtype, String ownerelement) {
        return okcMenuRepository
                .findByMenutypeAndElementtypeAndOwnerelementOrderByPositionAsc(
                        menutype, elementtype, ownerelement);
    }

    public OkcMenu findMenuItem(String menutype, String ownerelement,
                                String elementvalue) {
        List<OkcMenu> okcMenus = okcMenuRepository
                .findByMenutypeAndOwnerelementAndElementvalue(menutype,
                        ownerelement, elementvalue);
        if (okcMenus == null) {
            return null;
        }
        if (okcMenus.size() > 0) {
            return okcMenus.get(0);
        }
        return null;
    }

    public List<OkcMenu> findByMenutypeAndOwnerelement(String menutype,
                                                       String ownerelement) {
        return okcMenuRepository
                .findByMenutypeAndOwnerelementOrderByPositionAscSubpositionAsc(
                        menutype, ownerelement);
    }

    @Transactional(readOnly = true)
    public List<OkcMenu> findByElementvalue(String elementvalue) {
        List<OkcMenu> list = okcMenuRepository.findByElementvalue(elementvalue);
        return list;
    }

    @Transactional(readOnly = true)
    public List<OkcMenu> findByOwnerelement(String ownerelement) {

        List<OkcMenu> list = okcMenuRepository.findByOwnerelementNew(ownerelement);
        return list;
    }

    @Cacheable(value = "modules", key = "#menutype + #ownerelement")
    public List<OkcMenuSet> findOkcMenuSetList(String menutype,
                                               String ownerelement) {

        List<OkcMenu> okcMenus = findByMenutypeAndOwnerelement(menutype,
                ownerelement);
        // List<OkcMenu> okcMenus = findByOwnerelement(ownerelement);
        if (okcMenus == null)
            return null;
        List<OkcMenuSet> okcMenuSetList = new LinkedList<OkcMenuSet>();
        for (OkcMenu menu : okcMenus) {
            OkcMenuSet okcMenuSet = new OkcMenuSet();
            okcMenuSet.setOkcMenu(menu);

            List<OkcMenu> childrenMenus = findByMenutypeAndOwnerelement(
                    menu.getElementtype(), menu.getElementvalue());
            if (childrenMenus != null) {
                okcMenuSet.setOkcMenus(childrenMenus);
            }

            okcMenuSetList.add(okcMenuSet);
        }

        return okcMenuSetList;
    }

    @Override
    public OkcMenu findByMenuid(Long menuid) {
        if (menuid > 0) {
            return okcMenuRepository.findByMenuid(menuid);
        }
        return null;
    }

    public OkcMenu findByUrl(String url) {
        List<OkcMenu> list = okcMenuRepository.findByUrl(url);
        if (list != null && list.size() > 0)
            return list.get(0);
        return null;
    }

    @Caching(evict = {@CacheEvict(value = "modules"),
            @CacheEvict(value = "mainmenusnopatter", allEntries = true)})
    @Transactional
    public OkcMenu saveOkcMenu(OkcMenu okcMenu) {
        if (okcMenu.getVisible() == null) {
            okcMenu.setVisible(true);
        }
        okcMenu = okcMenuRepository.save(okcMenu);
        return okcMenu;
    }

    @Caching(evict = {@CacheEvict(value = "modules"),
            @CacheEvict(value = "menutreeview"),
            @CacheEvict(value = "menuztreeitems"),
            @CacheEvict(value = "mainmenusnopatter", allEntries = true)})
    @Transactional
    public OkcMenu saveCopyOkcMenu(OkcMenu okcMenu) {
        if (okcMenu.getVisible() == null) {
            okcMenu.setVisible(true);
        }
        OkcMenu copy = new OkcMenu();
        copy.setOkcmenuid(0L);
        copy.setMenutype(okcMenu.getMenutype());
        copy.setOwnerelement(okcMenu.getOwnerelement());
        copy.setPosition(okcMenu.getPosition());
        copy.setSubposition(okcMenu.getSubposition());
        copy.setElementtype(okcMenu.getElementtype());
        copy.setElementvalue(okcMenu.getElementvalue());
        copy.setHeaderdescription(okcMenu.getHeaderdescription());
        copy.setImage(okcMenu.getImage());
        copy.setUrl(okcMenu.getUrl());
        copy.setVisible(okcMenu.getVisible());
        copy = okcMenuRepository.saveAndFlush(copy);
        return copy;
    }

    @Caching(evict = {
            @CacheEvict(value = "modules", key = "#moduleId + 'findByModuleId'"),
            @CacheEvict(value = "menuztreeitems"),
            @CacheEvict(value = "mainmenusnopatter", allEntries = true)})
    @Transactional
    public void deleteMenu(Long menuid) {
        okcMenuRepository.delete(menuid);
    }

    @Transactional(readOnly = true)
    public OkcMenu findOne(Long menuid) {
        return okcMenuRepository.findOne(menuid);
    }

    @Transactional(readOnly = true)
    public String getMenuTreeString(HttpServletRequest request, boolean hasLink) {

        String path = "http://" + request.getServerName() + ":"
                + request.getServerPort() + request.getContextPath()
                + "/okcsys/okcmenu/edit";

        StringBuilder sb = new StringBuilder();
        // 获取主菜单
        List<OkcMenu> moduleMenus = findMainMenu();
        // 获取子菜单对应的
        sb.append("<ul id=\"menutree\">");
        for (OkcMenu okcMenu : moduleMenus) {
            sb.append("<li code='" + okcMenu.getElementvalue() + "' id='"
                    + okcMenu.getOkcmenuid() + "' menuid='"
                    + okcMenu.getMenuid() + "'>");
            if (hasLink) {
                sb.append(String.format("<a href=\"%s?okcmenuid=%d\">%s</a>",
                        path, okcMenu.getOkcmenuid(),
                        okcMenu.getHeaderdescription()));
            } else {
                sb.append(String.format("%s", okcMenu.getHeaderdescription()));
            }

            // 主菜单对应的二级菜单APP
            List<OkcMenu> appMenus = findAppList(okcMenu.getElementvalue());
            StringBuilder sb2 = new StringBuilder();
            for (OkcMenu app : appMenus) {

                sb2.append("<li code='" + app.getElementvalue() + "' id='"
                        + app.getOkcmenuid() + "'  menuid='" + app.getMenuid()
                        + "' ownerelement='" + okcMenu.getOwnerelement() + "'>");
                if (hasLink) {
                    sb2.append(String.format(
                            "<a href=\"%s?okcmenuid=%d\">%s</a>", path,
                            app.getOkcmenuid(), app.getHeaderdescription()));
                } else {
                    sb2.append(String.format("%s", app.getHeaderdescription()));
                }

                List<OkcMenu> appFilterMenus = findAppFilterList(app
                        .getElementvalue());

                // 三级菜单
                StringBuilder sb3 = new StringBuilder();
                for (OkcMenu appfilter : appFilterMenus) {

                    sb3.append("<li code='" + appfilter.getElementvalue()
                            + "' id='" + appfilter.getOkcmenuid()
                            + "'  menuid='" + appfilter.getMenuid()
                            + "' ownerelement='" + app.getOwnerelement() + "'>");

                    if (hasLink) {
                        sb3.append(String.format(
                                "<a href=\"%s?okcmenuid=%d\">%s</a>", path,
                                appfilter.getOkcmenuid(),
                                appfilter.getHeaderdescription()));
                    } else {
                        sb3.append(String.format("%s",
                                appfilter.getHeaderdescription()));
                    }
                    // 四级
                    StringBuilder sb4 = new StringBuilder();
                    String menutype = appfilter.getMenutype() + "|"
                            + appfilter.getOwnerelement();
                    List<OkcMenuSet> okcMenuSetList = findOkcMenuSetList(
                            menutype, appfilter.getElementvalue());
                    for (OkcMenuSet menuset : okcMenuSetList) {
                        sb4.append("<li code='" + appfilter.getElementvalue()
                                + "' id='" + appfilter.getOkcmenuid()
                                + "'  menuid='" + appfilter.getMenuid()
                                + "' ownerelement='"
                                + appfilter.getOwnerelement() + "'>");
                        if (hasLink) {
                            sb4.append(String
                                    .format("<a href=\"%s?okcmenuid=%d\">%s</a>",
                                            path, menuset.getOkcMenu()
                                                    .getOkcmenuid(), menuset
                                                    .getOkcMenu()
                                                    .getHeaderdescription()));
                        } else {
                            sb4.append(String.format("%s", menuset.getOkcMenu()
                                    .getHeaderdescription()));
                        }
                        StringBuilder sb5 = new StringBuilder();
                        for (OkcMenu item : menuset.getOkcMenus()) {
                            if (hasLink) {
                                sb5.append(String
                                        .format("<li code='%s' id='%d'><a href=\"%s?okcmenuid=%d\">%s</a></li>",
                                                item.getElementvalue(),
                                                item.getOkcmenuid(), path,
                                                item.getOkcmenuid(),
                                                item.getHeaderdescription()));
                            } else {
                                sb5.append(String
                                        .format("<li code='%s' id='%d' menuid='%s' ownerelement='%s'>%s</li>",
                                                item.getElementvalue(), item
                                                        .getOkcmenuid(), item
                                                        .getMenuid(), menuset
                                                        .getOkcMenu()
                                                        .getOwnerelement(),
                                                item.getHeaderdescription()));
                            }
                        }
                        if (menuset.getOkcMenus().size() > 0)
                            sb4.append("<ul>" + sb5.toString() + "</ul>");
                        sb4.append("</li>");

                    }

                    if (okcMenuSetList.size() > 0)
                        sb3.append("<ul>" + sb4.toString() + "</ul>");
                    sb3.append("</li>");
                }

                if (appFilterMenus.size() > 0)
                    sb2.append("<ul>" + sb3.toString() + "</ul>");
                sb2.append("</li>");
            }

            if (appMenus.size() > 0)
                sb.append("<ul>" + sb2.toString() + "</ul>");

            sb.append("</li>");
        }
        sb.append("</ul>");

        return sb.toString();
    }

    @Cacheable(value = "menutreeview")
    @Transactional(readOnly = true)
    public String getMenuTreeString(HttpServletRequest request) {
        return getMenuTreeString(request, true);
    }

    @Cacheable(value = "menuztreeitems")
    @Transactional(readOnly = true)
    public String getZTreeNodeCollection(String ownerelement) {
        // 根据新的菜单规则，重新实现该函数
        StringBuilder sb = new StringBuilder();

        // 获得一级菜单
        List<OkcMenu> appMenus = findAppList(null);
        StringBuilder sb2 = new StringBuilder();
        for (OkcMenu app : appMenus) {
            if (sb2.length() > 0)
                sb2.append(",");
            sb2.append(String
                    .format("{\"id\":\"%s\",\"name\":\"%s\",\"code\":\"%s\",\"title\":\"%s\",\"parentname\":\"%s\",\"open\":true,\"children\":[ ",
                            app.getMenuid(),
                            app.getHeaderdescription(),
                            app.getElementvalue(), app.getOwnerelement(),
                            ""));

            // 二级菜单
            List<OkcMenu> appFilterMenus = findAppFilterList(app.getElementvalue());
            StringBuilder sb3 = new StringBuilder();
            for (OkcMenu appfilter : appFilterMenus) {
                if (sb3.length() > 0)
                    sb3.append(",");

                sb3.append(String
                        .format("{\"id\":\"%s\",\"name\":\"%s\",\"code\":\"%s\",\"title\":\"%s\",\"topMenu\":\"%s\",\"parentname\":\"%s\",\"open\":true,\"children\":[ ",
                                appfilter.getMenuid(),
                                appfilter.getHeaderdescription(),
                                appfilter.getElementvalue(),
                                appfilter.getOwnerelement(),
                                appfilter.getElementvalue(),
                                app.getHeaderdescription()));

                // 三级菜单
                List<OkcMenu> okcMenuSetList = findAppFilterList(appfilter.getElementvalue());
                StringBuilder sb4 = new StringBuilder();
                for (OkcMenu menuset : okcMenuSetList) {
                    if (sb4.length() > 0)
                        sb4.append(",");

                    sb4.append(String
                            .format("{\"id\":\"%s\",\"name\":\"%s\",\"code\":\"%s\",\"title\":\"%s\",\"topMenu\":\"%s\",\"parentname\":\"%s\",\"open\":true,\"children\":[ ",
                                    menuset.getMenuid(),
                                    menuset.getHeaderdescription(),
                                    menuset.getElementvalue(),
                                    menuset.getOwnerelement(),
                                    appfilter.getElementvalue(),
                                    appfilter.getHeaderdescription()));

                    // 四级菜单
                    List<OkcMenu> okcMenuItems = findAppFilterList(menuset.getElementvalue());
                    StringBuilder sb5 = new StringBuilder();
                    for (OkcMenu item : okcMenuItems) {
                        if (sb5.length() > 0)
                            sb5.append(",");
                        sb5.append(String
                                .format("{\"id\":\"%s\",\"name\":\"%s\",\"code\":\"%s\",\"title\":\"%s\",\"topMenu\":\"%s\",\"parentname\":\"%s\",\"open\":false}",
                                        item.getMenuid(),
                                        item.getHeaderdescription(),
                                        item.getElementvalue(),
                                        item.getOwnerelement(),
                                        appfilter.getElementvalue(),
                                        menuset.getHeaderdescription()));
                    } // End Level 4

                    if (okcMenuItems.size() > 0)
                        sb4.append(sb5.toString());
                    sb4.append("]}");
                }// End Level 3

                if (okcMenuSetList.size() > 0)
                    sb3.append(sb4.toString());
                sb3.append("]}");
            }// End Level 2

            if (appFilterMenus.size() > 0)
                sb2.append(sb3.toString());
            sb2.append("]}");
        }// End Level 1

        sb.append(String
                .format("{\"id\":\"%s\",\"name\":\"%s\",\"code\":\"%s\",\"title\":\"%s\",\"open\":true,\"children\":[ ",
                        1,
                        "监测与控制",
                        "MCTRL",
                        ""));

        if (appMenus.size() > 0)
            sb.append(sb2.toString());
        sb.append("]}");

        return sb.toString();

		/*
        // 下面是旧菜单规则使用的函数实现
		
		// 获取指定的模块数据
		List<OkcMenu> ownerList = null;
		if (StringUtils.hasLength(ownerelement))
		{
			ownerList = okcMenuRepository
					.findByMenutypeAndElementtypeAndOwnerelementOrderByPositionAsc(
							"Module", "Mod", ownerelement);
		}
		else
		{
			ownerList = okcMenuRepository
					.findByMenutypeAndElementtypeOrderByPositionAsc("Module",
							"Mod");
		}
		//
		StringBuilder sb = new StringBuilder();

		// 迭代模块数据
		for (OkcMenu okcMenu : ownerList)
		{

			// 主菜单对应的二级菜单APP
			List<OkcMenu> appMenus = findAppList(okcMenu.getElementvalue());
			StringBuilder sb2 = new StringBuilder();
			for (OkcMenu app : appMenus)
			{
				if (sb2.length() > 0)
					sb2.append(",");
				sb2.append(String
						.format("{\"id\":\"%s\",\"name\":\"%s\",\"code\":\"%s\",\"title\":\"%s\",\"parentname\":\"%s\",\"open\":true,\"children\":[ ",
								app.getMenuid(), app.getHeaderdescription(),
								app.getElementvalue(), app.getOwnerelement(),
								app.getHeaderdescription()));

				List<OkcMenu> appFilterMenus = findAppFilterList(app
						.getElementvalue());

				// 三级菜单
				StringBuilder sb3 = new StringBuilder();
				for (OkcMenu appfilter : appFilterMenus)
				{
					if (sb3.length() > 0)
						sb3.append(",");

					sb3.append(String
							.format("{\"id\":\"%s\",\"name\":\"%s\",\"code\":\"%s\",\"title\":\"%s\",\"parentname\":\"%s\",\"open\":true,\"children\":[ ",
									appfilter.getMenuid(),
									appfilter.getHeaderdescription(),
									appfilter.getElementvalue(),
									appfilter.getOwnerelement(),
									appfilter.getHeaderdescription()));

					// 四级
					StringBuilder sb4 = new StringBuilder();
					String menutype = appfilter.getMenutype() + "|"
							+ appfilter.getOwnerelement();
					List<OkcMenuSet> okcMenuSetList = findOkcMenuSetList(
							menutype, appfilter.getElementvalue());
					for (OkcMenuSet menuset : okcMenuSetList)
					{

						if (sb4.length() > 0)
							sb4.append(",");

						sb4.append(String
								.format("{\"id\":\"%s\",\"name\":\"%s\",\"code\":\"%s\",\"title\":\"%s\",\"parentname\":\"%s\",\"open\":true,\"children\":[ ",
										menuset.getOkcMenu().getMenuid(),
										menuset.getOkcMenu()
												.getHeaderdescription(),
										menuset.getOkcMenu().getElementvalue(),
										menuset.getOkcMenu().getOwnerelement(),
										menuset.getOkcMenu()
												.getHeaderdescription()));

						// 五级
						StringBuilder sb5 = new StringBuilder();
						for (OkcMenu item : menuset.getOkcMenus())
						{
							if (sb5.length() > 0)
								sb5.append(",");
							sb5.append(String
									.format("{\"id\":\"%s\",\"name\":\"%s\",\"code\":\"%s\",\"title\":\"%s\",\"parentname\":\"%s\",\"open\":false}",
											item.getMenuid(), item
													.getHeaderdescription(),
											item.getElementvalue(), menuset
													.getOkcMenu()
													.getOwnerelement(), menuset
													.getOkcMenu()
													.getHeaderdescription()));

						}
						if (menuset.getOkcMenus().size() > 0)
							sb4.append(sb5.toString());
						sb4.append("]}");
						// End Sb4
					}

					if (okcMenuSetList.size() > 0)
						sb3.append(sb4.toString());
					sb3.append("]}");
				}

				if (appFilterMenus.size() > 0)
					sb2.append(sb3.toString());
				sb2.append("]}");
			}

			// 一级
			sb.append(String
					.format("{\"id\":\"%s\",\"name\":\"%s\",\"code\":\"%s\",\"title\":\"%s\",\"open\":true,\"children\":[ ",
							okcMenu.getMenuid(),
							okcMenu.getHeaderdescription(),
							okcMenu.getElementvalue(),
							okcMenu.getOwnerelement()));

			if (appMenus.size() > 0)
				sb.append(sb2.toString());
			sb.append("]}");

		}

		return sb.toString();
		*/

    } // End Function

    @Override
    public List<OkcMenu> findByMenutype(String menutype) {
        return okcMenuRepository.findByMenutypeOrderByPositionAsc(menutype);
    }

    @Override
    public void deleteMenu(OkcMenu okcMenu) {
        okcMenuRepository.delete(okcMenu);
    }
}
