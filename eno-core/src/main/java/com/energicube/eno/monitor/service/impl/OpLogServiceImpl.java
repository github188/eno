package com.energicube.eno.monitor.service.impl;

import com.energicube.eno.monitor.model.OpLogs;
import com.energicube.eno.monitor.model.UcEventLog;
import com.energicube.eno.monitor.repository.OpLogRepository;
import com.energicube.eno.monitor.repository.UcEventLogRepository;
import com.energicube.eno.monitor.service.OpLogService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OpLogServiceImpl implements OpLogService {

    private static Log logger = LogFactory.getLog(OpLogServiceImpl.class);
    @Autowired
    private OpLogRepository opLogRepository;

    @Autowired
    private UcEventLogRepository ucEventLogRepository;

    @Transactional
    public OpLogs save(OpLogs opLog) {
        return opLogRepository.save(opLog);
    }

    @Transactional(readOnly = true)
    public List<OpLogs> findBySubsys(String subsys) {

        return null;
    }

    @Transactional
    public OpLogs saveLogByUserAndMsg(String userName, String msg, String level, String subSys, String loger, String category) {
        try {
            OpLogs opLogs = new OpLogs();
            opLogs.setLevel(level);
            opLogs.setMessage(msg);
            opLogs.setUserid(userName);
            opLogs.setSubsys(subSys);
            opLogs.setLogger(loger);
            opLogs.setCategory(category);
            String date = DateTime.now().toString("yyyy-MM-dd HH:mm:ss");
            opLogs.setDated(date);
            return opLogRepository.save(opLogs);
        } catch (Exception e) {
            logger.error("---save log ---", e);
        }
        return null;
    }


    @Transactional(readOnly = true)
    public Page<OpLogs> findByCategory(String category, Pageable pageable) {

        return opLogRepository.findByCategoryOrderByDatedDesc(category,pageable);
    }


    @Override
    public List<UcEventLog> findUcEventlog(long logId) {
        List<UcEventLog> ucEventLogList=ucEventLogRepository.findByGreaterThanId(logId);
        return ucEventLogList;

    }
}
