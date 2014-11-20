package com.energicube.eno.asset.service.impl;

import com.energicube.eno.asset.common.Constants;
import com.energicube.eno.asset.model.MeasurePoint;
import com.energicube.eno.asset.model.Measurement;
import com.energicube.eno.asset.repository.MeasurePointRepository;
import com.energicube.eno.asset.repository.MeasurementRepository;
import com.energicube.eno.asset.repository.jpa.JpaMeasurePointRepository;
import com.energicube.eno.asset.service.IdGenService;
import com.energicube.eno.asset.service.MeasurePointService;
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

import java.util.List;

@Service
public class MeasurePointServiceImpl implements MeasurePointService {


    private MeasurePointRepository measurePointRepository;
    private MeasurementRepository measurementRepository;
    private IdGenService idGenService;
    private JpaMeasurePointRepository jpaMeasurePointRepository;

    @Autowired
    public MeasurePointServiceImpl(MeasurePointRepository measurePointRepository,
                                   MeasurementRepository measurementRepository, IdGenService idGenService,
                                   JpaMeasurePointRepository jpaMeasurePointRepository) {
        this.measurePointRepository = measurePointRepository;
        this.measurementRepository = measurementRepository;
        this.idGenService = idGenService;
        this.jpaMeasurePointRepository = jpaMeasurePointRepository;
    }


    @Transactional(readOnly = true)
    public DataTablesResponse<MeasurePoint> findAllMeasurePoints(
            String siteid, String orgid, DataTablesRequestParams params) {
        DataTablesResponse<MeasurePoint> result = new DataTablesResponse<MeasurePoint>();
        // 排序方式和排序列
        Direction sortDir = params.getsSortDir_0().equals("asc") ? Direction.ASC : Direction.DESC;
        String sortColName = params.getsColumns().split(",")[params.getiSortCol_0()];
        Sort sort = new Sort(sortDir, sortColName);
        // 当前页
        int pageNumber = (int) Math.ceil(params.getiDisplayStart() / params.getiDisplayLength());
        Pageable pageable = new PageRequest(pageNumber, params.getiDisplayLength(), sort);
        Page<MeasurePoint> page = measurePointRepository.findBySiteidAndOrgid(siteid, orgid, pageable);

        List<MeasurePoint> data = page.getContent();
        result.setsEcho(params.getsEcho());
        result.setiTotalDisplayRecords(page.getTotalElements());
        result.setiTotalRecords(page.getTotalElements());
        result.setAaData(data);

        return result;
    }


    @Transactional(readOnly = true)
    public DataSet<MeasurePoint> findMeasurePointsWithDatatablesCriterias(
            DatatablesCriterias criterias) {

        List<MeasurePoint> measurePoints = jpaMeasurePointRepository.findMeasurePointWithDatatablesCriterias(criterias);
        Long count = jpaMeasurePointRepository.getTotalCount();
        Long countFiltered = jpaMeasurePointRepository.getFilteredCount(criterias);

        return new DataSet<MeasurePoint>(measurePoints, count, countFiltered);
    }


    @Transactional(readOnly = true)
    public String generatePointNum() {
        return idGenService.generateAndUpdateNewId(Constants.MEASUREPOINTNUM, "", 4, 0, "", false);
    }


    @Transactional(readOnly = true)
    public List<MeasurePoint> findMeasurePointsByAssetnumAndMetername(
            String assetnum, String metername, Boolean isspec) {
        List<MeasurePoint> list = null;
        if (isspec == null) {
            list = measurePointRepository.findByAssetnumAndMetername(assetnum, metername);
        } else {
            list = measurePointRepository.findByAssetnumAndMeternameAndIsspec(assetnum, metername, isspec);
        }
        return list;
    }

    @Transactional(readOnly = true)
    public boolean existPointNum(String pointnum, String siteid, String orgid) {
        List<MeasurePoint> list = measurePointRepository.findByPointnumAndSiteidAndOrgid(pointnum, siteid, orgid);
        if (list != null && list.size() > 0) {
            return true;
        }
        return false;
    }

    @Transactional
    public MeasurePoint saveMeasurePoint(MeasurePoint measurePoint)
            throws Exception {
        boolean isnew = measurePoint.getMeasurepointid() == 0;
        measurePoint = measurePointRepository.saveAndFlush(measurePoint);
        if (isnew) {
            idGenService.updateNewId(Constants.MEASUREPOINTNUM);
        }
        return measurePoint;
    }


    @Transactional(readOnly = true)
    public MeasurePoint findMeasurePointById(long measurepointid) {
        return measurePointRepository.findOne(measurepointid);
    }


    @Transactional
    public void deleteMeasurePoint(long measurepointid) throws Exception {
        try {
            //判断测点是否发生了业务，如果发生业务不允许删除
            MeasurePoint measurePoint = measurePointRepository.findOne(measurepointid);
            if (measurePoint != null) {
                List<Measurement> list = measurementRepository.findByPointnumAndMeternameAndSiteid(measurePoint.getPointnum(), measurePoint.getMetername(), measurePoint.getSiteid(), new PageRequest(0, 100)).getContent();
                if (list != null && list.size() > 0) {
                    throw new Exception("测点已经存在测量记录，不允许删除");
                } else {
                    measurePointRepository.delete(measurepointid);
                }
            }
        } catch (Exception e) {
            throw e;
        }
    }

}
