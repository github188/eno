package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    public List<Organization> findByOrgidContaining(String orgid);

}