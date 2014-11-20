package com.energicube.eno.asset.repository;

import com.energicube.eno.asset.model.ClassAncestor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 分类结构祖代和层级数据操作
 */
public interface ClassAncestorRepository extends
        JpaRepository<ClassAncestor, Long> {

    /**
     * 获取类别祖代结构
     *
     * @param classstructureid 类别结构ID
     * @param classificationid 类别定义ID
     * @param ancestor         当前分类层级的一个祖代
     * @param ancestorclassid  祖代的类别
     * @return 类别祖代结构对象
     */
    public ClassAncestor findByClassstructureidAndClassificationidAndAncestorAndAncestorclassid(
            String classstructureid, String classificationid, String ancestor,
            String ancestorclassid) throws DataAccessException;

}
