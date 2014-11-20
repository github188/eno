package com.energicube.eno.asset.repository;

import com.energicube.eno.asset.model.LocSystem;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LocSystemRepository extends JpaRepository<LocSystem, Long> {

    /**
     * Lookup specifies the ID of the {@link LocSystem} object
     *
     * @param systemid ID
     * @return {@link LocSystem} List
     */
    public List<LocSystem> findBySystemid(String systemid) throws DataAccessException;
}
