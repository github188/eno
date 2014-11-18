package com.energicube.eno.monitor.service.impl;

import com.energicube.eno.monitor.model.UclcLinkcell;
import com.energicube.eno.monitor.repository.UclcLinkcellRepository;
import com.energicube.eno.monitor.service.UclcLinkcellService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UclcLinkcellServiceImpl implements UclcLinkcellService {

    @Autowired
    public UclcLinkcellRepository uclcLinkcellRepository;

    @Override
    public List<UclcLinkcell> findAll() {
        return uclcLinkcellRepository.findAll();
    }

    @Override
    public UclcLinkcell findOne(int condid) {
        return uclcLinkcellRepository.findOne(condid);
    }

    @Override
    @Transactional
    public void save(UclcLinkcell obj) {
        uclcLinkcellRepository.save(obj);
    }

    @Override
    @Transactional
    public void edit(UclcLinkcell obj) {
        uclcLinkcellRepository.saveAndFlush(obj);
    }

    @Override
    @Transactional
    public void del(int id) {
        uclcLinkcellRepository.delete(id);
    }

    @Override
    @Transactional
    public void del(List<UclcLinkcell> cellLinks) {
        uclcLinkcellRepository.delete(cellLinks);
    }

    @Override
    public List<UclcLinkcell> findByLinkageid(long Linkageid) {
        return uclcLinkcellRepository.findByLinkageid(Linkageid);
    }
}
