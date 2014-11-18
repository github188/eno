package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.OkcLogs;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * 系统日志数据操作接口
 *
 * @author CHENPING
 */
public interface OkcLogsRepository extends JpaRepository<OkcLogs, Long> {

    /**
     * 获取指定时间范围和等级的日志数据
     *
     * @param startDate 开始日期
     * @param endDate   结束日志
     * @param level     日志等级
     * @param pageable  分页参数
     * @return 日志分页列表
     */
    @Query("select log from OkcLogs log where dated>=?1 and dated<=?2 and level=?3")
    Page<OkcLogs> findByDateRange(String startDate, String endDate, String level, Pageable pageable) throws DataAccessException;

}
