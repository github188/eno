package com.energicube.eno.asset.repository;

import com.energicube.eno.asset.model.ClassStructure;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 类别的层次结构定义数据操作
 *
 * @author CHENPING
 */
public interface ClassStructureRepository extends JpaRepository<ClassStructure, Long> {

    /**
     * 查找所有类别层次定义
     *
     * @param parent 父级ID
     * @return 类别层次定义列表
     */
    public Page<ClassStructure> findByParent(String parent, Pageable pageable) throws DataAccessException;

    /**
     * 查找指定层次结构ID的定义列表
     *
     * @param classificationid 层资 结构ID
     * @return 类别层资 结构定义列表
     */
    public List<ClassStructure> findByClassstructureid(String classstructureid) throws DataAccessException;

    /**
     * 查找指定层次结构ID的定义列表
     *
     * @param classificationid 层资 结构ID
     * @param orgid            机构ID
     * @param siteid           地点ID
     * @return 类别层资 结构定义列表
     */
    public List<ClassStructure> findByClassstructureidAndSiteid(String classstructureid, String siteid) throws DataAccessException;


    /**
     * 查找指定类别ID对应的结构层次定义列表
     *
     * @param classificationid 类别ID
     * @return 结构层次定义列表
     */
    public List<ClassStructure> findByClassificationid(String classificationid) throws DataAccessException;

    /**
     * 查找指定组织机构的类别数据
     *
     * @param orgid 组织机构ID
     * @return 类别列表
     */
    @Query("select s from ClassStructure s where coalesce(orgid,'')=?1")
    public List<ClassStructure> findByOrgid(String orgid);


    /**
     * 查找所有类别层次定义
     *
     * @param parent 父级ID
     * @return 类别层次定义列表
     */
    @Query("select s from ClassStructure s where parent=(select cs.classstructureid  from ClassStructure cs where parent=?1)")
    public List<ClassStructure> findByRootParent(String parent) throws DataAccessException;

    /**
     * 查找所有类别层次定义
     *
     * @param parent 父级ID
     * @return 类别层次定义列表
     */
    public List<ClassStructure> findByParent(String parent) throws DataAccessException;
}
