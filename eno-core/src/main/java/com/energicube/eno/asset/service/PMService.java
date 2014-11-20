package com.energicube.eno.asset.service;

import com.energicube.eno.asset.model.*;
import com.energicube.eno.common.jsonquery.jpa.request.DataTablesRequestParams;
import com.energicube.eno.common.jsonquery.jpa.response.DataTablesResponse;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;


/**
 * 预防性维护计划业务逻辑操作接口
 *
 * @author CHENPING
 * @version 1.0
 */
public interface PMService {

    /**
     * 初始化PM
     */
    public PM initPM();

    /**
     * 检查新增PM在同一站点中是否已经存在
     *
     * @param pmnum  PM编号
     * @param siteid 站点编号
     */
    public boolean existPM(String pmnum, String siteid) throws Exception;

    /**
     * 保存预防性维护计划
     *
     * @param pm {@link PM} object
     * @return {@link PM} object
     */
    public PM savePM(PM pm) throws Exception;

    public PM savePMByFrequency(PM pm) throws Exception;

    public PM savePMBySeasons(PM pm) throws Exception;

    public PM savePMBySequence(PM pm) throws Exception;

    public PM savePMByAncestor(PM pm) throws Exception;


    /**
     * 获取预防性维护计划
     *
     * @param pmid 预防性维护计划ID
     * @return {@link PM} object
     */
    public PM findPMById(long pmid) throws Exception;

    /**
     * 获取预防性维护计划
     *
     * @param pmnum  预防性维护计划编号
     * @param siteid 地点
     * @return {@link PM} object
     */
    public PM findPMById(String pmnum, String siteid) throws Exception;

    /**
     * 删除预防性维护计划
     *
     * @param pmid 预防性维护计划ID
     */
    public void deletePM(long pmid) throws Exception;

    /**
     * 获取预防性维护计划列表
     *
     * @param siteid 地点
     * @param params 请求参数
     * @return {@link DataTablesResponse} data table object
     */
    public DataTablesResponse<PM> findPMList(String siteid, DataTablesRequestParams params) throws Exception;


    /**
     * <p/>
     * Query used to populate the DataTables that display the list of PM.
     *
     * @param criterias The DataTables criterias used to filter the PM.
     *                  (maxResult, filtering, paging, ...)
     * @return a bean that wraps all the needed information by DataTables to
     * redraw the table with the data.
     */
    public DataSet<PM> findPMsWithDatatablesCriterias(DatatablesCriterias criterias);


    /**
     * 获取指定编号的子预防性维护计划列表
     *
     * @param parent 父PM编号
     * @param siteid 地点
     * @param params 请求参数
     * @return {@link DataTablesResponse} data table object
     */
    public DataTablesResponse<PM> findPMChildren(String parent, String siteid, DataTablesRequestParams params) throws Exception;

    /**
     * 获取指定资产的预防性维护计划列表
     *
     * @param assetnum 资产编号
     * @param siteid   地点
     * @param params   请求参数
     * @return {@link DataTablesResponse} data table object
     */
    public DataTablesResponse<PM> findPMByAsset(String assetnum, String siteid, DataTablesRequestParams params) throws Exception;

    /**
     * 获取指定位置的预防性维护计划列表
     *
     * @param location 位置编号
     * @param siteid   地点
     * @param params   请求参数
     * @return {@link DataTablesResponse} data table object
     */
    public DataTablesResponse<PM> findPMByLocation(String location, String siteid, DataTablesRequestParams params) throws Exception;


    /**
     * 初始化PM计量器
     *
     * @param pm {@link PM} object
     * @return {@link PMMeter} object
     */
    public PMMeterSet initPMMeter(PM pm) throws Exception;

    /**
     * 检查PM计量器是否已经存在
     *
     * @param metername 计量器名称
     * @param pm        {@link PM} object
     * @return 如果存在为true, 否则为false
     */
    public boolean existPMMeter(String metername, String pmnum, String siteid) throws Exception;


    /**
     * 保存PM计量器
     *
     * @param pmMeter {@link PMMeter} PM计量器对象
     * @return {@link PMMeter} object
     */
    public PMMeter savePMMeter(PMMeter pmMeter) throws Exception;

    /**
     * 获取PM计量器
     *
     * @param pmmeterid PM计量器ID
     * @return {@link PMMeter} object
     */
    public PMMeter findPMMeterById(long pmmeterid) throws Exception;

    /**
     * 删除PM计量器
     *
     * @param pmmeterid PM计量器ID
     */
    public void deletePMMeter(long pmmeterid) throws Exception;

    /**
     * 获取指定PM的计量器列表
     *
     * @param pmnum  PM编号
     * @param siteid 地点
     * @param orgid  组织机构
     * @param params {@link DataTablesRequestParams} 请求参数
     * @return {@link DataTablesResponse} data tables list
     */
    public DataTablesResponse<PMMeterSet> findPMMeters(String pmnum, String siteid, DataTablesRequestParams params);


    /**
     * 初始化季节性日期内PM对象
     *
     * @param pm {@link PM} object
     * @return {@link PMSeasons} object
     */
    public PMSeasons initPMSeasons(PM pm) throws Exception;

    /**
     * 检查PM季节性日期是否重复设置
     *
     * @param pmSeasons {@link PM} object
     * @return 如果存在为true, 否则为false
     */
    public boolean existPMSeasons(PMSeasons pmSeasons) throws Exception;


    /**
     * 保存季节性日期内的PM对象
     *
     * @param pmSeasons {@link PMSeasons} object
     * @return {@link PMSeasons} object
     */
    public PMSeasons savePMSeasons(PMSeasons pmSeasons) throws Exception;

    /**
     * 获取PM指定的季节性日期设置
     *
     * @param pmseasonsid 季节性日期ID
     *                    {@link PMSeasons} object
     */
    public PMSeasons findPMSeasonsById(long pmseasonsid) throws Exception;

    /**
     * 删除PM内季节性日期的设置
     *
     * @param pmseasonsid 季节性日期ID
     */
    public void deletePMSeasons(long pmseasonsid) throws Exception;

    /**
     * 获取指定PM的季节性日期设置
     *
     * @param pmnum  PM编号
     * @param siteid 地点
     * @param orgid  组织机构
     * @param params {@link DataTablesRequestParams} 请求参数
     * @return {@link DataTablesResponse} data tables list
     */
    public DataTablesResponse<PMSeasons> findPMSeasonsList(String pmnum, String siteid, DataTablesRequestParams params);


    /**
     * 初始化作业计划序列表
     *
     * @param pm {@link PM} object
     * @return {@link PMSequence} object
     */
    public PMSequenceSet initPMSequenceSet(PM pm) throws Exception;

    /**
     * 检查PM的作业计划序列是否已经存在
     *
     * @param jpnum 作业计划编号
     * @param pmnum PM编号
     * @return 如果存在为true, 否则为false
     */
    public boolean existPMSequence(String jpnum, String pmnum, String siteid) throws Exception;

    /**
     * 保存PM作业计划序列
     *
     * @param pmSequence {@link PMSequence} object
     * @return {@link PMSequence} object
     */
    public PMSequence savePMSequence(PMSequence pmSequence) throws Exception;

    /**
     * 获取PM内指定的作业计划
     *
     * @param pmsequenceid 计划序列ID
     * @return {@link PMSequence} object
     */
    public PMSequence findPMSequenceById(long pmsequenceid) throws Exception;

    /**
     * 删除PM作业计划序列
     *
     * @param pmsequenceid 计划序列ID
     */
    public void deletePMSequence(long pmsequenceid) throws Exception;

    /**
     * 获取指定PM的作业计划序列
     *
     * @param pmnum  PM编号
     * @param siteid 地点
     * @param orgid  组织机构
     * @param params {@link DataTablesRequestParams} 请求参数
     * @return {@link DataTablesResponse} data tables list
     */
    public DataTablesResponse<PMSequenceSet> findPMSequenceList(String pmnum, String siteid, String orgid, DataTablesRequestParams params);


    /**
     * 初始化PM层次结构
     *
     * @param pm {@link PM} object
     * @return {@link PMAncestor} object
     */
    public PMAncestor initPMAncestor(PM pm) throws Exception;

    /**
     * 初始化PMAncestorSet对象
     *
     * @param pm {@link PM} object
     * @return {@link PMAncestorSet} object
     */
    public PMAncestorSet initPMAncestorSet(PM pm);

    /**
     * 检查PM层次结构的父级结构<br />
     * 自身不能设置自身为父级,不能选择子PM为自身的父级
     *
     * @param parent 父PM
     * @return 如果存在为true, 否则为false
     */
    public boolean existParentAncestor(String parent, PM pm) throws Exception;

    /**
     * 检查PM层次结构的子PM是否重复
     *
     * @param pm 当前PM
     * @return 如果存在为true, 否则为false
     */
    public boolean existPMAncestor(PMAncestor pmAncestor) throws Exception;

    /**
     * 保存PM层次结构
     *
     * @param pmAncestor {@link PMAncestor} object
     * @return {@link PMAncestor} object
     */
    public PMAncestor savePMAncestor(long pmid, PMAncestor pmAncestor) throws Exception;

    /**
     * 获取PM指定的子PM
     *
     * @param pmancestorid 层次结构ID
     * @return {@link PMAncestor} object
     */
    public PMAncestor findPMAncestorById(long pmancestorid) throws Exception;

    /**
     * 删除PM层次结构
     *
     * @param pmancestorid 层次结构ID
     */
    public void deletePMAncestor(long pmancestorid) throws Exception;

    /**
     * 获取指定PM的季节性日期设置
     *
     * @param pmnum  PM编号
     * @param siteid 地点
     * @param params {@link DataTablesRequestParams} 请求参数
     * @return {@link DataTablesResponse} data tables list
     */
    public DataTablesResponse<PMAncestorSet> findPMAncestorList(String pmnum, String siteid, DataTablesRequestParams params);


}
