package com.energicube.eno.monitor.service;

import com.energicube.eno.monitor.model.Datacolsrelation;
import org.springframework.data.domain.Page;

import java.util.List;

public interface DatacolsrelationService {
    /**
     * 分页查找
     */
    Page<Datacolsrelation> findByPage(int page, int size);

    /**
     * 添加
     */
    void saveDatacolsrelation(Datacolsrelation datacolsrelation);

    /**
     * 删除
     */
    void deleteDatacolsrelation(int id);

    /**
     * 查找一个
     */
    Datacolsrelation findOne(int id);

    /**
     * 根据外键查找一个
     */
    Datacolsrelation findOneByFk(String fk);

    /**
     * 根据配置id查找所有
     */
    List<Datacolsrelation> findByDatacolId(String dataid);
}
