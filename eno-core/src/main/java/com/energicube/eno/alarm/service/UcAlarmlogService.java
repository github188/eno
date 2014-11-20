package com.energicube.eno.alarm.service;


import com.energicube.eno.alarm.model.UcAlarmlog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

/**
 * 报警信息业务操作类
 */
public interface UcAlarmlogService {

    /**
     * 保存报警信息
     *
     * @param almlogid 实时报警唯一标识ID
     */
    public void batchSaveUcAlarmlog(int almlogid);

    /**
     * 根据子系统,报警级别，分页查询某段时间的报警记录
     * @param startDate
     * @param endDate
     * @param groupName
     * @param almpriority
     * @return
     */
    public Page<UcAlarmlog> searchUcAlarmlogList(String groupName, int almpriority, Date startDate, Date endDate, Pageable pageable);

    /**
     * 根据子系统,报警级别，查询某段时间的报警记录
     * @param startDate
     * @param endDate
     * @param groupName
     * @param almpriority
     * @return
     */
    public List<UcAlarmlog> searchUcAlarmlogList(String groupName, int almpriority, Date startDate, Date endDate);
}
