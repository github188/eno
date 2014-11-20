package com.energicube.eno.monitor.repository;

import com.energicube.eno.monitor.model.Docinfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocinfoRepository extends JpaRepository<Docinfo, Long> {

    public List<Docinfo> findByDoctype(String doctype);
}