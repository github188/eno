package com.energicube.eno.monitor.service;


import com.energicube.eno.common.jsonquery.jpa.request.DataTablesRequestParams;
import com.energicube.eno.common.jsonquery.jpa.response.DataTablesResponse;
import com.energicube.eno.monitor.model.OkcLogs;


/**
 * 系统日志操作
 *
 * @author CHENPING
 */
public interface OkcLogsService {

    /**
     * 获取指定时间范围和等级的日志数据
     *
     * @param startDate 开始日期
     * @param endDate   结束日志
     * @param level     日志等级
     * @param pageable  分页参数
     * @return 日志分页列表
     */
    public DataTablesResponse<OkcLogs> findByDateRange(String startDate, String endDate, String level, DataTablesRequestParams params) throws Exception;

    /**
     * 获取指定ID的日志信息
     *
     * @param logid 日志ID
     * @return 日志信息
     */
    public OkcLogs findByLogid(Long logid) throws Exception;

    /**
     * 保存日志
     *
     * @param okcLogs
     * @throws Exception
     */
    public void save(OkcLogs okcLogs) throws Exception;
}
