package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.Datacolsrelation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DatacolsrelationRepository extends JpaRepository<Datacolsrelation, Integer> {
    List<Datacolsrelation> findByDatacolumnconfigid(String datacolumnconfigid);
}
