package com.energicube.eno.asset.repository;

import com.energicube.eno.asset.model.AssetMeter;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 资产使用的计量器数据操作接口
 *
 * @author chenflat
 */
public interface AssetMeterRepository extends
        JpaRepository<AssetMeter, Integer> {

    /**
     * 查找资产使用的计量器
     *
     * @param assetnum 资产编码
     * @param siteid   地点ID
     * @return 资产使用的计量器
     */
    @Query("select am,m from AssetMeter am, Meter m where am.metername=m.metername and am.assetnum=? and am.siteid=?")
    public List<Object[]> findByAssetnumAndSiteid(String assetnum, String siteid) throws DataAccessException;

    @Query("select assetMeter,meter from AssetMeter assetMeter, Meter meter where assetMeter.metername=meter.metername and assetMeter.assetnum=? and assetMeter.siteid=?")
    public Page<Object[]> findByAssetnums(Pageable pageable, String assetnum, String siteid) throws DataAccessException;


    /**
     * 查找资产使用的计量器
     *
     * @param assetnum  资产编码
     * @param metername 计量器名称
     * @param siteid    地点ID
     * @return 资产使用的计量器
     */
    public List<AssetMeter> findByAssetnumAndMeternameAndSiteid(String assetnum, String metername, String siteid) throws DataAccessException;
}
