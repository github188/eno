package com.energicube.eno.asset.repository;

import com.energicube.eno.asset.model.MeterInGroup;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeterInGroupRepository extends JpaRepository<MeterInGroup, Long> {

    /**
     * 获取指定计量器组的信息
     *
     * @param groupname 计量器组名
     * @return 计量器列表信息
     */
    public List<MeterInGroup> findByGroupnameOrderBySequenceAsc(String groupname) throws DataAccessException;

    /**
     * 获取指定计量器组名称与计量器名称的列表信息
     *
     * @param groupname 计量器组名称
     * @param metername 计量器名称
     * @return 计量器组包含计量器信息
     */
    public List<MeterInGroup> findByGroupnameAndMetername(String groupname, String metername) throws DataAccessException;
}
