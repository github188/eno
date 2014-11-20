package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.OkcDMNumRange;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 数字范围型数据映射数据操作接口
 *
 * @author CHENPING
 */
public interface OkcDMNumRangeRepository extends
        JpaRepository<OkcDMNumRange, Long> {

}
