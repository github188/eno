package com.energicube.eno.monitor.service;

import com.energicube.eno.monitor.model.SubDoor;

import java.util.Date;
import java.util.List;

public interface DoorInfoService {

    /**
     * 查询刷卡列表
     *
     * @param tagname 表字段 doornum
     * @param start   开始时间
     * @param end     结束时间
     * @return 此门的刷卡列表
     */
    public List<SubDoor> findDoorInfosByTagNameAndDate(String tagname, Date start, Date end);

    /**
     * 查询刷卡列表
     *
     * @param tagname 表字段 doornum
     * @return 此门的刷卡列表
     */
    public List<SubDoor> findDoorInfosByTagName(String tagname);
}
