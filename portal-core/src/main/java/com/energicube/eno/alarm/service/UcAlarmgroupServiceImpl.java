package com.energicube.eno.alarm.service;

import com.energicube.eno.alarm.model.UcAlarmgroup;
import com.energicube.eno.alarm.repository.UcAlarmgroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UcAlarmgroupServiceImpl implements UcAlarmgroupService {

    @Autowired
    private UcAlarmgroupRepository ucAlarmgroupRepository;


    public List<UcAlarmgroup> findListByPid(int pid) {
        return ucAlarmgroupRepository.findListByPid(pid);
    }


    public List<UcAlarmgroup> findListByGroupMain() {
        return ucAlarmgroupRepository.findListByGroupMain();
    }

}
