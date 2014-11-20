package com.energicube.eno.asset.service;

import com.energicube.eno.asset.model.Companies;
import com.energicube.eno.common.jsonquery.jpa.request.DataTablesRequestParams;
import com.energicube.eno.common.jsonquery.jpa.response.DataTablesResponse;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;

/**
 * 资产供应商与生产商 业务逻辑接口
 */
public interface CompaniesService {

    /**
     * 判断公司标识是否已经存在
     *
     * @param company 公司标识
     * @return 存在为true，否则为false
     */
    public boolean existCompany(String company);

    /**
     * 判断在分支机构中是否已经存在
     *
     * @param parentCompany 父级公司标识
     * @param company       公司标识
     * @return 存在为true，否则为false
     */
    public boolean existBranch(String parentCompany, String company);

    /**
     * 保存公司信息
     *
     * @param companies 公司信息
     * @return {@link Companies} object
     */
    public Companies saveCompanies(Companies companies);

    /**
     * 获取指定类型的公司信息
     *
     * @param type   公司类型 C（承运人）、I（内部）、M（制造商）和 V（供应商）
     * @param params {@link DataTablesRequestParams} DataTabls请求参数
     * @return {@link DataTablesResponse} object
     */
    public DataTablesResponse<Companies> findCompanies(String type, DataTablesRequestParams params);


    /**
     * 获取指定公司的分支信息列表
     *
     * @param company 公司标识
     * @param params  {@link DataTablesRequestParams} DataTabls请求参数
     * @return 公司分支信息 {@link DataTablesResponse} object
     */
    public DataTablesResponse<Companies> findBranchs(String company, DataTablesRequestParams params);

    /**
     * 获取公司信息
     *
     * @param companiesid 公司ID
     * @return {@link Companies} object
     */
    public Companies findOne(long companiesid);

    /**
     * 删除公司信息
     *
     * @param companiesid 公司ID
     */
    public void deleteCompanies(long companiesid) throws Exception;

    /**
     * 删除公司分支信息
     *
     * @param companiesid 分支组构ID
     */
    public void deleteBranch(long companiesid) throws Exception;


    /**
     * <p/>
     * Query used to populate the DataTables that display the list of Companies.
     *
     * @param criterias The DataTables criterias used to filter the Companies.
     *                  (maxResult, filtering, paging, ...)
     * @return a bean that wraps all the needed information by DataTables to
     * redraw the table with the data.
     */
    public DataSet<Companies> findCompaniesWithDatatablesCriterias(DatatablesCriterias criterias);

}
