package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.UclcLinkage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UclcLinkageRepository extends JpaRepository<UclcLinkage, Long> {

    @Transactional(readOnly = true)
    UclcLinkage findBylinkageid(long linkageid);
}
