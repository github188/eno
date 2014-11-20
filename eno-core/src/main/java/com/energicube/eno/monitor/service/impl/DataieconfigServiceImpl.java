package com.energicube.eno.monitor.service.impl;

import com.energicube.eno.monitor.model.Dataieconfig;
import com.energicube.eno.monitor.repository.DataieconfigRepository;
import com.energicube.eno.monitor.service.DataieconfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DataieconfigServiceImpl implements DataieconfigService {

    @Autowired
    private DataieconfigRepository dataieconfigRepository;

    @Override
    @Transactional(readOnly = true)
    public Page<Dataieconfig> findByPage(int index, int size) {
        Pageable pageable = new PageRequest(index, size);
        Page<Dataieconfig> page = dataieconfigRepository.findAll(pageable);
        return page;
    }

    @Override
    @Transactional
    public void saveDataieconfig(Dataieconfig dataieconfig) {
        dataieconfigRepository.save(dataieconfig);
    }

    @Override
    @Transactional
    public void deleteDataieconfig(int id) {
        dataieconfigRepository.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Dataieconfig findOne(int id) {
        Dataieconfig one = dataieconfigRepository.findOne(id);
        return one;
    }

    @Override
    @Transactional(readOnly = true)
    public Dataieconfig findByDatacolumnconfigid(String datacolumnconfigid) {
        List<Dataieconfig> list = dataieconfigRepository.findByDatacolumnconfigid(datacolumnconfigid);
        return list.get(0);
    }

}
