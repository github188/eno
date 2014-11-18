package com.energicube.eno.asset.service.impl;

import com.energicube.eno.asset.model.JobPlan;
import com.energicube.eno.asset.model.JobTask;
import com.energicube.eno.asset.repository.JobPlanRepository;
import com.energicube.eno.asset.repository.JobTaskRepository;
import com.energicube.eno.asset.repository.jpa.JpaJobPlanRepository;
import com.energicube.eno.asset.service.JobPlanService;
import com.energicube.eno.common.jsonquery.jpa.request.DataTablesRequestParams;
import com.energicube.eno.common.jsonquery.jpa.response.DataTablesResponse;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Map;

@Service
public class JobPlanServiceImpl implements JobPlanService {

    private JobPlanRepository jobPlanRepository;
    private JobTaskRepository jobTaskRepository;
    private JpaJobPlanRepository jpaJobPlanRepository;

    @Autowired
    public JobPlanServiceImpl(JobPlanRepository jobPlanRepository,
                              JobTaskRepository jobTaskRepository,
                              JpaJobPlanRepository jpaJobPlanRepository) {
        this.jobPlanRepository = jobPlanRepository;
        this.jobTaskRepository = jobTaskRepository;
        this.jpaJobPlanRepository = jpaJobPlanRepository;
    }


    @Transactional(readOnly = true)
    public boolean existJpnum(String jpnum, String orgid, String siteid) {
        List<JobPlan> list = jobPlanRepository.findByJpnumAndOrgidAndSiteid(jpnum, orgid, siteid);
        if (list != null && list.size() > 0)
            return true;
        return false;
    }


    @Transactional
    public JobPlan saveJobPlan(JobPlan jobPlan) {
        if (jobPlan.getJobplanid() == 0 && "".equals(jobPlan.getStatus())) {
            jobPlan.setStatus("DRAFT");
            jobPlan.setJpnum(jobPlan.getJpnum().toUpperCase());
        }
        return jobPlanRepository.save(jobPlan);
    }

    @Transactional(readOnly = true)
    public DataTablesResponse<JobPlan> findAllJobPlans(String orgid,
                                                       String siteid, Map<String, String> condition,
                                                       DataTablesRequestParams params) {

        DataTablesResponse<JobPlan> result = new DataTablesResponse<JobPlan>();
        // 排序方式和排序列
        Direction sortDir = params.getsSortDir_0().equals("asc") ? Direction.ASC
                : Direction.DESC;
        String sortColName = params.getsColumns().split(",")[params
                .getiSortCol_0()];
        Sort sort = new Sort(sortDir, sortColName);
        // 当前页
        int pageNumber = (int) Math.ceil(params.getiDisplayStart()
                / params.getiDisplayLength());

        Pageable pageable = new PageRequest(pageNumber,
                params.getiDisplayLength(), sort);

        Page<JobPlan> page = null;

        page = jobPlanRepository.findAll(new Specification<JobPlan>() {
            public Predicate toPredicate(Root<JobPlan> root,
                                         CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<String> orgidPath = root.get("orgid");
                Path<String> siteidPath = root.get("siteid");
                //连接查询条件, 不定参数，可以连接0..N个查询条件
                query.where(cb.equal(orgidPath, ""), cb.equal(siteidPath, ""));
                return null;
            }
        }, pageable);

        List<JobPlan> data = page.getContent();
        result.setsEcho(params.getsEcho());
        result.setiTotalDisplayRecords(page.getTotalElements());
        result.setiTotalRecords(page.getTotalElements());
        result.setAaData(data);
        return result;
    }


    @Transactional(readOnly = true)
    public DataSet<JobPlan> findJobPlansWithDatatablesCriterias(
            DatatablesCriterias criterias) {

        List<JobPlan> jobPlans = jpaJobPlanRepository.findJobPlanWithDatatablesCriterias(criterias);
        Long countFiltered = jpaJobPlanRepository.getFilteredCount(criterias);
        Long count = jpaJobPlanRepository.getTotalCount();

        return new DataSet<JobPlan>(jobPlans, count, countFiltered);
    }


    @Transactional(readOnly = true)
    public JobPlan findOne(long jobplanid) {
        return jobPlanRepository.findOne(jobplanid);
    }

    @Transactional
    public void deleteJobPlan(long jobplanid) {
        JobPlan jobPlan = jobPlanRepository.findOne(jobplanid);
        if (jobPlan != null && jobPlan.getStatus().equals("DRAFT")) {
            jobPlanRepository.delete(jobPlan);
        } else {
            jobPlan.setStatus("INACTIVE");
            jobPlanRepository.save(jobPlan);
        }
    }


    @Transactional(readOnly = true)
    public boolean existJobTask(String jpnum, Integer jptask, String orgid,
                                String siteid) {
        List<JobTask> list = jobTaskRepository.findByJpnumAndJptaskAndOrgidAndSiteid(jpnum, jptask, orgid, siteid);
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }


    @Transactional
    public JobTask saveJobTask(JobTask jobTask) {
        return jobTaskRepository.save(jobTask);
    }


    @Transactional(readOnly = true)
    public JobTask findJobTaskById(long jobtaskid) {
        return jobTaskRepository.findOne(jobtaskid);
    }

    @Transactional
    public void deleteJobTaskById(long jobtaskid) throws Exception {

        jobTaskRepository.delete(jobtaskid);
    }


    @Transactional(readOnly = true)
    public DataTablesResponse<JobTask> findAllJobTasks(String jpnum,
                                                       String orgid, String siteid, DataTablesRequestParams params) {
        DataTablesResponse<JobTask> result = new DataTablesResponse<JobTask>();
        // 排序方式和排序列
        Direction sortDir = params.getsSortDir_0().equals("asc") ? Direction.ASC
                : Direction.DESC;
        String sortColName = params.getsColumns().split(",")[params
                .getiSortCol_0()];
        Sort sort = new Sort(sortDir, sortColName);
        // 当前页
        int pageNumber = (int) Math.ceil(params.getiDisplayStart()
                / params.getiDisplayLength());

        Pageable pageable = new PageRequest(pageNumber,
                params.getiDisplayLength(), sort);

        Page<JobTask> page = null;
        page = jobTaskRepository.findByJpnumAndOrgidAndSiteid(jpnum, orgid, siteid, pageable);

        List<JobTask> data = page.getContent();
        result.setsEcho(params.getsEcho());
        result.setiTotalDisplayRecords(page.getTotalElements());
        result.setiTotalRecords(page.getTotalElements());
        result.setAaData(data);

        return result;
    }


}
