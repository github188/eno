package com.energicube.eno.asset.service.impl;

import com.energicube.eno.asset.model.Companies;
import com.energicube.eno.asset.repository.CompaniesRepository;
import com.energicube.eno.asset.repository.jpa.JpaCompaniesRepository;
import com.energicube.eno.asset.service.CompaniesService;
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
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class CompaniesServiceImpl implements CompaniesService {

    private CompaniesRepository companiesRepository;
    private JpaCompaniesRepository jpaCompaniesRepository;

    @Autowired
    public CompaniesServiceImpl(CompaniesRepository companiesRepository,
                                JpaCompaniesRepository jpaCompaniesRepository) {
        this.companiesRepository = companiesRepository;
        this.jpaCompaniesRepository = jpaCompaniesRepository;
    }

    @Transactional(readOnly = true)
    public boolean existCompany(String company) {
        List<Companies> list = companiesRepository.findByCompany(company);
        if (list != null && list.size() > 0)
            return true;
        return false;
    }

    @Transactional(readOnly = true)
    public boolean existBranch(String parentCompany, String company) {
        List<Companies> list = companiesRepository
                .findByParentcompanyAndCompany(parentCompany, company);
        if (list != null && list.size() > 0)
            return true;
        return false;
    }

    @Transactional
    public Companies saveCompanies(Companies companies) {
        companies.setCompany(companies.getCompany().toUpperCase());
        return companiesRepository.save(companies);
    }

    @Transactional(readOnly = true)
    public DataTablesResponse<Companies> findCompanies(String type,
                                                       DataTablesRequestParams params) {
        DataTablesResponse<Companies> result = new DataTablesResponse<Companies>();

        // 排序方式和排序列
        Direction sortDir = params.getsSortDir_0().equals("asc") ? Direction.ASC
                : Direction.DESC;
        String sortColName = params.getsColumns().split(",")[params
                .getiSortCol_0()];
        Sort sort = new Sort(sortDir, sortColName);
        // 当前页
        int pageNumber = (int) Math.ceil(params.getiDisplayStart()
                / params.getiDisplayLength());

        Pageable pageable = new PageRequest(pageNumber,
                params.getiDisplayLength(), sort);
        Page<Companies> page = null;
        if (StringUtils.hasLength(type)) {
            page = companiesRepository.findByType(type, pageable);
        } else {
            page = companiesRepository.findAll(pageable);
        }

        List<Companies> data = page.getContent();
        result.setsEcho(params.getsEcho());
        result.setiTotalDisplayRecords(page.getTotalElements());
        result.setiTotalRecords(page.getTotalElements());
        result.setAaData(data);

        return result;
    }

    @Transactional(readOnly = true)
    public DataTablesResponse<Companies> findBranchs(String company,
                                                     DataTablesRequestParams params) {
        DataTablesResponse<Companies> result = new DataTablesResponse<Companies>();

        // 排序方式和排序列
        Direction sortDir = params.getsSortDir_0().equals("asc") ? Direction.ASC
                : Direction.DESC;
        String sortColName = params.getsColumns().split(",")[params
                .getiSortCol_0()];
        Sort sort = new Sort(sortDir, sortColName);
        // 当前页
        int pageNumber = (int) Math.ceil(params.getiDisplayStart()
                / params.getiDisplayLength());

        Pageable pageable = new PageRequest(pageNumber,
                params.getiDisplayLength(), sort);
        Page<Companies> page = companiesRepository.findByParentcompany(company, pageable);
        List<Companies> data = page.getContent();
        result.setsEcho(params.getsEcho());
        result.setiTotalDisplayRecords(page.getTotalElements());
        result.setiTotalRecords(page.getTotalElements());
        result.setAaData(data);

        return result;
    }

    @Transactional(readOnly = true)
    public Companies findOne(long companiesid) {
        return companiesRepository.findOne(companiesid);
    }


    @Transactional
    public void deleteCompanies(long companiesid) throws Exception {
        Companies companies = companiesRepository.findOne(companiesid);
        if (companies != null) {
            if (existBranch(companies.getCompany(), "")) {
                throw new Exception("公司存在下级公司");
            } else {
                companiesRepository.delete(companies);
            }
        }
    }

    @Transactional
    public void deleteBranch(long companiesid) throws Exception {
        Companies companies = companiesRepository.findOne(companiesid);
        companies.setParentcompany("");
        companiesRepository.save(companies);
    }

    @Transactional(readOnly = true)
    public DataSet<Companies> findCompaniesWithDatatablesCriterias(
            DatatablesCriterias criterias) {
        List<Companies> locations = jpaCompaniesRepository.findCompaniesWithDatatablesCriterias(criterias);
        Long count = jpaCompaniesRepository.getTotalCount();
        Long countFiltered = jpaCompaniesRepository.getFilteredCount(criterias);

        return new DataSet<Companies>(locations, count, countFiltered);
    }


}
