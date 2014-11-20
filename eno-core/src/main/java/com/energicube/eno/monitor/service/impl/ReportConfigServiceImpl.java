package com.energicube.eno.monitor.service.impl;

import com.energicube.eno.monitor.model.ReportConfig;
import com.energicube.eno.monitor.repository.ReportConfigRepository;
import com.energicube.eno.monitor.service.ReportConfigService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class ReportConfigServiceImpl implements ReportConfigService {
   
	private static Log logger = LogFactory.getLog(ReportConfigServiceImpl.class);
	
	@Autowired
    ReportConfigRepository reportConfigRepository;

    @Override
    public List<String> findSystemList() {
        List<String[]> result = reportConfigRepository.findSystemList();
        List<String> list = new ArrayList<String>();
        String str;
        for (Object[] obj : result) {
            str = obj[0].toString();
            if (!list.contains(str)) {
                list.add(str);
            }
        }
        return list;
    }

    @Override
    public List<String> findDeviceType(String system) {
        logger.info("--findDeviceType----system--" + system);
        List<String[]> result = null;
        if (StringUtils.isNotEmpty(system)) {
            result = reportConfigRepository.findDeviceType(system);
        } else {
        	//参数为空查询全部
            result = reportConfigRepository.findDeviceType();
        }
        List<String> list = new ArrayList<String>();
        String str;
        for (Object[] obj : result) {
            str = obj[0].toString();
            if (!list.contains(str)) {
                list.add(str);
            }
        }
        logger.info("--findDeviceType--result size--" + (list == null ? 0 : list.size()));
        return list;
    }

    @Override
    public List<String> findDeviceList(String system, String devicetype) {
        logger.info("--findDevice----system--" + system);
        logger.info("--findDevice----devicetype--" + devicetype);
        List<String[]> result = null;
        if (StringUtils.isNotEmpty(system) && StringUtils.isNotEmpty(devicetype)) {
            result = reportConfigRepository.findDevices(system, devicetype);
        } else {
        	//参数为空查询全部
            result = reportConfigRepository.findDevices();
        }
        List<String> list = new ArrayList<String>();
        String str;
        for (Object[] obj : result) {
            str = obj[0].toString();
            if (!list.contains(str)) {
                list.add(str);
            }
        }
        logger.info("--findDeviceType--result size--" + (list == null ? 0 : list.size()));
        return list;
    }

    @Override
    public List<String> findDeviceIdList(String system, String devicetype) {
        logger.info("--findDevice----system--" + system);
        logger.info("--findDevice----devicetype--" + devicetype);
        List<String[]> result = null;
        if (StringUtils.isNotEmpty(system) && StringUtils.isNotEmpty(devicetype)) {
            result = reportConfigRepository.findDevicesId(system, devicetype);
        } else {
        	//参数为空查询全部
            result = reportConfigRepository.findDevicesId();
        }
        List<String> list = new ArrayList<String>();
        String str;
        for (Object[] obj : result) {
            str = obj[0].toString();
            if (!list.contains(str)) {
                list.add(str);
            }
        }
        logger.info("--findDeviceType--result size--" + (list == null ? 0 : list.size()));
        return list;
    }

    @Override
    public List<ReportConfig> findParamsList(String system, String devicetype, String id) {
        List<ReportConfig> list = null;
        if (StringUtils.isNotEmpty(system) && StringUtils.isNotEmpty(devicetype) && StringUtils.isNotEmpty(id)) {
            list = reportConfigRepository.findParamsList(system, devicetype, id);
        } else {
            list = reportConfigRepository.findParamsList();
        }
        logger.info("--findIdList--result size--" + (list == null ? 0 : list.size()));
        return list;
    }

    @Override
    public List<ReportConfig> findReportconfigsList(String system,
                                                    String devicetype, String device) {
        List<ReportConfig> list = null;
        if (StringUtils.isNotEmpty(system) && StringUtils.isNotEmpty(devicetype) && StringUtils.isNotEmpty(device)) {
            list = reportConfigRepository.findReportConfigsList(system, devicetype, device);
        } else {
            list = reportConfigRepository.findParamsList();
        }
        logger.info("--findIdList--result size--" + (list == null ? 0 : list.size()));
        return list;
    }

    @Override
    public List<ReportConfig> save(Iterable<ReportConfig> reportConfigs) {
        return reportConfigRepository.save(reportConfigs);
    }

}
