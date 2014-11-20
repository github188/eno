package com.energicube.eno.asset.repository;

import com.energicube.eno.asset.model.AssetSpec;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AssetSpecRepository extends JpaRepository<AssetSpec, Integer> {


    /**
     * 查找资产使用的技术规范参数列表
     *
     * @param assetnum 资产编码
     * @param siteid   地点ID
     * @return 资产使用的计量器
     */
    @Query("select s,attr,cp from AssetSpec s, AssetAttribute attr,ClassSpec cp " +
            "where s.assetattrid=attr.assetattrid and s.classstructureid=cp.classstructureid " +
            " and s.assetattrid=cp.assetattrid and cp.assetattributeid=attr.assetattributeid and s.assetnum=? and (s.siteid=? or s.siteid is null)")
    public Page<Object[]> findByAssetnumAndSiteid(String assetnum, String siteid, Pageable pageable) throws DataAccessException;

    /**
     * 查找资产使用的技术规范参数
     *
     * @param assetnum    资产编码
     * @param assetattrid 资产属性ID
     * @param siteid      地点ID
     * @return 资产使用的技术规范参数
     */
    public List<AssetSpec> findByAssetnumAndAssetattridAndSiteid(String assetnum, String assetattrid, String siteid) throws DataAccessException;


    /**
     *  查找资产使用的技术规范参数列表总条数
     * @param assetnum 资产编码
     * @param siteid 地点ID
     * @return 术规范参数列表总条数
     * */
    //@Query("select count(s) from AssetSpec s, AssetAttribute attr where s.assetattrid=attr.assetattrid and s.assetnum=? and (s.siteid=? or s.siteid is null)")
    //public long countAssetSpecSetByAssetnumAndSiteid(String assetnum,String siteid) throws DataAccessException;


}
