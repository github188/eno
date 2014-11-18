package com.energicube.eno.monitor.service.impl;

import com.energicube.eno.common.jsonquery.jpa.request.DataTablesRequestParams;
import com.energicube.eno.common.jsonquery.jpa.response.DataTablesResponse;
import com.energicube.eno.monitor.model.UclcLinkage;
import com.energicube.eno.monitor.repository.UclcLinkageRepository;
import com.energicube.eno.monitor.service.UclcLinkageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UclcLinkageServiceImpl implements UclcLinkageService {

    @Autowired
    public UclcLinkageRepository uclcLinkageRepository;

    @Override
    public List<UclcLinkage> findAll() {
        return uclcLinkageRepository.findAll();
    }

    @Override
    public UclcLinkage findOne(long id) {
        UclcLinkage age = uclcLinkageRepository.findBylinkageid(id);
        return age;
    }

    @Override
    @Transactional
    public void save(UclcLinkage obj) {
        uclcLinkageRepository.save(obj);
    }

    @Override
    @Transactional
    public void update(UclcLinkage obj) {
        uclcLinkageRepository.saveAndFlush(obj);
    }

    @Override
    @Transactional
    public void del(long id) {
        uclcLinkageRepository.delete(id);
    }

    @Override
    public DataTablesResponse<UclcLinkage> findAllLinkAllToDataTables(DataTablesRequestParams params) {

        // 创建DataTable数据格式对象
        DataTablesResponse<UclcLinkage> result = new DataTablesResponse<UclcLinkage>();

        // 排序方式和排序列
        Direction sortDir = params.getsSortDir_0().equals("asc") ? Direction.ASC : Direction.DESC;
        String sortColName = params.getsColumns().split(",")[params.getiSortCol_0()];
        Sort sort = new Sort(sortDir, sortColName);
        // 当前页
        int pageNumber = (int) Math.ceil(params.getiDisplayStart() / params.getiDisplayLength());

        Page<UclcLinkage> page = uclcLinkageRepository.findAll(new PageRequest(pageNumber, params.getiDisplayLength(), sort));

        List<UclcLinkage> data = page.getContent();
        result.setsEcho(params.getsEcho());
        result.setiTotalDisplayRecords(page.getTotalElements());
        result.setiTotalRecords(page.getTotalElements());
        result.setAaData(data);

        return result;
    }
}
