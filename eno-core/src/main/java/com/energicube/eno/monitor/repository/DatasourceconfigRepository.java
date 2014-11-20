package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.Datasourceconfig;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DatasourceconfigRepository extends JpaRepository<Datasourceconfig, Integer> {
    List<Datasourceconfig> findByDatasourceconfigid(String datasourceconfigid);
}
