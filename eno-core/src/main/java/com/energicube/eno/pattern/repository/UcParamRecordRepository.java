package com.energicube.eno.pattern.repository;


import com.energicube.eno.pattern.model.UcParamRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-8-8
 * Time: 上午11:28
 * To change this template use File | Settings | File Templates.
 */
public interface UcParamRecordRepository extends JpaRepository<UcParamRecord, String> {

    /**
     * 通过设备ID查询此设备的运行参数
     *
     * @param id 设备ID
     * @return
     */
    public List<UcParamRecord> findByUcDeviceRecord_Id(String id);
}
