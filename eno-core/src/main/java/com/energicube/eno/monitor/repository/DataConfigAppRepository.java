package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.DataConfigApp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataConfigAppRepository extends JpaRepository<DataConfigApp, Integer> {

    DataConfigApp findByConfigid(String configid);

    DataConfigApp findByApp(String app);

}
