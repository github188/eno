package com.energicube.eno.asset.service;

import com.energicube.eno.asset.model.*;
import com.energicube.eno.common.jsonquery.jpa.request.DataTablesRequestParams;
import com.energicube.eno.common.jsonquery.jpa.response.DataTablesResponse;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * 资产服务接口类
 */
public interface AssetService {


    /**
     * <p/>
     * Query used to populate the DataTables that display the list of Assets.
     *
     * @param criterias The DataTables criterias used to filter the Assets.
     *                  (maxResult, filtering, paging, ...)
     * @return a bean that wraps all the needed information by DataTables to
     * redraw the table with the data.
     */
    public DataSet<Asset> findAssetsWithDatatablesCriterias(DatatablesCriterias criterias, Map<String, String> customCondition);


    public List<Asset> findAssetsByCondtion(Map<String, String> customCondition);


    /**
     * 获取指定位置的资产列表
     *
     * @param location 位置代码
     * @return 资产列表数据
     */
    public List<Asset> findByLocation(String location);

    /**
     * 获取指定位置、专业分类和资产类别的资产数据
     * <p>如果参数CLASSSTRUCTUREID为空，则直接调用findByLocationAndSpecclass方法</p>
     *
     * @param location         位置代码
     * @param specclass        专业分类
     * @param classstructureid 类别结构ID
     * @return 资产列表数据
     */
    public List<Asset> findByLocationAndSpecclassAndClassstructureidAndSiteid(String location, String specclass, String classstructureid, String siteid);


    /**
     * 获取指定专业分类和资产类别的资产数据
     *
     * @param specclass        专业分类
     * @param classstructureid 类别结构ID
     * @return 资产数据
     */
    public List<Asset> findBySpecclassAndClassstructureidAndSiteid(String specclass, String classstructureid, String siteid);


    /**
     * 获取指定位置和专业分类的资产数据
     *
     * @param location  位置代码
     * @param specclass 专业分类
     * @return 资产列表数据
     */
    public List<Asset> findByLocationAndSpecclassAndSiteid(String location, String specclass, String siteid);


    /**
     * 获取指定专业分类的资产
     *
     * @param specclass 专业分类
     * @return 资产列表
     */
    public List<Asset> findBySpecclass(String specclass);

    /**
     * 获取指定ID的资产信息
     *
     * @param assetid 资产ID
     * @return 资产信息
     */
    public Asset findAssetById(long assetid);

    /**
     * 获取资产对象
     *
     * @param assetnum 资产编号
     * @param siteid   地点
     * @param orgid    组织机构
     * @return {@link Asset} object
     */
    public Asset findAssetByAssetnum(String assetnum, String siteid, String orgid) throws Exception;

    /**
     * 查找指定类别ID的资产信息
     *
     * @param classstructureid 类别结构ID
     * @param siteid           地点
     * @param orgid            组织机构
     * @return {@link Asset} list object
     */
    public List<Asset> findByClassstructureid(String classstructureid, String siteid, String orgid);

    /**
     * 获取所有资产台账
     *
     * @param pageNumber 当前页码
     *
     * @return 资产台账列表
     * */
    //public Page<Asset> findAllAsset(Integer pageNumber);

    /**
     * 获取所有资产台账
     *
     * @param pageable 分页对象
     * @return 资产台账列表
     */
    public Page<Asset> findAllAsset(Pageable pageable);

    /**
     * 获取指定的地点和机构的所有资产台账信息
     *
     * @param siteid 地点
     * @param orgid  组织机构
     * @return {@link Page}ing of {@link Asset} list
     */
    public Page<Asset> findAssetsBySiteidAndOrgid(String siteid, String orgid, Pageable pageable);


    /**
     * 判断资产信息是否存在
     *
     * @param assetnum 资产编码
     * @return true=存在 false=不存在
     */
    public boolean existAsset(String assetnum);

    /**
     * 保存资产信息
     *
     * @param asset persistence object
     * @return asset  object
     */
    public Asset saveAsset(Asset asset);

    /**
     * 删除资产信息
     *
     * @param assetuid 资产主键ID
     */
    public void deleteAsset(long assetuid);


    /**
     * 查找资产的计量器信息
     *
     * @param assetnum 资产编码
     * @param siteid   安装地点
     */
    public List<AssetMeterSet> findAssetMeterByAssetnum(String assetnum, String siteid);


    /**
     * <p/>
     * Query used to populate the DataTables that display the list of AssetMeterSets.
     *
     * @param criterias The DataTables criterias used to filter the AssetMeterSets.
     *                  (maxResult, filtering, paging, ...)
     * @return a bean that wraps all the needed information by DataTables to
     * redraw the table with the data.
     */
    public DataSet<AssetMeterSet> findAssetMeterSetsWithDatatablesCriterias(DatatablesCriterias criterias, String assetnum, String siteid);


    /**
     * 查找资产使用的计量器
     *
     * @param assetmeterid 计量器ID
     * @return 资产使用的计量器
     */
    public AssetMeter findAssetMeterById(Integer assetmeterid);

    /**
     * 判断资产使用的计量器是否已经存在
     *
     * @param assetnum  资产代码
     * @param metername 计量器代码
     * @param siteid    安装地点
     * @return true=存在 false=不存在
     */
    public boolean existAssetMeter(String assetnum, String metername, String siteid);

    /**
     * 保存资产使用的计量器
     *
     * @param assetMeter 资产使用的计量器对象
     * @return 资产使用的计量器
     */
    public AssetMeter saveAssetMeter(AssetMeter assetMeter);

    /**
     * 删除资产使用的计量器
     *
     * @param assetmeterid 资产使用的计量器主键ID
     */
    public void deleteAssetMeter(int assetmeterid);


    /**
     * 查找资产技术规范参数
     *
     * @param assetnum 资产编码
     * @param siteid   安装地点
     */
    public List<AssetSpecSet> findAssetSpecByAssetnum(String assetnum, String siteid);

    /**
     * 查找资产的计量器信息
     *
     * @param assetnum 资产编码
     * @param siteid   安装地点
     */
    public DataTablesResponse<AssetSpecSet> findAssetSpecByAssetnum(String assetnum, String siteid, DataTablesRequestParams params);


    /**
     * 判断资产使用的技术规范参数属性是否已经存在
     *
     * @param assetnum    资产代码
     * @param assetattrid 属性ID
     * @param siteid      安装地点
     * @return true=存在 false=不存在
     */
    public boolean existAssetSpec(String assetnum, String assetattrid, String siteid);

    /**
     * 保存资产使用的技术规范参数
     *
     * @param assetSpec 技术规范参数
     * @return 资产使用的技术规范参数对象
     */
    public AssetSpec saveAssetSpec(AssetSpec assetSpec);

    /**
     * 批量保存技术规范参数
     *
     * @param classstructureid 类别结构ID
     * @param siteid           地点
     */
    public void batchSaveAssetSpec(String classstructureid, String siteid);

    /**
     * 删除资产使用的技术规范参数
     *
     * @param assetspecid 规范参数主键ID
     */
    public void deleteAssetSpec(int assetspecid);


    /**
     * 获取指定TAGID对应的资产信息
     *
     * @param tagid
     * @return {@link Asset} 资产信息
     */
    public Asset findByTagId(String tagid);


    /**
     * 获取指定位置、专业分类和资产类别的资产数据
     * <p>如果参数CLASSSTRUCTUREID为空，则直接调用findByLocationAndSpecclass方法</p>
     *
     * @param location         位置代码 模糊查询
     * @param specclass        专业分类
     * @param classstructureid 类别结构ID
     * @return 资产列表数据
     */
    public List<Asset> findByLikeLocationAndSpecclassAndClassstructureidAndSiteid(String location, String specclass, String classstructureid, String siteid);


    /**
     * 查找指定地址和机构的所有未移动资产信息
     *
     * @param location  楼层
     * @param specclass 专业分类
     * @param siteid    地点ID
     * @param orgid     组织ID
     * @param pageable  {@link Pageable} objects
     * @return {@link Asset} Page
     */
    public Page<Asset> findByLikeLocationAndSpecclassAndSiteidAndOrgid(String location, String specclass, String siteid, String orgid, Pageable pageable);


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
    public Page<Asset> findByLikeLocationAndSpecclassAndClassstructureidInAndSiteid(String location, String specclass, String classstructureid, String siteid, String orgid, Pageable pageable);

}
