package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.UclcLinkcell;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UclcLinkcellRepository extends JpaRepository<UclcLinkcell, Integer> {

    @Transactional(readOnly = true)
    List<UclcLinkcell> findByLinkageid(long linkageid);
}
