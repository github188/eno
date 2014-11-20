package com.energicube.eno.monitor.service;

import com.energicube.eno.monitor.model.KeyValue;
import com.energicube.eno.monitor.model.OkcMenu;
import com.energicube.eno.monitor.model.Pagelayout;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface PagelayoutService {

    Pagelayout findOne(long pagelayoutuid);

    List<Pagelayout> findAllPagelayout();


    /**
     * 查询指定菜单的默认页面布局
     *
     * @param menuid 菜单ID
     * @return 页面布局列表
     */
    Pagelayout findDefaultLayoutByMenuid(String menuid);


    /**
     * 获取指定菜单和分页对应的页面布局对象
     *
     * @param menuid    菜单id
     * @param pageindex 页码
     * @return 布局对象
     */
    Pagelayout findLayoutsByMenuid(String menuid, int pageindex);


    /**
     * 获取指定菜菜单的甩有页面布局
     *
     * @param menuid 菜单ID
     * @return 页面布局列表
     */
    List<Pagelayout> findLayoutsByMenuid(String menuid);

    /**
     * 分页查询所有布局
     *
     * @param pageable 分页参数对象
     * @return 页面布局分页列表
     */
    Page<Pagelayout> findPagelayoutList(Pageable pageable);

    /**
     * 保存页面布局
     *
     * @param pagelayout 页面布局对象
     * @return 持久化对象
     */
    Pagelayout savePagelayout(Pagelayout pagelayout);

    /**
     * 删除页面布局
     *
     * @param pagelayoutuid 页面布局ID
     */
    void deletePagelayout(long pagelayoutuid);


    /**
     * 上传背景图片
     *
     * @param request  http请求对象
     * @param filename 文件重命名名称，如果为空，则为原文件名称
     * @param syscode  系统编码
     * @return 文件服务器保存路径
     */
    String saveBackgroundFile(HttpServletRequest request, String syscode, String filename) throws Exception;


    /**
     * 上传菜单配置文件
     *
     * @param request  http请求对象
     * @param filename 文件重命名名称，如果为空，则为原文件名称
     * @param syscode  系统编码
     * @return 文件服务器保存路径
     * @author ChengKang
     */
    // 将服务的方法声明与实现分开，此处声明了saveMenuFile方法，该方法在PageManageController.java中被调用 [ ChengKang 2014-07-25 ]
    OkcMenu[] saveMenuFile(HttpServletRequest request, String syscode, String filename) throws Exception;

    /**
     * 下载（导出）菜单配置文件
     *
     * @param request  http请求对象
     * @param filename 下载的配置文件名称
     * @param syscode  系统编码
     * @return 文件服务器保存路径
     * @author ChengKang
     */
    // 将服务的方法声明与实现分开，此处声明了exportMenuFile方法，该方法在PageManageController.java中被调用 [ ChengKang 2014-07-27 ]
    String exportMenuFile_old(HttpServletRequest request, String syscode, List<OkcMenu> Menus);

    /**
     * 下载（导出）菜单配置文件
     *
     * @param request  http请求对象
     * @param filename 下载的配置文件名称
     * @param syscode  系统编码
     * @return 文件服务器保存路径
     * @author ChengKang
     */
    // 将服务的方法声明与实现分开，此处声明了exportMenuFile方法，该方法在PageManageController.java中被调用 [ ChengKang 2014-09-05 ]
    String exportMenuFile(HttpServletRequest request, String syscode, List<Map> Menus);


    List<KeyValue> getFileNames(HttpServletRequest request, String syscode);

    /**
     * 根据layoutid查找对象
     *
     * @param layoutid
     * @return
     */
    public List<Pagelayout> findByLayoutid(String layoutid);

    /**
     * 根据配置id查找对象
     *
     * @param deviceconfigid
     * @return
     */
    public List<Pagelayout> findByDeviceconfigid(int deviceconfigid);

}
