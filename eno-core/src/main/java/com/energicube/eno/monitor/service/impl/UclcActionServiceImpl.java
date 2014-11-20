package com.energicube.eno.monitor.service.impl;

import com.energicube.eno.monitor.model.UclcAction;
import com.energicube.eno.monitor.repository.UclcActionRepository;
import com.energicube.eno.monitor.service.UclcActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UclcActionServiceImpl implements UclcActionService {

    @Autowired
    public UclcActionRepository uclcActionRepository;

    @Override
    public List<UclcAction> findAll() {
        return uclcActionRepository.findAll();
    }

    @Override
    public List<UclcAction> findByActiontype(int actiontype) {
        return uclcActionRepository.findByActiontype(actiontype);
    }

    @Override
    public UclcAction findOne(int condid) {
        return uclcActionRepository.findOne(condid);
    }

    @Override
    @Transactional
    public void save(UclcAction obj) {
        uclcActionRepository.save(obj);
    }

    @Override
    @Transactional
    public void edit(UclcAction obj) {
        uclcActionRepository.saveAndFlush(obj);
    }

    @Override
    @Transactional
    public void del(int id) {
        uclcActionRepository.delete(id);
    }

    @Override
    @Transactional
    public void del(Iterable<UclcAction> obj) {
        uclcActionRepository.delete(obj);
    }

    @Override
    public List<UclcAction> findByCellid(int cellid) {
        return uclcActionRepository.findByCellid(cellid);
    }
}
