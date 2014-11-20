package com.energicube.eno.asset.repository;

import com.energicube.eno.asset.model.Locations;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Locations data access inteface
 *
 * @author CHENPING
 */
public interface LocationsRepository extends JpaRepository<Locations, Long> {

    /**
     * find locations by location code
     *
     * @param location location code
     * @return locations list
     */
    public List<Locations> findByLocation(String location) throws DataAccessException;

}
