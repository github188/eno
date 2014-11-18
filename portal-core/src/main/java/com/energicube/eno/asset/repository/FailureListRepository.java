package com.energicube.eno.asset.repository;

import com.energicube.eno.asset.model.FailureList;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FailureListRepository extends JpaRepository<FailureList, Long> {

    /**
     * 获取指定故障代码的列表
     *
     * @param failurecode 故障代码
     * @return 故障代码列表对象
     */
    public FailureList findByFailurecode(String failurecode) throws DataAccessException;

    /**
     * 获取故障代码列表
     *
     * @param parent 故障列表ID
     * @param type   故障类型
     * @return 故障代码列表
     */
    public List<FailureList> findByParentAndType(long parent, String type) throws DataAccessException;


    /**
     * 获取故障代码列表
     *
     * @param parent 故障列表ID
     * @param type   故障类型
     * @return 故障代码列表
     */
    public Page<FailureList> findByParentAndType(Pageable pageable, long parent, String type) throws DataAccessException;

    /**
     * 获取故障列表
     *
     * @param pageable 分页信息
     * @param 故障分类ID
     * @param 故障类型
     * @return 故障代码列表
     */
    @Query("select list,code from FailureList list,FailureCode code where list.failurecode=code.failurecode and list.parent=? and type=?")
    public List<Object[]> findFailureListSetByParentAndTypes(Pageable pageable, long parent, String type) throws DataAccessException;

    /**
     * 获取故障列表总条数
     *
     * @param 故障分类ID
     * @param 故障类型
     * @return 故障代码列表
     */
    @Query("select count(list) from FailureList list,FailureCode code where list.failurecode=code.failurecode and list.parent=? and type=?")
    public long findFailureListSetByParentAndTypesCount(long parent, String type) throws DataAccessException;


}
