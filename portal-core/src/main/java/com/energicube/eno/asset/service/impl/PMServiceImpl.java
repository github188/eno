package com.energicube.eno.asset.service.impl;

import com.energicube.eno.asset.common.Constants;
import com.energicube.eno.asset.model.*;
import com.energicube.eno.asset.repository.*;
import com.energicube.eno.asset.repository.jpa.JpaPMRepository;
import com.energicube.eno.asset.service.IdGenService;
import com.energicube.eno.asset.service.PMService;
import com.energicube.eno.common.jsonquery.jpa.request.DataTablesRequestParams;
import com.energicube.eno.common.jsonquery.jpa.response.DataTablesResponse;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import org.joda.time.LocalDateTime;
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
public class PMServiceImpl implements PMService {

    private PMRepository pmRepository;
    private PMAncestorRepository pmAncestorRepository;
    private PMMeterRepository pmMeterRepository;
    private PMSeasonsRepository pmSeasonsRepository;
    private PMSequenceRepository pmSequenceRepository;
    private IdGenService idGenService;

    private JpaPMRepository jpaPMRepository;

    @Autowired
    public PMServiceImpl(PMRepository pmRepository,
                         PMAncestorRepository pmAncestorRepository,
                         PMMeterRepository pmMeterRepository,
                         PMSeasonsRepository pmSeasonsRepository,
                         PMSequenceRepository pmSequenceRepository,
                         IdGenService idGenService,
                         JpaPMRepository jpaPMRepository) {
        this.pmRepository = pmRepository;
        this.pmAncestorRepository = pmAncestorRepository;
        this.pmMeterRepository = pmMeterRepository;
        this.pmSeasonsRepository = pmSeasonsRepository;
        this.pmSequenceRepository = pmSequenceRepository;
        this.idGenService = idGenService;
        this.jpaPMRepository = jpaPMRepository;
    }


    @Transactional(readOnly = true)
    public PM initPM() {
        PM pm = new PM();
        pm.setStatus("DRAFT");

        String newid = idGenService.generateNewId(Constants.PMNUM, "PM", 4, 0, "");
        pm.setPmnum(newid);
        return pm;
    }


    @Transactional(readOnly = true)
    public boolean existPM(String pmnum, String siteid) throws Exception {
        List<PM> list = pmRepository.findByPmnumAndSiteid(pmnum, siteid);
        if (list != null && list.size() > 0)
            return true;
        return false;
    }

    @Transactional
    public PM savePM(PM pm) throws Exception {
        boolean isnew = false;
        if (pm.getPmid() == 0) {
            pm.setStatus("DRAFT");
            isnew = true;
        }
        pm.setChangedate(LocalDateTime.now());
        if (isnew) {
            pm = pmRepository.save(pm);
            PMAncestor pmAncestor = new PMAncestor();
            pmAncestor.setAncestor(pm.getPmnum());
            pmAncestor.setPmnum(pm.getPmnum());
            pmAncestor.setHierarchylevels(0);
            pmAncestor.setSiteid(pm.getSiteid());
            pmAncestor.setOrgid(pm.getOrgid());
            pmAncestorRepository.save(pmAncestor);

            idGenService.updateNewId(Constants.PMNUM);

            return pm;
        } else {
            PM originalPM = pmRepository.findOne(pm.getPmid());
            originalPM.setAssetnum(pm.getAssetnum());
            originalPM.setLocation(pm.getLocation());
            originalPM.setLead(pm.getLead());
            originalPM.setLeadtime(pm.getLeadtime());
            originalPM.setPmcounter(pm.getPmcounter());
            originalPM.setJpseqinuse(pm.getJpseqinuse());
            originalPM.setHaschildren(pm.getHaschildren());
            originalPM.setJpnum(pm.getJpnum());
            originalPM.setWorktype(pm.getWorktype());
            originalPM.setWostatus(pm.getWostatus());
            originalPM.setPriority(pm.getPriority());
            originalPM.setLaststartdate(pm.getLaststartdate());
            originalPM.setLastcompdate(pm.getLastcompdate());
            originalPM.setInterruptible(pm.getInterruptible());
            originalPM.setSupervisor(pm.getSupervisor());
            originalPM.setCrewid(pm.getCrewid());
            originalPM.setLead(pm.getLead());
            originalPM.setUsefrequency(pm.getUsefrequency());
            originalPM.setParentchgsstatus(pm.getParentchgsstatus());
            return pmRepository.save(originalPM);
        }
    }


    @Transactional
    public PM savePMByFrequency(PM pm) throws Exception {
        if (pm.getPmid() == 0 && !StringUtils.hasLength(pm.getStatus())) {
            pm.setStatus("DRAFT");
            return savePM(pm);
        } else {
            PM originalPM = pmRepository.findOne(pm.getPmid());
            originalPM.setUsetargetdate(pm.getUsetargetdate());
            originalPM.setPmassetwogen(pm.getPmassetwogen());
            originalPM.setFrequency(pm.getFrequency());
            originalPM.setFrequnit(pm.getFrequnit());
            originalPM.setAlertlead(pm.getAlertlead());
            originalPM.setNextdate(pm.getNextdate());
            originalPM.setExtdate(pm.getExtdate());
            originalPM.setAdjnextdue(pm.getAdjnextdue());

            return pmRepository.save(originalPM);
        }
    }

    @Transactional
    public PM savePMBySeasons(PM pm) throws Exception {
        if (pm.getPmid() == 0 && !StringUtils.hasLength(pm.getStatus())) {
            pm.setStatus("DRAFT");
            return savePM(pm);
        } else {
            PM originalPM = pmRepository.findOne(pm.getPmid());
            originalPM.setSunday(pm.getSunday());
            originalPM.setMonday(pm.getMonday());
            originalPM.setTuesday(pm.getTuesday());
            originalPM.setWednesday(pm.getWednesday());
            originalPM.setThursday(pm.getThursday());
            originalPM.setFirstdate(pm.getFirstdate());
            originalPM.setSaturday(pm.getSaturday());
            originalPM.setTargstarttime(pm.getTargstarttime());
            originalPM.setSchedearly(pm.getSchedearly());
            return pmRepository.save(originalPM);
        }
    }

    @Transactional
    public PM savePMBySequence(PM pm) throws Exception {
        if (pm.getPmid() == 0 && !StringUtils.hasLength(pm.getStatus())) {
            pm.setStatus("DRAFT");
            return savePM(pm);
        } else {
            PM originalPM = pmRepository.findOne(pm.getPmid());
            originalPM.setJpnum(pm.getJpnum());

            return pmRepository.save(originalPM);
        }
    }

    @Transactional
    public PM savePMByAncestor(PM pm) throws Exception {
        if (pm.getPmid() == 0 && !StringUtils.hasLength(pm.getStatus())) {
            pm.setStatus("DRAFT");
            return savePM(pm);
        } else {
            PM originalPM = pmRepository.findOne(pm.getPmid());
            originalPM.setParent(pm.getParent());


            return pmRepository.save(originalPM);
        }
    }

    @Transactional(readOnly = true)
    public PM findPMById(long pmid) throws Exception {
        PM pm = null;
        if (pmid > 0) {
            pm = pmRepository.findOne(pmid);
        } else {
            pm = new PM();
        }
        return pm;
    }

    @Transactional(readOnly = true)
    public PM findPMById(String pmnum, String siteid) throws Exception {
        List<PM> list = pmRepository.findByPmnumAndSiteid(pmnum, siteid);
        if (list != null && list.size() > 0)
            return list.get(0);
        return null;
    }

    @Transactional
    public void deletePM(long pmid) throws Exception {
        PM pm = null;
        if (pmid > 0) {
            pm = pmRepository.findOne(pmid);
            if (pm.getStatus().equals("DRAFT")) {
                pmRepository.delete(pm);
            } else if (pm.getStatus().equals("ACTIVE")) {
                pm.setStatus("INACTIVE");
                pmRepository.save(pm);
            }
        }
    }

    @Transactional(readOnly = true)
    public DataTablesResponse<PM> findPMList(String siteid,
                                             DataTablesRequestParams params) throws Exception {
        DataTablesResponse<PM> result = new DataTablesResponse<PM>();
        // 排序方式和排序列
        Direction sortDir = params.getsSortDir_0().equals("asc") ? Direction.ASC : Direction.DESC;
        String sortColName = params.getsColumns().split(",")[params.getiSortCol_0()];
        Sort sort = new Sort(sortDir, sortColName);
        // 当前页
        int pageNumber = (int) Math.ceil(params.getiDisplayStart() / params.getiDisplayLength());
        Pageable pageable = new PageRequest(pageNumber, params.getiDisplayLength(), sort);
        Page<PM> page = pmRepository.findBySiteid(siteid, pageable);

        List<PM> data = page.getContent();
        result.setsEcho(params.getsEcho());
        result.setiTotalDisplayRecords(page.getTotalElements());
        result.setiTotalRecords(page.getTotalElements());
        result.setAaData(data);

        return result;
    }


    @Transactional(readOnly = true)
    public DataSet<PM> findPMsWithDatatablesCriterias(
            DatatablesCriterias criterias) {

        List<PM> pms = jpaPMRepository.findPMWithDatatablesCriterias(criterias);
        Long countFiltered = jpaPMRepository.getFilteredCount(criterias);
        Long count = jpaPMRepository.getTotalCount();

        return new DataSet<PM>(pms, count, countFiltered);
    }


    @Transactional(readOnly = true)
    public DataTablesResponse<PM> findPMChildren(String parent, String siteid,
                                                 DataTablesRequestParams params) throws Exception {

        DataTablesResponse<PM> result = new DataTablesResponse<PM>();
        // 排序方式和排序列
        Direction sortDir = params.getsSortDir_0().equals("asc") ? Direction.ASC : Direction.DESC;
        String sortColName = params.getsColumns().split(",")[params.getiSortCol_0()];
        Sort sort = new Sort(sortDir, sortColName);
        // 当前页
        int pageNumber = (int) Math.ceil(params.getiDisplayStart() / params.getiDisplayLength());
        Pageable pageable = new PageRequest(pageNumber, params.getiDisplayLength(), sort);
        Page<PM> page = pmRepository.findByParentAndSiteid(parent, siteid, pageable);

        List<PM> data = page.getContent();
        result.setsEcho(params.getsEcho());
        result.setiTotalDisplayRecords(page.getTotalElements());
        result.setiTotalRecords(page.getTotalElements());
        result.setAaData(data);

        return result;
    }

    @Transactional(readOnly = true)
    public DataTablesResponse<PM> findPMByAsset(String assetnum,
                                                String siteid, DataTablesRequestParams params) throws Exception {
        DataTablesResponse<PM> result = new DataTablesResponse<PM>();
        // 排序方式和排序列
        Direction sortDir = params.getsSortDir_0().equals("asc") ? Direction.ASC : Direction.DESC;
        String sortColName = params.getsColumns().split(",")[params.getiSortCol_0()];
        Sort sort = new Sort(sortDir, sortColName);
        // 当前页
        int pageNumber = (int) Math.ceil(params.getiDisplayStart() / params.getiDisplayLength());
        Pageable pageable = new PageRequest(pageNumber, params.getiDisplayLength(), sort);
        Page<PM> page = pmRepository.findByAssetnumAndSiteid(assetnum, siteid, pageable);

        List<PM> data = page.getContent();
        result.setsEcho(params.getsEcho());
        result.setiTotalDisplayRecords(page.getTotalElements());
        result.setiTotalRecords(page.getTotalElements());
        result.setAaData(data);
        return result;
    }

    @Transactional(readOnly = true)
    public DataTablesResponse<PM> findPMByLocation(String location,
                                                   String siteid, DataTablesRequestParams params) throws Exception {
        DataTablesResponse<PM> result = new DataTablesResponse<PM>();
        // 排序方式和排序列
        Direction sortDir = params.getsSortDir_0().equals("asc") ? Direction.ASC : Direction.DESC;
        String sortColName = params.getsColumns().split(",")[params.getiSortCol_0()];
        Sort sort = new Sort(sortDir, sortColName);
        // 当前页
        int pageNumber = (int) Math.ceil(params.getiDisplayStart() / params.getiDisplayLength());
        Pageable pageable = new PageRequest(pageNumber, params.getiDisplayLength(), sort);
        Page<PM> page = pmRepository.findByLocationAndSiteid(location, siteid, pageable);

        List<PM> data = page.getContent();
        result.setsEcho(params.getsEcho());
        result.setiTotalDisplayRecords(page.getTotalElements());
        result.setiTotalRecords(page.getTotalElements());
        result.setAaData(data);
        return result;
    }

    @Transactional(readOnly = true)
    public PMMeterSet initPMMeter(PM pm) throws Exception {
        PMMeter pmMeter = new PMMeter();
        PMMeterSet pmMeterSet = new PMMeterSet();
        if (pm != null) {
            pmMeter.setPmnum(pm.getPmnum());
            pmMeter.setSiteid(pm.getSiteid());
            pmMeter.setOrgid(pm.getOrgid());
        }
        pmMeterSet.setPm(pm);
        pmMeterSet.setPmMeter(pmMeter);
        return pmMeterSet;
    }

    @Transactional(readOnly = true)
    public boolean existPMMeter(String metername, String pmnum, String siteid)
            throws Exception {
        List<PMMeter> list = pmMeterRepository.findByMeternameAndPmnumAndSiteid(metername, pmnum, siteid);
        if (list != null && list.size() > 0)
            return true;
        return false;
    }

    @Transactional
    public PMMeter savePMMeter(PMMeter pmMeter) throws Exception {
        if (!StringUtils.hasLength(pmMeter.getPmnum())) {
            throw new Exception("PM编号不能为空");
        }
        return pmMeterRepository.save(pmMeter);
    }

    @Transactional(readOnly = true)
    public PMMeter findPMMeterById(long pmmeterid) throws Exception {
        if (pmmeterid == 0) {
            throw new Exception("PM计量器ID不能为0");
        }
        return pmMeterRepository.findOne(pmmeterid);
    }

    @Transactional
    public void deletePMMeter(long pmmeterid) throws Exception {
        if (pmmeterid == 0) {
            throw new Exception("PM计量器ID不能为0");
        }
        pmMeterRepository.delete(pmmeterid);
    }

    @Transactional(readOnly = true)
    public DataTablesResponse<PMMeterSet> findPMMeters(String pmnum,
                                                       String siteid, DataTablesRequestParams params) {

        DataTablesResponse<PMMeterSet> result = new DataTablesResponse<PMMeterSet>();
        // 排序方式和排序列
        //Direction sortDir = params.getsSortDir_0().equals("asc") ? Direction.ASC : Direction.DESC;
        //String sortColName = params.getsColumns().split(",")[params.getiSortCol_0()];
        //Sort sort = new Sort(sortDir, sortColName);
        // 当前页
        int pageNumber = 0, pageSize = 10;
        try {
            pageNumber = (int) Math.ceil(params.getiDisplayStart() / params.getiDisplayLength());
        } catch (Exception e) {

        }
        if (params == null || params.getiDisplayLength() == 0)
            pageSize = 10;
        else
            pageSize = params.getiDisplayLength();

        Pageable pageable = new PageRequest(pageNumber, pageSize);
        Page<Object[]> page = pmMeterRepository.findByPmnum(pmnum, siteid, pageable);

        List<PMMeterSet> data = getPMMeterSetList(page.getContent());
        result.setsEcho(params.getsEcho());
        result.setiTotalDisplayRecords(page.getTotalElements());
        result.setiTotalRecords(page.getTotalElements());
        result.setAaData(data);
        return result;
    }


    /**
     * 转换并返回列表
     */
    private List<PMMeterSet> getPMMeterSetList(List<Object[]> list) {
        List<PMMeterSet> result = new ArrayList<PMMeterSet>();
        for (Object[] obj : list) {
            PMMeterSet pmMeterSet = new PMMeterSet();
            pmMeterSet = parseArrayToPMMeterSet(obj);
            result.add(pmMeterSet);
        }
        return result;
    }

    /**
     * 解析数组对象并返回各个对象
     */
    private PMMeterSet parseArrayToPMMeterSet(Object[] arrObject) {
        PMMeterSet pmMeterSet = new PMMeterSet();
        Object[] objs = arrObject;
        for (Object o : objs) {
            if (o instanceof AssetMeter) {
                pmMeterSet.setAssetMeter((AssetMeter) o);
            } else if (o instanceof LocationMeter) {
                pmMeterSet.setLocationMeter((LocationMeter) o);
            } else if (o instanceof LocMeterReading) {
                pmMeterSet.setLocMeterReading((LocMeterReading) o);
            } else if (o instanceof Meter) {
                pmMeterSet.setMeter((Meter) o);
            } else if (o instanceof PMMeter) {
                pmMeterSet.setPmMeter((PMMeter) o);
            } else if (o instanceof PM) {
                pmMeterSet.setPm((PM) o);
            }
        }
        return pmMeterSet;
    }


    @Transactional(readOnly = true)
    public PMSeasons initPMSeasons(PM pm) throws Exception {
        PMSeasons pmSeasons = new PMSeasons();
        if (pm != null) {
            pmSeasons.setPmnum(pm.getPmnum());
            pmSeasons.setSiteid(pm.getSiteid());
            pmSeasons.setOrgid(pm.getOrgid());
        }
        return pmSeasons;
    }

    @Transactional(readOnly = true)
    public boolean existPMSeasons(PMSeasons pmSeasons) throws Exception {
        List<PMSeasons> list = pmSeasonsRepository.findByDays(pmSeasons.getPmnum(),
                pmSeasons.getSiteid(), pmSeasons.getEndday(), pmSeasons.getEndmonth(),
                pmSeasons.getStartday(), pmSeasons.getStartmonth());
        if (list != null && list.size() > 0)
            return true;
        return false;
    }

    @Transactional
    public PMSeasons savePMSeasons(PMSeasons pmSeasons) throws Exception {

        return pmSeasonsRepository.save(pmSeasons);
    }

    @Transactional(readOnly = true)
    public PMSeasons findPMSeasonsById(long pmseasonsid) throws Exception {
        PMSeasons pmSeasons = new PMSeasons();
        if (pmseasonsid > 0) {
            pmSeasons = pmSeasonsRepository.findOne(pmseasonsid);
        }
        return pmSeasons;
    }

    @Transactional
    public void deletePMSeasons(long pmseasonsid) throws Exception {
        if (pmseasonsid <= 0) {
            throw new IllegalArgumentException("PM季节性日期ID必须大于0");
        }
        pmSeasonsRepository.delete(pmseasonsid);
    }

    @Transactional(readOnly = true)
    public DataTablesResponse<PMSeasons> findPMSeasonsList(String pmnum,
                                                           String siteid, DataTablesRequestParams params) {

        DataTablesResponse<PMSeasons> result = new DataTablesResponse<PMSeasons>();
        // 排序方式和排序列
        Direction sortDir = params.getsSortDir_0().equals("asc") ? Direction.ASC : Direction.DESC;
        String sortColName = params.getsColumns().split(",")[params.getiSortCol_0()];
        Sort sort = new Sort(sortDir, sortColName);
        // 当前页
        int pageNumber = (int) Math.ceil(params.getiDisplayStart() / params.getiDisplayLength());
        Pageable pageable = new PageRequest(pageNumber, params.getiDisplayLength(), sort);
        Page<PMSeasons> page = pmSeasonsRepository.findByPmnumAndSiteid(pmnum, siteid, pageable);

        List<PMSeasons> data = page.getContent();
        result.setsEcho(params.getsEcho());
        result.setiTotalDisplayRecords(page.getTotalElements());
        result.setiTotalRecords(page.getTotalElements());
        result.setAaData(data);
        return result;

    }

    @Transactional(readOnly = true)
    public PMSequenceSet initPMSequenceSet(PM pm) throws Exception {
        PMSequenceSet pmSequenceSet = new PMSequenceSet();
        if (pm != null) {
            PMSequence pmSequence = new PMSequence();
            pmSequence.setPmnum(pm.getPmnum());
            pmSequence.setSiteid(pm.getSiteid());
            pmSequence.setOrgid(pm.getOrgid());
            pmSequence.setInterval(0);
            pmSequenceSet.setPmSequence(pmSequence);
        }
        return pmSequenceSet;
    }

    @Transactional(readOnly = true)
    public boolean existPMSequence(String jpnum, String pmnum, String siteid)
            throws Exception {
        List<PMSequence> list = pmSequenceRepository.findByJpnumAndPmnumAndSiteid(jpnum, pmnum, siteid);
        if (list != null && list.size() > 0)
            return true;
        return false;
    }

    @Transactional
    public PMSequence savePMSequence(PMSequence pmSequence) throws Exception {
        return pmSequenceRepository.save(pmSequence);
    }

    @Transactional(readOnly = true)
    public PMSequence findPMSequenceById(long pmsequenceid) throws Exception {
        if (pmsequenceid <= 0) {
            throw new IllegalArgumentException("PM作业计划序列表ID必须大于0");
        }
        return pmSequenceRepository.findOne(pmsequenceid);
    }

    @Transactional
    public void deletePMSequence(long pmsequenceid) throws Exception {
        if (pmsequenceid <= 0) {
            throw new IllegalArgumentException("PM作业计划序列表ID必须大于0");
        }
        pmSequenceRepository.delete(pmsequenceid);
    }

    @Transactional(readOnly = true)
    public DataTablesResponse<PMSequenceSet> findPMSequenceList(String pmnum,
                                                                String siteid, String orgid, DataTablesRequestParams params) {

        DataTablesResponse<PMSequenceSet> result = new DataTablesResponse<PMSequenceSet>();
        // 排序方式和排序列
        Direction sortDir = params.getsSortDir_0().equals("asc") ? Direction.ASC : Direction.DESC;
        String sortColName = params.getsColumns().split(",")[params.getiSortCol_0()];
        Sort sort = new Sort(sortDir, sortColName);
        // 当前页
        int pageNumber = (int) Math.ceil(params.getiDisplayStart() / params.getiDisplayLength());
        Pageable pageable = new PageRequest(pageNumber, params.getiDisplayLength());

        Page<Object[]> page = pmSequenceRepository.findPMSequences(pmnum, siteid, orgid, pageable);

        List<PMSequenceSet> data = getPMSequenceSetList(page.getContent());
        result.setsEcho(params.getsEcho());
        result.setiTotalDisplayRecords(page.getTotalElements());
        result.setiTotalRecords(page.getTotalElements());
        result.setAaData(data);
        return result;
    }

    /**
     * 转换并返回列表
     */
    private List<PMSequenceSet> getPMSequenceSetList(List<Object[]> list) {
        List<PMSequenceSet> result = new ArrayList<PMSequenceSet>();
        for (Object[] obj : list) {
            PMSequenceSet pmSequenceSet = new PMSequenceSet();
            pmSequenceSet = parseArrayToPMSequenceSet(obj);
            result.add(pmSequenceSet);
        }
        return result;
    }

    /**
     * 解析数组对象并返回各个对象
     */
    private PMSequenceSet parseArrayToPMSequenceSet(Object[] arrObject) {
        PMSequenceSet pmSequenceSet = new PMSequenceSet();
        Object[] objs = arrObject;
        for (Object o : objs) {
            if (o instanceof JobPlan) {
                pmSequenceSet.setJobPlan((JobPlan) o);
            } else if (o instanceof PMSequence) {
                pmSequenceSet.setPmSequence((PMSequence) o);
            }
        }
        return pmSequenceSet;
    }


    @Transactional(readOnly = true)
    public PMAncestor initPMAncestor(PM pm) throws Exception {
        PMAncestor pmAncestor = new PMAncestor();
        if (pm != null) {
            pmAncestor.setPmnum(pm.getPmnum());
            pmAncestor.setHierarchylevels(0);
            pmAncestor.setOrgid(pm.getOrgid());
            pmAncestor.setSiteid(pm.getSiteid());
        }
        return pmAncestor;
    }

    @Transactional(readOnly = true)
    public PMAncestorSet initPMAncestorSet(PM pm) {
        PMAncestor pmAncestor = new PMAncestor();
        if (pm != null) {
            pmAncestor.setPmnum(pm.getPmnum());
            pmAncestor.setHierarchylevels(0);
            pmAncestor.setOrgid(pm.getOrgid());
            pmAncestor.setSiteid(pm.getSiteid());
        }

        PMAncestorSet pmAncestorSet = new PMAncestorSet();
        pmAncestorSet.setPmAncestor(pmAncestor);
        return pmAncestorSet;
    }


    @Transactional(readOnly = true)
    public boolean existParentAncestor(String parent, PM pm) throws Exception {


        return false;
    }

    @Transactional(readOnly = true)
    public boolean existPMAncestor(PMAncestor pmAncestor) throws Exception {
        List<PMAncestor> list = pmAncestorRepository.findByAncestorAndPmnumAndHierarchylevelsAndSiteid(pmAncestor.getAncestor(), pmAncestor.getPmnum(), pmAncestor.getHierarchylevels(), pmAncestor.getSiteid());
        if (list != null && list.size() > 0)
            return true;
        return false;
    }

    @Transactional
    public PMAncestor savePMAncestor(long pmid, PMAncestor pmAncestor) throws Exception {
        PM pm = new PM();
        if (pmid > 0) {
            pm = pmRepository.findOne(pmid);
        }
        pmAncestor.setHierarchylevels(1);
        pmAncestor = pmAncestorRepository.save(pmAncestor);
        pm.setHaschildren(true);
        pmRepository.save(pm);
        return pmAncestor;
    }

    @Transactional(readOnly = true)
    public PMAncestor findPMAncestorById(long pmancestorid) throws Exception {
        if (pmancestorid <= 0) {
            throw new IllegalArgumentException("PM层次结构ID必须大于0");
        }
        return pmAncestorRepository.findOne(pmancestorid);
    }

    @Transactional
    public void deletePMAncestor(long pmancestorid) throws Exception {
        if (pmancestorid <= 0) {
            throw new IllegalArgumentException("PM层次结构ID必须大于0");
        }
        pmAncestorRepository.delete(pmancestorid);
    }

    @Transactional(readOnly = true)
    public DataTablesResponse<PMAncestorSet> findPMAncestorList(String pmnum,
                                                                String siteid, DataTablesRequestParams params) {

        DataTablesResponse<PMAncestorSet> result = new DataTablesResponse<PMAncestorSet>();
        // 排序方式和排序列
        Direction sortDir = params.getsSortDir_0().equals("asc") ? Direction.ASC : Direction.DESC;
        String sortColName = params.getsColumns().split(",")[params.getiSortCol_0()];
        Sort sort = new Sort(sortDir, sortColName);
        // 当前页
        int pageNumber = (int) Math.ceil(params.getiDisplayStart() / params.getiDisplayLength());
        Pageable pageable = new PageRequest(pageNumber, params.getiDisplayLength());

        Page<Object[]> page = pmAncestorRepository.findByPmnumAndSiteid(pmnum, siteid, pageable);

        //PMAncestor
        List<PMAncestorSet> data = getPMAncestorSetList(page.getContent());
        result.setsEcho(params.getsEcho());
        result.setiTotalDisplayRecords(page.getTotalElements());
        result.setiTotalRecords(page.getTotalElements());
        result.setAaData(data);
        return result;
    }


    /**
     * 转换并返回列表
     */
    private List<PMAncestorSet> getPMAncestorSetList(List<Object[]> list) {
        List<PMAncestorSet> result = new ArrayList<PMAncestorSet>();
        for (Object[] obj : list) {
            PMAncestorSet pmAncestorSet = new PMAncestorSet();
            pmAncestorSet = parseArrayToPMAncestorSet(obj);
            result.add(pmAncestorSet);
        }
        return result;
    }

    /**
     * 解析数组对象并返回各个对象
     */
    private PMAncestorSet parseArrayToPMAncestorSet(Object[] arrObject) {
        PMAncestorSet pmAncestorSet = new PMAncestorSet();
        Object[] objs = arrObject;
        for (Object o : objs) {
            if (o instanceof PM) {
                pmAncestorSet.setPm((PM) o);
            } else if (o instanceof PMAncestor) {
                pmAncestorSet.setPmAncestor((PMAncestor) o);
            } else if (o instanceof Asset) {
                pmAncestorSet.setAsset((Asset) o);
            } else if (o instanceof Locations) {
                pmAncestorSet.setLocations((Locations) o);
            }
        }
        return pmAncestorSet;
    }

}
