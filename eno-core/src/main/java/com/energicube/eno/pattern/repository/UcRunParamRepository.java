package com.energicube.eno.pattern.repository;


import com.energicube.eno.pattern.model.UcRunParam;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: 刘广路
 * Date: 13-8-8
 * Time: 上午11:25
 * To change this template use File | Settings | File Templates.
 */
public interface UcRunParamRepository extends JpaRepository<UcRunParam, String> {

    /**
     * 设备运行的参数
     *
     * @param id 设备ID
     * @return
     */
    public List<UcRunParam> findByUcDevicePattern_Id(String id);
}
