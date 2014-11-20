package com.energicube.eno.monitor.service.impl;

import com.energicube.eno.common.jsonquery.jpa.request.DataTablesRequestParams;
import com.energicube.eno.common.jsonquery.jpa.response.DataTablesResponse;
import com.energicube.eno.monitor.model.OkcLogs;
import com.energicube.eno.monitor.repository.OkcLogsRepository;
import com.energicube.eno.monitor.service.OkcLogsService;
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

import java.util.List;

@Service
public class OkcLogsServiceImpl implements OkcLogsService {

    private static final Log logger = LogFactory.getLog(OkcLogsServiceImpl.class);

    @Autowired
    private OkcLogsRepository okcLogsRepository;


    @Transactional(readOnly = true)
    public DataTablesResponse<OkcLogs> findByDateRange(String startDate,
                                                       String endDate, String level, DataTablesRequestParams params)
            throws Exception {

        logger.debug("DataTablesResponse Request  params:" + params);
        logger.debug("Request startDate:" + startDate + ",endDate:" + endDate + ",level" + level);

        DataTablesResponse<OkcLogs> result = new DataTablesResponse<OkcLogs>();
        Direction sortDir = params.getsSortDir_0().equals("asc") ? Direction.ASC : Direction.DESC;
        String sortColName = params.getsColumns().split(",")[params.getiSortCol_0()];
        Sort sort = new Sort(sortDir, sortColName);
        int pageNumber = (int) Math.ceil(params.getiDisplayStart() / params.getiDisplayLength());
        Pageable pageable = new PageRequest(pageNumber, params.getiDisplayLength(), sort);

        Page<OkcLogs> page = okcLogsRepository.findByDateRange(startDate, endDate, level, pageable);

        List<OkcLogs> data = page.getContent();
        result.setsEcho(params.getsEcho());
        result.setiTotalDisplayRecords(page.getTotalElements());
        result.setiTotalRecords(page.getTotalElements());
        result.setAaData(data);
        return result;
    }


    @Transactional(readOnly = true)
    public OkcLogs findByLogid(Long logid) throws Exception {
        return okcLogsRepository.findOne(logid);
    }

    public void save(OkcLogs okcLogs) throws Exception {
        okcLogsRepository.save(okcLogs);
    }


}
