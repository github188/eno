package com.energicube.eno.monitor.service;

import com.energicube.eno.monitor.model.Pagetag;
import java.util.List;
import java.util.Map;

/**
 * 各子系统业务操作类，目前用于暖通空调面板及设备列表业务操作
 *
 * @author zouzhixiang
 * @date 2014-09-21
 */
public interface SubSystemService {
    /**
     * 获取面板内容
     * 
     * @param layoutid
     * @param pagetagid
     * @return
     */
    public List<Object> findPanelContent(String layoutid, String pagetagid);

    /**
     * 生成面板对应的html代码
     * 
     * @param layoutid
     * @param pagetagList
     * @return
     */
    public List<Object> getPanelHtml(String layoutid, List pagetagList);

    /**
     * 获取设备列表需要的数据
     * 
     * @param layoutid
     * @param map
     * @param pagetagList
     * @return
     */
    public Map<String, List> getDeviceMap(String layoutid, Map<String, List> map, List<Pagetag> pagetagList);
}
