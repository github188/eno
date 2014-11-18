package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.OpLogs;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OpLogRepository extends JpaRepository<OpLogs, Long> {

    /**
     * 获取指定时间范围和等级的日志数据
     *
     * @param startDate 开始日期
     * @param endDate   结束日志
     * @param category  日志类型
     * @param pageable  分页参数
     * @return 日志分页列表
     */
    @Query("select log from OkcLogs log where dated>=?1 and dated<=?2 and category=?3")
    Page<OpLogs> findByDateRange(String startDate, String endDate, String category, Pageable pageable) throws DataAccessException;


    /**
     * 获取指定类别的操作日志
     *
     * @param category 日志类别
     * @return 日志分页列表
     */
    Page<OpLogs> findByCategoryOrderByDatedDesc(String category, Pageable pageable) throws DataAccessException;

    /**
     * 查询日志列表，不分页
     *
     * @param category 类型
     * @return 日志列表
     * @throws DataAccessException
     */
    List<OpLogs> findByCategoryOrderByDatedDesc(String category) throws DataAccessException;
}
