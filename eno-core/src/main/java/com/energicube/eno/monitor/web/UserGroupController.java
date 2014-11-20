package com.energicube.eno.monitor.web;

import com.energicube.eno.monitor.model.*;
import com.energicube.eno.monitor.repository.AppAuthRepository;
import com.energicube.eno.monitor.repository.MenuOptionReportRepository;
import com.energicube.eno.monitor.repository.OkcGroupRepository;
import com.energicube.eno.monitor.repository.OkcMenuRepository;
import com.energicube.eno.monitor.service.DictionaryService;
import com.energicube.eno.monitor.service.MenuService;
import com.energicube.eno.monitor.service.UserGroupService;
import com.energicube.eno.monitor.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * 角色与权限管理
 *
 * @author ZHANGTAO
 */
@Controller
@RequestMapping("/okcsys")
public class UserGroupController extends BaseController {

    private static final Log logger = LogFactory.getLog(UserGroupController.class);

    @Autowired
    private MenuService menuService;

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private OkcGroupRepository okcGroupRepository;

    @Autowired
    private AppAuthRepository appAuthRepository;

    @Autowired
    private OkcMenuRepository okcMenuRepository;

    @Autowired
    private MenuOptionReportRepository menuOptionReportRepository;

    @Autowired
    private DictionaryService dictionaryService;

    @Autowired
    private UserService userService;

    /**
     * @param model
     * @return 跳转到角色与权限页面
     */

    @RequestMapping(value = "/usergroup", method = RequestMethod.GET)
    public String initSelectRuleAndFunIndex(Model model) {
        List<OkcGroup> userGroups = okcGroupRepository.findAll();
        model.addAttribute("userGroups", userGroups);
        return "usergroup/index";
    }

    // 添加用户组
    @RequestMapping(value = "/usergroup/doInsertUserGroup", method = RequestMethod.GET)
    public
    @ResponseBody
    List doInsertUserGroup(HttpServletRequest request,
                           HttpServletResponse response, Model model) {
        OkcGroup userGroup = new OkcGroup();
        List<OkcGroup> userGroups = new ArrayList<OkcGroup>();
        List list = new ArrayList();
        try {
            AppAuth appAuthMctrl = new AppAuth();
            String ugroupIdValue = request.getParameter("ugroupIdValue");
            String groupname = new String(request.getParameter("jobGw")
                    .getBytes("ISO-8859-1"), "utf-8");
            String roleSelectValue = request.getParameter("roleSelectValue");
            String groupcomment = new String(request.getParameter(
                    "groupcomment").getBytes("ISO-8859-1"), "utf-8");
            userGroup.setGroupname(groupname);
            userGroup.setDescription(groupcomment);
            userGroup.setLangcode(roleSelectValue);
            if (!ugroupIdValue.equals("")) {
                List<OkcGroup> userGroup_Update = okcGroupRepository.findByGroupname(groupname);
                if (userGroup_Update.size() > 0 && userGroup_Update.get(0).getUgroupid() != Long.valueOf(ugroupIdValue)) {
                    list.add(0, "error");
                } else {
                    userGroup.setUgroupid(Long.valueOf(ugroupIdValue));
                    List<AppAuth> appAuthListByGroupName = appAuthRepository
                            .findByGroupname(okcGroupRepository.findOne(
                                    Long.valueOf(ugroupIdValue)).getGroupname());
                    if (appAuthListByGroupName.size() > 0) {
                        for (int k = 0; k < appAuthListByGroupName.size(); k++) {
                            if (appAuthListByGroupName.get(k).getAppcode().equals("MCTRL")) {
                                appAuthMctrl = appAuthListByGroupName.get(k);
                            }
                            appAuthRepository.delete(appAuthListByGroupName.get(k));
                        }
                    }
                    OkcGroup userGroupxp = okcGroupRepository.findOne(Long
                            .valueOf(ugroupIdValue));
                    List<GroupMember> groupMemberList = userGroupService
                            .getGroupMembersByGroupname(userGroupxp.getGroupname());
                    for (int lengt = 0; lengt < groupMemberList.size(); lengt++) {
                        GroupMember groupMemberValue = groupMemberList.get(lengt);
                        groupMemberValue.setGroupname(groupname);
                        userGroupService.saveGroupMembers(groupMemberValue);
                    }
                    List<MenuOption> menuOptionUpdateList = menuOptionReportRepository
                            .findByGroupname(userGroupxp.getGroupname());
                    String patStrg = "";
                    for (int lengt = 0; lengt < menuOptionUpdateList.size(); lengt++) {
                        MenuOption menuOptionValue = menuOptionUpdateList
                                .get(lengt);
                        patStrg = patStrg + menuOptionValue.getOkcmenuid() + ",";
                    }
                    String pat = request.getParameter("pat");
                    if (!pat.equals("")) {
                        String[] patString = pat.split(",");
                        for (int app = 0; app < patString.length; app++) {
                            AppAuth appAuth = new AppAuth();
                            appAuth.setGroupname(groupname);
                            appAuth.setAppcode(patString[app]);
                            if (patString[app].equals("MCTRL") && appAuthMctrl.getAppcode() != null && appAuthMctrl.getAppcode().equals("MCTRL")) {
                                appAuth.setSigname(appAuthMctrl.getSigname());
                            }
                            appAuthRepository.save(appAuth);
                        }
                    }
                    if (pat.equals("")) {
                        if (!patStrg.equals("")) {
                            String[] patString = patStrg.split(",");
                            for (int app = 0; app < patString.length; app++) {
                                menuOptionReportRepository
                                        .delete(menuOptionReportRepository
                                                .findByOkcmenuidAndGroupId(
                                                        patString[app],
                                                        userGroupxp.getGroupname()));
                            }
                        } else {
                        }
                    } else {
                        if (!patStrg.equals("")) {
                            String[] patStringYl = patStrg.split(",");
                            String[] patStringhl = pat.split(",");
                            List patStringCj = chaji(patStringYl, patStringhl);
                            if (patStringCj.size() > 0) {
                                for (int kt = 0; kt < patStringCj.size(); kt++) {
                                    menuOptionReportRepository
                                            .delete(menuOptionReportRepository
                                                    .findByOkcmenuidAndGroupId(
                                                            patStringCj.get(kt)
                                                                    .toString(),
                                                            userGroupxp
                                                                    .getGroupname()));
                                }
                            }
                        } else {
                        }
                    }
                    List<MenuOption> menuOptionUpdatehlList = menuOptionReportRepository
                            .findByGroupname(userGroupxp.getGroupname());
                    for (int lengt = 0; lengt < menuOptionUpdatehlList.size(); lengt++) {
                        MenuOption menuOptionValue = menuOptionUpdatehlList
                                .get(lengt);
                        menuOptionValue.setGroupname(groupname);
                        menuOptionReportRepository.save(menuOptionValue);
                    }
                    okcGroupRepository.save(userGroup);
                    list.add(0, "success");
                }
            } else {
                userGroups = okcGroupRepository.findByGroupname(groupname);
                if (userGroups.size() > 0) {
                    list.add(0, "error");
                } else {
                    okcGroupRepository.save(userGroup);
                    String pat = request.getParameter("pat");
                    if (!pat.equals("")) {
                        String[] patString = pat.split(",");
                        for (int app = 0; app < patString.length; app++) {
                            AppAuth appAuth = new AppAuth();
                            appAuth.setGroupname(groupname);
                            appAuth.setAppcode(patString[app]);
                            appAuthRepository.save(appAuth);
                        }
                    }
                    list.add(0, "success");
                }
            }
            userGroups = okcGroupRepository.findAll();
            model.addAttribute("userGroups", userGroups);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List chaji(String[] arr1, String[] arr2) {
        List list = new ArrayList();
        String arr1Str = "";
        String arr2Str = "";
        int flag = 0;
        for (int i = 0; i < arr1.length; i++) {
            for (int j = 0; j < arr2.length; j++) {
                arr1Str = arr1[i];
                arr2Str = arr2[j];
                if (arr2Str.equals(arr1Str)) {
                    flag = 1;
                }
            }
            if (flag != 1) {
                list.add(arr1[i]);
            }
            flag = 0;
        }
        return list;
    }

    // 修改用户组
    @RequestMapping(value = "/usergroup/usergroupUpdate", method = RequestMethod.GET)
    public
    @ResponseBody
    UserGroupTo usergroupUpdate(HttpServletRequest request,
                                HttpServletResponse response, Model model) {
        OkcGroup userGroup = new OkcGroup();
        UserGroupTo userGroupTo = new UserGroupTo();
        List<OkcGroup> userGroups = new ArrayList<OkcGroup>();
        List<AppAuth> appAuthList = new ArrayList<AppAuth>();
        try {
            String groupId = request.getParameter("groupId");
            userGroup = okcGroupRepository.findOne(Long.valueOf(groupId));
            userGroupTo.setGroupId(userGroup.getUgroupid());
            userGroupTo.setGroupName(userGroup.getGroupname());
            userGroupTo.setGroupComment(userGroup.getDescription());
            userGroupTo.setLangcode(userGroup.getLangcode());
            appAuthList = this.findMenuByUserGroup(userGroup.getGroupname());
            userGroupTo.setAppAuthList(appAuthList);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userGroupTo;
    }

    // 刷新用户组
    @RequestMapping(value = "/usergroup/refrashUserGroup", method = RequestMethod.GET)
    public
    @ResponseBody
    List<UserGroupTo> refrashUserGroup(Model model) {
        List<UserGroupTo> userGroupTos = new ArrayList<UserGroupTo>();
        Sort sort = new Sort(Sort.Direction.DESC, "ugroupid");
        List<OkcGroup> userGroups = okcGroupRepository.findAll(sort);
        for (int i = 0; i < userGroups.size(); i++) {
            UserGroupTo userGroupTo = new UserGroupTo();
            List<AppAuth> appAuthList = new ArrayList<AppAuth>();
            OkcGroup userGroup = userGroups.get(i);
            appAuthList = this.findMenuByUserGroup(userGroup.getGroupname());
            userGroupTo.setGroupId(userGroup.getUgroupid());
            userGroupTo.setGroupComment(userGroup.getDescription());
            userGroupTo.setGroupName(userGroup.getGroupname());
            userGroupTo.setAppAuthList(appAuthList);
            userGroupTos.add(userGroupTo);
        }
        return userGroupTos;
    }

    // 删除用户组
    @RequestMapping(value = "/usergroup/deleteUserGroupBygroupId", method = RequestMethod.GET)
    public String deleteUserGroupBygroupId(HttpServletRequest request,
                                           HttpServletResponse response, Model model) {
        String groupId = request.getParameter("groupId");
        List<GroupMember> groupMemberList = userGroupService
                .getGroupMembersByGroupname(okcGroupRepository.findOne(
                        Long.valueOf(groupId)).getGroupname());
        List<AppAuth> appAuthListByGroupName = appAuthRepository
                .findByGroupname(okcGroupRepository.findOne(
                        Long.valueOf(groupId)).getGroupname());
        List<MenuOption> menuOptionListByGroupName = menuOptionReportRepository
                .findByGroupname(okcGroupRepository.findOne(
                        Long.valueOf(groupId)).getGroupname());

        for (int i = 0; i < groupMemberList.size(); i++) {
            userGroupService.deleteGroupMembers(groupMemberList.get(i));
        }
        for (int i = 0; i < appAuthListByGroupName.size(); i++) {
            appAuthRepository.delete(appAuthListByGroupName.get(i));
        }
        for (int i = 0; i < menuOptionListByGroupName.size(); i++) {
            menuOptionReportRepository.delete(menuOptionListByGroupName.get(i));
        }
        okcGroupRepository.delete(Long.valueOf(groupId));
        List<OkcGroup> userGroups = okcGroupRepository.findAll();
        model.addAttribute("userGroups", userGroups);
        return "redirect:";
    }

    // 新增二级权限
    @RequestMapping(value = "/usergroup/doInsertMenuOption", method = RequestMethod.GET)
    public String doInsertMenuOption(HttpServletRequest request,
                                     HttpServletResponse response, Model model) {
        try {
            String para1 = request.getParameter("para1");
            String para2 = request.getParameter("para2");
            String para3 = new String(request.getParameter("para3").getBytes(
                    "ISO-8859-1"), "utf-8");
            MenuOption menuOption = new MenuOption();
            List<MenuOption> menuOptionList = menuOptionReportRepository
                    .findByOkcmenuidAndGroupId(para2, para3);
            if (menuOptionList.size() > 0) {
                menuOption = menuOptionList.get(0);
            }
            menuOption.setOptionname(para1);
            menuOption.setOkcmenuid(para2);
            menuOption.setGroupname(para3);
            menuOptionReportRepository.save(menuOption);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:";
    }

    // 刷新用户组
    @RequestMapping(value = "/usergroup/findModuleList", method = RequestMethod.GET)
    public
    @ResponseBody
    List<OkcMenu> findModuleList(Model model) {
        List<OkcMenu> moduleList = menuService.findModuleList();
        return moduleList;
    }

    // 查询特定角色控制的菜单
    public List<AppAuth> findMenuByUserGroup(String groupname) {
        List<AppAuth> appAuthList = new ArrayList<AppAuth>();
        List<AppAuth> appAuthListByGroupName = appAuthRepository.findByGroupname(groupname);
        logger.info(groupname + ":-------------------:" + appAuthListByGroupName.size());
        int sum = appAuthListByGroupName.size();
        for (int i = 0; i < sum; i++) {
            AppAuth appAuth = appAuthListByGroupName.get(i);
            OkcMenu okcMenu = okcMenuRepository.findByElementvalue(
                    appAuthListByGroupName.get(i).getAppcode()).get(0);
            appAuth.setAppcode(okcMenu.getHeaderdescription());
            appAuthList.add(appAuth);
        }
        return appAuthList;
    }

    /**
     * 查询用户组的权限
     * @param groupname 组名
     * @return 权限列表
     */
    @RequestMapping(value = "/usergroup/findMenuByUserGroupName", method = RequestMethod.GET)
    public
    @ResponseBody
    List<AppAuth> findMenuByUserGroupName(HttpServletRequest request,
                                          HttpServletResponse response, String groupname) {
        List<AppAuth> appAuthList = new ArrayList<AppAuth>();
        try {

            logger.debug("--groupname-1--"+groupname);
            List<AppAuth> appAuthListByGroupName = appAuthRepository
                    .findByGroupname(groupname);
            for (int i = 0; i < appAuthListByGroupName.size(); i++) {
                AppAuth appAuth = appAuthListByGroupName.get(i);
                String code = appAuthListByGroupName.get(i).getAppcode();
                OkcMenu okcMenu = okcMenuRepository.findByElementvalue(
                        appAuthListByGroupName.get(i).getAppcode()).get(0);
                appAuth.setSigname(okcMenu.getHeaderdescription());
                appAuth.setAppcode(code);
                List<OkcMenu> okcMenuList = menuService.findAppList(code);
                appAuth.setOkcMenuList(okcMenuList);
                appAuthList.add(appAuth);
            }
        } catch (Exception e) {
            logger.error("findMenuByUserGroupName",e);
        }
        return appAuthList;
    }

    @RequestMapping(value = "/usergroup/findAppMenu", method = RequestMethod.GET)
    public
    @ResponseBody
    List<OkcMenu> findAppMenu(HttpServletRequest request,
                              HttpServletResponse response, Model model) {
        List<OkcMenu> appList = new ArrayList<OkcMenu>();
        try {
            String appcode = request.getParameter("appcode");
            appList = menuService.findAppList(appcode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appList;
    }

    @RequestMapping(value = "/usergroup/findMenuByFuncokcmenuid", method = RequestMethod.GET)
    public
    @ResponseBody
    List<OkcMenu> findMenuByFuncokcmenuid(HttpServletRequest request,
                                          HttpServletResponse response, Model model) {
        List<OkcMenu> okcMenuList = new ArrayList<OkcMenu>();
        try {
            String func_okcmenuids = request.getParameter("func_okcmenuids");
            if (!func_okcmenuids.equals("")) {
                String[] patString = func_okcmenuids.split(",");
                for (int i = 0; i < patString.length; i++) {
                    OkcMenu okcMenu = okcMenuRepository.findOne(Long
                            .valueOf(patString[i]));
                    okcMenuList.add(okcMenu);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return okcMenuList;
    }

    @RequestMapping(value = "/usergroup/findFilterMenu", method = RequestMethod.GET)
    public
    @ResponseBody
    List<OkcMenu> findFilterMenu(HttpServletRequest request,
                                 HttpServletResponse response, Model model) {
        List<OkcMenu> appFilterList = new ArrayList<OkcMenu>();
        try {
            String appcode = request.getParameter("appcode");
            appFilterList = menuService.findAppFilterList(appcode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appFilterList;
    }

    @RequestMapping(value = "/usergroup/findByMenutypeAndOwnerelement", method = RequestMethod.GET)
    public
    @ResponseBody
    List<OkcMenu> findByMenutypeAndOwnerelement(HttpServletRequest request,
                                                HttpServletResponse response, Model model) {
        List<OkcMenu> appFilterList = new ArrayList<OkcMenu>();
        try {
            String menuType = request.getParameter("menuType");
            String appcode = request.getParameter("appcode");
            appFilterList = menuService.findByMenutypeAndOwnerelement(menuType,
                    appcode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appFilterList;
    }

    @RequestMapping(value = "/usergroup/doInsertDataListCg", method = RequestMethod.POST)
    @ResponseBody
    public String doInsertDataListCg(@RequestBody List<MenuOption> dataListCg,
                                     HttpServletRequest request, HttpServletResponse response,
                                     Model model) {
        try {
            /**
             * 之前做的暂时注释 List<MenuOption> menuOptionInsert = new
             * ArrayList<MenuOption>(); List<MenuOption> menuOptionUpdate = new
             * ArrayList<MenuOption>(); List<MenuOption> menuOptionYs =
             * menuOptionReportRepository .findAll(); List<MenuOption>
             * menuOptions = dataListCg; if (menuOptionYs.size() > 0) { for (int
             * i = 1; i < menuOptions.size(); i++) { MenuOption menuOptioni =
             * menuOptions.get(i); for (int j = 0; j < menuOptionYs.size(); j++)
             * { MenuOption menuOptionj = menuOptionYs.get(j); if
             * (menuOptioni.getGroupname().equals( menuOptionj.getGroupname())
             * && menuOptioni.getOkcmenuid().equals(
             * menuOptionj.getOkcmenuid())) {
             * menuOptioni.setId(menuOptionj.getId());
             * menuOptionUpdate.add(menuOptioni); } else {
             * menuOptionInsert.add(menuOptioni); } } }
             * System.out.print("menuOptionUpdate.size()=" +
             * menuOptionUpdate.size());
             * System.out.print("menuOptionInsert.size()=" +
             * menuOptionInsert.size()); for (int m = 0; m <
             * menuOptionUpdate.size(); m++) {
             * menuOptionReportRepository.save(menuOptionUpdate.get(m)); } for
             * (int n = 0; n < menuOptionInsert.size(); n++) {
             * menuOptionReportRepository.save(menuOptionInsert.get(n)); } }
             * else { for (int m = 1; m < menuOptions.size(); m++) {
             * menuOptionReportRepository.save(menuOptions.get(m)); } }
             */
            List<MenuOption> menuOptions = dataListCg;
            if (menuOptions.size() > 1) {
                List<AppAuth> appAuths = appAuthRepository
                        .findByGroupnameAndAppcode(menuOptions.get(1)
                                .getGroupname(), "MCTRL");
                if (appAuths.size() > 0) {
                    AppAuth appAuth = appAuths.get(0);
                    String signame = "";
                    for (int i = 1; i < menuOptions.size(); i++) {
                        signame = signame + menuOptions.get(i).getOkcmenuid() + "_"
                                + menuOptions.get(i).getOptionname() + "|";
                    }
                    appAuth.setSigname(signame);
                    appAuthRepository.save(appAuth);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @RequestMapping(value = "/usergroup/findByGroupnameAndAppcode", method = RequestMethod.GET)
    public
    @ResponseBody
    AppAuth findByGroupnameAndAppcode(HttpServletRequest request,
                                      HttpServletResponse response, Model model) {
        AppAuth appAuth = new AppAuth();
        try {
            String groupname = new String(request.getParameter("groupname")
                    .getBytes("ISO-8859-1"), "utf-8");
            List<AppAuth> appAuths = appAuthRepository.findByGroupnameAndAppcode(
                    groupname, "MCTRL");
            if (appAuths.size() > 0)
                appAuth = appAuths.get(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appAuth;
    }

    @RequestMapping(value = "/usergroup/findByOkcmenuidAndGroupId", method = RequestMethod.GET)
    public
    @ResponseBody
    List<MenuOption> findByOkcmenuidAndGroupId(HttpServletRequest request,
                                               HttpServletResponse response, Model model) {
        List<MenuOption> menuOptions = new ArrayList<MenuOption>();
        try {
            String okcmenuId = request.getParameter("okcmenuId");
            String groupId = request.getParameter("groupId");
            menuOptions = menuOptionReportRepository.findByOkcmenuidAndGroupId(
                    okcmenuId, groupId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return menuOptions;
    }

    @RequestMapping(value = "/usergroup/findMenuByMenuOption", method = RequestMethod.GET)
    public
    @ResponseBody
    List<OkcMenu> findMenuByMenuOption(HttpServletRequest request,
                                       HttpServletResponse response, Model model) {
        List<MenuOption> menuOptions = new ArrayList<MenuOption>();
        MenuOption menuOption = new MenuOption();
        List<OkcMenu> okcMenus = new ArrayList<OkcMenu>();
        try {
            String appcode = request.getParameter("appcode");
            String groupname = new String(request.getParameter("groupname")
                    .getBytes("ISO-8859-1"), "utf-8");
            menuOptions = menuOptionReportRepository.findByOkcmenuidAndGroupId(
                    appcode, groupname);
            if (menuOptions.size() > 0) {
                menuOption = menuOptions.get(0);
                String pat = menuOption.getOptionname();
                if (!pat.equals("")) {
                    String[] patString = pat.split(",");
                    for (int app = 0; app < patString.length; app++) {
                        String appId = patString[app];
                        OkcMenu ocMenu = okcMenuRepository.findOne(Long
                                .valueOf(appId));
                        okcMenus.add(ocMenu);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return okcMenus;
    }

    /**
     * 用户类别
     */
    @ModelAttribute("userclasses")
    public List<OkcDMAlphaNum> GetUserClass() {
        // 用户类型,取字典表
        return dictionaryService.findALNDictionaryByDictid("USERCATEGORY");
    }
}
