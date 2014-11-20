package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.SubPatrol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

/**
 * @author 刘广路
 * @since 2014-08-11
 */
public interface SubPatrolRepository extends JpaRepository<SubPatrol, String> {

    /**
     * 根据所有巡更记录
     *
     * @param pageable 分页
     * @return
     */
    public Page<SubPatrol> findByOrderByCheckTimeDesc(Pageable pageable);

    /**
     * 查询某时间段的巡更记录
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @param pageable  分页
     * @return
     */
    public Page<SubPatrol> findByCheckTimeGreaterThanAndCheckTimeLessThanOrderByCheckTimeDesc(Date startDate, Date endDate, Pageable pageable);
}
