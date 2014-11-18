package com.energicube.eno.alarm.service;


public interface UcAlarmactiveService {


    /**
     * 删除指定的实时火警信息
     *
     * @param almlogid 实时火警信息ID
     */
    public void deleteUcAlarmactive(int almlogid);
}
