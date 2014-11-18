package com.energicube.eno.monitor.service.impl;

import com.energicube.eno.monitor.model.NetvideoDvrcfg;
import com.energicube.eno.monitor.repository.NetvideoDvrcfgRepository;
import com.energicube.eno.monitor.service.NetvideoDvrcfgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class NetvideoDvrcfgServiceImpl implements NetvideoDvrcfgService {

    private NetvideoDvrcfgRepository netvideoDvrcfgRepository;

    @Autowired
    public NetvideoDvrcfgServiceImpl(
            NetvideoDvrcfgRepository netvideoDvrcfgRepository) {
        this.netvideoDvrcfgRepository = netvideoDvrcfgRepository;
    }

    @Transactional(readOnly = true)
    public List<NetvideoDvrcfg> findNetvideoDvrcfgs(Sort sort) {
        return netvideoDvrcfgRepository.findAll(sort);
    }

    @Transactional(readOnly = true)
    public NetvideoDvrcfg findNetvideoDvrcfgById(int dvrcfgid) {
        return netvideoDvrcfgRepository.findOne(dvrcfgid);
    }

    @Transactional
    public void saveNetvideoDvrcfg(NetvideoDvrcfg dvrcfg) {
        netvideoDvrcfgRepository.save(dvrcfg);
    }

    @Transactional
    public void saveNetvideoDvrcfg(Iterable<NetvideoDvrcfg> dvrcfg) {
        netvideoDvrcfgRepository.save(dvrcfg);
    }

    @Transactional
    public void deleteNetvideoDvrcfg(NetvideoDvrcfg dvrcfg) {
        netvideoDvrcfgRepository.delete(dvrcfg);
    }

    @Transactional
    public void deleteNetvideoDvrcfg(int dvrcfgid) {
        netvideoDvrcfgRepository.delete(dvrcfgid);
    }

    @Override
    @Transactional
    public int findMaxIndex() {
        if (netvideoDvrcfgRepository.findMaxIndex() == null) {
            return 0;
        } else {
            return netvideoDvrcfgRepository.findMaxIndex().intValue();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteAll() {
        netvideoDvrcfgRepository.deleteAll();
    }
}
