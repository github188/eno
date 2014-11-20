package com.energicube.eno.asset.repository;

import com.energicube.eno.asset.model.JobTask;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 作业计划作务数据操作接口
 *
 * @author CHENPING
 */
public interface JobTaskRepository extends JpaRepository<JobTask, Long> {

    /**
     * 查指作业计划任务
     *
     * @param jpnum  作业计划编号
     * @param orgid  组织机构色
     * @param siteid 地点
     * @return 作业计划任务
     */
    Page<JobTask> findByJpnumAndOrgidAndSiteid(String jpnum, String orgid, String siteid, Pageable pageable) throws DataAccessException;

    /**
     * 查找具有相同作业计划任务ID的数据
     *
     * @param jpnum  作业计划编号
     * @param orgid  组织机构色
     * @param siteid 地点
     * @return 作业计划任务列表
     */
    List<JobTask> findByJpnumAndJptaskAndOrgidAndSiteid(String jpnum, Integer jptask, String orgid, String siteid) throws DataAccessException;

}
