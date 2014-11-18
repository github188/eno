package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.Dict;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DictRepository extends JpaRepository<Dict, Integer> {
    /**
     * 根据id查询
     *
     * @param id
     * @return
     * @throws DataAccessException
     */
    @Query("select d from Dict d where d.value is not null")
    public List<Dict> findByNullValue() throws DataAccessException;

    @Query("select distinct d.assetattrid,d.value,d.translate,d.icon,d.icon_number,d.display from Dict d  ")
    public List<Dict> findDistinctValue() throws DataAccessException;
}
