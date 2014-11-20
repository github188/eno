package com.energicube.eno.monitor.web;

import com.energicube.eno.common.util.AjaxUtils;
import com.energicube.eno.monitor.model.AppAuth;
import com.energicube.eno.monitor.model.GroupMember;
import com.energicube.eno.monitor.model.OkcMenu;
import com.energicube.eno.monitor.model.UserInfo;
import com.energicube.eno.monitor.repository.AppAuthRepository;
import com.energicube.eno.monitor.repository.GroupMemberRepository;
import com.energicube.eno.monitor.service.MenuService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseController {

    private static final Log logger = LogFactory.getLog(BaseController.class);

    @Autowired
    private MenuService menuService;

    @Autowired
    private GroupMemberRepository groupMemberRepository;

    @Autowired
    private AppAuthRepository appAuthRepository;

    @ModelAttribute
    public void ajaxAttribute(WebRequest request, Model model) {
        model.addAttribute("ajaxRequest", AjaxUtils.isAjaxRequest(request));
    }


    /**
     * 获取主菜单
     */
    @ModelAttribute(value = "mainMenus")
    public List<OkcMenu> getMainMenu() {
        return menuService.findMainMenu();
    }

    /**
     * 获取模块应用
     */
    @ModelAttribute(value = "applist")
    public List<OkcMenu> getAppMenu(HttpServletRequest request) {
        String reqPath = request.getRequestURI().substring(
                request.getContextPath().length());
        String ret = reqPath.replace(".html", "");
        String[] arrPath = ret.split("/");
        String currOwnerEle = arrPath[arrPath.length - 2].toUpperCase();
        return menuService.findAppList(currOwnerEle);
    }

    @ModelAttribute(value = "filterMenus")
    public List<OkcMenu> getTypeFilterMenu(HttpServletRequest request) {

        String ownerelement = "";
        // 当前请求路径，与OkcMenu中的url一致
        String reqPath = request.getRequestURI().substring(
                request.getContextPath().length());
        OkcMenu parentOkcMenu = menuService.findByUrl(reqPath);
        if (parentOkcMenu != null) {
            ownerelement = parentOkcMenu.getOwnerelement();
        } else {
            String ret = reqPath.replace(".html", "");
            String[] arrPath = ret.split("/");
            ownerelement = arrPath[arrPath.length - 2].toUpperCase();
        }

        if (!StringUtils.hasLength(ownerelement))
            return null;

        return menuService.findTypeFilterList(ownerelement);
    }

    @ModelAttribute(value = "appAlarmFilterMenus")
    public List<OkcMenu> TypeFilterMenu(HttpServletRequest request) {
        List<OkcMenu> filterMenus = menuService.findAppFilterList("ASC");
        return filterMenus;
    }

    @ModelAttribute(value = "userDetails")
    public String getUserDetails() {
        String userDetail = null;
        try {
            Subject currentUser = SecurityUtils.getSubject();
            userDetail = currentUser.getPrincipal().toString();
        } catch (Exception e) {
            logger.error(e);
        }
        return userDetail;
    }

    public String getFinalUserId() {
        String strResult = "";

        try {
            Subject currentUser = SecurityUtils.getSubject();
            UserInfo userInfo = (UserInfo)currentUser.getPrincipal();
            return userInfo.getUserId();
        } catch (Exception e) {
            logger.error(e);
        }
        return strResult;
    }

    @ModelAttribute("sidebarMCtrs")
    public Map sidebarMCtrs() {
        Map jo = new HashMap<>();
        try {
            AppAuth appAuth = new AppAuth();
            jo.put("HVAC", "00");
            jo.put("LSPUB", "00");
            jo.put("LSN", "00");
            List<GroupMember> groupMembers = groupMemberRepository
                    .findByUserid(this.getFinalUserId());
            String groupName = "";
            if (groupMembers.size() > 0)
                groupName = groupMembers.get(0).getGroupname();
            List<AppAuth> appAuths = appAuthRepository
                    .findByGroupnameAndAppcode(groupName, "MCTRL");
            if (appAuths.size() > 0) {
                appAuth = appAuths.get(0);
            }
            String signame = appAuth.getSigname();
            logger.debug("signame----"+signame+"---getAppcode--"+appAuth.getAppcode());
            if (signame != null) {
                String appAuth_Str[] = appAuth.getSigname().split("\\|");
                jo.put("HVAC", appAuth_Str[0].substring(2, 4));
                jo.put("LSPUB", appAuth_Str[1].substring(2, 4));
                jo.put("LSN", appAuth_Str[2].substring(2, 4));
            }
        } catch (Exception e) {
            logger.error("-----sidebarMCtrs------", e);
        }
        return jo;
    }
}
