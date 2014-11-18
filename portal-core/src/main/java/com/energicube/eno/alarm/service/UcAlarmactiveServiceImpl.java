package com.energicube.eno.alarm.service;

import com.energicube.eno.alarm.repository.UcAlarmactiveRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UcAlarmactiveServiceImpl implements UcAlarmactiveService {

    private static final Log logger = LogFactory.getLog(UcAlarmactiveServiceImpl.class);

    @Autowired
    private UcAlarmactiveRepository ucAlarmactiveRepository;


    @Transactional
    public void deleteUcAlarmactive(int almlogid) {
        if (ucAlarmactiveRepository.exists(almlogid)) {

            logger.info("delete active alarm msg in a data table,almlogid:" + almlogid);

            ucAlarmactiveRepository.delete(almlogid);
        }
    }


}
