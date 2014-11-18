package com.energicube.eno.asset.repository;

import com.energicube.eno.asset.model.MeasureUnit;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 计量单位数据操作接口
 *
 * @author CHENPING
 */
public interface MeasureUnitRepository extends JpaRepository<MeasureUnit, Long> {

    /**
     * 查找指定的计量单位
     *
     * @param measureunitid 计量单位ID
     * @return 计量单位结果列表
     */
    public List<MeasureUnit> findByMeasureunitid(String measureunitid) throws DataAccessException;

}
