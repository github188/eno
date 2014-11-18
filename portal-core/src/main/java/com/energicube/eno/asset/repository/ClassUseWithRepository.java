package com.energicube.eno.asset.repository;

import com.energicube.eno.asset.model.ClassUseWith;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 分类结构使用的对象数据操作
 *
 * @author CHENPING
 */
public interface ClassUseWithRepository extends
        JpaRepository<ClassUseWith, Long> {

}
