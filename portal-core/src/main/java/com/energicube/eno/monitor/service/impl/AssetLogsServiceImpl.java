package com.energicube.eno.monitor.service.impl;

import com.energicube.eno.monitor.model.AssetMeasurement;
import com.energicube.eno.monitor.repository.jpa.AssetMeasurementRepository;
import com.energicube.eno.monitor.service.AssetLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AssetLogsServiceImpl implements AssetLogsService {

    @Autowired
    private AssetMeasurementRepository assetMeasurementRepository;

    @Transactional(readOnly = true)
    public AssetMeasurement findByTag(String tagid, String tagvalue) {

        List<Object> list = assetMeasurementRepository.findByTag(tagid, tagvalue);

        if (list == null || list.size() == 0)
            return null;
        Object[] objs = (Object[]) list.get(0);
        if (objs == null || objs.length == 0)
            return null;

        AssetMeasurement assetMeasurement = new AssetMeasurement();
        assetMeasurement.setLocationName(objs[0].toString());
        assetMeasurement.setAssetName(objs[1].toString());
        assetMeasurement.setTagvalue(objs[2].toString());

        return assetMeasurement;
    }

    @Transactional(readOnly = true)
    public AssetMeasurement findByTagId(String tagid) throws Exception {

        List<Object> list = assetMeasurementRepository.findByTagId(tagid);

        if (list == null || list.size() == 0)
            return null;
        Object[] objs = (Object[]) list.get(0);
        if (objs == null || objs.length == 0)
            return null;

        AssetMeasurement assetMeasurement = new AssetMeasurement();
        assetMeasurement.setTagId(objs[0].toString());
        assetMeasurement.setTagname(objs[1].toString());

        return assetMeasurement;
    }

}
