package com.energicube.eno.monitor.service.impl;

import com.energicube.eno.common.jsonquery.jpa.request.DataTablesRequestParams;
import com.energicube.eno.common.jsonquery.jpa.response.DataTablesResponse;
import com.energicube.eno.monitor.model.UcTag;
import com.energicube.eno.monitor.model.UclcCondition;
import com.energicube.eno.monitor.repository.UclcConditionRepository;
import com.energicube.eno.monitor.service.UclcConditionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UclcConditionServiceImpl implements UclcConditionService {

    @Autowired
    public UclcConditionRepository uclcConditionRepository;

    @Override
    public List<UclcCondition> findAll() {
        return uclcConditionRepository.findAll();
    }

    @Override
    public UclcCondition findOne(int condid) {
        return uclcConditionRepository.findOne(condid);
    }

    @Override
    @Transactional
    public void save(UclcCondition obj) {
        uclcConditionRepository.save(obj);
    }

    @Override
    @Transactional
    public void edit(UclcCondition obj) {
        uclcConditionRepository.saveAndFlush(obj);
    }

    @Override
    @Transactional
    public void del(int id) {
        uclcConditionRepository.delete(id);
    }

    @Override
    public DataTablesResponse<UclcCondition> findAllCondiotionToDataTables(DataTablesRequestParams params) {

        // 创建DataTable数据格式对象
        DataTablesResponse<UclcCondition> result = new DataTablesResponse<UclcCondition>();

        // 排序方式和排序列
        Direction sortDir = params.getsSortDir_0().equals("asc") ? Direction.ASC : Direction.DESC;
        String sortColName = params.getsColumns().split(",")[params.getiSortCol_0()];
        Sort sort = new Sort(sortDir, sortColName);
        // 当前页
        int pageNumber = (int) Math.ceil(params.getiDisplayStart() / params.getiDisplayLength());

        Page<UclcCondition> page = uclcConditionRepository.findAll(new PageRequest(pageNumber, params.getiDisplayLength(), sort));

        List<UclcCondition> data = page.getContent();
        result.setsEcho(params.getsEcho());
        result.setiTotalDisplayRecords(page.getTotalElements());
        result.setiTotalRecords(page.getTotalElements());
        result.setAaData(data);

        return result;
    }

    @Override
    public List<UcTag> findAllUcTagNames() {
        return uclcConditionRepository.findAllUcTagNames();
    }
}
