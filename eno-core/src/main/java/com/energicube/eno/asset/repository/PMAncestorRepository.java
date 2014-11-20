package com.energicube.eno.asset.repository;

import com.energicube.eno.asset.model.PMAncestor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * PM层次结构
 */
public interface PMAncestorRepository extends JpaRepository<PMAncestor, Long> {


    /**
     * 获取指定PMNum的层次结构数据
     *
     * @param pmnum    PM编号
     * @param pageable 分页参数
     * @return {@link PMAncestor} List
     */
    @Query("select pm,ancestor from PM pm,PMAncestor ancestor where pm.pmnum=ancestor.pmnum and ancestor.pmnum=?1 and ancestor.siteid=?2 and ancestor.hierarchylevels>0")
    public Page<Object[]> findByPmnumAndSiteid(String pmnum, String siteid, Pageable pageable) throws DataAccessException;


    /**
     * 获取指定PM的层次结构列表
     *
     * @param ancestor 当前PM记录的顶级PM,如果当前PM本身就是顶级PM，则此字段值就是当前PM
     * @param pmnum    PM编号
     * @param siteid   地点
     * @return {@link PMAncestor} list
     */
    public List<PMAncestor> findByAncestorAndPmnumAndHierarchylevelsAndSiteid(String ancestor, String pmnum, Integer hierarchylevels, String siteid) throws DataAccessException;


}
