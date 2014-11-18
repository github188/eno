package com.energicube.eno.fssm.service;


import com.energicube.eno.fssm.model.LinkMonitor;
import org.joda.time.LocalDateTime;

import java.util.List;

/**
 * 链路消息接口
 *
 * @author CHENPING
 */
public interface LinkMonitorService {


    /**
     * 保存链路信息
     *
     * @param linkMonitor 链路消息对象
     * @return {@link LinkMonitor}
     * @author CHENPING
     */
    public LinkMonitor saveLinkMonitor(LinkMonitor linkMonitor);

    /**
     * 获取所有链接消息
     *
     * @return list of {@link LinkMonitor}
     * @author CHENPING
     */
    public List<LinkMonitor> findAll();

    /**
     * 获取指定时间范围内的链接消息
     *
     * @param starttime 开始时间
     * @param endtime   结束时间
     * @return list of {@link LinkMonitor}
     * @author CHENPING
     */
    public List<LinkMonitor> findLinkMonitorByDateRange(LocalDateTime starttime, LocalDateTime endtime);
}
