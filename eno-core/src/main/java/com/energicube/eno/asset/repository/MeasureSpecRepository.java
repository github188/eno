package com.energicube.eno.asset.repository;

import com.energicube.eno.asset.model.MeasureSpec;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 测点使用的技术规范参数数据操作类
 */
public interface MeasureSpecRepository extends JpaRepository<MeasureSpec, Long> {

    /**
     * 获取指定资产编号和记量器编号对应的测量技术规范
     *
     * @param assetnum  资产编号
     * @param metername 记量器编号
     * @param siteid    地点
     * @return {@link MeasureSpec} list
     */
    public Page<MeasureSpec> findByAssetnumAndMeternameAndSiteid(String assetnum, String metername, String siteid, Pageable pageable) throws DataAccessException;

    /**
     * 获取计量器测点中相同属性的技术规范
     *
     * @param assetnum    资产编号
     * @param metername   记量器编号
     * @param siteid      地点
     * @param assetattrid 属性ID
     * @return {@link MeasureSpec} object list
     */
    public List<MeasureSpec> findByAssetnumAndMeternameAndSiteidAndAssetattrid(String assetnum, String metername, String siteid, String assetattrid) throws DataAccessException;


    /**
     * 获取计量器的测量技术规范
     *
     * @param metername 计量器名称
     * @param siteid    地点
     */
    public Page<MeasureSpec> findByMeternameAndSiteid(String metername, String siteid, Pageable pageable) throws DataAccessException;

    /**
     * 获取测量技术规范相关信息
     *
     * @param measurespecid 测量规范ID
     */
    @Query("select spec,attr from MeasureSpec spec,AssetAttribute attr where spec.assetattrid=attr.assetattrid and spec.measurespecid=?1")
    public Object[] findByMeasurespecid(long measurespecid) throws DataAccessException;

}
