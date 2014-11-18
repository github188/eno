package com.energicube.eno.asset.repository;

import com.energicube.eno.asset.model.PM;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 预防性维护计划数据操作接口
 *
 * @author CHENPING 2013-09-03
 * @version 1.0
 */
public interface PMRepository extends JpaRepository<PM, Long> {

    /**
     * 获取同一点中相同PM相同编号的PM列表
     *
     * @param pmnum  PM编号
     * @param siteid 站点
     * @return PM列表
     */
    List<PM> findByPmnumAndSiteid(String pmnum, String siteid) throws DataAccessException;

    /**
     * 查找指定地点的所有PM
     *
     * @param siteid   地点
     * @param pagealbe 分页参数
     * @param PM分页列表
     */
    Page<PM> findBySiteid(String siteid, Pageable pageable) throws DataAccessException;

    /**
     * 查找指定地点的所有具有子PM的列表
     *
     * @param parent   父级PM
     * @param siteid   地点
     * @param pagealbe 分页参数
     * @param PM分页列表
     */
    Page<PM> findByParentAndSiteid(String parent, String siteid, Pageable pageable) throws DataAccessException;

    /**
     * 查找指定资产的预防性维护计划
     *
     * @param assetnum 资产编号
     * @param siteid   地点
     * @param pagealbe 分页参数
     * @return PM分页列表
     */
    Page<PM> findByAssetnumAndSiteid(String assetnum, String siteid, Pageable pageable);


    /**
     * 查找指定位置的预防性维护计划
     *
     * @param location 位置
     * @param siteid   地点
     * @param pagealbe 分页参数
     * @return PM分页列表
     */
    Page<PM> findByLocationAndSiteid(String location, String siteid, Pageable pageable);


}
