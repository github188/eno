package com.energicube.eno.asset.service.impl;

import com.energicube.eno.asset.model.MeasureUnit;
import com.energicube.eno.asset.repository.MeasureUnitRepository;
import com.energicube.eno.asset.service.MeasureUnitService;
import com.energicube.eno.common.jsonquery.jpa.request.DataTablesRequestParams;
import com.energicube.eno.common.jsonquery.jpa.response.DataTablesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MeasureUnitServiceImpl implements MeasureUnitService {

    private MeasureUnitRepository measureUnitRepository;

    @Autowired
    public MeasureUnitServiceImpl(MeasureUnitRepository measureUnitRepository) {
        this.measureUnitRepository = measureUnitRepository;
    }

    @Transactional(readOnly = true)
    public List<MeasureUnit> getMeasureUnits() {
        return measureUnitRepository.findAll();
    }


    @Transactional(readOnly = true)
    public Page<MeasureUnit> findAllMeasureUnit(Pageable pageable) {
        return measureUnitRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public DataTablesResponse<MeasureUnit> findMeasureUnitToDataTables(
            DataTablesRequestParams params) {

        DataTablesResponse<MeasureUnit> result = new DataTablesResponse<MeasureUnit>();

        // 排序方式和排序列
        Direction sortDir = params.getsSortDir_0().equals("asc") ? Direction.ASC
                : Direction.DESC;
        String sortColName = params.getsColumns().split(",")[params
                .getiSortCol_0()];
        Sort sort = new Sort(sortDir, sortColName);
        // 当前页
        int pageNumber = (int) Math.ceil(params.getiDisplayStart()
                / params.getiDisplayLength());

        Page<MeasureUnit> page = measureUnitRepository.findAll(new PageRequest(
                pageNumber, params.getiDisplayLength(), sort));

        List<MeasureUnit> data = page.getContent();
        result.setsEcho(params.getsEcho());
        result.setiTotalDisplayRecords(page.getTotalElements());
        result.setiTotalRecords(page.getTotalElements());
        result.setAaData(data);

        return result;
    }

    @Transactional(readOnly = true)
    public boolean existMeasureUnit(String measureunitid) {
        List<MeasureUnit> list = measureUnitRepository.findByMeasureunitid(measureunitid);
        if (list.size() > 0)
            return true;
        return false;
    }

    @Transactional
    public MeasureUnit saveMeasureUnit(MeasureUnit measureUnit) {
        measureUnit.setMeasureunitid(measureUnit.getMeasureunitid().trim().toUpperCase());
        return measureUnitRepository.save(measureUnit);
    }

    @Transactional
    public void deleteMeasureUnit(long measureunituid) {
        if (measureunituid > 0) {
            measureUnitRepository.delete(measureunituid);
        } else {
            throw new NullPointerException("计量单位ID不能为空");
        }

    }


}
