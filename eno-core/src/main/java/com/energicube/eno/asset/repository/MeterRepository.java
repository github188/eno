package com.energicube.eno.asset.repository;

import com.energicube.eno.asset.model.Meter;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 计量器数据操作接口
 *
 * @author CHENPING
 */
public interface MeterRepository extends JpaRepository<Meter, Long> {

    public List<Meter> findByMetername(String metername) throws DataAccessException;

}
