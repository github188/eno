package com.energicube.eno.monitor.service;

import com.energicube.eno.monitor.model.Pagetag;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 页面设备标签定义业务操作类
 */
public interface PagetagService {

    /**
     * 查找指定页面的TAG数据
     *
     * @param pagelayoutuid 布局ID
     * @return Tag List Collection
     * @author CHENPING
     */
    public List<Pagetag> findByPagelayoutuid(long pagelayoutuid);

    /**
     * 根据classstructureid查询指定页面的对应的数据
     * 
     * @param pagelayoutuid
     * @param classid
     * @return
     */
    public List<Pagetag> findByLayoutidAndClassId(long pagelayoutuid, String classid);

    /**
     * 根据主键查找一个
     *
     * @param id 主键
     * @return
     */
    public Pagetag findOne(long id);

    /**
     * 保存pagetag
     */
    public Pagetag savePagetag(Pagetag pagetag);
    
    /**
     * 保存客流pagetag
     */
    public Pagetag savePassengerPagetag(Pagetag pagetag);

    /**
     * 删除指定ID的设备点
     *
     * @param id 设备点ID
     */
    public void deletePagetag(long id);

    /**
     * 更新设备点坐标信息
     */
    public Pagetag updatePagetagCoordinate(long pagetagid, String left, String top);

    /**
     * 更新设备点坐标信息
     *
     * @param pagetags 标签列表
     */
    public int updatePagetagCoords(Collection<Pagetag> pagetags);

    /**
     * 获取指定布局ID对应的设备点
     *
     * @param layoutid 布局ID
     * @return 设备点列表
     */
    public List<Pagetag> findByLayoutid(String layoutid);

    /**
     * 获取指定菜单对应的设备点和控件信息
     *
     * @param menuid
     * @param layoutid
     * @param elementvalue
     * @return
     */
    public Map<String, List> findPagetagAndControlByMenuid(String menuid, String layoutid, String elementvalue);

    /**
     * 获取指定菜单对应设备点的最后测量值
     *
     * @param menuid 菜单ID
     * @return {@link Pagetag} of list
     */
    public List<Pagetag> getTagLastValues(String menuid);


    /**
     * 获取指定布局ID对应的设备点和系统组件
     *
     * @param layoutid 布局ID
     * @return 设备点和系统组件对应的数据
     */
    public Map<String, List> findPagetagAndControlByLayoutid(String layoutid);

    /**
     * 获取所有有表达式的pagetag
     *
     * @return
     */
    public List<Pagetag> findPagetagByExpressionsNotNull();

    /**
     * 获取布局上点有表达式的pagetag
     *
     * @return
     */
    public List<Pagetag> findPagetagByLayoutidAndExpressionsNotNull(String layoutid);

    /**
     * 批量更新设备点属性
     *
     * @param layoutid 布局ID
     * @param pagetag  设备点信息
     */
    public void batchUpdatePagetagProps(String layoutid, Pagetag pagetag);

    /**
     * 批量添加设备点
     *
     * @param pagetags1
     */
    public void addPagetags(Collection<Pagetag> pagetags1);
    
    /**
     * 根据pagelayoutuid 获取变配电面板
     * 
     * @param pagelayoutuid 页面编号
     * @return Map<String, Object>
     * @throws Exception
     */
	public Map<String, Object> findETDPanelDatas(String pagelayoutuid) throws Exception;

	/**
	 * 设备排序
	 * 
	 * @param pagetagid
	 * @param type (type=true, 向上移动; type=false, 往下移动)
	 */
	public void sortTags(long pagetagid, boolean type) throws Exception;

	/**
	 *  从配置好的Pagetag中批量删除 PageTags
	 * @param pagetagids
	 */
	public void deletePagetags(String[] pagetagids) throws Exception;

}
