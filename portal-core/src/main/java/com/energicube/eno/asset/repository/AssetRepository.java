package com.energicube.eno.asset.repository;

import com.energicube.eno.asset.model.Asset;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * asset data access interface
 *
 * @author CHENPING
 */
public interface AssetRepository extends JpaRepository<Asset, Long> {

    /**
     * 获取指定位置的资产
     *
     * @param location 位置代码
     * @return 资产列表
     */
    public List<Asset> findByLocation(String location) throws DataAccessException;

    /**
     * 获取指定专业分类的资产
     *
     * @param specclass 专业分类
     * @return 资产列表
     */
    public List<Asset> findBySpecclass(String specclass) throws DataAccessException;

    /**
     * 获取指定位置和专业分类的资产数据
     *
     * @param location  位置代码
     * @param specclass 专业分类
     * @return 资产列表数据
     */
    @Query("select a from Asset a where location=?1 and coalesce(specclass,'')=?2 and coalesce(siteid,'')=?3")
    public List<Asset> findByLocationAndSpecclassAndSiteid(String location, String specclass, String siteid) throws DataAccessException;


    /**
     * 获取指定位置、专业分类和资产类别的资产数据
     * <p>如果参数CLASSSTRUCTUREID为空，则直接调用findByLocationAndSpecclass方法</p>
     *
     * @param location         位置代码
     * @param specclass        专业分类
     * @param classstructureid 类别结构ID
     * @return 资产列表数据
     */
    @Query("select a from Asset a where location=?1 and coalesce(specclass,'')=?2 and coalesce(classstructureid,'')=?3 and coalesce(siteid,'')=?4")
    public List<Asset> findByLocationAndSpecclassAndClassstructureidAndSiteid(String location, String specclass, String classstructureid, String siteid) throws DataAccessException;


    /**
     * 获取指定专业分类和资产类别的资产数据
     *
     * @param specclass        专业分类
     * @param classstructureid 类别结构ID
     * @return 资产数据
     */
    @Query("select a from Asset a where coalesce(moved,0)=0 and coalesce(specclass,'')=?1 and coalesce(classstructureid,'')=?2 and coalesce(siteid,'')=?3")
    public List<Asset> findBySpecclassAndClassstructureidAndSiteid(String specclass, String classstructureid, String siteid) throws DataAccessException;


    /**
     * 查找指定资产编码的信息
     *
     * @param assetnum 资产编码
     * @return 资产台账列表
     */
    public List<Asset> findByAssetnumAndMoved(String assetnum, boolean moved) throws DataAccessException;

    /**
     * 查找指定地址和机构的所有未移动资产信息
     *
     * @param siteid   地点ID
     * @param orgid    组织ID
     * @param pageable {@link Pageable} objects
     * @return {@link Asset} list
     */
    @Query("select a from Asset a where siteid=?1 and orgid=?2 and moved=false")
    public Page<Asset> findBySiteidAndOrgid(String siteid, String orgid, Pageable pageable) throws DataAccessException;

    /**
     * 查找资产信息
     *
     * @param assetnum 资产编号
     * @param siteid   地点
     * @param orgid    组织机构
     * @return {@link Asset} object
     */
    public Asset findByAssetnumAndSiteidAndOrgid(String assetnum, String siteid, String orgid) throws DataAccessException;

    /**
     * 查找指定类别ID的资产信息
     *
     * @param classstructureid 类别结构ID
     * @param siteid           地点
     * @param orgid            组织机构
     * @return {@link Asset} list object
     */
    public List<Asset> findByClassstructureidAndSiteidAndOrgid(String classstructureid, String siteid, String orgid) throws DataAccessException;

    /**
     * 查找指定TAGID的资产列表
     *
     * @param tagid TAGID
     * @return 资产列表
     */
    @Query("select s from Measurement m,Asset s where m.assetnum=s.assetnum and coalesce(m.siteid,'')=coalesce(s.siteid,'') and m.valuetag=?1")
    public List<Asset> findByTagId(String tagid) throws DataAccessException;


    /**
     * 获取指定位置、专业分类和资产类别的资产数据
     * <p>如果参数CLASSSTRUCTUREID为空，则直接调用findByLocationAndSpecclass方法</p>
     *
     * @param location         位置代码 模糊查询
     * @param specclass        专业分类
     * @param classstructureid 类别结构ID
     * @return 资产列表数据
     */
    @Query("select a from Asset a where location  like ?1 and coalesce(specclass,'')=?2 and coalesce(classstructureid,'')=?3 and coalesce(siteid,'')=?4")
    public List<Asset> findByLikeLocationAndSpecclassAndClassstructureidAndSiteid(String location, String specclass, String classstructureid, String siteid) throws DataAccessException;


    /**
     * 查找指定地址和机构的所有未移动资产信息
     *
     * @param location  楼层
     * @param specclass 专业分类
     * @param siteid    地点ID
     * @param orgid     组织ID
     * @param pageable  {@link Pageable} objects
     * @return {@link Asset} list
     */
    @Query("select a from Asset a where location  like ?1 and coalesce(specclass,'')=?2  and  coalesce(siteid,'')=?3 and coalesce(orgid,'')=?4  and moved=false")
    public Page<Asset> findByLikeLocationAndSpecclassAndSiteidAndOrgid(String location, String specclass, String siteid, String orgid, Pageable pageable) throws DataAccessException;

    /**
     * 根据资产编码查找对象
     *
     * @param assetnum
     * @return
     * @throws DataAccessException
     */
    public Asset findByAssetnum(String assetnum) throws DataAccessException;


    /**
     * 获取指定位置、专业分类和资产类别的资产数据
     *
     * @param location         位置代码 模糊查询
     * @param specclass        专业分类
     * @param classstructureid 类别结构ID
     * @param siteid           地点ID
     * @param orgid            组织ID
     * @return 资产列表数据
     */
    @Query("select a from Asset a where location  like ?1 and coalesce(specclass,'')=?2 and coalesce(classstructureid,'') in ( ?3 ) and coalesce(siteid,'')=?4  and coalesce(orgid,'')=?5  and moved=false")
    public Page<Asset> findByLikeLocationAndSpecclassAndClassstructureidAndSiteid(String location, String specclass, String classstructureid, String siteid, String orgid, Pageable pageable) throws DataAccessException;
}
