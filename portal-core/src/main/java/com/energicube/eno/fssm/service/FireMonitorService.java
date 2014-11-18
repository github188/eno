package com.energicube.eno.fssm.service;


import com.energicube.eno.fssm.model.FireMonitor;

import java.util.List;

/**
 * 消防消息业务接口
 *
 * @author CHENPING
 */
public interface FireMonitorService {

    /**
     * 保存消息信息
     *
     * @param fireMonitor
     * @return {@link FireMonitor} object
     */
    public FireMonitor saveAndSendFireMonitor(FireMonitor fireMonitor);

    /**
     * 获取最后一条火警数据
     *
     * @return {@link FireMonitor} object
     */
    public List<FireMonitor> findFireMonitors();


}
