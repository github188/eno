package com.energicube.eno.asset.service.impl;

import com.energicube.eno.asset.model.*;
import com.energicube.eno.asset.repository.MeasurePointRepository;
import com.energicube.eno.asset.repository.MeasureSpecRepository;
import com.energicube.eno.asset.repository.MeasurementRepository;
import com.energicube.eno.asset.service.MeasureSpecService;
import com.energicube.eno.common.jsonquery.jpa.request.DataTablesRequestParams;
import com.energicube.eno.common.jsonquery.jpa.response.DataTablesResponse;
import org.joda.time.LocalDate;
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

import java.util.List;

@Service
public class MeasureSpecServiceImpl implements MeasureSpecService {

    private MeasureSpecRepository measureSpecRepository;
    private MeasurePointRepository measurePointRepository;
    private MeasurementRepository measurementRepository;

    @Autowired
    public MeasureSpecServiceImpl(MeasureSpecRepository measureSpecRepository,
                                  MeasurePointRepository measurePointRepository,
                                  MeasurementRepository measurementRepository) {
        this.measureSpecRepository = measureSpecRepository;
        this.measurePointRepository = measurePointRepository;
        this.measurementRepository = measurementRepository;
    }

    @Transactional(readOnly = true)
    public DataTablesResponse<MeasureSpec> findMeasureSpecsToDataTables(
            String metername, String siteid, DataTablesRequestParams params) {
        return findAssetMeasureSpecsToDataTables("", metername, siteid, params);
    }

    @Transactional(readOnly = true)
    public DataTablesResponse<MeasureSpec> findAssetMeasureSpecsToDataTables(
            String assetnum, String metername, String siteid,
            DataTablesRequestParams params) {
        DataTablesResponse<MeasureSpec> result = new DataTablesResponse<MeasureSpec>();

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
        Page<MeasureSpec> page = null;
        if (StringUtils.hasLength(assetnum)) {
            page = measureSpecRepository.findByAssetnumAndMeternameAndSiteid(assetnum, metername, siteid, pageable);
        } else {
            page = measureSpecRepository.findByMeternameAndSiteid(metername, siteid, pageable);
        }
        List<MeasureSpec> data = page.getContent();
        result.setsEcho(params.getsEcho());
        result.setiTotalDisplayRecords(page.getTotalElements());
        result.setiTotalRecords(page.getTotalElements());
        result.setAaData(data);

        return result;
    }

    @Transactional(readOnly = true)
    public List<MeasureSpec> findMeasureSpecsByAssetnumAndMetername(
            String assetnum, String metername, String siteid) {
        Pageable pageable = new PageRequest(0, Integer.MAX_VALUE);
        Page<MeasureSpec> page = measureSpecRepository.findByAssetnumAndMeternameAndSiteid(assetnum, metername, siteid, pageable);
        return page.getContent();
    }

    @Transactional
    public MeasureSpec saveMeasureSpec(MeasureSpec measureSpec,
                                       long measurepointid) throws Exception {

        long measurespecid = measureSpec.getMeasurespecid();

        //测点对象
        MeasurePoint measurePoint = new MeasurePoint();
        String pointnum = "", metername = null, assetnum = null, siteid = "", orgid = "", classstructureid = "";
        if (measurepointid > 0) {
            measurePoint = measurePointRepository.findOne(measurepointid);
            pointnum = measurePoint.getPointnum();
            metername = measurePoint.getMetername();
            assetnum = measurePoint.getAssetnum();
            siteid = measurePoint.getSiteid();
            orgid = measurePoint.getOrgid();
            classstructureid = measurePoint.getClassstructureid();
        }
        //保存测点技术规范
        measureSpec.setAssetnum(assetnum);
        measureSpec.setMetername(metername);
        measureSpec.setSiteid(siteid);
        measureSpec.setOrgid(orgid);
        measureSpec.setClassstructureid(classstructureid);
        if (measureSpec.getChangeby() == null) {
            measureSpec.setChangeby("");
        }
        measureSpec.setChangedate(LocalDateTime.now());
        measureSpec = measureSpecRepository.save(measureSpec);
        //保存测点对应的读数
        if (!measurePoint.getIsspec()) {
            return measureSpec;
        }

        //是否自动创建测点信息
        boolean isCreate = false;
        //如果测点规范已经存在，但在测点读数中不存在记录，则自动创建
        if (measurespecid > 0) {
            List<Measurement> measurements = measurementRepository.findByPointnumAndMeternameAndSiteid(pointnum, metername, siteid, new PageRequest(0, Integer.MAX_VALUE)).getContent();
            if (measurements.size() == 0) {
                isCreate = true;
            }
        } else {
            isCreate = true;
        }
        if (isCreate) {
            //保存测点读数
            Measurement measurement = new Measurement();
            measurement.setAssetnum(assetnum);
            measurement.setMetername(metername);
            measurement.setLocation(measurePoint.getLocation());
            measurement.setMeasurespecid(measureSpec.getMeasurespecid());
            measurement.setBasemeasureunitid(measureSpec.getMeasureunitid());
            measurement.setPointnum(measurePoint.getPointnum());
            measurement.setOrgid(orgid);
            measurement.setSiteid(siteid);
            measurement.setCalcmethod(measureSpec.getCalcmethod());
            measurement.setMeasuredate(LocalDate.now());
            measurement.setMeasuretime(LocalDateTime.now());
            measurement.setValuetag("");
            measurementRepository.save(measurement);
        }
        return measureSpec;
    }

    @Transactional(readOnly = true)
    public MeasureSpec updateMeasureSpec(MeasureSpec measureSpec,
                                         long measurepointid) throws Exception {

        return null;
    }


    @Transactional(readOnly = true)
    public MeasureSpecSet findMeasureSpecSetById(long measurespecid) {
        Object[] objs = measureSpecRepository.findByMeasurespecid(measurespecid);

        MeasureSpecSet measureSpecSet = new MeasureSpecSet();

        for (Object obj : objs) {

            if (obj instanceof MeasureSpec) {
                measureSpecSet.setMeasureSpec((MeasureSpec) obj);
            }
            if (obj instanceof AssetAttribute) {
                measureSpecSet.setAssetAttribute((AssetAttribute) obj);
            }
        }
        return measureSpecSet;
    }

    @Transactional(readOnly = true)
    public boolean existMeasureSpec(String assetnum, String metername,
                                    String siteid, String assetattrid) {
        List<MeasureSpec> list = measureSpecRepository.findByAssetnumAndMeternameAndSiteidAndAssetattrid(assetnum, metername, siteid, assetattrid);
        if (list != null && list.size() > 0)
            return true;
        return false;
    }

    @Transactional
    public boolean deleteMeasureSpec(long measurespecid) throws Exception {
        try {
            measureSpecRepository.delete(measurespecid);
            return true;
        } catch (Exception e) {
            throw e;
        }

    }

    @Transactional(readOnly = true)
    public MeasureSpec findMeasureSpecById(long measurespecid) {
        return measureSpecRepository.findOne(measurespecid);
    }

}
