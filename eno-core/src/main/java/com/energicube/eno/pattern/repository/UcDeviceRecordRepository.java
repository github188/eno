package com.energicube.eno.pattern.repository;

import com.energicube.eno.pattern.model.UcDeviceRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-8-8
 * Time: 下午12:37
 * To change this template use File | Settings | File Templates.
 */
public interface UcDeviceRecordRepository extends JpaRepository<UcDeviceRecord, String> {


    /**
     * 通过模式ID查询设备记录
     *
     * @param recordId 模式记录的ID
     * @return
     */
    public List<UcDeviceRecord> findByUcPatternRecord_Id(String recordId);
}
