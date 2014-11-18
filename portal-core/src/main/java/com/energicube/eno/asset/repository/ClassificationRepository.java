package com.energicube.eno.asset.repository;

import com.energicube.eno.asset.model.Classification;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 资产类别定义数据操作
 *
 * @author CHENPING
 */
public interface ClassificationRepository extends
        JpaRepository<Classification, Long> {

    /**
     * 查找类别{@link Classification}定义
     *
     * @param classificationid 类别ID
     * @param siteid           位置
     * @return 类别定义列表
     */
    public List<Classification> findByClassificationidAndSiteid(String classificationid, String siteid) throws DataAccessException;

    public List<Classification> findByClassificationid(String classificationid) throws DataAccessException;

}
