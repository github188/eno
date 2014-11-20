package com.energicube.eno.monitor.service.impl;

import com.energicube.eno.common.jsonquery.jpa.request.DataTablesRequestParams;
import com.energicube.eno.common.jsonquery.jpa.response.DataTablesResponse;
import com.energicube.eno.monitor.model.UclcCell;
import com.energicube.eno.monitor.repository.UclcCellRepository;
import com.energicube.eno.monitor.service.UclcCellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UclcCellServiceImpl implements UclcCellService {

    @Autowired
    public UclcCellRepository uclcCellRepository;

    @Override
    public List<UclcCell> findAll() {
        return uclcCellRepository.findAll();
    }

    @Override
    public UclcCell findOne(int condid) {
        return uclcCellRepository.findOne(condid);
    }

    @Override
    @Transactional
    public void save(UclcCell obj) {
        uclcCellRepository.save(obj);
    }

    @Override
    @Transactional
    public void edit(UclcCell obj) {
        uclcCellRepository.saveAndFlush(obj);
    }

    @Override
    @Transactional
    public void del(int id) {
        uclcCellRepository.delete(id);
    }

    @Override
    public DataTablesResponse<UclcCell> findAllCellToDataTables(DataTablesRequestParams params) {

        // 创建DataTable数据格式对象
        DataTablesResponse<UclcCell> result = new DataTablesResponse<UclcCell>();

        // 排序方式和排序列
        Direction sortDir = params.getsSortDir_0().equals("asc") ? Direction.ASC : Direction.DESC;
        String sortColName = params.getsColumns().split(",")[params.getiSortCol_0()];
        Sort sort = new Sort(sortDir, sortColName);
        // 当前页
        int pageNumber = (int) Math.ceil(params.getiDisplayStart() / params.getiDisplayLength());

        Page<UclcCell> page = uclcCellRepository.findAll(new PageRequest(pageNumber, params.getiDisplayLength(), sort));

        List<UclcCell> data = page.getContent();
        result.setsEcho(params.getsEcho());
        result.setiTotalDisplayRecords(page.getTotalElements());
        result.setiTotalRecords(page.getTotalElements());
        result.setAaData(data);

        return result;
    }

}
