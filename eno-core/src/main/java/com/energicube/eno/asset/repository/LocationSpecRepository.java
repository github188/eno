package com.energicube.eno.asset.repository;

import com.energicube.eno.asset.model.LocationSpec;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationSpecRepository extends
        JpaRepository<LocationSpec, Long> {

}
