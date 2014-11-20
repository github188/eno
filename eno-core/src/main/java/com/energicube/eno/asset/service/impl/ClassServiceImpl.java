package com.energicube.eno.asset.service.impl;

import com.energicube.eno.asset.model.*;
import com.energicube.eno.asset.repository.*;
import com.energicube.eno.asset.repository.jpa.JpaAssetAttributeRepository;
import com.energicube.eno.asset.repository.jpa.JpaClassificationRepository;
import com.energicube.eno.asset.service.ClassService;
import com.energicube.eno.asset.service.IdGenService;
import com.energicube.eno.common.jsonquery.jpa.request.DataTablesRequestParams;
import com.energicube.eno.common.jsonquery.jpa.response.DataTablesResponse;
import com.energicube.eno.common.model.State;
import com.energicube.eno.common.model.Tree;
import com.energicube.eno.common.util.TreeUtil;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClassServiceImpl implements ClassService {

    private AssetAttributeRepository assetAttributeRepository;
    private ClassAncestorRepository classAncestorRepository;
    private ClassificationRepository classificationRepository;
    private ClassSpecRepository classSpecRepository;
    private ClassStructureRepository classStructureRepository;
    private ClassUseWithRepository classUseWithRepository;
    private JpaClassificationRepository jpaClassificationRepository;

    private JpaAssetAttributeRepository jpaAssetAttributeRepository;

    private IdGenService idGenService;

    @Autowired
    public ClassServiceImpl(ClassAncestorRepository classAncestorRepository,
                            ClassificationRepository classificationRepository,
                            ClassSpecRepository classSpecRepository,
                            ClassStructureRepository classStructureRepository,
                            ClassUseWithRepository classUseWithRepository,
                            AssetAttributeRepository assetAttributeRepository,
                            IdGenService idGenService, JpaAssetAttributeRepository jpaAssetAttributeRepository,
                            JpaClassificationRepository jpaClassificationRepository) {
        this.classAncestorRepository = classAncestorRepository;
        this.classificationRepository = classificationRepository;
        this.classSpecRepository = classSpecRepository;
        this.classStructureRepository = classStructureRepository;
        this.classUseWithRepository = classUseWithRepository;
        this.assetAttributeRepository = assetAttributeRepository;
        this.idGenService = idGenService;
        this.jpaAssetAttributeRepository = jpaAssetAttributeRepository;
        this.jpaClassificationRepository = jpaClassificationRepository;
    }

    @Transactional(readOnly = true)
    public boolean existAssetAttrId(String assetattrid) {
        List<AssetAttribute> list = assetAttributeRepository.findByAssetattrid(assetattrid);
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public DataTablesResponse<AssetAttribute> findAllAssetAttribute(
            DataTablesRequestParams params) {
        DataTablesResponse<AssetAttribute> result = new DataTablesResponse<AssetAttribute>();

        // 排序方式和排序列
        Direction sortDir = params.getsSortDir_0().equals("asc") ? Direction.ASC
                : Direction.DESC;
        String sortColName = params.getsColumns().split(",")[params
                .getiSortCol_0()];
        Sort sort = new Sort(sortDir, sortColName);
        // 当前页
        int pageNumber = (int) Math.ceil(params.getiDisplayStart()
                / params.getiDisplayLength());

        Page<AssetAttribute> page = assetAttributeRepository.findAll(new PageRequest(
                pageNumber, params.getiDisplayLength(), sort));

        List<AssetAttribute> data = page.getContent();
        result.setsEcho(params.getsEcho());
        result.setiTotalDisplayRecords(page.getTotalElements());
        result.setiTotalRecords(page.getTotalElements());
        result.setAaData(data);

        return result;
    }


    @Transactional(readOnly = true)
    public DataSet<AssetAttribute> findAssetAttributesWithDatatablesCriterias(
            DatatablesCriterias criterias) {


        List<AssetAttribute> persons = jpaAssetAttributeRepository.findAssetWithDatatablesCriterias(criterias);
        Long count = jpaAssetAttributeRepository.getTotalCount();
        Long countFiltered = jpaAssetAttributeRepository.getFilteredCount(criterias);

        return new DataSet<AssetAttribute>(persons, count, countFiltered);
    }

    @Transactional
    public AssetAttribute saveAssetAttribute(AssetAttribute assetAttribute) {
        assetAttribute.setAssetattrid(assetAttribute.getAssetattrid().toUpperCase().trim());
        assetAttribute.setDescription(assetAttribute.getDescription().trim());
        return assetAttributeRepository.saveAndFlush(assetAttribute);
    }

    @Transactional
    public void deleteAssetAttribute(long assetattributeid) {
        if (assetattributeid > 0) {
            assetAttributeRepository.delete(assetattributeid);
        } else {
            throw new NullPointerException("资产属性ID不能为空或空");
        }
    }

    @Transactional(readOnly = true)
    public Page<Classification> findAllClassification(Pageable pageable) {
        return classificationRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public DataTablesResponse<Classification> findClassificationsToDataTables(
            DataTablesRequestParams params) {
        // 创建DataTable数据格式对象
        DataTablesResponse<Classification> result = new DataTablesResponse<Classification>();

        // 排序方式和排序列
        Direction sortDir = params.getsSortDir_0().equals("asc") ? Direction.ASC
                : Direction.DESC;
        String sortColName = params.getsColumns().split(",")[params.getiSortCol_0()];
        Sort sort = new Sort(sortDir, sortColName);
        // 当前页
        int pageNumber = (int) Math.ceil(params.getiDisplayStart() / params.getiDisplayLength());

        Page<Classification> page = classificationRepository.findAll(new PageRequest(
                pageNumber, params.getiDisplayLength(), sort));

        List<Classification> data = page.getContent();
        result.setsEcho(params.getsEcho());
        result.setiTotalDisplayRecords(page.getTotalElements());
        result.setiTotalRecords(page.getTotalElements());
        result.setAaData(data);

        return result;
    }


    @Transactional(readOnly = true)
    public DataSet<Classification> findClassificationsWithDatatablesCriterias(
            DatatablesCriterias criterias) {

        List<Classification> classifications = jpaClassificationRepository.findClassificationsWithDatatablesCriterias(criterias);
        Long count = jpaClassificationRepository.getTotalCount();
        Long countFiltered = jpaClassificationRepository.getFilteredCount(criterias);

        return new DataSet<Classification>(classifications, count, countFiltered);

    }

    @Transactional(readOnly = true)
    public boolean existClassificationid(String classificationid, String siteid) {
        List<Classification> list = classificationRepository.findByClassificationidAndSiteid(classificationid, siteid);
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public Classification findClassificationByClassificationid(
            String classificationid) {
        List<Classification> list = classificationRepository.findByClassificationid(classificationid);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Transactional
    public Classification saveClassification(Classification classification) {
        classification.setClassificationid(classification.getClassificationid().toUpperCase());
        return classificationRepository.save(classification);
    }

    @Transactional
    public void deleteClassification(long classificationuid) {
        classificationRepository.delete(classificationuid);
    }

    @Transactional(readOnly = true)
    public DataTablesResponse<ClassStructure> findAllClassStructureToDataTables(
            DataTablesRequestParams params) {
        // 创建DataTable数据格式对象
        DataTablesResponse<ClassStructure> result = new DataTablesResponse<ClassStructure>();

        // 排序方式和排序列
        Direction sortDir = params.getsSortDir_0().equals("asc") ? Direction.ASC
                : Direction.DESC;
        String sortColName = params.getsColumns().split(",")[params.getiSortCol_0()];
        Sort sort = new Sort(sortDir, sortColName);
        // 当前页
        int pageNumber = (int) Math.ceil(params.getiDisplayStart() / params.getiDisplayLength());

        Page<ClassStructure> page = classStructureRepository.findAll(new PageRequest(
                pageNumber, params.getiDisplayLength(), sort));

        List<ClassStructure> data = page.getContent();
        result.setsEcho(params.getsEcho());
        result.setiTotalDisplayRecords(page.getTotalElements());
        result.setiTotalRecords(page.getTotalElements());
        result.setAaData(data);

        return result;
    }


    @Transactional(readOnly = true)
    public DataTablesResponse<ClassStructure> findClassStructureChildrenToDataTables(
            String parent, DataTablesRequestParams params) {

        // 创建DataTable数据格式对象
        DataTablesResponse<ClassStructure> result = new DataTablesResponse<ClassStructure>();

        // 排序方式和排序列
        Direction sortDir = params.getsSortDir_0().equals("asc") ? Direction.ASC
                : Direction.DESC;
        String sortColName = params.getsColumns().split(",")[params.getiSortCol_0()];
        Sort sort = new Sort(sortDir, sortColName);
        // 当前页
        int pageNumber = (int) Math.ceil(params.getiDisplayStart() / params.getiDisplayLength());

        Page<ClassStructure> page = classStructureRepository.findByParent(parent, new PageRequest(
                pageNumber, params.getiDisplayLength(), sort));
        List<ClassStructure> data = page.getContent();
        result.setsEcho(params.getsEcho());
        result.setiTotalDisplayRecords(page.getTotalElements());
        result.setiTotalRecords(page.getTotalElements());
        result.setAaData(data);

        return result;
    }

    @Transactional(readOnly = true)
    public List<ClassStructure> findClassStructuresByParent(String parent) {
        return classStructureRepository.findByParent(parent, new PageRequest(0, Integer.MAX_VALUE)).getContent();
    }


    @Transactional(readOnly = true)
    public List<Tree> findClassStructureTree() {
        List<Tree> treelist = new ArrayList<Tree>();
        List<ClassStructure> data = classStructureRepository.findAll();
        for (ClassStructure cs : data) {
            Tree tree = transform(cs);
            treelist.add(tree);
        }
        return TreeUtil.build(treelist);
    }

    private Tree transform(ClassStructure classStructure) {
        Tree tree = new Tree();
        tree.setId(classStructure.getClassstructureid());
        String text = classStructure.getDescription();
        if (!StringUtils.hasLength(text)) {
            text = classStructure.getClassstructureid();
        }
        tree.setText(text);
        tree.setParentid(classStructure.getParent());
        tree.setState(State.colsed);
        return tree;
    }


    @Transactional(readOnly = true)
    public List<ClassStructure> findAllClassStructure(String orgid) {
        if (!StringUtils.hasLength(orgid)) {
            orgid = "";
        }
        List<ClassStructure> data = classStructureRepository.findByOrgid(orgid);
        return data;
    }

    @Transactional(readOnly = true)
    public ClassStructure findClassStructureById(long id) {
        return classStructureRepository.findOne(id);
    }


    @Transactional(readOnly = true)
    public ClassStructure findClassStructureByClassstructureid(
            String classstructureid, String siteid) {
        List<ClassStructure> list = classStructureRepository.findByClassstructureidAndSiteid(classstructureid, siteid);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Transactional(readOnly = true)
    public boolean existClassStructure(String classstructureid) {
        List<ClassStructure> list = classStructureRepository.findByClassstructureid(classstructureid);
        if (list != null && list.size() > 0)
            return true;
        return false;
    }


    @Transactional(readOnly = true)
    public boolean existClassStructureByClassificationid(String classificationid) {
        List<ClassStructure> list = classStructureRepository.findByClassificationid(classificationid);
        if (list != null && list.size() > 0)
            return true;
        return false;
    }

    @Transactional
    public ClassStructure saveClassStructure(long parentId, ClassStructure classStructure) {
        //更新类别编码为大写
        classStructure.setClassificationid(classStructure.getClassificationid().toUpperCase());
        if (classStructure.getClassstructureuid() == 0) {
            String classstructureid = idGenService.generateNewId(ClassStructure.class.getName());
            classStructure.setClassstructureid(classstructureid);
        }
        if (parentId > 0) {
            ClassStructure parentClassStructure = classStructureRepository.findOne(parentId);
            classStructure.setParent(parentClassStructure.getClassstructureid());
        }
        //保存类别定义结构
        classStructure = classStructureRepository.save(classStructure);

        //保存类别祖代结构
        //新增当前
        ClassAncestor classAncestor = new ClassAncestor();
        classAncestor.setClassstructureid(classStructure.getClassstructureid());
        classAncestor.setClassificationid(classStructure.getClassificationid());
        classAncestor.setAncestorclassid(classStructure.getClassificationid());
        classAncestor.setAncestor(classStructure.getClassstructureid());
        classAncestor.setHierarchylevels(0);
        classAncestorRepository.save(classAncestor);

        addClassAncestor(classStructure);
        return classStructure;
    }


    private void addClassAncestor(ClassStructure classStructure) {
        if (StringUtils.hasLength(classStructure.getParent())) {
            ClassStructure parentClassStructure = null;
            //父级ID
            String parentId = classStructure.getParent();
            List<ClassStructure> list = classStructureRepository.findByClassstructureid(parentId);
            if (list != null && list.size() > 0) {
                parentClassStructure = list.get(0);
            }
            if (parentClassStructure != null && StringUtils.hasLength(parentClassStructure.getParent())) {
                addClassAncestor(parentClassStructure);
            }
            //获取上级祖代结构
            ClassAncestor parentClassAncestor = classAncestorRepository
                    .findByClassstructureidAndClassificationidAndAncestorAndAncestorclassid(
                            parentClassStructure.getClassstructureid(), parentClassStructure.getClassificationid(), parentId, parentClassStructure.getClassificationid());
            int level = parentClassAncestor.getHierarchylevels();

            for (int i = 0; i < level + 1; i++) {
                ClassAncestor ca = new ClassAncestor();
                ca.setClassstructureid(classStructure.getClassstructureid());
                ca.setClassificationid(classStructure.getClassificationid());
                ca.setAncestorclassid(parentClassAncestor.getAncestorclassid());  //?
                ca.setAncestor(parentClassAncestor.getAncestor());  //?
                ca.setHierarchylevels(parentClassAncestor.getHierarchylevels() + 1);
                classAncestorRepository.save(ca);
            }

        }
    }


    @Transactional
    public void deleteClassStructure(Long classstructureuid) {
        classStructureRepository.delete(classstructureuid);

    }

    @Transactional(readOnly = true)
    public DataTablesResponse<ClassSpec> findClassSpecsToDataTables(
            String classstructureid, DataTablesRequestParams params) {

        // 创建DataTable数据格式对象
        DataTablesResponse<ClassSpec> result = new DataTablesResponse<ClassSpec>();

        // 排序方式和排序列
        Direction sortDir = params.getsSortDir_0().equals("asc") ? Direction.ASC
                : Direction.DESC;
        String sortColName = params.getsColumns().split(",")[params.getiSortCol_0()];
        Sort sort = new Sort(sortDir, sortColName);
        // 当前页
        int pageNumber = (int) Math.ceil(params.getiDisplayStart() / params.getiDisplayLength());

        Page<ClassSpec> page = classSpecRepository.findByClassstructureid(classstructureid, new PageRequest(
                pageNumber, params.getiDisplayLength(), sort));
        List<ClassSpec> data = page.getContent();
        result.setsEcho(params.getsEcho());
        result.setiTotalDisplayRecords(page.getTotalElements());
        result.setiTotalRecords(page.getTotalElements());
        result.setAaData(data);

        return result;
    }

    @Transactional(readOnly = true)
    public boolean existClassSpecByClassificationid(String classstructureid,
                                                    String assetattrid) {
        List<ClassSpec> list = classSpecRepository.findByClassstructureidAndAssetattrid(classstructureid, assetattrid);
        if (list != null && list.size() > 0)
            return true;
        return false;
    }

    @Transactional
    public ClassSpec saveClassSpec(ClassSpec classSpec) {
        classSpec.setAssetattrid(classSpec.getAssetattrid().trim().toUpperCase());
        return classSpecRepository.save(classSpec);
    }

    @Transactional
    public void deleteClassSpec(Long classspecid) {
        classSpecRepository.delete(classspecid);
    }

    /**
     * 获取所有的分类数据
     *
     * @return
     */
    @Transactional
    public List<ClassStructure> findAllClassStructure() {
        return classStructureRepository.findAll();
    }

}
