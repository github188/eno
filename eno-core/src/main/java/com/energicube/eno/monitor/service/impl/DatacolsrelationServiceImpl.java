package com.energicube.eno.monitor.service.impl;

import com.energicube.eno.monitor.model.Datacolsrelation;
import com.energicube.eno.monitor.repository.DatacolsrelationRepository;
import com.energicube.eno.monitor.service.DatacolsrelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DatacolsrelationServiceImpl implements DatacolsrelationService {

    @Autowired
    private DatacolsrelationRepository datacolsrelationRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<Datacolsrelation> findByPage(int page, int size) {
        Pageable pageable = new PageRequest(page, size);
        Page<Datacolsrelation> page2 = datacolsrelationRepository.findAll(pageable);
        return page2;
    }

    @Override
    @Transactional
    public void saveDatacolsrelation(Datacolsrelation datacolsrelation) {
        datacolsrelationRepository.save(datacolsrelation);
    }

    @Override
    @Transactional
    public void deleteDatacolsrelation(int id) {
        datacolsrelationRepository.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Datacolsrelation findOne(int id) {
        Datacolsrelation one = datacolsrelationRepository.findOne(id);
        return one;
    }

    @Override
    @Transactional(readOnly = true)
    public Datacolsrelation findOneByFk(String fk) {
        List<Datacolsrelation> list = datacolsrelationRepository.findByDatacolumnconfigid(fk);
        if (list.size() == 0) return null;
        return list.get(0);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Datacolsrelation> findByDatacolId(String dataid) {
        List<Datacolsrelation> list = datacolsrelationRepository.findByDatacolumnconfigid(dataid);
        return list;
    }

}
