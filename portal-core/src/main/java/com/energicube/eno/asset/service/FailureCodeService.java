package com.energicube.eno.asset.service;

import com.energicube.eno.asset.model.FailureCode;
import com.energicube.eno.asset.model.FailureCodeType;
import com.energicube.eno.asset.model.FailureList;
import com.energicube.eno.asset.model.FailureListSet;
import com.energicube.eno.common.jsonquery.jpa.request.DataTablesRequestParams;
import com.energicube.eno.common.jsonquery.jpa.response.DataTablesResponse;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FailureCodeService {

    /**
     * 获取所有故障代码
     *
     * @param pageable 分页对象
     * @return 故障代码列表
     */
    public Page<FailureCode> findAllFailureCode(Pageable pageable);


    /**
     * <p/>
     * Query used to populate the DataTables that display the list of FailureCodes.
     *
     * @param criterias The DataTables criterias used to filter the FailureCodeS.
     *                  (maxResult, filtering, paging, ...)
     * @return a bean that wraps all the needed information by DataTables to
     * redraw the table with the data.
     */
    public DataSet<FailureCode> findFailureCodesWithDatatablesCriterias(DatatablesCriterias criterias);


    /**
     * 判断是否已经存在相同的故障代码
     *
     * @param failurecode 故障代码
     * @return true or false
     */
    public boolean existFailureCode(String failurecode);

    /**
     * 保存故障列表
     *
     * @param failureCode 故障代码对象
     * @return 故障列表对象
     */
    public FailureList saveFailureList(FailureCode failureCode);

    /**
     * 获取故障代码信息
     *
     * @param failurelist 故障列表ID
     * @return 故障代码信息
     */
    public FailureCode findFailureCodeByFailurelistId(Long failurelistId);

    /**
     * 获取故障代码信息
     *
     * @param failurelist 故障代码
     * @return 故障代码信息
     */
    public FailureCode findFailureCodeBycode(String failurecode);

    /**
     * 查找指定ID的故障代码
     *
     * @param 故障代码ID
     */
    public FailureCode findFailureCode(long failurecodeid);


    /**
     * 获取指定的故障代码列表
     *
     * @param failurelist 故障列表ID
     * @return 故障代码列表对象
     */
    public FailureList findFailureList(Long failurelistId);


    /**
     * 获取指定的故障代码列表
     *
     * @param failurelist 故障代码
     * @return 故障代码列表对象
     */
    public FailureList findFailureListByFailurecode(String failurecode);


    /**
     * 获取故障问题列表
     *
     * @param failurelist 故障列表ID
     * @return 故障问题列表
     */
    public DataTablesResponse<FailureListSet> findProblemList(long failurelist, DataTablesRequestParams params);


    /**
     * 获取故障原因列表
     *
     * @param failurelist 故障列表ID
     * @return 故障原因列表
     */
    public DataTablesResponse<FailureListSet> findCauseList(long failurelist, DataTablesRequestParams params);

    /**
     * 获取故障解决方法列表
     *
     * @param failurelist 故障列表ID
     * @return 故障解决方法列表
     */
    public DataTablesResponse<FailureListSet> findRemedyList(long failurelist, DataTablesRequestParams params);


    /**
     *
     * */
    public FailureCode saveFailureCode(FailureCode failureCode, FailureCodeType type);

    public void saveFailureCodes(List<FailureCode> failureCodes, FailureCodeType type);


}
