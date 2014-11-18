package com.energicube.eno.pattern.repository;

import com.energicube.eno.pattern.model.UcDevicePattern;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-8-8
 * Time: 下午12:37
 * To change this template use File | Settings | File Templates.
 */
public interface UcDevicePatternRepository extends JpaRepository<UcDevicePattern, String> {


    /**
     * 查询模式里的设备
     *
     * @param id 时间段id
     * @return
     */
    public List<UcDevicePattern> findByUcPatternAction_Id(String id);
}
