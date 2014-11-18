package com.energicube.eno.monitor.service.impl;

import com.energicube.eno.monitor.model.DataConfigApp;
import com.energicube.eno.monitor.repository.DataConfigAppRepository;
import com.energicube.eno.monitor.service.DataConfigAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 应用配置server实现类
 */
@Service
public class DataConfigAppServiceImpl implements DataConfigAppService {

    @Autowired
    private DataConfigAppRepository dataConfigAppRepository;

    /**
     * 分页查询
     */
    @Override
    public Page<DataConfigApp> findByPage(int page, int size) {
        Pageable pageable = new PageRequest(page, size);
        Page<DataConfigApp> dataConfigApp = dataConfigAppRepository.findAll(pageable);
        return dataConfigApp;
    }

    /**
     * 添加
     */
    @Override
    @Transactional
    public void saveDataConfigApp(DataConfigApp dataConfigApp) {
        dataConfigAppRepository.save(dataConfigApp);
    }

    /**
     * 删除
     */
    @Override
    @Transactional
    public void deleteDataConfigApp(int id) {
        dataConfigAppRepository.delete(id);
    }

    /**
     * 根据configid查找一个
     */
    @Override
    @Transactional(readOnly = true)
    public DataConfigApp findByConfigid(String configid) {
        return dataConfigAppRepository.findByConfigid(configid);
    }

    /**
     * 查找所有
     */
    @Override
    @Transactional(readOnly = true)
    public List<DataConfigApp> findAll() {
        return dataConfigAppRepository.findAll();
    }

    /**
     * 根据外键查找一个
     */
    @Override
    @Transactional(readOnly = true)
    public DataConfigApp findOneByFk(String fk) {
        return null;
    }

    /**
     * 根据APP查找一个
     */
    @Override
    @Transactional(readOnly = true)
    public DataConfigApp findOneByApp(String app) {
        return dataConfigAppRepository.findByApp(app);
    }
}
