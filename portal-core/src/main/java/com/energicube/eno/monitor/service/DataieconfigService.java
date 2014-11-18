package com.energicube.eno.monitor.service;

import com.energicube.eno.monitor.model.Dataieconfig;
import org.springframework.data.domain.Page;

public interface DataieconfigService {
    /**
     * 分页查询
     */
    public Page<Dataieconfig> findByPage(int index, int size);

    /**
     * 添加
     */
    public void saveDataieconfig(Dataieconfig dataieconfig);

    /**
     * 删除
     */
    public void deleteDataieconfig(int id);

    /**
     * 根据主键查找一个
     */
    public Dataieconfig findOne(int id);

    /**
     * 根据datacolumnconfigid查找
     */
    public Dataieconfig findByDatacolumnconfigid(String datacolumnconfigid);
}
