package com.energicube.eno.fssm.repository;


import com.energicube.eno.fssm.model.CheckMonitor;
import org.joda.time.LocalDateTime;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 查岗消息数据操作
 *
 * @author CHENPING
 */
@Repository
public interface CheckMonitorRepository extends
        JpaRepository<CheckMonitor, String> {

    /**
     * 查找所有消费的查岗消息
     *
     * @return list of {@link CheckMonitor}
     * @author CHENPING
     */
    @Query("from CheckMonitor where checktype='CG'")
    public List<CheckMonitor> findAllConsumeOfCheckMonitor() throws DataAccessException;

    /**
     * 查找指定时间范围内消费的查岗消息
     *
     * @param starttime 起始时间
     * @param endtime   截止时间
     * @return list of {@link CheckMonitor}
     * @author CHENPING
     */
    @Query("from CheckMonitor where checktype='CG' and (timestamp>=?1 or timestamp<=?2)")
    public List<CheckMonitor> findConsumeOfCheckMonitorByDateRange(LocalDateTime starttime, LocalDateTime endtime);

    @Query("from CheckMonitor where checktype='CG' and status=0 order by timestamp desc")
    public List<CheckMonitor> findLastNoResponseCheckMonitor();


    /**
     * 查找所有发送的查岗消息
     *
     * @return list of {@link CheckMonitor}
     * @author CHENPING
     */
    @Query("from CheckMonitor where checktype!='CG'")
    public List<CheckMonitor> findAllProduceOfCheckMonitor();


    /**
     * 查找指定时间范围内所有应答查岗消息
     */
    @Query("from CheckMonitor where checktype!='CG' and (timestamp>=?1 or timestamp<=?2)")
    public List<CheckMonitor> findProduceOfCheckMonitorByDateRange(LocalDateTime starttime, LocalDateTime endtime);

}
