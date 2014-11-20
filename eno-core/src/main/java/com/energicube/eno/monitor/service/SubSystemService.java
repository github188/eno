package com.energicube.eno.monitor.service;

import com.energicube.eno.message.redis.TagInfo;
import com.energicube.eno.monitor.model.Dict;
import com.energicube.eno.monitor.model.Pagetag;

import java.util.List;
import java.util.Map;

/**
 * 各子系统业务操作类
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
    public List<Object> findPanelContent(String layoutid, String pagetagid, List<TagInfo> taginfo, Map<String, Dict> contextMap);

    /**
     * 生成面板对应的html代码
     *
     * @param layoutid
     * @param pagetagList
     * @param taginfo
     * @param contextMap
     * @return
     */
    public List<Object> getPanelHtml(String layoutid, List pagetagList, List<TagInfo> taginfo, Map<String, Dict> contextMap);

    /**
     * 获取设备列表需要的数据
     *
     * @param layoutid
     * @param map
     * @param pagetagList
     * @param taginfo
     * @param contextMap
     * @return
     */
    public Map<String, List> getDeviceMap(String layoutid, Map<String, List> map, List<Pagetag> pagetagList, List<TagInfo> taginfo, Map<String, Dict> contextMap);
}
