package com.energicube.eno.asset.repository;

import com.energicube.eno.asset.model.AssetAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 公用技术规范参数定义数据操作接口
 *
 * @author CHENPING
 */
public interface AssetAttributeRepository extends
        JpaRepository<AssetAttribute, Long> {

    /**
     * 获取资产属性列表
     *
     * @param assetattrid 资产属性ID
     * @return {@link AssetAttribute} objects
     */
    public List<AssetAttribute> findByAssetattrid(String assetattrid);

    /**
     * 根据分类id查询属性信息
     *
     * @param classstructureid
     * @return
     * @author zouzhixiang
     * @date 2014-08-14
     */
    @Query("select a from ClassSpec c,AssetAttribute a where c.assetattrid = a.assetattrid  and c.classstructureid = ?1")
    public List<AssetAttribute> findByClassStructureId(String classstructureid);

}
