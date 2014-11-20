package com.energicube.eno.monitor.service.impl;

import com.energicube.eno.monitor.model.NetvideoMonitorcfg;
import com.energicube.eno.monitor.repository.NetvideoMonitorcfgRepository;
import com.energicube.eno.monitor.service.NetvideoMonitorcfgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NetvideoMonitorcfgServiceImpl implements NetvideoMonitorcfgService {

    private NetvideoMonitorcfgRepository netvideoMonitorcfgRepository;

    @Autowired
    public NetvideoMonitorcfgServiceImpl(
            NetvideoMonitorcfgRepository netvideoMonitorcfgRepository) {
        this.netvideoMonitorcfgRepository = netvideoMonitorcfgRepository;
    }

    @Transactional(readOnly = true)
    public List<NetvideoMonitorcfg> findNetvideoMonitorcfgs() {
        Sort sort = new Sort(Direction.ASC, "displaysequence");
        return netvideoMonitorcfgRepository.findAll(sort);
    }

    @Transactional(readOnly = true)
    public NetvideoMonitorcfg findNetvideoMonitorcfgById(int monitorid) {
        return netvideoMonitorcfgRepository.findOne(monitorid);
    }

    @Transactional
    public void saveNetvideoMonitorcfg(
            NetvideoMonitorcfg monitorcfg) {
        netvideoMonitorcfgRepository.save(monitorcfg);
    }

    @Transactional
    public void deleteNetvideoMonitorcfg(NetvideoMonitorcfg monitorcfg) {
        netvideoMonitorcfgRepository.delete(monitorcfg);
    }

    @Transactional
    public void deleteNetvideoMonitorcfg(int monitorid) {
        netvideoMonitorcfgRepository.delete(monitorid);
    }

    @Override
    public List<NetvideoMonitorcfg> findNetvideoMonitorcfgs(Sort sort) {
        return netvideoMonitorcfgRepository.findAll(sort);
    }

}
