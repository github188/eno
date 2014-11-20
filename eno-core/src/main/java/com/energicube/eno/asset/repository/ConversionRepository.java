package com.energicube.eno.asset.repository;

import com.energicube.eno.asset.model.Conversion;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 计量单位换算 数据操作
 *
 * @author CHENPING
 */
public interface ConversionRepository extends JpaRepository<Conversion, Long> {

}
