package com.energicube.eno.asset.service.impl;

import com.energicube.eno.asset.model.*;
import com.energicube.eno.asset.repository.MeasurementRepository;
import com.energicube.eno.asset.service.MeasurementService;
import com.energicube.eno.common.jsonquery.jpa.request.DataTablesRequestParams;
import com.energicube.eno.common.jsonquery.jpa.response.DataTablesResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
public class MeasurementServiceImpl implements MeasurementService {

    private Log logger = LogFactory.getLog(this.getClass());

    private MeasurementRepository measurementRepository;

    @Autowired
    public MeasurementServiceImpl(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }


    @Transactional(readOnly = true)
    public DataTablesResponse<MeasurementSet> findMeasurementsToDataTables(
            String pointnum, String metername, String siteid, String orgid,
            DataTablesRequestParams params) {
        DataTablesResponse<MeasurementSet> result = new DataTablesResponse<MeasurementSet>();

        // 排序方式和排序列
        Direction sortDir = params.getsSortDir_0().equals("asc") ? Direction.ASC : Direction.DESC;
        String sortColName = params.getsColumns().split(",")[params.getiSortCol_0()];
        Sort sort = new Sort(sortDir, sortColName);
        // 当前页
        int pageNumber = (int) Math.ceil(params.getiDisplayStart() / params.getiDisplayLength());
        Pageable pageable = new PageRequest(pageNumber, params.getiDisplayLength());

        Page<Object[]> page = measurementRepository.findByPointnumAndMetername(pointnum, metername, siteid, orgid, pageable);

        List<MeasurementSet> data = parseArrayObject(page.getContent());
        result.setsEcho(params.getsEcho());
        result.setiTotalDisplayRecords(page.getTotalElements());
        result.setiTotalRecords(page.getTotalElements());
        result.setAaData(data);

        return result;
    }

    @Transactional
    public Measurement saveMeasurement(Measurement measurement) {
        try {
            measurement = measurementRepository.save(measurement);
        } catch (Exception e) {
            return null;
        }
        return measurement;
    }

    @Transactional
    public boolean deleteMeasurement(long measurementid) throws Exception {
        try {
            measurementRepository.delete(measurementid);
            return true;
        } catch (Exception e) {
            throw e;
        }
    }


    @Transactional(readOnly = true)
    public List<MeasurementSet> findMeasurementsByAssetnumAndMetername(
            String assetnum, String metername, String siteid, String orgid) {
        List<Object[]> list = measurementRepository.findByAssetnumAndMetername(assetnum, metername, siteid, orgid);
        return parseArrayObject(list);
    }


    /**
     * 返回指定资产编号对应的采集属性 {@link MeasurementAttribute}
     *
     * @author CHENPING  2014-02-24
     */
    @Transactional(readOnly = true)
    public List<MeasurementAttribute> findMeasurementsByAssetnum(String assetnum,
                                                                 String siteid, String orgid) {

        List<Object[]> list = measurementRepository.findByAssetnum(assetnum, siteid, orgid);

        List<MeasurementAttribute> results = new ArrayList<MeasurementAttribute>();

        for (Object[] arrObj : list) {

            MeasurementAttribute ret = new MeasurementAttribute();
            for (Object obj : arrObj) {
                if (obj instanceof Measurement) {
                    ret.setAssetnum(((Measurement) obj).getAssetnum());
                    ret.setValuetag(((Measurement) obj).getValuetag());
                    ret.setMetername(((Measurement) obj).getMetername());
                }
                if (obj instanceof AssetAttribute) {
                    ret.setAssetattrid(((AssetAttribute) obj).getAssetattrid());
                    ret.setDescrption(((AssetAttribute) obj).getDescription());
                    ret.setMeasureunitid(((AssetAttribute) obj).getMeasureunitid());
                }
                if (obj instanceof MeasureSpec) { // 为适应WEB版万达设备列表和面板数据字典对应而加[2014-10-16,zzx]
                    ret.setClassstructureid(((MeasureSpec) obj).getClassstructureid());
                }
            }
            results.add(ret);
            if (logger.isDebugEnabled()) {
                logger.debug(ret);
            }
        }
        return results;
    }


    @Transactional(readOnly = true)
    public MeasurementSet findByMeasurementid(long measurementid) {
        MeasurementSet measurementSet = new MeasurementSet();
        List<Object[]> list = measurementRepository.findByMeasurementid(measurementid);
        if (list != null && list.size() > 0) {
            measurementSet = parseObject(list.get(0));
        }
        return measurementSet;
    }

    /**
     * 转换对象数组到对象
     *
     * @param list 对象数组列表
     * @return {@link MeasurementSet} list
     */
    private List<MeasurementSet> parseArrayObject(List<Object[]> list) {
        List<MeasurementSet> result = new ArrayList<MeasurementSet>();
        for (Object[] obj : list) {
            MeasurementSet measurementSet = new MeasurementSet();
            measurementSet = parseObject(obj);
            result.add(measurementSet);
        }
        return result;
    }

    /**
     * 解析对象
     *
     * @param arrObject 数组对象
     * @return {@link MeasurementSet} object
     */
    private MeasurementSet parseObject(Object[] arrObject) {
        MeasurementSet measurementSet = new MeasurementSet();
        Object[] obj = arrObject;
        for (Object o : obj) {
            if (o instanceof Measurement) {
                measurementSet.setMeasurement((Measurement) o);
            }
            if (o instanceof MeasurePoint) {
                measurementSet.setMeasurePoint((MeasurePoint) o);
            }
            if (o instanceof MeasureSpec) {
                measurementSet.setMeasureSpec((MeasureSpec) o);
            }
            if (o instanceof AssetAttribute) {
                measurementSet.setAssetAttribute((AssetAttribute) o);
            }
        }
        return measurementSet;
    }


}
