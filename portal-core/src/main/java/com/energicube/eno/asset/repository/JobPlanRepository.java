package com.energicube.eno.asset.repository;

import com.energicube.eno.asset.model.JobPlan;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 作业计划数据操作接口
 *
 * @author CHENPING
 */
public interface JobPlanRepository extends JpaRepository<JobPlan, Long>, JpaSpecificationExecutor<JobPlan> {

    /**
     * 查找组织和地点对应的作业计划
     *
     * @param orgid    组织机构
     * @param siteid   站点ID
     * @param pageable {@link Pageable} 分页对象
     * @return {@link JobPlan} paging list
     */
    Page<JobPlan> findByOrgidAndSiteid(String orgid, String siteid, Pageable pageable) throws DataAccessException;

    /**
     * 查找作业计划编号的相关记录
     *
     * @param jpnum  计划编号
     * @param orgid  组织机构
     * @param siteid 站点ID
     * @return {@link JobPlan} list
     */
    public List<JobPlan> findByJpnumAndOrgidAndSiteid(String jpnum, String orgid, String siteid) throws DataAccessException;


}
