package com.energicube.eno.asset.repository;

import com.energicube.eno.asset.model.LocHierarchy;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LocHierarchyRepository extends
        JpaRepository<LocHierarchy, Long> {

    /**
     * 获取所有位置层级关系数据
     */
    @Query("select h,l from LocHierarchy h,Locations l where h.location=l.location and l.disabled=false and coalesce(h.siteid,'')=coalesce(l.siteid,'') and coalesce(l.siteid,'')=:siteid and l.status='OPERATING'")
    public List<Object[]> findLocHierarchyList(@Param("siteid") String siteid) throws DataAccessException;

}
