package com.energicube.eno.asset.service.impl;

import com.energicube.eno.asset.model.*;
import com.energicube.eno.asset.repository.*;
import com.energicube.eno.asset.repository.jpa.JpaAssetMeterRepository;
import com.energicube.eno.asset.repository.jpa.JpaAssetRepository;
import com.energicube.eno.asset.service.AssetService;
import com.energicube.eno.common.jsonquery.jpa.request.DataTablesRequestParams;
import com.energicube.eno.common.jsonquery.jpa.response.DataTablesResponse;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
import java.util.Map;

@Service
public class AssetServiceImpl implements AssetService {

    private AssetRepository assetRepository;
    private AssetMeterRepository assetMeterRepository;
    private AssetSpecRepository assetSpecRepository;
    private ClassStructureRepository classStructureRepository;
    private ClassSpecRepository classSpecRepository;
    private JpaAssetRepository jpaAssetRepository;
    private JpaAssetMeterRepository jpaAssetMeterRepository;

    @Autowired
    public void setAssetRepository(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    @Autowired
    public void setAssetMeterRepository(AssetMeterRepository assetMeterRepository) {
        this.assetMeterRepository = assetMeterRepository;
    }

    @Autowired
    public void setAssetSpecRepository(AssetSpecRepository assetSpecRepository) {
        this.assetSpecRepository = assetSpecRepository;
    }

    @Autowired
    public void setJpaAssetRepository(JpaAssetRepository jpaAssetRepository) {
        this.jpaAssetRepository = jpaAssetRepository;
    }

    @Autowired
    public void setJpaAssetMeterRepository(
            JpaAssetMeterRepository jpaAssetMeterRepository) {
        this.jpaAssetMeterRepository = jpaAssetMeterRepository;
    }

    @Transactional(readOnly = true)
    public DataSet<Asset> findAssetsWithDatatablesCriterias(
            DatatablesCriterias criterias, Map<String, String> customCondition) {

        List<Asset> assets = jpaAssetRepository.findAssetWithDatatablesCriterias(criterias, customCondition);
        Long count = jpaAssetRepository.getTotalCount();
        Long countFiltered = jpaAssetRepository.getFilteredCount(criterias, customCondition);

        return new DataSet<Asset>(assets, count, countFiltered);
    }


    @Transactional(readOnly = true)
    public List<Asset> findAssetsByCondtion(Map<String, String> customCondition) {
        return jpaAssetRepository.findAssetsByCondtion(customCondition);
    }

    @Transactional(readOnly = true)
    public List<Asset> findByLocation(String location) {
        return assetRepository.findByLocation(location);
    }

    @Transactional(readOnly = true)
    public List<Asset> findBySpecclass(String specclass) {
        return assetRepository.findBySpecclass(specclass);
    }


    @Transactional(readOnly = true)
    public List<Asset> findByLocationAndSpecclassAndClassstructureidAndSiteid(
            String location, String specclass, String classstructureid,
            String siteid) {
        if (!StringUtils.hasLength(siteid)) {
            siteid = "";
        }
        return assetRepository.findByLocationAndSpecclassAndClassstructureidAndSiteid(location, specclass, classstructureid, siteid);
    }


    @Cacheable(value = "assets", key = "#specclass+ '-'+ #classstructureid +'findBySpecAndClass'")
    @Transactional(readOnly = true)
    public List<Asset> findBySpecclassAndClassstructureidAndSiteid(
            String specclass, String classstructureid, String siteid) {
        if (!StringUtils.hasLength(siteid)) {
            siteid = "";
        }
        return assetRepository.findBySpecclassAndClassstructureidAndSiteid(specclass, classstructureid, siteid);
    }

    @Transactional(readOnly = true)
    public List<Asset> findByLocationAndSpecclassAndSiteid(String location,
                                                           String specclass, String siteid) {
        if (!StringUtils.hasLength(siteid)) {
            siteid = "";
        }
        return assetRepository.findByLocationAndSpecclassAndSiteid(location, specclass, siteid);
    }

    @Transactional(readOnly = true)
    public Page<Asset> findAllAsset(Pageable pageable) {
        return assetRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Asset findAssetByAssetnum(String assetnum, String siteid,
                                     String orgid) throws Exception {
        if (!StringUtils.hasLength(siteid)) {
            siteid = "";
        }
        if (!StringUtils.hasLength(orgid)) {
            siteid = "";
        }
        Asset asset = null;
        try {
            asset = assetRepository.findByAssetnumAndSiteidAndOrgid(assetnum, siteid, orgid);
        } catch (Exception e) {
            asset = null;
        }
        return asset;
    }


    @Cacheable(value = "assets", key = "#specclass+ '-'+ #classstructureid +'findByClassstructureid'")
    @Transactional(readOnly = true)
    public List<Asset> findByClassstructureid(String classstructureid,
                                              String siteid, String orgid) {
        if (!StringUtils.hasLength(siteid)) {
            siteid = "";
        }
        if (!StringUtils.hasLength(orgid)) {
            siteid = "";
        }
        return assetRepository.findByClassstructureidAndSiteidAndOrgid(classstructureid, siteid, orgid);
    }


    @Transactional(readOnly = true)
    public Page<Asset> findAssetsBySiteidAndOrgid(String siteid, String orgid,
                                                  Pageable pageable) {
        if (!StringUtils.hasLength(siteid)) {
            siteid = "";
        }
        if (!StringUtils.hasLength(orgid)) {
            siteid = "";
        }
        return assetRepository.findBySiteidAndOrgid(siteid, orgid, pageable);
    }


    @Transactional(readOnly = true)
    public Asset findAssetById(long assetid) {
        return assetRepository.findOne(assetid);
    }

    @Transactional(readOnly = true)
    public boolean existAsset(String assetnum) {
        if (!StringUtils.hasLength(assetnum))
            return false;
        assetnum = assetnum.toUpperCase();
        List<Asset> assets = assetRepository.findByAssetnumAndMoved(assetnum, false);
        if (assets == null)
            return false;
        if (assets.size() > 0)
            return true;
        return false;
    }


    @CacheEvict(value = "assets", key = "#asset.specclass+ '-'+ #asset.classstructureid +'findBySpecAndClass'")
    @Transactional
    public Asset saveAsset(Asset asset) {
        asset.setAssetnum(asset.getAssetnum().toUpperCase());
        asset.setSiteid(asset.getSiteid().toUpperCase());
        asset.setMoved(false);
        return assetRepository.save(asset);
    }


    @Transactional
    public void deleteAsset(long assetuid) {

    }

    @Transactional(readOnly = true)
    public List<AssetMeterSet> findAssetMeterByAssetnum(String assetnum,
                                                        String siteid) {
        if (!StringUtils.hasLength(siteid)) {
            siteid = "";
        }

        List<AssetMeterSet> AssetMeterSets = new ArrayList<AssetMeterSet>();
        List<Object[]> list = assetMeterRepository.findByAssetnumAndSiteid(assetnum, siteid);
        for (Object[] obj : list) {
            AssetMeterSet assetMeterSet = new AssetMeterSet();
            if (obj[0] instanceof AssetMeter) {
                assetMeterSet.setAssetMeter((AssetMeter) obj[0]);
            }
            if (obj[1] instanceof Meter) {
                assetMeterSet.setMeter((Meter) obj[1]);
            }
            AssetMeterSets.add(assetMeterSet);
        }
        return AssetMeterSets;
    }


    @Transactional(readOnly = true)
    public DataSet<AssetMeterSet> findAssetMeterSetsWithDatatablesCriterias(
            DatatablesCriterias criterias, String assetnum, String siteid) {

        List<AssetMeterSet> assetMeterSets = jpaAssetMeterRepository.findAssetWithDatatablesCriterias(criterias, assetnum, siteid);
        Long count = jpaAssetMeterRepository.getTotalCount(assetnum, siteid);
        Long countFiltered = jpaAssetMeterRepository.getFilteredCount(criterias, assetnum, siteid);

        return new DataSet<AssetMeterSet>(assetMeterSets, count, countFiltered);
    }

    @Transactional(readOnly = true)
    public AssetMeter findAssetMeterById(Integer assetmeterid) {
        return assetMeterRepository.findOne(assetmeterid);
    }

    @Transactional(readOnly = true)
    public boolean existAssetMeter(String assetnum, String metername,
                                   String siteid) {

        List<AssetMeter> list = assetMeterRepository.findByAssetnumAndMeternameAndSiteid(assetnum, metername, siteid);
        if (list != null && list.size() > 0)
            return true;
        return false;
    }

    @Transactional(readOnly = true)

    public AssetMeter saveAssetMeter(AssetMeter assetMeter) {
        return assetMeterRepository.save(assetMeter);
    }


    @Transactional
    public void deleteAssetMeter(int assetmeterid) {
        assetMeterRepository.delete(assetmeterid);
    }


    @Transactional(readOnly = true)
    public List<AssetSpecSet> findAssetSpecByAssetnum(String assetnum,
                                                      String siteid) {
        if (!StringUtils.hasLength(siteid)) {
            siteid = "";
        }

        List<AssetSpecSet> assetSpecSets = new ArrayList<AssetSpecSet>();
        Page<Object[]> list = assetSpecRepository.findByAssetnumAndSiteid(assetnum, siteid, new PageRequest(0, 10));

        for (Object[] obj : list.getContent()) {
            AssetSpecSet assetSpecSet = new AssetSpecSet();
            if (obj[0] instanceof AssetSpec) {
                assetSpecSet.setAssetSpec((AssetSpec) obj[0]);
            }
            if (obj[1] instanceof AssetAttribute) {
                assetSpecSet.setAssetAttribute((AssetAttribute) obj[1]);
            }
            assetSpecSets.add(assetSpecSet);
        }
        return assetSpecSets;
    }


    @Transactional(readOnly = true)
    public DataTablesResponse<AssetSpecSet> findAssetSpecByAssetnum(
            String assetnum, String siteid, DataTablesRequestParams params) {
        DataTablesResponse<AssetSpecSet> result = new DataTablesResponse<AssetSpecSet>();

        // 排序方式和排序列
        Direction sortDir = params.getsSortDir_0().equals("asc") ? Direction.ASC : Direction.DESC;
        String sortColName = params.getsColumns().split(",")[params.getiSortCol_0()];
        Sort sort = new Sort(sortDir, sortColName);
        // 当前页
        int pageNumber = (int) Math.ceil(params.getiDisplayStart() / params.getiDisplayLength());

        //结果对象数组,需要转换为对象
        Page<Object[]> list = assetSpecRepository.findByAssetnumAndSiteid(assetnum, siteid, (new PageRequest(
                pageNumber, params.getiDisplayLength())));
        List<AssetSpecSet> assetSpecSets = new ArrayList<AssetSpecSet>();
        for (Object[] obj : list.getContent()) {
            AssetSpecSet assetSpecSet = new AssetSpecSet();
            for (Object o : obj) {
                if (o instanceof Asset) {
                    assetSpecSet.setAsset((Asset) o);
                }
                if (o instanceof AssetSpec) {
                    assetSpecSet.setAssetSpec((AssetSpec) o);
                }
                if (o instanceof AssetAttribute) {
                    assetSpecSet.setAssetAttribute((AssetAttribute) o);
                }
                if (o instanceof ClassSpec) {
                    assetSpecSet.setClassSpec((ClassSpec) o);
                }
                if (o instanceof MeasureUnit) {
                    assetSpecSet.setMeasureUnit((MeasureUnit) o);
                }

            }
            assetSpecSets.add(assetSpecSet);
        }
        //总条数
        //long count = assetSpecRepository.countAssetSpecSetByAssetnumAndSiteid(assetnum, siteid);

        List<AssetSpecSet> data = assetSpecSets;
        result.setsEcho(params.getsEcho());
        result.setiTotalDisplayRecords(list.getTotalElements());
        result.setiTotalRecords(list.getTotalElements());
        result.setAaData(data);

        return result;
    }


    @Transactional(readOnly = true)
    public boolean existAssetSpec(String assetnum, String assetattrid,
                                  String siteid) {
        List<AssetSpec> list = assetSpecRepository.findByAssetnumAndAssetattridAndSiteid(assetnum, assetattrid, siteid);
        if (list != null && list.size() > 0)
            return true;
        return false;
    }


    @Transactional
    public AssetSpec saveAssetSpec(AssetSpec assetSpec) {
        assetSpec = assetSpecRepository.save(assetSpec);
        return assetSpec;
    }

    @Transactional
    public void batchSaveAssetSpec(String classstructureid, String siteid) {

        classStructureRepository.findByClassstructureidAndSiteid(classstructureid, siteid);

    }

    @Transactional
    public void deleteAssetSpec(int assetspecid) {
        assetSpecRepository.delete(assetspecid);
    }

    @Transactional(readOnly = true)
    public Asset findByTagId(String tagid) {
        List<Asset> list = assetRepository.findByTagId(tagid);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }


    @Override
    public List<Asset> findByLikeLocationAndSpecclassAndClassstructureidAndSiteid(String location, String specclass, String classstructureid, String siteid) {
        if (!StringUtils.hasLength(siteid)) {
            siteid = "";
        }
        return assetRepository.findByLikeLocationAndSpecclassAndClassstructureidAndSiteid(location, specclass, classstructureid, siteid);
    }

    @Override
    public Page<Asset> findByLikeLocationAndSpecclassAndSiteidAndOrgid(String location, String specclass, String siteid, String orgid, Pageable pageable) {
        return assetRepository.findByLikeLocationAndSpecclassAndSiteidAndOrgid(location, specclass, siteid, orgid, pageable);
    }

    @Override
    public Page<Asset> findByLikeLocationAndSpecclassAndClassstructureidInAndSiteid(String location, String specclass, String classstructureid, String siteid, String orgid, Pageable pageable) {
        return assetRepository.findByLikeLocationAndSpecclassAndClassstructureidAndSiteid(location, specclass, classstructureid, siteid, orgid, pageable);
    }
}
