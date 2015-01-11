package com.energicube.eno.asset.repository;

import com.energicube.eno.asset.model.Measurement;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 测点计量器测量读数
 *
 * @author CHENPING
 */
public interface MeasurementRepository extends JpaRepository<Measurement, Long> {

    /**
     * 查找测点对应的测量记录
     *
     * @param pointnum  测点编号
     * @param metername 计量器名称
     * @param pageable  {@link Pageable} object
     * @return {@link Measurement} the paging list
     */
    public Page<Measurement> findByPointnumAndMeternameAndSiteid(String pointnum, String metername, String siteid, Pageable pageable) throws DataAccessException;

    /**
     * 查找资产对应的测量记录
     *
     * @param assetnum 资产编码
     * @param siteid   地点
     * @return Asset of {@link Measurement} the paging list
     */
    public Page<Measurement> findByAssetnumAndSiteid(String assetnum, String siteid, Pageable pageable) throws DataAccessException;

    /**
     * 查找TAGID对应的测量记录
     *
     * @param valuetag TAGID
     * @param siteid   地点
     * @return the specified  TAGID's {@link Measurement} list
     */
    public List<Measurement> findByValuetag(String valuetag, String siteid) throws DataAccessException;


    /**
     * 获取指定资产的计量信息
     *
     * @param assetnum  资产编号
     * @param metername 计量器
     * @param siteid    地点
     * @param orgid     组织机构
     * @return 资产计量信息
     */
    @Query("select m,spec,point,attr from Measurement m,MeasureSpec spec,MeasurePoint point,AssetAttribute attr" +
            " where m.measurespecid=spec.measurespecid and point.isspec=true " +
            "and m.pointnum=point.pointnum and spec.assetattrid=attr.assetattrid " +
            "and m.siteid=point.siteid and m.orgid=point.orgid " +
            "and m.assetnum=?1 and point.metername=?2 and coalesce(m.siteid,'')=?3 and coalesce(m.orgid,'')=?4")
    public List<Object[]> findByAssetnumAndMetername(String assetnum,
                                                     String metername, String siteid, String orgid) throws DataAccessException;


    @Query("select m,spec,point,attr,tags from Measurement m,MeasureSpec spec,MeasurePoint point,AssetAttribute attr, Tags tags" +
            " where tags.valuetag = m.valuetag and m.measurespecid=spec.measurespecid and point.isspec=true " +
            "and m.pointnum=point.pointnum and spec.assetattrid=attr.assetattrid " +
            "and m.siteid=point.siteid and m.orgid=point.orgid " +
            "and m.assetnum=?1 and coalesce(m.siteid,'')=?2 and coalesce(m.orgid,'')=?3")
    public List<Object[]> findByAssetnum(String assetnum, String siteid, String orgid) throws DataAccessException;


    /**
     * 获取指定资产的计量信息
     *
     * @param pointnum  测点
     * @param metername 计量器
     * @param siteid    地点
     * @param orgid     组织机构
     * @return 资产计量信息
     */
    @Query("select m,spec,point,attr from Measurement m,MeasureSpec spec,MeasurePoint point,AssetAttribute attr" +
            " where m.measurespecid=spec.measurespecid and point.isspec=true " +
            "and m.pointnum=point.pointnum and spec.assetattrid=attr.assetattrid " +
            "and m.siteid=point.siteid and m.orgid=point.orgid " +
            "and m.pointnum=?1 and point.metername=?2 and coalesce(m.siteid,'')=?3  and coalesce(m.orgid,'')=?4 ")
    public Page<Object[]> findByPointnumAndMetername(String pointnum,
                                                     String metername, String siteid, String orgid, Pageable pageable) throws DataAccessException;


    /**
     * 获取计量读数相关信息
     *
     * @param 计量器读数ID
     */
    @Query("select m,spec,point,attr from Measurement m,MeasureSpec spec,MeasurePoint point,AssetAttribute attr" +
            " where m.measurespecid=spec.measurespecid and point.isspec=true " +
            "and m.pointnum=point.pointnum and spec.assetattrid=attr.assetattrid " +
            "and m.siteid=point.siteid and m.orgid=point.orgid " +
            "and m.measurementid=?")
    public List<Object[]> findByMeasurementid(long measurementid) throws DataAccessException;
    
    /**
     * 获取计量读数相关信息
     *
     * @param 计量器读数ID
     */
    @Query("select m from Measurement m where m.assetnum = ?1")
    public List<Measurement> findByAssetNum(String assetnum) throws DataAccessException;


}
