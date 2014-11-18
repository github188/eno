package com.energicube.eno.monitor.service;

import com.energicube.eno.monitor.model.DataConfigApp;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 应用配置server接口
 */
public interface DataConfigAppService {

    /**
     * 分页查询
     */
    Page<DataConfigApp> findByPage(int page, int size);

    /**
     * 添加
     */
    void saveDataConfigApp(DataConfigApp dataConfigApp);

    /**
     * 删除
     */
    void deleteDataConfigApp(int id);

    /**
     * 根据configid查找一个
     */
    DataConfigApp findByConfigid(String configid);

    /**
     * 查找所有
     */
    List<DataConfigApp> findAll();

    /**
     * 根据外键查找一个
     */
    DataConfigApp findOneByFk(String fk);

    /**
     * 根据APP查找一个
     */
    DataConfigApp findOneByApp(String app);
}
