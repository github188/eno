package com.energicube.eno.asset.repository;

import com.energicube.eno.asset.model.Companies;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 公司数据操作接口
 */
public interface CompaniesRepository extends JpaRepository<Companies, Long> {

    /**
     * 查找指定公司的所有分支机构
     *
     * @param parentcompany 指定的公司标识
     * @param pageable      页{@link Pageable}分对象
     * @return {@link Companies} paging list
     */
    Page<Companies> findByParentcompany(String parentcompany, Pageable pageable) throws DataAccessException;

    /**
     * 查找指定类型的公司定义
     *
     * @param type     公司类型  C（承运人）、I（内部）、M（制造商）和 V（供应商）
     * @param pageable 页{@link Pageable}分对象
     * @return {@link Companies} paging list
     */
    Page<Companies> findByType(String type, Pageable pageable) throws DataAccessException;


    /**
     * 查找公司信息
     *
     * @param company 公司标识
     * @return 公司信息列表
     */
    List<Companies> findByCompany(String company) throws DataAccessException;

    /**
     * 查找公司信息
     *
     * @param parentcompany 上级公司标识
     * @param company       公司标识
     * @return 公司信息列表
     */
    List<Companies> findByParentcompanyAndCompany(String parentcompany, String company) throws DataAccessException;
}
