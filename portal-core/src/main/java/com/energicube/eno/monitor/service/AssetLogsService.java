package com.energicube.eno.monitor.service;

import com.energicube.eno.monitor.model.AssetMeasurement;

public interface AssetLogsService {

    public AssetMeasurement findByTag(String tagid, String tagvalue) throws Exception;

    public AssetMeasurement findByTagId(String tagid) throws Exception;

}
