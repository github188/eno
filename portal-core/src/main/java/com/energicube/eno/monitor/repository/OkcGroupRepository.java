package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.OkcGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OkcGroupRepository extends JpaRepository<OkcGroup, Long> {

    public List<OkcGroup> findByGroupname(String groupName);

    public List<OkcGroup> findByLangcode(String langcode);

}
