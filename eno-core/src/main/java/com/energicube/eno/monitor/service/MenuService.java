package com.energicube.eno.monitor.service;

import com.energicube.eno.monitor.model.OkcMenu;
import com.energicube.eno.monitor.model.OkcMenuSet;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 菜单逻辑操作接口类
 *
 * @author CHENPING
 */
public interface MenuService {


    /**
     * 获取所有菜单数据
     */
    public List<OkcMenu> findAll();

    /**
     * 获取模块列表
     *
     * @return 模块列表
     */
    public List<OkcMenu> findModuleList();

    /**
     * 获取主菜单列表
     *
     * @return 主菜单列表(包括默认的)
     */
    public List<OkcMenu> findMainMenu();

    /**
     * 获取主菜单列表
     *
     * @return 主菜单列表json(不包括模式的)
     */
    public String findMainMenuNoPatter();

    /**
     * 递归调用函数，获取指定顶级菜单的所有子菜单（多级），并作为一个JSON对象返回
     *
     * @param okcmenu
     * @return
     */
    public Map getMenuJsonObj(OkcMenu okcmenu);

    /**
     * 获取模块应用列表
     *
     * @param moduleId 模块ID
     * @return 模块应用列表
     */
    public List<OkcMenu> findAppList(String moduleId);

    /**
     * 获取模块过滤菜单
     *
     * @param ownerelement 父级菜单代码
     */
    public List<OkcMenu> findTypeFilterList(String ownerelement);


    @Transactional(readOnly = true)
    public List<OkcMenu> findAppFilterList(String ownerelement);

    /**
     * 获取菜单列表
     *
     * @param ownerelement 父级菜单代码
     */
    @Transactional(readOnly = true)
    public List<OkcMenu> findMenuList(String ownerelement);

    /**
     * 获取菜单列表
     *
     * @param menutype     菜单类型
     * @param elementtype  元素类型
     * @param ownerelement 归属元素
     * @return 菜单列表
     */
    public List<OkcMenu> findByMenutypeAndElementtypeAndOwnerelement(String menutype, String elementtype, String ownerelement);

    /**
     * 获取指定父元素的子元素列表
     *
     * @param ownerelement 父元素编码
     * @return 子元素列表
     */
    public List<OkcMenu> findByOwnerelement(String ownerelement);

    /**
     * 获取指定菜单编码的菜单项
     *
     * @param elementvalue 菜单编码
     * @return 元素列表
     */
    public List<OkcMenu> findByElementvalue(String elementvalue);

    /**
     * 获取菜单项
     *
     * @param menutype     菜单类型
     * @param ownerelement 归属元素
     * @param elementvalue 元素值
     * @return 菜单项
     */
    public OkcMenu findMenuItem(String menutype, String ownerelement, String elementvalue);


    /**
     * 获取菜单列表
     *
     * @param menutype     菜单类型
     * @param ownerelement 归属元素
     * @return 菜单列表
     */
    List<OkcMenu> findByMenutypeAndOwnerelement(String menutype, String ownerelement);

    /**
     * 获取菜单组合列表
     *
     * @param menutype     菜单类型
     * @param ownerelement 归属元素
     * @return 菜单组合列表
     */
    List<OkcMenuSet> findOkcMenuSetList(String menutype, String ownerelement);


    /**
     * 获取指定ID的菜单对象
     *
     * @param menuid 菜单ID
     * @return {@link OkcMenu} object
     */
    @Transactional(readOnly = true)
    OkcMenu findByMenuid(Long menuid);


    /**
     * 获取指定URL对应的菜单
     *
     * @param url URL地址
     */
    @Transactional(readOnly = true)
    OkcMenu findByUrl(String url);

    /**
     * 保存菜单项
     *
     * @param okcMenu 菜单对象
     * @return 菜单项
     */
    OkcMenu saveOkcMenu(OkcMenu okcMenu);

    /**
     * 保存复制对象
     */
    OkcMenu saveCopyOkcMenu(OkcMenu okcMenu);


    /**
     * 删除指定的菜单项
     *
     * @param menuid 菜单ID
     */
    void deleteMenu(Long menuid);

    OkcMenu findOne(Long menuid);


    String getMenuTreeString(HttpServletRequest request);

    String getMenuTreeString(HttpServletRequest request, boolean hasLink);

    String getZTreeNodeCollection(String ownerelement);

    /**
     * 获取菜单列表
     *
     * @param menutype 菜单类型
     * @return 菜单列表
     */
    List<OkcMenu> findByMenutype(String menutype);

    /**
     * 删除指定的菜单项
     *
     * @param okcMenu 菜单
     */
    void deleteMenu(OkcMenu okcMenu);
}
