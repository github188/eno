package com.energicube.eno.monitor.service.impl;

import com.energicube.eno.monitor.model.SubDoor;
import com.energicube.eno.monitor.repository.SubDoorRepository;
import com.energicube.eno.monitor.service.DoorInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DooInfoServiceImpl implements DoorInfoService {

    @Autowired
    SubDoorRepository subDoorRepository;

    @Override
    public List<SubDoor> findDoorInfosByTagNameAndDate(String tagname, Date start, Date end) {
        return subDoorRepository.findByDoorNumAndEventTimeOrderByEventTimeDesc(tagname, start, end);
    }

    @Override
    public List<SubDoor> findDoorInfosByTagName(String tagname) {
        return subDoorRepository.findByDoorNumOrderByEventTimeDesc(tagname);
    }
}
