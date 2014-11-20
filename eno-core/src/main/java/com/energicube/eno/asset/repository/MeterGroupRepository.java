package com.energicube.eno.asset.repository;

import com.energicube.eno.asset.model.MeterGroup;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 计量器组数据操作接口
 *
 * @author CHENPING
 */
public interface MeterGroupRepository extends JpaRepository<MeterGroup, Long> {

    /**
     * 查找指定组名称的信息
     *
     * @param groupname 组名称
     */
    public List<MeterGroup> findByGroupname(String groupname) throws DataAccessException;
}
