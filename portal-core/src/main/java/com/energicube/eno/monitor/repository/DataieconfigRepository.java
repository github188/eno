package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.Dataieconfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DataieconfigRepository extends JpaRepository<Dataieconfig, Integer> {
    List<Dataieconfig> findByDatacolumnconfigid(String datacolumnconfigid);
}
