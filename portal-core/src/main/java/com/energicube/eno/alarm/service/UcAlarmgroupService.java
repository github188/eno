package com.energicube.eno.alarm.service;

import com.energicube.eno.alarm.model.UcAlarmgroup;

import java.util.List;

public interface UcAlarmgroupService {

    public List<UcAlarmgroup> findListByPid(int pid);

    public List<UcAlarmgroup> findListByGroupMain();
}
