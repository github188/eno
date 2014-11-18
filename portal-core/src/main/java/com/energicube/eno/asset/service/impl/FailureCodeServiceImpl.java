package com.energicube.eno.asset.service.impl;

import com.energicube.eno.asset.model.FailureCode;
import com.energicube.eno.asset.model.FailureCodeType;
import com.energicube.eno.asset.model.FailureList;
import com.energicube.eno.asset.model.FailureListSet;
import com.energicube.eno.asset.repository.FailureCodeRepository;
import com.energicube.eno.asset.repository.FailureListRepository;
import com.energicube.eno.asset.repository.jpa.JpaFailureCodeRepository;
import com.energicube.eno.asset.service.FailureCodeService;
import com.energicube.eno.common.jsonquery.jpa.request.DataTablesRequestParams;
import com.energicube.eno.common.jsonquery.jpa.response.DataTablesResponse;
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

import java.util.ArrayList;
import java.util.List;

@Service
public class FailureCodeServiceImpl implements FailureCodeService {

    private FailureCodeRepository failureCodeRepository;
    private FailureListRepository failureListRepository;
    private JpaFailureCodeRepository jpaFailureCodeRepository;

    @Autowired
    public FailureCodeServiceImpl(FailureCodeRepository failureCodeRepository,
                                  FailureListRepository failureListRepository,
                                  JpaFailureCodeRepository jpaFailureCodeRepository) {
        this.failureCodeRepository = failureCodeRepository;
        this.failureListRepository = failureListRepository;
        this.jpaFailureCodeRepository = jpaFailureCodeRepository;
    }


    @Transactional(readOnly = true)
    public Page<FailureCode> findAllFailureCode(Pageable pageable) {
        return failureCodeRepository.findByList(pageable);
    }


    @Transactional(readOnly = true)
    public DataSet<FailureCode> findFailureCodesWithDatatablesCriterias(
            DatatablesCriterias criterias) {
        List<FailureCode> failureCodes = jpaFailureCodeRepository.findFailureCodeWithDatatablesCriterias(criterias);

        Long count = jpaFailureCodeRepository.getTotalCount();
        Long countFiltered = jpaFailureCodeRepository.getFilteredCount(criterias);

        return new DataSet<FailureCode>(failureCodes, count, countFiltered);
    }


    @Transactional(readOnly = true)
    public boolean existFailureCode(String failurecode) {
        List<FailureCode> result = failureCodeRepository
                .findByFailurecode(failurecode);
        if (result != null && result.size() > 1) {
            return true;
        }
        return false;
    }

    @Transactional
    public FailureList saveFailureList(FailureCode failureCode) {

        failureCodeRepository.save(failureCode);

        FailureList failureList = new FailureList();
        failureList.setFailurecode(failureCode.getFailurecode());
        failureList.setType(null);
        failureList.setParent(null);
        failureList.setOrgid(failureCode.getOrgid());
        failureList = failureListRepository.save(failureList);

        return failureList;
    }

    @Transactional(readOnly = true)
    public FailureCode findFailureCodeByFailurelistId(Long failurelistId) {
        FailureList failureList = failureListRepository.findOne(failurelistId);
        if (failureList != null) {
            String failurecode = failureList.getFailurecode();
            List<FailureCode> failureCodes = failureCodeRepository
                    .findByFailurecode(failurecode);
            if (failureCodes != null && failureCodes.size() > 0) {
                return failureCodes.get(0);
            }
        }
        return null;
    }

    @Transactional(readOnly = true)
    public FailureCode findFailureCode(long failurecodeid) {
        return failureCodeRepository.findOne(failurecodeid);
    }


    @Transactional(readOnly = true)
    public FailureList findFailureList(Long failurelistId) {
        return failureListRepository.findOne(failurelistId);
    }

    @Transactional(readOnly = true)
    public FailureCode findFailureCodeBycode(String failurecode) {
        List<FailureCode> failureCodes = failureCodeRepository
                .findByFailurecode(failurecode);
        if (failureCodes != null && failureCodes.size() > 0) {
            return failureCodes.get(0);
        }
        return null;
    }

    @Transactional(readOnly = true)
    public FailureList findFailureListByFailurecode(String failurecode) {
        return failureListRepository.findByFailurecode(failurecode);
    }


    private DataTablesResponse<FailureListSet> findFailureListSet(long failurelist, DataTablesRequestParams params, String failurecodetype) {

        DataTablesResponse<FailureListSet> result = new DataTablesResponse<FailureListSet>();

        // 排序方式和排序列
        Direction sortDir = params.getsSortDir_0().equals("asc") ? Direction.ASC : Direction.DESC;
        String sortColName = params.getsColumns().split(",")[params.getiSortCol_0()];
        Sort sort = new Sort(sortDir, sortColName);
        // 当前页
        int pageNumber = (int) Math.ceil(params.getiDisplayStart() / params.getiDisplayLength());

        //结果对象数组,需要转换为对象
        List<Object[]> list = failureListRepository.findFailureListSetByParentAndTypes((new PageRequest(
                pageNumber, params.getiDisplayLength())), failurelist, failurecodetype);
        List<FailureListSet> failureListSets = new ArrayList<FailureListSet>();
        for (Object[] obj : list) {
            FailureListSet failureListSet = new FailureListSet();
            for (Object o : obj) {
                if (o instanceof FailureList) {
                    failureListSet.setFailureList((FailureList) o);
                }
                if (o instanceof FailureCode) {
                    failureListSet.setFailureCode((FailureCode) o);
                }

            }
            failureListSets.add(failureListSet);
        }
        //总条数
        long count = failureListRepository.findFailureListSetByParentAndTypesCount(failurelist, failurecodetype);

        List<FailureListSet> data = failureListSets;
        result.setsEcho(params.getsEcho());
        result.setiTotalDisplayRecords(count);
        result.setiTotalRecords(count);
        result.setAaData(data);

        return result;
    }


    @Transactional(readOnly = true)
    public DataTablesResponse<FailureListSet> findProblemList(long failurelist, DataTablesRequestParams params) {
        return findFailureListSet(failurelist, params, FailureCodeType.PROBLEM.getDescription());
    }

    @Transactional(readOnly = true)
    public DataTablesResponse<FailureListSet> findCauseList(long failurelist, DataTablesRequestParams params) {
        return findFailureListSet(failurelist, params, FailureCodeType.CAUSE.getDescription());
    }

    @Transactional(readOnly = true)
    public DataTablesResponse<FailureListSet> findRemedyList(long failurelist, DataTablesRequestParams params) {
        return findFailureListSet(failurelist, params, FailureCodeType.REMEDY.getDescription());
    }

    public FailureCode saveFailureCode(FailureCode failureCode,
                                       FailureCodeType type) {

        return null;
    }

    public void saveFailureCodes(List<FailureCode> failureCodes,
                                 FailureCodeType type) {

    }

}
