package com.energicube.eno.fssm.service.impl;

import com.energicube.eno.fssm.model.LinkMonitor;
import com.energicube.eno.fssm.repository.LinkMonitorRepository;
import com.energicube.eno.fssm.service.LinkMonitorService;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LinkMonitorServiceImpl implements LinkMonitorService {


    private LinkMonitorRepository linkMonitorRepository;

    @Autowired
    public LinkMonitorServiceImpl(LinkMonitorRepository linkMonitorRepository) {
        this.linkMonitorRepository = linkMonitorRepository;
    }


    @Transactional
    public LinkMonitor saveLinkMonitor(LinkMonitor linkMonitor) {
        return linkMonitorRepository.save(linkMonitor);
    }

    @Transactional(readOnly = true)
    public List<LinkMonitor> findAll() {
        return linkMonitorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<LinkMonitor> findLinkMonitorByDateRange(
            LocalDateTime starttime, LocalDateTime endtime) {
        return linkMonitorRepository.findLinkMonitorByDateRange(starttime, endtime);
    }

    public static void main(String[] args) {


    }

}
