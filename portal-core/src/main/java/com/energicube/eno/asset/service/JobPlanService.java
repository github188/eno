package com.energicube.eno.asset.service;

import com.energicube.eno.asset.model.JobPlan;
import com.energicube.eno.asset.model.JobTask;
import com.energicube.eno.common.jsonquery.jpa.request.DataTablesRequestParams;
import com.energicube.eno.common.jsonquery.jpa.response.DataTablesResponse;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;

import java.util.Map;

/**
 * 作业计划业务逻辑接口
 */
public interface JobPlanService {

    /**
     * 判断同一组织地点是否存在相同的作业计划编号
     *
     * @param jpnum  计划编号
     * @param orgid  组织机构
     * @param siteid 站点ID
     * @return 存在为true, 否则为false
     */
    public boolean existJpnum(String jpnum, String orgid, String siteid);


    /**
     * 保存作业计划
     *
     * @param jobPlan {@link JobPlan} 作业计划对象
     * @return {@link JobPlan} object
     */
    public JobPlan saveJobPlan(JobPlan jobPlan);


    /**
     * 查找所有相关作业计划
     *
     * @param orgid     组织机构
     * @param siteid    站点ID
     * @param condition 查询条件
     * @param params    请求参数
     * @return {@link DataTablesResponse} object
     */
    public DataTablesResponse<JobPlan> findAllJobPlans(String orgid, String siteid,
                                                       Map<String, String> condition, DataTablesRequestParams params);


    /**
     * <p/>
     * Query used to populate the DataTables that display the list of JobPlan.
     *
     * @param criterias The DataTables criterias used to filter the JobPlan.
     *                  (maxResult, filtering, paging, ...)
     * @return a bean that wraps all the needed information by DataTables to
     * redraw the table with the data.
     */
    public DataSet<JobPlan> findJobPlansWithDatatablesCriterias(DatatablesCriterias criterias);


    /**
     * 获取指定ID的作业计划
     *
     * @param jobplanid 工作计划唯一ID
     * @return {@link JobPlan} object
     */
    public JobPlan findOne(long jobplanid);

    /**
     * 删除作业计划
     *
     * @param jobplanid 工作计划唯一ID
     */
    public void deleteJobPlan(long jobplanid);


    public boolean existJobTask(String jpnum, Integer jptask, String orgid, String siteid);

    /**
     * 保存计划工作任务
     *
     * @param jobTask {@link JobTask} 计划工作任务对象
     * @return 计划工作任务对象
     */
    public JobTask saveJobTask(JobTask jobTask);

    /**
     * 获取作业计划任务对象
     *
     * @param jobtaskid 计划任务ID
     * @return 计划工作任务对象
     */
    public JobTask findJobTaskById(long jobtaskid);

    /**
     * 删除计划任务对象
     *
     * @param jobtaskid 计划任务ID
     */
    public void deleteJobTaskById(long jobtaskid) throws Exception;

    /**
     * 获取作业计划的任务列表
     *
     * @param jpnum  作业计划ID
     * @param orgid  组织机构
     * @param siteid 站点ID
     * @param params 请求参数
     * @return {@link DataTablesResponse} object
     */
    public DataTablesResponse<JobTask> findAllJobTasks(String jpnum, String orgid, String siteid, DataTablesRequestParams params);


}
