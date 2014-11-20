package com.energicube.eno.asset.repository;

import com.energicube.eno.asset.model.ClassSpec;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 分类结构对应的资产属性模板数据操作
 *
 * @author CHENPING
 */
public interface ClassSpecRepository extends JpaRepository<ClassSpec, Long> {

    /**
     * 查找指定类别结构ID的规范数据
     *
     * @param classstructureid 类别结构ID
     * @param pageable         分页请求对象
     * @return {@link ClassSpec} {@link Page} object
     */
    public Page<ClassSpec> findByClassstructureid(String classstructureid, Pageable pageable) throws DataAccessException;

    /**
     * 查找指定类别结构ID和资产属性ID对应的资产类别规范数据
     *
     * @param classstructureid 类别结构ID
     * @param assetattrid      资产属性ID
     * @param {@link           ClassSpec} list object
     */
    public List<ClassSpec> findByClassstructureidAndAssetattrid(String classstructureid, String assetattrid) throws DataAccessException;

    /**
     * 根据分类id查询属性
     *
     * @param classstructureid
     * @return
     * @throws DataAccessException
     */
    public List<ClassSpec> findByClassstructureid(String classstructureid) throws DataAccessException;

}
