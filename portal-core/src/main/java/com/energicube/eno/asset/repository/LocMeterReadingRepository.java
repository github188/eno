package com.energicube.eno.asset.repository;

import com.energicube.eno.asset.model.LocMeterReading;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocMeterReadingRepository extends
        JpaRepository<LocMeterReading, Long> {

}
