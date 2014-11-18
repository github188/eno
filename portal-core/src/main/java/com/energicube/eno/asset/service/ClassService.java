package com.energicube.eno.asset.service;

import com.energicube.eno.asset.model.AssetAttribute;
import com.energicube.eno.asset.model.ClassSpec;
import com.energicube.eno.asset.model.ClassStructure;
import com.energicube.eno.asset.model.Classification;
import com.energicube.eno.common.jsonquery.jpa.request.DataTablesRequestParams;
import com.energicube.eno.common.jsonquery.jpa.response.DataTablesResponse;
import com.energicube.eno.common.model.Tree;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 类别业务操作接口类<br />
 * 包含了类别定义、类别层级、资产属性模板、公用技术规范参数定义等与类别相关的业务操作
 *
 * @author CHENPING
 */
public interface ClassService {

    /**
     * 判断属性ID是否已经存在
     *
     * @param assetattrid 属性ID
     * @return 如果存在则为true，否则为false
     */
    public boolean existAssetAttrId(String assetattrid);


    /**
     * 获取所有资产属性定义
     *
     * @param params 请求参数,参考 {@link DataTablesRequestParams}
     * @return 资产属性{@link AssetAttribute}数据定义{@link DataTablesResponse}列表
     */
    public DataTablesResponse<AssetAttribute> findAllAssetAttribute(DataTablesRequestParams params);


    /**
     * <p/>
     * Query used to populate the DataTables that display the list of AssetAttribute.
     *
     * @param criterias The DataTables criterias used to filter the AssetAttribute.
     *                  (maxResult, filtering, paging, ...)
     * @return a bean that wraps all the needed information by DataTables to
     * redraw the table with the data.
     */
    public DataSet<AssetAttribute> findAssetAttributesWithDatatablesCriterias(DatatablesCriterias criterias);


    /**
     * 保存资产属性定义
     *
     * @param assetAttribute {@link AssetAttribute} object
     * @return 资产属性对象
     */
    public AssetAttribute saveAssetAttribute(AssetAttribute assetAttribute);

    /**
     * 删除{@link AssetAttribute}对象
     *
     * @param assetattributeid 资产属性主键ID
     */
    public void deleteAssetAttribute(long assetattributeid);


    /**
     * 获取所有{@link Classification}定义
     *
     * @param pageable 分页对象
     * @return
     */
    public Page<Classification> findAllClassification(Pageable pageable);

    /**
     * 获取所有{@link Classification}定义
     *
     * @param params 请求参数,参考 {@link DataTablesRequestParams}
     * @return DataTablesResponse
     */
    public DataTablesResponse<Classification> findClassificationsToDataTables(DataTablesRequestParams params);


    /**
     * <p/>
     * Query used to populate the DataTables that display the list of Classifications.
     *
     * @param criterias The DataTables criterias used to filter the Classifications.
     *                  (maxResult, filtering, paging, ...)
     * @return a bean that wraps all the needed information by DataTables to
     * redraw the table with the data.
     */
    public DataSet<Classification> findClassificationsWithDatatablesCriterias(DatatablesCriterias criterias);


    /**
     * 判断类别ID是否已经存在
     *
     * @param classificationid 类别ID
     * @return 如果存在则为true，否则为false
     */
    public boolean existClassificationid(String classificationid, String siteid);

    /**
     * 持久化{@link classification}对象
     *
     * @param classification {@link Classification} Object
     * @return {@link classification}对象
     */
    public Classification saveClassification(Classification classification);

    /**
     * 查找指定类别定义ID的对象
     *
     * @param classificationid 类别ID
     * @return 类别定义对象
     */
    public Classification findClassificationByClassificationid(String classificationid);

    /**
     * 删除{@link Classification}对象
     *
     * @param classificationuid {@link Classification}对象主键ID
     */
    public void deleteClassification(long classificationuid);

    /**
     * 查找所有类别层次定义
     *
     * @param pageable 分页对象
     * @return 所有类别层次定义
     */
    public DataTablesResponse<ClassStructure> findAllClassStructureToDataTables(DataTablesRequestParams params);


    /**
     * 查找所有类别层次定义
     *
     * @param pageable 分页对象
     * @return 所有类别层次定义
     */
    public DataTablesResponse<ClassStructure> findClassStructureChildrenToDataTables(String parent, DataTablesRequestParams params);


    /**
     * 查找指定ID的所有子类别层次定义
     *
     * @param parent 父级ID
     * @return 子类别层次定义列表
     */
    public List<ClassStructure> findClassStructuresByParent(String parent);

    /**
     * 查找所有类别到树
     */
    public List<Tree> findClassStructureTree();

    /**
     * 查找所有类别列表
     *
     * @param orgid 组枳机构ID,默认为空
     * @return 类别列表
     */
    public List<ClassStructure> findAllClassStructure(String orgid);


    /**
     * 获取类别层次结构定义对象
     *
     * @param id 主键ID
     * @return {@link ClassStructure} object
     */
    public ClassStructure findClassStructureById(long id);

    /**
     * 获取指定结构ID的对象
     *
     * @param classstructureid 结构ID
     * @param orgid            机构ID
     * @param siteid           地点ID
     * @return 类别结构
     */
    public ClassStructure findClassStructureByClassstructureid(String classstructureid, String siteid);

    /**
     * 判断是否存在类别层次定义
     *
     * @param classificationid 类别层次定义ID
     * @return 如果存在则为true，否则为false
     */
    public boolean existClassStructure(String classstructureid);

    /**
     * 判断类别层次定义中是否存在相同的类别ID
     *
     * @param classificationid 类别ID
     * @return 如果存在则为true，否则为false
     */
    public boolean existClassStructureByClassificationid(String classificationid);

    /**
     * 持久化保存类别层次定义
     *
     * @param classStructure 类别层次定义{@link ClassStructure} object
     * @return 持久化后的类别层次定义
     */
    public ClassStructure saveClassStructure(long parentId, ClassStructure classStructure);

    /**
     * 删除指定主键ID的类别定义
     *
     * @param classstructureuid 类别结构定义ID
     */
    public void deleteClassStructure(Long classstructureuid);


    /**
     * 查找所有类别规范{@link ClassSpec}定义列表
     *
     * @param classificationid 类别定义ID
     * @param params           请求参数
     * @return 所有类别层次定义
     */
    public DataTablesResponse<ClassSpec> findClassSpecsToDataTables(String classstructureid, DataTablesRequestParams params);

    /**
     * 判断类别是否已经存在指定的属性ID
     *
     * @param classstructureid 类别结构定义ID
     * @param assetattrid      资产属性ID
     * @return 如果存在则为true，否则为false
     */
    public boolean existClassSpecByClassificationid(String classstructureid, String assetattrid);

    /**
     * 持久化类别规范数据
     *
     * @param classSpec 类别规范{@link ClassSpec}对象
     * @return 类别规范{@link ClassSpec}对象
     */
    public ClassSpec saveClassSpec(ClassSpec classSpec);

    /**
     * 删除指定的类别规范
     *
     * @param classspecid 类别规范ID
     */
    public void deleteClassSpec(Long classspecid);

    /**
     * 获取所有的分类数据
     *
     * @return
     */
    public List<ClassStructure> findAllClassStructure();

}
