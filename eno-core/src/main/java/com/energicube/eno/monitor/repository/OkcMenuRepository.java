package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.OkcMenu;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 菜单数据操作接口
 *
 * @author CHENPING
 */
public interface OkcMenuRepository extends JpaRepository<OkcMenu, Long> {

    /**
     * 获取模块列表<br />
     * 例如：<pre>findByMenutypeAndElementtype("Module","Mod")</pre>
     *
     * @param menutype    菜单类型值,值为Module、AppFilter、AppTool、AppTab
     * @param elementtype 元素的类型，需根据MENUTYPE、OWNERELEMENT值复合逻辑处理；
     * @return 菜单列表
     */
    @Query("select m from OkcMenu m where m.menutype=?1 and m.elementtype=?2 and m.visible=true order by m.position asc,m.subposition asc")
    List<OkcMenu> findByMenutypeAndElementtypeOrderByPositionAsc(String menutype, String elementtype) throws DataAccessException;

    @Query("select m from OkcMenu m where (m.ownerelement is null or m.ownerelement='') and m.visible=true order by m.position asc")
    List<OkcMenu> findByOwnerelementOrderByPositionAsc() throws DataAccessException;

    /**
     * 获取模块对应的应用
     *
     * @param menutype     菜单类型值,值为Module、AppFilter、AppTool、AppTab
     * @param elementtype  元素的类型，需根据MENUTYPE、OWNERELEMENT值复合逻辑处理；
     * @param ownerelement 当前ELEMENT所属的父级菜单，根据ELEMENTVALUE获得，模块菜单处于顶级所以字段值与ELEMENTVALUE一致
     */
    @Query("select m from OkcMenu m where m.menutype=?1 and m.elementtype=?2 and m.ownerelement=?3 and m.visible=true order by m.position asc,m.subposition asc")
    List<OkcMenu> findByMenutypeAndElementtypeAndOwnerelementOrderByPositionAsc(String menutype, String elementtype, String ownerelement) throws DataAccessException;

    @Query("select m from OkcMenu m where m.ownerelement=?1 and m.visible=true order by m.position asc,m.subposition asc")
    List<OkcMenu> findByOwnerelementOrderByPositionAsc(String ownerelement) throws DataAccessException;

    /**
     * 获取菜单列表
     *
     * @param menutype     菜单类型
     * @param ownerelement 归属元素
     * @return 菜单列表
     */
    List<OkcMenu> findByMenutypeAndOwnerelementOrderByPositionAscSubpositionAsc(String menutype, String ownerelement) throws DataAccessException;


    /**
     * 获取指定父元素的子元素列表
     *
     * @param ownerelement 父元素编码
     * @return 子元素列表
     */
    @Query("select distinct m.headerdescription,m.elementtype,m.elementvalue,m.position,m.subposition,m.ownerelement,m.image,m.url,m.visible,m.views,m.defaultView,m.viewnames,m.menuid,m.okcmenuid,m.menutype from OkcMenu m where m.ownerelement=?1 order by m.position asc,m.subposition asc")
    List<Object[]> findByOwnerelement(String ownerelement) throws DataAccessException;


    /**
     * 获取菜单条目
     */
    List<OkcMenu> findByMenutypeAndOwnerelementAndElementvalue(String menutype, String ownerelement, String elementvalue) throws DataAccessException;


    /**
     * 获取模块类型过滤菜单
     *
     * @param ownerelement 模块代码(顶级菜单代码)
     * @return 菜单列表
     */
    //@Query("select m from OkcMenu m,OkcMenu c where m.menutype=c.elementtype and m.ownerelement=c.elementvalue and c.elementtype='TYPEFILTER' and c.ownerelement=? order by m.position asc,m.subposition asc")
    // [ ChengKang 2014-08-26 ]
    @Query("select m from OkcMenu m,OkcMenu c where m.ownerelement=c.elementvalue and c.ownerelement=? order by m.position asc,m.subposition asc")
    List<OkcMenu> findByTypeFilter(String ownerelement) throws DataAccessException;

    // @Query("select m from OkcMenu m where m.menutype='APPFILTER' and m.ownerelement=? and m.visible=true order by m.position asc,m.subposition asc")

    @Query("select m from OkcMenu m where m.ownerelement=?1 and m.menutype=?2 and m.visible=true order by m.position asc,m.subposition asc")
    List<OkcMenu> findByAppFilterAndMenutype(String ownerelement,String menutype) throws DataAccessException;

    // 根据菜单编码查找菜单
    @Query("select m from OkcMenu m where m.elementvalue=? and m.visible=true order by m.position asc,m.subposition asc")
    List<OkcMenu> findByElementvalue(String elementvalue) throws DataAccessException;

    /**
     * 获取指定URL对应的菜单
     *
     * @param url URL地址
     */
    List<OkcMenu> findByUrl(String url) throws DataAccessException;

    OkcMenu findByMenuid(Long menuid) throws DataAccessException;

    //List<OkcMenu> findByElementvalue (String elementvalue) throws DataAccessException;

    /**
     * 获取菜单列表
     *
     * @param menutype 菜单类型
     * @return 菜单列表
     */
    List<OkcMenu> findByMenutypeOrderByPositionAsc(String menutype) throws DataAccessException;

    /**
     * 查询某菜单的父级
     *
     * @param ownerelement
     * @return
     * @throws DataAccessException
     */
    @Query("select m from OkcMenu m where m.ownerelement=?1 order by m.position asc,m.subposition asc")
    List<OkcMenu> findByOwnerelementNew(String ownerelement) throws DataAccessException;
}
