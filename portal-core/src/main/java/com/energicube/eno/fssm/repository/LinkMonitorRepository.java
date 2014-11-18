package com.energicube.eno.fssm.repository;

import com.energicube.eno.fssm.model.LinkMonitor;
import org.joda.time.LocalDateTime;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 链路数据操作接口
 */
@Repository
public interface LinkMonitorRepository extends
        JpaRepository<LinkMonitor, String> {

    @Query("from LinkMonitor where timestamp>=?1 or timestamp<=2 order by timestamp asc")
    public List<LinkMonitor> findLinkMonitorByDateRange(
            LocalDateTime starttime, LocalDateTime endtime) throws DataAccessException;

}
