package com.energicube.eno.asset.repository;

import com.energicube.eno.asset.model.LocationMeter;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * LocationMeter Data Access InterFace
 *
 * @author CHENPING
 */
public interface LocationMeterRepository extends
        JpaRepository<LocationMeter, Long> {

    /**
     * 查找附加到位置的计量器
     *
     * @param location 位置代码
     * @return 计量器列表
     */
    public List<LocationMeter> findByLocation(String location) throws DataAccessException;

    /**
     * 查找附加到位置的计量器
     *
     * @param location  位置代码
     * @param metername 计量器名称
     * @return 计量器列表
     */
    public List<LocationMeter> findByLocationAndMetername(String location, String metername) throws DataAccessException;

}
