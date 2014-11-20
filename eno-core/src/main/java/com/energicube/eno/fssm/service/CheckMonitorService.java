package com.energicube.eno.fssm.service;

import com.energicube.eno.fssm.model.CheckMonitor;
import org.joda.time.LocalDateTime;

import java.util.List;

/**
 * 查岗业务接口
 *
 * @author CHENPING
 */
public interface CheckMonitorService {

    /**
     * 保存查岗消息
     *
     * @param 查岗消息对象
     * @return {@link CheckMonitor}
     */
    public CheckMonitor saveConsumeOfCheckMonitor(CheckMonitor checkMonitor);

    /**
     * 响应查岗消息
     *
     * @param 查岗消息对象
     * @return {@link CheckMonitor}
     */
    public CheckMonitor saveProduceOfCheckMonitor(CheckMonitor checkMonitor);

    /**
     * 获取最后一条未响应的查岗消息
     *
     * @return {@link CheckMonitor}
     */
    public CheckMonitor getLastNoResponseCheckMonitor();


    /**
     * 获取所有接收到的查岗消息
     *
     * @return list of {@link CheckMonitor}
     * @author CHENPING
     */
    public List<CheckMonitor> findAllConsumeOfCheckMonitor();

    /**
     * 回复所有应答的查网消息
     *
     * @return list of {@link CheckMonitor}
     * @author CHENPING
     */
    public List<CheckMonitor> findAllProduceOfCheckMonitor();


    /**
     * 获取指定时间范围内接收到的查岗消息
     *
     * @param starttime 开始时间
     * @param endtime   结束时间
     * @return list of {@link CheckMonitor}
     * @author CHENPING
     */
    public List<CheckMonitor> findConsumeOfCheckMonitorByDateRange(LocalDateTime starttime, LocalDateTime endtime);

    /**
     * 指定时间范围内应答的查岗消息
     *
     * @param starttime 开始时间
     * @param endtime   结束时间
     * @return list of {@link CheckMonitor}
     * @author CHENPING
     */
    public List<CheckMonitor> findProduceOfCheckMonitorByDateRange(LocalDateTime starttime, LocalDateTime endtime);

}
