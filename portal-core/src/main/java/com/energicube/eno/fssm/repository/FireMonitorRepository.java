package com.energicube.eno.fssm.repository;


import com.energicube.eno.fssm.model.FireMonitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FireMonitorRepository extends
        JpaRepository<FireMonitor, String> {

}
