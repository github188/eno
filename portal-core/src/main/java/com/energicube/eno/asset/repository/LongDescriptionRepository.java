package com.energicube.eno.asset.repository;

import com.energicube.eno.asset.model.LongDescription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LongDescriptionRepository extends
        JpaRepository<LongDescription, Long> {

}
