package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.DataSql;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DataSqlconfigRepository extends JpaRepository<DataSql, Integer> {
    List<DataSql> findByDatasqlid(String datasqlid);
}
