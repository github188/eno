package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.UcEventLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/**
 * All rights Reserved, Designed By ZCLF
 * Copyright:    Copyright(C) 2013-2014
 * Company   ZCLF Energy Technologies Inc.
 *
 * @author 刘广路
 * @version 1.0
 * @date 2014/10/26 18:59
 * @Description CS设备运行日志事件
 */
public interface UcEventLogRepository extends JpaRepository<UcEventLog,Long> {

    /**
     * 查询最新的事件日志
     * @param id 大于此id的日志，此id是递增型
     * @return 事件日志
     */
    @Query("select a from UcEventLog a where a.id>?1 ")
    public List<UcEventLog> findByGreaterThanId(long id);
}
