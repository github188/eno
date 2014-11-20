package com.energicube.eno.monitor.service;

import com.energicube.eno.monitor.model.OpLogs;
import com.energicube.eno.monitor.model.UcEventLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 操作日志操作类
 */
public interface OpLogService {

    /**
     * 保存操作日志
     */
    public OpLogs save(OpLogs opLog);

    /**
     * 获取指定子系统的操作日志
     */
    public List<OpLogs> findBySubsys(String subsys);


    public OpLogs saveLogByUserAndMsg(String userName, String msg, String level, String subSys, String loger, String category);

    /**
     * 获取指定类别的操作日志
     *
     * @param category 日志类别
     * @return 日志分页列表
     */
    public Page<OpLogs> findByCategory(String category, Pageable pageable);

    /**
     * 查询最新的日志存储到运行日志中
     * @param logId 日志ID
     * @return 日志集合
     */
    public List<UcEventLog> findUcEventlog(long logId);

}
